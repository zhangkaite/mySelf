package com.ttmv.datacenter.da.storm.calcLevel.bolt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.calcLevel.utils.HbaseServiceUtil;
import com.ttmv.datacenter.da.storm.calcLevel.utils.OcmsServiceUtil;
import com.ttmv.datacenter.da.storm.calcLevel.utils.beans.LeverRules;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

/**
 * Created by zbs on 16/2/23.
 */
public class LevelVerificationBolt extends BaseRichBolt {

    private static Logger logger = Logger.getLogger(LevelVerificationBolt.class);

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String userId = tuple.getStringByField(Constant.Tuple_Field_Spending_LevelVerify);
            logger.info("[用户降级业务]收到Spout传入的数据 userId:" + userId);
            //从hbase中查出一条数据
            Long integral = queryMonthIntegral(userId);
            Long userLevel = queryLevel(userId);
            logger.info("[用户降级业务]从hbase中查询数据 " + getMonthBefore() + "月的[月总积分]=" + integral + " [用户ID]=" + userId + " [等级]=" + userLevel);
            if (integral != null && userLevel != null) {
                //从ocms中查出该等级该降的分值
                LeverRules leverRules = OcmsServiceUtil.getLeverRules(Constant.USERFLAG, userLevel);
                logger.info("[用户降级业务]从ocms系统中查询出 [等级]=" + userLevel + " 保值经验为 : " + leverRules.getLowestexp());
                if (integral.compareTo(leverRules.getLowestexp()) < 0) {
                    //进行经验扣减
                    logger.info("[用户降级业务]对用户进行经验扣减，扣减"+leverRules.getDemotionRate()+"分");
                    Long score = HbaseServiceUtil.decreaseScore(userId,Constant.USERFLAG,leverRules.getDemotionRate());
                    logger.info("[用户降级业务]扣减后的用户积分"+score+"分，对用户进行等级重新计算,并推送");
                    HbaseServiceUtil.updateLevel(userId, Constant.USERFLAG, true);
                }
            }
            //结束
            outputCollector.ack(tuple);
        } catch (Exception e) {
           logger.error("[用户降级业务]出现异常，异常原因."+e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {return;}

    /**
     * 从hbase中获得运行时间上一个月的消费积分
     * @param userId
     * @return
     * @throws Exception
     */
    private Long queryMonthIntegral(String userId) throws Exception {
        if(userId == null || "".equals(userId))
            return null;
        String monthIntegral = HbaseServiceUtil.queryMonthIntegral(userId, Constant.USERFLAG, getMonthBefore());
        if (monthIntegral == null || "".equals(monthIntegral))
            return null;
        return Long.valueOf(monthIntegral);
    }
    /**
     * 从hbase中获得用户等级
     * @param userId
     * @return
     * @throws Exception
     */
    private Long queryLevel(String userId) throws Exception {
        if(userId == null || "".equals(userId))
            return null;
        String userLevel =HbaseServiceUtil.queryLevel(userId, Constant.USERFLAG);
        if (userLevel == null || "".equals(userLevel))
            return null;
        return Long.valueOf(userLevel);
    }

    /**
     * 获取user20160303这样的string，用户hbase的列名(取得运行时间上一个月的时间)
     *
     * @return
     */
    private static String getMonthBefore() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        Date date= cal.getTime();
        return simpleDateFormat.format(date);
    }

}

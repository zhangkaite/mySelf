package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * Created by zkt on 2016/4/5.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@DisallowConcurrentExecution
public class UserLoginResultDataToSdmsWorker extends QuartzWorker<Object> {
    private static Logger logger = LogManager.getLogger(UserLoginResultDataToSdmsWorker.class);
    // 注入redis队列名称
    private String userLoginFinishQueue = ConsumeSpendConstant.USERLOGINMRFINISHQUEUE;

    @Override
    protected List<Object> getData(int num) {
        logger.debug("从redis队列dams_bus_statistic_finish获取运营业务统计mr调用的数据");
        List ls = null;
        if (num > 0) {
            try {
                ls = TbRedisService.getRedisQueueImpi().getValueBatch(userLoginFinishQueue, 1);
                if(ls != null && ls.size() > 0){                	
                	logger.info("[DAMS#UserLoginResultDataToSdmsWorker]获取redis数据："+JsonUtil.getObjectToJson(ls));
                }
            } catch (Exception e) {
                logger.error("从redis队列dams_bus_statistic_finish获取运营业务统计数据失败，失败的原因是:", e);
            }
        }
        return ls;
    }

    @Override
    protected void process(Object obj) throws Exception {
        String data = (String) obj;
        JSONObject jsonObject = new JSONObject(data);
        String statisticDay = jsonObject.getString("statisticDay").substring(0, 10);
        String outputPath = jsonObject.getString("outputPath");
        String dateType=jsonObject.getString("dateType");
        logger.info("从redis队列" + userLoginFinishQueue + "获取运营用户登录业务统计mr调用推送的数据statisticDay：" + statisticDay + "outputPath:" +
                outputPath);
        doBusiness(statisticDay, outputPath,dateType);
    }


    public void doBusiness(String statisticDay, String outputPath,String dateType) throws Exception {
    	//直接实例化一个对象
    	UserLoginStatistics userLoginStatistics=new UserLoginStatistics();
    	userLoginStatistics.setParams(outputPath, ConsumeSpendConstant
                .USERLOGINMQQUEUE, statisticDay,"LO",dateType);
    	userLoginStatistics.doBusStatistics();

    }


    @Override
    protected String toLog(Object o) {
        return null;
    }


}

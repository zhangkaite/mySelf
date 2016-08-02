package com.ttmv.datacenter.da.storm.calcLevel.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.util.RedisUtil;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 运行时候校验是否达到保级要求，如果没达到，进行等级扣减
 * Created by zbs on 16/2/23.
 */
public class LevelVerificationSpout extends BaseRichSpout {

    private static Logger logger = Logger.getLogger(LevelVerificationSpout.class);

    private SpoutOutputCollector spoutOutputCollector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(Constant.Tuple_Field_Spending_LevelVerify));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        try {
            List<Object> dataList = RedisUtil.getValueBatch(Constant.COUSUMEMONTHONLINEQUE, 10);
            if (dataList != null && dataList.size() > 0) {
                for(Object object : dataList) {
                    logger.debug("[用户降级业务]从redis中获取的原始数据 => " + object.toString());
                    String userId = getUserId(object);
                    logger.info("[用户降级业务]发射需要进行判断决定是否降级的用户"+userId+"数据到Bolt");
                    spoutOutputCollector.emit(new Values(userId));
                }
            }
        }catch (Exception e){
            logger.error("[用户降级业务]发射需要进行判断决定是否降级的用户到Bolt失败，失败原因:"+e);
        }
    }

    private String getUserId(Object data) throws JSONException{
        JSONObject jsonObject = new JSONObject((String)data);
        return jsonObject.getString("userId");
    }
}

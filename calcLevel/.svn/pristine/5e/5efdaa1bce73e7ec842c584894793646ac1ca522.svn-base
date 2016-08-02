package com.ttmv.datacenter.da.storm.calcLevel.service;

import backtype.storm.task.OutputCollector;
import backtype.storm.tuple.Values;

import org.json.JSONException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by zbs on 16/2/22.
 */
public abstract class Service {

    private static Logger logger = Logger.getLogger(Service.class);

    private OutputCollector outputCollector;

    public Service(OutputCollector outputCollector) throws Exception {
        if(outputCollector == null)
            throw new Exception("[[RedisDataLevelBolt]]OutputCollector对象为空");
        this.outputCollector = outputCollector;
    }

    public void perform(List<Object> data,Map<String,Integer> rules){
        if(data == null || rules == null){
            logger.error("[[RedisDataLevelBolt]]data数据为空，或者 rules 规则为空。");
            return;
        }
        try {
            getRule(rules);
            if(!verify(rules)){
                logger.error("[[RedisDataLevelBolt]]规则参数不全，请检查ocms系统中参数是否正确。");
                return;
            }
            for (Object object : data) {
            	logger.debug("[[RedisDataLevelBolt]]从spout获取的消费数据:"+object.toString());
                List<Object> result = headle(object);
                if (result != null && result.size() > 0) {
                    outputCollector.emit(new Values(result));
                }
            }
        }catch(Exception e) {
            logger.error("[[RedisDataLevelBolt]]对计算后的数据发射失败，失败的原因是：", e);
        }
    }

    protected abstract boolean verify(Map<String,Integer> rules);
    protected abstract void getRule(Map<String,Integer> rules);
    protected abstract List<Object> headle(Object object) throws JSONException;
}

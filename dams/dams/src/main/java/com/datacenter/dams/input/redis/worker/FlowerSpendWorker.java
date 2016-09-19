package com.datacenter.dams.input.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.OuterRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.input.redis.entity.FlowerSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorkerBatch;
import com.google.common.base.Strings;

public class FlowerSpendWorker extends QuartzWorkerBatch<String>{
	
	private static Logger logger=LogManager.getLogger(FlowerSpendWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings= OuterRedisQueueDao.getRedisQueueMessage(RedisQueueUtil.IM_FLOWER_REDISQUEUE,sum);	
				if(listStrings != null && listStrings.size() > 0){
					logger.info("[DAMS送花FlowerSpendWorker]读取外部系统队列数据："+JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS送花FlowerSpendWorker]读取外部系统队列数据出错！",e);
			}
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		if(listStrings != null && listStrings.size() > 0){
			for(String message : listStrings){
				if(!Strings.isNullOrEmpty(message)){
					FlowerSpendInfo flower = (FlowerSpendInfo)JsonUtil.getObjectFromJson(message, FlowerSpendInfo.class);
					MessageHandlerCenter.flowerConsumeCenter.handler(flower);
				}
			}
		}
	}

	
	@Override
	protected String toLog(String log) {
		return null;
	}

}

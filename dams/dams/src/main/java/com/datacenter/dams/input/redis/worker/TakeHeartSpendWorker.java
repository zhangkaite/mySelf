package com.datacenter.dams.input.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.OuterRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.input.redis.entity.TakeHeartSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorkerBatch;

public class TakeHeartSpendWorker extends QuartzWorkerBatch<String>{

	private static Logger logger=LogManager.getLogger(TakeHeartSpendWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings = OuterRedisQueueDao.getRedisQueueMessage(RedisQueueUtil.IM_TAKEHEART_REDISQUEUE,sum);
				if(listStrings != null && listStrings.size() > 0){
					logger.info("[DAMS送心TakeHeartSpendWorker]读取外部系统队列数据："+JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS送花TakeHeartSpendWorker]读取外部系统队列数据出错！",e);
			}
		}
		return null;
	}


	@Override
	protected void process(List<String> listStrings) throws Exception {
		if(listStrings != null && listStrings.size() > 0){
			for(String message:listStrings){	
				TakeHeartSpendInfo info = (TakeHeartSpendInfo)JsonUtil.getObjectFromJson(message, TakeHeartSpendInfo.class);
				MessageHandlerCenter.takeHeartConsumeCenter.handler(info);
			}
		}
	}

	@Override
	protected String toLog(String log) {
		return null;
	}

}

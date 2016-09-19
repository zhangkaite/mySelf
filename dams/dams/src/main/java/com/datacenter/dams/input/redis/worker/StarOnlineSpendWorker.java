package com.datacenter.dams.input.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.OuterRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.input.redis.entity.OnlineTimeSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorkerBatch;
import com.google.common.base.Strings;

/**
 * 明星在线麦时数据接收worker，数据存入redis
 * @author wulinli
 *
 */
public class StarOnlineSpendWorker extends QuartzWorkerBatch<String>{

	private static Logger logger=LogManager.getLogger(StarOnlineSpendWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings = OuterRedisQueueDao.getRedisQueueMessage(RedisQueueUtil.IM_STARONLINE_REDISQUEUE,sum);
				if(listStrings != null && listStrings.size() > 0){	
					logger.info("[DAMS明星麦时登陆StarOnlineSpendWorker]读取外部系统队列数据："+JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS明星麦时登陆StarOnlineSpendWorker#ERROR]读取外部系统队列数据出错。",e);
			}
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		if(listStrings != null && listStrings.size() > 0){
			for(String message : listStrings){			
				/* 将数据存储在redis中以便扫面全表的使用 */
				OnlineTimeSpendInfo info = null;
				if(!Strings.isNullOrEmpty(message)){					
					info = (OnlineTimeSpendInfo)JsonUtil.getObjectFromJson(message, OnlineTimeSpendInfo.class);
				}
				/* 处理数据 */
				MessageHandlerCenter.starOnlineSpendCenter.handler(info);
			}
		}
	}
	
	@Override
	protected String toLog(String log) {
		return null;
	}
}

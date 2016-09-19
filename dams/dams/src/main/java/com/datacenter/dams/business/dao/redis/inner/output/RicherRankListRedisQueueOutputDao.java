package com.datacenter.dams.business.dao.redis.inner.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class RicherRankListRedisQueueOutputDao{

	private static Logger logger=LogManager.getLogger(RicherRankListRedisQueueOutputDao.class);
	private static RedisQueueImpi redisQueueImpi;
	
	public static String getRedisQueueMessage() throws Exception {
		logger.debug("[DAMS#RicherRank#1.1]获取storm计算结果>>>富豪排行榜redis队列数据.");
		String data = redisQueueImpi.getValue(RedisQueueUtil.RICHER_RANKLIST_REDISQUEUE_RESULT);
		if(data != null && !"".equals(data)){
			return data;
		}
		return null;
	}

	public static void sendMessage(String message) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.RICHER_RANKLIST_REDISQUEUE_RESULT, message);
	}

	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		RicherRankListRedisQueueOutputDao.redisQueueImpi = redisQueueImpi;
	}
}

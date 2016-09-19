package com.datacenter.dams.business.dao.redis.inner.output;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class ActivityStarRankListRedisQueueOutputDao {

	private static Logger logger=LogManager.getLogger(ActivityStarRankListRedisQueueOutputDao.class);
	private static RedisQueueImpi redisQueueImpi;
	
	public static List<String> getRedisQueueMessage(int sum) throws Exception {
		logger.debug("[DAMS#ActivityStarRankOutput#1.1]获取storm计算结果>>>主播吊牌活动排行榜redis队列数据.");
		List<String> datas = redisQueueImpi.getValueBatch(RedisQueueUtil.ACTIVITY_INNER_REDISQUEUE_STAR_RANK_OUTPUT,sum);
		if(datas != null && datas.size() > 0){
			return datas;
		}
		return null;
	}

	public static void sendMessage(String message) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.ACTIVITY_INNER_REDISQUEUE_STAR_RANK_OUTPUT, message);
	}

	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		ActivityStarRankListRedisQueueOutputDao.redisQueueImpi = redisQueueImpi;
	}
}

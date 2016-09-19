package com.datacenter.dams.business.dao.redis.inner.output;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class ActivitySwingTagRedisQueueOutputDao {

	private static Logger logger=LogManager.getLogger(ActivitySwingTagRedisQueueOutputDao.class);
	private static RedisQueueImpi redisQueueImpi;
	
	public static List<String> getRedisQueueMessage(int sum) throws Exception {
		List<String> datas = redisQueueImpi.getValueBatch(RedisQueueUtil.ACTIVITY_INNER_REDISQUEUE_SWINGTAG_OUTPUT,sum);
		if(datas != null && datas.size() > 0){
			logger.debug("[DAMS#ActivitySwingTagOutput#1.1]获取storm计算结果>>>吊牌活动信息redis队列数据.数据是:"+datas);
			return datas;
		}
		return null;
	}

	public static void sendMessage(String message) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.ACTIVITY_INNER_REDISQUEUE_SWINGTAG_OUTPUT, message);
	}

	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		ActivitySwingTagRedisQueueOutputDao.redisQueueImpi = redisQueueImpi;
	}
}

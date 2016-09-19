package com.datacenter.dams.business.dao.redis.inner.input;

import java.util.List;

import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class OuterRedisQueueDao {

	private static RedisQueueImpi redisQueueImpi;

	/**
	 * 获取给定队列名的一条数据
	 * @param queueName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRedisQueueMessage(String queueName,int sum) throws Exception {
		List<String> datas = redisQueueImpi.getValueBatch(queueName,sum);
		if(datas != null && datas.size() > 0){
			return datas;
		}
		return null;
	}

	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		OuterRedisQueueDao.redisQueueImpi = redisQueueImpi;
	}
}

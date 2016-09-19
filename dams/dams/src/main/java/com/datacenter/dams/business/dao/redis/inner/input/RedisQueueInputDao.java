package com.datacenter.dams.business.dao.redis.inner.input;

import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class RedisQueueInputDao{

	private RedisQueueImpi redisQueueImpi;
	
	public void sendToRedisQueue(String queueName,String data) throws Exception {
		redisQueueImpi.setValue(queueName, data);
	}

	public RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		this.redisQueueImpi = redisQueueImpi;
	}
}

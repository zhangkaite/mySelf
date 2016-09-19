package com.datacenter.dams.business.dao.redis.inner.input;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class OffLineRedisQueueInputDao implements RedisQueueInputDaoInter{

	private RedisQueueImpi redisQueueImpi;
	
	@Override
	public void sendToRedisQueue(String data) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.SPEND_OFFLINE, data);
	}
	
	@Override
	public boolean RedisQueueIsFull() throws Exception {
		return false;
	}

	public RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		this.redisQueueImpi = redisQueueImpi;
	}
}

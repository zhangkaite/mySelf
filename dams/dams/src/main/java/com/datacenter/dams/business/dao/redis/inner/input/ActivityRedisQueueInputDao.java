package com.datacenter.dams.business.dao.redis.inner.input;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

/**
 * 活动redis队列的存储
 * @author wll
 *
 */
public class ActivityRedisQueueInputDao implements RedisQueueInputDaoInter{

	private RedisQueueImpi redisQueueImpi;
	
	@Override
	public void sendToRedisQueue(String data) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.ACTIVITY_INNER_REDISQUEUE_INPUT, data);
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

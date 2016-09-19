package com.datacenter.dams.business.dao.redis.inner.input;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class TcoinConsumeRedisQueueInputDao implements RedisQueueInputDaoInter{

	private RedisQueueImpi redisQueueImpi;
	
	@Override
	public void sendToRedisQueue(String data) throws Exception {
		redisQueueImpi.setValue(RedisQueueUtil.CONSUME_SPEND, data);
	}
	
	@Override
	public boolean RedisQueueIsFull() throws Exception {
		Long sum = redisQueueImpi.size(RedisQueueUtil.CONSUME_SPEND);
		if(sum != null){
			if(sum >= RedisQueueUtil.CONSUM_REDISQUEUE_FULL){
				return true;
			}else if(sum < RedisQueueUtil.CONSUM_REDISQUEUE_FULL){
				return false;
			}
		}
		return false;
	}
	
	public RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}
	
	public void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		this.redisQueueImpi = redisQueueImpi;
	}
}

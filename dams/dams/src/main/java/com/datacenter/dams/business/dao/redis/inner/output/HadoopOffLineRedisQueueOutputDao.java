package com.datacenter.dams.business.dao.redis.inner.output;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class HadoopOffLineRedisQueueOutputDao{

	private RedisQueueImpi redisQueueImpi;
	
	public String getRedisQueueMessage() throws Exception {
		String data = redisQueueImpi.getValue(RedisQueueUtil.SPEND_OFFLINE);
		if(data != null && !"".equals(data)){
			return data;
		}
		return null;
	}

	public void sendMessage(String message) throws Exception {
		
	}

	public RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		this.redisQueueImpi = redisQueueImpi;
	}
}

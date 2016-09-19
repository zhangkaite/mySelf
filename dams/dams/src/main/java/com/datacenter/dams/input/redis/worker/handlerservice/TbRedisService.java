package com.datacenter.dams.input.redis.worker.handlerservice;

import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class TbRedisService {

	private static RedisQueueImpi redisQueueImpi;

	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		TbRedisService.redisQueueImpi = redisQueueImpi;
	}
}

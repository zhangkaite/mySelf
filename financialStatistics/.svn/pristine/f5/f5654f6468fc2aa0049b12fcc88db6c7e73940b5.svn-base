package com.ttmv.financialStatistics;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	private static Logger logger = LogManager.getLogger(RedisUtil.class);

	public static Jedis getRedis() throws Exception {
		return new Jedis("dars_comu_1", 51313);
	}
	
	public static void pushDataToRedisQueue(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getRedis();
			logger.info("[redis队列] ==> 发送数据"+value+"到"+key+"队列");
			jedis.rpush(key, new String[] { value });
			jedis.close();
		} catch (Exception e) {
			throw e;
		}

	}
	
	
	

}

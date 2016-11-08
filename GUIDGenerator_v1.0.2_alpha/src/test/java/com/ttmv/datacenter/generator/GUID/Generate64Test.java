package com.ttmv.datacenter.generator.GUID;

import org.junit.Test;
import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.jedis.JedisPoolAgent;

import static org.junit.Assert.*;

public class Generate64Test {
	@Test
	public void testGenerate() throws Exception {
		RedisPoolConfig poolConfig = new RedisPoolConfig(108, 20, 60000);
		RedisAgent redis = new JedisPoolAgent("test_dc_redis_counter", 50000,
				poolConfig, 30000);
		for (int i = 0; i < 100; i++) {
			GUIDGenerator guid = new GUIDGenerator64(redis);
			Long id = guid.guid("test_64");
			assertNotNull(id);
		}
	}
}

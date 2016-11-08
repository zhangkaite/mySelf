package com.ttmv.datacenter.generator.GUID;

import static org.junit.Assert.*;
import org.junit.Test;
import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.jedis.JedisPoolAgent;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年3月23日
 */
public class Generate32Test {	
	@Test
	public void testGenerate32() throws Exception {
		RedisPoolConfig poolConfig = new RedisPoolConfig(108, 20, 60000);
		RedisAgent redis = new JedisPoolAgent("test_dc_redis_counter", 50000,
				poolConfig, 30000);
		for (int i = 0; i < 100; i++) {
			GUIDGenerator guid = new GUIDGenerator32(redis);
			Long l = guid.guid("test_32");
			assertNotNull(l);
		}
	}
}

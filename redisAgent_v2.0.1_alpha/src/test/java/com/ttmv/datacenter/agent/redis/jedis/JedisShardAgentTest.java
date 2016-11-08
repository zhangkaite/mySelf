package com.ttmv.datacenter.agent.redis.jedis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.ttmv.datacenter.agent.redis.jedisShard.impl.JedisShardPoolAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import com.ttmv.datacenter.agent.redis.RedisAgent;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年3月11日
 */
public class JedisShardAgentTest {

	private RedisAgent jedisAgent;
	private String key = "test_key";
	private String value = "test_value";
	private String mark = key;
	private String lua = "redis.call('set',KEYS[1],KEYS[2])";
	private int time = 1;
	private long startTime;  //获取开始时间 
	private long endTime;  //获取结束时间 

	@Before
	public void init() {
		try {
			// this.jedisAgent = new JedisAgent("192.168.15.20", 50031);
			startTime = System.currentTimeMillis();
			this.jedisAgent = new JedisShardPoolAgent(getShardList(), getConfig());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@After
	public void after() {
		endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
	}

	private List<JedisShardInfo> getShardList(){
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisA = new JedisShardInfo("test_uc_redis_m1", 50031,"shard1");
		JedisShardInfo jedisB = new JedisShardInfo("test_uc_redis_m1", 50003,"shard2");
		JedisShardInfo jedisC = new JedisShardInfo("test_uc_redis_m1", 50000,"shard3");
		list.add(jedisA);
		list.add(jedisB);
		list.add(jedisC);
		return list;
	}
	
	private JedisPoolConfig getConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(100);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(50);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(60000);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		return config;
	}

	@Test
	public void testSetIfNotExist() {
		try {
			System.out.println("开始测试setIfNotExist...");
			jedisAgent.setIfNotExist(key, value);
			jedisAgent.setIfNotExist(key, value + 1);
			assertTrue(jedisAgent.exists(key));
			assertEquals(value, jedisAgent.getValue(key));
			jedisAgent.deleteData(key);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSetOverride() {
		try {
			System.out.println("开始测试setOverride...");
			jedisAgent.setOverride(key, value + 111);
			jedisAgent.setOverride(key, value);
			assertTrue(jedisAgent.exists(key));
			assertEquals(value, jedisAgent.getValue(key));
			jedisAgent.deleteData(key);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testDeleteData() {
		try {
			System.out.println("开始测试deleteData...");
			jedisAgent.deleteData(key);
			jedisAgent.setOverride(key, value + 111);
			jedisAgent.deleteData(key);
			assertFalse(jedisAgent.exists(key));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSetDataTimeout() {
		try {
			System.out.println("开始测试setDataTimeout...");
			jedisAgent.setDataTimeout(key, time);
			Thread.sleep(time * 10);
			assertFalse(jedisAgent.exists(key));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetValue() {
		try {
			System.out.println("开始测试getValue...");
			assertNull(jedisAgent.getValue(key));
			jedisAgent.setOverride(key, value);
			assertNotNull(jedisAgent.getValue(key));
			assertEquals(value, jedisAgent.getValue(key));
			jedisAgent.deleteData(key);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testExists() {
		try {
			System.out.println("开始测试exists...");
			assertFalse(jedisAgent.exists(key));
			jedisAgent.setOverride(key, value);
			assertTrue(jedisAgent.exists(key));
			jedisAgent.deleteData(key);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testShard(){
		try {
			System.out.println("开始测试分片是否能正确的做操作...");
			for(int i=0;i<100;i++){
				jedisAgent.setOverride(key+i, value+i);
			}
			for(int i=0;i<100;i++){
			  System.out.println(key+i+":"+jedisAgent.getValue(key+i));
			  assertTrue(jedisAgent.exists(key+i));
			}
			for(int i=0;i<100;i++){
				jedisAgent.deleteData(key+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEvalLua() {
		try {
			System.out.println("开始测试evalLua...");
		    jedisAgent.evalLua(lua,null, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assertNotNull(e.getMessage());
		}
	}
	
	@Test
	public void testEvalShardLua() {
		try {
			System.out.println("开始测试evalShardLua...");
			for(int i=0;i<100;i++){
				  List<String> list = new ArrayList<String>();
				  list.add(key+i);
				  list.add(value+i);
			      jedisAgent.evalShardLua(lua, key+i , list , null);
			}
			for(int i=0;i<100;i++){
			  System.out.println(jedisAgent.getValue(key+i));
			  assertTrue(jedisAgent.exists(key+i));
			}
		} catch (Exception e) {
			e.getMessage();
			fail();
		}
	}
	
	@Test
	public void testPerformance() throws Exception {
		int i = 0;
		int make = 0;
		while (make == 0) {
			while (i < 100) {
				i++;
				Thread thread = new Thread(new Runnable() {
					public void run() {		
						for (int j = 0; j < 100; j++) {
							try {
								System.out.println(Thread.currentThread()+"  j:" + j);
								jedisAgent.setOverride(key+j, value+j);
								//jedisAgent.deleteData(key);
							} catch (Exception e) {
								e.printStackTrace();
								fail();
							}
						}
					}
				});
				thread.start();
			}
			Thread.sleep(20000);
			make = 1;
		}
	}

}

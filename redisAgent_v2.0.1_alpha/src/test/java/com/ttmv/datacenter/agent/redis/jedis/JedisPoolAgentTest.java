package com.ttmv.datacenter.agent.redis.jedis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.agent.redis.jedis.impl.JedisPoolAgent;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年3月11日
 */
public class JedisPoolAgentTest {

	private JedisPoolAgent jedisAgent;
	//private String key = "test_key";
	//private String value = "test_value";
	//private String lua = "redis.call('set','" + key + "','" + value + "')";
	//private int time = 1;
	private long startTime;  //获取开始时间 
    private long endTime;  //获取结束时间 
    
	@Before
	public void init() {
		try {
			startTime=System.currentTimeMillis(); 
			RedisPoolConfig config = new RedisPoolConfig(100,50,60000);
			this.jedisAgent = new JedisPoolAgent("192.168.13.35",6379,config,30000);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@After
	public void after() {
		endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
	}

//	@Test
//	public void testSetIfNotExist() {
//		try {
//			System.out.println("开始测试setIfNotExist...");
//			jedisAgent.setIfNotExist(key, value);
//			jedisAgent.setIfNotExist(key, value + 1);
//			assertTrue(jedisAgent.exists(key));
//			assertEquals(value, jedisAgent.getValue(key));
//			jedisAgent.deleteData(key);
//		} catch (Exception e) {
//			e.getMessage();
//			System.out.print(e);
//			fail();
//		}
//	}
//
//	@Test
//	public void testSetOverride() {
//		try {
//			System.out.println("开始测试setOverride...");
//			jedisAgent.setOverride(key, value + 111);
//			jedisAgent.setOverride(key, value);
//			assertTrue(jedisAgent.exists(key));
//			assertEquals(value, jedisAgent.getValue(key));
//			jedisAgent.deleteData(key);
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testDeleteData() {
//		try {
//			System.out.println("开始测试deleteData...");
//			jedisAgent.deleteData(key);
//			jedisAgent.setOverride(key, value + 111);
//			jedisAgent.deleteData(key);
//			assertFalse(jedisAgent.exists(key));
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testSetDataTimeout() {
//		try {
//			System.out.println("开始测试setDataTimeout...");
//			jedisAgent.setDataTimeout(key, time);
//			Thread.sleep(time * 10);
//			assertFalse(jedisAgent.exists(key));
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testGetValue() {
//		try {
//			System.out.println("开始测试getValue...");
//			assertNull(jedisAgent.getValue(key));
//			jedisAgent.setOverride(key, value);
//			assertNotNull(jedisAgent.getValue(key));
//			assertEquals(value, jedisAgent.getValue(key));
//			jedisAgent.deleteData(key);
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testExists() {
//		try {
//			System.out.println("开始测试exists...");
//			assertFalse(jedisAgent.exists(key));
//			jedisAgent.setOverride(key, value);
//			assertTrue(jedisAgent.exists(key));
//			jedisAgent.deleteData(key);
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testEvalLua() {
//		try {
//			System.out.println("开始测试evalLua...");
//			jedisAgent.evalLua(lua, new ArrayList<String>(), null);
//			assertTrue(jedisAgent.exists(key));
//			assertEquals(value, jedisAgent.getValue(key));
//			jedisAgent.deleteData(key);
//		} catch (Exception e) {
//			e.getMessage();
//			fail();
//		}
//	}
//
//	@Test
//	public void testPerformance() throws Exception {
//		int i = 0;
//		int make = 0;
//		while (make == 0) {
//			while (i < 100) {
//				i++;
//				Thread thread = new Thread(new Runnable() {
//					public void run() {
//						for (int j = 0; j < 100; j++) {
//							try {
//								System.out.println(Thread.currentThread()+"  j:" + j);
//								jedisAgent.setOverride(key, value);
//								jedisAgent.deleteData(key);
//							} catch (Exception e) {
//								e.printStackTrace();
//								fail();
//							}
//						}
//					}
//				});
//				thread.start();
//			}
//			Thread.sleep(20000);
//			make = 1;
//		}
//	}
//
//	@Test
//	public void testSetHashKey()throws Exception{
//		jedisAgent.setHashKeyField("one", "1", "1");
//		String json = jedisAgent.getHashKeyField("one", "1");
//		System.out.println(json);
//		jedisAgent.deleteData("one");
//	}
//
//	@Test
//	public void testSetMultipleHashKeyFields()throws Exception{
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("two1", "1");
//		map.put("two2", "2");
//		map.put("two3", "3");
//		jedisAgent.setMulipleHashKeyFields("two", map);
//		Map<String,String> result = jedisAgent.getHashKeyAllField("two");
//		System.out.println(result);
//		jedisAgent.deleteData("two");
//	}

	@Test
	public void testString() throws Exception {
		jedisAgent.setIfNotExist("junitTest", "aaa");
		jedisAgent.setIfNotExist("junitTest", "bbb");
		assertTrue(jedisAgent.exists("junitTest"));
		assertEquals("aaa", jedisAgent.getValue("junitTest"));
		jedisAgent.setOverride("junitTest", "ccc");
		assertEquals("ccc", jedisAgent.getValue("junitTest"));
		jedisAgent.deleteData("junitTest");
	}

	@Test
	public void testZset() throws Exception {
		jedisAgent.zsetAdd("junitZset","aaa",111);
		jedisAgent.zsetAdd("junitZset", "bbb", System.currentTimeMillis());
		jedisAgent.zsetAdd("junitZset","ccc",0);
		assertTrue(jedisAgent.zsetGetValue("junitZset","aaa") == 111);
        assertTrue(jedisAgent.zsetGetAll("junitZset").size() == 3);
		jedisAgent.zsetDelete("junitZset","bbb");
		assertTrue(jedisAgent.zsetGetAll("junitZset").size() == 2);
		assertTrue(jedisAgent.zsetRangeByScore("junitZset",-1,1).size() == 1);
		//-------- pip ---------------
		List<SetCollectionBean> list = new ArrayList<SetCollectionBean>();
		list.add(new SetCollectionBean("junitZsetPip","aaa",111));
		list.add(new SetCollectionBean("junitZsetPip","bbb",222));
		list.add(new SetCollectionBean("junitZsetPip","ccc",333));
		jedisAgent.zsetPipAdd(list);
		assertTrue(jedisAgent.zsetGetAll("junitZsetPip").size() == 3);
		assertTrue(jedisAgent.zsetGetValue("junitZsetPip","aaa") == 111);
		jedisAgent.zsetPipDelete(list);
		assertTrue(jedisAgent.zsetGetAll("junitZsetPip").size() == 0);
	}
}

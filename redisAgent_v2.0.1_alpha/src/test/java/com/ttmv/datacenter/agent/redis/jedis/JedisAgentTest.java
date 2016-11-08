package com.ttmv.datacenter.agent.redis.jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ttmv.datacenter.agent.redis.jedis.impl.JedisAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by James on 15/1/16.
 */
public class JedisAgentTest {

	private JedisAgent jedisAgent;
	private String key = "test_key";
	private String value = "test_value";
	private String lua = "redis.call('set','" + key + "','" + value + "')";
	private int time = 1;
    private long startTime;  //获取开始时间 
    private long endTime;  //获取结束时间 
	
    @Before
	public void init() {
		try {
			startTime=System.currentTimeMillis();   
			this.jedisAgent = new JedisAgent("test_uc_redis_m1", 50031);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
    
    @After
    public void after(){
    	endTime = System.currentTimeMillis();   
    	System.out.println("程序运行时间： "+(endTime-startTime)+"ms");  
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
			e.getMessage();
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
			e.getMessage();
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
			e.getMessage();
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
			e.getMessage();
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
			e.getMessage();
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
			e.getMessage();
			fail();
		}
	}

	@Test
	public void testEvalLua() {
		try {
			System.out.println("开始测试evalLua...");
			jedisAgent.evalLua(lua, new ArrayList<String>(), null);
			assertTrue(jedisAgent.exists(key));
			assertEquals(value, jedisAgent.getValue(key));
			jedisAgent.deleteData(key);
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
								jedisAgent.setOverride(key, value);
								jedisAgent.deleteData(key);
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
	
	@Test
	public void testSetHashKey()throws Exception{
		jedisAgent.setHashKeyField("one", "1", "1");
		String json = jedisAgent.getHashKeyField("one", "1");
		System.out.println(json);
		jedisAgent.deleteData("one");
	}
	
	@Test
	public void testSetMultipleHashKeyFields()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("two1", "1");
		map.put("two2", "2");
		map.put("two3", "3");	
		jedisAgent.setMulipleHashKeyFields("two", map);
		Map<String,String> result = jedisAgent.getHashKeyAllField("two");
		System.out.println(result);
		jedisAgent.deleteData("two");
	}
}

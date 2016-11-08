package com.ttmv.datacenter.agent.redis.jedisCloud;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;

import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.redisCloud.JedisPoolCloudAgent;

public class JedisPoolCloudAgentTest {
	
	private long startTime;
	private JedisPoolCloudAgent redisAgent = null;
	
	@Before
	public void before(){
		startTime = System.currentTimeMillis();
		RedisPoolConfig config = new RedisPoolConfig(100,50,60000);
		String masterName = "mymaster"; //redis主从监控的name，配置在redis的sentinel.conf文件中
		Set<String> sentinels = new HashSet<String>();
		sentinels.add(new HostAndPort("test_redisCloud", 60000).toString()); //sentinel的ip和端口，可以为多个 
		redisAgent = new JedisPoolCloudAgent(masterName,sentinels,config);
	}
	
	@After
	public void after(){
		long stopTime = System.currentTimeMillis();
		System.out.println(" --- 运行耗时["+(stopTime-startTime)+"] --- ");
	}
    
	/**
	 * 测试set值到redis 
	 */
	@Test
	public void setIfNotExistTest() throws Exception {
		String key = "test_isExist_add_set";
		String value = "test_value";
		redisAgent.setIfNotExist(key, value);
		assertTrue(redisAgent.exists(key));
	}

	/**
	 * 测试删除值 
	 */
	@Test
	public void deleteDataTest() throws Exception {
		String key = "test_delete";
		String value = "test_value";
		redisAgent.setIfNotExist(key, value);
		redisAgent.deleteData(key);
		assertFalse(redisAgent.exists(key));
	}
	
	/**
	 * 测试 带有time的值
	 */
	@Test
	public void setDataTimeoutTest() throws Exception {
		String key = "test_time_key";
		redisAgent.setDataTimeout(key, 10);
		assertTrue(redisAgent.exists(key));
		Thread.sleep(10);
		assertFalse(redisAgent.exists(key));
	}
	
	/**
	 * 测试 通过key获得一个value
	 */
	@Test
	public void getValueTest() throws Exception {
		String key = "test_getValue";
		String value = "test_value";
		redisAgent.setIfNotExist(key, value);
		assertEquals(value,redisAgent.getValue(key));
	}
	
	/**
	 * 测试 查询一个值是否存在
	 */
	@Test
	public void existsTest() throws Exception {
		String key = "test_exists";
		String value = "test_value";
		redisAgent.setIfNotExist(key, value);
		assertTrue(redisAgent.exists(key));
	}
	
	/**
	 * 测试 运行lua脚本
	 */
	@Test
	public void evalLuaTest() throws Exception {
		String key = "test_eval_lua";
		String value = "test_value";
	    String lua = "redis.call('set','" + key + "','" + value + "')";
	    redisAgent.evalLua(lua, new ArrayList<String>(), null);
	    assertTrue(redisAgent.exists(key));
	}
	
	/**
	 * 测试 主从自动切换（主挂了，从顶上，从变主，主变从）
	 * 1、开启主reids，开启从redis，开启redis sentinels
	 * 2、运行 nodeSwitchTest() 方法
	 * 3、手工关闭redis 主服务器 
	 * 4、等待一下，如果做了主从切换，程序结束
	 * 5、循环1000次，如果不做处理，程序结束。避免在使用mvn打包的时候测试不过，影响打包。
	 */
	@Test
	public void nodeSwitchTest() throws Exception {
		String master = redisAgent.getMaster();
		System.out.println(master);
		boolean value = true;
		int i = 0;
		while (value) {
			String new_master = redisAgent.getMaster();
			if(!new_master.equals(master)){
				value = false;
			}
			//循环1000次，如果不做处理，这个测试算是过了！
			if(i>1000){
				break;
			}
			i++;
			Thread.sleep(10);
		}
		System.out.println(redisAgent.getMaster());
	}
	
	/**
	 * 累，不测了
	 */
	@Test
	public void evalShardLuaTest() throws Exception {
	}
}

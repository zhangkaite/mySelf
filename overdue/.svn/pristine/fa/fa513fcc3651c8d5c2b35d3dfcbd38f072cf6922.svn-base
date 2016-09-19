package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.mapper.redis.RedisGoodNumberExpireMapper;

public class TestRedisGoodNumberExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addGoodNumberExpireTest()throws Exception{
		RedisGoodNumberExpireMapper mapper = (RedisGoodNumberExpireMapper)context.getBean("redisGoodNumberExpireMapper");
		GoodNumberExpire vip = new GoodNumberExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		vip.setId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisGoodNumberExpire("1",num);
	}
	
	@Test
	public void updateGoodNumberExpireTest()throws Exception{
		RedisGoodNumberExpireMapper mapper = (RedisGoodNumberExpireMapper)context.getBean("redisGoodNumberExpireMapper");
		GoodNumberExpire vip = new GoodNumberExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		vip.setId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisGoodNumberExpire("1", num);
	}
	
	@Test
	public void queryGoodNumberExpireTest()throws Exception{
		RedisGoodNumberExpireMapper mapper = (RedisGoodNumberExpireMapper)context.getBean("redisGoodNumberExpireMapper");
		long num = mapper.getRedisGoodNumberExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteGoodNumberExpireTest()throws Exception{
		RedisGoodNumberExpireMapper mapper = (RedisGoodNumberExpireMapper)context.getBean("redisGoodNumberExpireMapper");
		mapper.deleteRedisGoodNumberExpire("1");
	}
}

package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.mapper.redis.RedisLuxuryExpireMapper;

public class TestRedisLuxuryExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addLuxuryExpireTest()throws Exception{
		RedisLuxuryExpireMapper mapper = (RedisLuxuryExpireMapper)context.getBean("redisLuxuryExpireMapper");
		LuxuryExpire vip = new LuxuryExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisLuxuryExpire("1",num);
	}
	
	@Test
	public void updateLuxuryExpireTest()throws Exception{
		RedisLuxuryExpireMapper mapper = (RedisLuxuryExpireMapper)context.getBean("redisLuxuryExpireMapper");
		LuxuryExpire vip = new LuxuryExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisLuxuryExpire("1", num);
	}
	
	@Test
	public void queryLuxuryExpireTest()throws Exception{
		RedisLuxuryExpireMapper mapper = (RedisLuxuryExpireMapper)context.getBean("redisLuxuryExpireMapper");
		long num = mapper.getRedisLuxuryExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteLuxuryExpireTest()throws Exception{
		RedisLuxuryExpireMapper mapper = (RedisLuxuryExpireMapper)context.getBean("redisLuxuryExpireMapper");
		mapper.deleteRedisLuxuryExpire("1");
	}
}

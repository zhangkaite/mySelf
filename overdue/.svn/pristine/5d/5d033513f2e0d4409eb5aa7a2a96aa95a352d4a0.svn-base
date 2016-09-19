package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.mapper.redis.RedisNobilityExpireMapper;

public class TestRedisNobilityExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addNobilityExpireTest()throws Exception{
		RedisNobilityExpireMapper mapper = (RedisNobilityExpireMapper)context.getBean("redisNobilityExpireMapper");
		NobilityExpire vip = new NobilityExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisNobilityExpire("1",num);
	}
	
	@Test
	public void updateNobilityExpireTest()throws Exception{
		RedisNobilityExpireMapper mapper = (RedisNobilityExpireMapper)context.getBean("redisNobilityExpireMapper");
		NobilityExpire vip = new NobilityExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisNobilityExpire("1", num);
	}
	
	@Test
	public void queryNobilityExpireTest()throws Exception{
		RedisNobilityExpireMapper mapper = (RedisNobilityExpireMapper)context.getBean("redisNobilityExpireMapper");
		long num = mapper.getRedisNobilityExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteNobilityExpireTest()throws Exception{
		RedisNobilityExpireMapper mapper = (RedisNobilityExpireMapper)context.getBean("redisNobilityExpireMapper");
		mapper.deleteRedisNobilityExpire("1");
	}
}

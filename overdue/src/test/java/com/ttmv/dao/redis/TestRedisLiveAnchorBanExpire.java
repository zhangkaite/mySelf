package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.LiveAnchorBanExpire;
import com.ttmv.dao.mapper.redis.RedisLiveAnchorBanExpireMapper;

public class TestRedisLiveAnchorBanExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addLiveAnchorBanExpireTest()throws Exception{
		RedisLiveAnchorBanExpireMapper mapper = (RedisLiveAnchorBanExpireMapper)context.getBean("redisLiveAnchorBanExpireMapper");
		LiveAnchorBanExpire vip = new LiveAnchorBanExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisLiveAnchorBanExpire("1",num);
	}
	
	@Test
	public void updateLiveAnchorBanExpireTest()throws Exception{
		RedisLiveAnchorBanExpireMapper mapper = (RedisLiveAnchorBanExpireMapper)context.getBean("redisLiveAnchorBanExpireMapper");
		LiveAnchorBanExpire vip = new LiveAnchorBanExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisLiveAnchorBanExpire("1", num);
	}
	
	@Test
	public void queryLiveAnchorBanExpireTest()throws Exception{
		RedisLiveAnchorBanExpireMapper mapper = (RedisLiveAnchorBanExpireMapper)context.getBean("redisLiveAnchorBanExpireMapper");
		long num = mapper.getRedisLiveAnchorBanExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteLiveAnchorBanExpireTest()throws Exception{
		RedisLiveAnchorBanExpireMapper mapper = (RedisLiveAnchorBanExpireMapper)context.getBean("redisLiveAnchorBanExpireMapper");
		mapper.deleteRedisLiveAnchorBanExpire("1");
	}
}

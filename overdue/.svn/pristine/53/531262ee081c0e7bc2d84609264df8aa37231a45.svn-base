package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.LiveRoomBanExpire;
import com.ttmv.dao.mapper.redis.RedisLiveRoomBanExpireMapper;

public class TestRedisLiveRoomBanExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addLiveRoomBanExpireTest()throws Exception{
		RedisLiveRoomBanExpireMapper mapper = (RedisLiveRoomBanExpireMapper)context.getBean("redisLiveRoomBanExpireMapper");
		LiveRoomBanExpire vip = new LiveRoomBanExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisLiveRoomBanExpire("1",num);
	}
	
	@Test
	public void updateLiveRoomBanExpireTest()throws Exception{
		RedisLiveRoomBanExpireMapper mapper = (RedisLiveRoomBanExpireMapper)context.getBean("redisLiveRoomBanExpireMapper");
		LiveRoomBanExpire vip = new LiveRoomBanExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisLiveRoomBanExpire("1", num);
	}
	
	@Test
	public void queryLiveRoomBanExpireTest()throws Exception{
		RedisLiveRoomBanExpireMapper mapper = (RedisLiveRoomBanExpireMapper)context.getBean("redisLiveRoomBanExpireMapper");
		long num = mapper.getRedisLiveRoomBanExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteLiveRoomBanExpireTest()throws Exception{
		RedisLiveRoomBanExpireMapper mapper = (RedisLiveRoomBanExpireMapper)context.getBean("redisLiveRoomBanExpireMapper");
		mapper.deleteRedisLiveRoomBanExpire("1");
	}
}

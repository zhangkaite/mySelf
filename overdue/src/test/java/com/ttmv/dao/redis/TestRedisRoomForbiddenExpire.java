package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.RoomForbiddenExpire;
import com.ttmv.dao.mapper.redis.RedisRoomForbiddenExpireMapper;

public class TestRedisRoomForbiddenExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addRoomForbiddenExpireTest()throws Exception{
		RedisRoomForbiddenExpireMapper mapper = (RedisRoomForbiddenExpireMapper)context.getBean("redisRoomForbiddenExpireMapper");
		RoomForbiddenExpire vip = new RoomForbiddenExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisRoomForbiddenExpire("1",num);
	}
	
	@Test
	public void updateRoomForbiddenExpireTest()throws Exception{
		RedisRoomForbiddenExpireMapper mapper = (RedisRoomForbiddenExpireMapper)context.getBean("redisRoomForbiddenExpireMapper");
		RoomForbiddenExpire vip = new RoomForbiddenExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisRoomForbiddenExpire("1", num);
	}
	
	@Test
	public void queryRoomForbiddenExpireTest()throws Exception{
		RedisRoomForbiddenExpireMapper mapper = (RedisRoomForbiddenExpireMapper)context.getBean("redisRoomForbiddenExpireMapper");
		long num = mapper.getRedisRoomForbiddenExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteRoomForbiddenExpireTest()throws Exception{
		RedisRoomForbiddenExpireMapper mapper = (RedisRoomForbiddenExpireMapper)context.getBean("redisRoomForbiddenExpireMapper");
		mapper.deleteRedisRoomForbiddenExpire("1");
	}
}

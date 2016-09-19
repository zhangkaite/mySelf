package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.mapper.redis.RedisUserForbiddenExpireMapper;

public class TestRedisUserForbiddenExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addUserForbiddenExpireTest()throws Exception{
		RedisUserForbiddenExpireMapper mapper = (RedisUserForbiddenExpireMapper)context.getBean("redisUserForbiddenExpireMapper");
		UserForbiddenExpire vip = new UserForbiddenExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisUserForbiddenExpire("1",num);
	}
	
	@Test
	public void updateUserForbiddenExpireTest()throws Exception{
		RedisUserForbiddenExpireMapper mapper = (RedisUserForbiddenExpireMapper)context.getBean("redisUserForbiddenExpireMapper");
		UserForbiddenExpire vip = new UserForbiddenExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisUserForbiddenExpire("1", num);
	}
	
	@Test
	public void queryUserForbiddenExpireTest()throws Exception{
		RedisUserForbiddenExpireMapper mapper = (RedisUserForbiddenExpireMapper)context.getBean("redisUserForbiddenExpireMapper");
		long num = mapper.getRedisUserForbiddenExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void deleteUserForbiddenExpireTest()throws Exception{
		RedisUserForbiddenExpireMapper mapper = (RedisUserForbiddenExpireMapper)context.getBean("redisUserForbiddenExpireMapper");
		mapper.deleteRedisUserForbiddenExpire("1");
	}
}

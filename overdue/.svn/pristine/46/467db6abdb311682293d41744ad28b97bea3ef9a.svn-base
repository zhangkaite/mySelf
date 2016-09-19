package com.ttmv.dao.redis;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.mapper.redis.RedisVipExpireMapper;

public class TestRedisVipExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addVipExpireTest()throws Exception{
		RedisVipExpireMapper mapper = (RedisVipExpireMapper)context.getBean("redisVipExpireMapper");
		VipExpire vip = new VipExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		long num = 100000;
		mapper.addRedisVipExpire("1",num);
	}
	
	@Test
	public void updateVipExpireTest()throws Exception{
		RedisVipExpireMapper mapper = (RedisVipExpireMapper)context.getBean("redisVipExpireMapper");
		VipExpire vip = new VipExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		long num = 100000;
		mapper.updateRedisVipExpire("1", num);
	}
	
	@Test
	public void queryVipExpireTest()throws Exception{
		RedisVipExpireMapper mapper = (RedisVipExpireMapper)context.getBean("redisVipExpireMapper");
		long num = mapper.getRedisVipExpire("1");
		System.out.println(num);
	}
	
	@Test
	public void queryRangeVipExpireTest()throws Exception{
		RedisVipExpireMapper mapper = (RedisVipExpireMapper)context.getBean("redisVipExpireMapper");
		Set<Tuple> sets = mapper.getRangeRedisVipExpire(0L,1447920408585L);
		System.out.println(sets.size());
	}
	
	@Test
	public void deleteVipExpireTest()throws Exception{
		RedisVipExpireMapper mapper = (RedisVipExpireMapper)context.getBean("redisVipExpireMapper");
		mapper.deleteRedisVipExpire("2");
	}
}

package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.mapper.mysql.GoodNumberExpireMapper;

public class TestGoodNumberExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addGoodNumberExpireTest()throws Exception{
		GoodNumberExpireMapper mapper = (GoodNumberExpireMapper)context.getBean("goodNumberExpireMapper");
		GoodNumberExpire vip = new GoodNumberExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		mapper.addGoodNumberExpire(vip);
	}
	
	@Test
	public void updateGoodNumberExpireTest()throws Exception{
		GoodNumberExpireMapper mapper = (GoodNumberExpireMapper)context.getBean("goodNumberExpireMapper");
		GoodNumberExpire vip = new GoodNumberExpire();
		vip.setId(new BigInteger("1"));
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		vip.setGoodNumberId(new BigInteger("10000000"));
		mapper.updateGoodNumberExpire(vip);
	}
	
	@Test
	public void queryGoodNumberExpireTest()throws Exception{
		GoodNumberExpireMapper mapper = (GoodNumberExpireMapper)context.getBean("goodNumberExpireMapper");
		//GoodNumberExpire vip = mapper.queryGoodNumberExpire(new BigInteger("2"));
		//System.out.println(vip.getEndTime());
	}
	
	@Test
	public void deleteGoodNumberExpireTest()throws Exception{
		GoodNumberExpireMapper mapper = (GoodNumberExpireMapper)context.getBean("goodNumberExpireMapper");
		Integer result = mapper.deleteGoodNumberExpire(new BigInteger("2"));
		System.out.println(result);
	}
}

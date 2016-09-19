package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.mapper.mysql.LuxuryExpireMapper;

public class TestLuxuryExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addLuxuryExpireTest()throws Exception{
		LuxuryExpireMapper mapper = (LuxuryExpireMapper)context.getBean("luxuryExpireMapper");
		LuxuryExpire vip = new LuxuryExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		mapper.addLuxuryExpire(vip);
	}
	
	@Test
	public void updateLuxuryExpireTest()throws Exception{
		LuxuryExpireMapper mapper = (LuxuryExpireMapper)context.getBean("luxuryExpireMapper");
		LuxuryExpire vip = new LuxuryExpire();
		vip.setId(new BigInteger("3"));
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		vip.setCarId(new BigInteger("1"));
		mapper.updateLuxuryExpire(vip);
	}
	
	@Test
	public void queryLuxuryExpireTest()throws Exception{
		LuxuryExpireMapper mapper = (LuxuryExpireMapper)context.getBean("luxuryExpireMapper");
		//LuxuryExpire vip = mapper.queryLuxuryExpire(new BigInteger("1"));
		//System.out.println(vip.getEndTime());
	}
	
	@Test
	public void deleteLuxuryExpireTest()throws Exception{
		LuxuryExpireMapper mapper = (LuxuryExpireMapper)context.getBean("luxuryExpireMapper");
		Integer result = mapper.deleteLuxuryExpire(new BigInteger("1"));
		System.out.println(result);
	}
}

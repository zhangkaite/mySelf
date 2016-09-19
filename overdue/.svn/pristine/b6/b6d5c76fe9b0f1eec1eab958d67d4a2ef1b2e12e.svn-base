package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.mapper.mysql.NobilityExpireMapper;

public class TestNobilityExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addNobilityExpireTest()throws Exception{
		NobilityExpireMapper mapper = (NobilityExpireMapper)context.getBean("nobilityExpireMapper");
		NobilityExpire vip = new NobilityExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		mapper.addNobilityExpire(vip);
	}
	
	@Test
	public void updateNobilityExpireTest()throws Exception{
		NobilityExpireMapper mapper = (NobilityExpireMapper)context.getBean("nobilityExpireMapper");
		NobilityExpire vip = new NobilityExpire();
		vip.setId(new BigInteger("1"));
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		mapper.updateNobilityExpire(vip);
	}
	
	@Test
	public void queryNobilityExpireTest()throws Exception{
		NobilityExpireMapper mapper = (NobilityExpireMapper)context.getBean("nobilityExpireMapper");
		NobilityExpire vip = mapper.queryNobilityExpire(new BigInteger("1"));
		System.out.println(vip.getEndTime());
	}
	
	@Test
	public void deleteNobilityExpireTest()throws Exception{
		NobilityExpireMapper mapper = (NobilityExpireMapper)context.getBean("nobilityExpireMapper");
		Integer result = mapper.deleteNobilityExpire(new BigInteger("1"));
		System.out.println(result);
	}
}

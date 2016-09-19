package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.mapper.mysql.VipExpireMapper;

public class TestVipExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addVipExpireTest()throws Exception{
		VipExpireMapper mapper = (VipExpireMapper)context.getBean("vipExpireMapper");
		VipExpire vip = new VipExpire();
		vip.setEndTime(new Date());
		vip.setRemindTime(new Date());
		vip.setUserId(new BigInteger("1"));
		mapper.addVipExpire(vip);
	}
	
	@Test
	public void updateVipExpireTest()throws Exception{
		VipExpireMapper mapper = (VipExpireMapper)context.getBean("vipExpireMapper");
		VipExpire vip = new VipExpire();
		vip.setId(new BigInteger("1"));
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		mapper.updateVipExpire(vip);
	}
	
	@Test
	public void queryVipExpireTest()throws Exception{
		VipExpireMapper mapper = (VipExpireMapper)context.getBean("vipExpireMapper");
		VipExpire vip = mapper.queryVipExpire(new BigInteger("1"));
		System.out.println(vip.getEndTime());
	}
	
	@Test
	public void deleteVipExpireTest()throws Exception{
		VipExpireMapper mapper = (VipExpireMapper)context.getBean("vipExpireMapper");
		Integer result = mapper.deleteVipExpire(new BigInteger("1"));
		System.out.println(result);
	}
}

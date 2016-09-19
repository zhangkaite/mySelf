package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.mapper.mysql.UserForbiddenExpireMapper;

public class TestUserForbiddenExpire {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addUserForbiddenExpireTest()throws Exception{
		UserForbiddenExpireMapper mapper = (UserForbiddenExpireMapper)context.getBean("userForbiddenExpireMapper");
		UserForbiddenExpire vip = new UserForbiddenExpire();
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("1"));
		mapper.addUserForbiddenExpire(vip);
	}
	
	@Test
	public void updateUserForbiddenExpireTest()throws Exception{
		UserForbiddenExpireMapper mapper = (UserForbiddenExpireMapper)context.getBean("userForbiddenExpireMapper");
		UserForbiddenExpire vip = new UserForbiddenExpire();
		vip.setId(new BigInteger("1"));
		vip.setEndTime(new Date());
		vip.setUserId(new BigInteger("2"));
		mapper.updateUserForbiddenExpire(vip);
	}
	
	@Test
	public void queryUserForbiddenExpireTest()throws Exception{
		UserForbiddenExpireMapper mapper = (UserForbiddenExpireMapper)context.getBean("userForbiddenExpireMapper");
		UserForbiddenExpire vip = mapper.queryUserForbiddenExpire(new BigInteger("1"));
		System.out.println(vip.getEndTime());
	}
	
	@Test
	public void deleteUserForbiddenExpireTest()throws Exception{
		UserForbiddenExpireMapper mapper = (UserForbiddenExpireMapper)context.getBean("userForbiddenExpireMapper");
		Integer result = mapper.deleteUserForbiddenExpire(new BigInteger("1"));
		System.out.println(result);
	}
}

package com.ttmv.datacenter.usercenter.dao.implement.mysql;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMySqlUserInfo {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
	}

	@Test
	public void testAddMysqlUserInfo() {
	
	}
}

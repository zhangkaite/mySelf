package com.ttmv.datacenter.usercenter.dao.implement.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartMq {

	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-activemq.xml");
	}
}

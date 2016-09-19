package com.ttmv.datacenter.paycenter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class start {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring-worker-mq.xml");
	}
}

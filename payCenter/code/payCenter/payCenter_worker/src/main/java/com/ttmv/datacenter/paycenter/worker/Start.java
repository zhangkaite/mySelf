package com.ttmv.datacenter.paycenter.worker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("spring-worker-mq.xml");
	}
}

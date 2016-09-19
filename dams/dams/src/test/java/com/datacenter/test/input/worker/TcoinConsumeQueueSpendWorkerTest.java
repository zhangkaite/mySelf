package com.datacenter.test.input.worker;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TcoinConsumeQueueSpendWorkerTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	@Test
	public void testSelect()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	}
}

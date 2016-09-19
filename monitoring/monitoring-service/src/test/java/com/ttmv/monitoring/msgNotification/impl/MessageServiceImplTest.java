package com.ttmv.monitoring.msgNotification.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.msgNotification.MessageServiceInf;

public class MessageServiceImplTest {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-messageServer.xml");
		MessageServiceInf serviceInf = (MessageServiceImpl) context.getBean("messageServiceImpl");
		Map params = new HashMap();
		
		params.put("msgType", "FATALERROR");
		params.put("ip", "123.111.11.11");
		params.put("serverName", "serverName");
		params.put("serverID", "serverID");
		params.put("serverTime", "2016年3月30日");
		params.put("errorMsg", "errorMsg");
		params.put("alerterID", 32);
		
				
		try { 
			serviceInf.sendMessage(params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		try {
			serviceInf.sendMessage(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
}

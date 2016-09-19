package com.ttmv.monitoring.threshold;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.impl.configuration.threshold.QueryAllThresholdServiceImpl;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestThreshold {
	
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		WebServerInf webServerInf = (QueryAllThresholdServiceImpl)context.getBean("queryAllThresholdServiceImpl");
		Map map = webServerInf.handler(null);
	
	}
}

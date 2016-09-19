package com.ttmv.monitoring;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.QueryServerSubSysinfoServiceImpl;

public class QueryServerSubSysinfoServiceImplTest {

	
	@Test
	public void queryMN(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryServerSubSysinfoServiceImpl impl =  (QueryServerSubSysinfoServiceImpl)context.getBean("queryServerSubSysinfoServiceImpl");
		
		Map  map = new HashMap();
		map.put("sys_type", "1");
		
		Map result = (Map) impl.handler(map);
		System.out.println(result);
	}
}

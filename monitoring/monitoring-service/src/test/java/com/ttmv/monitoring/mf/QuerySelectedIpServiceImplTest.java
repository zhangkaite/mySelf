package com.ttmv.monitoring.mf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.mf.QuerySelectedIpServiceImpl;

public class QuerySelectedIpServiceImplTest {

	@Test
	public void querySelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QuerySelectedIpServiceImpl cpu =  (QuerySelectedIpServiceImpl)context.getBean("querySelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ABC");
		
		Map result = cpu.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.get(0).get("value"));
	}
}

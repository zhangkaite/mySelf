package com.ttmv.monitoring.php;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.php.QueryPHPSelectedIpServiceImpl;

public class QueryPHPSelectedIpServiceImplTest {

	@Test
	public void queryPHPSelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPHPSelectedIpServiceImpl impl =  (QueryPHPSelectedIpServiceImpl)context.getBean("queryPHPSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "A");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryPHPSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPHPSelectedIpServiceImpl impl =  (QueryPHPSelectedIpServiceImpl)context.getBean("queryPHPSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "A");
		map.put("ip", "192.168.13.157");
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
}

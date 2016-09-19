package com.ttmv.monitoring.mf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.mf.QuerySelectedPortServiceImpl;

public class QuerySelectedPortServiceImplTest {

	@Test
	public void querySelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QuerySelectedPortServiceImpl cpu =  (QuerySelectedPortServiceImpl)context.getBean("querySelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ABC");
		map.put("ip", "192.168.13.157");
		
		Map result = cpu.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.get(0).get("value"));
	}
}

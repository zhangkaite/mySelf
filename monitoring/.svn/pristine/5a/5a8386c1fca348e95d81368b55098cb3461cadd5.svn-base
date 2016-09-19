package com.ttmv.monitoring.php;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.php.QueryPhpServerDataServiceImpl;

public class QueryPHPServerDataServiceImplTest {

	@Test
	public void querySelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPhpServerDataServiceImpl impl =  (QueryPhpServerDataServiceImpl)context.getBean("queryPhpServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "A");
		map.put("ip", "192.168.13.158");
		map.put("port", 92);
		
		Map result = (Map) impl.handler(map);
		Map m =(Map)result.get("resData");
		System.out.println(m.get("ip"));
	}
}

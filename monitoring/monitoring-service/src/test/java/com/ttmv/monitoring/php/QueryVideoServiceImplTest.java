package com.ttmv.monitoring.php;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.php.QueryListByDateVideoServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryVideoSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryVideoSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryVideoServerDataServiceImpl;

public class QueryVideoServiceImplTest {

	@Test
	public void querySelectedVideoIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryVideoSelectedIpServiceImpl impl =  (QueryVideoSelectedIpServiceImpl)context.getBean("queryVideoSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpVideoService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryVideoSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryVideoSelectedPortServiceImpl impl =  (QueryVideoSelectedPortServiceImpl)context.getBean("queryVideoSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpVideoService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListVideoByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateVideoServiceImpl impl =  (QueryListByDateVideoServiceImpl)context.getBean("queryListByDateVideoServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpVideoService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryPhpVideo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryVideoServerDataServiceImpl impl =  (QueryVideoServerDataServiceImpl)context.getBean("queryVideoServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpVideoService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

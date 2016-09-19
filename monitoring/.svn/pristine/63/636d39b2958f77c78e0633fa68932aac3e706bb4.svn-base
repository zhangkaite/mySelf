package com.ttmv.monitoring.ssd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.ot.QueryListSSDServiceImpl;
import com.ttmv.monitoring.chartService.impl.ot.QuerySSDSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.ot.QuerySSDServerDataServiceImpl;

public class QuerySSDServiceImplTest {

	@Test
	public void querySelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QuerySSDServerDataServiceImpl impl =  (QuerySSDServerDataServiceImpl)context.getBean("querySSDServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ScreenshotService");
		map.put("ip", "192.168.13.165");
		map.put("port", 10);
		
		Map result = (Map) impl.handler(map);
		Map m =(Map)result.get("resData");
		System.out.println(m.get("IP"));
	}
	
	
	@Test
	public void querySSDSelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QuerySSDSelectedIpServiceImpl impl =  (QuerySSDSelectedIpServiceImpl)context.getBean("querySSDSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ScreenshotService");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void querySSDSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QuerySSDSelectedIpServiceImpl impl =  (QuerySSDSelectedIpServiceImpl)context.getBean("querySSDSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ScreenshotService");
		map.put("ip", "192.168.13.165");
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void querySSDListByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListSSDServiceImpl impl =  (QueryListSSDServiceImpl)context.getBean("queryListSSDServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ScreenshotService");
		map.put("ip", "192.168.13.165");
		map.put("port", 10);
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
}

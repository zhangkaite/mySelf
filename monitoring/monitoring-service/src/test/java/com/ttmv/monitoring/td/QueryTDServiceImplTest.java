package com.ttmv.monitoring.td;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.ot.QueryTDByIdServiceImpl;
import com.ttmv.monitoring.chartService.impl.ot.QueryTDSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.ot.QueryTDServerDataServiceImpl;
import com.ttmv.monitoring.entity.TranscodingData;

public class QueryTDServiceImplTest {

	@Test
	public void querySelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTDServerDataServiceImpl impl =  (QueryTDServerDataServiceImpl)context.getBean("queryTDServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		map.put("ip", "192.168.13.165");
		map.put("port", 129);
		
		Map result = (Map) impl.handler(map);
		Map m =(Map)result.get("resData");
		System.out.println(m.get("IP"));
	}
	
	
	@Test
	public void queryTDSelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTDSelectedIpServiceImpl impl =  (QueryTDSelectedIpServiceImpl)context.getBean("queryTDSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryTDSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTDSelectedIpServiceImpl impl =  (QueryTDSelectedIpServiceImpl)context.getBean("queryTDSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		map.put("ip", "192.168.13.165");
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryTDById(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTDByIdServiceImpl impl =  (QueryTDByIdServiceImpl)context.getBean("queryTDByIdServiceImpl");
		
		Map  map = new HashMap();
		map.put("id", "1");
		Map result = impl.handler(map);
		TranscodingData resData = (TranscodingData)result.get("resData");
		System.out.println(resData);
	}
}

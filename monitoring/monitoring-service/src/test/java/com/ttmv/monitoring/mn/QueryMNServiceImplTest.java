package com.ttmv.monitoring.mn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.mn.QueryMNSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.mn.QueryMNSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.mn.QueryMediaControlDataByIdServiceImpl;
import com.ttmv.monitoring.chartService.impl.mn.QueryMediaControlDataServiceImpl;
import com.ttmv.monitoring.entity.MediaControlData;

public class QueryMNServiceImplTest {

	
	@Test
	public void queryMNById(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMediaControlDataByIdServiceImpl impl =  (QueryMediaControlDataByIdServiceImpl)context.getBean("queryMediaControlDataByIdServiceImpl");
		
		Map  map = new HashMap();
		map.put("id", "32");
		
		Map result = (Map) impl.handler(map);
		MediaControlData m =(MediaControlData)result.get("resData");
		System.out.println(m.getCreateTime());
	}
	
	@Test
	public void queryMNSelectedIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMNSelectedIpServiceImpl impl =  (QueryMNSelectedIpServiceImpl)context.getBean("queryMNSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryMNSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMNSelectedPortServiceImpl impl =  (QueryMNSelectedPortServiceImpl)context.getBean("queryMNSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		map.put("ip", "192.168.13.165");
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryMN(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMediaControlDataServiceImpl impl =  (QueryMediaControlDataServiceImpl)context.getBean("queryMediaControlDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "B");
		map.put("ip", "192.168.13.165");
		map.put("port", 129);
		
		Map result = (Map) impl.handler(map);
		Map m =(Map)result.get("resData");
		System.out.println(m.get("IP"));
	}
}

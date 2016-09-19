package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.rms.QueryListByDateRmsServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.rms.QueryRmsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.rms.QueryRmsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.rms.QueryRmsServerDataServiceImpl;

public class QueryRmsServiceImplTest {

	@Test
	public void querySelectedRmsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryRmsSelectedIpServiceImpl impl =  (QueryRmsSelectedIpServiceImpl)context.getBean("queryRmsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowRmsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryRmsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryRmsSelectedPortServiceImpl impl =  (QueryRmsSelectedPortServiceImpl)context.getBean("queryRmsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowRmsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListRmsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateRmsServiceImpl impl =  (QueryListByDateRmsServiceImpl)context.getBean("queryListByDateRmsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowRmsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryRms(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryRmsServerDataServiceImpl impl =  (QueryRmsServerDataServiceImpl)context.getBean("queryRmsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowRmsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

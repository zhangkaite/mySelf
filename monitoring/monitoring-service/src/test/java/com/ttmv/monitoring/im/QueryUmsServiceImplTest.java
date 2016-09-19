package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.ums.QueryListByDateUmsServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.ums.QueryUmsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.ums.QueryUmsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.ums.QueryUmsServerDataServiceImpl;

public class QueryUmsServiceImplTest {

	@Test
	public void querySelectedUmsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryUmsSelectedIpServiceImpl impl =  (QueryUmsSelectedIpServiceImpl)context.getBean("queryUmsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowUmsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryUmsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryUmsSelectedPortServiceImpl impl =  (QueryUmsSelectedPortServiceImpl)context.getBean("queryUmsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowUmsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListUmsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateUmsServiceImpl impl =  (QueryListByDateUmsServiceImpl)context.getBean("queryListByDateUmsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowUmsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryUms(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryUmsServerDataServiceImpl impl =  (QueryUmsServerDataServiceImpl)context.getBean("queryUmsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowUmsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

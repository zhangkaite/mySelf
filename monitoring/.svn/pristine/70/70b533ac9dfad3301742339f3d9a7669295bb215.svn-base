package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.lbs.QueryLbsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.lbs.QueryLbsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.lbs.QueryLbsServerDataServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.lbs.QueryListByDateLbsServiceImpl;

public class QueryLbsServiceImplTest {

	@Test
	public void querySelectedLbsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryLbsSelectedIpServiceImpl impl =  (QueryLbsSelectedIpServiceImpl)context.getBean("queryLbsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowLbsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryLbsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryLbsSelectedPortServiceImpl impl =  (QueryLbsSelectedPortServiceImpl)context.getBean("queryLbsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowLbsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListLbsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateLbsServiceImpl impl =  (QueryListByDateLbsServiceImpl)context.getBean("queryListByDateLbsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowLbsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryLbs(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryLbsServerDataServiceImpl impl =  (QueryLbsServerDataServiceImpl)context.getBean("queryLbsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowLbsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

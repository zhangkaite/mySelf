package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.mts.QueryListByDateMtsServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mts.QueryMtsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mts.QueryMtsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mts.QueryMtsServerDataServiceImpl;

public class QueryMtsServiceImplTest {

	@Test
	public void querySelectedMtsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMtsSelectedIpServiceImpl impl =  (QueryMtsSelectedIpServiceImpl)context.getBean("queryMtsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMtsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryMtsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMtsSelectedPortServiceImpl impl =  (QueryMtsSelectedPortServiceImpl)context.getBean("queryMtsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMtsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListMtsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateMtsServiceImpl impl =  (QueryListByDateMtsServiceImpl)context.getBean("queryListByDateMtsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMtsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryMts(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMtsServerDataServiceImpl impl =  (QueryMtsServerDataServiceImpl)context.getBean("queryMtsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMtsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

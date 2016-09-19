package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.prs.QueryListByDatePrsServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.prs.QueryPrsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.prs.QueryPrsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.prs.QueryPrsServerDataServiceImpl;

public class QueryPrsServiceImplTest {

	@Test
	public void querySelectedPrsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPrsSelectedIpServiceImpl impl =  (QueryPrsSelectedIpServiceImpl)context.getBean("queryPrsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowPrsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryPrsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPrsSelectedPortServiceImpl impl =  (QueryPrsSelectedPortServiceImpl)context.getBean("queryPrsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowPrsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListPrsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDatePrsServiceImpl impl =  (QueryListByDatePrsServiceImpl)context.getBean("queryListByDatePrsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowPrsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryPrs(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPrsServerDataServiceImpl impl =  (QueryPrsServerDataServiceImpl)context.getBean("queryPrsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowPrsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

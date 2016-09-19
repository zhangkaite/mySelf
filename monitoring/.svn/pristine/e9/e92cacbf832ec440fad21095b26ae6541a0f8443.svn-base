package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.mss.QueryListByDateMssServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mss.QueryMssSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mss.QueryMssSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mss.QueryMssServerDataServiceImpl;

public class QueryMssServiceImplTest {

	@Test
	public void querySelectedMssIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMssSelectedIpServiceImpl impl =  (QueryMssSelectedIpServiceImpl)context.getBean("queryMssSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMssService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryMssSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMssSelectedPortServiceImpl impl =  (QueryMssSelectedPortServiceImpl)context.getBean("queryMssSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMssService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListMssByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateMssServiceImpl impl =  (QueryListByDateMssServiceImpl)context.getBean("queryListByDateMssServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMssService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryMss(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMssServerDataServiceImpl impl =  (QueryMssServerDataServiceImpl)context.getBean("queryMssServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMssService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

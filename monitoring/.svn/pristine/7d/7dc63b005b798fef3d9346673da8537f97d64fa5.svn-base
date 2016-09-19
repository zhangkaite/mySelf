package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.mds.QueryListByDateMdsServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mds.QueryMdsSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mds.QueryMdsSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.mds.QueryMdsServerDataServiceImpl;

public class QueryMdsServiceImplTest {

	@Test
	public void querySelectedMdsIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMdsSelectedIpServiceImpl impl =  (QueryMdsSelectedIpServiceImpl)context.getBean("queryMdsSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMdsService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryMdsSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMdsSelectedPortServiceImpl impl =  (QueryMdsSelectedPortServiceImpl)context.getBean("queryMdsSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMdsService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListMdsByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateMdsServiceImpl impl =  (QueryListByDateMdsServiceImpl)context.getBean("queryListByDateMdsServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMdsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryMds(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMdsServerDataServiceImpl impl =  (QueryMdsServerDataServiceImpl)context.getBean("queryMdsServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowMdsService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

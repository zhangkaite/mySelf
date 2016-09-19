package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.tas.QueryListByDateTasServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.tas.QueryTasSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.tas.QueryTasSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.tas.QueryTasServerDataServiceImpl;

public class QueryTasServiceImplTest {

	@Test
	public void querySelectedTasIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTasSelectedIpServiceImpl impl =  (QueryTasSelectedIpServiceImpl)context.getBean("queryTasSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowTasService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryTasSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTasSelectedPortServiceImpl impl =  (QueryTasSelectedPortServiceImpl)context.getBean("queryTasSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowTasService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListTasByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateTasServiceImpl impl =  (QueryListByDateTasServiceImpl)context.getBean("queryListByDateTasServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowTasService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryTas(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryTasServerDataServiceImpl impl =  (QueryTasServerDataServiceImpl)context.getBean("queryTasServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowTasService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

package com.ttmv.monitoring.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.im.hp.QueryHpByIdServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.hp.QueryHpSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.hp.QueryHpSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.hp.QueryHpServerDataServiceImpl;
import com.ttmv.monitoring.chartService.impl.im.hp.QueryListByDateHpServiceImpl;
import com.ttmv.monitoring.entity.HpServerData;

public class QueryHpServiceImplTest {

	@Test
	public void querySelectedHpIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryHpSelectedIpServiceImpl impl =  (QueryHpSelectedIpServiceImpl)context.getBean("queryHpSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowHttpProxyService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryHpSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryHpSelectedPortServiceImpl impl =  (QueryHpSelectedPortServiceImpl)context.getBean("queryHpSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowHttpProxyService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListHpByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateHpServiceImpl impl =  (QueryListByDateHpServiceImpl)context.getBean("queryListByDateHpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowHttpProxyService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryHp(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryHpServerDataServiceImpl impl =  (QueryHpServerDataServiceImpl)context.getBean("queryHpServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "ImShowHttpProxyService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
	
	@Test
	public void queryHpById(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryHpByIdServiceImpl impl =  (QueryHpByIdServiceImpl)context.getBean("queryHpByIdServiceImpl");
		
		Map  map = new HashMap();
		map.put("id", "1");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

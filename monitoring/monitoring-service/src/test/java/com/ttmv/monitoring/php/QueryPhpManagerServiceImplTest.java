package com.ttmv.monitoring.php;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.php.QueryListByDatePhpManagerServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryPhpManagerSelectedIpServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryPhpManagerSelectedPortServiceImpl;
import com.ttmv.monitoring.chartService.impl.php.QueryPhpManagerServerDataServiceImpl;

public class QueryPhpManagerServiceImplTest {

	@Test
	public void querySelectedManagerIP(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPhpManagerSelectedIpServiceImpl impl =  (QueryPhpManagerSelectedIpServiceImpl)context.getBean("queryPhpManagerSelectedIpServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpManageService");
		
		@SuppressWarnings("rawtypes")
		Map result = (Map) impl.handler(map);
		List m =(List)result.get("resData");
		System.out.println(m.size());
	}
	
	
	@Test
	public void queryManagerSelectedPort(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPhpManagerSelectedPortServiceImpl impl =  (QueryPhpManagerSelectedPortServiceImpl)context.getBean("queryPhpManagerSelectedPortServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpManageService");
		map.put("ip", "192.168.13.157");
		
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryListManagerByDate(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDatePhpManagerServiceImpl impl =  (QueryListByDatePhpManagerServiceImpl)context.getBean("queryListByDatePhpManagerServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpManageService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		Map result = impl.handler(map);
		List<Map> resData = (List<Map>)result.get("resData");
		System.out.println(resData.size());
	}
	
	@Test
	public void queryPhpManager(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryPhpManagerServerDataServiceImpl impl =  (QueryPhpManagerServerDataServiceImpl)context.getBean("queryPhpManagerServerDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "PhpManageService");
		map.put("ip", "192.168.13.157");
		map.put("port", 10);
		map.put("time", new Date());
		map.put("previousId", "2");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("CreateTime"));
	}
}

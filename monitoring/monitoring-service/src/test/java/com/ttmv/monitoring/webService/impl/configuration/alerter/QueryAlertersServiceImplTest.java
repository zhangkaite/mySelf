//package com.ttmv.monitoring.webService.impl.configuration.alerter;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.ttmv.monitoring.webService.WebServerInf;
//
//public class QueryAlertersServiceImplTest {
//
//	@Test
//	public void test() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
//		WebServerInf serviceInf = (QueryAlertersServiceImpl) context.getBean("queryAlertersServiceImpl");
//		Map reqMap = new HashMap();
//		Map resMap = null;
//		reqMap.put("page", "1");
//		reqMap.put("pageSize", "20");
//		resMap = serviceInf.handler(reqMap);
//		
//		System.out.println("响应码："+resMap.get("resultCode"));
//	}
//
//}

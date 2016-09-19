package com.ttmv.monitoring;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.mf.QueryMediaForwardDataServiceImpl;

public class CPUQueryServerImplTest {

	@Test
	public void queryByIPAndType(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMediaForwardDataServiceImpl cpu =  (QueryMediaForwardDataServiceImpl)context.getBean("cPUQueryServiceImpl");
		
		Map  map = new HashMap();
		map.put("IP","192.168.13.157");
		map.put("server_type", "ABC");
		
		Map result = cpu.handler(map);
		Map resData = (Map)result.get("resData");
		System.out.println(resData.get("CPU"));
	}
}

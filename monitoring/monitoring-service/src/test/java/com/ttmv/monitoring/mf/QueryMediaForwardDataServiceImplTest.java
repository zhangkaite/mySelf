package com.ttmv.monitoring.mf;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.chartService.impl.mf.QueryListByDateMediaForwardDataServiceImpl;
import com.ttmv.monitoring.chartService.impl.mf.QueryListMediaForwardDataServiceImpl;
import com.ttmv.monitoring.chartService.impl.mf.QueryMediaForwardDataByIdServiceImpl;
import com.ttmv.monitoring.chartService.impl.mf.QueryMediaForwardDataServiceImpl;
import com.ttmv.monitoring.entity.MediaForwardData;

public class QueryMediaForwardDataServiceImplTest {

	@Test
	public void queryMediaForwardData(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMediaForwardDataServiceImpl impl =  (QueryMediaForwardDataServiceImpl)context.getBean("queryMediaForwardDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "AvServerTransmitService");
		map.put("ip", "192.168.1.1");
		map.put("port", 8080);
		map.put("previousId", "NO");
		
		Map result = (Map) impl.handler(map);
		Map data =(Map)result.get("resData");
		System.out.println(data.get("id"));
	}
	
	@Test
	public void queryListMediaForwardData(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListMediaForwardDataServiceImpl impl =  (QueryListMediaForwardDataServiceImpl)context.getBean("queryListMediaForwardDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "1");
		map.put("ip", "192.168.1.1");
		map.put("port", 10088);
		
		Map result = (Map) impl.handler(map);
		List<MediaForwardData> datas =(List<MediaForwardData>)result.get("resData");
		System.out.println(datas.size());
	}
	
	@Test
	public void queryListByDateMediaForwardData(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryMediaForwardDataByIdServiceImpl impl =  (QueryMediaForwardDataByIdServiceImpl)context.getBean("queryMediaForwardDataByIdServiceImpl");
		
		Map  map = new HashMap();
		map.put("id", "107178");
		
		Map result = (Map) impl.handler(map);
		MediaForwardData data =(MediaForwardData)result.get("resData");
		System.out.println(data.getCreateTime());
	}
	
	@Test
	public void queryMediaForwardDataById(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		QueryListByDateMediaForwardDataServiceImpl impl =  (QueryListByDateMediaForwardDataServiceImpl)context.getBean("queryListByDateMediaForwardDataServiceImpl");
		
		Map  map = new HashMap();
		map.put("server_type", "1");
		map.put("ip", "192.168.1.1");
		map.put("port", 10088);
		map.put("time", new Date().getTime());
		
		Map result = (Map) impl.handler(map);
		List<MediaForwardData> datas =(List<MediaForwardData>)result.get("resData");
		System.out.println(datas.size());
	}
}

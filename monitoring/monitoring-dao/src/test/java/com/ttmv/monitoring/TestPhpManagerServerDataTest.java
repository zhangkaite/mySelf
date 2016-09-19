package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IPHPManagerServerDataInterImpl;

public class TestPhpManagerServerDataTest {

	@Test
	public void addPPhpManagerServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPManagerServerDataInterImpl impl = (IPHPManagerServerDataInterImpl)context.getBean("iPHPManagerServerDataInterImpl");
		PhpManagerServerData data = new PhpManagerServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setNetConnections(10);
		data.setNetLoad(1);
		data.setTimestamp(new Date());
		data.setSysLoad(10);
		data.setServerType("PhpVideoService");
		data.setServerId("2");
		data.setPort(10);
		impl.addPhpManagerServerData(data);		
	}
	
	@Test
	public void searchPPhpManagerServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPManagerServerDataInterImpl impl = (IPHPManagerServerDataInterImpl)context.getBean("iPHPManagerServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("PhpVideoService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		PhpManagerServerData result = impl.queryPhpManagerServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDatePPhpManagerServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPManagerServerDataInterImpl impl = (IPHPManagerServerDataInterImpl)context.getBean("iPHPManagerServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("PhpVideoService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 15:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<PhpManagerServerData> result = impl.queryListOnDatePhpManagerServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

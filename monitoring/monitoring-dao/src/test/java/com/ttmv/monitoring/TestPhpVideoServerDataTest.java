package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IPHPVideoServerDataInterImpl;

public class TestPhpVideoServerDataTest {

	@Test
	public void addPhpVideoServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPVideoServerDataInterImpl impl = (IPHPVideoServerDataInterImpl)context.getBean("iPHPVideoServerDataInterImpl");
		PhpVideoServerData data = new PhpVideoServerData();
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
		impl.addPhpVideoServerData(data);		
	}
	
	@Test
	public void searchPhpVideoServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPVideoServerDataInterImpl impl = (IPHPVideoServerDataInterImpl)context.getBean("iPHPVideoServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("PhpVideoService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		PhpVideoServerData result = impl.queryPhpVideoServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDatePhpVideoServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPVideoServerDataInterImpl impl = (IPHPVideoServerDataInterImpl)context.getBean("iPHPVideoServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("PhpVideoService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 15:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<PhpVideoServerData> result = impl.queryListOnDatePhpVideoServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

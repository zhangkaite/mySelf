package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IPHPServerDataInterImpl;

public class TestPhpServerData {

	@Test
	public void addPhpServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPServerDataInterImpl impl = (IPHPServerDataInterImpl)context.getBean("iPHPServerDataInterImpl");
		PHPServerData data = new PHPServerData();
		data.setIp("192.168.13.165");
		data.setPort(129);
		data.setServerType("B");
		data.setServerId("10");
		data.setCpu(50);
		data.setDisk(60);
		data.setMem(70);
		data.setNetConnections(190);
		data.setNetLoad(16);
		data.setSysLoad(50);
		data.setTimestamp(new Date());
		impl.addPHPServerData(data);
	}
	
	@Test
	public void testSelected()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPServerDataInterImpl impl = (IPHPServerDataInterImpl)context.getBean("iPHPServerDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setGroupField("ip");
		List<PHPServerData> list = impl.querySelectedPHPServerData(query);
		System.out.println(list.size());
	}
	
	@Test
	public void testSelectedOne()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPHPServerDataInterImpl impl = (IPHPServerDataInterImpl)context.getBean("iPHPServerDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("A");
		query.setIp("192.168.13.158");
		query.setPort(92);
		PHPServerData data = impl.queryPHPServerDataByIpAndServerTypeAndPort(query);
		System.out.println(data.getId());
	}
}

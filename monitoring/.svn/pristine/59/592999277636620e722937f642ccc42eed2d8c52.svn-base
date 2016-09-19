package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IPHPServerDataInterImpl;
import com.ttmv.monitoring.imp.ITranscodingDataInterImpl;

public class TestTDServerData {

	@Test
	public void addTranscodingData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITranscodingDataInterImpl impl = (ITranscodingDataInterImpl)context.getBean("iTranscodingDataInterImpl");
		TranscodingData data = new TranscodingData();
		data.setIp("192.168.13.165");
		data.setPort(129);
		data.setServerType("TranscodingService");
		data.setServerId("10");
		data.setCpu(50);
		data.setDisk(60);
		data.setMem(70);
		data.setTimestamp(new Date());
		impl.addTranscodingData(data);
	}
	
	@Test
	public void testSelected()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITranscodingDataInterImpl impl = (ITranscodingDataInterImpl)context.getBean("iTranscodingDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setGroupField("ip");
		List<TranscodingData> list = impl.querySelectedTranscodingData(query);
		System.out.println(list.size());
	}
	
	@Test
	public void testSelectedOne()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITranscodingDataInterImpl impl = (ITranscodingDataInterImpl)context.getBean("iTranscodingDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setIp("192.168.13.165");
		query.setPort(129);
		TranscodingData data = impl.queryTranscodingDataByIpAndServerTypeAndPort(query);
		System.out.println(data.getId());
	}
}

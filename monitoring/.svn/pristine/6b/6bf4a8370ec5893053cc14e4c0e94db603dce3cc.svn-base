package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IScreenShotDataInterImpl;

public class TestSSDServerData {

	@Test
	public void addScreenShotData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IScreenShotDataInterImpl impl = (IScreenShotDataInterImpl)context.getBean("iScreenShotDataInterImpl");
		ScreenShotData data = new ScreenShotData();
		data.setIp("192.168.13.165");
		data.setPort(129);
		data.setServerType("ScreenshotService");
		data.setServerId("10");
		data.setCpu(50);
		data.setDisk(60);
		data.setMem(70);
		data.setTimestamp(new Date());
		impl.addScreenShotData(data);
	}
	
	@Test
	public void testSelected()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IScreenShotDataInterImpl impl = (IScreenShotDataInterImpl)context.getBean("iScreenShotDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setGroupField("ip");
		List<ScreenShotData> list = impl.querySelectedScreenShotData(query);
		System.out.println(list.size());
	}
	
	@Test
	public void testSelectedOne()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IScreenShotDataInterImpl impl = (IScreenShotDataInterImpl)context.getBean("iScreenShotDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setIp("192.168.13.165");
		query.setPort(129);
		ScreenShotData data = impl.queryScreenShotDataByIpAndServerTypeAndPort(query);
		System.out.println(data.getId());
	}
	
	@Test
	public void testQueryList()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IScreenShotDataInterImpl impl = (IScreenShotDataInterImpl)context.getBean("iScreenShotDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setIp("192.168.13.165");
		query.setPort(129);
		ScreenShotData data = impl.queryScreenShotDataByIpAndServerTypeAndPort(query);
		System.out.println(data.getId());
	}
}

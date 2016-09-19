package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IMediaControlDataInterImpl;

public class TestMediaControlData {
	
	@Test
	public void addMediaControlData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaControlDataInterImpl impl = (IMediaControlDataInterImpl)context.getBean("iMediaControlDataInterImpl");
		MediaControlData data = new MediaControlData();
		data.setIp("192.168.13.166");
		data.setPort(124);
		data.setServerType("B");
		data.setServerId("10");
		data.setCpu(50);
		data.setDisk(60);
		data.setMem(70);
		data.setInputMessages(20);
		data.setOutputMessages(30);
		data.setMediaTransmissionServers("123456");
		data.setCreatedRoomCount(300);
		data.setTimestamp(new Date());
		impl.addMediaControlData(data);
	}
	
	@Test
	public void testSelected()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaControlDataInterImpl impl = (IMediaControlDataInterImpl)context.getBean("iMediaControlDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setGroupField("ip");
		List<MediaControlData> list = impl.querySelectedMediaControlData(query);
		System.out.println(list.size());
	}
	
	@Test
	public void testSelectedOne()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaControlDataInterImpl impl = (IMediaControlDataInterImpl)context.getBean("iMediaControlDataInterImpl");
		DataBeanQuery query  = new DataBeanQuery();
		query.setServerType("B");
		query.setIp("192.168.13.166");
		query.setPort(124);
		MediaControlData data = impl.queryMediaControlDataByIpAndServerTypeAndPort(query);
		System.out.println(data.getId());
	}
}

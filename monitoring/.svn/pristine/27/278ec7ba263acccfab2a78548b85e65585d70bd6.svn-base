package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IMediaControlDataInterImpl;
import com.ttmv.monitoring.imp.IMediaForwardDataInterImpl;

public class TestMediaForwardData {
	
	
	@Test
	public void addMediaForwardData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaForwardDataInterImpl impl = (IMediaForwardDataInterImpl)context.getBean("iMediaForwardDataInterImpl");
		MediaForwardData data = new MediaForwardData();
		data.setIp("192.168.13.166");
		data.setPort(124);
		data.setServerType("B");
		data.setServerId("10");
		data.setCpu(50);
		data.setDisk(60);
		data.setMem(70);
		data.setRoomCount(56);
		data.setTimestamp(new Date());
		data.setUdxConnectionLength(45);
		impl.addMediaForwardData(data);
	}
	
	@Test
	public void testSearch() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaForwardDataInterImpl mediaForwardDataDaoImpl = (IMediaForwardDataInterImpl)context.getBean("iMediaForwardDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setIp("192.168.1.1");
		data.setPort(8080);
		data.setServerType("AvServerTransmitService");
		MediaForwardData media = mediaForwardDataDaoImpl.queryMediaForwardDataByIpAndServerTypeAndPort(data);
		System.out.println(media.getId());
	}
	
	@Test
	public void testQueryList()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMediaForwardDataInterImpl impl = (IMediaForwardDataInterImpl)context.getBean("iMediaForwardDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("1");
		query.setIp("192.168.1.1");
		query.setPort(10088);
		query.setSum(600);
		List<MediaForwardData> datas = impl.queryListMediaForwardDataByIpAndServerTypeAndPort(query);
		System.out.println(datas.size());
	}
}

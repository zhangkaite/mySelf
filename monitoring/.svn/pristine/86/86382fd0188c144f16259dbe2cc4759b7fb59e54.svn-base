package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.ITasServerDataInterImpl;

public class TestTasServerDataTest {

	@Test
	public void addTasServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITasServerDataInterImpl impl = (ITasServerDataInterImpl)context.getBean("iTasServerDataInterImpl");
		TasServerData data = new TasServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowTasService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addTasServerData(data);		
	}
	
	@Test
	public void searchTasServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITasServerDataInterImpl impl = (ITasServerDataInterImpl)context.getBean("iTasServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowTasService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		TasServerData result = impl.queryTasServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateTasServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ITasServerDataInterImpl impl = (ITasServerDataInterImpl)context.getBean("iTasServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowTasService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 18:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<TasServerData> result = impl.queryListOnDateTasServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IUmsServerDataInterImpl;

public class TestUmsServerDataTest {

	@Test
	public void addUmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUmsServerDataInterImpl impl = (IUmsServerDataInterImpl)context.getBean("iUmsServerDataInterImpl");
		UmsServerData data = new UmsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowUmsService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addUmsServerData(data);		
	}
	
	@Test
	public void searchUmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUmsServerDataInterImpl impl = (IUmsServerDataInterImpl)context.getBean("iUmsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowUmsService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		UmsServerData result = impl.queryUmsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateUmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUmsServerDataInterImpl impl = (IUmsServerDataInterImpl)context.getBean("iUmsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowUmsService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 18:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<UmsServerData> result = impl.queryListOnDateUmsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

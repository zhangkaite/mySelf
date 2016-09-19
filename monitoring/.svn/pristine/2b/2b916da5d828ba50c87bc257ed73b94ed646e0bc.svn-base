package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IRmsServerDataInterImpl;

public class TestRmsServerDataTest {

	@Test
	public void addRmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IRmsServerDataInterImpl impl = (IRmsServerDataInterImpl)context.getBean("iRmsServerDataInterImpl");
		RmsServerData data = new RmsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowRmsService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addRmsServerData(data);		
	}
	
	@Test
	public void searchRmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IRmsServerDataInterImpl impl = (IRmsServerDataInterImpl)context.getBean("iRmsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowRmsService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		RmsServerData result = impl.queryRmsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateRmsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IRmsServerDataInterImpl impl = (IRmsServerDataInterImpl)context.getBean("iRmsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowRmsService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 17:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<RmsServerData> result = impl.queryListOnDateRmsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

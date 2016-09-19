package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IPrsServerDataInterImpl;

public class TestPrsServerDataTest {

	@Test
	public void addPrsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPrsServerDataInterImpl impl = (IPrsServerDataInterImpl)context.getBean("iPrsServerDataInterImpl");
		PrsServerData data = new PrsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowPrsService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addPrsServerData(data);		
	}
	
	@Test
	public void searchPrsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPrsServerDataInterImpl impl = (IPrsServerDataInterImpl)context.getBean("iPrsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowPrsService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		PrsServerData result = impl.queryPrsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDatePrsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IPrsServerDataInterImpl impl = (IPrsServerDataInterImpl)context.getBean("iPrsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowPrsService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 17:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<PrsServerData> result = impl.queryListOnDatePrsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

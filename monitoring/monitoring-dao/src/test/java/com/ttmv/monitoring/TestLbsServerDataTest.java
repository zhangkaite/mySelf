package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.ILbsServerDataInterImpl;

public class TestLbsServerDataTest {

	@Test
	public void addLbsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ILbsServerDataInterImpl impl = (ILbsServerDataInterImpl)context.getBean("iLbsServerDataInterImpl");
		LbsServerData data = new LbsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowHttpProxyService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addLbsServerData(data);		
	}
	
	@Test
	public void searchLbsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ILbsServerDataInterImpl impl = (ILbsServerDataInterImpl)context.getBean("iLbsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowHttpProxyService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		LbsServerData result = impl.queryLbsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateLbsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ILbsServerDataInterImpl impl = (ILbsServerDataInterImpl)context.getBean("iLbsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowHttpProxyService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 16:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<LbsServerData> result = impl.queryListOnDateLbsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

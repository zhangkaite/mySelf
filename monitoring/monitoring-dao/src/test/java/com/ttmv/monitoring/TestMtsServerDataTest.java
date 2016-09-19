package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IMtsServerDataInterImpl;

public class TestMtsServerDataTest {

	@Test
	public void addMtsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMtsServerDataInterImpl impl = (IMtsServerDataInterImpl)context.getBean("iMtsServerDataInterImpl");
		MtsServerData data = new MtsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowMtsService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addMtsServerData(data);		
	}
	
	@Test
	public void searchMtsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMtsServerDataInterImpl impl = (IMtsServerDataInterImpl)context.getBean("iMtsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowMtsService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		MtsServerData result = impl.queryMtsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateMtsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMtsServerDataInterImpl impl = (IMtsServerDataInterImpl)context.getBean("iMtsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowMtsService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 16:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<MtsServerData> result = impl.queryListOnDateMtsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

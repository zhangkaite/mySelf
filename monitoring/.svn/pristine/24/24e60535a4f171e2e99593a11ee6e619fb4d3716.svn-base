package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IHpServerDataInterImpl;

public class TestHpServerDataTest {

	@Test
	public void addHpServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IHpServerDataInterImpl impl = (IHpServerDataInterImpl)context.getBean("iHpServerDataInterImpl");
		HpServerData data = new HpServerData();
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
		impl.addHpServerData(data);		
	}
	
	@Test
	public void searchHpServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IHpServerDataInterImpl impl = (IHpServerDataInterImpl)context.getBean("iHpServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowHttpProxyService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		HpServerData result = impl.queryHpServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateHpServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IHpServerDataInterImpl impl = (IHpServerDataInterImpl)context.getBean("iHpServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowHttpProxyService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 15:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<HpServerData> result = impl.queryListOnDateHpServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

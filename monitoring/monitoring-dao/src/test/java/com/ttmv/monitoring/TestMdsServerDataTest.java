package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IMdsServerDataInterImpl;

public class TestMdsServerDataTest {

	@Test
	public void addMdsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMdsServerDataInterImpl impl = (IMdsServerDataInterImpl)context.getBean("iMdsServerDataInterImpl");
		MdsServerData data = new MdsServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowMdsService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addMdsServerData(data);		
	}
	
	@Test
	public void searchMdsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMdsServerDataInterImpl impl = (IMdsServerDataInterImpl)context.getBean("iMdsServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowMdsService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		MdsServerData result = impl.queryMdsServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateMdsServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMdsServerDataInterImpl impl = (IMdsServerDataInterImpl)context.getBean("iMdsServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowMdsService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 16:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<MdsServerData> result = impl.queryListOnDateMdsServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.imp.IMdsServerDataInterImpl;
import com.ttmv.monitoring.imp.IMssServerDataInterImpl;

public class TestMssServerDataTest {

	@Test
	public void addMssServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMssServerDataInterImpl impl = (IMssServerDataInterImpl)context.getBean("iMssServerDataInterImpl");
		MssServerData data = new MssServerData();
		data.setCpu(10);
		data.setDisk(10);
		data.setIp("192.168.13.157");
		data.setMem(10);
		data.setPort(10);
		data.setRunTime(10);
		data.setServerId("1");
		data.setServerType("ImShowMssService");
		data.setWorkThread(10);
		data.setTimestamp(new Date());
		impl.addMssServerData(data);		
	}
	
	@Test
	public void searchMssServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMssServerDataInterImpl impl = (IMssServerDataInterImpl)context.getBean("iMssServerDataInterImpl");
		DataBeanQuery data = new DataBeanQuery();
		data.setServerType("ImShowMssService");
		data.setIp("192.168.13.157");
		data.setPort(10);
		MssServerData result = impl.queryMssServerDataByIpAndServerTypeAndPort(data);		
		System.out.println(result.getId());
		System.out.println(result.getCreateTime().getTime());
	}
	
	@Test
	public void searchListByDateMssServerData() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IMssServerDataInterImpl impl = (IMssServerDataInterImpl)context.getBean("iMssServerDataInterImpl");
		DataBeanQuery query = new DataBeanQuery();
		query.setServerType("ImShowMssService");
		query.setIp("192.168.13.157");
		query.setPort(10);
		query.setCurentTime("2015-10-19 16:00:00");
		query.setPreviousTime("2015-10-19 14:00:00");
		List<MssServerData> result = impl.queryListOnDateMssServerDataByIpAndServerTypeAndPort(query);	
		System.out.println(result.size());
	}
}

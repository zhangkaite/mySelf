package com.ttmv.monitoring;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlertRecordInfoQuery;
import com.ttmv.monitoring.imp.IAlertRecordInfoInterImpl;

public class TestAlertRecordInfo {

	@Test
	public void addUserInfo() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlertRecordInfoInterImpl impl = (IAlertRecordInfoInterImpl)context.getBean("iAlertRecordInfoInterImpl");
		
		AlertRecordInfo info = new AlertRecordInfo();
		info.setIp("192.168.13.157");
		info.setServerType("BB");
		info.setAlertType("CPU");
		info.setAlertTime(new Date());
		info.setAlertMsg("CPU报警了啊");
		
		impl.addAlertRecordInfo(info);
	}
	
	@Test
	public void queryListUserInfo() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlertRecordInfoInterImpl impl = (IAlertRecordInfoInterImpl)context.getBean("iAlertRecordInfoInterImpl");
		
		AlertRecordInfo info = new AlertRecordInfo();
		info.setIp("192.168.13.157");
		info.setServerType("BB");
		List<AlertRecordInfo> list = impl.queryListAlertRecordInfo(info);
		System.out.println(list);
	}
	
	@Test
	public void queryPageUserInfo()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlertRecordInfoInterImpl impl = (IAlertRecordInfoInterImpl)context.getBean("iAlertRecordInfoInterImpl");
		
		AlertRecordInfoQuery query = new AlertRecordInfoQuery();
		query.setPageSize(10);
		query.setPage(1);
		//query.setServerType("BB");
		//query.setEndTime("2015-10-16 15:05:00");
		//query.setStartTime("2015-10-16 14:56:00");
		
		Page page = impl.queryPageAlertRecordInfo(query);
		//AlertRecordInfo info = (AlertRecordInfo) page.getData().get(0);
		System.out.println(page.getData().size() + " ===" + page.getSum());
	}
}

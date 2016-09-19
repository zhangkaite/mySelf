package com.ttmv.monitoring;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.ServerSubSysinfo;
import com.ttmv.monitoring.imp.IServerSubSysinfoInterImpl;

public class TestServerSubSysinfo {

	@Test
	public void testSelected()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IServerSubSysinfoInterImpl impl = (IServerSubSysinfoInterImpl)context.getBean("iServerSubSysinfoInterImpl");
		List<ServerSubSysinfo> list = impl.queryServerSubSysinfoBySysType("1");
		System.out.println(list.size());
	}
}

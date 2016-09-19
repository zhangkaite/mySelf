package com.ttmv.monitoring;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.WhiteListQuery;
import com.ttmv.monitoring.imp.IWhiteListInterImpl;

public class TestWhiteListTest {
	
	@Test
	public void addWhiteList(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IWhiteListInterImpl impl = (IWhiteListInterImpl)context.getBean("iWhiteListInterImpl");
	
		WhiteList data = new WhiteList();
		data.setStartIp("192.168.13");
		data.setEndIp("13");
		data.setType(3);
		try {			
			Integer result = impl.addWhiteList(data);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryWhiteList(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IWhiteListInterImpl impl = (IWhiteListInterImpl)context.getBean("iWhiteListInterImpl");
	
		try {			
			WhiteList data = impl.queryWhiteList(new BigInteger("1"));
			System.out.println(data.getStartIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryListWhiteList(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IWhiteListInterImpl impl = (IWhiteListInterImpl)context.getBean("iWhiteListInterImpl");
	
		try {			
			WhiteList query = new WhiteList();
			query.setStartIp("192.168.13");
			List<WhiteList> datas = impl.queryListByConditions(query);
			System.out.println(datas.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryPageWhiteList(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IWhiteListInterImpl impl = (IWhiteListInterImpl)context.getBean("iWhiteListInterImpl");
	
		try {			
			WhiteListQuery query = new WhiteListQuery();
			query.setPage(1);
			Page page = impl.queryPageWhiteList(query);
			System.out.println(page.getData().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

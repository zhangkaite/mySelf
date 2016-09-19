package com.ttmv.monitoring;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.imp.IAlerterInfoInterImpl;

public class TestAlerterInfo {

	/**
	 * 添加测试
	 * @throws Exception
	 */
	@Test
	public void addAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		List<String> list = new ArrayList<String>();
		list.add("3");
		list.add("2");
		list.add("1");
		AlerterInfoTmp tmp = new AlerterInfoTmp();
		tmp.setAlerterCount(3);
		tmp.setAlerterMsg(4);
		tmp.setAlerterName("第6个报警器");
		tmp.setAlerterType("1");
		tmp.setAlerterUser(list);
		impl.addAlerterInfo(tmp);
	}
	
	/**
	 * 修改测试
	 * @throws Exception
	 */
	@Test
	public void updateAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		List<String> list = new ArrayList<String>();
		list.add("3");
		list.add("2");
		AlerterInfoTmp tmp = new AlerterInfoTmp();
		tmp.setId(new BigInteger("1"));
		tmp.setAlerterCount(3);
		tmp.setAlerterMsg(4);
		tmp.setAlerterName("第1个报警器");
		tmp.setAlerterUser(list);
		tmp.setAlerterType("1");
		impl.updateAlerterInfo(tmp);
	}
	
	/**
	 * 查询测试
	 * @throws Exception
	 */
	@Test
	public void searchAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		AlerterInfoTmp tmp = impl.queryAlerterInfo(new BigInteger("14"));
		System.out.println(tmp.getAlerterUser());
	}
	
	/**
	 * 查询分页
	 * @throws Exception 
	 */
	@Test
	public void searchPageAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		AlerterInfoQuery query = new AlerterInfoQuery();
		query.setPage(1);
		query.setPageSize(20);
		Page p = impl.queryPageAlerterInfo(query);
		System.out.println(p.getData().size());	
	}
	
	/**
	 * 查询分页
	 * @throws Exception 
	 */
	@Test
	public void searchListAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		AlerterInfoQuery query = new AlerterInfoQuery();
		List<AlerterInfoTmp> list = impl.queryAlerterInfo(query);
		System.out.println(list.size());	
	}
	
	/**
	 * 删除
	 * @throws Exception 
	 */
	@Test
	public void deleteAlerterInfoTmp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IAlerterInfoInterImpl impl = (IAlerterInfoInterImpl)context.getBean("iAlerterInfoInterImpl");
		Integer result = impl.deleteAlerterInfo(new BigInteger("13"));
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		String str ="3|2|1";
	}
}

package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.mapper.mysql.CmpExpireMapper;

public class TestCmp {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addCmpTest()throws Exception{
		CmpExpireMapper mapper = (CmpExpireMapper)context.getBean("cmpExpireMapper");
		Cmp cmp = new Cmp();
		cmp.setUserId(new BigInteger("11"));
		cmp.setRemark("这是测试的数据。");
		cmp.setType("start");
		cmp.setTag("this is a test data.");
		cmp.setWarntime("2016-02-29");
		mapper.addCmp(cmp);
	}
	
	@Test
	public void updateCmpTest()throws Exception{
		CmpExpireMapper mapper = (CmpExpireMapper)context.getBean("cmpExpireMapper");
		Cmp cmp = new Cmp();
		cmp.setId(new BigInteger("1"));
		cmp.setUserId(new BigInteger("11"));
		cmp.setRemark("这是更改测试的数据。");
		cmp.setType("start");
		cmp.setWarntime("2016-02-29");
		mapper.updateCmp(cmp);
	}
	
	@Test
	public void deleteCmpTest()throws Exception{
		CmpExpireMapper mapper = (CmpExpireMapper)context.getBean("cmpExpireMapper");
		Cmp cmp = new Cmp();
		cmp.setUserId(new BigInteger("11"));
		cmp.setType("start");
		mapper.deleteCmp(cmp);
	}
	@Test
	public void queryCmpTest()throws Exception{
		CmpExpireMapper mapper = (CmpExpireMapper)context.getBean("cmpExpireMapper");
		Cmp cmp = new Cmp();
		cmp.setWarntime("2016-02-29");
		cmp.setUserId(new BigInteger("11"));
		List<Cmp> list = mapper.queryListCmpByBean(cmp);
		System.out.println(list.size());
	}
}

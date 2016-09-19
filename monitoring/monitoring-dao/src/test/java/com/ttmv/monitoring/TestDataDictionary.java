package com.ttmv.monitoring;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.DataDictionary;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.DataDictionaryQuery;
import com.ttmv.monitoring.inter.IDataDictionaryInter;

public class TestDataDictionary {

	/**
	 * 添加测试
	 * @throws Exception
	 */
	@Test
	public void addDataDictionary() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IDataDictionaryInter datadictionary = (IDataDictionaryInter)context.getBean("iDataDictionaryInter");
		DataDictionary data = new DataDictionary();
		data.setDataKey("EE");
		data.setDataValue("ff");
		data.setType("AA");
		datadictionary.addDataDictionary(data);
	}
	/**
	 * 修改测试
	 * @throws Exception 
	 */
	@Test
	public void updateDataDictionary() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IDataDictionaryInter datadictionary = (IDataDictionaryInter)context.getBean("iDataDictionaryInter");
		DataDictionary data = new DataDictionary();
		data.setDataKey("EE");
		data.setDataValue("ee");
		data.setType("AA");
		data.setId(new BigInteger("5"));
		datadictionary.updateDataDictionary(data);
	}
	
	/**
	 * 查询测试
	 * @throws Exception 
	 */
	@Test
	public void searchDataDictionary() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IDataDictionaryInter datadictionary = (IDataDictionaryInter)context.getBean("iDataDictionaryInter");
		DataDictionary data = datadictionary.queryDataDictionary("EE");
		System.out.println(data.getDataValue());
	}
	
	/**
	 * 分页查询测试
	 * @throws Exception 
	 */
	@Test
	public void searchPageDataDictionary() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IDataDictionaryInter datadictionary = (IDataDictionaryInter)context.getBean("iDataDictionaryInter");
		DataDictionaryQuery query = new DataDictionaryQuery();
		query.setPage(1);
		query.setPageSize(2);
		query.setType("AA");
		Page page = datadictionary.queryPageDataDictionary(query);
		System.out.println(page.getData().size());
	}
	
	/**
	 * 精确条件查询测试
	 * @throws Exception 
	 */
	@Test
	public void searchListDataDictionary() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IDataDictionaryInter datadictionary = (IDataDictionaryInter)context.getBean("iDataDictionaryInter");
		DataDictionaryQuery query = new DataDictionaryQuery();
		query.setPage(1);
		query.setPageSize(2);
		query.setType("AA");
		List<DataDictionary> list = datadictionary.queryDataDictionary(query);
		System.out.println(list.size());
	}
}

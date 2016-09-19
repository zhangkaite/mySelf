package com.ttmv.datacenter.paycenter.dao.implement.util;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TableIdGenerateTest {

	@Test
	public void test() {
	   ApplicationContext context = new ClassPathXmlApplicationContext("test_spring_service.xml");
	   TableIdGenerate64 tableIdGenerate = (TableIdGenerate64)context.getBean("tableIdGenerate64");
	   System.out.println("userIdGenerate : "+tableIdGenerate.getTableId("test_table_id")); 
	}

}

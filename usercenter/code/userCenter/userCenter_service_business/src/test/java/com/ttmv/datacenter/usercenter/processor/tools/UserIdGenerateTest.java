package com.ttmv.datacenter.usercenter.processor.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate64;
import com.ttmv.datacenter.usercenter.service.processor.tools.UserIdGenerate;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月21日
 */
public class UserIdGenerateTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUserId() throws Exception {
	   ApplicationContext context = new ClassPathXmlApplicationContext("test_spring_service.xml");
	   UserIdGenerate userIdGenerate = (UserIdGenerate)context.getBean("userIdGenerate");
	   System.out.println("userIdGenerate : "+userIdGenerate.getUserId());
	   Assert.assertNotNull(userIdGenerate); 
	   
	   TableIdGenerate tableIdGenerate = (TableIdGenerate)context.getBean("tableIdGenerate");
	   System.out.println("TEST_TABLIE_ID_32 : "+tableIdGenerate.getTableId("TEST_TABLIE_ID_32"));
	   Assert.assertNotNull(tableIdGenerate); 
	   
	   TableIdGenerate64 tableIdGenerate64 = (TableIdGenerate64)context.getBean("tableIdGenerate64");
	   System.out.println("TEST_TABLIE_ID_64 : "+tableIdGenerate64.getTableId("TEST_TABLIE_ID_64"));
	   Assert.assertNotNull(userIdGenerate); 
	}

}

package com.ttmv.datacenter.usercenter.service.facade.tools;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月6日 上午11:47:57  
 * @explain :
 */
public class MsgSenderToolTest {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("spring-service-activemq.xml");

	@Test
	public void test() {
		MsgSenderTool tool = (MsgSenderTool)context.getBean("msgSenderTool");
		tool.sendMessage("哈哈哈哈111111111111");
	}

}

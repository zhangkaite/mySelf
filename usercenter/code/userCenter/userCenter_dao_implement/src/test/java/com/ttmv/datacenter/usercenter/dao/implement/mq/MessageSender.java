package com.ttmv.datacenter.usercenter.dao.implement.mq;

import java.math.BigInteger;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

public class MessageSender {

	private JmsTemplate jmsTemplate = null ;
	private Destination addUserInfoDestination = null ;
	
	@Test
	public void testSend() throws Exception{
		ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-activemq.xml");
		jmsTemplate = (JmsTemplate)application.getBean("jmsTemplate");
		addUserInfoDestination = (Destination)application.getBean("destination_userInfo");
		/* 向队列中添加数据 */
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(new BigInteger("123"));
		userInfo.setUserName("wulinli");
		final String userInfoJson = JsonUtil.getObjectToJson(userInfo);
		try {
			jmsTemplate.send(addUserInfoDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(userInfoJson);
				}
			});
		} catch (Exception e) {
			throw new Exception("注册用户失败，用户的UserId是：" + userInfo.getUserid()
					+ "，失败的原因是：" + e.getMessage());
		}
	}

}

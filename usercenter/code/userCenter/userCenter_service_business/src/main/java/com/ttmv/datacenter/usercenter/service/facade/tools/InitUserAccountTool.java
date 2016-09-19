package com.ttmv.datacenter.usercenter.service.facade.tools;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月5日 上午11:34:01  
 * @explain :
 */
public class InitUserAccountTool {
	private JmsTemplate initUserAccountjmsTemplate;
	public JmsTemplate getInitUserAccountjmsTemplate() {
		return initUserAccountjmsTemplate;
	}
	public void setInitUserAccountjmsTemplate(JmsTemplate initUserAccountjmsTemplate) {
		this.initUserAccountjmsTemplate = initUserAccountjmsTemplate;
	}



	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		initUserAccountjmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}

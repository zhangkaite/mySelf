package com.ttmv.datacenter.usercenter.service.facade.tools;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月6日 上午2:40:06
 * @explain :异步队列消息发送器（注册）
 */
public class MsgSenderTool {
	private JmsTemplate addjmsTemplate;

	public JmsTemplate getAddjmsTemplate() {
		return addjmsTemplate;
	}

	public void setAddjmsTemplate(JmsTemplate addjmsTemplate) {
		this.addjmsTemplate = addjmsTemplate;
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		addjmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}

}

package com.ttmv.datacenter.usercenter.service.facade.tools.jmstool;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author Damon_Zs
 * @version 创建时间：2016年2月24日11:42:06
 * @explain :异步队列消息发送器（明星主播经验重置）
 */
public class DamsUcResetStarEXPTool {

	private JmsTemplate damsUcResetStarEXPjmsTemplate;


	public JmsTemplate getDamsUcResetStarEXPjmsTemplate() {
		return damsUcResetStarEXPjmsTemplate;
	}


	public void setDamsUcResetStarEXPjmsTemplate(
			JmsTemplate damsUcResetStarEXPjmsTemplate) {
		this.damsUcResetStarEXPjmsTemplate = damsUcResetStarEXPjmsTemplate;
	}
	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		damsUcResetStarEXPjmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}

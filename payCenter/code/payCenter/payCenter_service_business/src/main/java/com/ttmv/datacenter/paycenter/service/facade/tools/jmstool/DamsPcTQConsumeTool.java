package com.ttmv.datacenter.paycenter.service.facade.tools.jmstool;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DamsPcTQConsumeTool {

	private JmsTemplate damsPcTQConsumejmsTemplate;
	
	public JmsTemplate getDamsPcTQConsumejmsTemplate() {
		return damsPcTQConsumejmsTemplate;
	}

	public void setDamsPcTQConsumejmsTemplate(JmsTemplate damsPcTQConsumejmsTemplate) {
		this.damsPcTQConsumejmsTemplate = damsPcTQConsumejmsTemplate;
	}

	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		damsPcTQConsumejmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}

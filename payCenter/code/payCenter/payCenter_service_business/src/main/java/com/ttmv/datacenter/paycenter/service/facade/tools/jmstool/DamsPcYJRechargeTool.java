package com.ttmv.datacenter.paycenter.service.facade.tools.jmstool;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DamsPcYJRechargeTool {
	private JmsTemplate damsPcYJRechargejmsTemplate;

	public JmsTemplate getDamsPcYJRechargejmsTemplate() {
		return damsPcYJRechargejmsTemplate;
	}

	public void setDamsPcYJRechargejmsTemplate(
			JmsTemplate damsPcYJRechargejmsTemplate) {
		this.damsPcYJRechargejmsTemplate = damsPcYJRechargejmsTemplate;
	}

	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		damsPcYJRechargejmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}
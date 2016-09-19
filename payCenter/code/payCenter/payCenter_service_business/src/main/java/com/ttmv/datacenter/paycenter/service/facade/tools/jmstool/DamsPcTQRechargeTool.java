package com.ttmv.datacenter.paycenter.service.facade.tools.jmstool;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DamsPcTQRechargeTool {
	private JmsTemplate damsPcTQRechargejmsTemplate;
	
	public JmsTemplate getDamsPcTQRechargejmsTemplate() {
		return damsPcTQRechargejmsTemplate;
	}
	public void setDamsPcTQRechargejmsTemplate(
			JmsTemplate damsPcTQRechargejmsTemplate) {
		this.damsPcTQRechargejmsTemplate = damsPcTQRechargejmsTemplate;
	}



	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		damsPcTQRechargejmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}
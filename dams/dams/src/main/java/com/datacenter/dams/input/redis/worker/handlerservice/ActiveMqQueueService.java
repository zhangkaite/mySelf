package com.datacenter.dams.input.redis.worker.handlerservice;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqQueueService {
	private static Logger logger = LogManager.getLogger(ActiveMqQueueService.class);

	private String activeMqQueueName;
	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public String getActiveMqQueueName() {
		return activeMqQueueName;
	}

	public void setActiveMqQueueName(String activeMqQueueName) {
		this.activeMqQueueName = activeMqQueueName;
	}

	public void send(final String message) throws Exception {
		logger.debug("推送到SDMS系统队列[[" + activeMqQueueName + "]],数据是:" + message);
		try {
			jmsTemplate.send(activeMqQueueName, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
		logger.debug("[DAMS#RicherRank]富豪排行榜队列数据>>>推送到SDMS系统完毕.");
		logger.debug("推送到SDMS系统队列[[" + activeMqQueueName + "]]完成");
	}

}

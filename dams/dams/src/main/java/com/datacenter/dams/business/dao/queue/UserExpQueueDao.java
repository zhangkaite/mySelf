package com.datacenter.dams.business.dao.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 经验数据存入UC系统队列
 * @author wulinli
 *
 */
public class UserExpQueueDao {

	private static Logger logger=LogManager.getLogger(UserExpQueueDao.class);

	/* 明星排行榜 放入用户中心 队列 */
	private Destination userExpQueue;
	private JmsTemplate jmsTemplate;
	
	public void send(final String message)throws Exception{
		logger.debug("[DAMS#FamouserRank]用户经验队列数据>>>推送到UC系统队列,数据是:" + message);
		try {
			jmsTemplate.send(userExpQueue, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
		logger.debug("[DAMS#FamouserRank]用户经验队列数据>>>推送到UC系统完毕.");
	}

	public Destination getUserExpQueue() {
		return userExpQueue;
	}

	public void setUserExpQueue(Destination userExpQueue) {
		this.userExpQueue = userExpQueue;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}

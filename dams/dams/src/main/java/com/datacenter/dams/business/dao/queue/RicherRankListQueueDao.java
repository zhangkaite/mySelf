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
 *  富豪排行榜信息 信息放入用户中心
 * @author wll
 */
public class RicherRankListQueueDao {

	private static Logger logger=LogManager.getLogger(RicherRankListQueueDao.class);
	/* 富豪排行榜 放入用户中心 队列 */
	private Destination richerRankingListQueue;
	private JmsTemplate jmsTemplate;
	
	public void send(final String message)throws Exception{
		logger.debug("[DAMS#RicherRank]富豪排行榜队列数据>>>推送到SDMS系统队列,数据是:" + message);
		try {
			jmsTemplate.send(richerRankingListQueue, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
		logger.debug("[DAMS#RicherRank]富豪排行榜队列数据>>>推送到SDMS系统完毕.");
	}
	
	public Destination getRicherRankingListQueue() {
		return richerRankingListQueue;
	}
	public void setRicherRankingListQueue(Destination richerRankingListQueue) {
		this.richerRankingListQueue = richerRankingListQueue;
	}
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}

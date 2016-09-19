package com.datacenter.dams.business.dao.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActivityUserRankListQueueDao {

	private static Logger logger=LogManager.getLogger(ActivityUserRankListQueueDao.class);

	/* 用户活动积分排行榜 放入用户中心 队列 */
	private Destination activityUserRankingQueue;
	private JmsTemplate jmsTemplate;
	
	public void send(final String message)throws Exception{
		logger.debug("[DAMS#ActivityUserRankOutput]用户活动排行榜队列数据>>>推送到SDMS系统队列,数据是:" + message);
		try {
			jmsTemplate.send(activityUserRankingQueue, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
		logger.debug("[DAMS#ActivityUserRankOutput]用户活动排行榜队列数据>>>推送到SDMS系统完毕.");
	}
	
	public Destination getActivityUserRankingQueue() {
		return activityUserRankingQueue;
	}
	
	public void setActivityUserRankingQueue(Destination activityUserRankingQueue) {
		this.activityUserRankingQueue = activityUserRankingQueue;
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}

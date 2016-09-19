package com.datacenter.dams.business.dao.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActivityStarRankListQueueDao {

	private static Logger logger=LogManager.getLogger(ActivityStarRankListQueueDao.class);

	/* 主播活动积分排行榜 放入用户中心 队列 */
	private Destination activityStarRankingQueue;
	private JmsTemplate jmsTemplate;
	
	public void send(final String message)throws Exception{
		logger.debug("[DAMS#ActivityStarRankOutput]主播吊牌活动排行榜队列数据>>>推送到SDMS系统队列,数据是:" + message);
		try {
			jmsTemplate.send(activityStarRankingQueue, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
		logger.debug("[DAMS#ActivityStarRankOutput]主播吊牌活动排行榜队列数据>>>推送到SDMS系统完毕.");
	}


	public Destination getActivityStarRankingQueue() {
		return activityStarRankingQueue;
	}

	public void setActivityStarRankingQueue(Destination activityStarRankingQueue) {
		this.activityStarRankingQueue = activityStarRankingQueue;
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}

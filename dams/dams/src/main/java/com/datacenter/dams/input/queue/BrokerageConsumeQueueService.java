package com.datacenter.dams.input.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.input.queue.entity.BrokerageConsumeInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 控制取队列消息
 * @author wll
 */
public class BrokerageConsumeQueueService{
	
	public static final Logger logger = LogManager.getLogger(BrokerageConsumeQueueService.class);
	private static JmsTemplate jmsTemplate;
	private static RedisQueueInputDaoInter redisQueueInter;
	
	
	public static void receiveMessage(int sum ,String destinationName){
		/* 建立连接 */
		ConnectionFactory factory = jmsTemplate.getConnectionFactory();
		Connection connection = null;
		MessageConsumer consumer = null;
		Session session = null;
		try {
			connection = factory.createConnection();
			connection.start();
			/* 获取session */
			session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(destinationName);
			/* 创建消费者 */
			if(consumer == null){			
				consumer = session.createConsumer(destination);
			}
			/* 获取消息 */
			if(consumer != null){
				/* 获取配置数量的消息的信息 */
				for(int i=0;i<sum;i++){
					TextMessage message = (TextMessage) consumer.receive(500);
					if(message != null){
						String data = message.getText();
						logger.info("[DAMS#BrokerageConsumeQueueService佣金消费]读取数据是 : message="+data);
						if(Strings.isNullOrEmpty(data)){
							break;
						}
						BrokerageConsumeInfo spend = (BrokerageConsumeInfo) JsonUtil.getObjectFromJson(data, BrokerageConsumeInfo.class);
						/* 处理信息 */
						MessageHandlerCenter.getBrokerageConsumeSpendCenter().handler(spend);
						/* 处理消息 */
						session.commit();
						Thread.sleep(10);
					}else if(message == null){
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("[DAMS#BrokerageConsumeQueueService#ERROR]worker读取PC队列数据出错.",e);
		}finally{
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				logger.error("[DAMS#BrokerageConsumeQueueService#ERROR]队列关闭连接出错!",e);
			}
		}
		logger.debug("[DAMS#BrokerageConsumeQueueService]execute从PC队列中读取数据完成.");
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public RedisQueueInputDaoInter getRedisQueueInter() {
		return redisQueueInter;
	}

	public void setRedisQueueInter(RedisQueueInputDaoInter redisQueueInter) {
		this.redisQueueInter = redisQueueInter;
	}
}

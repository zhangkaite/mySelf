package com.datacenter.dams.input.queue;

import java.util.ArrayList;
import java.util.List;

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
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 控制取队列消息
 * @author wll
 */
public class TquanConsumeQueueService{
	
	public static final Logger logger = LogManager.getLogger(TquanConsumeQueueService.class);
	private static JmsTemplate jmsTemplate;
	private static RedisQueueInputDaoInter redisQueueInter;
	
	public static void receiveMessage(int sum ,String destinationName){
		
		/* 判断redis队列数据是否已满 */
		logger.debug("[DAMS#TQConsume]判断storm计算使用redis队列是否已满.");
		boolean flag = false;
		try {
			flag = redisQueueInter.RedisQueueIsFull();
		} catch (Exception e) {
			logger.error("查询Redis队列的元素数量是否已满,出错!",e);
		}
		if(flag){
			return ;
		}
		List<String> lists = new ArrayList<String>();
		/* 建立连接 */
		logger.debug("[DAMS#TQConsume]execute从PC队列中读取数据.");
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
					TextMessage message = (TextMessage) consumer.receive(1000);
					if(message != null){
						String data = message.getText();
						logger.info("[TQ消费入口数据记录]>>总数是 : "+sum+" , 数据标示数 : "+i+" , 数据是 : message="+data);
						if(Strings.isNullOrEmpty(data)){
							break;
						}
						TquanConsumeSpendInfo spend = (TquanConsumeSpendInfo) JsonUtil.getObjectFromJson(data,TquanConsumeSpendInfo.class);
						/* 处理信息 */
						MessageHandlerCenter.getTquanConsumeSpendCenter().handler(spend);
						session.commit();
						Thread.sleep(10);
					}else if(message == null){
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("[DAMS#TQConsume#ERROR]worker读取PC队列数据.",e);
		}finally{
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				logger.error("[DAMS#TQConsume#ERROR]队列关闭连接出错!",e);
			}
		}
		logger.debug("[DAMS#TQConsume]execute从PC队列中读取数据完成.");
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

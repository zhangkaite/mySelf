package com.ttmv.datacenter.usercenter.worker.listener;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午11:05:33  
 * @explain :service层日志记录队列监听
 */

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.worker.service.LogExcuteMessageServiceImpl;

public class LogJmsReceiverListener extends Thread implements MessageListener,ExceptionListener {
	private static Logger logger = LogManager.getLogger(LogJmsReceiverListener.class);
	
	private LogExcuteMessageServiceImpl logExcuteMessageServiceImpl;
	private String url;
	private String queue;
	
	ConnectionFactory connectionFactory;
	Connection connection = null;
	Session session;
	Destination destination;
	MessageConsumer consumer;

	public LogJmsReceiverListener() {
		
	}
	
	public void doStart(){
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, url);
		try {
			connection = connectionFactory.createConnection();
			connection.setExceptionListener((ExceptionListener) this);
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue);
			consumer = session.createConsumer(destination);
			new Thread(this).start();
		} catch (JMSException e) {
			logger.error("Create fail!");
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			System.out.println("开始监听(日志记录)消息...");
			consumer.setMessageListener((MessageListener) this);
		} catch (JMSException e) {
			logger.error(" MessageListener failed...");
			e.printStackTrace();
		}
	}

	/**
	 * 接收消息
	 */
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				final String msg = txtMsg.getText();
				logger.debug("[MQ:收到消息_登录日志消息]" + msg);
				//TODO 进入消息解析器
//				new Thread(){
//					public void run() {
//						try {
//							logExcuteMessageServiceImpl.doService(msg);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//			  }.start();
				logExcuteMessageServiceImpl.doService(msg);
			}
		} catch (JMSException e) {
			logger.error("The process of getting a message failed...");
			e.printStackTrace();
		}
	}

	// 异步消息异常处理
	public void onException(JMSException arg0) {
		logger.error("JMS异常！");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}

	public LogExcuteMessageServiceImpl getLogExcuteMessageServiceImpl() {
		return logExcuteMessageServiceImpl;
	}

	public void setLogExcuteMessageServiceImpl(
			LogExcuteMessageServiceImpl logExcuteMessageServiceImpl) {
		this.logExcuteMessageServiceImpl = logExcuteMessageServiceImpl;
	}


	

}

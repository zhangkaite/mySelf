package com.ttmv.datacenter.paycenter.worker.listener;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月4日 下午5:05:33  
 * @explain :service层用户注册队列监听
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

import com.ttmv.datacenter.paycenter.worker.service.AddUserAccountExcuteMessageServiceImpl;


public class AddUserAccountJmsReceiverListener extends Thread implements MessageListener,ExceptionListener {
	private static Logger logger = LogManager.getLogger(AddUserAccountJmsReceiverListener.class);
	
	private AddUserAccountExcuteMessageServiceImpl addUserAccountExcuteMessageServiceImpl;
	private String url;
	private String queue;
	
	ConnectionFactory connectionFactory;
	Connection connection = null;
	Session session;
	Destination destination;
	MessageConsumer consumer;

	public AddUserAccountJmsReceiverListener() {
		
	}
	
	public void doStart(){
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, url);
		try {
			connection = connectionFactory.createConnection();
			connection.setExceptionListener((ExceptionListener) this);
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue);
			consumer = session.createConsumer(destination);
			new Thread(this).start();
		} catch (JMSException e) {
			System.err.println("Create fail!");
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			logger.info("开始监听(用户注册_资金账户初始化)消息...");
			consumer.setMessageListener((MessageListener) this);
		} catch (JMSException e) {
			logger.error("消息监听错误！！！" , e);
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
				logger.info("收到...[用户注册_资金账户初始化]...消息！！！" + msg);
				//TODO 进入消息解析器
				try {
					addUserAccountExcuteMessageServiceImpl.doService(msg);
//					 new Thread(){
//							public void run() {
//								try {
//									addUserAccountExcuteMessageServiceImpl.doService(msg);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							}
//					  }.start();
				} catch (Exception e) {
					logger.error("[用户注册_资金账户初始化异常！！！]" ,e);
					e.printStackTrace();
				}
			}
		} catch (JMSException e) {
			logger.error("The process of getting a message failed..." ,e);
			e.printStackTrace();
		}
	}

	// 异步消息异常处理
	public void onException(JMSException arg0) {
		System.err.println("JMS异常！");
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

	public AddUserAccountExcuteMessageServiceImpl getAddUserAccountExcuteMessageServiceImpl() {
		return addUserAccountExcuteMessageServiceImpl;
	}

	public void setAddUserAccountExcuteMessageServiceImpl(
			AddUserAccountExcuteMessageServiceImpl addUserAccountExcuteMessageServiceImpl) {
		this.addUserAccountExcuteMessageServiceImpl = addUserAccountExcuteMessageServiceImpl;
	}





	

}

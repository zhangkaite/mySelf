package com.ttmv.datacenter.usercenter.dao.implement.mq;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MessageListenerTest implements MessageListener {

	public void onMessage(Message message) {
		System.out.println("这是测试的信息");
//		ActiveMQMapMessage msg  = null;
//		 if (message instanceof ActiveMQMapMessage) {  
//			 msg = (ActiveMQMapMessage) message;  
//             String username = null;
//             String password = null ;
//			try {
//				username = msg.getString("text");
//			} catch (JMSException e) {
//				e.printStackTrace();
//			}  
//             System.out.println("Message::: "+username);  
//         }  
	}

}

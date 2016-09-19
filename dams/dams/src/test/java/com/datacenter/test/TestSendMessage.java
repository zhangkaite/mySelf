package com.datacenter.test;


import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class TestSendMessage {

	@Test
	public void createQueue()throws Exception{
       ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.13.156:50006");
       Connection connection = factory.createConnection();
       connection.start();
       Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       Destination destination =  session.createQueue("DAMS_PC_YJConsume_Queue");
       
       //da系统计算报表的数据 dams_statistic_finish
       String txt = "{\"time\":\"1458617939\",\"tq_recharge\":\"100\",\"tb_consume\":\"6.6\",\"tb_consume_self\":\"100\",\"tb_recharge\":\"100\",\"tq_consume\":\"7.6\",\"tq_consume_self\":\"1.0\",\"yj_recharge\":\"100\",\"yj_consume\":\"7.6\"}";
       //TB充值队列 DAMS_PC_TBRecharge_Queue
       String tbRecharge = "{\"time\":1458526210,\"userID\":\"100\",\"number\":6.6,\"orderId\":\"100\",\"clientType\":\"1\",\"version\":\"100\"}";
     //TB充值队列 DAMS_PC_TBConsume_Queue
       String tbConsume = "{\"userID\":5,\"destinationUserID\":5,\"productID\":\"29\",\"productCount\":1,\"productPrice\":\"900\",\"number\":\"6.666\",\"time\":\"1458526210\",\"orderId\":\"123456789\",\"clientType\":\"1\",\"version\":\"v3.1\" ,\"roomID\":100}";
     //TB充值队列 DAMS_PC_TQRecharge_Queue
       String tqRecharge = "{\"time\":1458526210,\"userID\":\"100\",\"number\":6600,\"orderId\":\"100\",\"clientType\":\"1\",\"version\":\"100\",\"TDNumber\":6600,\"type\":0}";
     //TB充值队列 DAMS_PC_TQConsume_Queue
       String tqConsume = "{\"userID\":6,\"destinationUserID\":6,\"productID\":\"1\",\"productCount\":6.6,\"productPrice\":\"100\",\"number\":\"6600\",\"time\":\"1458526210\",\"orderId\":\"test123456789\",\"clientType\":\"1\",\"version\":\"v3.1\" ,\"userType\":\"1\",\"roomID\":100,\"equipID\":\"1\"}";
     //TB充值队列 DAMS_PC_YJRecharge_Queue
       String yjRecharge = "{\"time\":1458526210,\"userID\":\"100\",\"number\":6.6,\"orderId\":\"100\",\"clientType\":\"1\",\"version\":\"100\"}";
     //TB充值队列 DAMS_PC_YJConsume_Queue
       String yjConsume = "{\"time\":1458526210,\"userID\":\"100\",\"number\":6.6,\"orderId\":\"100\",\"clientType\":\"1\",\"version\":\"100\"}";
       
       //创建消费者
       MessageProducer producer = session.createProducer(destination);
       producer.setDeliveryMode(DeliveryMode.PERSISTENT); 
       for(int i=0;i<10;i++){    	   
    	   ActiveMQTextMessage message = new ActiveMQTextMessage();
    	   message.setText(yjConsume);
    	   producer.send(message);
       }
//       for(int i=0;i<10000;i++){    	   
//    	   ActiveMQTextMessage message = new ActiveMQTextMessage();
//    	   message.setText("{\"userID\":5,\"destinationUserID\":333,\"productID\":\"1\",\"productCount\":66,\"productPrice\":\"100\",\"number\":\"6600\",\"time\":\"1452960001\",\"orderId\":\"test123456789\",\"clientType\":\"1\",\"version\":\"v3.1\" ,\"roomID\":100}");
//    	   // message.setText("{\"userID\":6,\"destinationUserID\":111,\"productID\":\"1\",\"productCount\":66,\"productPrice\":\"100\",\"number\":\"6600\",\"time\":\"1452736923\",\"orderId\":\"test123456789\",\"clientType\":\"1\",\"version\":\"v3.1\" ,\"userType\":\"1\",\"roomID\":100,\"equipID\":\"1\"}");
//    	   producer.send(message);
//       }
	}
}

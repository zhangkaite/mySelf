package org.jam.dataflow.worker.flow;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;


import org.jam.dataflow.Dataflow;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AbsWritePostDB implements Job{

	private Connection connection = null;
	private MessageConsumer consumer = null;
	private Session session = null;
	private Destination destination = null;
    private String queueName;
	public void initMqConn() {
		try {
			connection = Dataflow.getWorkerManager().getQueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (Exception e) {
			Dataflow.logger().error("Failed to connect que.", e);
			return;
		}

	}

	public void writePostDB() {
		TextMessage message = null;
		try {
			destination = session.createQueue(queueName);
			consumer = session.createConsumer(destination);
			if (consumer != null) {
				message = (TextMessage) consumer.receive(50);
				if (message != null) {
					Dataflow.logger().info("[队列消息 ]:" + queueName + "==[msg]==: " + message.getText());
					Dataflow.getWriters().get(queueName).writePostDB(message.getText());
				} else {
					Dataflow.logger().debug("[空队列 ]:" + queueName + "message is null");
				}
				consumer.close();
			}
			message = null;
		} catch (Exception e) {
			if (message != null) {
				try {
					System.out.println("======================" + e.getMessage());
					Dataflow.failWritePost(queueName, message.getText(), e.getMessage());
				} catch (JMSException e1) {
					Dataflow.logger().error("Failed to get text from message of que: " + queueName);
				}
			}
			Dataflow.logger().error("Failed to write post from que: " + queueName, e);
		}finally {
			  try {
		            session.close();
		        } catch (JMSException e) {
		            Dataflow.logger().warn("Warn to close que connect error.", e);
		        }
		}

	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}


}

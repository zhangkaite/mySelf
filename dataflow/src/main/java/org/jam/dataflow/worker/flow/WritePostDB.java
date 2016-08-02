package org.jam.dataflow.worker.flow;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jam.dataflow.Dataflow;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by James on 16/4/25.
 */
@DisallowConcurrentExecution
public class WritePostDB implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Dataflow.logger().debug(Thread.currentThread().getName() + " startup writePostDB at " + (new Date()).toString());
        Connection connection = null;
        MessageConsumer consumer = null;
        Session session = null;
        Destination destination = null;

        try {
            connection = Dataflow.getWorkerManager().getQueConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e) {
            Dataflow.logger().error("Failed to connect que.", e);
            return ;
        }

        String[] keys = new String[Dataflow.getWriters().size()];
        Dataflow.getWriters().keySet().toArray(keys);
        TextMessage message = null;
        for (String key : keys) {
            try {
                destination = session.createQueue(key);
                consumer = session.createConsumer(destination);
                if (consumer != null) {
                    message = (TextMessage) consumer.receive(50);
                    if (message != null) {
                        Dataflow.logger().info("[队列消息 ]:" + key + "==[msg]==: " + message.getText());
                        Dataflow.getWriters().get(key).writePostDB(message.getText());
                    }else{
                        Dataflow.logger().debug("[空队列 ]:" + key + "message is null");
                    }
                    consumer.close();
                }
                message = null;
            } catch (Exception e) {
                    if(message != null){
                        try {
                        	System.out.println("======================"+e.getMessage());
                            Dataflow.failWritePost(key, message.getText(),e.getMessage());
                        } catch (JMSException e1) {
                            Dataflow.logger().error("Failed to get text from message of que: " + key);
                        }
                    }
                    Dataflow.logger().error("Failed to write post from que: " + key, e);
            }
        }
        try {
            session.close();
        } catch (JMSException e) {
            Dataflow.logger().warn("Warn to close que connect error.", e);
        }
    }

}

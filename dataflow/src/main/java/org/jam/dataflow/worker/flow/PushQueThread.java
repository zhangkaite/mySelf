package org.jam.dataflow.worker.flow;

import org.jam.dataflow.Dataflow;
import org.jam.dataflow.worker.WorkerManager;

import javax.jms.*;

/**
 * Created by James on 16/4/29.
 */
public class PushQueThread implements Runnable{

    private String queName;
    private String data;

    public PushQueThread(String queName, String data){
        this.queName = queName;
        this.data = data;
    }

    public void run() {
        Connection conn = null;
        Session session = null;
        try {
            conn = Dataflow.getWorkerManager().getQueConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage msg = session.createTextMessage(data);
            producer.send(msg);
            Dataflow.logger().debug("Pushed que msg: " + msg);
        } catch (Exception e) {
            Dataflow.logger().error("Failed to push que.", e);
            Dataflow.failPushQue(queName, data ,e.getMessage());
        }finally {
            try {
                session.close();
            } catch (JMSException e) {
                Dataflow.logger().warn("Warn to close que connect error.", e);
            }
        }
    }
}

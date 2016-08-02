package org.jam.dataflow.worker.retry;

import org.jam.dataflow.Dataflow;
import org.jam.dataflow.log.domain.FailLog;
import org.jam.dataflow.worker.WorkerManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.jms.*;

/**
 * Created by James on 16/5/12.
 */
public class RetryQueFailLog implements Job{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FailLog log = null;
        try {
            log = Dataflow.getDbAccessor().takeFailLog(FailLog.PHASE_PUSHQUE);
            if (null == log){
                return ;
            }
            if(pushQue(log.getBiz(), log.getData())){
                Dataflow.getDbAccessor().finishFailLog(log.getId());
            }else{
                Dataflow.getDbAccessor().returnFailLog(log.getId());
            }
        } catch (Exception e) {
            Dataflow.logger().error("Failed to retry push que.", e);
        }
    }


    private boolean pushQue(String queName, String data) throws Exception{
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
            Dataflow.logger().debug("Pushed que msg: " + msg + " in retry.");
        } catch (Exception e) {
            Dataflow.logger().error("Failed to push que.", e);
            Dataflow.getWorkerManager().restartQueConnection();
            return false;
        }finally {
            try {
            	if(session !=null){
            		
            		session.close();
            	}
            } catch (JMSException e) {
                Dataflow.logger().warn("Warn to close que connect error.", e);
            }
        }
        return true;
    }
}

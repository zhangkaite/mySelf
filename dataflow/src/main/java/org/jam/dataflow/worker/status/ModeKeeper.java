package org.jam.dataflow.worker.status;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.jam.dataflow.Dataflow;
import org.jam.dataflow.domain.DataMode;
import org.jam.dataflow.log.domain.FailLog;
import org.jam.dataflow.worker.WorkerManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created by James on 16/4/28.
 */
public class ModeKeeper implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DataMode mode = Dataflow.getDataMode();
        switch (mode) {
            case TOLOW:
                if (checkQueFinished()) {
                    Dataflow.setModeLOW();
                }
                break;
            case RECOVERING:
                if (checkWritePreFailLogFinished()) {
                    Dataflow.setModeRecovered();
                }
                break;
            default:
                break;
        }
    }

    public boolean checkQueFinished() {
        try {
            String queJmxUrl = Dataflow.getProperty("que-jmx-url");

            JMXServiceURL url = new JMXServiceURL(queJmxUrl);
            JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
            MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
            ObjectName name = new ObjectName(Dataflow.getProperty("que-broker-name"));
            BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection,
                    name, BrokerViewMBean.class, true);
            ObjectName[] names = mBean.getQueues();
            if(null == names || names.length == 0){
                return true;
            }
            for (ObjectName queueName : mBean.getQueues()) {
                QueueViewMBean queueMBean = (QueueViewMBean) MBeanServerInvocationHandler
                        .newProxyInstance(connection, queueName, QueueViewMBean.class, true);
                if (Dataflow.getWriters().containsKey(queueMBean.getName())) {
                    if (queueMBean.getQueueSize() != 0) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            Dataflow.logger().error("Failed to check que finished.", e);
            return false;
        }
    }

    public boolean checkWritePreFailLogFinished() {
        try{
            return !Dataflow.getDbAccessor().hasFailLog(FailLog.PHASE_WRITEPREDB);
        }catch (Exception e){
            Dataflow.logger().error("Failed to check pre data recover.", e);
            return false;
        }
    }

}

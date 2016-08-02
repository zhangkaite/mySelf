package org.jam.dataflow.worker;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jam.dataflow.Dataflow;
import org.jam.dataflow.worker.flow.WritePostDB;
import org.jam.dataflow.worker.retry.RetryPostFailLog;
import org.jam.dataflow.worker.retry.RetryPreFailLog;
import org.jam.dataflow.worker.retry.RetryQueFailLog;
import org.jam.dataflow.worker.status.ModeKeeper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created by James on 16/5/12.
 */
public class WorkerManager {

    private Scheduler scheduler;

    private Integer writepostWorkerInterval;
    private Integer modeKeeperInterval;
    private Integer retryFailInterval;

    private Connection queConnection;
    private ConnectionFactory connectionFactory;


    public WorkerManager() throws Exception{
        writepostWorkerInterval = Integer.valueOf(Dataflow.getProperty("writepost-worker-interval"));
        modeKeeperInterval = Integer.valueOf(Dataflow.getProperty("mode-keeper-interval"));
        retryFailInterval = Integer.valueOf(Dataflow.getProperty("retry-fail-interval"));



        scheduler = new StdSchedulerFactory().getScheduler();
        addWorker(WritePostDB.class, writepostWorkerInterval);
        addWorker(ModeKeeper.class, modeKeeperInterval);
        addWorker(RetryPreFailLog.class, retryFailInterval);
        addWorker(RetryQueFailLog.class, retryFailInterval);
        addWorker(RetryPostFailLog.class, retryFailInterval);

        try{
            String queUrl = Dataflow.getProperty("que-url");
            connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    queUrl);
            queConnection = connectionFactory.createConnection();
            queConnection.start();
        }catch (Exception e){
            Dataflow.logger().error("Failed to connect to que.", e);
        }
    }



    public void addWorker(Class jobClass, int interval) throws Exception{
        JobKey jobKey = new JobKey(jobClass.getName(), "dataflowJob");
        TriggerKey triggerKey = new TriggerKey(jobClass.getName(), "dataflowTrigger");
        if(scheduler.checkExists(triggerKey)){
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(simpleSchedule().withIntervalInMilliseconds(interval).repeatForever()).build();
        //Trigger trigger = TriggerBuilder.newTrigger().withSchedule(cronSchedule("0/5 * * * * ?")).build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void start() throws Exception{
        try {
            if (!scheduler.isShutdown())
                scheduler.start();
        } catch (Exception e) {
            Dataflow.logger().error("Failed to start dataflow workers.", e);
            throw e;
        }
    }

    public void close() throws Exception{
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            Dataflow.logger().error("Failed to close dataflow workers.", e);
            throw e;
        }
    }


    public Connection getQueConnection(){
        return queConnection;
    }

    public void restartQueConnection() throws Exception{
    	try {
    		String queUrl = Dataflow.getProperty("que-url");
    		connectionFactory = new ActiveMQConnectionFactory(
    				ActiveMQConnection.DEFAULT_USER,
    				ActiveMQConnection.DEFAULT_PASSWORD,
    				queUrl);
    		queConnection = connectionFactory.createConnection();
    		queConnection.start();
		} catch (Exception e) {
			Dataflow.logger().error("MQ连接重试失败！！！" ,e);
		}
    }
}

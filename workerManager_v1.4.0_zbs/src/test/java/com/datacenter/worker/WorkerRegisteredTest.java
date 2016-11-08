package com.datacenter.worker;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by zbs on 15/12/30.
 */
public class WorkerRegisteredTest {

    private Scheduler scheduler;
    private WorkerRegistered workerRegistered;
    @Before
    public void before() throws Exception {
        //创建一个Scheduler对象
        scheduler = new StdSchedulerFactory().getScheduler();
        //初始化一个job和调度器放到scheduler中
        addScheduler("testZero3","test",WorkerExample1.class,"0/1 * * * * ?");
        //初始化一个job和调度器放到scheduler中
       // addScheduler("example5","test",WorkerExample2.class,"*/10 * * * * ?");
        //配置zk
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,20,10);
        //得到一个zk连接
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.13.183:50001", retryPolicy);
        //设置zk中的父路径
        String parent = "/com/ttmv/datacenter/worker";
        //构造一个 WorkerRegistered 对象
        workerRegistered = new WorkerRegistered(zkClient,parent,scheduler);
    }

    @Test
    public void test() throws Exception {
        //启动
        workerRegistered.start();
        try {
            //当前线程等待650秒
            Thread.sleep(650L * 1000L);
        } catch (Exception e) {

        }
    }

    /**
     * 把job 和 trigger 组成一对，放入scheduler中
     *
     * @param workerName worker的名字
     * @param groupName 组名
     * @param c  worker具体的调用类，（实现了QuartzWorker的类）
     * @param cron worker的时间设置
     *
     * @throws SchedulerException
     */
    public void addScheduler(String workerName,String groupName,Class c,String cron) throws SchedulerException {
        JobDetail jobDetail =newJob(c).withIdentity(workerName, groupName).usingJobData("processDataBatch", "1").build();
        Trigger trigger = newTrigger().withIdentity(workerName, groupName).withSchedule(cronSchedule(cron)).build();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}

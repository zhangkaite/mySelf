package com.datacenter.dams;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.datacenter.worker.WorkerRegistered;

/**
 * 初始化worker,并执行启动
 * @author wll
 *
 */
public class InitWorker {

	 private Scheduler scheduler;
	 private WorkerRegistered workerRegistered;
	 private HashMap<String,HashMap<String,Object>> map = new	HashMap<String, HashMap<String,Object>>();
	 private String cron;
	 
	 public void init()throws Exception{
		 System.out.println("这是初始化的方法!......................................................................................................");
		 if(map.size() > 0){
			 /* 组名称 */
			 Set<String> groupKeys = map.keySet();
			 for(String groupName : groupKeys){
				 HashMap<String,Object> workerMap = map.get(groupName);
				 if(workerMap !=null && workerMap.size() > 0){
					 /* woker名称*/
					 Set<String> workerNames = workerMap.keySet();
					 for(String workerName : workerNames){
						 Object object = workerMap.get(workerName);
						 /* jobDetail 和 Trigger进行绑定,并放进scheduler中 */
						 this.addScheduler(workerName, groupName, object.getClass(),cron);
					 }
				 }
			 }
		 }
		 workerRegistered.start();
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
	   
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public WorkerRegistered getWorkerRegistered() {
		return workerRegistered;
	}

	public void setWorkerRegistered(WorkerRegistered workerRegistered) {
		this.workerRegistered = workerRegistered;
	}

	public HashMap<String, HashMap<String, Object>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, HashMap<String, Object>> map) {
		this.map = map;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
}

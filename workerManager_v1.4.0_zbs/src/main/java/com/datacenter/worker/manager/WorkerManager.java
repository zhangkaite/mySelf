package com.datacenter.worker.manager;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Exception;
import java.util.List;
import java.util.Map;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class WorkerManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkerManager.class);


    private Scheduler scheduler;
    private JobKey jobKey;

    public WorkerManager(JobKey jobKey, Scheduler scheduler) throws SchedulerException {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
    }

    /**
     * 改变轮循器配置规则,需要重启轮训器
     *
     * @param cron
     * @throws Exception
     */
    public WorkerManager changeCron(String cron) {
        try {
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                if (trigger != null) {
                    TriggerBuilder triggerBuilder = trigger.getTriggerBuilder();
                    Trigger newTrigger;
                    if(cron.split("_").length>1) {
                        newTrigger = triggerBuilder.withSchedule(cronSchedule(cron.trim().replaceAll("_", " "))).build();
                    }else{
                        newTrigger = triggerBuilder.withSchedule(simpleSchedule().withIntervalInMilliseconds(Long.valueOf(cron)).repeatForever()).build();
                    }
                    scheduler.rescheduleJob(trigger.getKey(), newTrigger);
                }
            }
        } catch (Exception e) {
            System.out.println("error : "+e);
            logger.error("修改调度器出错，请查询错误原因。", e);
        }
        return this;
    }

    /**
     * 修改参数，不需要重启轮训器
     *
     * @param map
     * @throws Exception
     */
    public WorkerManager changeAttribute(Map<String, String> map) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jobDataMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            scheduler.addJob(jobDetail, true, true);
        } catch (Exception e) {
            System.out.println("error : "+e);
            logger.error("修改参数出错，请查询错误原因。", e);
        }
        return this;
    }

    /**
     * 重启轮训器
     */
    public void resume() throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }

    /**
     * 停止轮循器
     */
    public void stop() throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }

    /**
     * 开启轮循器
     */
    public void start() throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }

}

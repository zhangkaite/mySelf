package com.datacenter.worker.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.List;


public abstract class QuartzWorkerBatch<T> implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QuartzWorker.class);
    private int processDataBatch;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        try {
            this.processDataBatch = Integer.valueOf(context.getJobDetail().getJobDataMap().getString("processDataBatch"));
            List<T> workerData = getData(processDataBatch);
            logData(workerData);
            logger.info("[QuartzWorker]进入process处理的数据条数为 : "+(workerData==null? 0:workerData.size()));
            if (workerData != null) {
                    try {
                        process(workerData);
                    } catch (Exception e) {
                        logFaild(workerData, e);
                    }
                    logOk(workerData);
            }
        } catch (Exception e) {
            logger.error("[QuartzWorker]本次worker运行出现异常，请查询异常原因。",e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("[QuartzWorker] 本次worker运行总时间 [time]:" + String.valueOf(endTime - startTime) + "毫秒" + " [processDataBatch]:" + processDataBatch);
    }

    private void logData(List<T> workerData) {
        if (workerData != null) {
            for (T d : workerData) {
                logger.debug("[QuartzWorker] 本次Worker处理的数据 : " + toLog(d));
            }
        } else {
            logger.debug("[QuartzWorker] Worker处理的数据为空，请查看是否正常.");
        }
    }

    private void logOk(List<T> list) {
        logger.debug("[QuartzWorker] Worker运行完毕，本次处理的数据 : " + list.toString());
    }

    private void logFaild(List<T> list, Exception e){
        logger.error("[QuartzWorker] 实现类中process方法中有异常。传入数据:" + list.toString(), e);
    }

    /**
     * 获取worker每次处理的一组数据
     *
     * @param processDataBatch
     * @return
     */
    protected abstract List<T> getData(int processDataBatch);

    /**
     * worker处理每条数据逻辑
     *
     * @param list
     * @throws Exception
     */
    protected abstract void process(List<T> list) throws Exception;

    /**
     * 每条数据记录日志的形式
     *
     * @param d
     * @return
     */
    protected abstract String toLog(T d);
}

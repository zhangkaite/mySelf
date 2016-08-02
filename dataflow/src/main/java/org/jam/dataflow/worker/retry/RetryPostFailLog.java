package org.jam.dataflow.worker.retry;

import org.jam.dataflow.Dataflow;
import org.jam.dataflow.log.domain.FailLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by James on 16/5/20.
 */
public class RetryPostFailLog implements Job{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FailLog log = null;
        try {
            log = Dataflow.getDbAccessor().takeFailLog(FailLog.PHASE_WRITEPOSTDB);
            if (null == log){
                return ;
            }
            Dataflow.getWriters().get(log.getBiz()).writePostDB(log.getData());
            Dataflow.getDbAccessor().finishFailLog(log.getId());
        } catch (Exception e) {
            Dataflow.getDbAccessor().returnFailLog(log.getId());
            Dataflow.logger().error("Failed to retry push que.", e);
        }
    }
}

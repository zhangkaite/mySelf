package org.jam.dataflow.worker.retry;

import org.jam.dataflow.Dataflow;
import org.jam.dataflow.DistributedWriteDaoTemplate;
import org.jam.dataflow.domain.DataMode;
import org.jam.dataflow.log.domain.FailLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created by James on 16/5/9.
 */
public class RetryPreFailLog implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FailLog log = null;
        if(Dataflow.getDataMode() == DataMode.RECOVERING){
            try {
                log = Dataflow.getDbAccessor().takeFailLog(FailLog.PHASE_WRITEPREDB);
                if(null == log){
                    Dataflow.setModeRecovered();
                    Dataflow.logger().debug("Finish recovering.");
                    return;
                }
                DistributedWriteDaoTemplate writer = Dataflow.getWriters().get(log.getBiz());
                writer.writePreDB(log.getData());
                Dataflow.getDbAccessor().finishFailLog(log.getId());
                Dataflow.logger().debug("Wrote in pre db with: " + log.getData() + " in retry.");
            } catch (Exception e) {
                try {
                    Dataflow.getDbAccessor().returnFailLog(log.getId());
                } catch (Exception e1) {
                    Dataflow.logger().error("Failed to return faile log: " + log.getId(), e);
                }
            }
        }
    }
}

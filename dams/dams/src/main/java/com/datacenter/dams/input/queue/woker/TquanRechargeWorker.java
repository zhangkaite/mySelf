package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.queue.TquanRechargeQueueService;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * TQ充值worker
 * @author wll
 */
@DisallowConcurrentExecution
public class TquanRechargeWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(TquanRechargeWorker.class);
	private String destinationName = "DAMS_PC_TQRecharge_Queue";
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#TquanRechargeWorker报表TQ充值]开始读取PC队列数据。。");
		TquanRechargeQueueService.receiveMessage(sum, destinationName);
		return null;
	}

	@Override
	protected void process(String message) throws Exception {
		
	}

	@Override
	protected String toLog(String arg0) {
		return null;
	}
}

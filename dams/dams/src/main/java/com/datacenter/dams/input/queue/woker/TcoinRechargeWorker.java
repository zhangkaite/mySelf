package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.queue.TcoinRechargeQueueService;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * TB充值worker
 * @author wll
 */
@DisallowConcurrentExecution
public class TcoinRechargeWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(TcoinRechargeWorker.class);
	private String destinationName = "DAMS_PC_TBRecharge_Queue";
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#TcoinRechargeWorker报表TB充值]开始读取PC队列数据。。");
		TcoinRechargeQueueService.receiveMessage(sum, destinationName);
		return null;
	}

	@Override
	protected void process(String arg0) throws Exception {
		
	}

	@Override
	protected String toLog(String arg0) {
		return null;
	}
}

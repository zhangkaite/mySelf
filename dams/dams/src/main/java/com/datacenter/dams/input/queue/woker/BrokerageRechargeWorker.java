package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.queue.BrokerageRechargeQueueService;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * 佣金兑换业务处理
 * @author wll
 */
@DisallowConcurrentExecution
public class BrokerageRechargeWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(BrokerageRechargeWorker.class);
	private String destinationName = "DAMS_PC_YJRecharge_Queue";
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#BrokerageRechargeWorker佣金兑换]worker开始读取。。");
		BrokerageRechargeQueueService.receiveMessage(sum, destinationName);
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

package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.queue.BrokerageConsumeQueueService;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * 佣金提现业务处理
 * @author wll
 */
@DisallowConcurrentExecution
public class BrokerageConsumeQueueSpendWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(BrokerageConsumeQueueSpendWorker.class);
	private String destinationName = "DAMS_PC_YJConsume_Queue";
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#BrokerageConsumeQueueSpendWorker佣金提现]worker开始读取。。");
		BrokerageConsumeQueueService.receiveMessage(sum, destinationName);
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

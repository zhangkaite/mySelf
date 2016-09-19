package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.queue.TcoinConsumeQueueService;
import com.datacenter.worker.worker.QuartzWorker;


/**
 * TB消费业务处理
 * @author wll
 */
@DisallowConcurrentExecution
public class TcoinConsumeQueueSpendWorker extends QuartzWorker<Object>{
	
	private static Logger logger=LogManager.getLogger(TcoinConsumeQueueSpendWorker.class);
	private String destinationName = "DAMS_PC_TBConsume_Queue";
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getData(int sum) {
		logger.debug("[DAMS#TBConsume#1]worker读取TB消费队列数据并处理.");
		TcoinConsumeQueueService.receiveMessage(sum, destinationName);
		return null;
	}

	@Override
	protected void process(Object object) throws Exception {
	
	}
	
	@Override
	protected String toLog(Object object) {	
		return null;
	}




}

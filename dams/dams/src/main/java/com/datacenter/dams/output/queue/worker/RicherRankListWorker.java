package com.datacenter.dams.output.queue.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.output.RicherRankListRedisQueueOutputDao;
import com.datacenter.worker.worker.QuartzWorker;
import com.google.common.base.Strings;

/**
 * 富豪排行榜 对外推送
 * 
 * @author wll
 */
@DisallowConcurrentExecution
public class RicherRankListWorker extends QuartzWorker<Object> {
	
	private static Logger logger=LogManager.getLogger(RicherRankListWorker.class);
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getData(int sum) {
		logger.debug("[DAMS#RicherRank#1]worker读取富豪排行榜队列数据并处理.");
		/*循环取出消息*/
		List<String> lists = new ArrayList<String>();
		for(int i=0;i<sum;i++){
			try {
				String message = RicherRankListRedisQueueOutputDao.getRedisQueueMessage();
				if(!Strings.isNullOrEmpty(message)){
					logger.debug("[DAMS#RicherRank]worker读取富豪排行榜队列数据.数据是:" + message);
					lists.add(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("[DAMS#RicherRank#1.2]worker读取富豪排行榜队列数据完毕.");
		return lists;
	}


	@Override
	protected void process(Object object) throws Exception {
		logger.debug("[DAMS#RicherRank#2]处理富豪排行榜队列数据.");
		if(object != null){
			String message = object.toString();
			MessageHandlerCenter.getRicherRankListResultCenter().handler(message);
		}
	}

	@Override
	protected String toLog(Object object) {
		return null;
	}
	
	
}

package com.datacenter.dams.output.queue.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.output.FamouserRankListRedisQueueOutputDao;
import com.datacenter.worker.worker.QuartzWorker;
import com.google.common.base.Strings;

/**
 * 明星排行榜 对外推送
 * @author wll
 */
@DisallowConcurrentExecution
public class FamouserRankListWorker extends QuartzWorker<Object>{

	private static Logger logger=LogManager.getLogger(FamouserRankListWorker.class);

	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getData(int sum) {
		logger.debug("[DAMS#FamouserRank#1]worker读取明星排行榜队列数据并处理.");
		/*循环取出消息*/
		List<String> lists = new ArrayList<String>();
		for(int i=0;i<sum;i++){
			try {
				String message = FamouserRankListRedisQueueOutputDao.getRedisQueueMessage();
				if(!Strings.isNullOrEmpty(message)){
					logger.debug("[DAMS#FamouserRank]worker读取明星排行榜队列数据,数据是:"+message);
					lists.add(message);
				}
			} catch (Exception e) {
				logger.error("[DAMS#FamouserRank#ERROR]worker读取明星排行榜队列数据出错.",e);
			}
		}
		logger.debug("[DAMS#FamouserRank#1.2]worker读取明星排行榜队列数据完毕.");
		return lists;
	}


	@Override
	protected void process(Object object) throws Exception {
		logger.debug("[DAMS#FamouserRank#2]处理明星排行榜队列数据.");
		if(object != null){
			String message = object.toString();
			MessageHandlerCenter.getFamouserRankListResultCenter().handler(message);
		}
	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}
	
	public void handler(final String message) throws Exception {
		
	}

}

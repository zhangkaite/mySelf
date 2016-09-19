package com.datacenter.dams.output.queue.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.output.ActivityStarRankListRedisQueueOutputDao;
import com.datacenter.worker.worker.QuartzWorkerBatch;

/**
 * 主播积分排行榜 推送数据到PC系统
 * @author wll
 */
@DisallowConcurrentExecution
public class ActivityStarRankOutputWorker extends QuartzWorkerBatch<String>{
	
	private static Logger logger=LogManager.getLogger(ActivityStarRankOutputWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#ActivityStarRankOutput]worker读取主播吊牌活动排行榜队列数据并处理.");
		if(sum != 0){
			List<String> list = new ArrayList<String>();
			try {
				list = ActivityStarRankListRedisQueueOutputDao.getRedisQueueMessage(sum);
				if(list != null && list.size() > 0){
					logger.info("[主播活动排行榜数据读取入口数据记录]>>总数是 : "+list.size()+" , 数据是 : data="+list.toString());
					return list;
				}
			} catch (Exception e) {
				logger.error("[DAMS#ActivityStarRankOutput#ERROR]worker读取主播吊牌活动排行榜队列数据出错.",e);
			}
			logger.debug("[DAMS#ActivityStarRankOutput]worker读取主播吊牌活动排行榜队列数据完毕.");
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		logger.debug("[DAMS#ActivityStarRankOutput]处理主播吊牌活动排行榜队列数据.");
		if(listStrings != null && listStrings.size() > 0){
			for(String message : listStrings){				
				MessageHandlerCenter.activityRankListResultCenter.handler(message);
			}
		}
	}

	@Override
	protected String toLog(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}

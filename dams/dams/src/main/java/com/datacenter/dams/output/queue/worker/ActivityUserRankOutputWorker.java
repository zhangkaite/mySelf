package com.datacenter.dams.output.queue.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.output.ActivityUserRankListRedisQueueOutputDao;
import com.datacenter.worker.worker.QuartzWorkerBatch;

/**
 * 用户积分排行榜 推送数据到PC系统
 * @author wll
 */
@DisallowConcurrentExecution
public class ActivityUserRankOutputWorker extends QuartzWorkerBatch<String>{
	
	private static Logger logger=LogManager.getLogger(ActivityUserRankOutputWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#ActivityUserRankOutput]worker读取用户吊牌活动排行榜队列数据并处理.");
		if(sum != 0){
			List<String> list = new ArrayList<String>();
			try {
				list = ActivityUserRankListRedisQueueOutputDao.getRedisQueueMessage(sum);
				if(list != null && list.size() >0){
					logger.info("[用户活动排行榜数据读取入口数据记录]>>总数是 : "+sum+", 数据是 : data="+list.toString());
					return list;
				}
			} catch (Exception e) {
				logger.error("[DAMS#ActivityUserRankOutput#ERROR]worker读取用户吊牌活动排行榜队列数据出错.",e);
			}
			logger.debug("[DAMS#ActivityUserRankOutput]worker读取用户吊牌活动排行榜队列数据完毕.");
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		logger.debug("[DAMS#ActivityUserRankOutput]处理用户吊牌活动排行榜队列数据.");
		if(listStrings != null && listStrings.size() > 0){
			for(String message : listStrings){
				MessageHandlerCenter.activityUserRankListResultCenter.handler(message);
			}
		}
	}

	@Override
	protected String toLog(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.queue.ActivityStarRankListQueueDao;

public class ActivityRankListResultCenter {

	private static Logger logger=LogManager.getLogger(ActivityRankListResultCenter.class);
	private ActivityStarRankListQueueDao activityStarRankListQueueDao;
	
	public void handler(Object object)throws Exception{
		logger.debug("[DAMS#ActivityStarRankOutput]业务中心处理主播吊牌活动排行榜队列数据.");
		if(object != null){
			String message = object.toString();
			try{				
				activityStarRankListQueueDao.send(message);
				logger.info("[DAMS#ActivityStarRankOutput]业务中心处理主播吊牌活动排行榜队列数据,推送到SDMS系统队列.数据是:" + message);
			}catch(Exception e){
				logger.info("[DAMS#ActivityStarRankOutput#ERROR]业务中心处理主播吊牌活动排行榜队列数据,推送到SDMS系统队列.数据是:"+message+"!",e);
			}
		}
	}

	public ActivityStarRankListQueueDao getActivityStarRankListQueueDao() {
		return activityStarRankListQueueDao;
	}

	public void setActivityStarRankListQueueDao(
			ActivityStarRankListQueueDao activityStarRankListQueueDao) {
		this.activityStarRankListQueueDao = activityStarRankListQueueDao;
	}
}

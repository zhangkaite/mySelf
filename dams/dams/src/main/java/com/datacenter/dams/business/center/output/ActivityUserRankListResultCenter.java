package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.queue.ActivityUserRankListQueueDao;

public class ActivityUserRankListResultCenter {

	private static Logger logger=LogManager.getLogger(ActivityUserRankListResultCenter.class);
	private ActivityUserRankListQueueDao activityUserRankListQueueDao;
	
	public void handler(Object object)throws Exception{
		logger.debug("[DAMS#ActivityUserRankOutput]业务中心处理用户吊牌活动排行榜队列数据.");
		if(object != null){
			String message = object.toString();
			try{				
				activityUserRankListQueueDao.send(message);
				logger.info("[DAMS#ActivityUserRankOutput]业务中心处理用户吊牌活动排行榜队列数据,推送到SDMS系统队列.数据是:" + message);
			}catch(Exception e){
				logger.info("[DAMS#ActivityUserRankOutput#ERROR]业务中心处理用户吊牌活动排行榜队列数据,推送到SDMS系统队列数据出错.数据是:" + message+"!",e);
			}
		}
	}

	public ActivityUserRankListQueueDao getActivityUserRankListQueueDao() {
		return activityUserRankListQueueDao;
	}

	public void setActivityUserRankListQueueDao(
			ActivityUserRankListQueueDao activityUserRankListQueueDao) {
		this.activityUserRankListQueueDao = activityUserRankListQueueDao;
	}
}

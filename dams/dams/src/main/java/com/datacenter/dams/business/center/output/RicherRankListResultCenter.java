package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.queue.RicherRankListQueueDao;

/**
 * 富豪排行榜 DAMS获取计算结果推送 SDMS
 * @author wll
 */
public class RicherRankListResultCenter{
	
	private static Logger logger=LogManager.getLogger(RicherRankListResultCenter.class);
	private RicherRankListQueueDao richerRankListQueueDao;
	
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#RicherRank]业务中心处理富豪排行榜队列数据.");
		if(object != null){	
			final String message = object.toString();
			if(!"".equals(message)){
				try{					
					richerRankListQueueDao.send(message);
					logger.info("[DAMS#RicherRank]业务中心处理富豪排行榜队列数据,推送到SDMS系统队列.数据是:"+message+"!");
				}catch(Exception e){
					logger.error("[DAMS#RicherRank#ERROR]业务中心处理富豪排行榜队列数据,推送到SDMS系统队列.数据是:"+message+"!",e);
				}
			}
		}
	}

	public RicherRankListQueueDao getRicherRankListQueueDao() {
		return richerRankListQueueDao;
	}

	public void setRicherRankListQueueDao(RicherRankListQueueDao richerRankListQueueDao) {
		this.richerRankListQueueDao = richerRankListQueueDao;
	}
}

package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.queue.FamouserRankListQueueDao;

/**
 * 名人排行榜 计算结果 对外推送中心
 * @author wll
 */
public class FamouserRankListResultCenter{
	
	private static Logger logger=LogManager.getLogger(FamouserRankListResultCenter.class);
	private FamouserRankListQueueDao famouserRankListQueueDao;
	
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#FamouserRank]业务中心处理明星排行榜队列数据.");
		if(object != null){	
			final String message = object.toString();
			if(!"".equals(message)){
				try{					
					famouserRankListQueueDao.send(message);
					logger.info("[DAMS#FamouserRank]业务中心处理明星排行榜队列数据,推送入MQ队列.数据是:"+message+"!");
				}catch(Exception e){
					logger.error("[DAMS#FamouserRank#ERROR]业务中心处理明星排行榜队列数据,推送入MQ队列出错.数据是:"+message+"!",e);
				}
			}
		}
	}

	public FamouserRankListQueueDao getFamouserRankListQueueDao() {
		return famouserRankListQueueDao;
	}

	public void setFamouserRankListQueueDao(
			FamouserRankListQueueDao famouserRankListQueueDao) {
		this.famouserRankListQueueDao = famouserRankListQueueDao;
	}
}

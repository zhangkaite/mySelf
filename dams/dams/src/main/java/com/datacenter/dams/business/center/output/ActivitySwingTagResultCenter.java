package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.output.SwingTagOutputDao;

public class ActivitySwingTagResultCenter {

	private static Logger logger=LogManager.getLogger(FamouserRankListResultCenter.class);
	private SwingTagOutputDao swingTagOutputDao;
	
	public void handler(Object object)throws Exception{
		logger.debug("[DAMS#ActivitySwingTagOutput]业务中心处理吊牌活动信息队列数据.");
		if(object != null){
			String message = object.toString();
			try{
				logger.info("吊牌活动推送=====>>>>" + message);
				swingTagOutputDao.sendMessage(message);
				logger.info("########Swing活动["+message+"],storm计算结果worker推送到吊牌活动时间#########"+System.currentTimeMillis());
			}catch(Exception e){
				logger.info("[DAMS#ActivitySwingTagOutput#ERROR]业务中心处理吊牌活动信息队列数据.推送入外部系统出错.数据是:" + message+"!",e);
			}
		}
	}

	public SwingTagOutputDao getSwingTagOutputDao() {
		return swingTagOutputDao;
	}

	public void setSwingTagOutputDao(SwingTagOutputDao swingTagOutputDao) {
		this.swingTagOutputDao = swingTagOutputDao;
	}
}

package com.datacenter.dams.business.service.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.TquanConsumeSpendActivityRedisQueueBean;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.ActivityInter;
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * T券刷礼物 活动影响
 * @author wll
 */
public class TquanConsumeActivityService implements ActivityInter{

	private static Logger logger=LogManager.getLogger(TquanConsumeActivityService.class);
	private RedisQueueInputDaoInter activityRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TQConsume]TQ消费数据写入storm计算吊牌活动队列.");
		if(object == null){
			return;
		}
		TquanConsumeSpendInfo tquanConsumeInfo = (TquanConsumeSpendInfo)object;
		TquanConsumeSpendActivityRedisQueueBean spend = this.getTquanConsumeSpendActivityRedisQueueBean(tquanConsumeInfo);
		if(spend != null){
			String json = JsonUtil.getObjectToJson(spend);
			try{				
				activityRedisQueueInputDao.sendToRedisQueue(json);
				logger.info("[DAMS#TQConsume]TQ消费数据写入storm计算吊牌活动队列,数据是:"+json);
			}catch(Exception e){
				logger.info("[DAMS#TQConsume#ERROR]TQ消费数据写入storm计算吊牌活动队列出错,数据是:"+json+"!",e);
			}
		}
	}
	
	private TquanConsumeSpendActivityRedisQueueBean getTquanConsumeSpendActivityRedisQueueBean(TquanConsumeSpendInfo tquanConsumeInfo)throws Exception{
		if(tquanConsumeInfo != null){
			TquanConsumeSpendActivityRedisQueueBean spend = new TquanConsumeSpendActivityRedisQueueBean();
			if(tquanConsumeInfo.getRoomID().toString().length() == 9){
				return null;
			}
			if(tquanConsumeInfo.getUserID() != null){
				spend.setSpendId(tquanConsumeInfo.getUserID().toString());
			}
			if(tquanConsumeInfo.getDestinationUserID() != null){
				spend.setSpendToId(tquanConsumeInfo.getDestinationUserID().toString());
			}
			if(tquanConsumeInfo.getNumber() != null){
				spend.setTq(new Float(tquanConsumeInfo.getNumber().toString()));
			}
			if(tquanConsumeInfo.getTime() != null && !"".equals(tquanConsumeInfo.getTime())){
				spend.setTime(Long.valueOf(tquanConsumeInfo.getTime()));
			}
			if(tquanConsumeInfo.getProductID() != null){
				spend.setPresentId(tquanConsumeInfo.getProductID().toString());
			}
			if(tquanConsumeInfo.getProductCount() != null){
				spend.setPresentNum(tquanConsumeInfo.getProductCount().toString());
			}
			return spend;
		}
		return null;
	}
	
	public RedisQueueInputDaoInter getActivityRedisQueueInputDao() {
		return activityRedisQueueInputDao;
	}
	public void setActivityRedisQueueInputDao(
			RedisQueueInputDaoInter activityRedisQueueInputDao) {
		this.activityRedisQueueInputDao = activityRedisQueueInputDao;
	}
}

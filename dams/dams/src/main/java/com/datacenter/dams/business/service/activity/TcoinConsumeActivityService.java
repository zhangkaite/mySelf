package com.datacenter.dams.business.service.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.TcoinConsumeSpendActivityRedisQueueBean;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.ActivityInter;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * TB刷礼物 活动影响
 * @author wll
 */
public class TcoinConsumeActivityService implements ActivityInter{

	private static Logger logger=LogManager.getLogger(TcoinConsumeActivityService.class);
	private RedisQueueInputDaoInter activityRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TBConsume#2.6]TB消费数据写入storm计算吊牌活动队列");
		if(object == null){
			return;
		}
		TcoinConsumeSpendInfo tcoinConsumeInfo = (TcoinConsumeSpendInfo)object;
		TcoinConsumeSpendActivityRedisQueueBean spend = this.getTcoinConsumeSpendActivityRedisQueueBean(tcoinConsumeInfo);
		if(spend != null){
			String json = JsonUtil.getObjectToJson(spend);
			try{				
				activityRedisQueueInputDao.sendToRedisQueue(json);
				logger.info("[DAMS#TBConsume#2.7]TB消费数据写入storm计算吊牌活动队列,数据是:"+json);
			}catch(Exception e){
				logger.info("[DAMS#TBConsume#ERROR]TB消费数据写入storm计算吊牌活动队列出错,数据是:"+json+"!",e);
			}
		}
	}
	
	private TcoinConsumeSpendActivityRedisQueueBean getTcoinConsumeSpendActivityRedisQueueBean(TcoinConsumeSpendInfo tcoinConsumeInfo)throws Exception{
		if(tcoinConsumeInfo != null){
			TcoinConsumeSpendActivityRedisQueueBean spend = new TcoinConsumeSpendActivityRedisQueueBean();
			//手机直播数据丢弃
			if(tcoinConsumeInfo.getRoomID().toString().length() == 9){
				return null;
			}
			if(tcoinConsumeInfo.getUserID() != null){
				spend.setSpendId(tcoinConsumeInfo.getUserID().toString());
			}
			if(tcoinConsumeInfo.getDestinationUserID() != null){
				spend.setSpendToId(tcoinConsumeInfo.getDestinationUserID().toString());
			}
			if(tcoinConsumeInfo.getNumber() != null){
				spend.setTb(new Float(tcoinConsumeInfo.getNumber().toString()));
			}
			if(tcoinConsumeInfo.getTime() != null && !"".equals(tcoinConsumeInfo.getTime())){
				spend.setTime(Long.valueOf(tcoinConsumeInfo.getTime()));
			}
			if(tcoinConsumeInfo.getProductID() != null){
				spend.setPresentId(tcoinConsumeInfo.getProductID().toString());
			}
			if(tcoinConsumeInfo.getProductCount() != null){
				spend.setPresentNum(Integer.parseInt(tcoinConsumeInfo.getProductCount().toString()));
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

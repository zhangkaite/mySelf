package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.input.redis.entity.FlowerAndHeartTempInfo;
import com.datacenter.dams.input.redis.entity.TakeHeartSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 送心 业务中心
 * @author wll
 */
public class TakeHeartConsumeCenter {

	private static Logger logger=LogManager.getLogger(TakeHeartConsumeCenter.class);
	
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		TakeHeartSpendInfo info = (TakeHeartSpendInfo)object;
		if(!this.checkObject(info)){
			return ;
		}
		FlowerAndHeartTempInfo fh = this.getFlowerAndHeartTempInfoFromData(info);
		if(fh != null){
			String jsonRedis = JsonUtil.getObjectToJson(fh);
			ExpRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.TAKEHEART_REDISQUEUE_INNER_INPUT, jsonRedis);
			logger.info("[DAMS送心TakeHeartConsumeCenter]送心数据发送到Storm计算队列。" + jsonRedis);
		}
	}
	
	/**
	 * 检查对象
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public boolean checkObject(TakeHeartSpendInfo info)throws Exception{
		boolean flag = true;
		if(info.getUserID() == null){
			flag = false;
		}
		if(info.getDestinationUserID() == null){
			flag = false;
		}
		if(info.getCount() == null){
			flag = false;
		}
		if(info.getRoomID() == null){
			flag = false;
		}
		if(Strings.isNullOrEmpty(info.getClientType())){
			flag = false;
		}
		if(Strings.isNullOrEmpty(info.getTime())){
			flag = false;
		}
		if(Strings.isNullOrEmpty(info.getType())){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 对象转换
	 * @param info
	 * @return
	 */
	private FlowerAndHeartTempInfo getFlowerAndHeartTempInfoFromData(TakeHeartSpendInfo info){
		if(info != null){			
			FlowerAndHeartTempInfo fh = new FlowerAndHeartTempInfo();
			fh.setSpendId(info.getUserID());
			fh.setSpendToId(info.getDestinationUserID());
			fh.setSum(info.getCount());
			fh.setTime(new Long(info.getTime()));
			return fh;
		}
		return null;
	}
}

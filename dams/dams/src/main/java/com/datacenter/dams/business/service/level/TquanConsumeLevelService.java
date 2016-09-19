package com.datacenter.dams.business.service.level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.UserOnlineInExpBean;
import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.business.service.LevelInter;
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * T券刷礼物 等级影响
 * @author wll
 */
public class TquanConsumeLevelService implements LevelInter{

	private static Logger logger=LogManager.getLogger(TcoinConsumeLevelService.class);
	
	@Override
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		/* 获取TQ用户消费的数据 */
		TquanConsumeSpendInfo tquan = (TquanConsumeSpendInfo)object;
		UserOnlineInExpBean tquanRedis = getUserOnlineInExpBeanFromData(tquan);
		/*分离数据放入redis队列,提供storm计算使用*/
		if(tquanRedis != null){
			String redisJson = JsonUtil.getObjectToJson(tquanRedis);
			try{				
				ExpRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.CONSUME_REDISQUEUE_INNER_INPUT, redisJson);
				logger.info("[DAMS#TQConsume]TQ消费数据写入storm计算【等级】队列,数据是:"+redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TQConsume#ERROR]TQ消费数据写入storm计算排【等级】列出错,数据是:"+redisJson+"!",e);
			}
		}
	}

	/**
	 * 对象转换
	 * @param tcoin
	 * @return
	 * @throws Exception
	 */
	private UserOnlineInExpBean getUserOnlineInExpBeanFromData(TquanConsumeSpendInfo tquan)throws Exception{
		if(tquan != null){			
			UserOnlineInExpBean spend = new UserOnlineInExpBean();
			if(tquan.getUserID() != null){
				spend.setSpendId(tquan.getUserID().toString());
			}
			if(tquan.getDestinationUserID() != null){
				spend.setSpendToId(tquan.getDestinationUserID().toString());
			}
			if(tquan.getNumber() != null){
				spend.setTq(new Float(tquan.getNumber().toString()));
			}
			if(!Strings.isNullOrEmpty(tquan.getTime())){
				spend.setTime(new Long(tquan.getTime()));
			}
			return spend;
		}
		return null;
	}
}

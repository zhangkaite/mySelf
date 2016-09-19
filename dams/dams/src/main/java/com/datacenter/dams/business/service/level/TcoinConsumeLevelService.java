package com.datacenter.dams.business.service.level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.UserOnlineInExpBean;
import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.business.service.LevelInter;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * TB刷礼物 等级影响
 * @author wll
 *
 */
public class TcoinConsumeLevelService implements LevelInter{

	private static Logger logger=LogManager.getLogger(TcoinConsumeLevelService.class);
	
	@Override
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		/* 获取TB用户消费的数据 */
		TcoinConsumeSpendInfo tcoin = (TcoinConsumeSpendInfo)object;
		UserOnlineInExpBean tcoinRedis = getUserOnlineInExpBeanFromData(tcoin);
		/*分离数据放入redis队列,提供storm计算使用*/
		if(tcoinRedis != null){
			String redisJson = JsonUtil.getObjectToJson(tcoinRedis);
			try{				
				ExpRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.CONSUME_REDISQUEUE_INNER_INPUT, redisJson);
				logger.info("[DAMS#TBConsume]TB消费数据写入storm计算【等级】队列,数据是:"+redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TBConsume#ERROR]TB消费数据写入storm计算排【等级】列出错,数据是:"+redisJson+"!",e);
			}
		}
	}

	/**
	 * 对象转换
	 * @param tcoin
	 * @return
	 * @throws Exception
	 */
	private UserOnlineInExpBean getUserOnlineInExpBeanFromData(TcoinConsumeSpendInfo tcoin)throws Exception{
		if(tcoin != null){			
			UserOnlineInExpBean spend = new UserOnlineInExpBean();
			if(tcoin.getUserID() != null){
				spend.setSpendId(tcoin.getUserID().toString());
			}
			if(tcoin.getDestinationUserID() != null){
				spend.setSpendToId(tcoin.getDestinationUserID().toString());
			}
			if(tcoin.getNumber() != null){
				spend.setTb(new Float(tcoin.getNumber().toString()));
			}
			if(!Strings.isNullOrEmpty(tcoin.getTime())){
				spend.setTime(new Long(tcoin.getTime()));
			}
			return spend;
		}
		return null;
	}
}

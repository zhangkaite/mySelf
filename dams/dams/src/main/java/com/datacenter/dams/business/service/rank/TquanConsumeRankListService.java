package com.datacenter.dams.business.service.rank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.TquanConsumeSpendRedisQueue;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.RankInter;
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * T券消费 排行榜影响
 * @author wll
 */
public class TquanConsumeRankListService implements RankInter{

	private static Logger logger=LogManager.getLogger(TquanConsumeRankListService.class);
	private RedisQueueInputDaoInter tquanConsumeRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TQConsume]TQ消费数据写入storm计算排行榜队列.");
		if(object == null){
			return ;
		}
		/* 获取TB用户消费的数据 */
		TquanConsumeSpendInfo tquan = (TquanConsumeSpendInfo)object;
		TquanConsumeSpendRedisQueue tcoinRedis = getTquanConsumeRedisQueueStormFromData(tquan);
		/*分离数据放入redis队列,提供storm计算使用*/
		if(tcoinRedis != null){
			String redisJson = JsonUtil.getObjectToJson(tcoinRedis);
			try{				
				tquanConsumeRedisQueueInputDao.sendToRedisQueue(redisJson);
				logger.info("[DAMS#TQConsumeTQ消费数据写入storm计算排行榜队列,数据是:" + redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TBConsume#ERROR]TQ消费数据写入storm计算排行榜队列出错,数据是:"+redisJson+"!",e);
			}
		}
	}

	/**
	 * 对象转换
	 * @param tcoin
	 * @return
	 * @throws Exception
	 */
	private TquanConsumeSpendRedisQueue getTquanConsumeRedisQueueStormFromData(TquanConsumeSpendInfo tquan)throws Exception{
		if(tquan != null){			
			TquanConsumeSpendRedisQueue spend = new TquanConsumeSpendRedisQueue();
			if(tquan.getUserID() != null){
				spend.setSpendId(tquan.getUserID().toString());
			}
			if(tquan.getDestinationUserID() != null){
				spend.setSpendToId(tquan.getDestinationUserID().toString());
			}
			if(tquan.getNumber() != null){
				spend.setTq(new Float(tquan.getNumber().toString()));
			}
			if(tquan.getTime() != null && !"".equals(tquan.getTime())){
				spend.setTime(Long.valueOf(tquan.getTime()));
			}
			return spend;
		}
		return null;
	}
	
	public RedisQueueInputDaoInter getTquanConsumeRedisQueueInputDao() {
		return tquanConsumeRedisQueueInputDao;
	}

	public void setTquanConsumeRedisQueueInputDao(
			RedisQueueInputDaoInter tquanConsumeRedisQueueInputDao) {
		this.tquanConsumeRedisQueueInputDao = tquanConsumeRedisQueueInputDao;
	}
}

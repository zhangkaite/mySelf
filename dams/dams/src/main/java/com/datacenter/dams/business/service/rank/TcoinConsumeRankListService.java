package com.datacenter.dams.business.service.rank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.bean.TcoinConsumeSpendRedisQueue;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.RankInter;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * TB消费 排行榜业务分拣
 * @author wll
 */
public class TcoinConsumeRankListService implements RankInter{
	
	private static Logger logger=LogManager.getLogger(TcoinConsumeRankListService.class);
	private RedisQueueInputDaoInter tcoinConsumeRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TBConsume]TB消费数据写入storm计算排行榜队列.");
		if(object == null){
			return ;
		}
		/* 获取TB用户消费的数据 */
		TcoinConsumeSpendInfo tcoin = (TcoinConsumeSpendInfo)object;
		TcoinConsumeSpendRedisQueue tcoinRedis = getTcoinConsumeRedisQueueStormFromData(tcoin);
		/*分离数据放入redis队列,提供storm计算使用*/
		if(tcoinRedis != null){
			String redisJson = JsonUtil.getObjectToJson(tcoinRedis);
			try{				
				tcoinConsumeRedisQueueInputDao.sendToRedisQueue(redisJson);
				logger.info("########TB消费worker写入排行榜队列["+redisJson+"]#########"+System.currentTimeMillis());
				logger.info("[DAMS#TBConsume]TB消费数据写入storm计算排行榜队列,数据是:"+redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TBConsume#ERROR]TB消费数据写入storm计算排行榜队列出错,数据是:"+redisJson+"!",e);
			}
		}
	}
	
	/**
	 * 对象转换
	 * @param tcoin
	 * @return
	 * @throws Exception
	 */
	private TcoinConsumeSpendRedisQueue getTcoinConsumeRedisQueueStormFromData(TcoinConsumeSpendInfo tcoin)throws Exception{
		if(tcoin != null){			
			TcoinConsumeSpendRedisQueue spend = new TcoinConsumeSpendRedisQueue();
			if(tcoin.getUserID() != null){
				spend.setSpendId(tcoin.getUserID().toString());
			}
			if(tcoin.getDestinationUserID() != null){
				spend.setSpendToId(tcoin.getDestinationUserID().toString());
			}
			if(tcoin.getNumber() != null){
				spend.setTb(new Float(tcoin.getNumber().toString()));
			}
			if(tcoin.getTime() != null && !"".equals(tcoin.getTime())){
				spend.setTime(Long.valueOf(tcoin.getTime()));
			}
			return spend;
		}
		return null;
	}

	public RedisQueueInputDaoInter getTcoinConsumeRedisQueueInputDao() {
		return tcoinConsumeRedisQueueInputDao;
	}

	public void setTcoinConsumeRedisQueueInputDao(
			RedisQueueInputDaoInter tcoinConsumeRedisQueueInputDao) {
		this.tcoinConsumeRedisQueueInputDao = tcoinConsumeRedisQueueInputDao;
	}
}

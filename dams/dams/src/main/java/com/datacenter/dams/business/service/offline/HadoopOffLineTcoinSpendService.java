package com.datacenter.dams.business.service.offline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.datacenter.dams.business.dao.redis.bean.hdfs.TcoinConsumeSpendRedisQueueToHdfs;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.OffLineInter;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * 将消费的原始的数据放入 hadoop离线计算队列
 * @author wll
 *
 */
public class HadoopOffLineTcoinSpendService implements OffLineInter{

	private static Logger logger=LogManager.getLogger(HadoopOffLineTcoinSpendService.class);
	private RedisQueueInputDaoInter offLineRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TBConsume]TB消费数据写入hadoop离线计算队列.");
		if(object == null){
			return ;
		}
		/* 获取TB用户消费的数据 */
		TcoinConsumeSpendInfo tcoin = (TcoinConsumeSpendInfo)object;
		TcoinConsumeSpendRedisQueueToHdfs hdfs = this.getTcoinConsumeSpendRedisQueueToHdfs(tcoin);
		if(hdfs != null){
			String redisJson = JsonUtil.getObjectToJson(hdfs);
			try{				
				offLineRedisQueueInputDao.sendToRedisQueue(redisJson);			
				logger.info("[DAMS#TBConsume]TB消费数据写入hadoop离线计算队列,数据是:"+redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TBConsume#ERROR]TB消费数据写入hadoop离线计算队列出错,数据是:"+redisJson+"!",e);
			}
		}
	}

	/**
	 * 转换TcoinConsumeSpendInfo为TcoinConsumeSpendRedisQueueToHdfs
	 * @param tcoin
	 * @return
	 */
	private TcoinConsumeSpendRedisQueueToHdfs getTcoinConsumeSpendRedisQueueToHdfs(TcoinConsumeSpendInfo tcoin){
		if(tcoin != null){
			TcoinConsumeSpendRedisQueueToHdfs hdfs = new TcoinConsumeSpendRedisQueueToHdfs();
			BeanUtils.copyProperties(tcoin, hdfs);
			/* 添加自定义的字段的值 */
			if(tcoin.getUserID() != null){
				hdfs.setSpendId(tcoin.getUserID().toString());
			}
			if(tcoin.getDestinationUserID() != null){
				hdfs.setSpendToId(tcoin.getDestinationUserID().toString());
			}
			if(tcoin.getNumber() != null){
				hdfs.setTb(new Float(tcoin.getNumber().toString()));
			}
			if(tcoin.getTime() != null && !"".equals(tcoin.getTime())){
				hdfs.setTime(Long.valueOf(tcoin.getTime()));
			}
			return hdfs;
		}
		return null;
	}
	
	public RedisQueueInputDaoInter getOffLineRedisQueueInputDao() {
		return offLineRedisQueueInputDao;
	}

	public void setOffLineRedisQueueInputDao(
			RedisQueueInputDaoInter offLineRedisQueueInputDao) {
		this.offLineRedisQueueInputDao = offLineRedisQueueInputDao;
	}
}

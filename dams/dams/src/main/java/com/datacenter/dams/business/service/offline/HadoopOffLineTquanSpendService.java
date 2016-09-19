package com.datacenter.dams.business.service.offline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.datacenter.dams.business.dao.redis.bean.hdfs.TquanConsumeSpendRedisQueueToHdfs;
import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDaoInter;
import com.datacenter.dams.business.service.OffLineInter;
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.util.JsonUtil;

/**
 * 将消费的原始的数据放入 hadoop离线计算队列
 * @author wll
 *
 */
public class HadoopOffLineTquanSpendService implements OffLineInter{

	private static Logger logger=LogManager.getLogger(HadoopOffLineTquanSpendService.class);
	private RedisQueueInputDaoInter offLineRedisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		logger.debug("[DAMS#TQConsume]TQ消费数据写入hadoop离线计算队列.");
		if(object == null){
			return ;
		}
		/* 获取TB用户消费的数据 */
		TquanConsumeSpendInfo tquan = (TquanConsumeSpendInfo)object;
		TquanConsumeSpendRedisQueueToHdfs hdfs = this.getTquanConsumeSpendRedisQueueToHdfs(tquan);
		if(hdfs != null){
			String redisJson = JsonUtil.getObjectToJson(hdfs);
			try{				
				offLineRedisQueueInputDao.sendToRedisQueue(redisJson);		
				logger.info("[DAMS#TQConsume]TQ消费数据写入hadoop离线计算队列,数据是:" + redisJson);
			}catch(Exception e){
				logger.info("[DAMS#TQConsume#ERROR]TQ消费数据写入hadoop离线计算队列出错,数据是:"+redisJson+"!",e);
			}
		}
	}

	/**
	 * 转换TcoinConsumeSpendInfo为TcoinConsumeSpendRedisQueueToHdfs
	 * @param tcoin
	 * @return
	 */
	private TquanConsumeSpendRedisQueueToHdfs getTquanConsumeSpendRedisQueueToHdfs(TquanConsumeSpendInfo tquan){
		if(tquan != null){
			TquanConsumeSpendRedisQueueToHdfs hdfs = new TquanConsumeSpendRedisQueueToHdfs();
			BeanUtils.copyProperties(tquan, hdfs);
			/* 添加自定义的字段的值 */
			if(tquan.getUserID() != null){
				hdfs.setSpendId(tquan.getUserID().toString());
			}
			if(tquan.getDestinationUserID() != null){
				hdfs.setSpendToId(tquan.getDestinationUserID().toString());
			}
			if(tquan.getNumber() != null){
				hdfs.setTq(new Float(tquan.getNumber().toString()));
			}
			if(tquan.getTime() != null && !"".equals(tquan.getTime())){
				hdfs.setTime(Long.valueOf(tquan.getTime()));
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

package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.input.redis.entity.OnlineTimeSpendInfo;
import com.datacenter.dams.util.ConsumeSpendConstant;

/**
 * 明星视频在线麦时，数据处理中心
 * @author wulinli
 *
 */
public class StarOnlineSpendCenter {

	private static Logger logger=LogManager.getLogger(StarOnlineSpendCenter.class);
	
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		OnlineTimeSpendInfo info = (OnlineTimeSpendInfo)object;
		this.dealOnlineTimeSpendInfo(info);
		logger.info("[DAMS明星麦时登陆StarOnlineSpendCenter]中心处理数据成功！");
	}
	
	/**
	 * 处理 OnlineTimeSpendInfo
	 * 如果登陆类型是：up 则数据添加到数据库
	 * 如果登陆类型是：down 则删除数据库中对应的id
	 * @param info
	 */
	private void dealOnlineTimeSpendInfo(OnlineTimeSpendInfo info)throws Exception{
		if(info != null){
			String type = info.getType();
			if(type.equals(ConsumeSpendConstant.STAR_UP)){
				String userId = info.getUserID().toString();
				ExpRedisQueueDao.sendSetMessage(RedisQueueUtil.DAMS_STAR_ONLINE_QUEUE,userId);
				logger.info("[DAMS明星麦时登陆StarOnlineSpendCenter]中心处理数据成功！set集合添加数据："+userId);
			}else if(type.equals(ConsumeSpendConstant.STAR_DOWN)){
				String userId = info.getUserID().toString();
				ExpRedisQueueDao.removeSetMessage(RedisQueueUtil.DAMS_STAR_ONLINE_QUEUE,userId);
				logger.info("[DAMS明星麦时登陆StarOnlineSpendCenter]中心处理数据成功！set集合删除数据："+userId);
			}
		}
	}
}

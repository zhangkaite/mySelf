package com.ttmv.service.inform.redisqueue;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.web.controller.util.JsonUtil;

/**
 * 会员续费提醒通知消息
 * @author Damon
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("userVipInformQ")
public class UserVipInformQ {
	private static Logger logger = LogManager.getLogger(UserVipInformQ.class);
	
	@Autowired
	private RedisQueueImpi jedisAgentPhpQueue;
	@Autowired
	private RedisQueueImpi jedisAgentImQueue;
	
	public void excute(UserInfoBean userInfoBean){
		logger.debug("vip 向redis队列通知");
		String resJson = "";
		try {
			resJson = this.getReqJson(userInfoBean);
		} catch (Exception e) {
			logger.error("组装json数据异常",e);
		}
		// php redis队列接口
		try {
			jedisAgentPhpQueue.setValue(OverdueConstant.PHP_QUEUENAME, resJson);
		} catch (Exception e) {
			logger.error("redis队列写入失败",e);
		}
		//im队列
		try {
			jedisAgentImQueue.setValue(OverdueConstant.IMSHOW_QUEUENAME, resJson);
		} catch (Exception e) {
			logger.error("redis队列写入失败 _"+OverdueConstant.IMSHOW_QUEUENAME,e);
		}
		logger.info("VIP会员未到期预提醒IM、php redis队列成功,通知内容:"+resJson);
	}
	
	private String getReqJson(UserInfoBean userInfoBean) throws Exception{
		Map date = new  HashMap();
		Map content = new  HashMap();
		date.put("cmd", "vipER");
		date.put("userid", userInfoBean.getUserid());
			content.put("nickName", userInfoBean.getNickName());
		 	content.put("TTnum", userInfoBean.getTTnum());
		 	content.put("endTime",userInfoBean.getVipEndTime());
		date.put("content", content);
		return JsonUtil.getObjectToJson(date);
	}
}

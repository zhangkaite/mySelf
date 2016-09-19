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
 * 豪车续费提醒
 * 
 * @author Damon
 * @time 2015年11月18日10:14:12
 */
@Service("luxuryCarInformQ")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LuxuryCarInformQ {

	private static Logger logger = LogManager.getLogger(LuxuryCarInformQ.class);
	@Autowired
	private RedisQueueImpi jedisAgentPhpQueue;
	@Autowired
	private RedisQueueImpi jedisAgentImQueue;

	public void excute(UserInfoBean userInfoBean) {
		String json = "";
		try {
			json = this.getReqJson(userInfoBean);
		} catch (Exception e) {
			logger.error("组装json数据异常", e);
		}
		// TODO redis队列接口 PHP_QUEUENAME
		try {
			jedisAgentPhpQueue.setValue(OverdueConstant.PHP_QUEUENAME, json);
		} catch (Exception e) {
			logger.error("redis队列写入失败 _" + OverdueConstant.PHP_QUEUENAME, e);
		}

		// TODO redis队列接口 IMSHOW_QUEUENAME
		try {
			jedisAgentImQueue.setValue(OverdueConstant.IMSHOW_QUEUENAME, json);
		} catch (Exception e) {
			logger.error("redis队列写入失败 _" + OverdueConstant.IMSHOW_QUEUENAME, e);
		}
		logger.info("豪车未到期预提醒IM、php redis队列成功,通知内容:"+json);
	}

	private String getReqJson(UserInfoBean userInfoBean) throws Exception {
		Map date = new HashMap();
		Map content = new HashMap();
		date.put("cmd", "carER");
		date.put("userid", userInfoBean.getUserid());
		content.put("nickName", userInfoBean.getNickName());
		content.put("TTnum", userInfoBean.getTTnum());
		content.put("carID", userInfoBean.getLuxuryCarID());
		content.put("endTime", userInfoBean.getLuxuryCarEndTime());
		date.put("content", content);
		return JsonUtil.getObjectToJson(date);
	}


}

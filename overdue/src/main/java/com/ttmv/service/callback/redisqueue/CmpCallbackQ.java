package com.ttmv.service.callback.redisqueue;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.web.controller.util.JsonUtil;

/**
 * 频道解冻回调
 * 
 * @author Damon
 * @time 2015年11月18日10:14:12
 */
@Service("cmpCallbackQ")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmpCallbackQ {

	private static Logger logger = LogManager.getLogger(CmpCallbackQ.class);
	@Autowired
	private RedisQueueImpi jedisAgentPhpQueue;
	@Autowired
	private RedisQueueImpi jedisAgentImQueue;

	public void excute(Cmp cmp) {
		
		String resJson = "";
		try {
			resJson = this.getReqJson(cmp);
		} catch (Exception e) {
			logger.error("金色弹窗开始/结束组装json数据异常", e);
		}
		// php redis队列接口
		try {
			jedisAgentPhpQueue.setValue(OverdueConstant.PHP_QUEUENAME, resJson);
			logger.debug("金色弹窗开始/结束php redis队列通知,通知的数据："+resJson);
		} catch (Exception e) {
			logger.error("金色弹窗开始/结束redis队列写入" + OverdueConstant.PHP_QUEUENAME + "失败", e);
		}
		// im队列
		try {
			jedisAgentImQueue.setValue(OverdueConstant.IMSHOW_QUEUENAME, resJson);
			logger.debug("金色弹窗开始/结束IM redis队列通知,通知的数据："+resJson);
		} catch (Exception e) {
			logger.error("金色弹窗开始/结束redis队列写入失败 _" + OverdueConstant.IMSHOW_QUEUENAME, e);
		}
		logger.info("金色弹窗开始/结束IM、php redis队列通知,通知的数据："+resJson);
	}

	private String getReqJson(Cmp cmp) throws Exception {
		Map data = new HashMap();
		if (cmp.getTag().equals(Constant.STARTCMP)) {
			data.put("cmd", "StartCMP");
		} else {
			data.put("cmd", "EndCMP");
		}
		data.put("viewType", Integer.valueOf(cmp.getType()));
		data.put("userid", cmp.getUserId().toString());
		return JsonUtil.getObjectToJson(data);
	}

}

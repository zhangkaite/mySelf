package com.ttmv.service.callback.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttmv.datacenter.sentry.handle.QuickSentry;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.web.controller.util.JsonUtil;


@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("userStateCallBack")
public class UserStateCallBack {
	
	private static Logger logger = LogManager.getLogger(UserStateCallBack.class);
	@Autowired
	private QuickSentry ucSentryAgentUstate;

	/**
	 * http请求，回调用户中心解冻用户
	 * @param reqMap
	 * @return
	 */
	public String excute(String userID,String endTime){
		logger.info("http请求，回调用户中心解冻用户userID:"+userID+";endTime:"+endTime);
		try {
			logger.info("回调用户中心解冻请求数据:"+this.getReqJson(userID,endTime));
			String resData = ucSentryAgentUstate.expressSendHttp("data="+this.getReqJson(userID,endTime));
			logger.info("数据中心返回[回调用户中心解冻]结果:" + resData);
			return resData;
		} catch (Exception e) {
			logger.error("调用用户中心接口失败！！！",e);
			return "Error500";
		}
		
	}
	
	/**
	 * 拼用户中心接口
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	private String getReqJson(String userID,String endTime) throws Exception{
		Map date = new  HashMap();
		Map reqData = new HashMap();
		date.put("service", "removeForbiddenUser");
		date.put("reqID", "overdue_"+System.currentTimeMillis());
		date.put("platfrom", "overdue");
		date.put("timeStamp", System.currentTimeMillis());
		date.put("tradeType", 2);
			reqData.put("userID", userID);
			reqData.put("type", OverdueConstant.USER_STATE_NORMAL);
		date.put("reqData", reqData);
		return JsonUtil.getObjectToJson(date);
	}
}

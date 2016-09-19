package com.ttmv.service.callback.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttmv.datacenter.sentry.handle.QuickSentry;
import com.ttmv.web.controller.util.JsonUtil;

/**
 * 回调用户中心接口，获取用户资料
 * @author Damon
 * @time 2015年11月18日10:50:02
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("getUserInfo")
public class GetUserInfo {
	private static Logger logger = LogManager.getLogger(GetUserInfo.class);
	
	@Autowired
	private QuickSentry ucSentryAgentGetUserInfo;
	
	
	/**
	 * http请求，回调用户中心查询用户资料
	 * @param reqMap
	 * @return
	 */
	public String excute(String userID){
		try {
			logger.info("http请求，回调用户中心查询用户资料userID:"+userID);
			String resData = ucSentryAgentGetUserInfo.expressSendHttp("data="+this.getReqJson(userID));
			logger.debug("数据中心返回[用户中心查询用户资料]结果:" + resData);
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
	private String getReqJson(String userID) throws Exception{
		Map date = new  HashMap();
		Map reqData = new HashMap();
		date.put("service", "queryUserById");
		date.put("reqID", "overdue_"+System.currentTimeMillis());
		date.put("platfrom", "overdue");
		date.put("timeStamp", System.currentTimeMillis());
		date.put("tradeType", 2);
		reqData.put("userID", userID);//获取用户资料
		date.put("reqData", reqData);
		return JsonUtil.getObjectToJson(date);
	}
	

}

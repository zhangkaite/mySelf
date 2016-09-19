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

/**
 * 回调用户中心，修改vip状态为到期
 * @author Damon
 * 2015年11月20日16:03:36
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("vipCallBack")
public class VipCallBack {
	private static Logger logger = LogManager.getLogger(VipCallBack.class);
	@Autowired
	private QuickSentry ucSentryAgentVIP;
	
	/**
	 * http请求，回调用户中心修改用户vip状态为非会员
	 * @param reqMap
	 * @return
	 */
	public String excute(String userID,String endTime){
		logger.info("http请求，回调用户中心修改用户vip状态为非会员开始userID:"+userID+";endTime:"+endTime);
		try {
			String resData = ucSentryAgentVIP.expressSendHttp("data="+this.getReqJson(userID,endTime));
			logger.info("数据中心返回[修改用户vip状态]结果:" + resData);
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
		date.put("service", "vipCallBack");
		date.put("reqID", "overdue_"+System.currentTimeMillis());
		date.put("platfrom", "overdue");
		date.put("timeStamp", System.currentTimeMillis());
		date.put("tradeType", 2);
			reqData.put("userID", userID);//普通用户资料修改
			reqData.put("vipType", OverdueConstant.VIP_NO);
			reqData.put("endTime", endTime);
		date.put("reqData", reqData);
		return JsonUtil.getObjectToJson(date);
	}


	
	
	

}

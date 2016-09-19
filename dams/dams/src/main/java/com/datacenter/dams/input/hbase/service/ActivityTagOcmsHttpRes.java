package com.datacenter.dams.input.hbase.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hbase.ActivityTagConstant;
import com.datacenter.dams.util.JsonUtil;
import com.ttmv.datacenter.sentry.handle.QuickSentry;

/**
 * 
 * 吊牌活动请求ocms查询当前活动的所有活动ID
 * @author kate
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActivityTagOcmsHttpRes {
	
	public static final Logger logger = LogManager.getLogger(ActivityTagOcmsHttpRes.class);
	
	public QuickSentry anchorActivitySentry;

	public QuickSentry getAnchorActivitySentry() {
		return anchorActivitySentry;
	}

	public void setAnchorActivitySentry(QuickSentry anchorActivitySentry) {
		this.anchorActivitySentry = anchorActivitySentry;
	}
	
	public String excute(Map reqMap) throws Exception {
		try {
		//	logger.debug("向ocms发送查询正在运行的活动ID http请求，请求的数据："+this.getReqJson(reqMap));
			return anchorActivitySentry.expressSendHttp("data=" + this.getReqJson(reqMap));
		} catch (Exception e) {
			logger.error("调用OCMS系统接口失败！！！", e);
			return "Error500";
		}
	}
	
	
	
	private String getReqJson(Map reqMap) throws Exception {
		Map dataMap = new HashMap();
		Map reqDataMap = new HashMap();
		dataMap.put("time", new Date().getTime());
		dataMap.put("service", ActivityTagConstant.REQOCMSSERVICE);
		dataMap.put("reqID", ActivityTagConstant.DAMS + "_" + System.currentTimeMillis());
		reqDataMap.put("reqData", dataMap);
		return JsonUtil.getObjectToJson(reqDataMap);
	}
	
	
	
	
	
	
	
	
	
	

}

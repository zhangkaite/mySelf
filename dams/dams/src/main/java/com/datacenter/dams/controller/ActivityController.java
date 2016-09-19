package com.datacenter.dams.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datacenter.dams.business.service.activity.StarInfoActivityService;
import com.datacenter.dams.util.ErrorCodeConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.dams.util.ResponseTool;
import com.datacenter.domain.protocol.da.StarActivityInfo;
import com.google.common.base.Strings;
import com.ttmv.datacenter.sentry.SentryAgent;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

@Controller
public class ActivityController {
	
	private static Logger logger = LogManager.getLogger(ActivityController.class);

	private StarInfoActivityService starInfoActivityService;
	private SentryAgent quickSentry;
	private SentryAgent checkUserIdQuickSentry;

	/**
	 * 主播吊牌活动信息查询
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes"})
	@RequestMapping(value = "/queryStarInfoActivity", method = RequestMethod.POST)
	public @ResponseBody String queryStarInfoActivity(@ModelAttribute("data") String data)throws Exception {
		logger.info("[DAMS#ActivityController]查询系统吊牌活动的参数是:" + data);
		Map resultMap = null;
		if (Strings.isNullOrEmpty(data)) {
			resultMap = ResponseTool.returnError(ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE, "[DAMS#ActivityController#ERROR]参数为空!");
			String resultJson = JsonUtil.getObjectToJson(resultMap);
			return resultJson;
		}
		Map map = (Map) JsonUtil.getObjectFromJson(data, Map.class);
		Map result_Map=(Map) map.get("reqData");
		if (!checkData(result_Map)) {
			resultMap = ResponseTool.returnError(ErrorCodeConstant.ERRORMSG_DBERROR_CODE, "[DAMS#ActivityController#ERROR]参数异常!");
			String resultJson = JsonUtil.getObjectToJson(resultMap);
			return resultJson;
		} 		
		String userId = result_Map.get("userID").toString();
		String activityId = result_Map.get("activityID").toString();
		/* 用户ID是否在活动中 */
		String checkUserID_params = "data={\"service\":\"queryStartActivity\",\"reqData\":{\"userID\":"+userId+",\"roomID\":0}}";
		String checkUserIDJson = checkUserIdQuickSentry.expressSendHttp(checkUserID_params);
		if(!Strings.isNullOrEmpty(checkUserIDJson)){
			JSONObject jsonObject = JSONObject.fromObject(checkUserIDJson);
			Object _activityId = jsonObject.get("resData");
			if(_activityId instanceof JSONNull){
				resultMap = ResponseTool.returnSuccess(null);
				String resultJson = JsonUtil.getObjectToJson(resultMap);
				logger.info("[DAMS#ActivityController]返回吊牌活动的信息是:" + resultJson);
				return resultJson;
			}
		}
		/* 获取结果 */
		StarActivityInfo result = null;
		result = starInfoActivityService.getJsonStarInfoActivity(userId, activityId,quickSentry);
		
		logger.info("[DAMS#ActivityController]读取DA系统吊牌活动的数据是:" + JsonUtil.getObjectToJson(result));
		resultMap = ResponseTool.returnSuccess(result);
		String resultJson = JsonUtil.getObjectToJson(resultMap);
		return resultJson;
	}

	/**
	 * 活动结束后,查询主播活动积分
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes"})
	@RequestMapping(value = "/queryStarInfoEndActivity", method = RequestMethod.POST)
	public @ResponseBody String queryStarInfoEndActivity(@ModelAttribute("data") String data)throws Exception {
		logger.debug("[DAMS#ActivityController]查询系统吊牌活动的参数是:" + data);
		Map resultMap = null;
		if (Strings.isNullOrEmpty(data)) {
			resultMap = ResponseTool.returnError(ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE, "[DAMS#ActivityController#ERROR]参数为空!");
			String resultJson = JsonUtil.getObjectToJson(resultMap);
			return resultJson;
		}
		Map map = (Map) JsonUtil.getObjectFromJson(data, Map.class);
		Map result_Map=(Map) map.get("reqData");
		if (!checkData(result_Map)) {
			resultMap = ResponseTool.returnError(ErrorCodeConstant.ERRORMSG_DBERROR_CODE, "[DAMS#ActivityController#ERROR]参数异常!");
			String resultJson = JsonUtil.getObjectToJson(resultMap);
			return resultJson;
		} 		
		String userId = result_Map.get("userID").toString();
		String activityId = result_Map.get("activityID").toString();
		/* 获取结果 */
		StarActivityInfo result = null;
		result = starInfoActivityService.getJsonStarEndInfoActivity(userId, activityId,quickSentry);
		
		logger.debug("[DAMS#ActivityController]读取DA系统吊牌活动结束后的数据是:" + JsonUtil.getObjectToJson(result));
		resultMap = ResponseTool.returnSuccess(result);
		String resultJson = JsonUtil.getObjectToJson(resultMap);
		return resultJson;
	}
	
	/**
	 * 检查数据
	 * @param map
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkData(Map map) {
		if (map.get("roomID") == null || map.get("roomID").toString().equals("")) {
			return false;
		}
		if (map.get("userID") == null || map.get("userID").toString().equals("")) {
			return false;
		}
		if (map.get("activityID") == null || map.get("activityID").toString().equals("")) {
			return false;
		}
		return true;
	}
	
	public StarInfoActivityService getStarInfoActivityService() {
		return starInfoActivityService;
	}

	public void setStarInfoActivityService(StarInfoActivityService starInfoActivityService) {
		this.starInfoActivityService = starInfoActivityService;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}

	public SentryAgent getCheckUserIdQuickSentry() {
		return checkUserIdQuickSentry;
	}

	public void setCheckUserIdQuickSentry(SentryAgent checkUserIdQuickSentry) {
		this.checkUserIdQuickSentry = checkUserIdQuickSentry;
	}
}

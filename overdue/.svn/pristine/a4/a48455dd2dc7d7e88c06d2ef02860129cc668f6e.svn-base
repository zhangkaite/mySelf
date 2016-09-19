package com.ttmv.service.input;

import java.util.HashMap;
import java.util.Map;

import com.ttmv.service.tools.constant.ErrorCodeConstant;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ResponseTool {
	
	/**
	 * 正常返回
	 * @param resMap
	 * @param resDate
	 * @return
	 */
	public static Map returnSuccess() {
		Map resMap = new HashMap();
		resMap.put("resultCode", ErrorCodeConstant.SYSTEM_SUCCESS_CODE);
		return resMap;
	}
	
	/**
	 * 系统异常返回
	 * @param resMap
	 * @return
	 */
	public static Map returnException() {
		Map resMap = new HashMap();
		resMap.put("resultCode", ErrorCodeConstant.SYSTEM_ERROR_CODE);
		return resMap;
	}
	
	/**
	 * 业务异常返回(自定义业务错误)
	 * @param resMap
	 * @param resultCode
	 * @param errorMsg
	 * @return
	 */
	public static Map returnError(String errorCode) {
		Map resMap = new HashMap();
		resMap.put("resultCode", errorCode);
		return resMap;
	}

}

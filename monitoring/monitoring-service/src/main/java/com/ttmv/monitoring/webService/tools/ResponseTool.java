package com.ttmv.monitoring.webService.tools;

import java.util.HashMap;
import java.util.Map;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日14:21:14
 * @explain :返回数据组装
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ResponseTool {
	
	
	/**
	 * 正常返回(有返回数据)
	 * @param resMap
	 * @param resDate
	 * @return
	 */
	public static Map returnSuccess(Object resData) {
		Map resMap = new HashMap();
		resMap.put("resultCode", ResultCodeConstant.RESULTCODE_SUCCESS);
		resMap.put("resData", resData);
		return resMap;
	}
	
	/**
	 * 正常返回 (无返回数据)
	 * @param resMap
	 * @param resDate
	 * @return
	 */
	public static Map returnSuccess() {
		Map resMap = new HashMap();
		resMap.put("resultCode", ResultCodeConstant.RESULTCODE_SUCCESS);
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
	
	/**
	 * 系统异常返回(默认系统错误)
	 * @param resMap
	 * @param resultCode
	 * @param errorMsg
	 * @return
	 */
	public static Map returnError() {
		Map resMap = new HashMap();
		resMap.put("resultCode", ResultCodeConstant.RESULTCODE_ERROR);
		return resMap;
	}
	


	
}

package com.ttmv.datacenter.usercenter.service.facade.tools;

import java.util.HashMap;
import java.util.Map;

import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年1月27日 下午3:13:38
 * @explain :返回数据组装
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ResponseTool {
	
	
	/**
	 * 正常返回
	 * @param resMap
	 * @param resDate
	 * @return
	 */
	public static Map returnSuccess(Object resData) {
		Map resMap = new HashMap();
		resMap.put("resultCode", ErrorCodeConstant.SYSTEM_SUCCESS_CODE);
		resMap.put("errorMsg", ErrorCodeConstant.ERRORMSG_DF_CODE);
		resMap.put("resData", resData);
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
		resMap.put("errorMsg", ErrorCodeConstant.ERRORMSG_SYSTEM_CODE);
		return resMap;
	}
	
	/**
	 * 业务错误返回
	 * @param resMap
	 * @param resultCode
	 * @param errorMsg
	 * @return
	 */
	public static Map returnError(String resultCode , String errorMsg) {
		Map resMap = new HashMap();
		resMap.put("resultCode", resultCode);
		resMap.put("errorMsg", errorMsg);
		return resMap;
	}
	
	public static void setErrorMsg( String errorMsg){
		Map resMap = new HashMap();
		resMap.put("errorMsg", errorMsg);
	}
	
	public static void setResultCode( String resultCode){
		Map resMap = new HashMap();
		resMap.put("errorMsg", resultCode);
	}


	
}

package com.ttmv.datacenter.paycenter.service.processor.constant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月02日 下午4:55:16
 * @explain :错误代码
 */
public interface ErrorCodeConstant {
	
	
	//---------------响应代码-------------
	/** 成功 */
	String SYSTEM_SUCCESS_CODE = "AAAAAAA";
	/** 系统异常*/
	String SYSTEM_ERROR_CODE = "Error500";
	/** 参数异常*/
	String SYSTEM_PARAMETER_ERROR_CODE = "Error999";
	/** 业务处理异常*/
	String SYSTEM_BUSINESS_ERROR_CODE = "Error000";
	/** 处理超时*/
	String SYSTEM_OVERTIME_ERROR_CODE = "Error503";
	/** 数据格式错误*/
	String SYSTEM_DATAFORMAT_ERROR_CODE = "Error900";
	/** 服务临时限流*/
	String SYSTEM_SERVERCLOSE_CODE = "Error502";
	
	 //--------------异常代码-----------------
	/** 默认成功*/
	String ERRORMSG_DF_CODE = "";
	/** 系统错误*/
	String ERRORMSG_SYSTEM_CODE = "OP500";
	
	/** 参数为空*/
	String ERRORMSG_PARAMETERNULL_CODE = "OP20000";
	/** 参数异常*/
	String ERRORMSG_PARAMETERERROR_CODE = "OP20001";
	/** 参数违反业务*/
	String ERRORMSG_PARAMETVIOLATE_CODE = "OP20002";
	
	/** 账户不存在*/
	String ERRORMSG_ACCOUNTERR_CODE = "OP30000";
	
	/** 账户余额不足*/
	String ERRORMSG_ACCOUNTNSF_CODE = "OP30001";

	

}

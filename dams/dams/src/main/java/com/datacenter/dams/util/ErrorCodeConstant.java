package com.datacenter.dams.util;



/**  
 * @explain :错误代码
 */
public interface ErrorCodeConstant {
	
	/** 成功 */
	String SYSTEM_SUCCESS_CODE = "AAAAAAA";
	/** 默认成功*/
	String ERRORMSG_DF_CODE = "";
	/** 参数异常*/
	String SYSTEM_PARAMETER_ERROR_CODE = "Error999";	
	/** 参数为空*/
	String ERRORMSG_PARAMETERNULL_CODE = "OP20000";
	/** 数据库异常*/
	String ERRORMSG_DBERROR_CODE = "OP40001";
}

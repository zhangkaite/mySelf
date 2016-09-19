package com.ttmv.datacenter.usercenter.service.processor.constant;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月26日 下午9:03:55  
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
	/** 系统服务关闭*/
	String ERRORMSG_SERVERCLOSE_CODE = "OP520";
	/** 登录失败*/
	String ERRORMSG_LOGIN_CODE = "OP10000";
	/** 用户名已经存在*/
	String ERRORMSG_USERNAME_CODE = "OP10001";
	/** 邮箱被注册*/
	String ERRORMSG_LOGINEMAIL_CODE = "OP10002";
	/** 手机被注册*/
	String ERRORMSG_LOGINMOBILE_CODE = "OP10003";
	/** TT号码被占用*/
	String ERRORMSG_TTNUM_CODE = "OP10004";
	/** 手机绑定次数大于5*/
	String ERRORMSG_BINDINGMOBILE_CODE = "OP10005";
	/** 邮箱被绑定*/
	String ERRORMSG_BINDINGEMAIL_CODE = "OP10006";
	/** MAC禁用*/
	String ERRORMSG_FORBIDMAC_CODE = "OP10007";
	/** IP禁用*/
	String ERRORMSG_FORBIDIP_CODE = "OP10008";
	/** 硬盘禁用*/
	String ERRORMSG_FORBIDHDNUM_CODE = "OP10009";
	/** 靓号被使用*/
	String ERRORMSG_LOGINGOODTTNUM_CODE = "OP10011";
	
	/** 参数为空*/
	String ERRORMSG_PARAMETERNULL_CODE = "OP20000";
	/** 参数异常*/
	String ERRORMSG_PARAMETERERROR_CODE = "OP20001";
	/** 参数违反业务*/
	String ERRORMSG_PARAMETVIOLATE_CODE = "OP20002";
	/** 邮箱非法*/
	String ERROR_EMAIL_VALIDATION_CODE = "OP20003";
	/** 手机非法*/
	String ERROR_PHONE_VALIDATION_CODE  = "OP20004";
	/** uName非法*/
	String ERROR_UNAME_VALIDATION_CODE  = "OP20005";
	
	
	
	/** 账户余额不足*/
	String ERRORMSG_ACCOUNTNSF_CODE = "OP30001";
	/** 用户状态异常*/
	String ERRORMSG_STATUSERROR_CODE = "OP30002";
	/** 用户冻结*/
	String ERRORMSG_USERFREEZE_CODE = "OP30003";
	/** 终端禁用*/
	String ERRORMSG_FORBID_CODE = "OP30004";
	
	/** 数据库异常*/
	String ERRORMSG_DBERROR_CODE = "OP40001";
	
	/** API调用异常*/
	String ERRORMSG_APIERROR_CODE = "OP90001";
	
	/** 未被授权的*/
	String ERRORMSG_UNLICENSED_CODE="OP501";
	
	

}

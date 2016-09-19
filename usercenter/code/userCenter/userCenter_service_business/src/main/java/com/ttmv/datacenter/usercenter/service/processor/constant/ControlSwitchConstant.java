package com.ttmv.datacenter.usercenter.service.processor.constant;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月6日 上午10:45:20  
 * @explain :开关常量
 */
public interface ControlSwitchConstant {

	//================API服务类开关==============
	/** 分布式锁*/
	String SWITCH_LOCK_CODE = "lock_switch";
	/** 计数器（表ID生成器）*/
	String SWITCH_CONT_CODE = "cont_switch";
	/** token生成器*/
	String SWITCH_TOKEN_CODE = "token_switch";
	
	
	//-----------------接口服务开关---------------
	/** 注册服务开关*/
	String SWITCH_ADDUSER_CODE = "addUser_switch";
	/** 登录服务开关*/
	String SWITCH_LOGIN_CODE = "login_switch";
	/** 第三方登录服务开关*/
	String SWITCH_SDKLOGIN_CODE ="sdkLogin_switch";
	/** */
	

}

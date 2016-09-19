package com.ttmv.monitoring.tools.constant;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:55:03
 * @explain :服务监控系统常量接口类
 */
public interface SmsConstant {
	
	// *************修改用户操作类型**********
	/** 修改*/
	Integer OPERATION_UPDATE = 1;
	/** 删除*/
	Integer OPERATION_DELETE = 9;
	
	
	//**************密码修改类型************
	/** 普通修改*/
	Integer RESETPWD_USER = 1;
	/** 超级管理员修改*/
	Integer RESETPWD_ADMIN = 9;
	
	
	//**************唯一性校验类型************
	/** 用户名校验*/
	Integer CHECKKEY_USERNAME = 1;
	
	// *************唯一性校验结果**********
	/** 未被使用 */
	Integer CHECKRES_ISNULL = 0;
	/** 已经被占用 */
	Integer CHECKRES_ISNOTNULL = 1;
	
	
	// *************用户状态**********
	/** 正常 */
	Integer USTATE_NORMAL = 1;
	/** 已删除 */
	Integer USTATE_REMOVED = 9;
	
	// *************监控系统类型**********
	/** 媒体转发服务器 */
	Integer THRESHOLD_TYPE_MTZF = 1;
	/** 媒体控服务器 */
	Integer THRESHOLD_TYPE_MTKZ = 2;
	
	// *************ip白名单类型**********
	/** 单IP */
	Integer WHITELIST_IP_ONE = 1;
	/** IP区间 */
	Integer WHITELIST_IP_TWO = 2;
	

}

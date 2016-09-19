package com.ttmv.datacenter.paycenter.worker.constant;

public interface AccountConstant {

	/**
	 * 加/减
	 */
	Integer ADD = 1;
	Integer MINUS = -1;
	
	/**
	 * 用户状态
	 */
	Integer NORMAL = 1;
	Integer FREEZE = 0;
	
	/**
	 * 用户余额是否充足
	 */
	Integer NOT_ENOUGH = -1;
	Integer ENOUGH = 1;
	
	/**
	 * 用户账户类型（0：TB账户，1：T券账户，2：佣金账户）
	 */
	Integer TCOIN = 0 ;
	Integer TQUAN = 1 ;
	Integer BROKERAGE = 2 ;
	
	/**
	 * 用户余额查询对应的Map的key
	 */
	String TCOIN_KEY = "tcoin";
	String TQUAN_KEY = "tquan";
	String BROKERAGE_KEY = "brokerage";
	
	/***
	 * 账户操作类型标识
	 */
	/** 账户初始化*/
	Integer PAYCENTER_ACCOUNT_INIT = 0;
	/** 账户处理账单*/
	Integer PAYCENTER_ACCOUNT_OPER = 1;
	
	/***
	 * 标志
	 */
	String FLAG="true";
	
	
	
	
	
	
}

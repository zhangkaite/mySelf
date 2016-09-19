package com.ttmv.datacenter.paycenter.dao.implement.constant;

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
	Integer NOT_USER = 0;
	Integer ENOUGH = 1;

	/**
	 * 用户账户类型（0：TB账户，1：T券账户，2：佣金账户）
	 */
	Integer TCOIN = 0;
	Integer TQUAN = 1;
	Integer BROKERAGE = 2;

	/**
	 * 用户余额查询对应的Map的key
	 */
	String TCOIN_KEY = "tcoin";
	String TQUAN_KEY = "tquan";
	String BROKERAGE_KEY = "brokerage";

	/***
	 * 账户操作类型标识
	 */
	Integer PAYCENTER_ACCOUNT_INIT = 0;
	Integer PAYCENTER_ACCOUNT_OPER = 1;

	/***
	 * 标志
	 */
	String FLAG = "true";

	/**
	 * 账户的字段值
	 */
	String ID = "id";
	String USERID = "userId";
	String BALANCE = "balance";
	String FREEZEBALANCE = "freezeBalance";
	String ACCOUNTSTATUS = "accountStatus";
	String CREATED = "created";
	String UPDATED = "updated";

	/**
	 * 报警支付中心服务类型
	 */
	String PC_SERVER_TYPE = "DcPayCenterService";

	String PC_SERVER_ID = "PC01";

	String PC_ERROR_INFO = "FATALERROR";

	String PC_MYSQL_MESSAGE = "支付中心mysql数据库异常";
	
	String PC_REDIS_MESSAGE="支付中心redis库异常";
	
	String PC_HBASE_MESSAGE="支付中心hbase库异常";
	
	String PC_HBASE_ZK_MESSAGE="支付中心hbase zookeeper异常";
	
	String PC_MQ_MESSAGE="支付中心消息队列异常";

}

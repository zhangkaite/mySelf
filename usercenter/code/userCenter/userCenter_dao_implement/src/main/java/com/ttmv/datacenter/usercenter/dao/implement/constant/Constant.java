package com.ttmv.datacenter.usercenter.dao.implement.constant;

public interface Constant {

	/**
	 * Item的id分割符
	 */
	String SEPARATOR_ITEM = "_";
	
	/**
	 * 注册用户信息保存状态
	 */
	int SUCCESS = 1;//成功
	int ACCEPT = 0;//已受理
	
	String UC_SERVER_TYPE = "DcUserCenterService";
	String UC_SERVER_ID = "UC001";
	String UC_ERROR = "FATALERROR";
	String UC_MYSQL_ERROR_MSG = "mysql数据库异常";
	String UC_SOLR_ERROR_MSG = "solr数据库异常";
	String UC_REDIS_ERROR_MSG = "redis数据库异常";
	String UC_HBASE_ERROR_MSG = "hbase数据库异常";
	String UC_ACTIVEMQ_ERROR_MSG = "activemq数据库异常";
}

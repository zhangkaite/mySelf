package com.ttmv.datacenter.usercenter.dao.implement.constant;

public interface UserInfoConstant {

	/**
	 * 成功与否的标识
	 */
	boolean PERSISTENT_YES = true;
	boolean PERSISTENT_NO = false;
	
	/**
	 * 注册用户信息保存状态
	 */
	int SUCCESS = 1;//成功
	int ACCEPT = 0;//已受理
	int OBJECT_NULL = 2;//对象不存在
	
	/**
	 * 用户注册信息的查询字段
	 */
	String USERINFO_USERNAME = "userName";
	String USERINFO_ADMINID = "adminId";
	String USERINFO_TTNUM = "TTnum";
	String USERINFO_NICKNAME = "nickName";
	String USERINFO_SEX = "sex";
	String USERINFO_TIME = "time";
	String USERINFO_ID = "id";
	
	/**
	 * 用户可以模糊查询的字段的个数
	 */
	int USERINFO_SEARCH_FIELD_SUM = 10;

	/**
	 * 用户状态标识inner
	 */
	int INNER_STATUS_NORMAL = 0;//正常
	int INNER_USERINFO_STATUS_FREEZE  = 1;//冻结
	int INNER_USERINFO_STATUS_DELETE  = 7;//删除
	
	/**
	 * 用户状态标识out
	 */
	int OUT_STATUS_NORMAL = 0;//正常
	int OUT_STATUS_FREEZE  = 1;//冻结
	int OUT_STATUS_DELETE  = 2;//删除
	
	/**
	 * 用户vipType标识inner
	 * Mysql
	 */
	int INNER_VIPTYPE_YES = 1;//是会员
	int INNER_VIPTYPE_NO = 0;//不是会员
	
	/**
	 * 用户vipType标识out
	 */
	int OUT_VIPTYPE_YES = 1;//会员
	int OUT_VIPTYPE_NO = 2;//不是会员
	
	/**
	 * 用户中心服务标示
	 */
	String ADD_USERINFO = "uc_task_dao_userInfo";
	String UPD_USERINFO = "uc_task_dao_userInfo_update";
	
	String  ADD_GROUP = "uc_task_dao_group";
	String  UPD_GROUP = "uc_task_dao_group_update";
	String  DEL_GROUP = "uc_task_dao_group_delete";
	
	String  ADD_CROSS = "uc_task_dao_userCrossRelation";
	String  UPD_CROSS = "uc_task_dao_userCrossRelation_update";
	String  DEL_CROSS = "uc_task_dao_userCrossRelation_delete";
}

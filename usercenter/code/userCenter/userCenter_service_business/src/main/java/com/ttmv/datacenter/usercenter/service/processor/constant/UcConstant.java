package com.ttmv.datacenter.usercenter.service.processor.constant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015-1-20 21:51:43
 * @explain :用户中心常量接口类
 */
public interface UcConstant {

	// ********************用户 修改类型****************
	/** 普通用户修改 */
	Integer UTYPE_GENERAL_CODE = 0;// 普通用户
	/** 官方用户修改 */
	Integer UTYPE_OFFICIAL_CODE = 1;// 官方用户
	/** 管控中心修改 */
	Integer UTYPE_CONTROL_CODE = 2;// 管控修改(带秘钥)
	/** 机器人用户 */
	Integer UTYPE_BOT_CODE = 9;// 电脑机器人
	
	/** 机器人TT号长度设置 ：44+6位自然数（共8位）*/
	Integer BOT_TTNUM_LENG = 6;// "44" + 6位自然数（共8位）
	/** 机器人TT号头部*/
	String BOT_TTNUM_TOP = "44" ;

	// ********************用户注册类型*************
	/** 账号注册 */
	Integer ADDUSER_USERNAME_CODE = 0; // 账号注册
	/** 手机注册 */
	Integer ADDUSER_PHONE_CODE = 1; // 手机注册
	/** 邮箱注册 */
	Integer ADDUSER_EMAIL_CODE = 2; // 邮箱注册

	// ********************用户登录类型**************
	/** 账号登陆 */
	Integer LOGIN_USER_CODE = 0;// 账号登陆
	/** TT号登陆 */
	Integer LOGIN_TTNUM_CODE = 1;// TT号登陆
	/** 手机登陆 */
	Integer LOGIN_PHONE_CODE = 2;// 手机登陆
	/** Email登陆 */
	Integer LOGIN_EMAIL_CODE = 3;// Email登陆
	/** 靓号登陆 */
	Integer LOGIN_GOODTTNUM_CODE = 4;// 靓号登陆
	/** token登陆 */
	Integer LOGIN_TOKEN_CODE = 5;// token登陆
	/** 第三方登陆 */
	Integer LOGIN_THIRDPARTY_CODE = 6;// 第三方登陆

	// ********************校验类型**************
	/** 邮箱唯一 */
	String VERIFY_ONLYEMAIL_CODE = "email";// 邮箱注册校验
	/** 手机唯一 */
	String VERIFY_ONLYMOBILE_CODE = "onlyphone";// 手机注册校验
	/** 用户名唯一 */
	String VERIFY_ONLYUSERNAME_CODE = "uName";// 用户名注册校验
	/** 绑定手机 */
	String VERIFY_BINDINGMOBILE_CODE = "phone";// 绑定手机验证
	/** 绑定手机 */
	String VERIFY_BINDINGEMAIL_CODE = "onlyemail";// 绑定邮箱验证

	// ********************密码找回类型**************
	/** 用户名+旧密码+新密码 */
	Integer MDFPWD_UNAMEPAWD_CODE = 1;// 用户名+旧密码+新密码
	/** 用户名+新密码 */
	Integer MDFPWD_UNAMENEWPWD_CODE = 0;// 用户名+新密码
	/** 手机+新密码 */
	Integer MDFPWD_MOBILENEWPWD_CODE = 2;// 手机+新密码
	/** 邮箱+新密码 */
	Integer MDFPWD_EMAILNEWPWD_CODE = 3;// 邮箱+新密码

	// ********************绑定号码类型**************
	/** 手机号绑定 */
	Integer BINDINGTYPE_MOBILE_CODE = 0;
	/** 邮箱绑定 */
	Integer BINDINGTYPE_EMAIL_CODE = 1;

	// ********************开通号码类型**************
	/** 手机号绑定 */
	Integer OPENTYPE_MOBILE_CODE = 0;
	/** 邮箱绑定 */
	Integer OPENTYPE_EMAIL_CODE = 1;
	/** 靓号绑定 */
	Integer OPENTYPE_GOODTTNUM_CODE = 2;

	// **************好友关系类型******************
	/** 关注关系 */
	Integer RELATIONSHIPTYPE_REGARD = 1;
	/** 好友关系 */
	Integer RELATIONSHIPTYPE_FRIEND = 2;

	// *************用户状态**********
	/** 正常 */
	Integer USTATE_NORMAL = 0;
	/** 冻结 */
	Integer USTATE_BLOCKING = 1;
	/** 删除 */
	Integer USTATE_REMOVED = 2;

	// ******vip类型*****
	/** 会员 */
	Integer VIPTYPE_YES = 1;
	/** 非会员 */
	Integer VIPTYPE_NO = 2;
	
	//主播
	String ANNOUNCERTYPE_Y = "Y";
	String ANNOUNCERTYPE_N = "N";

	// ***********好友添加模式（防骚扰设置）*******
	/** 允许所有人添加 */
	Integer DNDTYPE_ALL = 3;
	/** 回答问题添加 */
	Integer DNDTYPE_PROBLEM = 4;
	/** 不许任何人添加 */
	Integer DNDTYPE_NO = 2;
	/** 验证身份 */
	Integer DNDTYPE_VALIDATION = 1;

	// *******问题类型*******
	/** 你的家乡是？ */
	Integer PROBLEMTYPE_JX = 1;
	/** 你的职业是？ */
	Integer PROBLEMTYPE_ZY = 2;
	/** 你的名字是？ */
	Integer PROBLEMTYPE_NAME = 3;
	/** 你的手机是？ */
	Integer PROBLEMTYPE_PHONE = 4;

	// ******上下线类型*****
	/** 登录 */
	Integer BEHAVIOR_LOGIN = 1;
	/** 退出 */
	Integer BEHAVIOR_LOGOUT = 2;
	
	//**********靓号状态*********
	/** 正常 */
	Integer GOODTTNUMTYPE_Y = 2;
	/** 冻结 */
	Integer GOODTTNUMTYPE_N = 1;
	
	//**********token*********
	/** 超级token*/
	String SUPERTOKEN_CODE = "d791a6730c14f7370343003ecf3275d8";
	/** 管控修改token*/
	String CONTROL_CODE = "94a08da1fecbb6e8b46990538c7b50b2";
	
	//**********是否存在密码*********
	/** 有 */
	Integer PWD_YES_CODE = 1;
	/** 无 */
	Integer PWD_NO_CODE = 0;
	
	
}

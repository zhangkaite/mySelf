package com.ttmv.datacenter.usercenter.service.processor.constant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015-1-20 22:39:43
 * @explain :正则表达式常量类
 */
public interface RegexConstant {
	/**
	 * userName验证 正则表达式
	 * 首字母小写，其他小写字母数字组合，长度（6-25）
	 */
	String REGEX_USERNAME_0= "[a-z]{1}.*";//首字符为小写字母
	String REGEX_USERNAME_1= "^[0-9a-z]+$";//只包含数字小写字母
	String REGEX_USERNAME_2="^(?=.*[0-9]).*$";//必须包含一个数字
	String REGEX_USERNAME_3="^\\S{6,25}$";//长度为6-25
	
	
	/**
	 * 电话号码正则表达式
	 * 支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号
	 */
	String REGEX_MOBILE = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
	
	/**
	 * 邮箱验证 正则表达式
	 */
	String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	
	

}

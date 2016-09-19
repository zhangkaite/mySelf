package com.ttmv.datacenter.usercenter.service.processor.constant;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月26日 下午3:08:57  
 * @explain :锁
 */
public interface LockConstant {
	
	/**
	 * 唯一锁
	 */
	String ONLYONE_MOBILE_LOCK = "LOCK_MOBILE_";//注册手机
	String ONLYONE_EMAIL_LOCK = "LOCK_EMAIL_";//注册邮箱
	String ONLYONE_TTNUM_LOCK = "LOCK_TTNUM_";//注册TTnum
	String ONLYONE_USERNAME_LOCK = "LOCK_USERNAME_";//注册userName
	String ONLYONE_GOODTTNUM_LOCK = "LOCK_GOODTTNUM_";//靓号
	
	/**
	 * 业务限制次数锁
	 */
	String FIVE_MOBILE_LOCK = "LOCK_BINDINGMOBILE_";//绑定手机
	String ONE_EMAIL_LOCK = "LOCK_BINDINGEMAIL_";//绑定邮箱
	
	
	

}

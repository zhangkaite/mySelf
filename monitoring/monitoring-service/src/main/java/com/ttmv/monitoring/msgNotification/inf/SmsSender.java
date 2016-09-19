package com.ttmv.monitoring.msgNotification.inf;

import com.ttmv.monitoring.msgNotification.entity.SmsServerInfo;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月19日13:11:21
 * @explain :短信发送接口
 */
public interface SmsSender extends Sender{
	
	/**
	 * 单发短信
	 * @param mobilePhone
	 * @param message
	 * @throws Exception
	 */
	public void sendSms(SmsServerInfo smsServerInfo , String mobilePhones) throws Exception;
	
	/**
	 * 多人发送
	 * @param mobilePhones
	 * @param message
	 * @throws Exception
	 */
	public void sendSms(SmsServerInfo smsServerInfo , String[] mobilePhones) throws Exception ;

}

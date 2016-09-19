package com.ttmv.monitoring.msgNotification.inf;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月17日14:21:59
 * @explain :发送器
 */
public interface Sender {
	/** 
	 * @param title 消息的标题 
	 * @param content 消息的内容 
	 * @param to 消息的接收人 
	 * @throws Exception 
	 */  
	public void sender(String title, String content, String... to) throws Exception;  

}

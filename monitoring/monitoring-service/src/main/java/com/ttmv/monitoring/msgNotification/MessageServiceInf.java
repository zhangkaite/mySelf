package com.ttmv.monitoring.msgNotification;

import java.util.Map;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月17日19:01:59
 * @explain :消息消息发送服务
 */
public interface MessageServiceInf {
	
	/** 
	 * 根据消息模板表中的消息编号取得消息模板，填充，发送 
	 * @param params 内容的参数 
	 */
	public void sendMessage( Map params) throws Exception;

}

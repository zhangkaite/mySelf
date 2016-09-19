package com.ttmv.monitoring.msgNotification.inf;

import com.ttmv.monitoring.msgNotification.entity.MailSenderInfo;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月17日14:01:59
 * @explain :邮件发送接口
 */
public interface EmailSender extends Sender{
      
    /** 
     * 多人发送邮件，默认文本格式 
     */  
	public  boolean sendEmail(MailSenderInfo mailSenderInfo , String[] subject) throws Exception;  
      
    /** 
     * 发送邮件 (Html 格式)
     */  
	public  boolean sendHtmlEmail(MailSenderInfo mailSenderInfo , String toAddress) throws Exception;  
	
	 /** 
     * 发送邮件 (Text 格式)
     */  
	public  boolean sendTextEmail(MailSenderInfo mailSenderInfo , String toAddress) throws Exception; 
}

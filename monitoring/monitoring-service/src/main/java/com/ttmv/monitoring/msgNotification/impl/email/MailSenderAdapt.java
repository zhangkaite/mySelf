 package com.ttmv.monitoring.msgNotification.impl.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.msgNotification.entity.MailSenderInfo;
import com.ttmv.monitoring.msgNotification.inf.EmailSender;
import com.ttmv.monitoring.msgNotification.inf.Sender;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月19日13:08:22
 * @explain : 邮件发送器
 */
public class MailSenderAdapt implements Sender{
	private static Logger logger = LogManager.getLogger(MailSenderAdapt.class);
	
	private EmailSender emailSender; 
	
	public EmailSender getEmailSender() {
		return emailSender;
	}
	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sender(String title, String content, String... to)
			throws Exception {
		logger.info("[MailSenderAdapt] @ start...");
        //这个类主要是设置邮件   
        MailSenderInfo mailSenderInfo = new MailSenderInfo();    
        mailSenderInfo.setMailServerHost("smtp.ym.163.com");    
        mailSenderInfo.setMailServerPort("25");    
        mailSenderInfo.setValidate(true);    
        mailSenderInfo.setUserName("sms@ttmv.com");    
        mailSenderInfo.setPassword("123456");//您的邮箱密码    
        mailSenderInfo.setFromAddress("sms@ttmv.com");    
        mailSenderInfo.setSubject(title);    
        mailSenderInfo.setContent(content);    
        emailSender.sendEmail(mailSenderInfo, to);
		
	}

}

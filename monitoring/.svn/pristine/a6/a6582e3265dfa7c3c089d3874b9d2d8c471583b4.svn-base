package com.ttmv.monitoring.msgNotification.impl;

import com.ttmv.monitoring.msgNotification.impl.email.MailSenderAdapt;
import com.ttmv.monitoring.msgNotification.impl.sms.SmsSenderAdapt;
import com.ttmv.monitoring.msgNotification.inf.Sender;
import com.ttmv.monitoring.msgNotification.tools.MsgConstant;

/** 
 * 根据不同的消息类型，取得适应的消息发送器 
 * @author Damon_Zs
 * @version 创建时间：2015年9月18日19:01:59
 * @version 1.0 
 */  
public class SenderFactory {

    private MailSenderAdapt mailSenderAdapt;;  
  
    private SmsSenderAdapt smsSenderAdapt; 
  
    public Sender getSender(String type) {  
        Sender sender = null;  
        if (MsgConstant.BMT_TYPE_MAIL.equalsIgnoreCase(type)) {  
            // 邮件  
            sender = mailSenderAdapt;  
        } else if (MsgConstant.BMT_TYPE_SMS.equalsIgnoreCase(type)) {  
            // 短信  
            sender = smsSenderAdapt;  
        }  
        return sender;  
    }

	public MailSenderAdapt getMailSenderAdapt() {
		return mailSenderAdapt;
	}

	public void setMailSenderAdapt(MailSenderAdapt mailSenderAdapt) {
		this.mailSenderAdapt = mailSenderAdapt;
	}

	public SmsSenderAdapt getSmsSenderAdapt() {
		return smsSenderAdapt;
	}

	public void setSmsSenderAdapt(SmsSenderAdapt smsSenderAdapt) {
		this.smsSenderAdapt = smsSenderAdapt;
	}


}

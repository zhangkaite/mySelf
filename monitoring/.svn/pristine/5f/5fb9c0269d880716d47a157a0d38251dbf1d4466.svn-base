package com.ttmv.monitoring.msgNotification.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.msgNotification.MessageServiceInf;
import com.ttmv.monitoring.msgNotification.entity.Threshold;
import com.ttmv.monitoring.msgNotification.impl.email.EmailSenderImpl;
import com.ttmv.monitoring.msgNotification.impl.email.MailSenderAdapt;
import com.ttmv.monitoring.msgNotification.impl.sms.SmsSenderAdapt;
import com.ttmv.monitoring.msgNotification.inf.Sender;
import com.ttmv.monitoring.msgNotification.tools.MsgFormat;
import com.ttmv.monitoring.msgNotification.tools.SmsEncode;
import com.ttmv.monitoring.tools.util.AlerterUtil;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月18日19:31:59
 * @explain :消息消息发送服务实现
 */
public class MessageServiceImpl implements MessageServiceInf{
	private static Logger logger = LogManager.getLogger(MessageServiceImpl.class);
	
	private SenderFactory senderFactory;
	
	private MailSenderAdapt mailSenderAdapt;;  
	  
    private SmsSenderAdapt smsSenderAdapt; 
    
    private IAlerterInfoInter iAlerterInfoInter;
    
    private IUserInfoInter iUserInfoInter;

	public void sendMessage(Map params) throws Exception {
		logger.info("[MessageServiceImpl] @ start...");
		logger.debug("============消息相关信息打印[start]==========");
		//邮件标题设置（PROBLEM: 10.0.1.84）
		String title ="【TTMV服务监控平台】" + params.get("msgType")+":"+params.get("ip");
		String content = "";
		if(params.get("msgType").equals("PROBLEM")){//PROBLEM
			//阀值报警邮件信息组装
			content = MsgFormat.emailFormatFZyes(params);
		}else if(params.get("msgType").equals("OK")){//OK
			//阀值报警恢复邮件信息组装
			content = MsgFormat.emailFormatFZno(params);
		}else if(params.get("msgType").equals("FATALERROR")){//直接报警（FATALERROR）
			//直接报警邮件信息组装
			content = MsgFormat.emailFormatAlert(params);
		}
		//查询报警器
        AlerterInfoTmp tmp = iAlerterInfoInter.queryAlerterInfo(new BigInteger(params.get("alerterID").toString()));
        //报警器关联user查询
        List<UserInfo> users =  iUserInfoInter.queryListByIds(tmp.getAlerterUser());
        //邮箱数组
        String[] toEmails = new String[users.size()];
        for(int i=0;i<users.size();i++){
        	toEmails[i] = users.get(i).getUserMail();
        }
        //手机数组
        String[] toPhones = new String[users.size()];
        for(int i=0;i<users.size();i++){
        	toPhones[i] = users.get(i).getUserMobile();
        }
        logger.debug("===========消息相关信息打印[end]==========");
        
        //根据报警器得到报警类型（手机？邮箱？手机+邮箱）
        int[] thresholds = AlerterUtil.getIntArrayByStr( tmp.getAlerterType(), "\\|");
        
        if(thresholds.length == 1){
        	if(thresholds[0] == 2){//邮箱
        		mailSenderAdapt.sender(title, content, toEmails);
        	}else if(thresholds[0] == 1){//短信
        		smsSenderAdapt.sender(title, content, toPhones);
        	}
        }else if(thresholds.length == 2){
        	smsSenderAdapt.sender(title, content, toPhones);
        	mailSenderAdapt.sender(title, content, toEmails);
        }
	}

	public SenderFactory getSenderFactory() {
		return senderFactory;
	}
	public void setSenderFactory(SenderFactory senderFactory) {
		this.senderFactory = senderFactory;
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
	public IAlerterInfoInter getiAlerterInfoInter() {
		return iAlerterInfoInter;
	}
	public void setiAlerterInfoInter(IAlerterInfoInter iAlerterInfoInter) {
		this.iAlerterInfoInter = iAlerterInfoInter;
	}
	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}
	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}

}

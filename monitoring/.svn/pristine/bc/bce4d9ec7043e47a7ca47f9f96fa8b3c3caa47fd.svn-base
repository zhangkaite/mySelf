package com.ttmv.monitoring.msgNotification.impl.sms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.msgNotification.entity.SmsServerInfo;
import com.ttmv.monitoring.msgNotification.inf.Sender;
import com.ttmv.monitoring.msgNotification.inf.SmsSender;

/**
 * 短信发送器
 * @author Damon  
 * （短信运营商参数设置）
 */
public class SmsSenderAdapt implements Sender{
	
	private static Logger logger = LogManager.getLogger(SmsSenderAdapt.class);
	
	private SmsSender smsSender;  
	
	@SuppressWarnings("unused")
	public void sender(String title, String content, String... to)throws Exception {
		logger.info("[SmsSenderAdapt] Start...");
		SmsServerInfo smsServerInfo = new SmsServerInfo();
		smsServerInfo.setUrl("http://api2.santo.cc/submit");//短信服务商地址
		smsServerInfo.setCpid("bjwq-ipxmt2");//短信平台账号
		smsServerInfo.setCppwd("7R3Cujft");//短信平台密码
		smsServerInfo.setSm(content);//短信内容
		smsServerInfo.setDc("15");//消息内容编码（15:DBK ; 8:Unicode ; 1:ISO8859-1）
		if(true){//群发
			smsServerInfo.setCommand("MULTI_MT_REQUEST");
			smsSender.sendSms(smsServerInfo, to);
		}else{//单发
			smsServerInfo.setCommand("MT_REQUEST");
			smsSender.sendSms(smsServerInfo, to[0]);
		}
		
		
		
		
		
	}

	public SmsSender getSmsSender() {
		return smsSender;
	}
	public void setSmsSender(SmsSender smsSender) {
		this.smsSender = smsSender;
	}
	

}

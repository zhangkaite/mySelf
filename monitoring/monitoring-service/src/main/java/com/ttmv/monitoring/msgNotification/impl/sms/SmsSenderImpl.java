package com.ttmv.monitoring.msgNotification.impl.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.msgNotification.entity.MailSenderInfo;
import com.ttmv.monitoring.msgNotification.entity.SmsServerInfo;
import com.ttmv.monitoring.msgNotification.inf.SmsSender;
import com.ttmv.monitoring.msgNotification.tools.MsgConstant;
import com.ttmv.monitoring.msgNotification.tools.SmsEncode;
import com.ttmv.monitoring.msgNotification.tools.StringUtils;
import com.ttmv.monitoring.tools.constant.SmsConstant;

@SuppressWarnings("unused")
public class SmsSenderImpl implements SmsSender {
	private static Logger logger = LogManager.getLogger(SmsSenderImpl.class);

	//单发
	public void sendSms(SmsServerInfo smsServerInfo, String mobilePhones)
			throws Exception {
		smsServerInfo.setDa("86" + mobilePhones);
		doSend(smsServerInfo);
	}

	//群发
	public void sendSms(SmsServerInfo smsServerInfo, String[] mobilePhones)throws Exception {
		if (ArrayUtils.isEmpty(mobilePhones)) {
			throw new Exception("手机号码不能为空");
		}
		StringBuffer da = new StringBuffer("");
		//拼接群发手机号字符串（86xxxx;86xxxx）
		for (int i = 0; i < mobilePhones.length; i++) {
			da.append("86");
			da.append(mobilePhones[i]);
			da.append(",");
		}
		logger.debug("\n群发手机列表:["+da.toString()+"]");
		smsServerInfo.setDa(da.toString());
		doSend(smsServerInfo);
	}
	
	/** 
     * @param content  
     * @param phoneNo 
     * @return 
     * @throws CheckException 
     */  
    private String doSend(SmsServerInfo smsServerInfo) throws Exception{  
        // 使用httpclient模拟http请求  
        HttpClient client = new HttpClient();  
        // 设置参数编码  
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");  
        PostMethod method = new PostMethod(smsServerInfo.getUrl());  
        method.addParameter("command", smsServerInfo.getCommand());  
        method.addParameter("cpid", smsServerInfo.getCpid());  //账号
        method.addParameter("cppwd", smsServerInfo.getCppwd());  //密码
        method.addParameter("da", smsServerInfo.getDa()); //拼手机号
        method.addParameter("dc", smsServerInfo.getDc()); //编码设置
        method.addParameter("sm", SmsEncode.encodeHexStr(15, smsServerInfo.getSm()));  //短信内容
        System.out.println("-----------短信内容-------"+smsServerInfo.getSm());
        BufferedReader br = null;  
        String reponse  = null;  
        try {  
            int returnCode = client.executeMethod(method);  
            if (returnCode != HttpStatus.SC_OK) {  
                throw new Exception("短信接口异常");  
            }  
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));  
            reponse = br.readLine();  
            logger.debug(" \n短信平台返回信息:"+reponse);
            String[] str = reponse.split("=");
            String responseCode = str[str.length -1];  
            if (!"000".equals(responseCode)){  
            	if(responseCode.equals(MsgConstant.MTERRCODE_0101)){
            		throw new Exception("[" + responseCode + "]无效的commandd参数!!!"); 
            	}else if(responseCode.equals(MsgConstant.MTERRCODE_0100)){
            		throw new Exception("[" + responseCode + "]请求参数错误!!!"); 
            	}else if(responseCode.equals(MsgConstant.MTERRCODE_0104)){
            		throw new Exception("[" + responseCode + "]账号信息错误!!!"); 
            	}else if(responseCode.equals(MsgConstant.MTERRCODE_0106)){
            		throw new Exception("[" + responseCode + "]账号密码错误!!!"); 
            	}else if(responseCode.equals(MsgConstant.MTERRCODE_0110)){
            		throw new Exception("[" + responseCode + "]目标号码格式错误或群发号码数量超过100个!!!"); 
            	}else if(responseCode.equals(MsgConstant.MTERRCODE_0600)){
            		throw new Exception("[" + responseCode + "]未知错误!!!"); 
            	}
            }  
            logger.info("\n[" + responseCode + "]短信发送成功！！！ && [手机号列表:]" + smsServerInfo.getDa());
  
        } catch (Exception e) {  
            logger.error("doSend(String, String)", e);  
        } finally {  
            method.releaseConnection();  
            if (br != null)  
                try {  
                    br.close();  
                } catch (Exception e1) {  
                    logger.error("doSend(String, String)", e1);  
                    e1.printStackTrace();  
                }  
        }  
  
        return reponse;  
    }  
	

	public void sender(String title, String content, String... to)
			throws Exception {

	}
	
	public static void main(String[] args) {
		SmsSenderImpl impl = new SmsSenderImpl();
		
		SmsServerInfo smsServerInfo = new SmsServerInfo();
		smsServerInfo.setUrl("http://api2.santo.cc/submit");//短信服务商地址
		smsServerInfo.setCpid("bjwq-ipxmt");//短信平台账号
		smsServerInfo.setCppwd("EKTvOsOB");//短信平台密码
		smsServerInfo.setCommand("MULTI_MT_REQUEST");
		smsServerInfo.setSm("亲爱的TT视频用户，您现在正在使用手机进行用户注册验证，验证码是["+createRandom(true, 4)+"]，本条验证码3分钟内有效。 ");//短信内容
		smsServerInfo.setDc("15");//消息内容编码（15:DBK ; 8:Unicode ; 1:ISO8859-1）
		String [] str = new String[1];
		str[0] = "18500061826";
//		str[1] = "18610416271";
//		str[2] = "18500048350"; 
		try {
			impl.sendSms(smsServerInfo, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /** 
     * 创建指定数量的随机字符串 
     * @param numberFlag 是否是数字 
     * @param length 
     * @return 
     */  
    public static String createRandom(boolean numberFlag, int length){  
     String retStr = "";  
     String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";  
     int len = strTable.length();  
     boolean bDone = true;  
     do {  
      retStr = "";  
      int count = 0;  
      for (int i = 0; i < length; i++) {  
       double dblR = Math.random() * len;  
       int intR = (int) Math.floor(dblR);  
       char c = strTable.charAt(intR);  
       if (('0' <= c) && (c <= '9')) {  
        count++;  
       }  
       retStr += strTable.charAt(intR);  
      }  
      if (count >= 2) {  
       bDone = false;  
      }  
     } while (bDone);  
     
     return retStr;  
    }  

}

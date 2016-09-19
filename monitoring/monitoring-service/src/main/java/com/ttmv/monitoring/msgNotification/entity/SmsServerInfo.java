package com.ttmv.monitoring.msgNotification.entity;
/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月22日14:21:38
 * @explain : 发送短信需要使用的基本信息 
 */
public class SmsServerInfo {
	
	//第三方短信服务地址
	private String url ;
	
	//操作命令(群发、单发)
	private String command;
	
	//用户账号
	private String cpid;
	
	//用户账号密码
	private String cppwd;
	
	//目标账号（8618500001111）
	private String da;
	
	//消息内容编码（15:DBK ; 8:Unicode ; 1:ISO8859-1）
	private String dc;
	
	//消息内容，经编码后的字符串
	private String sm;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getCppwd() {
		return cppwd;
	}

	public void setCppwd(String cppwd) {
		this.cppwd = cppwd;
	}

	public String getDa() {
		return da;
	}

	public void setDa(String da) {
		this.da = da;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}
	
	
	
}

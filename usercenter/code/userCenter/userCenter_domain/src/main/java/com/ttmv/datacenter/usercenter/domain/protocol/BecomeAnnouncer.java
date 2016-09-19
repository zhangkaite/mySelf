package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**
 * 星级主播身份变更
 * @author Damon
 *
 */
public class BecomeAnnouncer {
	
	private BigInteger userID; //用户ID
	private String time;//开通时间
	private String token;
	private String type;//操作类型 2016年2月24日11:14:19
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	

	
}

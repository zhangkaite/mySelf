package com.datacenter.dams.input.redis.entity;

import java.math.BigInteger;

/**
 * 用户在线时长
 * @author wll
 */
public class OnlineTimeSpendInfo {

	private BigInteger userID;						//用户ID
	private String type;							//用户操作类型:登陆还是退出
	private String time;							//操作时间
	
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}

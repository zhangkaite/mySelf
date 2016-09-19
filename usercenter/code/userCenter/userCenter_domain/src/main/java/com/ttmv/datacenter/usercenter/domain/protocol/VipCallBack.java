package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

public class VipCallBack {
	
	private BigInteger userID; //用户ID
	private Integer vipType; //会员标示
	private String endTime;//预计到期时间
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public Integer getVipType() {
		return vipType;
	}
	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}

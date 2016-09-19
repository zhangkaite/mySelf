package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**
 * 开通会员 
 * @time 2015年11月9日10:49:57
 * @author Damon
 *
 */

public class OpeningVIP {
	
	private BigInteger userID; //用户ID
	private Integer type; //1：开通vip  2：续费vip
	private String startTime;//开通时间
	private Integer duration;//开通时长（单位：月）
	private String vipEndTime;//到期时间
	
	private String token;//用户token
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVipEndTime() {
		return vipEndTime;
	}
	public void setVipEndTime(String vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	
	
	

}

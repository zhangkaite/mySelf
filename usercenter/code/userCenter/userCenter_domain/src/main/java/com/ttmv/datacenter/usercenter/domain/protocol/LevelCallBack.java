package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

public class LevelCallBack {
	
	private BigInteger userID; //用户ID
	private BigInteger announcerExp;//主播经验
	private BigInteger exp;//用户经验
	private Integer userLevel;//userLevel
	private Integer announcerLevel;//主播等级
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public BigInteger getAnnouncerExp() {
		return announcerExp;
	}
	public void setAnnouncerExp(BigInteger announcerExp) {
		this.announcerExp = announcerExp;
	}
	public BigInteger getExp() {
		return exp;
	}
	public void setExp(BigInteger exp) {
		this.exp = exp;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public Integer getAnnouncerLevel() {
		return announcerLevel;
	}
	public void setAnnouncerLevel(Integer announcerLevel) {
		this.announcerLevel = announcerLevel;
	}
	
	

}

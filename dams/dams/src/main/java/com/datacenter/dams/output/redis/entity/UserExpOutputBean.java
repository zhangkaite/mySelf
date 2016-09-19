package com.datacenter.dams.output.redis.entity;

import java.math.BigInteger;

/**
 * 用户主播经验变更bean
 * @author wulinli
 *
 */
public class UserExpOutputBean {

	private BigInteger userID;
	private BigInteger announcerExp;
	private BigInteger announcerLevel;
	private BigInteger userLevel;
	private BigInteger exp;
	
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
	public BigInteger getAnnouncerLevel() {
		return announcerLevel;
	}
	public void setAnnouncerLevel(BigInteger announcerLevel) {
		this.announcerLevel = announcerLevel;
	}
	public BigInteger getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(BigInteger userLevel) {
		this.userLevel = userLevel;
	}
	public BigInteger getExp() {
		return exp;
	}
	public void setExp(BigInteger exp) {
		this.exp = exp;
	}
}

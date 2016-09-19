package com.ttmv.dao.bean.query;

import java.math.BigInteger;

public class QueryBean {

	private BigInteger userId;
	private BigInteger tempId;
    private String endTime;
    private String startTime;
    private String remindTime;
    private int numType;
    
    
    
	public int getNumType() {
		return numType;
	}
	public void setNumType(int numType) {
		this.numType = numType;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getTempId() {
		return tempId;
	}
	public void setTempId(BigInteger tempId) {
		this.tempId = tempId;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
}

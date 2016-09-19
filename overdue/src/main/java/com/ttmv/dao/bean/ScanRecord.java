package com.ttmv.dao.bean;

import java.math.BigInteger;
import java.util.Date;

public class ScanRecord {
    
	private BigInteger id;
    private Integer expireType;
    private Date endTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getExpireType() {
        return expireType;
    }

    public void setExpireType(Integer expireType) {
        this.expireType = expireType;
    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
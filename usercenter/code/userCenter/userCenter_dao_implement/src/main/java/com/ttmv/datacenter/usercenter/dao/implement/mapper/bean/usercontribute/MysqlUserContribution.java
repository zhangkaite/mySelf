package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercontribute;

import java.math.BigInteger;

public class MysqlUserContribution {
    
	private BigInteger id;
	private BigInteger userId;
    private BigInteger roomId;
    private String nickName;
    private BigInteger contributionSum;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getRoomId() {
        return roomId;
    }

    public void setRoomId(BigInteger roomId) {
        this.roomId = roomId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigInteger getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(BigInteger contributionSum) {
        this.contributionSum = contributionSum;
    }
}
package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UserContribution {

    private BigInteger id;
    private BigInteger userId;
    private BigInteger roomId;
    private String nickName;
    private String userPhoto;
    private BigDecimal contributionSum;
    //��ݴ洢����
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

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

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

	public BigDecimal getContributionSum() {
		return contributionSum;
	}

	public void setContributionSum(BigDecimal contributionSum) {
		this.contributionSum = contributionSum;
	}

    
}
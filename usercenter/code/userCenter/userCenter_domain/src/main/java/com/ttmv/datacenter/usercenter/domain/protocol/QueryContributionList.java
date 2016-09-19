package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

public class QueryContributionList {
	private BigInteger roomID; //用户ID
	private String dataType;
	

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigInteger getRoomID() {
		return roomID;
	}

	public void setRoomID(BigInteger roomID) {
		this.roomID = roomID;
	}
	
	

}

package com.datacenter.dams.input.redis.entity;

import java.math.BigInteger;

/**
 * 送花信息
 * @author wll
 */
public class FlowerSpendInfo {

	private BigInteger userID;						// 用户ID
	private BigInteger destinationUserID;			//消费目标用户ID
	private String roomID;						//消费直播间ID
	private Integer count;							//送花的数量
	private String clientType;						//设备类型
	private String type;							//道具类型(花,心)
	private String time;							//消费时间
	
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public BigInteger getDestinationUserID() {
		return destinationUserID;
	}
	public void setDestinationUserID(BigInteger destinationUserID) {
		this.destinationUserID = destinationUserID;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
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

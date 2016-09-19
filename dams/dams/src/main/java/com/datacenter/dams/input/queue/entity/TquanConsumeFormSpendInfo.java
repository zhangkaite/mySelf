package com.datacenter.dams.input.queue.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TquanConsumeFormSpendInfo {

	private BigInteger userID;							// 用户ID
	private BigInteger destinationUserID;			//消费目标用户ID
	private BigInteger roomID;							//消费直播间ID
	private String time;										//消费时间
	private BigInteger productID;						//商品ID
	private Integer productCount;						//商品数量
	private BigDecimal productPrice;				//商品价格
	private BigDecimal number;								//消费总额
	private String equipID;								//道具编号
	private String orderId;									//订单编号
	private String clientType;								//设备类型
	private String userType;								//消费类型
	private String version;									//版本号
	/* 类型标示 */
	private String dataType = "tq_consume";
	
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
	public BigInteger getRoomID() {
		return roomID;
	}
	public void setRoomID(BigInteger roomID) {
		this.roomID = roomID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigInteger getProductID() {
		return productID;
	}
	public void setProductID(BigInteger productID) {
		this.productID = productID;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public BigDecimal getNumber() {
		return number;
	}
	public void setNumber(BigDecimal number) {
		this.number = number;
	}
	public String getEquipID() {
		return equipID;
	}
	public void setEquipID(String equipID) {
		this.equipID = equipID;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}

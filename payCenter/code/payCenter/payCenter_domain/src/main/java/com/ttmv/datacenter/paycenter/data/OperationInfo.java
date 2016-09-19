package com.ttmv.datacenter.paycenter.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings("serial")
public class OperationInfo extends BaseBean implements Serializable{

	private BigInteger userId; 		//用户id
	private Date time;					//交易时间
	private BigDecimal number;		//交易金额
	private String orderId;			//订单编号
	private String clientType;		//设备类型
	private String version;			//版本号
	
	private Integer type;						//交易类型（-1：消费，1：充值）
	private Integer accountType;			//账户类型（0：TB账户，1：T券账户，2：佣金账户）
	
	//T券充值
	private Integer TDNumber; //消耗T豆数量
	private Integer TquanType;				//T券充值类型
	
	//TB消费
	private BigInteger destinationUserID; //消费对象
	private String productID;						//商品编号 
	private Integer productCount;		    //商品数量
	private BigDecimal productPrice;				//商品单价
	
	//T券消费
	private String equipID;					//道具编号
	private Integer userType;								//消费类型
	
	private String roomID;//频道号
	
	
	
	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getTDNumber() {
		return TDNumber;
	}

	public void setTDNumber(Integer tDNumber) {
		TDNumber = tDNumber;
	}

	public Integer getTquanType() {
		return TquanType;
	}

	public void setTquanType(Integer tquanType) {
		TquanType = tquanType;
	}

	public BigInteger getDestinationUserID() {
		return destinationUserID;
	}

	public void setDestinationUserID(BigInteger destinationUserID) {
		this.destinationUserID = destinationUserID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
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

	public String getEquipID() {
		return equipID;
	}

	public void setEquipID(String equipID) {
		this.equipID = equipID;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}



}

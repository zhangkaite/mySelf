package com.ttmv.datacenter.paycenter.domain.protocol;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 2016年3月21日15:57:47
 * @author Damon
 * TB或TQ消费
 */
public class TQorTBConsume {
	private BigInteger userID; //用户ID
	private String time; //消费时间
	
	private BigDecimal numberTB; //消费TB总额
	private BigDecimal numberTQ; //消费TQ总额
	
	private BigInteger destinationUserID; //消费对象
	private String productID; //商品编号
	private Integer productCount; //商品数量
	
	private BigDecimal productPriceTB; //商品TB单价
	private BigDecimal productPriceTQ; //商品TQ单价
	
	private String equipID; //道具编号
	private Integer userType; //用户类型
	private String orderId;//订单编号
	private String clientType;//设备类型 
	private String version;//版本号
	private String roomID;//频道号
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigDecimal getNumberTB() {
		return numberTB;
	}
	public void setNumberTB(BigDecimal numberTB) {
		this.numberTB = numberTB;
	}
	public BigDecimal getNumberTQ() {
		return numberTQ;
	}
	public void setNumberTQ(BigDecimal numberTQ) {
		this.numberTQ = numberTQ;
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
	public BigDecimal getProductPriceTB() {
		return productPriceTB;
	}
	public void setProductPriceTB(BigDecimal productPriceTB) {
		this.productPriceTB = productPriceTB;
	}
	public BigDecimal getProductPriceTQ() {
		return productPriceTQ;
	}
	public void setProductPriceTQ(BigDecimal productPriceTQ) {
		this.productPriceTQ = productPriceTQ;
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
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	
	
	
}

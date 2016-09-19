package com.datacenter.dams.business.dao.redis.bean.hdfs;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TquanConsumeSpendRedisQueueToHdfs {

	private String spendId;					//消费者ID
	private String spendToId;				//消费者消费对象ID
	private Float tb;								//消费TB数量
	private Float tq;								//消费TQ数量
	private Long time;							//消费时间
	
	private BigInteger userID;							// 用户ID
	private BigInteger destinationUserID;			//消费目标用户ID
	private BigInteger roomID;							//消费直播间ID
	private BigInteger productID;						//商品ID
	private Integer productCount;						//商品数量
	private BigDecimal productPrice;				//商品价格
	private Integer number;								//消费总额
	private String equipID;								//道具编号
	private String orderId;									//订单编号
	private String clientType;								//设备类型
	private String userType;								//消费类型
	private String version;									//版本号
	
	public String getSpendId() {
		return spendId;
	}
	public void setSpendId(String spendId) {
		this.spendId = spendId;
	}
	public String getSpendToId() {
		return spendToId;
	}
	public void setSpendToId(String spendToId) {
		this.spendToId = spendToId;
	}
	public Float getTb() {
		return tb;
	}
	public void setTb(Float tb) {
		this.tb = tb;
	}
	public Float getTq() {
		return tq;
	}
	public void setTq(Float tq) {
		this.tq = tq;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}

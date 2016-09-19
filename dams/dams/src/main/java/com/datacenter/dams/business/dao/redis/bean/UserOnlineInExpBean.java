package com.datacenter.dams.business.dao.redis.bean;

public class UserOnlineInExpBean {

	private String spendId;					//消费者ID
	private String spendToId;				//消费者消费对象ID
	private Float tb;						//消费TB数量
	private Float tq;						//消费TQ数量
	private Long time;						//消费时间
	
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
}

package com.datacenter.dams.input.redis.entity;

import java.math.BigInteger;

public class FlowerAndHeartTempInfo {

	private int sum;
	private BigInteger spendId;
	private BigInteger spendToId;
	private Long time;
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public BigInteger getSpendId() {
		return spendId;
	}
	public void setSpendId(BigInteger spendId) {
		this.spendId = spendId;
	}
	public BigInteger getSpendToId() {
		return spendToId;
	}
	public void setSpendToId(BigInteger spendToId) {
		this.spendToId = spendToId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}

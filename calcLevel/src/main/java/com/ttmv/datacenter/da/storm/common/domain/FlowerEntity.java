package com.ttmv.datacenter.da.storm.common.domain;

import java.math.BigInteger;

public class FlowerEntity {

	private String spendId;
	private String spendToId;
	private BigInteger sum;
	private Long time;

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

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

	public BigInteger getSum() {
		return sum;
	}

	public void setSum(BigInteger sum) {
		this.sum = sum;
	}

}

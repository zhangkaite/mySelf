package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;
import java.util.Date;

public class UserOnlineInfo extends BaseBean{

	private BigInteger id;
	private String userId;
	private String ip;
	private String mac;
	private String harddiskSn;
	private String token;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getHarddiskSn() {
		return harddiskSn;
	}

	public void setHarddiskSn(String harddiskSn) {
		this.harddiskSn = harddiskSn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
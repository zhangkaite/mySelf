package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;

public class TerminalForbid extends BaseBean{

	private BigInteger id;
	private String ip;
	private String mac;
	private String disksn;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public String getDisksn() {
		return disksn;
	}

	public void setDisksn(String disksn) {
		this.disksn = disksn;
	}
}
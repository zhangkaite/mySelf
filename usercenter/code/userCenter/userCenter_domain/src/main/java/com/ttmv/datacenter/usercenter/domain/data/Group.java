package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Group extends BaseBean{

	private BigInteger groupId;
	private BigInteger userId;
	private String name;
	private Integer gorder;
	private Integer defaultType;
	
	public BigInteger getGroupId() {
		return groupId;
	}

	public void setGroupId(BigInteger groupId) {
		this.groupId = groupId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getGorder() {
		return gorder;
	}

	public void setGorder(Integer gorder) {
		this.gorder = gorder;
	}
}
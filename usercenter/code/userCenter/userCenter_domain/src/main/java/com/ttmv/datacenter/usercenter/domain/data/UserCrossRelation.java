package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserCrossRelation extends BaseBean{

	private BigInteger id;
	private BigInteger userIdA;
	private BigInteger userIdB;
	private BigInteger groupId;
	private Integer type; 
	private String remarkName;//好友备注名称
	
	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserIdA() {
		return userIdA;
	}

	public void setUserIdA(BigInteger userIdA) {
		this.userIdA = userIdA;
	}

	public BigInteger getUserIdB() {
		return userIdB;
	}

	public void setUserIdB(BigInteger userIdB) {
		this.userIdB = userIdB;
	}

	public BigInteger getGroupId() {
		return groupId;
	}

	public void setGroupId(BigInteger groupId) {
		this.groupId = groupId;
	}
}
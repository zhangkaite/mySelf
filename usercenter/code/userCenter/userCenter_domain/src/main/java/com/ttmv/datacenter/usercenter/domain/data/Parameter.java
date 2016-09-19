package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;
import java.util.Date;

public class Parameter extends BaseBean{

	private BigInteger id;

	private String type;
	
	private String key;
	
	private String value;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
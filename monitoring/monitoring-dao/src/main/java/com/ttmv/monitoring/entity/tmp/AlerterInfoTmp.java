package com.ttmv.monitoring.entity.tmp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AlerterInfoTmp {

	private BigInteger id;					//id
    private String alerterName;			//报警器名称
    private String alerterType;			//通知类型
    private Integer alerterCount;		//通知次数	
    private List<String> alerterUser = new ArrayList<String>();			//通知用户列表
    private Integer alerterMsg;
    
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getAlerterName() {
		return alerterName;
	}
	public void setAlerterName(String alerterName) {
		this.alerterName = alerterName;
	}
	public String getAlerterType() {
		return alerterType;
	}
	public void setAlerterType(String alerterType) {
		this.alerterType = alerterType;
	}
	public Integer getAlerterCount() {
		return alerterCount;
	}
	public void setAlerterCount(Integer alerterCount) {
		this.alerterCount = alerterCount;
	}
	public List<String> getAlerterUser() {
		return alerterUser;
	}
	public void setAlerterUser(List<String> alerterUser) {
		this.alerterUser = alerterUser;
	}
	public Integer getAlerterMsg() {
		return alerterMsg;
	}
	public void setAlerterMsg(Integer alerterMsg) {
		this.alerterMsg = alerterMsg;
	}
}

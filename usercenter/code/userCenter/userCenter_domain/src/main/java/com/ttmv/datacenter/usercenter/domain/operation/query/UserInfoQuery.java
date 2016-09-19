package com.ttmv.datacenter.usercenter.domain.operation.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

public class UserInfoQuery extends UserInfo {

	private int page = 1;
	private int pageSize = 10;
	private	Date startTime;
	private Date endTime;
	
	
	/* 设置排序字段：key是排序的字段，value,true是升序，false是降序*/
	private Map<String ,Boolean> sorfFields ; 

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Map<String, Boolean> getSorfFields() {
		return sorfFields;
	}

	public void setSorfFields(Map<String, Boolean> sorfFields) {
		this.sorfFields = sorfFields;
	}
}

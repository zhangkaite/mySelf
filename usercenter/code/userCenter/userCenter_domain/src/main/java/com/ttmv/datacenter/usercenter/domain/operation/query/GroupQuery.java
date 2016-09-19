package com.ttmv.datacenter.usercenter.domain.operation.query;

import java.util.Date;

import com.ttmv.datacenter.usercenter.domain.data.Group;

public class GroupQuery extends Group{

	private int page = 1;
	private int pageSize = 10;
	private	Date startTime;
	private Date endTime;
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
}

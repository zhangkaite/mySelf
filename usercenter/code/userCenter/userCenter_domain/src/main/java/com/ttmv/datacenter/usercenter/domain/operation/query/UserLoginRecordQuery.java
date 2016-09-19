package com.ttmv.datacenter.usercenter.domain.operation.query;

import java.util.Calendar;
import java.util.Date;

import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;

public class UserLoginRecordQuery extends UserLoginRecord {

	private int page = 1;
	private int pageSize = 10 ;
	private Date endTime = new Date();
	private	Date startTime = getEndDate();
	
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
	
	/*获取当前日期的上一个月的时间*/
	private Date getEndDate(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date date = c.getTime();
		return date;
	}
}

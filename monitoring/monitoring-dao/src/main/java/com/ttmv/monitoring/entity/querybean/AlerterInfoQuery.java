package com.ttmv.monitoring.entity.querybean;

import java.math.BigInteger;

public class AlerterInfoQuery {
	
	private BigInteger id;
	private String alerterName;			//key
    private Integer pageSize = 10;	    //一页的记录数
    private Integer page;					//第几页
    
    /* 此属性不需要填写 */
    private Integer start;			       //开始位置

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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
}

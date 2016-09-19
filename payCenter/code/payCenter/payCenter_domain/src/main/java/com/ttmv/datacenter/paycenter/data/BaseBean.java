package com.ttmv.datacenter.paycenter.data;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年4月23日11:32:35
 * @explain : 所有bean的父类，其中包含reqID(业务请求ID)
 */
public class BaseBean {
	
	private String reqId;//请求id(业务流水号)

	@JsonIgnore
	public String getReqId() {
		return reqId;
	}
	
	@JsonIgnore
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	

}

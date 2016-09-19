package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp;

import org.apache.solr.client.solrj.beans.Field;

import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;

public class Item {
	
	@Field
	private String id;
	@Field
	private String dataSourceKey;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}

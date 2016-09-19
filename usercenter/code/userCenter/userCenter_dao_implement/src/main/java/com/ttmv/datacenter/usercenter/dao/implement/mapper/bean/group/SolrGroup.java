package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group;

import org.apache.solr.client.solrj.beans.Field;

public class SolrGroup {

	@Field
	private String id;
	@Field
	private String userId;
	@Field
	private Integer defaultType;//默认分组
	@Field
	private String dataSourceKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
	public Integer getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}
}

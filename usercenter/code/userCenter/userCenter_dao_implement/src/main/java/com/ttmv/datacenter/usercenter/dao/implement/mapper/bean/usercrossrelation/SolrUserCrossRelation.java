package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation;

import org.apache.solr.client.solrj.beans.Field;


public class SolrUserCrossRelation {
	
	@Field
	private String id;
	@Field
	private String userIdA;
	@Field
	private String userIdB;
	@Field
	private String groupId;
	@Field
	private Integer type;//2:好友 1:关注
	@Field
	private String dataSourceKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserIdA() {
		return userIdA;
	}
	public void setUserIdA(String userIdA) {
		this.userIdA = userIdA;
	}
	public String getUserIdB() {
		return userIdB;
	}
	public void setUserIdB(String userIdB) {
		this.userIdB = userIdB;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}

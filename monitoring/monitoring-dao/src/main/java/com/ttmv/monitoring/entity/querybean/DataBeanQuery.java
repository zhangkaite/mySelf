package com.ttmv.monitoring.entity.querybean;


public class DataBeanQuery {

	private String groupField;	//分组字段
	private String ip;					//ip条件
	private String serverType;	//serverType条件
	private Integer port; 			//port条件
	private Integer sum;			//查询数量
	private String curentTime;		//当前时间
	private String previousTime;  //时间段
	
	
	public String getGroupField() {
		return groupField;
	}
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public String getPreviousTime() {
		return previousTime;
	}
	public void setPreviousTime(String previousTime) {
		this.previousTime = previousTime;
	}
	public String getCurentTime() {
		return curentTime;
	}
	public void setCurentTime(String curentTime) {
		this.curentTime = curentTime;
	}
}

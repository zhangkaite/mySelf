package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 035_用户列表查询
 **/

public class QueryUsers {

private String TTnum; //TT号码
private String keyword; //查询关键字
private Integer type; //用户类型
private String nickName; //昵称
private Integer pageSize; //每页行数
private Integer vipType; //会员类型
private String startTime; //注册开始时段
private String endTime; //注册结束时段
private Integer page; //当前页数
private Integer uStatus; //0、正常；1、冻结；2、删除
//private Integer status;

public String getTTnum() {
	return TTnum;
}
public void setTTnum(String tTnum) {
	TTnum = tTnum;
}
public String getKeyword() {
	return keyword;
}
public void setKeyword(String keyword) {
	this.keyword = keyword;
}
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public String getNickName() {
	return nickName;
}
public void setNickName(String nickName) {
	this.nickName = nickName;
}
public Integer getPageSize() {
	return pageSize;
}
public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}
public Integer getVipType() {
	return vipType;
}
public void setVipType(Integer vipType) {
	this.vipType = vipType;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
public Integer getPage() {
	return page;
}
public void setPage(Integer page) {
	this.page = page;
}
//public Integer getStatus() {
//	return status;
//}
//public void setStatus(Integer status) {
//	this.status = status;
//}
public Integer getUStatus() {
	return uStatus;
}
public void setUStatus(Integer uStatus) {
	this.uStatus = uStatus;
}





}

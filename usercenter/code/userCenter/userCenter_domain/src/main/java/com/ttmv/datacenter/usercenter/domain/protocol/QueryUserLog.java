package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 032_用户上线记录查询
 **/

public class QueryUserLog {

private BigInteger userID; //用户ID
private BigInteger TTnum; //TT号
private Integer type; //类型
private String startTime; //查询时间段的开始时间
private String endTime; //查询时间段的结束时间
private Integer pageSize; //每页行数
private Integer page; //当前页数
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setTTnum (BigInteger TTnum) {
    this.TTnum = TTnum;
}
public BigInteger getTTnum() { 
     return this.TTnum;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setStartTime (String startTime) {
    this.startTime = startTime;
}
public String getStartTime() { 
     return this.startTime;
}
public void setEndTime (String endTime) {
    this.endTime = endTime;
}
public String getEndTime() { 
     return this.endTime;
}
public void setPageSize (Integer pageSize) {
    this.pageSize = pageSize;
}
public Integer getPageSize() { 
     return this.pageSize;
}
public void setPage (Integer page) {
    this.page = page;
}
public Integer getPage() { 
     return this.page;
}
}

package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 025_删除、冻结用户
 **/

public class ForbiddenUser {

private BigInteger userID; //用户ID
private Integer type; //操作类型
private String reason; //原因说明
private Integer handlerID; //操作者ID
private String startTime; //开始冻结时间
private Integer duration; //冻结时长
private String forbiddenEndTime;//冻结结束时间




public String getForbiddenEndTime() {
	return forbiddenEndTime;
}
public void setForbiddenEndTime(String forbiddenEndTime) {
	this.forbiddenEndTime = forbiddenEndTime;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setHandlerID (Integer handlerID) {
    this.handlerID = handlerID;
}
public Integer getHandlerID() { 
     return this.handlerID;
}
public void setStartTime (String startTime) {
    this.startTime = startTime;
}
public String getStartTime() { 
     return this.startTime;
}
public void setDuration (Integer duration) {
    this.duration = duration;
}
public Integer getDuration() { 
     return this.duration;
}
}

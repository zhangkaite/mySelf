package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 044_用户靓号状态修改
 **/

public class GoodTTnumManage {

private BigInteger userID; //用户ID
private String goodTTnum; //靓号号码
private Integer type; //操作类型
private String time; //操作时间
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setGoodTTnum (String goodTTnum) {
    this.goodTTnum = goodTTnum;
}
public String getGoodTTnum() { 
     return this.goodTTnum;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
}

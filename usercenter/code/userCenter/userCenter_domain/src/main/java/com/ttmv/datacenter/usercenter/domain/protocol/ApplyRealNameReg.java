package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 005_申请实名认证
 **/

public class ApplyRealNameReg {

private BigInteger userID; //用户ID
private String realname; //真实姓名
private String idcard; //身份证照片
private String handfront; //手持身份证正面照
private String handback; //手持身份证反面照
private String handNum; //身份证号码
private String phone; //联系电话
private String address; //详细地址
private String qq; //qq
private String time; //申请时间
private String validdate; //身份证有效日期
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setRealname (String realname) {
    this.realname = realname;
}
public String getRealname() { 
     return this.realname;
}
public void setIdcard (String idcard) {
    this.idcard = idcard;
}
public String getIdcard() { 
     return this.idcard;
}
public void setHandfront (String handfront) {
    this.handfront = handfront;
}
public String getHandfront() { 
     return this.handfront;
}
public void setHandback (String handback) {
    this.handback = handback;
}
public String getHandback() { 
     return this.handback;
}
public void setHandNum (String handNum) {
    this.handNum = handNum;
}
public String getHandNum() { 
     return this.handNum;
}
public void setPhone (String phone) {
    this.phone = phone;
}
public String getPhone() { 
     return this.phone;
}
public void setAddress (String address) {
    this.address = address;
}
public String getAddress() { 
     return this.address;
}
public void setQq (String qq) {
    this.qq = qq;
}
public String getQq() { 
     return this.qq;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
public void setValiddate (String validdate) {
    this.validdate = validdate;
}
public String getValiddate() { 
     return this.validdate;
}
}

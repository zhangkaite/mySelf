package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 000_管控添加用户
 **/

public class AdminAddUser {

private String userName; //登陆账户名
private String mobile; //手机号
private String email; //邮箱
private String password; //密码
private Integer type; //用户类型
private BigInteger adminId; //管理员ID
private String reason; //添加原因说明
private BigInteger TTnum; //TT号
private String nickName; //昵称
private Integer sex; //性别
private String name; //官方用户姓名
private String userPhoto; //用户头像
private String time; //注册时间
public void setUserName (String userName) {
    this.userName = userName;
}
public String getUserName() { 
     return this.userName;
}
public void setMobile (String mobile) {
    this.mobile = mobile;
}
public String getMobile() { 
     return this.mobile;
}
public void setEmail (String email) {
    this.email = email;
}
public String getEmail() { 
     return this.email;
}
public void setPassword (String password) {
    this.password = password;
}
public String getPassword() { 
     return this.password;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setAdminId (BigInteger adminId) {
    this.adminId = adminId;
}
public BigInteger getAdminId() { 
     return this.adminId;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setTTnum (BigInteger TTnum) {
    this.TTnum = TTnum;
}
public BigInteger getTTnum() { 
     return this.TTnum;
}
public void setNickName (String nickName) {
    this.nickName = nickName;
}
public String getNickName() { 
     return this.nickName;
}
public void setSex (Integer sex) {
    this.sex = sex;
}
public Integer getSex() { 
     return this.sex;
}
public void setName (String name) {
    this.name = name;
}
public String getName() { 
     return this.name;
}
public void setUserPhoto (String userPhoto) {
    this.userPhoto = userPhoto;
}
public String getUserPhoto() { 
     return this.userPhoto;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
}

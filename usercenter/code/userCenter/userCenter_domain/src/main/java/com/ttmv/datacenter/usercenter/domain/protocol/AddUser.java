package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 001_用户注册
 **/

public class AddUser {

private Integer type; //用户注册类型
private String userName; //登陆账户名
private String mobile; //手机号
private String email; //邮箱
private String password; //密码
private Integer adminId; //管理员ID
private String reason; //原因说明
private Integer TTnum; //TT号
private String nickName; //昵称
private Integer sex; //性别
private String userPhoto; //用户头像
private String key; //第三方登陆注册令牌
private Integer enrollTerminal; //注册端
private String time; //注册时间
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
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
public void setAdminId (Integer adminId) {
    this.adminId = adminId;
}
public Integer getAdminId() { 
     return this.adminId;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setTTnum (Integer TTnum) {
    this.TTnum = TTnum;
}
public Integer getTTnum() { 
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
public void setUserPhoto (String userPhoto) {
    this.userPhoto = userPhoto;
}
public String getUserPhoto() { 
     return this.userPhoto;
}
public void setKey (String key) {
    this.key = key;
}
public String getKey() { 
     return this.key;
}
public void setEnrollTerminal (Integer enrollTerminal) {
    this.enrollTerminal = enrollTerminal;
}
public Integer getEnrollTerminal() { 
     return this.enrollTerminal;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
}

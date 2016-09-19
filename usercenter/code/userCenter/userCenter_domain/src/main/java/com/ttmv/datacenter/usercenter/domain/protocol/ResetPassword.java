package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 002_用户密码修改
 **/

public class ResetPassword {

private Integer type; //密码修改类型
private String userName; //用户名
private String phone; //开通登录的手机号
private String email; //开通登录的邮箱
private String oldPassword; //原密码
private String newPassword; //新密码
private Integer admainId; //管理员ID
private String reason; //修改原因说明
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
public void setPhone (String phone) {
    this.phone = phone;
}
public String getPhone() { 
     return this.phone;
}
public void setEmail (String email) {
    this.email = email;
}
public String getEmail() { 
     return this.email;
}
public void setOldPassword (String oldPassword) {
    this.oldPassword = oldPassword;
}
public String getOldPassword() { 
     return this.oldPassword;
}
public void setNewPassword (String newPassword) {
    this.newPassword = newPassword;
}
public String getNewPassword() { 
     return this.newPassword;
}
public void setAdmainId (Integer admainId) {
    this.admainId = admainId;
}
public Integer getAdmainId() { 
     return this.admainId;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
}

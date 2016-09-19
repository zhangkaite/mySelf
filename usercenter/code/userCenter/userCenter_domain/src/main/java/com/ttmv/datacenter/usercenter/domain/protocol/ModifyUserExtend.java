package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 003_用户资料修改
 **/

public class ModifyUserExtend {

private Integer type; //用户信息修改类型
private BigInteger userID; //用户ID
private String mobile; //手机号
private String email; //邮箱
private String nickName; //昵称
private Integer sex; //性别
private String smalllogo; //用户小头像
private String logo; //用户头像
private String biglogo; //用户大头像
private String telephone; //固定电话
private String qq; //QQ号
private String sign; //个性签名
private String birthday; //生日
private Integer constellation; //星座
private Integer zodiac; //生肖
private String job; //工作
private String interest; //兴趣爱好
private String emotion; //情感状态
private String city; //所在城市
private String address; //详细地址
private String education; //学历
private String profession; //职业
private String industry; //行业
private String preferred; //偏爱
private String explain; //个人说明
private Integer income; //收入
private BigInteger admainId; //管理员ID
private String reason; //修改原因说明
private String password; //密码
private String name; //官方姓名

//------Damon 2015年12月16日20:17:46------
//2015年7月8日11:16:42 @Damon
private Integer vipType; //会员标示

//2015年8月10日17:22:59 @Damon
private String token;//用户token




public Integer getVipType() {
	return vipType;
}
public void setVipType(Integer vipType) {
	this.vipType = vipType;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}

//Damon 2015年12月16日20:17:46
//public Integer getVipType() {
//	return vipType;
//}
//public void setVipType(Integer vipType) {
//	this.vipType = vipType;
//}
public String getInterest() {
	return interest;
}
public void setInterest(String interest) {
	this.interest = interest;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
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
public void setSmalllogo (String smalllogo) {
    this.smalllogo = smalllogo;
}
public String getSmalllogo() { 
     return this.smalllogo;
}
public void setLogo (String logo) {
    this.logo = logo;
}
public String getLogo() { 
     return this.logo;
}
public void setBiglogo (String biglogo) {
    this.biglogo = biglogo;
}
public String getBiglogo() { 
     return this.biglogo;
}
public void setTelephone (String telephone) {
    this.telephone = telephone;
}
public String getTelephone() { 
     return this.telephone;
}
public void setQq (String qq) {
    this.qq = qq;
}
public String getQq() { 
     return this.qq;
}
public void setSign (String sign) {
    this.sign = sign;
}
public String getSign() { 
     return this.sign;
}
public void setBirthday (String birthday) {
    this.birthday = birthday;
}
public String getBirthday() { 
     return this.birthday;
}
public void setConstellation (Integer constellation) {
    this.constellation = constellation;
}
public Integer getConstellation() { 
     return this.constellation;
}
public void setZodiac (Integer zodiac) {
    this.zodiac = zodiac;
}
public Integer getZodiac() { 
     return this.zodiac;
}
public void setJob (String job) {
    this.job = job;
}
public String getJob() { 
     return this.job;
}
public void setEmotion (String emotion) {
    this.emotion = emotion;
}
public String getEmotion() { 
     return this.emotion;
}
public void setCity (String city) {
    this.city = city;
}
public String getCity() { 
     return this.city;
}
public void setAddress (String address) {
    this.address = address;
}
public String getAddress() { 
     return this.address;
}
public void setEducation (String education) {
    this.education = education;
}
public String getEducation() { 
     return this.education;
}
public void setProfession (String profession) {
    this.profession = profession;
}
public String getProfession() { 
     return this.profession;
}
public void setIndustry (String industry) {
    this.industry = industry;
}
public String getIndustry() { 
     return this.industry;
}
public void setPreferred (String preferred) {
    this.preferred = preferred;
}
public String getPreferred() { 
     return this.preferred;
}
public void setExplain (String explain) {
    this.explain = explain;
}
public String getExplain() { 
     return this.explain;
}
public void setIncome (Integer income) {
    this.income = income;
}
public Integer getIncome() { 
     return this.income;
}

public BigInteger getAdmainId() {
	return admainId;
}
public void setAdmainId(BigInteger admainId) {
	this.admainId = admainId;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setPassword (String password) {
    this.password = password;
}
public String getPassword() { 
     return this.password;
}
public void setName (String name) {
    this.name = name;
}
public String getName() { 
     return this.name;
}

}

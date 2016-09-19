package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo;

import java.math.BigInteger;
import java.util.Date;

public class HbaseUserInfo {
	
	private BigInteger userid;
	private BigInteger adminId;
	private String reason;
	private String nickName;
	private Integer sex;
	private Date time;
	private String userPhoto;
	private String mobile;
	private String email;
	private BigInteger exp;
	private Integer enrollterminal;
	private String QQ;
	private Integer constellation;
	private Integer zodiac;
	private String job;
	private String interest;
	private String emotion;
	private String city;
	private String address;
	private String biglogo;
	private String smalllogo;
	private String sign;
	private String telephone;
	private String education;
	private String profession;
	private String industry;
	private String preferred;
	private String explain;
	private Integer income;
	private Date birthday;
	private String MAC; 
	private String IP;  
	private String HDnum; 
	private Integer utype;//用户类型
	private String goodTTnum;//用户持有靓号
	private String realName;//真实姓名
	private String idcard;//身份证照片
	private String handfront;//手持身份证正面照
	private String handback;//手持身份证反面
	private String idcardNum;//身份证号码
	private String openId;//第三方登陆token
	private Integer sdkType;//第三方登陆平台
	private String cardNo;//银行卡号	
	private String bankRealName;//开户人	
	private String bankName; //开户行	
	private String bankAddress;//开户行地址
	
	private Integer dndType;//免打扰设置类型（1：所有人可添加、2：回答问题、3：拒绝任何人添加）
	private Integer problemType;//问题类型（1：家乡是？ 2：职业是？ 3：名字是？ 4 ：手机是？）
	private String answer;//问题答案
	
	private Date vipEndTime;//会员到期时间
	private Date forbiddenEndTime;//用户冻结结束时间
	
	//Damon 2015年12月16日19:42:43
	private String announcerType;//主播标示 （Y:主播 其他情况：均为非主播）
	private Integer announcerLevel;//主播等级
	private Integer userLevel;//用户等级
	private BigInteger announcerExp;//主播经验
	
	
	public Date getForbiddenEndTime() {
		return forbiddenEndTime;
	}

	public void setForbiddenEndTime(Date forbiddenEndTime) {
		this.forbiddenEndTime = forbiddenEndTime;
	}

	public BigInteger getAdminId() {
		return adminId;
	}

	public void setAdminId(BigInteger adminId) {
		this.adminId = adminId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public Integer getEnrollterminal() {
		return enrollterminal;
	}

	public void setEnrollterminal(Integer enrollterminal) {
		this.enrollterminal = enrollterminal;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public Integer getConstellation() {
		return constellation;
	}

	public void setConstellation(Integer constellation) {
		this.constellation = constellation;
	}

	public Integer getZodiac() {
		return zodiac;
	}

	public void setZodiac(Integer zodiac) {
		this.zodiac = zodiac;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBiglogo() {
		return biglogo;
	}

	public void setBiglogo(String biglogo) {
		this.biglogo = biglogo;
	}

	public String getSmalllogo() {
		return smalllogo;
	}

	public void setSmalllogo(String smalllogo) {
		this.smalllogo = smalllogo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preferred) {
		this.preferred = preferred;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getHDnum() {
		return HDnum;
	}

	public void setHDnum(String hDnum) {
		HDnum = hDnum;
	}

	public Integer getUtype() {
		return utype;
	}

	public void setUtype(Integer utype) {
		this.utype = utype;
	}

	public String getGoodTTnum() {
		return goodTTnum;
	}

	public void setGoodTTnum(String goodTTnum) {
		this.goodTTnum = goodTTnum;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getHandfront() {
		return handfront;
	}

	public void setHandfront(String handfront) {
		this.handfront = handfront;
	}

	public String getHandback() {
		return handback;
	}

	public void setHandback(String handback) {
		this.handback = handback;
	}

	public String getIdcardNum() {
		return idcardNum;
	}

	public void setIdcardNum(String idcardNum) {
		this.idcardNum = idcardNum;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigInteger getUserid() {
		return userid;
	}

	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankRealName() {
		return bankRealName;
	}

	public void setBankRealName(String bankRealName) {
		this.bankRealName = bankRealName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Integer getDndType() {
		return dndType;
	}

	public void setDndType(Integer dndType) {
		this.dndType = dndType;
	}

	public Integer getProblemType() {
		return problemType;
	}

	public void setProblemType(Integer problemType) {
		this.problemType = problemType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getSdkType() {
		return sdkType;
	}

	public void setSdkType(Integer sdkType) {
		this.sdkType = sdkType;
	}

	public Date getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}

	public BigInteger getExp() {
		return exp;
	}

	public void setExp(BigInteger exp) {
		this.exp = exp;
	}

	public String getAnnouncerType() {
		return announcerType;
	}

	public void setAnnouncerType(String announcerType) {
		this.announcerType = announcerType;
	}

	public Integer getAnnouncerLevel() {
		return announcerLevel;
	}

	public void setAnnouncerLevel(Integer announcerLevel) {
		this.announcerLevel = announcerLevel;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public BigInteger getAnnouncerExp() {
		return announcerExp;
	}

	public void setAnnouncerExp(BigInteger announcerExp) {
		this.announcerExp = announcerExp;
	}
	
	
	
}

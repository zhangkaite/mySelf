package com.ttmv.datacenter.usercenter.domain.data;

import java.math.BigInteger;
import java.util.Date;

public class UserInfo extends BaseBean{

	private BigInteger userid;
	private String userName;
	private String password;
	private BigInteger TTnum;
	private String loginMobile;
	private String bindingMobile;
	private String bindingEmail;
	private String loginEmail;
	private String loginGoodTTnum;//登录靓号
	private Integer loginGoodTTnumType;
	
	private BigInteger adminId;
	private String reason;	
	private String nickName;
	private Integer sex;
	private Date time;
	private String userPhoto;
	private String mobile;
	private String email;
	private BigInteger exp;//经验
	private Integer enrollterminal;//注册端
	private String QQ;//QQ号码
	private Integer constellation;//星座
	private Integer zodiac;//生肖
	private String job;//工作
	private String interest;//兴趣爱好
	private String emotion;//情感状态
	private String city;//所在城市
	private String address;//详细地址
	private String biglogo;//大头像
	private String smalllogo;//小头像
	private String sign;//个性签名
	private String telephone;//固话
	private String education;//学历
	private String profession;//职业
	private String industry;//行业
	private String preferred;//偏爱
	private String explain;//个人说明
	private Integer income;//收入
	private Date birthday;//生日
	private String MAC;//MAC
	private String IP;//IP
	private String realName;//真实姓名
	private String HDnum;//硬盘号
	private Integer utype;//用户类型
	
	private String goodTTnum;//用户持有靓号
	private String idcard;//身份证照片
	private String handfront;//手持身份证正面照
	private String handback;//手持身份证反面
	private String idcardNum;//身份证号码
	private String openId;//第三方登陆token
	private Integer sdkType;//第三方平台类型
	private String cardNo;//银行卡号	
	private String bankRealName;//开户人	
	private String bankName; //开户行	
	private String bankAddress;//开户行地址
	
	private Integer state;//用户状态（0:正常、1:冻结、2:删除）
	private Integer vipType;//1：会员；2、非会员
	
	private Integer dndType;//免打扰设置类型（1：所有人可添加、2：回答问题、3：拒绝任何人添加）
	private Integer problemType;//问题类型（1：家乡是？ 2：职业是？ 3：名字是？ 4 ：手机是？）
	private String answer;//问题答案
	
	//Damon 2015年11月9日11:05:35
	private Date vipEndTime;//会员到期时间
	private Date forbiddenEndTime ;//冻结结束时间
	
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

	public Date getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}

	public Integer getVipType() {
		return vipType;
	}

	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
	
	public String getLoginGoodTTnum() {
		return loginGoodTTnum;
	}

	public void setLoginGoodTTnum(String loginGoodTTnum) {
		this.loginGoodTTnum = loginGoodTTnum;
	}
	
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getBiglogo() {
		return biglogo;
	}

	public void setBiglogo(String biglogo) {
		this.biglogo = biglogo;
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

	public String getInteresting() {
		return interest;
	}

	public void setInteresting(String interesting) {
		this.interest = interesting;
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

	public Integer getEnrollterminal() {
		return enrollterminal;
	}

	public void setEnrollterminal(Integer enrollterminal) {
		this.enrollterminal = enrollterminal;
	}



	public Integer getUtype() {
		return utype;
	}

	public void setUtype(Integer utype) {
		this.utype = utype;
	}

	public BigInteger getUserid() {
		return userid;
	}

	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginMobile() {
		return loginMobile;
	}

	public void setLoginMobile(String loginMobile) {
		this.loginMobile = loginMobile;
	}

	public String getBindingMobile() {
		return bindingMobile;
	}

	public void setBindingMobile(String bindingMobile) {
		this.bindingMobile = bindingMobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBindingEmail() {
		return bindingEmail;
	}

	public void setBindingEmail(String bindingEmail) {
		this.bindingEmail = bindingEmail;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public BigInteger getTTnum() {
		return TTnum;
	}

	public void setTTnum(BigInteger tTnum) {
		TTnum = tTnum;
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

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getGoodTTnum() {
		return goodTTnum;
	}

	public void setGoodTTnum(String goodTTnum) {
		this.goodTTnum = goodTTnum;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public Integer getLoginGoodTTnumType() {
		return loginGoodTTnumType;
	}

	public void setLoginGoodTTnumType(Integer loginGoodTTnumType) {
		this.loginGoodTTnumType = loginGoodTTnumType;
	}

	public Integer getSdkType() {
		return sdkType;
	}

	public void setSdkType(Integer sdkType) {
		this.sdkType = sdkType;
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
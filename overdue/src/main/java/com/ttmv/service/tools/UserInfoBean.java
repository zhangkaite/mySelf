package com.ttmv.service.tools;

public class UserInfoBean {

	private String userid;
	private String userName;
	private String TTnum;
	private String goodTTnum;// 靓号
	private String nickName;
	private Integer state;// 用户状态（0:正常、1:冻结、2:删除）
	private Integer vipType;// 1：会员；2、非会员
	private String forbiddenEndTime;// 冻结结束时间
	private String vipEndTime;// 会员到期时间
	private String nobilityEndTime;// 爵位到期时间
	private String luxuryCarEndTime;// 豪车到期时间
	private String goodNumEndTime;// 靓号到期时间
	private String paramType;
	private String luxuryCarType;// 豪车类型
	private String luxuryCarID;// 豪车ID
	private String chanelID;// 频道号
	private String currentDate;
	private String goodNumFlag;
	private int numType;


	public int getNumType() {
		return numType;
	}

	public void setNumType(int numType) {
		this.numType = numType;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getGoodNumFlag() {
		return goodNumFlag;
	}

	public void setGoodNumFlag(String goodNumFlag) {
		this.goodNumFlag = goodNumFlag;
	}

	public String getChanelID() {
		return chanelID;
	}

	public void setChanelID(String chanelID) {
		this.chanelID = chanelID;
	}

	public String getLuxuryCarID() {
		return luxuryCarID;
	}

	public void setLuxuryCarID(String luxuryCarID) {
		this.luxuryCarID = luxuryCarID;
	}

	public String getLuxuryCarType() {
		return luxuryCarType;
	}

	public void setLuxuryCarType(String luxuryCarType) {
		this.luxuryCarType = luxuryCarType;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTTnum() {
		return TTnum;
	}

	public void setTTnum(String tTnum) {
		TTnum = tTnum;
	}

	public String getGoodTTnum() {
		return goodTTnum;
	}

	public void setGoodTTnum(String goodTTnum) {
		this.goodTTnum = goodTTnum;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getVipType() {
		return vipType;
	}

	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}

	public String getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(String vipEndTime) {
		this.vipEndTime = vipEndTime;
	}

	public String getForbiddenEndTime() {
		return forbiddenEndTime;
	}

	public void setForbiddenEndTime(String forbiddenEndTime) {
		this.forbiddenEndTime = forbiddenEndTime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNobilityEndTime() {
		return nobilityEndTime;
	}

	public void setNobilityEndTime(String nobilityEndTime) {
		this.nobilityEndTime = nobilityEndTime;
	}

	public String getLuxuryCarEndTime() {
		return luxuryCarEndTime;
	}

	public void setLuxuryCarEndTime(String luxuryCarEndTime) {
		this.luxuryCarEndTime = luxuryCarEndTime;
	}

	public String getGoodNumEndTime() {
		return goodNumEndTime;
	}

	public void setGoodNumEndTime(String goodNumEndTime) {
		this.goodNumEndTime = goodNumEndTime;
	}

}

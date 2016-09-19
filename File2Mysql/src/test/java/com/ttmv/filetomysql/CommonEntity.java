package com.ttmv.filetomysql;

public class CommonEntity {

	private String platfrom; // 请求业务系统
	private String service; // 请求服务名
	private String timeStamp; // 请求时间戳
	private String tradeType; // 交易类型
	private Object reqData; // 请求业务数据
	private String reqID; // 请求标示

	public String getPlatfrom() {
		return platfrom;
	}

	public void setPlatfrom(String platfrom) {
		this.platfrom = platfrom;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Object getReqData() {
		return reqData;
	}

	public void setReqData(Object reqData) {
		this.reqData = reqData;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

}

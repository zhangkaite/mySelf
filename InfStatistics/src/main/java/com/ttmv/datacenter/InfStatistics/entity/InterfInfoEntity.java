package com.ttmv.datacenter.InfStatistics.entity;

//{"res_time":"59","protocol_type":"TCP","server_ip":"192.168.10.118",
//"req_timeStamp":"1471311683034","req_id":"36010",
//"res_code":"AAAAAAA","service_code":"queryUserFriendGroup","log_type":"INFO"}
public class InterfInfoEntity {

	private String res_time;
	private String protocol_type;
	private String server_ip;
	private String req_timeStamp;
	private String req_id;
	private String res_code;
	private String service_code;
	private String log_type;

	public String getRes_time() {
		return res_time;
	}

	public void setRes_time(String res_time) {
		this.res_time = res_time;
	}

	public String getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}

	public String getServer_ip() {
		return server_ip;
	}

	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}

	public String getReq_timeStamp() {
		return req_timeStamp;
	}

	public void setReq_timeStamp(String req_timeStamp) {
		this.req_timeStamp = req_timeStamp;
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getLog_type() {
		return log_type;
	}

	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}

}

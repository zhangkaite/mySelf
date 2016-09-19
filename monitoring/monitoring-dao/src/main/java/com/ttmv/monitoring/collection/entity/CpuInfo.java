package com.ttmv.monitoring.collection.entity;

import java.util.Date;

public class CpuInfo {
	private String id;
	private String ip;
	private String mac;
	private String cpuNo;
	private String cpuMhz;
	private String cpuVendor;
	private String cpuModel;
	private String cpuCacheSize;
	private String cpuTotalCores;
	private String cpuTotalSockets;
	private String cpuCoresPerSocket;
	private String cpuUser;
	private String cpuSys;
	private String cpuWait;
	private String cpuNice;
	private String cpuIdle;
	private String cpuTotal;
	private Date updateTime;
    private Integer pageSize = 10;	    //一页的记录数
    private Integer page;					//第几页
    private Integer start;			//开始位置
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	
	public String getCpuNo() {
		return cpuNo;
	}

	public void setCpuNo(String cpuNo) {
		this.cpuNo = cpuNo;
	}

	public String getCpuMhz() {
		return cpuMhz;
	}

	public void setCpuMhz(String cpuMhz) {
		this.cpuMhz = cpuMhz;
	}

	public String getCpuVendor() {
		return cpuVendor;
	}

	public void setCpuVendor(String cpuVendor) {
		this.cpuVendor = cpuVendor;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public String getCpuCacheSize() {
		return cpuCacheSize;
	}

	public void setCpuCacheSize(String cpuCacheSize) {
		this.cpuCacheSize = cpuCacheSize;
	}

	public String getCpuTotalCores() {
		return cpuTotalCores;
	}

	public void setCpuTotalCores(String cpuTotalCores) {
		this.cpuTotalCores = cpuTotalCores;
	}

	public String getCpuTotalSockets() {
		return cpuTotalSockets;
	}

	public void setCpuTotalSockets(String cpuTotalSockets) {
		this.cpuTotalSockets = cpuTotalSockets;
	}

	public String getCpuCoresPerSocket() {
		return cpuCoresPerSocket;
	}

	public void setCpuCoresPerSocket(String cpuCoresPerSocket) {
		this.cpuCoresPerSocket = cpuCoresPerSocket;
	}

	public String getCpuUser() {
		return cpuUser;
	}

	public void setCpuUser(String cpuUser) {
		this.cpuUser = cpuUser;
	}

	public String getCpuSys() {
		return cpuSys;
	}

	public void setCpuSys(String cpuSys) {
		this.cpuSys = cpuSys;
	}

	public String getCpuWait() {
		return cpuWait;
	}

	public void setCpuWait(String cpuWait) {
		this.cpuWait = cpuWait;
	}

	public String getCpuNice() {
		return cpuNice;
	}

	public void setCpuNice(String cpuNice) {
		this.cpuNice = cpuNice;
	}

	public String getCpuIdle() {
		return cpuIdle;
	}

	public void setCpuIdle(String cpuIdle) {
		this.cpuIdle = cpuIdle;
	}

	public String getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(String cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
 
}

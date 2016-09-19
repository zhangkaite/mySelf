package com.datacenter.dams.input.hdfs.entity;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticsDataEntity {

	private String dataType;
	private BigDecimal sumData;
	private Date time;
	private String busType;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getSumData() {
		return sumData;
	}

	public void setSumData(BigDecimal sumData) {
		this.sumData = sumData;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

}

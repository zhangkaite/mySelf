package com.ttmv.monitoring.msgNotification.entity;

/**
 * 报警提示信息
 * @author Damon
 */
public class Threshold {
	
	private String thresholdName;//阀值名称
	
	private String thresholdValue;//阀值数值
	
	private String actualValue;//实际值

	public String getThresholdName() {
		return thresholdName;
	}

	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}

	public String getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	

}

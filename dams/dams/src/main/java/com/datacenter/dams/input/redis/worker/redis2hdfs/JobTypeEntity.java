package com.datacenter.dams.input.redis.worker.redis2hdfs;

import java.util.Date;

public class JobTypeEntity {

	/**
	 * 标识日榜、周榜、月榜、总榜
	 */
	private String jobType;

	/**
	 * 标识业务类型 富豪榜或者明星榜
	 */
	private String jobBusType;

	/**
	 * job统计截至天时间
	 */
	private Date jobStatisticTime;

	/**
	 * job调用启动时间
	 */
	private Date jobRunTime;

	public String getJobBusType() {
		return jobBusType;
	}

	public void setJobBusType(String jobBusType) {
		this.jobBusType = jobBusType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Date getJobRunTime() {
		return jobRunTime;
	}

	public void setJobRunTime(Date jobRunTime) {
		this.jobRunTime = jobRunTime;
	}

	public Date getJobStatisticTime() {
		return jobStatisticTime;
	}

	public void setJobStatisticTime(Date jobStatisticTime) {
		this.jobStatisticTime = jobStatisticTime;
	}

}

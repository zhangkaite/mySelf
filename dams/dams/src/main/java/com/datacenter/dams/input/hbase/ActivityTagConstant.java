package com.datacenter.dams.input.hbase;

public interface ActivityTagConstant {
	
	String REQOCMSSERVICE="getStartActivityId";
	String DAMS="dams";
	String RESULTSUCCESSCODE="AAAAAAA";
	/**
	 * 吊牌活动redis名称前缀
	 */
	String ACTIVITYTAGREDISNAME="aty_star_zset_";
	String HBASETABLENAME="activity_star_";
	String HBASECOLUMNFAMILY="a";
	String HBASECOLUMN="rank";

}

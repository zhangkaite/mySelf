package com.ttmv.datacenter.da.storm.calcLevel.context;

public interface Constant {
	/***
	 * TB redis队列名称
	 */
	public String TBREDISQUEUENAME="da_consume_spending_que";
	
	/***
	 * 点心 redis队列名称
	 */
	public String HEARTREDISQUEUENAME="da_consume_heart_que";
	
	/***
	 * 送花 redis队列名称
	 */
	public String FLOWERREDISQUEUENAME="da_consume_flower_que";
	
	/**
	 * 在线时长
	 */
	public String ANCHORONLINEREDISQUEUENAME="da_consume_online_que";

	public static final String COUSUMEMONTHONLINEQUE="da_consume_month_online_que";
	
	
	public int TDOUNUM=20;
	public int HEARTNUM=20;
	public int FLOWERNUM=20;
	public int ONLINENUM=20;
	public int DOWNLEVELNUM=20;
	
	public String TDOUTYPE="TDOUTYPE";
	public String FLOWERTYPE="FLOWERTYPE";
	public String HEARTTYPE="HEARTTYPE";
	public String ONLINETYPE="ONLINETYPE";
	public String USERONLINETYPE="USERONLINETYPE";
	public String DOWNLEVELTYPE="DOWNLEVELTYPE";
	
	String RESULTCODE_SUCCESS = "AAAAAAA";
	
	public String OCMSURL="http://levelhost:8180/queryAllGradeRegulation";
	public String OCMSLEVELCONFIGLIST="http://levelhost:8180/queryLevelByExp";
	public String OCMSEXPBYLEVEL ="http://levelhost:8180/queryExpByLevel";
	
	String HBASETABLE="da_level";
	String COLUMNFAMILY="level";
	String COLUMSTARTNALL="starall";
	String COLUMNUSERALL="userall";
	String COLUMNSTARTMONTH="start";
	String COLUMNUSERMONTH="user";
	String COLUMNUSERLEVEL="userlevel";
	String COLUMNSTARLEVEL="starlevel";
	String COLUMNTYPE="usertype";
	
	String ANCHORFLAG="star";
	String USERFLAG="user";
	
	
	String LEVELDATA2DAMSREDISQUEUE="dams_exp_que";
	String LEVELDATA2IMREDISQUEUE="dams_levelup_que";
	
	
	String SPOUT_NAME="levelSpout";
	String CAL_LEVEL_BOLT_NAME="calLevelBolt";
	String STORE_LEVEL_BOLT_NAME="storeDataToHbase";
	

    public static final String Tuple_Field_Spending_LevelVerify = "spending_level_verification";
	int STORMSLEEPTIME=1000;
	
}

package com.ttmv.datacenter.InfStatistics.util;

public class Constants {

	  public static final String FILTER_FIELD_NAME="message";
	  public static final String ANALYSIS_FIELD_NAME="analysizData";
	  public static final String EVERY_REQ_SUM_FIELD_NAME="everyReqSumData";
	  
	  public static final String HBASE_FAMILY="InterfReq";
	  public static final String HBASE_INTERF_SUM_COLUMN="sum";
	  public static final String HBASE_INTERF_STATUS_SUCCESS="success";
	  public static final String HBASE_INTERF_STATUS_ERROR="error";
	  /**
	   * 接口访问总量统计
	   */
	  public static final String HBASE_INTERF_REQ_SUM="Interf_Req_Sum";
	  /**
	   * 接口成功总量、失败总量统计hbase表
	   */
	  public static final String HBASE_INTERF_REQ_STATUS="Interf_Req_Status";
	  /**
	   * 接口每分钟访问统计
	   */
	  public static final String HBASE_INTERF_REQ_CURRENT="Interf_Req_Current";
	  /**每个接口访问总量统计hbase表名**/
	  public static final String HBASE_INTERF_EVERY_REQ_SUM="Every_Interf_Req_Sum";
	  
	  public static final String INTER_REQ_SUM_REDISQUEUE="";
	  
	  public static final String SPOUT_NAME="kafkaSpout";
	  public static final String DATA_FILTER_BOLT_NAME="dataFilterBolt";
	  public static final String DATA_ANALYSIS_BOLT_NAME="dataAnalysisBolt";
	  public static final String DATA_STORE_REQSUM_BOLT_NAME="reqSumBolt";
	  public static final String DATA_STORE_EVERY_REQ_BOLT_NAME="everyReqBolt";
	  public static final String STORE_REQ_SUM_BOLT_NAME="reqSumBolt";
	  public static final String STORE_REQ_STATUS_BOLT_NAME="reqStatusBolt";
	  public static final String DATA_STORE_EVERY_REQSUM_BOLT_NAME="everyReqSumBolt";
	  public static final String SEND_REQSUM_BOLT="send_req_sum";
	  public static final String SEND_REQSTATUS_BOLT="send_req_status";
	  public static final String SEND_EVERY_REQSUM_BOLT="send_every_req_sum";
	  public static final String SEND_EVERY_MIN_REQSUM_BOLT="send_every_min_req_sum";
	  
	
}

package com.ttmv.datacenter.usercenter.dao.implement.mapper.userloginrecord;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userloginrecord.HbaseUserLoginRecord;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserLoginRecordQuery;

public class HbaseUserLoginRecordMapper {

	private final Logger log = LogManager.getLogger(HbaseUserLoginRecordMapper.class);
	/* 转换时间格式 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private Configuration conf =HBaseConfiguration.create();
	private static final String USERLOGINRECORD_TABLENAME = "UserLoginRecord";
	private static final String USERLOGINRECORD_COLUMN_DATA = "record_data";
	private static final Long NUM = Long.parseLong("22000319170514185");
	private SentryAgent quickSentry;
	
	/**
	 * 添加HbaseUserLoginRecord
	 * @param hbaseUserLoginRecord
	 * @throws Exception
	 */
	public void addHbaseUserLoginRecord(HbaseUserLoginRecord hbaseUserLoginRecord,String reqID)throws Exception{
		
		String ttnum = hbaseUserLoginRecord.getTTnum().toString();
		log.debug("[" + reqID + "]@@" + "[获取HbaseUserLoginRecord的TTnum！]");
		/* 将时间段 翻转 存储 */
		Long newTime = Long.parseLong(sdf.format(hbaseUserLoginRecord.getTime()));
		log.debug("[" + reqID + "]@@" + "[获取HbaseUserLoginRecord的Time！]");
		String time = String.valueOf(NUM - newTime);
		log.debug("[" + reqID + "]@@" + "[Time的进行翻转计算！]");
		String type = hbaseUserLoginRecord.getType().toString();
		/* 组装rowkey 的格式： ttnum_time_type ,ttnum_时间_登陆/退出*/
		String rowKey = ttnum + time + type;
		log.debug("[" + reqID + "]@@" + "[组装hbase的key]");
		/* 转换数据 */
		String hbaseJson = null;
		try {
			hbaseJson = JsonUtil.getObjectToJson(hbaseUserLoginRecord);
			log.debug("[" + reqID + "]@@" + "[转换hbaseUserLoginRecord为Json对象成功]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[转换hbaseUserLoginRecord为Json对象失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[转换hbaseUserLoginRecord为Json对象失败！]",e);
		}
		/* 添加表 */
		try {
			this.createHTable(reqID);
		} catch (Exception e) {
			log.error("创建hbaseUserLoginRecord失败！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
		}
		/* 添加数据 */
		try {
			this.addRow(rowKey, hbaseJson);
			log.debug("[" + reqID + "]@@" + "[hbase添加hbaseUserLoginRecord成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[hbase添加hbaseUserLoginRecord失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[hbase添加hbaseUserLoginRecord失败！]",e);
		}
	}

	/**
	 * 通过UserLoginRecordQuery进行条件查询
	 * ttnum必须存在
	 * startTime
	 * endTime 默认的是30天
	 * @param query
	 */
	public List<HbaseUserLoginRecord> selectListBySelective(UserLoginRecordQuery query)throws Exception{
		String ttnum  = query.getTTnum().toString();
		Integer type = query.getType();
		int page = query.getPage();
		int pageSize = query.getPageSize();
		String startTime = null;
		String endTime = null;
		if(query.getStartTime() != null)
			startTime = sdf.format(query.getStartTime());
		if(query.getEndTime() != null)
			endTime = sdf.format(query.getEndTime());
		List<HbaseUserLoginRecord> list = getListHasConditions(ttnum,startTime,endTime,type,page,pageSize);
		return list;
	}
	
	/**
	 * 根据条件查询HbaseUserLoginRecord
	 * 存在分页查询
	 * 已确定条件：
	 *      存在ttnum，
	 *      存在默认时间段：当前和30天以前的数据
	 * 不确定条件：
	 *      登陆或是退出的类型
	 * @param ttnum
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	private List<HbaseUserLoginRecord> getListHasConditions(String ttnum,String startTime,String endTime,Integer type,int page , int pageSize)throws Exception{
		List<HbaseUserLoginRecord> list = new ArrayList<HbaseUserLoginRecord>();
		int startIndex = (page - 1) *pageSize + 1;
		HTable htable;
		try {
			htable = new HTable(conf, USERLOGINRECORD_TABLENAME);
		} catch (IOException e) {
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("hbase连接失败！",e);
		}
		Scan scan =new Scan();
		String end = String.valueOf(NUM - Long.parseLong(startTime));
		String start = String.valueOf(NUM - Long.parseLong(endTime));
		
		/* type类型不为空 */
		if(type != null && !type.equals(0)){
			Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(".*"+type.toString()+"$"));
			scan.setFilter(filter);
		}
		
		String startKey = ttnum + start + "0";//加上结尾的数字0
		String endKey = ttnum + end  + "3";//加上结尾的数字0
		scan.setStartRow(startKey.getBytes());
		scan.setStopRow(endKey.getBytes());
		
		ResultScanner rs = null;
		try {
			rs = htable.getScanner(scan);
		} catch (IOException e) {
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("UserLoginRecord查询失败！",e);
		}
		int index = 0;
		if(rs != null ){
			for(Result r : rs ){
				index ++;
				if(index >= startIndex){					
					String json = new String(r.getValue(USERLOGINRECORD_COLUMN_DATA.getBytes(), USERLOGINRECORD_COLUMN_DATA.getBytes()));
					HbaseUserLoginRecord hbase = this.jsonConvertToHbaseUserLoginRecord(json);
					list.add(hbase);
					if(list.size() == pageSize){
						break;
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 创建表
	 * @throws Exception 
	 */
	private void createHTable(String reqID) throws Exception{
		HBaseAdmin admin  = new HBaseAdmin(conf);
		if (admin.tableExists(USERLOGINRECORD_TABLENAME)){
			log.debug("[" + reqID + "]@@" + "[UserLoginRecord表已经存在！]");
            return;
		}
		TableName tableName = TableName.valueOf(USERLOGINRECORD_TABLENAME);
        HTableDescriptor htable = new HTableDescriptor(tableName);
        HColumnDescriptor data = new HColumnDescriptor(USERLOGINRECORD_COLUMN_DATA);
        htable.addFamily(data);
        admin.createTable(htable);
        log.debug("[" + reqID + "]@@" + "[创建Hbase的UserLoginRecord表成功！]");
	}
	
	/**
	 * 表添加 row 的数据
	 * @param rowKey
	 * @param json
	 */
	private void addRow(String rowKey,String json)throws Exception{
		HConnection connection = HConnectionManager.createConnection(conf);
		HTableInterface table = connection.getTable(USERLOGINRECORD_TABLENAME);
		Put put = new Put(rowKey.getBytes());
		put.add(USERLOGINRECORD_COLUMN_DATA.getBytes(),USERLOGINRECORD_COLUMN_DATA.getBytes(),json.getBytes());
		table.put(put);
		table.close();
		connection.close();
	}
	
	/**
	 * 把json转换成HbaseUserLoginRecord对象
	 * @param json
	 * @return
	 */
	private HbaseUserLoginRecord jsonConvertToHbaseUserLoginRecord(String json)throws Exception{
		HbaseUserLoginRecord hbase = (HbaseUserLoginRecord) JsonUtil.getObjectFromJson(json, HbaseUserLoginRecord.class);
		return hbase;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

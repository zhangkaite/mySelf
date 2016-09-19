package com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo;

import java.io.IOException;
import java.text.ParseException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

public class HbaseUserInfoMapper{

	private final Logger log = LogManager.getLogger(HbaseUserInfoMapper.class);
	private static final String HBASEUSERINFO_COLUMN_DATA = "hbaseUserInfo_data";
	private SentryAgent quickSentry;
	
	/* 初始化hbase的连接 */
	private Configuration conf = HBaseConfiguration.create();
	/**
	 * 通过唯一标识“id”,查询HbaseUserInfo表的数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public HbaseUserInfo getHbaseUserInfoById(String id)throws Exception{

		HbaseUserInfo hbase = new HbaseUserInfo();
		/* get table connection */
		HTable table;
		Result result;
		try {
			table = new HTable(conf, "HbaseUserInfo");
			log.debug("hbase连接表成功!");
			Get get = new Get(id.getBytes());
			result = table.get(get);
		} catch (IOException e) {
			log.debug("hbaseUserInfo查询失败！失败的原因：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("hbaseUserInfo查询失败！失败的原因：",e);
		}
		
		try {
			if(result.size() < 1){
				return null;
			}
			String json = new String(result.getValue(HBASEUSERINFO_COLUMN_DATA.getBytes(), HBASEUSERINFO_COLUMN_DATA.getBytes()));
			log.debug("hbaseUserInfo查询成功！");
			hbase =  (HbaseUserInfo) JsonUtil.getObjectFromJson(json, HbaseUserInfo.class);
			log.debug("json转HbaseUserInfo对象成功！");
			table.close();
			return hbase;
		} catch (Exception e) {
			log.debug("hbaseUserInfo查询失败！失败的原因：",e);
			throw new Exception("hbaseUserInfo查询失败！失败的原因：",e);
		}		
	}
	
	/**
	 * 添加HbaseUserInfo
	 * 
	 * @param hbase
	 * @param userInfoId
	 * @param reqID
	 * @throws Exception 
	 */
	public void addHbaseUserInfo(HbaseUserInfo hbase,String userInfoId,String reqID)throws Exception{
		HBaseAdmin hAdmin = null;
		try {
			hAdmin = new HBaseAdmin(conf);
			log.debug("[" + reqID + "]@@" + "[Hbase连接成功]");
		} catch (Exception e) {
			log.debug("[" + reqID + "]@@" + "[Hbase连接失败]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[Hbase连接失败]",e);
		}
		/* if table is not exist */
		try {
			if(!hAdmin.tableExists("HbaseUserInfo")){
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo表不存在，添加表！]");
				//创建 Hbase表
				HTableDescriptor table = new HTableDescriptor("HbaseUserInfo".getBytes());
				HColumnDescriptor hColumn = new HColumnDescriptor(HBASEUSERINFO_COLUMN_DATA);
				table.addFamily(hColumn);
				hAdmin.createTable(table);
				log.debug("[" + reqID + "]@@" + "[添加HbaseUserInfo表成功！]");
			}
		} catch (IOException e) {
			log.debug("[" + reqID + "]@@" + "[添加HbaseUserInfo表失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[添加HbaseUserInfo表失败！]",e);
		}
		
		if(hbase != null){
			/* 添加值 */
			HTable hTable = null;
			try {
				hTable = new HTable(conf, "HbaseUserInfo");
			} catch (IOException e) {
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo连接失败！]",e);
				quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
				throw new Exception("[" + reqID + "]@@" + "[HbaseUserInfo连接失败！]",e);
			}
			Put put = new Put(userInfoId.getBytes());			
			try {
				String json = JsonUtil.getObjectToJson(hbase);
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo转换Json数据成功！]");
				put.add(HBASEUSERINFO_COLUMN_DATA.getBytes(), HBASEUSERINFO_COLUMN_DATA.getBytes(), json.getBytes());
			} catch (Exception e) {
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo转换Json数据失败！]",e);
				throw new Exception("[" + reqID + "]@@" + "[HbaseUserInfo转换Json数据失败！]",e);
			}		
			try {
				hTable.put(put);
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo添加数据成功！]");
			} catch (Exception e) {
				log.debug("[" + reqID + "]@@" + "[HbaseUserInfo添加数据失败！]",e);
				quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_HBASE_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
				throw new Exception("[" + reqID + "]@@" + "[HbaseUserInfo添加数据失败！]",e);
			} 
		}
	}
	
	/**
	 * 合成UserInfo从HbaseUserInfo
	 * @param userInfo
	 * @param hbase
	 * @return
	 * @throws ParseException 
	 */
	public UserInfo getConvertHbaseUserInfoToUserInfo(UserInfo userInfo ,HbaseUserInfo hbase) throws ParseException{
		BeanUtils.copyProperties(hbase, userInfo);
		return userInfo;
	}

	/**
	 * UserInfo值复制HbaseUserInfo对象
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public HbaseUserInfo getConvertUserInfoToHbaseUserInfo(UserInfo userInfo)throws Exception{
		if(userInfo == null){
			log.debug("UserInfo对象不能为null");
			throw new Exception("UserInfo对象不能为null");
		}
		HbaseUserInfo hbase = new HbaseUserInfo();
		BeanCopyProperties.copyProperties(userInfo, hbase, false, null);
		return hbase;
	}
	
	public void setConf(HBaseConfiguration conf) {
		this.conf = conf;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

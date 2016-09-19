package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userloginrecord.HbaseUserLoginRecord;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userloginrecord.HbaseUserLoginRecordMapper;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserLoginRecordDao;
import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserLoginRecordQuery;

public class UserLoginRecordDaoImpl implements UserLoginRecordDao {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(UserLoginRecordDaoImpl.class);
	private HbaseUserLoginRecordMapper hbaseUserLoginRecordMapper;
	/**
	 * 添加UserLoginRecord
	 */
	public void addUserLoginRecord(UserLoginRecord userLoginRecord)throws Exception {	
		try{
			HbaseUserLoginRecord hbase = new HbaseUserLoginRecord();
			BeanUtils.copyProperties(userLoginRecord, hbase);
			log.debug("[" + userLoginRecord.getReqId() + "]@@" + "[UserLoginRecord复制生成HbaseUserLoginRecord成功！]");
			hbaseUserLoginRecordMapper.addHbaseUserLoginRecord(hbase,userLoginRecord.getReqId());
			log.debug("[" + userLoginRecord.getReqId() + "]@@" + "[添加HbaseUserLoginRecord成功！]");
		}catch(Exception e){
			log.error("[" + userLoginRecord.getReqId() + "]@@" + "[添加HbaseUserLoginRecord失败！]",e);
			throw new Exception("[" + userLoginRecord.getReqId() + "]@@" + "[添加HbaseUserLoginRecord失败！]",e);
		}
		log.debug("[" + userLoginRecord.getReqId() + "]@@" + "[添加UserLoginRecord成功！结束。。]");
	}
	
	/**
	 * 查询UserLoginRecord的集合
	 */
	public List<UserLoginRecord> selectListBySelective(UserLoginRecordQuery query) throws Exception {
		List<HbaseUserLoginRecord> list = hbaseUserLoginRecordMapper.selectListBySelective(query);
		List<UserLoginRecord> listUserLoginRecord = this.getListUserLoginRecord(list);
		return listUserLoginRecord ;
	}

	/**
	 * 转换HbaseUserLoginRecord 为 UserLoginRecord
	 * @param list
	 * @return
	 */
	private List<UserLoginRecord> getListUserLoginRecord(List<HbaseUserLoginRecord> list){
		if(list == null && list.size() == 0 ){
			return null;
		}
		 List<UserLoginRecord> listUserLoginRecord = new ArrayList<UserLoginRecord>();
		for(HbaseUserLoginRecord hbase : list){
			UserLoginRecord record = new UserLoginRecord();
			BeanUtils.copyProperties(hbase, record);
			listUserLoginRecord.add(record);
		}
		return listUserLoginRecord;
	}
	
	public void setHbaseUserLoginRecordMapper(
			HbaseUserLoginRecordMapper hbaseUserLoginRecordMapper) {
		this.hbaseUserLoginRecordMapper = hbaseUserLoginRecordMapper;
	}
}

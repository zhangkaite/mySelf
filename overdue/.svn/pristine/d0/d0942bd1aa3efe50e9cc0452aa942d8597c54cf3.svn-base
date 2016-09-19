package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.inter.IRedis;

public interface IRedisScanRecordInter extends IRedis{

	/**
	 * 添加ScanRecord
	 * @param scanRecord
	 * @return
	 * @throws Exception
	 */
	public void addRedisScanRecord(ScanRecord scanRecord)throws Exception;
	
	/**
	 * 修改ScanRecord
	 * @param scanRecord
	 * @return
	 * @throws Exception
	 */
	public void updateRedisScanRecord(ScanRecord scanRecord)throws Exception;
	
	/**
	 * 删除ScanRecord
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisScanRecord(String id)throws Exception;
	
	/**
	 * 查询ScanRecord
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ScanRecord queryRedisScanRecord(String id)throws Exception;
	
	/**
	 * 时间范围查询ScanRecord
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<ScanRecord> queryRedisScanRecord(Date endTime)throws Exception;
}
package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;

import com.ttmv.dao.bean.ScanRecord;

public interface IScanRecordInter {

	/**
	 * 添加ScanRecord
	 * @param scanRecord
	 * @return
	 * @throws Exception
	 */
	public Integer addScanRecord(ScanRecord scanRecord)throws Exception;
	
	/**
	 * 修改ScanRecord
	 * @param scanRecord
	 * @return
	 * @throws Exception
	 */
	public Integer updateScanRecord(ScanRecord scanRecord)throws Exception;
	
	/**
	 * 删除ScanRecord
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteScanRecord(BigInteger id)throws Exception;
	
	/**
	 * 查询ScanRecord
	 * @param expireType
	 * @return
	 * @throws Exception
	 */
	public ScanRecord queryScanRecord(Integer expireType)throws Exception;
}
package com.ttmv.dao.service.mysql;

import java.math.BigInteger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.inter.mysql.IScanRecordInter;
import com.ttmv.dao.mapper.mysql.ScanRecordMapper;

@Service("iScanRecordInterImpl")
public class IScanRecordInterImpl implements IScanRecordInter {

	@Resource(name = "scanRecordMapper")
	private ScanRecordMapper scanRecordMapper;
	
	@Override
	public Integer addScanRecord(ScanRecord scanRecord) throws Exception {
		Integer result = scanRecordMapper.addScanRecord(scanRecord);
		return result;
	}

	@Override
	public Integer updateScanRecord(ScanRecord scanRecord) throws Exception {
		Integer result = scanRecordMapper.updateScanRecord(scanRecord);
		return result;
	}

	@Override
	public Integer deleteScanRecord(BigInteger id) throws Exception {
		Integer result = scanRecordMapper.deleteScanRecord(id);
		return result;
	}

	@Override
	public ScanRecord queryScanRecord(Integer expireType) throws Exception {
		ScanRecord result = scanRecordMapper.queryScanRecord(expireType);
		return result;
	}
}

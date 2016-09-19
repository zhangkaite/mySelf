package com.ttmv.dao.mysql;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.mapper.mysql.ScanRecordMapper;

public class TestScanRecord {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@Test
	public void addScanRecordTest()throws Exception{
		ScanRecordMapper mapper = (ScanRecordMapper)context.getBean("scanRecordMapper");
		ScanRecord scan = new ScanRecord();
		scan.setExpireType(1);
		scan.setEndTime(new Date());
		mapper.addScanRecord(scan);
	}
	
	@Test
	public void updateScanRecordTest()throws Exception{
		ScanRecordMapper mapper = (ScanRecordMapper)context.getBean("scanRecordMapper");
		ScanRecord scan = new ScanRecord();
		scan.setId(new BigInteger("1"));
		scan.setEndTime(new Date());
		scan.setExpireType(2);
		mapper.updateScanRecord(scan);
	}
	
	@Test
	public void queryScanRecordTest()throws Exception{
		ScanRecordMapper mapper = (ScanRecordMapper)context.getBean("scanRecordMapper");
		ScanRecord scan = mapper.queryScanRecord(new Integer("1"));
		System.out.println(scan.getExpireType());
	}
	
	@Test
	public void deleteScanRecordTest()throws Exception{
		ScanRecordMapper mapper = (ScanRecordMapper)context.getBean("scanRecordMapper");
		Integer result = mapper.deleteScanRecord(new BigInteger("1"));
		System.out.println(result);
	}
	
	@Test
	public void queryTest()throws Exception{
		ScanRecordMapper mapper = (ScanRecordMapper)context.getBean("scanRecordMapper");
		ScanRecord scan = mapper.queryScanRecord(new Integer("2"));
		System.out.println(scan.getExpireType());
	}
	
	
}

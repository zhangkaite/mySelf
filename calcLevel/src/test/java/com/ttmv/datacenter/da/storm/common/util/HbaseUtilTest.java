package com.ttmv.datacenter.da.storm.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import junit.framework.TestCase;

public class HbaseUtilTest extends TestCase {
	
	
	public void createTable(){
		
		try {
			HbaseUtil.createTable("test20160223", "level");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void automModifyValue(){
		try {
			//HbaseUtil.addRow("test20160222", "2", "level", "all", Long.valueOf(1));
			HbaseUtil.automModifyValue("test20160222", "4", "level", "all", "200");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getOneDataByRowKey(){
		Result result;
		try {
			
			 result=HbaseUtil.getOneDataByRowKey("da_level", "22911", "level", "starall");
			 for (Cell cell : result.rawCells()) {
	                System.out.println(
	                        "Rowkey : "+Bytes.toString(result.getRow())+
	                        "   Familiy:Quilifier : "+Bytes.toString(CellUtil.cloneQualifier(cell))+
	                        "   Value : "+new BigInteger(CellUtil.cloneValue(cell))+
	                        "   Time : "+cell.getTimestamp()
	                        );
	            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	

}

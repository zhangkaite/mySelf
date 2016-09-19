package com.datacenter.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.datacenter.dams.input.redis.worker.redis2hdfs.JobTypeEntity;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.dams.util.JsonUtil;

public class Test {
	
	public static void main(String[] args) {
		/*for (int i = 0; i < 20; i++) {
			try {
				Work.run(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		 /*Hashtable  flagDate=new Hashtable<>();
		 flagDate.put("flagDate", new Date());
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		 System.out.println(sdf.format((Date)flagDate.get("flagDate")));*/
		
		
		JobTypeEntity jobTypeEntity = new JobTypeEntity();
		jobTypeEntity.setJobType(ConsumeSpendConstant.MRWEEKTYPE);
		jobTypeEntity.setJobStatisticTime(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		jobTypeEntity.setJobRunTime(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		jobTypeEntity.setJobBusType(ConsumeSpendConstant.DAYSTATISTIC);
		try {
			System.out.println(JsonUtil.getObjectToJson(jobTypeEntity));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

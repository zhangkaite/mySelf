package com.datacenter.dams.input.hbase.work;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hbase.entity.BillInfoEntity;
import com.datacenter.dams.input.hbase.util.DataUtil;

/**
 * 调度统计昨天用户在不同房间的消费t豆，将统计完的结果写入hbase 1、从hbase表查询出所有的业务消费数据 2、根据业务区分是手机直播消费还是其他消费
 * 3、将两种不同业务的统计计算数据持久化到不同的hbase表当中
 * 
 * @author zkt
 */
@SuppressWarnings({  "rawtypes" })
public class ScheduleRankStatic extends AbsScheduleRankingStatic{

	private final Logger log = LogManager.getLogger(ScheduleRankStatic.class);
	private Configuration conf = null;
	public ScheduleRankStatic() {
		conf = HBaseConfiguration.create();
	}

	/**
	 * 执行调度的具体方法入口
	 */
	public void excute() {
		// 获取当前时间昨天的时间起始时间和结束时间
		try {
			String[] startEndTime = DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), 1));
			log.info("周榜统计每天调度执行统计时间段起始时间:" + startEndTime[0] + ";结束时间：" + startEndTime[1]);
			// 获取当前时间前一天的所有消费数据，并且对相同频道消费的t豆进行累加
			List<BillInfoEntity> dataList = scanByStartAndStopRow(HBASEOPERATIONINFO_TABLENAME, startEndTime[0],
					startEndTime[1],conf);
			// 将终端消费一天统计的结果数据写入hbase NewHbaseRankingInfo表中
			if (dataList.size()>0) {
				Map result_map = getRomConSumerInfo(dataList, BUSOTHERTYPE);
                if (result_map.size()>0){
                    initStaticDatas2Hbase(result_map, HBASERANKINGINFO_TABLENAME,HBASEUSERINFO_COLUMN_DATA,conf,false);
                }
			}
		} catch (Exception e) {
			log.error("获取当前时间的前一天时间失败，失败的原因是:", e);
		}

	}

	
	

}

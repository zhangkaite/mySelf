package com.datacenter.dams.input.hbase.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hbase.util.DataUtil;

/***
 * 每天调度一次统计 对已统计出来的数据 取当前时间前29天的数据汇总
 * 
 * @author kate
 *
 */
@SuppressWarnings("rawtypes")
public class MonthScheldRankingStatic extends AbsScheduleRankingStatic {
	private final Logger log = LogManager.getLogger(MonthScheldRankingStatic.class);
	private Configuration conf = null;
	// 设置要查询的历史数据的天数
	private String queryDays;
    //汇总的结果存储hbase表名
	private String dataStoreHbaseName;
    //查询要统计的hbase表名
	private String queryHbaseName;
	public MonthScheldRankingStatic() {
		conf = HBaseConfiguration.create();
	}
	
	@Override
	public void excute() {
		try {
			List<Map> ls = new ArrayList<Map>();
			for (int i = 1; i <= Integer.parseInt(queryDays); i++) {
				String[] start_end_time = DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), i));
				Map history_result_map = scanByStartEndTime(queryHbaseName, start_end_time[0], start_end_time[1], conf);
				if (null != history_result_map && history_result_map.size() > 0) {
					ls.add(history_result_map);
				}
			}
			// 对29天的数据进行合并
			Map result_map = mergeMap(ls);
			initStaticDatas2Hbase(result_map, dataStoreHbaseName, "monthStatisctics", conf,true);
		} catch (Exception e) {
			log.error("存储每天统计结果的前29天数据失败，失败的原因是:", e);
		}

	}

	public String getQueryDays() {
		return queryDays;
	}

	public void setQueryDays(String queryDays) {
		this.queryDays = queryDays;
	}

	public String getDataStoreHbaseName() {
		return dataStoreHbaseName;
	}

	public void setDataStoreHbaseName(String dataStoreHbaseName) {
		this.dataStoreHbaseName = dataStoreHbaseName;
	}

	public String getQueryHbaseName() {
		return queryHbaseName;
	}

	public void setQueryHbaseName(String queryHbaseName) {
		this.queryHbaseName = queryHbaseName;
	}

}

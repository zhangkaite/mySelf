package com.datacenter.dams.input.hbase.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.hbase.ActivityTagConstant;
import com.datacenter.dams.input.hbase.service.ActivityTagRedis2HbaseService;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.worker.worker.QuartzWorker;

import redis.clients.jedis.Tuple;

/**
 * 
 * @author kate 1、通过http请求获取当前时间所有正在运行的活动ID
 *         2、根据获取的活动ID，获取redis对应的活动队列，获取队列所有数据，根据主播id更新hbase表里面排行数据
 *
 */
@SuppressWarnings("rawtypes")
@DisallowConcurrentExecution
public class ActivityTagsRedisUpHbase extends QuartzWorker {
	private static Logger logger = LogManager.getLogger(ActivityTagsRedisUpHbase.class);
	private static Configuration config = HBaseConfiguration.create();

	@Override
	protected List getData(int num) {
		//logger.debug("更新hbase吊牌活动主播排行work启动================");
		List<String> activityIdList = null;
		// 调用http请求获取正在运行的吊牌活动列表
		try {
			// 获取ocms传递的数据
			String resData = ActivityTagRedis2HbaseService.getActivityTagOcmsHttpRes().excute(null);
			JSONObject jsonObject = new JSONObject(resData);
			// {"resultID":1453348356690001,"resultCode":"AAAAAAA","resData":{"activities":"[132,131]"}}
			String resultCode = jsonObject.getString("resultCode");
			if (ActivityTagConstant.RESULTSUCCESSCODE.equals(resultCode)) {
				String object=jsonObject.getString("resData");
				if (null!=object&&!"null".equals(object)) {
					JSONObject resultObject=jsonObject.getJSONObject("resData");
					activityIdList=new ArrayList<>();
					JSONArray jsonArray = resultObject.getJSONArray("activities");
					for (int i = 0; i < jsonArray.length(); i++) {
						String actiovityId = jsonArray.get(i).toString();
						activityIdList.add(actiovityId);
					}
				}
				
			}

		} catch (Exception e) {
			logger.error("通过http请求ocms获取当前时间所有正在运行的活动ID失败，失败的原因是：", e);
		}
		return activityIdList;
	}

	@Override
	protected void process(Object object) throws Exception {
		String activityId = (String) object;
		// 根据活动ID查询redis数据
		String key = ActivityTagConstant.ACTIVITYTAGREDISNAME + activityId;
		Set<Tuple> set = ActivityTagRedis2HbaseService.getRedisUtil().getZset(key, 0, -1);
		String hbaseTableName = ActivityTagConstant.HBASETABLENAME + activityId;
		int num=set.size();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Tuple tuple = (Tuple) iterator.next();
			String starId = tuple.getElement();
			HbaseUtil.addRow(hbaseTableName, starId, ActivityTagConstant.HBASECOLUMNFAMILY,
					ActivityTagConstant.HBASECOLUMN, String.valueOf(num), config);
			num--;
		}

	}

	@Override
	protected String toLog(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

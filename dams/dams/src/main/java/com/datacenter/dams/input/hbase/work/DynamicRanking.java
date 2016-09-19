package com.datacenter.dams.input.hbase.work;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hbase.entity.BillInfoEntity;
import com.datacenter.dams.input.hbase.entity.UserContribution;
import com.datacenter.dams.input.hbase.util.DataUtil;
import com.datacenter.dams.input.redis.worker.handlerservice.CallActiveMQQueueService;
import com.datacenter.dams.util.JsonUtil;

/**
 * Created by zkt on 2015/12/11.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicRanking extends AbsScheduleRankingStatic {

	private final Logger log = LogManager.getLogger(DynamicRanking.class);
	private String dynamicQueue;
	// 设置要查询的历史数据的天数
	private String queryDays;
	// 设置查询的类型
	private String type;
	// 1、根据不同的业务从不同的hbase表查询每天统计计算的数据 2、设置业务数据参数标识 （手机直播统计|其他统计）
	private String HbaseTableName;
	private String busType;
	private static Configuration conf = HBaseConfiguration.create();

	/**
	 * 调度执行的数据入口
	 */
	public void excute() {
		try {
			String[] startEndTime = DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), 0));
			// 获取当前时间的hbase统计数据
			List<BillInfoEntity> dataList = scanByStartAndStopRow(HBASEOPERATIONINFO_TABLENAME, startEndTime[0], startEndTime[1],
					conf);
			Map current_result_map = null;
			if (null != dataList && dataList.size() > 0) {
				current_result_map = getRomConSumerInfo(dataList, busType);
				log.debug("频道贡献榜数据更新，当天数据查询出来的个数:" + current_result_map.size());
			}
			List<Map> ls = new ArrayList<Map>();
			//for (int i = 1; i <= Integer.parseInt(queryDays); i++) {
				String[] start_end_time = DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), 1));
				Map history_result_map = scanByStartEndTime(HbaseTableName, start_end_time[0], start_end_time[1], conf);
				log.info("用户贡献榜统计29天累积的hbase数据,起始时间:"+start_end_time[0]+";结束时间:"+start_end_time[1]);
				if (null != history_result_map && history_result_map.size() > 0) {
					ls.add(history_result_map);
				}else{
					log.info("==========用户贡献榜未查到29天累积的数据，跳出本次调度==========");
					return ;
				}
			//}
			if (null != current_result_map && current_result_map.size() > 0) {
				ls.add(current_result_map);
			}
			// 合并当前数据和已统计出的数据
			Map result_map = mergeMap(ls);
			log.debug("当天数据和周期：" + queryDays + "天数据合并，合并后的map数据个数:" + result_map.size());
			// 将合并后的数据持久化到mysql数据库
			sendtMergeDataToActiveMq(result_map);
		} catch (Exception e) {
			log.error("用户贡献榜获取起始时间和结束时间失败，失败的原因是:", e);
		}
	}

	public void sendtMergeDataToActiveMq(Map result_map) {
		Set rom_keys = result_map.keySet();
		List<UserContribution> userContributionsList = new ArrayList<UserContribution>();
		for (Iterator iterator = rom_keys.iterator(); iterator.hasNext();) {
			String rom_key = (String) iterator.next();
			Map user_maps = (Map) result_map.get(rom_key);
			Set user_keys = user_maps.keySet();
			for (Iterator iterator2 = user_keys.iterator(); iterator2.hasNext();) {
				String user_key = String.valueOf(iterator2.next());
				log.debug("合并后的数据房间号:" + rom_key + ";userID=" + user_key);
				String user_num = String.valueOf(user_maps.get(user_key));
				UserContribution uContribution = new UserContribution();
				uContribution.setUserId(new BigInteger(user_key));
				uContribution.setNickName("默认昵称");
				uContribution.setRoomId(new BigInteger(rom_key));
				uContribution.setContributionSum(new BigDecimal(user_num));
				uContribution.setUserPhoto("默认图片");
				uContribution.setDataType(type);
				userContributionsList.add(uContribution);
				log.debug("hbase统计的数据数据 roomId：" + uContribution.getRoomId() + ";userid:" + uContribution.getUserId()
						+ ";contributionSum:" + uContribution.getContributionSum());

			}
		}
		sendMqData(dynamicQueue, userContributionsList);
	}

	/**
	 * 将统计的结果数组发送到activeMq
	 * 
	 * @param dynamicQueue
	 * @param userContributionsList
	 */
	public void sendMqData(String dynamicQueue, List userContributionsList) {
		CallActiveMQQueueService.getActiveMqQueueService().setActiveMqQueueName(dynamicQueue);
		List<UserContribution> resultList = spliceArrays(userContributionsList, 100);
		for (int i = 0; i < resultList.size(); i++) {
			try {
				CallActiveMQQueueService.getActiveMqQueueService().send(JsonUtil.getObjectToJson(resultList.get(i)));
				log.info("向SDMS推送用户贡献榜数据:" + JsonUtil.getObjectToJson(resultList.get(i)));
			} catch (Exception e) {
				log.error("用户贡献榜" + busType + "统计结果发送消息队列失败，失败的原因是:", e);
			}
		}
	}

	/**
	 * 将数组根据要拆分的数据大小拆分成多个数组
	 * 
	 * @param datas
	 * @param splitSize
	 * @return
	 */
	public <T> List<List<T>> spliceArrays(List<T> datas, int splitSize) {
		if (datas == null || splitSize < 1) {
			return null;
		}
		int totalSize = datas.size();
		int count = (totalSize % splitSize == 0) ? (totalSize / splitSize) : (totalSize / splitSize + 1);
		List<List<T>> rows = new ArrayList<List<T>>();
		for (int i = 0; i < count; i++) {
			List<T> cols = datas.subList(i * splitSize, (i == count - 1) ? totalSize : splitSize * (i + 1));
			rows.add(cols);
		}
		return rows;
	}

	public String getHbaseTableName() {
		return HbaseTableName;
	}

	public void setHbaseTableName(String hbaseTableName) {
		HbaseTableName = hbaseTableName;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQueryDays() {
		return queryDays;
	}

	public void setQueryDays(String queryDays) {
		this.queryDays = queryDays;
	}

	public String getDynamicQueue() {
		return dynamicQueue;
	}

	public void setDynamicQueue(String dynamicQueue) {
		this.dynamicQueue = dynamicQueue;
	}

}

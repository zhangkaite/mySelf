package com.ttmv.datacenter.usercenter.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.util.DataUtil;
import com.ttmv.datacenter.usercenter.util.HbaseUtil;

/**
 * 调度统计昨天用户在不同房间的消费t豆，将统计完的结果写入hbase
 *
 * @author zkt
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ScheduleRankStatic {

	private final Logger log = LogManager.getLogger(ScheduleRankStatic.class);

	private Configuration conf = null;
	// hbase消费表
	private static final String HBASEOPERATIONINFO_TABLENAME = "HbaseOperationInfo";
	// hbase消费统计结果表
	private static final String HBASERANKINGINFO_TABLENAME = "NewHbaseRankingInfo";

	private static final String HBASEUSERINFO_COLUMN_DATA = "HbaseStaticDate";

	// 初始化hbase配置信息
	public ScheduleRankStatic() {
		conf = HBaseConfiguration.create();
	}

	/**
	 * 执行调度的具体方法入口
	 */
	public void excute() {
		// 获取当前时间昨天的时间起始时间和结束时间
		try {
			//String[] startEndTime = DataUtil.getStartEndTime(DataUtil.getData(new Date(), 1), 1);
			String[] startEndTime = DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), 1));
			log.info("周榜统计每天调度执行统计时间段起始时间:"+startEndTime[0]+";结束时间："+startEndTime[1]);
			// 获取当前时间前一天的所有消费数据，并且对相同频道消费的t豆进行累加  
			Map result_map = scanByStartAndStopRow(HBASEOPERATIONINFO_TABLENAME, startEndTime[0], startEndTime[1]);
			// 将一天统计的结果数据写入hbase HbaseRankingInfo表中
			initStaticDatas2Hbase(result_map);

		} catch (Exception e) {
			log.error("获取当前时间的前一天时间失败，失败的原因是:", e);
		}

	}

	/**
	 * 根据起始时间戳、结束时间戳、hbase表名称获取所有的数据
	 *
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @throws Exception
	 */
	public Map scanByStartAndStopRow(String tableName, String startRow, String stopRow) {
		
		ResultScanner rs = HbaseUtil.getRowsByStartEndTime(tableName, startRow, stopRow, conf);
		List<BillInfoEntity> ls = new ArrayList<BillInfoEntity>();
		for (Result r : rs) {
			try {
				BillInfoEntity bEntity = convert2Entity(r);
				// 只填加消费类型和房间号不为空的数据
				if ("-1".equals(bEntity.getType()) && null != bEntity.getRoomID()) {
					ls.add(bEntity);
				}
				
			} catch (Exception e) {
				log.error("hbase 获取的数据json转对象数据失败，失败的原因：", e);
			}
		}
		Map resultMap = getRomConSumerInfo(ls);
		return resultMap;
	}

	/**
	 * 将查询出的结果json串转换成对象
	 *
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public BillInfoEntity convert2Entity(Result result) throws Exception {
		BillInfoEntity billInfoEntity = null;
		for (Cell cell : result.rawCells()) {
			String info = new String(CellUtil.cloneValue(cell));
			billInfoEntity = (BillInfoEntity) JsonUtil.getObjectFromJson(info, BillInfoEntity.class);
		}
		return billInfoEntity;
	}

	/**
	 * 根据list数组获取当天所有房间每个人消费总数 1、删选符合条件的数据 2、将所有的数据按照房间号分类 3、将同一房间号 同一用户的消费数据求和
	 * 消费数据区分t券和t币
	 *
	 * @param ls
	 * @return
	 */

	public Map getRomConSumerInfo(List<BillInfoEntity> ls) {
		Map resultMap = new HashedMap();
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			BillInfoEntity billInfoEntity = (BillInfoEntity) iterator.next();
			String rom_id = billInfoEntity.getRoomID();
			// 如果房间号为空，则说明该数据不是房间T币消费数据
			if (null == rom_id || "".equals(rom_id)) {
				continue;
			}
			String user_id = String.valueOf(billInfoEntity.getUserId());
			BigDecimal trade_num = billInfoEntity.getNumber();
			// 判断当前消费类型是t币还是t券，t币乘以1000
			if ("0".equals(billInfoEntity.getAccountType())) {
				trade_num = trade_num.multiply(new BigDecimal(1000));
			}
			// 如果map中包含当前的房间号
			if (!resultMap.containsKey(rom_id)) {
				Map currUserTradeMap = new HashedMap();
				currUserTradeMap.put(user_id, trade_num);
				resultMap.put(rom_id, currUserTradeMap);
			} else {
				// 获取当前房间号的所有人员消费数据
				HashedMap consumMap = (HashedMap) resultMap.get(rom_id);
				if (consumMap.containsKey(user_id)) {
					BigDecimal current_trade_num =new BigDecimal(consumMap.get(user_id).toString()) ;
					BigDecimal new_trade_num = current_trade_num.add(trade_num);
					consumMap.put(user_id, new_trade_num);
					resultMap.put(rom_id, consumMap);
				} else {
					consumMap.put(user_id, trade_num);
					resultMap.put(rom_id, consumMap);
				}

			}

		}
		return resultMap;
	}

	/**
	 * 将统计的结果写入hbase中
	 */
	public void initStaticDatas2Hbase(Map resulMap) {
		// 如果hbase表不存在，先创建表
		String[] clumnFamily = { HBASEUSERINFO_COLUMN_DATA };
		try {
			HbaseUtil.createTable(HBASERANKINGINFO_TABLENAME, clumnFamily, conf);
			
		} catch (Exception e) {
			log.error("用户消费统计habse表创建失败，失败的原因", e);
		}
		// 组装hbase rowkey 13位时间戳+频道号+用户id
		for (Object obj : resulMap.keySet()) {
			String channel_id = (String) obj;
			Map result_map = (Map) resulMap.get(channel_id);
			for (Object result : result_map.keySet()) {
				String user_id = (String) result;
				String consume_num = String.valueOf(result_map.get(user_id));
				//String hbase_key = String.valueOf(new Date().getTime()) + channel_id + user_id;
				String hbase_key = String.valueOf(DataUtil.getQueryFixedTime(new Date(),1,-1).getTime()) + channel_id + user_id;
				RankingEntity rankingEntity = new RankingEntity();
				rankingEntity.setChannel_id(channel_id);
				rankingEntity.setUser_id(user_id);
				rankingEntity.setConsume_num(consume_num);
				try {
					String value = JsonUtil.getObjectToJson(rankingEntity);
					HbaseUtil.addRow(HBASERANKINGINFO_TABLENAME, hbase_key, HBASEUSERINFO_COLUMN_DATA,
							HBASEUSERINFO_COLUMN_DATA, value, conf);
				} catch (Exception e) {
					// TODO 添加报警机制

					log.error("hbase 添加数据失败，失败的原因是", e);
				}
			}
		}

	}

}

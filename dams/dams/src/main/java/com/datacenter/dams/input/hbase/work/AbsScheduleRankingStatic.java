package com.datacenter.dams.input.hbase.work;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hbase.entity.BillInfoEntity;
import com.datacenter.dams.input.hbase.entity.RankingEntity;
import com.datacenter.dams.input.hbase.util.DataUtil;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.JsonUtil;

/**
 * 调度抽象类
 * 
 * @author kate
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbsScheduleRankingStatic {
	private final Logger log = LogManager.getLogger(AbsScheduleRankingStatic.class);
	protected static final String BUSMOBILELIVETYPE = "MobileLive";
	protected static final String BUSOTHERTYPE = "UnMobileLive";
	// hbase消费表
	protected static final String HBASEOPERATIONINFO_TABLENAME = "HbaseOperationInfo";
	// hbase多终端消费统计结果表
	protected static final String HBASERANKINGINFO_TABLENAME = "NewHbaseRankingInfo";
	// hbase手机直播消费统计结果表
	protected static final String HBASEMOBILELIVE_TABLENAME = "LiveHbaseRankingInfo";
	protected static final String HBASEUSERINFO_COLUMN_DATA = "HbaseStaticDate";

	// 调度执行入口
	public abstract void excute();

	/**
	 * 根据表名、起始时间结束时间查询hbase数据列表(查询充值消费记录表数据)
	 *
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @throws Exception
	 */
	public List<BillInfoEntity> scanByStartAndStopRow(String tableName, String startRow, String stopRow, Configuration conf)
			throws Exception {
		List<String> dataList = HbaseUtil.getRowsByStartEndTime(tableName, startRow, stopRow, conf);
		if (null != dataList && dataList.size() > 0) {
			List<BillInfoEntity> ls = new ArrayList<BillInfoEntity>();
			for (String data : dataList) {
				BillInfoEntity bEntity = (BillInfoEntity) JsonUtil.getObjectFromJson(data, BillInfoEntity.class);
				if ("-1".equals(bEntity.getType()) && null != bEntity.getRoomID()) {
					ls.add(bEntity);
				}
			}
			return ls;
		}
		return null;
	}

	/**
	 * 根据某个时间段查询已调度统计完成的数据
	 *
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @return
	 */
	public Map scanByStartEndTime(String tableName, String startRow, String stopRow, Configuration conf) {
		List<RankingEntity> ls = new ArrayList<RankingEntity>();
		List<String> dataList=null;
		try {
			dataList = HbaseUtil.getRowsByStartEndTime(tableName, startRow, stopRow, conf);
			if (null != dataList) {
				for (String data : dataList) {
					RankingEntity rankingEntity = (RankingEntity) JsonUtil.getObjectFromJson(data, RankingEntity.class);
					log.debug("起始时间:" + startRow + ";结束时间:" + stopRow + "查询出来的数据room_id=" + rankingEntity.getChannel_id()
							+ ";user_id:" + rankingEntity.getUser_id());
					ls.add(rankingEntity);
				}
				Map resultMap = getRomConSumerInfo(ls);
				log.debug("hbase一段时间起始时间：" + startRow + "到结束时间：" + stopRow + "查询到的房间总数据：" + resultMap.size());
				return resultMap;
			}

		} catch (Exception e) {
			log.error("hbase用户贡献榜数据查询失败，失败的原因是:", e);
		}
		return null;
	}

	/*
	 * 获取历史纪录消费信息
	 * 
	 * @param ls
	 * 
	 * @return
	 */

	private Map getRomConSumerInfo(List<RankingEntity> ls) {
		Map resultMap = new HashedMap();
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			RankingEntity rankingEntity = (RankingEntity) iterator.next();
			String channel_id = rankingEntity.getChannel_id();
			String user_id = rankingEntity.getUser_id();
			BigDecimal num = new BigDecimal(rankingEntity.getConsume_num());
			if (resultMap.containsKey(channel_id)) {
				Map consume_map = (Map) resultMap.get(channel_id);
				if (consume_map.containsKey(user_id)) {
					BigDecimal currentNum = new BigDecimal(consume_map.get(user_id).toString());
					num = currentNum.add(num);
				}
				consume_map.put(user_id, num);
				resultMap.put(channel_id, consume_map);
			} else {
				Map consume_map = new HashedMap();
				consume_map.put(user_id, num);
				resultMap.put(channel_id, consume_map);
			}
		}
		return resultMap;
	}

	/**
	 * 将统计的结果写入hbase中
	 */
	public void initStaticDatas2Hbase(Map resulMap, String hbaseTableName, String columnFamily, Configuration conf,boolean flag) {
		// 如果hbase表不存在，先创建表
		try {
			if (flag) {
				HbaseUtil.createTable(conf, hbaseTableName, columnFamily);
			}else{
				HbaseUtil.createTableNoOverwrite(conf, hbaseTableName, columnFamily);
			}
		} catch (Exception e) {
			log.error("用户消费统计habse表创建失败，失败的原因", e);
		}
		// 组装hbase rowkey 13位时间戳+频道号+用户id
		Connection connection = null;
		Table table = null;
		try {
			connection = ConnectionFactory.createConnection(conf);
			table = connection.getTable(TableName.valueOf(hbaseTableName));
			for (Object obj : resulMap.keySet()) {
				String channel_id = (String) obj;
				Map result_map = (Map) resulMap.get(channel_id);
				for (Object result : result_map.keySet()) {
					String user_id = (String) result;
					String consume_num = String.valueOf(result_map.get(user_id));
					String hbase_key = String.valueOf(DataUtil.getQueryFixedTime(new Date(), 1, -1).getTime()) + channel_id
							+ user_id;
					RankingEntity rankingEntity = new RankingEntity();
					rankingEntity.setChannel_id(channel_id);
					rankingEntity.setUser_id(user_id);
					rankingEntity.setConsume_num(consume_num);
					try {
						String value = JsonUtil.getObjectToJson(rankingEntity);
						// HbaseUtil.addRow(hbaseTableName, hbase_key,
						// columnFamily, columnFamily, value, conf);
						HbaseUtil.addRow(table, hbase_key, columnFamily, columnFamily, value);
						log.info("用户贡献榜天调度hbase添加的数据hbase_key:" + hbase_key + ",value:" + value);
					} catch (Exception e) {
						log.error("hbase 添加数据失败，失败的原因是", e);
					}
				}
			}
		} catch (Exception e) {
			log.error("贡献榜hbase创建连接失败，失败的原因是:", e);
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				log.error("用户贡献榜hbase连接关闭失败,失败的原因是:", e);
			}
		}
	}

	/**
	 * @param listMap
	 * @return
	 */
	public Map mergeMap(List<Map> listMap) throws Exception {
		Map resultMap = new HashedMap();
		for (Map map : listMap) {
			for (Object room_id : map.keySet()) {
				Map currentMap = (Map) map.get(room_id);
				// 判断是否具有相同的房间号
				if (resultMap.containsKey(room_id)) {
					// 获取当前房间刷礼物的数据
					// 获取map结果中相同房间号的所有刷礼物的人和金额
					Map resultMapValue = (Map) resultMap.get(room_id);
					for (Object currkey : currentMap.keySet())
						if (!resultMapValue.containsKey(currkey)) {
							resultMapValue.put(currkey, currentMap.get(currkey));
							resultMap.put(room_id, resultMapValue);
						} else {
							BigDecimal resultValueConsumNum = new BigDecimal(resultMapValue.get(currkey).toString());
							BigDecimal currentMapConsumNum = new BigDecimal(currentMap.get(currkey).toString());
							resultMapValue.put(currkey, resultValueConsumNum.add(currentMapConsumNum));
							resultMap.put(room_id, resultMapValue);
						}
				} else {
					resultMap.put(room_id, currentMap);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 根据list数组获取当天所有房间每个人消费总数 1、删选符合条件的数据 2、将所有的数据按照房间号分类 3、将同一房间号 同一用户的消费数据求和
	 * 消费数据区分t券和t币
	 *
	 * @param ls
	 * @return
	 */

	public Map getRomConSumerInfo(List<BillInfoEntity> ls, String busType) {
		Map resultMap = new HashedMap();
		// 根据业务类型筛选符合条件的数据
		// List<BillInfoEntity> resultList = getListByType(ls, busType);
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
					BigDecimal current_trade_num = new BigDecimal(consumMap.get(user_id).toString());
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

	public List<BillInfoEntity> getListByType(List<BillInfoEntity> ls, String busType) {
		List<BillInfoEntity> mobileLiveList = new ArrayList<BillInfoEntity>();
		List<BillInfoEntity> otherList = new ArrayList<BillInfoEntity>();

		for (BillInfoEntity bEntity : ls) {
			String dataType = bEntity.getVersion();
			if (BUSMOBILELIVETYPE.equals(busType) && dataType.equals(busType)) {
				mobileLiveList.add(bEntity);
				continue;
			}

			if (BUSOTHERTYPE.equals(busType) && !dataType.equals(BUSMOBILELIVETYPE)) {
				otherList.add(bEntity);
			}
		}

		if (BUSMOBILELIVETYPE.equals(busType)) {
			return mobileLiveList;
		} else {
			return otherList;
		}

	}

}

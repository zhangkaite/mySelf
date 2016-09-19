package com.ttmv.datacenter.usercenter.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ttmv.datacenter.usercenter.dao.interfaces.UserContributeDao;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserContribution;
import com.ttmv.datacenter.usercenter.util.DataUtil;
import com.ttmv.datacenter.usercenter.util.HbaseUtil;

/**
 * Created by zkt on 2015/12/11.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicRanking {

	private final Logger log = LogManager.getLogger(DynamicRanking.class);

	// 设置要查询的历史数据的天数
	private String queryDays;

	// 设置查询的类型
	private String type;

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

	// 注入用户消费dao
	private UserContributeDao userContributeDao;

	// 注入用户信息查询dao
	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public UserContributeDao getUserContributeDao() {
		return userContributeDao;
	}

	public void setUserContributeDao(UserContributeDao userContributeDao) {
		this.userContributeDao = userContributeDao;
	}

	private static Configuration conf = HBaseConfiguration.create();
	// hbase表名
	private final String HBASEOPERATIONINFO_TABLENAME = "HbaseOperationInfo";

	// hbase消费统计结果表
	private final String HBASERANKINGINFO_TABLENAME = "NewHbaseRankingInfo";

	/**
	 * 调度执行的数据入口
	 */

	public void excute() {

		try {
			String[] startEndTime =DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), 0));
			// String[] startEndTime =DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), i));
			// 获取当前时间的hbase统计数据
			Map current_result_map = scanByStartAndStopRow(HBASEOPERATIONINFO_TABLENAME, startEndTime[0], startEndTime[1]);
			log.debug("频道贡献榜数据更新，当天数据查询出来的个数:" + current_result_map.size());
			List<Map> ls = new ArrayList<Map>();
			for (int i = 1; i <= Integer.parseInt(queryDays); i++) {
				//Date time = DataUtil.getQueryFixedTime(new Date(), 1, -i);
				String[] start_end_time =DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), i));
				Map history_result_map = scanByStartEndTime(HBASERANKINGINFO_TABLENAME, start_end_time[0], start_end_time[1]);
				if (null != history_result_map && history_result_map.size() > 0) {
					ls.add(history_result_map);
				}
			}

			if (null != current_result_map && current_result_map.size() > 0) {
				ls.add(current_result_map);
			}

			// 合并当前数据和已统计出的数据
			Map result_map = mergeMap(ls);
			log.debug("当天数据和周期：" + queryDays + "天数据合并，合并后的map数据个数:" + result_map.size());
			// 根据当前的数据获取每条数据用户详情信息
			Set rom_keys = result_map.keySet();
			for (Iterator iterator = rom_keys.iterator(); iterator.hasNext();) {
				String rom_key = (String) iterator.next();
				// 更新数据之前先根据业务类型删除该房间的所有数据
				UserContribution u = new UserContribution();
				u.setRoomId(new BigInteger(rom_key));
				u.setDataType(type);
				// userContributeDao.deleteByRoomIdAndDataType(u);
				Map user_maps = (Map) result_map.get(rom_key);
				Set user_keys = user_maps.keySet();
				for (Iterator iterator2 = user_keys.iterator(); iterator2.hasNext();) {
					String user_key = String.valueOf(iterator2.next());
					log.debug("合并后的数据房间号:" + rom_key + ";userID=" + user_key);
					String user_num = String.valueOf(user_maps.get(user_key));
					UserContribution uContribution = new UserContribution();
					uContribution.setUserId(new BigInteger(user_key));
					// UserInfo userInfo = null;
					// try {
					// userInfo = userInfoDao.selectUserInfoByUserId(new
					// BigInteger(user_key));
					//
					// } catch (Exception e) {
					// log.error("用户资料查询失败，失败的原因是：", e);
					// }
					// if(userInfo != null){
					uContribution.setNickName("默认昵称");
					uContribution.setRoomId(new BigInteger(rom_key));
					uContribution.setContributionSum(new BigDecimal(user_num));
					uContribution.setUserPhoto("默认图片");
					uContribution.setDataType(type);
					try {
						// 将统计后的数据持久化到mysql数据库
						log.debug("排行榜mysql更新数据 userid：" + uContribution.getUserId() + ";roomId:" + uContribution.getRoomId()
								+ ";contributionSum:" + uContribution.getContributionSum());
						Integer tag = userContributeDao.updateUserContribution(uContribution);
						log.debug("Mysql调用返回" + tag);
					} catch (Exception e) {
						log.error("排行榜mysql更新失败，失败的原因是：", e);
					}
					// }
				}
			}
		} catch (Exception e) {
			log.error("数据求和出错", e);
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
		List<BillInfoEntity> ls = new ArrayList<BillInfoEntity>();
		ResultScanner rs = HbaseUtil.getRowsByStartEndTime(tableName, startRow, stopRow, conf);
		Map resultMap = null;
		if (null != rs) {
			for (Result r : rs) {
				try {
					BillInfoEntity bEntity = convert2Entity(r);
					log.debug("起始时间:" + startRow + ";结束时间:" + stopRow + "查询出来的数据room_id=" + bEntity.getRoomID() + ";user_id:"
							+ bEntity.getUserId());
					// 只填加消费类型和房间号不为空的数据
					if ("-1".equals(bEntity.getType()) && null != bEntity.getRoomID()) {
						ls.add(bEntity);
					}
				} catch (Exception e) {
					log.error("hbase查询的结果转换对象失败，失败的原因是:", e);
				}
			}
			resultMap = getTodayRomConSumerInfo(ls);
			log.debug("hbase当天起始时间：" + startRow + "到结束时间：" + stopRow + "查询到的数据：" + resultMap.size());
			rs.close();
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

	public Map getTodayRomConSumerInfo(List<BillInfoEntity> ls) {
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

	/**
	 * 根据某个时间段查询已调度统计完成的数据
	 *
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @return
	 */
	public Map scanByStartEndTime(String tableName, String startRow, String stopRow) {
		List<RankingEntity> ls = new ArrayList<RankingEntity>();
		ResultScanner rs = HbaseUtil.getRowsByStartEndTime(tableName, startRow, stopRow, conf);
		if (null!=rs) {
			for (Result r : rs) {
				try {
					RankingEntity rankingEntity = convert2RankingEntity(r);
					log.debug("起始时间:" + startRow + ";结束时间:" + stopRow + "查询出来的数据room_id=" + rankingEntity.getChannel_id()
							+ ";user_id:" + rankingEntity.getUser_id());
					ls.add(rankingEntity);
				} catch (Exception e) {
					log.error("hbase查询的结果转换对象失败，失败的原因是:", e);
				}
			}
		}
		Map resultMap = getRomConSumerInfo(ls);
		log.debug("hbase一段时间起始时间：" + startRow + "到结束时间：" + stopRow + "查询到的房间总数据：" + resultMap.size());
		rs.close();
		return resultMap;

	}

	/**
	 * 获取历史纪录消费信息
	 * 
	 * @param ls
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
	 * 将查询出的结果json串转换成对象
	 *
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public RankingEntity convert2RankingEntity(Result result) throws Exception {
		RankingEntity rankingEntity = null;
		for (Cell cell : result.rawCells()) {
			String info = new String(CellUtil.cloneValue(cell));
			rankingEntity = (RankingEntity) JsonUtil.getObjectFromJson(info, RankingEntity.class);
		}
		return rankingEntity;
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
							/*
							 * Map nowMap = new HashedMap(); nowMap.put(currkey,
							 * resultValueConsumNum.add(currentMapConsumNum));
							 */
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

}

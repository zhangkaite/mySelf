package com.datacenter.dams.business.service.activity;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.activity.pro.LevelDomainUtil;
import com.datacenter.dams.business.service.activity.pro.TmpLevelDomain;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.domain.live.activity.GiftRules;
import com.datacenter.domain.live.activity.LevelDomain;
import com.datacenter.domain.protocol.da.Present;
import com.datacenter.domain.protocol.da.StarActivityInfo;
import com.google.common.base.Strings;
import com.ttmv.datacenter.sentry.SentryAgent;

import net.sf.json.JSONObject;

/**
 * 查询主播活动信息
 * 
 * @author wll
 */
public class StarInfoActivityService {

	private static Logger logger = LogManager.getLogger(StarInfoActivityService.class);
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	private static Configuration config = HBaseConfiguration.create();

	/* 表名 */
	private static final String HBASETABLE_PREFIX = "activity_star_";
	/* 总分数列组 和 列名 */
	private static final String SCORE_ALL = "score_all";
	/* 给定的礼物的个数 */
	private static final String PRESENT_ID = "present";
	/* 关卡的名称 */
	private static final String LEVELNAME = "pass";
	/* 主播排名 */
	private static final String RANK = "rank";
	/* 列族 */
	private static final String FAMILIY = "a";
	/* 分隔符 */
	private static final String SEPARATOR = "_";
	
	

	/**
	 * 根据主播Id和活动Id查询主播活动的信息
	 * 
	 * @param userId
	 * @param activityId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StarActivityInfo getJsonStarInfoActivity(String starId, String activityId, SentryAgent quickSentry)
			throws Exception {
		if (Strings.isNullOrEmpty(starId) || Strings.isNullOrEmpty(activityId)) {
			logger.error("DAMS查询Hbase的参数不能为空!");
			throw new Exception("DAMS查询Hbase的参数不能为空!");
		}
		String tableName = HBASETABLE_PREFIX + activityId;
		String keyPassOrder = null;
		if (!getPassIsExsist(starId, activityId)) {
			StarActivityInfo starActivityInfo = resLevelString(quickSentry, activityId, starId);
			starActivityInfo.setScore(
					getScore(tableName, starId) == null ? new BigInteger("0") : getScore(tableName, starId));
			starActivityInfo.setRanking(getRanking(tableName, starId) == null ? 0 : getRanking(tableName, starId));
			return starActivityInfo;
		}
		ResultScanner scanner = HbaseUtil.getAllVersionsData(tableName, starId, config);
		/* 单个主播活动排行信息 */
		StarActivityInfo starInfo = new StarActivityInfo();
		if (scanner != null) {
			/* 存储礼物 */
			List<Present> listPre = new ArrayList<Present>();
			/* 临时存储 对比使用 */
			Map<String, LevelDomain> mapTemp = new HashMap<String, LevelDomain>();
			Map mapColumns = this.getStarInfoActivityOrder(scanner);
			/* 如果没有查询到关卡或是值为0,则返回null */
			if (mapColumns == null || mapColumns.size() == 0) {
				return null;
			}
			scanner = HbaseUtil.getAllVersionsData(tableName, starId, config);
			if (scanner != null) {
				for (Result r : scanner) {
					for (Cell cell : r.rawCells()) {
						/* 列修饰符 */
						String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
						/* 列修饰符值 */
						byte[] value = CellUtil.cloneValue(cell);
						if (value == null || value.length == 0) {
							return null;
						}
						/* 分解数据 */
						if (!Strings.isNullOrEmpty(familiyQuilifier)) {
							if (familiyQuilifier.contains(LEVELNAME)) {
								String json = Bytes.toString(value);
								LevelDomain level = (LevelDomain) JsonUtil.getObjectFromJson(json, LevelDomain.class);
								/* key的组成pass_date_order */
								Integer tempOrder = level.getOrder();
								String tempKeyPassOrder = familiyQuilifier + SEPARATOR + tempOrder;
								mapTemp.put(tempKeyPassOrder, level);
								/* 先比较日期,再比较order */
								keyPassOrder = this.getLeveKey(tempKeyPassOrder, keyPassOrder);
								if (Strings.isNullOrEmpty(keyPassOrder)) {
									throw new Exception("关卡为空了!");
								}
							}
						}
					}
				}
				scanner.close();
			}

			Result result = HbaseUtil.getStormAllColumnData(tableName, starId, config);
			if (result != null) {
				for (Cell cell : result.rawCells()) {
					String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
					System.out.println(familiyQuilifier);
					if (!Strings.isNullOrEmpty(familiyQuilifier)) {
						if (familiyQuilifier.contains(SCORE_ALL)) {
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							starInfo.setScore(new BigInteger(_data));
						} else if (familiyQuilifier.contains(RANK)) {
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							starInfo.setRanking(new Integer(new BigInteger(Bytes.toString(_data)).toString()));
						} else if (mapColumns.containsKey(familiyQuilifier)) {
							String limitKey = getLimitNumKey(familiyQuilifier);
							if (Strings.isNullOrEmpty(limitKey)) {
								logger.info("[DAMS#StarInfoActivity]查询礼物的上限为空!");
								return null;
							}
							String num = mapColumns.get(limitKey).toString();
							Present p = new Present();
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							String values[] = familiyQuilifier.split(SEPARATOR);
							p.setProductID(new BigInteger(values[1]));
							p.setSum(new Integer(new BigInteger(_data).toString()));
							p.setLimitSum(new Integer(num));
							listPre.add(p);
							/* 删除列标示key 和 列标示值 */
							mapColumns.remove(familiyQuilifier);
							mapColumns.remove(familiyQuilifier + SEPARATOR + "ALL");
						}
					}
				}
			}

			/* 如果礼物数量为0 */
			if (mapColumns.size() > 0) {
				Set<String> sets = mapColumns.keySet();
				for (String str : sets) {
					if (str.contains("ALL")) {
						String values[] = str.split(SEPARATOR);
						Present p = new Present();
						p.setProductID(new BigInteger(values[1]));
						p.setSum(new Integer(0));
						p.setLimitSum(new Integer(mapColumns.get(str).toString()));
						listPre.add(p);
					}
				}
			}

			if (mapTemp != null && mapTemp.size() > 0) {
				/* 组装指定礼物的上限 */
				LevelDomain level = mapTemp.get(keyPassOrder);
				/* 补充其他值 */
				starInfo.setActivityID(new BigInteger(activityId));
				starInfo.setUserID(new BigInteger(starId));
				starInfo.setGifts(listPre);
				starInfo.setLevelName(level.getLevelname());
				starInfo.setLevelLogoMC(level.getMobilePhoto());
				starInfo.setLevelLogoPC(level.getPcPhoto());
				starInfo.setActivityPackage(level.getActivityPackage());
				return starInfo;
			}
		}
		logger.debug("[DAMS#ActivityController]Hbase 实时查询主播吊牌数据为空！！！");
		return null;
	}

	
	/**
	 * 活动结束后查询主播积分 >>>>> 根据主播Id和活动Id查询主播活动的信息
	 * 
	 * @param userId
	 * @param activityId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StarActivityInfo getJsonStarEndInfoActivity(String starId, String activityId, SentryAgent quickSentry)
			throws Exception {
		if (Strings.isNullOrEmpty(starId) || Strings.isNullOrEmpty(activityId)) {
			logger.error("DAMS查询Hbase的参数不能为空!");
			throw new Exception("DAMS查询Hbase的参数不能为空!");
		}
		String tableName = HBASETABLE_PREFIX + activityId;
		String keyPassOrder = null;
		ResultScanner scanner = HbaseUtil.getAllVersionsData(tableName, starId, config);
		/* 单个主播活动排行信息 */
		StarActivityInfo starInfo = new StarActivityInfo();
		if (scanner != null) {
			/* 存储礼物 */
			List<Present> listPre = new ArrayList<Present>();
			/* 临时存储 对比使用 */
			Map<String, LevelDomain> mapTemp = new HashMap<String, LevelDomain>();
			Map mapColumns = this.getStarEndInfoActivityOrder(scanner);
			/* 如果没有查询到关卡或是值为0,则返回null */
			if (mapColumns == null || mapColumns.size() == 0) {
				return null;
			}
			scanner = HbaseUtil.getAllVersionsData(tableName, starId, config);
			if (scanner != null) {
				for (Result r : scanner) {
					for (Cell cell : r.rawCells()) {
						/* 列修饰符 */
						String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
						/* 列修饰符值 */
						byte[] value = CellUtil.cloneValue(cell);
						if (value == null || value.length == 0) {
							return null;
						}
						/* 分解数据 */
						if (!Strings.isNullOrEmpty(familiyQuilifier)) {
							if (familiyQuilifier.contains(LEVELNAME)) {
								String json = Bytes.toString(value);
								LevelDomain level = (LevelDomain) JsonUtil.getObjectFromJson(json, LevelDomain.class);
								/* key的组成pass_date_order */
								Integer tempOrder = level.getOrder();
								String tempKeyPassOrder = familiyQuilifier + SEPARATOR + tempOrder;
								mapTemp.put(tempKeyPassOrder, level);
								/* 先比较日期,再比较order */
								keyPassOrder = this.getLeveKey(tempKeyPassOrder, keyPassOrder);
								if (Strings.isNullOrEmpty(keyPassOrder)) {
									throw new Exception("关卡为空了!");
								}
							}
						}
					}
				}
				scanner.close();
			}

			Result result = HbaseUtil.getStormAllColumnData(tableName, starId, config);
			if (result != null) {
				for (Cell cell : result.rawCells()) {
					String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
					System.out.println(familiyQuilifier);
					if (!Strings.isNullOrEmpty(familiyQuilifier)) {
						if (familiyQuilifier.contains(SCORE_ALL)) {
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							starInfo.setScore(new BigInteger(_data));
						} else if (familiyQuilifier.contains(RANK)) {
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							starInfo.setRanking(new Integer(new BigInteger(Bytes.toString(_data)).toString()));
						} else if (mapColumns.containsKey(familiyQuilifier)) {
							String limitKey = getLimitNumKey(familiyQuilifier);
							if (Strings.isNullOrEmpty(limitKey)) {
								logger.info("[DAMS#StarInfoActivity]查询礼物的上限为空!");
								return null;
							}
							String num = mapColumns.get(limitKey).toString();
							Present p = new Present();
							byte[] _data = HbaseUtil.getStormColumnData(config, tableName, starId, FAMILIY,
									familiyQuilifier);
							String values[] = familiyQuilifier.split(SEPARATOR);
							p.setProductID(new BigInteger(values[1]));
							p.setSum(new Integer(new BigInteger(_data).toString()));
							p.setLimitSum(new Integer(num));
							listPre.add(p);
							/* 删除列标示key 和 列标示值 */
							mapColumns.remove(familiyQuilifier);
							mapColumns.remove(familiyQuilifier + SEPARATOR + "ALL");
						}
					}
				}
			}

			/* 如果礼物数量为0 */
			if (mapColumns.size() > 0) {
				Set<String> sets = mapColumns.keySet();
				for (String str : sets) {
					if (str.contains("ALL")) {
						String values[] = str.split(SEPARATOR);
						Present p = new Present();
						p.setProductID(new BigInteger(values[1]));
						p.setSum(new Integer(0));
						p.setLimitSum(new Integer(mapColumns.get(str).toString()));
						listPre.add(p);
					}
				}
			}

			if (mapTemp != null && mapTemp.size() > 0) {
				/* 组装指定礼物的上限 */
				LevelDomain level = mapTemp.get(keyPassOrder);
				/* 补充其他值 */
				starInfo.setActivityID(new BigInteger(activityId));
				starInfo.setUserID(new BigInteger(starId));
				starInfo.setGifts(listPre);
				starInfo.setLevelName(level.getLevelname());
				starInfo.setLevelLogoMC(level.getMobilePhoto());
				starInfo.setLevelLogoPC(level.getPcPhoto());
				starInfo.setActivityPackage(level.getActivityPackage());
				return starInfo;
			}
		}
		logger.debug("[DAMS#ActivityController]Hbase 实时查询主播吊牌数据为空！！！");
		return null;
	}
	
	/**
	 * 确定当前关卡 和 礼物数量的最大值
	 * 
	 * @param userId
	 * @param activityId
	 * @return Map key : present_<preID>_<levelorder>_<date> value : "" key :
	 *         present_<preID>_<levelorder>_<date>_ ALL value : "6000"
	 *         如:关卡存在一个礼物60,Map对象中就会存在两个key present_60_3_20160125 = ""
	 *         匹配Hbase中的列 present_60_3_20160125_ALL = 600 匹配此列的最大值
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getStarInfoActivityOrder(ResultScanner scanner) throws Exception {
		if (scanner != null) {
			Map resultMap = new HashMap<String, String>();
			/* 临时存储 对比使用 */
			Map<String, LevelDomain> mapTemp = new HashMap<String, LevelDomain>();
			String _keyPassOrder = null;
			for (Result r : scanner) {
				for (Cell cell : r.rawCells()) {
					/* 列修饰符 */
					String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
					/* 列修饰符值 */
					String value = Bytes.toString(CellUtil.cloneValue(cell));
					if (value == null || "".equals(value)) {
						return null;
					}
					/* 分解数据 */
					if (!Strings.isNullOrEmpty(familiyQuilifier)) {
						if (familiyQuilifier.contains(LEVELNAME)) {
							LevelDomain level = (LevelDomain) JsonUtil.getObjectFromJson(value, LevelDomain.class);
							/* key的组成pass_date_order */
							Integer tempOrder = level.getOrder();
							String tempKeyPassOrder = familiyQuilifier + SEPARATOR + tempOrder;
							mapTemp.put(tempKeyPassOrder, level);
							/* 先比较日期,再比较order */
							_keyPassOrder = this.getLeveKey(tempKeyPassOrder, _keyPassOrder);
							if (Strings.isNullOrEmpty(_keyPassOrder)) {
								throw new Exception("关卡为空了!");
							}
						}
					}
				}
			}
			scanner.close();
			/* 获取当前的关卡 */
			if (mapTemp != null && mapTemp.size() > 0) {
				LevelDomain level = mapTemp.get(_keyPassOrder.toString());
				List<GiftRules> lists = level.getShowgifts();
				if (lists.size() > 0 && lists.size() > 0) {
					for (int y = 0; y < lists.size(); y++) {
						String giftId = lists.get(y).getProductID().toString();
						String limitNum = lists.get(y).getLimitSum();
						/* 需要匹配列 */
						String str = PRESENT_ID + SEPARATOR + giftId + SEPARATOR + level.getOrder() + SEPARATOR
								+ SDF.format(new Date());
						String strNum = str + SEPARATOR + "ALL";
						resultMap.put(str, "");
						resultMap.put(strNum, limitNum);
					}
				}
				return resultMap;
			}
		}
		return null;
	}

	
	/**
	 * 活动结束后查询主播积分 >>>>> 确定当前关卡 和 礼物数量的最大值
	 * 
	 * @param userId
	 * @param activityId
	 * @return Map key : present_<preID>_<levelorder>_<date> value : "" key :
	 *         present_<preID>_<levelorder>_<date>_ ALL value : "6000"
	 *         如:关卡存在一个礼物60,Map对象中就会存在两个key present_60_3_20160125 = ""
	 *         匹配Hbase中的列 present_60_3_20160125_ALL = 600 匹配此列的最大值
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getStarEndInfoActivityOrder(ResultScanner scanner) throws Exception {
		if (scanner != null) {
			Map resultMap = new HashMap<String, String>();
			/* 临时存储 对比使用 */
			Map<String, LevelDomain> mapTemp = new HashMap<String, LevelDomain>();
			String _keyPassOrder = null;
			for (Result r : scanner) {
				for (Cell cell : r.rawCells()) {
					/* 列修饰符 */
					String familiyQuilifier = Bytes.toString(CellUtil.cloneQualifier(cell));
					/* 列修饰符值 */
					String value = Bytes.toString(CellUtil.cloneValue(cell));
					if (value == null || "".equals(value)) {
						return null;
					}
					/* 分解数据 */
					if (!Strings.isNullOrEmpty(familiyQuilifier)) {
						if (familiyQuilifier.contains(LEVELNAME)) {
							LevelDomain level = (LevelDomain) JsonUtil.getObjectFromJson(value, LevelDomain.class);
							/* key的组成pass_date_order */
							Integer tempOrder = level.getOrder();
							String tempKeyPassOrder = familiyQuilifier + SEPARATOR + tempOrder;
							mapTemp.put(tempKeyPassOrder, level);
							/* 先比较日期,再比较order */
							_keyPassOrder = this.getLeveKey(tempKeyPassOrder, _keyPassOrder);
							if (Strings.isNullOrEmpty(_keyPassOrder)) {
								throw new Exception("关卡为空了!");
							}
						}
					}
				}
			}
			scanner.close();
			/* 获取当前的关卡 */
			if (mapTemp != null && mapTemp.size() > 0) {
				LevelDomain level = mapTemp.get(_keyPassOrder.toString());
				List<GiftRules> lists = level.getShowgifts();
				if (lists.size() > 0 && lists.size() > 0) {
					for (int y = 0; y < lists.size(); y++) {
						String giftId = lists.get(y).getProductID().toString();
						String limitNum = lists.get(y).getLimitSum();
						/* 需要匹配列 */
						String str = PRESENT_ID + SEPARATOR + giftId + SEPARATOR + level.getOrder() + SEPARATOR
								+ "20160214";
						String strNum = str + SEPARATOR + "ALL";
						resultMap.put(str, "");
						resultMap.put(strNum, limitNum);
					}
				}
				return resultMap;
			}
		}
		return null;
	}
	
	/**
	 * 获取最大数的Key
	 * 
	 * @param str
	 * @return
	 */
	private String getLimitNumKey(String str) {
		if (!Strings.isNullOrEmpty(str)) {
			return str + SEPARATOR + "ALL";
		}
		return null;
	}

	/**
	 * 比较日期和order组成的Key pass_date_order
	 * 
	 * @param args0
	 * @param args1
	 * @return
	 */
	private String getLeveKey(String args0, String args1) {
		if (!Strings.isNullOrEmpty(args0) && Strings.isNullOrEmpty(args1)) {
			return args0;
		}
		if (Strings.isNullOrEmpty(args0) && !Strings.isNullOrEmpty(args1)) {
			return args1;
		}
		if (!Strings.isNullOrEmpty(args0) && !Strings.isNullOrEmpty(args1)) {
			String values0[] = args0.split(SEPARATOR);
			String values1[] = args1.split(SEPARATOR);
			Integer date0 = new Integer(values0[1]);
			Integer date1 = new Integer(values1[1]);
			if (date0 > date1) {
				return args0;
			} else if (date0.toString().equals(date1.toString())) {
				/* 比较 order */
				Integer order0 = new Integer(values0[2]);
				Integer order1 = new Integer(values1[2]);
				if (order0 > order1) {
					return args0;
				} else if (order0.toString().equals(order1.toString())) {
					return args1;
				} else if (order0 < order1) {
					return args1;
				}
			} else if (date0 < date1) {
				return args1;
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public StarActivityInfo resLevelString(SentryAgent quickSentry, String activityId, String userId) {
		String params = "data={\"service\":\"getStartActivity\",\"reqData\":{\"activityID\":" + activityId + "}}";
		try {
			String resultHttp = quickSentry.expressSendHttp(params);
			logger.info("[DAMS#ActivityController]读取初始化关卡的数据是:" + resultHttp);
			if (resultHttp != null) {
				JSONObject jsonObject = JSONObject.fromObject(resultHttp);
				JSONObject JsonObject = JSONObject.fromObject(jsonObject.get("resData"));
				if (JsonObject != null) {
					List list = (List) JsonObject.get("levels");
					String activityPackage = JsonObject.getString("activityPackage").toString();
					if (list == null || list.size() == 0) {

						return null;
					}
					LevelDomain level = getInitLevel(list);
					// 组装初始吊牌活动信息详情 关卡排序获取order为1的关卡信息
					StarActivityInfo starActivityInfo = new StarActivityInfo();

					starActivityInfo.setRanking(0);
					starActivityInfo.setUserID(new BigInteger(userId));
					starActivityInfo.setActivityID(new BigInteger(activityId));
					starActivityInfo.setScore(new BigInteger("0"));
					starActivityInfo.setLevelLogoMC(level.getMobilePhoto());
					starActivityInfo.setLevelLogoPC(level.getPcPhoto());
					starActivityInfo.setLevelName(level.getLevelname());
					starActivityInfo.setActivityPackage(activityPackage);
				
					List<Present> lsList = new ArrayList<>();
					for (GiftRules gifts : level.getShowgifts()) {
						Present present = new Present();
						present.setLimitSum(Integer.valueOf(gifts.getLimitSum()));
						present.setProductID(new BigInteger(gifts.getProductID()));
						present.setSum(0);
						lsList.add(present);
					}
					starActivityInfo.setGifts(lsList);

					return starActivityInfo;
				}
			}
		} catch (Exception e) {
			logger.error("sdms请求ocms吊牌活动详情信息失败，失败的原因是:", e);
			return null;
		}
		return null;
	}

	public BigInteger getScore(String tableName, String userID) {
		byte[] _data = null;
		try {
			_data = HbaseUtil.getStormColumnData(config, tableName, userID, FAMILIY, SCORE_ALL);
			if (null != _data) {
				return new BigInteger(_data);
			}
		} catch (Exception e) {
			logger.error("hbase 查询score_all列数据失败，失败的原因:", e);
		}
		return null;
	}

	public Integer getRanking(String tableName, String userID) {
		byte[] _data = null;
		try {
			_data = HbaseUtil.getStormColumnData(config, tableName, userID, FAMILIY, RANK);
			if (null != _data) {
				return new Integer(new BigInteger(Bytes.toString(_data)).toString());
			}
		} catch (Exception e) {
			logger.error("hbase 查询rank列数据失败，失败的原因:", e);
		}
		return null;
	}

	/**
	 * 读取初始化关卡
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private LevelDomain getInitLevel(List levels) throws Exception {
		if (levels != null && levels.size() > 0) {
			for (int i = 0; i < levels.size(); i++) {
				String json = levels.get(i).toString();
				TmpLevelDomain tmpLevel = (TmpLevelDomain) JsonUtil.getObjectFromJson(json, TmpLevelDomain.class);
				if (tmpLevel != null) {
					LevelDomain level = LevelDomainUtil.getLevelDomainFromTemp(tmpLevel);
					if (level.getOrder() == 1)
						return level;
				}
			}
		}
		return null;
	}

	/**
	 * 判断关卡是否存在
	 * @return
	 */
	public  boolean getPassIsExsist(String starId,String activityId)throws Exception{
		/* 判断列是否存在 */
		String tableName = HBASETABLE_PREFIX + activityId;
		boolean flag = HbaseUtil.getHbaseColumnFlag(config, tableName, starId, FAMILIY, "pass_"+SDF.format(new Date()));
		return flag;
	}

}

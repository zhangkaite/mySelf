package com.ttmv.service.worker.impl.added;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.bean.query.QueryGoodNumberExpire;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.dao.inter.mysql.IScanRecordInter;
import com.ttmv.dao.inter.redis.IRedisGoodNumberExpireInter;
import com.ttmv.dao.util.JsonUtil;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.redisqueue.GoodNumberCallbackQ;
import com.ttmv.service.inform.redisqueue.GoodNumberInformQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;

@SuppressWarnings("rawtypes")
@Service
public class InOverdueGoodNumberWorkerImpl extends AbstractWorker {
	private static Logger logger = LogManager.getLogger(InOverdueGoodNumberWorkerImpl.class);
	// mysql每次扫表记录信息表接口
	@Resource(name = "iScanRecordInterImpl")
	private IScanRecordInter iScanRecordInter;
	// 用户中心获取用户资料
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;
	// mysql到期查询业务
	@Resource(name = "iGoodNumberExpireInterImpl")
	private IGoodNumberExpireInter iGoodNumberExpireInter;
	// redis到期业务查询
	@Resource(name = "iRedisGoodNumberExpireInterImpl")
	private IRedisGoodNumberExpireInter iRedisGoodNumberExpireInter;
	// 到期通知
	@Resource
	private GoodNumberCallbackQ goodNumberCallbackQ;
	// 到期提醒
	@Resource
	private GoodNumberInformQ goodNumberInformQ;

	/**
	 * 将mysql符合条件的数据录入redis
	 */
	@Override
	 @Scheduled(cron = "0 0 01 * * ? ")
    //@Scheduled(cron = "0 0/5 * * * ? ")
	public void traversalMysql() {
		logger.info(" 靓号 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis) 开始");
		QueryGoodNumberExpire queryGoodNumberExpire = new QueryGoodNumberExpire();
		List<GoodNumberExpire> ls = null;
		Date date = new Date();
		// 获取mysql记录的最新时间作为查询的起始时间
		ScanRecord scRecord = null;
		try {
			scRecord = iScanRecordInter.queryScanRecord(OverdueConstant.GOODNUMBER_TYPE);
			if (null == scRecord) {
				Date currentDate = new Date();
				scRecord = new ScanRecord();
				scRecord.setExpireType(OverdueConstant.GOODNUMBER_TYPE);
				scRecord.setEndTime(currentDate);
				try {
					iScanRecordInter.addScanRecord(scRecord);
				} catch (Exception e) {
					logger.error("mysql 靓号添加刷表记录操作失败，失败的原因是：", e);
				}
				queryGoodNumberExpire.setStartTime(DateUtil.getDate(OverdueConstant.STARTTIME));
			} else {
				queryGoodNumberExpire.setStartTime(scRecord.getEndTime());
			}

			Date time = DateUtil.getQueryFixedTime(date, 1, 1);
			// getQueryFixedTime(当前时间, 类型（日/时/分/秒）, 多少天)
			// 获取当前时间天的下一天时间
			queryGoodNumberExpire.setEndTime(time);
			try {
				ls = iGoodNumberExpireInter.queryListGoodNumberExpireByEndTime(queryGoodNumberExpire);
				// 把符合条件的数据存放到redis中
				try {
					if (null != ls && ls.size() > 0) {
						iRedisGoodNumberExpireInter.addPipList(ls);
					}
					// 更新mysql操作记录的最新时间
					if (null != scRecord) {
						Date currentDate = new Date();
						scRecord.setEndTime(currentDate);
						try {
							iScanRecordInter.updateScanRecord(scRecord);
						} catch (Exception e) {
							logger.error("mysql靓号更新刷表记录操作失败，失败的原因是：", e);
						}
					}
				} catch (Exception e1) {
					logger.error("靓号到期查询的数据放入redis失败，失败的原因是：", e1);
				}
			} catch (Exception e) {
				logger.error("靓号到期提醒mysql查询符合条件的数据查询失败，失败的原因：", e);
			}

		} catch (Exception e1) {
			logger.error("靓号 mysql记录表查询失败，失败的原因是：", e1);
		}

	}

	/**
	 * 高频扫描redis数据，对到期的数据向外通知，通知完成以后删除redis里已通知的数据
	 */
	@Scheduled(cron = "0/5 * *  * * ? ")
	@Override
	public void traversalRedis() {
		// logger.debug(" 靓号遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 开始");
		List<GoodNumberExpire> resultSet = null;
		try {
			resultSet = iRedisGoodNumberExpireInter.queryRedisGoodNumberExpire(new Date());
			if (null != resultSet && resultSet.size() > 0) {
				for (GoodNumberExpire goodNumberExpire : resultSet) {
					String user_id = goodNumberExpire.getUserId() + "";
					String goodTTnum = goodNumberExpire.getGoodNumberId() + "";
					// 根据user_id和goodTTnum查询靓号详情信息
					GoodNumberExpire godEntity = iGoodNumberExpireInter.queryGoodNumberExpire(new BigInteger(user_id),
							new BigInteger(goodTTnum));
					int numType = godEntity.getNumType();
					// 调用用户中心查询用户资料 查询用户昵称 、tt号
					String user_json = getUserInfo.excute(user_id);
					Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
					Map result_map = (Map) map.get("resData");
					String res_code = map.get("resultCode").toString();
					if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {
						try {
							String nickName = result_map.get("nickName") + "";
							String TTnum = result_map.get("TTnum") + "";
							UserInfoBean usBean = new UserInfoBean();
							usBean.setUserid(user_id);
							usBean.setTTnum(TTnum);
							usBean.setNickName(nickName);
							usBean.setGoodTTnum(goodTTnum);
							usBean.setGoodNumEndTime(
									String.valueOf(DateUtil.getUnixDate(goodNumberExpire.getEndTime())));
							usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
							if (numType > 0) {
								usBean.setNumType(numType);
							}
							// usBean.setGoodNumFlag(Constant.UNBINDFLAG);
							goodNumberCallbackQ.excute(usBean);
							try {
								logger.info("靓号到期删除数据中心redis数据，删除的数据用户ID:" + user_id + ",靓号：" + goodTTnum);
								iRedisGoodNumberExpireInter.deleteRedisGoodNumberExpire(user_id, goodTTnum);
							} catch (Exception e) {
								logger.error("靓号删除ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 失败", e);
							}
						} catch (Exception e) {
							logger.error("靓号已期时间提醒时间转换失败，失败的原因：", e);
						}

					}else{
                        iRedisGoodNumberExpireInter.deleteRedisGoodNumberExpire(user_id, goodTTnum);
                        logger.info("靓号到期删除在用户中心不存在的用户user_id:"+user_id+";goodTTnum:"+goodTTnum);
                    }
				}
			}
		} catch (Exception e) {
			logger.error("靓号到期redis查询失败，失败的原因是：", e);
			e.printStackTrace();
		}
		// logger.debug("靓号遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 结束");

	}

	/**
	 * 七天到期提醒
	 */
	@Override
	@Scheduled(cron = "0 0 01 * * ? ")
    //@Scheduled(cron = "0 0/5 * * * ? ")
	public void refreshMysql() {
		logger.info("靓号到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		// 获取当前时间后七天的日期
		QueryGoodNumberExpire queryGoodNumberExpire = new QueryGoodNumberExpire();
		queryGoodNumberExpire.setRemindTime(date);
		List<GoodNumberExpire> ls = null;
		try {
			ls = (List<GoodNumberExpire>) iGoodNumberExpireInter
					.queryListGoodNumberExpireByRemindTime(queryGoodNumberExpire);
		} catch (Exception e) {
			logger.error("靓号到期提醒数据查询失败，失败的原因：" + e.getMessage());
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			GoodNumberExpire goodNumberExpire = (GoodNumberExpire) iterator.next();
			String user_id = goodNumberExpire.getUserId() + "";
			String goodTTnum = goodNumberExpire.getGoodNumberId() + "";
			GoodNumberExpire godEntity = null;
			try {
				godEntity = iGoodNumberExpireInter.queryGoodNumberExpire(new BigInteger(user_id),
						new BigInteger(goodTTnum));
			} catch (Exception e1) {
				logger.error("靓号到期七天提醒根据userid和goodTTnum查询失败，失败的原因是：", e1);
			}
			int numType = godEntity.getNumType();
			String user_json = getUserInfo.excute(user_id);
			Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			Map result_map = (Map) map.get("resData");
			String res_code = map.get("resultCode").toString();
			if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {

				try {
					String nickName = result_map.get("nickName") + "";
					String TTnum = result_map.get("TTnum") + "";
					UserInfoBean usBean = new UserInfoBean();
					usBean.setUserid(user_id);
					usBean.setTTnum(TTnum);
					usBean.setNickName(nickName);
					usBean.setGoodTTnum(goodTTnum);
					usBean.setGoodNumEndTime(String.valueOf(DateUtil.getUnixDate(goodNumberExpire.getEndTime())));
					if (numType > 0) {
						usBean.setNumType(numType);
					}
					// usBean.setGoodNumFlag(Constant.UNBINDFLAG);
					goodNumberInformQ.excute(usBean);
				} catch (Exception e) {
					logger.error("靓号到期时间提醒时间转换失败，失败的原因：", e);
				}

			}
		}

	}

	@Scheduled(cron = "0 0 01 * * ? ")
    //@Scheduled(cron = "0 0/5 * * * ? ")
	public void delayToNotify() {
		logger.info("靓号 到期后第一天提醒 通过扫mysql");
		Date date = new Date();
		Date yestday = DateUtil.getQueryFixedTime(date, 1, -1);
		QueryGoodNumberExpire queryGoodNumberExpire = new QueryGoodNumberExpire();
		queryGoodNumberExpire.setStartTime(yestday);
		queryGoodNumberExpire.setEndTime(date);
		List<GoodNumberExpire> ls = null;
		try {
			ls = iGoodNumberExpireInter.queryListDelayNotify(queryGoodNumberExpire);
		} catch (Exception e) {
			logger.error("靓号到期后一天提醒数据查询失败，失败的原因：" + e.getMessage());
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			GoodNumberExpire goodNumberExpire = (GoodNumberExpire) iterator.next();
			String user_id = goodNumberExpire.getUserId() + "";
			String goodTTnum = goodNumberExpire.getGoodNumberId() + "";
			String user_json = getUserInfo.excute(user_id);
			Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			Map result_map = (Map) map.get("resData");
			if (null != result_map) {

				try {
					String nickName = result_map.get("nickName") + "";
					String TTnum = result_map.get("TTnum") + "";
					UserInfoBean usBean = new UserInfoBean();
					usBean.setUserid(user_id);
					usBean.setTTnum(TTnum);
					usBean.setNickName(nickName);
					usBean.setGoodTTnum(goodTTnum);
					usBean.setGoodNumEndTime(String.valueOf(DateUtil.getUnixDate(goodNumberExpire.getEndTime())));
					usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
					goodNumberCallbackQ.excute(usBean);
				} catch (Exception e) {
					logger.error("靓号到期后一天时间提醒时间转换失败，失败的原因：", e);
				}
			}
		}
	}

}

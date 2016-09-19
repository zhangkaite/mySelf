package com.ttmv.service.worker.impl.added;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.bean.query.QueryLuxuryExpire;
import com.ttmv.dao.inter.mysql.ILuxuryExpireInter;
import com.ttmv.dao.inter.mysql.IScanRecordInter;
import com.ttmv.dao.inter.redis.IRedisLuxuryExpireInter;
import com.ttmv.dao.util.JsonUtil;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.redisqueue.LuxuryCarCallbackQ;
import com.ttmv.service.inform.redisqueue.LuxuryCarInformQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;

@SuppressWarnings("rawtypes")
@Service
public class InOverdueLuxuryCarWorkerImpl extends AbstractWorker {
	private static Logger logger = LogManager.getLogger(InOverdueLuxuryCarWorkerImpl.class);
	// mysql每次扫表记录信息表接口
	@Resource(name = "iScanRecordInterImpl")
	private IScanRecordInter iScanRecordInter;
	// 用户中心获取用户资料
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;
	// mysql到期查询业务
	@Resource(name = "iLuxuryExpireInterImpl")
	private ILuxuryExpireInter iLuxuryExpireInter;
	// redis到期业务查询
	@Resource(name = "iRedisLuxuryExpireInterImpl")
	private IRedisLuxuryExpireInter iRedisLuxuryExpireInter;
	// 到期通知
	@Resource(name = "luxuryCarCallbackQ")
	private LuxuryCarCallbackQ luxuryCarCallbackQ;
	// 到期提醒
	@Resource(name = "luxuryCarInformQ")
	private LuxuryCarInformQ luxuryCarInformQ;

	/**
	 * 将mysql符合条件的数据录入redis
	 */
	@Override
	@Scheduled(cron = "0 0 01 * * ? ")
	public void traversalMysql() {
		logger.info("豪车 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis) 开始");
		QueryLuxuryExpire queryLuxuryExpire = new QueryLuxuryExpire();
		List<LuxuryExpire> ls = null;
		Date date = new Date();
		// 获取mysql记录的最新时间作为查询的起始时间
		ScanRecord scRecord = null;
		try {
			scRecord = iScanRecordInter.queryScanRecord(OverdueConstant.LUXURYCAR_TYPE);
			if (null == scRecord) {
				Date currentDate = new Date();
				scRecord = new ScanRecord();
				scRecord.setExpireType(OverdueConstant.LUXURYCAR_TYPE);
				scRecord.setEndTime(currentDate);
				try {
					iScanRecordInter.addScanRecord(scRecord);
				} catch (Exception e) {
					logger.error("mysql 豪车添加刷表记录操作失败，失败的原因是：", e);
				}
				queryLuxuryExpire.setStartTime(DateUtil.getDate(OverdueConstant.STARTTIME));
			}else{
				queryLuxuryExpire.setStartTime(scRecord.getEndTime());
			}
			Date time = DateUtil.getQueryFixedTime(date, 1, 1);
			// getQueryFixedTime(当前时间, 类型（日/时/分/秒）, 多少天)
			// 获取当前时间天的下一天时间
			queryLuxuryExpire.setEndTime(time);
			try {
				ls = iLuxuryExpireInter.queryListLuxuryExpireByEndTime(queryLuxuryExpire);
				// 把符合条件的数据存放到redis中
				try {
					if (null != ls && ls.size() > 0) {
						iRedisLuxuryExpireInter.addPipList(ls);
					}
					// 更新mysql操作记录的最新时间
					if (null != scRecord) {
						Date currentDate = new Date();
						scRecord.setEndTime(currentDate);
						try {
							iScanRecordInter.updateScanRecord(scRecord);
						} catch (Exception e) {
							logger.error("mysql 豪车更新刷表记录操作失败，失败的原因是：", e);
						}
					}
				} catch (Exception e1) {
					logger.error("豪车到期查询的数据放入redis失败，失败的原因是：", e1);
				}
			} catch (Exception e) {
				logger.error("豪车到期提醒mysql查询符合条件的数据查询失败，失败的原因：", e);
			}

		} catch (Exception e1) {
			logger.error("豪车 mysql记录表查询失败，失败的原因是：", e1);
		}

	}

	/**
	 * 高频扫描redis数据，对到期的数据向外通知，通知完成以后删除redis里已通知的数据
	 */
	@Scheduled(cron = "0/5 * *  * * ? ")
	@Override
	public void traversalRedis() {
		// logger.debug(" 豪车遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 开始");
		List<LuxuryExpire> resultSet = null;
		try {
			resultSet = iRedisLuxuryExpireInter.queryRedisLuxuryExpire(new Date());
			if (null != resultSet && resultSet.size() > 0) {
				for (LuxuryExpire luxuryExpire : resultSet) {
					String user_id = luxuryExpire.getUserId()+"";
					String car_id = luxuryExpire.getCarId()+"";
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
							usBean.setLuxuryCarID(car_id);
							usBean.setLuxuryCarEndTime(String.valueOf(DateUtil.getUnixDate(luxuryExpire
								.getEndTime())));
							usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
							// 拼装爵位到期服务接口 json
							luxuryCarCallbackQ.excute(usBean);
							try {
								logger.info("豪车到期删除数据中心redis数据，删除的数据用户ID:" + user_id + ",豪车号：" + car_id);
								iRedisLuxuryExpireInter.deleteRedisLuxuryExpire(user_id, car_id);
							} catch (Exception e) {
								logger.error("豪车删除ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 失败", e);
							}
						} catch (Exception e) {
							logger.error("豪车已期提醒截至时间转换失败，失败的原因：", e);
						}

					}else{
                        iRedisLuxuryExpireInter.deleteRedisLuxuryExpire(user_id, car_id);
                        logger.info("豪车到期删除在用户中心不存在的用户user_id:"+user_id+";car_id:"+car_id);
                    }

				}
			}
		} catch (Exception e) {
			logger.error("豪车到期redis查询失败，失败的原因是：", e);
			e.printStackTrace();
		}
		// logger.debug("豪车遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 结束");

	}

	/**
	 * 七天到期提醒
	 */
	@Override
	@Scheduled(cron = "0 0 01 * * ? ")
	public void refreshMysql() {
		logger.info("豪车到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		QueryLuxuryExpire queryLuxuryExpire = new QueryLuxuryExpire();
		queryLuxuryExpire.setRemindTime(date);
		List<LuxuryExpire> ls = null;
		try {
			ls = (List<LuxuryExpire>) iLuxuryExpireInter.queryListLuxuryExpireByRemindTime(queryLuxuryExpire);
		} catch (Exception e) {
			logger.error("豪车到期提醒数据查询失败，失败的原因：", e);
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			LuxuryExpire luxuryExpire = (LuxuryExpire) iterator.next();
			String user_id = luxuryExpire.getUserId()+"";
			String car_id = luxuryExpire.getCarId()+"";
			String user_json = getUserInfo.excute(user_id);
			Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			Map result_map = (Map) map.get("resData");
			String res_code = map.get("resultCode").toString();
			if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {
				try {
					String nickName = (String) result_map.get("nickName");
					String TTnum = result_map.get("TTnum") + "";
					UserInfoBean usBean = new UserInfoBean();
					usBean.setUserid(user_id);
					usBean.setTTnum(TTnum);
					usBean.setNickName(nickName);
					usBean.setLuxuryCarID(car_id);
					usBean.setLuxuryCarEndTime(String.valueOf(DateUtil.getUnixDate(luxuryExpire.getEndTime())));
					luxuryCarInformQ.excute(usBean);
				} catch (Exception e) {
					logger.error("豪车到期提醒截至时间转换失败，失败的原因：", e);
				}

			}
		}

	}
	
	@Scheduled(cron = "0 0 01 * * ? ")
	public void delayToNotify() {
		logger.info("豪车到期后一天提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		Date yestday = DateUtil.getQueryFixedTime(date, 1, -1);
		QueryLuxuryExpire queryLuxuryExpire = new QueryLuxuryExpire();
		queryLuxuryExpire.setStartTime(yestday);
		queryLuxuryExpire.setEndTime(date);
		List<LuxuryExpire> ls = null;
		try {
			ls = (List<LuxuryExpire>) iLuxuryExpireInter.queryListDelayNotify(queryLuxuryExpire);
		} catch (Exception e) {
			logger.error("豪车到期提醒数据查询失败，失败的原因：", e);
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			LuxuryExpire luxuryExpire = (LuxuryExpire) iterator.next();
			String user_id = luxuryExpire.getUserId()+"";
			String car_id = luxuryExpire.getCarId()+"";
			String user_json = getUserInfo.excute(user_id);
			Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			Map result_map = (Map) map.get("resData");
			String res_code = map.get("resultCode").toString();
			if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {
				try {
					String nickName = (String) result_map.get("nickName");
					String TTnum = result_map.get("TTnum") + "";
					UserInfoBean usBean = new UserInfoBean();
					usBean.setUserid(user_id);
					usBean.setTTnum(TTnum);
					usBean.setNickName(nickName);
					usBean.setLuxuryCarID(car_id);
					usBean.setLuxuryCarEndTime(String.valueOf(DateUtil.getUnixDate(luxuryExpire.getEndTime())));
					usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
					luxuryCarCallbackQ.excute(usBean);
				} catch (Exception e) {
					logger.error("豪车到期提醒截至时间转换失败，失败的原因：", e);
				}

			}
		}
	}

}

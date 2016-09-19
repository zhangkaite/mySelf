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

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.bean.query.QueryNobilityExpire;
import com.ttmv.dao.inter.mysql.INobilityExpireInter;
import com.ttmv.dao.inter.mysql.IScanRecordInter;
import com.ttmv.dao.inter.redis.IRedisNobilityExpireInter;
import com.ttmv.dao.util.JsonUtil;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.redisqueue.NobilityCallbackQ;
import com.ttmv.service.inform.redisqueue.NobilityInformQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;

/**
 * 爵位开通定时任务(mysql ——> redis)
 * 
 * @author Damon 2015年11月13日09:40:43
 */
@SuppressWarnings("rawtypes")
@Service
public class InOverdueNobilityWorkerImpl extends AbstractWorker {

	private static Logger logger = LogManager.getLogger(InOverdueNobilityWorkerImpl.class);

	// 依赖注入爵位到期业务接口 mysql查询
	@Resource(name = "iNobilityExpireInterImpl")
	private INobilityExpireInter iNobilityExpireInter;

	// 到期业务redis查询
	@Resource(name = "iRedisNobilityExpireInterImpl")
	private IRedisNobilityExpireInter iRedisNobilityExpireInter;

	// mysql每次扫表记录信息表接口
	@Resource(name = "iScanRecordInterImpl")
	private IScanRecordInter iScanRecordInter;

	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;// 用户中心获取用户资料
	// 拼装爵位到期服务接口 json
	@Resource(name = "nobilityCallbackQ")
	private NobilityCallbackQ nobilityCallbackQ;
	// 到期业务提醒
	@Resource(name = "nobilityInformQ")
	private NobilityInformQ nobilityInformQ;

	/**
	 * 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis)
	 */

	@Scheduled(cron = "0 0 01 * * ? ")
	public void traversalMysql() {
		logger.info(" 爵位 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis) 开始");
		QueryNobilityExpire queryNobilityExpire = new QueryNobilityExpire();
		List<NobilityExpire> ls = null;
		Date date = new Date();
		// 获取mysql记录的最新时间作为查询的起始时间
		ScanRecord scRecord = null;
		try {
			scRecord = iScanRecordInter.queryScanRecord(OverdueConstant.NOBILITY_TYPE);
			if (null == scRecord) {
				Date currentDate = new Date();
				scRecord = new ScanRecord();
				scRecord.setExpireType(OverdueConstant.NOBILITY_TYPE);
				scRecord.setEndTime(currentDate);
				try {
					iScanRecordInter.addScanRecord(scRecord);
				} catch (Exception e) {
					logger.error("mysql 爵位添加刷表记录操作失败，失败的原因是：", e);
				}
				queryNobilityExpire.setStartTime(DateUtil.getDate(OverdueConstant.STARTTIME));
			}else{
				queryNobilityExpire.setStartTime(scRecord.getEndTime());
			}
			Date time = DateUtil.getQueryFixedTime(date, 1, 1);
			// getQueryFixedTime(当前时间, 类型（日/时/分/秒）, 多少天)
			// 获取当前时间天的下一天时间
			queryNobilityExpire.setEndTime(time);
			try {
				ls = iNobilityExpireInter.queryListNobilityExpireByEndTime(queryNobilityExpire);
				// 把符合条件的数据存放到redis中
				try {
					if (null != ls && ls.size() > 0) {
						iRedisNobilityExpireInter.addPipList(ls);
					}
					// 更新mysql操作记录的最新时间
					if (null != scRecord) {
						Date currentDate = new Date();
						scRecord.setEndTime(currentDate);
						try {
							iScanRecordInter.updateScanRecord(scRecord);
						} catch (Exception e) {
							logger.error("mysql 爵位更新刷表记录操作失败，失败的原因是：", e);
						}
					}
				} catch (Exception e1) {
					logger.error("爵位到期查询的数据放入redis失败，失败的原因是：", e1);
				}

			} catch (Exception e) {
				logger.error("爵位到期提醒mysql查询符合条件的数据查询失败，失败的原因：", e);
			}
		} catch (Exception e1) {
			logger.error("mysql爵位查询失败，失败的原因是：", e1);
		}

	}

	/**
	 * 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知)
	 */
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void traversalRedis() {
		// 获取当天时间开始时间时间戳、结束时间时间戳
		// logger.debug(" 爵位遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 开始");
		List<NobilityExpire> resultSet = null;
		try {
			resultSet = iRedisNobilityExpireInter.queryRedisNobilityExpire(new Date());
			if (null != resultSet && resultSet.size() > 0) {
				for (NobilityExpire nobilityExpire : resultSet) {
					String user_id = nobilityExpire.getUserId().toString();
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
							usBean.setNobilityEndTime(String.valueOf(DateUtil.getUnixDate(nobilityExpire
								.getEndTime())));
							usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
							// 拼装爵位到期服务接口 json
							nobilityCallbackQ.excute(usBean);
							try {
								logger.info("爵位到期删除数据中心redis数据，删除的数据用户ID:" + user_id);
								iRedisNobilityExpireInter.deleteRedisNobilityExpire(user_id);
							} catch (Exception e) {
								logger.error("爵位删除ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 失败", e);
							}
						} catch (Exception e) {
							logger.error("爵位已到期提醒时间转换失败，失败的原因：", e);
						}

					}else{
                        iRedisNobilityExpireInter.deleteRedisNobilityExpire(user_id);
                        logger.info("爵位到期删除在用户中心不存在的用户user_id:"+user_id);
                    }
				}
			}
		} catch (Exception e) {
			logger.error("爵位到期redis查询失败，失败的原因是：", e);
			e.printStackTrace();
		}
		// logger.debug(" 爵位 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 结束");
	}

	/***
	 * 爵位到期提醒刷mysql(刷新频率一天一次)
	 */
	@Scheduled(cron = "0 0 01 * * ? ")
	public void refreshMysql() {
		logger.info("爵位到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		// 获取当前时间后七天的日期
		QueryNobilityExpire queryNobilityExpire = new QueryNobilityExpire();
		queryNobilityExpire.setRemindTime(date);
		List<NobilityExpire> ls = null;
		try {
			ls = (List<NobilityExpire>) iNobilityExpireInter
				.queryListNobilityExpireByRemindTime(queryNobilityExpire);
		} catch (Exception e) {
			logger.error("爵位到期提醒数据查询失败，失败的原因：", e);
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			NobilityExpire nobilityExpire = (NobilityExpire) iterator.next();
			String user_id = String.valueOf(nobilityExpire.getUserId());
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
					usBean.setNobilityEndTime(String.valueOf(DateUtil.getUnixDate(nobilityExpire.getEndTime())));
					nobilityInformQ.excute(usBean);
				} catch (Exception e) {
					logger.error("爵位到期提醒时间转换失败，失败的原因：", e);
				}

			}
		}
	}
	
	
	@Scheduled(cron = "0 0 01 * * ? ")
	public void delayToNotify() {
		logger.info("爵位到期后一天提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		Date yestday = DateUtil.getQueryFixedTime(date, 1, -1);
		QueryNobilityExpire queryNobilityExpire = new QueryNobilityExpire();
		queryNobilityExpire.setStartTime(yestday);
		queryNobilityExpire.setEndTime(date);
		List<NobilityExpire> ls = null;
		try {
			ls = (List<NobilityExpire>) iNobilityExpireInter
				.queryListDelayNotify(queryNobilityExpire);
		} catch (Exception e) {
			logger.error("爵位到期后一天提醒数据查询失败，失败的原因：", e);
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			NobilityExpire nobilityExpire = (NobilityExpire) iterator.next();
			String user_id = String.valueOf(nobilityExpire.getUserId());
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
					usBean.setNobilityEndTime(String.valueOf(DateUtil.getUnixDate(nobilityExpire.getEndTime())));
					usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
					nobilityCallbackQ.excute(usBean);
				} catch (Exception e) {
					logger.error("爵位到期后一天提醒时间转换失败，失败的原因：", e);
				}

			}
		}
		
	}

}

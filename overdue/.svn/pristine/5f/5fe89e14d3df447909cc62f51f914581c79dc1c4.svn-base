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

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.bean.query.QueryVipExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.dao.inter.mysql.IScanRecordInter;
import com.ttmv.dao.inter.mysql.IVipExpireInter;
import com.ttmv.dao.inter.redis.IRedisVipExpireInter;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.http.VipCallBack;
import com.ttmv.service.callback.redisqueue.GoodNumberCallbackQ;
import com.ttmv.service.callback.redisqueue.UserVipCallbackQ;
import com.ttmv.service.callback.redisqueue.VipBindGoodNumberCallbackQ;
import com.ttmv.service.inform.redisqueue.GoodNumberInformQ;
import com.ttmv.service.inform.redisqueue.UserVipInformQ;
import com.ttmv.service.inform.redisqueue.VipBindGoodNumberInformQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;
import com.ttmv.web.controller.util.JsonUtil;

/**
 * 会员开通定时任务(mysql ——> redis)
 * 
 * @author Damon 2015年11月13日09:40:43
 */
@SuppressWarnings("rawtypes")
@Service
public class InOverdueVipWorkerImpl extends AbstractWorker {

	private static Logger logger = LogManager.getLogger(InOverdueVipWorkerImpl.class);
	// 会员mysql查询
	@Resource(name = "iVipExpireInterImpl")
	private IVipExpireInter iVipExpireInter;
	// 会员redis查询
	@Resource(name = "iRedisVipExpireInterImpl")
	private IRedisVipExpireInter iRedisVipExpireInter;

	// mysql每次扫表记录信息表接口
	@Resource(name = "iScanRecordInterImpl")
	private IScanRecordInter iScanRecordInter;

	// 用户中心回调修改资料
	@Resource(name = "vipCallBack")
	private VipCallBack vipCallBack;

	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;// 用户中心获取用户资料

	// 会员续费提醒通知消息
	@Resource(name = "userVipInformQ")
	private UserVipInformQ userVipInformQ;

	@Resource(name = "userVipCallbackQ")
	private UserVipCallbackQ userVipCallbackQ;

	// 添加靓号到期通知
	@Resource(name = "goodNumberCallbackQ")
	private GoodNumberCallbackQ goodNumberCallbackQ;

	// 添加靓号到期续费提醒
	@Resource(name = "goodNumberInformQ")
	private GoodNumberInformQ goodNumberInformQ;

	// mysql到期查询业务
	@Resource(name = "iGoodNumberExpireInterImpl")
	private IGoodNumberExpireInter iGoodNumberExpireInter;
	
	@Resource(name="vipBindGoodNumberInformQ")
   private VipBindGoodNumberInformQ	vipBindGoodNumberInformQ;
	
	@Resource(name="vipBindGoodNumberCallbackQ")
	private VipBindGoodNumberCallbackQ vipBindGoodNumberCallbackQ;

	/**
	 * 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis)
	 */
	@Scheduled(cron = "0 0 01 * * ? ")
   // @Scheduled(cron = "0 0/5 * * * ? ")
	// 每天凌晨04:00触发
	public void traversalMysql() {
		logger.info(" vip 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis) 开始");
		QueryVipExpire queryVipExpire = new QueryVipExpire();
		Date date = new Date();
		// 获取mysql记录的最新时间作为查询的起始时间
		ScanRecord scRecord = null;
		try {
			scRecord = iScanRecordInter.queryScanRecord(OverdueConstant.VIP_TYPE);
			//如果记录表为空，则扫描当前时间后一天到1970年的所有数据
			if (null == scRecord) {
				Date currentDate = new Date();
				scRecord = new ScanRecord();
				scRecord.setExpireType(OverdueConstant.VIP_TYPE);
				scRecord.setEndTime(currentDate);
				try {
					iScanRecordInter.addScanRecord(scRecord);
				} catch (Exception e) {
					logger.error("mysql vip会员添加刷表记录操作失败，失败的原因是：", e);
				}
				queryVipExpire.setStartTime(DateUtil.getDate(OverdueConstant.STARTTIME));
			}else{
				queryVipExpire.setStartTime(scRecord.getEndTime());
			}
			Date time = DateUtil.getQueryFixedTime(date, 1, 1);
			
			// getQueryFixedTime(当前时间, 类型（日/时/分/秒）, 多少天)
			queryVipExpire.setEndTime(time);
			List<VipExpire> ls = null;
			try {
				ls = iVipExpireInter.queryListVipExpireByEndTime(queryVipExpire);
				// 把符合条件的数据存放到redis中
				try {
					if (null != ls && ls.size() > 0) {
						iRedisVipExpireInter.addPipList(ls);
					}
					// 更新mysql操作记录的最新时间
					if (null != scRecord) {
						Date currentDate = new Date();
						scRecord.setEndTime(currentDate);
						try {
							iScanRecordInter.updateScanRecord(scRecord);
						} catch (Exception e) {
							logger.error("mysql vip更新刷表记录操作失败，失败的原因是：", e);
						}
					}
				} catch (Exception e) {
					logger.error("vip到期查询的数据放入redis失败，失败的原因是：", e);
				}
			} catch (Exception e) {
				logger.error("vip到期提醒mysql查询符合条件的数据查询失败，失败的原因：", e);
			}

		} catch (Exception e1) {
			logger.error("vip mysql查询失败，失败的原因是：", e1);
		}
		logger.info(" vip 遍历MySql库(低频率筛选数据，符合条件数据添加到ReDis) 结束");

	}

	/**
	 * 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知)
	 */
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void traversalRedis() {
		// logger.debug(" vip 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 开始");
		List<VipExpire> resultSet = null;
		try {
			resultSet = iRedisVipExpireInter.queryRedisVipExpire(new Date());
			if (null != resultSet && resultSet.size() > 0) {
				for (VipExpire vipExpire : resultSet) {
					String user_id = vipExpire.getUserId().toString();
					Date end_time = vipExpire.getEndTime();
					// 回调ucenter接口，修改用户状态
					logger.info("vip会员到期回调用户中心接口，修改用户会员状态");
					String result_json = vipCallBack.excute(user_id, end_time.getTime() / 1000 + "");
					Map map = (Map) JsonUtil.getObjectFromJson(result_json, Map.class);
					String result_code = map.get("resultCode").toString();
					if (OverdueConstant.SUCCESS_FLAG.equals(result_code)) {
						String user_json = getUserInfo.excute(user_id);
						Map maps = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
						Map result_map = (Map) maps.get("resData");
						String res_code = map.get("resultCode").toString();
						if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {
							try {
								String nickName = result_map.get("nickName") + "";
								String TTnum = result_map.get("TTnum") + "";
								UserInfoBean usBean = new UserInfoBean();
								usBean.setUserid(user_id);
								usBean.setTTnum(TTnum);
								usBean.setNickName(nickName);
								usBean.setVipEndTime(String.valueOf(DateUtil.getUnixDate(vipExpire.getEndTime())));
								logger.info("vip到期通知向IM、php redis推送消息");
							//	userVipCallbackQ.excute(usBean);
								try {
									GoodNumberExpire goodNumberExpire = new GoodNumberExpire();
									goodNumberExpire.setUserId(vipExpire.getUserId());
									goodNumberExpire.setFlag(Constant.FLAG_OFF);
									// 根据用户id查询绑定的靓号
									GoodNumberExpire gExpire = iGoodNumberExpireInter.queryBindedNum(goodNumberExpire);
									if (null != gExpire) {
										usBean.setGoodTTnum(gExpire.getGoodNumberId().toString());
										logger.info("vip到期如果绑定的有靓号，则通知靓号到期 ，向redis推送消息");
										//给出vip绑定靓号组合到期通知
										usBean.setGoodNumEndTime(String.valueOf(DateUtil.getUnixDate(vipExpire.getEndTime())));
										
										vipBindGoodNumberCallbackQ.excute(usBean);
										//靓号解绑
										gExpire.setFlag(null);
										iGoodNumberExpireInter.updateFlag(gExpire);
									}else{
										usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
										userVipCallbackQ.excute(usBean);
									}
								} catch (Exception e) {
									logger.error("根据用户id查询靓号失败，失败的原因是：", e);
								}

								try {
									logger.info("会员到期删除数据中心redis数据，删除的数据用户ID:" + user_id);
									iRedisVipExpireInter.deleteRedisVipExpire(user_id);
								} catch (Exception e) {
									logger.error(" vip 删除ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 失败", e);
								}
							} catch (Exception e1) {
								logger.error("vip 到期提醒 遍历ReDis库时间转换失败，失败的原因是：", e1);
							}

						}else{
                            iRedisVipExpireInter.deleteRedisVipExpire(user_id);
                            logger.info("vip到期删除在用户中心不存在的用户user_id:"+user_id);
                        }
					}
				}

			}
		} catch (Exception e) {
			logger.error("vip redis遍历redis库查询失败，失败的原因是：", e);
		}

		// logger.debug(" vip 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知) 结束");
	}

	/**
	 * vip 到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)
	 * 查询提醒时间从今天开始的数据，查询的时间段00:00:01-23:59:59
	 */
	@Override
	 @Scheduled(cron = "0 0 01 * * ? ")
    //@Scheduled(cron = "0 0/5 * * * ? ")
	public void refreshMysql() {
		logger.info("vip 到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)");
		Date date = new Date();
		QueryVipExpire queryVipExpire = new QueryVipExpire();
		List<VipExpire> ls = null;
		try {
			queryVipExpire.setRemindTime(date);
			ls = iVipExpireInter.queryListVipExpireByRemindTime(queryVipExpire);
		} catch (Exception e) {
			logger.error("vip到期提醒查询符合条件的数据查询失败，失败的原因：", e);
		}
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			VipExpire vipExpire = (VipExpire) iterator.next();
			String user_id = String.valueOf(vipExpire.getUserId());
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
					usBean.setVipEndTime(String.valueOf(DateUtil.getUnixDate(vipExpire.getEndTime())));
					logger.info("vip到期提醒向IM、php redis推送消息");
					//如果会员没有绑定靓号，给出会员到期提醒，如果会员绑定靓号，则给出会员靓号组合提醒
					try {
						GoodNumberExpire goodNumberExpire = new GoodNumberExpire();
						goodNumberExpire.setUserId(vipExpire.getUserId());
						goodNumberExpire.setFlag(Constant.FLAG_OFF);
						// 根据用户id查询绑定的靓号
						GoodNumberExpire gExpire = iGoodNumberExpireInter.queryBindedNum(goodNumberExpire);
						if (null != gExpire) {
							usBean.setGoodTTnum(gExpire.getGoodNumberId().toString());
							logger.info("vip到期如果绑定的有靓号，则通知靓号到期 ，向redis推送消息");
							usBean.setGoodNumEndTime(String.valueOf(DateUtil.getUnixDate(vipExpire.getEndTime())));
							vipBindGoodNumberInformQ.excute(usBean);
							
						}else {
							userVipInformQ.excute(usBean);
						}
					} catch (Exception e) {
						logger.error("根据用户id查询靓号失败，失败的原因是：", e);
					}
					
					
					
					
				} catch (Exception e) {
					logger.error("vip 到期提醒 通过扫mysql将数据写入redis (刷新频率一天一次)时间转换失败，失败的原因是：", e);
				}

			}
		}
	}

	/***
	 * vip到期一天后提醒 刷mysql数据库
	 */
	 @Scheduled(cron = "0 0 01 * * ? ")
     // @Scheduled(cron = "0 0/5 * * * ? ")
	public void delayToNotify() {
		logger.info("vip 到期后第一天提醒 通过扫mysql");
		Date date = new Date();
		Date yestday = DateUtil.getQueryFixedTime(date, 1, -1);
		QueryVipExpire queryVipExpire = new QueryVipExpire();
		queryVipExpire.setStartTime(yestday);
		queryVipExpire.setEndTime(date);
		List<VipExpire> ls = null;
		try {
			ls = iVipExpireInter.queryListDelayNotify(queryVipExpire);
			for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
				VipExpire vipExpire = (VipExpire) iterator.next();
				String user_id = String.valueOf(vipExpire.getUserId());
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
						usBean.setVipEndTime(String.valueOf(DateUtil.getUnixDate(vipExpire.getEndTime())));
						usBean.setCurrentDate(String.valueOf(DateUtil.getUnixDate(new Date())));
						userVipCallbackQ.excute(usBean);
					} catch (Exception e) {
						logger.error("vip 到期后一天提醒 通过扫mysql将数据写入redis (刷新频率一天一次)时间转换失败，失败的原因是：", e);
					}

				}
			}
		} catch (Exception e) {
			logger.error("vip到期后一天提醒mysql数据查询失败,失败的原因是：",e);
		}

	}

}

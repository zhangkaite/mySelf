package com.ttmv.service.worker.impl.control;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.LiveRoomBanExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisLiveRoomBanExpireInter;
import com.ttmv.dao.util.JsonUtil;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.redisqueue.BanLiveRoomCallbackQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;

/**
 * ClassName: InOverdueLiveRoomBanWorkerImpl <br/>
 * Function: 遍历房间冻结redis队列，筛选复合条件的数据，将数据写入im、php redis消息队列 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年11月20日 下午3:37:23 <br/>
 *
 * @author zkt
 * @version
 * @since JDK 1.6
 */
@SuppressWarnings("rawtypes")
@Service
public class InOverdueLiveRoomBanWorkerImpl extends AbstractWorker {

	private static Logger logger = LogManager.getLogger(InOverdueLiveRoomBanWorkerImpl.class);

	// redis依赖
	@Resource(name = "iRedisLiveRoomBanExpireInterImpl")
	private IRedisLiveRoomBanExpireInter iRedisLiveRoomBanExpireInter;
	@Resource
	private BanLiveRoomCallbackQ banLiveRoomCallbackQ;

	// 用户中心获取用户资料
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;

	@Override
	public void traversalMysql() {
		// TODO Auto-generated method stub
	}

	/**
	 * 遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知)
	 */

	@Override
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void traversalRedis() {
		List<LiveRoomBanExpire> ls = null;
		try {
			ls = iRedisLiveRoomBanExpireInter.queryRedisLiveRoomBanExpire(new Date());
		} catch (Exception e) {
			logger.error("房间禁播redis查询失败，失败的原因是：", e);
		}
		if (null != ls && ls.size() > 0) {
			for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
				LiveRoomBanExpire LiveRoomBanExpire = (LiveRoomBanExpire) iterator.next();
				// 回调用户中心冻结用户http服务
				String user_id = LiveRoomBanExpire.getUserId().toString();
				Date end_time = LiveRoomBanExpire.getEndTime();
				String chanelID=LiveRoomBanExpire.getChanelID();
				String user_json = getUserInfo.excute(user_id);
				Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
				Map result_map = (Map) map.get("resData");
				UserInfoBean usBean = new UserInfoBean();
				String res_code = map.get("resultCode").toString();
				if (null != result_map && OverdueConstant.SUCCESS_FLAG.equals(res_code)) {
					try {
						usBean.setUserid(user_id);
						String nickName = result_map.get("nickName") + "";
						String TTnum = result_map.get("TTnum") + "";
						usBean.setUserid(user_id);
						usBean.setTTnum(TTnum);
						usBean.setNickName(nickName);
						usBean.setChanelID(chanelID);
						usBean.setForbiddenEndTime(String.valueOf(DateUtil.getUnixDate(end_time)));
						banLiveRoomCallbackQ.excute(usBean);
						try {
							logger.info("房间禁播删除数据中心redis数据，删除的数据用户ID:" + user_id);
							iRedisLiveRoomBanExpireInter.deleteRedisLiveRoomBanExpire(LiveRoomBanExpire.getUserId()
									+ Constant.SEPARATOR + LiveRoomBanExpire.getChanelID());
						} catch (Exception e) {
							logger.error("房间禁播redis数据删除失败，失败的原因是：", e);

						}

					} catch (Exception e) {
						logger.error("房间禁播查询用户中心失败，失败的原因是:",e);
					}
				}else {
					try {
						iRedisLiveRoomBanExpireInter.deleteRedisLiveRoomBanExpire(user_id
								+ Constant.SEPARATOR +chanelID);
						logger.info("房间禁播查询用户中心未查到用户信息user_id:"+user_id+",从redis删除该条数据channelID:"+chanelID);
					} catch (Exception e) {
						logger.error("房间禁播redis数据删除失败，失败的原因是：", e);
					}
				}
			}
		}

	}

	@Override
	public void refreshMysql() {
		// TODO Auto-generated method stub

	}

}

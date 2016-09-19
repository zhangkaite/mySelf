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

import com.ttmv.dao.bean.RoomForbiddenExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisRoomForbiddenExpireInter;
import com.ttmv.dao.util.JsonUtil;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.callback.redisqueue.ForbiddenRoomCallbackQ;
import com.ttmv.service.tools.UserInfoBean;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.worker.AbstractWorker;

/**
 * ClassName: InOverdueRoomForbiddenWorkerImpl <br/>
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
public class InOverdueRoomForbiddenWorkerImpl extends AbstractWorker {

	private static Logger logger = LogManager.getLogger(InOverdueRoomForbiddenWorkerImpl.class);

	// redis依赖
	@Resource(name = "iRedisRoomForbiddenExpireInterImpl")
	private IRedisRoomForbiddenExpireInter iRedisRoomForbiddenExpireInter;
	@Resource
	private ForbiddenRoomCallbackQ forbiddenRoomCallbackQ;

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
		List<RoomForbiddenExpire> ls = null;
		try {
			ls = iRedisRoomForbiddenExpireInter.queryRedisRoomForbiddenExpire(new Date());
		} catch (Exception e) {
			logger.error("冻结房间redis查询失败，失败的原因是：", e);
		}
		if (null != ls && ls.size() > 0) {
			for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
				RoomForbiddenExpire RoomForbiddenExpire = (RoomForbiddenExpire) iterator.next();
				// 回调用户中心冻结用户http服务
				String user_id = RoomForbiddenExpire.getUserId().toString();
				Date end_time = RoomForbiddenExpire.getEndTime();
				String chanel_id = RoomForbiddenExpire.getChanelID();
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
						usBean.setChanelID(chanel_id);
						usBean.setForbiddenEndTime(String.valueOf(DateUtil.getUnixDate(end_time)));
						forbiddenRoomCallbackQ.excute(usBean);
						try {
							logger.info("频道(房间)冻结删除数据中心redis数据，删除的数据用户ID:" + user_id);
							iRedisRoomForbiddenExpireInter.deleteRedisRoomForbiddenExpire(RoomForbiddenExpire
									.getUserId() + Constant.SEPARATOR + RoomForbiddenExpire.getChanelID());
						} catch (Exception e) {
							logger.error("频道(房间)冻结redis数据删除失败，失败的原因是：", e);

						}

					} catch (Exception e) {
						logger.error("频道(房间)冻结调用用户中心查询用户资料失败，失败的原因:",e);
					}
				}else{
					try {
						iRedisRoomForbiddenExpireInter.deleteRedisRoomForbiddenExpire(user_id
								 + Constant.SEPARATOR + chanel_id);
						logger.info("频道(房间)冻结删除数据中心redis数据，删除用户中心不存在的用户ID:" + user_id+";chanel_id:"+chanel_id);
					} catch (Exception e) {
						logger.error("频道(房间)冻结redis数据删除失败，失败的原因是：", e);
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

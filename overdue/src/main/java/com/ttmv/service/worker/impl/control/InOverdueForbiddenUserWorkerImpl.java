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

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.inter.redis.IRedisUserForbiddenExpireInter;
import com.ttmv.service.callback.http.UserStateCallBack;
import com.ttmv.service.tools.constant.OverdueConstant;
import com.ttmv.service.worker.AbstractWorker;
import com.ttmv.web.controller.util.JsonUtil;

/**
 * ClassName: InOverdueForbiddenUserWorkerImpl <br/>
 * Function: 遍历用户冻结redis队列，筛选复合条件的数据，回调用户中心接口修改用户冻结状态 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年11月20日 下午3:37:23 <br/>
 *
 * @author zkt
 * @version
 * @since JDK 1.6
 */
@SuppressWarnings("rawtypes")
@Service
public class InOverdueForbiddenUserWorkerImpl extends AbstractWorker {

	private static Logger logger = LogManager.getLogger(InOverdueForbiddenUserWorkerImpl.class);

	// redis依赖
	@Resource(name = "iRedisUserForbiddenExpireInterImpl")
	private IRedisUserForbiddenExpireInter iRedisUserForbiddenExpireInter;
	@Resource
	private UserStateCallBack userStateCallBack;
	

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
		List<UserForbiddenExpire> ls = null;
		try {
			ls = iRedisUserForbiddenExpireInter.queryRedisUserForbiddenExpire(new Date());
		} catch (Exception e) {
			logger.error("冻结用户redis查询失败，失败的原因是：", e);
		}
		if (null != ls && ls.size() > 0) {
			for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
				UserForbiddenExpire userForbiddenExpire = (UserForbiddenExpire) iterator.next();
				// 回调用户中心冻结用户http服务
				String user_id = userForbiddenExpire.getUserId().toString();
				Date end_time = userForbiddenExpire.getEndTime();
				String result_json = userStateCallBack.excute(user_id, end_time.getTime() / 1000 + "");
				Map map = (Map) JsonUtil.getObjectFromJson(result_json, Map.class);
				String result_code = map.get("resultCode").toString();
				if (OverdueConstant.SUCCESS_FLAG.equals(result_code)) {
					// 从redis删除该条记录
					try {
						logger.info("用户冻结删除数据中心redis数据，删除的数据用户ID:" + user_id);
						iRedisUserForbiddenExpireInter.deleteRedisUserForbiddenExpire(userForbiddenExpire
							.getUserId()
							+ "");
					} catch (Exception e) {
						logger.error("用户冻结redis数据删除失败，失败的原因是：", e);

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

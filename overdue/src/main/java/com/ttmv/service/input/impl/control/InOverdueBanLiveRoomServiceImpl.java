package com.ttmv.service.input.impl.control;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.LiveRoomBanExpire;
import com.ttmv.dao.inter.redis.IRedisLiveRoomBanExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;
import com.ttmv.service.tools.util.UtilBean;

/**
 * @explain 频道禁播
 * @author Damon
 * @time 2015年11月10日14:33:33
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueBanLiveRoomServiceImpl")
public class InOverdueBanLiveRoomServiceImpl extends OverdueService {
	private static Logger logger = LogManager.getLogger(InOverdueBanLiveRoomServiceImpl.class);

	@Resource(name = "iRedisLiveRoomBanExpireInterImpl")
	private IRedisLiveRoomBanExpireInter iRedisLiveRoomBanExpireInter;
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;
	@Override
	public Map handler(Map reqMap) {
		logger.info("频道禁播服务开始...");
		// 数据校验
		try {
			this.checkData(reqMap);
			// db对象组装
			LiveRoomBanExpire liveRoomBanExpire = null;
			/*String user_json = getUserInfo.excute(reqMap.get("userID")+"");
			Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			Map result_map = (Map) map.get("resData");*/
			//if (null != result_map) {
				try {
					liveRoomBanExpire = (LiveRoomBanExpire) this.getDataObject(reqMap);
					try {
						iRedisLiveRoomBanExpireInter.addRedisLiveRoomBanExpire(liveRoomBanExpire);
					} catch (Exception e) {
						logger.error("频道禁播redis信息添加失败，失败的原因是：", e);
						return ResponseTool.returnException();

					}
				} catch (Exception e) {
					logger.error("入库对象[LiveRoomBanExpire]组装失败", e);
					return ResponseTool.returnException();
				}
			//}
			

		} catch (Exception e) {
			logger.error("数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}

		// 返回处理结果
		return ResponseTool.returnSuccess();
	}

	@Override
	public Object getDataObject(Map reqMap) throws Exception {
		LiveRoomBanExpire liveRoomBanExpire = new LiveRoomBanExpire();
		liveRoomBanExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		liveRoomBanExpire.setChanelID(reqMap.get("chanelID").toString());
		liveRoomBanExpire.setEndTime(UtilBean.unixTimeFmt(Long
			.parseLong(reqMap.get("forbiddenEndTime").toString())));
		return liveRoomBanExpire;
	}

	@Override
	public void checkData(Map reqMap) throws Exception {
		if (reqMap.get("userID") == null || "".equals(reqMap.get("userID"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[userID] is null...]");
		}
		if (reqMap.get("startTime") == null || "".equals(reqMap.get("startTime"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[startTime] is null...]");
		}
		if (reqMap.get("duration") == null || "".equals(reqMap.get("duration"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[duration] is null...]");
		}
		if (reqMap.get("tag") == null || "".equals(reqMap.get("tag"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[tag] is null...]");
		}
		if (reqMap.get("chanelID") == null || "".equals(reqMap.get("chanelID"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[chanelID] is null...]");
		}
		
		if (reqMap.get("forbiddenEndTime") == null || "".equals(reqMap.get("forbiddenEndTime"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[forbiddenEndTime] is null...]");
		}

	}

}

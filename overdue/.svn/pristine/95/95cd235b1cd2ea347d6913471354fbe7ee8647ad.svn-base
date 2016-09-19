package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.inter.mysql.INobilityExpireInter;
import com.ttmv.dao.inter.redis.IRedisNobilityExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.tools.util.UtilBean;

/**
 * @explain 爵位到期（增值服务）
 * @author Damon
 * @Time 2015年11月10日14:18:31
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueNobilityServiceImpl")
public class InOverdueNobilityServiceImpl extends OverdueService {
	private static Logger logger = LogManager.getLogger(InOverdueNobilityServiceImpl.class);

	// 添加数据层业务依赖
	@Resource(name = "iNobilityExpireInterImpl")
	private INobilityExpireInter iNobilityExpireInter;
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;
	@Resource(name = "iRedisNobilityExpireInterImpl")
	private IRedisNobilityExpireInter iRedisNobilityExpireInter;

	@Override
	public Map handler(Map reqMap) {
		logger.info("爵位到期续费。。。。");

		try {
			checkData(reqMap);
			// db对象组装
			NobilityExpire nobilityExpire = null;
			/*
			 * String user_json = getUserInfo.excute(reqMap.get("userID") + "");
			 * Map map = (Map) JsonUtil.getObjectFromJson(user_json, Map.class);
			 * Map result_map = (Map) map.get("resData");
			 */
			// if (null != result_map) {
			try {
				nobilityExpire = (NobilityExpire) this.getDataObject(reqMap);
				// add数据
				NobilityExpire noExpire = null;

				try {
					noExpire = iNobilityExpireInter.queryNobilityExpire(nobilityExpire.getUserId());
					if (null == noExpire) {
						iNobilityExpireInter.addNobilityExpire(nobilityExpire);
					} else {
						nobilityExpire.setId(noExpire.getId());
						iNobilityExpireInter.updateNobilityExpire(nobilityExpire);
						iRedisNobilityExpireInter.deleteRedisNobilityExpire(nobilityExpire.getUserId().toString());

					}
				} catch (Exception e) {
					logger.error("数据库操作失败！！！", e);
					return ResponseTool.returnException();
				}
			} catch (Exception e) {
				logger.error("入库对象[VipExpire]组装失败", e);
				return ResponseTool.returnException();
			}
			/*
			 * }else{ logger.error("该用户不存在！！！！"); return
			 * ResponseTool.returnException(); }
			 */

		} catch (Exception e) {
			logger.error("数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		logger.info("爵位到期续费成功。。。。");
		// 返回处理结果
		return ResponseTool.returnSuccess();
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */
	public Object getDataObject(Map reqMap) throws Exception {
		NobilityExpire nobilityExpire = new NobilityExpire();
		nobilityExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		// （unix时间戳）long转java date
		nobilityExpire.setEndTime(UtilBean.unixTimeFmt(Long.parseLong(reqMap.get("nobilityEndTime").toString())));
		nobilityExpire
				.setRemindTime(DateUtil.getQueryFixedTime(DateUtil.getDate(reqMap.get("nobilityEndTime").toString()), 1, -7));
		return nobilityExpire;
	}

	/**
	 * 业务数据校验(数据规格)
	 * 
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if (reqMap.get("userID") == null || "".equals(reqMap.get("userID"))) {
			throw new Exception("[InOverdueNobilityServiceImpl[userID] is null...]");
		}
		if (reqMap.get("startTime") == null || "".equals(reqMap.get("startTime"))) {
			throw new Exception("[InOverdueNobilityServiceImpl[startTime] is null...]");
		}
		if (reqMap.get("duration") == null || "".equals(reqMap.get("duration"))) {
			throw new Exception("[InOverdueNobilityServiceImpl[duration] is null...]");
		}
		if (reqMap.get("tag") == null || "".equals(reqMap.get("tag"))) {
			throw new Exception("[InOverdueNobilityServiceImpl[tag] is null...]");
		}
		if (reqMap.get("nobilityEndTime") == null || "".equals(reqMap.get("nobilityEndTime"))) {
			throw new Exception("[InOverdueNobilityServiceImpl[nobilityEndTime] is null...]");
		}
	}

}

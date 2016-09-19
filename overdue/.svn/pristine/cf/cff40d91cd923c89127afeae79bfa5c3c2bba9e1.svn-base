package com.ttmv.service.input.impl.control;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.inter.redis.IRedisUserForbiddenExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;
import com.ttmv.service.tools.util.UtilBean;

/**
 * @explain 用户冻结 将获取的数据写入redis
 * @author Damon
 * @time 2015年11月10日14:20:16
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueForbiddenUserServiceImpl")
public class InOverdueForbiddenUserServiceImpl extends OverdueService {

	private static Logger logger = LogManager.getLogger(InOverdueForbiddenUserServiceImpl.class);

	// redis依赖
	@Resource(name = "iRedisUserForbiddenExpireInterImpl")
	private IRedisUserForbiddenExpireInter iRedisUserForbiddenExpireInter;
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;

	@Override
	public Map handler(Map reqMap) {
		logger.info("[reqId]:"+reqMap.get("reqId")+"@@用户冻结业务处理@@[userID]:"+reqMap.get("userID") +"--->>>Start...");
		// 数据校验
		try {
			this.checkData(reqMap);
			// db对象组装
			UserForbiddenExpire userForbiddenExpire = null;
				try {
					userForbiddenExpire = (UserForbiddenExpire) this.getDataObject(reqMap);
					// 将数据刷入redis，如果redis里面没有用户冻结信息，直接新增，如果已存在用户冻结信息，则覆盖该条信息
					try {
						iRedisUserForbiddenExpireInter.addRedisUserForbiddenExpire(userForbiddenExpire);
					} catch (Exception e) {
						logger.error("用户冻结redis信息添加失败，失败的原因是：", e);
						return ResponseTool.returnException();

					}
				} catch (Exception e) {
					logger.error("入库对象[UserForbiddenExpire]组装失败", e);
					return ResponseTool.returnException();
				}

		} catch (Exception e) {
			logger.error("数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}

		logger.info("[reqId]:"+reqMap.get("reqId")+"@@用户冻结业务处理@@[userID]:"+reqMap.get("userID") +"--->>>End");
		// 返回处理结果
		return ResponseTool.returnSuccess();
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */
	public Object getDataObject(Map reqMap) throws Exception {
		UserForbiddenExpire userForbiddenExpire = new UserForbiddenExpire();
		userForbiddenExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		// （unix时间戳）long转java date
		userForbiddenExpire.setEndTime(UtilBean.unixTimeFmt(Long.parseLong(reqMap.get("forbiddenEndTime")
			.toString())));
		return userForbiddenExpire;
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
		if (reqMap.get("forbiddenEndTime") == null || "".equals(reqMap.get("forbiddenEndTime"))) {
			throw new Exception("[InOverdueForbiddenUserServiceImpl[forbiddenEndTime] is null...]");
		}
	}

}

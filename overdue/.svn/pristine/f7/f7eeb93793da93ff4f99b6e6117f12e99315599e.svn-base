package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.dao.inter.redis.IRedisGoodNumberExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.tools.util.UtilBean;

/**
 * @explain 靓号到期（增值服务）
 * @author Damon
 * @Time 2015年11月10日14:32:12
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueGoodNumberServiceImpl")
public class InOverdueGoodNumberServiceImpl extends OverdueService {

	private static Logger logger = LogManager.getLogger(InOverdueGoodNumberServiceImpl.class);
	@Resource(name = "iGoodNumberExpireInterImpl")
	private IGoodNumberExpireInter iGoodNumberExpireInter;
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;
	@Resource(name = "iRedisGoodNumberExpireInterImpl")
	private IRedisGoodNumberExpireInter iRedisGoodNumberExpireInter;
	
	@Override
	public Map handler(Map reqMap) {
		logger.info("靓号到期续费。。。。");
		try {
			checkData(reqMap);
			// db对象组装
			GoodNumberExpire goodNumberExpire = null;
			try {
				goodNumberExpire = (GoodNumberExpire) this.getDataObject(reqMap);
				// add数据
				GoodNumberExpire gExpire = null;
					try {
						gExpire = iGoodNumberExpireInter.queryGoodNumberExpire(goodNumberExpire.getUserId(),
							goodNumberExpire.getGoodNumberId());
						if (null == gExpire) {
							iGoodNumberExpireInter.addGoodNumberExpire(goodNumberExpire);
						} else {
							goodNumberExpire.setId(gExpire.getId());
							iGoodNumberExpireInter.updateGoodNumberExpire(goodNumberExpire);
							iRedisGoodNumberExpireInter.deleteRedisGoodNumberExpire(goodNumberExpire.getUserId().toString(),
							goodNumberExpire.getGoodNumberId().toString());
						}
					} catch (Exception e) {
						logger.error("靓号到期数据库操作失败！！！", e);
						return ResponseTool.returnException();
					}
			
			} catch (Exception e) {
				logger.error("入库对象[GoodNumberExpire]组装失败", e);
				return ResponseTool.returnException();
			}
		} catch (Exception e) {
			logger.error("数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		logger.info("靓号到期续费成功。。。。");
		// 返回处理结果
		return ResponseTool.returnSuccess();
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */

	public Object getDataObject(Map reqMap) throws Exception {
		GoodNumberExpire goodNumberExpire = new GoodNumberExpire();
		goodNumberExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		goodNumberExpire.setGoodNumberId(new BigInteger(reqMap.get("goodTTnum").toString()));
		// （unix时间戳）long转java date
		goodNumberExpire.setEndTime(UtilBean.unixTimeFmt(Long.parseLong(reqMap.get("goodNumEndTime").toString())));
		goodNumberExpire.setRemindTime(DateUtil.getQueryFixedTime(DateUtil.getDate(reqMap.get("goodNumEndTime")
			.toString()), 1, -7));
		if (null!=reqMap.get("numType")) {
			goodNumberExpire.setNumType((Integer)reqMap.get("numType"));
		}else{
			goodNumberExpire.setNumType(1);
		}
		return goodNumberExpire;
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
			throw new Exception("[InOverdueGoodNumberServiceImpl[userID] is null...]");
		}
		if (reqMap.get("startTime") == null || "".equals(reqMap.get("startTime"))) {
			throw new Exception("[InOverdueGoodNumberServiceImpl[startTime] is null...]");
		}
		if (reqMap.get("duration") == null || "".equals(reqMap.get("duration"))) {
			throw new Exception("[InOverdueGoodNumberServiceImpl[duration] is null...]");
		}
		if (reqMap.get("tag") == null || "".equals(reqMap.get("tag"))) {
			throw new Exception("[InOverdueGoodNumberServiceImpl[tag] is null...]");
		}
		if (reqMap.get("goodNumEndTime") == null || "".equals(reqMap.get("goodNumEndTime"))) {
			throw new Exception("[InOverdueGoodNumberServiceImpl[goodNumEndTime] is null...]");
		}
	}

}

package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.inter.mysql.IVipExpireInter;
import com.ttmv.dao.inter.redis.IRedisVipExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;
import com.ttmv.service.tools.util.DateUtil;
import com.ttmv.service.tools.util.UtilBean;

/**
 * @explain 会员到期（增值服务）
 * @Date 2015年11月10日14:20:54
 * @author Damon
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueVipServiceImpl")
public class InOverdueVipServiceImpl extends OverdueService {
	private static Logger logger = LogManager.getLogger(InOverdueVipServiceImpl.class);

	@Resource(name = "iVipExpireInterImpl")
	private IVipExpireInter iVipExpireInter;
	// 会员redis查询
	@Resource(name = "iRedisVipExpireInterImpl")
	private IRedisVipExpireInter iRedisVipExpireInter;

	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;

	public Map handler(Map reqMap) {
		logger.info("[reqId]:"+reqMap.get("reqId")+"@@开通续费VIP业务处理@@[userID]:"+reqMap.get("userID") +"--->>>Start...");
		// 数据校验
		try {
			this.checkData(reqMap);
			// db对象组装
			VipExpire vipExpire = null;
			try {
				vipExpire = (VipExpire) this.getDataObject(reqMap);
					//判断当前的数据是新增还是更新
					try {
						VipExpire vExpire = iVipExpireInter.queryVipExpire(vipExpire.getUserId());
						if (null == vExpire) {
							iVipExpireInter.addVipExpire(vipExpire);
						} else {
							vipExpire.setId(vExpire.getId());
							iVipExpireInter.updateVipExpire(vipExpire);
							// 删除redis存在的userid
							try {
								iRedisVipExpireInter.deleteRedisVipExpire(vipExpire.getUserId() + "");
							} catch (Exception e) {
								// 需不需要重试机制
								logger.error("vip操作redis失败，失败的原因是:", e);
							}
						}
					} catch (Exception e) {
						logger.error("vip会员mysql数据库操作失败！！！", e);
						return ResponseTool.returnException();
					}
			} catch (Exception e) {
				logger.error("入库对象[VipExpire]组装失败", e);
				return ResponseTool.returnException();
			}
			
		} catch (Exception e) {
			logger.error("数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		// 返回处理结果
		logger.info("reqId:"+reqMap.get("reqId")+"@@开通续费VIP业务处理成功！！！@@" +"userID:"+reqMap.get("userID") +"-->>>End");
		return ResponseTool.returnSuccess();
		
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */
	public Object getDataObject(Map reqMap) throws Exception {

		VipExpire vipExpire = new VipExpire();
		vipExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		// （unix时间戳）long转java date
		vipExpire.setEndTime(UtilBean.unixTimeFmt(Long.parseLong(reqMap.get("vipEndTime").toString())));
		vipExpire.setRemindTime(DateUtil.getQueryFixedTime(DateUtil.getDate(reqMap.get("vipEndTime").toString()),
			1, -7));
		return vipExpire;
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
			throw new Exception("[InOverdueVipServiceImpl[userID] is null...]");
		}
		if (reqMap.get("startTime") == null || "".equals(reqMap.get("startTime"))) {
			throw new Exception("[InOverdueVipServiceImpl[startTime] is null...]");
		}
		if (reqMap.get("duration") == null || "".equals(reqMap.get("duration"))) {
			throw new Exception("[InOverdueVipServiceImpl[duration] is null...]");
		}
		if (reqMap.get("tag") == null || "".equals(reqMap.get("tag"))) {
			throw new Exception("[InOverdueVipServiceImpl[tag] is null...]");
		}
		if (reqMap.get("vipEndTime") == null || "".equals(reqMap.get("vipEndTime"))) {
			throw new Exception("[InOverdueVipServiceImpl[vipEndTime] is null...]");
		}
	}

}

package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;

/**
 * 01104_靓号到期提醒关闭
 * 
 * @author kate
 *
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueCloseGoodNumberServiceImpl")
public class InOverdueCloseGoodNumberServiceImpl extends OverdueService {

	private static Logger logger = LogManager.getLogger(InOverdueCloseGoodNumberServiceImpl.class);
	@Resource(name = "iGoodNumberExpireInterImpl")
	private IGoodNumberExpireInter iGoodNumberExpireInter;

	@Override
	public Map handler(Map reqMap) {
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
							goodNumberExpire.getGoodNumberId(), goodNumberExpire.getNumType());
					if (null != gExpire) {
						iGoodNumberExpireInter.deleteGoodNumberExpire(gExpire.getId());
					}
				} catch (Exception e) {
					logger.error("靓号到期提醒关闭数据库操作失败！！！", e);
					return ResponseTool.returnException();
				}

			} catch (Exception e) {
				logger.error("靓号到期提醒关闭入库对象[GoodNumberExpire]组装失败", e);
				return ResponseTool.returnException();
			}
		} catch (Exception e) {
			logger.error("靓号到期提醒关闭数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		logger.info("靓号到期提醒关闭处理成功");
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
		goodNumberExpire.setNumType((Integer) reqMap.get("numType"));
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
			throw new Exception("[InOverdueCloseGoodNumberServiceImpl[userID] is null...]");
		}
		if (reqMap.get("goodTTnum") == null || "".equals(reqMap.get("goodTTnum"))) {
			throw new Exception("[InOverdueCloseGoodNumberServiceImpl[goodTTnum] is null...]");
		}
		if (reqMap.get("numType") == null || "".equals(reqMap.get("numType"))) {
			throw new Exception("[InOverdueCloseGoodNumberServiceImpl[numType] is null...]");
		}
		if (reqMap.get("time") == null || "".equals(reqMap.get("time"))) {
			throw new Exception("[InOverdueCloseGoodNumberServiceImpl[time] is null...]");
		}
	}

}

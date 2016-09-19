package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.inter.mysql.ILuxuryExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;

/**
 * @explain 豪车到期（增值服务）
 * @author Damon
 * @Time 2015年11月10日14:23:45
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueCloseLuxuryCarServiceImpl")
public class InOverdueCloseLuxuryCarServiceImpl extends OverdueService {

	private static Logger logger = LogManager.getLogger(InOverdueCloseLuxuryCarServiceImpl.class);
	@Resource(name = "iLuxuryExpireInterImpl")
	private ILuxuryExpireInter iLuxuryExpireInter;

	@Override
	public Map handler(Map reqMap) {
		try {
			checkData(reqMap);
			
		} catch (Exception e) {
			logger.error("[[豪车到期提醒关闭]]数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		
		LuxuryExpire luxuryExpire=null;
		try {
			luxuryExpire = iLuxuryExpireInter.queryLuxuryExpire(
					new BigInteger(reqMap.get("userID").toString()), new BigInteger(reqMap.get("carID").toString()));
			if (null!=luxuryExpire) {
				iLuxuryExpireInter.deleteLuxuryExpire(luxuryExpire.getId());
			}
		} catch (Exception e) {
			logger.error("[[豪车到期提醒关闭]]数据删除操作失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		logger.info("[[豪车到期提醒关闭]]处理成功！！！");
		// 返回处理结果
		return ResponseTool.returnSuccess();
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */

	public Object getDataObject(Map reqMap) throws Exception {

		return null;
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
			throw new Exception("[InOverdueCloseLuxuryCarServiceImpl[userID] is null...]");
		}
		if (reqMap.get("time") == null || "".equals(reqMap.get("time"))) {
			throw new Exception("[InOverdueCloseLuxuryCarServiceImpl[time] is null...]");
		}

		if (reqMap.get("carID") == null || "".equals(reqMap.get("carID"))) {
			throw new Exception("[InOverdueCloseLuxuryCarServiceImpl[carID] is null...]");
		}

	}

}

package com.ttmv.service.input.impl.added;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.callback.http.GetUserInfo;
import com.ttmv.service.input.ResponseTool;

@SuppressWarnings("rawtypes") 
@Service("manageGoodTTNumServiceImpl")
public class ManageGoodTTNumServiceImpl extends OverdueService {
	private static Logger logger = LogManager.getLogger(ManageGoodTTNumServiceImpl.class);
	@Resource(name = "iGoodNumberExpireInterImpl")
	private IGoodNumberExpireInter iGoodNumberExpireInter;
	@Resource(name = "getUserInfo")
	private GetUserInfo getUserInfo;

	@Override
	public Map handler(Map obj) {
		logger.info("[reqId]:"+obj.get("reqId")+"@@靓号绑定或解绑业务处理@@[userID]:"+obj.get("userID") +"--->>>Start...");
		try {
			checkData(obj);
			try {
				GoodNumberExpire goodNumberExpire = (GoodNumberExpire) getDataObject(obj);
				//校验请求的用户是否存在
				if (null != goodNumberExpire.getType() && Constant.FLAG_OFF.equals(goodNumberExpire.getType())) {
					goodNumberExpire.setFlag(Constant.FLAG_OFF);
				} else {
					goodNumberExpire.setFlag(null);
				}
				iGoodNumberExpireInter.updateFlag(goodNumberExpire);
			} catch (Exception e) {
				logger.error("靓号绑定对象组装失败，失败的原因是:", e);
				return ResponseTool.returnException();
			}
		} catch (Exception e) {
			logger.error("靓号绑定校验失败，失败的原因是：", e);
			return ResponseTool.returnException();
		}
		logger.info("[reqId]:"+obj.get("reqId")+"@@靓号绑定或解绑业务处理@@[userID]:"+obj.get("userID") +"--->>>End");
		return ResponseTool.returnSuccess();
	}

	/**
	 * 组装入库对象
	 * 
	 * @return
	 */

	public Object getDataObject(Map reqMap) throws Exception {
		GoodNumberExpire goodExpire = new GoodNumberExpire();
		goodExpire.setUserId(new BigInteger(reqMap.get("userID").toString()));
		goodExpire.setGoodNumberId(new BigInteger(reqMap.get("goodTTnum").toString()));
		goodExpire.setType(reqMap.get("type").toString());
		return goodExpire;

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
			throw new Exception("[BindGoodNumImpl[userID] is null...]");
		}
		if (reqMap.get("goodTTnum") == null || "".equals(reqMap.get("goodTTnum"))) {
			throw new Exception("[BindGoodNumImpl[goodTTnum] is null...]");
		}

		if (reqMap.get("type") == null || "".equals(reqMap.get("type"))) {
			throw new Exception("[BindGoodNumImpl[type] is null...]");
		}

	}

}

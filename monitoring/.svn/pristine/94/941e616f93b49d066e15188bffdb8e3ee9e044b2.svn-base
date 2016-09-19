package com.ttmv.monitoring.webService.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月17日12:01:02
 * @explain : W007_用户唯一性校验
 */
@SuppressWarnings({ "rawtypes","unchecked"})
public class CheckUserKeyServiceImpl  implements WebServerInf{
	private static Logger logger = LogManager.getLogger(AddUserServiceImpl.class);
	private IUserInfoInter iUserInfoInter;
	
	/**
	 * 唯一性校验业务处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[CheckUserKeyServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		
		if(SmsConstant.CHECKKEY_USERNAME.equals(reqMap.get("checkKey").toString())){//用户名唯一性校验
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setUserName(reqMap.get("userName").toString());
			List<UserInfo> users = null;
 			try {
 				users = iUserInfoInter.queryUserInfo(userInfoQuery);
			} catch (Exception e) {
				logger.error("用户名查询处理异常!!!" , e);
				return ResponseTool.returnError();
			}
 			if(users.size() == 0){
 				logger.info("校验通过,校验key未被使用!!! checkKey:" + reqMap.get("checkKey") + " size:" + users.size());
 				return ResponseTool.returnSuccess(takeResData(SmsConstant.CHECKRES_ISNULL));
 			}else{
 				logger.debug("校验失败,校验key已被使用!!!checkKey:" + reqMap.get("checkKey") + " size:" + users.size());
 				return ResponseTool.returnSuccess(takeResData(SmsConstant.CHECKRES_ISNOTNULL));
 			}
		}else {
			logger.error("校验类型非法!!!");
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_ILLEGAL_TYPE);
		}
	}
	
	
	/**
	 * 组装返回数据
	 * @param tag
	 * @return resData
	 */
	private Map takeResData(Integer tag){
		Map resData = new HashMap();
		resData.put("result", tag);
		return resData;
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("checkKey") == null || "".equals(reqMap.get("checkKey"))){//校验类型非空验证
			throw new Exception("[CheckUserKey_[checkKey] is null...]");
		}
		if(reqMap.get("checkValue") == null || "".equals(reqMap.get("checkValue"))){//校验值非空验证
			throw new Exception("[CheckUserKey_[checkValue] is null...]");
		}
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}
	
	
	
}

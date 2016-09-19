package com.ttmv.monitoring.webService.impl.user;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:42:49
 * @explain : W004_用户个人信息查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUserByIdServiceImpl implements WebServerInf {
	
	private static Logger logger = LogManager.getLogger(QueryUserByIdServiceImpl.class);
	
	private IUserInfoInter iUserInfoInter;

	/**
	 * 用户详细信息查询逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryUserByIdServiceImpl] @ start...");
		//请求数据验证
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//查询UserInfo
		UserInfo userInfo = null;
		try {
			userInfo = iUserInfoInter.queryUserInfo(new BigInteger(reqMap.get("userID").toString()));
		} catch (Exception e) {
			logger.error("用户信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		if(userInfo == null){
			logger.warn("userID查询用户不存在!!!");
			return ResponseTool.returnSuccess(null);
		}
		//组装返回结果resMap
		Map resData = this.takeResData(userInfo);
		return ResponseTool.returnSuccess(resData);
	}
	
	/**
	 * 组装返回数据
	 * @param resData
	 * @param userInfo
	 * @return 
	 */
	private Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		resData.put("userID", userInfo.getId());//userID
		resData.put("userName", userInfo.getUserName());//userName
		resData.put("mobile", userInfo.getUserMobile());//mobile
		resData.put("email", userInfo.getUserMail());//email
		resData.put("realName", userInfo.getUserRealName());//realName
		resData.put("creatTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userInfo.getCreateTime()));//creatTime
		return resData;
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception{
		if(reqMap.get("userID") == null || "".equals(reqMap.get("userID"))){//userID非空验证
			throw new Exception("[QueryUserById_[userID] is null...]");
		}
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}

	
	
}

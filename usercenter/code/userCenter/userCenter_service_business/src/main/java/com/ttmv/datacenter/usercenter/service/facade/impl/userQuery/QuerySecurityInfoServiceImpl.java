package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.QuerySecurityInfo;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 上午10:51:45  
 * @explain :用户安全中心信息查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QuerySecurityInfoServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QuerySecurityInfoServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[用户安全中心信息查询]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		QuerySecurityInfo querySecurityInfo = null;
		try {
			querySecurityInfo = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserInfo userInfo = null;
		try {
			userInfo = userInfoDao.selectUserInfoByUserId(querySecurityInfo.getUserID());
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		if(userInfo == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(null);
		}
		Map resMap = this.takeResData(userInfo);
		logger.info("[" + reqID + "]@@"+ "[***用户安全中心信息查询成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 拼装返回信息
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		resData.put("TTnum", userInfo.getTTnum());
		resData.put("nickName", userInfo.getNickName());
		resData.put("bindingMobile", userInfo.getBindingMobile());
		resData.put("bindingEmail", userInfo.getBindingEmail());
		resData.put("loginMobile", userInfo.getLoginMobile());
		resData.put("loginEmail", userInfo.getLoginEmail());
		resData.put("LoginGoodTTnum", userInfo.getLoginGoodTTnum());
		return resData;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private QuerySecurityInfo checkData(Object object) throws Exception{
		QuerySecurityInfo querySecurityInfo = (QuerySecurityInfo)object;
		if(querySecurityInfo == null){
			throw new Exception("对象转换失败！！！");
		}
		if(querySecurityInfo.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		return querySecurityInfo;
	}

	
	
}

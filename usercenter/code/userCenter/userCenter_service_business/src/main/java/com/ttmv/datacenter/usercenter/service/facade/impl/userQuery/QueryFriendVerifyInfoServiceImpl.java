package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.GetFriendVerifyInfo;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月13日 上午11:27:26  
 * @explain :SetFriendVerifyInfoServiceImpl  获取好友验证信息
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryFriendVerifyInfoServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryFriendVerifyInfoServiceImpl.class);
	private UserInfoDao userInfoDao;
	
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[获取好友验证信息]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		GetFriendVerifyInfo getFriendVerifyInfo = null;
		try {
			getFriendVerifyInfo = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserInfo userInfo = null;
		try {
			userInfo = userInfoDao.selectUserInfoByUserId(getFriendVerifyInfo.getUserID());
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		//创建返回数据
		logger.info("[" + reqID + "]@@"+ "[***获取好友验证信息成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(this.takeResData(userInfo));
	}
	
	/**
	 * 返回数据
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		resData.put("userID", userInfo.getUserid());//userID
		resData.put("dndType", userInfo.getDndType());//防打扰类型
		resData.put("problemType", userInfo.getProblemType());//问题类型
		resData.put("answer", userInfo.getAnswer());//问题答案
		return resData;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private GetFriendVerifyInfo checkData(Object object) throws Exception{
		GetFriendVerifyInfo getFriendVerifyInfo = (GetFriendVerifyInfo)object;
		if(getFriendVerifyInfo == null){
			throw new Exception("对象转换失败！！！");
		}
		if(getFriendVerifyInfo.getUserID() == null){
			throw new Exception("UserID为空！！！");
		}
		return getFriendVerifyInfo;
	}

	
}

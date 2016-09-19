package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.ForbiddenUser;
import com.ttmv.datacenter.usercenter.domain.protocol.OpeningVIP;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午10:01:27  
 * @explain :删除、冻结用户
 */
@SuppressWarnings({ "rawtypes","unchecked" })
public class ForbiddenUserServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ForbiddenUserServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	private SentryAgent overSentryAgentDJ;
	
	


	public SentryAgent getOverSentryAgentDJ() {
		return overSentryAgentDJ;
	}

	public void setOverSentryAgentDJ(SentryAgent overSentryAgentDJ) {
		this.overSentryAgentDJ = overSentryAgentDJ;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[删除、冻结用户]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		ForbiddenUser forbiddenUser = null;
		//数据校验
		try {
			forbiddenUser = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		//创建修改对象
		UserInfo userInfo = null;
		try {
			userInfo = this.createUserInfo(forbiddenUser,reqID);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" + "UserInfo对象创建失败_", e1);
			return ResponseTool.returnException();
		}
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+ "[数据修改失败！！！]",e);
			return ResponseTool.returnException();
		}
		//TODO 2015年11月10日10:16:37  Damon 冻结命令发送到到期业务系统
		String jsonData = "";
		try {
			jsonData = getJsonData(forbiddenUser);
		} catch (Exception e) {
			logger.warn("冻结计时jsonData组装失败！！！",e);
		}
		String resData = overSentryAgentDJ.expressSendHttp("data="+jsonData);
		if(resData==null || "".equals(resData)){
			logger.error("调用到期系统失败！！！");
			//操作回滚
			try {
				userInfoDao.updateUserInfo(createUserInfoCallBack(forbiddenUser, reqID));
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@"+ "[冻删操作回滚失败失败！！！]",e);
			}
			return ResponseTool.returnException();
		}
		Map resMaap = null;
		try {
			resMaap = (Map) JsonUtil.getObjectFromJson(resData, Map.class);
		} catch (Exception e) {
			logger.error("json转换失败！！！");
		}
		if(resMaap.get("resultCode").equals("AAAAAAA")){
			logger.info("[" + reqID + "]@@"+ "[***删、冻用户成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
			return ResponseTool.returnSuccess(null);
		}else{
			logger.error("用户冻结到期服务处理失败，错误代码:"+resMaap.get("resultCode"));
			logger.error("[到期系统上报消息上报失败:]====>>>" + "data="+jsonData);
			//操作回滚
			try {
				userInfoDao.updateUserInfo(createUserInfoCallBack(forbiddenUser, reqID));
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@"+ "[冻删操作回滚失败失败！！！]",e);
				return ResponseTool.returnSuccess(null);
			}
			
			return ResponseTool.returnException();
		}
		
	}
	
	
	/**
	 * 拼jsonData
	 * @param openingVIP
	 * @return
	 * @throws Exception
	 */
	private String getJsonData(ForbiddenUser forbiddenUser) throws Exception{
		Map forbidden = new HashMap<String,Object>();
		forbidden.put("userID", forbiddenUser.getUserID());
		forbidden.put("startTime",forbiddenUser.getStartTime());
		forbidden.put("duration", forbiddenUser.getDuration());
		forbidden.put("tag", "user_forbidden");
		forbidden.put("forbiddenEndTime", forbiddenUser.getForbiddenEndTime());
		return JsonUtil.getObjectToJson(forbidden);
		
	}
	
	/**
	 * 创建UserInfo对象
	 * @param forbiddenUser
	 * @return
	 */
	private UserInfo createUserInfo(ForbiddenUser forbiddenUser , String reqId) throws Exception{
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(forbiddenUser.getUserID());
		if(forbiddenUser.getType() .equals(0)){//冻结用户
			userInfo.setState(UcConstant.USTATE_BLOCKING);//set状态 (冻结)
			userInfo.setForbiddenEndTime(unixTimeFmt(Long.parseLong(forbiddenUser.getForbiddenEndTime())));//2015年11月10日10:16:01 冻结结束时间
		}else if(forbiddenUser.getType() .equals(9)){//删除用户
			userInfo.setState(UcConstant.USTATE_REMOVED);//删除状态
		}
		userInfo.setReqId(reqId);
		return userInfo;
	}
	
	/**
	 * 创建UserInfo对象
	 * @param forbiddenUser
	 * @return
	 */
	private UserInfo createUserInfoCallBack(ForbiddenUser forbiddenUser , String reqId) throws Exception{
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(forbiddenUser.getUserID());
		if(forbiddenUser.getType() .equals(0)){//冻结用户
			userInfo.setState(UcConstant.USTATE_NORMAL);//set状态 (正常)
			userInfo.setForbiddenEndTime(null);//2015年11月10日10:16:01 冻结结束时间
		}else if(forbiddenUser.getType() .equals(9)){//删除用户
			userInfo.setState(UcConstant.USTATE_NORMAL);//删除状态
		}
		userInfo.setReqId(reqId);
		return userInfo;
	}
	
	/**
	 * Unix时间戳转java date
	 * @param 2015年6月16日15:25:12 Damon
	 * @return
	 * @throws ParseException
	 */
	public static Date unixTimeFmt(long time) throws ParseException {
		String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time * 1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dt);
	}
	

	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private ForbiddenUser checkData(Object object) throws Exception{
		ForbiddenUser forbiddenUser = (ForbiddenUser)object;
		if(forbiddenUser == null){
			throw new Exception("对象转换失败！！！");
		}
		if(forbiddenUser.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(forbiddenUser.getType().equals(UcConstant.USTATE_BLOCKING)){//冻结
			if(forbiddenUser.getStartTime()==null){//操作时间
				throw new Exception("StartTime为空！！！");
			}
			if(forbiddenUser.getDuration()==null){//时长
				throw new Exception("Duration为空！！！");
			}
			if(forbiddenUser.getForbiddenEndTime()==null){//结束时间
				throw new Exception("ForbiddenEndTime为空！！！");
			}
		}
		return forbiddenUser;
	}
	
}

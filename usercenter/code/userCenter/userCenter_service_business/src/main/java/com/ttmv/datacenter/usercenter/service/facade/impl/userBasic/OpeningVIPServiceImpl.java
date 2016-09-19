package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.OpeningVIP;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年11月9日10:42:03
 * @explain :用户开通会员
 */
@SuppressWarnings({ "rawtypes","unchecked" })
public class OpeningVIPServiceImpl extends AbstractUserBase {

	private static Logger logger = LogManager.getLogger(OpeningVIPServiceImpl.class);

	private UserInfoDao userInfoDao;
	private TokenCenterAgent tokenCenterAgent;
	private SentryAgent overSentryAgentVIP;

	public Map handler(Object object, String reqID) {

		logger.debug("[" + reqID + "]@@" + "[用户开通会员]_Start...");
		Long startTime = System.currentTimeMillis();
		OpeningVIP openingVIP = null;
		// 数据验证
		try {
			openingVIP = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}

		if (openingVIP.getToken() != null
				&& !UcConstant.SUPERTOKEN_CODE.equals(openingVIP.getToken())) {
			String uid = tokenCenterAgent.getUserId(openingVIP.getToken());
			if (!(openingVIP.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// userInfo 创建
		UserInfo userInfo = null;
		try {
			userInfo = createUserInfo(openingVIP, reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "UserInfo对象创建失败_", e);
			return ResponseTool.returnException();
		}
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "会员开通失败_", e);
			return ResponseTool.returnException();
		}
		// TODO 2015年11月9日11:00:55 会员加入到期任务
		String jsonData = "";
		try {
			jsonData = getJsonData(openingVIP);
		} catch (Exception e) {
			logger.warn("会员倒计时jsonData组装失败！！！",e);
		}
		String resCode = overSentryAgentVIP.expressSendHttp("data="+jsonData);
		Map resMaap = null;
		try {
			resMaap = (Map) JsonUtil.getObjectFromJson(resCode, Map.class);
		} catch (Exception e) {
			logger.error("json转换失败！！！");
		}
		if(resMaap.get("resultCode").equals("AAAAAAA")){
			logger.info("[" + reqID + "]@@" + "[开通会员成功业务处理耗时(ms)]:"+ (System.currentTimeMillis() - startTime));
			return ResponseTool.returnSuccess(null);
		}else{
			logger.error("vip到期服务处理失败，错误代码:"+resCode);
			logger.error("[到期系统上报消息上报失败:]====>>>" + "data="+jsonData);
			//会员回滚
			try {
				userInfoDao.updateUserInfo(createUserInfoCallBack(openingVIP, reqID));
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@" + "会员开通回滚操作失败_", e);
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
	private String getJsonData(OpeningVIP openingVIP) throws Exception{
		Map openingVip = new HashMap<String,Object>();
		openingVip.put("userID", openingVIP.getUserID());
		openingVip.put("startTime",openingVIP.getStartTime());
		openingVip.put("duration", openingVIP.getDuration());
		openingVip.put("tag", "vip");
		openingVip.put("vipEndTime", openingVIP.getVipEndTime());
		return JsonUtil.getObjectToJson(openingVip);
		
	}

	/**
	 * 创建修改对象
	 * 
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception
	 */
	private UserInfo createUserInfo(OpeningVIP openingVIP, String reqID)
			throws Exception {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(openingVIP.getUserID().toString());
		userInfo.setUserid(userID);
		userInfo.setReqId(reqID);
		userInfo.setVipType(UcConstant.VIPTYPE_YES);
		userInfo.setVipEndTime(unixTimeFmt(Long.parseLong(openingVIP
				.getVipEndTime())));

		return userInfo;
	}

	/**
	 * 创建修改对象(
	 * 
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception
	 */
	private UserInfo createUserInfoCallBack(OpeningVIP openingVIP, String reqID)
			throws Exception {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(openingVIP.getUserID().toString());
		userInfo.setUserid(userID);
		userInfo.setReqId(reqID);
		userInfo.setVipType(UcConstant.VIPTYPE_NO);
		userInfo.setVipEndTime(null);

		return userInfo;
	}
	
	
	/**
	 * Unix时间戳转java date
	 * 
	 * @param 2015年6月16日15:25:12 Damon
	 * @return
	 * @throws ParseException
	 */
	public static Date unixTimeFmt(long time) throws ParseException {
		String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(time * 1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dt);
	}

	/**
	 * 业务数据校验
	 * 
	 * @param validation
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	protected OpeningVIP checkData(Object object) throws Exception {
		OpeningVIP openingVIP = (OpeningVIP) object;
		if (openingVIP == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (openingVIP.getType() == null) {
			throw new Exception("[开通会员_type 为空！！！]");
		}
		if (openingVIP.getToken() == null || "".equals(openingVIP.getToken())) {
			logger.warn("[修改用户信息_token 为空！！！]");
			throw new Exception("[开通会员_token 为空！！！]");
		}
		if (openingVIP.getUserID() == null) {
			throw new Exception("[开通会员_userID 为空！！！]");
		}
		if (openingVIP.getStartTime() == null) {
			throw new Exception("[开通会员_StartTime 为空！！！]");
		}
		if (openingVIP.getDuration() == null) {
			throw new Exception("[开通会员_Duration 为空！！！]");
		}

		return openingVIP;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}

	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}

	public SentryAgent getOverSentryAgentVIP() {
		return overSentryAgentVIP;
	}

	public void setOverSentryAgentVIP(SentryAgent overSentryAgentVIP) {
		this.overSentryAgentVIP = overSentryAgentVIP;
	}




}

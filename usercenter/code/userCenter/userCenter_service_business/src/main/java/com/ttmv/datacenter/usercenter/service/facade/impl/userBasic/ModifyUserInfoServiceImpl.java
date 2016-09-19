package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.lockcenter.Locker;
import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.ModifyUserExtend;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.LockConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年1月27日 下午11:59:57
 * @explain :修改用户信息
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModifyUserInfoServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager
			.getLogger(ModifyUserInfoServiceImpl.class);
	private UserInfoDao userInfoDao;
	private Locker locker;// 锁
	private TokenCenterAgent tokenCenterAgent;

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}
	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}
	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[修改用户信息]_Start...");
		Long startTime = System.currentTimeMillis();
		ModifyUserExtend modifyUserExtend = null;
		// 数据验证
		try {
			modifyUserExtend = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// ##########################################################
		// 通过token验证用户合法性 @Damon 2015年8月10日17:04:44
		if (modifyUserExtend.getToken() != null && !UcConstant.SUPERTOKEN_CODE.equals(modifyUserExtend.getToken())) {
			String uid = tokenCenterAgent.getUserId(modifyUserExtend.getToken());
			if (!(modifyUserExtend.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// ##############################################################
		// 创建修改对象
		UserInfo userInfo = this.creatUserInfo(modifyUserExtend, reqID);
		// 修改
		Map resMap = null;
		if (modifyUserExtend.getType().equals(UcConstant.UTYPE_GENERAL_CODE)) {// 普通用户修改
			resMap = this.modifyUserInfo(userInfo, reqID);
		} else if (modifyUserExtend.getType().equals(UcConstant.UTYPE_OFFICIAL_CODE)) {// 官方用户修改
			resMap = this.adminModifyUserInfo(userInfo, reqID);
		} else if(modifyUserExtend.getType().equals(UcConstant.UTYPE_CONTROL_CODE)){//管控修改
			resMap = this.modifyUserInfo(userInfo, reqID);
		}else{
			logger.info("[" + reqID + "]@@" + "[修改失败_修改类型不存在！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		// 返回修改结果
		logger.info("[" + reqID + "]@@" + "[业务处理耗时(ms)]:"
				+ (System.currentTimeMillis() - startTime));
		return resMap;
	}

	/**
	 * 官方用户信息修改
	 * 
	 * @param userInfo
	 * @param reqID
	 * @return
	 */
	private Map adminModifyUserInfo(UserInfo userInfo, String reqID) {

		Map lockMap = new HashMap();
		if (userInfo.getBindingMobile() != null) {
			// 修改手机先加锁
			if (!locker
					.lockUntilFE(
							LockConstant.FIVE_MOBILE_LOCK
									+ userInfo.getBindingMobile(), 5)) {
				logger.info("[" + reqID + "]@@" + "[修改失败_手机号:"
						+ userInfo.getBindingMobile() + "已绑定5次！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_BINDINGMOBILE_CODE);
			}
			// 加锁标记
			lockMap.put(LockConstant.FIVE_MOBILE_LOCK,
					userInfo.getBindingMobile());
		}
		if (userInfo.getBindingEmail() != null) {
			// 修改邮箱先加锁
			if (!locker
					.lockUntilFE(
							LockConstant.ONE_EMAIL_LOCK
									+ userInfo.getBindingEmail(), 1)) {
				logger.info("[" + reqID + "]@@" + "[修改失败_邮箱:"
						+ userInfo.getBindingEmail() + "已绑定1次！！！]");
				// 解锁先前加锁的邮箱
				this.unLocks(lockMap, reqID);
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_BINDINGEMAIL_CODE);
			}
			// 加锁标记
			lockMap.put(LockConstant.ONE_EMAIL_LOCK, userInfo.getBindingEmail());
		}
		// 如果修改了手机或邮箱，查询原手机或邮箱，解锁
		UserInfo oldinfo = null;
		if (lockMap.size() > 0) {
			try {
				oldinfo = userInfoDao.selectUserInfoByUserId(userInfo
						.getUserid());
			} catch (Exception e) {
				logger.warn("[" + reqID + "]@@" + "[数据查询失败！！！]"
						+ e.getMessage());
			}
		}
		// 修改资料
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@ [修改失败！！！]", e);
			// 解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnException();
		}
		// 解锁原始对象
		if (oldinfo != null) {
			if (userInfo.getBindingMobile() != null) {// 注册手机解锁
				if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
						+ userInfo.getBindingMobile())) {// 解锁BindingMobile
					logger.warn("[" + reqID + "]@@" + "[BindingMobile解锁失败:"
							+ userInfo.getBindingMobile());
				}
			}
			if (userInfo.getBindingEmail() != null) {
				if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
						+ userInfo.getBindingEmail())) {// 解锁BindingEmail
					logger.warn("[" + reqID + "]@@" + "[BindingEmail解锁失败:"
							+ userInfo.getBindingEmail());
				}
			}

		}

		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 解锁
	 * 
	 * @param lockMap
	 * @param reqID
	 */
	public void unLocks(Map lockMap, String reqID) {
		Set key = lockMap.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String lockKey = (String) it.next();

			if (lockKey.equals(LockConstant.ONLYONE_MOBILE_LOCK)) {// 注册手机解锁
				if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
						+ lockMap.get(lockKey))) {// 解锁LoginMobile
					logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"
							+ lockMap.get(lockKey));
				}
			} else if (lockKey.equals(LockConstant.ONLYONE_EMAIL_LOCK)) {// 注册邮箱解锁
				if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
						+ lockMap.get(lockKey))) {// 解锁LoginEmail
					logger.warn("[" + reqID + "]@@" + "[loginEmail解锁失败:"
							+ lockMap.get(lockKey));
				}
			} else if (lockKey.equals(LockConstant.ONLYONE_TTNUM_LOCK)) {// TT号解锁
				if (!locker.unlockUniqueFE(LockConstant.ONLYONE_TTNUM_LOCK
						+ lockMap.get(lockKey))) {// 解锁userName
					logger.warn("[" + reqID + "]@@" + "[TTnum解锁失败:"
							+ lockMap.get(lockKey));
				}
			} else if (lockKey.equals(LockConstant.ONLYONE_USERNAME_LOCK)) {// userName解锁
				if (!locker.unlockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK
						+ lockMap.get(lockKey))) {// 解锁userName
					logger.warn("[" + reqID + "]@@" + "[userName解锁失败:"
							+ lockMap.get(lockKey));
				}
			} else if (lockKey.equals(LockConstant.FIVE_MOBILE_LOCK)) {// 绑定手机
				if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
						+ lockMap.get(lockKey))) {// 解锁bindingMobile
					logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
							+ lockMap.get(lockKey));
				}
			} else if (lockKey.equals(LockConstant.ONE_EMAIL_LOCK)) {// 绑定邮箱
				if (!locker.releaseOneFE(LockConstant.ONE_EMAIL_LOCK
						+ lockMap.get(lockKey))) {// 解锁BindingEmail
					logger.warn("[" + reqID + "]@@" + "[bindingEmail解锁失败:"
							+ lockMap.get(lockKey));
				}
			}
		}
	}

	/**
	 * 普通用户资料修改
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	private Map modifyUserInfo(UserInfo userInfo, String reqID) {
		try {
			userInfoDao.updateUserInfo(userInfo);
			return ResponseTool.returnSuccess(null);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "修改失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
	}

	/**
	 * 业务数据校验
	 * 
	 * @param validation
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	protected ModifyUserExtend checkData(Object object) throws Exception {
		ModifyUserExtend modifyUserExtend = (ModifyUserExtend) object;
		if (modifyUserExtend == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (modifyUserExtend.getType() == null) {
			throw new Exception("[修改用户信息_type 为空！！！]");
		}
		if (modifyUserExtend.getToken() == null
				|| "".equals(modifyUserExtend.getToken())) {
			
			logger.warn("[修改用户信息_token 为空！！！]");
			throw new Exception("[修改用户信息_token 为空！！！]");
		}
		if (modifyUserExtend.getUserID() == null) {
			throw new Exception("[修改用户信息_userID 为空！！！]");
		}
		
		//管控修改，比对token
		if(modifyUserExtend.getType().equals(UcConstant.UTYPE_CONTROL_CODE) 
				&& modifyUserExtend.getToken().equals(UcConstant.CONTROL_CODE)){
			modifyUserExtend.setToken(UcConstant.SUPERTOKEN_CODE);
		}
		
		return modifyUserExtend;
	}

	/**
	 * 创建修改对象
	 * 
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception
	 */
	private UserInfo creatUserInfo(ModifyUserExtend modifyUserExtend,
			String reqID) {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(modifyUserExtend.getUserID()
				.toString());
		userInfo.setUserid(userID);
		userInfo.setReqId(reqID);
		if (modifyUserExtend.getType().equals(UcConstant.UTYPE_OFFICIAL_CODE)) {// 修改官方用户
			if (modifyUserExtend.getEmail() != null) {
				userInfo.setBindingEmail(modifyUserExtend.getEmail());// 绑定邮箱
				userInfo.setLoginEmail("");// 解除邮箱登录功能
			}
			if (modifyUserExtend.getMobile() != null) {
				userInfo.setBindingMobile(modifyUserExtend.getMobile());// 绑定手机
				userInfo.setLoginMobile("");// 解除手机登录
			}
			if (modifyUserExtend.getName() != null) {
				userInfo.setRealName(modifyUserExtend.getName());// 姓名
			}
			if (modifyUserExtend.getPassword() != null) {
				userInfo.setPassword(modifyUserExtend.getPassword());// 密码
			}
	

		} else if (modifyUserExtend.getType().equals(
				UcConstant.UTYPE_GENERAL_CODE)) {// 普通用户修改
			if (modifyUserExtend.getEmail() != null) {
				userInfo.setEmail(modifyUserExtend.getEmail());// 显示邮箱
			}
			if (modifyUserExtend.getMobile() != null) {
				userInfo.setMobile(modifyUserExtend.getMobile());// 显示手机
			}
		}
//2015年12月16日19:22:45  Damon 关键信息不再在修改资料中修改，提供专门接口	（避免用用修改资料，修改了vip状态）	
//		// 2015年7月8日11:16:19 Damon
//		if (modifyUserExtend.getVipType() != null) {
//			userInfo.setVipType(modifyUserExtend.getVipType());// 会员标识
//		}
		//Damon 2016年1月5日13:47:33
		if (modifyUserExtend.getReason() != null) {// 修改原因
			userInfo.setReason(modifyUserExtend.getReason());
		}
		if (modifyUserExtend.getAdmainId() != null) {// 管理员ID
			userInfo.setAdminId(modifyUserExtend.getAdmainId());
		}
		
		if (modifyUserExtend.getSex() != null) {
			userInfo.setSex(modifyUserExtend.getSex());// 性别
		}
		if (modifyUserExtend.getNickName() != null) {
			userInfo.setNickName(modifyUserExtend.getNickName());// 昵称
		}
		if (modifyUserExtend.getLogo() != null) {
			userInfo.setUserPhoto(modifyUserExtend.getLogo());// 头像
		}
		if (modifyUserExtend.getTelephone() != null) {
			userInfo.setTelephone(modifyUserExtend.getTelephone());// 固话
		}
		if (modifyUserExtend.getQq() != null) {
			userInfo.setQQ(modifyUserExtend.getQq());// QQ
		}
		if (modifyUserExtend.getSign() != null) {
			userInfo.setSign(modifyUserExtend.getSign());// 个性签名
		}
		if (modifyUserExtend.getConstellation() != null) {
			userInfo.setConstellation(modifyUserExtend.getConstellation());// 星座
		}
		if (modifyUserExtend.getZodiac() != null) {
			userInfo.setZodiac(modifyUserExtend.getZodiac());// 生肖
		}
		if (modifyUserExtend.getJob() != null) {
			userInfo.setJob(modifyUserExtend.getJob());// 工作
		}
		if (modifyUserExtend.getInterest() != null) {
			userInfo.setInterest(modifyUserExtend.getInterest());// 兴趣爱好
		}
		if (modifyUserExtend.getEmotion() != null) {
			userInfo.setEmotion(modifyUserExtend.getEmotion());// 情感状态
		}
		if (modifyUserExtend.getCity() != null) {
			userInfo.setCity(modifyUserExtend.getCity());// 所在城市
		}
		if (modifyUserExtend.getAddress() != null) {
			userInfo.setAddress(modifyUserExtend.getAddress());// 详细地址
		}
		if (modifyUserExtend.getEducation() != null) {
			userInfo.setEducation(modifyUserExtend.getEducation());// 教育程度
		}
		if (modifyUserExtend.getProfession() != null) {
			userInfo.setProfession(modifyUserExtend.getProfession());// 职业
		}
		if (modifyUserExtend.getIndustry() != null) {
			userInfo.setIndustry(modifyUserExtend.getIndustry());// 行业
		}
		if (modifyUserExtend.getPreferred() != null) {
			userInfo.setPreferred(modifyUserExtend.getPreferred());// 偏爱
		}
		if (modifyUserExtend.getExplain() != null) {
			userInfo.setExplain(modifyUserExtend.getExplain());// 个人说明
		}
		if (modifyUserExtend.getIncome() != null) {
			userInfo.setIncome(modifyUserExtend.getIncome());// 收入
		}
		if (modifyUserExtend.getSmalllogo() != null) {
			userInfo.setSmalllogo(modifyUserExtend.getSmalllogo());// 小头像
		}
		if (modifyUserExtend.getBiglogo() != null) {
			userInfo.setBiglogo(modifyUserExtend.getBiglogo());// 大头像
		}
		if (modifyUserExtend.getBirthday() != null) {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = ft.parse(modifyUserExtend.getBirthday());
			} catch (ParseException e) {
				logger.warn("修改生日_传入日期格式错误！！！" + e.getMessage());
			}
			userInfo.setBirthday(date);// 生日
		}
		return userInfo;
	}

}

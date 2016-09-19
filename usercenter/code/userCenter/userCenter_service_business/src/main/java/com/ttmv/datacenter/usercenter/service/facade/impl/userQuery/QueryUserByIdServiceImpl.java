package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryUserById;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.DateUtil;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月27日 下午11:58:42  
 * @explain :根据id查询用户资料
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUserByIdServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryUserByIdServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[根据id查询用户资料]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		QueryUserById queryUserById = null;
		try {
			queryUserById = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//查询信息
		UserInfo userInfo = null;
		BigInteger userId = queryUserById.getUserID();
		try {
			userInfo = userInfoDao.selectUserInfoByUserId(userId);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "用户资料查询失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERERROR_CODE);
		}
		if(userInfo == null){
			logger.debug("[" + reqID + "]@@" + "userID对应用户不存在！！！");
			return ResponseTool.returnSuccess(null);
		}
		logger.info("[" + reqID + "]@@"+ "[***用户信息查询完成***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(takeResData(userInfo));
	}

	/**
	 * 业务数据校验
	 * @param validation
	 * @param reqID
	 * @return
	 */
	private QueryUserById checkData(Object object) throws Exception {
		QueryUserById queryUserById = (QueryUserById) object;
		if(queryUserById == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if (queryUserById.getUserID() == null) {
			throw new Exception("[查询用户信息_userID 为空！！！]");
		}	
		return queryUserById;
	}
	
	/**
	 * 组装返回数据
	 * @param resData
	 * @param userInfo
	 * @return
	 */
	protected Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		resData.put("userID", userInfo.getUserid());
		resData.put("TTnum", userInfo.getTTnum());//TT号
		resData.put("mobile", userInfo.getBindingMobile());//绑定手机
		resData.put("email", userInfo.getBindingEmail());//绑定邮箱
		resData.put("nickName", userInfo.getNickName());//昵称
		resData.put("sex", userInfo.getSex());//性别
		resData.put("logo", userInfo.getUserPhoto());//标准头像
		resData.put("exp", userInfo.getExp());//经验值
		resData.put("qq", userInfo.getQQ());//QQ号码
		resData.put("constellation", userInfo.getConstellation());//星座
		resData.put("zodiac", userInfo.getZodiac());//生肖
		resData.put("job", userInfo.getJob());//工作
		resData.put("interest", userInfo.getInteresting());//兴趣爱好
		resData.put("emotion", userInfo.getEmotion());//情感状态
		resData.put("city", userInfo.getCity());//所在城市
		resData.put("address", userInfo.getAddress());//详细地址
		resData.put("biglogo", userInfo.getBiglogo());//大头像
		resData.put("smalllogo", userInfo.getSmalllogo());//小头像
		resData.put("sign", userInfo.getSign());//个性签名
		resData.put("telephone", userInfo.getTelephone());//固话
		resData.put("education", userInfo.getEducation());//学历
		resData.put("profession", userInfo.getProfession());//职业
		resData.put("industry", userInfo.getIndustry());//行业
		resData.put("preferred", userInfo.getPreferred());//偏爱
		resData.put("explain", userInfo.getExplain());//个人说明
		resData.put("income", userInfo.getIncome());//收入
		
		resData.put("userName", userInfo.getUserName());//账号名
		
		resData.put("uType", userInfo.getUtype());//用户类型
		resData.put("uStatus", userInfo.getState());//用户状态
		resData.put("vipType", userInfo.getVipType());//是否为会员
		resData.put("reason", userInfo.getReason());//修改原因
		
		//Damon 2015年12月16日19:41:32
		resData.put("announcerType", userInfo.getAnnouncerType());//主播标示
		resData.put("announcerLevel", userInfo.getAnnouncerLevel());//主播等级
		resData.put("announcerExp", userInfo.getAnnouncerExp());//主播经验
		resData.put("userLevel", userInfo.getUserLevel());//用户等级
		
		
		resData.put("realName", userInfo.getRealName());//真实姓名
		
		resData.put("goodTTnum", userInfo.getLoginGoodTTnum());
		
		Long vipEndTime = null;
		try {
			vipEndTime = DateUtil.getUnixDate(userInfo.getVipEndTime());
			resData.put("vipEndTime",vipEndTime);//会员到期时间(转unix时间戳)
		} catch (Exception e) {
			logger.error("时间转换失败！！！");
			resData.put("vipEndTime",null);//会员到期时间(转unix时间戳)
		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		if(userInfo.getBirthday()!=null){
			resData.put("birthday", sf.format(userInfo.getBirthday()));//生日
		}else {
			resData.put("birthday",null);
		}
		return resData;
	}
	
	public static void main(String[] args) {
		Date date = new Date(1449805049000l);
	}
	
}

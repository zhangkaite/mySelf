package com.ttmv.datacenter.usercenter.service.facade.impl.overCallBack;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.VipCallBack;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.DateUtil;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年11月17日15:33:29
 * @explain :修改用户vip状态信息
 */
@SuppressWarnings({ "rawtypes"})
public class VipCallBackServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager.getLogger(VipCallBackServiceImpl.class);
	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[到期回调 修改用户vip状体信息]_Start...");
		Long startTime = System.currentTimeMillis();
		VipCallBack vipCallBack  = null;
		// 数据验证
		try {
			vipCallBack = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// 创建修改对象
		UserInfo userInfo = this.creatUserInfo(vipCallBack, reqID);
		// 对比过期时间
		UserInfo info = null;
		try {
			info = userInfoDao.selectUserInfoByUserId(vipCallBack.getUserID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map resMap = null;
		if(info!=null){
			Date dt = DateUtil.getDate(vipCallBack.getEndTime());
			if(info.getVipEndTime().after(dt)){//实际时间 大于 预期时间(不处理)
				resMap = ResponseTool.returnSuccess(null);
			}else if(dt.after(info.getVipEndTime())){// 预期时间 大于 实际时间实际时间 (修改)
				resMap = this.modifyUserInfo(userInfo, reqID);
			}else if(info.getVipEndTime().equals(dt)){//时间相等（修改状态）
				resMap = this.modifyUserInfo(userInfo, reqID);
			}
		}else{
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE, ErrorCodeConstant.ERRORMSG_PARAMETERERROR_CODE);
		}
		// 返回修改结果
		logger.info("[" + reqID + "]@@" + "[业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return resMap;
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
	protected VipCallBack checkData(Object object) throws Exception {
		VipCallBack vipCallBack = (VipCallBack) object;
		if (vipCallBack == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (vipCallBack.getVipType() == null) {
			throw new Exception("[修改会员到期信息_VipType 为空！！！]");
		}
		if (vipCallBack.getUserID() == null) {
			throw new Exception("[修改会员到期信息_userID 为空！！！]");
		}
		if (vipCallBack.getEndTime() == null) {
			throw new Exception("[修改会员到期信息_EndTime 为空！！！]");
		}
		return vipCallBack;
	}

	/**
	 * 创建修改对象
	 * 
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception
	 */
	private UserInfo creatUserInfo(VipCallBack vipCallBack,String reqID) {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(vipCallBack.getUserID().toString());
		userInfo.setVipType(vipCallBack.getVipType());
		userInfo.setUserid(userID);
		return userInfo;
	}
	
	public static void main(String[] args) {
		Date dt = new Date(Long.parseLong(1451543438+""));//预期
		if((new Date(Long.parseLong(1449728574+"")).after(dt))){//实际时间 大于 预期时间(不处理)
			System.out.println("实际时间还没到");
		}else if(dt.after(new Date(Long.parseLong(1449728574+"")))){// 预期时间 大于 实际时间实际时间 (修改)
			System.out.println("实际时间已经过期了");
		}else if((new Date(Long.parseLong(1449728574+""))).equals(dt)){//时间相等（修改状态）
			System.out.println("相等");
		}
	}

}

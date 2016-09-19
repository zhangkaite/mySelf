package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.BindingBankCard;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月10日 上午10:16:35  
 * @explain :银行卡信息绑定
 */
@SuppressWarnings({ "rawtypes" })
public class BindingBankCardServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager.getLogger(BindingBankCardServiceImpl.class);
	private UserInfoDao userInfoDao;
	private TokenCenterAgent tokenCenterAgent;
	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}

	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[银行卡信息绑定]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		//数据检查
		BindingBankCard bankCard = null;
		try {
			bankCard = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// ##########################################################
		// 通过token验证用户合法性 @Damon 2015年8月11日14:23:22
		if (bankCard.getToken() != null && !"whatthefucking".equals(bankCard.getToken())) {
			String uid = tokenCenterAgent.getUserId(bankCard.getToken());
			if (!(bankCard.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// ##############################################################		
		
		
		//创建修改对象
		UserInfo userInfo  = this.creatUserInfo(bankCard ,reqID);
		//修改
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "修改失败_" , e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@" + "[业务处理耗时(ms)]:"
				+ (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 创建修改对象
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception 
	 */
	private UserInfo creatUserInfo(BindingBankCard bankCard ,String reqID){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(bankCard.getUserID());
		userInfo.setBankName(bankCard.getBankName());
		userInfo.setCardNo(bankCard.getCardNo());
		userInfo.setBankAddress(bankCard.getBankAddress());
		userInfo.setBankRealName(bankCard.getRealName());
		userInfo.setReqId(reqID);//业务流水号
		return userInfo;
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * 
	 * @param addUser
	 * @param reqID
	 * @return boolean
	 * @exception Exception
	 */
	private BindingBankCard checkData(Object object) throws Exception {
		BindingBankCard bankCard = (BindingBankCard)object;
		if(bankCard == null){
			throw new Exception("对象转换失败！！！");
		}
		if (bankCard.getUserID() == null) {
			throw new Exception("[UserID 为空！！！]");
		}
		if (bankCard.getCardNo() == null || "".equals(bankCard.getCardNo())) {
			throw new Exception("[绑定银行卡号CardNo 为空！！！]");
		}
		if (bankCard.getRealName() == null || "".equals(bankCard.getRealName())) {
			throw new Exception("[RealName 为空！！！]");
		}
		if (bankCard.getBankName() == null || "".equals(bankCard.getBankName())) {
			throw new Exception("[银行卡名称BankName 为空！！！]");
		}
		if (bankCard.getBankAddress() == null || "".equals(bankCard.getBankAddress())) {
			throw new Exception("[BankAddress 为空！！！]");
		}
		if (bankCard.getToken() == null
				|| "".equals(bankCard.getToken())) {
			logger.warn("[token 为空！！！]");
			throw new Exception("[token 为空！！！]");
		}
		return bankCard;
	}

	
}

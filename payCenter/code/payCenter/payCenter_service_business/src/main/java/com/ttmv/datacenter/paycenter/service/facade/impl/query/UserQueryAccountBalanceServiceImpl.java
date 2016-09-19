package com.ttmv.datacenter.paycenter.service.facade.impl.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.interfaces.AccountInfoDao;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;
import com.ttmv.datacenter.paycenter.domain.protocol.UserQueryAccountBalance;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年4月2日 下午17:25:47  
 * @explain :账户余额查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserQueryAccountBalanceServiceImpl extends AbstractPayCenter{
	private static Logger logger = LogManager.getLogger(UserQueryAccountBalanceServiceImpl.class);
	private AccountInfoDao accountInfoDao;
	public AccountInfoDao getAccountInfoDao() {
		return accountInfoDao;
	}
	public void setAccountInfoDao(AccountInfoDao accountInfoDao) {
		this.accountInfoDao = accountInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[:账户余额查询]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		//数据非空验证
		UserQueryAccountBalance queryAccountBalance = null;
		try {
			queryAccountBalance = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//数据类型验证
		List<Integer> types = queryAccountBalance.getAccountTypes();
		
		for (int i = 0; i < types.size(); i++) {
			if(!(types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_TB)  ||
					types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_TQ) || 
						types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_YJ))){
				logger.error("[" + reqID + "]@@" + "输入账户查询类型不存在！！！");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
			}
		}
		//查询余额
		Map accountMap = null;
		try {
			accountMap = accountInfoDao.selectAccount(queryAccountBalance.getUserID(), types);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "余额查询异常！！！" + e.getMessage());
			return ResponseTool.returnException();
		}
		//拼装结果
		Map resMap = new HashMap();
		Map map = new HashMap();
		for (int i = 0; i < types.size(); i++) {
			resMap.put("userID", queryAccountBalance.getUserID());
			if(types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_TB)){//TB
				TcoinInfo tcoin = (TcoinInfo)accountMap.get("tcoin");
				if(tcoin.getBalance() == null){
					logger.error("[" + reqID + "]@@"+"[userID]" + queryAccountBalance.getUserID() + "查询账户不存在！！！");
					return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
				}
				map.put("tBAccount",tcoin.getBalance());
			}else if(types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_TQ)){//T券
				TquanInfo tquan = (TquanInfo)accountMap.get("tquan");
				if(tquan.getBalance() == null){
					logger.error("[" + reqID + "]@@"+"[userID]" + queryAccountBalance.getUserID() + "查询账户不存在！！！");
					return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
				}
				map.put("tQAccount", tquan.getBalance());
			}else if(types.get(i).equals(PcConstant.PC_ACCOUNTTYPE_YJ)){//佣金
				BrokerageInfo brokerage = (BrokerageInfo)accountMap.get("brokerage");
				if(brokerage.getBalance() == null){
					logger.error("[" + reqID + "]@@"+"[userID]" + queryAccountBalance.getUserID() + "查询账户不存在！！！");
					return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
				}
				map.put("yJAccount", brokerage.getBalance());
			}
			resMap.put("accountTypes", map);
		}
		logger.info("[" + reqID + "]@@"+"[userID]" + queryAccountBalance.getUserID() + "[***余额查询***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private UserQueryAccountBalance checkData(Object object) throws Exception {
		UserQueryAccountBalance queryAccountBalance = (UserQueryAccountBalance) object;
		if (queryAccountBalance == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(queryAccountBalance.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(queryAccountBalance.getTime() == null){
			throw new Exception("[Time为空！！！]");
		}
		if(queryAccountBalance.getAccountTypes() == null || queryAccountBalance.getAccountTypes().size()<1){
			throw new Exception("[AccountTypes为空！！！]");
		}
		return queryAccountBalance;
	}

}

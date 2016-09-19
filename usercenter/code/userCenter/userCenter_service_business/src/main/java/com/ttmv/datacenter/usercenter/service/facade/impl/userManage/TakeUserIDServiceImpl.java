package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.domain.protocol.TakeUserID;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.tools.UserIdGenerate;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年11月11日14:15:22
 * @explain :获取用户ID
 */
@SuppressWarnings({ "rawtypes","unchecked" })
public class TakeUserIDServiceImpl  extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(TakeUserIDServiceImpl.class);
	private UserIdGenerate userIdGenerate;// userID生成接口
	
	public UserIdGenerate getUserIdGenerate() {
		return userIdGenerate;
	}
	public void setUserIdGenerate(UserIdGenerate userIdGenerate) {
		this.userIdGenerate = userIdGenerate;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[生成用户id]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		TakeUserID takeUserID  = null;
		try {
			takeUserID = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		if("getUserID".equals(takeUserID.getOperationType())){
			BigInteger userID = userIdGenerate.getUserId();
			Map resData = new HashMap();
			resData.put("userID", userID);
			logger.info("[" + reqID + "]@@"+ "[***获取用户id***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
			return ResponseTool.returnSuccess(resData);
		}else{
			logger.error("[" + reqID + "]@@" + "[操作类型不存在！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * 
	 * @param addUser
	 * @param reqID
	 * @return boolean
	 * @exception Exception
	 */
	private TakeUserID checkData(Object object) throws Exception {
		TakeUserID takeUserID = (TakeUserID) object;
		if (takeUserID == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(takeUserID.getOperationType() == null){
			throw new Exception("OperationType为空！！！");
		}
		return takeUserID;
		
	}

}

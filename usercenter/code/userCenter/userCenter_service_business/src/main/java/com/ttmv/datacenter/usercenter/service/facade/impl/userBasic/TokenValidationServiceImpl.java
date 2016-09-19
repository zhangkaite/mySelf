package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.domain.protocol.TokenValidation;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月10日 下午2:47:27  
 * @explain :token验证
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TokenValidationServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(TokenValidationServiceImpl.class);
	private TokenCenterAgent tokenCenterAgent;

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}
	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}

	public Map handler(Object object, String reqID) {
		
		logger.info("[" + reqID + "]@@" + "[token验证]_Start...");
		Long startTime = System.currentTimeMillis();
		TokenValidation tokenValidation = null;
		//数据验证
		try {
			tokenValidation = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		int in = -1;
		boolean bb = tokenCenterAgent.checkTokenForUserId(tokenValidation.getUserID(), tokenValidation.getClientType(), tokenValidation.getToken());
		if(bb){//验证成功返回数据
			in = 1;
		}else{
			in = 0;
		}
		Map resData = new HashMap();
		resData.put("result", in);
		logger.info("[" + reqID + "]@@" + "[业务处理耗时(ms)]:"
				+ (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resData);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private TokenValidation checkData(Object object) throws Exception{
		TokenValidation tokenValidation = (TokenValidation)object;
		if(tokenValidation == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if(tokenValidation.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(tokenValidation.getToken() == null || "".equals(tokenValidation.getToken())){
			throw new Exception("[Token为空！！！]");
		}
		if(tokenValidation.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		return tokenValidation;
	}

}

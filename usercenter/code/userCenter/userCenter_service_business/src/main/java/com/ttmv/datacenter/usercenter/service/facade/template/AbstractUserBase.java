package com.ttmv.datacenter.usercenter.service.facade.template;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.domain.protocol.GeneralPro;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * 用户中心接口 （模板）
 * @author Damon_Zs
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractUserBase extends FacadeService{
	private static Logger logger = LogManager.getLogger(AbstractUserBase.class);
	
	public abstract Map handler(Object object, String reqID);

	public Map execute(GeneralPro generalPro, Object object){
		Map resMap = new HashMap();
		if(object!=null && generalPro!=null){
			if(this.validate(generalPro)){//通用域数据校验
				resMap = this.handler(object,generalPro.getReqID());
				resMap.put("responseID", generalPro.getReqID());
				return resMap;
			}else{
				resMap.put("resultCode", ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE);
				resMap.put("errorMsg","OP90002");
				resMap.put("responseID", generalPro.getReqID());
				logger.error("["+generalPro.getReqID()+"]@@"+"[请求数据异常，通用域字段数据缺失！！！]");
				return resMap;
			}
		}else{
			resMap.put("resultCode", ErrorCodeConstant.SYSTEM_ERROR_CODE);
			resMap.put("errorMsg","OP500");
			resMap.put("responseID", generalPro.getReqID());
			logger.error("["+generalPro.getReqID()+"]@@"+"[gromit传入参数为NULL！！！]");
			return resMap;
		}
	}
	
	/**
	 * 通用域字段校验
	 * @param generalPro
	 * @return tag
	 */
	public boolean validate(GeneralPro generalPro){
		boolean tag = true;
		if(generalPro.getReqID()==null){
			logger.debug("[通用域字段为空_reqID]");
			return false;
		}
		if(generalPro.getPlatfrom()== null){
			logger.debug("["+generalPro.getReqID()+"]@@"+"[通用域字段为空_platform]");
			return false;
		}
		if(generalPro.getReqData()==null){
			logger.debug("["+generalPro.getReqID()+"]@@"+"[通用域字段为空_reqData]");
			return false;
		}
		if(generalPro.getService()==null){
			logger.debug("["+generalPro.getReqID()+"]@@"+"[通用域字段为空_service]");
			return false;
		}
		if(generalPro.getTimeStamp()==null){
			logger.debug("["+generalPro.getReqID()+"]@@"+"[通用域字段为空_timeStamp]");
			return false;
		}
		if(generalPro.getTradeType()==null){
			logger.debug("["+generalPro.getReqID()+"]@@"+"[通用域字段为空_tradeType]");
			return false;
		}
		return tag;
	}


	
}

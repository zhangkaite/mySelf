package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.ParameterDao;
import com.ttmv.datacenter.usercenter.domain.data.Parameter;
import com.ttmv.datacenter.usercenter.domain.protocol.ParameterManage;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午8:34:31  
 * @explain :参数管理
 */
@SuppressWarnings({ "rawtypes"})
public class ParameterManageServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ParameterManageServiceImpl.class);
	private ParameterDao parameterDao;
	public ParameterDao getParameterDao() {
		return parameterDao;
	}
	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	@Override
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[参数管理]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		ParameterManage parameterManage = null;
		//参数检测
		try {
			parameterManage = this.checkDate(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			ResponseTool.returnException();
		}
		//创建parameter
		Parameter parameter = this.creatParameter(parameterManage);
		//处理
		Map resMap = new HashMap();
		if(parameterManage.getReqType() == 0){//添加参数
			resMap = this.addParameter(parameter,reqID);
		}else if(parameterManage.getReqType() == 2){//修改参数
			resMap = this.updateParameter(parameter,reqID);
		}else if(parameterManage.getReqType() == 1){//删除参数
			resMap = this.deleteParameter(parameter,reqID);
		}else{
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		} 
		logger.info("[" + reqID + "]@@"+ "[***参数操作***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return resMap;
	}
	
	/**
	 * 创建Parameter
	 * @param parameterManage
	 * @return
	 */
	private Parameter creatParameter(ParameterManage parameterManage){
		Parameter parameter = new Parameter();
		if(parameterManage.getReqType() == 0){
			parameter.setKey(parameterManage.getKey().toString());
			parameter.setType(parameterManage.getType().toString());
			parameter.setValue(parameterManage.getValue());
		}else if(parameterManage.getReqType() == 2){
			parameter.setKey(parameterManage.getKey().toString());
			parameter.setType(parameterManage.getType().toString());
			parameter.setValue(parameterManage.getValue());
		}else if(parameterManage.getReqType() == 1){
			parameter.setKey(parameterManage.getKey().toString());
		}
		return parameter;
	}
	
	/**
	 * 参数添加
	 * @param parameterManage
	 * @param reqID
	 * @return
	 */
	private Map addParameter(Parameter parameter , String reqID){
		try {
			parameterDao.addParameter(parameter);
		} catch (Exception e) {
			return ResponseTool.returnException();
		}
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 参数修改
	 * @param parameterManage
	 * @param reqID
	 * @return
	 */
	private Map updateParameter(Parameter parameter , String reqID){
		try {
			parameterDao.updateParameter(parameter);
		} catch (Exception e) {
			return ResponseTool.returnException();
		}
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 参数删除
	 * @param parameterManage
	 * @param reqID
	 * @return
	 */
	private Map deleteParameter(Parameter parameter , String reqID){
		try {
			parameterDao.deleteParameterByKey(parameter.getKey());
		} catch (Exception e) {
			ResponseTool.returnException();
			// TODO: handle exception
		}
		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 数据检测
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private ParameterManage checkDate(Object object) throws Exception{
		ParameterManage parameterManage = (ParameterManage)object;
		if(parameterManage == null){
			throw new Exception("对象转换失败！！！");
		}
		if(parameterManage.getReqType() == null){
			throw new Exception("请求类型为空！！！");
		}
		if(parameterManage.getReqType() == 0){//添加
			if(parameterManage.getType() == null){
				throw new Exception("参数类型为空！！！");
			}
			if(parameterManage.getValue() == null){
				throw new Exception("参数值为空！！！");
			}
		}
		else if(parameterManage.getReqType() == 1){//删除
			if(parameterManage.getKey() == null){
				throw new Exception("key值为空！！！");
			}
		}
		else if(parameterManage.getReqType() == 2){//修改
			if(parameterManage.getKey() == null){
				throw new Exception("key值为空！！！");
			}
			if(parameterManage.getType() == null){
				throw new Exception("参数类型为空！！！");
			}
			if(parameterManage.getValue() == null){
				throw new Exception("参数值为空！！！");
			}
		}
		return parameterManage;
	}
	
}

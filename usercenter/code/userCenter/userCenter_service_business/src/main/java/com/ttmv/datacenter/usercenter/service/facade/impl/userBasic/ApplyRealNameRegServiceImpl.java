package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.ApplyRealNameReg;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午7:58:29  
 * @explain :添加实名认证
 */
@SuppressWarnings({ "rawtypes" })
public class ApplyRealNameRegServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ApplyRealNameRegServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[添加实名认证]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		//数据检查
		ApplyRealNameReg applyRealNameReg = null;
		try {
			applyRealNameReg = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建修改对象
		UserInfo userInfo = null;
		try {
			userInfo = this.creatUserInfo(applyRealNameReg , reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"修改对象创建失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		//修改
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"数据修改失败！！！", e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***实名认证信息添加成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 常见修改对象
	 * @param applyRealNameReg
	 * @return
	 */
	private UserInfo creatUserInfo(ApplyRealNameReg applyRealNameReg , String reqID){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(applyRealNameReg.getUserID());
		userInfo.setRealName(applyRealNameReg.getRealname());
		userInfo.setIdcardNum(applyRealNameReg.getHandNum());
		userInfo.setIdcard(applyRealNameReg.getIdcard());
		userInfo.setHandback(applyRealNameReg.getHandback());
		userInfo.setHandfront(applyRealNameReg.getHandfront());
		userInfo.setReqId(reqID);//业务流水号
		return userInfo;
	}
	
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private ApplyRealNameReg checkData(Object object) throws Exception{
		ApplyRealNameReg applyRealNameReg = (ApplyRealNameReg)object;
		if(applyRealNameReg == null){
			throw new Exception("转换对象异常！！！");
		}
		if(applyRealNameReg.getIdcard()==null || "".equals(applyRealNameReg.getIdcard())){
			throw new Exception("身份证照片不能为空！！！");
		}
		if(applyRealNameReg.getUserID()==null){
			throw new Exception("userID 不能为空！！！");
		}
		if(applyRealNameReg.getRealname()==null || "".equals(applyRealNameReg.getRealname())){
			throw new Exception("真实姓名不能为空！！！");
		}
		if(applyRealNameReg.getHandback()==null || "".equals(applyRealNameReg.getHandback())){
			throw new Exception("手持身份证反面照片不能为空！！！");
		}
		if(applyRealNameReg.getHandfront()==null || "".equals(applyRealNameReg.getHandfront())){
			throw new Exception("手持身份证正面照片不能为空！！！");
		}
		if(applyRealNameReg.getHandNum()==null || "".equals(applyRealNameReg.getHandNum())){
			throw new Exception("身份证号码不能为空！！！");
		}
		return applyRealNameReg;
	}

}

package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.protocol.GoodTTnumManage;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年3月3日 下午4:28:12
 * @explain :靓号状态修改
 */
@SuppressWarnings({"rawtypes"})
public class GoodTTnumManageServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager
			.getLogger(GoodTTnumManageServiceImpl.class);

	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[靓号状态修改]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		GoodTTnumManage goodTTnumManage = null;
		// 数据校验
		try {
			goodTTnumManage = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		//修改
		try {
			if(UcConstant.GOODTTNUMTYPE_N.equals(goodTTnumManage.getType())){//冻结（不可用）
				userInfoDao.updateGoodTTnum(goodTTnumManage.getUserID(), UcConstant.GOODTTNUMTYPE_N);
			}else if(UcConstant.GOODTTNUMTYPE_Y.equals(goodTTnumManage.getType())){//解冻（恢复正常）
				userInfoDao.updateGoodTTnum(goodTTnumManage.getUserID(), UcConstant.GOODTTNUMTYPE_Y);
			}else{//操作类型不存在
				logger.error("[" + reqID + "]@@" + "[处理失败_操作类型不存在！！！]");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
			}
			
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"数据修改失败！！！" +e.getMessage());
		}
		logger.info("[" + reqID + "]@@"+ "[***靓号状态修改***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 数据校验
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private GoodTTnumManage checkData(Object object) throws Exception {
		GoodTTnumManage goodTTnumManage = (GoodTTnumManage) object;
		if (goodTTnumManage == null) {
			throw new Exception("对象转换失败！！！");
		}
		if (goodTTnumManage.getUserID() == null) {
			throw new Exception("参数UserID为空！！！");
		}
		if (goodTTnumManage.getType() == null) {
			throw new Exception("参数Type为空！！！");
		}
		if (goodTTnumManage.getGoodTTnum() == null) {
			throw new Exception("参数GoodTTnum为空！！！");
		}
		return goodTTnumManage;
	}

}

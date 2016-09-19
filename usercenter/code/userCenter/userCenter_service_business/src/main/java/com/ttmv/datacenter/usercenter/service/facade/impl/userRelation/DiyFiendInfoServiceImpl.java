package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.DiyFiendInfo;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月6日 上午12:23:44  
 * @explain :好友信息设置（DIY）
 */
@SuppressWarnings({ "rawtypes"})
public class DiyFiendInfoServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(DiyFiendInfoServiceImpl.class);
	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}

	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}


	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[好友信息设置（DIY）]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		DiyFiendInfo diyFiendInfo = null;
		try {
			diyFiendInfo = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserCrossRelationQuery userCrossRelationQuery = this.createUserCrossRelationQuery(diyFiendInfo); 
		List<UserCrossRelation> list = null;
		try {
			list = userCrossRelationDao.selectListBySelective(userCrossRelationQuery);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e1.getMessage());
			return ResponseTool.returnException();
		}
		if(list == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserCrossRelation crossRelation = this.createUserCrossRelation(diyFiendInfo,list.get(0).getId());
		crossRelation.setReqId(reqID);
		try {
			userCrossRelationDao.updateUserCrossRelation(crossRelation);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***好友信息设置***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 修改对象创建
	 * @param diyFiendInfo
	 * @return
	 */
	private UserCrossRelation createUserCrossRelation(DiyFiendInfo diyFiendInfo ,BigInteger id){
		UserCrossRelation crossRelation = new  UserCrossRelation();
		crossRelation.setId(id);
		if(diyFiendInfo.getType().equals(0)){
			crossRelation.setRemarkName(diyFiendInfo.getRemarkName());
		}else if(diyFiendInfo.getType().equals(1)){
			crossRelation.setGroupId(diyFiendInfo.getGroupId());
		}
		return crossRelation;
	}
	
	/**
	 * 修改对象创建
	 * @param diyFiendInfo
	 * @return
	 */
	private UserCrossRelationQuery createUserCrossRelationQuery(DiyFiendInfo diyFiendInfo){
		UserCrossRelationQuery crossRelation = new  UserCrossRelationQuery();
		crossRelation.setUserIdA(diyFiendInfo.getUserID());
		crossRelation.setUserIdB(diyFiendInfo.getFriendId());
		return crossRelation;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private DiyFiendInfo checkData(Object object) throws Exception{
		DiyFiendInfo diyFiendInfo =(DiyFiendInfo)object;
		if(diyFiendInfo == null){
			throw new Exception("对象转换失败！！！");
		}
		if(diyFiendInfo.getUserID() == null){
			throw new Exception("UserID为空！！！");
		}
		if(diyFiendInfo.getFriendId() == null){
			throw new Exception("FriendId为空！！！");
		}
		if(diyFiendInfo.getType() == null){
			throw new Exception("Type为空！！！");
		}
		
		return diyFiendInfo;
	}
	
	

}

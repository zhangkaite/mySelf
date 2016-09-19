package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.protocol.AddAttention;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午10:35:57  
 * @explain :用户添加关注 
 */
@SuppressWarnings({ "rawtypes"})
public class AddAttentionServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(AddAttentionServiceImpl.class);
	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}
	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[好友关注]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		AddAttention addAttention = null;
		//数据校验
		try {
			addAttention = this.checkDate(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建关注对象
		UserCrossRelation userCrossRelation =this.creatuserCrossRelation(addAttention,reqID);
		try {
			userCrossRelationDao.addUserCrossRelation(userCrossRelation);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+ "[数据添加失败!!!]",e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***添加好友关注***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	
	/**
	 * 创建对象
	 * @param addFriend
	 * @return
	 */
	private UserCrossRelation creatuserCrossRelation(AddAttention addAttention , String reqId){
		UserCrossRelation UserCrossRelation = new UserCrossRelation();
		UserCrossRelation.setType(UcConstant.RELATIONSHIPTYPE_REGARD);//关系类型（关注）
		UserCrossRelation.setUserIdA(addAttention.getUserID());
		UserCrossRelation.setUserIdB(addAttention.getAttentionId());
		UserCrossRelation.setReqId(reqId);
		return UserCrossRelation;
	}
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private AddAttention checkDate(Object object) throws Exception{
		AddAttention addAttention = (AddAttention)object;
		if(addAttention == null){
			throw new Exception("对象转换异常！！！");
		}
		if(addAttention.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(addAttention.getAttentionId() == null){
			throw new Exception("关注对象id为空！！！");
		}
		return addAttention;
	}

}

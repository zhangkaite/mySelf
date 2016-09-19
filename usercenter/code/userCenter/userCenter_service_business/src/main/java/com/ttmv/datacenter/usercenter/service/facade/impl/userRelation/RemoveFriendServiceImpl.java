package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.RemoveFriend;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午10:37:24  
 * @explain :删除好友
 */
@SuppressWarnings({ "rawtypes"})
public class RemoveFriendServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(RemoveFriendServiceImpl.class);
	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}
	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[好友删除]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		RemoveFriend removeFriend = null;
		//数据验证
		try {
			removeFriend = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建查询对象
		UserCrossRelationQuery crossRelationQuery = new UserCrossRelationQuery();
		crossRelationQuery.setUserIdA(removeFriend.getUserID());
		crossRelationQuery.setUserIdB(removeFriend.getFriendId());
		crossRelationQuery.setType(UcConstant.RELATIONSHIPTYPE_FRIEND);
		List<UserCrossRelation> userCrossRelations = null;
		try {
			userCrossRelations = userCrossRelationDao.selectListBySelective(crossRelationQuery);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" + "好友关系对象查询失败_" + e1.getMessage());
			return ResponseTool.returnException();
		}
		if(userCrossRelations != null && userCrossRelations.size()>=1){
			try {
				userCrossRelationDao.deleteUserCrossRelation(userCrossRelations.get(0).getId(),reqID);
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@"+ "好友删除失败！！！"+e.getMessage());
				return ResponseTool.returnException();
			}
			
			logger.info("[" + reqID + "]@@"+ "[***删除好友成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
			return ResponseTool.returnSuccess(null);
		}else{
			logger.info("[" + reqID + "]@@"+ "[您删除的好友不存在！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_PARAMETERERROR_CODE);
		}
		
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private RemoveFriend checkData(Object object) throws Exception{
		RemoveFriend removeFriend = (RemoveFriend)object;
		if(removeFriend == null){
			throw new Exception("对象转换异常！！！");
		}
		if(removeFriend.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(removeFriend.getFriendId() == null){
			throw new Exception("FriendId为空！！！");
		}
		return removeFriend;
	}

}

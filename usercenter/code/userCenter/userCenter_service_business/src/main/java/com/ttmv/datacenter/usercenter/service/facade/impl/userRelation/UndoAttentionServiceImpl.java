package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.UndoAttention;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午10:36:44  
 * @explain :用户取消关注
 */
@SuppressWarnings({ "rawtypes" })
public class UndoAttentionServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(RemoveFriendServiceImpl.class);
	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}
	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}
	
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[取消关注]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		UndoAttention undoAttention = null;
		//数据校验
		
		try {
			undoAttention = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserCrossRelationQuery crossRelationQuery = new UserCrossRelationQuery();
		crossRelationQuery.setUserIdA(undoAttention.getUserID());
		crossRelationQuery.setUserIdB(undoAttention.getAttentionId());
		crossRelationQuery.setType(1);
		
		List<UserCrossRelation> crossRelation = null;
		try {
			crossRelation = userCrossRelationDao.selectListBySelective(crossRelationQuery);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"数据查询失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		
		if(crossRelation.size() >= 1){
			try {
				userCrossRelationDao.deleteUserCrossRelation(crossRelation.get(0).getId(),reqID);
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@" +"数据删除失败！！！"+e.getMessage());
				return ResponseTool.returnException();
			}
		}
		
		logger.info("[" + reqID + "]@@"+ "[***取消好友关注成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private UndoAttention checkData(Object object) throws Exception{
		UndoAttention undoAttention = (UndoAttention)object;
		if(undoAttention == null){
			throw new Exception("对象转换异常！！！");
		}
		if(undoAttention.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(undoAttention.getAttentionId() == null){
			throw new Exception("AttentionId为空！！！");
		}
		return undoAttention;
	}
}

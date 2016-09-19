package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryFriend;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午3:43:15  
 * @explain :查询好友id List
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryFriendServiceImpl extends AbstractUserBase{
private static Logger logger = LogManager.getLogger(QueryFriendServiceImpl.class);
	
	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}
	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[查询好友id]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		QueryFriend queryFriend = null;
		try {
			queryFriend = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//查询
		List<BigInteger> uid = null;
		try {
			uid= userCrossRelationDao.selectUserInfoIdsByUserId(queryFriend.getUserID(),UcConstant.RELATIONSHIPTYPE_FRIEND);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		if(uid == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(null);
		}
		//返回
		Map resMap = new HashMap();
		resMap.put("userID", uid);
		logger.info("[" + reqID + "]@@"+ "[***查询好友id集合成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryFriend checkData(Object object) throws Exception{
		QueryFriend queryFriend = (QueryFriend)object;
		if(queryFriend == null){
			throw new Exception("对象转换失败！！！");
		}
		if(queryFriend.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		return queryFriend;
	}

}

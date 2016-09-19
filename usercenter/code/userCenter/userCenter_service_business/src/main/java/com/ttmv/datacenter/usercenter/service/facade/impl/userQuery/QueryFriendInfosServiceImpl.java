package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryFriendInfos;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 上午10:40:49  
 * @explain :批量获取好友信息
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryFriendInfosServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryFriendInfosServiceImpl.class);
	private UserInfoDao userInfoDao;
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[批量获取好友信息]_Start...");
		Long startTime = System.currentTimeMillis();
		
		QueryFriendInfos queryFriendInfos = null;
		try {
			queryFriendInfos = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		List<UserInfo> users = null;
		try {
			users = userInfoDao.selectUserInfosByIds(queryFriendInfos.getUserIDs());
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		if(users == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(null);
		}
		Map resMap = this.takeResData(users);
		logger.info("[" + reqID + "]@@"+ "[***批量获取好友信息***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		
		return resMap;
	}
	
	/**
	 * 组装返回结果
	 * @param 
	 * @return
	 */
	private Map takeResData(List<UserInfo> users){
		List result = new ArrayList();
		for(int i=0; i<users.size(); i++){
			UserInfo user = users.get(i);
			Map map = new HashMap();
			map.put("userID", user.getUserid());
			map.put("nickName", user.getNickName());
			map.put("exp", user.getExp());
			map.put("userPhoto",user.getUserPhoto());
			map.put("sign", user.getSign());
			
			//Damon 2015年12月18日15:44:30
			map.put("announcerLevel", user.getAnnouncerLevel());
			map.put("userLevel", user.getUserLevel());
			map.put("announcerExp", user.getAnnouncerExp());
			result.add(map);
		}
		return ResponseTool.returnSuccess(result);
	}
	
	/**
	 * 参数验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryFriendInfos checkData(Object object) throws Exception{
		QueryFriendInfos queryFriendInfos = (QueryFriendInfos)object;
		if(queryFriendInfos == null){
			throw new Exception("对象转换失败！！！");
		}
		if(queryFriendInfos.getUserIDs()==null){
			throw new Exception("好友id集合为空！！！");
		}
		return queryFriendInfos;
	}

}

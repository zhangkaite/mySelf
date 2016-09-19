package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryUsers;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午11:03:07  
 * @explain :用户列表查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUsersServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryUsersServiceImpl.class);
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[用户列表查询]_Start...");
		Long startTime = System.currentTimeMillis();
		QueryUsers queryUsers = null;
		try {
			queryUsers = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建查询对象
		UserInfoQuery userInfoQuery = null;
		try {
			userInfoQuery = this.createUserInfoQuery(queryUsers);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" + "UserInfoQuery对象创建失败_", e1);
			return ResponseTool.returnException();
		}
		List<UserInfo> users = null;
		try {
			users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		Map resMap = null;
		if(users!=null){
			//组装返回数据
			resMap = this.takeResData(users);
			logger.info("[" + reqID + "]@@"+ "[***用户列表查询***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		}else{
			logger.info("[" + reqID + "]@@"+ "没有找到符合条件的数据！！！");
			resMap = ResponseTool.returnSuccess(null);
		}
		return resMap;
	}
	
	private Map takeResData(List<UserInfo> users){
		List result = new ArrayList();
		for(int i=0; i<users.size(); i++){
			UserInfo user = users.get(i);
			Map map = new HashMap();
			map.put("userID", user.getUserid());
			map.put("TTnum", user.getTTnum());
			map.put("nickName", user.getNickName());
			map.put("vipType", user.getVipType());
			map.put("creatSource", user.getEnrollterminal());
			map.put("userPhoto",user.getUserPhoto());
			map.put("userName", user.getUserName());
			map.put("uType", user.getUtype());
			map.put("uStatus", user.getState());
			map.put("creatTime", user.getTime());
			result.add(map);
		}
		return ResponseTool.returnSuccess(result);
	}
	
	/**
	 * 创建查询对象
	 * @param queryUsers
	 * @return
	 */
	private UserInfoQuery createUserInfoQuery(QueryUsers queryUsers) throws Exception{
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		//排序字段
		Map<String,Boolean> sortFields = new HashMap<String, Boolean>();
		sortFields.put("time", false);
		userInfoQuery.setSorfFields(sortFields);
		if(queryUsers.getUStatus() == null){
			//userInfoQuery.setState(UcConstant.USTATE_NORMAL);//正常状态
		}else{
			userInfoQuery.setState(queryUsers.getUStatus());//用户状态
		}
		if(queryUsers.getTTnum()!=null && !"".equals(queryUsers.getTTnum())){
			userInfoQuery.setTTnum(new BigInteger(queryUsers.getTTnum()));
		}else{
			if(queryUsers.getPage() !=null){
				userInfoQuery.setPage(queryUsers.getPage());
			}
			if(queryUsers.getPageSize()!=null){
				userInfoQuery.setPageSize(queryUsers.getPageSize());
			}
			if(queryUsers.getType() != null){
				if(queryUsers.getType().equals(1)){//普通
					userInfoQuery.setUtype(UcConstant.UTYPE_GENERAL_CODE);
				}else if(queryUsers.getType().equals(2)){//官方
					userInfoQuery.setUtype(UcConstant.UTYPE_OFFICIAL_CODE);
				}
			}
			if(queryUsers.getVipType() != null){
				userInfoQuery.setVipType(queryUsers.getVipType());
			}
			if(queryUsers.getNickName() != null){
				userInfoQuery.setNickName(queryUsers.getNickName());
			}
			if(queryUsers.getStartTime() != null){
				userInfoQuery.setStartTime(unixTimeFmt(Long.parseLong(queryUsers.getStartTime())));
			}
			if(queryUsers.getEndTime() != null){
				userInfoQuery.setEndTime(unixTimeFmt(Long.parseLong(queryUsers.getEndTime())));
			}
			
		}
		return userInfoQuery;
	}
	
	/**
	 * Unix时间戳转java date
	 * @param 2015年6月16日15:25:12 Damon
	 * @return
	 * @throws ParseException
	 */
	public static Date unixTimeFmt(long time) throws ParseException {
		String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time * 1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dt);

	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private QueryUsers checkData(Object object) throws Exception{
		QueryUsers queryUsers = (QueryUsers)object;
		if(queryUsers == null){
			throw new Exception("数据转换异常！！！");
		}
		if(queryUsers.getType() == null){
			throw new Exception("Type查询类型为空！！！");
		}
		return queryUsers;
	}

}

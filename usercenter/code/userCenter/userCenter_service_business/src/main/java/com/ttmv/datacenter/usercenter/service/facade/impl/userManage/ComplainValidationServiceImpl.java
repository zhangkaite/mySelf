package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.ComplainValidation;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午11:02:26  
 * @explain :申述账号检测
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ComplainValidationServiceImpl extends AbstractUserBase{
private static Logger logger = LogManager.getLogger(ComplainValidationServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[申述账号检测]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		ComplainValidation complainValidation = null;
		try {
			complainValidation = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		//创建查询对象
		UserInfoQuery userInfoQuery = this.createUserInfoQuery(complainValidation);
		List<UserInfo> users = null;
		try {
			users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"数据查询失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		UserInfo user = null;
		Map resMap = new HashMap();
		if(users != null && users.size()==1){
			user = users.get(0);
			resMap.put("userID", user.getUserid());
		}else{
			resMap = null;
		}
		//返回
		logger.info("[" + reqID + "]@@"+ "[***申述账号检测***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建查询对象
	 * @param complainValidation
	 * @return
	 */
	private UserInfoQuery createUserInfoQuery(ComplainValidation complainValidation ){
		UserInfoQuery infoQuery = new UserInfoQuery();
		if(complainValidation.getType() == 0){//用户名
			infoQuery.setUserName(complainValidation.getValue());
		}else if(complainValidation.getType() == 1){//TT号码
			infoQuery.setTTnum(new BigInteger(complainValidation.getValue()));
		}else if(complainValidation.getType() == 2){//手机
			infoQuery.setLoginMobile(complainValidation.getValue());
		}else if(complainValidation.getType() == 3){//邮箱
			infoQuery.setLoginEmail(complainValidation.getValue());
		}
		return infoQuery;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private ComplainValidation checkData(Object object) throws Exception{
		ComplainValidation complainValidation = (ComplainValidation)object;
		if(complainValidation == null){
			throw new Exception("对象转换异常！！！");
		}
		if(complainValidation.getType() == null){
			throw new Exception("Type为空！！！");
		}
		if(complainValidation.getValue() == null){
			throw new Exception("value为空！！！");
		}	
		return complainValidation;
	}

}

package com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.MysqlUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.util.UserInfoMarkUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.mark.UserBaseMark;

public class MysqlUserInfoMapper extends BaseMysqlMapper{

	private static final String USERINFO_MAPPER = "com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlUserInfoMapper";
	private SentryAgent quickSentry;
	
	/**
	 * 通过id获取MyUserInfo
	 * 
	 * @param id
	 * @return MysqlUserInfo
	 * @throws Exception
	 */
	public MysqlUserInfo getMysqlUserInfo(BigInteger id,String dataSourceKey) throws Exception {
		/* 获取读的 dataSource*/
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			MysqlUserInfo userInfo =session.selectOne(USERINFO_MAPPER
					+ ".selectByPrimaryKey", id);
			if (userInfo != null) {
				return userInfo;
			}
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}

	/**
	 * 查询所有的MysqlUserInfo
	 * @param dataSourceKey
	 * @return
	 */
	public List<MysqlUserInfo> getAllMysqlUserInfo(String dataSourceKey)throws Exception{
		/* 获取读的 dataSource*/
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<MysqlUserInfo> list =session.selectList(USERINFO_MAPPER + ".selectAll");
			if (list != null && list.size() > 0) {
				return list;
			}
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return null;
	}
	/**
	 * 修改MysqlUserInfo 
	 * 
	 * @param mysqlUserInfo
	 * @param mapper
	 * @throws Exception
	 * @return int
	 */
	public int updateMysqlUserInfo(MysqlUserInfo mysqlUserInfo,String dataSourceKey) throws Exception {
		/* 获取读的 dataSource*/
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try {
			session = sqlSessionFactory.openSession();
			int num = session.update(USERINFO_MAPPER
						+ ".updateByPrimaryKeySelective",
				mysqlUserInfo);
			return num;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("修改MysqlUserInfo失败！",e);
		}finally{
			session.close();
		}
	}

	/**
	 * 新增MySqlUserInfo
	 * 
	 * @param mysqlUserInfo
	 * @param mapper
	 * @return int
	 */
	public List<Object> addMysqlUserInfo(MysqlUserInfo mysqlUserInfo,String dataSourceKey) throws Exception {
		List<Object> results = new ArrayList<Object>();
		/* 设置写入的datasource */
		this.setWriteDataSource(dataSourceKey);
		SqlSession session =  null;
		try {
			session = sqlSessionFactory.openSession();
			int num = session.insert(USERINFO_MAPPER
						+ ".insertSelective", mysqlUserInfo);
			results.add(num);
			return results;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("用户添加失败！",e);
		}finally{
			session.close();
		}
	}
	
	public void getUpdateMysqlUserInfo(UserInfo userInfo,MysqlUserInfo mysql)throws Exception{
	    if(userInfo.getPassword() != null && !"".equals(userInfo.getPassword())){
	    	mysql.setUpassword(userInfo.getPassword());
	    }
	    if(userInfo.getBindingEmail() != null && !"".equals(userInfo.getBindingEmail())){
	    	mysql.setBindingemail(userInfo.getBindingEmail());
	    }
	    if(userInfo.getLoginEmail() != null && !"".equals(userInfo.getLoginEmail())){
	    	mysql.setLoginemail(userInfo.getLoginEmail());
	    }
	    if(userInfo.getBindingMobile() != null && !"".equals(userInfo.getBindingMobile())){
	    	mysql.setBindingmobile(userInfo.getBindingMobile());
	    }
		if(userInfo.getLoginMobile() != null && !"".equals(userInfo.getLoginMobile())){
			mysql.setLoginmobile(userInfo.getLoginMobile());
		}
		if(userInfo.getLoginGoodTTnum() != null && !"".equals(userInfo.getLoginGoodTTnum())){
			mysql.setLoginGoodTtnum(userInfo.getLoginGoodTTnum());
		}
		if(userInfo.getLoginGoodTTnumType() != null && !"".equals(userInfo.getLoginGoodTTnumType())){
			mysql.setLoginGoodTtnumType(userInfo.getLoginGoodTTnumType());
		}
		byte[] mark = mysql.getMark();
		if(userInfo.getState() != null && !"".equals(userInfo.getState())){
			Integer status = userInfo.getState();
			UserInfoMarkUtil.convertStatusToInner(mark, status);
		}
		if(userInfo.getVipType() != null && !"".equals(userInfo.getVipType())){
			Integer vip = userInfo.getVipType();
			UserInfoMarkUtil.convertVipToInner(mark, vip);
		}
		mysql.setMark(mark);
	}
	
	/**
	 * 转换MysqlUserInfo到UserInfo
	 * @param userInfo
	 * @param mysqlUserInfo
	 * @return
	 */
	public UserInfo getConvertMysqlUserInfoToUserInfo(UserInfo userInfo,MysqlUserInfo mysql)throws Exception{
		userInfo.setUserid(mysql.getUserId());
		userInfo.setUserName(mysql.getUsername());
		userInfo.setPassword(mysql.getUpassword());
		userInfo.setTTnum(new BigInteger(mysql.getTTnum()));
		/* 登陆的GoodTTnum */
		if(mysql.getLoginGoodTtnum() != null && !"".equals(mysql.getLoginGoodTtnum())){
			userInfo.setLoginGoodTTnum(mysql.getLoginGoodTtnum());
		}
		if(mysql.getLoginemail() != null && !"".equals(mysql.getLoginemail())){
			userInfo.setLoginEmail(mysql.getLoginemail());
		}
		if(mysql.getBindingemail() != null && !"".equals(mysql.getBindingemail())){
			userInfo.setBindingEmail(mysql.getBindingemail());
		}
		if(mysql.getLoginmobile() != null && !"".equals(mysql.getLoginmobile())){
			userInfo.setLoginMobile(mysql.getLoginmobile());
		}
		if(mysql.getBindingemail() != null && !"".equals(mysql.getBindingmobile())){
			userInfo.setBindingMobile(mysql.getBindingmobile());
		}
		/* 转换mark值 */
		byte[] mark = mysql.getMark();
		if(mark == null ){
			throw new Exception("查询MysqlUserInfo对象的Mark不能为空!");
		}
		Integer status = UserInfoMarkUtil.convertStatusToOut(mark);
		Integer vip = UserInfoMarkUtil.convertVipToOut(mark);
		userInfo.setState(status);
		userInfo.setVipType(vip);
		return userInfo;
	}
	
	/**
	 * 转换UserInfo对象到MysqlUserInfo对象
	 * @param userInfo
	 * @return
	 */
	public MysqlUserInfo getConvertUserInfoToMysqlUserInfo(UserInfo userInfo)throws Exception{
		MysqlUserInfo mysql = new MysqlUserInfo();
		/* 转换mark值 */
		byte mark[] = UserBaseMark.createMark();
		Integer status = userInfo.getState();
		Integer vip = userInfo.getVipType();
		UserInfoMarkUtil.convertStatusToInner(mark, status);
		UserInfoMarkUtil.convertVipToInner(mark, vip);
		mysql.setUserId(userInfo.getUserid());
		mysql.setUpassword(userInfo.getPassword());
		mysql.setUsername(userInfo.getUserName());
		mysql.setTTnum(userInfo.getTTnum().toString());
		mysql.setMark(mark);
		
		/* 登陆邮箱 */
		if(userInfo.getLoginEmail() != null && !"".equals(userInfo.getLoginEmail())){
			mysql.setLoginemail(userInfo.getLoginEmail());
		}
		
		/* 绑定邮箱*/
		if(userInfo.getBindingEmail() != null && !"".equals(userInfo.getBindingEmail())){
			mysql.setBindingemail(userInfo.getBindingEmail());
		}
		
		/* 登陆手机 */
		if(userInfo.getLoginMobile() != null && !"".equals(userInfo.getLoginMobile())){
			mysql.setLoginmobile(userInfo.getLoginMobile());
		}
		
		/* 绑定手机*/
		if(userInfo.getBindingMobile() != null && !"".equals(userInfo.getBindingMobile())){
			mysql.setBindingmobile(userInfo.getBindingMobile());
		}
		
		/* 绑定的靓号*/
		if(userInfo.getLoginGoodTTnum() != null && !"".equals(userInfo.getLoginGoodTTnum())){
			mysql.setLoginGoodTtnum(userInfo.getLoginGoodTTnum());
		}
		return mysql;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.bean.query.QueryUserForbiddenExpire;
import com.ttmv.dao.inter.mysql.IUserForbiddenExpireInter;
import com.ttmv.dao.mapper.mysql.UserForbiddenExpireMapper;

@Service("iUserForbiddenExpireInterImpl")
public class IUserForbiddenExpireInterImpl implements IUserForbiddenExpireInter {

	@Resource(name = "userForbiddenExpireMapper")
	private UserForbiddenExpireMapper userForbiddenExpireMapper;
	
	@Override
	public Integer addUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire) throws Exception {
		Integer result = userForbiddenExpireMapper.addUserForbiddenExpire(userForbiddenExpire);
		return result;
	}

	@Override
	public Integer updateUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire) throws Exception {
		Integer result = userForbiddenExpireMapper.updateUserForbiddenExpire(userForbiddenExpire);
		return result;
	}

	@Override
	public Integer deleteUserForbiddenExpire(BigInteger id) throws Exception {
		Integer result = userForbiddenExpireMapper.deleteUserForbiddenExpire(id);
		return result;
	}

	@Override
	public UserForbiddenExpire queryUserForbiddenExpire(BigInteger userId) throws Exception {
		UserForbiddenExpire UserForbiddenExpire = userForbiddenExpireMapper.queryUserForbiddenExpire(userId);
		return UserForbiddenExpire;
	}

	@Override
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByEndTime(QueryUserForbiddenExpire queryUserForbiddenExpire) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryUserForbiddenExpire.getStartTime()));
		query.setEndTime(sdf.format(queryUserForbiddenExpire.getEndTime()));
		List<UserForbiddenExpire> datas = userForbiddenExpireMapper.queryListUserForbiddenExpireByEndTime(query);
		return datas;
	}

	@Override
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByRemindTime(
			QueryUserForbiddenExpire queryUserForbiddenExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setRemindTime(sdf.format(queryUserForbiddenExpire.getRemindTime()));
		List<UserForbiddenExpire> datas = userForbiddenExpireMapper.queryListUserForbiddenExpireByRemindTime(query);
		return datas;
	}
}

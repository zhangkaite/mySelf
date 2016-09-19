package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.mapper.UserInfoDaoMapper;

public class IUserInfoInterImpl implements	IUserInfoInter{

	private UserInfoDaoMapper userInfoDaoMapper;
	
	public Integer addUserInfo(UserInfo userInfo)throws Exception {
		Integer result = userInfoDaoMapper.addUserInfo(userInfo);
		return result;
	}

	public Integer updateUserInfo(UserInfo userInfo)throws Exception {
		Integer result = userInfoDaoMapper.updateUserInfo(userInfo);
		return result;
	}

	public Integer deleteUserInfo(BigInteger id,Integer status) throws Exception {
		if(id == null){
			throw new Exception("ID不能为空！");
		}
		if(status == null){
			throw new Exception("用户状态不能为空！");
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setUserStatus(status);
		Integer result = userInfoDaoMapper.updateUserInfo(userInfo);
		return result;
	}
	
	public Integer deleteUserInfo(BigInteger id)throws Exception {
		if(id == null){
			throw new Exception("ID不能为空！");
		}
		Integer result = userInfoDaoMapper.deleteUserInfo(id);
		return result;
	}

	public UserInfo queryUserInfo(BigInteger id) throws Exception{
		if(id == null){
			throw new Exception("ID不能为空！");
		}
		UserInfo data = userInfoDaoMapper.queryUserInfo(id);
		return data;
	}

	public Page queryPageUserInfo(UserInfoQuery userInfoQuery) throws Exception{
		Integer page = userInfoQuery.getPage();
		Integer pageSize = userInfoQuery.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = userInfoDaoMapper.queryPageUserInfoSum(userInfoQuery);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		userInfoQuery.setStart(start);
		List<UserInfo> datas = userInfoDaoMapper.queryPageUserInfo(userInfoQuery);
		p.setSum(sum);
		p.setData(datas);
		return p;
	}

	public List<UserInfo> queryUserInfo(UserInfoQuery userInfoQuery)throws Exception {
		List<UserInfo> datas = userInfoDaoMapper.queryUserInfo(userInfoQuery);
		return datas;
	}
	
	public UserInfo login(String userName,String userPasswd) throws Exception {
		if(userName == null ||"".equals(userName)){
			throw new Exception("用户名不能为空！");
		}
		if(userPasswd == null ||"".equals(userPasswd)){
			throw new Exception("密码不能为空！");
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserPasswd(userPasswd);
		UserInfo user = userInfoDaoMapper.login(userInfo);
		return user;
	}

	/**
	 * 获取userInfo分页的起始位置
	 * 
	 * @param solrs
	 * @param page
	 * @param pageSize
	 */
	private Integer getPagingStart(Integer sum, Integer page, Integer pageSize)throws Exception{
		Integer start = 0;
		/* 分页的总数 */
		Integer sumPage = sum / pageSize + 1;
		/* 取得起始位置 */
		if (page <= sumPage) {
			start = (page - 1) * pageSize;
		}else{
			throw new Exception("page不正确,不能大于总页数或是小于1！");
		}
		return start;
	}
	
	public List<UserInfo> queryListByIds(List<String> ids) throws Exception {
		if(ids == null || ids.size() == 0){
			throw new Exception("ids的集合不能为空！");
		}
		List<UserInfo> list = new ArrayList<UserInfo>();
		for(int i=0;i<ids.size();i++ ){
			String id = ids.get(i)+"";
			UserInfo user = queryUserInfo(new BigInteger(id));
			list.add(user);
		}
		return list;
	}
	
	public void setUserInfoDaoMapper(UserInfoDaoMapper userInfoDaoMapper)throws Exception {
		this.userInfoDaoMapper = userInfoDaoMapper;
	}	
}

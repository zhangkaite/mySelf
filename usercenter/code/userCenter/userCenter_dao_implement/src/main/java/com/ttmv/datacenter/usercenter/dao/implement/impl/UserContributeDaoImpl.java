package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercontribute.MysqlUserContribution;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.MysqlGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercontribute.MysqlUserContributionMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserContributeDao;
import com.ttmv.datacenter.usercenter.domain.data.UserContribution;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryContributionList;

@SuppressWarnings({  "unused" })
public class UserContributeDaoImpl implements UserContributeDao {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(UserContributeDaoImpl.class);

	private TableIdGenerate tableIdGenerate;
	private MysqlUserContributionMapper mysqlUserContributionMapper;
	
	public List<UserContribution> getAllUserContributionByRoomId(UserContribution userContribution) throws Exception {
		if(userContribution != null){
			List<UserContribution> datas = mysqlUserContributionMapper.getAllRoomUser(userContribution);
			if(datas !=null && datas.size() > 0){
				return datas;
			}
		}
		return null;
	}

	public Integer updateUserContribution(UserContribution userContribution)throws Exception {
		if(userContribution !=null ){
			Integer result = null;
			UserContribution user = mysqlUserContributionMapper.queryMysqlUserContribution(userContribution);
			if(user == null){	
				log.info("[roomID]==>" + userContribution.getRoomId() + "频道排行榜数据查询为NULL,执行insert操作[userID]==>>" + userContribution.getUserId() );
				userContribution.setId(new BigInteger(tableIdGenerate.getTableId("UserContribute").toString()));
				result = mysqlUserContributionMapper.insertMysqlUserContribution(userContribution);
			}else{
				user.setContributionSum(userContribution.getContributionSum());
				user.setNickName(userContribution.getNickName());
				user.setUserPhoto(userContribution.getUserPhoto());
				log.info("[roomID]==>" + userContribution.getRoomId() + "频道排行榜数据查询成功,执行update操作[userID]==>>" + userContribution.getUserId() );
				result = mysqlUserContributionMapper.updateMysqlUserContribution(user);
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 根据用户ID和dateType删除数据
	 * @param userContribution
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByRoomIdAndDataType(UserContribution userContribution)throws Exception{
		if(userContribution !=null ){
			Integer result = mysqlUserContributionMapper.deleteByRoomIdAndDataType(userContribution);
			return result;
		}
		return null;
	}
	
	public TableIdGenerate getTableIdGenerate() {
		return tableIdGenerate;
	}

	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}

	public MysqlUserContributionMapper getMysqlUserContributionMapper() {
		return mysqlUserContributionMapper;
	}

	public void setMysqlUserContributionMapper(
			MysqlUserContributionMapper mysqlUserContributionMapper) {
		this.mysqlUserContributionMapper = mysqlUserContributionMapper;
	}
}

package com.ttmv.datacenter.usercenter.dao.implement.mapper.group;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.MysqlGroup;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.domain.data.Group;

public class MysqlGroupMapper extends BaseMysqlMapper {

	private String GROUP_MAPPER = "com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlGroupMapper";
	private SentryAgent quickSentry;
	
	/**
	 * 添加MysqlGroup对象
	 * 
	 * @param mysqlGroup
	 */
	public List<Object> addMysqlGroup(MysqlGroup mysqlGroup,String dataSourceKey) throws Exception {
		List<Object> results = new ArrayList<Object>();
		/* 设置写入的datasource */
		this.setWriteDataSource(dataSourceKey);
		SqlSession session =  null;
		try {
			session = sqlSessionFactory.openSession();
			int num = session.insert(
					GROUP_MAPPER + ".insertSelective", mysqlGroup);
			results.add(num);
			return results;
		} catch (Exception e) {
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("mysqlGroup查询出错！",e);
		}finally{
			session.close();
		}
		
	}

	/**
	 * 查询所有的MysqlGroup
	 * @param dataSourceKey
	 * @return
	 */
	public List<MysqlGroup> getAllMysqlGroup(String dataSourceKey)throws Exception{
		/* 获取读的 dataSource*/
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<MysqlGroup> list =session.selectList(GROUP_MAPPER + ".selectAll");
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
	 * 修改MysqlGroup对象
	 * 
	 * @param mysqlGroup
	 * @return
	 */
	public int updateMysqlGroup(MysqlGroup mysqlGroup, String dataSourceKey)
			throws Exception {
		/* 获取写入的datasource */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			int num = session.update(GROUP_MAPPER + ".updateByPrimaryKeySelective", mysqlGroup);
			return num;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("mysqlGroup修改出错！",e);
		}finally{
			session.close();
		}	
	}

	/**
	 * 删除MysqlGroup对象
	 * 
	 * @param mysqlGroup
	 * @return
	 */
	public int deleteMysqlGroup(BigInteger id, String dataSourceKey)
			throws Exception {
		/* 获取指定的datasource */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			int num = session.delete(GROUP_MAPPER + ".deleteByPrimaryKey", id);
			return num;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("mysqlGroup删除出错！",e);
		}finally{
			session.close();
		}
	}

	/**
	 * 根据id查询MysqlGroup
	 * 
	 * @param id
	 * @return
	 */
	public MysqlGroup getMysqlGroupById(BigInteger id, String dataSourceKey)
			throws Exception {
		/* 获取读取的datasource */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			MysqlGroup mysql = session.selectOne(GROUP_MAPPER + ".selectByPrimaryKey", id);
			return mysql;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}

	/**
	 * 通过userId 查询Group
	 * 
	 * @param userid
	 * @return
	 */
	public List<MysqlGroup> getMysqlGroupByUserid(BigInteger userid,
			String dataSourceKey) throws Exception {
		/* 获取读取的datasource */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<MysqlGroup> list = session.selectList(GROUP_MAPPER + ".selectByUserId", userid);
			return list;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 转换Group对象
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public MysqlGroup getConvertGroupToMsqlGroup(Group group)throws Exception{
		MysqlGroup mysql = new MysqlGroup();
	
		BeanCopyProperties.copyProperties(group, mysql, false,null);
		mysql.setId(group.getGroupId());
		return mysql;
	}
	
	/**
	 * 复制
	 * @param mysql
	 * @return
	 * @throws Exception
	 */
	public Group getConvertMysqlGroupToGroup(MysqlGroup mysql)throws Exception{
		Group group = new Group();
		BeanCopyProperties.copyProperties(mysql, group, false,null);
		group.setGroupId(mysql.getId());
		return group;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

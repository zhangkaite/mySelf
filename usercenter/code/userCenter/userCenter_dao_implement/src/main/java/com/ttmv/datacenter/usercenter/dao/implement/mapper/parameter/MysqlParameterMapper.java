package com.ttmv.datacenter.usercenter.dao.implement.mapper.parameter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.parameter.MysqlParameter;
import com.ttmv.datacenter.usercenter.domain.operation.query.ParameterQuery;

public class MysqlParameterMapper extends BaseMysqlMapper {

	private String PARAMETER_MAPPER = "com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlParameterMapper";

	/**
	 * 添加parameter对象
	 * 
	 * @param parameter
	 * @return
	 */
	public int addMysqlParameter(MysqlParameter parameter) throws Exception{
		/* 获取写入的datasource */
		this.getWriteDataSource();

		int num = sqlSessionFactory.openSession().insert(
				this.PARAMETER_MAPPER + ".insertSelective", parameter);
		return num;
	}

	/**
	 * 修改parameter对象
	 * 
	 * @param parameter
	 * @return
	 */
	public int updateMysqlParameter(MysqlParameter parameter,
			String dataSourceKey)throws Exception {
		/* 获取指定datasource连接 */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().update(
				this.PARAMETER_MAPPER + ".updateByPrimaryKeySelective",
				parameter);
		return num;

	}

	/**
	 * 删除指定的id的数据
	 * 
	 * @param id
	 * @param dataSourceKey
	 * @return
	 */
	public int deleteMysqlParameter(BigInteger id, String dataSourceKey) throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().delete(
				this.PARAMETER_MAPPER + ".deleteByPrimaryKey", id);
		return num;
	}

	/**
	 * 删除指定的id的数据
	 * 
	 * @param id
	 * @param dataSourceKey
	 * @return
	 */
	public int deleteMysqlParameterByKey(String key, String dataSourceKey)throws Exception {
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().delete(
				this.PARAMETER_MAPPER + ".deleteByKey", key);
		return num;
	}

	/**
	 * 通过parameterId 获取Parameter 对象
	 * 
	 * @param id
	 * @param dataSourceKey
	 * @return
	 */
	public MysqlParameter getMysqlParameterByKey(String key,
			String dataSourceKey)throws Exception {
		/* 获取指定datasource连接 */
		this.getReadDataSource(dataSourceKey);

		MysqlParameter mysql = sqlSessionFactory.openSession().selectOne(
				this.PARAMETER_MAPPER + ".selectByKey", key);
		return mysql;
	}

	/**
	 * 根据类型查询parameter列表
	 * 
	 * @param type
	 * @param dataSourceKey
	 * @return
	 */
	public List<MysqlParameter> getMysqlParameterByType(String type,
			String dataSourceKey)throws Exception {
		/* 获取指定datasource连接 */
		this.getReadDataSource(dataSourceKey);

		List<MysqlParameter> mysqls = sqlSessionFactory.openSession()
				.selectList(this.PARAMETER_MAPPER + ".selectListByType", type);
		return mysqls;
	}

	/**
	 * 根据id查询MysqlParameter
	 * 
	 * @param id
	 * @return
	 */
	public MysqlParameter getMysqlParameterById(BigInteger id,
			String dataSourceKey) throws Exception{
		/* 获取指定datasource连接 */
		this.getReadDataSource(dataSourceKey);

		MysqlParameter mysql = sqlSessionFactory.openSession().selectOne(
				this.PARAMETER_MAPPER + ".selectByPrimaryKey", id);
		return mysql;
	}

	/**
	 * 多条件查询
	 * 
	 * @param parameterQuery
	 * @return
	 */
	public List<MysqlParameter> getMysqlParametersByParameterQuery(
			ParameterQuery parameterQuery, String dataSourceKey) throws Exception{
		/* 获取指定datasource连接 */
		this.getReadDataSource(dataSourceKey);

		List<MysqlParameter> mysqls = sqlSessionFactory.openSession()
				.selectList(
						this.PARAMETER_MAPPER + ".selectListByParameterQuery",
						parameterQuery);
		if(mysqls != null){
			return mysqls;
		}
		return null;
	}
}

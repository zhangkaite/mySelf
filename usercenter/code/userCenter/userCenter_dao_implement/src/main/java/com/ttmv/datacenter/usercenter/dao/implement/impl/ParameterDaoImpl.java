package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.parameter.MysqlParameter;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.parameter.MysqlParameterMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.interfaces.ParameterDao;
import com.ttmv.datacenter.usercenter.domain.data.Parameter;
import com.ttmv.datacenter.usercenter.domain.operation.query.ParameterQuery;

public class ParameterDaoImpl implements ParameterDao {

	/* 固定的datasourceKey */
	private static final String DATA_SOURCE_KEY = "uc_mysql_m2";
	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(ParameterDaoImpl.class);

	private MysqlParameterMapper mysqlParameterMapper;

	/**
	 * 添加parameter
	 */
	public int addParameter(Parameter parameter) throws Exception {
		if (parameter == null) {
			log.error("Parameter对象不能为空！");
			throw new Exception("添加Parameter对象不能为null");
		}
		/* 添加mysql指定数据库 */
		MysqlParameter mysql = new MysqlParameter();
		BeanCopyProperties.copyProperties(parameter, mysql, false,null);
		int result = mysqlParameterMapper.addMysqlParameter(mysql);
		return result;
	}

	/**
	 * 更新Parameter
	 */
	public int updateParameter(Parameter parameter) throws Exception {
		if (parameter == null) {
			log.error("Parameter对象不能为空！");
			throw new Exception("修改Parameter对象不能为null");
		}
		/* 修改mysql指定数据库 */
		MysqlParameter mysql = new MysqlParameter();
		BeanCopyProperties.copyProperties(parameter, mysql, false, null);
		int result = mysqlParameterMapper.updateMysqlParameter(mysql,
				DATA_SOURCE_KEY);
		return result;
	}

	/**
	 * 根据Id删除Parameter
	 */
	public int deleteParameter(BigInteger id) throws Exception {
		if (id == null) {
			log.error("id不能为空！");
			throw new Exception("修改Parameter对象id不能为null");
		}
		/* 返回指定的结果 */
		int result = mysqlParameterMapper.deleteMysqlParameter(id,
				DATA_SOURCE_KEY);
		return result;
	}

	/**
	 * 根据Key删除Parameter
	 */
	public int deleteParameterByKey(String key) throws Exception {
		if (key == null) {
			log.error("key不能为空！");
			throw new Exception("删除Parameter对象key不能为null");
		}
		/* 返回指定的结果 */
		int result = mysqlParameterMapper.deleteMysqlParameterByKey(key,
				DATA_SOURCE_KEY);
		return result;
	}

	/**
	 * 通过key查询parameter
	 */
	public Parameter selectParameterByKey(String key) throws Exception {
		if (key == null) {
			log.error("id不能为空！");
			throw new Exception("查询Parameter对象key不能为null");
		}
		Parameter parameter = new Parameter();
		MysqlParameter mysql = mysqlParameterMapper.getMysqlParameterByKey(key,
				DATA_SOURCE_KEY);
		if (mysql != null) {
			BeanCopyProperties.copyProperties(mysql, parameter, false,null);
			return parameter;
		}
		return null;
	}

	/**
	 * 通过type查询parameter
	 */
	public List<Parameter> selectParameterByType(String type) throws Exception {
		if (type == null) {
			log.error("type不能为空！");
			throw new Exception("查询Parameter对象type不能为null");
		}
		List<Parameter> parameters = new ArrayList<Parameter>();
		List<MysqlParameter> mysqls = mysqlParameterMapper
				.getMysqlParameterByType(type, DATA_SOURCE_KEY);
		if (mysqls != null && mysqls.size() > 0) {
			for (MysqlParameter mysql : mysqls) {
				Parameter parameter = new Parameter();
				BeanCopyProperties.copyProperties(mysql, parameter, false,null);
				parameters.add(parameter);
			}
			return parameters;
		}
		return null;
	}

	/**
	 * 通过Id查询parameter
	 */
	public Parameter selectParameter(BigInteger id) throws Exception {
		if (id == null) {
			log.error("id不能为空！");
			throw new Exception("查询Parameter对象 id不能为null");
		}
		Parameter parameter = new Parameter();
		MysqlParameter mysql = mysqlParameterMapper.getMysqlParameterById(id,
				DATA_SOURCE_KEY);
		if (mysql != null) {
			BeanCopyProperties.copyProperties(mysql, parameter, false, null);
			return parameter;
		}
		return null;
	}

	/**
	 * 多条件查询
	 */
	public List<Parameter> selectListBySelective(ParameterQuery parameterQuery)
			throws Exception {
		if (parameterQuery != null) {
			log.error("ParameterQuery不能为空！");
			throw new Exception("查询Parameter对象ParameterQuery不能为null");
		}
		List<Parameter> parameters = new ArrayList<Parameter>();
		List<MysqlParameter> mysqls = mysqlParameterMapper
				.getMysqlParametersByParameterQuery(parameterQuery,
						DATA_SOURCE_KEY);
		if (mysqls != null && mysqls.size() > 0) {
			for (MysqlParameter mysql : mysqls) {
				Parameter parameter = new Parameter();
				BeanCopyProperties.copyProperties(mysql, parameter, false, null);
				parameters.add(parameter);
			}
			return parameters;
		}
		return null;
	}

	public void setMysqlParameterMapper(
			MysqlParameterMapper mysqlParameterMapper) {
		this.mysqlParameterMapper = mysqlParameterMapper;
	}
}

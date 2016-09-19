package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.DataDictionary;
import com.ttmv.monitoring.entity.querybean.DataDictionaryQuery;

public class DataDictionaryDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String DATADICTIONARY_MAPPER ="com.ttmv.monitoring.imp.DataDictionaryMapper";
	
	public Integer addDataDictionary(DataDictionary dataDictionary) {
		Integer result = sqlSessionFactory.openSession().insert(this.DATADICTIONARY_MAPPER + ".insertSelective",dataDictionary);
		return result;
	}

	public Integer updateDataDictionary(DataDictionary dataDictionary) {
		Integer result = sqlSessionFactory.openSession().update(this.DATADICTIONARY_MAPPER + ".updateByPrimaryKeySelective", dataDictionary);
		return result;
	}

	public Integer deleteDataDictionary(DataDictionaryQuery dataDictionaryQuery) {
		Integer result = sqlSessionFactory.openSession().delete(this.DATADICTIONARY_MAPPER + ".deleteSelective", dataDictionaryQuery);
		return result;
	}

	public DataDictionary queryDataDictionary(BigInteger id) {
		DataDictionary data = sqlSessionFactory.openSession().selectOne(this.DATADICTIONARY_MAPPER + ".selectByPrimaryKey", id);
		return data;
	}
	
	public DataDictionary queryDataDictionary(String dataKey) {
		DataDictionary data = sqlSessionFactory.openSession().selectOne(this.DATADICTIONARY_MAPPER + ".selectByDataKey", dataKey);
		return data;
	}
	
	public List<DataDictionary> queryDataDictionary(DataDictionaryQuery dataDictionaryQuery) {
		List<DataDictionary> datas =  sqlSessionFactory.openSession().selectList(this.DATADICTIONARY_MAPPER + ".selectByConditions", dataDictionaryQuery);
		return datas;
	}
	
	public List<DataDictionary> queryPageDataDictionary(DataDictionaryQuery dataDictionaryQuery) {
		List<DataDictionary> datas =  sqlSessionFactory.openSession().selectList(this.DATADICTIONARY_MAPPER + ".selectByFuzzyConditions", dataDictionaryQuery);
		return datas;
	}
	
	public Integer queryPageDataDictionarySum(DataDictionaryQuery dataDictionaryQuery) {
		Integer sum =  sqlSessionFactory.openSession().selectOne(this.DATADICTIONARY_MAPPER + ".selectByFuzzyConditionsAll", dataDictionaryQuery);
		return sum;
	}
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}

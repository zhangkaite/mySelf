package com.ttmv.monitoring.inter;

import java.util.List;

import com.ttmv.monitoring.entity.DataDictionary;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.DataDictionaryQuery;

public interface IDataDictionaryInter {

	/**
	 * 添加DataDictionary
	 * @param dataDictionary
	 * @throws Exception
	 */
	public Integer addDataDictionary(DataDictionary dataDictionary)throws Exception;
	
	/**
	 * 修改DataDictionary
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public Integer	updateDataDictionary(DataDictionary dataDictionary)throws Exception;
	
	/**
	 * 删除DataDictionary
	 * @param dataDictionaryQuery
	 * @return
	 * @throws Exception
	 */
	public Integer deleteDataDictionary(DataDictionaryQuery dataDictionaryQuery)throws Exception;
	
	/**
	 * 根据Key查询DataDictionary
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public DataDictionary queryDataDictionary(String key)throws Exception;
	
	/**
	 * 条件分页查询DataDictionary
	 * @param dataDictionaryQuery
	 * @return
	 * @throws Exception
	 */
	public Page queryPageDataDictionary(DataDictionaryQuery dataDictionaryQuery)throws Exception;
	
	/**
	 * 条件查询，精确查询DataDictionary
	 * @param dataDictionaryQuery
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> queryDataDictionary(DataDictionaryQuery dataDictionaryQuery)throws Exception;
}

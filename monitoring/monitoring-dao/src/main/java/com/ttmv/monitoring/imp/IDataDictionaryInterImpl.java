package com.ttmv.monitoring.imp;

import java.util.List;

import com.ttmv.monitoring.entity.DataDictionary;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.DataDictionaryQuery;
import com.ttmv.monitoring.inter.IDataDictionaryInter;
import com.ttmv.monitoring.mapper.DataDictionaryDaoMapper;

public class IDataDictionaryInterImpl implements IDataDictionaryInter{

	private DataDictionaryDaoMapper dataDictionaryDaoMapper;
	
	public Integer addDataDictionary(DataDictionary dataDictionary)throws Exception {
		Integer result = dataDictionaryDaoMapper.addDataDictionary(dataDictionary);
		return result;
	}

	public Integer updateDataDictionary(DataDictionary dataDictionary)throws Exception {
		Integer result = dataDictionaryDaoMapper.updateDataDictionary(dataDictionary);
		return result;
	}

	public Integer deleteDataDictionary(DataDictionaryQuery dataDictionaryQuery) throws Exception {
		Integer result = dataDictionaryDaoMapper.deleteDataDictionary(dataDictionaryQuery);
		return result;
	}

	public DataDictionary queryDataDictionary(String dataKey) throws Exception {
		DataDictionary dataDictionary = dataDictionaryDaoMapper.queryDataDictionary(dataKey);
		return dataDictionary;
	}

	public List<DataDictionary> queryDataDictionary(DataDictionaryQuery dataDictionaryQuery) throws Exception {
		List<DataDictionary> datas = dataDictionaryDaoMapper.queryDataDictionary(dataDictionaryQuery);
		return datas;
	}
	
	public Page queryPageDataDictionary(DataDictionaryQuery dataDictionaryQuery)throws Exception {
		Integer page = dataDictionaryQuery.getPage();
		Integer pageSize = dataDictionaryQuery.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = dataDictionaryDaoMapper.queryPageDataDictionarySum(dataDictionaryQuery);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		dataDictionaryQuery.setStart(start);
		List<DataDictionary> datas = dataDictionaryDaoMapper.queryPageDataDictionary(dataDictionaryQuery);
		p.setSum(sum);
		p.setData(datas);
		return p;
	}
	
	/**
	 * 分页的起始位置
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
		/* 添加其他的判断操作 */
		return start;
	}
	
	public void setDataDictionaryDaoMapper(DataDictionaryDaoMapper dataDictionaryDaoMapper) {
		this.dataDictionaryDaoMapper = dataDictionaryDaoMapper;
	}

}

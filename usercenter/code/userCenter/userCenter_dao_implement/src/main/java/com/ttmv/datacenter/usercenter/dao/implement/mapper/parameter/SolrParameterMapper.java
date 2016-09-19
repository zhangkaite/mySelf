package com.ttmv.datacenter.usercenter.dao.implement.mapper.parameter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.parameter.SolrParameter;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;

public class SolrParameterMapper {

	private final Logger log = LogManager.getLogger(SolrParameterMapper.class);

	private SolrServer solrServer;
	private SentryAgent quickSentry;
	
	/**
	 * 添加SolrParameter的数据/或是修改SolrParameter数据
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void addSolrParameter(SolrParameter solr) throws Exception{
		try {
			solrServer.addBean(solr);
		} catch (Exception e) {
			log.error("添加索引SolrParameter失败!",e);
			throw new Exception("添加索引SolrParameter失败!",e);
		} finally {
			try {
				solrServer.optimize();
				solrServer.commit();
			} catch (Exception e) {
				log.error("添加索引SolrParameter失败，在提交更新索引处失败",e);
				throw new Exception("添加索引SolrParameter失败，在提交更新索引处失败",e);
			} 
		}
	}
	
	/**
	 * 多条件查询
	 * 
	 * @param fieldValues
	 * @param timeMap
	 * @param start
	 * @param count
	 * @param sortFields
	 * @param showFields
	 * @param flagAccurateSearch
	 * @return
	 * @throws SolrServerException
	 */
	public List<Item> getSolrParameters(Map<String, Object> fieldValues,Map<String, List<String>> timeMap, Integer start, Integer count,Map<String, Boolean> sortFields, List<String> showFields) throws Exception {
		QueryResponse response = SolrUtil.assemblyConditionsOfSelect(solrServer, fieldValues, timeMap, start, count, sortFields,showFields,quickSentry);
		if(response == null){
			return null;
		}
		List<Item> items = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items;
		}
		return null;
	}

	/**
	 * 根据id删除SolrParameter
	 * @param key
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public void deleteSolrParameter(String id) throws Exception{
		solrServer.deleteByQuery("id:"+id);
		solrServer.commit();
	}
	
	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

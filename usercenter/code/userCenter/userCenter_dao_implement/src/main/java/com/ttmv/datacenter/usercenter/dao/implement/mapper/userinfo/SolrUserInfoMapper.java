package com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseSolrCloud;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.SolrUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

public class SolrUserInfoMapper  {

	private final Logger log = LogManager.getLogger(SolrUserInfoMapper.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private final int DELAY_TIME = 10;
	private BaseSolrCloud solrServer ;
	private SentryAgent quickSentry;
	
	/**
	 * 添加索引
	 * 
	 * @param userInfo
	 * @throws Exception
	 * @throws IOException
	 */
	public void addSolrUserInfo(SolrUserInfo userInfo,String reqID) throws Exception {
		try {
			solrServer.addBean(userInfo);
			log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo失败！]",e);
		} finally {
			try {
				solrServer.optimize();
				log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo成功，执行optimize操作！]");
				solrServer.commit();
				log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo成功，执行commit操作！]");
			} catch (Exception e) {
				log.error("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo成功，执行optimize或commit操作出错！]",e);
				throw new Exception("[" + reqID + "]@@" + "[添加Solr的SolrUserInfo成功，执行optimize或commit操作出错！]",e);
			} 
		}
	}
	
	/**
	 * 添加索引 采用延迟提交的方式，延迟1秒中提交
	 * @param userInfo
	 * @param reqID
	 * @throws Exception
	 */
	public void addSolrUserInfoDelay(SolrUserInfo userInfo,String reqID) throws Exception{
		try {
			solrServer.addBean(userInfo,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserInfo成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserInfo失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserInfo失败！]",e);
		} 
	}
	
	/**
	 * 对solrUserInfo 列的值 原子性更改
	 * @param fieldName
	 * @param value
	 */
	public void updateSolrUserInfoField(String id,String fieldName,String value,String reqID)throws Exception{
		try{
			SolrInputDocument doc = new SolrInputDocument();
			//标示 需要修改的docment的id
			doc.addField(SolrConstant.SOLR_KEY_ID, id);
			Map map = new HashMap();
			map.put("set", value);
			doc.addField(fieldName, map);
			solrServer.add(doc,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[原子更新修改Solr的SolrUserInfo成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[修改Solr的SolrUserInfo失败]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[修改Solr的SolrUserInfo失败]",e);
		}
	}
	
	/**
	 * 分页查询结果
	 * 
	 * @param queryResponse
	 * @return
	 * @throws SolrServerException
	 */
	public List<Item> getItems(Map<String,Object> fieldValue, Map<String,List<String>> timeMap , Integer start, Integer count,Map<String,Boolean> sortFields,List<String> showFields) throws Exception {
		QueryResponse response = SolrUtil.assemblyConditionsOfSelect(solrServer,fieldValue,timeMap,start,count,sortFields,showFields,quickSentry);
		if(response == null){
			return null;
		}
		/* 组装最后的结果 */
		List<Item> Items = response.getBeans(Item.class);
		if (Items != null && Items.size() > 0) {
			return Items;
		}
		return null;
	}

	/**
	 * 精确条件查询，一条查询结果
	 * 返回userId和datasourcekey
	 * @param field
	 * @param key
	 * @param start
	 * @param count
	 * @return
	 * @throws SolrServerException 
	 */
	public Item getItem(String field,String key,
			int start, int count) throws Exception{
		/* 根据精确添加查询 */
		QueryResponse response = SolrUtil.selectByKeyWordReturnUserIdAndDataSourcekey(solrServer,field, key, start, count,quickSentry);
		List<Item> items  = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items.get(0);
		}
		return null;
	}
	
	/**
	 * 精确条件查询，一条查询结果
	 * @param field
	 * @param key
	 * @param start
	 * @param count
	 * @return
	 * @throws SolrServerException 
	 */
	public SolrUserInfo getSolrUserInfoByKey(String field,String key,
			int start, int count) throws Exception{
		 /* 根据精确添加查询 */ 
		QueryResponse response = SolrUtil.selectByKeyWord(solrServer,field, key,
				start, count,quickSentry);
		List<SolrUserInfo> solrUserInfos  = response.getBeans(SolrUserInfo.class);
		if(solrUserInfos != null && solrUserInfos.size() > 0){
			return solrUserInfos.get(0);
		}
		return null;
	}
	
	/**
	 * 通过ids查询items的集合
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Item> getItemsByIds(List<BigInteger> ids)throws Exception{
		/* 多ids查询*/
		QueryResponse response = SolrUtil.selectByIds(solrServer, ids,quickSentry);
		List<Item> items  = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items;
		}
		return null;
	}
	
	/**
	 * 把UserInfo转换成SolrUserInfo
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public SolrUserInfo getConvertToSolrUserInfo(UserInfo userInfo) throws Exception{
		SolrUserInfo solrUserInfo = new SolrUserInfo();
		BeanCopyProperties.copyProperties(userInfo, solrUserInfo, true,null);
		/* 转换mark用户 */
		solrUserInfo.setId(userInfo.getUserid().toString());
		return solrUserInfo;
	}
	
	/**
	 * 把要修改的值，放入就对象中
	 * @param userInfo
	 * @param solr
	 * @return
	 * @throws Exception
	 */
	public SolrUserInfo getConvertToSolrUserInfo(UserInfo userInfo,SolrUserInfo solr)throws Exception{
		BeanCopyProperties.copyProperties(userInfo, solr, true,null);
		/* 转换mark用户 */
		return solr;
	}
	
	/**
	 * solr服务器进行提交操作
	 */
	public void solrCommit()throws Exception{
		try {
			solrServer.optimize();
			log.debug("执行optimize操作成功！");
			solrServer.commit();
			log.debug("执行commit操作成功！");
		} catch (Exception e) {
			log.error("执行optimize或commit操作出错！",e);
			throw new Exception("执行optimize或commit操作出错！",e);
		} 
	}
	
	public void setSolrServer(BaseSolrCloud solrServer) {
		this.solrServer = solrServer;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

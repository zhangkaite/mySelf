package com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.SolrUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;

public class SolrUserCrossRelationMapper {
	private final Logger log = LogManager.getLogger(SolrUserCrossRelationMapper.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private final int DELAY_TIME = 1000;
	private SentryAgent quickSentry;
	private CloudSolrServer solrServer;
	/**
	 * 添加SolrUserCrossRelation
	 * 
	 * @param solrUserCrossRelation
	 */
	public void addSolrUserCrossRelation(SolrUserCrossRelation solrUserCrossRelation,String reqID)throws Exception {
		try {
			solrServer.addBean(solrUserCrossRelation);
			log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation失败！]",e);
		} finally {
			try {
				solrServer.optimize();
				log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation成功，执行optimize操作！]");
				solrServer.commit();
				log.debug("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation成功，执行commit操作！]");
			} catch (Exception e) {
				log.error("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation成功，执行optimize或commit操作出错！]",e);
				throw new Exception("[" + reqID + "]@@" + "[添加Solr的SolrUserCrossRelation成功，执行optimize或commit操作出错！]",e);
			} 
		}
	}

	/**
	 * 添加索引 采用延迟提交的方式，延迟1秒中提交
	 * @param userInfo
	 * @param reqID
	 * @throws Exception
	 */
	public void addSolrUserCrossRelationDelay(SolrUserCrossRelation crossrelation,String reqID) throws Exception{
		try {
			solrServer.addBean(crossrelation,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserCrossRelation成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserCrossRelation失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[添加Solr延迟提交SolrUserCrossRelation失败！]",e);
		} 
	}
	
	/**
	 * 对SolrUserCrossRelation 列的值 原子性更改
	 * @param fieldName
	 * @param value
	 */
	public void updateSolrUserCrossRelationField(String id,String fieldName,String value,String reqID)throws Exception{
		try{
			SolrInputDocument doc = new SolrInputDocument();
			//标示 需要修改的docment的id
			doc.addField(SolrConstant.SOLR_KEY_ID, id);
			Map map = new HashMap();
			map.put("set", value);
			doc.addField(fieldName, map);
			solrServer.add(doc,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[原子更新修改Solr的SolrUserCrossRelation成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[修改Solr的SolrUserCrossRelation失败]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[修改Solr的SolrUserCrossRelation失败]",e);
		}
	}
	
	/**
	 * 根据id删除SolrUserCrossRelation
	 * 
	 * @param id
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public void deleteSolrUserCrossRelation(String id,String reqID) throws Exception {
		try{
			solrServer.deleteById(id);
			log.debug("[" + reqID + "]@@" + "[删除SolrUserCrossRelation成功！]");
			solrServer.commit();			
			log.debug("[" + reqID + "]@@" + "[删除SolrUserCrossRelation成功，执行commit()！]");
		}catch(Exception e){
			log.debug("[" + reqID + "]@@" + "[删除SolrUserCrossRelation失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[删除SolrUserCrossRelation失败！]",e);
		}
	}

	
	/**
	 * 根据id查询SolrUserCrossRelation
	 * @return
	 */
	public SolrUserCrossRelation getUserCrossRelationByKey(String field,String value,int start ,int count)throws Exception{
		/* 根据精确添加查询 */
		QueryResponse response = SolrUtil.selectByKeyWord(solrServer,field, value, start, count,quickSentry);
		List<SolrUserCrossRelation> crosses = response.getBeans(SolrUserCrossRelation.class);
		if(crosses != null && crosses.size() > 0){
			return crosses.get(0);
		}
		return null;
	}
	
	
	/**
	 * 通过id查询item
	 * @param id
	 * @return
	 */
	public Item getItem(String field,String id,int start,int count) throws Exception{
		QueryResponse response = SolrUtil.selectByKeyWordReturnUserIdAndDataSourcekey(solrServer,field, id, start, count,quickSentry);
		log.debug("[return ReqID]@@" + "[查询solr的QueryResponse成功！]");
		List<Item> items  = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items.get(0);
		}
		return null;
	}
	
	/**
	 * 查询多个items
	 * @param fieldValue
	 * @param timeMap
	 * @param start
	 * @param count
	 * @param sortFields
	 * @param showFields
	 * @param flagAccurateSearch
	 * @return
	 * @throws SolrServerException
	 */
	public List<Item> getItems(Map<String,Object> fieldValue, Map<String,List<String>> timeMap , Integer start, Integer count,Map<String,Boolean> sortFields,List<String> showFields) throws Exception{
		QueryResponse response = SolrUtil.assemblyConditionsOfSelect(solrServer,fieldValue,timeMap,start,count,sortFields,showFields,quickSentry);
		log.debug("查询QueryResponse的items集合成功！");
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
	 * 查询多个id
	 * @param fieldValue
	 * @param timeMap
	 * @param start
	 * @param count
	 * @param sortFields
	 * @param showFields
	 * @param flagAccurateSearch
	 * @return
	 * @throws Exception 
	 */
	public List<BigInteger> getUserIdBs(Map<String,Object> fieldValue, Map<String,List<String>> timeMap , Integer start, Integer count,Map<String,Boolean> sortFields,List<String> showFields) throws Exception{
		QueryResponse response = SolrUtil.assemblyConditionsOfSelect(solrServer,fieldValue,timeMap,start,count,sortFields,showFields,quickSentry);
		if(response == null){
			return null;
		}
		/* 组装最后的结果 */
		List<BigInteger> list = new ArrayList<BigInteger>();
		List<Item> items = response.getBeans(Item.class);
		if (items != null && items.size() > 0) {
			for(Item item : items ){
				SolrUserCrossRelation solr = getUserCrossRelationByKey(SolrConstant.SOLR_KEY_ID,item.getId(),SolrConstant.SOLR_START,SolrConstant.SOLR_UNIQUE);
				list.add(new BigInteger(solr.getUserIdB()));
			}
			return list;
		}
		return null;
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
	
	public void setSolrServer(CloudSolrServer solrServer) {
		this.solrServer = solrServer;
	}
	
	/**
	 * 生成SolrUserCrossRelation对象从
	 * UserCrossRelation对象中复制值
	 * @return
	 * @throws Exception
	 */
	public SolrUserCrossRelation groupConvertToSolrUserCrossRelation(UserCrossRelation userCrossRelation)throws Exception{
		SolrUserCrossRelation solr = new SolrUserCrossRelation();
		BeanCopyProperties.copyProperties(userCrossRelation, solr, true, null);
		return solr;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

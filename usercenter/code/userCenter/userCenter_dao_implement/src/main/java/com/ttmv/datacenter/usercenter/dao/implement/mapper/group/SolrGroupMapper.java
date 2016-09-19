package com.ttmv.datacenter.usercenter.dao.implement.mapper.group;

import java.io.IOException;
import java.math.BigInteger;
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
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.SolrGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.domain.data.Group;

public class SolrGroupMapper {

	private final Logger log = LogManager.getLogger(SolrGroupMapper.class);
	private final int DELAY_TIME = 1000;
	private CloudSolrServer solrServer;
	private SentryAgent quickSentry;
	
	/**
	 * 添加SolrGroup对象
	 * @param group
	 */
	public void addSolrGroup(SolrGroup group,String reqID)throws Exception{
		try {
			solrServer.addBean(group);
			log.debug("[" + reqID + "]@@" + "[Solr添加SolrGroup成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[Solr添加SolrGroup失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[Solr添加SolrGroup失败！]",e);
		} finally {
			try {
				solrServer.optimize();
				log.debug("[" + reqID + "]@@" + "[修改Solr的SolrGroup成功，执行optimize操作！]");
				solrServer.commit();
				log.debug("[" + reqID + "]@@" + "[Solr添加SolrGroup成功，Solr执行commit成功！]");
			} catch (Exception e) {
				log.error("[" + reqID + "]@@" + "[Solr添加SolrGroup失败，Solr执行Commit失败！]",e);
				throw new Exception("[" + reqID + "]@@" + "[Solr添加SolrGroup失败，Solr执行Commit失败！]",e);
			}
		}
	}
	
	/**
	 * 添加索引 采用延迟提交的方式，延迟1秒中提交
	 * @param userInfo
	 * @param reqID
	 * @throws Exception
	 */
	public void addSolrGroupDelay(SolrGroup group,String reqID) throws Exception{
		try {
			solrServer.addBean(group,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[添加Solr延迟提交SolrGroup成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[添加Solr延迟提交SolrGroup失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[添加Solr延迟提交SolrGroup失败！]",e);
		} 
	}
	
	/**
	 * 对SolrGroup 列的值 原子性更改
	 * @param fieldName
	 * @param value
	 */
	public void updateSolrGroupField(String id,String fieldName,String value,String reqID)throws Exception{
		try{
			SolrInputDocument doc = new SolrInputDocument();
			//标示 需要修改的docment的id
			doc.addField(SolrConstant.SOLR_KEY_ID, id);
			Map map = new HashMap();
			map.put("set", value);
			doc.addField(fieldName, map);
			solrServer.add(doc,DELAY_TIME);
			log.debug("[" + reqID + "]@@" + "[原子更新修改Solr的SolrGroup成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[修改Solr的SolrGroup失败]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[修改Solr的SolrGroup失败]",e);
		}
	}
	
	/**
	 * 删除SolrGroup对象
	 * @param group
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public void deleteSolrGroup(String id,String reqID) throws Exception{
		try{		
			solrServer.deleteById(id);
			log.debug("[" + reqID + "]@@" + "[Solr删除SolrGroup成功！]");
			solrServer.commit();
			log.debug("[" + reqID + "]@@" + "[Solr删除SolrGroup成功,Solr执行Commit成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[Solr删除SolrGroup失败！失败的原因]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_SOLR_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[Solr删除SolrGroup失败！失败的原因]",e);
		}
	}
	
	/**
	 * 根据userid查询多个SolrGroup对象
	 * @return List<Item>
	 */
	public List<Item>  getSolrGroupsByUserId(String field,String userid,int start ,int count)throws Exception{
		/* 根据精确添加查询 */
		QueryResponse response = SolrUtil.selectByKeyWordReturnUserIdAndDataSourcekey(solrServer,field, userid, start, count,quickSentry);
		List<Item> items  = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items;
		}
		return null;
	}
	
	/**
	 * 根据id查询SolrGroup
	 * @return
	 */
	public SolrGroup getSolrGroupByKey(String field,String value,int start ,int count)throws Exception{
		/* 根据精确添加查询 */
		QueryResponse response = SolrUtil.selectByKeyWord(solrServer,field, value, start, count,quickSentry);
		List<SolrGroup> groups = response.getBeans(SolrGroup.class);
		if(groups != null && groups.size() > 0){
			return groups.get(0);
		}
		return null;
	}
	
	/**
	 * 根据id查询Item
	 * @return
	 */
	public Item getItemByKey(String field,String value,int start ,int count)throws Exception{
		/* 根据精确添加查询 */
		QueryResponse response = SolrUtil.selectByKeyWordReturnUserIdAndDataSourcekey(solrServer,field, value, start, count,quickSentry);
		List<Item> items  = response.getBeans(Item.class);
		if(items != null && items.size() > 0){
			return items.get(0);
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
	 * 通过ids查询items的集合
	 * @param ids
	 * @return
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
	 * 模糊分页查询结果
	 * 
	 * @param queryResponse
	 * @return
	 * @throws SolrServerException
	 */
	public List<Item> getItems(Map<String,Object> fieldValue,Integer start, Integer count,Map<String,Boolean> sortFields,List<String> showFields) throws Exception {
		QueryResponse response = SolrUtil.assemblyConditionsOfSelect(solrServer,fieldValue,null,start,count,sortFields,showFields,quickSentry);
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
	 * 转换Group对象转换为SolrGroup对象
	 * @param group
	 * @return
	 */
	public SolrGroup groupConvertToSolrGroup(Group group)throws Exception{
		SolrGroup solr = new SolrGroup();
		BeanCopyProperties.copyProperties(group, solr, true,null);
		solr.setId(String.valueOf(group.getGroupId()));
		return solr;
	}
	
	/**
	 * 修改：转换Group对象中的值到SolrGroup中
	 * @param group
	 * @param solr
	 * @return
	 */
	public SolrGroup groupConvertToSolrGroup(Group group,SolrGroup solr)throws Exception{
		BeanCopyProperties.copyProperties(group, solr,true , null);
		solr.setId(group.getGroupId().toString());
		return solr;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}

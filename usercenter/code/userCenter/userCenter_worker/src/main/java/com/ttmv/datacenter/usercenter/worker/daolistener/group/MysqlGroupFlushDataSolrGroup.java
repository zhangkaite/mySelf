package com.ttmv.datacenter.usercenter.worker.daolistener.group;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.MysqlGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.SolrGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.MysqlGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.SolrGroupMapper;

/**
 * 将mysql中用户组的数据回刷到Solr中
 * @author wll
 */
public class MysqlGroupFlushDataSolrGroup {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(MysqlGroupFlushDataSolrGroup.class);

	private MysqlGroupMapper mysqlGroupMapper;
	private SolrGroupMapper solrGroupMapper;
	
	/**
	 * 执行数据的会刷
	 * @throws Exception 
	 */
	public void executeFlush() throws Exception{
		String key_m1 = "uc_mysql_m1";
		String key_m2 = "uc_mysql_m2";
		/* 查询m1库中的数据 */
		List<MysqlGroup> list1 = mysqlGroupMapper.getAllMysqlGroup(key_m1);
		log.debug("1.查询m1数据成功！总数：" + list1.size());
		/* 查询m2库中的数据 */
		List<MysqlGroup> list2 = mysqlGroupMapper.getAllMysqlGroup(key_m2);
		log.debug("2.查询m2数据成功！总数：" + list2.size());
		
		if(list1.size() > 0){
			log.debug("开始回刷uc_mysql_m1");
			for(int i=0;i<list1.size();i++){
				MysqlGroup group = list1.get(i);
				SolrGroup solr = new SolrGroup();
				solr.setId(group.getId().toString());
				solr.setDataSourceKey(key_m1);
				solr.setUserId(group.getUserId().toString());
				solr.setDefaultType(group.getDefaultType());
				solrGroupMapper.addSolrGroupDelay(solr, "手工刷新mysqlGroup到solrGroup中,Id是："+group.getId().toString());
				log.debug("手工刷新mysqlGroup到solrGroup成功，ID是："+group.getId().toString());
			}
		}
		
		if(list2.size() > 0){
			log.debug("开始回刷uc_mysql_m2");
			for(int i=0;i<list2.size();i++){
				MysqlGroup group = list2.get(i);
				SolrGroup solr = new SolrGroup();
				solr.setId(group.getId().toString());
				solr.setDataSourceKey(key_m2);
				solr.setUserId(group.getUserId().toString());
				solr.setDefaultType(group.getDefaultType());
				solrGroupMapper.addSolrGroupDelay(solr, "手工刷新mysqlGroup到solrGroup中,Id是："+group.getId().toString());
				log.debug("手工刷新mysqlGroup到solrGroup成功，ID是："+group.getId().toString());
			}
		}
	}
	
	public void setMysqlGroupMapper(MysqlGroupMapper mysqlGroupMapper) {
		this.mysqlGroupMapper = mysqlGroupMapper;
	}
	public void setSolrGroupMapper(SolrGroupMapper solrGroupMapper) {
		this.solrGroupMapper = solrGroupMapper;
	}
}

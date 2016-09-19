package com.ttmv.datacenter.usercenter.worker.daolistener.crossrelation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.MysqlUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.SolrUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.MysqlUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.SolrUserCrossRelationMapper;

/**
 * 将mysql中用户组的数据回刷到Solr中
 * 
 * @author wll
 */
public class MysqlCrossRelationFlushDataSolrCrossRelation {

	/* 日志输出类 */
	private final Logger log = LogManager
			.getLogger(MysqlCrossRelationFlushDataSolrCrossRelation.class);

	private MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper;
	private SolrUserCrossRelationMapper solrUserCrossRelationMapper;

	/**
	 * 执行数据的会刷
	 * 
	 * @throws Exception
	 */
	public void executeFlush() throws Exception {
		String key_m1 = "uc_mysql_m1";
		String key_m2 = "uc_mysql_m2";
		/* 查询m1库中的数据 */
		List<MysqlUserCrossRelation> list1 = mysqlUserCrossRelationMapper
				.getAllMysqlUserCrossRelation(key_m1);
		log.debug("1.查询m1数据成功！总数：" + list1.size());
		/* 查询m2库中的数据 */
		List<MysqlUserCrossRelation> list2 = mysqlUserCrossRelationMapper
				.getAllMysqlUserCrossRelation(key_m2);
		log.debug("2.查询m2数据成功！总数：" + list2.size());

		if(list1.size() > 0){
			log.debug("开始回刷uc_mysql_m1");
			for(int i=0;i<list1.size();i++){
				MysqlUserCrossRelation mysql = list1.get(i);
				SolrUserCrossRelation solr = new SolrUserCrossRelation();
				solr.setId(mysql.getId().toString());
				solr.setDataSourceKey(key_m1);
				solr.setGroupId(mysql.getGroupId().toString());
				solr.setUserIdA(mysql.getUserIdA().toString());
				solr.setUserIdB(mysql.getUserIdB().toString());
				solr.setType(mysql.getType());
				solrUserCrossRelationMapper.addSolrUserCrossRelationDelay(solr, "手工刷新MysqlUserCrossRelation到SolrUserCrossRelation中,Id是："+mysql.getId().toString());
				log.debug("手工刷新MysqlUserCrossRelation到SolrUserCrossRelation成功，ID是："+mysql.getId().toString());
			}
		}
		
		if(list2.size() > 0){
			log.debug("开始回刷uc_mysql_m2");
			for(int i=0;i<list2.size();i++){
				MysqlUserCrossRelation mysql = list2.get(i);
				SolrUserCrossRelation solr = new SolrUserCrossRelation();
				solr.setId(mysql.getId().toString());
				solr.setDataSourceKey(key_m1);
				solr.setGroupId(mysql.getGroupId().toString());
				solr.setUserIdA(mysql.getUserIdA().toString());
				solr.setUserIdB(mysql.getUserIdB().toString());
				solr.setType(mysql.getType());
				solrUserCrossRelationMapper.addSolrUserCrossRelationDelay(solr, "手工刷新MysqlUserCrossRelation到SolrUserCrossRelation中,Id是："+mysql.getId().toString());
				log.debug("手工刷新MysqlUserCrossRelation到SolrUserCrossRelation成功，ID是："+mysql.getId().toString());
			}
		}
	}

	public void setMysqlUserCrossRelationMapper(
			MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper) {
		this.mysqlUserCrossRelationMapper = mysqlUserCrossRelationMapper;
	}

	public void setSolrUserCrossRelationMapper(
			SolrUserCrossRelationMapper solrUserCrossRelationMapper) {
		this.solrUserCrossRelationMapper = solrUserCrossRelationMapper;
	}
}

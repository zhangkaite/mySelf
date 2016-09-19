package com.ttmv.datacenter.usercenter.worker.daolistener.userinfo;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.MysqlUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.SolrUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.HbaseUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.MysqlUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.SolrUserInfoMapper;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

/**
 * 将mysql中用户的数据回刷到Solr中
 * @author wll
 */
public class MysqlUserInfoFlushDataSolrUserInfo {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(MysqlUserInfoFlushDataSolrUserInfo.class);
	
	private MysqlUserInfoMapper mysqlUserInfoMapper;
	private HbaseUserInfoMapper hbaseUserInfoMapper;
	private SolrUserInfoMapper solrUserInfoMapper;
	/**
	 * 执行数据的会刷
	 * @throws Exception 
	 */
	public void executeFlush() throws Exception{
		String key_m1 = "uc_mysql_m1";
		String key_m2 = "uc_mysql_m2";
		/* 查询m1库中的数据 */
		List<MysqlUserInfo> list1 = mysqlUserInfoMapper.getAllMysqlUserInfo(key_m1);
		log.debug("1.查询m1数据成功！总数：" + list1.size());
		/* 查询m2库中的数据 */
		List<MysqlUserInfo> list2 = mysqlUserInfoMapper.getAllMysqlUserInfo(key_m2);
		log.debug("2.查询m2数据成功！总数：" + list2.size());
		if(list1.size() > 0){
			log.debug("开始回刷uc_mysql_m1");
			for(int i=0;i<list1.size();i++){
				MysqlUserInfo mysql =list1.get(i);
				HbaseUserInfo hbase = hbaseUserInfoMapper.getHbaseUserInfoById(mysql.getUserId().toString());
				if(hbase == null){
					log.debug("hbaseUserInfo不存在！");
					continue;
				}
				log.debug("3.查询hbase成功！id是："+mysql.getUserId());
				UserInfo userInfo = new UserInfo();
				mysqlUserInfoMapper.getConvertMysqlUserInfoToUserInfo(userInfo, mysql);
				log.debug("4.mysqlUserInfo转化成UserInfo成功！");
				hbaseUserInfoMapper.getConvertHbaseUserInfoToUserInfo(userInfo, hbase);
				log.debug("5.HbaseUserInfo转化成UserInfo成功！");
				SolrUserInfo solr = solrUserInfoMapper.getConvertToSolrUserInfo(userInfo);
				solr.setDataSourceKey(key_m1);
				log.debug("6.UserInfo转化成SolrUserInfo成功！");
				solrUserInfoMapper.addSolrUserInfoDelay(solr, "手工刷新mysqlUserInfo到solrUserInfo中ID是：" + mysql.getUserId().toString());
				log.debug("7.SolrUserInfo添加成功！");
			}
		}
		if(list2.size() > 0){
			log.debug("开始回刷uc_mysql_m2");
			for(int y=0;y<list2.size();y++){
				MysqlUserInfo mysql =list2.get(y);
				HbaseUserInfo hbase = hbaseUserInfoMapper.getHbaseUserInfoById(mysql.getUserId().toString());
				if(hbase == null){
					log.debug("hbaseUserInfo不存在！");
					continue;
				}
				log.debug("3.查询hbase成功！id是："+mysql.getUserId());
				UserInfo userInfo = new UserInfo();
				mysqlUserInfoMapper.getConvertMysqlUserInfoToUserInfo(userInfo, mysql);
				log.debug("4.mysqlUserInfo转化成UserInfo成功！");
				hbaseUserInfoMapper.getConvertHbaseUserInfoToUserInfo(userInfo, hbase);
				log.debug("5.HbaseUserInfo转化成UserInfo成功！");
				SolrUserInfo solr = solrUserInfoMapper.getConvertToSolrUserInfo(userInfo);
				solr.setDataSourceKey(key_m2);
				log.debug("6.UserInfo转化成SolrUserInfo成功！");
				solrUserInfoMapper.addSolrUserInfoDelay(solr, "手工刷新mysql到solr中ID："+y);
				log.debug("7.SolrUserInfo添加成功！");
			}
		}
	}
	
	public void setMysqlUserInfoMapper(MysqlUserInfoMapper mysqlUserInfoMapper) {
		this.mysqlUserInfoMapper = mysqlUserInfoMapper;
	}
	public void setHbaseUserInfoMapper(HbaseUserInfoMapper hbaseUserInfoMapper) {
		this.hbaseUserInfoMapper = hbaseUserInfoMapper;
	}
	public void setSolrUserInfoMapper(SolrUserInfoMapper solrUserInfoMapper) {
		this.solrUserInfoMapper = solrUserInfoMapper;
	}
}

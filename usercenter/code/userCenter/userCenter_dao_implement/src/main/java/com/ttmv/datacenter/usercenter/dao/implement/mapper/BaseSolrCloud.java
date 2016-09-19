package com.ttmv.datacenter.usercenter.dao.implement.mapper;

import org.apache.solr.client.solrj.impl.CloudSolrServer;

public class BaseSolrCloud extends CloudSolrServer{

	public BaseSolrCloud(String zkHost,String collection) {
		super(zkHost);
		super.setDefaultCollection(collection);
	}
}

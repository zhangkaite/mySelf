package com.ttmv.datacenter.usercenter.dao.implement.solr;

import java.util.Date;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.SolrUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.SolrUserInfoMapper;

public class TestSolrUserInfoMapper {

	private static ApplicationContext context = null;

	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
	}

	@Test
	public void addSolrUserInfoMapper() {
		SolrUserInfoMapper mapper = (SolrUserInfoMapper) context
				.getBean("solrUserInfoMapper");
		SolrUserInfo solr = new SolrUserInfo();
		solr.setAdminId("1234578");
		solr.setBindingMobile("1234578");
		solr.setDataSourceKey("uc_master");
		solr.setLoginMobile("12345678");
		solr.setNickName("wulinli");
		solr.setSex(1);
		solr.setTime(new Date());
		solr.setTTnum("456789152");
		solr.setId("77777777");
		solr.setUserName("wulinli");

		try {
			mapper.addSolrUserInfo(solr, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchSolrUserInfoMapper() {
		SolrUserInfoMapper mapper = (SolrUserInfoMapper) context
				.getBean("solrUserInfoMapper");
		SolrUserInfo solr = new SolrUserInfo();

		try {
			mapper.addSolrUserInfo(solr,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteFromUserInfo() throws Exception {
		SolrServer server = new HttpSolrServer("http://uc_solr:50020/solr");
		server.deleteByQuery("*:*");
		server.commit();
	}
	/*
	 * @Test public void testTime(){ SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); try {
	 * System.out.println(System.currentTimeMillis()); } catch(Exception e) {
	 * e.printStackTrace(); } }
	 */

}

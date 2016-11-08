package com.ttmv.datacenter.agent.redis.jedis.impl;

import com.ttmv.datacenter.agent.redis.jedis.JedisAbstract;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;
import com.ttmv.datacenter.agent.redis.tool.ModuleInitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

public class JedisAgent extends JedisAbstract {

    private Logger logger = LogManager.getLogger(JedisAgent.class);

	public JedisAgent(String host, int port) {
		if (CheckParameterUtil.checkIsEmpty(host) || port == 0) {
			ModuleInitUtil.failInitBySet("JedisAgent", new String[]{"host", "port"});
		}
		this.host = host;
		this.port = port;
	}

	/**
	 * 获取连接
	 * @return
	 */
	protected Jedis getJedis() throws Exception {
		Jedis jedis = new Jedis(host, port);
		jedis.connect();
		if (!jedis.isConnected()) {
			throw new Exception("redis:[" + host+":"+ port + "]连接失败!");
		}
		return jedis;
	}

	/**
	 * 关闭数据库连接
	 * @param jedis
	 */
	@Override
	protected void returnResource(Jedis jedis) {
		if(jedis != null && jedis.isConnected()){
			jedis.close();
		}
	}

}

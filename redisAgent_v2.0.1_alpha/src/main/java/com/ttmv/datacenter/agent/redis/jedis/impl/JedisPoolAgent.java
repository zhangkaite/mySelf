package com.ttmv.datacenter.agent.redis.jedis.impl;

import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.jedis.JedisAbstract;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;
import com.ttmv.datacenter.agent.redis.tool.ModuleInitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * Created by zbs on 15/11/11.
 */
public class JedisPoolAgent extends JedisAbstract {

    private Logger logger = LogManager.getLogger(JedisPoolAgent.class);

    private JedisPool pool = null;

    public JedisPoolAgent(String host, int port, RedisPoolConfig poolConfig, int timeout) {
        if (CheckParameterUtil.checkIsEmpty(host) || port == 0 || poolConfig == null || timeout==0) {
            ModuleInitUtil.failInitBySet("JedisPoolAgent", new String[]{"host", "port", "poolConfig", "timeout"});
        }
        this.host = host;
        this.port = port;
        JedisPoolConfig config = poolConfig.getRedisPoolConfig();
        pool = new JedisPool(config, host, port, timeout);
    }

    /**
     * 从数据库链接池中获取链接
     * @return
     */
    protected Jedis getJedis() throws Exception {
        return pool.getResource();
    }

    /**
     * 返还数据库连接池链接
     * @param jedis
     */
    @Override
    protected void returnResource(Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
            //jedis.close();
        }
    }

}

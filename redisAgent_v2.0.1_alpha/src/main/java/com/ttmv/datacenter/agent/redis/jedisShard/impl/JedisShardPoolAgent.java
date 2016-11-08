package com.ttmv.datacenter.agent.redis.jedisShard.impl;

import com.ttmv.datacenter.agent.redis.jedisShard.JedisShardAbstract;
import com.ttmv.datacenter.agent.redis.tool.ModuleInitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import java.util.List;

/**
 * Created by zbs on 15/11/12.
 */
public class JedisShardPoolAgent extends JedisShardAbstract{

    private Logger logger = LogManager.getLogger(JedisShardPoolAgent.class);

    private ShardedJedisPool pool = null;

    public JedisShardPoolAgent(List<JedisShardInfo> shards,JedisPoolConfig config){
        if (shards == null || config == null) {
            ModuleInitUtil.failInitBySet("JedisAgent", new String[]{"shards", "config"});
        }
        pool = new ShardedJedisPool(config,shards);
    }

    @Override
    protected void returnResource(ShardedJedis shardedJedis) {
        if (shardedJedis != null) {
            pool.returnResource(shardedJedis);
        }
    }

    @Override
    protected ShardedJedis getShardeJedis() throws Exception {
        return pool.getResource();
    }
}

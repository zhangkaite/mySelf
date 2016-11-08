package com.ttmv.datacenter.generator.GUID;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.generator.GUID.lua.Script;
import com.ttmv.datacenter.utils.check.CheckParameterUtil;
import com.ttmv.datacenter.utils.check.ModuleInitUtil;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年3月23日
 */
public class GUIDGenerator32 implements GUIDGenerator {
	private Logger logger = LogManager.getLogger(GUIDGenerator32.class);
    private RedisAgent redisAgent;

    public GUIDGenerator32(RedisAgent redisAgent){
    	if(redisAgent == null){
    		ModuleInitUtil.failInitBySet(GUIDGenerator32.class.toString(), new String[]{"redisAgent"});
    	}
        this.redisAgent = redisAgent;
    }

    public Long guid(String generateGroupName) throws Exception{
        if(CheckParameterUtil.checkIsEmpty(generateGroupName) || null == redisAgent){
            logger.fatal("GUIDGenerator 初始化失败.");
            throw new Exception("GUIDGenerator 初始化失败");
        }
        try{
        	List<String> keys = new ArrayList<String>();
            keys.add(generateGroupName); 
            Object o = redisAgent.evalLua(Script.UUID_COUNT32, keys, null);
        	Long uuid;
            if(o instanceof Long){
            	uuid = (Long) o;
            }else{
            	throw new Exception("生成uuid，调用lua脚本返回的类型错误.");
            }
            logger.info("通过kye : ["+generateGroupName+"] 创建的 uuid : "+uuid);
            if(uuid == -1){
            	throw new Exception("生成的number超过最大数.");
            }
            return uuid;
        }catch(Exception e){
            logger.error("生成 GUID 失败.", e);
            throw new Exception("生成 GUID 失败.", e);
        }
    }
}

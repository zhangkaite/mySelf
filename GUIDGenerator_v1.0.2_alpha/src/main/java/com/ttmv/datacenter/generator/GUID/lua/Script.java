package com.ttmv.datacenter.generator.GUID.lua;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年2月12日
 */
public class Script {

   private final static int uuid_liveTime = 60 * 60;  //秒为单位
   
   //毫秒级别的uuid，每个毫秒时间支持1000个.如果每毫秒超过1000，返回-1
   public final static String  UUID_COUNT64 = ""
   		+ " local count=0 "
   		+ " local count = redis.call('get', KEYS[1]); "
   		+ " if not count then "
   		+ "     redis.call('set', KEYS[1], 0); "
   		+ "     redis.call('expire',KEYS[1],"+uuid_liveTime+")"
   		+ "     count=0 "
   		+ " end  "
   		+ " if 1000 <= tonumber(count) then "
   		+ "     return tonumber(-1)   "
   		+ " end   "
   		+ " redis.call('incr', KEYS[1])   "
   		+ " local count = redis.call('get',KEYS[1])   "
   		+ " return tonumber(count)";
   
   //仅仅自增，没其他用途
   public final static String  UUID_COUNT32 = ""
   		+ " local count=0 "
   		+ " local count = redis.call('get', KEYS[1]); "
   		+ " if not count then "
   		+ "     redis.call('set', KEYS[1], 0); "
   		+ "     count=0 "
   		+ " end  "
   		+ " if 2147483648 <= tonumber(count) then "
   		+ "     return tonumber(-1)   "
   		+ " end   "
   		+ " redis.call('incr', KEYS[1])   "
   		+ " local count = redis.call('get',KEYS[1])   "
   		+ " return tonumber(count)";

}


package com.ttmv.datacenter.agent.lockcenter.redis.lua;

public class Script {

	// 永久锁--次数
	public final static String LOCK_UNTIL_FE_COUNT = ""
			+ " local count = redis.call('get',KEYS[1]); "
			+ " if not count then " 
			+ "    redis.call('set',KEYS[1],1);  "
			+ "    return 1 "
			+ " end  "
			+ " if tonumber(count) < tonumber(ARGV[1]) then  "
			+ "    redis.call('incr',KEYS[1]);  "
			+ "    return 1; "
			+ " end "
			+  "return 0 ";
	// 释放一次锁
	public final static String RELEASE_ONE_FE =  ""
	        + " local count = redis.call('get',KEYS[1]); "
			+ " if not count then "
			+ "    return 0 "
			+ " end  "
			+ " if 1 < tonumber(count) then "
			+ "    redis.call('decr',KEYS[1]); "
			+ "    return 1; "
			+ " end "
			+ " if 1 == tonumber(count) then "
			+ "    redis.call('del',KEYS[1]); "
			+ "    return 1; "
			+ " end "
			+ " return 0";
	// 永久锁
	public final static String LOCK_UNTIL_FE = ""
	        + " local count = redis.call('get',KEYS[1]); "
	        + " if not count then "
	        + "    redis.call('set',KEYS[1],1);  "
	        + "    return 1 "
	        + " end "
	        + " return 0 ";
	// 永久锁解锁
	public final static String UN_LOCK_UNTIL_FE = ""
	        + " local count = redis.call('get',KEYS[1]); "
	        + " if not count then    "
	        + "    return 1 "
	        + " end "
	        + " redis.call('del',KEYS[1]);  "
	        + " return 1 ";
	// 临时锁
	public final static String LOCK_UNTIL = ""
	        + " local count = redis.call('get',KEYS[1]); "
	        + " if not count then  "
	        + "    redis.call('set',KEYS[1],1); "
	        + "    redis.call('expire',KEYS[1],ARGV[1]); "
	        + "    return 1 "
	        + " end "
	        + " return 0 ";
	// 临时锁解锁
	public final static String UN_LOCK_UNTIL = ""
	        + " local count = redis.call('get',KEYS[1]); "
	        + " if not count then    "
	        + "     return 1 "
	        + " end "
	        + " redis.call('del',KEYS[1]);  "
	        + " return 1";

}

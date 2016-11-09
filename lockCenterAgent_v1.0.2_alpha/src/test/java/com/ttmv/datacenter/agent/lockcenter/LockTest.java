package com.ttmv.datacenter.agent.lockcenter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.jedis.impl.JedisPoolAgent;

public class LockTest{
    
	private String redisHost = "dc_redis_lock_m1";
	private int redisPost = 50000;
	private String hbaseHost = "test_dc_hbase_lock";
	private String hbasePost = "50040";
	private Locker locker ;
	private boolean lock;
	
	@Before
	public void before(){
		RedisPoolConfig config = new RedisPoolConfig(108,20,60000);
		RedisAgent redis = new JedisPoolAgent(redisHost,redisPost,config,30000);
		locker = new LockCenterAgent(redis,hbaseHost,hbasePost);
	}
	
    /**
     * 测试永久锁
     **/
    @Test
    public void testLockUniqueFE(){
    	String key = "FE_LOCK_TEST_zs111";
    	lock = locker.lockUniqueFE(key); //加锁成功
    	assertFalse(!lock); 
    	System.out.println("加锁"+lock);
    	lock = locker.lockUniqueFE(key); //加锁失败
    	assertFalse(lock);
    	System.out.println("重复加锁"+lock);
    	lock = locker.unlockUniqueFE(key); //解锁成功
    	assertFalse(!lock);
    	System.out.println("解锁"+lock);
    	System.out.print("ok");
    }
    /**
     * 测试永久锁——按次数
     **/
    @Test
    public void testLockUniqueFECount(){
    	String key = "NUM_LOCK_TEST";
    	int count = 5;
    	for(int i =0;i < count;i++){
    	    lock = locker.lockUntilFE(key,5);
    	    assertFalse(!lock);
    	}
    	lock = locker.lockUntilFE(key,5);
 	    assertFalse(lock);
 	    for(int i =0;i < count;i++){
   	     lock = locker.releaseOneFE(key);
   	     assertFalse(!lock);
   	    }
    }
    
    @Test
    public void testReq(){
    	String key = "NUM_LOCK_TEST";
    	lock = locker.releaseOneFE(key);
        System.out.println(lock);
    }
    /**
     * 测试临时锁
     **/
    @Test
    public void testLockUntil(){
    	String key = "LOCK_TEST";
    	lock = locker.lockUnique(key);
    	assertFalse(!lock);
    	lock = locker.lockUnique(key);
    	assertFalse(lock);
    	try {
			Thread.sleep(11);
			locker.unlockUnique(key);
		} catch (InterruptedException e) {
            fail(e.getMessage());
		}
    }

}

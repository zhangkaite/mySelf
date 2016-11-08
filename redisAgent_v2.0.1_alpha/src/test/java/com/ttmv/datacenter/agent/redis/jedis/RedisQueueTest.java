package com.ttmv.datacenter.agent.redis.jedis;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.RedisQueue;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.agent.redis.jedis.impl.JedisPoolAgent;
import com.ttmv.datacenter.agent.redis.jedisShard.impl.JedisShardPoolAgent;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by zbs on 15/11/18.
 */
public class RedisQueueTest {

//    @Test
//    public void redisQueueTest() throws Exception {
//        RedisPoolConfig config = new RedisPoolConfig(108,20,60000);
//        RedisQueue redisQueue = new RedisQueueImpi("192.168.13.153",50042,config,30000);
//        for(int i=0;i<100;i++) {
//            redisQueue.setValue("temp", "{'aa':"+i+",'bb',"+i+"}");
//        }
////        System.out.println(redisQueue.size("center2php_l_expire"));
////        System.out.println(redisQueue.searchtValueByRange("center2php_l_expire", 0, 50).toString());
////        System.out.println(redisQueue.getValue("center2php_l_expire"));
//       System.out.println(redisQueue.getValueBatch("temp",330));
//
//       //List<String> list = redisQueue.searchtValueByRange("temp",0,10);
//       // redisQueue.delValue("temp", list.toString());
//    }


    @Test
    public void redis() throws Exception {
        RedisPoolConfig config = new RedisPoolConfig(20, 20, 50);
        RedisQueue redisQueue = new RedisQueueImpi("192.168.13.153", 50042, config, 30000);
        Random random = new Random();
//        for(int i=0;i<500;i++){
//            redisQueue.setValue("testQueue20160127",String.valueOf(random.nextInt()));
//        }
        long startTime = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            redisQueue.getValue("testQueue20160127");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("第一次取单个测试耗时"+String.valueOf(endTime-startTime));
        startTime = System.currentTimeMillis();
        redisQueue.getValueBatch("testQueue20160127", 1000);
        endTime = System.currentTimeMillis();
        System.out.println("第二次取多个测试耗时"+String.valueOf(endTime-startTime));
    }


    public static void main(String[] arge) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RedisPoolConfig config = new RedisPoolConfig(20, 20, 50);
                    RedisAgent redis = new JedisPoolAgent("192.168.13.153", 50042, config, 30000);
                    int i = 10;
                    Thread.sleep(10);
                    while (1 == 1) {
                        List<SetCollectionBean> list = new ArrayList<SetCollectionBean>();
                        list.add(new SetCollectionBean("test", String.valueOf(i+100), 1));
                        redis.zsetPipAdd(list);
                        System.out.println("set data " + i);
                        i++;
                        Thread.sleep(20);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        );
        Thread thread2 = new Thread(new Runnable() {
            RedisPoolConfig config = new RedisPoolConfig(50, 20, 5);
            RedisAgent redis = new JedisPoolAgent("192.168.13.153", 50042, config, 30000);
            @Override
            public void run() {
                try {
                    while (1 == 1) {
                        Set set = redis.zsetGetAll("test");
                        System.out.println(set.size());
                        Thread.sleep(30);
                    }
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        });

        thread1.start();
        thread2.start();

//        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.13.153", 50042, 200);
//        int i = 0;
//        while(1==1) {
//            Jedis jedis = pool.getResource();
//            jedis.zadd("test",1,String.valueOf(i));
//            System.out.println(i);
//            i++;
//            pool.returnResource(jedis);
//        }
    }
}

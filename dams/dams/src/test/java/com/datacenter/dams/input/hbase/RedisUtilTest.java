package com.datacenter.dams.input.hbase;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Tuple;

public class RedisUtilTest {

	@Test
	public void test() {
		try {
			RedisUtil redisUtil=new RedisUtil("192.168.13.173", 51313, 5, 10000, 10000);
			Set<Tuple> set=redisUtil.getZset(ActivityTagConstant.ACTIVITYTAGREDISNAME+"13", 0, -1);
			for (Tuple cTuple : set) {
				System.out.println("startID:"+cTuple.getElement()+"rank:"+cTuple.getScore());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.datacenter.test.hbase;

import org.junit.Assert;
import org.junit.Test;

public class StarInfoActivityServiceTest {

	@Test
	public void test()throws Exception{
		try{
			Assert.assertEquals("不相等呀", 2, 10/0);			
		}catch(Exception e){
			Assert.fail("Junit测试出错!");
		}
	}
	
	@Test
	public void testHbaseConnection(){
		
	}
}

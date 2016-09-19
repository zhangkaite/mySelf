package com.ttmv.datacenter.usercenter.dao.implement.test;

import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;

public class client {

	public static void main(String[] args) throws Exception {
		One one = new One();
		one.setId("123456");
		
		OneTemp temp = new OneTemp();
		temp.setId("654321");
		temp.setName("吴林立");
		
//		BeanCopyProperties.copyProperties(one, temp);
		System.out.println(temp.getName());
	}

}

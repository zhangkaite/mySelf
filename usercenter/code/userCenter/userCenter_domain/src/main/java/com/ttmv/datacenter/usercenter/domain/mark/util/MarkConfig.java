package com.ttmv.datacenter.usercenter.domain.mark.util;

public abstract class MarkConfig {
     //------------ -- 用户基本信息  -------------------------------------
	//------------------  位置 ---------------------------------------------
	protected static int[] state = new int[]{0,2};  // 用户状态的位置  
	protected static int[] vipType = new int[]{3,3}; //用户是否是会员
	
	//------------------  值 ---------------------------------------------
	protected static byte[] statie_normal = new byte[]{0,0,0}; //用户状态正常
	protected static byte[] static_freeze = new byte[]{0,0,1}; //用户状态冻结
	protected static byte[] static_delete = new byte[]{1,1,1}; //用户状态删除
	
	
	protected static byte[] vipType_false = new byte[]{0}; //非会员
	protected static byte[] vipType_true = new byte[]{1};//会员
	

}

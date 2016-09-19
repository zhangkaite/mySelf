package com.ttmv.datacenter.usercenter.dao.implement.util;

import com.ttmv.datacenter.usercenter.dao.implement.constant.UserInfoConstant;
import com.ttmv.datacenter.usercenter.domain.mark.UserBaseMark;

public class UserInfoMarkUtil {
	
	/**
	 * 用户状态--转换值为对外提供的值
	 * @param mark
	 * @return
	 */
	public static Integer convertStatusToOut(byte[] mark){
		if(mark == null){
			return null;
		}
		Integer value  = UserBaseMark.getUserState(mark);
		return value;
	}
	
	/**
	 * 转化值对外提供的值
	 * @param mark
	 * @return
	 */
	public static Integer convertVipToOut(byte[] mark){
		if(mark == null){
			return null;
		}
		Integer value  = UserBaseMark.getVipType(mark);
		return value;
	}
	
	/**
	 * 用户vip--转化值为数据库mark
	 * @param value
	 * @return
	 */
	public static byte[] convertVipToInner(byte[] mark ,Integer value){
		if(value == null){
			return null;
		}
		if(value == UserInfoConstant.OUT_VIPTYPE_NO){
			mark = UserBaseMark.notVipType(mark);
			return mark;
		}else if(value == UserInfoConstant.OUT_VIPTYPE_YES){
			mark = UserBaseMark.vipType(mark);
			return mark;
		}
		return null;
	}
	
	/**
	 * 用户状态--转化值为数据库mark
	 * @param value
	 * @return
	 */
	public static byte[] convertStatusToInner(byte[] mark , Integer value){
		if(value == null){
			return null;
		}
		if(value == UserInfoConstant.OUT_STATUS_DELETE){
			mark = UserBaseMark.userDelete(mark);
			return mark;
		}else if(value == UserInfoConstant.OUT_STATUS_NORMAL){
			mark = UserBaseMark.userNormal(mark);
			return mark;
		}else if(value == UserInfoConstant.OUT_STATUS_FREEZE){
			mark = UserBaseMark.userFreeze(mark);
			return mark;
		}	
		return null;
	}
}

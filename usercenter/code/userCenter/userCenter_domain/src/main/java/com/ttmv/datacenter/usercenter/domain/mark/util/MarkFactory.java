package com.ttmv.datacenter.usercenter.domain.mark.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarkFactory {

	private static final Logger logger = LogManager
			.getLogger(MarkFactory.class);
	/**
	 * 把传入的data 和 value 中的值进行合并。得到新的byte[]
	 * */
	public final static byte[] getByte(byte[] data,int[] sub,byte[] value){
		if(data == null || data.length<0 || sub == null || sub.length<2 || value == null || value.length<0){
			logger.warn("Invalid parameter");
			return null;
		}
		byte[] returnByte = MarkFactory.getByte(data, sub[0], sub[1], value);
		return returnByte;
	}
	
	/**
	 * 把传入的data 和 value 根据 start 和 end 位， 进行合并。得到新的byte[]
	 * */
	public static byte[] getByte(byte[] data, int start, int end, byte[] value) {
		if (data == null || data.length < 0 || start > end || value == null
				|| value.length < 0 || value.length != (end - start + 1)) {
			logger.warn("Invalid parameter");
			return null;
		}
		byte[] newData = ByteUtil.toBinary(data);
		int num = 0;
		for (; start < end + 1; start++) {
			byte temp = value[num++];
			newData[start] = temp;
		}
		return ByteUtil.toByteArray(newData);
	}
	/**
	 * 根据传入的byte 和 sub 截取 一段 byte
	 */
	public static byte[] cutBytes(byte[] data,int[] sub){
		if(data == null || data.length <0 || sub == null || sub.length<2 ){
			logger.warn("Invalid parameter");
			return null;
		}
		byte[] newData = ByteUtil.cutBytes(ByteUtil.toBinary(data), sub[0], sub[1]);
		return newData;
	}
}

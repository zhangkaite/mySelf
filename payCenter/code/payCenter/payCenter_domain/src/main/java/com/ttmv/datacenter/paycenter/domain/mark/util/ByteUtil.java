package com.ttmv.datacenter.paycenter.domain.mark.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ByteUtil {
	/**
	 * 把byte[]  转化为二进制 byte[] 数组，一个byte只表示0和1，用来模拟二进制，所以它的长度为原来byte的8倍
	 * */
	private static final Logger logger = LogManager.getLogger(ByteUtil.class);

	public static byte[] toBinary(byte[] b) {
		if (b == null || b.length==0){
			logger.warn("Invalid parameter");
			return null;
		}
		int num = b.length * 8;
		int byteLength = b.length;
		byte[] newByte = new byte[num];
		int j = 0;
		for(int i=0;i<byteLength;i++){
          byte temp = b[i];
          newByte[j++] = (byte)(temp >> 7 & 0x1);
          newByte[j++] = (byte)(temp >> 6 & 0x1);
          newByte[j++] = (byte)(temp >> 5 & 0x1);
          newByte[j++] = (byte)(temp >> 4 & 0x1);
          newByte[j++] = (byte)(temp >> 3 & 0x1);
          newByte[j++] = (byte)(temp >> 2 & 0x1);
          newByte[j++] = (byte)(temp >> 1 & 0x1);
          newByte[j++] = (byte)(temp >> 0 & 0x1);         
		}
		return newByte;
	}
	/**
	 * 二进制 byte[] 数组 转换为 byte[]  
	 * */
	public static byte[] toByteArray(byte[] b) {
		if (b == null || b.length==0 || b.length%8!=0){
			logger.debug("Invalid parameter");
			return null;
		}
	      int num = b.length/8;
	      int sub = 0;
	      byte[] byteArray = new byte[num];
	      for (int i = 0; i < num; i++) {
		     byte temp = 0x00;
	         if(b[sub++] != 0){
	        	 temp = -0x80;
	         }
	         temp =  (byte)((b[sub++] << 6) ^ temp);
	         temp =  (byte)((b[sub++] << 5) ^ temp);
	         temp =  (byte)((b[sub++] << 4) ^ temp);
	         temp =  (byte)((b[sub++] << 3) ^ temp);
	         temp =  (byte)((b[sub++] << 2) ^ temp);
	         temp =  (byte)((b[sub++] << 1) ^ temp);
	         temp =  (byte)((b[sub++] << 0) ^ temp);	     
	         byteArray[i] = temp; 	
		   }
		return byteArray;
	}
	/**
	 *  判断2个 byte[] 数组 是否一致  
	 * */
	public static boolean isEqualBytes(byte[] data,byte[] value){
		if(data==null && value == null){
			logger.debug("Empty parameter");
			return false;
		}
		if(data.length != value.length){
			return false; 
		}
		for(int i=0;i<data.length;i++){
			if(data[i] != value[i]){
				return false;
		    }
		 }
		return true;
	}
	/**
	 *  根据start 和 end 截取 一段 byte[]  
	 * */
	public static byte[] cutBytes(byte[] data,int start,int end){
		if(data==null || data.length<0 || start < 0 || end < 0 || start > end){
			logger.debug("Invalid parameter");
			return null;
		}
		byte[] value = new byte[end-start+1];
		for(int i=0;i<value.length;i++){
			byte temp = data[start++];
			value[i] = temp;
		}
        return value;
	}
}

package com.ttmv.monitoring.msgNotification.tools;

public class SmsEncode {
	 /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 根据编码值获取字符集名称
     *
     * @param dataCoding
     * @return
     */
    public static String getEncoding(int dataCoding) {
        switch (dataCoding) {
            case 0:
                return MsgConstant.ENC_ASCII;
            case 3:
                return MsgConstant.ENC_ISO8859_1;
            case 4:
                return MsgConstant.ENC_BINARY;
            case 8:
                return MsgConstant.ENC_UTF16_BE;
            case 15:
                return MsgConstant.ENC_GBK;
            default:
                return null;
        }
    }
	
    /**
     * 将普通字符串转换成Hex编码字符串
     *
     * @param dataCoding 编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param realStr    普通字符串
     * @return Hex编码字符串
     */
    public static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;

        if (realStr != null) {
            byte[] data = null;
            try {
                data = realStr.getBytes(getEncoding(dataCoding));

                if (data != null) {
                    hexStr = byteArr2HexStr(data);
                }
            } catch (Exception e) {
            	System.err.println("字符串转换成Hex编码字符串发生异常！");
            }
        }
        return hexStr;
    }

    /**
     * 将Hex编码字符串还原成普通字符串
     *
     * @param dataCoding 反编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param hexStr     Hex编码字符串
     * @return 普通字符串
     */
    public static String decodeHexStr(int dataCoding, String hexStr) {
        String realStr = null;

        if (hexStr != null) {
            try {
                byte[] data = hexStr2ByteArr(hexStr);
                realStr = new String(data, getEncoding(dataCoding));
            } catch (Exception e) {
                 System.err.println("Hex编码字符串转换成普通字符串发生异常！");
            }
        }

        return realStr;
    }
	
//	    public static void main(String[] args) {
//	        try {
//	            System.out.println(MessageHelper.encodeHexStr(15, "您本次操作的验证码为：399471，请勿透露给他人[战旗TV]"));
//	//            System.out.println(MessageHelper.decodeHexStr(15, "30353134626662316235623961307c3430307c3430327c7c"));
//	//            System.out.println(MessageHelper.getSmsLength("123456789012345678901234567890123456789012345678901234567890123456"));
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
		
		

		

}

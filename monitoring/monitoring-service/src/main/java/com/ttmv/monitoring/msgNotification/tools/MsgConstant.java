package com.ttmv.monitoring.msgNotification.tools;

/**
 * 消息通知常量接口
 * @author Damon
 *
 */
public interface MsgConstant {

	/** 短信*/
	String BMT_TYPE_SMS = "sms";
	/** 邮件*/	
	String BMT_TYPE_MAIL = "email";
	
	// *************短信平台错误代码**********
	/** 无效的commandd参数 */
	String MTERRCODE_0101 = "0101";
	/** 请求参数错误 */
	String MTERRCODE_0100 = "0100";
	/** 账号信息错误 */
	String MTERRCODE_0104 = "0104";
	/** 账号密码错误 */
	String MTERRCODE_0106 = "0106";
	/** 目标号码格式错误或群发号码数量超过100个 */
	String MTERRCODE_0110 = "0110";
	/** 未知错误 */
	String MTERRCODE_0600 = "0600";
	
    public static final int ASCII = 0;
    public static final int ASCII2 = 1;
    public static final int ISO8859_1 = 3;
    public static final int BINARY = 4;
    public static final int UNICODE = 8;
    public static final int GBK = 15;

    public static final String ENC_ASCII = "ASCII";
    public static final String ENC_CP1252 = "Cp1252";
    public static final String ENC_ISO8859_1 = "ISO8859_1";
    public static final String ENC_UTF16_BEM = "UnicodeBig";
    public static final String ENC_UTF16_BE = "UnicodeBigUnmarked";
    public static final String ENC_UTF16_LEM = "UnicodeLittle";
    public static final String ENC_UTF16_LE = "UnicodeLittleUnmarked";
    public static final String ENC_UTF8 = "UTF8";
    public static final String ENC_UTF16 = "UTF-16";
    public static final String ENC_GBK = "GBK";
    public static final String ENC_BINARY = "";
	
}

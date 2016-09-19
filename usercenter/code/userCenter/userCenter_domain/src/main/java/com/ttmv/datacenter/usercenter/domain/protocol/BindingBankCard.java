package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月10日 下午2:18:27  
 * @explain :银行卡绑定
 */
public class BindingBankCard {

	private BigInteger userID;//用户ID
	private String cardNo;//银行卡号
	private String realName;//开户人
	private String bankName;//开户行
	private String bankAddress;//开户行地址
	private String token;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	

	
	
}

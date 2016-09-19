package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月13日 上午10:45:51  
 * @explain :好友验证设置
 */
public class SetFriendVerifyInfo {
	private BigInteger userID;	//用户ID
	private Integer dndType;//防打扰类型
	private Integer problemType;//问题类型
	private String answer;//问题答案
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public Integer getDndType() {
		return dndType;
	}
	public void setDndType(Integer dndType) {
		this.dndType = dndType;
	}
	public Integer getProblemType() {
		return problemType;
	}
	public void setProblemType(Integer problemType) {
		this.problemType = problemType;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}

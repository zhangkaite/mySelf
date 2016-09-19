package com.ttmv.datacenter.usercenter.domain.protocol;
/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月3日 下午4:49:51  
 * @explain :050_第三方登录
 */
public class SdkLogin {

	private String nickName;//昵称
	private String openID;
	private String userPhoto;//默认图像
	private Integer sdkType;//第三方平台类型
	private Integer clientType;//登录端类型 
	
	
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	public Integer getSdkType() {
		return sdkType;
	}
	public void setSdkType(Integer sdkType) {
		this.sdkType = sdkType;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	
	
}

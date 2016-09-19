package com.ttmv.datacenter.usercenter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.hdsf.server.domain.RegistableService;
import com.ttmv.datacenter.hdsf.server.zookeeper.ZKCenterAgent;


public class RegisteredService  extends Thread {
	
	private static final Logger logger = LogManager.getLogger(RegisteredService.class);
	private static ZKCenterAgent zkCenterAgent;
	private static String port;
	
	@SuppressWarnings("static-access")
	public RegisteredService(ZKCenterAgent zkCenterAgent,String port){
		this.zkCenterAgent = zkCenterAgent;
		this.port = port;
	}

	public void run() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			Map<String, String> serverConfig = new HashMap<String, String>();
			serverConfig.put("ipv4", addr.getHostAddress().toString());
			serverConfig.put("port", port);
			for (String servicePath : getServicePath()) {
				zkCenterAgent.setService(new RegistableService(servicePath,
						serverConfig));
			}
			zkCenterAgent.regist();
		} catch (Exception e) {
			logger.error("注册服务出现异常 ", e);
		}
	}
	
	private static List<String> getServicePath(){
		List<String> list = new ArrayList<String>();;
		list.add("/com/ttmv/service/data-center/user-center/adminAddUser");//管控添加用户
		list.add("/com/ttmv/service/data-center/user-center/resetPassword");//密码修改
		list.add("/com/ttmv/service/data-center/user-center/modifyUserExtend");//修改用户信息
		list.add("/com/ttmv/service/data-center/user-center/loginBinding");//登录方式开通
		list.add("/com/ttmv/service/data-center/user-center/applyRealNameReg");//添加实名认证
		list.add("/com/ttmv/service/data-center/user-center/createFriendGroup");//创建好友分组
		list.add("/com/ttmv/service/data-center/user-center/modifyFriendGroup");//修改好友分组
		list.add("/com/ttmv/service/data-center/user-center/removeFriendGroup");//删除好友分组
		list.add("/com/ttmv/service/data-center/user-center/userInfoBinding");//用户信息绑定
		list.add("/com/ttmv/service/data-center/user-center/userValidation");//账户信息校验
		list.add("/com/ttmv/service/data-center/user-center/parameterManage");//参数管理
		list.add("/com/ttmv/service/data-center/user-center/forceOutDevice");//封终端设备
		list.add("/com/ttmv/service/data-center/user-center/unsetDevice");//解封终端
		list.add("/com/ttmv/service/data-center/user-center/forbiddenUser");//冻结删除用户
		list.add("/com/ttmv/service/data-center/user-center/removeForbiddenUser");//解冻用户
		list.add("/com/ttmv/service/data-center/user-center/queryUserLog");//用户上线记录查询
		list.add("/com/ttmv/service/data-center/user-center/queryUserById");//用户基本信息查询
		list.add("/com/ttmv/service/data-center/user-center/queryUsers");
		list.add("/com/ttmv/service/data-center/user-center/queryFriend");
		list.add("/com/ttmv/service/data-center/user-center/querySecurityInfo");//用户安全信息查询
		list.add("/com/ttmv/service/data-center/user-center/addAttention");//用户关注
		list.add("/com/ttmv/service/data-center/user-center/undoAttention");//取消关注
		list.add("/com/ttmv/service/data-center/user-center/addFriend");//添加好友
		list.add("/com/ttmv/service/data-center/user-center/removeFriend");//删除好友
		list.add("/com/ttmv/service/data-center/user-center/goodTTnumManage");//靓号状态修改
		list.add("/com/ttmv/service/data-center/user-center/complainValidation");//申述账号检测 
		list.add("/com/ttmv/service/data-center/user-center/diyFiendInfo");//好友信息DIY
		list.add("/com/ttmv/service/data-center/user-center/queryFriendInfos");//好友信息列表获取
		list.add("/com/ttmv/service/data-center/user-center/queryUserFriendGroup");//048_查询用户好友分组信息及好友关系
		list.add("/com/ttmv/service/data-center/user-center/queryFriendGroupInfo");//049_查询用户好友分组信息
		list.add("/com/ttmv/service/data-center/user-center/bindingBankCard");//银行卡绑定
		list.add("/com/ttmv/service/data-center/user-center/tokenValidation");//token验证
		list.add("/com/ttmv/service/data-center/user-center/setFriendVerifyInfo");//好友添加认证信息设置
		list.add("/com/ttmv/service/data-center/user-center/getFriendVerifyInfo");//获取验证信息
		list.add("/com/ttmv/service/data-center/user-center/relieveLoginBinding");//登录账号解绑
		
		list.add("/com/ttmv/service/data-center/user-center/login");//登录
		list.add("/com/ttmv/service/data-center/user-center/logout");//退出
		
		return list;
	}
}

package com.springapp.util;

public  class ResultCode {
	
	public static String getResultMessage(String resultCode){
		if ("AAAAAAA".equals(resultCode)) {
			return "数据处理成功";
		}else if ("ERR_500".equals(resultCode)) {
			return "系统异常";
		}else if ("ERR_400".equals(resultCode)) {
			return "请求参数非法";
		}else if ("ERR_401".equals(resultCode)) {
			return "请求参数缺失";
		}else if ("ERR_402".equals(resultCode)) {
			return "参数类型异常";
		}else if ("ERR_403".equals(resultCode)) {
			return "异常的查询结果";
		}else if ("ERR_404".equals(resultCode)) {
			return "登录失败（用户名或密码错误）";
		}else if ("ERR_405".equals(resultCode)) {
			return "用户名已被使用";
		}else if("ERR_406".equals(resultCode)){
			return "用户已经被删除";
		}else if("ERR_407".equals(resultCode)){
			return "删除失败，该报警器已经关联用户";
		}
		else {
			return "未知错误类型";
		}
		
	}

}

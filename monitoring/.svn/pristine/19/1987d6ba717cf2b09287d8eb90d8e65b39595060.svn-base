<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="common/common.jsp"></jsp:include>
<head>
<title>Insert title here</title>
<style type="text/css">
body {
	padding-top: 100px;
	padding-bottom: 40px;
	background-color: #C0C0C0;
}

.form-signin {
	max-width: 400px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
	text-align: center;
	margin-top: 5px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<base href="/">
</head>
<body>
	<div class="container" ng-controller="login">
		<form class="form-signin">
			<h2 class="form-signin-heading">服务监控平台</h2>
			<input type="text" class="input-block-level" ng-model="user.loginName" placeholder="登录名">
			<input type="password" ng-model="user.password" class="input-block-level" placeholder="密码">
			<input type="text" name="rand" id="rand" ng-model="user.rand" style="width:60%" placeholder="验证码">
			<a href="javascript:void(0);" onclick="changeVerifCode()" id="verifCode">
				<img id="verifCodeImg" src="/generate" alt="验证码" />
			</a>
			<button class="btn btn-large btn-primary" style="margin-left: 36%; padding: 6px 30px;" type="submit"
				ng-click="login(user)">登录</button>
		</form>
	</div>
</body>
<!-- /container -->
<script type="text/javascript">
	

	function changeVerifCode() {
		$("#verifCodeImg").attr("src", "/generate?r="+Math.random());
	}

	App.controller('login', [ '$scope', '$filter', '$location', 'userLogin',
			function($scope, $filter, $location, userLogin) {
				$scope.login = function(user) {
					if(user.rand==null||user.rand==''){
						alert("验证码不能为空");
						return false;
					}
					var passwd=$.md5(user.password);
					user.password=passwd;
					userLogin.login(user, "/userLogin").then(function(resData) {
						//0表示登录成功
						if (resData.result == '0') {
							window.location.href = window.location.href + "homepage"
						} else if(resData.result == '1') {
							alert("用户名或密码错误");
							user.password='';
							changeVerifCode();
						}else{
							alert("验证码错误");
							user.password='';
							user.rand='';
							changeVerifCode();
						}
					});
				};
			}

	]);

	App.factory('userLogin', [ '$http', function($http) {
		var userLogin = {};
		userLogin.login = function(params, urls) {
			var req = {
				method : 'POST',
				url : urls,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					reqData : JSON.stringify(params)
				}
			}
			var datas = $http(req).then(function(resp) {
				return resp.data;
			});
			return datas;
		};
		return userLogin;
	} ]);
</script>

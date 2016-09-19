<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="common/invalid.jsp"></jsp:include>
<style>
.list-header {
	text-align: center
}

.addUser_span {
	color: red;
}

.display-none {
	display: none
}
</style>
<jsp:include page="common/common.jsp"></jsp:include>
<div ng-controller="userList">
	<!-- 添加查询条件 -->
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<h3 style="margin-top: 6px;">
				<span class="glyphicon glyphicon-user" style="font-weight: 900">&nbsp;用户管理</span>
				<button type="button" data-toggle="modal" data-target="#myModal" ng-click="cleanForm()"
					style="font-size: 22px; background-color: transparent; float: right; border: none">
					<span class="glyphicon glyphicon-plus"></span>
				</button>
			</h3>
		</div>
		&nbsp;
		<!-- 错误提示model -->
		<!-- Modal -->
		<div class="modal fade" id="alertModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">提示信息</h4>
					</div>
					<div class="modal-body" id="showDetail">{{alertMsg}}</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<form class="form-inline" style="margin-top: 15px; padding-left: 10px;">
			<div class="form-group">
				<span class="glyphicon glyphicon-search"></span> <label for="exampleInputName2">用户名</label>
				<input type="text" ng-model="userName" class="form-control" id="exampleInputName2">
			</div>
			&nbsp;
			<div class="form-group">
				<span class="glyphicon glyphicon-search"></span> <label for="exampleInputEmail2">邮箱</label>
				<input type="text" ng-model="email" class="form-control" id="exampleInputEmail2">
				<button type="button" class="btn btn-primary" style="margin-left: 20px;" ng-click="showView()">查看</button>
			</div>
		</form>
		&nbsp;
		<div class="panel-body">
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style="font-size: 18px">登录名</th>
					<th class="list-header" style="font-size: 18px">真实姓名</th>
					<th class="list-header" style="font-size: 18px">手机号</th>
					<th class="list-header" style="font-size: 18px">邮箱</th>
					<th class="list-header" style="font-size: 18px">新增时间</th>
					<th class="list-header" style="font-size: 18px">修改资料</th>
					<th class="list-header" style="font-size: 18px">修改密码</th>
					<th class="list-header" style="font-size: 18px">删除</th>
				</tr>
				<tr ng-repeat="user in users">
					<td class="display-none">{{user.id}}</td>
					<td class="list-header">{{user.userName}}</td>
					<td class="list-header">{{user.realName}}</td>
					<td class="list-header">{{user.mobile}}</td>
					<td class="list-header">{{user.email}}</td>
					<td class="list-header">{{user.creatTime}}</td>
					<td style="text-align: center"><a data-toggle="modal" ng-click="userModify(user)"
							data-target="#editModal">
							<span class="glyphicon glyphicon-pencil"></span>
						</a></td>
					<td style="text-align: center"><a>
							<button type="button" class="btn btn-primary" ng-click="getUserInfo(user)" style="padding: 1px 1px;"
								data-toggle="modal" data-target="#editPwdModal">修改密码</button>
						</a></td>
					<td style="text-align: center"><a ng-click="delUser(user)">
							<span class="glyphicon glyphicon-remove"></span>
						</a></td>
				</tr>
			</table>
			<pagination></pagination>
		</div>
		<div style="height: 30px"></div>
		<!-- 修改密码 -->
		<div class="modal fade" id="editPwdModal" tabindex="-1" role="dialog" aria-labelledby="editPwdModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="editPwdModalLabel">修改密码</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" name="editPwdForm" novalidate>
							<div class="form-group" style="display: none">
								<label class="col-sm-2 control-label">用户ID</label>
								<div class="col-sm-5">
									<input type="text" ng-model="userName" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">新密码</label>
								<div class="col-sm-5">
									<input type="password" name="newpassword" ng-model="newpassword" required ng-minlength="6"
										ng-maxlength="16" class="form-control" placeholder="Password">
									
								</div>
								<div><span class="addUser_span" ng-show="editPwdForm.newpassword.$error.required">必填项</span> <span
										class="addUser_span" ng-show="editPwdForm.newpassword.$error.minlength">密码过短</span> <span
										class="addUser_span" ng-show="editPwdForm.newpassword.$error.maxlength">密码太长</span></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">确认密码</label>
								<div class="col-sm-5">
									<input type="password" name="reNewpassword" ng-model="reNewpassword" required ng-minlength="6"
										ng-maxlength="16" class="form-control" placeholder="Password">
									
								</div>
								<div><span class="addUser_span" ng-show="editPwdForm.reNewpassword.$error.required">必填项</span> <span
										class="addUser_span" ng-show="editPwdForm.reNewpassword.$error.minlength">密码太短</span> <span
										class="addUser_span" ng-show="editPwdForm.reNewpassword.$error.maxlength">密码太长</span></div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary" ng-disabled="editPwdForm.$invalid"
									ng-click="modifyPwd()">提交更改</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 修改用户信息模态对话框 -->
		<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="editModalLabel">修改用户</h4>
					</div>
					<div class="modal-body" ng-repeat="modusers in modifyusers">
						<form class="form-horizontal" name="modUserForm" novalidate>
							<div class="form-group" style="display: none">
								<label class="col-sm-2 control-label">用户ID</label>
								<div class="col-sm-5">
									<input type="text" ng-model="modusers.userID" required class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">登录名</label>
								<div class="col-sm-5">
									<input type="text" ng-model="modusers.userName" readonly="readonly" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">真实姓名</label>
								<div class="col-sm-5">
									<input type="text" name="realName" ng-model="modusers.realName" required class="form-control">
									<span class="addUser_span" ng-show="modUserForm.realName.$error.required">必填项</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-5">
									<input type="text" name="mobile" ng-model="modusers.mobile" ng-pattern="/1[3|5|7|8|][0-9]{9}/"
										required class="form-control">
									<span class="addUser_span" ng-show="modUserForm.mobile.$error.required">必填项</span> <span
										class="addUser_span" ng-show="modUserForm.mobile.$error.pattern">手机号格式非法</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-5">
									<input type="email" name="email" ng-model="modusers.email" class="form-control" placeholder="Email">
									<span class="addUser_span" ng-show="modUserForm.email.$error.required">必填项</span> <span
										class="addUser_span" ng-show="modUserForm.email.$error.email">邮箱格式非法</span>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="initPage()">关闭</button>
								<button type="button" class="btn btn-primary" ng-disabled="modUserForm.$invalid"
									ng-click="updateUser(modusers)">提交更改</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 添加用户信息模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">新增用户</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" name="addUserForm" novalidate>
							<div class="form-group">
								<label class="col-sm-2 control-label">登录名</label>
								<div class="col-sm-5">
									<input type="text" name="userName" ng-model="user.userName" required class="form-control">
								</div>
								<div><span class="addUser_span" ng-show="addUserForm.userName.$error.required">必填项</span></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">真实姓名</label>
								<div class="col-sm-5">
									<input type="text" name="realName" ng-model="user.realName" required class="form-control">
								</div>
								<div><span class="addUser_span" ng-show="addUserForm.realName.$error.required">必填项</span></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-5">
									<input type="text" name="mobile" ng-model="user.mobile" ng-pattern="/1[3|5|7|8|][0-9]{9}/" required
										class="form-control">
									
								</div>
								<div><span class="addUser_span" ng-show="addUserForm.mobile.$error.required">必填项</span> <span
										class="addUser_span" ng-show="addUserForm.mobile.$error.pattern">手机号格式非法</span>
										</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-5">
									<input type="email" name="email" ng-model="user.email" required class="form-control" value="" required>
									
								</div>
								<div><span class="addUser_span" ng-show="addUserForm.email.$error.required">必填项</span> <span
										class="addUser_span" ng-show="addUserForm.email.$error.email">邮箱格式非法</span></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">密码</label>
								<div class="col-sm-5">
									<input type="password" name="passd" ng-model="user.password" required ng-minlength="6" value=""
										ng-maxlength="16" class="form-control" placeholder="Password">
									
								</div>
								<div><span class="addUser_span" ng-show="addUserForm.passd.$error.required">必填项</span> <span
										class="addUser_span" ng-show="addUserForm.passd.$error.minlength">密码太短</span> <span
										class="addUser_span" ng-show="addUserForm.passd.$error.maxlength">密码太长</span></div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" ng-click="initPage()" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" ng-disabled="addUserForm.$invalid"
							ng-click="addUser(user)">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	App.controller('userList', function($scope, $filter, $timeout, factorys, PaginationService) {
		var pageSize = 10
		$scope.loadList = function(page) {
			var params = {
				"pageSize" : pageSize,
				"page" : page
			}
			factorys.get(params, "/userShow").then(function(users) {

				$scope.users = users.result;
				$scope.conf = PaginationService.config(page, users.dataSum, pageSize);

			});
		};
		//init load data
		$scope.loadList(1);

		$scope.changeCurrentPage = function(item) {
			if (item == '...') {
				return;
			} else {
				$scope.loadList(item);
			}
		}
		//changeItemsPerPage
		$scope.changeItemsPerPage = function() {
			pageSize = $scope.conf.itemsPerPage;
			$scope.loadList(1);
		}
		// prevPage
		$scope.prevPage = function() {
			if ($scope.conf.currentPage > 1) {
				var item = $scope.conf.currentPage - 1;
				$scope.changeCurrentPage(item);
			}
		};
		// nextPage
		$scope.nextPage = function() {
			if ($scope.conf.currentPage < $scope.conf.numberOfPages) {
				var item = $scope.conf.currentPage + 1;
				$scope.changeCurrentPage(item);
			}
		};

		$scope.initPage = function() {
			$scope.loadList(1);
		}

		//添加用户
		$scope.addUser = function(user) {
			var passwd = $.md5(user.password);
			user.password = passwd;
			factorys.add(user, "/addUser").then(function(resData) {
				filterPost(resData);
				$("#myModal").modal("hide");
				$scope.loadList(1);
			});
		};
		//删除用户
		$scope.delUser = function(user) {
			if (confirm('确定删除该用户吗?')) {
				user.type = '9';
				factorys.del(user, "/delUser").then(function(resData) {
					filterPost(resData);
					$scope.loadList(1);
				});
			}
		}
		//修改用户
		$scope.userModify = function(user) {
			//user.type='1';
			factorys.get(user, "/queryUser").then(function(resData) {
				filterPost(resData);
				$scope.modifyusers = resData;
			});
		}
		//更新用户
		$scope.updateUser = function(modifyusers) {
			modifyusers.type = '1';
			factorys.update(modifyusers, "/updateUser").then(function(resData) {
				filterPost(resData);
				$scope.loadList(1);
				$("#editModal").modal("hide");
			});
		};
		/***查询用户*/
		$scope.showView = function() {
			var params = {
				userName : $scope.userName,
				email : $scope.email,
				pageSize : pageSize,
				page : 1
			}
			factorys.get(params, "/queryUsers").then(function(resData) {

				filterPost(resData);
				$scope.users = resData.result;
				$scope.conf = PaginationService.config(1, resData.dataSum, pageSize);
			});
		}
		
		$scope.modifyPwd = function() {
			var newPassword = $scope.newpassword;
			var reNewPassword = $scope.reNewpassword;
			var userName = $scope.userName;
			if (newPassword != reNewPassword) {
				$("#editPwdModal").modal("hide");
				$scope.alertMsg = "密码不一致";
				$("#alertModal").modal();
				return;
			}
			var params = {
				"userName" : userName,
				"newPassword" : $.md5(newPassword),
				"type" : "9"
			}
			factorys.get(params, "/updatePasswd").then(function(resData) {
				$("#editPwdModal").modal("hide");
				if (resData.flag == '0') {
					$scope.alertMsg = "修改成功";
					$("#alertModal").modal();

				} else {
					$scope.alertMsg = "修改失败";
					$("#alertModal").modal();
				}

			});

		}

		$scope.getUserInfo = function(user) {
			$scope.userName = user.userName;

		}
		
		$scope.cleanForm=function(){
			user.email='';
			user.password='';
		}
		
		
		
		
	});

	
</script>

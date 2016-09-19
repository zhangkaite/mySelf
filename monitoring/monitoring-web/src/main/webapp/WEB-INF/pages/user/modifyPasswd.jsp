<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<style>
.list-header {
	text-align: center
}

.addUser_span {
	color: red;
}

select {
	display: block;
	height: 34px;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.42857143;
	color: #555;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	float: left
}
</style>
<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/invalid.jsp"></jsp:include>
<script src="/threshold/threshold.js"></script>
<script type="text/javascript">
	App.controller('modifyPwd', function($scope, $http, $timeout, $interval, factorys, PaginationService) {
		$scope.modifyPwd = function() {
			var password = $scope.password;
			var newPassword = $scope.newPassword;
			var reNewPassword = $scope.reNewPassword;
			if (newPassword != reNewPassword) {
				$scope.alertMsg = "对不起，两次输入密码不一致！！！";
				$("#alertModal").modal();
				return;
			}
			var params = {
				"password" : $.md5(password),
				"newPassword" : $.md5(newPassword),
				"type" : "1"
			}
			factorys.get(params, "/updatePasswd").then(function(resData) {

				if (resData.flag == '0') {
					$scope.alertMsg = "恭喜您，密码修改成功!";
					$("#alertModal").modal();

				} else {
					$scope.alertMsg = "密码不正确，修改失败！";
					$("#alertModal").modal();
				}

			});

		}

	});
</script>
<div ng-controller="modifyPwd">
	<!-- 添加查询条件 -->
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<h3 style="margin-top: 6px;">
				<span class="glyphicon glyphicon-cog" style="font-weight: 900">&nbsp;修改密码&nbsp;</span>
			</h3>
		</div>
		&nbsp;
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
		<form class="form-horizontal" name="modPwdForm" novalidate>
			<div class="form-group">
				<label class="col-sm-2 control-label">原始密码</label>
				<div class="col-sm-5">
					<input type="password" name="password" class="form-control" ng-model="password" required ng-minlength="6"
						ng-maxlength="16" placeholder="Password">
				</div>
				<div>
					<span class="addUser_span" ng-show="modPwdForm.password.$error.required">必填项</span> <span
						class="addUser_span" ng-show="modPwdForm.password.$error.minlength">密码过短</span> <span
						class="addUser_span" ng-show="modPwdForm.password.$error.maxlength">密码太长</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">新密码</label>
				<div class="col-sm-5">
					<input type="password" name="newPassword" class="form-control" ng-model="newPassword" required
						ng-minlength="6" ng-maxlength="16" placeholder="Password">
				</div>
				<div>
					<span class="addUser_span" ng-show="modPwdForm.newPassword.$error.required">必填项</span> <span
						class="addUser_span" ng-show="modPwdForm.newPassword.$error.minlength">密码过短</span> <span
						class="addUser_span" ng-show="modPwdForm.newPassword.$error.maxlength">密码太长</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">确认新密码</label>
				<div class="col-sm-5">
					<input type="password" name="reNewPassword" class="form-control" ng-model="reNewPassword" required
						ng-minlength="6" ng-maxlength="16" placeholder="Password">
				</div>
				<div>
					<span class="addUser_span" ng-show="modPwdForm.reNewPassword.$error.required">必填项</span> <span
						class="addUser_span" ng-show="modPwdForm.reNewPassword.$error.minlength">密码过短</span> <span
						class="addUser_span" ng-show="modPwdForm.reNewPassword.$error.maxlength">密码太长</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-4">
					<button type="submit" ng-disabled="modPwdForm.$invalid" ng-click="modifyPwd()" class="btn btn-primary">提交</button>
				</div>
			</div>
		</form>
	</div>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<style>
</style>
<jsp:include page="../common/common.jsp"></jsp:include>
<div ng-controller="dataDict">
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<h4>
				<span class="glyphicon glyphicon-user" style="font-weight: 900">数据字典列表</span>
			</h4>
		</div>
		<form class="form-inline"
			style="margin-top: 10px; padding-left: 10px;">
			<button type="button" data-toggle="modal" data-target="#myModal"
				style="" class="btn btn-default">新增</button>
		</form>
		<table class="table table-striped table-bordered table-hover">
			<tr>
				<th class="list-header">字典名称</th>
				<th class="list-header">字典类型</th>
				<th class="list-header">字典类型名称</th>
				<th class="list-header">操作</th>
			</tr>
			<tr ng-repeat="burglar in burglars ">
				<td class="display-none">{{burglar.id}}</td>
				<td class="list-header"></td>
				<td class="list-header"></td>
				<td class="list-header"></td>
				<td><a data-toggle="modal" ng-click="burglarModify(user)"
					data-target="#editModal">修改</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a
					ng-click="delBurglar(user)">删除</a></td>
			</tr>
		</table>
	</div>

</div>
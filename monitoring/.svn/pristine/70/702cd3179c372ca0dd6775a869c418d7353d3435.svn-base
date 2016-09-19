<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.list-header {
	text-align: center
}

.display-none {
	display: none
}

body {
	padding: 15px;
}

.select2>.select2-choice.ui-select-match {
	/* Because of the inclusion of Bootstrap */
	height: 29px;
}

.selectize-control>.selectize-dropdown {
	top: 36px;
}
</style>
<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/invalid.jsp"></jsp:include>
<script src="/burglar/burglar.js"></script>
<body ng-controller="DemoCtrl">
	<div>
		<!-- 添加查询条件 -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<form class="form-inline" >
				    <h3 style="margin-top: 6px;"><span class="glyphicon glyphicon-bell" style="font-weight: 900">&nbsp;报警器配置</span>
						<button type="button" data-toggle="modal" data-target="#myModal" ng-click="loadList()" style="font-size:22px;background-color:transparent;float:right;border:none"><span class="glyphicon glyphicon-plus"></span></button>
					</h3>
				</form>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style="width:80%; font-size: 18px">报警名称</th>
					<!-- <th class="list-header">报警方式</th>
					<th class="list-header">报警次数</th>
					<th class="list-header">报警信息模版</th>
					<th class="list-header">关联人员</th> -->
					<th class="list-header" style="font-size: 18px">编辑</th>
					<th class="list-header" style="font-size: 18px">删除</th>
				</tr>
				<tr ng-repeat="alerter in alerters ">
					<td class="display-none">{{alerter.alerterID}}</td>
					<td class="list-header">{{alerter.alerterName}}</td>
                <td style="text-align: center"><a data-toggle="modal" ng-click="burglarModify(alerter)" data-target="#modifyModal"><span class="glyphicon glyphicon-pencil"></span></a></td>
					<td style="text-align: center"><a
						ng-click="delBurglar(alerter)"><span class="glyphicon glyphicon-remove"></span></a></td>
				</tr>
			</table>
			<pagination></pagination>
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
						<div class="modal-body">
							<p>{{alertMsg}}</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
			<!--##################################################### 修改####################################################### -->
			<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyModalLabel"
				aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="modifyModalLabel">修改报警器</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-3 control-label">报警器名称</label>
									<div style="display: none;">
										<input type="text" ng-model="burglar.alerterID" class="form-control">
									</div>
									<div class="col-sm-6">
										<input type="text" ng-model="burglar.alerterName" readonly class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">报警方式</label>
									<div class="col-sm-2" ng-repeat="alerterType in alerterTypes">
										<input type="checkbox" style="margin-top: 10px;" checklist-model="burgl.alerterTypes"
											checklist-value="alerterType.id">
										&nbsp;&nbsp;{{alerterType.text}}
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">报警次数</label>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="1" ng-model="burglar.count">
										1
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="5" ng-model="burglar.count">
										5
									</div>
								</div>
								<div class="form-group" style="display: none;">
									<label class="col-sm-3 control-label">报警信息模版</label>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="1" ng-model="burglar.model">
										模版1
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="2" ng-model="burglar.model">
										模版2
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="3" ng-model="burglar.model">
										模版3
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">关联人员</label>
									<div class="col-sm-6">
										<ui-select multiple ng-model="multipleDemo.selectedPeople" theme="bootstrap" ng-disabled="disabled"
											sortable="true" close-on-select="false" style="width: auto;"> <ui-select-match>
										{{$item.userName}} </ui-select-match> <ui-select-choices repeat="person in mmm | propsFilter: {userName: $select.search}">
										<div ng-bind-html="person.userName | highlight: $select.search"></div>
										<small> email: {{person.email}} </small> </ui-select-choices> </ui-select>
										<!-- <p>Selected: {{multipleDemo.selectedPeople[0].userName}}</p> -->
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="init()">关闭</button>
							<button type="button" class="btn btn-primary" ng-click="updateBurglar(burglar)">更新</button>
						</div>
					</div>
				</div>
			</div>
			<!-- 添加用户信息模态框（Modal） -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">新增报警器</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal" novalidate>
								<div class="form-group">
									<label class="col-sm-3 control-label">报警器名称</label>
									<div class="col-sm-6">
										<input type="text" ng-model="burglar.alerterName" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">报警方式</label>
									<div class="col-sm-2" ng-repeat="alerterType in alerterTypes">
										<input type="checkbox" style="margin-top: 10px;" ng-click="selectData(alerterType.id)"
											checklist-model="burglar.alerterTypes" checklist-value="alerterType.id">
										&nbsp;&nbsp;{{alerterType.text}}
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">报警次数</label>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="1" ng-model="burglar.count">
										1
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="5" ng-model="burglar.count">
										5
									</div>
								</div>
								<div class="form-group" style="display: none;">
									<label class="col-sm-3 control-label">报警信息模版</label>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="1" ng-model="burglar.model">
										模版1
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="2" ng-model="burglar.model">
										模版2
									</div>
									<div class="col-sm-2">
										<input type="radio" style="margin-top: 10px;" value="3" ng-model="burglar.model">
										模版3
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">关联人员</label>
									<div class="col-sm-6">
										<ui-select multiple ng-model="multipleDemo.selectedPeople" theme="bootstrap" ng-disabled="disabled"
											sortable="true" close-on-select="false" style="width: auto;"> <ui-select-match>{{$item.userName}}
										</ui-select-match> <ui-select-choices repeat="person in people | propsFilter: {userName: $select.search}">
										<div ng-bind-html="person.userName | highlight: $select.search"></div>
										<small> email: {{person.email}} </span>
										</small> </ui-select-choices> </ui-select>
										<!-- <p>Selected: {{multipleDemo.selectedPeople[0].userName}}</p> -->
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="init()">关闭</button>
							<button type="button" class="btn btn-primary" ng-click="saveBurglar(burglar)">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

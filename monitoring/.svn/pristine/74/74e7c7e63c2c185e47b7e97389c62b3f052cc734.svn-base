<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.list-header {
	text-align: center
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
<script src="/collection/cpuList.js"></script>
<body ng-controller="cpuList">
	<div>
		<!-- 添加查询条件 -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<form class="form-inline">
					<h3 style="margin-top: 6px;">
						<span class="glyphicon glyphicon-list-alt" style="font-weight: 900">&nbsp;cpu列表</span>
					</h3>
				</form>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style="font-size: 14px">IP地址</th>
					<th class="list-header" style="font-size: 14px">MAC地址</th>
					<th class="list-header" style="font-size: 14px">CPU片段</th>
					<th class="list-header" style="font-size: 14px">CPU的总量MHz</th>
					<th class="list-header" style="font-size: 14px">CPU的供应商</th>
					<th class="list-header" style="font-size: 14px">CPU用户使用率</th>
					<th class="list-header" style="font-size: 14px">CPU系统使用率</th>
					<th class="list-header" style="font-size: 14px">CPU当前等待率</th>
					<th class="list-header" style="font-size: 14px">CPU当前错误率</th>
					<th class="list-header" style="font-size: 14px">CPU当前空闲率</th>
					<th class="list-header" style="font-size: 14px">CPU总的使用率</th>
				</tr>
				<tr ng-repeat="curdata in dataLists ">
					<td>{{curdata.ip}}</td>
					<td>{{curdata.mac}}</td>
					<td>{{curdata.cpuNo}}</td>
					<td>{{curdata.cpuMhz}}</td>
					<td>{{curdata.cpuVendor}}</td>
					<td>{{curdata.cpuUser}}</td>
					<td>{{curdata.cpuSys}}</td>
					<td>{{curdata.cpuWait}}</td>
					<td>{{curdata.cpuNice}}</td>
					<td>{{curdata.cpuIdle}}</td>
					<td>{{curdata.cpuTotal}}</td>
				</tr>
			</table>
			<pagination></pagination>

		</div>
	</div>
</body>

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
<script src="/redis/redisList.js"></script>
<body ng-controller="Redis">
	<div>
		<!-- 添加查询条件 -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
			<span class="glyphicon glyphicon-list-alt" style="font-weight: 900">&nbsp;redis详情</span>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style=" font-size: 18px">主机</th>
					<th class="list-header" style=" font-size: 18px">key名称</th>
					<th class="list-header" style="font-size: 18px">key类型</th>
					<th class="list-header" style="font-size: 18px">value长度</th>
					<th class="list-header" style="font-size: 18px">分配的内存总量</th>
					<th class="list-header" style="font-size: 18px">内存消耗峰值</th>
				</tr>
				<tr ng-repeat="redis in redisList ">
					<td class="list-header">{{redis.hostName}}</td>
					<td class="list-header">{{redis.keyName}}</td>
					<td class="list-header">{{redis.keyType}}</td>
					<td class="list-header">{{redis.valueLength}}</td>
					<td class="list-header">{{redis.used_memory}}</td>
					<td class="list-header">{{redis.used_memory_peak}}</td>
				</tr>
			</table>
			<!-- <pagination></pagination> -->
		</div>
	</div>
</body>

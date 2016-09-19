<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IM最新数据列表</title>
</head>
<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/invalid.jsp"></jsp:include>
<script src="/charts/imDataList.js"></script>
<div ng-controller="imDataList">
	<!-- 添加查询条件 -->
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<h3 style="margin-top: 6px;">
				<span class="glyphicon glyphicon-list" style="font-weight: 900">&nbsp;IM最新数据列表</span>
			</h3>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style="font-size: 18px">服务名称</th>
					<th class="list-header" style="font-size: 18px">服务地址</th>
					<th class="list-header" style="font-size: 18px">持续运行时间</th>
					<th class="list-header" style="font-size: 18px">CPU占用百分比</th>
					<th class="list-header" style="font-size: 18px">内存占用(MB)</th>
					<th class="list-header" style="font-size: 18px">硬盘空间占用(GB)</th>
					<th class="list-header" style="font-size: 18px">工作线程数</th>
					<th class="list-header" style="font-size: 18px">客户端连接数</th>
					<th class="list-header" style="font-size: 18px">已收到命令包数</th>
					<th class="list-header" style="font-size: 18px">已响应命令包数</th>
					<th class="list-header" style="font-size: 18px">群组数量</th>
				</tr>
				<tr ng-repeat="imServiceData in imServiceDatas">
					<td class="display-none">{{imServiceData.server_type}}</td>
					<td class="list-header">{{imServiceData.ip}}</td>
					<td class="list-header">{{imServiceData.run_time}}</td>
					<td class="list-header">{{imServiceData.cpu}}</td>
					<td class="list-header">{{imServiceData.mem}}</td>
					<td class="list-header">{{imServiceData.disk}}</td>
					<td class="list-header">{{imServiceData.work_thread_sum}}</td>
					<td class="list-header">{{imServiceData.client_connection_sum}}</td>
					<td class="list-header">{{imServiceData.input_cmds}}</td>
					<td class="list-header">{{imServiceData.output_cmds}}</td>
					<td class="list-header">{{imServiceData.group_count}}</td>
				</tr>
			</table>

		</div>
	</div>
</div>


</html>
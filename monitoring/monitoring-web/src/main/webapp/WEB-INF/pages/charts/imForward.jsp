<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/invalid.jsp"></jsp:include>
<script src="/charts/imForward.js"></script>
<style type="text/css">
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

p {
	float: right;
	padding-left: 8px;
}

.style_p {
	float: left;
	font-size: 24px;
}

div {
	float: left;
	width: 100%;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div>
		&nbsp;
		<div>
			<p class="style_p" style="font-size: 20px">选择子系统：</p>
			<select id="firsSelect">
			</select>
			<p class="style_p" style="font-size: 20px">&nbsp;&nbsp;&nbsp;IP：</p>
			<select id="oneSelect">
			</select>
			<p class="style_p" style="font-size: 20px; display: none">端口：</p>
			<select style="display: none" id="twoSelect">
			</select>
			<button type="button" class="btn btn-primary" style="margin-left: 20px;" onclick="showView()">查看</button>
		</div>
		<div class="modal-footer">
			<label style="font-size: 16px; float: left; margin-top: 5px;"></label>
			<div>
				<p class="style_p" style="font-size: 18px; float: left">数据刷新频率(秒/次)：</p>
				<select id="f5Select" style="float: left; font-size: 14px; height: 28px">
					<option>10</option>
					<option selected="selected">30</option>
				</select> <select id="threeSelect" style="float: right">
				</select>
				<p class="style_p" style="font-size: 20px; float: right">曲线切换：</p>
			</div>
		</div>
		<div id="container" style="min-width: 500px; height: 500px; max-width: 100%;"></div>
		<div class="modal-footer">
			<label style="font-size: 16px; float: left; margin-top: 5px;">&nbsp;实时数据详情：</label>
		</div>
		<div class="modal-footer" style="height: 200px">
			<table class="table table-bordered">
				<tr>
					<td>工作线程:
						<p id="WorkThread"></p>
					</td>
					<td>持续运行时间 秒:
						<p id="RunTime"></p>
					</td>
					<td>CPU占用百分比:
						<p id="CPU"></p>
					</td>
					<td>硬盘空间占用(GB):
						<p id="Disk"></p>
					</td>
					<td>内存占用(MB):
						<p id="MEM"></p>
					</td>
				</tr>
				<tr id="imShowRmsService" style="display: none">
					<td>已收到命令包数:
						<p id="InputCmds"></p>
					</td>
					<td>已响应命令包数:
						<p id="OutputCmds"></p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr id="imShowMdsService" style="display: none">
					<td>群组数量:
						<p id="GroupCount">
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr id="imShowMtsService" style="display: none">
					<td>客户端连接数量:
						<p id="ClientConnectionSum"></p>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>


			</table>
		</div>
	</div>
	<p id="curData" style="display: none"></p>
</body>
</html>

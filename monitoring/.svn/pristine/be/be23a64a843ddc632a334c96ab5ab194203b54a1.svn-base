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
<body ng-controller="ErrorCtrl">
	<div>
		<!-- 添加查询条件 -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<form class="form-inline">
					<h3 style="margin-top: 6px;">
						<span class="glyphicon glyphicon-bell" style="font-weight: 900">&nbsp;服务报警日志</span>
						<!-- 		<button type="button" data-toggle="modal" data-target="#myModal" ng-click="loadList()"
							style="font-size: 22px; background-color: transparent; float: right; border: none">
							<span class="glyphicon glyphicon-plus"></span>
						</button> -->
					</h3>
				</form>
			</div>
			<div style="float: left; width: 50%; margin-top: 15px;">
				<label for="dtp_input2" class="col-md-3 control-label">起始时间</label>
				<div class="input-group date form_date col-md-7" data-date="" data-date-format="yyyy-mm-dd"
					data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
					<input class="form-control" id="startTime" size="16" type="text" value="" readonly>
					<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			<div style="float: left; width: 50%; margin-top: 15px;">
				<label for="dtp_input2" class="col-md-3 control-label">结束时间</label>
				<div class="input-group date form_date col-md-7" data-date="" data-date-format="yyyy-mm-dd"
					data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
					<input class="form-control" id="endTime" size="16" type="text" value="" readonly>
					<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			<div style="float: left; width: 50%; margin-top: 15px;">
				<label for="dtp_input2" class="col-md-3 control-label">报警类型</label>
				<div >
					<select style="height: 30px;width: 44%; font-size: 12px" id="alertType">
						<option value="">--请选择--</option>
						<option value="PROBLEM">PROBLEM</option>
						<option value="OK">OK</option>
						<option value="FATALERROR">FATALERROR</option>
					</select>
				</div>
			</div>
			<div style="float: left; width: 50%; margin-top: 15px;">
				 <label class="col-md-3 control-label">服务类型</label>
				<div >
					<select style="height: 30px; width: 44%;font-size: 12px" id="serverType">
						<option value="">--请选择--</option>
						<option value="AvServerTransmitService">流媒体转发服务</option>
						<option value="AvServerControlService">流媒体控制服务</option>
						<option value="ImShowLbsService">LBS服务</option>
						<option value="ImShowMdsService">MDS服务</option>
						<option value="ImShowMtsService">MTS服务</option>
						<option value="ImShowTasService">TAS服务</option>
						<option value="ImShowUmsService">UMS服务</option>
						<option value="ImShowPrsService">PRS服务</option>
						<option value="ImShowMssService">MSS服务</option>
						<option value="ImShowRmsService">RMS服务</option>
						<option value="ImShowHttpProxyService">HTTPPROXY服务</option>
						<option value="PhpManageService">PHP管控</option>
						<option value="PhpLiveService">PHP直播</option>
						<option value="PhpVideoService">PHP点播</option>
						<option value="DcUserCenterService">用户中心</option>
						<option value="DcPayCenterService">支付中心</option>
						<option value="TranscodingService">转码服务</option>
						<option value="ScreenshotService">截图服务</option>
					</select>
					<button type="button" class="btn btn-primary" style="margin-left: 5px;" ng-click="showView()">查看</button>
				</div>
			</div>
			<div class="modal fade" id="alertModal" role="dialog">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">详细信息</h4>
						</div>
						<div class="modal-body" id="showDetail"></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
			<div style="float: left; width: 100%; margin-top: 15px;"></div>
			<div style="height: 750px; float: left; width: 100%;">
				<table class="table table-striped table-bordered table-hover">
					<tr>
						<th class="list-header">报警时间</th>
						<th class="list-header">报警服务</th>
						<th class="list-header">报警服务器IP</th>
						<th class="list-header">服务器ID</th>
						<th class="list-header">报警类型</th>
						<th class="list-header">报警信息摘要</th>
					</tr>
					<tr ng-repeat="alertererror in alertererrors ">
						<td class="list-header">{{alertererror.alertTime| date:'yyyy-MM-dd HH:mm:ss'}}</td>
						<td class="list-header">{{alertererror.serverType}}</td>
						<td class="list-header">{{alertererror.IP}}</td>
						<td class="list-header">{{alertererror.serverID}}</td>
						<td class="list-header">{{alertererror.alertType}}</td>
						<td class="list-header">
							<!-- <button type="button" class="btn btn-primary" style="margin-left: 5px;"
								ng-click="mouseenter(alertererror)">详情</button> --> <a ng-click="mouseenter(alertererror)">查看详情</a>
						</td>
					</tr>
				</table>
				<pagination></pagination>
			</div>
		</div>
		<jsp:include page="../common/common.jsp"></jsp:include>
		<script src="/error/errorList.js"></script>
</body>

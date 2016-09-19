<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<style>
.list-header {
	text-align: center
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
<div ng-controller="thresholdList">
	<!-- 添加查询条件 -->
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<h3 style="margin-top: 6px;">
				<span class="glyphicon glyphicon-cog" style="font-weight: 900">&nbsp;阀值配置&nbsp;</span>
			</h3>
		</div>
		<div class="modal-footer" style="background-color: #FFF">
			<label style="font-size: 16px; float: left; margin-top: 5px;">被监控系统：&nbsp;</label> 
			<select	style="height: 30px; font-size: 12px" id="serverType">
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
				<option value="TranscodingService">转码服务</option>
				<option value="ScreenshotService">截图服务</option>
			</select>
		</div>
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
		<div class="panel-body">
			<div>
				<form class="form-horizontal" style="margin-top: 10px;">
					<!-- 添加报警器选择 -->
					<div class="form-group">
						<label class="col-sm-3 control-label">关联报警器名称</label>
						<div class="col-sm-6">
							<ui-select multiple ng-model="multipleDemo.zkt" theme="bootstrap" ng-disabled="disabled"
								sortable="true" close-on-select="false" style="width: 80%;"> <ui-select-match
								placeholder="请选择报警器">{{$item.alerterName}}</ui-select-match> <ui-select-choices
								repeat="person in people | propsFilter: {alerterName: $select.search}">
							<div ng-bind-html="person.alerterName | highlight: $select.search"></div>
							<small> </small> </ui-select-choices> </ui-select>
							<!-- <p>Selected: {{multipleDemo.zkt}}</p> -->
						</div>
					</div>
					<div class="form-group" ng-repeat="threshold in thresholdNames">
						<label class="col-sm-3 control-label" style="display: none">{{threshold.key}}</label> <label
							class="col-sm-3 control-label">{{threshold.name}}</label>
						<div class="col-sm-5">
							<input type="text" ng-model="threshold.value" class="form-control" />
						</div>
					</div>
					<div class="modal-footer" style="height:100px">
						<button type="button" class="btn btn-primary" ng-click="saveThreshold(thresholdNames)">更新</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

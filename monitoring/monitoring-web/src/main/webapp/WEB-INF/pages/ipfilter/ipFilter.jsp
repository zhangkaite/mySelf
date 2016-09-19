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
<script src="/ipfilter/ipfilter.js"></script>
<body ng-controller="IpFilter">
	<div>
		<!-- 添加查询条件 -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<form class="form-inline">
					<h3 style="margin-top: 6px;">
						<span class="glyphicon glyphicon-list-alt" style="font-weight: 900">&nbsp;IP白名单</span>
						<button type="button" data-toggle="modal" data-target="#myModal" ng-click="loadList()"
							style="font-size: 22px; background-color: transparent; float: right; border: none">
							<span class="glyphicon glyphicon-plus"></span>
						</button>
					</h3>
				</form>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="list-header" style=" font-size: 18px">起始IP</th>
					<th class="list-header" style=" font-size: 18px">结束IP</th>
					<th class="list-header" style="font-size: 18px">修改</th>
					<th class="list-header" style="font-size: 18px">删除</th>
				</tr>
				<tr ng-repeat="ip in ipFilters ">
					<td class="display-none">{{ip.whiteID}}</td>
					<td class="list-header">{{ip.startIP}}</td>
					<td class="list-header">{{ip.endIP}}  </td>
					
					<td style="text-align: center"><a data-toggle="modal" ng-click="modifyip(ip)"
						data-target="#editModal">
						<span class="glyphicon glyphicon-pencil"></span>
					</a></td>
					
					<td style="text-align: center">
					<a ng-click="delip(ip)"> <span class="glyphicon glyphicon-remove"></span></a>
					</td>
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
			<!-- 添加用户信息模态框（Modal） -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">新增IP白名单</h4>
						</div>
						<div class="modal-body" style="width:800px;">
							<form class="form-horizontal" name="whiteIpForm" novalidate>
							<div class="form-group" style="padding-left:20px;">
							  <input type="checkbox" id="ckbox" onclick="showDispaly()">&nbsp;设置IP区间
							</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">开始IP</label>
									<div class="col-sm-8">
										<input type="text" name="ip1" ng-model="whiteip.ip1"  style="width:80px;" ng-keyup="changeNextIp(whiteip,0)" required >-
									    <input type="text" name="ip2" ng-model="whiteip.ip2" style="width:80px;" ng-keyup="changeNextIp(whiteip,0)" required >-
									    <input type="text" name="ip3"  ng-model="whiteip.ip3" style="width:80px;" ng-keyup="changeNextIp(whiteip,0)" required >-
									    <input type="text" name="ip4"  ng-model="whiteip.ip4"style="width:80px;" ng-keyup="changeNextIp(whiteip,0)" required >
									</div>
								</div>
								<div class="form-group" id="div2" style="display:none">
									<label class="col-sm-2 control-label">结束IP</label>
									<div class="col-sm-8">
										<input type="text" name="ip5" ng-model="whiteip.ip5" style="width:80px;" readonly  required >-
									    <input type="text" name="ip6" ng-model="whiteip.ip6" style="width:80px;" readonly required >-
									    <input type="text" name="ip7"  ng-model="whiteip.ip7"  style="width:80px;" readonly  required >-
									    <input type="text" name="ip8"  ng-model="whiteip.ip8" style="width:80px;"  required >
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="init()">关闭</button>
							<button type="button"  class="btn btn-primary" ng-click="saveIP(whiteip)">保存</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 修改模态对话框 -->
			
			<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="editModal">修改IP白名单</h4>
						</div>
						<div class="modal-body" style="width:800px;">
							<form class="form-horizontal" name="whiteIpForm" novalidate>
							<div class="form-group" style="padding-left:20px;">
							  <input type="checkbox" id="ckboxEdit" onclick="showDispalyEdit()">设置IP区间
							</div>
							<div class="form-group" style="display: none">
								<label class="col-sm-2 control-label">ID</label>
								<div class="col-sm-5">
									<input type="text" ng-model="whiteips.whiteID" required class="form-control">
									
								</div>
							</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">开始IP</label>
									<div class="col-sm-8">
										<input type="text" name="ip1" ng-model="whiteips.ip1"  style="width:80px;"  ng-keyup="changeNextIp(whiteips,1)" required >-
									    <input type="text" name="ip2" ng-model="whiteips.ip2" style="width:80px;"  ng-keyup="changeNextIp(whiteips,1)" required >-
									    <input type="text" name="ip3"  ng-model="whiteips.ip3" style="width:80px;"  ng-keyup="changeNextIp(whiteips,1)"  required >-
									    <input type="text" name="ip4"  ng-model="whiteips.ip4"style="width:80px;"   required >
									</div>
								</div>
								<div class="form-group" id="div2Edit" style="display:none">
									<label class="col-sm-2 control-label">结束IP</label>
									<div class="col-sm-8">
										<input type="text" name="ip5" ng-model="whiteips.ip5" style="width:80px;" readonly  required >-
									    <input type="text" name="ip6" ng-model="whiteips.ip6" style="width:80px;" readonly required >-
									    <input type="text" name="ip7"  ng-model="whiteips.ip7"  style="width:80px;" readonly required >-
									    <input type="text" name="ip8"  ng-model="whiteips.ip8" style="width:80px;"  required >
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="init()">关闭</button>
							<button type="button"  class="btn btn-primary" ng-click="updateIP(whiteips)">更新</button>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			
		</div>
	</div>
</body>

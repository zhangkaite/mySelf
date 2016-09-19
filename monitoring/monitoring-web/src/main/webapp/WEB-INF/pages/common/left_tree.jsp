<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#main-nav {
	margin-left: 1px;
}

#main-nav.nav-tabs.nav-stacked>li>a {
	padding: 10px 8px;
	font-size: 15px;
	font-weight: 600;
	color: #4A515B;
	background: #E9E9E9;
	background: -moz-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #FAFAFA),
		color-stop(100%, #E9E9E9));
	background: -webkit-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
	background: -o-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
	background: -ms-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
	background: linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA',
		endColorstr='#E9E9E9');
	-ms-filter:
		"progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9')";
	border: 1px solid #D5D5D5;
	border-radius: 4px;
}

#main-nav.nav-tabs.nav-stacked>li>a>span {
	color: #4A515B;
}

#main-nav.nav-tabs.nav-stacked>li.active>a, #main-nav.nav-tabs.nav-stacked>li>a:hover
	{
	color: #FFF;
	background: #3C4049;
	background: -moz-linear-gradient(top, #4A515B 0%, #3C4049 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #4A515B),
		color-stop(100%, #3C4049));
	background: -webkit-linear-gradient(top, #4A515B 0%, #3C4049 100%);
	background: -o-linear-gradient(top, #4A515B 0%, #3C4049 100%);
	background: -ms-linear-gradient(top, #4A515B 0%, #3C4049 100%);
	background: linear-gradient(top, #4A515B 0%, #3C4049 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B',
		endColorstr='#3C4049');
	-ms-filter:
		"progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B', endColorstr='#3C4049')";
	border-color: #2B2E33;
}

#main-nav.nav-tabs.nav-stacked>li.active>a, #main-nav.nav-tabs.nav-stacked>li>a:hover>span
	{
	color: #FFF;
}

#main-nav.nav-tabs.nav-stacked>li {
	margin-bottom: 4px;
}

/*定义二级菜单样式*/
.secondmenu a {
	font-size: 15px;
	color: #4A515B;
	text-align: center;
}

.navbar-static-top {
	background-color: #212121;
	margin-bottom: 5px;
}

.navbar-brand {
	background: url('') no-repeat 10px 8px;
	display: inline-block;
	vertical-align: middle;
	padding-left: 50px;
	color: #fff;
}
</style>
<ul id="main-nav" class="nav nav-tabs nav-stacked" style="padding: 20px 0px 20px 0px">
	<li class="active"><a href="/errorLog/list" url="/errorLog/list"> <i class="glyphicon glyphicon-th-large"></i> 报警日志
	</a></li>
	<li><a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse"> <i class="glyphicon glyphicon-home"></i>
			系统管理
	</a>
		<ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<c:if test="${sessionScope.userName=='admin'}">
				<li><a href="/userList" url="/userList"> <i class="glyphicon glyphicon-user"></i> 用 户 管 理
				</a></li>
			</c:if>
			<li><a href="/modifyPasswd" url="/modifyPasswd"> <i class="glyphicon glyphicon-user"></i> 修 改 密 码
			</a></li>
		</ul></li>
	<!-- <li><a href="#appSetting" class="nav-header collapsed" data-toggle="collapse"> <i class="glyphicon glyphicon-cog"></i>
			配置管理
	</a>
		<ul id="appSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<li><a href="/burglar/list" url="/burglar/list"> <i class="glyphicon glyphicon-bell"></i> 报警器配置
			</a></li>
			<li><a href="/threshold/list" url="/threshold/list"> <i class="glyphicon glyphicon-cog"></i> 阀 值 配 置
			</a></li>
			<li><a href="/ipFilter/list" url="/ipFilter/list"> <i class="glyphicon glyphicon-list-alt"></i> IP白名单配置
			</a></li>
		</ul></li> -->
<!-- 	<li><a href="#charts" class="nav-header collapsed" data-toggle="collapse"> <i class="glyphicon glyphicon-random"></i>
			实时监控曲线
	</a>
		<ul id="charts" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<li><a href="/mf/mfChart" url="/mf/mfChart"> <i class="glyphicon glyphicon-facetime-video"></i> 视 频 服 务
			</a></li>
			<li><a href="/php/mfChart" url="/php/mfChart"> <i class="glyphicon glyphicon-comment"></i> P H P 服 务
			</a></li>
			<li><a href="/im/mfChart" url="/im/mfChart"> <i class="glyphicon glyphicon-music"></i> IM&秀场 服 务
			</a></li>
			<li><a href="/im/imDataList" url="/im/imDataList"> <i class="glyphicon glyphicon-music"></i> IM&秀场实时数据
			</a></li>
			<li><a href="/otherService/mfChart" url="/otherService/mfChart"> <i class="glyphicon glyphicon-asterisk"></i> 其 他 服 务
			</a></li>
		</ul></li> -->
	<li><a href="#serviceMonitor" class="nav-header collapsed" data-toggle="collapse"> <i class="glyphicon glyphicon-random"></i>
			服务监控
	</a>
		<ul id="serviceMonitor" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<li><a href="/collection/list" url="/collection/list"> <i class="glyphicon glyphicon-facetime-video"></i> CPU监控
			</a></li>
		</ul></li>
	<li><a href="#"> <i class="glyphicon glyphicon-fire"></i> 关于系统
	</a></li>
</ul>
<script>
	$(function() {
		$("#main-nav a").click(function(e) {

			e.preventDefault();
			var url = $(this).attr("url");
			$("#iframe").attr("src", url);
		});
	});
</script>
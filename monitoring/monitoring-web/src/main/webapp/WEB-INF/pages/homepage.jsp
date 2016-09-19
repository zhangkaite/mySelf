<%--
  Created by IntelliJ IDEA.
  User: wang
  Date: 15/9/14
  Time: 下午12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>

<style type="text/css">
html {
	overflow-y: hidden;
}
</style>
<title></title>
</head>
<body>
	<jsp:include page="common/header.jsp"></jsp:include>
	<div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2" style="overflow: auto; height: 100%; background-color: #e9e9e9;float:left">
					<jsp:include page="common/left_tree.jsp"></jsp:include>
				</div>
				<div class="col-md-10" style="float:left;overflow: auto;margin-top:8px;">
					<iframe id="iframe" src="/errorLog/list" frameborder="0" style="width:100%;height:80%;"> </iframe>
				</div>
			</div>
		</div>
	</div>
	<%-- <jsp:include page="common/footer.jsp"></jsp:include> --%>
</body>

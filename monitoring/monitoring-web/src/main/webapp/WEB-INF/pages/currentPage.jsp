<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String userName = (String) session.getAttribute("userName");
	if (userName == null) {
%>
<script type="text/javascript">
	parent.location.href = "/";
</script>
<%
	}
%>


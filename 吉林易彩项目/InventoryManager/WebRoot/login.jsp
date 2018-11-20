<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String projectName = application.getInitParameter("projectName");
	String iconPath = application.getInitParameter("iconPath");
	String copyRight = application.getInitParameter("copyRight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title><%=projectName %></title>
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="css/login/login.css"/>
		<link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
		<link rel="shortcut icon" href=<%=iconPath %> type="image/x-icon"/>
		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" src="js/login/login.js"></script>
		<script language=javascript>
			if(top.location!=self.location){
				top.location = self.location;
			}
		</script>
		<style type="text/css">
			.i-script{
				background-image:url(<%=iconPath %>) !important;
				background-repeat:no-repeat;
				background-position:left;
				margin-right:20xp;
			}
		</style>
		<body><input type="hidden" id="projectName" value=<%=projectName %>/></body>
</html>

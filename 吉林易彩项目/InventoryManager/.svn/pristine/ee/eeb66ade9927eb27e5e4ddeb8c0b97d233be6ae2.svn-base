<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="manage.user.pojo.UserInfoBean"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	UserInfoBean userInfoBean=(UserInfoBean)request.getSession().getAttribute("userBean");
	String projectName=application.getInitParameter("projectName");
	String iconPath=application.getInitParameter("iconPath");
	String copyRight=application.getInitParameter("copyRight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title><%=projectName %></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">
		<link rel="stylesheet" type="text/css" href="css/login/login.css">
    	<link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
		<link rel="shortcut icon" href=<%=iconPath %> type="image/x-icon" />
		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" src="js/menu/menutoolbar.js"></script>
		<script type="text/javascript" src="js/base/util/redirectToPages.js"></script>
		<script type="text/javascript" src="js/main/main.js"></script>
		<style type="text/css">
			.mainIcon{
				background-image: url(<%=iconPath %>) !important;
				background-repeat: no-repeat;
				background-position: left;
				width: 23px;
				height: 16px;
			}
		</style>
		<script type="text/javascript">
  			var context_path = '${pageContext.request.contextPath}';
		</script>
  	</head>
  	<body>
  		<input id="url" type="hidden" value="/pages/report/resReport.jsp">
  		<input id="userArea" type="hidden" value="<%=userInfoBean.getAreaName() %>">
  		<input id="userRealName" type="hidden" value="<%=userInfoBean.getRealname() %>">
  		<input id="userName" type="hidden" value="<%=userInfoBean.getUsername() %>">
  		<input id="projectName" type="hidden" value="<%=projectName %>">
  		<input id="copyRight" type="hidden" value="<%=copyRight %>">
  		<div id="pnNorthBody"></div>
  	</body>
</html>

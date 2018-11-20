<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>站点光交资源</title>
	
	<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="css/userList.css" />
	<link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
	<link rel="stylesheet" type="text/css" href="js/ext/resources/css/multiselect.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css" />
	<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/pages/approval/site/js/stationInfo.js"></script>
	<script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
		var resId =  '<%=request.getParameter("resId") %>';
		var resType = '<%=request.getParameter("resType") %>';
		var resName = decodeURI('<%=request.getParameter("resName") %>');
	</script>
  </head>
  
  <body>
  	<body>
		<div id='leftDiv'></div>
		<div id='centerDiv'></div>
		<div id='rightDiv'></div>
	</body>
  </body>
</html>

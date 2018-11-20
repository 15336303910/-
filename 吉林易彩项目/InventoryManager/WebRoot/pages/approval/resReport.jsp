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
    	<pre style="word-wrap: break-word; white-space: pre-wrap;">
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>统计报表</title>
	    <script type="text/javascript">
			var context_path = '${pageContext.request.contextPath}';
		</script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ext/resources/css/ext-all.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ext/resources/css/multiselect.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/xtheme-gray.css" />
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/adapter/ext/RowExpander.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/adapter/ext/DDView.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/adapter/ext/MultiSelect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext/adapter/ext/ItemSelector.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/base/util/util.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/base/util/help.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json/json2.js"></script>
		<!--本地文件-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/approval/resReport.js"></script>
		<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/js/common/common.js"></script>
		<script>
		var request;
		</script>
  	</head>
  	<body>
	   	<input type="hidden" id="pageSize" value="20"/>
		<input type="hidden" id="limit" value="20"/>
  	</body>
</html>
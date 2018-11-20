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
	    <title>站点数据</title>
	    <style>
		.x-grid-record-red table{
  	 		background: #E6D1E3;
		}
		.x-grid-record-yellow table{
   			background: #F3FEC2;
		}
		.x-grid-record-green table{
   			background: #92FCCC;
		}	
</style>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
		<!--本地文件-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/approval/siteApproval.js"></script>
		<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/js/common/common.js"></script>
		<script>
		var request;
		var jsonStr = '${jsonStr}';
		var data=eval("("+jsonStr+")");
		var generatId =  '<%=request.getParameter("generatId") %>';
		var approval = '<%=request.getParameter("approval") %>';
		</script>
  	</head>
  	<body>
	   	<input type="hidden" id="pageSize" value="20"/>
		<input type="hidden" id="limit" value="20"/>
  	</body>
</html>
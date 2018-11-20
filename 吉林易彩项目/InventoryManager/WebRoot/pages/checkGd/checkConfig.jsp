<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>配置管理</title>
	    <script type="text/javascript">
			var context_path = '${pageContext.request.contextPath}';
		</script>
	    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
	    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/multiselect.css" />
	    <link rel="stylesheet" type="text/css" href="css/base.css" />
	    <link rel="stylesheet" type="text/css" href="css/xtheme-gray.css" />
	    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" src="js/ext/adapter/ext/RowExpander.js"></script>
		<script type="text/javascript" src="js/ext/adapter/ext/DDView.js"></script>
		<script type="text/javascript" src="js/ext/adapter/ext/MultiSelect.js"></script>
		<script type="text/javascript" src="js/ext/adapter/ext/ItemSelector.js"></script>
		<script type="text/javascript" src="js/base/util/util.js"></script>
		<script type="text/javascript" src="js/base/util/help.js"></script>
		<!--本地文件-->
		<script type="text/javascript" src="js/checkGd/config/configIndex.js"></script>
		<script type="text/javascript" src="js/checkGd/config/editConfigObject.js"></script>
		<script type="text/javascript" src="js/checkGd/basic/basicWindow.js"></script>	
  	</head>
  	<body>
	   	<input type="hidden" id="pageSize" value="20"/>
		<input type="hidden" id="limit" value="20"/>
  	</body>
</html>

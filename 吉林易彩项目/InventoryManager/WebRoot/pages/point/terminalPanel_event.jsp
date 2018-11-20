<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="js/point/terminalPanel_event.js"></script>
		<script type="text/javascript">
		
		</script>
	</head>
	<body>
	<div id="alarmPanel" style="width:100%; height:100%">
		<input type="hidden" id="alarmIndex" value="1" />
		<input type="hidden" id="lastId" value="0"> 
	</div>
	<iframe style="display: none;" src="pages/point/getEvent.jsp"></iframe>
	</body>
</html>

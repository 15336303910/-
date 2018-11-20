<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>工单统计</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
   	<link rel="stylesheet" type="text/css" href="css/base.css" />
   	
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/multiselect.css" />
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/TreeComboBox.js"></script>
	<script type="text/javascript" src="js/base/util/util.js"></script>
	<script src="js/jquery/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/statistics/orderstatisticsbyuser.js"></script>
</head>
  
<body id="orderCenter">
	<input type="hidden" id="pageSize" value="20"/>
   	<input type="hidden" id="limit" value="20"/>
	<input type="hidden" id="areano" value=""/>
	<input type="hidden" id="eid" value=""/>
</body>
</html>
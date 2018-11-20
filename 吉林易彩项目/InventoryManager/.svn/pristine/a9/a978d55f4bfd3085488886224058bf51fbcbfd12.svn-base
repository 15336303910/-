<%@page import="manage.user.pojo.UserInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String domainId = request.getParameter("domainId");

if(domainId==null || domainId.equals("")){
	UserInfoBean userBean = (UserInfoBean)request.getSession().getAttribute("userBean");
	domainId = userBean.getDomainid()+"";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="js/ext4.2/css/example.css" />
	<link rel="stylesheet" href="css/demo.css" type="text/css"> 
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
	
    <style type="text/css">
       body {
	    padding:0px;
	    padding-top:0px;
	}
    </style>
    <script src="js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="js/jquery/com/selectArea.js"></script>
    <script type="text/javascript" src="js/ext4.2/include-ext.js?theme=gray"></script>
    <script type="text/javascript" src="js/statistics/statisticReport.js"></script>
    <script type="text/javascript" src="js/base/util/help.js"></script>
  </head>
  <body>
  <div id="stationTreeContent" class="menuContent" style="display:none; z-index:1000; position: absolute;">
  	<ul id="stationTree" class="ztree" style="margin-top:0; width:160px;"></ul>
  </div>
  <input type="hidden" id="domainId" value="<%=domainId %>">
</body>
</html>

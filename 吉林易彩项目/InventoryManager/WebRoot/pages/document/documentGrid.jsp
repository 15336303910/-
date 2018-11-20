<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="css/userList.css" />
	<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/base/util/util.js"></script>
	<script type="text/javascript" src="js/document/documentManageGrid.js"></script>
    <script type="text/javascript" src="js/document/documentWindow.js"></script>
    <script type="text/javascript" src="js/document/addDocumentFormPanel.js"></script>
    <script type="text/javascript" src="js/document/addDocumentWorkorder.js"></script>
    <script type="text/javascript" src="js/document/docPointGrid.js"></script>
    <script type="text/javascript" src="js/document/queryDocumentFormPanel.js"></script>
    <script type="text/javascript" src="js/document/dataImportFormPanel.js"></script>
  </head>
  
  <body>
	<%String str=request.getParameter("domainName");
	%>
    <input type="hidden" id="pageSize" value="20"/>
	<input type="hidden" id="limit" value="20"/>
	<input type="hidden" id="userid" value=""/>
	<input type="hidden" id="domainCode" value="${domainCode}"/>
	<input type="hidden" id="domainName" value="<%=str %>"/>
  </body>
</html>

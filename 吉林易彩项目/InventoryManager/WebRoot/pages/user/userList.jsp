<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String domainCode = new String(request.getParameter("domainCode"));
String domainName = new String(request.getParameter("domainName"));
request.setAttribute("domainCode",domainCode);
request.setAttribute("domainName",domainName);
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="css/workorder/orderGrid.css" />
	<link rel="stylesheet" type="text/css" href="css/userList.css" />
	<link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
	
	<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/base/util/util.js"></script>
	<script type="text/javascript" src="js/user/userManageGrid.js"></script>
	<script type="text/javascript" src="js/user/userWindow.js"></script>
	<script type="text/javascript" src="js/user/addUserFormPanel.js"></script>
	<script type="text/javascript" src="js/user/modifyUserFormPanel.js"></script>
	<script type="text/javascript" src="js/user/queryUserFormPanel.js"></script>
	<script type="text/javascript" src="js/user/userck/userCkWindow.js"></script>
	<script type="text/javascript" src="js/user/userck/userCkFormPanel.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/TreeComboBox.js"></script>
	<script type="text/javascript" src="js/user/roleListFormPanel.js"></script>
	<script type="text/javascript" src="js/user/addRoleFormPanel.js"></script>
	<script type="text/javascript" src="js/user/modifyRoleFormPanel.js"></script>
	<script type="text/javascript" src="js/user/groupMagFormPanel.js"></script>
	<script type="text/javascript" src="js/user/groupAdd.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/js/common/common.js"></script>
	<script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
		var domainCode = '${domainCode}';
	</script>
  </head>
  
  <body>
  	 <div id="userList">
  	 <%	String str=request.getParameter("domainName");

  	 
  	  %>
		<input type="hidden" id="pageSize" value="20"/>
		<input type="hidden" id="limit" value="20"/>
		<input type="hidden" id="userid" value=""/>
		<input type="hidden" id="domainCode" value="${domainCode}"/>
		<input type="hidden" id="domainName" value="<%=str %>"/>
	</div>
  </body>
</html>

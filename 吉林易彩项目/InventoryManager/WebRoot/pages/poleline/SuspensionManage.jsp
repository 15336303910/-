<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/multiselect.css" />
    <link rel="stylesheet" type="text/css" href="css/base.css" />
    <link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
    <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
	</script>
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/RowExpander.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/DDView.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/MultiSelect.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/ItemSelector.js"></script>
	<script type="text/javascript" src="js/base/util/util.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/poleline/SuspensionGrid.js"></script>
	<script type="text/javascript" src="js/poleline/importSuspension.js"></script>
	<script type="text/javascript" src="js/poleline/importSuspensionseg.js"></script>
	<script type="text/javascript" src="js/poleline/poleline/SuspensionsegGrid.js"></script>
	<script type="text/javascript" src="js/poleline/polelineWindow.js"></script>
	
  </head>
  
  <body>
    <input type="hidden" id="pageSize" value="10"/>
	<input type="hidden" id="limit" value="10"/>
  </body>
</html>

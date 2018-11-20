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
    <title>智能电子标签管理系统</title>
    <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
	</script>
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/multiselect.css" />
    <link rel="stylesheet" type="text/css" href="css/base.css" />
    <link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
    
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/RowExpander.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/DDView.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/MultiSelect.js"></script>
	<script type="text/javascript" src="js/ext/adapter/ext/ItemSelector.js"></script>
	<script type="text/javascript" src="js/base/util/util.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/pipe/pipesegment/pipeSegmentManageGrid.js"></script>
	<script type="text/javascript" src="js/pipe/pipesegment/addPipeSegmentGrid.js"></script>
	<script type="text/javascript" src="js/pipe/pipesegment/modifyPipeSegmentGrid.js"></script> 
	<script type="text/javascript" src="js/pipe/pipesegment/addPipeBySeg.js"></script> 
	<script type="text/javascript" src="js/pipe/pipesegment/importPipeseg.js"></script> 
	<script type="text/javascript" src="js/poleline/polelineWindow.js"></script>
	<script type="text/javascript" src="js/pipe/part/poleGrid_aa.js"></script>
	<script type="text/javascript" src="js/pipe/part/poleGrid_zz.js"></script>
	<script type="text/javascript" src="js/pipe/part/wellGrid_aa.js"></script>
	<script type="text/javascript" src="js/pipe/part/wellGrid_zz.js"></script>
	<script type="text/javascript" src="js/pipe/part/poleGrid_as.js"></script>
	<script type="text/javascript" src="js/pipe/part/poleGrid_zs.js"></script>
	<script type="text/javascript" src="js/pipe/part/wellGrid_as.js"></script>
	<script type="text/javascript" src="js/pipe/part/wellGrid_zs.js"></script>
	<script type="text/javascript" src="js/pipe/part/pipeGrid.js"></script>
	<script type="text/javascript" src="js/pipe/util/pipeUtil.js"></script>
	<script type="text/javascript" src="js/pipe/part/selectWellface.js"></script>
	<script type="text/javascript" src="js/pipe/part/face.js"></script>
	<script type="text/javascript" src="js/pipe/part/faceNumber.js"></script>
	<script type="text/javascript" src="js/pipe/part/faceConfirm.js"></script>
	<script type="text/javascript" src="js/pipe/part/pipeBySeg.js"></script>
	<script type="text/javascript" src="js/base/util/help.js"></script>
	
  </head>
  <body>
  	<input type="hidden" id="pipeId" value="<%=request.getParameter("pipeId")%>">
   	<input type="hidden" id="pageSize" value="20"/>
	<input type="hidden" id="limit" value="20"/>
  </body>
</html>

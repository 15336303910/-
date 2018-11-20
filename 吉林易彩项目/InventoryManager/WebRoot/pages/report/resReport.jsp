<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html lang="en">
<head>
    <title>资源报表</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
    <!--[if lte IE 7]>
    <script type="text/javascript" src="print.js"></script>
    <![endif]-->
	<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/echarts/doc/asset/js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/echarts/doc/asset/js/esl/esl.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/resReport.js" charset="utf-8"></script>
</head>
<script type="text/javascript">
  var context_path = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript">
	$(function(){
		var request;
		ajaxLoad("/reportAction!getResReport.action",loadDemo);
	});
</script>
<body>
	<div id="main" style="height:550px;width:820px;border:solid 1px blue;border-radius:10px;margin-top:10px;margin-left:250px;margin-bottom: 10px;margin-right: 150px"></div>
</body>
</html>


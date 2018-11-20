<!DOCTYPE html>
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
    
    <title>图片呈现</title>
     <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
  	</script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/css/bootstrap.min.css" /> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/css/bootstrap-datetimepicker.min.css" /> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery/slider/jquery.slideBox.css" /> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/slider/jquery.sliderBox.js" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/js/bootstrap.min.js" /> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/js/bootstrap-datetimepicker.min.js" /> 
	<script type="text/javascript">
		var id =  '<%=request.getParameter("id") %>';
		var type = '<%=request.getParameter("resType") %>';
		$(function(){
			$('#demo4').slideBox({
			});
		});
	</script>
  </head>

<body>
	<div id="demo4" class="slideBox">
		<ul class="items">
			<li><a href="http://www.jq22.com/" title="这里是测试标题一"><img src="<%=request.getContextPath()%>/1.jpg"></a></li>
			<li><a href="http://www.jq22.com/" title="这里是测试标题二"><img src="<%=request.getContextPath()%>/2.jpg"></a></li>
		</ul>
	</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>资源图片</title>
		<link href="<%=request.getContextPath()%>/css/slideBox/jquery.slideBox.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/slideBox/jquery.slideBox.js" charset="utf-8"></script>
		<script type="text/javascript">
			var context_path = '${pageContext.request.contextPath}';
			var id =  '<%=request.getParameter("id") %>';
			var type = '<%=request.getParameter("resType") %>';
			$(document).ready(function() {
				$.ajax({
					type:'post',
					url:"<%=basePath%>"+"approvalAction!getResImage.action?resId="+id+"&&type="+type,
					dataType:'json',
					success:function(result) {
						var objArr = result.imgStr;
						for (var i=0; i<objArr.length; i++) {
							var imageName = objArr[i].ImageName;
							var imagePath = objArr[i].ImagePath;
							var li = $("<li><a href='javascript:volid(0)' ><img width='450' height='337' src='"+imagePath+"'></a></li>");
							$(".items").append(li);
						}
						$('#demo1').slideBox();
					}
				});
			});
		</script>
	</head>
	<body>
		<div id="demo1" class="slideBox">
		  	<ul class="items">
		  	</ul>
		</div>
	</body>
</html>
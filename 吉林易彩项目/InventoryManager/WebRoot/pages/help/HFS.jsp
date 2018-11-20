<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   
  </head>
  
  <body>
     <img src="imgs/help/HFS1.png" width="500"/><br/>
	1.全部刷新：单击刷新表单。<br/>
	2.表主体：双击任意字段可查看详细信息。如图<br/>
	<img src="imgs/help/HFS2.png" width="500"/>
  </body>
</html>

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
    <img width="500" src="imgs/help/resourcecheck1.png"><br>
	1.资源核查：单击左侧资源核查按钮，进入发送核查任务界面。如图<br>
	<img width="500" src="imgs/help/resourcecheck3.png"><br>
	2.查看报表：选中一条记录单击查看报表，可查看选中核查人的任务状态统计图及历史任务统计。如图<br>
	<img width="500" src="imgs/help/resourcecheck6.png"><br>
	3.表主体：双击一条信息可查看此核查人所要核查的资源及核查过的资源。如图<br>
	<img width="500" src="imgs/help/resourcecheck2.png"><br>
	4：全部刷新：在资源核查页面，点击全部刷新，刷新当页面。<br>
  </body>
</html>

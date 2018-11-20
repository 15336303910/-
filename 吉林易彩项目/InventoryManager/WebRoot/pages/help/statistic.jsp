<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>
  
  <body>
    <img width="500" src="../../imgs/help/statistic1.png"><br/>
    1.统计维度：可在地区与人员间切换。<br/>
    2.可选择区域：在图中可选择区域选择一条记录，可在右侧显示区域查看当前记录的信息和统计图。<br/>
    3.搜索：在选择某一地区后点击搜索按钮，可在页面现在此地区下属地区的所有资源信息及统计图。
  </body>
</html>

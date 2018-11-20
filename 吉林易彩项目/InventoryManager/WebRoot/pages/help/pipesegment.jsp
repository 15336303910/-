<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
  <img width="500" src="imgs/help/pipeseg1.png"><br>
  1.添加：单击添加按钮，进入添加页面。在添加页面中可进行批量添加。如图<br>
  <img width="500" src="imgs/help/pipeseg2.png"><br>
  2.修改：在表中选择一条记录后单击修改按钮，可修改当前记录并保存。如图<br>
  <img width="500" src="imgs/help/pipeseg3.png"><br>
  3.删除： 选择一条数据后单击删除按钮会提示用户是否确认删除。如图，点确认删除信息<br>
  <img width="500" src="imgs/help/delete.png"> <br>
  4.定位：选择一条记录后单击定位后可查看此管道段在地图上位置。<br>
  5.生成管道：单击生成管道按钮，进入生成管道界面。如图<br>
  <img width="500" src="imgs/help/pipeseg4.png"><br>
  6.全部刷新：在管道段管理页面，点击全部刷新，刷新当页面。<br>
  7.条件查询：根据所填或所选条件，搜索需要的管道段。<br>
  </body>
</html>

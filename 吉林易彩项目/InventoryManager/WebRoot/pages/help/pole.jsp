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
    <img width="500" src="imgs/help/pole1.png"><br>
  1.添加：单击添加按钮，进入添加页面。在添加页面中可进行批量添加。如图<br>
  <img width="500" src="imgs/help/pole2.png"><br>
  2.修改：在表中选择一条记录后单击修改按钮，可修改当前记录并保存。如图<br>
  <img width="500" src="imgs/help/pole3.png"><br>
  3.删除：选择一条数据后单击删除按钮会提示用户是否确认删除。如图，点确认删除信息<br>
 <img width="500" src="imgs/help/delete.png"> <br>
  4.导入数据：单击导入数据按钮，进入导入数据页面。如图<br>
  <img width="500" src="imgs/help/pole4.png"><br>
	导入数据成功必要条件：1.文件为“.xls”后缀；2.文件格式与下载模板或导出数据一致。点击“+”可进行批量导入功能（建议：一次导入控制在最多5个文件左右）<br>
  5.导出数据：单击导出数据按钮，进入导出数据页面。在此页面可导出所选局站下所有电杆信息。如图<br>
  <img width="500" src="imgs/help/pole5.png"><br>
  6.定位：选择一条记录后单击定位后可查看此电杆在地图上位置。<br>
  7.全部刷新：在电杆管理页面，点击全部刷新，刷新当页面。<br>
  8.条件查询：根据所填或所选条件，搜索需要的电杆。<br>
  </body>
</html>

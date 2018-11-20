<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
     <link rel="stylesheet" type="text/css" href="js/ext4.2/css/example.css" />
     <link rel="stylesheet" type="text/css" href="js/ext4.2/css/tabs.css" />
     <script type="text/javascript" src="js/ext4.2/include-ext.js"></script>
     <script type="text/javascript" src="js/resource/report2.js"></script>
  </head>
  
  <body>
    <%String str=request.getParameter("str");
      str=str.replace("|", "\'");
      str = str.replaceAll("\'name\'", "name");
 	  str = str.replaceAll("\'num\'", "num");
     %>
    <input type="hidden" id="str" value="<%=str %>"/>
  </body>
</html>

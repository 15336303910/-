<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看报表</title>
      <link rel="stylesheet" type="text/css" href="js/ext4.2/css/example.css" />
      
    <link rel="shortcut icon" href="imgs/lt_logo.png" type="image/x-icon" />
     <script type="text/javascript" src="js/ext4.2/include-ext.js"></script>
     <script type="text/javascript" src="js/equt/reportPoint.js"></script> 
	
  </head>
  
  <body>
 <%String str=(String)request.getAttribute("json");
 	//str=new String(str.getBytes("ISO-8859-1"),"UTF-8");
 	str = str.replaceAll("\"", "'");
 	str = str.replaceAll("\'name\'", "name");
 	str = str.replaceAll("\'num\'", "num");
 %>
 <input type="hidden" id="report" value="<%=str %>"/>
 
 <div style="position:absolute; left:25px; top:80px; height:200px; width:300px;z-index:9999;">
  <table width="200" border="1" style="position:absolute; width: 290px; border-color:#CCC; border-collapse:collapse;">
    <tr>
      <td width="48" style="background-color:#147ECD"><font size="1" color="#FFFFFF">设备名称</font></td>
      <td colspan="5"><font size="1"><s:property value="%{equtInfoBean.ename}"/></font></td>
    </tr>
    <tr>
      <td style="background-color:#147ECD"><font size="1" color="#FFFFFF">设备地址</font></td>
      <td colspan="5"><font size="1"><s:property value="%{equtInfoBean.eaddr}"/></font></td>
    </tr>
    <tr>
      <td rowspan="3" style="background-color:#147ECD"><font size="1" color="#FFFFFF">端子类型</font></td>
      <td width="38" align="center"><font size="1">空闲</font></td>
      <td width="43" align="center"><font size="1">故障</font></td>
      <td width="43" align="center"><font size="1">可用</font></td>
      <td width="43" align="center"><font size="1">占用</font></td>
      <td width="47" align="center"><font size="1">待核查</font></td>
    </tr>
    <tr>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.alarmCount}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.areaName}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.cstate}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.did}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.domainName}"/></font></td>
    </tr>
    <tr>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.areaId}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.areanoStr}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.cuser}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.dir}"/></font></td>
      <td align="center"><font size="1"><s:property value="%{equtInfoBean.lath}"/></font></td>
    </tr>
  </table>
</div>
 
 
 
 
 
  </body>
</html>

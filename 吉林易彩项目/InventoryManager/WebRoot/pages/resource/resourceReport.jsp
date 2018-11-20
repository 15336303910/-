<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
     <link rel="stylesheet" type="text/css" href="js/ext4.2/css/tabs.css" />
     <link rel="shortcut icon" href="imgs/lt_logo.png" type="image/x-icon" />
     <script type="text/javascript" src="js/ext4.2/include-ext.js"></script>
     <script type="text/javascript" src="js/resource/showReport.js"></script>

  </head>
  
  <body>
    <%String str=request.getParameter("str");
    String str1=(String)request.getAttribute("json1"); 
    str=str.replaceAll("\'", "|");
    str1=str1.replaceAll("\"", "|");
    %>
    <input type="hidden" id="str" value="<%=str %>"/>
    <input type="hidden" id="str1" value="<%=str1 %>"/>
    <table width="820" border="1" style="border-color:#CCC; border-collapse:collapse;">
	  <tr>
	    <th colspan="7" scope="col" align="center"  style="background-color:#147ECD"><font face="黑体" size="+2" color="#FFFFFF">资源统计表</font></th>
	  </tr>
	  <tr>
	    <td width="122" style="background-color:#147ECD"><font color="#FFFFFF">所属地区</font></td>
	    <td width="113" align="center"><s:property value="%{sta.domianName}"/></td>
	    <td width="113" style="background-color:#147ECD"><font color="#FFFFFF">总资源数</font></td>
	    <td width="113" align="center"><s:property value="%{sta.total}"/></td>
	    <td width="113">&nbsp;</td>
	    <td width="113">&nbsp;</td>
	    <td width="113">&nbsp;</td>
	  </tr>
	  <tr>
	    <td style="background-color:#147ECD"><font color="#FFFFFF">资源类型</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">局站</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">机房</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">设备</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">电源</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">井</font></td>
	    <td style="background-color:#147ECD" align="center"><font color="#FFFFFF">电杆</font></td>
	  </tr>
	  <tr>
	    <td style="background-color:#147ECD"><font color="#FFFFFF">总数</font></td>
	    <td align="center"><s:property value="%{sta.stationNum}"/></td>
	    <td align="center"><s:property value="%{sta.generatorNum}"/></td>
	    <td align="center"><s:property value="%{sta.equtNum}"/></td>
	    <td align="center"><s:property value="%{sta.HFSNum}"/></td>
	    <td align="center"><s:property value="%{sta.wellNum}"/></td>
	    <td align="center"><s:property value="%{sta.poleNum}"/></td>
	  </tr>
	</table>
    <div id="tab">
   		<div id="tab1" class="x-hide-display"></div>
	  	<div id="tab2" class="x-hide-display"></div>
    </div>
  </body>
</html>

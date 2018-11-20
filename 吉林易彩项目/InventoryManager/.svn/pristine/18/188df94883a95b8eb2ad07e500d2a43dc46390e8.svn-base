<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.File"%>  
<%@ page import="java.io.*"%>
<%@page import="java.net.URLDecoder"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


String root = getServletContext().getRealPath("/");      //得到网站绝对路径  
//String filepath = "upload_file\\lwfile\\";                        //设置文件存放的相对路径(windows)  
//String filepath = "tmpl/";                             //设置文件存放的相对路径(linux)  
String fileName = request.getParameter("filename");    //得到文件名  
//fileName=URLDecoder.decode(fileName);
String realfilename=fileName.replace("tmpl/",""); 
realfilename=fileName.replace("downloadtemp/",""); 
  
if(fileName.equals("equttmpl"))
{
 fileName="tmpl/equt_model.xls";
 realfilename="端子批量维护模板.xls";
}else if(fileName.equals("docequttmpl")){
 fileName="tmpl/docequt_model.xls";
 realfilename="设备档案模板.xls";

}else if(fileName.equals("routetmpl")){
	 fileName="tmpl/excel_model_route.xls";
 	realfilename="光缆批量维护模版.xls";
}else if(fileName.equals("poletmpl")){
	fileName="tmpl/excel_model_importpole.xls";
	realfilename="局站电杆信息模板.xls";
}else if(fileName.equals("pipetmpl")){
	fileName="tmpl/excel_pipe.xls";
	realfilename="管道信息模板.xls";
}else if(fileName.equals("support")){
    fileName="tmpl/excel_support.xls";
    realfilename="撑点信息模板.xls";
}else if(fileName.equals("suspension")){
    fileName="tmpl/excel_suspension.xls";
    realfilename="吊线信息模板.xls";
}else if(fileName.equals("poleLSManage")){
    fileName="tmpl/excel_poleLSManage.xls";
    realfilename="杆路段信息模板.xls";
}else if(fileName.equals("suspensionseg")){
    fileName="tmpl/excel_suspensionseg.xls";
    realfilename="吊线段信息模板.xls";
}else if(fileName.equals("ipoletmpl")){//tengxy
	fileName="tmpl/pole.xls";
	realfilename="电杆信息模板.xls";
}else if(fileName.equals("iwelltmpl")){//tengxy
	fileName="tmpl/well.xls";
	realfilename="井信息模板.xls";
}else if(fileName.equals("iLeduptmpl")){//tengxy
	fileName="tmpl/Ledup.xls";
	realfilename="引上点信息模板.xls";
}else if(fileName.equals("polelinetmpl")){
	fileName="tmpl/excel_poleline.xls";
	realfilename="杆路信息模板.xls";
}else if(fileName.equals("pipesegtmpl")){
	fileName="tmpl/excel_pipeseg.xls";
	realfilename="管道段信息模板.xls";
}else if(fileName.equals("tubetmpl")){
	fileName="tmpl/excel_tube.xls";
	realfilename="管孔信息模板.xls";
}
// 设置响应头和下载保存的文件名  
response.reset();  
//response.setContentType("application/octet-stream");       //windows  
//response.addHeader("Content-Disposition", "filename=\"" + myName + "\"");      //windows  
response.setContentType("application/octet-stream; charset=GBK");     //linux  
response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(realfilename.getBytes("gb2312"),"iso8859-1") + "\"");      //linux  
 
//新建文件输入输出流  
OutputStream output = null;  
FileInputStream fis = null;  
try{  
  //新建File对象  
  File f = new File(root  + fileName);  
  out.print(f);
  //新建文件输入输出流对象  
  output = response.getOutputStream();  
  fis = new FileInputStream(f);  
  //设置每次写入缓存大小  
  byte[] b = new byte[(int)f.length()];  
  //out.print(f.length());  
  //把输出流写入客户端  
  int i = 0;  
  while((i = fis.read(b)) > 0){  
    output.write(b, 0, i);  
  }  
  output.flush();  
}  
catch(Exception e){  
  e.printStackTrace();  
}  
finally{  
  if(fis != null){  
    fis.close();  
    fis = null;  
  }  
  if(output != null){  
    output.close();  
    output = null;  
    out.clear();
    out=pageContext.pushBody();
  }  
} 
%>
<script type="text/javascript">
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <base href="<%=basePath%>">
    <title>export.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

  </body>
</html>

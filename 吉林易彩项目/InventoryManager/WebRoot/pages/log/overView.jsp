<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
 		<title>智能电子标签管理系统</title>
        <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
        
       	<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>

  </head>
  
  <body>
  <script language="javascript" type="text/javascript">
    	Ext.onReady(function(){ 
    		
			
			
			var pnCenter=new Ext.TabPanel({
				id:"pnCenter",
				region:"center",
				width:'100%',
				border: false,
				addDomain:null,
				activeTab:0,
				items:[{
					title : "局站日志",
					html : '<iframe name="stationLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/stationLog.jsp"></iframe>'
				},{
					title : "机房日志",
					html : '<iframe name="generatorLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/generatorLog.jsp"></iframe>'
				},{
				
					title : "ODF设备日志",
					html : '<iframe name="equtLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/equtLog.jsp"></iframe>'
},{
					
					title : "OCC设备日志",
					html : '<iframe name="HFSLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/OccEqutLog.jsp"></iframe>'
},{
					
					title : "电源日志",
					html : '<iframe name="HFSLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/HFSLog.jsp"></iframe>'
},{
					
					title : "井日志",
					html : '<iframe name="wellLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/wellLog.jsp"></iframe>'
},{
					
					title : "电杆日志",
					html : '<iframe name="poleLog" scrolling="auto" frameborder="0" width="100%" height="100%" src="pages/log/poleLog.jsp"></iframe>'
}]
				
				
			});
			
			var pnCenterframe=new Ext.Panel({
				id:"pnCenterframe",
				title:"当前位置：日志管理",
				region:"center",
				layout:'fit',
				width:'100%',
				bodyStyle:'border-width: 0 0 0 1px',
				items:[pnCenter]
			});
			
			
			new Ext.Viewport({
			id:'view',
			layout:"border",
			items:[pnCenterframe]

			}); 
    	});
        </script> 
  </body>
</html>

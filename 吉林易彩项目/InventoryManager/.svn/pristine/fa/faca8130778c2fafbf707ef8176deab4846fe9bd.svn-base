<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
     <head>
     	<base href="<%=basePath%>">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>智能电子标签管理系统</title>
        <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" title="gray" href="css/xtheme-gray.css" />
        
       	<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
    </head>
    <body >
    	<script language="javascript" type="text/javascript">
    	Ext.onReady(function(){ 
    		var pnNorth=new Ext.Panel({
				id:"pnNorth",
				region:"north",
				height:90,
				layout:'column',
				bodyStyle:'border-width:1px 1px 1px 1px;',
				html:"<iframe src='top.jsp' style='border-width:0 0 0 0;' width='100%'></iframe>"
			});
			var pnWest=new Ext.Panel({
				id:"pnWest",
				region:"west",
				collapseMode: "mini",
                split: true, //允许按钮收缩
                width:250,
				bodyStyle:'border-width: 0 1px 0 1px',
				html:"<iframe id='domainTreeIframe' src='pages/document/domainTree.jsp' name='domainTree' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>"	
			});
			
			var pnCenter=new Ext.TabPanel({
				id:"pnCenter",
				region:"center",
				width:'100%',
				border: false,
				addDomain:null
				
			});
			
			var pnCenterframe=new Ext.Panel({
				id:"pnCenterframe",
				region:"center",
				layout:'fit',
				width:'100%',
				bodyStyle:'border-width: 0 0 0 1px',
				items:[pnCenter]
			});
			
			
			var pnSouth=new Ext.Panel({
				id:"pnSouth",
				region:"south",
				border:false,
				//bodyStyle:'border-width:1px 0 0 0;',
				width:'100%',
				height:20,
				bodyStyle:'border-width:1px 1px 1px 1px',
				html:'<div align="center"><font size="0.8">版权所有：青岛英凯利信息科技有限公司</font></div>'
			});
			new Ext.Viewport({
			id:'view',
			layout:"border",
			items:[pnWest,pnCenterframe]

			}); 
    	});
        </script>    
        
    </body>
</html>

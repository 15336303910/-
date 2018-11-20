<!DOCTYPE html>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
     <title>工单地图展现</title>
     <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
		var id =  '<%=request.getParameter("id") %>';
		var type = '<%=request.getParameter("type") %>';
  	</script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/arcgis/js/dojo/dijit/themes/tundra/tundra.css"/>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/arcgis/init.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/arcgis/gisComm.js"></script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/arcgis/js/esri/css/esri.css" />
     <script>
     	var request;
     	var map;
     	var diag =new Dialog();
     	var winWidth ;
		var winHeight ;
		if (window.innerWidth){
			winWidth = window.innerWidth;
		}else if ((document.body) && (document.body.clientWidth)){
			winWidth = document.body.clientWidth;
		}
		if (window.innerHeight){
			winHeight = window.innerHeight;
		}else if ((document.body) && (document.body.clientHeight)){
			winHeight = document.body.clientHeight;
		}
		if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
		{
			winHeight = document.documentElement.clientHeight;
			winWidth = document.documentElement.clientWidth;
		}
        require(["esri/map",
            "esri/layers/ArcGISDynamicMapServiceLayer",
            "esri/layers/ArcGISTiledMapServiceLayer",
            "esri/toolbars/navigation",
            "dijit/form/Button",
            "dijit/Toolbar",
            "dojo/domReady!"],
        function(Map,ArcGISDynamicMapServiceLayer){
        	map = new Map("mapDiv");
        	//下面这个是正式的
        	//var layer = new esri.layers.ArcGISDynamicMapServiceLayer("http://10.224.133.101/arcgis/rest/services/beijing0804/MapServer");
           	var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer");
            map.addLayer(layer);
        	ajaxLoad();
        })
       function ajaxLoad(){
    	   request = (window.XMLHttpRequest) ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");  
    	   request.onreadystatechange = resetMap;  
    	   request.open("POST", context_path+"/approvalAction!resetMap.action?id="+id+"&type="+type,true);  
    	   request.send(null);
       }
        //重画地图
       function resetMap(){
    	   if (request.readyState ==4 && request.status==200){
    		   map.centerAndZoom(getCentPoint(request.responseText),7);
    		   var drawLayer =  drawRes(request.responseText)
               map.addLayer(drawLayer);
    		   dojo.connect(drawLayer,"onClick",drawClick);
    	   }
       }
       //得到提示信息
       function drawClick(event){
    	   var graphic=event.graphic;
    	   var resType = graphic.attributes.resType;
    	   var width= 300;
    	   var height = 500;
    	   var url = context_path+"/pages/approval/resPoint.jsp?id="+graphic.attributes.id+"&resType="+graphic.attributes.resType+"&grapType="+graphic.attributes.grapType+"&taskId="+id;
    	   if(resType == 'station' || resType == 'site'){
    		   resType = 'station';
    		   url = context_path+"/pages/approval/site/stationInfo.jsp?resId="+graphic.attributes.id+"&resType="+resType+"&resName="+graphic.attributes.resName+"&taskId="+id;
    		   //url = context_path+"/pages/approval/resSiteInfo.jsp?id="+graphic.attributes.id+"&resType="+resType+"&grapType="+graphic.attributes.grapType;
    		   width = 900;
    	   	   height = 500;
    	   }
    	   /* if(resType == 'optical'){
               url = context_path+"/pages/approval/site/stationInfo.jsp?resId="+graphic.attributes.id+"&resType="+resType+"&resName="+graphic.attributes.resName+"&taskId="+id;
               width = 900;
               height = 500;
           } */
    	   diag.Width = width;
    	   diag.Height = height;
    	   diag.Modal = false;
    	   diag.Drag=true;
    	   diag.Title = "资源详情";
    	   diag.Top="0%";
    	   diag.Left="100%";
    	   diag.URL = url;
    	   diag.show();
       }
    </script>
</head>
<body class="tundra">
	<div id="mapDiv" style="width:winWidth; height:570px; border:1px solid #000;"></div>
</body>
</html>
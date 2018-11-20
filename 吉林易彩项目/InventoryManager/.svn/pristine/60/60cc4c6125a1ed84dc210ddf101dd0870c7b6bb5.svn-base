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
     	var diag = new Dialog();
        require(["esri/map",
            "esri/layers/ArcGISDynamicMapServiceLayer",
            "esri/layers/ArcGISTiledMapServiceLayer",
            "esri/toolbars/navigation",
            "dijit/form/Button",
            "dijit/Toolbar",
            "dojo/domReady!"],
        function(Map,ArcGISDynamicMapServiceLayer){
        	map = new Map("mapDiv");
            var layer=new ArcGISDynamicMapServiceLayer("http://10.224.202.65:6080/arcgis/rest/services/beijingMap/MapServer");
            //var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer");
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
    		   map.centerAndZoom(getCentPoint(request.responseText),14);
    	   }
       }
    </script>
</head>
<body class="tundra">
<div id="mapDiv" style="width:900px; height:600px; border:1px solid #000;"></div>
</body>
</html>
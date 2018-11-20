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
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/arcgis/gisComm.js"></script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/arcgis/js/esri/css/esri.css" />
     <script type="text/javascript">
       dojo.require("esri.map");
       dojo.require("esri.dijit.BasemapToggle");
   	   dojo.require("esri.dijit.HomeButton");
   	   dojo.require("esri.dijit.OverviewMap");
   	   dojo.require("esri.toolbars.draw");
       var map;
       dojo.ready(init);
       var request;
       function ajaxLoad(){
    	   request = (window.XMLHttpRequest) ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");  
    	   request.onreadystatechange = resetMap;  
    	   request.open("POST", context_path+"/approvalAction!resetMap.action?id="+id+"&type="+type,true);  
    	   request.send(null);
       }
       function resetMap(){
    	   if (request.readyState ==4 && request.status==200){
    		   //ArcGISTiledMapServiceLayer
    		   var myTiledMapServiceLayer = new esri.layers.DynamicMapServiceLayer("http://10.224.202.65:6080/arcgis/rest/services/beijingMap/MapServer?f=jsapi");
               map.addLayer(myTiledMapServiceLayer);
               map.centerAt(getCentPoint(request.responseText));
               map.addLayer(drawRes(request.responseText));
    	   }
       }
       function init() {
    	 map = new esri.Map("arcgisDiv", {
  			center: [126.3808325728, 29.8934563305],
  		    zoom: 14,
  		    basemap: "streets"
  		});
    	ajaxLoad();
      }
     </script>
  </head>
  <body class="tundra">
     <div id="arcgisDiv" style="width:900px; height:600px; border:1px solid #000;"></div>
  </body>
</html>
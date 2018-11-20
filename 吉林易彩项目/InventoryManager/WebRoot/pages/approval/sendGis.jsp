<!DOCTYPE html>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
     <title>工单地图展现</title>
     <style>
   	 	html,
    	body,
    	#mapDiv {
      		padding: 0;
      		margin: 0;
      		height: 100%;
      		width: 100%;
    	}

    	#infoDiv {
      		position: absolute;
      		top: 15px;
      		left: 60px;
    	}

    	#infoDiv input {
      		border: none;
      		box-shadow: rgba(0, 0, 0, 0.3) 0px 1px 2px;
    	}
  </style>
     <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
		var id =  '<%=request.getParameter("id") %>';
		var type = '<%=request.getParameter("type") %>';
  	</script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/arcgis/js/dojo/dijit/themes/tundra/tundra.css"/>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js" charset="utf-8"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/arcgis/init.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/arcgis/gisComm.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/approval/sendGis.js"></script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/arcgis/js/esri/css/esri.css" />
     <script>
     	var request;
     	var map,pointStr;
     	var diag =new Dialog();
     	var winWidth ;
		var winHeight ;
		 var graphic;
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
        	var layer = new esri.layers.ArcGISDynamicMapServiceLayer("http://10.224.202.65:6080/arcgis/rest/services/bjMapService1106/MapServer");
           	//var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer");
            map.addLayer(layer);
        	ajaxLoad();
        })
       function ajaxLoad(){
    	   request = (window.XMLHttpRequest) ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");  
    	   request.onreadystatechange = resetMap;  
    	   request.open("POST", context_path+"/approvalAction!getSendGis.action?id="+id+"&type="+type,true);  
    	   request.send(null);
       }
        //重画地图
       function resetMap(){
    	   if (request.readyState ==4 && request.status==200){
    		   
    		   var result=eval("("+request.responseText+")");
    		   var pt = new esri.geometry.Point([result.points[0].longitude,result.points[0].latitude]);
    		   map.centerAndZoom(pt,.03);
    		   //画点线
    		   map.addLayer(drawPoints(result.points,result.lines));
    		   pointStr = result.points;
    		  
    		   //添加班组里面的采集数据
    		   var drawLayer =  drawRes(result.resStr);
    		   map.addLayer(drawLayer);
    		   //渲染驳回
    		   drawReject(result.reject,drawLayer);
    		   dojo.connect(drawLayer,"onClick",drawClick);
    	   }
       }
       //得到提示信息
       function drawClick(event){
    	   graphic=event.graphic;
    	   var resType = graphic.attributes.resType;
    	   document.getElementById("radResId").value = graphic.attributes.id;
    	   document.getElementById("radResType").value = graphic.attributes.resType;
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
       
       /**
       	审核通过的
       **/
       function clickFun(){
    	   var resType = graphic.attributes.resType;
    	   var img = context_path+"/images/map/"+resType+"Audit.png";
    	   var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
    	   graphic.setSymbol(pictureMarkerSymbol);
       }
       
       
       /**
       	渲染驳回
       **/
       function rejectFun(){
    	   var resType = graphic.attributes.resType;
    	   var img = context_path+"/images/map/"+resType+"Reject.png";
    	   var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
    	   graphic.setSymbol(pictureMarkerSymbol);
       }
       
       /**
        * 得到具体点辐射信息
        * @returns
        */	
       function radiateFun(){
    	var radResId = document.getElementById("radResId").value;
    	var radResType = document.getElementById("radResType").value;
    	if(radResType !='well' && radResType !='pole'){
    		alert("不支持此类资源!");
    		return ;
    	}
    	if(radResId ==null || radResId == ''){
    		alert("资源信息不完整!");
    		return ;
    	}
       	$.ajax({
       		url:context_path+"/approvalAction!getRadiate.action?",
               type: "POST",
               data: {resId:document.getElementById("radResId").value,resType:document.getElementById("radResType").value},
               dataType: "text",
               success: function (data) {
               	var radLayer = drawRes(data);
               	map.addLayer(radLayer);
               	dojo.connect(radLayer,"onClick",drawClick);
               },
               error: function (data) {
               	alert("获取数据失败!");
               }
            });
       }
       //得到现有数据
       function readNowRes(){
    	   var resLayer = drawResPoint(pointStr);
		   map.addLayer(resLayer);
       }
    </script>
</head>
<body class="tundra">
	<div id="mapDiv" style="width:winWidth; height:570px; border:1px solid #000;"></div>
	<div id="infoDiv">
    <input class="esri-component esri-widget-button esri-widget esri-interactive" type="button"
      id="approveBtn" value="审批完成" onclick="approveFun()">&nbsp;&nbsp;&nbsp;
      <input class="esri-component esri-widget-button esri-widget esri-interactive" type="button"
      id="radiateBtn" value="点辐射" onclick="radiateFun()">
      <input type="hidden" id="radResId" name ="radResId"/>
      <input type="hidden" id="radResType" name ="radResType"/>
      &nbsp;&nbsp;&nbsp;
      <input class="esri-component esri-widget-button esri-widget esri-interactive" type="button"
      id="readNowRes" value="查看现有数据" onclick="readNowRes()">
  </div>
</body>
</html>
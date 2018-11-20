<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看电杆位置信息</title>
<link rel="shortcut icon" href="imgs/lt_logo.png" type="image/x-icon" />
<!--引用百度地图API-->
<style type="text/css">
    html,body{margin:0;padding:0;}
    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>
<script src="js/jquery/jquery-1.3.2.min.js"></script>
</head>
<script type="text/javascript">
	//API初始化失败
	function loadError(){
		alert("网络连接错误，请检查您的网络连接。");
		window.opener=null;
		window.open('','_self');
		window.close();
	}
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?ak=P02bsWvPBvn27mo598SyVYgX&v=2.0" onerror="loadError();">
</script>

<body>
  <!--百度地图容器-->
  <div style="width:697px;height:550px;border:#ccc solid 1px;" id="dituContent"></div>
  <div style="display:none;">
  <table id="lt">
    <s:iterator value="resultList2" id="list" status='st'>			
   			<tr>
   				<td><s:property value="poleLongitude"/></td>
   				<td><s:property value="poleLatitude"/></td>
   				<td><s:property value="poleAddress"/></td>
   				<td><s:property value="poleNameSub"/></td>
   				<td><s:property value="areaname"/></td>
   				<td><s:if test="poleTypeEnumId==1">
						普通杆
					</s:if>
                    <s:elseif test="poleTypeEnumId==2">
						单接杆
					</s:elseif>                     
					<s:elseif test="poleTypeEnumId==3">
						双接杆
					</s:elseif>
					<s:elseif test="poleTypeEnumId==4">
						H型杆
					</s:elseif>
                    <s:elseif test="poleTypeEnumId==5">
						A型杆
					</s:elseif>                     
					<s:elseif test="poleTypeEnumId==6">
						L型杆
					</s:elseif>
					 <s:elseif test="poleTypeEnumId==7">
						三角杆
					</s:elseif>                     
					<s:elseif test="poleTypeEnumId==8">
						井型杆
					</s:elseif>
					<s:elseif test="poleTypeEnumId==9">
						引上杆
					</s:elseif>
                    <s:elseif test="poleTypeEnumId==10">
						终端杆
					</s:elseif>                     
					<s:elseif test="poleTypeEnumId==11">
						角杆
					</s:elseif>
					<s:elseif test="poleTypeEnumId==12">
						分歧杆
					</s:elseif>
                    <s:elseif test="poleTypeEnumId==13">
						T型杆
					</s:elseif>                     
					<s:elseif test="poleTypeEnumId==14">
						跨路杆
					</s:elseif>
					<s:else>未知</s:else>
				</td>
					
   				<td><s:if test="poleMaterial==1">
						钢筋混凝土电杆
					</s:if>
                    <s:elseif test="poleMaterial==2">
						木质电杆
					</s:elseif>                     
					<s:elseif test="poleMaterial==3">
						铁质电杆
					</s:elseif>
					<s:else>未知</s:else>
				</td>
				
   				<td><s:if test="poleLength==1">
						7.5m
					</s:if>
                    <s:elseif test="poleLength==2">
						8m
					</s:elseif>                     
					<s:elseif test="poleLength==3">
						9m
					</s:elseif>
				    <s:elseif test="poleLength==2">
						10m
					</s:elseif>                     
					<s:elseif test="poleLength==3">
						12m
					</s:elseif>
					
					<s:else>未知</s:else>
				</td>
   				<td><s:property value="buriedDepth"/></td>
   				<td><s:property value="poleRadius"/></td>
   				<td><s:property value="poleCode"/></td>
   			</tr>
   			 		
  	 </s:iterator>
  	 </table>
  </div>
</body>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMarker();//向地图中添加marker
    }
    
    //创建地图函数：
    function createMap(){
    	var lat =$("#lt").find("tr").eq(0).find("td").eq(1).text();
    	var lon =$("#lt").find("tr").eq(0).find("td").eq(0).text();
    	if((lat==""||lat==null)||(lon==""||lat==null)){
    		alert("杆路段经纬度不存在");
    		window.close();
    		return;
    	}
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(lon,lat);//定义一个中心点坐标
        map.centerAndZoom(point,15);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }
    
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    
    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
    }
    
    //标注点数组

	var markerArr = [];
	var Json=[];
	function createMarker(){
	 	$("#lt").find("tr").each(function(){
			var lon=$(this).children().eq(0).text();
			var lat=$(this).children().eq(1).text();
			var addr=$(this).children().eq(2).text();
			var name=$(this).children().eq(3).text();
			var area=$(this).children().eq(4).text();
			var type=$(this).children().eq(5).text();
			var material=$(this).children().eq(6).text();
			var length=$(this).children().eq(7).text();
			var depth=$(this).children().eq(8).text();
			var radius=$(this).children().eq(9).text();
			var code=$(this).children().eq(10).text();
			   
			var point=lon+"|"+lat;
			
	 	 	var pole = {lon:lon,lat:lat,addr:addr,name:name,area:area,type:type,material:material,length:length,depth:depth,radius:radius,code:code,point:point};
			markerArr.push(poleToMarker(pole));
			Json.push(new BMap.Point(lon,lat));
   		 })
	
	}
	

	 function poleToMarker(pole){
		var marker =   
							{
							title:pole.name,
							content:"<table><tr><td>电杆地址：</td><td>"+pole.addr+"</td></tr>"+"<tr><td>电杆名称：</td><td>"+pole.name+"</td></tr>"+"<tr><td>电杆编号：</td><td>"+pole.code+"</td></tr>"+"<tr><td>所属局站：</td><td>"+pole.area+"</td></tr>"+"<tr><td>电杆类型：</td><td>"+pole.type+"</td></tr>"+"<tr><td>电杆材质：</td><td>"+pole.material+"</td></tr>"+"<tr><td>电杆程式：</td><td>"+pole.length+"</td></tr>"+"<tr><td>深埋：</td><td>"+pole.depth+"</td></tr>"+"<tr><td>电杆半径：</td><td>"+pole.radius+"</td></tr></table>",
							point:pole.point,
    						isOpen:0,
    						icon:{w:21,h:21,l:0,t:0,x:6,lb:5}
    						};
						 
		return marker;
    }


 
  

    //创建marker
    function addMarker(){
        for(var i=0;i<markerArr.length;i++){
            var json = markerArr[i];
           
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
			var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
            var polyline = new BMap.Polyline(Json,{strokeColor:"red", strokeWeight:1, strokeOpacity:0.9});//在地图上画线
        	map.addOverlay(polyline);//画线
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                        borderColor:"#808080",
                        color:"#333",
                        cursor:"pointer"
            });
			
			(function(){
				var index = i;
				var _iw = createInfoWindow(i);
				var _marker = marker;
				_marker.addEventListener("click",function(){
				    this.openInfoWindow(_iw);
			    });
			    _iw.addEventListener("open",function(){
				    _marker.getLabel().hide();
			    })
			    _iw.addEventListener("close",function(){
				    _marker.getLabel().show();
			    })
				label.addEventListener("click",function(){
				    _marker.openInfoWindow(_iw);
			    })
				if(!!json.isOpen){
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
        }
    }
    //创建InfoWindow
    function createInfoWindow(i){
        var json = markerArr[i];
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
        return iw;
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("http://map.baidu.com/image/us_cursor.gif", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }
   createMarker();
     initMap();
 
   
</script>
</html>

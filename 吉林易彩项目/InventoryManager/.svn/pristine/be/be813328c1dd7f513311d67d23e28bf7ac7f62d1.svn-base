<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看管道段位置信息</title>
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
  	    <s:iterator value="wellresultList" id="list" status='st'>			
   			<tr class="pipetr">
   				<td><s:property value="longitude"/></td>
   				<td><s:property value="latitude"/></td>
   				<td><s:property value="wellAddress"/></td>
   				<td><s:property value="wellNameSub"/></td>
   				<td><s:property value="alias"/></td>
   				<td><s:property value="areaname"/></td>
   				<td><s:if test="manholeTypeEnumId==1">						
					</s:if>
                    <s:elseif test="manholeTypeEnumId==2">
						手孔
					</s:elseif>                     
					<s:elseif test="manholeTypeEnumId==3">
						双人孔
					</s:elseif>
					<s:elseif test="manholeTypeEnumId==4">
						三人孔
					</s:elseif>
                    <s:elseif test="manholeTypeEnumId==5">
						局前井
					</s:elseif>                     
					<s:elseif test="manholeTypeEnumId==6">
						地下室
					</s:elseif>
					<s:else>未知</s:else>
				</td>
				<td><s:property value="coverTopElevation"/></td> 
				<td><s:property value="bottomAltitude"/></td>
				<td><s:property value="manholeDepth"/></td>
				<td><s:property value="manholeLength"/></td>
				<td><s:property value="manholeWidth"/></td>
   				<td><s:if test="manholeShape==1">
						直通型
					</s:if>
                    <s:elseif test="manholeShape==2">
						丁字型
					</s:elseif>                     
					<s:elseif test="manholeShape==3">
						十字型
					</s:elseif>
					 <s:elseif test="manholeShape==4">
						15度斜通型
					</s:elseif>                     
					<s:elseif test="manholeShape==5">
						30度斜通型
					</s:elseif>
					 <s:elseif test="manholeShape==6">
						45度斜通型
					</s:elseif>                     
					<s:elseif test="manholeShape==7">
						60度斜通型
					</s:elseif>
					 <s:elseif test="manholeShape==8">
						75度斜通型
					</s:elseif>                     
					<s:elseif test="manholeShape==9">
						不规则型
					</s:elseif>
					 <s:elseif test="manholeShape==10">
						扇型
					</s:elseif>                     
					<s:elseif test="manholeShape==11">
						拐弯型(左)
					</s:elseif>
					 <s:elseif test="manholeShape==12">
						拐弯型(右)
					</s:elseif>                     
					<s:elseif test="manholeShape==13">
						手井
					</s:elseif>
					 <s:elseif test="manholeShape==14">
						方井
					</s:elseif>                     
					<s:elseif test="manholeShape==15">
						穿线孔
					</s:elseif>
					<s:else>未知</s:else>
				</td>
				
   				<td><s:if test="manholeSpecification==1">
						特大号
					</s:if>
                    <s:elseif test="manholeSpecification==2">
						大号
					</s:elseif>                     
					<s:elseif test="manholeSpecification==3">
						中号
					</s:elseif>
				    <s:elseif test="manholeSpecification==4">
						小号
					</s:elseif>                     
					<s:elseif test="manholeSpecification==5">
						手孔
					</s:elseif>
					<s:else>未知</s:else>
				</td>
				
				
				
					<td><s:if test="manholeStructure==1">
						砖砌体
					</s:if>
                    <s:elseif test="manholeStructure==2">
						钢筋混凝土体
					</s:elseif>                     
					<s:elseif test="manholeStructure==3">
						装配式
					</s:elseif>
				   
					<s:else>未知</s:else>
				</td>
				
				
					<td><s:if test="roadTypeEnumId==1">
						车行道
					</s:if>
                    <s:elseif test="roadTypeEnumId==2">
						人行道
					</s:elseif>                    
					
					<s:else>未知</s:else>
				</td>
				
				<td><s:if test="roadSurfaceTypeEnumId==1">
						柏油马路
					</s:if>
                    <s:elseif test="roadSurfaceTypeEnumId==2">
						沙石路面
					</s:elseif>    
					 <s:elseif test="roadSurfaceTypeEnumId==3">
						混凝土砌块路面
					</s:elseif> 
					 <s:elseif test="roadSurfaceTypeEnumId==4">
						水泥砖铺路面
					</s:elseif> 
					 <s:elseif test="roadSurfaceTypeEnumId==5">
						条石路面
					</s:elseif> 
					 <s:elseif test="roadSurfaceTypeEnumId==6">
						水泥路面
					</s:elseif> 
					 <s:elseif test="roadSurfaceTypeEnumId==7">
						砼路面
					</s:elseif> 
					<s:else>未知</s:else>
				</td>
				
   				<td><s:property value="coverMaterialEnumId"/></td>
   				<td><s:property value="coverShapeEnumId"/></td>
   				<td><s:property value="coverQuantity"/></td>
   				<td><s:property value="wallThickness"/></td>
   				<td><s:property value="subbaseThickness"/></td>
   			</tr>
   			 		
  	 </s:iterator>
  

    <s:iterator value="poleresultList" id="list" status='st'>			
   			<tr class="poletr">
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
    	var lat =$(".pipetr").eq(0).find("td").eq(1).text();
    	var lon =$(".pipetr").eq(0).find("td").eq(0).text();
    	
    	
    	if((lat==""||lat=="null")||(lon==""||lat=="null")){
    		alert("管道经纬度不存在");
    		window.close();
    		return;
    	}
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(lon,lat);//定义一个中心点坐标
        map.centerAndZoom(point,19);//设定地图的中心点和坐标并将地图显示在地图容器中
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
	
		$(".pipetr").each(function(){
			var lon=$(this).children().eq(0).text();
			
			var lat=$(this).children().eq(1).text();
			var addr=$(this).children().eq(2).text();
			var name=$(this).children().eq(3).text();
			var alias=$(this).children().eq(4).text();
			var area=$(this).children().eq(5).text();
			var type=$(this).children().eq(6).text();
			var cover=$(this).children().eq(7).text();
			var bot=$(this).children().eq(8).text();
			var md=$(this).children().eq(9).text();
			var ml=$(this).children().eq(10).text();
			var mw=$(this).children().eq(11).text();
			var ms=$(this).children().eq(12).text();
			var msf=$(this).children().eq(13).text();
			var mst=$(this).children().eq(14).text();
			var rt=$(this).children().eq(15).text();
			var rs=$(this).children().eq(16).text();
			var cm=$(this).children().eq(17).text();
			var cs=$(this).children().eq(18).text();
			var cq=$(this).children().eq(19).text();
			var wt=$(this).children().eq(20).text();
			var st=$(this).children().eq(21).text();	   
			var point=lon+"|"+lat;
			
	 	 	var pipe = {lon:lon,
	 	 				lat:lat,
	 	 				addr:addr,
	 	 				name:name,
	 	 				alias:alias,
	 	 				area:area,
	 	 				type:type,
	 	 				cover:cover,
						bot:bot,
						md:md,
						ml:ml,
						mw:mw,
						ms:ms,
						msf:msf,
						mst:mst,
						rt:rt,
						rs:rs,
						cm:cm,
						cs:cs,
						cq:cq,
						wt:wt,
						st:st,
						point:point
						};			
			markerArr.push(pipeToMarker(pipe));
			Json.push(new BMap.Point(lon,lat));
   		 });
	
	
	
	
	 	$(".poletr").each(function(){
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
			
	 	 	var pole = {lon:lon,
	 	 				lat:lat,
	 	 				addr:addr,
	 	 				name:name,
	 	 				area:area,
	 	 				type:type,
	 	 				material:material,
	 	 				length:length,
	 	 				depth:depth,
	 	 				radius:radius,
	 	 				code:code,
	 	 				point:point};
			markerArr.push(poleToMarker(pole));
			Json.push(new BMap.Point(lon,lat));
   		 });
	
	}
	

	 function poleToMarker(pole){
		var marker =   
							{
							title:"电杆："+pole.name,
							content:"<table><tr><td>电杆地址：</td><td>"+pole.addr+"</td></tr>"+"<tr><td>电杆名称：</td><td>"+pole.name+"</td></tr>"+"<tr><td>电杆编号：</td><td>"+pole.code+"</td></tr>"+"<tr><td>所属局站：</td><td>"+pole.area+"</td></tr>"+"<tr><td>电杆类型：</td><td>"+pole.type+"</td></tr>"+"<tr><td>电杆材质：</td><td>"+pole.material+"</td></tr>"+"<tr><td>电杆程式：</td><td>"+pole.length+"</td></tr>"+"<tr><td>深埋：</td><td>"+pole.depth+"</td></tr>"+"<tr><td>电杆半径：</td><td>"+pole.radius+"</td></tr>"+
									"<tr><td></td><td> <input type='button' onclick=showQuickMap('"+pole.lon+"','"+pole.lat+"') value='在平面图中查看'></td></tr>"+
									"</table>",
							point:pole.point,
    						isOpen:0,
    						icon:{w:23,h:25,l:23,t:21,x:9,lb:12}
    						};
						 
		return marker;
    }
    
    
    

    
    
    
    
    
    
	function pipeToMarker(pipe){
	
		var marker = 
							{
							title:"井："+pipe.name,
							content:"<table><tr><td>井地址：</td><td>"+
									pipe.addr+"</td></tr>"+
									"<tr><td>井名称：</td><td>"+
									pipe.name+"</td></tr>"+
									"<tr><td>井别名：</td><td>"+
									pipe.alias+"</td></tr>"+
									"<tr><td>所属局站：</td><td>"+
									pipe.area+"</td></tr>"+
									"<tr><td>井类型：</td><td>"+
									pipe.type+"</td></tr>"+
									"<tr><td>盖顶高程：</td><td>"+
									pipe.cover+"</td></tr>"+
									"<tr><td>井坑底高程：</td><td>"+
									pipe.bot+"</td></tr>"+
									"<tr><td>井深：</td><td>"+
									pipe.md+"</td></tr>"+
									"<tr><td>井长：</td><td>"+
									pipe.ml+"</td></tr>"+
									"<tr><td>井宽：</td><td>"+
									pipe.mw+"</td></tr>"+
									"<tr><td>井形状：</td><td>"+
									pipe.ms+"</td></tr>"+
									"<tr><td>井规格：</td><td>"+
									pipe.msf+"</td></tr>"+
									"<tr><td>井结构：</td><td>"+
									pipe.mst+"</td></tr>"+
									"<tr><td>道路类型：</td><td>"+
									pipe.rt+"</td></tr>"+
									"<tr><td>路面类型：</td><td>"+
									pipe.rs+"</td></tr>"+
									"<tr><td>井盖材质：</td><td>"+
									pipe.cm+"</td></tr>"+
									"<tr><td>井盖形状：</td><td>"+
									pipe.cs+"</td></tr>"+
									"<tr><td>井盖数量：</td><td>"+
									pipe.cq+"</td></tr>"+
									"<tr><td>井壁厚度：</td><td>"+
									pipe.wt+"</td></tr>"+
									"<tr><td>底基厚度：</td><td>"+
									pipe.st+"</td></tr>"+
									"<tr><td></td><td> <input type='button' onclick=showQuickMap('"+pipe.lon+"','"+pipe.lat+"') value='在平面图中查看'></td></tr>"+
									"</table>",
							point:pipe.point,
    						isOpen:0,
    						icon:{w:23,h:25,l:23,t:21,x:9,lb:12}
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
        var icon = new BMap.Icon("http://map.baidu.com/image/us_cursor.gif", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),anchor:new BMap.Size(json.x,json.h)})
        return icon;
    }
    
    function showQuickMap(lon,lat){
	    //alert(lon+","+lat);
	    var url = "pages/pipe/map_quick.jsp?lat="+lat+"&lon="+lon;
		var iWidth=699; //弹出窗口的宽度;
		var iHeight=552; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2+50; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2+50; //获得窗口的水平位置;	
		var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
		window.open(url,"quickwindow",param);
    }
   createMarker();
     initMap();
 
   
</script>
</html>

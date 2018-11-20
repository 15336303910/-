<!DOCTYPE html>
<% 
String path = request.getContextPath();
%>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	
	<style type="text/css">
	body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
	#allmap {height: 500px;width:100%;overflow: hidden;}
	#result {width:100%;font-size:12px;}
	dl,dt,dd,ul,li{
		margin:0;
		padding:0;
		list-style:none;
	}
	dt{
		font-size:14px;
		font-family:"微软雅黑";
		font-weight:bold;
		border-bottom:1px dotted #000;
		padding:5px 0 5px 5px;
		margin:5px 0;
	}
	dd{
		padding:5px 0 0 5px;
	}
	li{
		line-height:28px;
	}
	</style>
	
	<title>站址可视化监控</title>
</head>
<body>
	<div id="allmap" style="height:100%">	
	</div>
	<!--  
	<div id="result">
		<!--<input type="button" value="默认样式" onclick="searchInfoWindow.open(marker);"/>
		<input type="button" value="样式1" onclick="openInfoWindow1()"/>
		<input type="button" value="样式2" onclick="openInfoWindow2()"/> 
		<input type="button" value="样式3" onclick="openInfoWindow3()"/>
	</div>-->
<script type="text/javascript">
	// 百度地图API功能
   
	var map = new BMap.Map('allmap');
    var poi = new BMap.Point(116,47);
    map.centerAndZoom(poi, 16);
    map.enableScrollWheelZoom();
    CreateControl(map);
    
    function myFun(result){
		var cityName = result.name;
		map.setCenter(cityName);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
	
    var adds = [
		new BMap.Point(117,36.40),
		new BMap.Point(117,37.00),
		new BMap.Point(117,37.40),
		new BMap.Point( 116.59,35.38)
	];
	for(var i = 0; i<adds.length; i++){
		var marker = new BMap.Marker(adds[i]);
		map.addOverlay(marker);
		marker.setLabel(new BMap.Label("中国移动资源点00:"+(i+1),{offset:new BMap.Size(20,-10)}));
		 marker.addEventListener("click", function(e){
	    searchInfoWindow3.open(marker);
    });
	}
    var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                    '<img src="../img/baidu.jpg" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
                    '地址：北京市海淀区上地十街10号<br/>电话：(010)59928888<br/>简介：百度大厦位于北京市海淀区西二旗地铁站附近，为百度公司综合研发及办公总部。' +
                  '</div>';

    //创建检索信息窗口对象
    var searchInfoWindow = null;
	searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
			title  : "百度大厦",      //标题
			width  : 290,             //宽度
			height : 105,              //高度
			panel  : "panel",         //检索结果面板
			enableAutoPan : true,     //自动平移
			searchTypes   :[
				BMAPLIB_TAB_SEARCH,   //周边检索
				BMAPLIB_TAB_TO_HERE,  //到这里去
				BMAPLIB_TAB_FROM_HERE //从这里出发
			]
		});
    var marker = new BMap.Marker(poi); //创建marker对象
    marker.enableDragging(); //marker可拖拽
    marker.addEventListener("click", function(e){
	    searchInfoWindow3.open(marker);
    });
    map.addOverlay(marker); //在地图中添加marker
	//样式1
	

	
	
	//样式3
	var text="资源信息:<a  onclick='searchInfoWindow4.open(new BMap.Point(116.319852,40.057031));'>详情</a><br/>"+
	      	"照片信息:3张<br/>"+
	      	"资源状态:正常<br/>"+
	      	"预警信息:<br/>"+
	      	"故障记录:<br/>"+
	      	"巡检记录<br/>";
	      	
	 var text4="物理站牌编号:四川移动自有<br/>"+
	      	"产权单位:中国移动<br/>"+
	      	"固定资产编号:正常<br/>"+
	      	"物业联系人:<br/>"+
	      	"资源资产id:<br/>";
	var searchInfoWindow3 = new BMapLib.SearchInfoWindow(map,text, {
		title: "信息内容", //标题
		width: 200, //宽度
		height: 180, //高度
		enableSendToPhone:false,
		panel : "panel", //检索结果面板
		enableAutoPan : true, //自动平移
		searchTypes :[
		]
	});
	var searchInfoWindow4 = new BMapLib.SearchInfoWindow(map,text4, {
		title: "资源详情", //标题
		width: 200, //宽度
		enableSendToPhone:false,
		height: 180, //高度
		panel : "panel", //检索结果面板
		enableAutoPan : true, //自动平移
		searchTypes :[
		]
	});
	function openInfoWindow3() {
		searchInfoWindow3.open(new BMap.Point(116.328852,40.057031)); 
	}
	//添加检索控件
	function CreateControl(map) {

            function ZoomControl() {
                // 设置默认停靠位置和偏移量  
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(800, 10);
            }
            ZoomControl.prototype = new BMap.Control();
            ZoomControl.prototype.initialize = function(map) {
                // 创建一个DOM元素  
                var div = document.createElement("div");
                // 添加文字说明  
                var form = div.appendChild(document.createElement("form"));
                var input0 = div.appendChild(document.createElement("input"));
                input0.setAttribute("id", "text");
                var input1 = div.appendChild(document.createElement("input"));
                input1.type = "submit";
                input1.value = "搜索";
                input1.setAttribute("id", "search");
                input1.setAttribute("class", "btn btn-primary");
                // 设置样式  
                div.style.cursor = "pointer";
                div.style.border = "0px solid gray";
                div.style.backgroundColor = "white";
                input1.onclick = function() {
                    var name=$("#text").val();
                    alert(name);
                    window.location.href="${pageContext.request.contextPath }/device/devicelist1.do?name="+encodeURIComponent(encodeURIComponent(name,'UTF-8'),'UTF-8');
                }; 
                map.getContainer().appendChild(div);
                // 将DOM元素返回  
                return div;
            };
            var myZoomCtrl = new ZoomControl();
            // 添加到地图当中  
            map.addControl(myZoomCtrl);
        }
</script>
</body>
</html>

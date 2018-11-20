<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>地市地图</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()-1
			    });
				$.get("${pageContext.request.contextPath}/extensions/echarts/EchartsMap/mapdata/geometryCouties/mian_yang.json",function(mapJson){
					echarts.registerMap("绵阳",mapJson);
			        var chart = echarts.init(document.getElementById("maina"),"macarons");  
			        var option = {  
			            title:{  
			                text:"",
			                subtext:"",
							textStyle:{
								color:"black"
							}  
			            },
			            tooltip:{
							trigger:"item",
							formatter:"",
							textStyle:{
								color:"black"
							}
						},
						legend:{
							orient:"vertical",
							x:"right",
							data:["犯罪数量"]
						},
			            dataRange:{  
			                min:0,  
			                max:500,  
			                color:["#F9CECE","#EE393A"],
			                text:["高","低"], 
			                calculable:true
			            },
			            series:[{  
		                    name:"犯罪数量",  
		                    type:"map",  
		                    map:"绵阳",
		                    selectedMode:"single",
		                    itemStyle:{
								normal:{
									label:{
										show:true,
										textStyle:{
											color:"black"
										}
									}
								},
								emphasis:{
									label:{
										show:true
									}
								}
							},
		                    data:[{
		                    	name:"涪城区",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"游仙区",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"安州区",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"江油市",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"三台县",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"平武县",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"安县",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"盐亭县",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"梓潼县",
		                    	value:Math.round(Math.random()*500)
		                    },{
		                    	name:"北川羌族自治县",
		                    	value:Math.round(Math.random()*500)
		                    }]  
			            }]
			        };  
			        chart.setOption(option);  
				});  
			});
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 0px green;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<center>
	  		<div style="width:500px;height:400px;border:solid 1px #A3D0E3;border-radius:7px;box-shadow:0 0 20px #A3D0E3;" id="maina">&nbsp;</div>
		</center>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
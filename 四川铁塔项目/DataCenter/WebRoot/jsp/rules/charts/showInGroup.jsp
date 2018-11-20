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
    	<title>分组总览</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-10
			    });
				$("#initDiv").css({
			    	"height":$("#mainPanel").height()-$("#chartPanel").height()-5
			    });
				/*获取数据进行初始化*/
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				require([
					"echarts",
					"echarts/chart/bar"
				],drawChart);
			});
			function drawChart(ec){
				var thisChart = ec.init(document.getElementById("initDiv"),"macarons");
				var $option = {
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:["人井要归属某个管道段","电杆要归属某个杆路段","标石要归属某个直埋段","管道段无敷设的光缆信息"]
				    },
				    toolbox:{
				        show:true,
				        feature:{
				            saveAsImage:{
				            	show:true
				            }
				        }
				    },
				    calculable:true,
				    xAxis:[{
						type:"category",
						data:["杭州市","宁波市","温州市","嘉兴市","湖州市","绍兴市"]
					}],
				    yAxis:[{
						type:"value"
					}],
				    series:[{
						name:"人井要归属某个管道段",
						type:"bar",
						data:[160,82,53,146,76,50]
					},{
						name:"电杆要归属某个杆路段",
						type:"bar",
				        data:[200,170,80,120,20,100]
					},{
						name:"标石要归属某个直埋段",
						type:"bar",
						data:[80,50,113,200,73,102]
					},{
						name:"管道段无敷设的光缆信息",
						type:"bar",
						data:[100,72,30,200,100,83]
					}]
				};
				thisChart.setOption($option);
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
		<div class="panel panel-default" style="margin-top:5px;height:470px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="chartPanel">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>任务视图 >>分组总览
			</div>
			<div class="panel-body h340" id="ruleShowDiv">
				<div style="width:99.8%;height:422px;overflow:auto;border:solid 1px #FFF;" id="initDiv"></div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
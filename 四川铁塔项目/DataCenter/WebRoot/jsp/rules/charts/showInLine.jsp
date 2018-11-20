<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String ruleId = request.getParameter("ruleId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>历史分析</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript">
			var columnTitle = "";
			var columnValue = "";
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
				var ruleCode = $("#hiddenOfRuleId").val();
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/lineHistoryChart.ilf",
					async:false,
					type:"POST",
					data:"ruleCode="+ruleCode,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							columnTitle = textStatus["titles"];
							columnValue = textStatus["values"];
							require.config({
								paths: {
									echarts:"${pageContext.request.contextPath}/js"
								}
							});
							require([
								"echarts",
								"echarts/chart/line"
							],drawChart);						
						}else{
							window.wxc.xcConfirm("报表初始化异常.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("报表初始化异常.",window.wxc.xcConfirm.typeEnum.error);
					}
				});		
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
						data:["问题数据数量历史分析"]
					},
					toolbox:{
						show:false
					},
					calculable:true,
					xAxis:[{
						type:"category",
						boundaryGap:false,
						data:columnTitle
					}],
					yAxis:[{
						type:"value",
						axisLabel:{
							formatter:"{value}"
						}
					}],
					series:[{
						name:"问题数据数量历史分析",
						type:"line",
						data:columnValue
					}]
				};
				thisChart.setOption($option);
			}
			function getRandom(){
				var thisValue = Math.random();
				thisValue = parseInt(thisValue*100);
				return thisValue; 
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" value="<%=ruleId %>" id="hiddenOfRuleId"></input>
		<div class="panel panel-default" style="margin-top:5px;height:470px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="chartPanel">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>核查规则 >> 问题数据数量历史分析
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
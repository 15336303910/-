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
			var columnValue = [];
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
					url:"${pageContext.request.contextPath}/compareDetailAction/barChartNewest.ilf",
					async:false,
					type:"POST",
					data:"ruleCode="+ruleCode,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							columnValue = textStatus["values"];
							require.config({
								paths:{
									echarts:"${pageContext.request.contextPath}/js"
								}
							});
							require([
								"echarts",
								"echarts/chart/bar"
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
				        data:["最新比对结果分析"]
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
						data:["数据不一致","仅A端有数据","仅Z端有数据","A端异常数据","Z端异常数据"]
					}],
				    yAxis:[{
						type:"value"
					}],
				    series:[{
						name:"最新比对结果分析",
						type:"bar",
						data:columnValue,
						barWidth:30,
						itemStyle:{
			            	normal:{  
			                    color:function(params){
			                        var colorList = ["green","blue","orange","red","yellow","#A3D0E3","black","#79CE30","#66B7FF","#D1D1D1"];
			                        return colorList[params.dataIndex];
			                    }
			                },
			                emphasis:{
		                        shadowBlur:10,
		                        shadowOffsetX:0,
		                        shadowColor:"rgba(0,0,0,0.5)"
			                }
			            }
					}]
				};
				thisChart.setOption($option);
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=ruleId %>" id="hiddenOfRuleId"></input>
		<div class="panel panel-default" style="margin-top:5px;height:470px;" id="mainPanel">
			<div class="panel-heading" id="chartPanel">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>数据比对 >> 最新比对结果分析
			</div>
			<div class="panel-body h340" id="ruleShowDiv">
				<div style="width:99.8%;height:422px;overflow:auto;" id="initDiv"></div>
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
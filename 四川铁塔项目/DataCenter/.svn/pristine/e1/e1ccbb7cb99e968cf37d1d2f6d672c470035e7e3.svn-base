<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>即时核查工具</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				require([
					"echarts",
					"echarts/chart/bar"
				],DrawNullCharts);
			});
			var $chart = null; 
			/*
					空报表
				*/
			function DrawNullCharts(ec){
				$chart = ec.init(document.getElementById("chartDiv"),"blue");
				var $option = {
					title:{
					    text:"",
					    subtext:"",
					},
					legend:{
						selectedMode:true,
						data:[""]
					},
					xAxis:[{
						type:"category",
						data:["","","","","",""]
					}],
					yAxis:[{
						type:"value",
						boundaryGap:[0,0.1]
					}],
					series:[{
						name:"",
						type:"bar",
						stack:"sum",
						barCategoryGap:"50%",
						itemStyle:{
							normal:{
						        color:"#7BC0F2"
						    }
						},
						data:[0,0,0,0,0,0]
					}]
				};				
				$chart.setOption($option);
			}
			/*
					柱状图
				*/
			function DrawBarCharts(ec){
				$chart = ec.init(document.getElementById("chartDiv"),"blue");
				var $option = {
					title:{
					    text:"",
					    subtext:"",
					},
					tooltip:{
						trigger:"axis"
					},
					legend:{
						selectedMode:true,
						data:["问题列表"]
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
						data:["唯一性","完整性","一致性","其他问题"]
					}],
					yAxis:[{
						type:"value",
						boundaryGap:[0,0.1]
					}],
					series:[{
						name:"问题列表",
						type:"bar",
						stack:"sum",
						barCategoryGap:"50%",
						itemStyle:{
							normal:{
						        color:"#7BC0F2",
						        borderColor:"tomato",
						        borderWidth:6,
						        borderRadius:6,
						        label:{
						            show:true,
						            position:"insideTop"
						        }
						    }
						},
						data:[260,200,220,120]
					}]
				};				
				$chart.setOption($option);
			}
			/*
					饼图
				*/
			function DrawPieCharts(ec){
				$chart = ec.init(document.getElementById("chartDiv"),"blue");
				var $option = {
					title:{
						text:"",
				        subtext:"",
						x:"center"
					},
					tooltip:{
						trigger:"item",
						formatter:"{a} <br/>{b} : {c} ({d}%)"
					},
					legend:{
						type:"category",
				        data:["唯一性","完整性","一致性","其他"]
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
					series:[{
						name:"",
						type:"pie",
						radius:"50%",
						center:["50%","50%"],
						data:[{
							value:75,
							name:"唯一性"
						},{
							value:220,
							name:"完整性"
						},{
							value:300,
							name:"一致性"
						},{
							value:120,
							name:"其他"
						}]
					}]
				};
				$chart.setOption($option);
			}
			/*
					线图
				*/
			function DrawLineCharts(ec){
				$chart = ec.init(document.getElementById("chartDiv"),"blue");
				var $option = {
					title:{
						text:"",
						subtext:""
					},
					tooltip:{
						trigger:"axis"
					},
					legend:{
						data:["环比得分"]
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
						boundaryGap:false,
						data:["一季度","二季度","三季度","四季度"]
					}],
					yAxis:[{
						type:"value",
						axisLabel:{
							formatter:"{value} %"
						}
					}],
					series:[{
						name:"环比得分",
						type:"line",
						data:[23,53,72,45],
						markPoint:{
							data:[{
								type:"max",
								name:"最大值"
							},{
								type:"min",
								name:"最小值"
							}]
						},
						markLine:{
							data:[{
								type:"average",
								name:"平均值"
							}]
						}
					}]
				};
				$chart.setOption($option);
			}			
			function createChart(){
				var chartType = document.getElementById("chartTypeSelection").value;
				if(chartType=="-"){
					require.config({
						paths: {
							echarts:"${pageContext.request.contextPath}/js"
						}
					});
					require([
						"echarts",
						"echarts/chart/bar"
					],DrawNullCharts);
				}else{
					if(chartType=="柱图"){
						require.config({
							paths: {
								echarts:"${pageContext.request.contextPath}/js"
							}
						});
						require([
							"echarts",
							"echarts/chart/bar"
						],DrawBarCharts);
					}else if(chartType=="饼图"){
						require.config({
							paths:{
								echarts:"${pageContext.request.contextPath}/js"
							}
						});
						require([
							"echarts",
							"echarts/chart/pie"
						],DrawPieCharts);
					}else if(chartType=="线图"){
						require.config({
							paths:{
								echarts:"${pageContext.request.contextPath}/js"
							}
						});
						require([
							"echarts",
							"echarts/chart/line"
						],DrawLineCharts);
					}
				}
			}
		</script>
  	</head>
  	<body style="width:100%;background-color:#F3F3F3">
  		<center>
			<div style="width:98%;height:220px;border:solid 1px #A3D0E3;margin-top:5px;border-radius:7px;">
				<div style="float:left;width:80%;height:200px;margin-top:8px;margin-left:10px;">
					<textarea style="width:100%;height:200px;border:solid 1px #A3D0E3;" placeholder=" 请输入待执行的SQL语句"></textarea>
				</div>
				<div style="float:left;width:17%;height:200px;margin-top:8px;margin-left:10px;">
					<select style="width:125px;height:35px;font-size:12px;margin-top:10px;border:solid 1px #A3D0E3">
						<option>请选择数据源</option>
						<option>综资数据库</option>
						<option>综合代维库</option>
					</select>
					<select style="width:125px;height:35px;font-size:12px;border:solid 1px #A3D0E3" id="chartTypeSelection">
						<option value="-">请选择报表形式</option>
						<option value="柱图">柱图</option>
						<option value="饼图">饼图</option>
						<option value="线图">线图</option>
					</select>
					<button class="btn btn-primary" style="cursor:pointer;width:125px;height:35px;" onclick="javascript:createChart();">
						<i class="icon-film icon-white"></i>生成报表
					</button>
					<button class="btn btn-warning" style="cursor:pointer;width:125px;height:35px;margin-top:10px;">
						<i class="icon-file icon-white"></i>导出问题列表
					</button>
				</div>
			</div>
			<div style="width:99%;height:230px;margin-top:5px;border-radius:7px;">
				<div style="float:left;width:99%;height:225px;border:solid 1px #A3D0E3;margin-left:3px;border-radius:7px;" id="chartDiv"></div>
			</div>
		</center>
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<title>出账单报表</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/todc-bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/js/table/superTable.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/body-layout.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/content/css/test.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#bodyContainer").css({
			    	"height":$("#bodyHeight").height()+15
			    });
				flushMainChart();
			});
			function flushMainChart(){
				window.parent.showLoading(45,47);
				$.ajax({
					url:"${pageContext.request.contextPath}/czByMajorService/czByCity.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						window.parent.hideLoading();
						if(textStatus["SUCCESS"]){
							var chartConfig = textStatus["CHART_CONFIG"];
							lineOption.xAxis[0].data = chartConfig["xAxis"];
							lineOption.toolbox.show = false;
							lineOption.series[0].data = chartConfig["tlData"];
							lineOption.series[1].data = chartConfig["sfData"];
							lineOption.series[2].data = chartConfig["csData"];
							lineOption.series[3].data = chartConfig["wzData"];
							initLineChart();
						}
					},
					error:function(){
						window.parent.hideLoading();
					}
				});
			}
			var lineOption = {
	            tooltip:{
	                show:true
	            },
	            legend:{
	                data:["塔类","室分","传输","微站"]
	            },
	            toolbox:{  
	                show:true,
	                feature:{ 
	                    selfButtons:{    
							show:true,    
	                        title:"返回",    
	                        icon:"${pageContext.request.contextPath}/icons/rebackmenu.png",    
	                        option:{},    
							onclick:function(options){    
								flushMainChart();
							}    
						}
	                }  
	            },
	            xAxis:[{
	            	type:"category",
	                data:["成都","乐山","绵阳","德阳","眉山","达州","内江","南充","广安","广元","甘孜","阿坝","绵阳","德阳","眉山","达州","内江","南充","广安","广元","甘孜","阿坝"]
	            }],
	            yAxis:[{
                 	type:"value",
					name:"出账额度（元）",
					position:"left"
				}],
	            series:[{
					"name":"塔类",
					"type":"bar",
					"stack":"出账额度（元）",
					"data":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
					"barWidth":40
				},{
	                "name":"室分",
	                "type":"bar",
					"stack":"出账额度（元）",
	                "data":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	                "barWidth":40
				},{
	                "name":"传输",
	                "type":"bar",
					"stack":"出账额度（元）",
	                "data":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	                "barWidth":40
	            },{
	                "name":"微站",
	                "type":"bar",
					"stack":"出账额度（元）",
	                "data":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	            	"barWidth":40
				}]
	        };
			function initLineChart(){
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				require([
					"echarts",
					"echarts/chart/bar"
				],DrawLineChart);
			}
			function DrawLineChart(ec){
				var lineChart = ec.init(document.getElementById("bodyContainer"),"infographic");
				lineChart.setOption(lineOption);
				var ecConfig = require("echarts/config");
				lineChart.on(ecConfig.EVENT.CLICK,function(param){
					if(typeof param.seriesIndex=="undefined"){    
				        return;    
				    }    
				    if(param.type=="click"){    
				        var CityName = param.name;
				        window.parent.showLoading(45,47);
						$.ajax({
							url:"${pageContext.request.contextPath}/czByMajorService/czByCityMonth.ilf",
							async:false,
							type:"POST",
							data:"cityName="+CityName,
							dataType:"json",
							timeout:10000,
							success:function(textStatus){
								window.parent.hideLoading();
								if(textStatus["SUCCESS"]){
									var chartConfig = textStatus["CHART_CONFIG"];
									lineOption.toolbox.show = true;
									lineOption.xAxis[0].data = chartConfig["xAxis"];
									lineOption.series[0].data = chartConfig["tlData"];
									lineOption.series[1].data = chartConfig["sfData"];
									lineOption.series[2].data = chartConfig["csData"];
									lineOption.series[3].data = chartConfig["wzData"];
									initLineChart();
								}
							},
							error:function(){
								window.parent.hideLoading();
							}
						});
				    }  
				});	
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
    	<div style="width:99.9%;height:400px;overflow:auto;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;margin-top:3px;" id="bodyContainer">
    		&nbsp;
    	</div>
    </body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/ConScroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/plugins.js"></script>
</html>
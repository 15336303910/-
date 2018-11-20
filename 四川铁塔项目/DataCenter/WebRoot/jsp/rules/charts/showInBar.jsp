<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String demoCode = request.getParameter("demoCode");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>核查结果</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript">
			var columnTitle = [];
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
				/*获取数据进行初始化*/
				window.parent.showLoading(45,42);
				var demoCode = $("#hiddenOfDemoCode").val();
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/barCountChart.ilf",
					async:false,
					type:"POST",
					data:"demoCode="+demoCode,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.hideLoading();
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
			function eConsole(param) {    
			    if(typeof param.seriesIndex=="undefined"){    
			        return;    
			    }    
			    if(param.type=="click"){
			    	window.wxc.xcConfirm("即将跳转至核查结果详情.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							var ruleName = param.name;
							var recTotal = param.value;
					        $.ajax({
								url:"${pageContext.request.contextPath}/ruleEditAction/findRuleByName.ilf",
								async:true,
								type:"POST",
								data:"ruleName="+ruleName,
								dataType:"json",
								timeout:10000,
								success:function(textStatus){
									if(textStatus["success"] && textStatus["isFinded"]){
										var ruleObject = textStatus["result"];
										window.parent.turnToJsp("问题数据详情","jsp/rules/prolDetails.jsp?ruleId="+ruleObject["ID"]+"&total="+recTotal);
									}
								},
								error:function(){}
							});	
						}
					});		        
			    }    
			}
			function drawChart(ec){
				var thisChart = ec.init(document.getElementById("initDiv"),"macarons");
				var ecConfig = require("echarts/config");
				thisChart.on(ecConfig.EVENT.CLICK,eConsole);				
				var $option = {
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:[""]
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
						data:columnTitle,
						axisLabel:{
							interval:0,
							formatter:function(params){
                                var newParamsName = "";
                                var paramsNameNumber = params.length;
                                var provideNumber = 7;
                                var rowNumber = Math.ceil(paramsNameNumber/provideNumber);
                                if(paramsNameNumber>provideNumber){
                                    for(var p=0;p<rowNumber;p++){
                                        var tempStr = "";
                                        var start = p*provideNumber;
                                        var end = start + provideNumber;
                                        if(p == rowNumber-1){
                                            tempStr = params.substring(start,paramsNameNumber);
                                        }else{
                                            tempStr = params.substring(start,end)+"\n";
                                        }
                                        newParamsName += tempStr;
                                    }
                                }else{
                                    newParamsName = params;
                                }
                                return newParamsName
                            }
						}
				   	}],
				    yAxis:[{
						type:"value",
						axisLabel:{
							formatter:"{value}"
						}
					}],
				    series:[{
						name:"",
						type:"bar",
						barWidth:30, 
						data:columnValue,
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
			function getRandom(){
				var thisValue = Math.random();
				thisValue = parseInt(thisValue*100);
				return thisValue; 
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" value="<%=demoCode %>" id="hiddenOfDemoCode"></input>
		<div class="panel panel-default" style="margin-top:5px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="chartPanel">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>核查结果 >> 最近一次的核查结果统计
			</div>
			<div class="panel-body">
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
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>数据体检</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style>
			.jstrees{
				overflow:auto;
				border:1px solid silver;
				min-height:100px;
				text-align:left;
				font-size:15px;
				font-size:2.4em;
				font-size:62.5%;
				float:left;
			}
		</style>
		<script type="text/javascript">
			function RandomNum(Min,Max){
				var Range = Max - Min;
				var Rand = Math.random();   
				var num = Min + Math.round(Rand * Range);
				return num;
			}
			var myChart1 = null;
			var myChart2 = null;
			var myChart3 = null;
			$(document).ready(function(){
				/*
					渲染左侧资源结构并绑定单击事件
				*/
				$("#frmt").on("changed.jstree",function(e,data){
					if(data.selected.length){
						var nodeText = data.instance.get_node(data.selected[0]).text;
						/*渲染得分情况：报表*/
						var randomValue = RandomNum(1,100);
						var option1 = findConfig(true,"",randomValue);
						myChart1.setOption(option1);
						/*渲染得分情况：得分*/
						document.getElementById("totalScores").innerHTML = randomValue;
						/*渲染得分情况：评级*/
						var tipStr = "";
						if(randomValue<=50){
							tipStr = "较差";
						}else if((50<randomValue) && (randomValue<=90)){
							tipStr = "一般";
						}else if(90<randomValue){
							tipStr = "良好";
						} 
						document.getElementById("totalGrade").innerHTML = tipStr;
						/*唯一性*/
						randomValue = RandomNum(1,100);
						var option2 = findConfig(false,"唯一性",randomValue);
						myChart2.setOption(option2);
						/*完整性*/
						randomValue = RandomNum(1,100);
						var option3 = findConfig(false,"完整性",randomValue);
						myChart3.setOption(option3);
						/*问题统计*/
						randomValue = RandomNum(1,200);
						document.getElementById("aaaaaa").innerHTML = randomValue;
						randomValue = RandomNum(1,200);
						document.getElementById("bbbbbb").innerHTML = randomValue;
						randomValue = RandomNum(1,200);
						document.getElementById("cccccc").innerHTML = randomValue;
					}
				}).jstree({
					"core":{
						"data":[{
							"text":"综合资源系统",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"传输外线",
								"state":{
									"opened":true
								},
								"children":[{
									"text":"机房",
									"icon":"jstree-file",
									"state":{
										"selected":false
									}
								},{
									"text":"传输网元",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								},{
									"text":"杆路",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								},{
									"text":"设备",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								}]
							}]
						},{
							"text":"集团综合代维",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"集客",
								"state":{
									"opened":true
								},
								"children":[{
									"text":"考核主表",
									"icon":"jstree-file",
									"state":{
										"selected":false
									}
								},{
									"text":"费用主表",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								},{
									"text":"工单主表",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								}]
							}]
						}]
					}
				});
				/*
					渲染相关的图表
				*/
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				require([
					"echarts",
					"echarts/chart/gauge"
				],DrawCharts);
				require([
					"echarts",
					"echarts/chart/gauge"
				],DrawOthers);
				function DrawOthers(ec){
					myChart2 = ec.init(document.getElementById("uniqueChart"),"macarons");
					myChart3 = ec.init(document.getElementById("completeChart"),"macarons");
					var option2 = findConfig(false,"唯一性",76.7);
					myChart2.setOption(option2);
					var option3 = findConfig(false,"完整性",35.3);	
					myChart3.setOption(option3);
				}
				function DrawCharts(ec){
					myChart1 = ec.init(document.getElementById("maina"),"macarons");
					var option1 = findConfig(true,"",62.7);
					myChart1.setOption(option1);
				}
			});
			function findConfig(isMain,title,value){
				var option1 = null;
				if(isMain){
					option1 = {
						tooltip:{
							formatter: "{a} <br/>{b}:{c}%"
						},
						toolbox:{
							show:true,
							feature:{
								mark:{
									show:false
								},
								restore:{
									show:false
								},
								saveAsImage:{
									show:false
								}
							}
						},
						series:[{
							name:"得分",
							type:"gauge",
							center:["55%","53%"],
							splitNumber:10,
							axisLine:{
								lineStyle:{
									color:[
										[0.2,"skyblue"],
										[0.4,"lightgreen"],
										[0.8,"orange"],
										[1,"#ff4500"]
									],
									width:8
								}
							},
							axisTick:{
								splitNumber:10,
								length:12,
								lineStyle:{
									color:"auto"
								}
							},
							axisLabel:{
								textStyle:{
									color:"auto"
								}
							},
							splitLine:{
								show:true,
								length:30,
								lineStyle:{
									color:"auto"
								}
							},
							pointer:{
								width:5
							},
							title:{
								show:true,
								offsetCenter:[0,"-40%"],
								textStyle:{
									fontWeight:"bolder"
								}
							},
							detail:{
								formatter:"{value}",
								textStyle:{
									color:"auto",
									fontWeight:"bolder"
								}
							},
							data:[{value:value,name:title}]
						}]
					};	
				}else{
					option1 = {
					    toolbox:{
					        show:true,
					        feature:{
					            mark:{
									show:false
								},
					            restore:{
									show:false
								},
					            saveAsImage:{
									show:false
								}
					        }
					    },
					    series:[{
							name:"",
							type:"gauge",
							center:["38%","50%"],
							radius:[0,"75%"],
							startAngle:140,
							endAngle:-140,
							min:0,
							max:100,
							precision:0,
							splitNumber:10,
							axisLine:{
								show:true,
								lineStyle:{
									color:[
										[0.2,"skyblue"],
										[0.4,"lightgreen"],
										[0.8,"orange"],
										[1,"#ff4500"]
									], 
					                width:30
								}
							},
					        axisTick:{
								show:true,
					            splitNumber:5,
					            length:8,
					            lineStyle:{
					                color:"#eee",
					                width:1,
					                type:"solid"
					            }
					        },
					        axisLabel:{
								show:true,
								formatter:function(v){
									switch(v+""){
										case "10": return "弱";
										case "30": return "低";
										case "60": return "中";
										case "90": return "高";
										default: return "";
									}
								},
								textStyle:{
									color:"#333"
								}
							},
					        splitLine:{
								show:true,
					            length:30,
					            lineStyle:{
									color:"#eee",
									width:2,
									type:"solid"
					            }
					        },
					        pointer:{
								length:"80%",
					            width:8,
					            color:"auto"
					        },
					        title:{
								show:true,
								offsetCenter:["-65%",-10],
					            textStyle:{
									color:"#333",
									fontSize:15
								}
							},
							detail:{
								show:true,
								backgroundColor:"rgba(0,0,0,0)",
								borderWidth:0,
								borderColor:"#ccc",
								width:100,
								height:40,
								offsetCenter:["-60%",10],
								formatter:"{value}分",
								textStyle:{
									color:"auto",
									fontSize:17
								}
							},
							data:[{value:value,name:title}]
						}]
					};
				}
				return option1;
			}
		</script>
  	</head>
  	<body style="background:#ECEFF0;">
  		<center>
  			<div style="width:20%;height:460px;border:solid 1px #53BFC6" id="frmt" class="jstrees"></div> 			
    		<div style="float:left;width:79%;height:460px;border:solid 1px #53BFC6;margin-left:2px;">
    			<div style="width:98%;height:240px;">
    				<div style="width:99%;height:460px;">
    					<div style="width:40%;height:250px;float:left;" id="maina"></div>
    					<div style="width:59%;height:250px;float:left;margin-left:5px;">
    						<div style="width:85%;height:35px;text-align:left;font-size:20px;font-family:'楷体';margin-top:40px;">
    							最近一次数据质量得分为：<font color="red" id="totalScores">62.7</font>
    						</div>
    						<div style="width:85%;height:35px;text-align:left;font-size:20px;font-family:'楷体';margin-top:15px;">
    							最近一次数据质量评级为：<font color="red" id="totalGrade">一般</font>
    						</div>
    						<div style="width:85%;height:35px;text-align:left;font-size:24px;font-family:'楷体';margin-top:25px;">
    							<input type="button" value="立即体检" style="-webkit-box-shadow:0 0 5px rgba(11,11,11,11);-webkit-transition:border linear .2s,-webkit-box-shadow linear .2s;width:160px;height:55px;cursor:pointer;border-radius:10px;font-size:24px;font-family:'楷体';border:solid 1px #4488BB;background-color:#22E783;"></input>
    						</div>
    					</div>
    					<div style="width:99.6%;height:200px;float:left;margin-top:5px;border-top:dotted 1px #4488BB;">
    						<div style="width:44%;height:200px;float:left;">
    							<div style="width:72%;height:35px;text-align:left;font-size:14px;font-family:'楷体';margin-top:55px;">
    								发现很严重问题：<font color="red" id="aaaaaa">20</font>个
    								<a style="margin-left:10px;" href="#">导出问题及建议</a>
    							</div>
    							<div style="width:72%;height:35px;text-align:left;font-size:14px;font-family:'楷体';">
    								发现严重问题：<font color="red" id="bbbbbb">103</font>个
    								<a style="margin-left:10px;" href="#">导出问题及建议</a>
    							</div>
    							<div style="width:72%;height:35px;text-align:left;font-size:14px;font-family:'楷体';">
    								发现一般问题：<font color="red" id="cccccc">95</font>个
    								<a style="margin-left:10px;" href="#">导出问题及建议</a>
    							</div>
    						</div>
    						<div style="width:55%;height:200px;float:left;margin-left:5px;">
    							<div style="width:49%;height:200px;float:left;" id="uniqueChart"></div>
    							<div style="width:49%;height:200px;float:left;margin-left:5px;" id="completeChart"></div>
    						</div>
    					</div>
    				</div>
    			</div>
    		</div>
    	</center>
  	</body>
</html>

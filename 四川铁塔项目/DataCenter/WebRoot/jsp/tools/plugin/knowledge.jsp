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
    	<title>知识图谱</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/AutoComplete/css/jquery.bigautocomplete.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/AutoComplete/js/jquery.bigautocomplete.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#chartContainer").css({
			    	"height":$("#bodyHeight").height()-$("#panelHeading").height()-5
			    });
				$("#initDiv").css({
			    	"height":$("#chartContainer").height()-20
			    });
				require.config({
					paths:{
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				require([
					"echarts",
					"echarts/chart/force"
				],drawChart);
				$("#aResourceInput").bigAutocomplete({
					width:200,
					data:[{
						title:"石家庄正定正定2"
					}],
					callback:function(data){}
				});
				$("#zResourceInput").bigAutocomplete({
					width:200,
					data:[{
						title:"石家庄正定县交通局18"
					}],
					callback:function(data){}
				});
			});
			function flushAInput(){
				var aInputValue = $("#aResourceInput").val();
				if(aInputValue==""){
					$("#aResourceInput").bigAutocomplete({
						width:200,
						data:[],
						callback:function(data){
								
						}
					});
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/knowledgeMapAction/findResources.ilf",
						async:true,
						type:"POST",
						data:"resourceName="+aInputValue,
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["SUCCESS"]){
								var names = textStatus["NAMES"];
								$("#aResourceInput").bigAutocomplete({
									width:200,
									data:names,
									callback:function(data){
										var valueLength = data.title.length;
										if(valueLength<=10){
											$("#aResourceInput").css({
												width:200
											});
										}else{
											$("#aResourceInput").css({
												width:10*valueLength
											});
										}
									}
								});
							}
						},
						error:function(){}
					});
				}
			}
			function flushZInput(){
				var zInputValue = $("#zResourceInput").val();
				if(zInputValue==""){
					$("#zResourceInput").bigAutocomplete({
						width:200,
						data:[],
						callback:function(data){
								
						}
					});
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/knowledgeMapAction/findResources.ilf",
						async:true,
						type:"POST",
						data:"resourceName="+zInputValue,
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["SUCCESS"]){
								var names = textStatus["NAMES"];
								$("#zResourceInput").bigAutocomplete({
									width:200,
									data:names,
									callback:function(data){
										var valueLength = data.title.length;
										if(valueLength<=10){
											$("#zResourceInput").css({
												width:200
											});
										}else{
											$("#zResourceInput").css({
												width:10*valueLength
											});
										}
									}
								});
							}
						},
						error:function(){}
					});
				}
			}
			var analysisOption = {
				title:{
					text:"资源关系分析图谱",
					subtext:"",
					x:"right",
					y:"bottom"
				},
				tooltip:{
					trigger:"item",
					formatter:"{a}:{b}"
				},
				toolbox:{
					show:true,
					feature:{
						restore:{
							show:true
						},
						saveAsImage:{
							show:true
						}
					}
				},
				legend:{
					x:"left",
					data:["ODF","光缆段","站点"]
				},
				series:[{
					type:"force",
					name:"资源关系分析图谱",
					radius:"70%",
					categories:[{
			            "name":"ODF"
			        },{
			            "name":"光缆段"
			        },{
			            "name":"站点"
			        }],
					itemStyle:{
						normal:{
							label:{
								show:true,
								textStyle:{
									color:"black"
								}
							},
							nodeStyle:{
								brushType:"both",
								strokeColor:"rgba(255,215,0,0.4)",
								lineWidth:1
							}
						},
						emphasis:{
							label:{
								show:false
							},
							nodeStyle:{},
							linkStyle:{}
						}
					},
		            minRadius:15,
		            maxRadius:25,
		            density:0.05,
		            attractiveness:1.2,
		            linkSymbol:"arrow",
		            draggable:true,
		            nodes:[{
		                "category":0,
		                "name":"ODF001",
		                "value":0
		            },{
		                "category":1,
		                "name":"燕赵北基站-燕曙基站（石家庄正定燕曙-石家庄正定燕赵北）",
		                "value":5
		            },{
		                "category":1,
		                "name":"石家庄正定燕赵北-石家庄正定县交通局18",
		                "value":7
		            },{
		                "category":1,
		                "name":"石家庄正定燕赵北-石家庄正定正定2",
		                "value":4
		            },{
		                "category":1,
		                "name":"石家庄正定燕赵北-石家庄正定小蜜蜂家园室分",
		                "value":1
		            },{
		                "category":2,
		                "name":"石家庄正定燕赵北",
		                "value":4
		            },{
		                "category":2,
		                "name":"石家庄正定正定2",
		                "value":10
		            },{
		                "category":2,
		                "name":"石家庄正定县交通局18",
		                "value":4
		            }],
				    links:[
						{"source":1,"target":0},
				        {"source":1,"target":5},
				        {"source":2,"target":0},
				        {"source":2,"target":7},
				        {"source":3,"target":6},
				        {"source":3,"target":0},
				        {"source":4,"target":5},
				        {"source":4,"target":0},
				        {"source":3,"target":0},
				        {"source":1,"target":0},
				        {"source":4,"target":0},
				        {"source":2,"target":0},
				        {"source":1,"target":5},
				        {"source":4,"target":5},
				        {"source":3,"target":6},
				        {"source":2,"target":7}
			        ]
				}]
			};
			var myChart = null;
			function drawChart(ec){
				myChart = ec.init(document.getElementById("initDiv"),"macarons");
				myChart.setOption(analysisOption);
			}
			function doAnalysis(){
				var aResourceName = $("#aResourceInput").val();
				if(aResourceName==""){
					window.wxc.xcConfirm("请输入资源名称.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				var zResourceName = $("#zResourceInput").val();
				if(zResourceName==""){
					window.wxc.xcConfirm("请输入资源名称.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				var params = {
					aResName:aResourceName,
					zResName:zResourceName,
					grade:$("#analyseGradeSelect").val()
				};
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/knowledgeMapAction/doAnalysis.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(params),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["SUCCESS"]){
							analysisOption = {
								title:{
									text:"资源关系分析图谱",
									subtext:"",
									x:"right",
									y:"bottom"
								},
								tooltip:{
									trigger:"item",
									formatter:"{a}:{b}"
								},
								toolbox:{
									show:true,
									feature:{
										restore:{
											show:true
										},
										saveAsImage:{
											show:true
										}
									}
								},
								legend:{
									x:"left",
									data:textStatus["legentData"]
								},
								series:[{
									type:"force",
									name:"资源关系分析图谱",
									categories:textStatus["categories"],
									itemStyle:{
										normal:{
											label:{
												show:true,
												textStyle:{
													color:"black"
												}
											},
											nodeStyle:{
												brushType:"both",
												strokeColor:"rgba(255,215,0,0.4)",
												lineWidth:1
											}
										},
										emphasis:{
											label:{
												show:false
											},
											nodeStyle:{},
											linkStyle:{}
										}
									},
						            minRadius:15,
						            maxRadius:25,
						            density:0.05,
						            attractiveness:1.2,
						            linkSymbol:"arrow",
						            draggable:true,
						            nodes:textStatus["nodes"],
								    links:textStatus["links"]
								}]
							};
							myChart.setOption(analysisOption,true);
						}else{
							window.wxc.xcConfirm(textStatus["MESSAGE"],window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("分析异常，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.9%;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:1px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-heading" style="border:solid 1px #CCC;border-bottom:0px;" id="panelHeading">
				<span class="panel-label"></span>互联网工具 >> 知识图谱
				<form class="form-search pull-right">
					<div style="float:right;">
						<input type="text" placeholder="请输入资源名称" value="石家庄正定正定2" onchange="javascript:flushAInput();" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="aResourceInput"></input>
						<input type="text" placeholder="请输入资源名称" value="石家庄正定县交通局18" onchange="javascript:flushZInput();" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="zResourceInput"></input>
						<select id="analyseGradeSelect" style="width:130px;height:29px;border:solid 1px #A3D0E3;">
							<option value="1">搜索层数：1</option>
							<option value="2">搜索层数：2</option>
							<option value="3">搜索层数：3</option>
							<option value="4">搜索层数：4</option>
							<option value="5" selected>搜索层数：5</option>
							<option value="6">搜索层数：6</option>
							<option value="7">搜索层数：7</option>
							<option value="8">搜索层数：8</option>
							<option value="9">搜索层数：9</option>
							<option value="10">搜索层数：10</option>
						</select>
						<a class="btn btn-info"  style="cursor:pointer;" onclick="javascript:doAnalysis();">
							<i class="icon-film icon-white"></i>执行分析
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<div style="width:99.9%;height:200px;border:solid 1px #CCC;" id="chartContainer">
								<center>
									<div style="width:95%;height:200px;margin-top:10px;" id="initDiv"></div>
								</center>
							</div>
						</div>
					</div>
				</div>
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
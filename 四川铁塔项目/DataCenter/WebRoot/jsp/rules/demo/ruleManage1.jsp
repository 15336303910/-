<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>规则管理</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/smartwizard/css/smart_wizard.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/smartwizard/jquery.smartWizard.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style type="text/css">
			.jstrees{
				overflow:auto;
				border:1px solid silver;
				min-height:100px;
				text-align:left;
				font-size:18px;
				font-size:2.4em;
				font-size:100%;
				float:left;
			}
		</style>
		<script type="text/javascript">
			var colorPool = [
				"#FF9A56",
				"#39DF87",
				"#4B8EFD",
				"#FBB14A",
				"#D94937",
				"#35A946",
				"#59BFDE",
				"#505050"
			];
			function showOrHide(typeCode){
				if(typeCode==1){
					$("#ruleShowDiv").show(300);
					$("#ruleConfigDiv").hide();
					$("#ruleEditDiv").hide();
				}else if(typeCode==2){
					$("#ruleShowDiv").hide();
					$("#ruleEditDiv").hide();
					$("#ruleConfigDiv").show(500);
				}else if(typeCode==3){
					$(".models").hide();
					$("#ruleEditDiv").show(300);
				}else if(typeCode==4){
					$("#ruleEditDiv").hide();
					$("#mainPanel").show();
					showOrHide(2);
				}
			}
			function onFinishCallback(){}
			function leaveAStepCallback(obj){
		    	return true;
		    }
			function brownDetail(){
		    	var buttonNexts = document.getElementsByClassName("buttonNext");
		    	if(buttonNexts.length>0){
		    		var buttonNext = buttonNexts[0];
		    		buttonNext.click();
		    	}
		    }
			$(document).ready(function(){
				$("#wizard1").smartWizard({
		  			transitionEffect:"fade",
		  			keyNavigation:false,
		  			onFinish:onFinishCallback,
		  			onLeaveStep:leaveAStepCallback
		  		});
				$("#frmt").jstree({
					"core":{
						"data":[{
							"text":"综合资源系统",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"传输外线",
								"state":{
									"opened":true,
									"selected":false
								},
								"children":[{
									"text":"机房表",
									"state":{
										"selected":true
									}
								}]
							},{
								"text":"集客",
								"state":{
									"opened":false,
									"selected":false
								}
							},{
								"text":"家客",
								"state":{
									"opened":false,
									"selected":false
								}
							},{
								"text":"室分与WLAN",
								"state":{
									"opened":false,
									"selected":false
								}
							}]
						},{
							"text":"集团综合代维",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"传输外线",
								"state":{
									"opened":false,
									"selected":false
								}
							},{
								"text":"集客",
								"state":{
									"opened":false,
									"selected":false
								}
							},{
								"text":"家客",
								"state":{
									"opened":false,
									"selected":false
								}
							},{
								"text":"室分与WLAN",
								"state":{
									"opened":false,
									"selected":false
								}
							}]
						}]
					}
				});
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
				var thisChart = ec.init(document.getElementById("maina"),"macarons");
				var $option = {
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:["问题数"]
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
						data:["人井要归属某个管道段","电杆要归属某个杆路段","标石要归属某个直埋段","管道段无敷设的光缆信息"]
				   	}],
				    yAxis:[{
						type:"value",
						axisLabel:{
							formatter:"{value}"
						}
					}],
				    series:[{
						name:"问题数",
						type:"bar",
						barWidth:40, 
						data:[83,125,61,93],
						markPoint:{
			                data:[{
								type:"max",name:"最大值"
							},{
								type:"min",name:"最小值"
							}]
			            },
			            markLine:{
			            	data:[{
			            		type:"average",name:"平均值"
			            	}]
			            }
					}]
				};
				thisChart.setOption($option);
			}
			function turnToTeamShow($value){
				if($value==1){
					window.parent.turnToJsp("分组总览","jsp/rules/showInGroup.jsp");	
				}else if($value==2){
					window.parent.turnToJsp("历史分析","jsp/rules/showInLine.jsp");
				}else{
					//window.parent.turnToJsp("规则编辑器","jsp/configs/ruleEdit.jsp");
					$("#ruleConfigDiv").show();
				}
			}
			var acheObject = null;
			function doSave(){
				var $value = $("#expressionInput").val();
				var $isFilter = false;
				var items = document.getElementsByName("isFilterNull");
				for(var i=0;i<items.length;i++){
					if(items[i].checked){
						$isFilter = true;
						break;
					}
				}	
				if(acheObject==null){
					acheObject = new HashMap();
				}
				var $date = new Date();
				var $time = $date.getTime();
				var finalObject = {
					expressKey:$time,
					expression:$value,
					isFilter:$isFilter
				};
				acheObject.put($time,finalObject);
				var innerHtml="<table class='table table-bordered table-hover' style='border:0px;'>";
						innerHtml+="<tbody>";
							innerHtml+="<tr>";
								innerHtml+="<td style='width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>&nbsp;</td>";
								innerHtml+="<td style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>核查属性</td>";
								innerHtml+="<td style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>表达式/核查类型</td>";
								innerHtml+="<td style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>是否忽略空值</td>";
							innerHtml+="</tr>";	
						if(acheObject!=null && acheObject.valueSet().length>0){
							var items = acheObject.valueSet();
							for(var j=0;j<items.length;j++){
								var $obj = items[j];
								innerHtml+="<tr>";
									innerHtml+="<td style='text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>";
										innerHtml+="<input type='checkbox' value='' name='editedItem'></input>";
									innerHtml+="</td>";
									innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>机房类型（编号）</td>";
									innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>"+$obj["expression"]+"</td>";
									innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>";
										if($obj["isFilter"]){
											innerHtml+="<font color='red'>是</font>";	
										}else{
											innerHtml+="<font color='green'>否</font>";
										}
									innerHtml+="</td>";
								innerHtml+="</tr>";		
							}
						}
						innerHtml+="</tbody>";
					innerHtml+="</table>";
				document.getElementById("editedItems").innerHTML = innerHtml;
			}
			function relateChange($isChecked,$checkedValue){
				if($isChecked){
					var checkboxes = document.getElementsByName("isRelate");
					for(var i=0;i<checkboxes.length;i++){
						var $checkbox = checkboxes[i];
						if($checkbox.value!=$checkedValue){
							$checkbox.checked = false;
						}
					}
					$(".referColumn").show();
					$(".rightPanel").hide();
					$("#glassTable").show();
					$(".topButtons").show();
					$("#ruleNameButtons").hide();
					$("#errorRangeTr").hide();
					document.getElementById("saveButton").style.marginTop ="-3px";
					if($checkedValue=="核查枚举值"){
						$(".relatedColumns").hide();
						$(".valueColumnCheck").show();
						$(".functions").hide();
						$("#columnsTable").show();
					}else if($checkedValue=="核查关联数据条数"){
						$(".relatedColumns").hide();
						$(".valueColumnCheck").hide();
						$(".functions").hide();
						$("#columnsTable").show();
						$("#errorRangeTr").show();
					}else if($checkedValue=="数据一致性"){
						$(".valueColumnCheck").hide();
						$(".relatedColumns").show();
						$(".functions").hide();
						$("#columnsTable").show();
					}else if($checkedValue=="插件类规则"){
						$(".relatedColumns").hide();
						$(".referColumn").hide();
						$(".functions").hide();
						$(".topButtons").hide();
						$("#ruleNameButtons").show();
						$("#functionTable").show();
					}
				}else{
					var isInit = true;
					var checkboxes = document.getElementsByName("isRelate");
					for(var i=0;i<checkboxes.length;i++){
						var $checkbox = checkboxes[i];
						if($checkbox.checked){
							isInit = false;
							break;
						}
					}
					if(isInit){
						$(".referColumn").hide();
						$(".rightPanel").hide();
						$(".relatedColumns").hide();
						$("#editedTable").show(500);	
					}
				}
			}
		</script>
  	</head>
  	<body style="width:100%;">
  		<div class="panel panel-default models" style="margin-top:1px;height:490px;display:none;" id="ruleEditDiv">
  			<div class="panel-body h340" style="border:0px;">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #DFE8F1">
		  			<tr>
		    			<td>
		      				<div id="wizard1" class="swMain" style="height:462px;">
			        			<ul>
			          				<li>
			          					<a href="#step-1" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">1</span>
			          						<span class="stepDesc">
			          							第一步<br/>
			            						<small>编辑规则概要信息</small>
			            					</span>
			            				</a>
			            			</li>
			          				<li>
			          					<a href="#step-2" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">2</span>
			          						<span class="stepDesc">
			          							第二步<br/>
			            						<small>配置核查规则</small>
			            					</span>
			            				</a>
			            			</li>
			          				<li>
			          					<a href="#step-3" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">3</span>
			          						<span class="stepDesc">
			          							第三步<br/>
			            						<small>启用/调度</small>
			            					</span>
			            				</a>
			            			</li>
			        			</ul>
						        <div id="step-1" style="width:100%;border-top:solid 1px #CCC">
						        	<center>
										<table class="table table-bordered table-hover" style="width:700px;margin-top:20px;">
											<tr height="40">
												<td style="background:#F9FAFE;"><label class="l-name">核查目标.</label></td>
												<td>
													<font color="blue">综资数据库 >> 机房表</font>
													<a class="btn btn-warning" style="margin-left:335px;cursor:pointer;margin-top:-3px;" onclick="javascript:showOrHide(4);">
														<i class="icon-repeat icon-white"></i>返回
													</a>
												</td>
											</tr>
											<tr height="30">
												<td style="background:#F9FAFE;"><label class="l-name">规则名称.</label></td>
												<td><input type="text" placeholder="" style="width:530px;height:30px;margin-top:5px;"></td>
											</tr>
											<tr height="30">
												<td style="background:#F9FAFE;"><label class="l-name">规则分类.</label></td>
												<td>
													<select class="w500" style="width:530px">
														<option value="A">AD设备小区检查</option>
														<option value="B">DSLAM网检查</option>
														<option value="-">添加规则集合？</option>
													</select>
												</td>
											</tr>
											<tr height="130">
												<td style="background:#F9FAFE;"><label class="l-name">规则描述.</label></td>
												<td>
													<textarea style="width:530px;height:130px;"></textarea>
												</td>
											</tr>
										</table>
									</center>
						        </div>
						        <div id="step-2" style="width:100%;border-top:solid 1px #CCC">
					        		<div style="width:99%;height:322px;margin-left:2px;overflow:auto;">
					        			<%--左侧50%--%>
					        			<div style="float:left;width:50%;height:625px;">
					        				<div style="float:left;width:99%;height:276px;border:solid 1px #CCC;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>机房表
													<form class="form-search pull-right">	
														<div style="float:right;">
															<input type="text" placeholder="请输入属性名称" style="width:160px;height:30px;font-size:12px;"></input>
															<a class="btn btn-success" style="cursor:pointer;">
																<i class="icon-plus-sign icon-white"></i>执行查询
															</a>
														</div>
													</form>
												</div>
												<div class="panel-body h300" style="border:0px;">
													<div class="div_scroll">
														<div style="height:auto;width:auto;">
															<table class="table table-bordered table-hover" style="border:0px;">
																<tbody>
																	<tr>
																		<td style="width:5%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;"></td>
																		<td style="width:25%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">属性名称</td>
																		<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">属性标识</td>
																		<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;" class="relatedColumns">关联字段</td>
																		<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;" class="referColumn">关联属性</td>
																		<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;">限定条件</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="radio" value="" name="WWWWW"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_ID</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房编号</td>
																		<td class="relatedColumns" style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;">
																			<a style="cursor:pointer;">未关联</a>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;text-size:10px;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="radio" value="" name="WWWWW"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_NAME</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房名称</td>
																		<td class="relatedColumns" style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;"><a style="cursor:pointer;">未关联</a></td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="radio" value="" name="WWWWW"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">WIDTH</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">宽度</td>
																		<td class="relatedColumns" style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;"><a style="cursor:pointer;">未关联</a></td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="radio" value="" name="WWWWW"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_TYPE</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房类型（编号）</td>
																		<td class="relatedColumns" style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;"><a style="cursor:pointer;">未关联</a></td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="radio" value="" name="WWWWW"></input>
																		</td>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;">ROOM_NAME</td>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;">机房类型（名称）</td>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;display:none;" class="relatedColumns"><a style="cursor:pointer;">未关联</a></td>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;display:none;text-align:center;" class="referColumn">
																			<input type="radio" value=""></input>
																		</td>
																		<td style="border:0px;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;"></input>
																		</td>
																	</tr>
																</tbody>
															</table>						
														</div>
													</div>
												</div>
						        			</div>
						        			<div style="float:left;width:99%;height:120px;border:solid 1px #CCC;margin-top:5px;">
						        				<div style="width:100%;height:110px;">
						        					<div class="panel-heading">
														<span class="panel-label"></span>配置规则
													</div>
													<div style="margin-top:5px;">
														<input type="checkbox" value="Y" name="isFilterNull" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC"></input>
														<font style="margin-left:5px;margin-top:2px;">否忽略空值</font>
														<input type="checkbox" value="核查枚举值" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:relateChange(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">枚举值</font>
														<input type="checkbox" value="核查关联数据条数" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:relateChange(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">数据关联数目</font>
														<input type="checkbox" value="数据一致性" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:relateChange(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">数据一致性</font>
														<input type="checkbox" value="插件类规则" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:relateChange(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">插件类规则</font>
													</div>
													<input type="text" placeholder="" style="width:380px;height:29px;font-size:12px;margin-top:8px;margin-left:5px;border:solid 1px black;" id="expressionInput"></input>
													<a class="btn btn-success" style="cursor:pointer;margin-top:-1px;" onclick="javascript:doSave();">
														<i class="icon-plus-sign icon-white"></i>添加规则
													</a>
						        				</div>
						        			</div>
						        			<div style="float:left;width:99%;height:258px;border:solid 1px #CCC;margin-top:5px;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>规则表达式规范说明
												</div>
												<div class="panel-body h340" style="border:0px;">
													<div class="div_scroll">
														<div style="height:auto;width:auto;">
															<table class="table table-bordered table-hover" style="border:0px;">
																<tbody>
																	<tr>
																		<td style="border-left:0px;text-align:center;">1.</td>
																		<td>a-b表示取值范围，表示字段值的正常取值范围在a,b两个值之间（不包括a,b）.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">2.</td>
																		<td>a~b表示取值范围，表示字段值的正常取值范围在a,b两个值之间（包括a,b）.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">3.</td>
																		<td>a,b,c,d表示枚举范围，表达字段值必须在a,b,c,d范围内选取.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">4.</td>
																		<td>“NOT NULL”表示字段值不能为空.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">5.</td>
																		<td>“UNIQUE”表示字段值在本表中必须唯一.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">6.</td>
																		<td>“arg >= 105”表示字段值必须大于105.同类还有>,<,<=.</td>
																	</tr>
																	<tr>
																		<td style="border-left:0px;text-align:center;">7.</td>
																		<td>多类表达式可以使用“and”或“or”连接，例如"arg >= 30 and arg <=100"</td>
																	</tr>
																</tbody>
															</table>						
														</div>
													</div>
												</div>
						        			</div>
					        			</div>
					        			<div style="float:left;width:49%;height:668px;border:solid 1px #CCC;display:none;" class="rightPanel" id="glassTable">
					        				<div style="float:left;width:99%;height:226px;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>参照数据
													<form class="form-search pull-right">	
														<div style="float:right;">
															<input type="text" placeholder="请输入模型名称" style="width:160px;height:30px;font-size:12px;margin-top:-3px;" class="topButtons"></input>
															<input type="text" placeholder="请输入函数名称" style="width:160px;height:30px;font-size:12px;margin-top:-3px;display:none;" id="ruleNameButtons"></input>
															<a class="btn btn-primary" style="cursor:pointer;margin-top:-3px;">
																<i class="icon-search icon-white"></i>查询
															</a>
															<a class="btn btn-info" style="cursor:pointer;margin-top:-3px;" id="saveButton">
																<i class="icon-plus-sign icon-white"></i>保存 
															</a>
														</div>
													</form>
												</div>
												<div class="panel-body h600" style="border:0px;">
													<div class="div_scroll">
														<div style="height:auto;width:auto;">
															<%--函数表格：开始--%>
															<table class="table table-bordered table-hover functions" style="border:solid 1px #DFE8F1;border-top:0px;border-left:0px;display:none;" id="functionTable">
																<tbody>
																	<tr>
																		<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;background:#F9FEFA;border-right:solid 1px #DFE8F1;">&nbsp;</td>
																		<td style="width:90%;border:0px;border-bottom:solid 1px #DFE8F1;background:#F9FEFA;">函数名称</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="checkbox" name="PPPPP" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">数据完整性校验函数</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="checkbox" name="PPPPP" value=""></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">数据唯一性校验函数</td>
																	</tr>
																	<tr>
																		<td style="border:0px;border-right:solid 1px #DFE8F1;text-align:center;">
																			<input type="checkbox" name="PPPPP" value=""></input>
																		</td>
																		<td style="border:0px;">查找新增记录时间相隔较短的异常数据</td>
																	</tr>
																</tbody>
															</table>
															<%--常规表格：开始--%>
															<table class="table table-bordered table-hover functions" style="border:solid 1px #DFE8F1;border-top:0px;border-left:0px;" id="columnsTable">
																<tbody>
																	<tr>
																		<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;" class="valueColumnCheck">值属性</td>
																		<td style="width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">属性名称</td>
																		<td style="width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">属性标识</td>
																		<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;display:none;" class="referColumn">关联属性</td>
																		<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;">限定条件</td>
																	</tr>
																	<tr>
																		<td class="valueColumnCheck" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">
																			<input type="radio" value="" name="AAAAA"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_ID</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房编号</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value="" name="BBBBB"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;font-size:10px;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td class="valueColumnCheck"  style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">
																			<input type="radio" value="" name="AAAAA"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_NAME</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房名称</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value="" name="BBBBB"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;font-size:10px;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td class="valueColumnCheck" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">
																			<input type="radio" value="" name="AAAAA"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">WIDTH</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">宽度</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value="" name="BBBBB"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;font-size:10px;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td class="valueColumnCheck" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">
																			<input type="radio" value="" name="AAAAA"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_TYPE</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房类型（编号）</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value="" name="BBBBB"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;font-size:10px;"></input>
																		</td>
																	</tr>
																	<tr>
																		<td class="valueColumnCheck" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">
																			<input type="radio" value="" name="AAAAA"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">ROOM_NAME</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;">机房类型（名称）</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;text-align:center;display:none;" class="referColumn">
																			<input type="radio" value="" name="BBBBB"></input>
																		</td>
																		<td style="border:0px;border-bottom:solid 1px #DFE8F1;">
																			<input type="text" style="width:100%;height:20px;border:solid 1px #DEEFF8;font-size:10px;"></input>
																		</td>
																	</tr>
																	<tr id="errorRangeTr" style="display:none;">
																		<td style="border:0px;border-right:solid 1px #DFE8F1;">异常范围</td>
																		<td style="border:0px;;" colspan="5">
																			<input type="text" value="sum <= 3 or sum >= 500" style="width:90%;height:27px;margin-top:5px;border:solid 1px #DEEFF8;font-size:12px;"></input>
																		</td>
																	</tr>
																</tbody>
															</table>						
														</div>
													</div>
												</div>
						        			</div>
					        			</div> 
					        			<div style="float:left;width:49%;height:669px;" class="rightPanel" id="editedTable">
					        				<div style="float:left;width:99%;height:668px;border:solid 1px #CCC;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>已配置规则
													<form class="form-search pull-right">	
														<div style="float:right;">
															<a class="btn btn-danger"  style="cursor:pointer;margin-top:5px;">
																<i class="icon-trash icon-white"></i>删除规则
															</a>
														</div>
													</form>
												</div>
												<div class="div_scroll">
													<div style="height:auto;width:auto;" id="editedItems">
														<table class="table table-bordered table-hover" style="border:0px;">
															<tbody>
																<tr>
																	<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;">&nbsp;</td>
																	<td style="width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">核查属性</td>
																	<td style="width:40%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">表达式/核查类型</td>
																	<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">是否忽略空值</td>
																</tr>
																<tr>
																	<td colspan="4" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;">
																		<font color="red">尚未配置任何规则</font>																
																	</td>
																</tr>
															</tbody>
														</table>					
													</div>
												</div>
						        			</div>
					        			</div>
					        		</div>
						        </div>
						        <div id="step-3" style="width:100%;border-top:solid 1px #CCC">
						        	<center>
										<table class="table table-bordered table-hover" style="width:700px;margin-top:70px;">
											<tr height="40">
												<td style="background:#F9FAFE;"><label class="l-name">调度周期.</label></td>
												<td>
													<input type="text" value="0 0 2 * * ?" style="width:455px;height:30px;font-size:12px;"></input>													
													<a class="btn btn-warning"  style="cursor:pointer;margin-top:-10px;">
														<i class="icon-download-alt icon-white"></i>配置
													</a>
												</td>
											</tr>
											<tr height="60">
												<td style="background:#F9FAFE;"><label class="l-name">核查范围.</label></td>
												<td>
													<input type="text" value="全省" style="width:455px;height:30px;font-size:12px;"></input>													
													<a class="btn btn-warning"  style="cursor:pointer;margin-top:-10px;">
														<i class="icon-download-alt icon-white"></i>选择
													</a>
												</td>
											</tr>
											<tr height="60">
												<td style="background:#F9FAFE;"><label class="l-name">是否启用.</label></td>
												<td>
													<select class="w500" style="width:530px">
														<option>启用</option>
														<option>停用</option>
													</select>
												</td>
											</tr>
										</table>
									</center>
						        </div>
							</div>
						</td>
					</tr>
				</table>
			</div>
  		</div>
		<div class="panel panel-default" style="margin-top:1px;height:480px;" class="models" id="mainPanel">
			<div class="panel-heading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>规则管理 >>规则管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:showOrHide(1);">
							<i class="icon-th icon-white"></i>任务视图
						</a>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:showOrHide(2);">
							<i class="icon-th icon-white"></i>规则视图
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body h340" style="border:0px;" id="ruleShowDiv">
				<%--任务视图--%>
				<div style="width:99.8%;height:438px;overflow:auto;">
					<%--展示单元[01]开始--%>
					<div style="width:96%;height:90px;margin-left:10px;margin-top:20px;border-bottom:dotted 2px lightblue;">
						<div style="float:left;width:60px;height:60px;padding-top:25px;padding-left:30px;">
							<img src="${pageContext.request.contextPath}/img/icon/abiword.ico" style="height:60px;width:60px;"></img>
						</div>
						<div style="float:left;width:200px;height:60px;padding-top:25px;padding-left:20px;">
							<div style="width:390px;height:20px;padding-top:5px;">
								<font>AD设备小区检查</font>
							</div>
							<div style="width:390px;height:20px;padding-top:5px;">
								<font>规则数：</font><font color='blue'>3</font>
							</div>
						</div>
						<div style="float:right;width:200px;height:60px;margin-top:15px;margin-right:50px;">
							<div style="float:right;margin-top:39px;width:300px;height:20px;">
								<div style="float:right;">
									<img src="${pageContext.request.contextPath}/img/icon/gnumeric.ico" style="height:20px;width:20px;"></img>
									<a style="margin-left:3px" href="#itemsModal" role="button" data-toggle="modal" style="cursor:pointer">规则列表</a>
									<img src="${pageContext.request.contextPath}/img/icon/dashcode.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a style="margin-left:3px" href="#chartModal" role="button" data-toggle="modal" style="cursor:pointer">分析结果</a>
									<img src="${pageContext.request.contextPath}/img/icon/codeblocks.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a onclick="javascript:turnToTeamShow(1);" style="margin-left:3px;cursor:pointer">分组总览</a>
								</div>
							</div>
						</div>
					</div>
					<%--展示单元[01]结束--%>
					<div style="width:96%;height:90px;margin-left:10px;border-bottom:dotted 2px lightblue;">
						<div style="float:left;width:60px;height:60px;margin-top:25px;margin-left:30px;">
							<img src="${pageContext.request.contextPath}/img/icon/abiword.ico" style="height:60px;width:60px;"></img>
						</div>
						<div style="float:left;width:200px;height:60px;margin-top:25px;margin-left:20px;">
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>DSLAM网检查</font>
							</div>
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>规则数：</font><font color='blue'>12</font>
							</div>
						</div>
						<div style="float:right;width:300px;height:60px;margin-top:15px;margin-right:50px;">
							<div style="float:right;margin-top:39px;width:300px;height:20px;">
								<div style="float:right;">
									<img src="${pageContext.request.contextPath}/img/icon/gnumeric.ico" style="height:20px;width:20px;"></img>
									<a href="#" style="margin-left:3px">规则列表</a>	
									<img src="${pageContext.request.contextPath}/img/icon/dashcode.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a style="margin-left:3px" href="#chartModal" role="button" data-toggle="modal" style="cursor:pointer">分析结果</a>
									<img src="${pageContext.request.contextPath}/img/icon/codeblocks.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a onclick="javascript:turnToTeamShow(1);" style="margin-left:3px;cursor:pointer">分组总览</a>
								</div>
							</div>
						</div>
					</div>
					<div style="width:96%;height:90px;margin-left:10px;border-bottom:dotted 2px lightblue;">
						<div style="float:left;width:60px;height:60px;margin-top:25px;margin-left:30px;">
							<img src="${pageContext.request.contextPath}/img/icon/abiword.ico" style="height:60px;width:60px;"></img>
						</div>
						<div style="float:left;width:200px;height:60px;margin-top:25px;margin-left:20px;">
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>PON网检查</font>
							</div>
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>规则数：</font><font color='blue'>8</font>
							</div>
						</div>
						<div style="float:right;width:300px;height:60px;margin-top:15px;margin-right:50px;">
							<div style="float:right;margin-top:39px;width:300px;height:20px;">
								<div style="float:right;">
									<img src="${pageContext.request.contextPath}/img/icon/gnumeric.ico" style="height:20px;width:20px;"></img>
									<a href="#" style="margin-left:3px">规则列表</a>	
									<img src="${pageContext.request.contextPath}/img/icon/dashcode.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a style="margin-left:3px" href="#chartModal" role="button" data-toggle="modal" style="cursor:pointer">分析结果</a>
									<img src="${pageContext.request.contextPath}/img/icon/codeblocks.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a onclick="javascript:turnToTeamShow(1);" style="margin-left:3px;cursor:pointer">分组总览</a>
								</div>
							</div>
						</div>
					</div>
					<div style="width:96%;height:90px;margin-left:10px;border-bottom:dotted 2px lightblue;">
						<div style="float:left;width:60px;height:60px;margin-top:25px;margin-left:30px;">
							<img src="${pageContext.request.contextPath}/img/icon/abiword.ico" style="height:60px;width:60px;"></img>
						</div>
						<div style="float:left;width:200px;height:60px;margin-top:25px;margin-left:20px;">
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>标准地址检查</font>
							</div>
							<div style="width:390px;height:20px;margin-top:5px;">
								<font>规则数：</font><font color='blue'>3</font>
							</div>
						</div>
						<div style="float:right;width:300px;height:60px;margin-top:15px;margin-right:50px;">
							<div style="float:right;margin-top:39px;width:300px;height:20px;">
								<div style="float:right;">
									<img src="${pageContext.request.contextPath}/img/icon/gnumeric.ico" style="height:20px;width:20px;"></img>
									<a href="#" style="margin-left:3px">规则列表</a>	
									<img src="${pageContext.request.contextPath}/img/icon/dashcode.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a style="margin-left:3px" href="#chartModal" role="button" data-toggle="modal" style="cursor:pointer">分析结果</a>
									<img src="${pageContext.request.contextPath}/img/icon/codeblocks.ico" style="height:20px;width:20px;margin-left:15px;"></img>
									<a onclick="javascript:turnToTeamShow(1);" style="margin-left:3px;cursor:pointer">分组总览</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel-body h340" style="display:none;border:0px;" id="ruleConfigDiv">
				<div style="width:99.8%;height:440px;overflow:auto;">
					<div style="float:left;width:18%;height:430px;margin-top:4px;margin-left:3px;border:solid 1px #DFE8F1;" id="frmt" class="jstrees">
						<%--左侧树形结构--%>
					</div>
					<div style="float:left;width:81%;height:430px;margin-top:4px;margin-left:2px;border:solid 1px #DFE8F1;">
						<div class="panel-heading">
							<span class="panel-label"></span>规则预览 & 规则设计
							<form class="form-search pull-right">	
								<div style="float:right;">
									<input type="text" placeholder="请输入规则名称" style="width:160px;height:29px;font-size:12px;"></input>
									<a class="btn btn-primary" style="cursor:pointer">
										<i class="icon-search icon-white"></i>执行查询 
									</a>
									<a class="btn btn-info" style="cursor:pointer">
										<i class="icon-play-circle icon-white"></i>立即执行
									</a>
									<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:turnToTeamShow(2);">
										<i class="icon-signal icon-white"></i>历史分析
									</a>
									<a class="btn btn-success" style="cursor:pointer" onclick="javascript:showOrHide(3);" >
										<i class="icon-plus-sign icon-white"></i>编辑规则
									</a>
									<a class="btn btn-danger"  style="cursor:pointer">
										<i class="icon-trash icon-white"></i>删除规则
									</a>	
								</div>
							</form>
						</div>
						<div class="panel-body h390" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table class="table table-bordered table-hover" style="border:0px;">
											<thead>
												<tr height="35">
													<td width="3%" style="background:#F9FAFE;border-left:0px;" class="tc">&nbsp;</td>
													<td width="3%" style="background:#F9FAFE;" class="tc">&nbsp;</td>
													<td width="18%" style="background:#F9FAFE;text-align:center;">规则名称</td>
													<td width="36%" style="background:#F9FAFE;text-align:center;">规则描述</td>
													<td width="8%" style="background:#F9FAFE;text-align:center;">创建人</td>
													<td width="12%" style="background:#F9FAFE;text-align:center;">创建时间</td>
													<td width="12%" style="background:#F9FAFE;text-align:center;">最后一次执行</td>
													<td width="8%" style="background:#F9FAFE;text-align:center;">状态</td>
												</tr>
											</thead>
											<tbody>
												<tr height="27">
													<td style="text-align:center;border-left:0px;">1</td>
													<td class="tc"><input type="checkbox" value=""></td>
													<td>机房名称非空性验证</td>
													<td>核查机房的名称为空的异常数据</td>
													<td>管理员</td>
													<td>20170110 12:00:00</td>
													<td>20170112 01:00:00</td>
													<td>启用</td>
												</tr>
												<tr height="27">
													<td style="text-align:center;border-left:0px;">2</td>
													<td class="tc"><input type="checkbox" value=""></td>
													<td>机房名称唯一性验证</td>
													<td>在所有机房数据中，机房名称必须是唯一的，否则即为异常数据.</td>
													<td>管理员</td>
													<td>20170110 12:00:00</td>
													<td>20170112 01:00:00</td>
													<td>启用</td>
												</tr>
												<tr height="27">
													<td style="text-align:center;border-left:0px;">3</td>
													<td class="tc"><input type="checkbox" value=""></td>
													<td>机房类型是否符合枚举值</td>
													<td>机房类型必须在指定的几个类型中间选择，否则即为异常数据</td>
													<td>管理员</td>
													<td>20170110 12:00:00</td>
													<td>20170112 01:00:00</td>
													<td>启用</td>
												</tr>
												<tr height="27"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="27"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="27"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="27"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="25"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="25"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="25"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
												<tr height="25"><td style="border-left:0px;">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
											</tbody>
										</table>						
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="chartModal" class="modal hide fade" style="width:650px;height:355px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 比对历史分析</h3>
			</div>
			<div class="modal-body" style="max-height:260px;">
				<div style="width:630px;height:250px;" id="maina"></div>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
				</button>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="itemsModal" class="modal hide fade" style="width:650px;height:355px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 包含规则</h3>
			</div>
			<div class="modal-body" style="max-height:260px;">
				<table class="table table-bordered table-hover">
					<thead>
						<tr height="25">
							<th width="20%">规则名称</th>
							<th width="80%">规则描述</th>
						</tr>
					</thead>
					<tbody>
						<tr height="25">
							<td>人井要归属某个管道段</td>
							<td>核查没有归属管道段的人井</td>
						</tr>
						<tr height="25">
							<td>管道段无敷设的光缆信息</td>
							<td>管道段无敷设的光缆信息</td>
						</tr>
						<tr height="25">
							<td>电杆周围5M内有其他电杆，但不属于同一个杆路段</td>
							<td>冗余的电杆数据或电杆经纬度错误</td>
						</tr>
						<tr height="25">
							<td>标石要归属某个直埋段</td>
							<td>查找没有归属直埋段的标石</td>
						</tr>
						<tr height="25"><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr height="25"><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr height="25"><td>&nbsp;</td><td>&nbsp;</td></tr>
					</tbody>
				</table>				
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
				</button>
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
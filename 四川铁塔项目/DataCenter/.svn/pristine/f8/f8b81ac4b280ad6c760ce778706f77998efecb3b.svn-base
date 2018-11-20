<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
   		<meta name="viewport" content="width=device-width, initial-scale=1">
    	<base href="<%=basePath%>">
    	<title>在线风控</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		
		<script type="text/javascript">
			$(document).ready(function(){
				
				/*后台获取首页数据*/
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findRiskControlOnlineData.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:"cityName=-1&thisMonth=-1",
					timeout:10000,
					success:function(riskData){
						if(riskData["success"]){
							/*风险问题总数*/
							$("#risk_question_total").html(riskData["RISK_QUESTION_TOTAL"]+"个");
							/*问题已反馈总数*/
							$("#feedback_question_total").html(riskData["FEEDBACK_QUESTION_TOTAL"]+"个");
							/*总体反馈率*/
							$("#feedback_rate").html(Math.round(parseInt(riskData["FEEDBACK_QUESTION_TOTAL"])/parseInt(riskData["RISK_QUESTION_TOTAL"])*10000)/100.00+"%");
							/*资金问题总数*/
							$("#fund_question_total").html(riskData["FUND_QUESTION_TOTAL"]+"个");
							/*收入问题总数*/
							$("#income_question_total").html(riskData["INCOME_QUESTION_TOTAL"]+"个");
							/*基站电费总数*/
							$("#stand_electric_total").html(riskData["STAND_ELECTRIC_TOTAL"]+"个");
							/*场租费总数*/
							$("#colocation_rent_total").html(riskData["COLOCATION_RENT_TOTAL"]+"个");
							/*工程类总数*/
							$("#project_total").html(riskData["PROJECT_TOTAL"]+"个");
							/*代维问题总数*/
							$("#ashaburna_question_total").html(riskData["ASHABURNA_QUESTION_TOTAL"]+"个");
							/*下方问题列表及数量*/
							var lineHtml="";
							var fund_question=riskData["FUND_QUESTION_DETAIL"];
							var project_question=riskData["PROJECT_QUESTION_DETAIL"];
							for(var i=0;i<fund_question.length;i++){
								var data=fund_question[i];
								lineHtml+="<tr>";
								lineHtml+="	  <td>"+data["ID"]+"</td>";
								lineHtml+="	  <td>"+data["RISK_NAME"]+"</td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showRiskQuestion("+data["RISK_ID"]+")'>"+parseInt(data["QUNUM"])+"</a></td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showFeedBackQuestion("+data["RISK_ID"]+")'>"+parseInt(data["FEEDNUM"])+"</a></td>";
								lineHtml+="</tr>";	
							}
							$("#fund_question_detail").html(lineHtml);
							lineHtml="";
							for(var i=0;i<project_question.length;i++){
								var data=project_question[i];
								lineHtml+="<tr>";
								lineHtml+="	  <td>"+data["ID"]+"</td>";
								lineHtml+="	  <td>"+data["RISK_NAME"]+"</td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showRiskQuestion("+data["RISK_ID"]+")'>"+parseInt(data["QUNUM"])+"</a></td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showFeedBackQuestion("+data["RISK_ID"]+")'>"+parseInt(data["FEEDNUM"])+"</a></td>";
								lineHtml+="</tr>";	
							}
							$("#project_question_detail").html(lineHtml);
							if(riskData["IS_PROVINCE"]){
								/*开始获取地市下拉框数据*/
								$.ajax({
									url:"${pageContext.request.contextPath}/myBenchAction/dataCenterPage.ilf",
									async:true,
									type:"POST",
									dataType:"json",
									data:"cityName=-1&thisMonth=-1",
									timeout:10000,
									success:function(textStatus){
										if(textStatus["SUCCESS"]){
											/*地市列表*/
											var cityArray = textStatus["CITY_LIST"];
											var citySelect = "";
											citySelect+="<select style='width:180px;height:29px;border:solid 1px #A3D0E3;' id='citySelect'>";
											for(var i=0;i<cityArray.length;i++){
												var cityName = cityArray[i];
												citySelect+="<option value='"+cityName["CITY_NAME"]+"'>"+cityName["CITY_NAME"]+"</option>";
											}
											citySelect+="</select>";
											document.getElementById("citySelection").innerHTML = citySelect;
											/*
												=== 初始化地市信息   ===
											 */
											var cityName = "";
											if(textStatus["IS_PROVINCE"]){
												cityName = "全省";
											}else{
												cityName = textStatus["MY_CITY"];
											}
											if(cityName!=""){
												if(cityName.length>2){
													cityName = cityName.substring(0,2);
												}
												var citySelectObj = document.getElementById("citySelect");
												if(citySelectObj!=null && citySelectObj.options!=null && citySelectObj.options.length>0){
													for(var i=0;i<citySelectObj.options.length;i++){
							                             var optionValue = citySelectObj.options[i].value;
							                             if(optionValue.indexOf(cityName)!=-1){
															citySelectObj.options[i].selected = "selected";
						                                    break;
							                             }
													}
												}	
											}
										}
									},
									error:function(){}
								});
								/*参数配置*/
								var param_config="<a href='javascript:addTabOf(0)'><span class='glyphicon glyphicon-cog' aria-hidden='true' style='color:#696969;font-size:25px;'></span><span class='glyphicon glyphicon-cog' aria-hidden='true' style='color:#696969;font-size:15px;'></span></a>"
								$("#param_config").html(param_config);
							}else{
								/*只加载用户当前地市*/
								var city="<option value='"+riskData["BELONG_AREA"]+"'>"+riskData["BELONG_AREA"]+"</option>"
								$("#citySelect").html(city);
							}
						}
						
					},
					error:function(){}
				});
				
				
			});
			function showRiskQuestion(qu_id){
				var city=$("#citySelect").val();
				city=encodeURI(city);
				var date=$("#designedDate").val();
				url = encodeURI("jsp/firstIndex/riskControlOnline/questionDataList.jsp?quid="+qu_id+"&city="+city+"&date="+date);
				window.parent.parent.turnToJsp("问题数据列表",url);
			}
			function showFeedBackQuestion(qu_id){
				var city=$("#citySelect").val();
				city=encodeURI(city);
				var date=$("#designedDate").val();
				url = encodeURI("jsp/firstIndex/riskControlOnline/feedbackDataList.jsp?quid="+qu_id+"&city="+city+"&date="+date);
				window.parent.parent.turnToJsp("已反馈数据列表",url);
			}
			function addTabOf(tabCode){
				var city=$("#citySelect").val();
				city=encodeURI(city);
				var date=$("#designedDate").val();
				var url="";
				if(parseInt(tabCode)==0){
					/*参数配置*/
					window.parent.parent.turnToJsp("参数配置","jsp/firstIndex/riskControlOnline/parameterConfiguration.jsp");
				}else if(parseInt(tabCode)==1){
					/*风险点问题总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/riskQuestionAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("风险点问题总数",url);
				}else if(parseInt(tabCode)==2){
					/*问题已反馈总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/riskFeedbackQuestionAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("问题已反馈总数",url);
				}else if(parseInt(tabCode)==3){
					/*总体反馈率*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/riskFeedbackProbability.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("总体反馈率",url);
				}else if(parseInt(tabCode)==4){
					/*资金问题总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/fundQuestionAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("资金问题总数",url);
				}else if(parseInt(tabCode)==5){
					/*收入问题总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/incomeQuestionAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("收入问题总数",url);
				}else if(parseInt(tabCode)==6){
					/*基站电费总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/baseStationElectricChargeAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("基站电费总数",url);
				}else if(parseInt(tabCode)==7){
					/*场租费总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/colocationChargeAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("场租费总数",url);
				}else if(parseInt(tabCode)==8){
					/*工程类总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/engineeringTypeAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("工程类总数",url);
				}else if(parseInt(tabCode)==9){
					/*代维问题总数*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/ashaburnaQuestionAmount.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("代维问题总数",url);
				}else if(parseInt(tabCode)==10){
					/*风险点管控列表*/
					url = encodeURI("jsp/firstIndex/riskControlOnline/riskControlOnlineAll.jsp?city="+city+"&date="+date);
					window.parent.parent.turnToJsp("风险点管控列表",url);
				}
			}
			function searchData(){
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findRiskControlOnlineData.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:"city="+$("#citySelect").val()+"&mouth="+$("#designedDate").val(),
					timeout:10000,
					success:function(riskData){
						
						if(riskData["success"]){
							//window.wxc.xcConfirm("下面是"+$("#citySelect").val()+"~"+$("#designedDate").val()+"的数据", window.wxc.xcConfirm.typeEnum.confirm);
							//alert("下面是"+$("#citySelect").val()+"~"+$("#designedDate").val()+"的数据");
							/*风险问题总数*/
							$("#risk_question_total").html(riskData["RISK_QUESTION_TOTAL"]+"个");
							/*问题已反馈总数*/
							$("#feedback_question_total").html(riskData["FEEDBACK_QUESTION_TOTAL"]+"个");
							/*总体反馈率*/
							$("#feedback_rate").html(Math.round(parseInt(riskData["FEEDBACK_QUESTION_TOTAL"])/parseInt(riskData["RISK_QUESTION_TOTAL"])*10000)/100.00+"%");
							/*资金问题总数*/
							$("#fund_question_total").html(riskData["FUND_QUESTION_TOTAL"]+"个");
							/*收入问题总数*/
							$("#income_question_total").html(riskData["INCOME_QUESTION_TOTAL"]+"个");
							/*基站电费总数*/
							$("#stand_electric_total").html(riskData["STAND_ELECTRIC_TOTAL"]+"个");
							/*场租费总数*/
							$("#colocation_rent_total").html(riskData["COLOCATION_RENT_TOTAL"]+"个");
							/*工程类总数*/
							$("#project_total").html(riskData["PROJECT_TOTAL"]+"个");
							/*代维问题总数*/
							$("#ashaburna_question_total").html(riskData["ASHABURNA_QUESTION_TOTAL"]+"个");
							/*下方问题列表及数量*/
							var lineHtml="";
							var fund_question=riskData["FUND_QUESTION_DETAIL"];
							var project_question=riskData["PROJECT_QUESTION_DETAIL"];
							for(var i=0;i<fund_question.length;i++){
								var data=fund_question[i];
								lineHtml+="<tr>";
								lineHtml+="	  <td>"+data["ID"]+"</td>";
								lineHtml+="	  <td>"+data["RISK_NAME"]+"</td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showRiskQuestion("+data["RISK_ID"]+")'>"+parseInt(data["QUNUM"])+"</a></td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showFeedBackQuestion("+data["RISK_ID"]+")'>"+parseInt(data["FEEDNUM"])+"</a></td>";
								lineHtml+="</tr>";	
							}
							$("#fund_question_detail").html(lineHtml);
							lineHtml="";
							for(var i=0;i<project_question.length;i++){
								var data=project_question[i];
								lineHtml+="<tr>";
								lineHtml+="	  <td>"+data["ID"]+"</td>";
								lineHtml+="	  <td>"+data["RISK_NAME"]+"</td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showRiskQuestion("+data["RISK_ID"]+")'>"+parseInt(data["QUNUM"])+"</a></td>";
								lineHtml+="	  <td><a href='javascript:void(0)' onclick='showFeedBackQuestion("+data["RISK_ID"]+")'>"+parseInt(data["FEEDNUM"])+"</a></td>";
								lineHtml+="</tr>";	
							}
							$("#project_question_detail").html(lineHtml);
						}
						
					},
					error:function(){}
				});
			}
			
			
		</script>
		<style type="text/css">
			.table th,.table td{
				text-align:center
			}
			.table tbody tr td{
				vertical-align:middle
			}
		</style>
  	</head>
  	<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:-20px;">
		<div class="panel panel-primary"  id="mainPanel">
			<!-- 
			<div class="panel-heading" id="panelHeading">
				<h3 class="panel-title">在线风控</h3>
			</div>
			 -->
			 
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font color="white" size="5">在线风控</font>
				</div>
				<div id="param_config" style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-right:10px;text-align:right">
				</div>
			</div>
			
			<div class="panel-body">
				<div style="width:100%;height:50px">
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>地市：</span>
						<div style="display:inline"  id="citySelection">
								<select style="width:180px;border:solid 1px #A3D0E3;" id="citySelect">
									<option value="-">-</option>
								</select>
						</div>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>日期：</span>
						<input id="designedDate" type="text" placeholder="请选择日期" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate"></input>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<a class="btn btn-success" style="cursor:pointer;margin-bottom:10px;" onclick="javascript:searchData();">
								<span class="icon-search icon-white"></span>查询
						</a>
					</div>
				</div>

			
				<div style="width:100%;height:75px;border:solid 0px red;margin-top:5px;margin-left:10px;cursor:pointer;">
  					<div onclick="javascript:addTabOf(1)" style="float:left;width:30%;height:75px;background:#FF0000;box-shadow:0 0 10px #FF0000;border-radius:7px;margin-left:7px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:17px;">
 							<span class="glyphicon glyphicon-bell" aria-label="Left Align" aria-hidden="true" style="float:right;color:white;font-Size:35px"></span>
  						</div>
  						<div style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">风险点问题总数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="risk_question_total">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(2)" style="float:left;width:30%;height:75px;background:#00DB00;box-shadow:0 0 10px #00DB00;border-radius:7px;margin-left:6px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:17px;">
 							<span class="glyphicon glyphicon-edit" aria-label="Left Align" aria-hidden="true" style="float:right;color:white;font-Size:35px"></span>
  						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">问题已反馈总数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="feedback_question_total">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(3)" style="float:left;width:30%;height:75px;background:#00CACA;box-shadow:0 0 10px #00CACA;margin-left:7px;border-radius:7px;">
  						<div style="float:left;width:40%;height:50px;margin-top:16px;">
	  						<img src="${pageContext.request.contextPath}/icons/payoff.png" style="float:right;width:38px;height:38px;"></img>
 						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">总体反馈率</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="feedback_rate">-</font></div>
  						</div>
  					</div>
  				</div>
			
				<div style="width:99%;height:75px;border:solid 0px red;margin-top:15px;cursor:pointer;">
  					<div onclick="javascript:addTabOf(4)" style="float:left;width:15%;height:75px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">资金问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="fund_question_total">-</font></div>
  						</div>
  					</div>
  					
  					<div onclick="javascript:addTabOf(5)" style="float:left;width:15%;height:75px;margin-left:10px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">收入问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="income_question_total">-</font></div>
  						</div>
  					</div>
  					
  					<div onclick="javascript:addTabOf(6)" style="float:left;width:15%;height:75px;margin-left:10px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">基站电费问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="stand_electric_total">-</font></div>
  						</div>
  					</div>
  					
  					<div onclick="javascript:addTabOf(7)" style="float:left;width:15%;height:75px;margin-left:10px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">场租费问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="colocation_rent_total">-</font></div>
  						</div>
  					</div>
  					
  					<div onclick="javascript:addTabOf(8)" style="float:left;width:15%;height:75px;margin-left:10px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">工程类问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="project_total">-</font></div>
  						</div>
  					</div>
  					
  					<div onclick="javascript:addTabOf(9)" style="float:left;width:15%;height:75px;margin-left:10px;background:#FFD306;box-shadow:0 0 10px #FFD306;border-radius:7px;cursor:pointer;">
  						<div align="center" style="width:100%;height:50px;margin-top:17px;">
  							<div><font color="white" size="2">代维问题</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="ashaburna_question_total">-</font></div>
  						</div>
  					</div>
  					
  				</div>
			
				
			 	<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table  class="table table-bordered display"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align: center;'>
							<!--<button class="btn btn-success" type="submit">资金问题</button>-->
							<font size="4" color="#228B22">资金问题</font>
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点描述</th>
								<th>问题数量</th>
								<th>已反馈</th>
							</tr>
						</thead>
						<tbody id="fund_question_detail">
							
						</tbody>
					</table>
				</div>
				
				<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table class="table table-bordered"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align:right;display:inline-block;width:55%'>
							<!--  <button class="btn btn-success" type="submit">工程类</button>-->
							<font size="4" color="#228B22">工程类</font>
						</div>
						<!-- 此处添加文字提示，单独的箭头图标指向不明显 ,div整体添加点击事件-->
						<div class="center-block" style='text-align: right;display:inline-block;width:45%;cursor:pointer;'>			
							<font size="3" color="#40E0D0"  onclick="javascript:addTabOf(10)">查看全部>></font>
							<!--<img src="${pageContext.request.contextPath}/jsp/firstIndex/reports/ShowAll.png" style='width:30px;height:20px;' onclick="javascript:addTabOf(10)"/>-->					
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点描述</th>
								<th>问题数量</th>
								<th>已反馈</th>
							</tr>
						</thead>
						<tbody id="project_question_detail">
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>	
	</body>
</html>
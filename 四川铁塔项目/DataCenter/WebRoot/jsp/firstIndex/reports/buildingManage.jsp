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
    	<title>电费管理</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/buildAssistCountAction/findCountInIndex.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(obj){
						if(obj["SUCCESS"]){
							/*[渲染]顶部统计信息*/
							var titleCount = obj["TITLE_DATA_COUNT"];
							if(titleCount!=null){
								$("#PMS_IN_BUILDING").html(titleCount["PMS在建项目数"]==null?"&nbsp;":titleCount["PMS在建项目数"]);
								$("#PROJECT_DELAY_COUNT").html(titleCount["工程超期数"]==null?"&nbsp;":titleCount["工程超期数"]);
								$("#DELAY_COUNT_RATIO").html(titleCount["工程超期率"]==null?"&nbsp;":parseFloat(parseFloat(titleCount["工程超期率"])*100.0).toFixed(1)+"%");
								if(obj["IS_PROVINCE"]){
									$("#COUNT_CITY").html("全省");	
								}else{
									$("#COUNT_CITY").html(obj["COUNT_CITY"]);
								}
							}
							/*需求承接预审*/
							var partOneObjs = obj["ACCEPT_NEEDS_AUDIT_FINISH_RATIO"];
							if(partOneObjs==null || partOneObjs.length<=5){
								$("#ONE_MORE_BUTTON").hide();
							}
							if(partOneObjs!=null && partOneObjs.length>0){
								var innerHtml = "";
								for(var i=0;i<partOneObjs.length;i++){
									var partOneObj = partOneObjs[i];
									if(i<=4){
										innerHtml+= "<tr height='30'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partOneObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partOneObj["需求预审单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partOneObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partOneObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";	
									}else{
										innerHtml+= "<tr height='30' class='PART_ONE_MORE' style='display:none;'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partOneObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partOneObj["需求预审单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partOneObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partOneObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";
									}
								}
								document.getElementById("PART_ONE_TBODY").innerHTML = innerHtml;
							}
							/*服务订单管理统计*/
							var partTwoObjs = obj["SERVICE_ORDER_COUNT"];
							if(partTwoObjs==null || partTwoObjs.length<=5){
								$("#TWO_MORE_BUTTON").hide();
							}
							if(partTwoObjs!=null && partTwoObjs.length>0){
								var innerHtml = "";
								for(var i=0;i<partTwoObjs.length;i++){
									var partTwoObj = partTwoObjs[i];
									if(i<=4){
										innerHtml+= "<tr height='30'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partTwoObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partTwoObj["服务订单发起数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partTwoObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partTwoObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";	
									}else{
										innerHtml+= "<tr height='30' class='PART_TWO_MORE' style='display:none;'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partTwoObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partTwoObj["服务订单发起数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partTwoObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partTwoObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";
									}
								}
								document.getElementById("PART_TWO_TBODY").innerHTML = innerHtml;
							}
							/*审签管理*/
							var partThreeObjs = obj["CHECK_AUDIT_COUNT"];
							if(partThreeObjs==null || partThreeObjs.length<=5){
								$("#THREE_MORE_BUTTON").hide();
							}
							if(partThreeObjs!=null && partThreeObjs.length>0){
								var innerHtml = "";
								for(var i=0;i<partThreeObjs.length;i++){
									var partThreeObj = partThreeObjs[i];
									if(i<=4){
										innerHtml+= "<tr height='30'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partThreeObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partThreeObj["审签发起工单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partThreeObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partThreeObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";	
									}else{
										innerHtml+= "<tr height='30' class='PART_THREE_MORE' style='display:none;'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partThreeObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partThreeObj["审签发起工单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partThreeObj["审批完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partThreeObj["审批完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";
									}
								}
								document.getElementById("PART_THREE_TBODY").innerHTML = innerHtml;
							}
							/*外验交付管理*/
							var partFourObjs = obj["OUTCHECK_JOBS"];
							if(partFourObjs==null || partFourObjs.length<=5){
								$("#FOUR_MORE_BUTTON").hide();
							}
							if(partFourObjs!=null && partFourObjs.length>0){
								var innerHtml = "";
								for(var i=0;i<partFourObjs.length;i++){
									var partFourObj = partFourObjs[i];
									if(i<=4){
										innerHtml+= "<tr height='30'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partFourObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partFourObj["外验发起工单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partFourObj["工单完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partFourObj["工单完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";	
									}else{
										innerHtml+= "<tr height='30' class='PART_FOUR_MORE' style='display:none;'>";
										innerHtml+= "	<td style='text-align:left;border-right:solid 1px black;border-bottom:solid 1px black;'><div style='margin-left:7px;'>"+partFourObj["地市区县"]+"</div></td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partFourObj["外验发起工单数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+partFourObj["工单完成数"]+"</td>";
										innerHtml+= "	<td style='text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;'>"+parseFloat(parseFloat(partFourObj["工单完成率"])*100.0).toFixed(1)+"%"+"</td>";
										innerHtml+= "</tr>";
									}
								}
								document.getElementById("PART_FOUR_TBODY").innerHTML = innerHtml;
							}
						}
					}
				});
			});
			function showMore(className){
				$("."+className).show(500);
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;height:480px;" id="bodyHeight">
  		<input type="hidden" id="HIDDEN_OF_USER_ACCOUNT" value=""></input>
		<div class="panel panel-default" style="margin-top:-20px;height:480px;border:solid 1px #DEEFF8;overflow:auto;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">&nbsp;运营支撑平台&nbsp;&nbsp;>>&nbsp;&nbsp;建设辅助门户</div>
			<div class="panel-body" style="border:0px;overflow:auto;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<div style="width:99.9%;height:70px;border:solid 0px red;">
								<table cellpadding="0" cellspacing="0" style="width:100%;font-size:12px;border-left:solid 1px black;border-top:solid 1px black;">
									<tr height="35">
										<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">地市</td>
										<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">PMS在建项目数</td>
										<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">工程超期数</td>
										<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">工程超期率</td>
									</tr>
									<tr height="35">
										<td style="text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;" id="COUNT_CITY">&nbsp;</td>
										<td style="text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;" id="PMS_IN_BUILDING">&nbsp;</td>
										<td style="text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;" id="PROJECT_DELAY_COUNT">&nbsp;</td>
										<td style="text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;" id="DELAY_COUNT_RATIO">&nbsp;</td>
									</tr>
								</table>
							</div>
							<div style="width:99.9%;height:250px;border:solid 0px red;margin-top:10px;">
								<div style="float:left;width:49.5%;height:245px;">
									<div class="panel-heading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
										&nbsp;&nbsp;>>&nbsp;&nbsp;需求承接方案预审
										<form class="form-search pull-right">	
											<div style="float:right;">
												<img id="ONE_MORE_BUTTON" onclick="javascript:showMore('PART_ONE_MORE');" src="${pageContext.request.contextPath}/jsp/firstIndex/reports/ShowAll.png" style="width:15px;height:15px;margin-top:13px;cursor:pointer;"/>
											</div>
										</form>
									</div>
									<div class="panel-body" style="border:solid 1px #DFE8F1;height:185px;overflow:auto;">
										<div class="panlecontent container4 clearfix">
											<div class="div_scroll">
												<div style="height:auto;width:auto">
													<div style="width:99.9%;border:solid 0px red;">
														<table cellpadding="0" cellspacing="0" style="width:99%;font-size:12px;border-left:solid 1px black;border-top:solid 1px black;margin-bottom:10px;">
															<thead>
																<tr height="30">
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">地市/区县</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">需求预审单数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审批完成数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">需求完成率</td>
																</tr>
															</thead>
															<tbody id="PART_ONE_TBODY">
																<tr height="30"><td colspan="4" style="text-align:center;border-bottom:solid 1px black;border-right:solid 1px black;">暂无数据</td></tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div style="float:right;width:49.5%;height:245px;">
									<div class="panel-heading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
										&nbsp;&nbsp;>>&nbsp;&nbsp;线下采购订单管理
										<form class="form-search pull-right">	
											<div style="float:right;">
												<img id="TWO_MORE_BUTTON" onclick="javascript:showMore('PART_TWO_MORE');" src="${pageContext.request.contextPath}/jsp/firstIndex/reports/ShowAll.png" style="width:15px;height:15px;margin-top:13px;cursor:pointer;"/>
											</div>
										</form>
									</div>
									<div class="panel-body" style="border:solid 1px #DFE8F1;height:185px;overflow:auto;">
										<div class="panlecontent container4 clearfix">
											<div class="div_scroll">
												<div style="height:auto;width:auto">
													<div style="width:99.9%;border:solid 0px red;">
														<table cellpadding="0" cellspacing="0" style="width:99%;font-size:12px;border-left:solid 1px black;border-top:solid 1px black;">
															<thead>
																<tr height="30">
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">地市/区县</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">服务订单发起数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审批完成数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审批完成率</td>
																</tr>
															</thead>
															<tbody id="PART_TWO_TBODY">
																<tr height="30"><td colspan="4" style="text-align:center;border-bottom:solid 1px black;border-right:solid 1px black;">暂无数据</td></tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div style="width:99.9%;height:250px;border:solid 0px red;margin-top:10px;">
								<div style="float:left;width:49.5%;height:245px;">
									<div class="panel-heading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
										&nbsp;&nbsp;>>&nbsp;&nbsp;签证审批管理
										<form class="form-search pull-right">	
											<div style="float:right;">
												<img id="THREE_MORE_BUTTON" onclick="javascript:showMore('PART_THREE_MORE');" src="${pageContext.request.contextPath}/jsp/firstIndex/reports/ShowAll.png" style="width:15px;height:15px;margin-top:13px;cursor:pointer;"/>
											</div>
										</form>
									</div>
									<div class="panel-body" style="border:solid 1px #DFE8F1;height:185px;overflow:auto;">
										<div class="panlecontent container4 clearfix">
											<div class="div_scroll">
												<div style="height:auto;width:auto">
													<div style="width:99.9%;border:solid 0px red;">
														<table cellpadding="0" cellspacing="0" style="width:99%;font-size:12px;border-left:solid 1px black;border-top:solid 1px black;">
															<thead>
																<tr height="30">
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">地市/区县</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审签发起工单数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审批完成数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">审签完成率</td>
																</tr>
															</thead>
															<tbody id="PART_THREE_TBODY">
																<tr height="30"><td colspan="4" style="text-align:center;border-bottom:solid 1px black;border-right:solid 1px black;">暂无数据</td></tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div style="float:right;width:49.5%;height:245px;">
									<div class="panel-heading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
										&nbsp;&nbsp;>>&nbsp;&nbsp;外验交付管理
										<form class="form-search pull-right">	
											<div style="float:right;">
												<img id="FOUR_MORE_BUTTON" onclick="javascript:showMore('PART_FOUR_MORE');" src="${pageContext.request.contextPath}/jsp/firstIndex/reports/ShowAll.png" style="width:15px;height:15px;margin-top:13px;cursor:pointer;"/>
											</div>
										</form>
									</div>
									<div class="panel-body" style="border:solid 1px #DFE8F1;height:185px;overflow:auto;">
										<div class="panlecontent container4 clearfix">
											<div class="div_scroll">
												<div style="height:auto;width:auto">
													<div style="width:99.9%;border:solid 0px red;">
														<table cellpadding="0" cellspacing="0" style="width:99%;font-size:12px;border-left:solid 1px black;border-top:solid 1px black;">
															<thead>
																<tr height="30">
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">地市/区县</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">外验发起数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">工单完成数</td>
																	<td style="text-align:center;background:#87F1F1;width:25%;border-right:solid 1px black;border-bottom:solid 1px black;">工单完成率</td>
																</tr>
															</thead>
															<tbody id="PART_FOUR_TBODY">
																<tr height="30"><td colspan="4" style="text-align:center;border-right:solid 1px black;border-bottom:solid 1px black;">暂无数据</td></tr>
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
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
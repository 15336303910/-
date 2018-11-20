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
    	<title>工作任务分派</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findLoginInfo.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					success:function(textStatus){
						if(textStatus["success"]){
							var loginUser = textStatus["LOGIN_USER"];
							if(loginUser!=null){
								if(loginUser["IS_PROVINCE"]){
									/*Title*/
									$("#jobTitleBanner").html(">> 省公司工作任务安排/分派");
									/*Button：显示省工单派发按钮*/
									$("#provinceJobPublish").show();
									$("#provinceJobPublish").css({
										"margin-top":"5px"
									});
									/*Button：隐藏市工单派发按钮*/
									$("#cityJobPublish").css({
										"margin-top":"0px"
									});
									$("#cityJobPublish").hide();
									/*Page*/
									$("#provinceJobsContainer").show();
									$("#cityJobsContainer").show();
								}else{
									/*Title*/
									$("#jobTitleBanner").html(">> 地市公司工作任务安排/分派");
									/*Button*/
									$("#cityJobPublish").show();
									$("#cityJobPublish").css({
										"margin-top":"5px"
									});
									$("#provinceJobPublish").hide();
									/*Page*/
									$("#cityJobsContainer").show();
									$("#provinceJobsContainer").show();
								}
							}
						}
					},
					error:function(){}
				});
				flushMyJobs();
			});
			function flushMyJobs(){
				$.ajax({
					url:"${pageContext.request.contextPath}/jobDispatch/findDatas.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					success:function(textStatus){
						window.parent.hideCircleLoading();
						if(textStatus["success"]){
							var items = textStatus["PROVINCE_ITEMS"];
							if(items!=null && items.length>0){
								var date = new Date();
								if(items!=null && items.length>0){
									var innerHtmlA = "";
									for(var i=0;i<items.length;i++){
										var itemObj = items[i];
										var longTime = itemObj["SEND_TIME"]["time"];
										date.setTime(longTime);
										innerHtmlA+="<tr>";
										innerHtmlA+="	<td>"+itemObj["FLOW_TITLE"]+"</td>";
										innerHtmlA+="	<td>"+itemObj["SEND_MAN"]+"</td>";
										innerHtmlA+="	<td>"+date.toLocaleString()+"</td>";
										if(itemObj["END_TIME"]==null){
											innerHtmlA+="<td>未完成</td>";	
										}else{
											innerHtmlA+="<td>已完成</td>";
										}
										innerHtmlA+="</tr>";
									}
									document.getElementById("PROVINCE_TABLE").innerHTML = innerHtmlA;
								}	
							}
							items = textStatus["CITY_ITEMS"];
							if(items!=null && items.length>0){
								var date = new Date();
								if(items!=null && items.length>0){
									var innerHtmlA = "";
									for(var i=0;i<items.length;i++){
										var itemObj = items[i];
										var longTime = itemObj["SEND_TIME"]["time"];
										date.setTime(longTime);
										innerHtmlA+="<tr>";
										innerHtmlA+="	<td>"+itemObj["FLOW_TITLE"]+"</td>";
										innerHtmlA+="	<td>"+itemObj["SEND_MAN"]+"</td>";
										innerHtmlA+="	<td>"+date.toLocaleString()+"</td>";
										if(itemObj["END_TIME"]==null){
											innerHtmlA+="<td>未完成</td>";	
										}else{
											innerHtmlA+="<td>已完成</td>";
										}
										innerHtmlA+="<tr>";
									}
									document.getElementById("CITY_TABLE").innerHTML = innerHtmlA;
								}	
							}
							setTimeout("javascript:flushMyJobs();",5000);
						}
					},
					error:function(){}
				});
			}
		</script>
  	</head>
  	<body style="width:99%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading"><font id="jobTitleBanner">>> 省公司工作任务安排/分派</font>
				<form class="form-search pull-right">	
					<div style="float:right;">
						<a id="provinceJobPublish" class="btn btn-success" style="cursor:pointer;" onclick="javascript:window.parent.linkToGdSystem(3);">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>发起省分工作任务
						</a>
						<a id="cityJobPublish" class="btn btn-warning" style="cursor:pointer;display:none;" onclick="javascript:window.parent.linkToGdSystem(4);">
							<i class="icon-plus-sign icon-white"></i>发起市公司工作任务
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<div style="width:100%;height:410px;border:solid 0px red;">
								<div class="panel-body" style="border:0px;" id="provinceJobsContainer">
									<div class="panlecontent container4 clearfix">
										<div class="div_scroll">
											<div style="height:auto;width:auto">
												<table class="table table-bordered table-hover">
													<thead>
														<tr>
															<td style="background:#6BC2EB;width:45%;">省分工作安排</td>
															<td style="background:#6BC2EB;width:15%;">发布人</td>
															<td style="background:#6BC2EB;width:20%;">发布日期</td>
															<td style="background:#6BC2EB;width:20%;">完成情况</td>
														</tr>
													</thead>
													<tbody id="PROVINCE_TABLE">
														<tr>
															<td colspan="4" style="text-align:center;">暂无省任务安排</td>
														</tr>
													</tbody>
												</table>	
											</div>
										</div>
									</div>
								</div>
								<div class="panel-body" style="border:0px;display:none;" id="cityJobsContainer">
									<div class="panlecontent container4 clearfix">
										<div class="div_scroll">
											<div style="height:auto;width:auto">
												<table class="table table-bordered table-hover">
													<thead>
														<tr>
															<td style="background:#6BC2EB;width:45%;">地市工作安排</td>
															<td style="background:#6BC2EB;width:15%;">发布人</td>
															<td style="background:#6BC2EB;width:20%;">发布日期</td>
															<td style="background:#6BC2EB;width:20%;">完成情况</td>
														</tr>
													</thead>
													<tbody id="CITY_TABLE">
														<tr>
															<td colspan="2">暂无地市任务安排</td>
														</tr>
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
	</body>
</html>
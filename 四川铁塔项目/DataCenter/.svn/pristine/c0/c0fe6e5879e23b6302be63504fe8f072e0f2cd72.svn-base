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
    	<title>场租续签提醒</title>
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
					url:"${pageContext.request.contextPath}/dealExpireAlarm/findDatas.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					success:function(textStatus){
						if(textStatus["success"]){
							var datas = textStatus["DATAS"];
							if(datas.length>0){
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									if(dataObj["地区"]!=null && dataObj["地区"]!=""){
										var CityName = dataObj["地区"];
										if(CityName=="凉山州"){
											CityName = "凉山";
										}else if(CityName=="甘孜州"){
											CityName = "甘孜";
										}else if(CityName=="阿坝州"){
											CityName = "阿坝";
										}
										lineHtml+="<tr>";
										lineHtml+="	  <td><a onclick='javascript:linkToDetails(\""+CityName+"\");' style='cursor:pointer;'>"+CityName+"</a></td>";
										lineHtml+="	  <td>"+(dataObj["站址数"]==null?"&nbsp;":dataObj["站址数"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["六个月内的到期合同数"]==null?"&nbsp;":dataObj["六个月内的到期合同数"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["已过期合同数"]==null?"&nbsp;":dataObj["已过期合同数"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["六月内已到期及过期合同占比"]==null?"&nbsp;":dataObj["六月内已到期及过期合同占比"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["零场租站数"]==null?"&nbsp;":dataObj["零场租站数"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["无合同站数"]==null?"&nbsp;":dataObj["无合同站数"])+"</td>";
										lineHtml+="</tr>";	
									}
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;	
							}
						}
					},
					error:function(){}
				});
			});
			function linkToDetails(cityName){
				window.parent.addTabForDealExpire(cityName);
			}
			function linkToJobs(){
				window.parent.addTabForDoingJob();
			}
		</script>
  	</head>
  	<body style="width:99.9%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">>> 场租续签（点击查看六个月内到期合同明细及已过期合同明细）
				<form class="form-search pull-right">	
					<div style="float:right;">
						<a class="btn btn-warning" style="cursor:pointer;margin-top:5px;" onclick="javascript:linkToJobs();">
							<i class="icon-flag icon-white"></i>场租续签提醒在途工单
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #DFE8F1;height:405px;overflow:auto;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;">地区</td>
										<td style="background:#6BC2EB;">站址数</td>
										<td style="background:#6BC2EB;">六个月内的到期合同数</td>
										<td style="background:#6BC2EB;">已过期合同数</td>
										<td style="background:#6BC2EB;">六月内已到期及过期合同占比</td>
										<td style="background:#6BC2EB;">零场租站数</td>
										<td style="background:#6BC2EB;">无合同站数</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="text-align:center;">-</td>
										<td style="color:red;">取稽核系统有效站址数</td>
										<td style="color:red;">取物业系统合同状态为“有效”，合同截止日期在180天内的合同数量</td>
										<td style="color:red;">取物业系统合同状态为“有效”，合同截止日期早于当前日期的合同数量</td>
										<td style="color:red;">（已过期合同数+六个月内到期合同数）/站址数*100%</td>
										<td style="color:red;">取物业系统物业信息，“是否零场租”为是，“零场租原因”不为空的站址数量</td>
										<td style="color:red;">剔除零场租站址，剔除物业系统合同信息合同状态”有效“，合同类型为租赁和电租一体的站址，剩余稽核系统站址数</td>
									</tr>
								</tbody>
								<tbody id="COUNTS_TABLE">
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
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
    	<title>在途工单：场租续签提醒</title>
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
				flushJobData();
			});
			function flushJobData(){
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/dealExpireAlarm/findDoingJobs.ilf",
					async:true,
					type:"POST",
					data:"siteCode="+$("#siteCodeInput").val(),
					dataType:"json",
					success:function(textStatus){
						if(textStatus["success"]){
							var datas = textStatus["DATAS"];
							if(datas.length>0){
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									lineHtml+="<tr>";
									lineHtml+="	  <td><input type='checkbox' name='JOB_CHECKS' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);' value='"+dataObj["INT_ID"]+"'></input></td>";
									lineHtml+="	  <td>"+(dataObj["地市"]==null?"&nbsp;":dataObj["地市"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["区县"]==null?"&nbsp;":dataObj["区县"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["工单编号"]==null?"&nbsp;":dataObj["工单编号"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["工单名称"]==null?"&nbsp;":dataObj["工单名称"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["站址编码"]==null?"&nbsp;":dataObj["站址编码"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["站址名称"]==null?"&nbsp;":dataObj["站址名称"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同编码"]==null?"&nbsp;":dataObj["合同编码"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同名称"]==null?"&nbsp;":dataObj["合同名称"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同类型"]==null?"&nbsp;":dataObj["合同类型"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同签订日期"]==null?"&nbsp;":dataObj["合同签订日期"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同起始日期"]==null?"&nbsp;":dataObj["合同起始日期"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同终止日期"]==null?"&nbsp;":dataObj["合同终止日期"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同总金额"]==null?"&nbsp;":dataObj["合同总金额"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["合同状态"]==null?"&nbsp;":dataObj["合同状态"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["续签状态"]==null?"&nbsp;":dataObj["续签状态"])+"</td>";
									lineHtml+="</tr>";
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;
								window.parent.overLoading(true);
							}else{
								window.parent.overLoading(true);
							}
						}else{
							window.parent.overLoading(true);
						}
					},
					error:function(){
						window.parent.overLoading(true);
					}
				});
			}
			function viewHaveReply(){
				var checkedId = getCheckedValue("JOB_CHECKS");
				if(checkedId==null){
					window.wxc.xcConfirm("请选择一条在途工单.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/dealExpireAlarm/jobHaveReplyMessages.ilf",
						async:false,
						type:"POST",
						data:"jobCode="+checkedId,
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								var messages = textStatus["MESSAGES"];
								var lineHtml = "";
								if(messages.length>0){
									for(var j=0;j<messages.length;j++){
										var dataObj = messages[j];
										var date = new Date();
										date.setTime(dataObj["反馈时间"]["time"]);
										lineHtml+="<tr>";
										lineHtml+="	  <td style='text-align:right;background:#CCC'>"+dataObj["反馈人"]+"：</td>";
										lineHtml+="	  <td>"+dataObj["详情"]+"</td>";
										lineHtml+="	  <td>"+date.toLocaleString()+"</td>";
										lineHtml+="</tr>";
									}	
								}else{
									lineHtml+="<tr>";
									lineHtml+="	  <td colspan='3' style='text-align:center;'><font color='red'>暂无已反馈信息</font></td>";
									lineHtml+="</tr>";
								}
								document.getElementById("LOGS_TABLE").innerHTML = lineHtml;
								$("#logDetails").dialog({
									title:"已反馈信息",
									show:{
										effect:"slide",
										duration:200
									},
									hide:{
								        effect:"slide",
								        duration:200
									},
									width:600,
									height:350,
									autoOpen:true,
									closeOnEscape:true,
									draggable:false,
									resizable:false,
									modal:true,
									buttons:{
						  				"确定":function(){
						  					$(this).dialog("close");
										}
						  			}
								});
							}
						},
						error:function(){
							window.wxc.xcConfirm("信息反馈失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					});	
				}
			}
			function downloadData(){
				window.wxc.xcConfirm("是否确认导出工单数据？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/dealExpireAlarm/exportData.ilf");
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});	
			}
		</script>
  	</head>
  	<body style="width:99.9%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">>> 在途工单：场租续签提醒
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input id="siteCodeInput" type="text" placeholder="请输入站址编码" style="border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;"></input>
						<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:flushJobData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-primary" style="cursor:pointer;" onclick="javascript:downloadData();">
							<i class="icon-download-alt icon-white"></i>数据导出
						</a>
						<a class="btn btn-warning" style="cursor:pointer;" onclick="javascript:viewHaveReply();">
							<i class="icon-flag icon-white"></i>查看沟通记录
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;height:410px;overflow:auto;border:solid 0px red;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;">&nbsp;</td>
										<td style="background:#6BC2EB;">地市</td>
										<td style="background:#6BC2EB;">区县</td>
										<td style="background:#6BC2EB;">工单编号</td>
										<td style="background:#6BC2EB;">工单名称</td>
										<td style="background:#6BC2EB;">站址编码</td>
										<td style="background:#6BC2EB;">站址名称</td>
										<td style="background:#6BC2EB;">合同编码</td>
										<td style="background:#6BC2EB;">合同名称</td>
										<td style="background:#6BC2EB;">合同类型</td>
										<td style="background:#6BC2EB;">合同签订日期</td>
										<td style="background:#6BC2EB;">合同起始日期</td>
										<td style="background:#6BC2EB;">合同终止日期</td>
										<td style="background:#6BC2EB;">合同金额</td>
										<td style="background:#6BC2EB;">合同状态</td>
										<td style="background:#6BC2EB;">续签状态</td>
									</tr>
								</thead>
								<tbody id="COUNTS_TABLE">
									<tr>
										<td colspan="16" style="text-align:center;">暂无工单</td>
									</tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--窗口历史：开始-->
		<div id="logDetails" style="display:none;height:300px;">
			<div style="border:solid 0px #54A8E1;height:255px;overflow:auto;">
				<center>
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<td style="background:#6BC2EB;">反馈人</td>
								<td style="background:#6BC2EB;">详情</td>
								<td style="background:#6BC2EB;">反馈时间</td>
							</tr>
						</thead>
						<tbody id="LOGS_TABLE">
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
						</tbody>
					</table>		
				</center>
			</div>
		</div>
		<!--窗口历史：结束-->
	</body>
</html>
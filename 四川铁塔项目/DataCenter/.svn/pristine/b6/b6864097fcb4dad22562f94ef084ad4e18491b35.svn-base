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
    	<title>客户诉求管理</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				flushCountData();
			});
			function flushCountData(){
				window.parent.showCircleLoading();
				$.ajax({
					url:"${pageContext.request.contextPath}/clientQuestionCount/findDatas.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"START_DATE="+$("#START_DATE_INPUT").val()+"&LIMIT_DATE="+$("#LIMIT_DATE_INPUT").val()+"&CONSUMER_TYPE="+$("#CONSUMER_TYPE_SELECT").val()+"&REQUEST_TYPE="+$("#REQUEST_TYPE_SELECT").val(),
					success:function(textStatus){
						window.parent.hideCircleLoading();
						if(textStatus["success"]){
							var datas = textStatus["DATAS"];
							if(datas.length>0){
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									lineHtml+="<tr>";
									lineHtml+="	  <td>"+(dataObj["地市"]==null?"&nbsp;":dataObj["地市"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["总工单数"]==null?"&nbsp;":dataObj["总工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["已接单工单数"]==null?"&nbsp;":dataObj["已接单工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["未接单工单数"]==null?"&nbsp;":dataObj["未接单工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["正常处理工单数"]==null?"&nbsp;":dataObj["正常处理工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["超时处理工单数"]==null?"&nbsp;":dataObj["超时处理工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["已归档工单数"]==null?"&nbsp;":dataObj["已归档工单数"])+"</td>";
									lineHtml+="	  <td>"+(dataObj["工单完成率"]==null?"&nbsp;":parseFloat(parseFloat(dataObj["工单完成率"])*100.0).toFixed(1))+"%</td>";
									lineHtml+="</tr>";
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;	
							}
						}
					},
					error:function(){
						window.parent.hideCircleLoading();
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;height:488px;overflow:hidden;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select id="CONSUMER_TYPE_SELECT" style="width:150px;height:30px;border:solid 1px #DFE8F1;">
							<option value="-">- 请选择客户类型  -</option>
							<option value="移动">移动</option>
							<option value="联通">联通</option>
							<option value="电信">电信</option>
							<option value="其他">其他</option>
						</select>
						<select id="REQUEST_TYPE_SELECT" style="width:150px;height:30px;border:solid 1px #DFE8F1;">
							<option value="-">- 请选择诉求类别  -</option>
							<option value="维护服务">维护服务</option>
							<option value="服务对接">服务对接</option>
							<option value="建设交付">建设交付</option>
						</select>
						<input id="START_DATE_INPUT" type="text" placeholder="请选择统计开始日期" style="cursor:pointer;border:solid 1px #DFE8F1;width:150px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
						<input id="LIMIT_DATE_INPUT" type="text" placeholder="请选择统计结束日期" style="cursor:pointer;border:solid 1px #DFE8F1;width:150px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
						<a class="btn btn-inverse" style="cursor:pointer;" onclick="javascript:flushCountData();">
							<i class="icon-refresh icon-white"></i>刷新
						</a>
						<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:window.parent.linkToGdSystem(2);">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>派发客户诉求工单
						</a>
						<a class="btn btn-warning" style="cursor:pointer;" onclick="javascript:window.parent.addTabOf(6);">
							<i class="icon-download-alt icon-white"></i>查询明细
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #DFE8F1;height:443px;overflow:auto;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;text-align:center;">地市</td>
										<td style="background:#6BC2EB;text-align:center;">诉求工单数</td>
										<td style="background:#6BC2EB;text-align:center;">已接单工单数</td>
										<td style="background:#6BC2EB;text-align:center;">未接单工单数</td>
										<td style="background:#6BC2EB;text-align:center;">正常处理工单数</td>
										<td style="background:#6BC2EB;text-align:center;">超时处理工单数</td>
										<td style="background:#6BC2EB;text-align:center;">已归档工单数</td>
										<td style="background:#6BC2EB;text-align:center;">工单完成率</td>
									</tr>
								</thead>
								<tbody id="COUNTS_TABLE">
									<tr><td colspan="8" style="text-align:center;">暂无数据</td></tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
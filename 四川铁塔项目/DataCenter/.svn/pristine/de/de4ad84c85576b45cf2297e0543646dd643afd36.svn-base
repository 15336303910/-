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
    	<title>数据比对</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#panelBody").css({
			    	"height":$("#bodyHeight").height()-$("#panelHeading").height()-5
			    });
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-85;
				var pageNumbers = parseInt(tableHeight/27);
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":false,
					"bProcessing":true,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":false,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"98%",
					"aoColumns":[{
						"mData":"工单编号",
						"sWidth":"120px"
					},{
						"mData":"工单名称",
						"sWidth":"270px"
					},{
						"mData":"运营商",
						"sWidth":"100px"
					},{
						"mData":"问题类别",
						"sWidth":"110px"
					},{
						"mData":"问题细分类型",
						"sWidth":"140px"
					},{
						"mData":"问题描述",
						"sWidth":"300px"
					},{
						"mData":"客户要求完成时间",
						"sWidth":"150px"
					},{
						"mData":"问题级别",
						"sWidth":"100px"
					},{
						"mData":"问题来源",
						"sWidth":"100px"
					},{
						"mData":"派单时间",
						"sWidth":"140px"
					},{
						"mData":"派单市州",
						"sWidth":"100px"
					},{
						"mData":"派单部门",
						"sWidth":"120px"
					},{
						"mData":"抄送市州",
						"sWidth":"100px"
					},{
						"mData":"抄送部门",
						"sWidth":"120px"
					},{
						"mData":"接单时间",
						"sWidth":"120px"
					},{
						"mData":"问题原因",
						"sWidth":"200px"
					},{
						"mData":"解决举措",
						"sWidth":"200px"
					},{
						"mData":"解决进度",
						"sWidth":"120px"
					},{
						"mData":"是否已解决",
						"sWidth":"110px"
					},{
						"mData":"是否向客户沟通",
						"sWidth":"120px"
					},{
						"mData":"客户是否认可",
						"sWidth":"120px"
					},{
						"mData":"归档时间",
						"sWidth":"140px"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(aData["派单时间"]!=null && aData["派单时间"]!=""){
							var date = new Date();
							date.setTime(aData["派单时间"]["time"]);
							$("td:eq(9)",nRow).html(date.toLocaleString());
						}
						if(aData["归档时间"]!=null && aData["归档时间"]!=""){
							var date = new Date();
							date.setTime(aData["归档时间"]["time"]);
							$("td:eq(21)",nRow).html(date.toLocaleString());
						}
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
							dataType:"json",		
							success:function(data){
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/clientQuestionCount/findGdCountDetails.ilf"
				});
			});
			function searchData(){
				conditions = [{
					name:"START_DATE",
					value:$("#START_DATE_INPUT").val()
				},{
					name:"LIMIT_DATE",
					value:$("#LIMIT_DATE_INPUT").val()
				}];
				oTable.fnDraw();
			}
			function exportDetails(){
				window.wxc.xcConfirm("是否确认根据查询条件导出相关工单明细？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						window.parent.showLoading(45,46);
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/clientQuestionCount/exportDetailsToFile.ilf?START_DATE="+$("#START_DATE_INPUT").val()+"&LIMIT_DATE="+$("#LIMIT_DATE_INPUT").val());
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					    setTimeout("javascript:window.parent.hideLoading();",2000);
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.3%;border:solid 1px #FFF;margin-left:3px;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:1px;">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>&nbsp;&nbsp;客户诉求管&nbsp;&nbsp;>>&nbsp;&nbsp;诉求工单明细
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input id="START_DATE_INPUT" type="text" placeholder="请选择发单明细起始日期" style="cursor:pointer;border:solid 1px #DFE8F1;width:170px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
						<input id="LIMIT_DATE_INPUT" type="text" placeholder="请选择发单明细最晚日期" style="cursor:pointer;border:solid 1px #DFE8F1;width:170px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
						<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-refresh icon-white"></i>查询
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:exportDetails();">
							<i class="icon-download-alt icon-white"></i>导出明细
						</a>
					</div>
				</form>
			</div>
			<div id="panelBody" class="panel-body h340" style="border:solid 1px #DFE8F1;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;margin-top:3px;margin-left:3px;margin-right:3px;">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<td style="text-align:center;background:#FCFDFE;">工单编号</td>
										<td style="text-align:center;background:#FCFDFE;">工单名称</td>
										<td style="text-align:center;background:#FCFDFE;">运营商</td>
										<td style="text-align:center;background:#FCFDFE;">问题类别</td>
										<td style="text-align:center;background:#FCFDFE;">问题细分类型</td>
										<td style="text-align:center;background:#FCFDFE;">问题描述</td>
										<td style="text-align:center;background:#FCFDFE;">客户要求完成时间</td>
										<td style="text-align:center;background:#FCFDFE;">问题级别</td>
										<td style="text-align:center;background:#FCFDFE;">问题来源</td>
										<td style="text-align:center;background:#FCFDFE;">派单时间</td>
										<td style="text-align:center;background:#FCFDFE;">派单市州</td>
										<td style="text-align:center;background:#FCFDFE;">派单部门</td>
										<td style="text-align:center;background:#FCFDFE;">抄送市州</td>
										<td style="text-align:center;background:#FCFDFE;">抄送部门</td>
										<td style="text-align:center;background:#FCFDFE;">接单时间</td>
										<td style="text-align:center;background:#FCFDFE;">问题原因</td>
										<td style="text-align:center;background:#FCFDFE;">解决举措</td>
										<td style="text-align:center;background:#FCFDFE;">解决进度</td>
										<td style="text-align:center;background:#FCFDFE;">是否已解决</td>
										<td style="text-align:center;background:#FCFDFE;">是否和客户沟通</td>
										<td style="text-align:center;background:#FCFDFE;">客户是否认可</td>
										<td style="text-align:center;background:#FCFDFE;">归档时间</td>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
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
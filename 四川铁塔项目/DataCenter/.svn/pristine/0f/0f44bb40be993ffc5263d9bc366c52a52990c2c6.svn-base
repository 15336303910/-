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
    	<title>电费支付台账</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/27);
				$("#siteNames").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				conditions = [{
					name:"MODEL_NAME",
					value:$("#TABLE_TYPE_SELECT").val()
				}];
				/*初始化菜单 列表*/
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":true,
					"bProcessing":true,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":false,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"SERIAL_NUM",
						"sWidth":"70px"
					},{
						"mData":"地市",
						"sWidth":"100px"
					},{
						"mData":"区县",
						"sWidth":"100px"
					},{
						"mData":"站址编码",
						"sWidth":"200px"
					},{
						"mData":"站址名称",
						"sWidth":"260px"
					},{
						"mData":"电费所属期间",
						"sWidth":"140px"
					},{
						"mData":"总电量",
						"sWidth":"140px"
					},{
						"mData":"缴费金额",
						"sWidth":"140px"
					},{
						"mData":"购电开始日期",
						"sWidth":"140px"
					},{
						"mData":"购电截止日期",
						"sWidth":"140px"
					},{
						"mData":"用电起始度数",
						"sWidth":"140px"
					},{
						"mData":"用电终止度数",
						"sWidth":"140px"
					},{
						"mData":"缴费户号",
						"sWidth":"160px"
					},{
						"mData":"电表编码",
						"sWidth":"160px"
					},{
						"mData":"电表类型",
						"sWidth":"140px"
					},{
						"mData":"电损",
						"sWidth":"140px"
					},{
						"mData":"其他费用",
						"sWidth":"140px"
					},{
						"mData":"单价",
						"sWidth":"140px"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["SERIAL_NUM"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["购电开始日期"]!=null){
							var date = new Date();
							date.setTime(aData["购电开始日期"]["time"]);
							$("td:eq(8)",nRow).html(date.toLocaleDateString());
						}
						if(aData["购电截止日期"]!=null){
							var date = new Date();
							date.setTime(aData["购电截止日期"]["time"]);
							$("td:eq(9)",nRow).html(date.toLocaleDateString());
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
					"sAjaxSource":"${pageContext.request.contextPath}/elecPayHistory/findItems.ilf"
				});
			});
			function searchData(){
				var siteName = $("#siteNames").val();
				if(siteName!=""){
					conditions = [{
						name:"SITE_NAME",
						value:siteName
					}];	
				}else{
					conditions = [];
				}
				conditions.push({
					name:"MODEL_NAME",
					value:$("#TABLE_TYPE_SELECT").val()
				});
				oTable.fnDraw();
			}
			function exportData(){
				window.wxc.xcConfirm("是否根据查询条件导出数据？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/elecPayHistory/exportData.ilf?modelName="+$("#TABLE_TYPE_SELECT").val()+"&keyWord="+encodeURIComponent(encodeURIComponent($("#siteNames").val(),"UTF-8"),"UTF-8"));
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});
			}
			function createData(){
				window.parent.turnToJsp("新增电费支付台账","jsp/dbs/addHistory.jsp");
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;">&nbsp;>> 电费支付台账
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select id="TABLE_TYPE_SELECT" style="width:200px;height:29px;border:solid 1px #A3D0E3;">
							<option value="WY_ELECTRI_FEE_DS">直供电</option>
							<option value="WY_ELECTRI_FEE_TURN">转供电</option>
						</select>
						<input type="text" placeholder="请输入站址名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="siteNames"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:exportData();">
							<i class="icon-download-alt icon-white"></i>导出数据
						</a>
						<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:createData();">
							<i class="icon-upload icon-white"></i>新增数据
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;border:solid 1px #A3D0E3;border-top:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th style="text-align:center;">&nbsp;</th>
										<th style="text-align:center;">地市</th>
										<th style="text-align:center;">区县</th>
										<th style="text-align:center;">站址编码</th>
										<th style="text-align:center;">站址名称</th>
										<th style="text-align:center;">电费所属期间</th>
										<th style="text-align:center;">总电量</th>
										<th style="text-align:center;">缴费金额（元）</th>
										<th style="text-align:center;">购电开始日期</th>
										<th style="text-align:center;">购电截止日期</th>
										<th style="text-align:center;">用电起始度数</th>
										<th style="text-align:center;">用电终止度数</th>
										<th style="text-align:center;">缴费户号</th>
										<th style="text-align:center;">电表编码</th>
										<th style="text-align:center;">电表类型</th>
										<th style="text-align:center;">电损</th>
										<th style="text-align:center;">其他费用（元）</th>
										<th style="text-align:center;">单价（元）</th>
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
</html>
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
    	<title>工程进度跟踪</title>
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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/citySelect/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/Popt.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/cityJson.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/citySet.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
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
				var pageNumbers = parseInt(tableHeight/26);
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
						"mData":"PRJ_ID",
						"sWidth":"65px"
					},{
						"mData":"PRJ_YEAR",
						"sWidth":"120px"
					},{
						"mData":"DICTNAME",
						"sWidth":"120px"
					},{
						"mData":"PROJECT_NAME",
						"sWidth":"690px"
					},{
						"mData":"PROJECT_NODE",
						"sWidth":"130px"
					},{
						"mData":"CITY_NAME",
						"sWidth":"130px"
					},{
						"mData":"REGION_NAME",
						"sWidth":"130px"
					},{
						"mData":"SITE_ID",
						"sWidth":"250px"
					},{
						"mData":"SITE_NAME",
						"sWidth":"450px"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["PRJ_ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/projectMonitorAction/findItems.ilf"
				});
			});
			function searchData(){
				var PRO_NAME = $("#PRO_NAME_INPUT").val();
				if(PRO_NAME!=""){
					conditions = [{
						name:"PRO_NAME",
						value:PRO_NAME
					}];	
				}else{
					conditions = [];
				}
				conditions.push({
					name:"PRO_STATUS",
					value:$("#PRO_STATUS_SELECT").val()
				});
				oTable.fnDraw();
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 0px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>项目管理 >> 工程进度跟踪
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select id="PRO_STATUS_SELECT" style="width:200px;border:solid 1px #A3D0E3;border-top-left-radius:5px;border-bottom-left-radius:5px;">
							<option value="-">请选择项目状态</option>
							<option value="B">立项</option>
							<option value="C">可研</option>
							<option value="D">设计</option>
							<option value="E">实施</option>
							<option value="F">验收</option>
							<option value="G">决算</option>
						</select>
						<input id="PRO_NAME_INPUT" type="text" placeholder="请输入项目名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-top-left-radius:5px;border-bottom-left-radius:5px;"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #A3D0E3;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">项目年份</th>
										<th style="text-align:center;">项目类型</th>
										<th style="text-align:center;">项目名称</th>
										<th style="text-align:center;">项目状态</th>
										<th style="text-align:center;">地市</th>
										<th style="text-align:center;">区县</th>
										<th style="text-align:center;">所属站点编码</th>
										<th style="text-align:center;">所属站点名称</th>
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
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
    	<title>外验交付流程发起</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/project/flow/flow.js"></script>
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			var acheMap = null;
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
						"mData":"CURRENT_NODE",
						"sWidth":"180px"
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
					},{
						"mData":"CONTENTS",
						"sWidth":"450px",
						"bVisible":false
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheMap==null){
							acheMap = new HashMap();
						}
						acheMap.put(aData["PRJ_ID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["PRJ_ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["CURRENT_NODE"]==null){
							$("td:eq(1)",nRow).html("<font color='red'>未发起外验</font>");	
						}else{
							$("td:eq(1)",nRow).html("<font color='green'>"+aData["CURRENT_NODE"]+"</font>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/projectCheckinAction/findProjectsChecked.ilf"
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
				oTable.fnDraw();
			}
			function brownJob(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					window.parent.turnToJsp("项目外验交付详情","jsp/project/edit/brownJob.jsp?prjId="+checkedValue);	
				}else{
					window.wxc.xcConfirm("请先选择一个项目.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function openContentWindow(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					var projectMap = acheMap.get(checkedValue);
					if(projectMap["CURRENT_NODE"]!=null && projectMap["CURRENT_NODE"].indexOf("驳回")!=-1){
						$("#contentArea").val(projectMap["CONTENTS"]);
						$("#contentModal").dialog({
							title:"驳回原因",
							show:{
								effect:"slide",
								duration:400
							},
							hide:{
						        effect:"slide",
						        duration:400
							},
							width:370,
							height:260,
							autoOpen:true,
							closeOnEscape:true,
							draggable:false,
							resizable:false,
							modal:true,
							buttons:{
				  				"确认":function(){
				  					$(this).dialog("close");
								}
				  			}
						});
					}else{
						window.wxc.xcConfirm("当前环节不可查看驳回原因.",window.wxc.xcConfirm.typeEnum.info);	
					}
				}else{
					window.wxc.xcConfirm("请先选择一个项目.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 0px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>项目管理 >> 已发起外验交付任务查询
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select id="PRO_STATUS_SELECT" style="display:none;width:200px;border:solid 1px #A3D0E3;border-top-left-radius:5px;border-bottom-left-radius:5px;">
							<option value="-">请选择项目状态</option>
							<option value="B1">立项编制</option>
							<option value="B2">立项审核</option>
							<option value="B3">立项批复</option>
							<option value="C1">可研编制</option>
							<option value="C2">可研审核</option>
							<option value="C3">可研批复</option>
						</select>
						<input id="PRO_NAME_INPUT" type="text" placeholder="请输入项目名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-top-left-radius:5px;border-bottom-left-radius:5px;"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:brownJob();">
							<i class="icon-flag icon-white"></i>查看详情 
						</a>
						<a class="btn btn-danger" style="cursor:pointer" onclick="javascript:openContentWindow();">
							<i class="icon-envelope icon-white"></i>查看驳回原因 
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
										<th style="text-align:center;">当前状态</th>
										<th style="text-align:center;">项目年份</th>
										<th style="text-align:center;">项目类型</th>
										<th style="text-align:center;">项目名称</th>
										<th style="text-align:center;">项目状态</th>
										<th style="text-align:center;">地市</th>
										<th style="text-align:center;">区县</th>
										<th style="text-align:center;">所属站点编码</th>
										<th style="text-align:center;">所属站点名称</th>
										<th style="text-align:center;">驳回原因</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="contentModal" style="display:none;height:295px;">
			<div style="height:150px;border:solid 0px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:550px;font-size:12px;margin-top:5px;">
							<tr height="60">
								<td>
									<textarea id="contentArea" readonly type="text" style="border:solid 1px #54A8E1;width:100%;height:170px;font-size:12px;"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
	</body>
</html>
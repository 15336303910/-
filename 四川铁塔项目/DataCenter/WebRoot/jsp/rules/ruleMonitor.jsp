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
    	<title>核查任务监控</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<%--引入已封装的工具js--%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<style type="text/css">
			thead th{
				text-align:center;
			}
		</style>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-3
			    });
			    /*表格高度及容量*/
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
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
						"mData":"ID",
						"sWidth":"10%"
					},{
						"mData":"RULE_ID",
						"bVisible":false
					},{
						"mData":"RULE_NAME",
						"sWidth":"20%"
					},{
						"mData":"START_TIME",
						"sWidth":"14.4%"
					},{
						"mData":"END_TIME",
						"sWidth":"14.4%"
					},{
						"mData":"IS_FINISHED",
						"sWidth":"10%"
					},{
						"mData":"RECORD_TOTAL",
						"sWidth":"10%"
					},{
						"mData":"FATUAL_TOTAL",
						"sWidth":"10%"
					},{
						"mData":"FATUAL_RATIO",
						"sWidth":"10%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						/*缓存数据*/
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						/*模型选择*/
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						/*任务开始时间*/
						var fireDate = new Date();
						fireDate.setTime(aData["START_TIME"].time);
						$("td:eq(2)",nRow).html(fireDate.toLocaleString());
						if(aData["IS_FINISHED"]=="Y"){
							$("td:eq(4)",nRow).html("<center><font color='blue'>执行完毕</font></center>");
							/*任务结束时间*/
							fireDate.setTime(aData["END_TIME"].time);
							$("td:eq(3)",nRow).html(fireDate.toLocaleString());	
						}else{
							$("td:eq(4)",nRow).html("<center><font color='red'>正在执行</font></center>");
						}
						/*问题数*/
						if(aData["IS_FINISHED"]=="Y" && aData["FATUAL_TOTAL"]!="-" && parseInt(aData["FATUAL_TOTAL"])!=0){
							$("td:eq(6)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+");' style='cursor:pointer;color:blue;'>"+aData["FATUAL_TOTAL"]+"</a>");	
						}
						/*问题数占比*/
						if(aData["FATUAL_RATIO"]!=null && aData["FATUAL_RATIO"]!=""){
							$("td:eq(7)",nRow).html(aData["FATUAL_RATIO"]+"%");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/taskMonitorAction/findItems.ilf"
				});
				$("#dataTable tbody").click(function(event){
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
			});
			function problemDetail(jobId){
				var jobObject = acheItems.get(jobId);
				if(jobObject!=null){
					var ruleId = jobObject["RULE_ID"];
					var problemTotal = jobObject["FATUAL_TOTAL"];			
					window.parent.turnToJsp("问题数据详情","jsp/rules/prolDetails.jsp?ruleId="+ruleId+"&total="+problemTotal);	
				}
			}
			function getData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			};
			function searchData(){
				conditions = [];
				var jobState = $("#jobStateSelect").val();
				if(jobState!="-"){
					conditions.push({
						name:"IS_FINISHED",
						value:jobState
					});				
				}		
				oTable.fnDraw();
			}
			function removeJob(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null){
					window.wxc.xcConfirm("请先选择一条监控.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认删除此规则的核查任务及相关数据？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							window.parent.loading(true);
							$.ajax({
								url:"${pageContext.request.contextPath}/taskMonitorAction/deleteMonitor.ilf",
								async:false,
								type:"POST",
								data:"jobId="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									window.parent.overLoading(true);
									if(textStatus["success"]){
										window.wxc.xcConfirm("核查任务移除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("核查任务移除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.parent.overLoading(true);
									window.wxc.xcConfirm("核查任务移除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:0px;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>规则管理 >> 核查任务监控
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-top:5px;" id="initSelection">
							<select class="w500" id="jobStateSelect" style="width:140px;margin-right:5px;margin-top:3px;">
								<option value="-">- 选择任务状态 -</option>
								<option value="N">正在执行</option>
								<option value="Y">执行完毕</option>
							</select>
						</div>
						<a class="btn btn-primary" style="cursor:pointer;margin-top:5px;width:80px;" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<a class="btn btn-danger" style="cursor:pointer;margin-top:5px;width:80px;" onclick="javascript:removeJob();">
							<i class="icon-trash icon-white"></i>删除任务
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th>规则编号</th>
										<th>规则名称</th>
										<th>发起时间</th>
										<th>结束时间</th>
										<th>执行状态</th>
										<th>记录数</th>
										<th>核查问题数</th>
										<th>问题数占比</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>					
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:380px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3>+ 数据模型信息维护</h3>
			</div>
			<div class="modal-body" style="max-height:280px;">
				<form class="l-group" id="editForm">
					<input type="hidden" value="" id="hiddenOfCode"></input>
					<table class="table table-bordered table-hover">
						<tr>
							<td>
								<label class="l-name">所属数据源.</label>
							</td>
							<td id="dbEditTd">
								<select class="w500" id="dbTypeSelection">
									<option value="-">-</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label class="l-name">所属专业.</label>
							</td>
							<td>
								<select class="w500" id="majorSelection">
									<option value="空间资源">空间资源</option>
									<option value="传输外线">传输外线</option>
									<option value="传输内线">传输内线</option>
									<option value="无线">无线</option>
									<option value="动环">动环</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">模型标识.</label></td>
							<td><input id="tableAliasInput" type="text" placeholder="请输入模型标识." style="width:500px;height:30px;margin-top:5px;"></input></td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">模型名称.</label></td>
							<td><input id="tableNameInput" type="text" placeholder="请输入模型名称." style="width:500px;height:30px;margin-top:5px;"></input></td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">是否在用.</label></td>
							<td>
								<select class="w500" id="isUseSelection">
									<option value="Y">在用</option>
									<option value="N">停用</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;" onclick="javascript:doSave();">
					<i class="icon-envelope icon-white"></i>保存配置
				</button>
				<button class="btn btn-success" style="cursor:pointer;" onclick="javascript:doTest();">
					<i class="icon-film icon-white"></i>配置测试
				</button>
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;" id="canelButton">
					<i class="icon-zoom-out icon-white"></i>取消
				</button>
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
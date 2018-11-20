<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String monitorId = request.getParameter("monitorId");
	String brownType = request.getParameter("resultType");
	String fatalCount = request.getParameter("fatalCount");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>问题数据详情</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css"/>
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
			var itemOrder = 1;
			$(document).ready(function(){
				/*分辨率适配*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-85;
				var pageNumbers = parseInt(tableHeight/25);
				/*数据初始化*/
				conditions = [{
					name:"BELONG_MONITOR",
					value:$("#hiddenOfMonitorId").val()
				},{
					name:"PROBLEM_TYPE",
					value:$("#hiddenOfType").val()
				},{
					name:"FATAL_COUNT",
					value:$("#hiddenOfCount").val()
				}];
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
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"DATA_CITY",
						"sWidth":"10%"
					},{
						"mData":"PRIMARY_VALUE",
						"sWidth":"20%"
					},{
						"mData":"RESOURCE_NAME",
						"sWidth":"25%"
					},{
						"mData":"PROBLEM_DESC",
						"sWidth":"44%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/compareDetailAction/findProblems.ilf"
				});
				$("#resourceNameKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/dataCompareAction/fatalDescribe.ilf",
					async:false,
					type:"POST",
					data:"belongMonitor="+$("#hiddenOfMonitorId").val()+"&fatalType="+$("#hiddenOfType").val(),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"] && textStatus["options"]!=null && textStatus["options"].length>0){
							var options = textStatus["options"];
							var optionHtml = "";
							optionHtml+="<select style='width:400px;border:solid 1px #A3D0E3;' id='fatualTypeSelect'>";
								optionHtml+="<option value='-'>请选择错误类型</option>";
							for(var i=0;i<options.length;i++){
								var $option = options[i];
								optionHtml+="<option value='"+$option["PROBLEM_DESC"]+"'>"+$option["PROBLEM_DESC"]+"</option>";
							}
							optionHtml+="</select>";
							document.getElementById("fatualContainer").innerHTML = optionHtml;
						}
					},
					error:function(){}
				});
				var fatalType = $("#hiddenOfType").val();
				if(fatalType=="IS_UNIFORM"){
					$("#fatualContainer").hide();
				}
			});
			function getData(sSource,aoData,fnCallback){
				itemOrder = 1;
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
				conditions = [{
					name:"BELONG_MONITOR",
					value:$("#hiddenOfMonitorId").val()
				},{
					name:"PROBLEM_TYPE",
					value:$("#hiddenOfType").val()
				},{
					name:"FATAL_COUNT",
					value:$("#hiddenOfCount").val()
				}];
				var fatalType = $("#fatualTypeSelect").val();
				if(fatalType!="-"){
					conditions.push({
						name:"PROBLEM_DESC",
						value:fatalType
					});
				}
				var resourceName = $("#resourceNameKey").val();
				if(resourceName!=""){
					conditions.push({
						name:"RESOURCE_NAME",
						value:resourceName
					});				
				}			
				oTable.fnDraw();
			}
			function downloadData(){
				var checkedModelId = $("#hiddenOfTableId").val();
				if(checkedModelId=="-1"){
					window.wxc.xcConfirm("请先选择一个资源模型.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认下载此资源模型数据？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							/*
								获取数据导出应有的参数.
							 */
							var MONITOR_ID = $("#hiddenOfMonitorId").val();
							var FATAL_TYPE = $("#hiddenOfType").val();
							/*
								执行导出.
							 */
							var exportForm = $("<form>");
						    exportForm.attr("style","display:none");
						    exportForm.attr("target","");
						    exportForm.attr("method","post");
						    exportForm.attr("action","${pageContext.request.contextPath}/compareDetailAction/exportMyFatalData.ilf?moditorId="+MONITOR_ID+"&fatalType="+FATAL_TYPE);
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:99.3%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfMonitorId" value="<%=monitorId %>"></input>
  		<input type="hidden" id="hiddenOfType" value="<%=brownType %>"></input>
  		<input type="hidden" id="hiddenOfCount" value="<%=fatalCount %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:0px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
				<span class="panel-label"></span>&nbsp;问题数据详情&nbsp;>>&nbsp;默认呈现属性比对不一致数，其他问题类型请选择后查询 
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-top:6px;margin-right:3px;" id="fatualContainer">
							<select style="width:400px;border:solid 1px #A3D0E3;" id="fatualTypeSelect">
								<option value="-">请选择错误类型</option>
							</select>
						</div>
						<input type="text" placeholder="请输入资源名称" style="width:200px;height:30px;font-size:12px;border:solid 1px #A3D0E3;" id="resourceNameKey"></input>
						<a class="btn btn-primary"  style="cursor:pointer;" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-warning MANAGER_BUTTONS"  style="cursor:pointer;" onclick="javascript:downloadData();">
							<i class="icon-download-alt icon-white"></i>数据导出
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #DFE8F1;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th>所属地市</th>
										<th>资源编号</th>
										<th>资源名称</th>
										<th>问题描述</th>
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
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
    	<title>数据全量浏览</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style type="text/css">
			.jstrees{
				overflow:auto;
				border:1px solid silver;
				min-height:100px;
				text-align:left;
				font-size:18px;
				font-size:2.4em;
				font-size:100%;
				float:left;
			}
			thead th{
				text-align:center;
			}
			.dataTables_wrapper{
				border:solid 1px #A3D0E3;
			}
		</style>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			var tableHeight = 0;
			var pageAccount = 0;
			var pageNumber = 1;
			var pageContext = 4;
			var tableHeight = 300;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#hiddenOfHeight").val()-2
			    });
				$("#ruleEditsBuck").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-2
			    });
				$("#frmt").css({
			    	"height":$("#ruleEditsBuck").height()-$("#leftTreeHead").height()-10
			    });
				tableHeight = $("#ruleEditsBuck").height()-$("#ruleTablePanel").height()-70-2;
				pageAccount = parseInt(tableHeight/27);
				/*任务视图每页记录数*/
				pageContext = parseInt($("#ruleClassBuck").height()/105);
				/*初始化资源树*/
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleManageAction/findMyDesigned.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							var nodes = textStatus["nodes"];
							$("#frmt").on("changed.jstree",function(e,data){
								if(data.selected.length){
									var nodeCode = data.instance.get_node(data.selected[0]).id;
									var regExp = new RegExp("^[0-9]*$");
									if(regExp.test(nodeCode)){
										$("#hiddenOfTableId").val(nodeCode);
										/*
											====== 渲染模板列表  ====== 
										*/
										$.ajax({
											url:"${pageContext.request.contextPath}/dataDesignAction/findTemplatesConfiged.ilf",
											async:false,
											type:"POST",
											data:"modelId="+nodeCode,
											dataType:"json",
											timeout:10000, 
											success:function(textStatus){
												if(textStatus["SUCCESS"]){
													var Options = textStatus["OPTIONS"];
													var optionHtml = "";
													optionHtml+="<select id='TEMPLATE_SELECT' onchange='javascript:changeSelection(this.value);' style='width:200px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;'>";
													if(Options.length>0){
														for(var i=0;i<Options.length;i++){
															var optionObj = Options[i];
															optionHtml+="<option value='"+optionObj["BELONG_TEMPLATE"]+"'>"+optionObj["BELONG_TEMPLATE"]+"</option>";		
														}
													}else{
														optionHtml+="<option value='-'>尚未配置模板</option>";
													}
													optionHtml+="</select>";
													document.getElementById("TEMPLATE_SELECTION").innerHTML = optionHtml;
												}else{
													window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
												}
											},
											error:function(){
												window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
											}
										});
										/*重新渲染表头*/
										$.ajax({
											url:"${pageContext.request.contextPath}/dataDesignAction/findColumns.ilf",
											async:false,
											type:"POST",
											data:"modelId="+nodeCode+"&templateName="+encodeURIComponent(encodeURIComponent($("#TEMPLATE_SELECT").val(),"UTF-8"),"UTF-8"),
											dataType:"json",
											timeout:10000, 
											success:function(textStatus){
												if(textStatus["success"] && textStatus["is_column"]){
													var columns = textStatus["columns"];
													var headHtml = "";
													for(var i=0;i<columns.length;i++){
														var columnObj = columns[i];
														headHtml+="<th>"+columnObj["COLUMN_ALIAS"]+"</th>";	
													}
													document.getElementById("headContainer").innerHTML = headHtml;
													/*重新渲染表格*/
													var columnHeads = new Array();
													for(var i=0;i<columns.length;i++){
														var columnObj = columns[i];
														var columnName = columnObj["COLUMN_NAME"];
														var strLength = columnName.length;
														var headObj = {
															"mData":columnObj["COLUMN_NAME"],
															"sWidth":parseInt(strLength*30)+"px"
														};
														columnHeads.push(headObj);
													}
													initTable(columnHeads);
												}
											},
											error:function(){}
										});
									}
								}
							}).jstree({
								"core":{
									"data":nodes,
									"dblclick_toggle":true
								}
							});
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("模型浏览初始化失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				}];
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
					"destroy":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"99.9%",
					"aoColumns":[{
						"mData":"RES_ID"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageAccount,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
					},
					"fnServerData":function (sSource,aoData,fnCallback){
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
					"sAjaxSource":"${pageContext.request.contextPath}/dataDesignAction/findMyItems.ilf"
				});
			});
			function changeSelection(templateName){
				/*重新渲染表头*/
				$.ajax({
					url:"${pageContext.request.contextPath}/dataDesignAction/findColumns.ilf",
					async:false,
					type:"POST",
					data:"modelId="+$("#hiddenOfTableId").val()+"&templateName="+encodeURIComponent(encodeURIComponent(templateName,"UTF-8"),"UTF-8"),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"] && textStatus["is_column"]){
							var columns = textStatus["columns"];
							var headHtml = "";
							for(var i=0;i<columns.length;i++){
								var columnObj = columns[i];
								headHtml+="<th>"+columnObj["COLUMN_ALIAS"]+"</th>";	
							}
							document.getElementById("headContainer").innerHTML = headHtml;
							/*重新渲染表格*/
							var columnHeads = new Array();
							for(var i=0;i<columns.length;i++){
								var columnObj = columns[i];
								var columnName = columnObj["COLUMN_NAME"];
								var strLength = columnName.length;
								var headObj = {
									"mData":columnObj["COLUMN_NAME"],
									"sWidth":parseInt(strLength*30)+"px"
								};
								columnHeads.push(headObj);
							}
							initTable(columnHeads);
						}
					},
					error:function(){}
				});
			}
			function initTable(columnHeads){
				acheItems = null;
				oTable.fnDestroy();
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":true,
					"bProcessing":false,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":false,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"99.9%",
					"aoColumns":columnHeads,
					"aoColumnDefs":[],
					"iDisplayLength":pageAccount,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
					},
					"fnServerData":function (sSource,aoData,fnCallback){
						window.parent.showLoading(45,42);
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
							dataType:"json",		
							success:function(data){
								window.parent.hideLoading();
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/dataDesignAction/findMyItems.ilf"
				});
				searchData();
			}
			function searchData(){
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				},{
					name:"TEMPLATE_NAME",
					value:$("#TEMPLATE_SELECT").val()
				}];		
				oTable.fnDraw();
			}
			function removeResData(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条资源数据.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认删除此资源数据？.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/dataBrownAction/removeResData.ilf",
								async:false,
								type:"POST",
								data:"checkedResId="+checkedValue+"&modelId="+$("#hiddenOfTableId").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("资源数据删除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("资源数据删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("资源数据删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}
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
							var exportForm = $("<form>");
						    exportForm.attr("style","display:none");
						    exportForm.attr("target","");
						    exportForm.attr("method","post");
						    exportForm.attr("action","${pageContext.request.contextPath}/dataDesignAction/exportData.ilf?modelId="+checkedModelId+"&templateName="+encodeURIComponent(encodeURIComponent($("#TEMPLATE_SELECT").val(),"UTF-8"),"UTF-8"));
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="-1" id="hiddenOfTableId"></input>
  		<input type="hidden" value="-1" id="hiddenOfCount"></input>
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-body" style="width:100%;border:0px;" id="ruleConfigDiv">
				<div style="width:99.8%;overflow:auto;border:solid 1px #FFF;" id="ruleEditsBuck">
					<div style="float:left;width:28%;height:440px;margin-top:4px;margin-left:3px;" id="leftTreePanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="leftTreeHead">
							<span class="panel-label"></span>已订阅模型
						</div>
						<div class="panel-body" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<div id="frmt" class="jstrees" style="width:99%;height:680px;overflow:auto;border:solid 1px #A3D0E3;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div style="float:left;width:71%;margin-top:4px;margin-left:4px;" id="rightDataPanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="ruleTablePanel">
							<span class="panel-label"></span>数据管理
							<form class="form-search pull-right">	
								<div style="float:right;">
									<div style="float:left;" id="TEMPLATE_SELECTION">
										<select onchange="javascript:changeSelection(this.value);" id="TEMPLATE_SELECT" style="width:200px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;">
											<option value="-">尚未配置模板</option>
										</select>
									</div>
									<div style="float:left;">
										<a class="btn btn-info" style="cursor:pointer;margin-top:5px;" onclick="javascript:downloadData();">
											<i class="icon-download-alt icon-white"></i>数据导出
										</a>
									</div>
								</div>
							</form>
						</div>
						<div class="panel-body h350" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
											<thead>
												<tr id="headContainer">
													<th>数据详情</th>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--核查任务已完成--%>
		<div id="tipDivs" style="display:none;position:fixed;width:300px;height:100px;border:solid 1px #397FF1;bottom:4px;right:7px;background:#FFF;">
			<div style="margin-top:30px;margin-left:30px;" id="tipsContainer">&nbsp;</div>
		</div>
		<%--包含规则列表--%>
		<div id="itemsModal" class="modal hide fade" style="width:750px;height:355px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 包含规则列表</h3>
			</div>
			<div class="modal-body" style="max-height:260px;" id="ruleItemBrown">&nbsp;</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
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
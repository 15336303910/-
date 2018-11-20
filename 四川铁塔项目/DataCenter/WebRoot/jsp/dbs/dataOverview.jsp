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
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
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
					url:"${pageContext.request.contextPath}/ruleManageAction/findDbData.ilf",
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
										$("#resNameKey").val("");
										/*重新渲染表头*/
										$.ajax({
											url:"${pageContext.request.contextPath}/dataBrownAction/findColumns.ilf",
											async:false,
											type:"POST",
											data:"modelId="+nodeCode,
											dataType:"json",
											timeout:10000, 
											success:function(textStatus){
												/*
													===== 地市下拉列表  ===== 
												 */
												var citySelect = "";
												citySelect+="<select id='BELONG_CITY' style='width:150px;height:29px;border:solid 1px #A3D0E3;'>";
												if(textStatus["IS_CITY_COLUMN_EXIST"]){
													var CityList = textStatus["CITY_LIST"];
													if(CityList!=null && CityList.length>0){
														for(var i=0;i<CityList.length;i++){
															var cityName = CityList[i];
															if(cityName=="-"){
																citySelect+="<option value='-'>全省</option>";
															}else{
																citySelect+="<option value='"+cityName+"'>"+cityName+"</option>";	
															}
														}
													}
												}else{
													citySelect+="<option value='-'>-</option>";
												}
												citySelect+= "</select>";
												document.getElementById("CITY_LIST_DIV").innerHTML = citySelect;
												/*
													===== 模型的表头信息  ===== 
												 */
												if(textStatus["success"] && textStatus["is_column"]){
													var columns = textStatus["columns"];
													var headHtml = "";
													headHtml+="<th>&nbsp;</th>";
													for(var i=0;i<columns.length;i++){
														var columnObj = columns[i];
														headHtml+="<th>"+columnObj["COLUMN_ALIAS"]+"</th>";	
													}
													document.getElementById("headContainer").innerHTML = headHtml;
													/*重新渲染表格*/
													var columnHeads = new Array();
													for(var i=0;i<columns.length;i++){
														var columnObj = columns[i];
														if(i==0){
															columnHeads.push({
																"mData":columnObj["COLUMN_NAME"],
																"sWidth":"70px"
															});
														}
														var columnName = columnObj["COLUMN_NAME"];
														var strLength = columnName.length;
														var headObj = {
															"mData":columnObj["COLUMN_NAME"],
															"sWidth":parseInt(strLength*20)+"px"
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
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/dataBrownAction/findItems.ilf"
				});
				$("#resNameKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				$("#MODEL_NAME_INPUT").bind("keydown",function(event){
					if(event.keyCode==13){
						flushDbTree();
						return false;
					}
				});
			});
			function searchData(){
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				}];
				var cityName = $("#BELONG_CITY").val();
				if(cityName!="-" && cityName!="-"){
					conditions.push({
						name:"CITY_NAME",
						value:cityName
					});
				}
				var resName = $("#resNameKey").val();
				if(resName!=""){
					conditions.push({
						name:"NAME_KEY",
						value:resName
					});				
				}		
				oTable.fnDraw();
			}
			function initTable(columnHeads){
				acheItems = null;
				oTable.fnDestroy();
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
					"sScrollX":"99.9%",
					"aoColumns":columnHeads,
					"aoColumnDefs":[],
					"iDisplayLength":pageAccount,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["RES_ID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["RES_ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/dataBrownAction/findItems.ilf"
				});
				searchData();
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
						    exportForm.attr("action","${pageContext.request.contextPath}/dataBrownAction/exportData.ilf?belongCity="+encodeURIComponent(encodeURIComponent($("#BELONG_CITY").val(),"UTF-8"),"UTF-8")+"&nameKey="+encodeURIComponent(encodeURIComponent($("#resNameKey").val(),"UTF-8"),"UTF-8")+"&modelId="+checkedModelId);
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});	
				}
			}
			function downloadFile(){
				var checkedModelId = $("#hiddenOfTableId").val();
				if(checkedModelId=="-1"){
					window.wxc.xcConfirm("请先选择一个资源模型.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认下载此资源模型模板？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							var exportForm = $("<form>");
						    exportForm.attr("style","display:none");
						    exportForm.attr("target","");
						    exportForm.attr("method","post");
						    exportForm.attr("action","${pageContext.request.contextPath}/dataBrownAction/exportTemplate.ilf?modelId="+checkedModelId);
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});	
				}
			}
			function openEditWindow(){				
				$("#brownModal").dialog({
					title:"模板下载/数据导入",
					show:{
						effect:"slide",
						duration:700
					},
					hide:{
				        effect:"slide",
				        duration:700
					},
					width:400,
					height:240,
					autoOpen:true,
					closeOnEscape:true,
					draggable:false,
					resizable:false,
					modal:true,
					buttons:{
		  				"确定":function(){
		  					var uploadFileName = $("#TemplateUpload").val();
		  					if(uploadFileName.indexOf(".xlsx")==-1){
		  						window.wxc.xcConfirm("请上传*.xlsx格式的数据文件.",window.wxc.xcConfirm.typeEnum.info);
		  					}else{
		  						$.ajaxFileUpload({
		  							url:"${pageContext.request.contextPath}/dataBrownAction/uploadData.ilf",
		  							secureuri:false,
		  							contentType:"text/xml",
		  							fileElementId:"TemplateUpload",
		  							type:"POST",
		  							dataType:"json",
		  							success:function(data,status){
		  								if(data["success"]){
		  									window.wxc.xcConfirm("数据导入已完成.",window.wxc.xcConfirm.typeEnum.info);
		  									$("#brownModal").dialog("close");
		  									searchData();
		  								}else{
		  									window.wxc.xcConfirm(data["message"],window.wxc.xcConfirm.typeEnum.info);
		  								}
		  							},
		  							error:function(message){
		  								window.wxc.xcConfirm("数据导入失败，请联系系统管理员 .",window.wxc.xcConfirm.typeEnum.info);
		  							}
		  						});	
		  					}
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
			function flushDbTree(){
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleManageAction/findModelsByKey.ilf",
					async:false,
					type:"POST",
					data:"modelName="+$("#MODEL_NAME_INPUT").val(),
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							var nodes = textStatus["nodes"];
							var tree = $("#frmt");
							tree.jstree(true).settings.core.data = nodes;  
							tree.jstree(true).refresh();
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("模型查找失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
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
							<input type="text" id="MODEL_NAME_INPUT" style="height:29px;width:175px;border:solid 1px #A3D0E3;margin-top:5px;" placeholder="请输入模型名称"></input>
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
									<div style="float:left;margin-top:6px;margin-right:3px;" id="CITY_LIST_DIV">
										<select id='BELONG_CITY' style="width:150px;height:29px;border:solid 1px #A3D0E3;">
											<option value="-">-</option>
										</select>
									</div>
									<div style="float:left;">
										<input type="text" placeholder="请输入资源名称" style="width:150px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="resNameKey"></input>
										<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:searchData();">
											<i class="icon-search icon-white"></i>执行查询
										</a>
										<a class="btn btn-info" style="cursor:pointer" onclick="javascript:downloadData();">
											<i class="icon-download-alt icon-white"></i>数据导出
										</a>
									</div>
									<%--<a class="btn btn-danger" style="cursor:pointer" onclick="javascript:removeResData();" >
										<i class="icon-trash icon-white"></i>删除数据
									</a>
									<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:openEditWindow();">
										<i class="icon-upload icon-white"></i>数据导入
									</a>
									<a class="btn btn-primary" style="cursor:pointer">
										<i class="icon-flag icon-white"></i>数据修改
									</a>--%>
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
		<!--窗口表单：开始-->
		<div id="brownModal" style="display:none;height:295px;">
			<div style="height:150px;border:solid 1px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:350px;font-size:12px;margin-top:15px;">
							<tr height="60">
								<td style="text-align:right;width:100px">是否需要模板：</td>
								<td><a class="btn btn-success" style="cursor:pointer" onclick="javascript:downloadFile();">
									<i class="icon-download-alt icon-white"></i>下载模板
								</a></td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">数据导入：</td>
								<td><input type="file" name="TemplateUpload" id="TemplateUpload" style="cursor:pointer;width:220px;height:29px;border:solid 1px #A3D0E3;"></input></td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<!--窗口表单：结束-->
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
</html>
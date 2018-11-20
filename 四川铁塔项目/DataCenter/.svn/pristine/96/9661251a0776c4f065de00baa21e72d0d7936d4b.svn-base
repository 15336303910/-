<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String tableCode = request.getParameter("tableId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>模型字段维护</title>
    	<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
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
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });		
				$("#columnPanel").css({
			    	"height":$("#bodyHeight").height()-$("#editPanel").height()-5
			    });
				var tableHeight = $("#columnPanel").height()-$("#columnHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/30);
				/*数据表格*/
				var tableId = $("#hiddenOfTableId").val();
				conditions.push({
					name:"BELONG_TABLE",
					value:tableId
				});
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
						"sWidth":"6%"
					},{
						"mData":"COLUMN_ALIAS",
						"sWidth":"16%"
					},{
						"mData":"COLUMN_NAME",
						"sWidth":"15%"
					},{
						"mData":"DATA_TYPE",
						"sWidth":"15%"
					},{
						"mData":"IS_EXPORT",
						"sWidth":"16%"
					},{
						"mData":"IS_PRIMARY_KEY",
						"sWidth":"15.4%"
					},{
						"mData":"IS_NULL_ABLE",
						"sWidth":"15%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:changeCheck(this.checked,this.value);'></input></center>");
						if(aData["IS_EXPORT"]=="Y"){
							$("td:eq(4)",nRow).html("<center><font color='green'>导出</font></center>");	
						}else{
							$("td:eq(4)",nRow).html("<center><font color='red'>不导出</font></center>");
						}
						if(aData["IS_PRIMARY_KEY"]=="Y"){
							$("td:eq(5)",nRow).html("<center><font color='green'>是</font></center>");	
						}else{
							$("td:eq(5)",nRow).html("<center><font color='red'>否</font></center>");
						}
						if(aData["IS_NULL_ABLE"]=="Y"){
							$("td:eq(6)",nRow).html("<center><font color='green'>是</font></center>");	
						}else{
							$("td:eq(6)",nRow).html("<center><font color='red'>否</font></center>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/basicColumnAction/findItems.ilf"
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
			function changeCheck(isChecked,checkedValue){
				if(isChecked){
					var jobs = document.getElementsByName("checksRadio");
					if(jobs!=null && jobs.length>0){
						for(var i=0;i<jobs.length;i++){
							if(jobs[i].value==checkedValue){
								jobs[i].checked = true;
							}else{
								jobs[i].checked = false;
							}
						}
					}
					if(acheItems!=null && acheItems.containsKey(checkedValue)){
						var checkedObj = acheItems.get(checkedValue);
						if(checkedObj!=null){
							$("#hiddenOfColumnCode").val(checkedObj["ID"]);
							$("#aliasNameInput").val(checkedObj["COLUMN_ALIAS"]);
							$("#columnNameInput").val(checkedObj["COLUMN_NAME"]);
							$("#dataTypeSelection").val(checkedObj["DATA_TYPE"]);
							$("#isExported").val(checkedObj["IS_EXPORT"]);
							$("#isPrimaryKeySelect").val(checkedObj["IS_PRIMARY_KEY"]);
							$("#isNullAbleSelect").val(checkedObj["IS_NULL_ABLE"]);
						}
					}
				}else{
					$("#hiddenOfColumnCode").val("");
					document.getElementById("columnEditForm").reset();
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
				var tableId = $("#hiddenOfTableId").val();
				conditions.push({
					name:"BELONG_TABLE",
					value:tableId
				});
				var columnKey = $("#columnKeyInput").val();
				if(columnKey!=""){
					conditions.push({
						name:"COLUMN_ALIAS",
						value:columnKey
					});				
				}		
				oTable.fnDraw();
			}
			function saveColumnMeta(){
				//属性标识
				var aliasName = $("#aliasNameInput").val();
				if(aliasName==""){
					window.wxc.xcConfirm("属性标识不能为空.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				//字段名称
				var columnName = $("#columnNameInput").val();
				if(columnName==""){
					window.wxc.xcConfirm("字段名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				//值类型
				var dataType = $("#dataTypeSelection").val();
				if(dataType=="-"){
					window.wxc.xcConfirm("请选择属性的值类型.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				//能否导出
				var isPrimaryKey = $("#isPrimaryKeySelect").val();
				var isExportable = $("#isExported").val();
				if(isPrimaryKey=="Y" && isExportable=="N"){ 
					window.wxc.xcConfirm("主键必须配置为导出.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				/*执行保存*/
				var requestParams = {
					belongTable:$("#hiddenOfTableId").val(),
					id:$("#hiddenOfColumnCode").val(),
					aliasName:aliasName,
					columnName:columnName,
					dataType:dataType,
					isExport:isExportable,
					isPrimary:isPrimaryKey,
					isNullAble:$("#isNullAbleSelect").val()
				};
				$.ajax({
					url:"${pageContext.request.contextPath}/basicColumnAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(requestParams),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#hiddenOfColumnCode").val("");
							document.getElementById("columnEditForm").reset();
							searchData();
						}else{
							window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("系统异常.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function deleteModel(){
				var isChecked = false;
				var checkedValue = null;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							isChecked = true;
							checkedValue = jobs[i].value; 
							break;
						}
					}
				}
				if(isChecked){
					window.wxc.xcConfirm("是否确认删除此属性？.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/basicColumnAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"itemCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										searchData();
										$("#hiddenOfColumnCode").val("");
										document.getElementById("columnEditForm").reset();
									}else{
										window.wxc.xcConfirm("模型属性删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("模型属性删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}else{
					window.wxc.xcConfirm("请先选择一个模型属性.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=tableCode %>" id="hiddenOfTableId"></input>
  		<div style="float:left;width:99.9%;height:300px;border:solid 1px #FFF;" id="columnPanel">
  			<div class="panel panel-default" style="margin-top:-20px;">
				<div class="panel-heading" id="columnHeading">
					<span class="panel-label"></span>模型维护  >> 属性维护
					<form class="form-search pull-right">	
						<div style="float:right;">
							<input type="text" placeholder="请输入字段名称" style="width:200px;height:29px;border:solid 1px #A3D0E3;font-size:12px;" id="columnKeyInput"></input>
							<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
								<i class="icon-search icon-white"></i>执行查询 
							</a>
							<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteModel();">
								<i class="icon-trash icon-white"></i>删除属性
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
											<th>字段标识</th>
											<th>字段名称</th>
											<th>数据类型</th>
											<th>是否导出</th>
											<th>是否主键</th>
											<th>能否为空</th>
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
  		<div style="float:left;width:99.9%;height:170px;" id="editPanel">
  			<div class="panel panel-default" style="margin-top:-20px;height:170px;border-top:solid 1px #DFE8F1;">
				<div class="panel-heading">
					<span class="panel-label"></span>模型维护  >> 属性编辑
					<form class="form-search pull-right">	
						<div style="float:right;">
							<a class="btn btn-primary" style="width:200px;cursor:pointer;margin-top:5px;margin-right:500px;" onclick="javascript:saveColumnMeta();">
								<i class="icon-plus-sign icon-white"></i>保存
							</a>
						</div>
					</form>
				</div>
				<div class="panel-body h340" style="border:0px;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto">
								<form id="columnEditForm">
									<input type="hidden" id="hiddenOfColumnCode" value=""></input>
									<table cellpadding="0" cellspacing="0" border="0" style="width:100%;border:solid 1px #A3D0E3;font-size:12px;margin-top:10px;">
										<tbody>
											<tr height="30">
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">字段标识</td>
												<td style="width:23%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
													<center>
														<input type="text" id="aliasNameInput" placeholder="请输入字段标识" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;"></input>
													</center>
												</td>
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">字段名称</td>
												<td style="width:23%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
													<center>
														<input type="text" id="columnNameInput" placeholder="请输入字段名称" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;"></input>
													</center>
												</td>
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">数据类型</td>
												<td style="width:24%;border-bottom:solid 1px #A3D0E3;text-align:center;">
													<center>
														<select id="dataTypeSelection" class="w500" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;">
															<option value="-">- 选择数据类型 -</option>
															<option value="字符串">字符串</option>
															<option value="数字">数字</option>
															<option value="日期">日期</option>
															<option value="地市">地市</option>
														</select>
													</center>
												</td>
											</tr>
											<tr height="30">
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">是否导出</td>
												<td style="width:23%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
													<center>
														<select id="isExported" class="w500" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;">
															<option value="N">不导出</option>
															<option value="Y">导出</option>
														</select>
													</center>
												</td>
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">是否主键</td>
												<td style="width:23%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
													<center>
														<select id="isPrimaryKeySelect" class="w500" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;">
															<option value="N">否</option>
															<option value="Y">是</option>
														</select>
													</center>
												</td>
												<td style="width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">能否为空</td>
												<td style="width:24%;border-bottom:solid 1px #A3D0E3;text-align:center;">
													<center>
														<select id="isNullAbleSelect" class="w500" style="width:95%;height:30px;margin-top:7px;border:solid 1px #4287F7;border-radius:7px;">
															<option value="Y">可以为空</option>
															<option value="N">不能为空</option>
														</select>
													</center>
												</td>
											</tr>
										</tbody>
									</table>
								</form>					
							</div>
						</div>
					</div>
				</div>
			</div>
  		</div>
  	</body>
</html>

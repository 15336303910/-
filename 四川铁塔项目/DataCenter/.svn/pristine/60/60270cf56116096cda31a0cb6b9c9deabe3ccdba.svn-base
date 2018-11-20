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
    	<title>数据模型管理</title>
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
				/*
					数据源列表[顶部搜索栏；数据源编辑表单]
				 */
				$.ajax({
					url:"${pageContext.request.contextPath}/basicDbTableAction/findDbOptions.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus!=null && textStatus.length>0){
							var selectHtml = "<select class='w500' id='belongDbSelect' style='width:160px;border:solid 1px #A3D0E3;border-radius:6px;'>";
							selectHtml+="<option value='-'>- 请选择数据源 - </option>";
							var optionHtml = "<select class='w500' id='dbTypeSelection'>";
							for(var i=0;i<textStatus.length;i++){
								var thisItem = textStatus[i];
								/*顶部菜单*/
								selectHtml+="<option value='"+thisItem["ID"]+"'>"+thisItem["DB_NAME"]+"</option>";
								/*编辑表单*/
								optionHtml+="<option value='"+thisItem["ID"]+"'>"+thisItem["DB_NAME"]+"</option>";
							}
							selectHtml+="</select>";
							optionHtml+="</select>";
							document.getElementById("initSelection").innerHTML = selectHtml;
							document.getElementById("dbEditTd").innerHTML = optionHtml;
						}
					},
					error:function(){}
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/propertyService/getMajorList.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							var options = textStatus["MAJORS"];
							var selectHtml = "<select class='w500' id='majorSelection'>";
							selectHtml+="<option value='-'>- 请指定专业 -</option>";
							if(options!=null && options.length>0){
								for(var i=0;i<options.length;i++){
									var optionObj = options[i];
									selectHtml+="<option value='"+optionObj["PRO_VALUE"]+"'>"+optionObj["PRO_VALUE"]+"</option>";
								}	
							}
							selectHtml+="</select>";
							document.getElementById("MAJOR_CONTAINER").innerHTML = selectHtml;
						}
					},
					error:function(){}
				});
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
			    /*表格高度及容量*/
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
				/*
					模型数据展示[列表]
				 */
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":true,
					"bProcessing":true,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":true,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"6%"
					},{
						"mData":"BELONG_DB",
						"bVisible":false
					},{
						"mData":"DB_NAME",
						"sWidth":"15%"
					},{
						"mData":"BELONG_MAJOR",
						"sWidth":"14%"
					},{
						"mData":"TABLE_ALIAS",
						"sWidth":"14%"
					},{
						"mData":"TABLE_NAME",
						"sWidth":"33%"
					},{
						"mData":"USE_STATE",
						"sWidth":"17.4%"
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
						/*属性展示*/
						$("td:eq(4)",nRow).html("<a style='cursor:pointer;text-decoration:underline;' onclick='javascript:turnToPage("+aData["ID"]+");'>"+aData["TABLE_NAME"]+"</a>");
						/*是否在用*/
						if(aData["USE_STATE"]=="Y"){
							$("td:eq(5)",nRow).html("<font color='green'>在用</font>");	
						}else{
							$("td:eq(5)",nRow).html("<font color='red'>停用</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/basicDbTableAction/findItems.ilf"
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
			/*
				界面跳转
			 */
			function turnToPage(thisValue){
				var title = "字段维护";
				if(acheItems!=null){
					var checkedObj = acheItems.get(thisValue);
					title+=":"+checkedObj["TABLE_NAME"];
				}
				window.parent.turnToJsp(title,"jsp/dbs/editColumn.jsp?tableId="+thisValue);
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
				var tableName = $("#dbTableKeyInput").val();
				if(tableName!=""){
					conditions.push({
						name:"TABLE_ALIAS",
						value:tableName
					});				
				}
				var belongDb = $("#belongDbSelect").val();
				if(belongDb!="-"){
					conditions.push({
						name:"BELONG_DB",
						value:belongDb
					});				
				}			
				oTable.fnDraw();
			}
			function initIfChecked(){
				document.getElementById("editForm").reset();
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null && acheItems!=null){
					var checkedObj = acheItems.get(checkedValue);
					if(checkedObj!=null){
						$("#hiddenOfCode").val(checkedObj["ID"]);
						$("#dbTypeSelection").val(checkedObj["BELONG_DB"]);
						$("#majorSelection").val(checkedObj["BELONG_MAJOR"]);
						$("#tableAliasInput").val(checkedObj["TABLE_ALIAS"]);
						$("#tableNameInput").val(checkedObj["TABLE_NAME"]);
						$("#isUseSelection").val(checkedObj["USE_STATE"]);
					}
				}
			}
			function validInputs(){
				var aliasName = $("#tableAliasInput").val();
				if(aliasName==""){
					window.wxc.xcConfirm("模型标识不能为空.",window.wxc.xcConfirm.typeEnum.info);
					return false;
				}
				var tableName = $("#tableNameInput").val();
				if(tableName==""){
					window.wxc.xcConfirm("模型名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
					return false;
				}
				return true;
			}
			/*
				根据表单来测试模型是否可用
			 */
			function doTest(){
				if(validInputs()){
					var requestParams = {
						belongDb:$("#dbTypeSelection").val(),
						tableName:$("#tableNameInput").val()
					};
					$.ajax({
						url:"${pageContext.request.contextPath}/basicDbTableAction/testConnect.ilf",
						async:false,
						type:"POST",
						data:"params="+JSON.stringify(requestParams),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								window.wxc.xcConfirm("模型测试成功.",window.wxc.xcConfirm.typeEnum.info);
							}else{
								window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
							}
						},
						error:function(){
							window.wxc.xcConfirm("测试失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					});
				}
			}
			/*
				保存模型
			 */
			function doSave(){
				if(validInputs()){
					var requestParams = {
						id:$("#hiddenOfCode").val(),
						belongDb:$("#dbTypeSelection").val(),
						belongMajor:$("#majorSelection").val(),
						aliasName:$("#tableAliasInput").val(),
						tableName:$("#tableNameInput").val(),
						isUsing:$("#isUseSelection").val()
					};
					$.ajax({
						url:"${pageContext.request.contextPath}/basicDbTableAction/saveAudit.ilf",
						async:false,
						type:"POST",
						data:"params="+JSON.stringify(requestParams),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								document.getElementById("editForm").reset();
								document.getElementById("canelButton").click();
								window.wxc.xcConfirm("模型信息保存成功.",window.wxc.xcConfirm.typeEnum.info);
								searchData();
							}else{
								window.wxc.xcConfirm("模型信息保存失败.",window.wxc.xcConfirm.typeEnum.error);
							}
						},
						error:function(){
							window.wxc.xcConfirm("模型信息保存失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					});
				}
			}
			/*
				删除模型
			 */
			function deleteModel(){				
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					window.wxc.xcConfirm("删除模型会同时删除其包含的字段信息.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/basicDbTableAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"itemCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("数据模型删除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("数据模型删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("数据模型删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}else{
					window.wxc.xcConfirm("请先选择一个数据模型.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			/*
				修改模型状态
			 */
			function changeState(stateCode){				
				var checkedValue = getCheckedValue("checksRadio");			
				if(checkedValue!=null){
					var isUsing = "Y";
					if(stateCode==1){
						isUsing = "N";
					}
					window.wxc.xcConfirm("是否确认修改此模型的状态？.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/basicDbTableAction/updateState.ilf",
								async:false,
								type:"POST",
								data:"isUsing="+isUsing+"&checkedCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("模型状态修改成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("模型状态修改失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("模型状态修改失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});		
				}else{
					window.wxc.xcConfirm("请先选择一个模型.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;border:0px;">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>数据浏览 >> 数据模型维护
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-top:6px;margin-right:2px;" id="initSelection">
							<select class="w500" id="belongDbSelect" style="width:160px;border:solid 1px #A3D0E3;border-radius:6px;">
								<option value="-">- 选择所属数据源 -</option>
							</select>
						</div>
						<input type="text" placeholder="请输入数据模型名称" style="width:150px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;margin-left:5px;margin-right:5px;" id="dbTableKeyInput"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;' onclick="javascript:searchData();"></img>
						<a class="btn btn-success" href="#myModal" role="button" data-toggle="modal" style="cursor:pointer;margin-left:10px;" onclick="javascript:initIfChecked();">
							<i class="icon-plus-sign icon-white"></i>维护表信息
						</a>
						<a class="btn btn-warning"  style="cursor:pointer" onclick="javascript:changeState(1);">
							<i class="icon-zoom-out icon-white"></i>停用
						</a>
						<a class="btn btn-info"  style="cursor:pointer" onclick="javascript:changeState(2);">
							<i class="icon-film icon-white"></i>启用
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteModel();">
							<i class="icon-trash icon-white"></i>删除模型
						</a>	
					</div>
				</form>
			</div>
			<div class="panel-body h340" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th>数据源编号</th>
										<th>所属数据源</th>
										<th>所属专业</th>
										<th>模型标识</th>
										<th>模型名称</th>
										<th>是否在用</th>
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
								<div id="MAJOR_CONTAINER">
									<select class="w500" id="majorSelection">
										<option value="-">- 请指定专业 -</option>
									</select>
								</div>
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
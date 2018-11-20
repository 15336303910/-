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
    	<title>菜单管理</title>
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
			var zTreeObject = null;
			var AsyncSetting = {
				async:{
					enable:true,
					url:"${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf"
				},
				data:{
					simpleData:{
					   enable:true
					}
				},
	            check:{
					enable:false
	            }
	        };
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
				var pageNumbers = parseInt(tableHeight/25);
				$("#menuNames").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				/*初始化树*/
				$.post("${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf",{},function(result){
					zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
			    });	
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
						"mData":"ID",
						"sWidth":"10%"
					},{
						"mData":"MENU_NAME",
						"sWidth":"20%"
					},{
						"mData":"IS_LEAF",
						"sWidth":"14%"
					},{
						"mData":"IS_USING",
						"sWidth":"14%"
					},{
						"mData":"MENU_URL",
						"sWidth":"40%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["IS_USING"]=="Y"){
							$("td:eq(2)",nRow).html("<font color='green'>在用</font>");	
						}else{
							$("td:eq(2)",nRow).html("<font color='red'>停用</font>");
						}
						if(aData["IS_LEAF"]=="Y"){
							$("td:eq(3)",nRow).html("<font color='green'>是</font>");	
						}else{
							$("td:eq(3)",nRow).html("<font color='red'>否</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/sysmenuConfigAction/findMenus.ilf"
				});
				$("#dataTable tbody").click(function(event) {
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class") == "even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
			});
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
				var menuName = $("#menuNames").val();
				if(menuName!=""){
					conditions = [{
						name:"MENU_NAME",
						value:menuName
					}];	
				}else{
					conditions = [];
				}
				oTable.fnDraw();
			}
			function randomCarton(){
				var cartonArray = ["blind","clip","drop","explode","fold","puff","slide","scale","size","pulsate"];
				var randomValue = Math.random();
				var valueIndex = parseInt(randomValue*10);
				return cartonArray[valueIndex];				
			}
			function openEditWindow(){
				var isChecked = false;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							$("#hiddenCode").val(jobs[i].value); 
							isChecked = true;
							break;
						}
					}
				}
				if(isChecked){
					$.ajax({
						url:"${pageContext.request.contextPath}/sysmenuConfigAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"menuCode="+$("#hiddenCode").val(),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								$("#menuCodeHidden").val(textStatus["ID"]);
								$("#menuCnName").val(textStatus["MENU_NAME"]);
								$("#isLeafSelection").val(textStatus["IS_LEAF"]);
								$("#menuUrlInput").val(textStatus["MENU_URL"]);
								$("#isUsingSelection").val(textStatus["IS_USING"]);
								$("#parentMenuCode").val(textStatus["MENU_PARENT"]);
								$("#parentMenuName").val(textStatus["PARENT_NAME"]);
								$("#isExportSelection").val(textStatus["IS_EXPORT"]);
							}
						},
						error:function(){}
					});
				}				
				$("#brownModal").dialog({
					title:"编辑菜单信息",
					show:{
						effect:"slide",
						duration:700
					},
					hide:{
				        effect:"slide",
				        duration:700
					},
					width:600,
					height:430,
					autoOpen:true,
					closeOnEscape:true,
					draggable:false,
					resizable:false,
					modal:true,
					buttons:{
		  				"确定":function(){
		  					var editObject = new Object();
		  					/*菜单编号*/
		  					editObject["menuCode"] = $("#menuCodeHidden").val();
		  					/*菜单名称*/
		  					var menuName = $("#menuCnName").val();
		  					if(menuName==""){
		  						window.wxc.xcConfirm("菜单名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
		  						return;
		  					}else{
		  						editObject["menuName"] = menuName;
		  					}
		  					/*是否叶子*/
		  					var isLeaf = $("#isLeafSelection").val();
		  					editObject["isLeaf"] = isLeaf;
		  					if(isLeaf=="Y"){
		  						/*关联URL*/
		  						var menuURL = $("#menuUrlInput").val();
		  						if(menuURL==""){
		  							window.wxc.xcConfirm("叶子菜单必须指定关联的URL.",window.wxc.xcConfirm.typeEnum.info);
		  							return;
		  						}
		  						editObject["menuUrl"] = menuURL;
		  					}else{
		  						editObject["menuUrl"] = "";
		  					}
		  					/*父级菜单*/
	  						var parentCode = $("#parentMenuCode").val();
  							if(parentCode==""){
  								window.wxc.xcConfirm("请指定父级菜单.",window.wxc.xcConfirm.typeEnum.info);
  								return;
  							}
  							editObject["parentCode"] = parentCode;
  							var parentName = $("#parentMenuName").val();
  							editObject["parentName"] = parentName;
  							/*判断是否在用*/
  							editObject["isUsing"] = $("#isUsingSelection").val();
  							editObject["isExport"] = $("#isExportSelection").val();
  							window.wxc.xcConfirm("是否确认提交编辑？","custom",{
								title:"提示",
								btn:parseInt("0011",2),
								onOk:function(){
									saveAudits(editObject);
								}
							});
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
			function saveAudits(editObject){
				$.ajax({
					url:"${pageContext.request.contextPath}/sysmenuConfigAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#brownModal").dialog("close");
							window.wxc.xcConfirm("菜单配置成功.",window.wxc.xcConfirm.typeEnum.info);
							$("#menuCodeHidden").val("");
							document.getElementById("editForms").reset();
							searchData();
							$.post("${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf",{},function(result){
								zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
						    });
						}else{
							window.wxc.xcConfirm("菜单配置失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("菜单配置失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function assignedTeam(isInitSearch){
				$("#treeDiv").dialog({
					title:"菜单结构",
					show:{
						effect:randomCarton(),
						duration:1000
					},
					hide:{
				        effect:"slide",
				        duration:1000
					},
					width:335,
					height:410,
					autoOpen:true,
					closeOnEscape:true,
					draggable:true,
					resizable:true,
					modal:true,
					buttons:{
						"清空":function(){
							if(isInitSearch){
								$("#menuNames").val("");
							}else{
								$("#parentMenuCode").val("");
								$("#parentMenuName").val("");	
							}
							$(this).dialog("close");
						},
		  				"确定":function(){
		  					var TreeObject = $.fn.zTree.getZTreeObj("treeDemo");
							var SelectedObjects = TreeObject.getSelectedNodes();
							if(SelectedObjects.length>0){
								var nodeObject = SelectedObjects[0];
								if(isInitSearch){
									$("#menuNames").val(nodeObject.name);
								}else{
									$("#parentMenuCode").val(nodeObject.id);
									$("#parentMenuName").val(nodeObject.name);
								}
								$(this).dialog("close");
							}else{
								window.wxc.xcConfirm("请选择一个父级菜单.",window.wxc.xcConfirm.typeEnum.info);
							}
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
			function deleteMenu(){
				var isChecked = false;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							$("#hiddenCode").val(jobs[i].value); 
							isChecked = true;
							break;
						}
					}
				}
				if(isChecked){
					window.wxc.xcConfirm("删除菜单时，其下属的菜单也将会被删除..","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/sysmenuConfigAction/deleteMenu.ilf",
								async:false,
								type:"POST",
								data:"menuCode="+$("#hiddenCode").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("菜单删除成功.",window.wxc.xcConfirm.typeEnum.info);
										$.post("${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf",{},function(result){
											zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
									    });	
										$("#hiddenCode").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("菜单删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("菜单删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个菜单.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入菜单名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="menuNames"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>编辑菜单
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteMenu();">
							<i class="icon-trash icon-white"></i>删除菜单
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
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">菜单名称</th>
										<th style="text-align:center;">是否在用</th>
										<th style="text-align:center;">是否关联界面</th>
										<th style="text-align:center;">菜单URL</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--窗口表单：开始-->
		<div id="brownModal" style="display:none;height:295px;">
			<div style="height:330px;border:solid 1px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:550px;font-size:12px;margin-top:15px;">
							<tr height="60">
								<td style="text-align:right;width:100px">菜单名称：</td>
								<td colspan="3">
									<input type="hidden" id="menuCodeHidden"></input>
									<input type="text" placeholder="请输入菜单名称" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;" id="menuCnName"></input>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">是否URL：</td>
								<td colspan="3">
									<select class="selections" id="isLeafSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:32px;">
									    <option value="N">否</option>
									    <option value="Y">是</option>
									</select>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">关联URL：</td>
								<td colspan="3">
									<input type="text" id="menuUrlInput" placeholder="请输入关联的URL" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;"></input>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">是否在用：</td>
								<td>
									<select class="selections" id="isUsingSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:170px;height:32px;">
									    <option value="Y">在用</option>
									    <option value="N">停用</option>
									</select>
								</td>
								<td style="text-align:right;width:100px">是否外部：</td>
								<td>
									<select class="selections" id="isExportSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:150px;height:32px;">
									    <option value="N">否</option>
									    <option value="Y">是</option>
									</select>
								</td>
							</tr>						
							<tr height="60">
								<td style="text-align:right;width:100px">父级菜单：</td>
								<td colspan="3">
									<input id="parentMenuCode" type="hidden"></input>
									<input id="parentMenuName" type="text" placeholder="点击选择父级菜单" readonly onclick="javascript:assignedTeam(false);" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;"></input>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<!--窗口表单：结束-->
		<div style="display:none;" id="treeDiv">
			<div style="width:310px;height:315px;overflow:auto;border:solid 2px #DEEFF8;border-radius:10px">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>
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
    	<title>角色管理</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			/*----------------------------------------------------------------------------------------*/
			var zTreeObject = null;
			var roleSetTreeObject = null;
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
					enable:true
	            }
	        };
			var roleSetTreeSetting = {
				async:{
					enable:true,
					url:"${pageContext.request.contextPath}/roleSetManageAction/findRoleSetTree.ilf"
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
				$("#roleNameInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				/*初始化菜单树*/
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
						"mData":"ROLE_NAME",
						"sWidth":"74%"
					},{
						"mData":"IS_USING",
						"sWidth":"14%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["IS_USING"]=="Y"){
							$("td:eq(2)",nRow).html("<font color='green'>是</font>");	
						}else{
							$("td:eq(2)",nRow).html("<font color='red'>否</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/roleManageAction/findRoles.ilf"
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
				var roleName = $("#roleNameInput").val();
				if(roleName!=""){
					conditions = [{
						name:"ROLE_NAME",
						value:roleName
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
					/*渲染基本信息*/
					$.ajax({
						url:"${pageContext.request.contextPath}/roleManageAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"roleCode="+$("#hiddenCode").val(),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								$("#roleCodeHidden").val(textStatus["ID"]);
								$("#roleCnName").val(textStatus["ROLE_NAME"]);
								$("#isUsingSelection").val(textStatus["IS_USING"]);
							}
						},
						error:function(){}
					});
					/*初始化菜单树*/
					$.post("${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf?roleCode="+$("#hiddenCode").val(),{},function(result){
						zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
				    });
				}				
				$("#brownModal").dialog({
					title:"配置角色权限",
					show:{
						effect:"slide",
						duration:1000
					},
					hide:{
				        effect:"slide",
				        duration:1000
					},
					width:620,
					height:415,
					autoOpen:true,
					closeOnEscape:true,
					draggable:true,
					resizable:true,
					modal:true,
					buttons:{
		  				"确定":function(){
		  					/*角色名称*/
		  					var roleNames = $("#roleCnName").val();
		  					if(roleNames==""){
		  						window.wxc.xcConfirm("角色名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
		  					/*是否在用*/
		  					var isUsings = $("#isUsingSelection").val();
		  					/*菜单配置*/
		  					var treeObject = $.fn.zTree.getZTreeObj("treeDemo");
							var checkedNodes = treeObject.getCheckedNodes(true);
							var checkedNodesCode = "";
							if(checkedNodes.length>0){
								for(var i=0;i<checkedNodes.length;i++){
									var thisNode = checkedNodes[i];
									if(checkedNodesCode==""){
										checkedNodesCode = thisNode.id;
									}else{
										checkedNodesCode+=","+thisNode.id;
									}
								}								
							}else{
								window.wxc.xcConfirm("请至少选择一项菜单.",window.wxc.xcConfirm.typeEnum.info);
								return;
							}
							var finalObject = {
								roleCode:$("#roleCodeHidden").val(),
								roleNames:roleNames,
								isUsings:isUsings,
								checkedNodes:checkedNodesCode
							};
							window.wxc.xcConfirm("是否确认提交编辑？","custom",{
								title:"提示",
								btn:parseInt("0011",2),
								onOk:function(){
									saveAudits(finalObject);	
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
					url:"${pageContext.request.contextPath}/roleManageAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#brownModal").dialog("close");
							window.wxc.xcConfirm("角色配置成功.",window.wxc.xcConfirm.typeEnum.info);
							document.getElementById("editForms").reset();
							searchData();
							$.post("${pageContext.request.contextPath}/sysmenuConfigAction/findMenuTreeUnChecks.ilf",{},function(result){
								zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
						    });
						}else{
							window.wxc.xcConfirm("角色配置失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("角色配置失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function deleteRole(){
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
					window.wxc.xcConfirm("是否确定删除角色？","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/roleManageAction/deleteRole.ilf",
								async:false,
								type:"POST",
								data:"roleCode="+$("#hiddenCode").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("角色移除成功.",window.wxc.xcConfirm.typeEnum.info);
										$("#hiddenCode").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("角色移除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("角色移除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个角色.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入角色名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="roleNameInput"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<%--<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>编辑角色
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteRole();">
							<i class="icon-trash icon-white"></i>删除角色
						</a>--%>	
					</div>
				</form>
			</div>
			<div class="panel-body">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">角色名称</th>
										<th style="text-align:center;">在用/停用</th>
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
			<div style="height:310px;border:solid 1px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:600px;font-size:12px;margin-top:15px;">
							<tr height="40">
								<td style="text-align:right;width:70px;">角色名称：</td>
								<td colspan="3">
									<input type="hidden" id="roleCodeHidden"></input>
									<input type="text" placeholder="请输入角色名称" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:480px;height:33px;font-size:12px;margin-top:6px;" id="roleCnName"></input>
								</td>
							</tr>
							<tr height="40">
								<td style="text-align:right;width:70px">是否在用：</td>
								<td colspan="3">
									<select class="selections" id="isUsingSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:480px;height:32px;">
									    <option value="Y">在用</option>
									    <option value="N">停用</option>
									</select>
								</td>
							</tr>
							<tr height="215">
								<td style="text-align:right;width:70px">划分菜单：</td>
								<td colspan="3">
									<div style="margin-left:5px;width:480px;height:180px;margin-top:-20px;border:solid 1px #54A8E1;border-radius:7px;overflow:auto;">
										<ul id="treeDemo" class="ztree"></ul>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<div id="roleSetTreeDiv" style="display:none;">
			<div style="margin-left:5px;width:250px;height:270px;border:solid 1px #54A8E1;border-radius:7px;overflow:auto;">
				<ul id="roleSetTree" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>
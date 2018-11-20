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
    	<title>部门管理</title>
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
					url:"${pageContext.request.contextPath}/departConfigAction/findTreeData.ilf"
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
				$("#departNameKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				/*初始化公司树*/
				$.post("${pageContext.request.contextPath}/departConfigAction/findTreeData.ilf",{},function(result){
					zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
			    });	
				/*初始化部门列表*/
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
						"mData":"ORGANIZATION_NAME",
						"sWidth":"20%"
					},{
						"mData":"ORGANIZATION_LEVEL",
						"sWidth":"13%"
					},{
						"mData":"ORGANIZATION_DESC",
						"sWidth":"56%"
					},{
						"mData":"PARENT_CODE",
						"sWidth":"16.2%",
						"bVisible":false,
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/departConfigAction/findItems.ilf"
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
				var departName = $("#departNameKey").val();
				if(departName==""){
					conditions = [];
				}else{
					conditions = [{
						name:"ORGANIZATION_NAME",
						value:departName
					}];	
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
				$("#departCodeHidden").val("");
				$("#departLevelSelection").attr("disabled",false);
				$("#parentDepartName").attr("disabled",false);			
				document.getElementById("editForms").reset();
				var isChecked = false;
				var checkedCount = 0;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							$("#departCodeHidden").val(jobs[i].value); 
							isChecked = true;
							checkedCount++;
						}
					}
				}
				if(checkedCount>1){
					window.wxc.xcConfirm("请选择一个部门.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(isChecked){
					$.ajax({
						url:"${pageContext.request.contextPath}/departConfigAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"departId="+$("#departCodeHidden").val(),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								$("#departCodeHidden").val(textStatus["ID"]);
								$("#departName").val(textStatus["ORGANIZATION_NAME"]);
								$("#departLevelSelection").val(textStatus["ORGANIZATION_LEVEL"]);
								$("#departLevelSelection").attr("disabled",true);
								$("#departDescribe").val(textStatus["ORGANIZATION_DESC"]);
								$("#parentDepartCode").val(textStatus["PARENT_CODE"]);
								$("#parentDepartName").val(textStatus["PARENT_NAME"]);
								$("#parentDepartName").attr("disabled",true);
							}
						},
						error:function(){}
					});
				}				
				$("#brownModal").dialog({
					title:"编辑组织信息",
					show:{
						effect:"slide",
						duration:1000
					},
					hide:{
				        effect:"slide",
				        duration:1000
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
		  					/*部门编号*/
		  					editObject["departCode"] = $("#departCodeHidden").val();
		  					/*部门名称*/
		  					var departName = $("#departName").val();
		  					if(departName==""){
		  						window.wxc.xcConfirm("部门名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
		  						return;
		  					}else{
		  						editObject["departName"] = departName;
		  					}
		  					/*部门级别*/
		  					var departLevel = $("#departLevelSelection").val();
		  					editObject["departLevel"] = departLevel;
		  					if(departLevel=="部门"){
		  						var parentDepart = $("#parentDepartCode").val();
		  						if(parentDepart==""){
		  							window.wxc.xcConfirm("部门必须指定归属某子公司或总公司.",window.wxc.xcConfirm.typeEnum.info);
		  							return;
		  						}
		  						editObject["parentDepart"] = parentDepart;
		  					}else if(departLevel=="分公司"){
		  						var parentDepart = $("#parentDepartCode").val();
		  						if(parentDepart==""){
		  							window.wxc.xcConfirm("分公司必须指定归属某个总公司.",window.wxc.xcConfirm.typeEnum.info);
		  							return;
		  						}
		  						editObject["parentDepart"] = parentDepart;
		  					}else if(departLevel=="总公司"){
		  						editObject["parentDepart"] = "0";
		  					}
		  					/*部门描述*/
	  						var departDescribe = $("#departDescribe").val();
  							if(departDescribe==""){
  								window.wxc.xcConfirm("部门描述不能为空.",window.wxc.xcConfirm.typeEnum.info);
  								return;
  							}
  							editObject["departDescribe"] = departDescribe;
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
					url:"${pageContext.request.contextPath}/departConfigAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#brownModal").dialog("close");
							window.wxc.xcConfirm("部门配置成功.",window.wxc.xcConfirm.typeEnum.info);
							$("#departCodeHidden").val("");
							document.getElementById("editForms").reset();
							searchData();
							$.post("${pageContext.request.contextPath}/departConfigAction/findTreeData.ilf",{},function(result){
								zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
						    });
						}else{
							window.wxc.xcConfirm("部门配置失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("部门配置失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function assignedTeam(){
				$("#treeDiv").dialog({
					title:"组织结构",
					show:{
						effect:"slide",
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
							$("#parentDepartCode").val("");
							$("#parentDepartName").val("");
							$(this).dialog("close");
						},
		  				"确定":function(){
		  					var TreeObject = $.fn.zTree.getZTreeObj("treeDemo");
							var SelectedObjects = TreeObject.getSelectedNodes();
							if(SelectedObjects.length>0){
								var nodeObject = SelectedObjects[0];
								$("#parentDepartCode").val(nodeObject.id);
								$("#parentDepartName").val(nodeObject.name);
								$(this).dialog("close");
							}else{
								window.wxc.xcConfirm("请选择一项.",window.wxc.xcConfirm.typeEnum.info);
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
				var checkedValue = null;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							checkedValue = jobs[i].value; 
							isChecked = true;
							break;
						}
					}
				}
				if(isChecked){
					window.wxc.xcConfirm("删除组织信息时，会关联删除其包含的部门和员工用户信息..","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/departConfigAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"departCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("组织删除成功.",window.wxc.xcConfirm.typeEnum.info);
										$.post("${pageContext.request.contextPath}/departConfigAction/findTreeData.ilf",{},function(result){
											zTreeObject = $.fn.zTree.init($("#treeDemo"),AsyncSetting,result);
									    });	
										searchData();
									}else{
										window.wxc.xcConfirm("组织删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("组织删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个部门.",window.wxc.xcConfirm.typeEnum.info);
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
						<input type="text" placeholder="请输入组织名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="departNameKey"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<%--<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>编辑组织信息
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteMenu();">
							<i class="icon-trash icon-white"></i>删除组织
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
										<th style="text-align:center;">部门名称</th>
										<th style="text-align:center;">部门级别</th>
										<th style="text-align:center;">部门描述</th>
										<th style="text-align:center;">上级组织</th>
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
								<td style="text-align:right;width:100px">组织名称：</td>
								<td colspan="3">
									<input type="hidden" id="departCodeHidden"></input>
									<input type="text" placeholder="请输入组织名称" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;" id="departName"></input>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">组织级别：</td>
								<td colspan="3">
									<select class="selections" id="departLevelSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:32px;">
									    <option value="总公司">总公司</option>
									    <option value="分公司">分公司</option>
									    <option value="部门">部门</option>
									</select>
								</td>
							</tr>
							<tr height="120">
								<td style="text-align:right;width:100px">组织描述：</td>
								<td colspan="3">
									<textarea id="departDescribe" style="resize:none;width:96.3%;height:100px;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;"></textarea>
								</td>
							</tr>					
							<tr height="60">
								<td style="text-align:right;width:100px">上级组织：</td>
								<td colspan="3">
									<input id="parentDepartCode" type="hidden"></input>
									<input id="parentDepartName" type="text" placeholder="点击选择上级组织" readonly onclick="javascript:assignedTeam();" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;"></input>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<!--窗口表单：结束-->
		<div style="display:none;" id="treeDiv">
			<div style="width:310px;height:315px;border:solid 2px #DEEFF8;border-radius:10px">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>
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
    	<title>账户管理</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">	
			var dataTreeSetting = {
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
				$("#userAccountInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				/*初始化地域选取组件*/
				$("#relateAreaInputs").click(function (e) {
					SelCity(this,e);
				    console.log("inout",$(this).val(),new Date())
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
						"sWidth":"8%"
					},{
						"mData":"USER_NAME",
						"sWidth":"14%"
					},{
						"mData":"IS_LOCKED",
						"sWidth":"14%"
					},{
						"mData":"EMPLOYEE_NAME",
						"sWidth":"14%"
					},{
						"mData":"EMPLOYEE_COMPANY",
						"sWidth":"31%"
					},{
						"mData":"EMPLOYEE_PHONE",
						"sWidth":"17%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["IS_LOCKED"]=="N"){
							$("td:eq(2)",nRow).html("<font color='green'>正常</font>");	
						}else{
							$("td:eq(2)",nRow).html("<font color='red'>锁定</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/userManageAction/findUseraccounts.ilf"
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
				var roleName = $("#userAccountInput").val();
				if(roleName!=""){
					conditions = [{
						name:"USER_NAME",
						value:roleName
					}];	
				}else{
					conditions = [];
				}
				oTable.fnDraw();
			}
			function initUserAccount(){
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
					window.wxc.xcConfirm("是否确定要解锁此账户？","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/userManageAction/lockUser.ilf",
								async:false,
								type:"POST",
								data:"userCode="+$("#hiddenCode").val()+"&userState=N",
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("账户已解锁.",window.wxc.xcConfirm.typeEnum.info);
										$("#hiddenCode").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("锁定解锁失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("解锁账户失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});	
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个账号.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function lockUserAccount(){
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
					window.wxc.xcConfirm("是否确定要锁定此账户？","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/userManageAction/lockUser.ilf",
								async:false,
								type:"POST",
								data:"userCode="+$("#hiddenCode").val()+"&userState=Y",
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("账户已锁定.",window.wxc.xcConfirm.typeEnum.info);
										$("#hiddenCode").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("锁定账户失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("锁定账户失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});	
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个账号.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function deleteUserAccount(){
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
					window.wxc.xcConfirm("是否确定删除账户？注意，此操作不可回滚.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/userManageAction/deleteUserAccount.ilf",
								async:false,
								type:"POST",
								data:"userCode="+$("#hiddenCode").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("账户移除成功.",window.wxc.xcConfirm.typeEnum.info);
										$("#hiddenCode").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("账户移除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("账户移除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个账户.",window.wxc.xcConfirm.typeEnum.info);
				}
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
						url:"${pageContext.request.contextPath}/userManageAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"userCode="+$("#hiddenCode").val(),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								$("#userCodeHidden").val(textStatus["ID"]);
		  						$("#userAccountInputs").val(textStatus["USER_NAME"]);
		  						$("#relateAreaInputs").val(textStatus["BELONG_AREA"]);
		  						if(textStatus["START_DATE"]!=null && textStatus["START_DATE"]!=""){
		  							var timeObject = textStatus["START_DATE"];
		  							var thisDate = new Date();
		  							thisDate.setTime(timeObject["time"]);
		  							$("#startDatetime").val(thisDate.getFullYear()+"-"+(thisDate.getMonth()+1)+"-"+thisDate.getDate());	
		  						}
		  						if(textStatus["LIMIT_DATE"]!=null && textStatus["LIMIT_DATE"]!=""){
		  							var timeObject = textStatus["LIMIT_DATE"];
		  							var thisDate = new Date();
		  							thisDate.setTime(timeObject["time"]);
		  							$("#limitDatetime").val(thisDate.getFullYear()+"-"+(thisDate.getMonth()+1)+"-"+thisDate.getDate());	
		  						}
		  						$("#isLockedSelection").val(textStatus["IS_LOCKED"]);		  						
		  						$("#isInviteableChoose").val(textStatus["IS_HAVE_INVITE_ABLE"]);
		  						$("#employeeCnName").val(textStatus["EMPLOYEE_NAME"]);
		  						$("#employeeSex").val(textStatus["EMPLOYEE_SEX"]);
		  						$("#companyCodeChoose").val(textStatus["EMPLOYEE_COMPANY_CODE"]);		  						
		  						$("#companyNameChoose").val(textStatus["EMPLOYEE_COMPANY"]);
		  						$("#departCodeChoose").val(textStatus["EMPLOYEE_DEPART_CODE"]);
		  						$("#departNameChoose").val(textStatus["EMPLOYEE_DEPARTNAME"]);
		  						$("#employeeIdInput").val(textStatus["EMPLOYEE_ID_CARD"]);
		  						$("#employeePhoneInput").val(textStatus["EMPLOYEE_PHONE"]);
		  						$("#isLengthyUsing").val(textStatus["IS_LENGTHY_USE"]);
		  						$("#roleCodeChoose").val(textStatus["ROLE_CODE"]);
		  						$("#roleNameChoose").val(textStatus["ROLE_NAME"]);
							}
						},
						error:function(){}
					});
				}				
				$("#brownModal").dialog({
					title:"配置账号信息  ",
					show:{
						effect:"slide",
						duration:1000
					},
					hide:{
				        effect:"slide",
				        duration:1000
					},
					width:670,
					height:450,
					autoOpen:true,
					closeOnEscape:true,
					draggable:true,
					resizable:true,
					modal:true,
					buttons:{
		  				"确定":function(){
		  					/*封装数据*/
		  					var editObject = {
		  						userCode:$("#userCodeHidden").val(),
		  						userAccount:$("#userAccountInputs").val(),
		  						belongArea:$("#relateAreaInputs").val(),
		  						beginDate:$("#startDatetime").val(),
		  						limitDate:$("#limitDatetime").val(),
		  						isLocked:$("#isLockedSelection").val(),
		  						isInvited:$("#isInviteableChoose").val(),
		  						employeeName:$("#employeeCnName").val(),
		  						employeeSex:$("#employeeSex").val(),
		  						companyCode:$("#companyCodeChoose").val(),
		  						companyName:$("#companyNameChoose").val(),
		  						departCode:$("#departCodeChoose").val(),
		  						departName:$("#departNameChoose").val(),
		  						employeeId:$("#employeeIdInput").val(),
		  						employeePhone:$("#employeePhoneInput").val(),
		  						isLengthyUse:$("#isLengthyUsing").val(),
		  						userRoleCode:$("#roleCodeChoose").val(),
		  						userRoleName:$("#roleNameChoose").val()
		  					};
		  					/*执行验证*/
		  					if(editObject["userAccount"]==""){
		  						window.wxc.xcConfirm("用户账号不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
		  					if(editObject["belongArea"]==""){
		  						window.wxc.xcConfirm("归属地域不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
		  					if(editObject["isLengthyUse"]=="N"){
		  						if(editObject["beginDate"]=="" || editObject["limitDate"]==""){
		  							window.wxc.xcConfirm("非长期有效账户的开始日期和结束日期不能为空.",window.wxc.xcConfirm.typeEnum.info);
									return;	
		  						}
		  					}
		  					if(editObject["employeeName"]==""){
		  						window.wxc.xcConfirm("人员姓名不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
		  					if(editObject["employeePhone"]==""){
		  						window.wxc.xcConfirm("联系电话不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
		  					if(editObject["userRoleName"]==""){
		  						window.wxc.xcConfirm("用户角色不能为空.",window.wxc.xcConfirm.typeEnum.info);
								return;
		  					}
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
					url:"${pageContext.request.contextPath}/userManageAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#brownModal").dialog("close");
							window.wxc.xcConfirm("账户配置成功.",window.wxc.xcConfirm.typeEnum.info);
							document.getElementById("editForms").reset();
							searchData();
						}else{
							window.wxc.xcConfirm("账户配置失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("账户配置失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			var dataTreeObject = null;
			function chooseDataTree(typeCode){
				/*转换类型*/
				var typeName = "";
				if(parseInt(typeCode)==1){
					typeName = "Company";
				}else if(parseInt(typeCode)==2){
					typeName = "Department";
				}else if(parseInt(typeCode)==3){
					typeName = "Role";
				}
				/*执行验证*/
				if(typeName=="Department"){
					var companyName = $("#companyNameChoose").val();
					if(companyName==""){
						window.wxc.xcConfirm("请先选择所属公司.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}
				}
				/*初始化*/
				$("#hiddenOfEditType").val(typeName);
				if(typeName=="Company"){
					$.post("${pageContext.request.contextPath}/departConfigAction/findCompanies.ilf",{},function(result){
						dataTreeObject = $.fn.zTree.init($("#dataTree"),dataTreeSetting,result);
				    });			
				}else if(typeName=="Department"){
					var companyCode = $("#companyCodeChoose").val();
					$.post("${pageContext.request.contextPath}/departConfigAction/findDeparts.ilf?companyCode="+companyCode,{},function(result){
						dataTreeObject = $.fn.zTree.init($("#dataTree"),dataTreeSetting,result);
				    });	
				}else if(typeName=="Role"){
					var belongArea = $("#relateAreaInputs").val();
					$.post("${pageContext.request.contextPath}/roleManageAction/findRolesTree.ilf",{areaName:belongArea},function(results){
						dataTreeObject = $.fn.zTree.init($("#dataTree"),dataTreeSetting,results);
				    });	
				}
				/*弹出窗口*/
				$("#dataTreeDiv").dialog({
					title:"选择",
					show:{
						effect:randomCarton(),
						duration:1000
					},
					hide:{
				        effect:"slide",
				        duration:1000
					},
					width:290,
					height:365,
					autoOpen:true,
					closeOnEscape:true,
					draggable:true,
					resizable:true,
					modal:true,
					buttons:{
						"清空":function(){
							var currentEditType = $("#hiddenOfEditType").val();
							if(currentEditType=="Company"){
								$("#companyCodeChoose").val("");
								$("#companyNameChoose").val("");
								$("#departCodeChoose").val("");
								$("#departNameChoose").val("");
							}else if(currentEditType=="Department"){
								$("#departCodeChoose").val("");
								$("#departNameChoose").val("");
							}else if(currentEditType=="Role"){
								$("#roleCodeChoose").val("");
								$("#roleNameChoose").val("");
							}					
							$(this).dialog("close");
						},
		  				"确定":function(){
		  					var TreeObject = $.fn.zTree.getZTreeObj("dataTree");
							var SelectedObjects = TreeObject.getSelectedNodes();
							var selectedCode = "";
							var selectedName = "";
							if(SelectedObjects.length>0){
								var nodeObject = SelectedObjects[0];
								selectedCode = nodeObject.id;
								selectedName = nodeObject.name;
							}else{
								window.wxc.xcConfirm("请选择一条数据.",window.wxc.xcConfirm.typeEnum.info);
								return;
							}							
							var currentEditType = $("#hiddenOfEditType").val();
							if(currentEditType=="Company"){
								$("#companyCodeChoose").val(selectedCode);
								$("#companyNameChoose").val(selectedName);
								$(this).dialog("close");
							}else if(currentEditType=="Department"){
								$("#departCodeChoose").val(selectedCode);
								$("#departNameChoose").val(selectedName);
								$(this).dialog("close");
							}else if(currentEditType=="Role"){
								if(parseInt(selectedCode)==0){
									window.wxc.xcConfirm("请选择一条角色.",window.wxc.xcConfirm.typeEnum.info);
								}else{
									$("#roleCodeChoose").val(selectedCode);
									$("#roleNameChoose").val(selectedName);
									$(this).dialog("close");
								}
							}
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
			function flushRelate(){
				$("#departCodeChoose").val("");
				$("#departNameChoose").val("");
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>系统管理 >> 用户管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入用户账号" style="width:150px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="userAccountInput"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<%--<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:lockUserAccount();">
							<i class="icon-lock icon-white"></i>锁定
						</a>
						<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:initUserAccount();">
							<i class="icon-refresh icon-white"></i>解锁
						</a>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>编辑账号
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteUserAccount();">
							<i class="icon-trash icon-white"></i>删除账号
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
										<th style="text-align:center;">用户账号</th>
										<th style="text-align:center;">锁定/正常</th>
										<th style="text-align:center;">人员姓名</th>
										<th style="text-align:center;">所属公司</th>
										<th style="text-align:center;">联系电话</th>
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
		<div id="brownModal" style="display:none;">
			<div style="height:345px;border:solid 1px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:600px;font-size:12px;margin-top:5px;">
							<tr height="35">
								<td style="text-align:right;width:70px;">用户账号<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="hidden" id="userCodeHidden"></input>
									<input type="text" placeholder="请输入账号" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="userAccountInputs"></input>
								</td>
								<td style="text-align:right;width:70px">归属地域<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="text" placeholder="请点击选取用户地域" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;margin-right:10px;" readonly id="relateAreaInputs"></input>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px">开始日期<font color="green" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input id="startDatetime" type="text" placeholder="请选择开始日期" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;margin-right:10px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
								</td>
								<td style="text-align:right;width:70px;">结束日期<font color="green" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input id="limitDatetime" type="text" placeholder="请选择结束日期" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;margin-right:10px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px">是否锁定<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<select class="selections" id="isLockedSelection" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:32px;">
									    <option value="N">正常</option>
									    <option value="Y">锁定</option>
									</select>
								</td>
								<td style="text-align:right;width:70px;">邀请码权<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<select class="selections" id="isInviteableChoose" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:32px;">
									    <option value="Y">有</option>
									    <option value="N">无</option>
									</select>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px;">员工姓名<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="text" placeholder="请输入员工姓名" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="employeeCnName"></input>
								</td>
								<td style="text-align:right;width:70px;">员工性别<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<select class="selections" id="employeeSex" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:32px;">
									    <option value="男">男</option>
									    <option value="女">女</option>
									</select>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px;">所属公司<font color="green" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="hidden" id="companyCodeChoose"></input>
									<input type="text" onclick="javascript:chooseDataTree(1);" onchange="javascript:flushRelate();" placeholder="请点击选择公司" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="companyNameChoose" readonly></input>
								</td>
								<td style="text-align:right;width:70px;">所属部门<font color="green" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="hidden" id="departCodeChoose"></input>
									<input type="text" onclick="javascript:chooseDataTree(2);" placeholder="请点击选择部门" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="departNameChoose" readonly></input>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px;">长期有效<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<select class="selections" id="isLengthyUsing" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:32px;">
									    <option value="Y">是</option>
									    <option value="N">否</option>
									</select>
								</td>
								<td style="text-align:right;width:70px;">联系电话<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="text" placeholder="请输入员工电话" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="employeePhoneInput"></input>
								</td>
							</tr>
							<tr height="35">
								<td style="text-align:right;width:70px;">员工编号<font color="green" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="text" placeholder="请输入员工编号" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;" id="employeeIdInput"></input>
								</td>
								<td style="text-align:right;width:70px">关联角色<font color="red" size="3" style="margin-left:3px;">*</font></td>
								<td>
									<input type="hidden" id="roleCodeChoose"></input>
									<input type="text" onclick="javascript:chooseDataTree(3);" placeholder="请点击选取关联角色" style="cursor:pointer;border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:190px;height:33px;font-size:12px;margin-top:6px;margin-right:10px;" readonly id="roleNameChoose"></input>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<div id="dataTreeDiv" style="display:none;">
			<div style="margin-left:5px;width:250px;height:270px;border:solid 1px #54A8E1;border-radius:7px;overflow:auto;">
				<ul id="dataTree" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>
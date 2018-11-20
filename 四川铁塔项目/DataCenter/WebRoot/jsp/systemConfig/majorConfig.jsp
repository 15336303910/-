<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>专业管理</title>
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
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
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
					"sScrollY":"350px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"10%"
					},{
						"mData":"MAJOR_CODE",
						"sWidth":"15%"
					},{
						"mData":"MAJOR_NAME",
						"sWidth":"15%"
					},{
						"mData":"MAJOR_DESCRIBE",
						"sWidth":"70%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":12,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio'></input></center>");				
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/majorConfigAction/findItems.ilf"
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
				var theMajorName = $("#majorNames").val();
				if(theMajorName!=""){
					conditions = [{
						name:"MAJOR_NAME",
						value:theMajorName
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
				$("#majorIdHidden").val("");
				document.getElementById("editForms").reset();
				var jobs = document.getElementsByName("checksRadio");
				var checkedCount = 0;
				var checkedValue = null;
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							checkedCount++;
							checkedValue = jobs[i].value;
						}
					}				
				}
				if(checkedCount>1){
					window.wxc.xcConfirm("请选择一个项目.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else if(checkedCount==1){
					$("#majorIdHidden").val(checkedValue);
				}
				if(checkedCount==1){
					$.ajax({
						url:"${pageContext.request.contextPath}/majorConfigAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"projectId="+$("#majorIdHidden").val(),
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["success"]){
								$("#majorIdHidden").val(textStatus["ID"]);
								$("#majorCode").val(textStatus["MAJOR_CODE"]);
								$("#majorName").val(textStatus["MAJOR_NAME"]);
								$("#majorDescribe").val(textStatus["MAJOR_DESCRIBE"]);
							}
						},
						error:function(){}
					});
				}				
				$("#brownModal").dialog({
					title:"编辑专业信息",
					show:{
						effect:randomCarton(),
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
		  					editObject["ID"] = $("#majorIdHidden").val();
		  					/*专业编号*/
		  					editObject["majorCode"] = $("#majorCode").val();
		  					if(editObject["majorCode"]==""){
		  						window.wxc.xcConfirm("专业编号不能为空.",window.wxc.xcConfirm.typeEnum.info);
		  						return;
		  					}
		  					/*专业名称*/
		  					editObject["majorName"] = $("#majorName").val();
		  					if(editObject["majorName"]==""){
		  						window.wxc.xcConfirm("专业名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
		  						return;
		  					}
		  					/*专业描述*/
		  					editObject["majorDescribe"] = $("#majorDescribe").val();
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
					url:"${pageContext.request.contextPath}/majorConfigAction/saveAudit.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#brownModal").dialog("close");
							window.wxc.xcConfirm("专业编辑成功.",window.wxc.xcConfirm.typeEnum.info);
							$("#majorIdHidden").val("");
							document.getElementById("editForms").reset();
							searchData();
						}else{
							window.wxc.xcConfirm("专业编辑失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					},
					error:function(){
						window.wxc.xcConfirm("专业编辑失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function deleteItem(){
				var jobs = document.getElementsByName("checksRadio");
				var checkedCount = 0;
				var checkedValue = null;
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							checkedCount++;
							checkedValue = jobs[i].value;
						}
					}				
				}
				if(checkedCount>1){
					window.wxc.xcConfirm("请选择一个专业.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else if(checkedCount==1){
					$("#majorIdHidden").val(checkedValue);
				}
				if(checkedCount==1){
					window.wxc.xcConfirm("是否确定要删除专业..","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/majorConfigAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"commandCode="+$("#majorIdHidden").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("专业删除成功.",window.wxc.xcConfirm.typeEnum.info);
										$("#majorIdHidden").val("");
										searchData();
									}else{
										window.wxc.xcConfirm("专业删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("专业删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一个专业.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;">
		<div class="panel panel-default" style="margin-top:-20px;height:470px;">
			<div class="panel-heading">
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入专业名称" style="width:200px;height:29px;font-size:12px;" id="majorNames"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>编辑专业
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteItem();">
							<i class="icon-trash icon-white"></i>删除专业
						</a>	
					</div>
				</form>
			</div>
			<div class="panel-body h340">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">专业编号</th>
										<th style="text-align:center;">专业名称</th>
										<th style="text-align:center;">专业描述</th>
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
								<td style="text-align:right;width:100px">专业编号：</td>
								<td colspan="3">
									<input type="hidden" id="majorIdHidden"></input>
									<input type="text" placeholder="请输入专业编号" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;" id="majorCode"></input>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">专业名称：</td>
								<td colspan="3">
									<input type="text" placeholder="请输入专业名称" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;" id="majorName"></input>
								</td>
							</tr>
							<tr height="60">
								<td style="text-align:right;width:100px">专业描述：</td>
								<td colspan="3">
									<textarea id="majorDescribe" style="border-radius:7px;border:solid 1px #54A8E1;margin-left:5px;width:97%;height:180px;"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
	</body>
</html>
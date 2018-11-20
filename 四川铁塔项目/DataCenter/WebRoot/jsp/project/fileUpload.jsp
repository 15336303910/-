<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
	String projectCode = request.getParameter("projectCode");
	System.out.println("=>"+projectCode);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>工程资料管理</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
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
			var oTable = null;
			var conditions = [];
			var acheMaps = null;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/26);
				conditions = [{
					name:"BELONG_PROJECT",
					value:$("#hiddenOfProjectCode").val()
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
					"sScrollY":tableHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"10%"
					},{
						"mData":"FILE_NAME",
						"sWidth":"48%"
					},{
						"mData":"UPLOAD_DATE",
						"sWidth":"20%"
					},{
						"mData":"EMPLOYEE_NAME",
						"sWidth":"20%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheMaps==null){
							acheMaps = new HashMap();
						}
						acheMaps.put(aData["ID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:checkedOne(this.checked,this.name,this.value);'></input></center>");
						if(aData["UPLOAD_DATE"]!=null){
							var longs = aData["UPLOAD_DATE"]["time"];
							var date = new Date();
							date.setTime(longs);
							$("td:eq(2)",nRow).html(date.toLocaleString());
						}
					},
					"fnServerData":function(sSource,aoData,fnCallback){
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
					"sAjaxSource":"${pageContext.request.contextPath}/projectFilesAction/findItems.ilf"
				});
			});
			function checkedOne(isChecked,checkboxName,checkedValue){
				if(isChecked){
					/*1.选中*/
					var checkboxes = document.getElementsByName(checkboxName);
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							if(checkboxes[i].value==checkedValue){
								checkboxes[i].checked = true;
							}else{
								checkboxes[i].checked = false;
							}
						}
					}
					/*2.验证*/
					var checkedMap = acheMaps.get(checkedValue);
					if(parseInt(checkedMap["UPLOADER"])==parseInt(checkedMap["LOGIN_USER_ID"])){
						$("#DELETE_BUTTON").show(300);
					}else{
						$("#DELETE_BUTTON").hide(300);
					}
				}else{
					$("#DELETE_BUTTON").show(300);
				}
			}
			function doUpload(){
				var uploadFileName = $("#TemplateUpload").val();
				if(uploadFileName==null || uploadFileName==""){
					window.wxc.xcConfirm("请先选择需要上传的文件.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/projectFilesAction/uploadDataFile.ilf?projectCode="+$("#hiddenOfProjectCode").val(),
						secureuri:false,
						contentType:"text/xml",
						fileElementId:"TemplateUpload",
						type:"POST",
						dataType:"json",
						success:function(data,status){
							if(data["SUCCESS"]){
								window.wxc.xcConfirm("资料上传成功.",window.wxc.xcConfirm.typeEnum.info);
								oTable.fnDraw();
							}else{
								window.wxc.xcConfirm("资料上传失败.",window.wxc.xcConfirm.typeEnum.info);
							}
						},
						error:function(message){
							window.wxc.xcConfirm("资料上传失败.",window.wxc.xcConfirm.typeEnum.info);
						}
					});	
				}
			}
			function downLoadFile(){
				var checkedValue = null;
				var jobs = document.getElementsByName("checksRadio");
				if(jobs!=null && jobs.length>0){
					for(var i=0;i<jobs.length;i++){
						if(jobs[i].checked){
							checkedValue = jobs[i].value;
							break;
						}
					}
				}
				if(checkedValue!=null){
					try{ 
						var exportForm = $("<form>");
						exportForm.attr("style","display:none");
						exportForm.attr("target","");
						exportForm.attr("method","post");
						exportForm.attr("action","${pageContext.request.contextPath}/projectFilesAction/downloadTemplate.ilf?projectCode="+checkedValue);
						$("body").append(exportForm);
						exportForm.submit();
						exportForm.remove();
					}catch(e){ 
						window.wxc.xcConfirm("资料下载失败.",window.wxc.xcConfirm.typeEnum.info);
					} 
				}else{
					window.wxc.xcConfirm("请先选择一个待下载的资料.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function removeFile(){
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
					window.wxc.xcConfirm("是否确定删除项目资料？..","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/projectFilesAction/removeFile.ilf",
								async:false,
								type:"POST",
								data:"fileCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["SUCCESS"]){
										window.wxc.xcConfirm("资料删除成功.",window.wxc.xcConfirm.typeEnum.info);
										conditions = [{
											name:"BELONG_PROJECT",
											value:$("#hiddenOfProjectCode").val()
										}];
										oTable.fnDraw();
									}else{
										window.wxc.xcConfirm("资料删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("资料删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择一项工程资料.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfProjectCode" value="<%=projectCode %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 0px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>项目管理 >> 项目已上传资料查看/下载/上传
				<form class="form-search pull-right">
					<div style="float:right;">
						<input type="file" id="TemplateUpload" name="TemplateUpload" style="width:300px;height:30px;border:solid 1px #A3D0E3;"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:doUpload();">
							<i class="icon-flag icon-white"></i>执行资料上传
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:downLoadFile();">
							<i class="icon-download-alt icon-white"></i>资料下载
						</a>
						<a class="btn btn-danger" id="DELETE_BUTTON" style="cursor:pointer" onclick="javascript:removeFile();">
							<i class="icon-trash icon-white"></i>删除资料
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #A3D0E3;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">资料名称</th>
										<th style="text-align:center;">上传时间</th>
										<th style="text-align:center;">上传人</th>
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
</html>
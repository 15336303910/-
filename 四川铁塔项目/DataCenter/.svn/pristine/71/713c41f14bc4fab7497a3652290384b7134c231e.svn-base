<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>高德坐标系转化</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style type="text/css">
			thead th{
				text-align:center;
			}
			.dataTables_wrapper{
				border:solid 1px #A3D0E3;
			}
		</style>
		<script type="text/javascript">
			/*
				定义坐标转化表格变量和参数变量
			 */
			var oTable = null;
			var conditions = [];
			var tableHeight = 0;
			$(document).ready(function(){
				var layoutHeight = document.body.clientHeight;
				document.getElementById("mainPanel").style.height = (layoutHeight-290)+"px";
				document.getElementById("multiTransfer").style.height = (layoutHeight-335)+"px";
				document.getElementById("singleTransfer").style.height = (layoutHeight-335)+"px";
				tableHeight = layoutHeight-472;
				/*
					坐标批量转化表格.
				 */
				conditions = [{
					name:"BELONG_JOB",
					value:$("#hiddenOfJobCode").val()
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
						"mData":"X_INPUT",
						"sWidth":"15%"
					},{
						"mData":"Y_INPUT",
						"sWidth":"15%"
					},{
						"mData":"TRANSFER_RESULT",
						"sWidth":"15%"
					},{
						"mData":"ADDRESS_OUTPUT",
						"sWidth":"54.5%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":100,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/xyTransfer/findItems.ilf"
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
			function refreshPage(){
				conditions = [{
					name:"BELONG_JOB",
					value:$("#hiddenOfJobCode").val()
				}];
				oTable.fnDraw();
			}
			function downloadTemplate(){
				window.wxc.xcConfirm("是否确认下载模板文件？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/xyTransfer/downloadTemplate.ilf");
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});	
			}
			function downloadDataFile(){
				var fileName = $("#hiddenOfOutputFile").val();
				if(fileName==""){
					window.wxc.xcConfirm("尚未进行转化工作，无对应数据文件.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认下载数据文件？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							var exportForm = $("<form>");
						    exportForm.attr("style","display:none");
						    exportForm.attr("target","");
						    exportForm.attr("method","post");
						    exportForm.attr("action","${pageContext.request.contextPath}/xyTransfer/downloadDataFile.ilf?fileName="+fileName);
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});	
				}
			}
			function showOrHide(typeCode){
				$(".editpanels").hide();
				if(typeCode==1){
					$("#singleTransfer").show(400);
				}else if(typeCode==2){
					$("#multiTransfer").show(400);
				}
			}
			function doTransfer(){
				var editObject = {
					xPoint:$("#X_POINT_VALUE").val(),
					yPoint:$("#Y_POINT_VALUE").val()
				};
				if(editObject["xPoint"]==""){
					window.wxc.xcConfirm("请输入经度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(editObject["yPoint"]==""){
					window.wxc.xcConfirm("请输入纬度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				$("#TRANSFER_RESULT").html("<font color='black'>正在转化...</font>");
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/xyTransfer/xytransfer.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							$("#TRANSFER_RESULT").html("<font color='green'>转化成功</font>");
							$("#LOCATION_VALUE").val(textStatus["LOCATION_VALUE"]);
						}else{
							$("#TRANSFER_RESULT").html("<font color='red'>转化失败</font>");
							$("#LOCATION_VALUE").val("");
						}
					},
					error:function(){
						window.parent.overLoading(true);
						$("#TRANSFER_RESULT").html("<font color='red'>转化失败</font>");
						$("#LOCATION_VALUE").val("");
					}
				});
			}
			function doUpload(){
				var uploadFileName = $("#TemplateUpload").val();
				if(uploadFileName.indexOf(".xlsx")==-1){
					window.wxc.xcConfirm("请上传*.xlsx格式的文件.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					document.getElementById("canelButton").click();
					window.parent.loading(true);
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/xyTransfer/uploadDataFile.ilf",
						secureuri:false,
						contentType:"text/xml",
						fileElementId:"TemplateUpload",
						type:"POST",
						dataType:"json",
						success:function(data,status){
							window.parent.overLoading(true);
							if(data["success"]){
								$("#hiddenOfJobCode").val(data["jobCode"]);
								$("#hiddenOfOutputFile").val(data["outputFile"]);
								refreshPage();
							}else{
								window.wxc.xcConfirm("文件上传失败.",window.wxc.xcConfirm.typeEnum.info);
							}
						},
						error:function(message){
							window.parent.overLoading(true);
							window.wxc.xcConfirm("文件上传失败.",window.wxc.xcConfirm.typeEnum.info);
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;">
  		<input type="hidden" value="-1" id="hiddenOfJobCode"></input>
  		<input type="hidden" value="-1" id="hiddenOfCount"></input>
  		<input type="hidden" value="" id="hiddenOfOutputFile"></input>
		<div class="panel panel-default" style="margin-top:1px;height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-heading">
				<span class="panel-label"></span>高德坐标系 >> 根据坐标信息获取地理地址
				<form class="form-search pull-right">	
					<div style="float:right;">
						<a class="btn btn-warning" onclick="javascript:showOrHide(1);" style="cursor:pointer;margin-left:10px;">
							<i class="icon-th icon-white"></i>单个转化
						</a>
						<a class="btn btn-success" onclick="javascript:showOrHide(2);" style="cursor:pointer;">
							<i class="icon-th icon-white"></i>批量转化
						</a>
					</div>
				</form>
			</div>
			<%--规则视图--%>
			<div class="panel-body editpanels" style="width:100%;border:0px;" id="multiTransfer">
				<div style="width:99.8%;height:430px;overflow:auto;border:solid 1px #FFF;" id="ruleEditsBuck">
					<div style="float:left;width:99%;margin-top:4px;margin-left:4px;">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
							<span class="panel-label"></span>坐标批量转化
							<form class="form-search pull-right">	
								<div style="float:right;">
									<a class="btn btn-success" onclick="javascript:downloadTemplate();" style="cursor:pointer;">
										<i class="icon-th icon-white"></i>模板下载
									</a>
									<a class="btn btn-inverse" style="cursor:pointer" href="#myModal" role="button" data-toggle="modal">
										<i class="icon-file icon-white"></i>文件上传
									</a>
									<a class="btn btn-warning" style="cursor:pointer;" onclick="javascript:downloadDataFile();">
										<i class="icon-play-circle icon-white"></i>结果导出
									</a>
								</div>
							</form>
						</div>
						<div class="panel-body h350" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
											<thead>
												<tr>
													<th><font color="red">输入经度</font></th>
													<th><font color="red">输入纬度</font></th>
													<th><font color="green">转化结果</font></th>
													<th><font color="green">输出结构化地址</font></th>
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
			<div class="panel-body editpanels" style="border:0px;display:none;" id="singleTransfer">
				<center>
					<div style="width:60%;height:310px;overflow:auto;border:dotted 2px #A3D0E3;border-radius:10px;margin-top:60px;">
						<center>
							<table cellpadding="0" cellspacing="0" style="width:90%;font-size:14px;margin-top:20px;">
								<tr height="50px;">
									<td style="width:20%;text-align:right;">输入经度：<font color="red" style="margin-right:3px;">*</font></td>
									<td style="width:80%;" colspan="3">
										<input id="X_POINT_VALUE" type="text" style="width:80%;height:35px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
									</td>
								</tr>
								<tr height="50px;">
									<td style="width:20%;text-align:right;">输入纬度：<font color="red" style="margin-right:3px;">*</font></td>
									<td style="width:80%;" colspan="3">
										<input id="Y_POINT_VALUE" type="text" style="width:80%;height:35px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
									</td>
								</tr>
								<tr height="50px;">
									<td style="width:20%;text-align:right;">转化结果：</td>
									<td style="width:80%;" id="TRANSFER_RESULT" colspan="3">
										<font color='red'>未执行转化</font>
									</td>
								</tr>
								<tr height="50px;">
									<td style="width:20%;text-align:right;">输出地址：</td>
									<td style="width:80%;" colspan="3">
										<input id="LOCATION_VALUE" readonly type="text" style="width:80%;height:35px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
									</td>
								</tr>
								<tr height="40px;">
									<td colspan="4" style="text-align:center;">
										<a class="btn btn-warning" style="width:140px;cursor:pointer;margin-top:10px;" onclick="javascript:doTransfer();">
											<i class="icon-cog icon-white"></i>执行转化
										</a>
									</td>
								</tr>
							</table>
						</center>
					</div>
				</center>
			</div>
		</div>
		<%--文件上传：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:200px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3>+ 经纬度批量上传</h3>
			</div>
			<div class="modal-body" style="max-height:180px;">
				<form class="l-group" id="uploadForm">
					<table class="table table-bordered table-hover">
						<tr>
							<td><input type="file" name="TemplateUpload" id="TemplateUpload" style="width:90%;height:30px;"></input></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;" onclick="javascript:doUpload();">
					<i class="icon-envelope icon-white"></i>确定上传
				</button>
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;display:none;" id="canelButton">
					<i class="icon-zoom-out icon-white"></i>取消
				</button>
			</div>
		</div>
		<%--文件上传：结束--%>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
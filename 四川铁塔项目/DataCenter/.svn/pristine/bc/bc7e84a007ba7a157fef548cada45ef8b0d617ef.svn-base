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
    	<title>规则导入</title>
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
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-3
			    });
			    /*表格高度及容量*/
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
				/*
					坐标批量转化表格.
				 */
				conditions = [];
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
						"mData":"FILE_NAME",
						"sWidth":"25%"
					},{
						"mData":"UPLOAD_DATE",
						"sWidth":"20%"
					},{
						"mData":"RULE_NAME",
						"sWidth":"30%"
					},{
						"mData":"ACTION_RESULT",
						"sWidth":"24.5%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						var longTime = aData["UPLOAD_DATE"]["time"];
						var date = new Date();
						date.setTime(longTime);
						$("td:eq(1)",nRow).html(date.toLocaleString());
						if(aData["ACTION_RESULT"]=="上传成功"){
							$("td:eq(3)",nRow).html("<font color='green'>上传成功</font>");
						}else{
							$("td:eq(3)",nRow).html("<font color='red'>"+aData["ACTION_RESULT"]+"</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/ruleImportAction/findItems.ilf"
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
					name:"RULE_NAME",
					value:$("#ruleNameKey").val()
				}];
				oTable.fnDraw();
			}
			function downloadTemplate(){
				window.wxc.xcConfirm("是否下载规则编辑模板？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/ruleEditAction/downloadTemplate.ilf");
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
			function doUpload(){
				var uploadFileName = $("#TemplateUpload").val();
				if(uploadFileName.indexOf(".xlsx")==-1){
					window.wxc.xcConfirm("请上传*.xlsx格式的规则编辑文件.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					document.getElementById("canelButton").click();
					window.parent.loading(true);
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/ruleEditAction/uploadDataFile.ilf",
						secureuri:false,
						contentType:"text/xml",
						fileElementId:"TemplateUpload",
						type:"POST",
						dataType:"json",
						success:function(data,status){
							window.parent.overLoading(true);
							window.wxc.xcConfirm(data["message"],window.wxc.xcConfirm.typeEnum.info);
							if(data["success"]){
								refreshPage();
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
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="-1" id="hiddenOfJobCode"></input>
  		<input type="hidden" value="-1" id="hiddenOfCount"></input>
  		<input type="hidden" value="" id="hiddenOfOutputFile"></input>
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:1px;height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>规则管理 >> 规则导入管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入规则名称" style="width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="ruleNameKey"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:refreshPage();"></img>
						<a class="btn btn-success" onclick="javascript:downloadTemplate();" style="cursor:pointer;">
							<i class="icon-th icon-white"></i>规则模板下载
						</a>
						<a class="btn btn-inverse" style="cursor:pointer" href="#myModal" role="button" data-toggle="modal">
							<i class="icon-file icon-white"></i>规则上传
						</a>
						<a class="btn btn-warning" style="cursor:pointer;" onclick="javascript:downloadDataFile();">
							<i class="icon-play-circle icon-white"></i>结果导出
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body editpanels" style="width:100%;border:0px;">
				<div style="width:99.8%;overflow:auto;border:solid 1px #FFF;">
					<div style="float:left;width:99%;margin-top:4px;margin-left:4px;">
						<div class="panel-body h350" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
											<thead>
												<tr>
													<th>上传文件名称</th>
													<th>上传时间</th>
													<th>规则名称</th>
													<th>上传结果</th>
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
		<%--文件上传：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:200px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3>+ 规则信息上传</h3>
			</div>
			<div class="modal-body" style="max-height:180px;">
				<form class="l-group" id="uploadForm">
					<table class="table table-bordered table-hover">
						<tr>
							<td><input type="file" name="TemplateUpload" id="TemplateUpload" style="width:90%;height:50px;"></input></td>
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
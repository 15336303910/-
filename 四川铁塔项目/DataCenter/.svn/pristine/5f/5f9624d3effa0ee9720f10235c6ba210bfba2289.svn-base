<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
	String prjId = request.getParameter("prjId");
	System.out.println("=>发起外验交付工单："+prjId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>外验交付流程发起</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/tips/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/project/flow/flow.js"></script>
		<style type="text/css">
			#shclNes{
				width:100px;
				height:100px;
			}
		</style>
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			var nodeJson = null;
			$(document).ready(function(){
				$("#shclNes").shCircleLoader({
					namespace:"myns",
					color:"#6BC2EB",
					dotsRadius:15
				});
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#shclNes").show();
				$.ajax({
					url:"${pageContext.request.contextPath}/projectCheckinAction/findOneDetail.ilf",
					async:false,
					type:"POST",
					data:"projectId="+$("#hiddenOfProjectId").val(),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						$("#shclNes").hide();
						if(textStatus["SUCCESS"]){
							if(textStatus["IS_UPLOADED_CHECK_FILE"]){
								var fileObj = textStatus["CHECK_FILE_OBJECT"];
								$("#TemplateNamees").val(fileObj["FILE_NAMES"]);
								$("#IsUploaded").val("Y");
							}
							if(textStatus["IS_DOING"]){
								nodeJson = textStatus["NODE_INFO"];
								/*当前环节*/
								var curNodeName = nodeJson["name"];
								$("#nodeTips").html("★  当前环节："+curNodeName);
								/*下一环节*/
								var curOrder = nodeJson["order"];
								curOrder++;
								var nextNode = getNodeOfFlowByOrder(curOrder);
								if(nextNode==null){
									$("#nextNodeTips").html("★  下一环节：外验完毕");
								}else{
									$("#nextNodeTips").html("★  下一环节："+nextNode["name"]);
								}
							}
						}
					},
					error:function(){
						$("#shclNes").hide();
					}
				});
				researchIcons();
				conditions = [{
					name:"IS_FILTER_REGION",
					value:"Y"
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
					"sScrollY":"220px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"8%"
					},{
						"mData":"USER_NAME",
						"sWidth":"21%"
					},{
						"mData":"IS_LOCKED",
						"sWidth":"14%",
						"bVisible":false
					},{
						"mData":"EMPLOYEE_NAME",
						"sWidth":"21%"
					},{
						"mData":"EMPLOYEE_COMPANY",
						"sWidth":"31%"
					},{
						"mData":"EMPLOYEE_PHONE",
						"sWidth":"17%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":8,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='userCheckbox' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/userManageAction/findUseraccounts.ilf"
				});
				$("#userAccountInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
			});
			function doUpload(){
				var uploadFileName = $("#TemplateUpload").val();
				if(uploadFileName==null || uploadFileName==""){
					window.wxc.xcConfirm("请先选择需要上传的项目交付单.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/projectCheckinAction/uploadData.ilf?projectId="+$("#hiddenOfProjectId").val(),
						secureuri:false,
						contentType:"text/xml",
						fileElementId:"TemplateUpload",
						type:"POST",
						dataType:"json",
						success:function(data,status){
							if(data["SUCCESS"]){
								$("#TemplateNamees").val(uploadFileName);
								$("#IsUploaded").val("Y");
								window.wxc.xcConfirm("项目交付单上传成功.",window.wxc.xcConfirm.typeEnum.info);
							}
						},
						error:function(message){
							window.wxc.xcConfirm("项目交付单上传失败.",window.wxc.xcConfirm.typeEnum.info);
						}
					});	
				}
			}
			function doExport(){
				try{ 
					var exportForm = $("<form>");
					exportForm.attr("style","display:none");
					exportForm.attr("target","");
					exportForm.attr("method","post");
					exportForm.attr("action","${pageContext.request.contextPath}/projectCheckinAction/downloadFile.ilf?projectId="+$("#hiddenOfProjectId").val());
					$("body").append(exportForm);
					exportForm.submit();
					exportForm.remove();
				}catch(e){ 
					
				} 
			}
			function researchIcons(){
				$("#shclNes").show();
				$.ajax({
					url:"${pageContext.request.contextPath}/projectCheckinAction/findOneDetail.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"projectId="+$("#hiddenOfProjectId").val(),
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							var tableHtml = "";
							tableHtml+="<table cellpadding='0' cellspacing='0' style='width:99%;''>";
							if(!textStatus["IS_UPLOADED_IMAGE"]){
								$("#IsUploadedImage").val("N");
								tableHtml+="<tr height='180'>";
								tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
								tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
								tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
								tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
								tableHtml+="</tr>";
							}else{
								$("#IsUploadedImage").val("Y");
								var images = textStatus["IMAGES"];
								var imageLength = images.length;
								var totalRow = 0;
								if(imageLength%4==0){
									totalRow = parseInt(imageLength/4)+1;
								}else{
									totalRow = parseInt(imageLength/4)+1;
								}
								for(var p=1;p<=totalRow;p++){
									var pageStart = (p-1)*4;
									if(pageStart==0){
										pageStart = 1;
									}else{
										pageStart++;
									}
									var pageLimit = pageStart+3;
									$.ajax({
										url:"${pageContext.request.contextPath}/projectCheckinAction/findImagePage.ilf",
										async:false,
										type:"POST",
										dataType:"json",
										data:"projectId="+$("#hiddenOfProjectId").val()+"&pageStart="+pageStart+"&pageLimit="+pageLimit,
										timeout:10000, 
										success:function(pageResult){
											if(pageResult["SUCCESS"]){
												var items = pageResult["ITEMS"];
												tableHtml+="<tr height='180'>";
												for(var b=0;b<items.length;b++){
													var appObject = items[b];
													tableHtml+="	<td style='width:25%;'>";
													tableHtml+="		<center>";
													tableHtml+="			<div style='width:98%;height:175px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;'>";
													tableHtml+="				<div style='float:left;height:175px;width:100%;'>";
													tableHtml+="					<img src='${pageContext.request.contextPath}/uploads/project/image/"+appObject["IMAGE_NAME"]+"' style='width:95%;height:94%;margin-top:5px;'></img>";
													tableHtml+="				</div>";
													tableHtml+="			</div>";
													tableHtml+="		</center>";
													tableHtml+="	</td>";
												}
												if(items.length!=4 && p==totalRow){
													tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
													var rowIconCount = parseInt(items.length)+1;
													if(rowIconCount<4){
														for(var h=rowIconCount;h<4;h++){
															tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
														}
														tableHtml+="</tr>";
													}
												}else if(items.length==10 && p==totalRow){
													tableHtml+="</tr>";
													tableHtml+="<tr height='180'>";
													tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:25%;'>&nbsp;</td>";
													tableHtml+="</tr>";
												}
											}
										},
										error:function(){}
									});
								}
							}
							tableHtml+="</table>";
							document.getElementById("appContainer").innerHTML = tableHtml;
						}
						$("#shclNes").hide();
					},
					error:function(){
						$("#shclNes").hide();
					}
				});
			}
			function rejectJob(){
				var isRejectAble = nodeJson["isRejectAble"];
				if(!isRejectAble){
					window.wxc.xcConfirm("当前环节并不支持驳回审批.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					var rejectOrder = nodeJson["rejectToOrder"];
					var nextNode = getNodeOfFlowByOrder(rejectOrder);
					window.wxc.xcConfirm("是否确定要执行驳回操作？","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/projectCheckinAction/nextChainNode.ilf",
								async:false,
								type:"POST",
								data:"projectId="+$("#hiddenOfProjectId").val()+"&nextOperateUser=-2&flowNode="+JSON.stringify(nextNode),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["SUCCESS"]){
										window.wxc.xcConfirm("任务已审核完毕.","custom",{
											title:"提示",
											btn:parseInt("0011",2),
											onOk:function(){
												window.parent.closeAssignedTab("外验交付审核");
											}
										});
									}else{
										window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});	
						}
					});
					
				}
			}
			function submitJob(){
				var curOrder = nodeJson["order"];
				curOrder++;
				var nextNode = getNodeOfFlowByOrder(curOrder);
				var checkedUser = null;
				if(nextNode!=null){
					checkedUser = getCheckedValue("userCheckbox");
					if(checkedUser==null){
						window.wxc.xcConfirm("请选择下一环节执行人.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}
				}else{
					checkedUser = -1;
					nextNode = {
						isHaveNext:false
					};
				}
				window.wxc.xcConfirm("是否确定要传递到下一环节执行人？","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/projectCheckinAction/nextChainNode.ilf",
							async:false,
							type:"POST",
							data:"projectId="+$("#hiddenOfProjectId").val()+"&nextOperateUser="+checkedUser+"&flowNode="+JSON.stringify(nextNode),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["SUCCESS"]){
									window.wxc.xcConfirm("任务已审核完毕.","custom",{
										title:"提示",
										btn:parseInt("0011",2),
										onOk:function(){
											window.parent.closeAssignedTab("外验交付审核");
										}
									});
								}else{
									window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							},
							error:function(){
								window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
							}
						});	
					}
				});
			}
			function deleteImage(){
				var imageChecked = getCheckedValue("imageCheckbox");
				if(imageChecked==null){
					window.wxc.xcConfirm("请先选择一张项目完工照片.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/projectCheckinAction/removeImage.ilf",
						async:false,
						type:"POST",
						data:"imageId="+imageChecked,
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							if(textStatus["SUCCESS"]){
								researchIcons();
							}
						},
						error:function(){
							
						}
					});
				}
			}
			function openUploadWindow(){			
				$("#brownModal").dialog({
					title:"上传照片附件",
					show:{
						effect:"slide",
						duration:300
					},
					hide:{
				        effect:"slide",
				        duration:300
					},
					width:600,
					height:190,
					autoOpen:true,
					closeOnEscape:true,
					draggable:false,
					resizable:false,
					modal:true,
					buttons:{
		  				"确定":function(){
		  					$(this).dialog("close");
		  					$.ajaxFileUpload({
								url:"${pageContext.request.contextPath}/projectCheckinAction/uploadImage.ilf?projectId="+$("#hiddenOfProjectId").val(),
								secureuri:false,
								contentType:"text/xml",
								fileElementId:"imageUpload",
								type:"POST",
								dataType:"json",
								success:function(data,status){
									if(data["SUCCESS"]){
										document.getElementById("editForms").reset();
										researchIcons();
									}
								},
								error:function(message){
									window.wxc.xcConfirm("照片上传失败.",window.wxc.xcConfirm.typeEnum.info);
								}
							});
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
			function searchData(){
				conditions = [{
					name:"IS_FILTER_REGION",
					value:"Y"
				}];
				var USER_NAME = $("#userAccountInput").val();
				if(USER_NAME!=""){
					conditions.push({
						name:"USER_NAME",
						value:USER_NAME
					});	
				}
				oTable.fnDraw();
			}
			function openContentWindow(){
				$("#contentModal").dialog({
					title:"填写驳回原因",
					show:{
						effect:"slide",
						duration:400
					},
					hide:{
				        effect:"slide",
				        duration:400
					},
					width:370,
					height:260,
					autoOpen:true,
					closeOnEscape:true,
					draggable:false,
					resizable:false,
					modal:true,
					buttons:{
		  				"确认驳回":function(){
		  					var contentValue = $("#contentArea").val();
		  					if(contentValue==""){
		  						window.wxc.xcConfirm("请填写驳回原因.",window.wxc.xcConfirm.typeEnum.info);
		  					}else{
		  						var isRejectAble = nodeJson["isRejectAble"];
		  						if(!isRejectAble){
		  							window.wxc.xcConfirm("当前环节并不支持驳回审批.",window.wxc.xcConfirm.typeEnum.info);
		  						}else{
		  							var rejectOrder = nodeJson["rejectToOrder"];
		  							var nextNode = getNodeOfFlowByOrder(rejectOrder);
		  							window.wxc.xcConfirm("是否确定要执行驳回操作？","custom",{
		  								title:"提示",
		  								btn:parseInt("0011",2),
		  								onOk:function(){
		  									nextNode["rejectContent"] = contentValue;
		  									$.ajax({
		  										url:"${pageContext.request.contextPath}/projectCheckinAction/nextChainNode.ilf",
		  										async:false,
		  										type:"POST",
		  										data:"projectId="+$("#hiddenOfProjectId").val()+"&nextOperateUser=-2&flowNode="+JSON.stringify(nextNode),
		  										dataType:"json",
		  										timeout:10000, 
		  										success:function(textStatus){
		  											if(textStatus["SUCCESS"]){
		  												$("#contentModal").dialog("close");
		  												window.wxc.xcConfirm("任务已审核完毕.","custom",{
		  													title:"提示",
		  													btn:parseInt("0011",2),
		  													onOk:function(){
		  														window.parent.closeAssignedTab("外验交付审核");
		  													}
		  												});
		  											}else{
		  												window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
		  											}
		  										},
		  										error:function(){
		  											window.wxc.xcConfirm("任务发起失败.",window.wxc.xcConfirm.typeEnum.error);
		  										}
		  									});	
		  								}
		  							});
		  							
		  						}
		  					}
						},
						"取消":function(){
							$(this).dialog("close");
						}
		  			}
				});
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfProjectId" value="<%=prjId %>"></input>
  		<div style="width:99.8%;height:125px;" id="topPanel">
  			<div class="panel panel-default" style="margin-top:-20px;height:115px;border:solid 0px #FFF;">
				<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
					<span class="panel-label"></span>项目交付单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='red' id="nodeTips"></font>
				</div>
				<div class="panel-body" style="border:solid 1px #A3D0E3;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto">
								<div style="float:left;display:none;">
									<input type="hidden" id="IsUploaded" value="N"></input>
									<input type="file" id="TemplateUpload" name="TemplateUpload" style="cursor:pointer;width:350px;height:30px;border:solid 1px #A3D0E3;"></input>
								</div>
								<div style="float:left;margin-left:5px;">
									<input type="text" id="TemplateNamees" name="TemplateNamees" style="cursor:pointer;width:450px;height:30px;border:solid 1px #A3D0E3;background:#FFF;" readonly placeholder="已上传文件名称"></input>
								</div>
								<div style="float:left;margin-left:5px;display:none;">
									<a class="btn btn-success" style="cursor:pointer" onclick="javascript:doUpload();">
										<i class="icon-flag icon-white"></i>执行上传
									</a>
								</div>
								<div style="float:left;margin-left:5px;">
									<a class="btn btn-success" style="cursor:pointer" onclick="javascript:doExport();">
										<i class="icon-flag icon-white"></i>下载外验交付单
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
  		</div>
  		<div style="width:99.8%;height:400px;" id="centerPanel">
  			<div class="panel panel-default" style="margin-top:-20px;height:400px;border:solid 0px #FFF;" id="subOfCenterPanel">
				<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
					<span class="panel-label"></span>项目各项完工照片
					<form class="form-search pull-right">	
						<div style="float:right;">
							<input type="hidden" id="IsUploadedImage" value="N"></input>
							<%--<a class="btn btn-danger" style="cursor:pointer;margin-top:5px;" onclick="javascript:deleteImage();">
								<i class="icon-trash icon-white"></i>删除照片
							</a>--%>
						</div>
					</form>
				</div>
				<div class="panel-body" style="border:solid 1px #A3D0E3;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto;">
								<div style="width:100%;height:320px;overflow:auto;" id="appContainer">
									&nbsp;
								</div>		
							</div>
						</div>
					</div>
				</div>
			</div>
  		</div>
  		<div style="width:99.8%;height:300px;">
  			<div class="panel panel-default" style="height:300px;border:solid 0px #FFF;">
				<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
					<span class="panel-label"></span>下一环节审批人.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='green' id="nextNodeTips"></font>
					<form class="form-search pull-right">	
						<div style="float:right;">
							<input type="text" placeholder="请输入用户账号或姓名" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-top-left-radius:6px;border-bottom-left-radius:6px;" id="userAccountInput"></input>
							<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
								<i class="icon-search icon-white"></i>执行查询 
							</a>
							<a class="btn btn-success" style="cursor:pointer" onclick="javascript:submitJob();">
								<i class="icon-ok icon-white"></i>审批通过
							</a>
							<a class="btn btn-danger" style="cursor:pointer" onclick="javascript:openContentWindow();">
								<i class="icon-repeat icon-white"></i>审批驳回
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
  		</div>
		<div id="brownModal" style="display:none;height:295px;">
			<div style="height:100px;border:solid 1px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:550px;font-size:12px;margin-top:15px;">
							<tr height="60">
								<td style="text-align:right;width:100px">附件照片：</td>
								<td colspan="3">
									<input type="file" id="imageUpload" name="imageUpload" style="border:solid 1px #54A8E1;margin-left:5px;width:436px;height:33px;font-size:12px;margin-top:6px;"></input>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<div id="contentModal" style="display:none;height:295px;">
			<div style="height:150px;border:solid 0px #54A8E1;border-radius:10px;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:550px;font-size:12px;margin-top:5px;">
							<tr height="60">
								<td>
									<textarea id="contentArea" type="text" style="border:solid 1px #54A8E1;width:100%;height:170px;font-size:12px;"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
  		<div id="shclNes" style="position:fixed;top:40%;right:47%;display:none;"></div>
	</body>
</html>
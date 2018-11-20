<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String detailId = request.getParameter("detailId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>互联网小工具配置</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/js/table/superTable.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/body-layout.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/content/css/test.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			function turnToToolDetail(detailId){
				var editingName = "";
				var belongApps = editingObj["BELONG_APPS"];
				for(var i=0;i<belongApps.length;i++){
					var appDetail = belongApps[i];
					if(appDetail["ID"]==detailId){
						editingName = appDetail["APP_NAME"];
						break;
					}
				}
				window.parent.turnToJsp("应用详情:"+editingName,"jsp/tools/toolDetail.jsp?detailId="+detailId);
			}
			function downloadTool(){
				window.wxc.xcConfirm("是否确定要立即下载此工具？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/netToolAction/downloadApp.ilf?fileName="+editingObj["TOOL_NAME"]);
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});
			}
			var editingObj = null;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-2
			    });
				$("#domContainer").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-3
			    });
				$.ajax({
					url:"${pageContext.request.contextPath}/netToolAction/findOne.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"netToolDetailId="+$("#hiddenOfDetailCode").val(),
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"] && textStatus["detailObject"]!=null){
							/*缓存*/
							editingObj = textStatus["detailObject"];
							/*渲染基本信息*/
							if(editingObj["IS_DOWNLOAD"]=="Y"){
								$("#ACTION_TYPE").html("工具下载");
								$("#TURN_TO_USE").bind("click",function(){
									downloadTool();
								});
							}else{
								$("#TURN_TO_USE").bind("click",function(){
									turnInstract();
								});
							}
							$("#APP_ICON").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+editingObj["APP_ICON"]);
							if(editingObj["APP_NAME"]!=null){
								$("#APP_NAME").html(editingObj["APP_NAME"]);
								$("#appNameTitle").html(editingObj["APP_NAME"]);
								$("#panelTitle").html(editingObj["APP_NAME"]);
								$("#pageTitle").html("工具详情 >> "+editingObj["APP_NAME"]);
							}
							$("#PUBLISH_USER").html(editingObj["EMPLOYEE_NAME"]);
							var date = new Date();
							date.setTime(editingObj["PUBLISH_DATE"]["time"]);
							$("#PUBLISH_DATE").html(date.toLocaleString());
							if(editingObj["APP_VERSION"]!=null && editingObj["APP_VERSION"]!=""){
								$("#APP_VERSION").html(editingObj["APP_VERSION"]);
							}else{
								$("#APP_VERSION").html("暂无版本信息");
							}
							$("#APP_DESC").html(editingObj["APP_DESC"]);
							$("#APP_LANGUAGE").html(editingObj["APP_LANGUAGE"]);
							$("#OTHER_DESC").html(editingObj["OTHER_DESC"]);
							$("#SEARCH_KEY").html(editingObj["SEARCH_KEY"]);
							if(editingObj["APP_DESC_IMAGE"]!=null && editingObj["APP_DESC_IMAGE"]!=""){
								$("#APP_DESC_IMAGE").attr("src","${pageContext.request.contextPath}/uploads/"+editingObj["APP_DESC_IMAGE"]);	
							}
							/*渲染相关应用*/
							var str = "";
							str+="<table cellpadding='0' cellspacing='0' style='width:99%;margin-top:20px;'>";
							str+="	  <tr height='120'>";
							var belongApps = editingObj["BELONG_APPS"];
							if(belongApps.length>0){
								for(var i=0;i<belongApps.length;i++){
									var appDetail = belongApps[i];
									str+="<td style='width:20%;'>";
									str+="	  <center>";
									str+="	  	  <div style='width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;' onclick='javascript:turnToToolDetail("+appDetail["ID"]+");'>";
									str+="			  <div style='width:100%;height:65px;margin-top:5px;'>";
									str+="			  	  <img src='${pageContext.request.contextPath}/jsp/tools/images/"+appDetail["APP_ICON"]+"' style='height:50px;width:50px;margin-top:10px;'></img>";
									str+="			  </div>";
									str+="			  <div style='width:100%;height:25px;margin-top:5px;'>";
									str+="				  <font color='#4AA3DF' size='3'>"+appDetail["APP_NAME"]+"</font>";
									str+="			  </div>";
									str+="		  </div>";
									str+="	  </center>";
									str+="</td>";
								}
								var startIndex = belongApps.length+1;
								for(var j=startIndex;j<=5;j++){
									str+="<td style='width:20%;'>&nbsp;</td>";
								}
							}else{
								str+="	  <td style='text-align:center;'><font color='red' size='2'>该发布者尚未发布其他应用</font></td>";
							}
							str+="	  </tr>";
							str+="</table>";
							document.getElementById("belongAppsDetail").innerHTML = str;
						}
					},
					error:function(){}
				});
			});
			function turnInstract(){
				if(editingObj["IS_EXPORT"]=="Y"){
					window.open(editingObj["APP_URL"]);	
				}else{
					window.parent.turnToJsp(editingObj["APP_NAME"],editingObj["APP_URL"]);
				}
			}
			function editText(tdId,inputId){
				var editedText = $("#hiddenOfEditedText").val();
				if(editedText==""){
					$("#hiddenOfEditedText").val($("#"+tdId).html());
					document.getElementById(tdId).innerHTML = "<input type='text' id='"+inputId+"' value='"+$("#hiddenOfEditedText").val()+"' style='width:80%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;'></input>";
					/*聚焦输入*/
					$("#"+inputId).focus();
					/*绑定事件*/
					$("#"+inputId).bind("keydown",function(event){
						if(event.keyCode==13){
							var inputValue = $("#"+inputId).val();
							document.getElementById(tdId).innerHTML = inputValue;
							$.ajax({
								url:"${pageContext.request.contextPath}/netToolAction/updateAudit.ilf",
								async:false,
								type:"POST",
								data:"detailId="+$("#hiddenOfDetailCode").val()+"&columnName="+tdId+"&newValue="+inputValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){},
								error:function(){}
							});
							if(inputId=="APP_NAME_INPUT"){
								$("#appNameTitle").html(inputValue);
								$("#panelTitle").html(inputValue);
								$("#pageTitle").html("工具详情 >> "+inputValue);
							}
							$("#hiddenOfEditedText").val("");
							return false;
						}
					});	
				}else{
					window.wxc.xcConfirm("请先保存正在编辑的项.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			var isShow = false;
			function sohEditButtons(){
				if(isShow){
					$(".iconButtons").hide(500);
					isShow = false;
				}else{
					$(".iconButtons").show(500);
					isShow = true;
				}
			}
			var isImageShow = false;
			function sohImageButtons(){
				if(isImageShow){
					$("#uploadButton").hide(500);
					isImageShow = false;
				}else{
					$("#uploadButton").show(500);
					isImageShow = true;
				}
			}
			/*
				[没有合适的图标]按钮触发. 显示基本信息编辑区域，隐藏图标选择区域.
			 */
			function rebackIconEdit(){
				$("#appIconEdit").hide();
				$("#appInfoEdit").show(500);
			}
			/*
				[系统图标]按钮触发.显示图标选择区域，隐藏基本信息编辑区域.
			 */
			function chooseSystemIcon(){
				$("#appInfoEdit").hide();
				$("#appIconEdit").show(500);
			}
			/*
				[双击图标]触发.根据双击的图标，更换当前图标.隐藏图标选择区域，显示基本信息编辑.
			 */
			function refreshIcon(iconName){
				$.ajax({
					url:"${pageContext.request.contextPath}/netToolAction/updateAudit.ilf",
					async:false,
					type:"POST",
					data:"detailId="+$("#hiddenOfDetailCode").val()+"&columnName=APP_ICON&newValue="+iconName,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){},
					error:function(){}
				});
				$("#APP_ICON").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+iconName);
				$("#appIconEdit").hide();
				$("#appInfoEdit").show(500);
				$(".iconButtons").hide(500);
				isShow = false;
			}
			function deleteApp(){
				window.wxc.xcConfirm("是否确定要移除此项应用？.","custom",{
					title:"警告",
					btn:parseInt("0011",2),
					onOk:function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/netToolAction/deleteAudit.ilf",
							async:false,
							type:"POST",
							data:"detailId="+$("#hiddenOfDetailCode").val(),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["success"]){
									window.wxc.xcConfirm("应用已被成功移除.",window.wxc.xcConfirm.typeEnum.info);
									window.parent.closeAssignedTab("应用详情:"+editingObj["APP_NAME"]);
								}
							},
							error:function(){}
						});
					}
				});
			}
			function doUpload(){
				var uploadFileName = $("#TemplateUpload").val();
				if(uploadFileName.indexOf(".png")==-1){
					window.wxc.xcConfirm("请上传*.png格式的图像.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/netToolAction/uploadFile.ilf",
						secureuri:false,
						contentType:"text/xml",
						fileElementId:"TemplateUpload",
						type:"POST",
						dataType:"json",
						success:function(data,status){
							if(data["success"] && data["IMAGE_NAME"]!=null){
								if(editingObj!=null){
									editingObj["APP_DESC_IMAGE"] = data["IMAGE_NAME"];
								}
								document.getElementById("uploadForm").reset();
								document.getElementById("canelButton").click();
								$("#APP_DESC_IMAGE").attr("src","${pageContext.request.contextPath}/uploads/"+data["IMAGE_NAME"]);
								/*
									修改数据库中的关联图像.
								 */
								$.ajax({
									url:"${pageContext.request.contextPath}/netToolAction/updateAudit.ilf",
									async:false,
									type:"POST",
									data:"detailId="+$("#hiddenOfDetailCode").val()+"&columnName=APP_DESC_IMAGE&newValue="+data["IMAGE_NAME"],
									dataType:"json",
									timeout:10000, 
									success:function(textStatus){},
									error:function(){}
								});
								isImageShow = false;
								$("#uploadButton").hide(500);
							}else{
								window.wxc.xcConfirm("文件上传失败.",window.wxc.xcConfirm.typeEnum.info);
							}
						},
						error:function(message){
							window.wxc.xcConfirm("文件上传失败.",window.wxc.xcConfirm.typeEnum.info);
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" id="hiddenOfEditedText" value=""></input>
  		<input type="hidden" id="hiddenOfDetailCode" value="<%=detailId %>"></input>
		<div class="panel panel-default" style="height:480px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span><font id="pageTitle">&nbsp;</font>
				<form class="form-search pull-right">
					<div style="float:right;">
						<a class="btn btn-danger"  style="cursor:pointer;margin-top:5px;" onclick="javascript:deleteApp();">
							<i class="icon-trash icon-white"></i>移除应用
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<center>
								<div style="width:99.6%;height:435px;overflow:auto;border:solid 1px #FFF;" id="domContainer">
									<div style="width:20%;float:left;">
										<%--应用图标--%>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:95%;margin-top:3px;height:300px;">
											<div class="panletitle" style="text-align:left;width:95%;">
												<span class="panle-label"></span><font id="panelTitle">&nbsp;</font>
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<img id="APP_ICON" ondblclick="javascript:sohEditButtons();" src="${pageContext.request.contextPath}/jsp/tools/images/img.png" style='height:80px;width:80px;margin-top:80px;cursor:pointer;'></img><br/><br/>
															<font color='#4AA3DF' size="3" id="appNameTitle">&nbsp;</font><br/><br/>
															<button class="iconButtons" style="display:none;width:140px;background:#FFF;border:solid 1px #A3D0E3;border-radius:7px;" onclick="javascript:chooseSystemIcon();">选择图标</button>
														</center>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div style="width:79%;float:left;" id="appInfoEdit">
										<%--应用基本信息--%>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;margin-top:3px;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>应用基本信息
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<table cellpadding="0" cellspacing="0" style="width:100%;font-size:14px;margin-top:20px;">
															<tr height="40px;">
																<td style="width:15%;text-align:right;">工具名称：</td>
																<td style="width:85%;cursor:pointer;" colspan="3" id="APP_NAME" ondblclick="javascript:editText('APP_NAME','APP_NAME_INPUT');">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">发布人员：</td>
																<td style="width:20%;" id="PUBLISH_USER">&nbsp;</td>
																<td style="width:15%;text-align:right;">发布时间：</td>
																<td style="width:50%;" id="PUBLISH_DATE">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">当前版本：</td>
																<td style="width:85%;cursor:pointer;" colspan="3" id="APP_VERSION" ondblclick="javascript:editText('APP_VERSION','APP_VERSION_INPUT');">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">工具简介：</td>
																<td style="width:85%;cursor:pointer;" colspan="3" id="APP_DESC" ondblclick="javascript:editText('APP_DESC','APP_DESC_INPUT');">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">工具语言：</td>
																<td style="width:85%;" colspan="3" id="APP_LANGUAGE">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">其他说明：</td>
																<td style="width:85%;cursor:pointer;" colspan="3" id="OTHER_DESC" ondblclick="javascript:editText('OTHER_DESC','OTHER_DESC_INPUT');">&nbsp;</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">关键字：</td>
																<td style="width:20%;cursor:pointer;" id="SEARCH_KEY" ondblclick="javascript:editText('SEARCH_KEY','SEARCH_KEY_INPUT');">&nbsp;</td>
																<td style="width:65%;text-align:left;" colspan="2">
																	<div style="width:20%;height:35px;margin-left:80px;border:solid 1px #A3D0E3;border-radius:6px;cursor:pointer;" id="TURN_TO_USE">
																		<div style="float:left;margin-left:10px;margin-top:4px;">
																			<img src="${pageContext.request.contextPath}/icons/nets/run.png" style='height:30px;width:30px;'></img>
																		</div>
																		<div style="float:left;margin-left:12px;margin-top:7px;" id="ACTION_TYPE">进入应用</div>
																	</div>
																</td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>
										<%--应用展示--%>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>应用展示
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<img id="APP_DESC_IMAGE" ondblclick="javascript:sohImageButtons();" src="${pageContext.request.contextPath}/jsp/tools/images/img.png" style='height:400px;width:90%;margin-top:15px;cursor:pointer;'></img><br/><br/>
															<button id="uploadButton" style="width:160px;background:#FFF;border:solid 1px #A3D0E3;border-radius:7px;display:none;" href="#myModal" role="button" data-toggle="modal">上传应用相关截图</button>
														</center>
													</div>
												</div>
											</div>
										</div>
										<%--发布者相关的其他应用--%>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>发布者相关的其他应用
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;" id="belongAppsDetail">
												<table cellpadding="0" cellspacing="0" style="width:99%;margin-top:20px;">
													<tr height="120">
														<td>
															<center><font color='red' size='2'>该发布者尚未发布其他应用</font></center>
														</td>
													</tr>
												</table>
											</div>
										</div>
									</div>
									<%--图标选择区域--%>
									<div style="width:79%;float:left;display:none;" id="appIconEdit">
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;margin-top:3px;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>系统已有图标选择
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<table cellpadding="0" cellspacing="0" style="width:99%;">
																<%--第一行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('navigate.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/navigate.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('clocks.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/clocks.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('xyPoint.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/xyPoint.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('pieChart.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/pieChart.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('eyes.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/eyes.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('camera.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/camera.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第二行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('phone.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/phone.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('shuttlecock.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/shuttlecock.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('graphChart.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/graphChart.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('master.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/master.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('location.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/location.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('leaf.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/leaf.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第三行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('celiang.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/celiang.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('renwu.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/renwu.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('yaoshi.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/yaoshi.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('jihe.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/jihe.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('clouds.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/clouds.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('configs.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/configs.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第四行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('lucene.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/lucene.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('remove.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/remove.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('pdf.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/pdf.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('dunpai.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/dunpai.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('tuopu.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/tuopu.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('words.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/words.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第五行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('zuanshi.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/zuanshi.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('xunhuan.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/xunhuan.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('dengpao.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/dengpao.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('woshou.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/woshou.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('mail.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/mail.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('dollars.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/dollars.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第六行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('searchFile.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/searchFile.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('running.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/running.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('abstract.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/abstract.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('database.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/database.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('about.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/about.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('dingling.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/dingling.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第七行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('rocket.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/rocket.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('validCode.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/validCode.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('ide-data.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/ide-data.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('csb.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/csb.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('kms.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/kms.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('redis.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/redis.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--最后一行--%>
																<tr height="40">
																	<td colspan="6">
																		<center>
																			<a class="btn btn-warning" style="width:140px;cursor:pointer;margin-top:10px;" onclick="javascript:rebackIconEdit();">
																				<i class="icon-repeat icon-white"></i>没有合适的图标
																			</a>
																		</center>
																	</td>
																</tr>
															</table>
														</center>
													</div>
												</div>
											</div>
										</div>
									</div>
									<%--图标选择区域--%>
								</div>
							</center>	
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--文件上传：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:200px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3>+ 应用相关图片</h3>
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
					<i class="icon-envelope icon-white"></i>确定上传图片
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
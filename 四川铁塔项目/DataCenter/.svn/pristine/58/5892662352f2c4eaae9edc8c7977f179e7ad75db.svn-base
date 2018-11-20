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
    	<title>互联网应用配置</title>
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
			var editObject = {
				APP_ICON:""
			};
			function doSaveEdit(){
				if(editObject["APP_ICON"]==""){
					window.wxc.xcConfirm("请先选择应用图标.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				/*1.应用名称*/
				var appName = $("#appNameInput").val();
				if(appName==""){
					window.wxc.xcConfirm("请输入应用名称.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					editObject["APP_NAME"] = appName;
				}
				/*2.应用地址*/
				editObject["IS_DOWNLOAD"] = $("#isDownloadSelect").val();
				if(editObject["IS_DOWNLOAD"]=="N"){
					var appUrl = $("#appUrlInput").val();
					if(appUrl==""){
						window.wxc.xcConfirm("请输入应用地址.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}else{
						editObject["APP_URL"] = appUrl;
					}
				}
				/*3.1.应用版本*/
				editObject["APP_VERSION"] = $("#appVersionInput").val();
				/*3.2.是否外部链接*/
				editObject["IS_EXPORT"] = $("#isExportSelect").val();
				/*4.应用简介*/
				var appDesc = $("#appDescInput").val();
				if(appDesc==""){
					window.wxc.xcConfirm("请输入应用描述.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					editObject["APP_DESC"] = appDesc;
				}
				/*5.语言类型*/
				editObject["APP_LANGUAGE"] = $("#appLanguageSelection").val();
				/*6.关键字*/
				editObject["SEARCH_KEY"] = $("#searchKeyInput").val();
				/*7.其他说明*/
				editObject["OTHER_DESC"] = $("#otherDescInput").val();
				/*8.应用相关图片*/
				var imageName = $("#hiddenOfImageName").val();
				editObject["APP_DESC_IMAGE"] = imageName;
				/*9.是否下载*/
				editObject["TOOL_NAME"] = $("#appDownloadName").val();
				var tips = "是否确定保存编辑？.";
				if(imageName==""){
					tips = "当前尚未上传应用相关图片，是否继续保存？.";
				}
				window.wxc.xcConfirm(tips,"custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						editObject["ID"] = $("#hiddenOfCode").val();
						$.ajax({
							url:"${pageContext.request.contextPath}/netToolAction/saveAudit.ilf",
							async:false,
							type:"POST",
							data:"params="+JSON.stringify(editObject),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["success"]){
									window.wxc.xcConfirm("应用信息已成功保存.",window.wxc.xcConfirm.typeEnum.info);
									$("#hiddenOfCode").val(textStatus["newCode"]);
								}
							},
							error:function(){}
						});
					}
				});
			}
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
			});
			function turnInstract(){
				window.open("http://www.baidu.com");
			}
			/*
				[双击图标图像]触发.根据双击的图标，更换当前图标.隐藏图标选择区域，显示基本信息编辑.
			 */
			function refreshIcon(iconName){
				editObject["APP_ICON"] = iconName;
				$("#APP_ICON").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+iconName);
				$("#appIconEdit").hide();
				$("#appInfoEdit").show(500);
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
								$("#hiddenOfImageName").val(data["IMAGE_NAME"]);
								document.getElementById("uploadForm").reset();
								document.getElementById("canelButton").click();
								$("#APP_DESC_IMAGE").attr("src","${pageContext.request.contextPath}/uploads/"+data["IMAGE_NAME"]);
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
  		<input type="hidden" id="hiddenOfCode" value="-1"></input>
  		<input type="hidden" id="hiddenOfImageName" value=""></input>
		<div class="panel panel-default" style="height:480px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>互联网工具配置
			</div>
			<div class="panel-body h340" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<center>
								<div style="width:99.6%;height:435px;overflow:auto;" id="domContainer">
									<div style="width:20%;float:left;">
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:95%;margin-top:3px;height:460px;">
											<div class="panletitle" style="text-align:left;width:95%;">
												<span class="panle-label"></span>配置应用图标
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<img src="${pageContext.request.contextPath}/jsp/tools/images/toolHead.png" style='height:80px;width:80px;margin-top:70px;' id="APP_ICON"></img><br/><br/>
															<button style="width:140px;background:#FFF;border:solid 1px #A3D0E3;border-radius:7px;" onclick="javascript:chooseSystemIcon();">选择图标</button>
														</center>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div style="width:79%;float:left;" id="appInfoEdit">
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
																<td style="width:15%;text-align:right;">应用名称：<font color="red" style="margin-right:3px;">*</font></td>
																<td style="width:20%;">
																	<input id="appNameInput" type="text" style="width:80%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
																</td>
																<td style="width:15%;text-align:right;">是否工具下载：</td>
																<td style="width:50%;">
																	<select id="isDownloadSelect" style="width:66%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:7px;">
																		<option value="N">否</option>
																		<option value="Y">是</option>
																	</select>
																</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">应用地址：<font color="red" style="margin-right:3px;">*</font></td>
																<td style="width:20%;">
																	<input id="appUrlInput" type="text" style="width:80%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
																</td>
																<td style="width:15%;text-align:right;">工具文件名称：</td>
																<td style="width:50%;">
																	<input id="appDownloadName" type="text" style="width:66%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
																</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">当前版本：<font color="green" style="margin-right:3px;">*</font></td>
																<td style="width:20%;">
																	<input id="appVersionInput" type="text" value="" style="width:80%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
																</td>
																<td style="width:15%;text-align:right;">是否外部链接：</td>
																<td style="width:50%;">
																	<select id="isExportSelect" style="width:66%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:7px;">
																		<option value="N">否</option>
																		<option value="Y">是</option>
																	</select>
																</td>
															</tr>
															<tr height="70px;">
																<td style="width:15%;text-align:right;">工具简介：<font color="red" style="margin-right:3px;">*</font></td>
																<td style="width:85%;" colspan="3">
																	<textarea id="appDescInput" type="text" style="width:80%;height:70px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;resize:none;"></textarea>
																</td>
															</tr>
															<tr height="40px;">
																<td style="width:15%;text-align:right;">工具语言：<font color="red" style="margin-right:3px;">*</font></td>
																<td style="width:20%;">
																	<select id="appLanguageSelection" style="width:100%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:7px;">
																		<option value="中文（CH）">中文（CH）</option>
																		<option value="英文（EN）">英文（EN）</option>
																	</select>
																</td>
																<td style="width:15%;text-align:right;">工具关键字：</td>
																<td style="width:50%;">
																	<input id="searchKeyInput" type="text" value="" placeholder="请输入关键字，多个以'；'隔开" style="width:66%;height:27px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;"></input>
																</td>
															</tr>
															<tr height="70px;">
																<td style="width:15%;text-align:right;">其他说明：<font color="green" style="margin-right:3px;">*</font></td>
																<td style="width:85%;" colspan="3">
																	<textarea id="otherDescInput" type="text" style="width:80%;height:70px;border:solid 1px #A3D0E3;border-radius:6px;margin-top:8px;resize:none;"></textarea>
																</td>
															</tr>
															<tr height="40px;">
																<td colspan="4" style="text-align:center;">
																	<a class="btn btn-warning" style="width:140px;cursor:pointer;margin-top:10px;" onclick="javascript:doSaveEdit();">
																		<i class="icon-cog icon-white"></i>保存相关配置
																	</a>
																</td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>应用展示<font color="green" style="margin-left:5px;">*</font>
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<img src="${pageContext.request.contextPath}/jsp/tools/images/img.png" style='height:400px;width:90%;margin-top:30px;' id="APP_DESC_IMAGE"></img><br/><br/>
															<button style="width:160px;background:#FFF;border:solid 1px #A3D0E3;border-radius:7px;" href="#myModal" role="button" data-toggle="modal">上传应用相关截图</button>
														</center>
													</div>
												</div>
											</div>
										</div>
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>我的已发布应用
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
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
												<span class="panle-label"></span>选择系统中预置的图标
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
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('Knowledge.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/Knowledge.png" style='height:50px;width:50px;margin-top:10px;'></img>
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
																<%--第八行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('Circle.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/Circle.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('DB2DB.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/DB2DB.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('yellowLeaf.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/yellowLeaf.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('blueFlow.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/blueFlow.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('seeData.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/seeData.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('dbHome.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/jsp/tools/images/dbHome.png" style='height:50px;width:50px;margin-top:10px;'></img>
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
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
    	<title>头像配置</title>
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
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-2
			    });
				$("#centerPanel").css({
			    	"height":$("#mainPanel").height()-$("#headPanel").height()-5
			    });
				$.ajax({
					url:"${pageContext.request.contextPath}/userManageAction/findMyIcon.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#USER_ICON").attr("src","${pageContext.request.contextPath}/extensions/index/heads/"+textStatus["iconName"]);
						}
					},
					error:function(){}
				});
			});
			function refreshIcon(iconName){
				$.ajax({
					url:"${pageContext.request.contextPath}/userManageAction/updateMyIcon.ilf",
					async:false,
					type:"POST",
					data:"newIcon="+iconName,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						$("#USER_ICON").attr("src","${pageContext.request.contextPath}/extensions/index/heads/"+iconName);
						window.wxc.xcConfirm("用户头像修改成功.",window.wxc.xcConfirm.typeEnum.info);
					},
					error:function(){}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="border:solid 1px #FFF;height:480px;" id="mainPanel">
			<div class="panel-heading" id="headPanel">
				<span class="panel-label"></span>头像配置
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<center>
								<div style="width:99.6%;height:435px;overflow:auto;border:solid 1px #FFF;" id="centerPanel">
									<div style="width:20%;float:left;">
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:95%;margin-top:3px;height:260px;">
											<div class="panletitle" style="text-align:left;width:95%;">
												<span class="panle-label"></span>当前头像
												<a class="panle-setting"></a>
											</div>
											<div class="panlecontent" style="text-align:left;width:100%;">
												<div class="pr">
													<div style="width:100%">
														<center>
															<img src="${pageContext.request.contextPath}/jsp/tools/images/toolHead.png" style='height:80px;width:80px;margin-top:70px;border-radius:50%;' id="USER_ICON"></img>
														</center>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div style="width:79%;float:left;" id="appIconEdit">
										<div class="panle panle-title panle-footer" id="js-box3-1" style="width:100%;margin-top:3px;">
											<div class="panletitle" style="text-align:left;width:99%;">
												<span class="panle-label"></span>双击更换我的头像
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
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1001.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1001.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1002.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1002.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1003.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1003.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1004.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1004.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1005.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1005.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1006.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1006.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第二行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1007.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1007.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1008.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1008.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1009.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1009.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1010.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1010.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1011.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1011.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1012.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1012.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第三行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1013.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1013.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1014.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1014.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1015.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1015.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1016.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1016.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1017.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1017.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1018.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1018.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第四行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1019.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1019.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1020.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1020.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1021.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1021.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1022.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1022.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1023.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1023.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1024.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1024.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第五行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1025.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1025.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1026.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1026.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1027.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1027.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1028.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1028.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1029.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1029.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1030.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1030.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																</tr>
																<%--第六行图标--%>
																<tr height="120">
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1031.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1031.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1032.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1032.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1033.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1033.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1034.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1034.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1035.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1035.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
																		</center>
																	</td>
																	<td style="width:15%;">
																		<center>
																			<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" ondblclick="javascript:refreshIcon('1036.png');">
																				<div style="width:100%;height:65px;margin-top:15px;">
																					<img src="${pageContext.request.contextPath}/extensions/index/heads/1036.png" style='height:50px;width:50px;margin-top:10px;'></img>
																				</div>
																			</div>
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
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
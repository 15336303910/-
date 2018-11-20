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
    	<title>互联网小工具配置</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var acheMap = new HashMap();
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#hiddenOfHeight").val()-5
			    });
				$("#appContainer").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-5
			    });
				researchIcons();
				$("#appNameKeyInput").bind("keydown",function(event){
					if(event.keyCode==13){
						researchIcons();
						return false;
					}
				});
			});
			function researchIcons(){
				var countCondition = [{
					name:"APP_NAME",
					value:$("#appNameKeyInput").val()
				}];
				$.ajax({
					url:"${pageContext.request.contextPath}/netToolAction/findCount.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"conditions="+JSON.stringify(countCondition),
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							var tableHtml = "";
							tableHtml+="<table cellpadding='0' cellspacing='0' style='width:99%;''>";
							if(textStatus["totalCount"]==0){
								tableHtml+="<tr height='120'>";
								tableHtml+="	<td style='width:10%;'>";
								tableHtml+="		<center>";
								tableHtml+="			<div style='width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;' onclick='javascript:turnToToolEdit();'>";
								tableHtml+="				<div style='margin-top:25px;'>";
								tableHtml+="					<font color='#4FA6E0' size='25'>+</font>";
								tableHtml+="				</div>";
								tableHtml+="			</div>";
								tableHtml+="		</center>";
								tableHtml+="	</td>";
								tableHtml+="	<td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td>";
								tableHtml+="	<td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td>";
								tableHtml+="	<td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td><td style='width:10%;'>&nbsp;</td>";
								tableHtml+="</tr>";
							}else{
								var totalRow = 0;
								if(parseInt(textStatus["totalCount"])%10==0){
									totalRow = parseInt(parseInt(textStatus["totalCount"])/10);
								}else{
									totalRow = parseInt(parseInt(textStatus["totalCount"])/10+1);
								}
								for(var p=1;p<=totalRow;p++){
									var pageStart = (p-1)*10;
									var pageLimit = 10;
									var conditions = [{
										name:"iDisplayStart",
										value:pageStart
									},{
										name:"iDisplayLength",
										value:pageLimit
									},{
										name:"APP_NAME",
										value:$("#appNameKeyInput").val()
									}];
									$.ajax({
										url:"${pageContext.request.contextPath}/netToolAction/findItems.ilf",
										async:false,
										type:"POST",
										dataType:"json",
										data:"conditions="+JSON.stringify(conditions),
										timeout:10000, 
										success:function(pageResult){
											if(pageResult["success"]){
												var items = pageResult["items"];
												tableHtml+="<tr height='120'>";
												for(var b=0;b<items.length;b++){
													var appObject = items[b];
													acheMap.put(appObject["ID"],appObject);
													tableHtml+="	<td style='width:10%;'>";
													tableHtml+="		<center>";
													tableHtml+="			<div style='width:95%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;' onclick='javascript:turnToToolDetail("+appObject["ID"]+");'>";
													tableHtml+="				<div style='width:100%;height:65px;margin-top:5px;'>";
													tableHtml+="					<img src='${pageContext.request.contextPath}/jsp/tools/images/"+appObject["APP_ICON"]+"' style='height:50px;width:50px;margin-top:10px;'></img>";
													tableHtml+="				</div>";
													tableHtml+="				<div style='width:100%;height:25px;margin-top:5px;'>";
													tableHtml+="					<font color='#4AA3DF' size='3'>"+appObject["APP_NAME"]+"</font>";
													tableHtml+="				</div>";
													tableHtml+="			</div>";
													tableHtml+="		</center>";
													tableHtml+="	</td>";	
												}
												if(items.length!=10 && p==totalRow){
													tableHtml+="	<td style='width:10%;'>";
													tableHtml+="		<center>";
													tableHtml+="			<div style='width:95%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;' onclick='javascript:turnToToolEdit();'>";
													tableHtml+="				<div style='margin-top:25px;'>";
													tableHtml+="					<font color='#4FA6E0' size='25'>+</font>";
													tableHtml+="				</div>";
													tableHtml+="			</div>";
													tableHtml+="		</center>";
													tableHtml+="	</td>";
													var rowIconCount = parseInt(items.length)+1;
													if((rowIconCount+1)<=10){
														for(var h=(rowIconCount+1);h<=10;h++){
															tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
														}
														tableHtml+="</tr>";
													}
												}else if(items.length==10 && p==totalRow){
													tableHtml+="</tr>";
													tableHtml+="<tr height='120'>";
													tableHtml+="	<td style='width:10%;'>";
													tableHtml+="		<center>";
													tableHtml+="			<div style='width:95%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;' onclick='javascript:turnToToolEdit();'>";
													tableHtml+="				<div style='margin-top:25px;'>";
													tableHtml+="					<font color='#4FA6E0' size='25'>+</font>";
													tableHtml+="				</div>";
													tableHtml+="			</div>";
													tableHtml+="		</center>";
													tableHtml+="	</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
													tableHtml+="	<td style='width:10%;'>&nbsp;</td>";
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
					},
					error:function(){}
				});
			}
			function turnToToolDetail(detailId){
				var detailObj = acheMap.get(detailId);
				window.parent.turnToJsp("应用详情:"+detailObj["APP_NAME"],"jsp/tools/toolDetail.jsp?detailId="+detailId);
			}
			function turnToToolEdit(){
				window.parent.turnToJsp("编辑应用","jsp/tools/toolEdit.jsp");
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
		<div class="panel panel-default" style="margin-top:1px;height:480px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>工具管理 >> 互联网工具配置
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入工具名称进行查询" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border:solid 1px #A3D0E3;border-radius:6px;" id="appNameKeyInput"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:researchIcons();"></img>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<center>
								<div style="width:99.5%;height:435px;overflow:auto;overflow:auto;border:solid 1px #FFF;" id="appContainer">
									<table cellpadding="0" cellspacing="0" style="width:99%;">
										<tr height="120">
											<td style="width:10%;">
												<center>
													<div style="width:88%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;" onclick="javascript:turnToToolEdit();">
														<div style="margin-top:25px;">
															<font color='#4FA6E0' size="25">+</font>
														</div>
													</div>
												</center>
											</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
											<td style="width:10%;">&nbsp;</td>
										</tr>
									</table>
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
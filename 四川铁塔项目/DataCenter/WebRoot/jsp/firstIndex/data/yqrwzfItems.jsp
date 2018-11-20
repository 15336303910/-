<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
	String cityName = java.net.URLDecoder.decode(request.getParameter("cityName"),"UTF-8");
	String countDate = java.net.URLDecoder.decode(request.getParameter("countDate"),"UTF-8");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>电费管理</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				flushData();
			});
			function flushData(){
				window.parent.showLoading(46,46);
				$.ajax({
					url:"${pageContext.request.contextPath}/dfServer/yqrwzfServer.ilf",
					async:true,
					type:"POST",
					data:"CITY_NAME="+$("#HIDDEN_OF_CITY_NAME").val()+"&COUNT_DATE="+$("#HIDDEN_OF_COUNT_DATE").val(),
					dataType:"json",
					success:function(textStatus){
						window.parent.hideLoading();
						if(textStatus["SUCCESS"]){
							var datas = textStatus["DATAS"];
							if(datas.length>0){
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									lineHtml+="<tr>";
									lineHtml+="	  <td>"+dataObj["PROVINCE"]+"</td>";
									lineHtml+="	  <td>"+dataObj["CITY"]+"</td>";
									lineHtml+="	  <td>"+dataObj["REGION_ID"]+"</td>";
									lineHtml+="	  <td>"+dataObj["SITE_CODE"]+"</td>";
									lineHtml+="	  <td>"+dataObj["SITE_NAME"]+"</td>";
									lineHtml+="	  <td>"+dataObj["SITE_TYPE"]+"</td>";
									lineHtml+="	  <td>"+dataObj["SITE_LEVEL"]+"</td>";
									lineHtml+="	  <td>"+dataObj["STATE"]+"</td>";
									lineHtml+="</tr>";
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;
							}
						}
					},
					error:function(){
						window.parent.hideLoading();
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.9%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
  		<input type="hidden" id="HIDDEN_OF_CITY_NAME" value="<%=cityName %>"></input>
  		<input type="hidden" id="HIDDEN_OF_COUNT_DATE" value="<%=countDate %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">>> 已确认未支付站</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;">省份</td>
										<td style="background:#6BC2EB;">地市</td>
										<td style="background:#6BC2EB;">区县</td>
										<td style="background:#6BC2EB;">站址编码</td>
										<td style="background:#6BC2EB;">站址名称</td>
										<td style="background:#6BC2EB;">类型</td>
										<td style="background:#6BC2EB;">级别</td>
										<td style="background:#6BC2EB;">状态</td>
									</tr>
								</thead>
								<tbody id="COUNTS_TABLE">
									<tr><td colspan="8" style="text-align:center;">暂无数据</td></tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
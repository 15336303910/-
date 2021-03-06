<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
   		<meta name="viewport" content="width=device-width, initial-scale=1">
    	<base href="<%=basePath%>">
    	<title>在线风控参数配置编辑</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript">
		var qu_id="";
			$(document).ready(function(){
				 qu_id=<%=request.getParameter("id")%>;//这里面的qu_id类似你的id
				//查询ID对应的数据
				$.ajax({
					url:"${pageContext.request.contextPath}/ParameterConfigurationAction/findParameterConfigurationEditChaDatas.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:{"num":qu_id},
					timeout:10000,
					success:function(riskData){
						if(riskData["success"]){
							var list=riskData["fund_list"];
							$("#r_name").html(list[0]["RISK_NAME"]);
							$("#p_name").html(list[0]["PARA_NAME"]);
							$("#r_type").html(list[0]["RISK_TYPE"]);
							$("#shuru").val(list[0]["PARA_VALUE"]);
						}
					}
				});
			
			});
			
			//更新点击保存后ID对应的数据
			function insert(){
				var t=document.getElementById("shuru").value;
				
				$.ajax({
					url:"${pageContext.request.contextPath}/ParameterConfigurationAction/findParameterConfigurationEditGxDatas.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:{"wb_id":qu_id,"wb_value":t},
					timeout:10000,
					success:function(data){
						if(data["success"]){
							alert("保存成功!");
						}
					}
				});
				
			}
			
		</script>
		<style type="text/css">
			.table th,.table td{
				text-align:center
			}
			.table tbody tr td{
				vertical-align:middle
			}
		</style>
		
  	</head>
  	<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:-20px;">
		<div class="panel panel-primary"  id="mainPanel">
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font color="white" size="5">参数配置编辑</font>
				</div>
			</div>
			
			<div class="panel-body">
				<table style="width:100%;">
					<tr height="50px">
						<td style="width:30%;text-align:center;vertical-align:middle;">风险点名称：</td>
						<td><span id="r_name">-</span></td>
					</tr>
					<tr height="50px">
						<td style="width:30%;text-align:center;vertical-align:middle;"><span id="p_name"></span></td>
						<td>
							<input id="shuru" type="text" name="fname" style="width:90px; height:30px;"/>
						</td>
					</tr>
					<tr height="50px">
						<td style="width:30%;text-align:center;vertical-align:middle;">风险点类型：</td>
						<td><span id="r_type">-</span></td>
					</tr>
					<tr height="50px">
						<td colspan="2" style="width:30%;text-align:center;vertical-align:middle;">
							<button type="button" onclick="insert()" class="btn btn-success">保存</button>
							<button type="button"  class="btn btn-danger">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>	
	</body>
</html>
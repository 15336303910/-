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
				$.ajax({
					url:"${pageContext.request.contextPath}/eleCostManage/findTypes.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					success:function(textStatus){
						if(textStatus["success"]){
							/*地市选择下拉框.*/
							var options = textStatus["OPTIONS"];
							var typeSelect = "";
							typeSelect+="<select style='width:180px;height:29px;border:solid 1px #A3D0E3;' id='typeSelect'>";
							typeSelect+="	 <option value='-'>全部</option>";
							for(var i=0;i<options.length;i++){
								var typeName = options[i];
								typeSelect+="<option value='"+typeName["OPER"]+"'>"+typeName["OPER"]+"</option>";
							}
							typeSelect+="</select>";
							document.getElementById("typeSelection").innerHTML = typeSelect;
							/*刷新数据.*/
							flushData();
						}
					},
					error:function(){}
				});
			});
			function flushData(){
				window.parent.showCircleLoading();
				$.ajax({
					url:"${pageContext.request.contextPath}/eleCostManage/findDatas.ilf",
					async:true,
					type:"POST",
					data:"RUN_TYPE="+$("#typeSelect").val()+"&COUNT_DATE="+$("#designedDate").val(),
					dataType:"json",
					success:function(textStatus){
						window.parent.hideCircleLoading();
						if(textStatus["SUCCESS"]){
							var datas = textStatus["DATAS"];
							if(datas.length>0){
								var date = new Date();
								var nowMonth = date.getFullYear()+"年"+date.getMonth()+"月";
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									lineHtml+="<tr>";
									lineHtml+="	  <td>"+dataObj["地区"]+"</td>";
									lineHtml+="	  <td>"+dataObj["站址数"]+"</td>";
									if(dataObj["已确认电费站数"]>0){
										lineHtml+="	  <td><a style='cursor:pointer;' onclick='javascript:turnToYQRS(\""+dataObj["地区"]+"\");'>"+dataObj["已确认电费站数"]+"</a></td>";
									}else{
										lineHtml+="	  <td>"+dataObj["已确认电费站数"]+"</td>";	
									}
									if(dataObj["已确认未支付站数"]>0){
										lineHtml+="	  <td><a style='cursor:pointer;' onclick='javascript:turnToYQRWZF(\""+dataObj["地区"]+"\");'>"+dataObj["已确认未支付站数"]+"</a></td>";
									}else{
										lineHtml+="	  <td>"+dataObj["已确认未支付站数"]+"</td>";	
									}
									if(dataObj["已确认已支付站数"]>0){
										lineHtml+="	  <td><a style='cursor:pointer;' onclick='javascript:turnToYQRYZF(\""+dataObj["地区"]+"\");'>"+dataObj["已确认已支付站数"]+"</a></td>";
									}else{
										lineHtml+="	  <td>"+dataObj["已确认已支付站数"]+"</td>";	
									}
									if(dataObj["未确认站数"]>0){
										lineHtml+="	  <td><a style='cursor:pointer;' onclick='javascript:turnToWQR(\""+dataObj["地区"]+"\");'>"+dataObj["未确认站数"]+"</a></td>";
									}else{
										lineHtml+="	  <td>"+dataObj["未确认站数"]+"</td>";	
									}
									if(dataObj["未确认已支付站数"]>0){
										lineHtml+="	  <td><a style='cursor:pointer;' onclick='javascript:turnToWQRYZF(\""+dataObj["地区"]+"\");'>"+dataObj["未确认已支付站数"]+"</a></td>";
									}else{
										lineHtml+="	  <td>"+dataObj["未确认已支付站数"]+"</td>";	
									}
									lineHtml+="	  <td>"+dataObj["未确认已支付占比"]+"</td>";
									lineHtml+="	  <td class='DATE_CLASS'>"+nowMonth+"</td>";
									lineHtml+="</tr>";
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;
								var designDate = $("#designedDate").val();
								if(designDate!=null && designDate!=""){
									$(".DATE_CLASS").html(designDate);
								}
							}
						}
					},
					error:function(){
						window.parent.hideCircleLoading();
					}
				});
			}
			function turnToYQRS(cityName){
				window.parent.addTabForJsp(cityName+":已确认站","jsp/firstIndex/data/yqrzItems.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&countDate="+encodeURIComponent(encodeURIComponent($("#designedDate").val(),"UTF-8"),"UTF-8"));
			}
			function turnToYQRWZF(cityName){
				window.parent.addTabForJsp(cityName+":已确认未支付站","jsp/firstIndex/data/yqrwzfItems.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&countDate="+encodeURIComponent(encodeURIComponent($("#designedDate").val(),"UTF-8"),"UTF-8"));
			}
			function turnToYQRYZF(cityName){
				window.parent.addTabForJsp(cityName+":已确认已支付站","jsp/firstIndex/data/yqryzfItems.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&countDate="+encodeURIComponent(encodeURIComponent($("#designedDate").val(),"UTF-8"),"UTF-8"));
			}
			function turnToWQR(cityName){
				window.parent.addTabForJsp(cityName+":未确认站","jsp/firstIndex/data/wqrzItems.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&countDate="+encodeURIComponent(encodeURIComponent($("#designedDate").val(),"UTF-8"),"UTF-8"));
			}
			function turnToWQRYZF(cityName){
				window.parent.addTabForJsp(cityName+":未确认已支付站","jsp/firstIndex/data/wqryzfItems.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&countDate="+encodeURIComponent(encodeURIComponent($("#designedDate").val(),"UTF-8"),"UTF-8"));
			}
		</script>
  	</head>
  	<body style="width:99.9%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">>> 电费管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-right:3px;margin-top:6px;" id="typeSelection">
							<select style="width:180px;height:29px;border:solid 1px #A3D0E3;" id="typeSelect">
								<option value="-">-</option>
							</select>
						</div>
						<input id="designedDate" type="text" placeholder="请选择统计日期" style="cursor:pointer;border:solid 1px #A3D0E3;width:150px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy年MM月'})" class="Wdate"></input>
						<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:flushData();">
							<i class="icon-search icon-white"></i>执行统计
						</a>
						<a class="btn btn-primary" style="cursor:pointer;" onclick="javascript:window.parent.linkToGdSystem(1);">
							<input type="hidden" id="hiddenCode" value=""></input>
							<i class="icon-plus-sign icon-white"></i>发起电费确认工单
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;">地市/区县</td>
										<td style="background:#6BC2EB;">站址数</td>
										<td style="background:#6BC2EB;">已确认电费站数</td>
										<td style="background:#6BC2EB;">已确认未支付站数</td>
										<td style="background:#6BC2EB;">已确认已支付站数</td>
										<td style="background:#6BC2EB;">未确认站数</td>
										<td style="background:#6BC2EB;">未确认已支付站数</td>
										<td style="background:#6BC2EB;">未确认已支付占比</td>
										<td style="background:#6BC2EB;">统计日期</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="text-align:center;">-</td>
										<td style="color:red;">取稽核系统有效站址数</td>
										<td style="color:red;">运营商已确认电费的站址数</td>
										<td style="color:red;">运营商已确认电费但尚未支付站址数</td>
										<td style="color:red;">运营商未确认站址数</td>
										<td style="color:red;">运营商未确认但已支付电费站址数</td>
										<td style="color:red;">运营商已确认并已支付站址数</td>
										<td style="color:red;">未确认已支付站数/站址数*100%</td>
										<td style="text-align:center;">-</td>
									</tr>
								</tbody>
								<tbody id="COUNTS_TABLE">
									<tr><td colspan="8"></td></tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
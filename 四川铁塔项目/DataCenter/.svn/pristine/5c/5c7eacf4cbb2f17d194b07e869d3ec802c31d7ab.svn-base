<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
	String designedDate = request.getParameter("designedDate");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>枚举标准表</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#PICK_TITLE").html("枚举标准："+$("#hiddenOfDates").val());
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				initPage();
			});
			var dataMap = null;
			function initPage(){
				var thisDate = $("#hiddenOfDates").val();
				/*
					根据日期获取SC标准表详情.
				 */
				window.parent.showLoading(47,45);
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findEnumDetailOfDate.ilf",
					async:false,
					type:"POST",
					data:"designedDate="+thisDate,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.hideLoading();
						if(textStatus["SUCCESS"]){
							dataMap = new HashMap();
							var Datas = textStatus["DATAS"];
							var innerHtml = "";
							for(var a=0;a<Datas.length;a++){
								var rowData = Datas[a];
								dataMap.put(rowData["ID"],rowData);
								innerHtml+="<tr style='height:30px;'>";
								innerHtml+="	<td>"+(rowData["ENUM_NAME"]==null?"&nbsp;":rowData["ENUM_NAME"])+"</td>";
								innerHtml+="	<td>"+(rowData["COLUMN_NAME"]==null?"&nbsp;":rowData["COLUMN_NAME"])+"</td>";
								innerHtml+="	<td>"+(rowData["DATA_SOURCE"]==null?"&nbsp;":rowData["DATA_SOURCE"])+"</td>";
								innerHtml+="	<td>"+(rowData["IS_SURE"]==null?"<font color='red'>未确认</font>":"<font color='green'>已确认</font>")+"</td>";
								innerHtml+="	<td>"+(rowData["CHECKING_DATE"]==null?"&nbsp;":rowData["CHECKING_DATE"])+"</td>";
								innerHtml+="	<td>"+(rowData["SURE_USER"]==null?"&nbsp;":rowData["SURE_USER"])+"</td>";
								innerHtml+="	<td style='width:400px;'><input id='CONTENT_"+rowData["ID"]+"' type='text' style='width:400px;height:25px;font-size:11px;border:dotted 1px #A3D0E3;' value='"+(rowData["CONTENTS"]==null?"":rowData["CONTENTS"])+"'></input></td>";
								innerHtml+="</tr>";
							}
							document.getElementById("ITEM_DATAS").innerHTML = innerHtml;
						}
					},
					error:function(){
						window.parent.hideLoading();
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfDates" value="<%=designedDate %>"></input>
		<div class="panel panel-default" style="margin-top:1px;">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span><font id="PICK_TITLE">&nbsp;</font>
			</div>
			<div class="panel-body h340" style="border:0px;height:410px;overflow:auto;margin-top:3px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<table id="SC_TABLE" class="table table-bordered table-hover" style="height:60px;border:solid 0px red;margin-bottom:10px;">
								<thead>
									<tr>
										<td style="background:#FFC000;text-align:center;">枚举值名称</td>
										<td style="background:#FFC000;text-align:center;">字段名称</td>
										<td style="background:#FFC000;text-align:center;">标准来源系统</td>
										<td style="background:#FFC000;text-align:center;">是否确认</td>
										<td style="background:#FFC000;text-align:center;">确认时间</td>
										<td style="background:#FFC000;text-align:center;">确认人</td>
										<td style="background:#FFC000;text-align:center;">备注</td>
									</tr>
								<thead>
								<tbody id="ITEM_DATAS">
									<tr>
										<td colspan="7" style="text-align:center;">暂无数据</td>
									</tr>
								</tbody>
							</table>						
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
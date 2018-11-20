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
    	<title>在线风控风险点已反馈问题详情</title>
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
			$(document).ready(function(){
				var riskType='<%=request.getParameter("riskType")%>';
				riskType = decodeURI(riskType);
				var city='<%=request.getParameter("city")%>';
				city = decodeURI(city);
				var mouth='<%=request.getParameter("mouth")%>';
				var account='<%=request.getParameter("account")%>';
				$("#city").val(city);
				$("#date").val(mouth);
				$("#question_amount").html(account);
				$.ajax({
					url:"${pageContext.request.contextPath}/feedBackQuestionAmountAction/findDetail.ilf",
					type:"POST",
					async:true,
					data:{"riskType":riskType,"city":city,"mouth":mouth},
					timeout:10000,
					success:function(params){
						if(params["success"]){
							/*获取到具体问题、具体月份、具体地市的数据list，填充到表格的th跟td*/
							var list=params["list"];
							$("#table_td").children('td').eq(0).html(riskType);
							var th_str="";
							var td_str="";
							for(var i=0;i<list.length;i++){
								th_str+="<th>"+list[i].RISK_NAME+"</th>";
								td_str+="<td>"+list[i].AMOUNT+"</td>";
							}
							$("#table_th").append(th_str);
							$("#table_td").append(td_str);	
						}
					}
				});
			});
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
					<font color="white" size="5">在线风控--风险点已反馈问题详情</font>
				</div>
			</div>
			
			<div class="panel-body">
				<div style="width:100%;height:50px">
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>地市：</span>
						<input id="city" type="text" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" value="-" readonly="readonly"></input>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>日期：</span>
						<input id="date" type="text" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" value="-" readonly="readonly"></input>
					</div>
				</div>
				
			 	<div class="col-xs-12 col-sm-12 col-md-12"
					style="margin-top:15px">
					<div style="width:100%;height:40px;">
						<font size="4">风险点已反馈数量:</font>
						<button type="button" class="btn btn-danger btn-sm" style="width:70px;"><span id="question_amount">7332</span></button>
					</div>
					<table id="table_detail" class="table table-bordered table-hover display"
						style='width:100%;vertical-align: middle; text-align: center;'>
						<div style="width:100%;height:6px"></div>
						<thead width="100%">
							<tr id="table_th">
								<th>风险点类型</th>
							</tr>
						</thead>
						<tbody>
							<tr id="table_td">
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>	
	</body>
</html>
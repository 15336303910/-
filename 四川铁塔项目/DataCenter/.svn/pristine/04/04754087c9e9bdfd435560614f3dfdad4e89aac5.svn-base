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
    	<title>指标责任划分</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">	
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				var tableHeight = $("#bodyHeight").height()-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
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
					"sScrollY":tableHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"8%"
					},{
						"mData":"USER_NAME",
						"sWidth":"14%"
					},{
						"mData":"IS_LOCKED",
						"sWidth":"14%"
					},{
						"mData":"EMPLOYEE_NAME",
						"sWidth":"14%"
					},{
						"mData":"EMPLOYEE_COMPANY",
						"sWidth":"31.7%"
					},{
						"mData":"EMPLOYEE_PHONE",
						"sWidth":"17.6%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						$("td:eq(1)",nRow).html("<font color='blue'>"+aData["USER_NAME"]+"</font>");
						if(aData["IS_LOCKED"]=="N"){
							$("td:eq(2)",nRow).html("<font color='green'>正常</font>");	
						}else{
							$("td:eq(2)",nRow).html("<font color='red'>锁定</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/userManageAction/findUseraccounts.ilf"
				});
				$("#dataTable tbody").click(function(event) {
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class") == "even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				$("#userAccountInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
			});
			function getData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			};
			function searchData(){
				var roleName = $("#userAccountInput").val();
				if(roleName!=""){
					conditions = [{
						name:"USER_NAME",
						value:roleName
					}];	
				}else{
					conditions = [];
				}
				oTable.fnDraw();
			}
			function openEditWindow(){
				var checkedCode = getCheckedValue("checksRadio");
				if(checkedCode==null){
					window.wxc.xcConfirm("请选择一个用户进行维护.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.parent.turnToJsp("指标责任划分编辑","jsp/indexes/edit/editDomain.jsp?userId="+checkedCode);	
				}
			}
			function auditChart(){
				var checkedCode = getCheckedValue("checksRadio");
				if(checkedCode==null){
					window.wxc.xcConfirm("请选择一个账户进行分析.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.parent.turnToJsp("指标责任分析","jsp/indexes/chart/auditChart.jsp?userId="+checkedCode);	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;">
			<div class="panel-heading" id="panelHeading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>指标管理 >> 划分指标责任
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入用户账号" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="userAccountInput"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:searchData();"></img>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:openEditWindow();">
							<i class="icon-plus-sign icon-white"></i>划分指标责任
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:auditChart();">
							<i class="icon-signal icon-white"></i>指标责任分析
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body">
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
	</body>
</html>
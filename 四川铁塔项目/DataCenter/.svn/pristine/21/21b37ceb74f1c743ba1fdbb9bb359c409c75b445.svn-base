<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String compareId = request.getParameter("compareId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>数据比对</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
				conditions = [{
					name:"BELONG_COMPARE",
					value:$("#hiddenOfCompareCode").val()
				}];
				$.ajax({
					url:"${pageContext.request.contextPath}/compareDetailAction/isLoginProvince.ilf",
					async:false,
					type:"POST",
					data:"compareId="+$("#hiddenOfCompareCode").val(),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							/*比对规则信息*/
							var bdRule = textStatus["COMPARE_DETAIL"];
							if(bdRule!=null){
								$("#BD_RULE_TITLE").html("数据比对 >> 分批次查看比对结果："+bdRule["RULE_DESC"]);	
							}
							/*是否省级用户*/
							if(textStatus["IS_PROVINCE"]){
								$(".MANAGER_BUTTONS").show(300);
							}else{
								$(".MANAGER_BUTTONS").hide(300);
							}
						}
					},
					error:function(){}
				});
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":false,
					"bProcessing":true,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":false,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"99.9%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"7%"
					},{
						"mData":"START_DATE",
						"sWidth":"11%"
					},{
						"mData":"IS_FINISHED",
						"sWidth":"8%"
					},{
						"mData":"ISFATAL",
						"sWidth":"8%"
					},{
						"mData":"NOT_UNIFORM",
						"sWidth":"10%"
					},{
						"mData":"AONLY",
						"sWidth":"10%"
					},{
						"mData":"ZONLY",
						"sWidth":"10%"
					},{
						"mData":"AFATUAL",
						"sWidth":"10%"
					},{
						"mData":"ZFATUAL",
						"sWidth":"9%"
					},],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						var longTime = aData["START_DATE"].time;
						var date = new Date();
						date.setTime(longTime);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						$("td:eq(1)",nRow).html("<center>"+date.toLocaleString()+"</center>");
						if(aData["IS_FINISHED"]=="Y"){
							$("td:eq(2)",nRow).html("<center><font color='green'>已完成</font></center>");
						}else{
							$("td:eq(2)",nRow).html("<center><font color='blue'>进行中</font></center>");
						}
						if(aData["ISFATAL"]=="Y"){
							$("td:eq(3)",nRow).html("<center><font color='red'>中止</font></center>");
						}else{
							$("td:eq(3)",nRow).html("<center><font color='green'>正常</font></center>");
						}
						if(aData["NOT_UNIFORM"]!=null && aData["NOT_UNIFORM"]>0){
							$("td:eq(4)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+",\"IS_UNIFORM\","+aData["NOT_UNIFORM"]+");' style='cursor:pointer;color:red;'>"+aData["NOT_UNIFORM"]+"</a>");
						}else{
							$("td:eq(4)",nRow).html("<font color='green'>-</font>");
						}
						if(aData["AONLY"]!=null && aData["AONLY"]>0){
							$("td:eq(5)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+",\"IS_A_ONLY\","+aData["AONLY"]+");' style='cursor:pointer;color:red;'>"+aData["AONLY"]+"</a>");
						}else{
							$("td:eq(5)",nRow).html("<font color='green'>-</font>");
						}
						if(aData["ZONLY"]!=null && aData["ZONLY"]>0){
							$("td:eq(6)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+",\"IS_Z_ONLY\","+aData["ZONLY"]+");' style='cursor:pointer;color:red;'>"+aData["ZONLY"]+"</a>");
						}else{
							$("td:eq(6)",nRow).html("<font color='green'>-</font>");
						}
						if(aData["AFATUAL"]!=null && aData["AFATUAL"]>0){
							$("td:eq(7)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+",\"IS_A_FATAL\","+aData["AFATUAL"]+");' style='cursor:pointer;color:red;'>"+aData["AFATUAL"]+"</a>");
						}else{
							$("td:eq(7)",nRow).html("<font color='green'>-</font>");
						}
						if(aData["ZFATUAL"]!=null && aData["ZFATUAL"]>0){
							$("td:eq(8)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+",\"IS_Z_FATAL\","+aData["ZFATUAL"]+");' style='cursor:pointer;color:red;'>"+aData["ZFATUAL"]+"</a>");
						}else{
							$("td:eq(8)",nRow).html("<font color='green'>-</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/compareDetailAction/findCountItems.ilf"
				});
				setTimeout("javascript:doFlush();",30000);
			});
			function doFlush(){
				conditions = [{
					name:"BELONG_COMPARE",
					value:$("#hiddenOfCompareCode").val()
				}];
				oTable.fnDraw();
				setTimeout("javascript:doFlush();",30000);
			}
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
			}
			function problemDetail(monitorId,resultType,fatalCount){
				/*
					1.IS_UNIFORM
					2.IS_A_ONLY
					3.IS_Z_ONLY
					4.IS_A_FATAL
					5.IS_Z_FATAL
				 */
				window.parent.turnToJsp("问题数据详情","jsp/dataBd/prolDetails.jsp?fatalCount="+fatalCount+"&monitorId="+monitorId+"&resultType="+resultType);
			}
			function deleteJob(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null){
					window.wxc.xcConfirm("请选择一项批次进行删除操作.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("删除此批次任务同时也将删除问题数据明细.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/compareDetailAction/deleteJobItem.ilf",
								async:false,
								type:"POST",
								data:"monitorId="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("比对任务删除成功.",window.wxc.xcConfirm.typeEnum.info);
										doFlush();
									}else{
										window.wxc.xcConfirm("比对任务删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("比对任务删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:99.3%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfCompareCode" value="<%=compareId %>"></input>
		<div class="panel panel-default" style="margin-top:1px;">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span><font id="BD_RULE_TITLE">数据比对 >> 分批次查看比对结果</font>
				<form class="form-search pull-right">	
					<div style="float:right;">
						<a class="btn btn-danger MANAGER_BUTTONS"  style="cursor:pointer;margin-top:5px;" onclick="javascript:deleteJob();">
							<i class="icon-trash icon-white"></i>删除任务
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<td style="text-align:center;background:#FCFDFE;">&nbsp;</td>
										<td style="text-align:center;background:#FCFDFE;">比对批次</td>
										<td style="text-align:center;background:#FCFDFE;">执行状态</td>
										<td style="text-align:center;background:#FCFDFE;">正常/异常中止</td>
										<td style="text-align:center;background:#FCFDFE;"><font color='red'>数据不一致</font></td>
										<td style="text-align:center;background:#FCFDFE;"><font color='red'>仅C(参考)表有数据</font></td>
										<td style="text-align:center;background:#FCFDFE;"><font color='red'>仅S(标准)表有数据</font></td>
										<td style="text-align:center;background:#FCFDFE;"><font color='red'>C(参考)表数据异常</font></td>
										<td style="text-align:center;background:#FCFDFE;"><font color='red'>S(标准)表数据异常</font></td>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
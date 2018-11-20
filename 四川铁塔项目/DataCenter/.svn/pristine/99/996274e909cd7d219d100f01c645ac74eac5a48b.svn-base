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
			function deleteWindow(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null){
					window.wxc.xcConfirm("请选择一条规则进行删除操作.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确定要删除规则？..","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/compareDetailAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"itemCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("比对规则删除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("比对规则删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("比对规则删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}
			}
			function turnEdit(){
				var ruleId = -1;
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					ruleId = checkedValue;
				}
				window.parent.turnToJsp("数据比对配置","jsp/dataBd/editRule.jsp?ruleId="+ruleId);
			}
			function turnCount(){
				var compareId = -1;
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					compareId = checkedValue;
					window.parent.turnToJsp("比对结果统计","jsp/dataBd/dataCount.jsp?compareId="+compareId);	
				}else{
					window.wxc.xcConfirm("请先选择一条比对规则.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$.ajax({
					url:"${pageContext.request.contextPath}/compareDetailAction/isLoginProvince.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"compareId=-1",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							if(textStatus["IS_PROVINCE"]){
								$(".MANAGER_BUTTONS").show(300);
							}else{
								$(".MANAGER_BUTTONS").hide(300);
							}
						}
					},
					error:function(){}
				});
				/*表格高度及容量*/
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/26);
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
					"sScrollX":"99%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"7%"
					},{
						"mData":"RULE_CODE",
						"sWidth":"20%"
					},{
						"mData":"RULE_NAME",
						"sWidth":"20%"
					},{
						"mData":"CREATE_DATE",
						"sWidth":"13%"
					},{
						"mData":"RULE_DESC",
						"sWidth":"38%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						var date = new Date();
						if(aData["CREATE_DATE"]!=null){
							date.setTime(aData["CREATE_DATE"]["time"]);
							$("td:eq(3)",nRow).html("<center>"+date.toLocaleString()+"</center>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/compareDetailAction/findItems.ilf"
				});
				$("#compareRuleName").bind("keydown",function(event){
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
				var compareRuleName = $("#compareRuleName").val();
				if(compareRuleName!=""){
					conditions = [{
						name:"RULE_NAME",
						value:compareRuleName
					}];	
				}else{
					conditions = [];
				}
				oTable.fnDraw();
			}
			function initRuleInfo(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null){
					$("#ruleNameText").html("&nbsp;");
					$("#aRuleNameText").html("&nbsp;");
					$("#zRuleNameText").html("&nbsp;");
					$("#zRuleDescText").html("&nbsp;");
					unCheckAllBoxes("CHECK_TYPES");
					var str = "";
					str+="<font color='blue'>*&nbsp;标识属性定义</font>";
					str+="<table class='table table-bordered table-hover' style='margin-top:3px;'>";
					str+="	  <thead>";
					str+="		  <tr>";
					str+="			  <td style='background:#E8F2FE;text-align:center;'>模型A属性</td>";
					str+="			  <td style='background:#E8F2FE;text-align:center;'>模型B属性</td>";
					str+="		  </tr>";
					str+="	  </thead>";
					str+="	  <tbody>";
					str+="		  <tr>";
					str+="			  <td colspan='2' style='text-align:center;'>暂无匹配信息</td>";
					str+="		  </tr>";
					str+="	  <tbody>";
					str+="</table>";
					document.getElementById("matchColumnContainer").innerHTML = str;
					$("#connectColumnName").html("&nbsp;");
				}else{
					var isFullScreen = true;
					window.parent.loading(isFullScreen);
					$.ajax({
						url:"${pageContext.request.contextPath}/compareDetailAction/findOne.ilf",
						async:false,
						type:"POST",
						data:"compareRuleId="+checkedValue,
						dataType:"json",
						timeout:10000, 
						success:function(textStatus){
							window.parent.overLoading(isFullScreen);
							if(textStatus["success"]){
								/*比对规则名称*/
								$("#ruleNameText").html(textStatus["RULE_NAME"]);
								/*核查表名称*/
								$("#aRuleNameText").html(textStatus["A_TABLE_NAME"]);
								/*参照表名称*/
								$("#zRuleNameText").html(textStatus["Z_TABLE_NAME"]);
								/*比对描述*/
								$("#zRuleDescText").html(textStatus["RULE_DESC"]);
								/*是否比对一致性、是否比对仅A端有数据、是否比对仅Z端有数据*/
								if(textStatus["IS_UNIFORM"]=="Y"){
									checkMultiValue("CHECK_TYPES","IS_UNIFORM");
								}else{
									unCheckMultiValue("CHECK_TYPES","IS_UNIFORM");
								}
								if(textStatus["IS_A_ONLY"]=="Y"){
									checkMultiValue("CHECK_TYPES","IS_A_ONLY");
								}else{
									unCheckMultiValue("CHECK_TYPES","IS_A_ONLY");
								}
								if(textStatus["IS_Z_ONLY"]=="Y"){
									checkMultiValue("CHECK_TYPES","IS_Z_ONLY");
								}else{
									unCheckMultiValue("CHECK_TYPES","IS_Z_ONLY");
								}
								if(textStatus["IS_A_FATUAL"]=="Y"){
									checkMultiValue("CHECK_TYPES","IS_A_FATUAL");
								}else{
									unCheckMultiValue("CHECK_TYPES","IS_A_FATUAL");
								}
								if(textStatus["IS_Z_FATUAL"]=="Y"){
									checkMultiValue("CHECK_TYPES","IS_Z_FATUAL");
								}else{
									unCheckMultiValue("CHECK_TYPES","IS_Z_FATUAL");
								}
								/*效用字段*/
								$("#connectColumnName").html(textStatus["CONNECT_COLUMN_ALIAS"]);
								/*匹配列表*/
								var str = "";
								str+="<font color='blue'>*&nbsp;标识属性定义</font>";
								str+="<table class='table table-bordered table-hover' style='margin-top:3px;'>";
								str+="	<thead>";
								str+="		<tr>";
								str+="			<td style='background:#E8F2FE;text-align:center;'>模型A属性</td>";
								str+="			<td style='background:#E8F2FE;text-align:center;'>模型B属性</td>";
								str+="		</tr>";
								str+="	</thead>";
								str+="	<tbody>";
								var matchedColumns = textStatus["MATCHED_COLUMNS"];
								for(var i=0;i<matchedColumns.length;i++){
									var matchColumn = matchedColumns[i];
									str+="<tr>";
									str+="	<td>"+matchColumn["A_COLUMN_ALIAS"]+"</td>";
									str+="	<td>"+matchColumn["Z_COLUMN_ALIAS"]+"</td>";
									str+="</tr>";
								}
								str+="	<tbody>";
								str+="</table>";
								document.getElementById("matchColumnContainer").innerHTML = str;
							}
						},
						error:function(){
							window.parent.overLoading(isFullScreen);
						}
					});
				}
			}
			function auditRightNow(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条比对规则.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					window.wxc.xcConfirm("是否确认立即执行此比对规则？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/dataCompareAction/compareRightNow.ilf",
								async:true,
								type:"POST",
								data:"compareId="+checkedValue,
								dataType:"json",
								timeout:10000,
								success:function(textStatus){},
								error:function(){}
							});
						}
					});	
				}
			}
			function turnToBarChart(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条比对规则.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.parent.turnToJsp("比对结果分析","jsp/dataBd/chart/showInColumn.jsp?ruleId="+checkedValue);
				}
			}
		</script>
  	</head>
  	<body style="width:99.3%;border:solid 1px #FFF;margin-left:3px;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:1px;">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>数据比对 >> 比对规则管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入规则名称进行查询" style="cursor:pointer;width:230px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border:solid 1px #A3D0E3;border-top-left-radius:6px;border-bottom-left-radius:6px;" id="compareRuleName"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:searchData();"></img>
						<a class="btn btn-success" href="#countModal" role="button" data-toggle="modal" style="cursor:pointer" onclick="javascript:initRuleInfo();">
							<i class="icon-bookmark icon-white"></i>比对说明
						</a>
						<a class="btn btn-inverse MANAGER_BUTTONS" style="cursor:pointer" onclick="javascript:turnEdit();">
							<i class="icon-plus-sign icon-white"></i>编辑数据比对
						</a>
						<a class="btn btn-danger MANAGER_BUTTONS"  style="cursor:pointer" onclick="javascript:deleteWindow();">
							<i class="icon-trash icon-white"></i>删除规则
						</a>
						<a class="btn btn-success MANAGER_BUTTONS" style="cursor:pointer" onclick="javascript:auditRightNow();">
							<i class="icon-play-circle icon-white"></i>立即执行
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:turnCount();">
							<i class="icon-signal icon-white"></i>查看比对结果
						</a> 
						<%--<a class="btn btn-info" style="cursor:pointer" onclick="javascript:turnToBarChart();">
							<i class="icon-signal icon-white"></i>比对结果分析
						</a>--%>
					</div>
				</form>
			</div>
			<div class="panel-body h340" style="border:solid 1px #DFE8F1;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<td>&nbsp;</td>
										<td style="text-align:center;background:#FCFDFE;">编码</td>
										<td style="text-align:center;background:#FCFDFE;">比对规则名称</td>
										<td style="text-align:center;background:#FCFDFE;">创建日期</td>
										<td style="text-align:center;background:#FCFDFE;">描述</td>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="countModal" class="modal hide fade" style="width:650px;height:450px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 比对规则说明</h3>
			</div>
			<div class="modal-body" style="max-height:330px;">
				<div style="width:100%;height:330px;">
					<div style="float:left;width:49.5%;height:235px;">
						<div style="width:99.9%;height:115px;">
							<font color="blue">*&nbsp;比对模型信息</font>
							<table class="table table-bordered table-hover" style="margin-top:3px;">
								<tbody>
									<tr>
										<td style="width:25%;background:#E8F2FE;text-align:right;">规则名称:</td>
										<td style="width:75%;color:red;" id="ruleNameText">&nbsp;</td>
									</tr>
									<tr>
										<td style="background:#E8F2FE;text-align:right;">比对模型A:</td>
										<td id="aRuleNameText" style="color:red;">&nbsp;</td>
									</tr>
									<tr>
										<td style="background:#E8F2FE;text-align:right;">比对模型z:</td>
										<td id="zRuleNameText" style="color:red;">&nbsp;</td>
									</tr>
									<tr height="117">
										<td style="background:#E8F2FE;text-align:right;">规则描述:</td>
										<td id="zRuleDescText" style="color:red;">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div style="float:left;width:49%;height:235px;margin-left:5px;">
						<div style="width:99.9%;height:150px;overflow:auto;" id="matchColumnContainer">
							<font color="blue">*&nbsp;标识属性定义</font>
							<table class="table table-bordered table-hover" style="margin-top:3px;">
								<thead>
									<tr>
										<td style="background:#E8F2FE;text-align:center;">模型A属性</td>
										<td style="background:#E8F2FE;text-align:center;">模型Z属性</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan="2" style="text-align:center;color:red;">暂无匹配信息</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div style="width:99.9%;height:90px;overflow:auto;">
							<font color="blue">*&nbsp;效用属性定义</font>
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#E8F2FE;text-align:center;">效用属性</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td id="connectColumnName">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div style="float:left;width:99%;height:85px;margin-top:5px;">
						<font color="blue">*&nbsp;比对结果类型定义</font>
						<table class="table table-bordered table-hover" style="margin-top:3px;border:0px;">
							<tbody>
								<tr>
									<td style="width:25%;border:0px;">
										<input type="checkbox" value="IS_UNIFORM" name="CHECK_TYPES"></input><font style="margin-left:10px;">数据是否一致</font>
									</td>
									<td style="width:25%;border:0px;">
										<input type="checkbox" value="IS_A_ONLY" name="CHECK_TYPES"></input><font style="margin-left:10px;">仅A端有数据</font>
									</td>
									<td style="width:50%;border:0px;">
										<input type="checkbox" value="IS_Z_ONLY" name="CHECK_TYPES"></input><font style="margin-left:10px;">仅Z端有数据</font>
									</td>
								</tr>
								<tr>
									<td style="border:0px;">
										<input type="checkbox" value="IS_A_FATUAL" name="CHECK_TYPES"></input><font style="margin-left:10px;">A端数据异常</font>
									</td>
									<td style="border:0px;">
										<input type="checkbox" value="IS_Z_FATUAL" name="CHECK_TYPES"></input><font style="margin-left:10px;">Z端数据异常</font>
									</td>
									<td style="border:0px;">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
				</button>
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
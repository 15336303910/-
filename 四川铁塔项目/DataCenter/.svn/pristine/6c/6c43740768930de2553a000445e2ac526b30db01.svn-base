<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String ruleId = request.getParameter("ruleId");
	String totals = request.getParameter("total");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>问题数据详情</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<%--引入已封装的工具js--%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<style type="text/css">
			thead th{
				text-align:center;
			}
		</style>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			var itemOrder = 1;
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()-3
			    });
			    /*表格高度及容量*/
			    var tableHeight = parseInt($("#hiddenOfHeight").val())-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/26);
				conditions = [{
					name:"RULE_ID",
					value:$("#hiddenOfRuleId").val()
				},{
					name:"COUNTS",
					value:$("#hiddenOfCounts").val()
				}];
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
						"mData":"PRIMARY_VALUE",
						"sWidth":"6%"
					},{
						"mData":"PRIMARY_VALUE",
						"sWidth":"15%"
					},{
						"mData":"NAME_VALUE",
						"sWidth":"35%"
					},{
						"mData":"PROBLEM_DESCRIBE",
						"sWidth":"43.5%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["PRIMARY_VALUE"],aData);
						$("td:eq(0)",nRow).html("<center>"+itemOrder+"</center>");
						itemOrder++;
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/taskMonitorAction/findProblems.ilf"
				});
				$("#dataTable tbody").click(function(event){
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/taskMonitorAction/selectTypes.ilf",
					async:false,
					type:"POST",
					data:"ruleCode="+$("#hiddenOfRuleId").val(),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"] && textStatus["options"]!=null && textStatus["options"].length>0){
							var options = textStatus["options"];
							var optionHtml = "";
							optionHtml+="<select style='width:400px;border:solid 1px #A3D0E3;' id='fatualTypeSelect'>";
								optionHtml+="<option value='-'>请选择错误类型</option>";
							for(var i=0;i<options.length;i++){
								var $option = options[i];
								optionHtml+="<option value='"+$option["PROBLEM_DESC"]+"'>"+$option["PROBLEM_DESC"]+"</option>";
							}
							optionHtml+="</select>";
							document.getElementById("fatualContainer").innerHTML = optionHtml;
						}
					},
					error:function(){}
				});
				$("#resourceNameKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
			});
			function getData(sSource,aoData,fnCallback){
				itemOrder = 1;
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
				conditions = [{
					name:"RULE_ID",
					value:$("#hiddenOfRuleId").val()
				},{
					name:"COUNTS",
					value:$("#hiddenOfCounts").val()
				}];
				var fatualType = $("#fatualTypeSelect").val();
				if(fatualType!="-"){
					conditions.push({
						name:"PROBLEM_DESC",
						value:fatualType
					});
				}
				var resourceName = $("#resourceNameKey").val();
				if(resourceName!=""){
					conditions.push({
						name:"NAME_VALUE",
						value:resourceName
					});				
				}			
				oTable.fnDraw();
			}
			function exportData(){
				window.wxc.xcConfirm("是否确认导出数据？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/taskMonitorAction/exportData.ilf?ruleCode="+$("#hiddenOfRuleId").val()+"&fatalType="+encodeURIComponent(encodeURIComponent($("#fatualTypeSelect").val(),"UTF-8"),"UTF-8")+"&keyWord="+encodeURIComponent(encodeURIComponent($("#resourceNameKey").val(),"UTF-8"),"UTF-8"));
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfRuleId" value="<%=ruleId %>"></input>
  		<input type="hidden" id="hiddenOfCounts" value="<%=totals %>"></input>
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:0px;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>规则管理 >> 问题数据详情
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-top:5px;margin-right:3px;" id="fatualContainer">
							<select style="width:400px;" id="fatualTypeSelect">
								<option value="-">请选择错误类型</option>
							</select>
						</div>
						<input type="text" placeholder="请输入资源名称" style="border:solid 1px #A3D0E3;width:140px;height:29px;font-size:12px;" id="resourceNameKey"></input>
						<a class="btn btn-primary" style="cursor:pointer;width:100px;" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<a class="btn btn-warning" style="cursor:pointer;width:100px;" onclick="javascript:exportData();">
							<i class="icon-download-alt icon-white"></i>导出问题明细
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th>资源编号</th>
										<th>资源名称</th>
										<th>问题描述</th>
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
		<div id="myModal" class="modal hide fade" style="width:650px;height:380px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3>+ 数据模型信息维护</h3>
			</div>
			<div class="modal-body" style="max-height:280px;">
				<form class="l-group" id="editForm">
					<input type="hidden" value="" id="hiddenOfCode"></input>
					<table class="table table-bordered table-hover">
						<tr>
							<td>
								<label class="l-name">所属数据源.</label>
							</td>
							<td id="dbEditTd">
								<select class="w500" id="dbTypeSelection">
									<option value="-">-</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label class="l-name">所属专业.</label>
							</td>
							<td>
								<select class="w500" id="majorSelection">
									<option value="空间资源">空间资源</option>
									<option value="传输外线">传输外线</option>
									<option value="传输内线">传输内线</option>
									<option value="无线">无线</option>
									<option value="动环">动环</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">模型标识.</label></td>
							<td><input id="tableAliasInput" type="text" placeholder="请输入模型标识." style="width:500px;height:30px;margin-top:5px;"></input></td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">模型名称.</label></td>
							<td><input id="tableNameInput" type="text" placeholder="请输入模型名称." style="width:500px;height:30px;margin-top:5px;"></input></td>
						</tr>
						<tr>
							<td style="text-align:center;"><label class="l-name">是否在用.</label></td>
							<td>
								<select class="w500" id="isUseSelection">
									<option value="Y">在用</option>
									<option value="N">停用</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;" onclick="javascript:doSave();">
					<i class="icon-envelope icon-white"></i>保存配置
				</button>
				<button class="btn btn-success" style="cursor:pointer;" onclick="javascript:doTest();">
					<i class="icon-film icon-white"></i>配置测试
				</button>
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;" id="canelButton">
					<i class="icon-zoom-out icon-white"></i>取消
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
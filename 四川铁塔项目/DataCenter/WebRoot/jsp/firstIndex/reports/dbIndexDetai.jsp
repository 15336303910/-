<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cityName = java.net.URLDecoder.decode(request.getParameter("cityName"),"UTF-8");
	String countDate = java.net.URLDecoder.decode(request.getParameter("countDate"),"UTF-8");
	String dataType = java.net.URLDecoder.decode(request.getParameter("dataType"),"UTF-8");
	String frameHeight = request.getParameter("frameHeight");
	System.out.println(cityName+","+countDate+","+dataType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>数据比对问题数据详情</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css"/>
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
				/*分辨率适配*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-85;
				var pageNumbers = parseInt(tableHeight/25);
				/*数据初始化*/
				conditions = [{
					name:"CITY_NAME",
					value:$("#hiddenOfCityName").val()
				},{
					name:"COUNT_DATE",
					value:$("#hiddenOfCountDate").val()
				},{
					name:"DATA_TYPE",
					value:$("#hiddenOfDataType").val()
				},{
					name:"FATAL_DATA_TYPE",
					value:"IS_UNIFORM"
				}];
				$("#FATAL_DATA_TYPE").val("IS_UNIFORM");
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
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"DATA_CITY",
						"sWidth":"120px"
					},{
						"mData":"PRIMARY_VALUE",
						"sWidth":"250px"
					},{
						"mData":"RESOURCE_NAME",
						"sWidth":"270px"
					},{
						"mData":"PROBLEM_TYPE",
						"sWidth":"240px"
					},{
						"mData":"COMPARE_RULE_NAME",
						"sWidth":"280px"
					},{
						"mData":"PROBLEM_DESC",
						"sWidth":"800px"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(aData["PROBLEM_TYPE"]=="IS_UNIFORM"){
							$("td:eq(3)",nRow).html("S标准表与C参考表数据不一致");
						}else if(aData["PROBLEM_TYPE"]=="IS_A_ONLY"){
							$("td:eq(3)",nRow).html("仅S标准表有数据");
						}else if(aData["PROBLEM_TYPE"]=="IS_Z_ONLY"){
							$("td:eq(3)",nRow).html("仅C参考表有数据");
						}else if(aData["PROBLEM_TYPE"]=="IS_A_FATAL"){
							$("td:eq(3)",nRow).html("S标准表数据异常");
						}else if(aData["PROBLEM_TYPE"]=="IS_Z_FATAL"){
							$("td:eq(3)",nRow).html("C参考表数据异常");
						}
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
							dataType:"json",		
							success:function(data){
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/compareDetailAction/findProDetailsOfCenter.ilf"
				});
			});
			function searchData(){
				conditions.push({
					name:"FATAL_DATA_TYPE",
					value:$("#FATAL_DATA_TYPE").val()
				});
				conditions.push({
					name:"RES_NAME",
					value:$("#resourceNameKey").val()
				});	
				oTable.fnDraw();
			}
			function downloadData(){
				var fatalType = $("#FATAL_DATA_TYPE").val();
				if(fatalType=="" || fatalType=="-"){
					window.wxc.xcConfirm("请先选择一个导出数据问题类型.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认下载此问题明细？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							window.parent.showLoading(47,46);
							setTimeout("javascript:window.parent.hideLoading();",3000);
							var exportForm = $("<form>");
						    exportForm.attr("style","display:none");
						    exportForm.attr("target","");
						    exportForm.attr("method","post");
						    exportForm.attr("action","${pageContext.request.contextPath}/compareDetailAction/exportDetailsInDbCenter.ilf?CITY_NAME="+encodeURIComponent(encodeURIComponent($("#hiddenOfCityName").val(),"UTF-8"),"UTF-8")+"&COUNT_DATE="+$("#hiddenOfCountDate").val()+"&DATA_TYPE="+encodeURIComponent(encodeURIComponent($("#hiddenOfDataType").val(),"UTF-8"),"UTF-8")+"&FATAL_TYPE="+$("#FATAL_DATA_TYPE").val()+"&KEY_WORD="+encodeURIComponent(encodeURIComponent($("#resourceNameKey").val(),"UTF-8"),"UTF-8"));
						    $("body").append(exportForm);
						    exportForm.submit();
						    exportForm.remove();
						}
					});		
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfCityName" value="<%=cityName %>"></input>
  		<input type="hidden" id="hiddenOfCountDate" value="<%=countDate %>"></input>
  		<input type="hidden" id="hiddenOfDataType" value="<%=dataType %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:0px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
				<span class="panel-label"></span>数据比对 >>&nbsp;详情&nbsp;>>&nbsp;<font color="red">默认呈现属性比对不一致数，其他问题类型请选择后查询</font>
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入资源名称" style="width:150px;height:30px;font-size:12px;border:solid 1px #A3D0E3;" id="resourceNameKey"></input>
						<a class="btn btn-primary"  style="cursor:pointer;" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
						<a class="btn btn-warning"  style="cursor:pointer;" onclick="javascript:downloadData();">
							<i class="icon-download-alt icon-white"></i>导出明细
						</a>
					</div>
					<div style="float:right;">
						<select id="FATAL_DATA_TYPE" style="width:230px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;">
							<option value="-">- 请选择问题类型 -</option>
							<option value="IS_UNIFORM">S标准表与C参考表数据不一致</option>
							<option value="IS_A_ONLY">仅S标准表有数据</option>
							<option value="IS_Z_ONLY">仅C参考表有数据</option>
							<option value="IS_A_FATAL">S标准表数据异常</option>
							<option value="IS_Z_FATAL">C参考表数据异常</option>
						</select>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #A3D0E3;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th>所属地市</th>
										<th>资源编号</th>
										<th>资源名称</th>
										<th>问题类型</th>
										<th>所属比对规则</th>
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
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
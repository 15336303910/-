<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>获取坐标附近交叉路口</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/ajaxfileupload/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style type="text/css">
			thead th{
				text-align:center;
			}
			.dataTables_wrapper{
				border:solid 1px #A3D0E3;
			}
		</style>
		<script type="text/javascript">
			/*
				定义道路信息表格变量和参数变量
			 */
			var roadTable = null;
			var roadCondition = [];
			var tableHeight = 0;
			$(document).ready(function(){
				var layoutHeight = document.body.clientHeight;
				document.getElementById("mainPanel").style.height = (layoutHeight-290)+"px";
				tableHeight = layoutHeight-470;
				/*
					道路信息转化表格.
				 */
				roadCondition = [{
					name:"BELONG_JOB",
					value:$("#hiddenOfRoadJobCode").val()
				}];
				roadTable = $("#roadTable").dataTable({
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
						"mData":"X_OUTPUT",
						"sWidth":"10%"
					},{
						"mData":"Y_OUTPUT",
						"sWidth":"10%"
					},{
						"mData":"DIRECTION",
						"sWidth":"10%"
					},{
						"mData":"DISTANCE",
						"sWidth":"10%"
					},{
						"mData":"ADDRESS_OUTPUT",
						"sWidth":"59.5%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":100,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
					},
					"fnServerData":getRoadData,
					"sAjaxSource":"${pageContext.request.contextPath}/xyTransfer/findItems.ilf"
				});
				$("#roadTable tbody").click(function(event){
					$(roadTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
			});
			function getRoadData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(roadCondition),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			};
			function transferRoadInfos(){
				var editObject = {
					xPoint:$("#X_LOCATION_VALUE").val(),
					yPoint:$("#Y_LOCATION_VALUE").val(),
					circle:$("#SEARCH_CIRCLE").val()
				};
				if(editObject["xPoint"]==""){
					window.wxc.xcConfirm("请输入经度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(editObject["yPoint"]==""){
					window.wxc.xcConfirm("请输入纬度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(editObject["circle"]=="-"){
					window.wxc.xcConfirm("请选择搜索半径.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/xyTransfer/getCornersOfXYPoint.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(editObject),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							$("#hiddenOfRoadJobCode").val(textStatus["ROAD_JOB_CODE"]);
							roadCondition = [{
								name:"BELONG_JOB",
								value:$("#hiddenOfRoadJobCode").val()
							}];
							roadTable.fnDraw();
						}else{
							window.wxc.xcConfirm("获取道路信息失败，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("获取道路信息失败，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
					}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;">
  		<input type="hidden" value="-1" id="hiddenOfRoadJobCode"></input>
		<div class="panel panel-default" style="margin-top:1px;height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-heading">
				<span class="panel-label"></span>高德坐标系 >> 获取坐标附近的交叉路口
			</div>
			<div class="panel-body" style="width:100%;border:0px;">
				<div style="width:99.8%;height:430px;overflow:auto;border:solid 1px #FFF;" id="ruleEditsBuck">
					<div style="float:left;width:99%;margin-top:4px;margin-left:4px;">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
							<span class="panel-label"></span>交叉路口信息批量获取
							<form class="form-search pull-right">	
								<div style="float:right;">
									<input type="text" id="X_LOCATION_VALUE" value="116.481488" placeholder="请输入坐标经度" style="width:170px;height:28px;border:solid 1px #A3D0E3;border-radius:6px;"></input>
									<input type="text" id="Y_LOCATION_VALUE" value="39.990464" placeholder="请输入坐标纬度" style="width:170px;height:28px;border:solid 1px #A3D0E3;border-radius:6px;margin-left:5px;margin-right:5px;"></input>
									<select id="SEARCH_CIRCLE" style="width:170px;height:29px;border:solid 1px #A3D0E3;border-radius:6px;margin-right:5px;">
										<option value="-">-请指定搜索半径-</option>
										<option value="100">100米</option>
										<option value="200">200米</option>
										<option value="300">300米</option>
										<option value="400">400米</option>
										<option value="500">500米</option>
										<option value="600">600米</option>
										<option value="700">700米</option>
										<option value="800">800米</option>
										<option value="900">900米</option>
										<option value="1000">1000米</option>
										<option value="1500">1500米</option>
										<option value="2000">2000米</option>
										<option value="3000">3000米</option>
									</select>
									<a class="btn btn-warning" style="cursor:pointer;border-radius:6px;" onclick="javascript:transferRoadInfos();">
										<i class="icon-play-circle icon-white"></i>获取交叉路口信息
									</a>
								</div>
							</form>
						</div>
						<div class="panel-body h350" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table cellpadding="0" cellspacing="0" border="0" id="roadTable" style="border:0px;">
											<thead>
												<tr>
													<th><font color="green">经度</font></th>
													<th><font color="green">纬度</font></th>
													<th><font color="green">方位</font></th>
													<th><font color="green">距离（米）</font></th>
													<th><font color="green">交叉路口名称</font></th>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
							</div>
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
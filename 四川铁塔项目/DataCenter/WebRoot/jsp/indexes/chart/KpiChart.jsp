<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String kpiId = request.getParameter("kpiId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>指标分析</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript">
			var zTable = null;
			var zConditions = [];
			var titles = null;
			var values = null;
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#outlineContainer").css({
			    	"height":$("#hiddenOfHeight").val()-2
			    });
				var tableHeight = $("#outlineContainer").height()-$("#headPanel").height()-$("#aTableHead").height()-90;
				var pageNumbers = parseInt(tableHeight/25);
				$("#zChartContainer").css({
			    	"height":tableHeight+68
			    });
				$("#pieCharts").css({
			    	"height":$("#zChartContainer").height()-15
			    });
				/*初始化指标*/
				var indexCode = $("#hiddenOfKpiCode").val();
				if(indexCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexDetailAction/findIndexDetail.ilf",
						async:false,
						type:"POST",
						data:"indexCode="+indexCode,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"]){
								var $obj = textStatus["thisOne"];
								$("#indexNameInput").val($obj["INDEX_NAME"]);
								$("#isUsingSelect").val($obj["IS_USING"]);
								$("#gradeSelection").val($obj["INDEX_LEVEL"]);
							}
						},
						error:function(){}
					});	
				}
				zConditions = [{
					name:"BELONG_INDEX",
					value:$("#hiddenOfKpiCode").val()
				}];
				zTable = $("#zDataTable").dataTable({
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
						"mData":"TABLE_NAME",
						"sWidth":"24%"
					},{
						"mData":"RULE_NAME",
						"sWidth":"62%"
					},{
						"mData":"COUNT_RATIO",
						"sWidth":"14%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(2)",nRow).html("<font color='green'>"+aData["COUNT_RATIO"]+"%</font>");
					},
					"fnServerData":getZData,
					"sAjaxSource":"${pageContext.request.contextPath}/indexDetailAction/findRuleUnchecked.ilf"
				});
				$("#zDataTable tbody").click(function(event){
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				/*初始化报表*/
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				var indexCode = $("#hiddenOfKpiCode").val();
				if(indexCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexDetailAction/accountRatio.ilf",
						async:false,
						type:"POST",
						data:"indexCode="+indexCode,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"]){
								titles = textStatus["titles"];
								values = textStatus["values"];
								require([
									"echarts",
									"echarts/chart/pie"
								],DrawPieCharts);
							}
						},
						error:function(){}
					});	
				}
			});
			function getZData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(zConditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			};
			var pieChart = null;
			function DrawPieCharts(ec){
				pieChart = ec.init(document.getElementById("pieCharts"),"macarons");
				var $option = {
					title:{
				        text:"",
				        subtext:"",
				        x:"center"
				    },
				    tooltip:{
				        trigger:"item",
				        formatter:"{b}:{d}%"
				    },
				    legend:{
				        orient:"horizontal",
				        x:"left",
				        data:titles
				    },
				    toolbox:{
				        show:true,
				        feature:{
				            saveAsImage:{
				            	show:true
				            }
				        }
				    },
				    calculable:true,
				    series:[{
			            name:"",
			            type:"pie",
			            radius:"50%",
			            center:["50%","60%"],
			            data:values
					}]
				};		
				pieChart.setOption($option);
			}
			function doRefresh(){
				/*1.刷新基本信息*/
				var indexCode = $("#hiddenOfKpiCode").val();
				if(indexCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexDetailAction/findIndexDetail.ilf",
						async:false,
						type:"POST",
						data:"indexCode="+indexCode,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"]){
								var $obj = textStatus["thisOne"];
								$("#indexNameInput").val($obj["INDEX_NAME"]);
								$("#isUsingSelect").val($obj["IS_USING"]);
								$("#gradeSelection").val($obj["INDEX_LEVEL"]);
							}
						},
						error:function(){}
					});	
				}
				/*2.刷新表格*/
				zConditions = [{
					name:"BELONG_INDEX",
					value:$("#hiddenOfKpiCode").val()
				}];
				zTable.fnDraw();
				/*3.刷新报表*/
				if(indexCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexDetailAction/accountRatio.ilf",
						async:false,
						type:"POST",
						data:"indexCode="+indexCode,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"]){
								titles = textStatus["titles"];
								values = textStatus["values"];
								require([
									"echarts",
									"echarts/chart/pie"
								],DrawPieCharts);
							}
						},
						error:function(){}
					});	
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=kpiId %>" id="hiddenOfKpiCode"></input>
  		<div style="width:99.9%;height:484px;border:solid 1px #FFF;" id="outlineContainer">
  			<div class="panel panel-default" style="float:left;width:99.5%;height:112px;border:solid 1px #A3D0E3;margin-left:3px;margin-top:3px;" id="headPanel">
  				<div class="panel-heading">
					<span class="panel-label"></span>指标基本信息
				</div>
				<div class="panel-body" style="border:0px;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto">
								<table cellpadding="0" cellspacing="0" border="0" style="width:70%;border:0px;font-size:12px;margin-top:10px;">
									<thead>
										<tr height="40">
											<td style="width:30%;text-align:center;">
												<center>
													<input id="indexNameInput" type="text" placeholder="请输入指标名称" style="width:95%;height:30px;border:solid 1px #A3D0E3;margin-top:5px;border-radius:5px;"></input>
												</center>
											</td>
											<td style="width:30%;text-align:center;">
												<center>
													<select id="gradeSelection" style="width:95%;height:30px;margin-top:5px;border:solid 1px #A3D0E3;border-radius:5px;">
														<option value="-">-- 请选择指标级别 --</option>
														<option value="2">一般</option>
														<option value="3">重要</option>
														<option value="5">非常重要</option>
													</select>
												</center>
											</td>
											<td style="width:30%;text-align:center;">
												<select id="isUsingSelect" style="width:95%;height:30px;margin-top:5px;border:solid 1px #A3D0E3;border-radius:5px;">
													<option value="-">-- 请选择指标状态 --</option>
													<option value="Y">在用</option>
													<option value="N">停用</option>
												</select>
											</td>
											<td style="width:10%;text-align:left;">
												<a class="btn btn-success" style="cursor:pointer;margin-top:-5px;" onclick="javascript:doRefresh();">
													<i class="icon-plus-sign icon-white"></i>执行刷新
												</a>
											</td>
										</tr>
									</thead>
									<tbody></tbody>
								</table>					
							</div>
						</div>
					</div>
				</div>
  			</div>
  			<div style="float:left;width:100%;height:365px;margin-top:5px;">
  				<div style="float:left;width:40.3%;height:360px;margin-left:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="aTableHead">
						<span class="panel-label"></span>指标已包含规则
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="zDataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td style="background:#FCFDFE;text-align:center;">归属模型</td>
												<td style="background:#FCFDFE;text-align:center;">规则名称</td>
												<td style="background:#FCFDFE;text-align:center;">权重</td>
											</tr>
										</thead>
										<tbody></tbody>
									</table>					
								</div>
							</div>
						</div>
					</div>
  				</div>
  				<div style="float:right;width:58.3%;margin-right:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="zTableHead">
						<span class="panel-label"></span>规则占比分析
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<div style="width:99%;height:317px;border:solid 1px #A3D0E3;" id="zChartContainer">
										<center>
											<div style="width:99%;height:305px;margin-top:5px;border:solid 1px #FFF;" id="pieCharts"></div>
										</center>
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
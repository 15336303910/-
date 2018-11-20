<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String userId = request.getParameter("userId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>指标责任分析</title>
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
				/*账户基本信息*/
				var userCode = $("#hiddenOfUserCode").val();
				if(userCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexUserAction/findUserDetail.ilf",
						async:false,
						type:"POST",
						data:"userCode="+userCode,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"]){
								var $obj = textStatus["userMap"];
								$("#userNameDiv").html("账户："+$obj["USER_NAME"]);
								$("#employeeName").html("姓名："+$obj["EMPLOYEE_NAME"]);
								$("#employeeCompany").html("公司："+$obj["EMPLOYEE_COMPANY"]);
							}
						},
						error:function(){}
					});	
				}
				/*已关联指标*/
				zConditions = [{
					name:"BELONG_USER",
					value:$("#hiddenOfUserCode").val()
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
						"mData":"INDEX_NAME",
						"sWidth":"73%"
					},{
						"mData":"INDEX_LEVEL",
						"sWidth":"20%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(1)",nRow).html("<font color='green'>"+aData["INDEX_LEVEL"]+"%</font>");
					},
					"fnServerData":getZData,
					"sAjaxSource":"${pageContext.request.contextPath}/indexUserAction/findItems.ilf"
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
				var userCode = $("#hiddenOfUserCode").val();
				if(userCode!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/indexUserAction/accountRatio.ilf",
						async:false,
						type:"POST",
						data:"userCode="+userCode,
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
			function searchCheckedData(){
				zConditions = [{
					name:"BELONG_USER",
					value:$("#hiddenOfUserCode").val()
				}];			
				zTable.fnDraw();
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=userId %>" id="hiddenOfUserCode"></input>
  		<div style="width:99.9%;height:484px;border:solid 1px #FFF;" id="outlineContainer">
  			<div class="panel panel-default" style="float:left;width:99.5%;height:105px;border:solid 1px #A3D0E3;margin-left:3px;margin-top:3px;" id="headPanel">
  				<div class="panel-heading">
					<span class="panel-label"></span>用户基本信息
				</div>
				<div class="panel-body" style="border:0px;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto">
								<table cellpadding="0" cellspacing="0" border="0" style="width:70%;border:0px;font-size:12px;margin-top:10px;">
									<thead>
										<tr height="30">
											<td style="width:30%;text-align:center;">
												<center>
													<div style="width:90%;height:30px;border:solid 1px #A3D0E3;border-radius:5px;">
														<div style="margin-top:5px;" id="userNameDiv">&nbsp;</div>
													</div>
												</center>
											</td>
											<td style="width:30%;text-align:center;">
												<center>
													<div style="width:90%;height:30px;border:solid 1px #A3D0E3;border-radius:5px;">
														<div style="margin-top:5px;" id="employeeName">&nbsp;</div>
													</div>
												</center>
											</td>
											<td style="width:40%;text-align:center;">
												<center>
													<div style="width:90%;height:30px;border:solid 1px #A3D0E3;border-radius:5px;">
														<div style="margin-top:5px;" id="employeeCompany">&nbsp;</div>
													</div>
												</center>
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
  			<div style="float:left;width:100%;height:372px;margin-top:5px;">
  				<div style="float:left;width:40.3%;height:367px;margin-left:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="aTableHead">
						<span class="panel-label"></span>用户已关联指标
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="zDataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td style="background:#FCFDFE;text-align:center;">指标名称</td>
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
  				<div style="float:right;width:58.3%;height:367px;margin-right:3px;">
					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
						<span class="panel-label"></span>指标权重分析
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<div style="width:99%;border:solid 1px #A3D0E3;" id="zChartContainer">
										<center>
											<div style="width:99%;height:305px;margin-top:5px;" id="pieCharts"></div>
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
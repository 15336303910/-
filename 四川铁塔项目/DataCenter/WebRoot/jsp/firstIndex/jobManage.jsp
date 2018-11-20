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
    	<title>作业维护</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<style type="text/css">
			.MAIN_PANEL{
				border-radius:0px;
				border:solid 1px #57BEDD;
				box-shadow:0px 0px 3px #57BEDD;
				position:absolute;
				background:#FFF;
			}
			.PANEL_HEADING{
				width:100%;
				height:40px;
				color:white;
				border-bottom:solid 1px #57BEDD;
				background:#57BEDD;
			}
			.PANEL_BODY{
				width:100%;
				overflow:auto;
			}
			.PANEL_FOOT{
				width:100%;
				height:45px;
				border-top:dotted 1px #F0F0F0;
				position:absolute;
				left:0;
				bottom:0;
			}
		</style>
		<script type="text/javascript">
			var cTable = null;
			var cConditions = null;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
			
				$("#MASK_DIV").css({
			    	"height":$("#bodyHeight").height()
			    });
				/*表头*/
				var date = new Date();
				var thisMonth = date.getMonth()+1;
				var dateStr =  date.getFullYear()+"-"+(thisMonth<10?"0"+thisMonth:thisMonth);
				$("#DATE_CHOOSEN").val(dateStr);
				/*是否作业管理员*/
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/isJobManager.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"] && !textStatus["IS_JOB_MANAGER"]){
							$("#JOB_MANAGER_BUTTON").hide(300);
						}else{
							$("#JOB_MANAGER_BUTTON").show(300);
						}
					},
					error:function(){}
				});
				initPage();
				cConditions = [{
					name:"CONTRACT_ZH_LABEL",
					value:-1
				}];
				cTable = $("#contractTable").dataTable({
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
					"sScrollY":"245px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"DATA_NAME",
						"sWidth":"215px"
					},{
						"mData":"IS_REFERENCED",
						"sWidth":"145px"
					},{
						"mData":"MODULAR_NAME",
						"sWidth":"195px"
					},{
						"mData":"BUSINESS_SYSTEM",
						"sWidth":"160px"
					},{
						"mData":"BELONG_DEPARTMENT",
						"sWidth":"145px"
					},{
						"mData":"ACQUISITION_MODE",
						"sWidth":"145px"
					},{
						"mData":"COLLECTION_CYCLE",
						"sWidth":"140px"
					},{
						"mData":"CHECK_CYCLE",
						"sWidth":"140px"
					},{
						"mData":"TABLE_INFORMATION_COMB",
						"sWidth":"615px"
					},{
						"mData":"IS_KEEP",
						"sWidth":"125px"
					},{
						"mData":"TABLE_DERIVED_TABLE",
						"sWidth":"170px"
					},{
						"mData":"NOTE",
						"sWidth":"250px"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":9,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
							
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(cConditions),
							dataType:"json",		
							success:function(data){
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/jobPlanManageAction/findJobExplain.ilf"
				});
			});
			function initPage(){
				var thisDate = $("#DATE_CHOOSEN").val();
				/*日期非空验证*/
				if(thisDate==""){
					var date = new Date();
					var thisMonth = date.getMonth()+1;
					var dateStr =  date.getFullYear()+"-"+(thisMonth<10?"0"+thisMonth:thisMonth);
					$("#DATE_CHOOSEN").val(dateStr);
					thisDate = $("#DATE_CHOOSEN").val();
				}
				/*日期[月]决定总列数.*/
				var thisYear = parseInt(thisDate.split("-")[0]);
				var thisMonth = parseInt(thisDate.split("-")[1]);
				var totalDay = 30;
				if(thisMonth==1 || thisMonth==3 || thisMonth==5 || thisMonth==7 || thisMonth==8 || thisMonth==10 || thisMonth==12){
					totalDay = 31;
				}else if(thisMonth==2){
					if((thisYear%4==0 && thisYear%100!=0) || (thisYear%400==0)){
						totalDay = 29;
					}else{
						totalDay = 28;
					}
				}
				/*表格头部.*/
				var innerHTML = "<td style='width:90px;border-right:solid 1px #A3D0E3;border-bottom:solid 1px #A3D0E3;'>&nbsp;</td>";
				for(var i=1;i<=totalDay;i++){
					innerHTML+= "<td style='width:40px;border-right:solid 1px #A3D0E3;border-bottom:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>"+i+"号</td>";
				}
				document.getElementById("DAY_CONTAINER").innerHTML = innerHTML;
				/*正在获取数据.*/
				var tipHtml = "";
				tipHtml+="<tr style='height:40px;'>";
				tipHtml+="	  <td colspan='"+(totalDay+1)+"' style='text-align:center;border-right:solid 1px #A3D0E3;border-bottom:solid 1px #A3D0E3;'><font color='red'>正在获取数据...</font></td>";
				tipHtml+="</tr>";
				document.getElementById("DATA_CONTAINER").innerHTML = tipHtml;
				/*获取数据*/
				window.parent.showLoading(45,47);
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findJobRunDetails.ilf",
					async:false,
					type:"POST",
					data:"designedDate="+thisDate,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						window.parent.hideLoading();
						if(textStatus["SUCCESS"]){
							var datas = textStatus["DATAS"];
							var innerHtml = "";
							var inputDate = $("#DATE_CHOOSEN").val();
							for(var a=0;a<datas.length;a++){
								var rowData = datas[a];
								innerHtml+="<tr style='height:45px;'>";
								var rowTitle = "";
								for(var b=0;b<rowData.length;b++){
									var cellValue = rowData[b];
									if(b==0){
										rowTitle = cellValue;
										innerHtml+="<td style='text-align:center;border-bottom:dotted 1px #A3D0E3;'>"+cellValue+"</td>";
									}else{
										var thisDate = inputDate+"-"+(b<10?"0"+b:b);
										if(cellValue=="-"){
											/*日期未到*/
											innerHtml+="<td style='cursor:pointer;text-align:center;border-bottom:dotted 1px #A3D0E3;'>-</td>";	
										}else{
											/*未交*/
											if(cellValue=="N"){
												innerHtml+="<td class='TDS' id='TD"+a+""+b+"' onclick='javascript:turnToDetail(this.id,\""+rowTitle+"\",\""+thisDate+"\");' style='cursor:pointer;text-align:center;border-bottom:dotted 1px #A3D0E3;'><img src='${pageContext.request.contextPath}/icons/question.png' style='width:21px;height:21px;'></img></td>";
											/*已按时交*/
											}else if(cellValue=="Y"){
												innerHtml+="<td class='TDS' id='TD"+a+""+b+"' onclick='javascript:turnToDetail(this.id,\""+rowTitle+"\",\""+thisDate+"\");' style='cursor:pointer;text-align:center;border-bottom:dotted 1px #A3D0E3;'><img src='${pageContext.request.contextPath}/icons/right.png' style='width:17px;height:17px;'></img></td>";
											/*补交*/
											}else if(cellValue=="B"){
												innerHtml+="<td class='TDS' id='TD"+a+""+b+"' onclick='javascript:turnToDetail(this.id,\""+rowTitle+"\",\""+thisDate+"\");' style='cursor:pointer;text-align:center;border-bottom:dotted 1px #A3D0E3;'><img src='${pageContext.request.contextPath}/icons/rightafter.png' style='width:17px;height:17px;'></img></td>";
											}
										}
									}
								}
								innerHtml+="</tr>";
							}
							document.getElementById("DATA_CONTAINER").innerHTML = innerHtml;
						}
					},
					error:function(){
						window.parent.hideLoading();
					}
				});
			}
			function turnToDetail(clickedCell,rowTitle,thisDate){
				$(".TDS").css({
					background:"#FFF"
				});
				$("#"+clickedCell).css({
					background:"#A3D0E3"
				});
				$("#hiddenOfEditTitle").val(rowTitle);
				$("#hiddenOfEditDate").val(thisDate);
			}
			function turnJspToDetail(){
				var editTitle = $("#hiddenOfEditTitle").val();
				var editDate = $("#hiddenOfEditDate").val();
				if(editTitle=="" || editDate==""){
					window.wxc.xcConfirm("请先选择一项.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					if(editTitle=="S标准表"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/scDetail.jsp?designedDate="+editDate);	
					}else if(editTitle=="标准枚举"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/enumDetail.jsp?designedDate="+editDate);
					}else if(editTitle=="采集情况"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/pickDetail.jsp?designedDate="+editDate);
					}
				}
			}
			function turnJspToEdit(){
				var editTitle = $("#hiddenOfEditTitle").val();
				var editDate = $("#hiddenOfEditDate").val();
				if(editTitle=="" || editDate==""){
					window.wxc.xcConfirm("请先选择一项.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					if(editTitle=="S标准表"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/edit/scEdit.jsp?designedDate="+editDate);	
					}else if(editTitle=="标准枚举"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/edit/enumEdit.jsp?designedDate="+editDate);
					}else if(editTitle=="采集情况"){
						window.parent.turnToJsp(editTitle+":"+editDate,"jsp/jobs/edit/pickEdit.jsp?designedDate="+editDate);
					}
				}
			}
			function showEditWindow(){
				$("#MASK_DIV").show();
				$("#EDIT_PANEL").slideDown(350);
			}
			function hideEditWindow(){
				$("#MASK_DIV").hide();
				$("#EDIT_PANEL").slideUp(350);
			}
			function researchDetailItem(){
				cConditions = [{
					name:"PICK_TABLE_NAME",
					value:$("#PICK_TABLE_NAME_INPUT").val()
				},{
					name:"MAPPING_SYSTEM",
					value:$("#MAPPING_SYSTEM_SELECT").val()
				},{
					name:"MODULE_DATA_APPLY",
					value:$("#MODULE_DATA_APPLY_INPUT").val()
				},{
					name:"COLLECTION_CYCLE",
					value:$("#COLLECTION_CYCLE_SELECT").val()
				}];
				cTable.fnDraw();
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfEditTitle" value=""></input>
  		<input type="hidden" id="hiddenOfEditDate" value=""></input>
		<div class="panel panel-default" style="margin-top:1px;">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>作业计划
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input id="DATE_CHOOSEN" type="text" placeholder="请选择月份" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" style="cursor:pointer;width:180px;height:29px;font-size:12px;border:solid 1px #A3D0E3;"></input>
						<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:initPage();">
							<i class="icon-refresh icon-white"></i>获取统计
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:turnJspToDetail();">
							<i class="icon-book icon-white"></i>查看详情
						</a>
						<a class="btn btn-primary" id="JOB_MANAGER_BUTTON" style="cursor:pointer" onclick="javascript:turnJspToEdit();">
							<i class="icon-cog icon-white"></i>编辑作业
						</a>
						<a class="btn btn-info" style="cursor:pointer" onclick="javascript:showEditWindow();">
							<i class="icon-book icon-white"></i>查看数据采集.同步.应用说明
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body h340" style="border:0px;margin-top:3px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="width:100%;border:solid 1px #A3D0E3;border-right:0px;font-size:12px;">
								<thead>
									<tr style="height:30px;" id="DAY_CONTAINER">
										<td style="width:110px;border-right:solid 1px #A3D0E3;">&nbsp;</td>
										<td style="text-align:center;background:#FCFDFE;">1号</td>
										<td style="text-align:center;background:#FCFDFE;">2号</td>
										<td style="text-align:center;background:#FCFDFE;">3号</td>
										<td style="text-align:center;background:#FCFDFE;">4号</td>
									</tr>
								</thead>
								<tbody id="DATA_CONTAINER">
									<tr style="height:30px;">
										<td colspan="32" style="text-align:center;border-right:solid 1px #A3D0E3;border-bottom:solid 1px #A3D0E3;"><font color="red">暂无数据</font></td>
									</tr>
								</tbody>
							</table>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--编辑窗口--%>
		<div id="MASK_DIV" style="display:none;width:100%;height:500px;position:absolute;top:0px;left:0px;background-color:#FFF;opacity:0.8;"></div>
  		<div id="EDIT_PANEL" class="MAIN_PANEL" style="width:1150px;height:460px;top:20px;right:5%;display:none;">
  			<div class="PANEL_HEADING">
  				<div style="float:left;margin-top:10px;margin-left:10px;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/rightarrow.png" style="width:15px;height:15px;margin-top:3px;"/>	
  				</div>
  				<div style="float:left;margin-top:10px;font-size:12px;font-family:'Raleway';">&nbsp;&nbsp;数据采集.同步.应用说明</div>
  				<img onclick="javascript:hideEditWindow();" src="${pageContext.request.contextPath}/extensions/form/image/close.png" style="float:right;width:17px;height:17px;margin-top:11px;margin-right:10px;cursor:pointer;"/>
  			</div>
  			<div class="PANEL_BODY" style="height:400px;background:#FEFEFE;border:solid 0px red;">
  				<div class="panel panel-default" style="height:390px;width:99.2%;margin-left:3px;margin-top:2px;overflow:auto;">
					<div class="panel-heading" style="margin-top:1px;border:solid 1px #DFE8F1;border-bottom:0px;">
						<form class="form-search pull-right">	
							<div style="float:right;">
								<input id="MODULE_DATA_APPLY_INPUT" type="text" placeholder="请输入模块数据应用关键字" style="cursor:pointer;width:170px;height:30px;font-size:12px;border:solid 1px #DFE8F1;"></input>
								<input id="PICK_TABLE_NAME_INPUT" type="text" placeholder="请输入对应采集表名称" style="cursor:pointer;width:150px;height:30px;font-size:12px;border:solid 1px #DFE8F1;"></input>
								<select id="MAPPING_SYSTEM_SELECT" style="width:170px;;height:30px;border:solid 1px #DFE8F1;font-size:12px;">
									<option value="-">- 请选择对应业务系统  -</option>
									<option value="CRM">CRM</option>
									<option value="PMS">PMS</option>
									<option value="财务">财务</option>
									<option value="合同">合同</option>
									<option value="经分">经分</option>
									<option value="商合-交易">商合-交易</option>
									<option value="商合-认证">商合-认证</option>
									<option value="审计">审计</option>
									<option value="物业">物业</option>
									<option value="选址">选址</option>
									<option value="运维监控">运维监控</option>
									<option value="资源">资源</option>
								</select>
								<select id="COLLECTION_CYCLE_SELECT" style="width:150px;;height:30px;border:solid 1px #DFE8F1;font-size:12px;">
									<option value="-">- 请选择采集周期  -</option>
									<option value="天">天</option>
									<option value="周">周</option>
									<option value="月">月</option>
								</select>
								<a class="btn btn-inverse" style="cursor:pointer" onclick="javascript:researchDetailItem();">
									<i class="icon-refresh icon-white"></i>执行查询
								</a>
							</div>
						</form>
					</div>
					<div class="panel-body" style="border:solid 1px #DFE8F1;overflow:auto;height:326px;">
						<div class="panlecontent container4 clearfix" style="width:98%;height:310px;margin-left:10px;margin-top:5px;">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="contractTable" style="border:0px;">
										<thead>
											<tr height="32">
												<th><center><font color="black" style="margin-left:10px;">对应采集表名称</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">是否在平台中应用</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">模块数据应用</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">对应业务系统</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">对应业务部门</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">数据获取方式</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">数据采集周期</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">数据核对周期</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">表数据分析说明</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">表处置状态</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">表内衍生表</font></center></th>
												<th><center><font color="black" style="margin-left:10px;">备注</font></center></th>
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
  			<div class="PANEL_FOOT" style="background:#FEFEFE;">
 				<div onclick="javascript:hideEditWindow();" style="float:left;width:115px;height:27px;color:white;background:#57BEDD;font-size:12px;border-radius:5px;cursor:pointer;margin-top:7px;margin-left:40%;box-shadow:0 0 0px #57BEDD;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/submit.png" style="float:left;width:23px;height:13px;margin-top:7px;margin-left:7px;"/>
  					<div style="float:left;margin-left:7px;">
  						<center style="margin-top:3px;">关闭窗口</center>
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
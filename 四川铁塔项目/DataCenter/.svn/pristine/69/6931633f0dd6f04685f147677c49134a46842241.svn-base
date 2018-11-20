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
    	<title>活动告警明细</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			function flushRegionSelect(cityName){
				if(cityName=="-"){
					var innerHtml = "";
					innerHtml+="<select id='REGION_SELECT' style='width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;'>";
					innerHtml+="	<option value='-'>- 请选择区县 -</option>";
					innerHtml+="</select>";
					document.getElementById("REGION_CONTAINER").innerHTML = innerHtml;
				}else{
					$.ajax({ 
						type:"POST", 
						url:"${pageContext.request.contextPath}/elecCostPayout/findRegions.ilf",
						dataType:"JSON",
						data:"typeCode=4&cityName="+cityName,
						async:false,
					 	success:function(resultObj){
					 		if(resultObj["SUCCESS"]){
						 		var Regions = resultObj["Regions"];
								var innerHtml = "";
								innerHtml+="<select id='REGION_SELECT' style='width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;'>";
								innerHtml+="	<option value='-'>- 请选择区县 -</option>";
								for(var i=0;i<Regions.length;i++){
									var regionName = Regions[i];
									innerHtml+="<option value='"+regionName["COUNTY_NAME"]+"'>"+regionName["COUNTY_NAME"]+"</option>";	
								}
								innerHtml+="</select>";
								document.getElementById("REGION_CONTAINER").innerHTML = innerHtml;
					 		}
					 	}
					});	
				}
			}
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				$.ajax({ 
					type:"POST", 
					url:"${pageContext.request.contextPath}/elecCostPayout/findCitys.ilf",
					dataType:"JSON",
					data:"typeCode=D",
					async:false,
				 	success:function(resultObj){
				 		if(resultObj["SUCCESS"]){
					 		var Citys = resultObj["Citys"];
							var innerHtml = "";
							innerHtml+="<select id='CITY_SELECT' onchange='javascript:flushRegionSelect(this.value);' style='width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;'>";
							innerHtml+="	<option value='-'>- 请选择地市 -</option>";
							for(var i=0;i<Citys.length;i++){
								var regionName = Citys[i];
								innerHtml+="<option value='"+regionName["CITY_NAME"]+"'>"+regionName["CITY_NAME"]+"</option>";	
							}
							innerHtml+="</select>";
							document.getElementById("CITY_CONTAINER").innerHTML = innerHtml;
				 		}
				 	}
				});
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/27);
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
						"mData":"CITY_NAME",
						"sWidth":"150px"
					},{
						"mData":"COUNTY_NAME",
						"sWidth":"150px"
					},{
						"mData":"SITE_CODE",
						"sWidth":"160px"
					},{
						"mData":"TOWER_NAME",
						"sWidth":"250px"
					},{
						"mData":"ALARM_LEVEL",
						"sWidth":"130px"
					},{
						"mData":"ALARM_NAME",
						"sWidth":"240px"
					},{
						"mData":"ALARM_BEGIN_TIME",
						"sWidth":"180px"
					},{
						"mData":"ALARM_END_TIME",
						"sWidth":"180px"
					},{
						"mData":"ALARM_TYPE",
						"sWidth":"120px"
					},{
						"mData":"ALARM_CASE",
						"sWidth":"300px"
					},{
						"mData":"TOWER_TYPE",
						"sWidth":"150px"
					},{
						"mData":"TOWER_STATUS",
						"sWidth":"150px"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(aData["ALARM_BEGIN_TIME"]!=null){
							var date = new Date();
							date.setTime(aData["ALARM_BEGIN_TIME"]["time"]);
							$("td:eq(6)",nRow).html(date.toLocaleString());
						}
						if(aData["ALARM_END_TIME"]!=null){
							var date = new Date();
							date.setTime(aData["ALARM_END_TIME"]["time"]);
							$("td:eq(7)",nRow).html(date.toLocaleString());
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
					"sAjaxSource":"${pageContext.request.contextPath}/alarmDetail/findDatas.ilf"
				});
			});
			function searchData(){
				/*站址编码、名称、告警名称*/
				var siteName = $("#siteNameKey").val();
				if(siteName!=""){
					conditions = [{
						name:"SITE_CODE_OR_NAME",
						value:siteName
					}];	
				}else{
					conditions = [];
				}
				var alarmName = $("#alarmNameKey").val();
				if(alarmName!=""){
					conditions.push({
						name:"ALARM_NAME",
						value:alarmName
					});	
				}
				conditions.push({
					name:"CITY_NAME",
					value:$("#CITY_SELECT").val()
				});
				conditions.push({
					name:"COUNTRY_NAME",
					value:$("#REGION_SELECT").val()
				});
				oTable.fnDraw();
			}
		</script>
  	</head>
  	<body style="width:99.6%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;border-bottom:0px;">>> 活动告警明细
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入站址编码或名称" style="cursor:pointer;width:150px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="siteNameKey"></input>
						<input type="text" placeholder="请输入告警名称" style="cursor:pointer;width:150px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="alarmNameKey"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
					</div>
					<div style="float:right;" id="REGION_CONTAINER">
							<select id='REGION_SELECT' style="width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;">
								<option value="-">- 请选择区县  -</option>
							</select>
						</div>
					<div style="float:right;" id="CITY_CONTAINER">
						<select id='CITY_SELECT' style="width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;">
							<option value="-">- 请选择地市  -</option>
						</select>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:solid 1px #DFE8F1;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th style="text-align:center;">地市</th>
										<th style="text-align:center;">区县</th>
										<th style="text-align:center;">站址编码</th>
										<th style="text-align:center;">站址名称</th>
										<th style="text-align:center;">告警级别&nbsp;&nbsp;<font color='red' size="4">*</font></th>
										<th style="text-align:center;">预警名称&nbsp;&nbsp;<font color='red' size="4">*</font></th>
										<th style="text-align:center;">预警开始时间</th>
										<th style="text-align:center;">预警结束时间</th>
										<th style="text-align:center;">预警类型&nbsp;&nbsp;<font color='red' size="4">*</font></th>
										<th style="text-align:center;">预警原因&nbsp;&nbsp;<font color='red' size="4">*</font></th>
										<th style="text-align:center;">站址类型</th>
										<th style="text-align:center;">站址状态</th>
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
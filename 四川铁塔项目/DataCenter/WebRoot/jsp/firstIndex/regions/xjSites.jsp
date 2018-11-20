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
    	<title>巡检站数明细</title>
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
						data:"typeCode=3&cityName="+cityName,
						async:false,
					 	success:function(resultObj){
					 		if(resultObj["SUCCESS"]){
						 		var Regions = resultObj["Regions"];
								var innerHtml = "";
								innerHtml+="<select id='REGION_SELECT' style='width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;'>";
								innerHtml+="	<option value='-'>- 请选择区县 -</option>";
								for(var i=0;i<Regions.length;i++){
									var regionName = Regions[i];
									innerHtml+="<option value='"+regionName["REGION_NAME"]+"'>"+regionName["REGION_NAME"]+"</option>";	
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
					data:"typeCode=C",
					async:false,
				 	success:function(resultObj){
				 		if(resultObj["SUCCESS"]){
					 		var Citys = resultObj["Citys"];
							var innerHtml = "";
							innerHtml+="<select id='CITY_SELECT' onchange='javascript:flushRegionSelect(this.value);' style='width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;margin-top:6px;margin-right:3px;padding-left:5px;'>";
							innerHtml+="	<option value='-'>- 请选择地市 -</option>";
							for(var i=0;i<Citys.length;i++){
								var regionName = Citys[i];
								innerHtml+="<option value='"+regionName["CITY"]+"'>"+regionName["CITY"]+"</option>";	
							}
							innerHtml+="</select>";
							document.getElementById("CITY_CONTAINER").innerHTML = innerHtml;
				 		}
				 	}
				});
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/25);
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
						"mData":"CITY",
						"sWidth":"15%"
					},{
						"mData":"REGION_ID",
						"sWidth":"15%"
					},{
						"mData":"SITE_CODE",
						"sWidth":"34%"
					},{
						"mData":"SITE_NAME",
						"sWidth":"35%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						
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
					"sAjaxSource":"${pageContext.request.contextPath}/xjSiteDetail/findDatas.ilf"
				});
			});
			function searchData(){
				var menuName = $("#siteNameKey").val();
				if(menuName!=""){
					conditions = [{
						name:"SITE_NAME",
						value:menuName
					}];	
				}else{
					conditions = [];
				}
				conditions.push({
					name:"CITY",
					value:$("#CITY_SELECT").val()
				});
				conditions.push({
					name:"REGION",
					value:$("#REGION_SELECT").val()
				});
				oTable.fnDraw();
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:484px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">>> 巡检站址明细
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入站点编码或名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="siteNameKey"></input>
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
			<div class="panel-body" style="border:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">地市</th>
										<th style="text-align:center;">区县</th>
										<th style="text-align:center;">站址编码</th>
										<th style="text-align:center;">站址名称</th>
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
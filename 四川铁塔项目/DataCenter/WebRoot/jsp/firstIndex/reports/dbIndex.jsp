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
    	<title>数据中心首页</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				/*默认日期渲染*/
				var date = new Date();
				var curYears = date.getFullYear();
				var curMonth = date.getMonth();
				var curDates = date.getDate();
				if(curMonth==0){
					curMonth = 12;
					curYears = curYears-1;
				}else{
					curMonth++;
				}
				var finalStr = curYears+"-"+(curMonth<10?"0"+curMonth:curMonth)+"-"+(curDates<10?"0"+curDates:curDates);
				$("#designedDate").val(finalStr);
				/*开始获取数据*/
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/dataCenterPage.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:"cityName=-1&thisMonth=-1",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							/*地市列表*/
							var cityArray = textStatus["CITY_LIST"];
							var citySelect = "";
							citySelect+="<select style='width:180px;height:29px;border:solid 1px #A3D0E3;' id='citySelect'>";
							for(var i=0;i<cityArray.length;i++){
								var cityName = cityArray[i];
								citySelect+="<option value='"+cityName["CITY_NAME"]+"'>"+cityName["CITY_NAME"]+"</option>";
							}
							citySelect+="</select>";
							document.getElementById("citySelection").innerHTML = citySelect;
							/*
								=== 初始化地市信息   ===
							 */
							var cityName = "";
							if(textStatus["IS_PROVINCE"]){
								cityName = "全省";
							}else{
								cityName = textStatus["MY_CITY"];
							}
							if(cityName!=""){
								if(cityName.length>2){
									cityName = cityName.substring(0,2);
								}
								var citySelectObj = document.getElementById("citySelect");
								if(citySelectObj!=null && citySelectObj.options!=null && citySelectObj.options.length>0){
									for(var i=0;i<citySelectObj.options.length;i++){
			                             var optionValue = citySelectObj.options[i].value;
			                             if(optionValue.indexOf(cityName)!=-1){
											citySelectObj.options[i].selected = "selected";
		                                    break;
			                             }
									}
								}	
							}
							doFlush();
						}
					},
					error:function(){}
				});
			});
			function doFlush(){
				window.parent.showCircleLoading();
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/dataCenterPage.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:"cityName="+$("#citySelect").val()+"&thisMonth="+$("#designedDate").val(),
					timeout:10000,
					success:function(textStatus){
						window.parent.hideCircleLoading();
						if(textStatus["SUCCESS"]){
							var scCounts = textStatus["SC_COUNT"];
							$("#SC_TABLE").css({
								height:120
							});
							var innerHtml = "";
							if(scCounts.length>0){
								for(var i=0;i<scCounts.length;i++){
									var scCount = scCounts[i];
									var fontColor = "black";
									if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])>=1 && parseInt(scCount["序号"])<=5){
										/*1~5为红色*/
										fontColor = "red";
									}else if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])>=9 && parseInt(scCount["序号"])<=25){
										/*9~25为红色*/
										fontColor = "red";
									}else if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])>=38 && parseInt(scCount["序号"])<=39){
										/*38~39为红色*/
										fontColor = "red";
									}else if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])>=6 && parseInt(scCount["序号"])<=8){
										/*6~8为黄色*/
										fontColor = "#FFC000";
									}else if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])>=26 && parseInt(scCount["序号"])<=37){
										/*26~37为黄色*/
										fontColor = "#FFC000";
									}
									innerHtml+="<tr>";
									innerHtml+="	<td><a style='cursor:pointer;' onclick='javascript:turnToCompareDetail(\""+scCount["运营数据"]+"\");'>"+scCount["运营数据"]+"</a></td>";
									innerHtml+="	<td>"+scCount["序号"]+"</td>";
									innerHtml+="	<td style='text-align:center;'>"+scCount["S标准数据来源系统"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["稽核系统"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["统一业务接入平台"]+"</td>";
									if(scCount["序号"]!=null && scCount["序号"]!="" && parseInt(scCount["序号"])==9){
										innerHtml+="<td style='color:#FFC000'>"+scCount["CRM"]+"</td>";
									}else{
										innerHtml+="<td style='color:"+fontColor+";'>"+scCount["CRM"]+"</td>";	
									}
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["规划选址"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["PMS"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["资源系统"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["运维监控"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["物业系统"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["商务平台"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["合同系统"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["财务收入成本"]+"</td>";
									innerHtml+="	<td style='color:"+fontColor+";'>"+scCount["财务资产卡片"]+"</td>";
									innerHtml+="</tr>";
									if(i==(scCounts.length-1)){
										innerHtml+="<tr height='10'><td colspan='15'></td></tr>";
									}
								}
								document.getElementById("ITEM_DATAS").innerHTML = innerHtml;
							}else{
								innerHtml+="<tr>";
								innerHtml+="	<td colspan='15' style='text-align:center;'>暂无数据</td>";
								innerHtml+="</tr>";
								document.getElementById("ITEM_DATAS").innerHTML = innerHtml;
							}
						}
					},
					error:function(){
						window.parent.hideCircleLoading();
					}
				});
			}
			function turnToCompareDetail(dataType){
				var DATA_TYPE = dataType;
				var CITY_NAME = $("#citySelect").val();
				var COUNT_DATE = $("#designedDate").val();
				if(CITY_NAME=="全省" || CITY_NAME=="-"){
					window.wxc.xcConfirm("请先指定一个地市.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(COUNT_DATE==""){
					window.wxc.xcConfirm("请先指定一个统计周期.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				window.parent.addTabForJsp(CITY_NAME+"-"+COUNT_DATE+"-"+DATA_TYPE,"jsp/firstIndex/reports/dbIndexDetai.jsp?cityName="+encodeURIComponent(encodeURIComponent(CITY_NAME,"UTF-8"),"UTF-8")+"&countDate="+COUNT_DATE+"&dataType="+encodeURIComponent(encodeURIComponent(DATA_TYPE,"UTF-8"),"UTF-8"));
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 0px red;height:485px;overflow:hidden;" id="bodyHeight">
		<div class="panel panel-default" style="margin-top:-20px;height:503px;border:solid 0px red;" id="mainPanel">
			<div class="panel-heading" id="panelHeading">&nbsp;&nbsp;>>&nbsp;&nbsp;数据中心
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;margin-right:3px;margin-top:6px;" id="citySelection">
							<select style="width:180px;height:29px;border:solid 1px #A3D0E3;" id="citySelect">
								<option value="-">-</option>
							</select>
						</div>
						<input id="designedDate" type="text" placeholder="请选择统计日期" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"></input>
						<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:doFlush();">
							<i class="icon-plus-sign icon-white"></i>执行统计
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="height:440px;border:solid 1px #FFF;overflow:auto;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:435px;width:auto;overflow:auto;border:solid 1px #FFF;">
							<table id="SC_TABLE" class="table table-bordered table-hover" style="height:120px;border:solid 0px red;">
								<thead>
									<tr>
										<td style="background:#FFC000;text-align:center;" colspan="15">S-C比对结果统计汇总表</td>
									</tr>
									<tr>
										<td style="background:#FFC000;text-align:center;" rowspan="2">运营数据</td>
										<td style="background:#FFC000;text-align:center;" rowspan="2">序号</td>
										<td style="background:#FFC000;text-align:center;" rowspan="2">S标准数据<br/>来源系统</td>
										<td style="background:#FFC000;text-align:center;" colspan="12">C参考数据来源系统</td>
									</tr>
									<tr>
										<td style="background:#FFC000;text-align:center;">稽核系统</td>
										<td style="background:#FFC000;text-align:center;">统一业务<br/>接入平台</td>
										<td style="background:#FFC000;text-align:center;">CRM</td>
										<td style="background:#FFC000;text-align:center;">规划选址</td>
										<td style="background:#FFC000;text-align:center;">PMS</td>
										<td style="background:#FFC000;text-align:center;">资源系统</td>
										<td style="background:#FFC000;text-align:center;">运维监控</td>
										<td style="background:#FFC000;text-align:center;">物业系统</td>
										<td style="background:#FFC000;text-align:center;">商务平台</td>
										<td style="background:#FFC000;text-align:center;">合同系统</td>
										<td style="background:#FFC000;text-align:center;">财务接入<br/>成本</td>
										<td style="background:#FFC000;text-align:center;">财务资产<br/>卡片</td>
									</tr>
									<tr>
										<td colspan="15" style="background:#FFC000;;">
											比对结果中红色字体表示比对规则直接引用原字段值直接进行比对；黄色字体表示字段先通过枚举值标准表翻译转换，然后再比对。该表统计数字表示“C参考数据来源系统”的“运营数据”下对应<br/>字段与“S标准数据来源”的标准值进行比对，比对结果不一致的数量。例如“运营数据”为：经度，“S标准数据来源”为：稽核系统，“C参考数据来源系统”为资管系统，假如统计数字为xx，则表示资<br/>管系统的经度与稽核系统的经度进行比对，不一致的数量有xx条。
										</td>
									</tr>
								<thead>
								<tbody id="ITEM_DATAS">
									<tr>
										<td style="text-align:center;">-</td>
										<td style="text-align:center;">-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
									</tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
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
    	<title>新增台账</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			var acheMap = null;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#siteNames").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				conditions = [{
					name:"MODEL_NAME",
					value:"WY_ELECTRI_FEE_DS"
				}];
				/*初始化菜单 列表*/
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
					"sScrollY":"200px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"SITE_CODE",
						"sWidth":"8%"
					},{
						"mData":"CITY",
						"sWidth":"14%"
					},{
						"mData":"REGION_ID",
						"sWidth":"14%"
					},{
						"mData":"SITE_CODE",
						"sWidth":"32%"
					},{
						"mData":"SITE_NAME",
						"sWidth":"31.5%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":7,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheMap==null){
							acheMap = new HashMap();
						}
						acheMap.put(aData["SITE_CODE"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["SITE_CODE"]+"' name='checksRadio' onchange='javascript:uniqueSelected(this.checked,this.name,this.value);'></input></center>");
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
					"sAjaxSource":"${pageContext.request.contextPath}/elecPayHistory/findSiteInfos.ilf"
				});
			});
			function uniqueSelected(isChecked,checkboxName,checkedValue){
				if(isChecked){
					var checkboxes = document.getElementsByName(checkboxName);
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							if(checkboxes[i].value==checkedValue){
								checkboxes[i].checked = true;
								var checkedObj = acheMap.get(checkedValue);
								if(checkedObj!=null){
									$("#CITY_NAME_INPUT").val(checkedObj["CITY"]);
									$("#REGION_NAME_INPUT").val(checkedObj["REGION_ID"]);
									$("#SITE_CODE_INPUT").val(checkedObj["SITE_CODE"]);
									$("#SITE_NAME_INPUT").val(checkedObj["SITE_NAME"]);
									$("#headingTitle").html("&nbsp;>> "+checkedObj["SITE_NAME"]);
								}
							}else{
								checkboxes[i].checked = false;
							}
						}
					}
				}else{
					$("#CITY_NAME_INPUT").val("");
					$("#REGION_NAME_INPUT").val("");
					$("#SITE_CODE_INPUT").val("");
					$("#SITE_NAME_INPUT").val("");
					$("#headingTitle").html("&nbsp;>> 台账基本信息");
				}
			}
			function searchData(){
				var siteName = $("#siteNames").val();
				if(siteName!=""){
					conditions = [{
						name:"SITE_NAME",
						value:siteName
					}];	
				}else{
					conditions = [];
				}
				oTable.fnDraw();
			}
			function saveMyEdit(){
				window.wxc.xcConfirm("是否确认保存此台账信息？..","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var editObj = {
							SITE_CODE:$("#SITE_CODE_INPUT").val(),
							SITE_NAME:$("#SITE_NAME_INPUT").val(),
							SUPPLY_TYPE:$("#SUPPLY_TYPE_SELECT").val(),
							CITY_NAME:$("#CITY_NAME_INPUT").val(),
							REGION_NAME:$("#REGION_NAME_INPUT").val(),
							DATE_CIRCLE:$("#DATE_CIRCLE_INPUT").val(),
							TOTAL:$("#TOTAL_INPUT").val(),
							PAYOUT_COUNT:$("#PAYOUT_COUNT_INPUT").val(),
							BUY_BEGIN_DATE:$("#BUY_BEGIN_DATE_INPUT").val(),
							BUY_LIMIT_DATE:$("#BUY_LIMIT_DATE_INPUT").val(),
							BUY_BEGIN_COUNT:$("#BUY_BEGIN_COUNT_INPUT").val(),
							BUY_LIMIT_COUNT:$("#BUY_LIMIT_COUNT_INPUT").val(),
							USER_ACCOUNT:$("#USER_ACCOUNT_INPUT").val(),
							AMETER_CODE:$("#AMETER_CODE_INPUT").val(),
							AMETER_TYPE:$("#AMETER_TYPE_INPUT").val(),
							ELEC_LOST:$("#ELEC_LOST_INPUT").val(),
							OTHER_COST:$("#OTHER_COST_INPUT").val(),
							ELEC_PRICE:$("#ELEC_PRICE_INPUT").val()
						};
						if(editObj["SITE_CODE"]==""){
							window.wxc.xcConfirm("请先选择站点.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["DATE_CIRCLE"]==""){
							window.wxc.xcConfirm("请填写台账所属期间.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["PAYOUT_COUNT"]==""){
							window.wxc.xcConfirm("请填写缴费金额（元）.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["BUY_BEGIN_COUNT"]==""){
							window.wxc.xcConfirm("请填写起始度数.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["BUY_LIMIT_COUNT"]==""){
							window.wxc.xcConfirm("请填写终止度数.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["AMETER_CODE"]==""){
							window.wxc.xcConfirm("请填写电表编码.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						if(editObj["ELEC_PRICE"]==""){
							window.wxc.xcConfirm("请填写电价.",window.wxc.xcConfirm.typeEnum.info);
							return;
						}
						$.ajax({
							url:"${pageContext.request.contextPath}/elecPayHistory/saveAudit.ilf",
							async:false,
							type:"POST",
							data:"params="+JSON.stringify(editObj),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["SUCCESS"]){
									window.wxc.xcConfirm("台账录入成功.",window.wxc.xcConfirm.typeEnum.info);
								}
							},
							error:function(){
								window.wxc.xcConfirm("台账录入失败.",window.wxc.xcConfirm.typeEnum.error);
							}
						});
					}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfSiteCode" value=""></input>
  		<input type="hidden" id="hiddenOfSiteName" value=""></input>
  		<div class="panel panel-default" style="margin-top:-17px;height:320px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;">&nbsp;>> 选择站点
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入站址名称" style="width:200px;height:29px;font-size:12px;border:solid 1px #A3D0E3;" id="siteNames"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询
						</a>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;border:solid 1px #A3D0E3;border-top:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
								<thead>
									<tr>
										<th style="text-align:center;">&nbsp;</th>
										<th style="text-align:center;">地市</th>
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
  		<div class="panel panel-default" style="margin-top:10px;height:290px;border:solid 0px red;">
  			<div class="panel-heading" style="border:solid 1px #A3D0E3;" id="headingTitle">&nbsp;>> 台账基本信息</div>
  			<div class="panel-body" style="border:0px;border:solid 1px #A3D0E3;border-top:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" style="width:99.9%;font-size:12px;border:solid 1px #A3D0E3;border-bottom:0px;">
								<tbody>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">站点编码：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" readonly id="SITE_CODE_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">站点名称：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" readonly id="SITE_NAME_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">用电类型：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<select id="SUPPLY_TYPE_SELECT" style="width:95%;height:25px;font-size:12px;border:solid 1px #A3D0E3;">
												<option value="WY_ELECTRI_FEE_DS">直供电</option>
												<option value="WY_ELECTRI_FEE_TURN">转供电</option>
											</select>
										</td>
									</tr>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">地市：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" readonly id="CITY_NAME_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">区县：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" readonly id="REGION_NAME_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">电费所属期间：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;cursor:pointer;" onfocus="WdatePicker({dateFmt:'yyyy年MM月'})" class="Wdate" id="DATE_CIRCLE_INPUT"/>
										</td>
									</tr>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">总电量：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="TOTAL_INPUT" value="0.0"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">缴费金额（元）：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="PAYOUT_COUNT_INPUT" value="0.0"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">购电开始日期：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;cursor:pointer;" id="BUY_BEGIN_DATE_INPUT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
										</td>
									</tr>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">购电截止日期：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;cursor:pointer;" id="BUY_LIMIT_DATE_INPUT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">用电起始度数：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="BUY_BEGIN_COUNT_INPUT" value="0.0"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">用电终止度数：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="BUY_LIMIT_COUNT_INPUT" value="0.0"/>
										</td>
									</tr>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">缴费户号：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="USER_ACCOUNT_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">电表编码：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="AMETER_CODE_INPUT"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #DEEFF8;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">电表类型：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<select style="width:95%;height:25px;font-size:12px;border:solid 1px #A3D0E3;" id="AMETER_TYPE_INPUT">
												<option value="普通电表">普通电表</option>
												<option value="普通电表（购电插卡式）">普通电表（购电插卡式）</option>
												<option value="智能电表">智能电表</option>
											</select>
										</td>
									</tr>
									<tr height="30">
										<td style="width:13.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">电损：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="ELEC_LOST_INPUT" value="0.0"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">其他费用（元）：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="OTHER_COST_INPUT" value="0.0"/>
										</td>
										<td style="width:13.6%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:right;background:#A3D0E3;">电价（元）：</td>
										<td style="width:19.6%;border-bottom:solid 1px #A3D0E3;text-align:center;">
											<input type="text" style="width:95%;height:25px;font-size:12px;margin-top:5px;border:solid 1px #A3D0E3;" id="ELEC_PRICE_INPUT" value="0.0"/>
										</td>
									</tr>
									<tr height="40">
										<td colspan="6" style="text-align:center;border-bottom:solid 1px #A3D0E3;">
											<a class="btn btn-inverse" style="cursor:pointer;width:150px;" onclick="javascript:saveMyEdit();">
												<i class="icon-upload icon-white"></i>保存编辑
											</a>
										</td>
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
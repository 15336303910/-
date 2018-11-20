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
    	<title>标准工期管理</title>
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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/citySelect/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/Popt.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/cityJson.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/citySelect/js/citySet.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/tips/jquery.shCircleLoader.js"></script>
		<style type="text/css">
			#shclNes{
				width:100px;
				height:100px;
			}
		</style>
		<script type="text/javascript">	
			var oTable = null;
			var conditions = [];
			var acheItems = null;
			$(document).ready(function(){
				/*加载中...*/
				$("#shclNes").shCircleLoader({
					namespace:"myns",
					color:"#6BC2EB",
					dotsRadius:15
				});
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#bodyHeight").height()
			    });
				var tableHeight = $("#mainPanel").height()-$("#panelHeading").height()-80;
				var pageNumbers = parseInt(tableHeight/26);
				oTable = $("#dataTable").dataTable({
					"bSortClasses":false,
					"aLengthMenu":[10,20,30],
					"bAutoWidth":true,
					"bSort":true,
					"bProcessing":false,
					"bServerSide":true,
					"bFilter":false,
					"bLengthChange":false,
					"sPaginationType":"full_numbers",
					"bStateSave":false,
					"bScrollCollapse":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"DICTID",
						"sWidth":"10%"
					},{
						"mData":"DICTID",
						"sWidth":"10%"
					},{
						"mData":"DICTNAME",
						"sWidth":"20%"
					},{
						"mData":"WORK_CIRCLE_CONFIGED",
						"sWidth":"20%"
					},{
						"mData":"EMPLOYEE_NAME",
						"sWidth":"18%"
					},{
						"mData":"CONFIG_TIME",
						"sWidth":"20%"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["DICTID"],aData);
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["DICTID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						if(aData["WORK_CIRCLE_CONFIGED"]==null){
							$("td:eq(3)",nRow).html("<div id='DICT_KEY_"+aData["DICTID"]+"' onclick='javascript:editNumber(\""+aData["DICTID"]+"\");' style='width:99%;height:99%;cursor:pointer;'>-</div>");	
						}else{
							$("td:eq(3)",nRow).html("<div id='DICT_KEY_"+aData["DICTID"]+"' onclick='javascript:editNumber(\""+aData["DICTID"]+"\");' style='width:99%;height:99%;cursor:pointer;'>"+aData["WORK_CIRCLE_CONFIGED"]+"</div>");
						}
						if(aData["CONFIG_TIME"]!=null){
							var configDate = aData["CONFIG_TIME"];
							var date = new Date();
							date.setTime(configDate.time);
							$("td:eq(5)",nRow).html(date.toLocaleString());	
						}
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						$("#shclNes").show();
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
							dataType:"json",		
							success:function(data){
								$("#shclNes").hide();
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/workTimeConfigAction/findItems.ilf"
				});
			});
			function searchData(){
				var PRO_NAME = $("#PRO_NAME_INPUT").val();
				if(PRO_NAME!=""){
					conditions = [{
						name:"PRO_NAME",
						value:PRO_NAME
					}];	
				}else{
					conditions = [];
				}
				conditions.push({
					name:"PRO_STATUS",
					value:$("#PRO_STATUS_SELECT").val()
				});
				oTable.fnDraw();
			}
			function editNumber(editCode){
				$("#hiddenOfEditCode").val(editCode);
				var editObj = acheItems.get(editCode);
				if(editObj["WORK_CIRCLE_CONFIGED"]==null || editObj["WORK_CIRCLE_CONFIGED"]==""){
					document.getElementById("DICT_KEY_"+editCode).innerHTML = "<input type='text' id='INPUT_"+editCode+"' style='width:99%;height:25px;border:solid 1px #A3D0E3;font-size:12px;' value=''></input>";
				}else{
					document.getElementById("DICT_KEY_"+editCode).innerHTML = "<input type='text' id='INPUT_"+editCode+"' style='width:99%;height:25px;border:solid 1px #A3D0E3;font-size:12px;' value=''></input>";	
				}
				/*保证输入唯一*/
				var valueSet = acheItems.valueSet();
				for(var i=0;i<valueSet.length;i++){
					var valueObj = valueSet[i];
					if(valueObj["DICTID"]!=editCode && document.getElementById("DICT_KEY_"+valueObj["ID"])!=null){
						if(valueObj["WORK_CIRCLE_CONFIGED"]==null || valueObj["WORK_CIRCLE_CONFIGED"]==""){
							document.getElementById("DICT_KEY_"+valueObj["DICTID"]).innerHTML = "<div id='DICT_KEY_"+valueObj["DICTID"]+"' onclick='javascript:editNumber("+valueObj["DICTID"]+");' style='width:99%;height:99%;cursor:pointer;'>-</div>";
						}else{
							document.getElementById("DICT_KEY_"+valueObj["DICTID"]).innerHTML = "<div id='DICT_KEY_"+valueObj["DICTID"]+"' onclick='javascript:editNumber("+valueObj["DICTID"]+");' style='width:99%;height:99%;cursor:pointer;'>"+valueObj["WORK_CIRCLE_CONFIGED"]+"</div>";	
						}
					}
				}
				/*聚焦输入*/
				$("#INPUT_"+editCode).focus();
				/*绑定事件*/
				$("#INPUT_"+editCode).bind("keydown",function(event){
					if(event.keyCode==8){
						var editingCode = $("#hiddenOfEditCode").val();
						var newCircleValue = $("#INPUT_"+editingCode).val();
						if(newCircleValue!=null && newCircleValue!="" && newCircleValue.length>0){
							var deletedValue = newCircleValue.substr(0,newCircleValue.length-1);
							if(deletedValue!=null){
								$("#INPUT_"+editingCode).val(deletedValue);	
							}else{
								$("#INPUT_"+editingCode).val("");	
							}
						}else{
							$("#INPUT_"+editingCode).val("");
						}
						return false;
					}else if(event.keyCode==13){
						var editingCode = $("#hiddenOfEditCode").val();
						if(editingCode!=null && editingCode!=""){
							var newCircleValue = $("#INPUT_"+editingCode).val();
							/*保存入库*/
							$("#shclNes").show();
							$.ajax({
								url:"${pageContext.request.contextPath}/workTimeConfigAction/updateCount.ilf",
								async:false,
								type:"POST",
								data:"nodeCode="+$("#hiddenOfEditCode").val()+"&newCircleValue="+newCircleValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									$("#shclNes").hide();
									oTable.fnDraw();
								},
								error:function(){
									$("#shclNes").hide();
								}
							});
							$("#hiddenOfEditCode").val("");
						}
						return false;
					}
				});
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="" id="hiddenOfEditCode"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:485px;border:solid 0px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>项目管理 >> 标准工期管理&nbsp;&nbsp;&nbsp;<font color="red">*&nbsp;点击标准工期进行标准工期（天）编辑，输入完毕后敲击Enter保存.</font>
			</div>
			<div class="panel-body" style="border:solid 1px #A3D0E3;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table cellpadding="0" cellspacing="0" border="0" id="dataTable">
								<thead>
									<tr>
										<th style="text-align:center;width:50px;">&nbsp;</th>
										<th style="text-align:center;">状态编码</th>
										<th style="text-align:center;">状态名称</th>
										<th style="text-align:center;">标准工期（天）</th>
										<th style="text-align:center;">配置人员</th>
										<th style="text-align:center;">配置时间</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="shclNes" style="position:fixed;top:40%;right:47%;display:none;"></div>
	</body>
</html>
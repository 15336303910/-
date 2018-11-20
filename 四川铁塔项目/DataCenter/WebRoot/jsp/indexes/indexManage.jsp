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
    	<title>指标管理</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<%--引入已封装的工具js--%>
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
			$(document).ready(function(){
				/*初始化界面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				var tableHeight = $("#bodyHeight").height()-$("#panelHeading").height()-75;
				var pageNumbers = parseInt(tableHeight/30);
				/*指标数据展示[列表]*/
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
						"mData":"ID",
						"sWidth":"7%"
					},{
						"mData":"INDEX_NAME",
						"sWidth":"40%"
					},{
						"mData":"INDEX_LEVEL",
						"sWidth":"12%"
					},{
						"mData":"CREATE_DATE",
						"sWidth":"20.1%"
					},{
						"mData":"IS_USING",
						"sWidth":"20.1%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						/*缓存数据*/
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						/*模型选择*/
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						/*属性展示*/
						$("td:eq(1)",nRow).html("<a style='cursor:pointer;text-decoration:underline;' onclick='javascript:turnToPage("+aData["ID"]+");'>"+aData["INDEX_NAME"]+"</a>");
						/*指标级别*/
						if(aData["INDEX_LEVEL"]==2){
							$("td:eq(2)",nRow).html("<font color='green'>一般</font>");
						}else if(aData["INDEX_LEVEL"]==3){
							$("td:eq(2)",nRow).html("<font color='orange'>重要</font>");
						}else if(aData["INDEX_LEVEL"]==5){
							$("td:eq(2)",nRow).html("<font color='red'>非常重要");
						}
						/*创建日期*/
						var $date = new Date();
						$date.setTime(aData["CREATE_DATE"]["time"]);
						$("td:eq(3)",nRow).html($date.toLocaleString());
						/*是否在用*/
						if(aData["IS_USING"]=="Y"){
							$("td:eq(4)",nRow).html("<center><font color='green'>在用</font></center>");	
						}else{
							$("td:eq(4)",nRow).html("<center><font color='red'>停用</font></center>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/indexDetailAction/findItems.ilf"
				});
				$("#dataTable tbody").click(function(event){
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				$("#kpiKeyInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
			});
			function getData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			};
			/*界面跳转*/
			function turnToPage(thisValue){
				window.parent.turnToJsp("指标维护","jsp/indexes/edit/editKpi.jsp?kpiId="+thisValue);
			}
			function initIfChecked(){
				var indexCode = -1;
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					indexCode = checkedValue;
				}
				window.parent.turnToJsp("指标维护","jsp/indexes/edit/editKpi.jsp?kpiId="+indexCode);
			}
			function searchData(){
				conditions = [];
				var kpiLevel = $("#gradeSelection").val();
				if(kpiLevel!="-"){
					conditions.push({
						name:"INDEX_LEVEL",
						value:kpiLevel
					});
				}
				var kpiName = $("#kpiKeyInput").val();
				if(kpiName!=""){
					conditions.push({
						name:"INDEX_NAME",
						value:kpiName
					});				
				}			
				oTable.fnDraw();
			}
			/*删除指标*/
			function deleteModel(){				
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					window.wxc.xcConfirm("是否确认删除指标.","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/indexDetailAction/deleteIndex.ilf",
								async:false,
								type:"POST",
								data:"indexCode="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("指标删除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("指标删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("指标删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}else{
					window.wxc.xcConfirm("请先选择一个指标.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function kpiChart(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue!=null){
					window.parent.turnToJsp("指标分析","jsp/indexes/chart/KpiChart.jsp?kpiId="+checkedValue);
				}else{
					window.wxc.xcConfirm("请先选择一个指标.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;border:0px;">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>指标管理 >> 指标配置
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select id="gradeSelection" style="width:160px;height:30px;border:solid 1px #A3D0E3;border-radius:6px;">
							<option value="-">-- 查看全部 --</option>
							<option value="2">一般</option>
							<option value="3">重要</option>
							<option value="5">非常重要</option>
						</select>
						<input type="text" placeholder="请输入指标名称" style="width:160px;height:29px;font-size:12px;margin-left:3px;border:solid 1px #A3D0E3;border-radius:6px;" id="kpiKeyInput"></input>
						<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:searchData();"></img>
						<a class="btn btn-success" style="cursor:pointer" onclick="javascript:initIfChecked();">
							<i class="icon-plus-sign icon-white"></i>维护指标信息
						</a>
						<a class="btn btn-warning" style="cursor:pointer" onclick="javascript:kpiChart();">
							<i class="icon-signal icon-white"></i>指标分析
						</a>
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteModel();">
							<i class="icon-trash icon-white"></i>删除指标
						</a>	
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
										<th>&nbsp;</th>
										<th>指标名称</th>
										<th>指标级别</th>
										<th>创建日期</th>
										<th>启用/停用</th>
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
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
    	<title>指标编辑</title>
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
		<script type="text/javascript">
			var oTable = null;
			var conditions = [];
			var zTable = null;
			var zConditions = [];
			var totalRatio = 0;
			var acheItems = null;
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
				/*指标浏览*/
				conditions = [{
					name:"EXPECT_USER",
					value:$("#hiddenOfUserCode").val()
				}];
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
						"sWidth":"74%"
					},{
						"mData":"INDEX_LEVEL",
						"sWidth":"20%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='indexCheckes'></input></center>");
						if(aData["INDEX_LEVEL"]==2){
							$("td:eq(2)",nRow).html("<font color='green'>一般</font>");
						}else if(aData["INDEX_LEVEL"]==3){
							$("td:eq(2)",nRow).html("<font color='orange'>重要</font>");
						}else if(aData["INDEX_LEVEL"]==5){
							$("td:eq(2)",nRow).html("<font color='red'>非常重要</font>");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/indexUserAction/findItems.ilf"
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
						"mData":"ID",
						"sWidth":"7%"
					},{
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
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						totalRatio+=aData["INDEX_LEVEL"];
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='hasChecked'></input></center>");
						$("td:eq(2)",nRow).html("<div id='dataContainer"+aData["ID"]+"'><font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+aData["ID"]+");'>"+aData["INDEX_LEVEL"]+"%</font></div>");
						$("#ratioShow").html("（权重合计："+totalRatio+"%）");
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
			});
			function reaudits(){
				totalRatio = 0;
				var valueSet = acheItems.valueSet();
				for(var i=0;i<valueSet.length;i++){
					var valueObj = valueSet[i];
					totalRatio+=parseInt(valueObj["INDEX_LEVEL"]);
				}
				$("#ratioShow").html("（权重合计："+totalRatio+"%）");
			}
			function editNumber(editCode){
				$("#hiddenOfEditCode").val(editCode);
				var editObj = acheItems.get(editCode);
				document.getElementById("dataContainer"+editCode).innerHTML = "<input type='text' id='Input"+editCode+"' style='width:50px;height:25px;' value='"+editObj["INDEX_LEVEL"]+"'></input>";
				/*保证输入唯一*/
				var valueSet = acheItems.valueSet();
				for(var i=0;i<valueSet.length;i++){
					var valueObj = valueSet[i];
					if(valueObj["ID"]!=editCode && document.getElementById("dataContainer"+valueObj["ID"])!=null){
						document.getElementById("dataContainer"+valueObj["ID"]).innerHTML = "<font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+valueObj["ID"]+");'>"+valueObj["INDEX_LEVEL"]+"%</font>";
					}
				}
				/*聚焦输入*/
				$("#Input"+editCode).focus();
				/*绑定事件*/
				$("#Input"+editCode).bind("keydown",function(event){
					if(event.keyCode==13){
						var editingCode = $("#hiddenOfEditCode").val();
						/*缓存编辑成果*/
						var editingObj = acheItems.get(editingCode);
						editingObj["INDEX_LEVEL"] = $("#Input"+editingCode).val();
						acheItems.put(editingCode,editingObj);
						/*初始化界面*/
						document.getElementById("dataContainer"+editingCode).innerHTML = "<font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+editingCode+");'>"+$("#Input"+editingCode).val()+"%</font>";
						/*保存入库*/
						$.ajax({
							url:"${pageContext.request.contextPath}/indexUserAction/updateCount.ilf",
							async:false,
							type:"POST",
							data:"userCode="+$("#hiddenOfUserCode").val()+"&indexCode="+editingCode+"&count="+editingObj["INDEX_LEVEL"],
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){},
							error:function(){}
						});
						reaudits();
						return false;
					}
				});
			}
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
			function getZData(sSource,aoData,fnCallback){
				totalRatio = 0;
				acheItems = new HashMap();
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
			function searchData(){
				conditions = [{
					name:"EXPECT_USER",
					value:$("#hiddenOfUserCode").val()
				}];
				var kpiKey = $("#kpiKeyInput").val();
				if(kpiKey!=""){
					conditions.push({
						name:"INDEX_NAME",
						value:kpiKey
					});				
				}
				oTable.fnDraw();
			}
			function searchCheckedData(){
				zConditions = [{
					name:"BELONG_USER",
					value:$("#hiddenOfUserCode").val()
				}];			
				zTable.fnDraw();
			}
			function doInsert(){
				var indexes = document.getElementsByName("indexCheckes");
				if(indexes.length>0){
					var checkedIds = "";
					for(var i=0;i<indexes.length;i++){
						if(indexes[i].checked){
							if(checkedIds==""){
								checkedIds = indexes[i].value;
							}else{
								checkedIds+= ","+indexes[i].value;
							}
						}
					}
					if(checkedIds==""){
						window.wxc.xcConfirm("请至少选择一项指标进行添加.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						var userCode = $("#hiddenOfUserCode").val();
						if(userCode!=-1){
							$.ajax({
								url:"${pageContext.request.contextPath}/indexUserAction/saveAudit.ilf",
								async:false,
								type:"POST",
								data:"userCode="+userCode+"&indexCodes="+checkedIds,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										searchData();
										searchCheckedData();
									}
								},
								error:function(){}
							});	
						}
					}
				}else{
					window.wxc.xcConfirm("当前没有可供选择的指标.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function doRemove(){
				var indexes = document.getElementsByName("hasChecked");
				if(indexes.length>0){
					var checkedIds = "";
					for(var i=0;i<indexes.length;i++){
						if(indexes[i].checked){
							if(checkedIds==""){
								checkedIds = indexes[i].value;
							}else{
								checkedIds+= ","+indexes[i].value;
							}
						}
					}
					if(checkedIds==""){
						window.wxc.xcConfirm("请至少选择一项指标进行删除.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						var userCode = $("#hiddenOfUserCode").val();
						$.ajax({
							url:"${pageContext.request.contextPath}/indexUserAction/deleteChecked.ilf",
							async:false,
							type:"POST",
							data:"userCode="+userCode+"&indexCodes="+checkedIds,
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["success"]){
									searchData();
									searchCheckedData();
								}
							},
							error:function(){}
						});	
					}
				}else{
					window.wxc.xcConfirm("当前没有可供选择的指标.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=userId %>" id="hiddenOfUserCode"></input>
  		<input type="hidden" value="" id="hiddenOfEditCode"></input>
  		<div style="width:99.9%;height:484px;" id="outlineContainer">
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
  				<div style="float:left;width:49.3%;height:367px;margin-left:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="aTableHead">
						<span class="panel-label"></span>指标浏览
						<form class="form-search pull-right">	
							<div style="float:right;">
								<input type="text" placeholder="请输入指标名称" style="width:200px;height:29px;font-size:12px;margin-left:3px;" id="kpiKeyInput"></input>
								<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
									<i class="icon-search icon-white"></i>执行查询 
								</a>	
							</div>
						</form>
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td style="background:#FCFDFE;">&nbsp;</td>
												<td style="background:#FCFDFE;text-align:center;">指标名称</td>
												<td style="background:#FCFDFE;text-align:center;">重要性</td>
											</tr>
										</thead>
										<tbody></tbody>
									</table>					
								</div>
							</div>
						</div>
					</div>
  				</div>
  				<div style="float:left;width:5%;height:367px;margin-left:5px;">
  					<center>
  						<div style="margin-top:130px;">
  							<font color='green' size="5" style="cursor:pointer;" onclick="javascript:doInsert();">>></font><br/><br/><br/>
  							<font color='red' size="5" style="cursor:pointer;" onclick="javascript:doRemove();"><<</font>
  						</div>
  					</center>
  				</div>
  				<div style="float:right;width:44.3%;height:367px;margin-right:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
						<span class="panel-label"></span>用户已关联指标<font color='red' id="ratioShow">（权重合计：0%）</font>
					</div>
					<div class="panel-body h340" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="zDataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td style="background:#FCFDFE">&nbsp;</td>
												<td style="background:#FCFDFE;text-align:center;">指标名称</td>
												<td style="background:#FCFDFE;text-align:center;">重要性</td>
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
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
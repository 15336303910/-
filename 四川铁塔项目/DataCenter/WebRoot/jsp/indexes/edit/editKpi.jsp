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
			var acheItems = null;
			var totalRatio = 0;
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
				/*规则浏览*/
				conditions = [{
					name:"EXPECT_INDEX",
					value:$("#hiddenOfKpiCode").val()
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
						"sWidth":"8%"
					},{
						"mData":"TABLE_NAME",
						"sWidth":"20%"
					},{
						"mData":"RULE_NAME",
						"sWidth":"58%"
					},{
						"mData":"RULE_GRADE",
						"sWidth":"11%"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='ruleCheckes'></input></center>");
						if(aData["RULE_GRADE"]==2){
							$("td:eq(3)",nRow).html("<font color='green'>一般</font>");
						}else if(aData["RULE_GRADE"]==3){
							$("td:eq(3)",nRow).html("<font color='orange'>重要</font>");
						}else if(aData["RULE_GRADE"]==5){
							$("td:eq(3)",nRow).html("<font color='red'>非常重要");
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/indexDetailAction/findRuleUnchecked.ilf"
				});
				$("#dataTable tbody").click(function(event){
					$(oTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				$("#ruleKeyInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
				/*已选规则*/
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
						"mData":"ID",
						"sWidth":"8%"
					},{
						"mData":"TABLE_NAME",
						"sWidth":"20%"
					},{
						"mData":"RULE_NAME",
						"sWidth":"58%"
					},{
						"mData":"COUNT_RATIO",
						"sWidth":"10%"
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
						totalRatio+=aData["COUNT_RATIO"];
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='hasChecked'></input></center>");
						$("td:eq(3)",nRow).html("<div id='dataContainer"+aData["ID"]+"'><font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+aData["ID"]+");'>"+aData["COUNT_RATIO"]+"%</font></div>");
						$("#ratioShow").html("（权重合计："+totalRatio+"%）");
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
			});
			function reaudits(){
				totalRatio = 0;
				var valueSet = acheItems.valueSet();
				for(var i=0;i<valueSet.length;i++){
					var valueObj = valueSet[i];
					totalRatio+=parseInt(valueObj["COUNT_RATIO"]);
				}
				$("#ratioShow").html("（权重合计："+totalRatio+"%）");
			}
			function editNumber(editCode){
				$("#hiddenOfEditCode").val(editCode);
				var editObj = acheItems.get(editCode);
				document.getElementById("dataContainer"+editCode).innerHTML = "<input type='text' id='Input"+editCode+"' style='width:50px;height:25px;border:solid 1px #A3D0E3;' value='"+editObj["COUNT_RATIO"]+"'></input>";
				/*保证输入唯一*/
				var valueSet = acheItems.valueSet();
				for(var i=0;i<valueSet.length;i++){
					var valueObj = valueSet[i];
					if(valueObj["ID"]!=editCode && document.getElementById("dataContainer"+valueObj["ID"])!=null){
						document.getElementById("dataContainer"+valueObj["ID"]).innerHTML = "<font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+valueObj["ID"]+");'>"+valueObj["COUNT_RATIO"]+"%</font>";
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
						editingObj["COUNT_RATIO"] = $("#Input"+editingCode).val();
						acheItems.put(editingCode,editingObj);
						/*初始化界面*/
						document.getElementById("dataContainer"+editingCode).innerHTML = "<font color='green' style='cursor:pointer;' onclick='javascript:editNumber("+editingCode+");'>"+$("#Input"+editingCode).val()+"%</font>";
						/*保存入库*/
						$.ajax({
							url:"${pageContext.request.contextPath}/indexDetailAction/updateCount.ilf",
							async:false,
							type:"POST",
							data:"indexCode="+$("#hiddenOfKpiCode").val()+"&ruleCode="+editingCode+"&count="+editingObj["COUNT_RATIO"],
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
					name:"EXPECT_INDEX",
					value:$("#hiddenOfKpiCode").val()
				}];
				var ruleName = $("#ruleKeyInput").val();
				if(ruleName!=""){
					conditions.push({
						name:"RULE_NAME",
						value:ruleName
					});				
				}			
				oTable.fnDraw();
			}
			function searchCheckedData(){
				zConditions = [{
					name:"BELONG_INDEX",
					value:$("#hiddenOfKpiCode").val()
				}];			
				zTable.fnDraw();
			}
			function doInsert(){
				var rules = document.getElementsByName("ruleCheckes");
				if(rules.length>0){
					var checkedIds = "";
					for(var i=0;i<rules.length;i++){
						if(rules[i].checked){
							if(checkedIds==""){
								checkedIds = rules[i].value;
							}else{
								checkedIds+= ","+rules[i].value;
							}
						}
					}
					if(checkedIds==""){
						window.wxc.xcConfirm("请至少选择一项核查规则进行添加.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						var indexCode = $("#hiddenOfKpiCode").val();
						if(indexCode!=-1){
							$.ajax({
								url:"${pageContext.request.contextPath}/indexDetailAction/saveAudit.ilf",
								async:false,
								type:"POST",
								data:"indexCode="+indexCode+"&ruleCodes="+checkedIds,
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
						}else{
							window.wxc.xcConfirm("请先保存指标基本信息再进行添加规则.",window.wxc.xcConfirm.typeEnum.info);
						}
					}
				}else{
					window.wxc.xcConfirm("当前没有可供选择的核查规则.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function doRemove(){
				var rules = document.getElementsByName("hasChecked");
				if(rules.length>0){
					var checkedIds = "";
					for(var i=0;i<rules.length;i++){
						if(rules[i].checked){
							acheItems.remove(rules[i].value);					
							if(checkedIds==""){
								checkedIds = rules[i].value;
							}else{
								checkedIds+= ","+rules[i].value;
							}
						}
					}
					if(checkedIds==""){
						window.wxc.xcConfirm("请至少选择一项核查规则进行移除.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						var indexCode = $("#hiddenOfKpiCode").val();
						$.ajax({
							url:"${pageContext.request.contextPath}/indexDetailAction/deleteChecked.ilf",
							async:false,
							type:"POST",
							data:"indexCode="+indexCode+"&ruleCodes="+checkedIds,
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
					window.wxc.xcConfirm("当前没有可供选择的核查规则.",window.wxc.xcConfirm.typeEnum.info);
				}
			}
			function doSaveBasic(){
				var $obj = {
					indexCode:$("#hiddenOfKpiCode").val(),
					indexName:$("#indexNameInput").val(),
					isUsing:$("#isUsingSelect").val(),
					indexLevel:$("#gradeSelection").val()
				};
				if($obj["indexName"]==""){
					window.wxc.xcConfirm("指标名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if($obj["isUsing"]=="-"){
					window.wxc.xcConfirm("请选择指标状态.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if($obj["indexLevel"]=="-"){
					window.wxc.xcConfirm("请选择指标级别.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				$.ajax({
					url:"${pageContext.request.contextPath}/indexDetailAction/saveBasic.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify($obj),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							window.wxc.xcConfirm("保存成功.",window.wxc.xcConfirm.typeEnum.info);
							$("#hiddenOfKpiCode").val(textStatus["newCode"]);
						}
					},
					error:function(){}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" value="<%=kpiId %>" id="hiddenOfKpiCode"></input>
  		<input type="hidden" value="" id="hiddenOfEditCode"></input>
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
												<a class="btn btn-success" style="cursor:pointer;margin-top:-5px;" onclick="javascript:doSaveBasic();">
													<i class="icon-plus-sign icon-white"></i>保存基本信息
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
  			<div style="float:left;width:100%;margin-top:5px;">
  				<div style="float:left;width:49.3%;margin-left:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="aTableHead">
						<span class="panel-label"></span>规则浏览
						<form class="form-search pull-right">	
							<div style="float:right;">
								<input type="text" placeholder="请输入规则名称" style="width:200px;height:29px;font-size:12px;margin-left:3px;" id="ruleKeyInput"></input>
								<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
									<i class="icon-search icon-white"></i>执行查询 
								</a>	
							</div>
						</form>
					</div>
					<div class="panel-body" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td>&nbsp;</td>
												<td style="background:#FCFDFE;text-align:center;">归属模型</td>
												<td style="background:#FCFDFE;text-align:center;">规则名称</td>
												<td style="background:#FCFDFE;text-align:center;">规则等级</td>
											</tr>
										</thead>
										<tbody></tbody>
									</table>					
								</div>
							</div>
						</div>
					</div>
  				</div>
  				<div style="float:left;width:5%;height:360px;margin-left:5px;">
  					<center>
  						<div style="margin-top:130px;">
  							<font color='green' size="5" style="cursor:pointer;" onclick="javascript:doInsert();">>></font><br/><br/><br/>
  							<font color='red' size="5" style="cursor:pointer;" onclick="javascript:doRemove();"><<</font>
  						</div>
  					</center>
  				</div>
  				<div style="float:right;width:44.3%;margin-right:3px;">
  					<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
						<span class="panel-label"></span>指标已包含规则<font color='red' id="ratioShow">（权重合计：0%）</font>
					</div>
					<div class="panel-body" style="border:0px;">
						<div class="panlecontent container4 clearfix">
							<div class="div_scroll">
								<div style="height:auto;width:auto">
									<table cellpadding="0" cellspacing="0" border="0" id="zDataTable" style="border:0px;border-right:0px;">
										<thead>
											<tr>
												<td>&nbsp;</td>
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
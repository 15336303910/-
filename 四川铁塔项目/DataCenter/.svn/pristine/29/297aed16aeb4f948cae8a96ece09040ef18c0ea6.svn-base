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
    	<title>数据订阅</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<style type="text/css">
			.jstrees{
				overflow:auto;
				border:1px solid silver;
				min-height:100px;
				text-align:left;
				font-size:18px;
				font-size:2.4em;
				font-size:100%;
				float:left;
			}
			thead th{
				text-align:center;
			}
			.dataTables_wrapper{
				border:solid 1px #A3D0E3;
			}
		</style>
		<script type="text/javascript">
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			var tableHeight = 0;
			var pageAccount = 0;
			var pageNumber = 1;
			var pageContext = 4;
			var tableHeight = 300;
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mainPanel").css({
			    	"height":$("#hiddenOfHeight").val()-2
			    });
				$("#ruleEditsBuck").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-2
			    });
				$("#frmt").css({
			    	"height":$("#ruleEditsBuck").height()-$("#leftTreeHead").height()-10
			    });
				tableHeight = $("#ruleEditsBuck").height()-$("#ruleTablePanel").height()-41;
				pageAccount = parseInt(tableHeight/27);
				pageContext = parseInt($("#ruleClassBuck").height()/105);
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleManageAction/findDbData.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							var nodes = textStatus["nodes"];
							$("#frmt").on("changed.jstree",function(e,data){
								if(data.selected.length){
									var nodeCode = data.instance.get_node(data.selected[0]).id;
									var regExp = new RegExp("^[0-9]*$");
									if(regExp.test(nodeCode)){
										$("#hiddenOfTableId").val(nodeCode);
										/*
											====== 渲染模板列表  ====== 
										*/
										$.ajax({
											url:"${pageContext.request.contextPath}/dataDesignAction/findTemplatesConfiged.ilf",
											async:false,
											type:"POST",
											data:"modelId="+nodeCode,
											dataType:"json",
											timeout:10000, 
											success:function(textStatus){
												if(textStatus["SUCCESS"]){
													var Options = textStatus["OPTIONS"];
													var optionHtml = "";
													optionHtml+="<select id='TEMPLATE_SELECT' onchange='javascript:searchData();' style='width:160px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;'>";
													if(Options.length>0){
														for(var i=0;i<Options.length;i++){
															var optionObj = Options[i];
															optionHtml+="<option value='"+optionObj["BELONG_TEMPLATE"]+"'>"+optionObj["BELONG_TEMPLATE"]+"</option>";		
														}
													}else{
														optionHtml+="<option value='-'>尚未配置模板</option>";
													}
													optionHtml+="</select>";
													document.getElementById("TEMPLATE_SELECTION").innerHTML = optionHtml;
												}else{
													window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
												}
											},
											error:function(){
												window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
											}
										});
										$("#TEMPLATE_NAME_INPUT").val("");
										/*
											====== 渲染数据信息  ====== 
										*/
										unCheckAllBoxes("isCheckAll");
										acheItems = null;
										searchData();
									}
								}
							}).jstree({
								"core":{
									"data":nodes,
									"dblclick_toggle":true
								}
							});
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("模型浏览初始化失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				}];
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
					"destroy":true,
					"sScrollY":tableHeight+"px",
					"sScrollX":"99.9%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"140px"
					},{
						"mData":"COLUMN_ALIAS",
						"sWidth":"350px"
					},{
						"mData":"COLUMN_NAME",
						"sWidth":"250px"
					},{
						"mData":"DATA_TYPE",
						"sWidth":"130px"
					},{
						"mData":"ID",
						"sWidth":"300px"
					},{
						"mData":"IS_DESIGN",
						"bVisible":false
					},{
						"mData":"FILTER",
						"bVisible":false
					},{
						"mData":"IS_PRIMARY_KEY",
						"bVisible":false
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageAccount,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						if(aData["IS_DESIGN"] || aData["IS_PRIMARY_KEY"]=="Y"){
							$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' checked></input></center>");	
						}else{
							$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio'></input></center>");
						}
						$("td:eq(4)",nRow).html("<center><input type='text' id='"+aData["ID"]+"_INPUT' value='"+aData["FILTER"]+"' style='border:solid 1px #A3D0E3;width:98%;height:23px;font-size:12px;margin-bottom:0px;'></input></center>");
					},
					"fnServerData":function (sSource,aoData,fnCallback){
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
							dataType:"json",		
							success:function(data){
								fnCallback(data);
								document.getElementById("#tipContainer").innerHTML = tipHtml;
								isShow = false;
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/dataDesignAction/findItems.ilf"
				});
				$("#MODEL_NAME_INPUT").bind("keydown",function(event){
					if(event.keyCode==13){
						flushDbTree();
						return false;
					}
				});
			});
			function searchData(){
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				},{
					name:"BELONG_TEMPLATE",
					value:$("#TEMPLATE_SELECT").val()
				}];
				oTable.fnDraw();
			}
			function saveDesign(){
				if(acheItems==null || acheItems.keySet().length==0){
					window.wxc.xcConfirm("请先选择一个包含属性字段的模型.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					var isChecked = false;
					var checkboxes = document.getElementsByName("checksRadio");
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							if(checkboxes[i].checked){
								isChecked = true;
							}
						}
					}
					var templateName = $("#TEMPLATE_SELECT").val();
					var newName = $("#TEMPLATE_NAME_INPUT").val();
					if(templateName=="-" && newName==""){
						window.wxc.xcConfirm("请输入订阅模板名称.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}else{
						if(newName!="" || templateName=="-"){
							templateName = newName;
						}
					}
					if(!isChecked){
						window.wxc.xcConfirm("此模型下请至少订阅一项属性.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}else{
						var columnObjs = acheItems.valueSet();
						for(var j=0;j<columnObjs.length;j++){
							var obj = columnObjs[j];
							var columnId = obj["ID"];
							/*保存是否订阅*/
							var isDesigned = false;
							var checkboxes = document.getElementsByName("checksRadio");
							if(checkboxes!=null && checkboxes.length>0){
								for(var i=0;i<checkboxes.length;i++){
									if(checkboxes[i].checked && parseInt(checkboxes[i].value)==parseInt(columnId)){
										isDesigned = true;
									}
								}
							}
							if(isDesigned){
								obj["IS_DESIGN"] = true;
							}else{
								obj["IS_DESIGN"] = false;
							}
							obj["FILTER_CON"] = $("#"+obj["ID"]+"_INPUT").val();
							acheItems.put(obj["ID"],obj);
						}
						window.wxc.xcConfirm("是否确认保存此模型下的订阅规则？.","custom",{
							title:"提示",
							btn:parseInt("0011",2),
							onOk:function(){
								$.ajax({
									url:"${pageContext.request.contextPath}/dataDesignAction/saveAudit.ilf",
									async:false,
									type:"POST",
									data:"templateName="+encodeURIComponent(encodeURIComponent(templateName,"UTF-8"),"UTF-8")+"&params="+encodeURIComponent(encodeURIComponent(JSON.stringify(acheItems.valueSet()),"UTF-8"),"UTF-8")+"&modelCode="+$("#hiddenOfTableId").val(),
									dataType:"json",
									timeout:10000, 
									success:function(textStatus){
										if(textStatus["success"]){
											window.wxc.xcConfirm("模型订阅配置成功.",window.wxc.xcConfirm.typeEnum.info);
											$.ajax({
												url:"${pageContext.request.contextPath}/dataDesignAction/findTemplatesConfiged.ilf",
												async:false,
												type:"POST",
												data:"modelId="+$("#hiddenOfTableId").val(),
												dataType:"json",
												timeout:10000, 
												success:function(textStatus){
													if(textStatus["SUCCESS"]){
														var Options = textStatus["OPTIONS"];
														var optionHtml = "";
														optionHtml+="<select id='TEMPLATE_SELECT' onchange='javascript:searchData();' style='width:200px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;'>";
														if(Options.length>0){
															for(var i=0;i<Options.length;i++){
																var optionObj = Options[i];
																optionHtml+="<option value='"+optionObj["BELONG_TEMPLATE"]+"'>"+optionObj["BELONG_TEMPLATE"]+"</option>";		
															}
														}else{
															optionHtml+="<option value='-'>尚未配置模板</option>";
														}
														optionHtml+="</select>";
														document.getElementById("TEMPLATE_SELECTION").innerHTML = optionHtml;
														searchData();
													}else{
														window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
													}
												},
												error:function(){
													window.wxc.xcConfirm("获取模板列表信息失败.",window.wxc.xcConfirm.typeEnum.error);
												}
											});
										}else{
											window.wxc.xcConfirm("模型订阅配置失败.",window.wxc.xcConfirm.typeEnum.error);
										}
									},
									error:function(){
										window.wxc.xcConfirm("模型订阅配置失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								});
							}
						});
					}
				}
			}
			function CheckedAll(isChecked,boxName,boxValue){
				if(isChecked){
					var checkboxes = document.getElementsByName("checksRadio");
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							checkboxes[i].checked = true;
						}
					}
				}else{
					var checkboxes = document.getElementsByName("checksRadio");
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							checkboxes[i].checked = false;
						}
					}
				}
			}
			function flushDbTree(){
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleManageAction/findModelsByKey.ilf",
					async:false,
					type:"POST",
					data:"modelName="+$("#MODEL_NAME_INPUT").val(),
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						window.parent.overLoading(true);
						if(textStatus["success"]){
							var nodes = textStatus["nodes"];
							var tree = $("#frmt");
							tree.jstree(true).settings.core.data = nodes;  
							tree.jstree(true).refresh();
						}
					},
					error:function(){
						window.parent.overLoading(true);
						window.wxc.xcConfirm("模型查找失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			var tipHtml ="";
			tipHtml+="<div id='designTip' onclick='javascript:hideMyself();' style='position:fixed;top:120px;left:30%;background:rgba(52,152,219,1);width:550px;height:280px;border:solid 1px #3498DB;box-shadow:0px 0px 10px #3498DB;border-radius:7px;overflow:auto;display:none;'>";
			tipHtml+="	  <center>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'><font color='red' style='float:left;'>*</font><font color='red' style='float:left;margin-left:5px;'>配置说明</font></div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;'>1.左侧'是否订阅'为选择订阅字段功能，勾选后在'我的订阅'中即可查看到已被订阅的字段值.</font>";
			tipHtml+="		  </div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;'>2.属性标识和属性名称为'数据订阅'功能提供参考和订阅目标的定位.</font>";
			tipHtml+="		  </div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;'>3.属性类型为'数据订阅'的使用者提供配置过滤条件时的参考.如果是字符串类型，应加双引号.</font>";
			tipHtml+="		  </div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;'>4.过滤条件使用逻辑运算符表达.</font>";
			tipHtml+="		  </div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;margin-left:30px;'>4.1.过滤数字类型示例：=100&nbsp;,&nbsp;!=100&nbsp;,&nbsp;not in(1,2)&nbsp;,&nbsp;>100&nbsp;,&nbsp;&lt;100&nbsp;,&nbsp;&lt;=100&nbsp;,&nbsp;&gt;100&nbsp;,&nbsp;&gt;=100.</font>";
			tipHtml+="		  </div>";
			tipHtml+="		  <div style='width:90%;height:20px;margin-top:10px;'>";
			tipHtml+="			  <font style='float:left;margin-left:30px;'>4.2.过滤字符串类型示例：='混凝土建筑'&nbsp;,&nbsp;like '%成都%'&nbsp;,&nbsp;not in('成都','阿坝')</font>";
			tipHtml+="		  </div>";
			tipHtml+="	  </center>";
			tipHtml+="</div>";
			var isShow = false;
			function showOrHideTip(){
				if(!isShow){
					document.getElementById("tipContainer").innerHTML = tipHtml;
					$("#designTip").show(500);
					isShow = true;
				}else{
					$("#designTip").hide(500);
					isShow = false;
				}
			}
			function hideMyself(){
				$("#designTip").hide(500);
				isShow = false;
			}
			function removeDesign(){
				var checkedName = $("#TEMPLATE_SELECT").val();
				if(checkedName=="" || checkedName=="-"){
					window.wxc.xcConfirm("请先选择一个订阅模板.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					window.wxc.xcConfirm("是否确认删除此订阅模板？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							window.parent.loading(true);
							$.ajax({
								url:"${pageContext.request.contextPath}/dataDesignAction/removeTemplate.ilf",
								async:false,
								type:"POST",
								data:"templateName="+encodeURIComponent(encodeURIComponent(checkedName,"UTF-8"),"UTF-8")+"&modelCode="+$("#hiddenOfTableId").val(),
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["SUCCESS"]){
										$.ajax({
											url:"${pageContext.request.contextPath}/dataDesignAction/findTemplatesConfiged.ilf",
											async:false,
											type:"POST",
											data:"modelId="+$("#hiddenOfTableId").val(),
											dataType:"json",
											timeout:10000, 
											success:function(textStatus){
												window.parent.overLoading(true);
												if(textStatus["SUCCESS"]){
													var Options = textStatus["OPTIONS"];
													var optionHtml = "";
													optionHtml+="<select id='TEMPLATE_SELECT' onchange='javascript:searchData();' style='width:200px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;'>";
													if(Options.length>0){
														for(var i=0;i<Options.length;i++){
															var optionObj = Options[i];
															optionHtml+="<option value='"+optionObj["BELONG_TEMPLATE"]+"'>"+optionObj["BELONG_TEMPLATE"]+"</option>";		
														}
													}else{
														optionHtml+="<option value='-'>尚未配置模板</option>";
													}
													optionHtml+="</select>";
													document.getElementById("TEMPLATE_SELECTION").innerHTML = optionHtml;
													searchData();
												}else{
													window.parent.overLoading(true);
												}
											},
											error:function(){
												window.parent.overLoading(true);
											}
										});
									}else{
										window.parent.overLoading(true);
										window.wxc.xcConfirm("订阅模板删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.parent.overLoading(true);
									window.wxc.xcConfirm("订阅模板删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="-1" id="hiddenOfTableId"></input>
  		<input type="hidden" value="-1" id="hiddenOfCount"></input>
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-body" style="width:100%;border:0px;" id="ruleConfigDiv">
				<div style="width:99.8%;overflow:auto;border:solid 1px #FFF;" id="ruleEditsBuck">
					<div style="float:left;width:28%;height:440px;margin-top:4px;margin-left:3px;" id="leftTreePanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="leftTreeHead">
							<span class="panel-label"></span><input type="text" id="MODEL_NAME_INPUT" style="height:29px;width:175px;border:solid 1px #A3D0E3;margin-top:5px;" placeholder="请输入模型名称"></input>
						</div>
						<div class="panel-body" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<div id="frmt" class="jstrees" style="width:99%;height:680px;overflow:auto;border:solid 1px #A3D0E3;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div style="float:left;width:71%;margin-top:4px;margin-left:4px;" id="rightDataPanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="ruleTablePanel">
							<span class="panel-label"></span>订阅条件配置
							<img onclick="javascript:showOrHideTip();" src="${pageContext.request.contextPath}/icons/tips.png" style="width:15px;height:15px;margin-left:5px;cursor:pointer;"></img>
							<form class="form-search pull-right">	
								<div style="float:right;">
									<div style="float:left;" id="TEMPLATE_SELECTION">
										<select onchange="javascript:searchData();" id="TEMPLATE_SELECT" style="width:160px;height:29px;margin-right:5px;margin-top:6px;border:solid 1px #A3D0E3;">
											<option value="-">尚未配置模板</option>
										</select>
									</div>
									<div style="float:left;">
										<input id="TEMPLATE_NAME_INPUT" type="text" placeholder="请输入订阅模板名称" style="width:140px;height:30px;font-size:12px;border:solid 1px #A3D0E3;"></input>
										<a class="btn btn-danger" style="cursor:pointer;" onclick="javascript:saveDesign();" >
											<i class="icon-flag icon-white"></i>保存订阅
										</a>
										<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:removeDesign();" >
											<i class="icon-trash icon-white"></i>删除订阅
										</a>
									</div>
								</div>
							</form>
						</div>
						<div class="panel-body h350" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto;">
										<table cellpadding="0" cellspacing="0" border="0" id="dataTable" style="border:0px;">
											<thead>
												<tr>
													<th>是否订阅<input type="checkbox" value="Y" name="isCheckAll" style="margin-left:5px;" onchange="javascript:CheckedAll(this.checked,this.name,this.value);"></input></th>
													<th>属性标识</th>
													<th>属性名称</th>
													<th>属性类型</th>
													<th>过滤条件</th>
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
				<div id="tipContainer"></div>
			</div>
		</div>
		<%--核查任务已完成--%>
		<div id="tipDivs" style="display:none;position:fixed;width:300px;height:100px;border:solid 1px #397FF1;bottom:4px;right:7px;background:#FFF;">
			<div style="margin-top:30px;margin-left:30px;" id="tipsContainer">&nbsp;</div>
		</div>
		<%--包含规则列表--%>
		<div id="itemsModal" class="modal hide fade" style="width:750px;height:355px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 包含规则列表</h3>
			</div>
			<div class="modal-body" style="max-height:260px;" id="ruleItemBrown">&nbsp;</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
				</button>
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
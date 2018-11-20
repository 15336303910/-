<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String tableId = request.getParameter("tableId");
	String rulesId = request.getParameter("ruleId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>规则编辑</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/smartwizard/css/smart_wizard.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/smartwizard/jquery.smartWizard.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<%--引入已封装的工具js--%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/dateUtil.js"></script>
		<%--引入已封装的功能js--%>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/rules/css/pageStyle.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/columnTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/glassesTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/ruleEdit.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/buildConn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/initResTree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/initPage.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/rules/js/quartzs.js"></script>
		<script type="text/javascript">
			var ruleObj = {
				editedItems:[]
			};
			$(document).ready(function(){
				window.parent.showLoading(45,42);
				/*初始化引导组件高度*/
				$("#outLinePanel").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#stepWizards").css({
			    	"height":$("#outLinePanel").height()-5
			    });
				$("#step-1").css({
			    	"height":$("#stepWizards").height()-180
			    });
				$("#step-2").css({
			    	"height":$("#stepWizards").height()-180
			    });
				$("#ruleEditPanel").css({
			    	"height":$("#step-2").height()-10
			    });
				$("#step-3").css({
			    	"height":$("#stepWizards").height()-180
			    });
				$("#stepWizards").smartWizard({
		  			transitionEffect:"fade",
		  			keyNavigation:false,
		  			onFinish:onFinishCallback,
		  			onLeaveStep:leaveAStepCallback
		  		});
				initPageData();
				initClassSelection();
				bindingKeyDown();
				window.parent.hideLoading();
				$("#stepOneTable").css({
			    	"margin-top":parseInt(($("#step-1").height()-$("#stepOneTable").height())/2)
			    });
			});
			function bindingKeyDown(){
				$("#columnAliasInput").bind("keydown",function(event){
					if(event.keyCode==13){
						searchColumnData();
						return false;
					}
				});
				$("#glassColumnKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchGlassColumn();
						return false;
					}
				});
			}
			function initPageData(){
				initTbInfo();
				initResTree();
				/*字段表*/
				conditions = [{
					name:"BELONG_TABLE",
					value:$("#hiddenOfBelongTable").val()
				}];
				initColumnsOfTable();
				/*参照表*/
				glassParam = [{
					name:"BELONG_TABLE",
					value:$("#hiddenOfGlassTable").val()
				}];
				initGlassTable();
			}
			function initClassSelection(){
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/findClasses.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							/*界面控制*/
							$("#classNameKeyin").val("");
							$("#classNameInput").hide();
							/*渲染下拉框*/
							var items = textStatus["items"];
							var optionHtml = "";
							optionHtml+="<select id='classTypeSelect' style='width:530px;border:solid 1px #4287F7;' onchange='javascript:classTypeChoose(this.value);'>";
								optionHtml+="<option value='-'>- 不对此规则分类  -</option>";
								if(items!=null && items.length>0){
									for(var i=0;i<items.length;i++){
										var itemObj = items[i];
										optionHtml+="<option value='"+itemObj["CLASS_NAME"]+"'>"+itemObj["CLASS_NAME"]+"</option>";		
									}
								}							
								optionHtml+="<option value='NONE'>- 没有我要的分类？ -</option>";
							optionHtml+="<select>";
							document.getElementById("classSelectDiv").innerHTML = optionHtml;
							initIfUpdate();
						}
					},
					error:function(){}
				});
			}
			function leaveAStepCallback(obj){
				if(ruleObj==null){
					ruleObj = new Object();
				}
				var validPass = true;
				var step_num = obj.attr("rel");
				if(step_num==1){
					//1.[非空验证]规则名称
					var ruleNameValue = $("#ruleNameInput").val();
					if(ruleNameValue==""){
						window.wxc.xcConfirm("请输入规则名称.",window.wxc.xcConfirm.typeEnum.info);
						return false;
					}
					//2.[非空验证]规则描述
					var ruleDescValue = $("#ruleDescInput").val();
					if(ruleDescValue==""){
						window.wxc.xcConfirm("请输入规则描述.",window.wxc.xcConfirm.typeEnum.info);
						return false;
					}
					//3.[非空验证]解决建议
					var resolveTip = $("#howToRecover").val();
					if(resolveTip==""){
						window.wxc.xcConfirm("请输入此类问题的解决建议.",window.wxc.xcConfirm.typeEnum.info);
						return false;
					}
					//3.[非空验证]规则分类
					var classType = $("#classTypeSelect").val();
					if(classType=="NONE"){
						var className = $("#classNameKeyin").val();
						if(className==""){
							window.wxc.xcConfirm("新规则分类的名称不能为空.",window.wxc.xcConfirm.typeEnum.info);
							return false;
						}
						ruleObj["className"] = className;	
					}else{
						ruleObj["className"] = classType;
					}			
					ruleObj["ruleName"] = $("#ruleNameInput").val();
					ruleObj["ruleDesc"] = $("#ruleDescInput").val();
					ruleObj["recovery"] = $("#howToRecover").val();
				}else if(step_num==2){
					//3.[非空验证]已编辑规则至少一条.
					if(ruleObj==null || ruleObj["editedItems"]==null || ruleObj["editedItems"].length==0){
						window.wxc.xcConfirm("请至少添加一条核查规则.",window.wxc.xcConfirm.typeEnum.info);
						validPass = false;
					}
				}
				return validPass;
		    }
			function onFinishCallback(){
				//1.缓存调度周期
				var conExpress = $("#conExpressionInput").val();
				if(conExpress==""){
					window.wxc.xcConfirm("请编辑调度周期.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					ruleObj["conExpress"] = conExpress;
				}
				//2.缓存规则状态[启用、停用]
				ruleObj["isUsing"] = $("#isUsingSelection").val();
				//3.缓存所属模型
				ruleObj["tableId"] = $("#hiddenOfBelongTable").val();				
				//4.缓存调度详细配置
				var circleObj = new Object();
				if(conExpress=="-"){
					//4.1.手动执行
					circleObj["CIRCLE_TYPE"] = "无";
				}else{
					//4.2.执行粒度
					circleObj["CIRCLE_TYPE"] = getCheckedValue("execCircle");
					//4.3.周粒度或月粒度[填充周几或几号.]
					if(circleObj["CIRCLE_TYPE"]=="周"){
						circleObj["DAY_OF_WEEK"] = $("#chooseDayOfWeek").val();
					}else if(circleObj["CIRCLE_TYPE"]=="月"){
						circleObj["DAY_OF_MONTH"] = $("#chooseDayOfMonth").val();
					}				
					//4.4.小时、分钟、秒
					circleObj["HOUR_VAR"] = $("#hourOfDay").val();
					circleObj["MINUTE_VAR"] = $("#minuteOfHour").val();
					circleObj["SECOND_VAR"] = $("#secondOfMinute").val();					
				}
				/*5.版本*/
				ruleObj["ruleVersion"] = $("#ruleVersionInput").val();
				ruleObj["quartzDetail"] = circleObj;
				ruleObj["ruleId"] = $("#hiddenOfRuleCode").val();
				ruleObj["ruleType"] = "配置核查";
				ruleObj["ruleGradeLevel"] = $("#gradeSelection").val();
				window.wxc.xcConfirm("是否确认保存此条规则？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/ruleEditAction/saveAudit.ilf",
							async:false,
							type:"POST",
							data:"params="+JSON.stringify(ruleObj),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["success"]){
									window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.info);
									initPageEdits();
								}else{
									window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
								}
							},
							error:function(){
								window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
							}
						});
					}
				});
			}
			function initPageEdits(){
				//0.[初始化]初始化最终提交到后台服务器的缓存对象
				ruleObj = null;
				//1.[初始化]第一步骤的表单
				$("#hiddenOfRuleCode").val(-1);
				document.getElementById("stepOneForm").reset();
				initClassSelection();
				//2.[初始化]第二步骤
					//2.1.清除字段关联缓存
					connectedColumn = new Array();
					colorMap = new HashMap();
					//2.2.清除字段查询条件
					$("#columnAliasInput").val("");
					//2.3.重新查询字段
					searchColumnData();
					//2.4.清除是否忽略空值
					var filters = document.getElementsByName("isFilterNull");
					if(filters.length>0){
						filters[0].checked = false;
					}
					//2.5.清除连表核查选项
					var checkboxes = document.getElementsByName("isRelate");
					for(var i=0;i<checkboxes.length;i++){
						checkboxes[i].checked = false;
					}
					//2.6.放开规则表达式编辑框
					$("#expressionInput").val("");
					$("#expressionInput").attr("disabled",false);
					//2.7.初始化参照表数据[隐藏资源树、渲染表格]
					isTreeShow = false;
					$("#treeDiv").hide();
					$("#hiddenOfGlassTable").val("-1");
					$("#checkedGlassTable").val("");
					$("#glassColumnKey").val("");								
					searchGlassColumn();
					//2.8.初始化已配置规则信息并进行渲染
					acheObject = new HashMap();
					initEditedTable();
					//2.9.跳转界面
					$(".rightPanel").hide();
					$("#editedTable").show();
				//3.[初始化]第三步骤调度表达式和是否启用
				$("#conExpressionInput").val("");
				$("#isUsingSelection").val("Y");
				//4.返回第一步
				$("#oneStep").click();
			}
			function changeConnEdit($isChecked,$checkedValue){
				//0.首先隐藏数量范围设置
				$("#numberRange").hide(300);
				$("#matchTypees").hide(300);
				if($isChecked){
					var checkedCount = getCheckedCount("columnSelect");
					if(checkedCount==0){
						/*
							多表关联[必须]选定[一个]目标属性	 
						 */
						unCheckAllBoxes("isRelate");
						window.wxc.xcConfirm("请先选择核查字段.",window.wxc.xcConfirm.typeEnum.info);
					}else if(checkedCount>1){
						/*
							多表关联[至多]选定[一个]目标属性	 
					 	 */
						unCheckAllBoxes("isRelate");
						window.wxc.xcConfirm("联表核查只能针对单一字段.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						var isNeedValid = true;
						var isPassValid = true;
						if($checkedValue=="数据一致性"){
							isNeedValid = false;
						}
						if(isNeedValid){
							/*
								如果当前类型不是[数据一致性]则必须先选择目标属性.
					 	 	 */
							var checkedValue = getCheckedValue("columnSelect");
							if(checkedValue==null){
								window.wxc.xcConfirm("请先选择核查字段.",window.wxc.xcConfirm.typeEnum.info);
								unCheckAllBoxes("isRelate");
								isPassValid = false;
							}
						}
						if(isPassValid){						 
							//2.如果已选择核查字段，则确保当前选择的[核查目的]唯一性.
							uniqueCheckbox(true,"isRelate",$checkedValue);
							//3.使得[表达式输入]清空并失效.
							$("#expressionInput").val("");
							$("#expressionInput").attr("disabled",true);
							//4.显示左侧关联表格界面
							$(".rightPanel").hide();
							$("#glassTable").show();
							//5.判断是否展示数量范围						
							if($checkedValue=="核查关联数据条数"){
								$("#numberRange").show(300);
							}else if($checkedValue=="多属性匹配"){
								$("#matchTypees").show(300);
							}
							if($checkedValue=="多属性匹配" && glassAches!=null && glassAches.valueSet().length>0){
								var aoDatas = glassAches.valueSet();
								for(var i=0;i<aoDatas.length;i++){
									var aObject = aoDatas[i];
									if(document.getElementById("CDIV"+aObject["ID"])!=null){
										document.getElementById("CDIV"+aObject["ID"]).innerHTML = "<input type='checkbox' value='"+aObject["ID"]+"' name='valueSelect'></input>";
									}
								}
							}else{
								if(glassAches!=null && glassAches.valueSet().length>0){
									var aoDatas = glassAches.valueSet();
									for(var i=0;i<aoDatas.length;i++){
										var aObject = aoDatas[i];
										if(document.getElementById("CDIV"+aObject["ID"])!=null){
											document.getElementById("CDIV"+aObject["ID"]).innerHTML = "<input type='checkbox' value='"+aObject["ID"]+"' name='valueSelect' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input>";
										}
									}
								}
							}
						}	
					}					
				}else{
					initRightPanel();
				}
			}
			function aNumOperate($type){
				if($type==1){
					/*减*/
					var startNumber = document.getElementById("startNumber").innerHTML;
					if(parseInt(startNumber)==0){
						
					}else{
						startNumber = parseInt(startNumber)-1;
						document.getElementById("startNumber").innerHTML = startNumber;
					}		
				}else if($type==2){
					/*加*/
					var startNumber = document.getElementById("startNumber").innerHTML;
					startNumber = parseInt(startNumber)+1;
					document.getElementById("startNumber").innerHTML = startNumber;
				}
			}
			function zNumOperate($type){		
				var limitNumber = document.getElementById("limitNumber").innerHTML;
				if(parseInt(limitNumber)==0 && $type==1){
					
				}else{
					if($type==1){
						limitNumber = parseInt(limitNumber)-1;
					}else if($type==2){
						limitNumber = parseInt(limitNumber)+1;
					}
					document.getElementById("limitNumber").innerHTML = limitNumber;	
				}
			}
			function goBackEdit(){
				//1.01.数目范围
				document.getElementById("startNumber").innerHTML = "0";
				document.getElementById("limitNumber").innerHTML = "0";
				$("#numberRange").hide();
				//1.02.刷新
				initRightPanel();
				//1.03.补充
				unCheckAllBoxes("editedItem");
				unCheckAllBoxes("isRelate");
				searchColumnData();
			}
			function classTypeChoose(className){
				if(className=="NONE"){
					document.getElementById("classTypeSelect").style.width = "175px";
					$("#classNameInput").show(400);
				}else{
					$("#classNameInput").hide();
					document.getElementById("classTypeSelect").style.width = "530px";
				}
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" value="<%=tableId %>" id="hiddenOfBelongTable"></input>
  		<input type="hidden" value="<%=rulesId %>" id="hiddenOfRuleCode"></input>
  		<div class="panel panel-default models" style="margin-top:1px;height:490px;border:solid 1px #FFF;" id="outLinePanel">
  			<div class="panel-body" style="border:0px;">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #DFE8F1">
		  			<tr>
		    			<td>
		      				<div id="stepWizards" class="swMain" style="height:200px;border:solid 1px #FFF;">
			        			<ul>
			          				<li>
			          					<a href="#step-1" style="margin-top:5px;width:180px" id="oneStep">
			          						<span class="stepNumber">1</span>
			          						<span class="stepDesc">
			          							<font size="2">第一步</font><br/>
			          							<small><font size="3">编辑规则概要信息</font></small>
			            					</span>
			            				</a>
			            			</li>
			          				<li>
			          					<a href="#step-2" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">2</span>
			          						<span class="stepDesc">
			          							<font size="2">第二步</font><br/>
			          							<small><font size="3">配置核查规则</font></small>
			            					</span>
			            				</a>
			            			</li>
			          				<li>
			          					<a href="#step-3" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">3</span>
			          						<span class="stepDesc">
			          							<font size="2">第三步</font><br/>
			          							<small><font size="3">启用/调度</font></small>
			            					</span>
			            				</a>
			            			</li>
			        			</ul>
			        			<%--step-1--%>
						        <div id="step-1" style="width:100%;border:solid 1px #CCC">
						        	<center>
						        		<form id="stepOneForm">
											<table class="table table-bordered table-hover" style="width:700px;margin-top:10px;" id="stepOneTable">
												<tr height="40">
													<td style="background:#F9FAFE;"><label class="l-name">核查目标.</label></td>
													<td><font color="blue" id="tableNameTd">&nbsp;</font></td>
												</tr>
												<tr height="30">
													<td style="background:#F9FAFE;"><label class="l-name">规则名称.</label></td>
													<td><input id="ruleNameInput" type="text" value="" placeholder="" style="width:530px;height:30px;margin-top:5px;border:solid 1px #4287F7;"></input></td>
												</tr>
												<tr height="30">
													<td style="background:#F9FAFE;"><label class="l-name">规则分类.</label></td>
													<td>
														<div style="width:99%;height:30px;">
															<div style="float:left;" id="classSelectDiv">
																<select id="classTypeSelect" style="width:530px;border:solid 1px #4287F7;" onchange="javascript:classTypeChoose(this.value);">
																	<option value="-">- 不对此规则分类  -</option>
																	<option value="NONE">- 没有我要的分类？ -</option>
																</select>
															</div>
															<div style="float:left;margin-left:5px;display:none;" id="classNameInput">
																<input id="classNameKeyin" type="text" value="" placeholder="请在此处输入新的规则类别" style="width:350px;height:30px;border:solid 1px #4287F7;"></input>
															</div>
														</div>
													</td>
												</tr>
												<tr height="60">
													<td style="background:#F9FAFE;"><label class="l-name">核查目的.</label></td>
													<td>
														<textarea id="ruleDescInput" placeholder="" style="resize:none;width:530px;height:60px;border:solid 1px #4287F7;"></textarea>												
													</td>
												</tr>
												<tr height="60">
													<td style="background:#F9FAFE;"><label class="l-name">解决建议.</label></td>
													<td>
														<textarea id="howToRecover" placeholder="" style="resize:none;width:530px;height:60px;border:solid 1px #4287F7;"></textarea>												
													</td>
												</tr>
											</table>
										</form>
									</center>
						        </div>
						        <%--step-2--%>
						        <div id="step-2" style="width:100%;border:solid 1px #CCC">
					        		<div style="width:99%;height:322px;margin-left:2px;overflow:auto;" id="ruleEditPanel">
					        			<div style="float:left;width:50%;height:625px;">
					        				<%--选取要核查的字段--%>
					        				<div style="float:left;width:99%;height:270px;border:solid 1px #A3D0E3;">
						        				<div class="panel-heading">
													<span class="panel-label"></span><font id="tableNameFont">&nbsp;</font>
													<form class="form-search pull-right">	
														<div style="float:right;">
															<input type="text" placeholder="请输入属性标识或名称" style="width:160px;height:30px;font-size:12px;border:solid 1px #A3D0E3;" id="columnAliasInput"></input>
															<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:searchColumnData();">
																<i class="icon-plus-sign icon-white"></i>查询
															</a>
														</div>
													</form>
												</div>
												<div class="panel-body h300" style="border:0px;">
													<div class="div_scroll" style="margin-left:1px;">
														<div style="height:auto;width:auto;">
															<table cellpadding="0" cellspacing="0" border="0" id="columnTable" style="border:0px;margin-top:3px;border-top:solid 1px #A3D0E3;border-left:solid 1px #A3D0E3;">
																<thead>
																	<tr><th>&nbsp;</td><th>属性标识</td><th>属性名称</td><th>关联字段</td><th>关联属性</td></tr>
																</thead>
																<tbody></tbody>
															</table>						
														</div>
													</div>
												</div>
						        			</div>
						        			<%--编辑核查种类及表达式--%>
						        			<div style="float:left;width:99%;height:120px;border:solid 1px #A3D0E3;margin-top:5px;">
						        				<div style="width:100%;height:110px;">
						        					<div class="panel-heading">
														<span class="panel-label"></span><font id="columnNameFont">配置规则</font>
													</div>
													<div style="margin-top:5px;">
														<input type="checkbox" value="Y" name="isFilterNull" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC"></input>
														<font style="margin-left:5px;margin-top:2px;">是否忽略空值</font>
														<input type="checkbox" value="核查枚举值" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:changeConnEdit(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">枚举值（动态）</font>
														<input type="checkbox" value="核查关联数据条数" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:changeConnEdit(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">数据关联数目</font>
														<input type="checkbox" value="多属性匹配" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:changeConnEdit(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">多属性匹配</font>
														<%--
														<input type="checkbox" value="数据一致性" name="isRelate" style="margin-left:5px;margin-top:-1px;border:solid 1px #CCC" onchange="javascript:changeConnEdit(this.checked,this.value);"></input>
														<font style="margin-left:5px;margin-top:2px;">数据一致性</font>
														--%>
													</div>
													<input type="text" placeholder="" style="width:380px;height:29px;font-size:12px;margin-top:8px;margin-left:5px;border:solid 1px black;" id="expressionInput"></input>
													<a class="btn btn-success" style="cursor:pointer;margin-top:-1px;" onclick="javascript:doSaveLocal();">
														<i class="icon-plus-sign icon-white"></i>保存规则
													</a>
						        				</div>
						        			</div>
						        			<%--表达式说明--%>
						        			<div style="float:left;width:99%;height:195px;border:solid 1px #A3D0E3;margin-top:5px;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>规则表达式规范说明
												</div>
												<div class="panel-body h340" style="border:0px;">
													<div class="div_scroll">
														<div style="height:auto;width:auto;">
															<table class="table table-bordered table-hover" style="border:0px;">
																<tr><td style="border-left:0px;text-align:center;">1.</td><td>a~b表示取值范围，表示过滤字段值应为a到b之间的数字，包括a,b.</td></tr>
																<tr><td style="border-left:0px;text-align:center;">2.</td><td>'a','b','c','d'表示枚举范围，表达字段值必须在a,b,c,d范围内选取.</td></tr>
																<tr><td style="border-left:0px;text-align:center;">3.</td><td>“NOT NULL”表示字段值不能为空.</td></tr>
																<tr><td style="border-left:0px;text-align:center;">4.</td><td>“UNIQUE”表示字段值在本表中必须唯一.</td></tr>
																<tr><td style="border-left:0px;text-align:center;">5.</td><td>“arg >= 105”表示字段值必须大于105.同类还有>,<,<=.</td>	</tr>
															</table>						
														</div>
													</div>
												</div>
						        			</div>
					        			</div>
					        			<%--右边部分（一）--%>
					        			<div style="float:left;width:49%;height:609px;" class="rightPanel" id="editedTable">
					        				<div style="float:left;width:99%;height:598px;border:solid 1px #A3D0E3;">
						        				<div class="panel-heading">
													<span class="panel-label"></span>已配置规则
													<form class="form-search pull-right">	
														<div style="float:right;">
															<a class="btn btn-danger"  style="cursor:pointer;margin-top:5px;" onclick="javascript:cleanEditedItem();">
																<i class="icon-trash icon-white"></i>删除规则
															</a>
														</div>
													</form>
												</div>
												<div class="div_scroll">
													<div style="height:auto;width:auto;" id="editedItems">
														<table class="table table-bordered table-hover" style="border:0px;">
															<tbody>
																<tr>
																	<td style="width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;">&nbsp;</td>
																	<td style="width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">待核查属性</td>
																	<td style="width:40%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">表达式/核查类型</td>
																	<td style="width:20%;border:0px;border-bottom:solid 1px #DFE8F1;background:#F9FAFE;text-align:center;">是否忽略空值</td>
																</tr>
																<tr>
																	<td colspan="4" style="text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;">
																		<font color="red">尚未配置任何规则</font>																
																	</td>
																</tr>
															</tbody>
														</table>					
													</div>
												</div>
						        			</div>
					        			</div>
					        			<%--右边部分（二）--%>
					        			<div style="float:left;width:49%;height:668px;display:none;" class="rightPanel" id="glassTable">
					        				<div style="float:left;width:99%;height:226px;">
						        				<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;">
													<span class="panel-label"></span>
													<form class="form-search pull-right">
														<input type="hidden" id="hiddenOfGlassTable" value="-1"></input>	
														<div style="float:right;">
															<input type="text" placeholder="点击选择参照表" id="checkedGlassTable" readonly onclick="javascript:assignedTeam();" style="width:105px;height:30px;font-size:12px;margin-top:-3px;cursor:pointer;border:solid 1px #A3D0E3;"></input>
															<input type="text" placeholder="请输入属性标识或名称" id="glassColumnKey" style="width:140px;height:30px;font-size:12px;margin-top:-3px;border:solid 1px #A3D0E3;"></input>
															<a class="btn btn-success" style="cursor:pointer;margin-top:-3px;" onclick="javascript:searchGlassColumn();">
																<i class="icon-search icon-white"></i>查询 
															</a>
															<a class="btn btn-info" style="cursor:pointer;margin-top:-3px;" onclick="javascript:saveRelateCheck();">
																<i class="icon-plus-sign icon-white"></i>保存
															</a>
															<a class="btn btn-warning" style="cursor:pointer;margin-top:-3px;" onclick="javascript:goBackEdit();">
																<i class="icon-repeat icon-white"></i>返回
															</a>
														</div>
													</form>
												</div>
												<div class="panel-body h600" style="border-left:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">
													<%--窗口表单：结束--%>
													<div id="treeDiv" style="display:none;">
														<div style="margin-left:1px;width:98.8%;height:200px;overflow:auto;border:solid 1px #A3D0E3;">
															<ul id="tableTree" class="ztree"></ul>
														</div>
													</div>
													<div class="div_scroll" style="margin-left:1px;">
														<div style="height:auto;width:auto;">
															<table id="glassTableColumn" cellpadding="0" cellspacing="0" style="border:0px;margin-top:3px;border:solid 1px #A3D0E3;">
																<thead>
																	<tr><th>值属性</td><th>属性标识</td><th>属性名称</td><th>关联属性</td></tr>
																</thead>
																<tbody></tbody>
															</table>				
														</div>
													</div>
												</div>
												<table id="numberRange" cellpadding="0" cellspacing="0" style="display:none;border:0px;margin-top:3px;font-size:12px;border:solid 1px #A3D0E3;">
													<tr height="30">
														<td style="width:90px;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">最小数量</td>
														<td style="width:30px;text-align:right;cursor:pointer;" onclick="aNumOperate(1);">
															<font color="red">-</font>
														</td>
														<td style="width:45px;text-align:center;">
															<font id="startNumber">0</font>
														</td>
														<td style="width:30px;text-align:left;cursor:pointer;border-right:solid 1px #A3D0E3;" onclick="aNumOperate(2);">
															<font color="green">+</font>
														</td>
														<td style="width:90px;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">最大数量</td>
														<td style="width:30px;text-align:right;cursor:pointer;" onclick="zNumOperate(1);">
															<font color="red">-</font>
														</td>
														<td style="width:45px;text-align:center;">
															<font id="limitNumber">0</font>
														</td>
														<td style="width:30px;border-right:solid 1px #A3D0E3;text-align:left;cursor:pointer;" onclick="zNumOperate(2);">
															<font color="green">+</font>
														</td>
													</tr>
												</table>
												<table id="matchTypees" cellpadding="0" cellspacing="0" style="display:none;border:0px;margin-top:3px;font-size:12px;border:solid 1px #A3D0E3;">
													<tr height="30">
														<td style="width:90px;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">匹配类型</td>
														<td style="width:70px;text-align:center;">
															<input type="checkbox" value="OR" name="isParalle" onchange="javascript:uniqueCheckbox(this.checked,this.name,this.value);" checked></input>&nbsp;&nbsp;OR
														</td>
														<td style="width:80px;border-right:solid 1px #A3D0E3;">
															<input type="checkbox" value="AND" name="isParalle" onchange="javascript:uniqueCheckbox(this.checked,this.name,this.value);"></input>&nbsp;&nbsp;AND
														</td>
														<td style="width:90px;border-right:solid 1px #A3D0E3;text-align:center;background:#DEEFF8;">匹配维度</td>
														<td style="width:90px;border-right:solid 1px #A3D0E3;text-align:center;">
															<input type="checkbox" value="T" name="matchDimension" onchange="javascript:uniqueCheckbox(this.checked,this.name,this.value);" checked></input>&nbsp;&nbsp;数据表
														</td>
													</tr>
												</table>
						        			</div>
					        			</div>
					        		</div>
						        </div>
						        <div id="step-3" style="width:100%;border:solid 1px #CCC">
						        	<center>
										<table class="table table-bordered table-hover" style="width:700px;margin-top:20px;" id="stepThreeTable">
											<tr height="40">
												<td style="background:#F9FAFE;"><label class="l-name">调度配置.</label></td>
												<td colspan="6">
													<input type="text" value="" style="cursor:pointer;width:480px;height:30px;font-size:12px;" id="conExpressionInput" readonly></input>													
													<a class="btn btn-warning"  style="cursor:pointer;margin-top:-10px;" onclick="javascript:doCircleConfig();" id="configConButton">
														<i class="icon-download-alt icon-white"></i><font id="exConfigButton">配置</font>
													</a>
												</td>
											</tr>
											<tr height="40" style="border:solid 1px #3794D1;display:none;" class="circleConfig">
												<td style="background:#F9FAFE;"><label class="l-name">执行粒度.</label></td>
												<td colspan="6" style="background:#F9FAFE;">
													<input type="checkbox" name="execCircle" value="日" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);"></input><font style="margin-left:10px;">每天</font>
													<input type="checkbox" name="execCircle" value="周" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">每周</font>
													<input type="checkbox" name="execCircle" value="月" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">每月</font>
													<input type="checkbox" name="execCircle" value="无" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">手动执行</font>
												</td>
											</tr>
											<tr height="40" style="border:solid 1px #3794D1;display:none;" class="circleConfig">
												<td style="background:#F9FAFE;"><label class="l-name">执行时间.</label></td>
												<td colspan="6" style="background:#F9FAFE;">
													<select style="width:150px;display:none;" id="chooseDayOfWeek">
														<option value="-">-- 请选择周粒度 --</option>
														<option value="MON">周一</option>
														<option value="TUE">周二</option>
														<option value="WED">周三</option>
														<option value="THU">周四</option>
														<option value="FRI">周五</option>
														<option value="SAT">周六</option>
														<option value="SUN">周天</option>
													</select>
													<select style="width:150px;display:none;" id="chooseDayOfMonth">
														<% for(int i=1;i<=31;i++){ %>
															<% if(i<10){%>					
																<option value="<%=i %>">0<%=i %>日</option>
															<%}else{%>
																<option value="<%=i %>"><%=i %>日</option>
															<%}%>
														<%}%>
													</select>
													<select style="width:80px;" id="hourOfDay">
														<% for(int i=0;i<=23;i++){ %>
															<% if(i<10){%>
																<% if(i==1){%>	
																	<option value="<%=i %>" selected>0<%=i %>点</option>
																<%}else{%>
																	<option value="<%=i %>">0<%=i %>点</option>
																<%}%>		
															<%}else{%>
																<option value="<%=i %>"><%=i %>点</option>
															<%}%>
														<%}%>
													</select>
													<select style="width:80px;" id="minuteOfHour">
														<% for(int i=0;i<=59;i++){ %>
															<% if(i<10){%>
																<option value="<%=i %>">0<%=i %>分</option>
															<%}else{%>
																<option value="<%=i %>"><%=i %>分</option>
															<%}%>
														<%}%>
													</select>
													<select style="width:80px;" id="secondOfMinute">
														<% for(int i=0;i<=59;i++){ %>
															<% if(i<10){%>
																<option value="<%=i %>">0<%=i %>秒</option>
															<%}else{%>
																<option value="<%=i %>"><%=i %>秒</option>
															<%}%>
														<%}%>
													</select>
												</td>
											</tr>
											<tr height="60">
												<td style="background:#F9FAFE;"><label class="l-name">是否启用.</label></td>
												<td colspan="6">
													<select class="w500" style="width:555px" id="isUsingSelection">
														<option value="Y">启用</option>
														<option value="N">停用</option>
													</select>
												</td>
											</tr>
											<tr height="60">
												<td style="background:#F9FAFE;"><label class="l-name">规则等级.</label></td>
												<td colspan="2">
													<select style="width:230px" id="gradeSelection">
														<option value="2">一般</option>
														<option value="3">重要</option>
														<option value="5">非常重要</option>
													</select>
												</td>
												<td style="background:#F9FAFE;"><label class="l-name">当前版本.</label></td>
												<td colspan="3">
													<input type="text" id="ruleVersionInput" value="1.0" value="" placeholder="" style="width:150px;height:30px;border:solid 1px #CCC;"></input>
												</td>
											</tr>
										</table>
									</center>
						        </div>
							</div>
						</td>
					</tr>
				</table>
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
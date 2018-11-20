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
		<script type="text/javascript">
			var ruleObj = null;
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
				var tableId = $("#hiddenOfBelongTable").val();
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/findOneTable.ilf",
					async:false,
					type:"POST",
					data:"tableId="+tableId,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){				
							$("#tableNameTd").html(textStatus["DB_NAME"]+" >> "+textStatus["TABLE_ALIAS"]);
							$("#tableNameFont").html(textStatus["DB_NAME"]+" >> "+textStatus["TABLE_ALIAS"]);
						}
					},
					error:function(){}
				});
				initClassSelection();
				window.parent.hideLoading();
				$("#stepOneTable").css({
			    	"margin-top":parseInt(($("#step-1").height()-$("#stepOneTable").height())/2)
			    });
			});
			function initIfUpdate(){
				var ruleId = $("#hiddenOfRuleCode").val();
				if(parseInt(ruleId)!=-1){
					$.ajax({
						url:"${pageContext.request.contextPath}/ruleEditAction/findOneDetail.ilf",
						async:false,
						type:"POST",
						data:"ruleId="+ruleId,
						dataType:"json",
						timeout:10000, 
						success:function(obj){
							ruleObj = obj;				
							/*渲染基本信息[规则名称、规则分类、核查目的、解决建议]*/
							$("#ruleNameInput").val(ruleObj["ruleName"]);
							$("#ruleDescInput").val(ruleObj["ruleDesc"]);
							$("#howToRecover").val(ruleObj["recovery"]);
							$("#classTypeSelect").val(ruleObj["className"]);
							$("#gradeSelection").val(ruleObj["ruleGrade"]);
							$("#ruleVersionInput").val(ruleObj["ruleVersion"]);
							/*渲染调度详情*/
							var quartzObj = ruleObj["quartzDetail"];
							execCircleSetting(true,"execCircle",quartzObj["CIRCLE_TYPE"]);
							if(quartzObj["CIRCLE_TYPE"]!="无"){
								$("#hourOfDay").val(quartzObj["HOUR_VAR"]);
								$("#minuteOfHour").val(quartzObj["SECOND_VAR"]);
								$("#secondOfMinute").val(quartzObj["MINUTE_VAR"]);
								if(quartzObj["CIRCLE_TYPE"]=="周"){
									$("#chooseDayOfWeek").val(quartzObj["DAY_OF_WEEK"]);
								}else if(quartzObj["CIRCLE_TYPE"]=="月"){
									$("#chooseDayOfMonth").val(quartzObj["DAY_OF_MONTH"]);
								}			
							}	
							document.getElementById("configConButton").click();
							document.getElementById("configConButton").click();
							/*渲染调度及是否启用*/
							$("#isUsingSelection").val(ruleObj["isUsing"]);
							//4.渲染规则详情
							var $items = ruleObj["editedItems"];
							if($items.length>0){
								var $item = $items[0];
								$("#exportColumns").val($item["exportColumns"]);
								$("#auditSql").val($item["auditSql"]);
							}
						},
						error:function(){
							window.wxc.xcConfirm("初始化失败.",window.wxc.xcConfirm.typeEnum.error);
						}
					});	
				}
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
					/*[非空验证]导出字段*/
					var exportColumn = $("#exportColumns").val();
					if(exportColumn==""){
						window.wxc.xcConfirm("请输入导出属性.",window.wxc.xcConfirm.typeEnum.info);
						return false;
					}
					/*[非空验证]核查SQL语句*/
					var auditSql = $("#auditSql").val();
					if(auditSql==""){
						window.wxc.xcConfirm("请输入核查语句.",window.wxc.xcConfirm.typeEnum.info);
						return false;
					}
					var items = [{
						auditType:"自定义核查",
						exportColumns:exportColumn,
						auditSql:auditSql
					}];
					ruleObj["editedItems"] = items;
				}
				return validPass;
		    }
			function onFinishCallback(){
				/*缓存调度周期*/
				var conExpress = $("#conExpressionInput").val();
				if(conExpress==""){
					window.wxc.xcConfirm("请编辑调度周期.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					ruleObj["conExpress"] = conExpress;
				}
				/*缓存规则状态[启用、停用]*/
				ruleObj["isUsing"] = $("#isUsingSelection").val();
				/*缓存所属模型*/
				ruleObj["tableId"] = $("#hiddenOfBelongTable").val();
				/*缓存调度详细配置*/
				var circleObj = new Object();
				if(conExpress=="-"){
					/*手动执行*/
					circleObj["CIRCLE_TYPE"] = "无";
				}else{
					/*执行粒度*/
					circleObj["CIRCLE_TYPE"] = getCheckedValue("execCircle");
					/*周粒度或月粒度[填充周几或几号.]*/
					if(circleObj["CIRCLE_TYPE"]=="周"){
						circleObj["DAY_OF_WEEK"] = $("#chooseDayOfWeek").val();
					}else if(circleObj["CIRCLE_TYPE"]=="月"){
						circleObj["DAY_OF_MONTH"] = $("#chooseDayOfMonth").val();
					}				
					/*小时、分钟、秒*/
					circleObj["HOUR_VAR"] = $("#hourOfDay").val();
					circleObj["MINUTE_VAR"] = $("#minuteOfHour").val();
					circleObj["SECOND_VAR"] = $("#secondOfMinute").val();					
				}
				/*5.版本*/
				ruleObj["ruleVersion"] = $("#ruleVersionInput").val();
				ruleObj["quartzDetail"] = circleObj;
				ruleObj["ruleId"] = $("#hiddenOfRuleCode").val();
				ruleObj["ruleType"] = "自定义核查";
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
				ruleObj = null;
				/*第一步：初始化*/
				$("#hiddenOfRuleCode").val("-1");
				document.getElementById("stepOneForm").reset();
				initClassSelection();
				/*第二步：初始化*/
				document.getElementById("stepTwoForm").reset();
				/*第三步：清空调度*/
				$("#conExpressionInput").val("");
				$("#exConfigButton").html("配置");	
				unCheckAllBoxes("execCircle");
				$("#chooseDayOfWeek").val("-");
				$("#chooseDayOfWeek").hide();	
				$("#chooseDayOfMonth").val("0");
				$("#chooseDayOfMonth").hide();	
				$("#hourOfDay").val("0");
				$("#minuteOfHour").val("0");
				$("#secondOfMinute").val("0");	
				isExecShow = false;
				$(".circleConfig").hide();
				/*第三步：是否启用*/
				$("#isUsingSelection").val("Y");
				/*[初始化]返回第一步*/
				$("#oneStep").click();
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
			function classTypeChoose(className){
				if(className=="NONE"){
					document.getElementById("classTypeSelect").style.width = "175px";
					$("#classNameInput").show(400);
				}else{
					$("#classNameInput").hide();
					document.getElementById("classTypeSelect").style.width = "530px";
				}
			}
			var isExecShow = false;
			function doCircleConfig(){
				if(!isExecShow){
					$("#exConfigButton").html("保存");
					$(".circleConfig").show(300);
					isExecShow = true;
				}else{
					var circleType = getCheckedValue("execCircle");
					if(circleType!=null){
						var isValid = true;
						if(circleType=="无"){
							$("#conExpressionInput").val("-");	
						}else{							
							//1.执行数据验证
							var hourOfDay = $("#hourOfDay").val();
							if(hourOfDay=="-" || hourOfDay==""){
								window.wxc.xcConfirm("请配置小时粒度.",window.wxc.xcConfirm.typeEnum.info);
								return;
							}
							var minuteOfHour = $("#minuteOfHour").val();
							if(minuteOfHour=="-" || minuteOfHour==""){
								window.wxc.xcConfirm("请配置分钟粒度.",window.wxc.xcConfirm.typeEnum.info);
								return;
							}
							var secondOfMinute = $("#secondOfMinute").val();
							if(secondOfMinute=="-" || secondOfMinute==""){
								window.wxc.xcConfirm("请配置秒粒度.",window.wxc.xcConfirm.typeEnum.info);
								return;
							}
							//2.拼装表达式
							if(circleType=="日"){
								//2.1.每天定点执行
								var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" * * ?";
								$("#conExpressionInput").val($expression);
							}else if(circleType=="周"){
								//2.2.每周定点执行
								var dayOfWeek = $("#chooseDayOfWeek").val();
								if(dayOfWeek=="-" || dayOfWeek==""){
									window.wxc.xcConfirm("请配置周粒度.",window.wxc.xcConfirm.typeEnum.info);
									return;
								}
								var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" ? * "+dayOfWeek;
								$("#conExpressionInput").val($expression);
							}else if(circleType=="月"){
								//2.3.每月定点执行
								var dayOfMonth = $("#chooseDayOfMonth").val();
								if(dayOfMonth=="-" || dayOfMonth==""){
									window.wxc.xcConfirm("请配置天粒度.",window.wxc.xcConfirm.typeEnum.info);
									return;
								}
								var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" "+dayOfMonth+" * ?";
								$("#conExpressionInput").val($expression);
							}
						}
						if(isValid){
							$("#exConfigButton").html("配置");
							$(".circleConfig").hide(300);
							isExecShow = false;		
						}
					}else{
						$("#exConfigButton").html("配置");
						$("#conExpressionInput").val("");
						$(".circleConfig").hide(300);
						isExecShow = false;	
					}
				}
			}
			function execCircleSetting(isChecked,checkboxName,checkedValue){
				uniqueCheckbox(isChecked,checkboxName,checkedValue);
				if(checkedValue=="无"){
					$("#chooseDayOfWeek").hide(300);
					$("#chooseDayOfMonth").hide(300);
				}else if(checkedValue=="周"){
					$("#conExpressionInput").val("");
					$("#chooseDayOfMonth").hide();
					$("#chooseDayOfWeek").show(300);				
				}else if(checkedValue=="月"){
					$("#chooseDayOfWeek").hide();
					$("#chooseDayOfMonth").show(300);
				}else if(checkedValue=="日"){
					$("#chooseDayOfWeek").hide(300);
					$("#chooseDayOfMonth").hide(300);
				}
			}
		</script>
  	</head>
  	<body style="width:100%;">
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" value="<%=tableId %>" id="hiddenOfBelongTable"></input>
  		<input type="hidden" value="<%=rulesId %>" id="hiddenOfRuleCode"></input>
  		<div class="panel panel-default models" style="margin-top:1px;height:490px;" id="outLinePanel">
  			<div class="panel-body h340" style="border:0px;">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #DFE8F1">
		  			<tr>
		    			<td>
		      				<div id="stepWizards" class="swMain" style="height:460px;">
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
			          							<small><font size="3">配置核查语句</font></small>
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
												<tr height="55">
													<td style="background:#F9FAFE;"><label class="l-name">问题描述.</label></td>
													<td>
														<textarea id="ruleDescInput" placeholder="" style="resize:none;width:530px;height:55px;border:solid 1px #4287F7;resize:none;"></textarea>												
													</td>
												</tr>
												<tr height="60">
													<td style="background:#F9FAFE;"><label class="l-name">解决建议.</label></td>
													<td>
														<textarea id="howToRecover" placeholder="" style="resize:none;width:530px;height:60px;border:solid 1px #4287F7;resize:none;"></textarea>												
													</td>
												</tr>
											</table>
										</form>
									</center>
						        </div>
						        <div id="step-2" style="width:100%;border:solid 1px #CCC;">
					        		<div style="width:99%;height:323px;margin-left:2px;overflow:auto;">
						        		<form id="stepTwoForm">
											<table class="table table-bordered table-hover" style="width:99%;">
												<tr height="30">
													<td style="background:#F9FAFE;text-align:center;width:10%;">
														<label class="l-name">导出属性.</label>
													</td>
													<td style="width:90%;">
														<input id="exportColumns" type="text" value="" placeholder="请依次输入导出属性：主键属性,资源名称属性,核查属性" style="width:99.5%;height:30px;margin-top:5px;border:solid 1px #4287F7;"></input>
													</td>
												</tr>
												<tr height="250">
													<td style="background:#F9FAFE;text-align:center;">
														<label class="l-name">核查语句.</label>
													</td>
													<td>
														<textarea id="auditSql" placeholder="请在此输入核查SQL语句，语句中必须包含导出属性." style="resize:none;width:99.5%;height:250px;border:solid 1px #4287F7;"></textarea>												
													</td>
												</tr>
											</table>
										</form>
					        		</div>
						        </div>
						        <div id="step-3" style="width:100%;border:solid 1px #CCC">
						        	<center>
										<table class="table table-bordered table-hover" style="width:700px;margin-top:20px;">
											<tr height="40">
												<td style="background:#F9FAFE;"><label class="l-name">调度配置.</label></td>
												<td colspan="6">
													<input type="text" value="" style="cursor:pointer;width:480px;height:30px;font-size:12px;" id="conExpressionInput" readonly></input>													
													<a class="btn btn-warning"  style="cursor:pointer;margin-top:-10px;" onclick="javascript:doCircleConfig();" id="configConButton">
														<i class="icon-download-alt icon-white"></i>
														<font id="exConfigButton">配置</font>
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
												<td colspan="7">
													<select style="width:555px" id="isUsingSelection">
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
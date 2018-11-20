<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String ruleId = request.getParameter("ruleId");
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>编辑比对规则</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/frame.css"/>		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/smartwizard/css/smart_wizard.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/smartwizard/jquery.smartWizard.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<style type="text/css">
			.txtBox{
				border:0px;
			}
		</style>
		<script type="text/javascript">
			var aTable = null;
			var aConditions = [];
			var zTable = null;
			var zConditions = [];
			var mTable = null;
			var mConditions = [];
			var aColumns = new HashMap();
		    $(document).ready(function(){
		    	window.parent.showLoading(45,45);
				/*初始化引导组件高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				$("#mostOutLine").css({
			    	"height":$("#bodyHeight").height()-2
			    });
				$("#outLinePanel").css({
			    	"height":$("#mostOutLine").height()-5
			    });
				$("#stepWizard").css({
			    	"height":$("#outLinePanel").height()-5
			    });
				$("#step-1").css({
			    	"height":$("#stepWizard").height()-180
			    });
				$("#step-2").css({
			    	"height":$("#stepWizard").height()-180
			    });
				$("#step-3").css({
			    	"height":$("#stepWizard").height()-180
			    });
				var tableHeight = $("#step-1").height()-$("#step_1_heading").height()-45;
				var pageNumbers = parseInt(tableHeight/25);
				var columnHeight = $("#step-2").height()-$("#panelFooter").height()-45;
				var columnPage = parseInt(columnHeight/25);
				$("#editPanels").css({
			    	"margin-top":parseInt((($("#step-3").height()-$("#editPanels").height())/2)-10)
			    });
		  		$("#stepWizard").smartWizard({
		  			transitionEffect:"fade",
		  			keyNavigation:false,
		  			onFinish:onFinishCallback,
		  			onLeaveStep:leaveAStepCallback
		  		});
		  		/*渲染数据库下拉框*/
		  		initDbSelection();
		  		/*考核表*/
		  		aConditions = [{
		  			name:"BELONG_DB",
		  			value:$("#hiddenOfaDb").val()
		  		}];
				aTable = $("#aDataTable").dataTable({
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
						"sWidth":"10%"
					},{
						"mData":"TABLE_ALIAS",
						"sWidth":"46%"
					},{
						"mData":"TABLE_NAME",
						"sWidth":"40%"
					},{
						"mData":"BELONG_MAJOR",
						"bVisible":false
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='aModelCheck' onchange='javascript:flushAColumns(this.checked,this.name,this.value);'></input></center>");
						$("td:eq(1)",nRow).html("<font style='font-weight:bold;'>["+aData["BELONG_MAJOR"]+"]</font>&nbsp;"+aData["TABLE_ALIAS"]);
					},
					"fnServerData":getAData,
					"sAjaxSource":"${pageContext.request.contextPath}/dataPlugin/findTableItems.ilf"
				});
				$("#aDataTable tbody").click(function(event){
					$(aTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				/*参照表*/
				zConditions = [{
		  			name:"BELONG_DB",
		  			value:$("#hiddenOfzDb").val()
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
						"sWidth":"10%"
					},{
						"mData":"TABLE_ALIAS",
						"sWidth":"46%"
					},{
						"mData":"TABLE_NAME",
						"sWidth":"40%"
					},{
						"mData":"BELONG_MAJOR",
						"bVisible":false
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageNumbers,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='zModelCheck' onchange='javascript:flushGlassColumns(this.checked,this.name,this.value);'></input></center>");
						$("td:eq(1)",nRow).html("<font style='font-weight:bold;'>["+aData["BELONG_MAJOR"]+"]</font>&nbsp;"+aData["TABLE_ALIAS"]);
					},
					"fnServerData":getZData,
					"sAjaxSource":"${pageContext.request.contextPath}/dataPlugin/findTableItems.ilf"
				});
				$("#zDataTable tbody").click(function(event){
					$(zTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				/*字段匹配*/
				mConditions = [{
		  			name:"BELONG_TABLE",
		  			value:$("#hiddenOfaModel").val()
		  		}];
				mTable = $("#columnMatchTable").dataTable({
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
					"sScrollY":columnHeight+"px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"ID",
						"sWidth":"120px"
					},{
						"mData":"COLUMN_ALIAS",
						"sWidth":"450px"
					},{
						"mData":"COLUMN_NAME",
						"sWidth":"450px"
					},{
						"mData":"COLUMN_NAME",
						"sWidth":"245px"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":columnPage,
					"sDom":"<'top'>frt<'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(aColumns==null){
							aColumns = new HashMap();
						}
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["COLUMN_NAME"]+"' name='matchChecks' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						$("td:eq(1)",nRow).html("<font class='ALIAS_FONT' id='KEY_"+aData["ID"]+"'>"+aData["COLUMN_ALIAS"]+"</font>");
						if(glassColumns==null || glassColumns.valueSet().length==0){
							/*如果匹配字段为空，则直接展示'未匹配'状态.*/
							$("td:eq(3)",nRow).html("<div id='matchDiv"+aData["COLUMN_NAME"]+"'><a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+aData["COLUMN_NAME"]+"\");'>未匹配</a></div>");
							aData["IS_MATCHED"] = false;
							aData["MATCHED_COLUMN"] = null;
						}else{
							if(glassColumns.containsKey(aData["COLUMN_NAME"])){
								/*如果包含KEY,获取匹配的属性并进行展示.*/
								var matchObject = glassColumns.get(aData["COLUMN_NAME"]);
								$("td:eq(3)",nRow).html("<div id='matchDiv"+aData["COLUMN_NAME"]+"'><a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:green;' onclick='javascript:doInitCheck(\""+aData["COLUMN_NAME"]+"\");'>"+matchObject["COLUMN_NAME"]+"</a></div>");
								aData["IS_MATCHED"] = true;
								aData["MATCHED_COLUMN"] = matchObject["COLUMN_NAME"];
							}else{
								/*如果不包含KEY，则展示'未匹配'*/
								$("td:eq(3)",nRow).html("<div id='matchDiv"+aData["COLUMN_NAME"]+"'><a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+aData["COLUMN_NAME"]+"\");'>未匹配</a><div>");
								aData["IS_MATCHED"] = false;
								aData["MATCHED_COLUMN"] = null;
							}
						}	
						aColumns.put(aData["COLUMN_NAME"],aData);
					},
					"fnServerData":getMData,
					"sAjaxSource":"${pageContext.request.contextPath}/basicColumnAction/findItemsUnPage.ilf"
				});
				$("#columnMatchTable tbody").click(function(event){
					$(mTable.fnSettings().aoData).each(function(){
						$(this.nTr).removeClass("row_selected");
					});
					if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
						$(event.target.parentNode).addClass("row_selected");
					}
				});
				var ruleCode = $("#hiddenOfRuleCode").val();
				if(parseInt(ruleCode)!=-1){
					initIfUpdate(ruleCode);
				}
				window.parent.hideLoading();
				$("#A_TABLE_SELECT").bind("keydown",function(event){
					if(event.keyCode==13){
						if($("#aModelSelect").val()=="-"){
							window.wxc.xcConfirm("请先选择核查表数据源.",window.wxc.xcConfirm.typeEnum.info);
						}else{
							searchAData();
						}
						return false;
					}
				});
				$("#Z_TABLE_SELECT").bind("keydown",function(event){
					if(event.keyCode==13){
						if($("#zModelSelect").val()=="-"){
							window.wxc.xcConfirm("请先选择业务宽表数据源.",window.wxc.xcConfirm.typeEnum.info);
						}else{
							searchZData();
						}
						return false;
					}
				});
				$("#COLUMN_RESEARCH_INPUT").bind("keydown",function(event){
					if(event.keyCode==13){
						var keyinValue = $("#COLUMN_RESEARCH_INPUT").val();
						$(".ALIAS_FONT").css({
							"color":"black",
							"font-weight":"normal"
						});
						var totalFinded = 0;
						if(aColumns!=null && aColumns.valueSet().length>0){
							var valueSet = 	aColumns.valueSet();
							for(var i=0;i<valueSet.length;i++){
								var valueObj = valueSet[i];
								if(valueObj["COLUMN_ALIAS"]!=null && valueObj["COLUMN_ALIAS"].indexOf(keyinValue)!=-1){
									$("#KEY_"+valueObj["ID"]).css({
										"color":"red",
										"font-weight":"bold"
									});
									totalFinded++;
									if(totalFinded==1){
										window.location.hash = "KEY_"+valueObj["ID"];
									}
								}
							}
						}
						if(totalFinded==0){
							window.wxc.xcConfirm("根据关键字未找到类似的属性.",window.wxc.xcConfirm.typeEnum.info);	
						}else if(totalFinded>1){
							window.wxc.xcConfirm("根据关键字共查找到"+totalFinded+"个匹配属性.",window.wxc.xcConfirm.typeEnum.info);	
						}
						return false;
					}
				});
			});
		    function initIfUpdate(ruleCode){
		    	var isFullScreen = true;
				window.parent.loading(isFullScreen);
				$.ajax({
					url:"${pageContext.request.contextPath}/compareDetailAction/findOne.ilf",
					async:false,
					type:"POST",
					data:"compareRuleId="+ruleCode,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							/*比对规则名称*/
							$("#ruleNameInput").val(textStatus["RULE_NAME"]);
							/*比对规则描述*/
							$("#ruleDescInput").val(textStatus["RULE_DESC"]);
							/*刷新数据库、模型数据*/
							$("#aModelSelect").val(textStatus["A_BELONG_DB"]);
							$("#zModelSelect").val(textStatus["Z_BELONG_DB"]);
							flushAModel(textStatus["A_BELONG_DB"]);
							flushZModel(textStatus["Z_BELONG_DB"]);
							checkAssignedValue("aModelCheck",textStatus["A_TABLE_CODE"]);
							checkAssignedValue("zModelCheck",textStatus["Z_TABLE_CODE"]);
							/*核查目标*/
							if(textStatus["IS_UNIFORM"]=="Y"){
								checkMultiValue("checkTypes","IS_UNIFORM");
							}else{
								unCheckMultiValue("checkTypes","IS_UNIFORM");
							}
							if(textStatus["IS_A_ONLY"]=="Y"){
								checkMultiValue("checkTypes","IS_A_ONLY");
							}else{
								unCheckMultiValue("checkTypes","IS_A_ONLY");
							}
							if(textStatus["IS_Z_ONLY"]=="Y"){
								checkMultiValue("checkTypes","IS_Z_ONLY");
							}else{
								unCheckMultiValue("checkTypes","IS_Z_ONLY");
							}
							if(textStatus["IS_A_FATUAL"]=="Y"){
								checkMultiValue("checkTypes","IS_A_FATUAL");
							}else{
								unCheckMultiValue("checkTypes","IS_A_FATUAL");
							}
							if(textStatus["IS_Z_FATUAL"]=="Y"){
								checkMultiValue("checkTypes","IS_Z_FATUAL");
							}else{
								unCheckMultiValue("checkTypes","IS_Z_FATUAL");
							}
							/*渲染调度详情*/
							var quartzObj = textStatus["QUARTZ_DETAIL"];
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
							/*是否启用*/
							$("#isUsingSelection").val(textStatus["IS_USING"]);
							/*增量比对*/
							$("#isIncreaseSelection").val(textStatus["IS_INCREASE"]);
							/*匹配详情*/
							flushAColumns(true,"aModelCheck",textStatus["A_TABLE_CODE"]);
							acheGlassColumns(textStatus["Z_TABLE_CODE"],textStatus["MATCHED_COLUMNS"]);
							uniqueCheckbox(true,"matchChecks",textStatus["CONNECT_COLUMN_NAME"]);
						}
						window.parent.overLoading(isFullScreen);
					},
					error:function(){
						window.parent.overLoading(isFullScreen);
					}
				});
		    }
		    function acheGlassColumns(zModelCode,hasMatcheds){
	    		$.ajax({
					url:"${pageContext.request.contextPath}/basicColumnAction/findColumnsOfModel.ilf",
					async:false,
					type:"POST",
					data:"modelId="+zModelCode,
					dataType:"json",
					timeout:10000,
					success:function(zColumns){
						/*缓存原始数据*/
						glassColumns = new HashMap();
						if(zColumns.length>0){
							for(var i=0;i<zColumns.length;i++){
								var zColumn = zColumns[i];
								glassColumns.put(zColumn["COLUMN_NAME"],zColumn);
							}
						}
						/*根据匹配情况进行缓存、渲染*/
						for(var j=0;j<hasMatcheds.length;j++){
							var matchedColumn = hasMatcheds[j];
							var zColumnName = matchedColumn["Z_COLUMN_NAME"];
							if(glassColumns.containsKey(zColumnName)){
								var zColumn = glassColumns.get(zColumnName);
								zColumn["COLUMN_NAME"] = matchedColumn["Z_COLUMN_NAME"];
								/*进行Z端数据替换*/
								glassColumns.remove(zColumnName);
								glassColumns.put(matchedColumn["A_COLUMN_NAME"],zColumn);
								/*更新A端数据*/
								if(aColumns!=null && aColumns.containsKey(matchedColumn["A_COLUMN_NAME"])){
									var aColumn = aColumns.get(matchedColumn["A_COLUMN_NAME"]);
									aColumns.remove(matchedColumn["A_COLUMN_NAME"]);
									aColumn["IS_MATCHED"] = true;
									aColumn["MATCHED_COLUMN"] = matchedColumn["Z_COLUMN_NAME"];
									aColumns.put(matchedColumn["A_COLUMN_NAME"],aColumn);
									/*更新界面*/
									var divId = "matchDiv"+matchedColumn["A_COLUMN_NAME"];
									if(document.getElementById(divId)!=null){
										document.getElementById(divId).innerHTML = "<a href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:green;' onclick='javascript:doInitCheck(\""+matchedColumn["A_COLUMN_NAME"]+"\");'>"+matchedColumn["Z_COLUMN_NAME"]+"</a>";
									}
								}
								
							}
						}
						if(glassColumns.keySet().length>0){
							var isInit = false;
							flushColumnSelects(isInit);
						}else{
							var isInit = true;
							flushColumnSelects(isInit);
						}
					},
					error:function(){}
				});
		    }
		    function getAData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					async:false,
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(aConditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			}
			function getZData(sSource,aoData,fnCallback){
				$.ajax({
					url:sSource,
					type:"POST",
					async:false,
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(zConditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			}
			function getMData(sSource,aoData,fnCallback){
				aColumns = new HashMap();
				$.ajax({
					url:sSource,
					type:"POST",
					async:false,
					data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(mConditions),
					dataType:"json",		
					success:function(data){
						fnCallback(data);
					}
				});
			}
		    function flushAColumns(isChecked,checkboxName,checkedValue){
		    	if(isChecked){
		    		/*1.保证选择的唯一性*/
		    		uniqueCheckbox(isChecked,checkboxName,checkedValue);
		    		/*2.渲染A端模型列表*/
		    		$("#hiddenOfaModel").val(checkedValue);
		    	}else{
		    		$("#hiddenOfaModel").val("-1");
		    	}
		    	/*初始化Z端属性*/
	    		if(glassColumns!=null && glassColumns.keySet().length>0){
	    			var keys = glassColumns.keySet();
	    			for(var i=0;i<keys.length;i++){
	    				var key = keys[i];
	    				var obj = glassColumns.get(key);
	    				if(obj["COLUMN_NAME"]!=key){
	    					glassColumns.remove(key);
	    					glassColumns.put(obj["COLUMN_NAME"],obj);
	    				}
	    			}
	    		}
		    	mConditions = [{
		  			name:"BELONG_TABLE",
		  			value:$("#hiddenOfaModel").val()
		  		}];
		    	mTable.fnDraw();
		    }
		    var glassColumns = new HashMap();
		    function flushGlassColumns(isChecked,checkboxName,checkedValue){
		    	if(isChecked){
		    		/*1.保证Z端Model选择的唯一性*/
		    		uniqueCheckbox(isChecked,checkboxName,checkedValue);
		    		/*2.获取ZModel对应的zColumns并执行自动匹配*/
		    		$.ajax({
						url:"${pageContext.request.contextPath}/basicColumnAction/findColumnsOfModel.ilf",
						async:false,
						type:"POST",
						data:"modelId="+checkedValue,
						dataType:"json",
						timeout:10000,
						success:function(zColumns){
							/*3.首先以<COLUMN_NAME,Object>的形式将数据缓存至glassColumns*/
							glassColumns = new HashMap();
							if(zColumns.length>0){
								for(var i=0;i<zColumns.length;i++){
									var zColumn = zColumns[i];
									glassColumns.put(zColumn["COLUMN_NAME"],zColumn);
								}
							}
							if(aColumns!=null && aColumns.valueSet().length>0){
								/*4.首先清空aColumns的匹配关系*/
								var aKeys = aColumns.keySet();
								for(var i=0;i<aKeys.length;i++){
									var aKey = aKeys[i];
									var aObj = aColumns.get(aKey);
									var divId = "matchDiv"+aObj["COLUMN_NAME"];
									if(document.getElementById(divId)!=null){
										document.getElementById(divId).innerHTML = "<a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+aObj["COLUMN_NAME"]+"\");'>未匹配</a>";
									}
									aObj["IS_MATCHED"] = false;
									aObj["MATCHED_COLUMN"] = null;
									aColumns.put(aKey,aObj);
								}
							}
							/*5.重新渲染参照表字段选择列表*/
							if(glassColumns.keySet().length>0){
								var isInit = false;
								flushColumnSelects(isInit);
							}else{
								var isInit = true;
								flushColumnSelects(isInit);
							}
							if(aColumns!=null && aColumns.valueSet().length>0 && zColumns.length>0){
								window.wxc.xcConfirm("是否使用属性自动匹配功能？","custom",{
									title:"提示",
									btn:parseInt("0011",2),
									onOk:function(){
										/*执行匹配并保存匹配关系*/
										var matchedSuccess = 0;
										for(var j=0;j<zColumns.length;j++){
											var zObj = zColumns[j];
											var zName = zObj["COLUMN_NAME"];
											var divId = "matchDiv"+zName;
											if(document.getElementById(divId)!=null){
												/*如果找到了匹配则渲染界面*/
												document.getElementById(divId).innerHTML = "<a href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:green;' onclick='javascript:doInitCheck(\""+zName+"\");'>"+zName+"</a>";
												/*保存匹配关系*/
												var aColumn = aColumns.get(zName);
												if(aColumn!=null){
													aColumn["IS_MATCHED"] = true;
													aColumn["MATCHED_COLUMN"] = zName;
													aColumns.put(zName,aColumn);
												}
												/*匹配计数*/
												matchedSuccess++;
											}
										}
										window.wxc.xcConfirm("匹配完毕.成功匹配"+matchedSuccess+"项.",window.wxc.xcConfirm.typeEnum.info);
									}
								});
							}
						},
						error:function(){}
					});
		    	}else{
		    		glassColumns = new HashMap();
		    		if(aColumns!=null && aColumns.valueSet().length>0){
						var aKeys = aColumns.keySet();
						for(var i=0;i<aKeys.length;i++){
							var aKey = aKeys[i];
							var aObj = aColumns.get(aKey);
							var divId = "matchDiv"+aObj["COLUMN_NAME"];
							if(document.getElementById(divId)!=null){
								document.getElementById(divId).innerHTML = "<a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+aObj["COLUMN_NAME"]+"\");'>未匹配</a>";;
							}
							aObj["IS_MATCHED"] = false;
							aObj["MATCHED_COLUMN"] = null;
							aColumns.put(aKey,aObj);
						}
					}
		    	}
		    }
		    function flushColumnSelects(isInit){
		    	var inHtml = "";
		    	inHtml+="<table cellpadding='0' cellspacing='0' style='width:97%;border:solid 1px #A3D0E3;border-bottom:0px;font-size:12px;'>";
		    	inHtml+="	<tr height='30'>";
		    	inHtml+="		<td style='width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>&nbsp;</td>";
		    	inHtml+="		<td style='width:45%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>Z端属性标识</td>";
		    	inHtml+="		<td style='width:45%;border-bottom:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>Z端属性名称</td>";
		    	inHtml+="	</tr>";
		    	if(isInit){
		    		/*如果指定进行初始化，则有参照字段也不展示.*/
		    		inHtml+="<tr height='30'>";
			    	inHtml+="	<td colspan='3' style='border-bottom:solid 1px #A3D0E3;'><center><font color='red'>暂无待匹配的属性</font></center></td>";
			    	inHtml+="</tr>";
		    	}else{
		    		/*如果不指定进行初始化，则有则展示，无则清空.*/
		    		if(glassColumns==null || glassColumns.keySet().length==0){
		    			inHtml+="<tr height='30'>";
				    	inHtml+="	<td colspan='3' style='border-bottom:solid 1px #A3D0E3;'><center><font color='red'>暂无待匹配的属性</font></center></td>";
				    	inHtml+="</tr>";
		    		}else{
		    			var zColumns = glassColumns.valueSet();
		    			for(var j=0;j<zColumns.length;j++){
		    				var zColumn = zColumns[j];
		    				inHtml+="	<tr height='30'>";
		    		    	inHtml+="		<td style='width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;'><center><input type='checkbox' value='"+zColumn["COLUMN_NAME"]+"' name='matchChoose' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center></td>";
		    		    	inHtml+="		<td style='width:45%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;'>"+zColumn["COLUMN_ALIAS"]+"</td>";
		    		    	inHtml+="		<td style='width:45%;border-bottom:solid 1px #A3D0E3;'>"+zColumn["COLUMN_NAME"]+"</td>";
		    		    	inHtml+="	</tr>";
		    			}
		    		}
		    	}
		    	inHtml+="</table>";
		    	document.getElementById("columnsSelect").innerHTML = inHtml;
		    }
		    function doInitCheck(aColumnName){
		    	/*缓存A端的COLUMN_NAME*/
		    	$("#hiddenOfKey").val(aColumnName);
		    	/*根据匹配情况进行渲染*/
		    	var matchColumn = glassColumns.get(aColumnName);
		    	var aColumn = aColumns.get(aColumnName);
		    	if(aColumn!=null){
		    		var title = aColumn["COLUMN_ALIAS"]+"("+aColumn["COLUMN_NAME"]+")";
			    	$("#myModalLabel").html("+ "+title);	
		    	}else{
		    		$("#myModalLabel").html("+ 属性匹配编辑");
		    	}
		    	if(matchColumn==null){
		    		unCheckAllBoxes("matchChoose");
		    	}else{
		    		checkAssignedValue("matchChoose",matchColumn["COLUMN_NAME"]);	
		    	}
		    }
			function searchAData(){
				aConditions = [{
		  			name:"BELONG_DB",
		  			value:$("#hiddenOfaDb").val()
		  		},{
		  			name:"TABLE_ALIAS",
		  			value:$("#A_TABLE_SELECT").val()
		  		}];			
				aTable.fnDraw();
			}
			function searchZData(){
				zConditions = [{
		  			name:"BELONG_DB",
		  			value:$("#hiddenOfzDb").val()
		  		},{
		  			name:"TABLE_ALIAS",
		  			value:$("#Z_TABLE_SELECT").val()
		  		}];			
				zTable.fnDraw();
			}
			function flushAModel(checkedValue){
				/*刷新核查模型列表*/
				if(checkedValue=="-"){
					$("#hiddenOfaDb").val("-1");
				}else{
					$("#hiddenOfaDb").val(checkedValue);
				}
				searchAData();
				/*初始化考核模型的属性列表*/
				$("#hiddenOfaModel").val("-1");
		    	mConditions = [{
		  			name:"BELONG_TABLE",
		  			value:$("#hiddenOfaModel").val()
		  		}];
		    	mTable.fnDraw();
			}
			function flushZModel(checkedValue){
				/*刷新参照模型列表*/
				if(checkedValue=="-"){
					$("#hiddenOfzDb").val("-1");
				}else{
					$("#hiddenOfzDb").val(checkedValue);
				}
				searchZData();
				/*初始化匹配字段（界面、关联关系、glassColumns）*/
				glassColumns = new HashMap();
				if(aColumns!=null && aColumns.keySet().length>0){
					var keys = aColumns.keySet();
					for(var i=0;i<keys.length;i++){
						var thisKey = keys[i];
						var aColumn = aColumns.get(thisKey);
						/*界面渲染*/
						var divId = "matchDiv"+aColumn["COLUMN_NAME"];
						if(document.getElementById(divId)!=null){
							document.getElementById(divId).innerHTML = "<a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+aColumn["COLUMN_NAME"]+"\");'>未匹配</a>";
						}
						/*关联关系*/
						aColumn["IS_MATCHED"] = false;
						aColumn["MATCHED_COLUMN"] = null;
						aColumns.put(thisKey,aColumn);
					}
				}
			}
			var ruleObj = null;
		    function leaveAStepCallback(obj){
		    	if(ruleObj==null){
					ruleObj = new Object();
				}
		    	var validPass = true;
				var step_num = obj.attr("rel");
				if(step_num==1){
					ruleObj["A_TABLE_ID"] = getCheckedValue("aModelCheck");
					ruleObj["Z_TABLE_ID"] = getCheckedValue("zModelCheck");		
				}else if(step_num==2){
					var IS_UNIFORM = "N";
					var IS_A_ONLY = "N";
					var IS_Z_ONLY = "N";
					var IS_A_FATUAL = "N";
					var IS_Z_FATUAL = "N";
					var checkboxes = document.getElementsByName("checkTypes");
					for(var i=0;i<checkboxes.length;i++){
						var $checkbox = checkboxes[i];
						if($checkbox.checked){
							var checkedValue = $checkbox.value;
							if(checkedValue=="IS_UNIFORM"){
								IS_UNIFORM = "Y";
							}else if(checkedValue=="IS_A_ONLY"){
								IS_A_ONLY = "Y";
							}else if(checkedValue=="IS_Z_ONLY"){
								IS_Z_ONLY = "Y";
							}else if(checkedValue=="IS_A_FATUAL"){
								IS_A_FATUAL = "Y";
							}else if(checkedValue=="IS_Z_FATUAL"){
								IS_Z_FATUAL = "Y";
							} 
						}
					}
					ruleObj["IS_UNIFORM"] = IS_UNIFORM;
					ruleObj["IS_A_ONLY"] = IS_A_ONLY;
					ruleObj["IS_Z_ONLY"] = IS_Z_ONLY;
					ruleObj["IS_A_FATUAL"] = IS_A_FATUAL;
					ruleObj["IS_Z_FATUAL"] = IS_Z_FATUAL;
					ruleObj["CONNECT_COLUMN"] = getCheckedValue("matchChecks");
					ruleObj["MATCH_COLUMNS"] = [];
					if(aColumns!=null && aColumns.keySet().length>0){
						var matchColumns = [];
						var keySets = aColumns.keySet();
						for(var i=0;i<keySets.length;i++){
							var key = keySets[i];
							var obj = aColumns.get(key);
							if(obj["IS_MATCHED"] && obj["MATCHED_COLUMN"]!=null){
								matchColumns.push(obj);
							}
						}
						ruleObj["MATCH_COLUMNS"] = matchColumns;
					}
				}
				return validPass;
		    }
		    function onFinishCallback(){
		    	ruleObj["RULE_NAME"] = $("#ruleNameInput").val();
				ruleObj["RULE_DESC"] = $("#ruleDescInput").val();
		    	/*1.缓存调度周期*/
				ruleObj["QUARTZ_EXPRESS"] = $("#conExpressionInput").val();
				/*2.缓存规则状态[启用、停用]*/
				ruleObj["IS_USING"] = $("#isUsingSelection").val();
				/*3.是否增量比对*/
				ruleObj["IS_INCREASE"] = $("#isIncreaseSelection").val();
				/*4.缓存调度配置详情*/
				var conExpress = $("#conExpressionInput").val();
				var circleObj = new Object();
				if(conExpress=="-"){
					circleObj["CIRCLE_TYPE"] = "无";
				}else{
					circleObj["CIRCLE_TYPE"] = getCheckedValue("execCircle");
					if(circleObj["CIRCLE_TYPE"]=="周"){
						circleObj["DAY_OF_WEEK"] = $("#chooseDayOfWeek").val();
					}else if(circleObj["CIRCLE_TYPE"]=="月"){
						circleObj["DAY_OF_MONTH"] = $("#chooseDayOfMonth").val();
					}
					circleObj["HOUR_VAR"] = $("#hourOfDay").val();
					circleObj["MINUTE_VAR"] = $("#minuteOfHour").val();
					circleObj["SECOND_VAR"] = $("#secondOfMinute").val();					
				}
				ruleObj["QUARTZ_DETAIL"] = circleObj;
				ruleObj["RULE_ID"] = $("#hiddenOfRuleCode").val();
				/*
					验证数据.
				 */
				if(ruleObj["RULE_NAME"]==""){
					window.wxc.xcConfirm("请输入比对规则名称.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["RULE_DESC"]==""){
					window.wxc.xcConfirm("请输入比对规则描述.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["A_TABLE_ID"]==""){
					window.wxc.xcConfirm("请选择待核查模型.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["Z_TABLE_ID"]==""){
					window.wxc.xcConfirm("请选择参照模型.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["IS_UNIFORM"]=="N" &&　ruleObj["IS_A_ONLY"]=="N"　&& ruleObj["IS_Z_ONLY"]=="N" && ruleObj["IS_A_FATUAL"]=="N" && ruleObj["IS_Z_FATUAL"]=="N"){
					window.wxc.xcConfirm("请至少选择一项比对内容.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["CONNECT_COLUMN"]==null){
					window.wxc.xcConfirm("请选择效用属性.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				if(ruleObj["MATCH_COLUMNS"]==null || ruleObj["MATCH_COLUMNS"].length==0){
					window.wxc.xcConfirm("请至少匹配一个模型属性.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				/*
					保存数据.
				  */
				window.wxc.xcConfirm("是否确认保存此条比对规则？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var isFullScreen = true;
						window.parent.loading(isFullScreen);
						$.ajax({
							url:"${pageContext.request.contextPath}/compareDetailAction/saveAudit.ilf",
							async:false,
							type:"POST",
							data:"params="+JSON.stringify(ruleObj),
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								window.parent.overLoading(isFullScreen);
								if(textStatus["success"]){
									$("#hiddenOfRuleCode").val(textStatus["editingRuleCode"]);
									window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.info);
									$("#oneStep").click();
								}else{
									window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
								}
							},
							error:function(){
								window.parent.overLoading(isFullScreen);
								window.wxc.xcConfirm(textStatus["message"],window.wxc.xcConfirm.typeEnum.error);
							}
						});
					}
				});
		    }
		    function initDbSelection(){
		    	$.ajax({
					url:"${pageContext.request.contextPath}/basicDbAction/getDbNameList.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["success"]){
							var dbObjects = textStatus["dbList"];
							if(dbObjects.length>0){
								var aOptionHtml = "";
								var zOptionHtml = "";
								aOptionHtml+="<select style='width:240px;border:solid 1px #A3D0E3;text-align:center;margin-left:-10px;' id='aModelSelect' onchange='javascript:flushAModel(this.value);'>";
								zOptionHtml+="<select style='width:240px;border:solid 1px #A3D0E3;text-align:center;margin-left:-10px;' id='zModelSelect' onchange='javascript:flushZModel(this.value);'>";
									aOptionHtml+="<option value='-'>-- 请选择核查表数据源 --</option>";
									zOptionHtml+="<option value='-'>-- 请选择参照表数据源 --</option>";
								for(var i=0;i<dbObjects.length;i++){
									var dbObject = dbObjects[i];
									aOptionHtml+="<option value='"+dbObject["ID"]+"'>"+dbObject["DB_NAME"]+"</option>";
									zOptionHtml+="<option value='"+dbObject["ID"]+"'>"+dbObject["DB_NAME"]+"</option>";
								}
								aOptionHtml+="</select>";
								zOptionHtml+="</select>";
								document.getElementById("aModelContainer").innerHTML = aOptionHtml;
								document.getElementById("zModelContainer").innerHTML = zOptionHtml;
							}
						}
					},
					error:function(){}
				});
		    }
		    function saveAndRefresh(){
		    	var editingColumn = $("#hiddenOfKey").val();
		    	if(aColumns!=null){
		    		var aColumn = aColumns.get(editingColumn);
		    		if(aColumn!=null){
		    			/*将编辑结果维护至aColumn*/
		    			var matchedValue = getCheckedValue("matchChoose");
		    			if(matchedValue==null){
		    				aColumn["IS_MATCHED"] = false;
		    				aColumn["MATCHED_COLUMN"] = null;
		    			}else{
		    				aColumn["IS_MATCHED"] = true;
		    				aColumn["MATCHED_COLUMN"] = matchedValue;
		    			}
		    			aColumns.put(editingColumn,aColumn);
		    			/*渲染界面*/
		    			var divId = "matchDiv"+editingColumn;
		    			if(document.getElementById(divId)!=null){
		    				if(matchedValue==null){
		    					document.getElementById(divId).innerHTML = "<a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:red;' onclick='javascript:doInitCheck(\""+editingColumn+"\");'>未匹配</a>";
		    				}else{
		    					document.getElementById(divId).innerHTML = "<a title='点我修改属性的匹配关系' href='#countModal' role='button' data-toggle='modal' style='cursor:pointer;color:green;' onclick='javascript:doInitCheck(\""+editingColumn+"\");'>"+matchedValue+"</a>";	
		    				}
						}
		    			if(matchedValue==null){
		    				/*如果有关联关系，则断开与zColumns中的关系*/
		    				if(glassColumns!=null && glassColumns.containsKey(editingColumn)){
		    					var matchColumn = glassColumns.get(editingColumn);
		    					glassColumns.remove(editingColumn);
		    					glassColumns.put(matchColumn["COLUMN_NAME"],matchColumn);
		    				}
		    			}else{
		    				/*如果选择的匹配属性不为空，则修改关联状态*/
		    				var keySets = glassColumns.keySet();
		    				for(var w=0;w<keySets.length;w++){
		    					var thisKey = keySets[w];
		    					var columnObj = glassColumns.get(thisKey);
		    					if(columnObj["COLUMN_NAME"]==matchedValue){
		    						glassColumns.remove(thisKey);
		    						glassColumns.put(editingColumn,columnObj);
		    						break;
		    					}
		    				}
		    			}
		    		}	
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
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="-1" id="hiddenOfaDb"></input>
  		<input type="hidden" value="-1" id="hiddenOfzDb"></input>
  		<input type="hidden" value="-1" id="hiddenOfaModel"></input>
  		<input type="hidden" value="-1" id="hiddenOfKey"></input>
  		<input type="hidden" value="<%=frameHeight %>" id="hiddenOfHeight"></input>
  		<input type="hidden" value="<%=ruleId %>" id="hiddenOfRuleCode"></input>
  		<div style="width:100%;height:300px;border:solid 1px #FFF;" id="mostOutLine">
  			<div style="width:99%;float:right;height:460px;margin-top:3px;margin-right:2px;border:solid 1px #FFF;" id="outLinePanel">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #DFE8F1">
		  			<tr>
		    			<td>
		      				<div id="stepWizard" class="swMain" style="height:462px;border:solid 1px #FFF;">
			        			<ul>
			          				<li>
			          					<a href="#step-1" style="margin-top:5px;width:180px" id="oneStep">
			          						<span class="stepNumber">1</span>
			          						<span class="stepDesc">
			          							<font size="2">第一步</font><br/>
			            						<small><font size="3">选择比对模型</font></small>
			            					</span>
			            				</a>
			            			</li>
			          				<li>
			          					<a href="#step-2" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">2</span>
			          						<span class="stepDesc">
			          							<font size="2">第二步</font><br/>
			            						<small><font size="3">模型属性及匹配</font></small>
			            					</span>
			            				</a>
			            			</li>
			            			<li>
			          					<a href="#step-3" style="margin-top:5px;width:180px">
			          						<span class="stepNumber">3</span>
			          						<span class="stepDesc">
			          							<font size="2">第三步</font><br/>
			            						<small><font size="3">其他配置</font></small>
			            					</span>
			            				</a>
			            			</li>
			        			</ul>
						        <div id="step-1" style="width:100%;border-top:solid 1px #CCC">
									<div class="panel-heading" style="border-bottom:0px;" id="step_1_heading">
										<div style="float:left;" id="aModelContainer">
											<select class="w200" style="width:240px;text-align:center;" id="aModelSelect">
												<option value="-">-- 请选择核查表数据源 --</option>
											</select>
										</div>
										<div style="float:left;margin-left:5px;">
											<input type="text" id="A_TABLE_SELECT" style="width:240px;height:30px;border:solid 1px #A3D0E3;" placeholder="请输入核查模型名称,点击Enter查询"></input>
										</div>
										<div style="float:right;margin-left:5px;">
											<input type="text" id="Z_TABLE_SELECT" style="width:240px;height:30px;border:solid 1px #A3D0E3;" placeholder="请输入宽表模型名称,点击Enter查询"></input>
										</div>
										<div style="float:right;" id="zModelContainer" id="zModelSelect">
											<select class="w200" style="width:240px;text-align:center;">
												<option value="-">-- 请选择参照表数据源 --</option>
											</select>
										</div>
									</div>
									<div class="panel-body h340" style="border:0px;">
										<div class="panlecontent container4 clearfix" style="border:0px;">
											<div class="div_scroll" style="border:0px;">
												<div style="height:auto;width:auto" style="border:0px;">
													<div style="float:left;width:49.6%;" style="border:0px;margin-top:2px;">
														<div class="panel-body h340" style="border:0px;">
															<div class="panlecontent container4 clearfix">
																<div class="div_scroll">
																	<div style="height:auto;width:auto">
																		<table cellpadding="0" cellspacing="0" border="0" id="aDataTable" style="border:0px;">
																			<thead>
																				<tr>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">&nbsp;</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">模型标识</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">模型名称</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">所属专业</th>
																				</tr>
																			</thead>
																			<tbody></tbody>
																		</table>					
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div style="float:left;width:49.6%;margin-left:2px;margin-right:2px;">
														<div class="panel-body h340" style="border:0px;">
															<div class="panlecontent container4 clearfix">
																<div class="div_scroll">
																	<div style="height:auto;width:auto">
																		<table cellpadding="0" cellspacing="0" border="0" id="zDataTable" style="border:0px;">
																			<thead>
																				<tr>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">&nbsp;</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">模型标识</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">模型名称</th>
																					<th style="border-top:0px;text-align:center;background:#FCFDFE;">所属专业</th>
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
										</div>
									</div>
						        </div>
						        <div id="step-2" style="width:99%;border-top:solid 1px #CCC">
									<div class="panel-body" style="margin-top:5px;border:0px;">
										<div class="panlecontent container4 clearfix">
											<div class="div_scroll">
												<div style="height:auto;width:auto">
													<table cellpadding="0" cellspacing="0" border="0" id="columnMatchTable" style="border:0px;">
														<thead>
															<tr>
																<th style="text-align:center;background:#FCFDFE;">是否效用属性</th>
																<th style="text-align:center;background:#FCFDFE;">A端模型属性标识</th>
																<th style="text-align:center;background:#FCFDFE;">A端模型属性名称</th>
																<th style="text-align:center;background:#FCFDFE;">Z端模型属性名称</th>
															</tr>
														</thead>
														<tbody></tbody>
													</table>					
												</div>
											</div>
										</div>
									</div>
									<div class="panel-footer" style="border:solid 1px #CCC;margin-top:2px;" id="panelFooter">
										<div style="margin-top:10px;">
											<font color="blue">*&nbsp;比对结果类型定义：</font>
											<input type="checkbox" name="checkTypes" value="IS_UNIFORM">
												<font style="margin-left:5px;">
													<a title="将A端和Z端不一致的数据视为问题数据" style="cursor:pointer;">数据是否一致</a>
												</font>
											</input>
											<input type="checkbox" name="checkTypes" value="IS_A_ONLY" style="margin-left:10px">
												<font style="margin-left:5px;">
													<a title="将仅A端有但是Z端没有的数据视为问题数据" style="cursor:pointer;">仅A端有数据</a>
												</font>
											</input>
											<input type="checkbox" name="checkTypes" value="IS_Z_ONLY" style="margin-left:10px">
												<font style="margin-left:5px;">
													<a title="将仅Z端有但是A端没有的数据视为问题数据" style="cursor:pointer;">仅Z端有数据</a>
												</font>
											</input>
											<input type="checkbox" name="checkTypes" value="IS_A_FATUAL" style="margin-left:10px">
												<font style="margin-left:5px;">
													<a title="将A表中效用属性为空的记录视为问题数据" style="cursor:pointer;">A端数据异常</a>
												</font>
											</input>
											<input type="checkbox" name="checkTypes" value="IS_Z_FATUAL" style="margin-left:10px">
												<font style="margin-left:5px;">
													<a title="将Z表中效用属性为空的记录视为问题数据" style="cursor:pointer;">Z端数据异常</a>
												</font>
											</input>
											<input type="text" id="COLUMN_RESEARCH_INPUT" placeholder="请输入字段名称并敲击Enter查询" style="margin-left:10px;width:220px;height:25px;font-size:12px;border:solid 1px #A3D0E3;"></input>
										</div>
									</div>
						        </div>
						        <div id="step-3" style="width:100%;border-top:solid 1px #CCC">
						        	<center>
										<table class="table table-bordered table-hover" style="width:700px;margin-top:30px;" id="editPanels">
											<tr height="40">
												<td style="background:#F9FAFE;">
													<label class="l-name">规则名称.</label>
												</td>
												<td colspan="5">
													<input type="text" id="ruleNameInput" placeholder="" style="border:solid 1px lightblue;border-radius:7px;font-size:12px;width:580px;height:30px;margin-top:5px;"></input>
												</td>
											</tr>
											<tr height="40">
												<td style="background:#F9FAFE;">
													<label class="l-name">调度配置.</label>
												</td>
												<td colspan="5">
													<input type="text" value="" style="cursor:pointer;width:505px;height:30px;font-size:12px;border:solid 1px lightblue;border-radius:7px;" id="conExpressionInput" readonly></input>													
													<a class="btn btn-warning"  style="cursor:pointer;margin-top:-10px;" onclick="javascript:doCircleConfig();" id="configConButton">
														<i class="icon-download-alt icon-white"></i><font id="exConfigButton">配置</font>
													</a>
												</td>
											</tr>
											<tr height="40" style="border:solid 1px #3794D1;display:none;" class="circleConfig">
												<td style="background:#F9FAFE;">
													<label class="l-name">执行粒度.</label>
												</td>
												<td colspan="5" style="background:#F9FAFE;">
													<input type="checkbox" name="execCircle" value="日" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);"></input><font style="margin-left:10px;">每天</font>
													<input type="checkbox" name="execCircle" value="周" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">每周</font>
													<input type="checkbox" name="execCircle" value="月" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">每月</font>
													<input type="checkbox" name="execCircle" value="无" onchange="javascript:execCircleSetting(this.checked,this.name,this.value);" style="margin-left:20px;"></input><font style="margin-left:10px;">手动执行</font>
												</td>
											</tr>
											<tr height="40" style="border:solid 1px #3794D1;display:none;" class="circleConfig">
												<td style="background:#F9FAFE;"><label class="l-name">执行时间.</label></td>
												<td colspan="5" style="background:#F9FAFE;">
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
												<td colspan="2">
													<select class="w500" style="width:230px;border:solid 1px lightblue;border-radius:7px;" id="isUsingSelection">
														<option value="Y">启用</option>
														<option value="N">停用</option>
													</select>
												</td>
												<td style="background:#F9FAFE;"><label class="l-name">增量比对.</label></td>
												<td colspan="2">
													<select class="w500" style="width:230px;border:solid 1px lightblue;border-radius:7px;" id="isIncreaseSelection">
														<option value="N">否</option>
														<option value="Y">是</option>
													</select>
												</td>
											</tr>
											<tr height="80">
												<td style="background:#F9FAFE;"><label class="l-name">规则名称.</label></td>
												<td colspan="5">
													<textarea id="ruleDescInput" placeholder="" style="border:solid 1px lightblue;border-radius:7px;resize:none;font-size:12px;width:580px;height:80px;"></textarea>
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
  		<%--窗口表单：开始--%>
		<div id="countModal" class="modal hide fade" style="width:650px;height:380px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 属性匹配编辑</h3>
			</div>
			<div class="modal-body" style="max-height:265px;">
				<div style="width:100%;height:261px;overflow:auto;" id="columnsSelect">
					<table cellpadding='0' cellspacing='0' style='width:97%;border:solid 1px #A3D0E3;border-bottom:0px;font-size:12px;'>
						<tr height='30'>
							<td style='width:10%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>&nbsp;</td>
							<td style='width:45%;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>Z端属性标识</td>
							<td style='width:45%;border-bottom:solid 1px #A3D0E3;text-align:center;background:#FCFDFE;'>Z端属性名称</td>
						</tr>
						<tr height='30'>
							<td colspan='3'><center><font color='red'>暂无待匹配的属性</font></center></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-success" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;" onclick="javascript:saveAndRefresh();">
					<i class="icon-bookmark icon-white"></i>保存并关闭窗口
				</button>
			</div>
		</div>
		<%--窗口表单：结束--%>
    </body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
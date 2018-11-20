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
    	<title>规则查询</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
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
			function bindingKeyDown(){
				$("#ruleNameKey").bind("keydown",function(event){
					if(event.keyCode==13){
						searchData();
						return false;
					}
				});
			}
			var acheItems = null;
			var oTable = null;
			var conditions = [];
			var tableHeight = 0;
			var pageAccount = 0;
			var pageNumber = 1;
			var pageContext = 4;
			var tableHeight = 300;
			$(document).ready(function(){
				/*初始化页面高度*/
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()
			    });
				/*主面板：包含面板头和编辑区域*/
				$("#mainPanel").css({
			    	"height":$("#hiddenOfHeight").val()-2
			    });
				/*[任务视图]编辑区域高度*/
				$("#ruleClassBuck").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-2
			    });
				/*[规则视图]编辑区域高度*/
				$("#ruleEditsBuck").css({
			    	"height":$("#mainPanel").height()-$("#panelHeading").height()-2
			    });
				$("#frmt").css({
			    	"height":$("#ruleEditsBuck").height()-$("#leftTreeHead").height()-10
			    });
				tableHeight = $("#ruleEditsBuck").height()-$("#ruleTablePanel").height()-70-2;
				pageAccount = parseInt(tableHeight/30);
				/*任务视图每页记录数*/
				pageContext = parseInt($("#ruleClassBuck").height()/105);
				/*初始化资源树*/
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleManageAction/findDbData.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["success"]){
							var nodes = textStatus["nodes"];
							$("#frmt").on("changed.jstree",function(e,data){
								if(data.selected.length){
									var nodeCode = data.instance.get_node(data.selected[0]).id;
									var regExp = new RegExp("^[0-9]*$");
									if(regExp.test(nodeCode)){
										$("#hiddenOfTableId").val(nodeCode);				
										searchData();
									}
								}
							}).jstree({
								"core":{
									"data":nodes,
									"dblclick_toggle":true
								}
							});
							$("#HIDDEN_OF_LINKURL").val(textStatus["linkurl"]);
						}
					},
					error:function(){
						window.wxc.xcConfirm("模型浏览初始化失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
				bindingKeyDown();
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
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
						"sWidth":"65px"
					},{
						"mData":"RULE_NAME",
						"sWidth":"280px"
					},{
						"mData":"RULE_VERSION",
						"sWidth":"80px"
					},{
						"mData":"RULE_TYPE",
						"sWidth":"100px"
					},{
						"mData":"LAST_ACTION_TIME",
						"sWidth":"170px"
					},{
						"mData":"PROBLEM_TOTAL",
						"sWidth":"110px"
					},{
						"mData":"CREATE_TIME",
						"sWidth":"170px"
					},{
						"mData":"CREATE_USER_NAME",
						"sWidth":"100px"
					},{
						"mData":"RULE_GRADE",
						"sWidth":"160px"
					},{
						"mData":"FILE_REPORT",
						"sWidth":"160px"
					}],
					"aoColumnDefs":[],
					"iDisplayLength":pageAccount,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheItems==null){
							acheItems = new HashMap();
						}
						acheItems.put(aData["ID"],aData);
						/*1.0.选择框*/
						$("td:eq(0)",nRow).html("<center><input type='checkbox' value='"+aData["ID"]+"' name='checksRadio' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></center>");
						/*1.1.问题数*/
						if(aData["PROBLEM_TOTAL"]!="-" && parseInt(aData["PROBLEM_TOTAL"])!=0){
							$("td:eq(5)",nRow).html("<a onclick='javascript:problemDetail("+aData["ID"]+");' style='cursor:pointer;color:red;'>"+aData["PROBLEM_TOTAL"]+"</a>");	
						}else{
							$("td:eq(5)",nRow).html("<font color='blue'>"+aData["PROBLEM_TOTAL"]+"</font>");
						}
						/*1.2.创建时间*/
						var $date = new Date();
						$date.setTime(aData["CREATE_TIME"].time);
						$("td:eq(6)",nRow).html($date.toLocaleString());
						/*1.3.最后执行日期*/
						if(aData["LAST_ACTION_TIME"]!=null && aData["LAST_ACTION_TIME"]!=""){
							var $date = new Date();
							$date.setTime(aData["LAST_ACTION_TIME"].time);
							$("td:eq(4)",nRow).html($date.toLocaleString());	
						}
						/*1.4.规则类型*/
						if(aData["RULE_TYPE"]=="配置核查"){
							$("td:eq(3)",nRow).html("<center><font color='blue'>"+aData["RULE_TYPE"]+"</font></center>");	
						}else{
							$("td:eq(3)",nRow).html("<center><font color='green'>"+aData["RULE_TYPE"]+"</font></center>");
						}
						/*1.5.规则等级*/
						if(aData["RULE_GRADE"]==2){
							$("td:eq(8)",nRow).html("<center><font color='blue'>一般</font></center>");	
						}else if(aData["RULE_GRADE"]==3){
							$("td:eq(8)",nRow).html("<center><font color='orange'>重要</font></center>");
						}else if(aData["RULE_GRADE"]==5){
							$("td:eq(8)",nRow).html("<center><font color='red'>非常重要</font></center>");
						}
						/*1.6.核查报告*/
						if(aData["FILE_REPORT"]!=null && aData["FILE_REPORT"]!=""){
							$("td:eq(9)",nRow).html("<center><a style='cursor:pointer;' onclick='javascript:downloadData("+aData["ID"]+");'>"+aData["FILE_REPORT"]+"</a></center>");	
						}
					},
					"fnServerData":getData,
					"sAjaxSource":"${pageContext.request.contextPath}/ruleManageAction/findItems.ilf"
				});
				initRuleClass();
				$("#MODEL_NAME_INPUT").bind("keydown",function(event){
					if(event.keyCode==13){
						flushDbTree();
						return false;
					}
				});
			});
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
			function fireDataOperateJob(){
				window.open($("#HIDDEN_OF_LINKURL").val());
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
			function searchData(){
				conditions = [{
					name:"BIND_TABLE",
					value:$("#hiddenOfTableId").val()
				}];
				var ruleName = $("#ruleNameKey").val();
				if(ruleName!=""){
					conditions.push({
						name:"RULE_NAME",
						value:ruleName
					});				
				}		
				oTable.fnDraw();
			}
			function problemDetail(ruleId){
				var ruleObj = acheItems.get(ruleId);
				var problemTotal = ruleObj["PROBLEM_TOTAL"];
				window.parent.turnToJsp("问题数据详情","jsp/rules/prolDetails.jsp?ruleId="+ruleId+"&total="+problemTotal);
			}
			function turnToPage(){
				var checkedTableId = $("#hiddenOfTableId").val();
				if(checkedTableId==-1){
					//1.如果尚未选择模型，则提示
					window.wxc.xcConfirm("请先选择一个模型.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					//2.1.模型编号
					var tableId = $("#hiddenOfTableId").val();
					//2.2.规则编号
					var ruleId = -1;
					var checkedValue = getCheckedValue("checksRadio");
					if(checkedValue!=null){
						ruleId = checkedValue;
					}
					if(ruleId!=-1){
						var acheObj = acheItems.get(ruleId);
						if(acheObj["RULE_TYPE"]=="配置核查"){
							window.parent.turnToJsp("规则编辑","jsp/rules/edit/ruleEdit.jsp?tableId="+tableId+"&ruleId="+ruleId);		
						}else{
							window.wxc.xcConfirm("请使用自定义核查编辑器.",window.wxc.xcConfirm.typeEnum.info);
						}
					}else{
						window.parent.turnToJsp("规则编辑","jsp/rules/edit/ruleEdit.jsp?tableId="+tableId+"&ruleId="+ruleId);
					}	
				}
			}
			function turnToSqlPage(){
				var checkedTableId = $("#hiddenOfTableId").val();
				if(checkedTableId==-1){
					window.wxc.xcConfirm("请先选择一个模型.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					var tableId = $("#hiddenOfTableId").val();
					var ruleId = -1;
					var checkedValue = getCheckedValue("checksRadio");
					if(checkedValue!=null){
						ruleId = checkedValue;
					}
					if(ruleId!=-1){
						var acheObj = acheItems.get(ruleId);
						if(acheObj["RULE_TYPE"]=="自定义核查"){
							window.parent.turnToJsp("自定义核查编辑","jsp/rules/edit/sqlRuleEdit.jsp?tableId="+tableId+"&ruleId="+ruleId);			
						}else{
							window.wxc.xcConfirm("请使用配置核查编辑器.",window.wxc.xcConfirm.typeEnum.info);
						}
					}else{
						window.parent.turnToJsp("自定义核查编辑","jsp/rules/edit/sqlRuleEdit.jsp?tableId="+tableId+"&ruleId="+ruleId);
					}
				}
			}
			function deleteRule(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条规则.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.wxc.xcConfirm("是否确认删除此规则？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/ruleEditAction/deleteItem.ilf",
								async:false,
								type:"POST",
								data:"ruleId="+checkedValue,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["success"]){
										window.wxc.xcConfirm("规则删除成功.",window.wxc.xcConfirm.typeEnum.info);
										searchData();
									}else{
										window.wxc.xcConfirm("规则删除失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("规则删除失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});
						}
					});	
				}
			}
			var moditorJob = new HashMap();
			function oversawJob(){
				if(moditorJob.keySet().length>0){
					var keySet = moditorJob.keySet();
					var keys = keySet[0];
					$.ajax({
						url:"${pageContext.request.contextPath}/ruleEditAction/moditorProcess.ilf",
						async:false,
						type:"POST",
						timeout:0,
						data:"ruleIds="+keys,
						dataType:"json",
						timeout:10000,
						success:function(textStatus){
							if(textStatus["success"] && textStatus["ITEMS"].length>0){
								var ruleNames = "";
								var items = textStatus["ITEMS"];
								for(var i=0;i<items.length;i++){
									var $item = items[i];
									moditorJob.remove($item["RULE_ID"]);
									if(ruleNames==""){
										ruleNames = $item["RULE_NAME"];
									}else{
										ruleNames+= ";"+$item["RULE_NAME"];
									}
								}
								/*渲染提示*/
								document.getElementById("tipsContainer").innerHTML = "<font color='green'>规则名称："+ruleNames+".</font><br/><font color='red'>执行结果：数据核查已完成，请刷新规则列表.</font>";
								$("#tipDivs").show(1000);
								setTimeout("javascript:$('#tipDivs').hide(1000);",4000);
								if(moditorJob.keySet().length>0){
									setTimeout("javascript:oversawJob();",5000);
								}
							}else{
								if(moditorJob.keySet().length>0){
									setTimeout("javascript:oversawJob();",5000);
								}
							}
						},
						error:function(){
							if(moditorJob.keySet().length>0){
								setTimeout("javascript:oversawJob();",5000);	
							}
						}
					});
				}
			}
			function auditRightNow(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条规则.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}else{
					var jobToken = createToken();
					window.wxc.xcConfirm("是否确认立即执行规则？.","custom",{
						title:"提示",
						btn:parseInt("0011",2),
						onOk:function(){
							moditorJob.put(jobToken,checkedValue);
							/*1.隐藏确认框.*/
							var buttons = document.getElementsByClassName("cancel");
							if(buttons.length>0){
								var canelButton = buttons[0];
								canelButton.click();
							}
							setTimeout("javascript:oversawJob();",5000);
							/*2.执行核查.*/
							$.ajax({
								url:"${pageContext.request.contextPath}/ruleEditAction/auditRightNow.ilf",
								async:true,
								type:"POST",
								data:"ruleId="+checkedValue+"&jobToken="+jobToken,
								dataType:"json",
								timeout:10000,
								success:function(textStatus){},
								error:function(){}
							});
						}
					});	
				}
			}
			function createToken(){
				var strArray = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
				var finalStr = "";
				while(finalStr.length<=30){
					var randomValue = Math.random()*25;
					var randomIndex = parseInt(randomValue);
					finalStr+=strArray[randomIndex];
				}	
				return finalStr;
			}
			function turnToLineChart(){
				var checkedValue = getCheckedValue("checksRadio");
				if(checkedValue==null || checkedValue==""){
					window.wxc.xcConfirm("请先选择一条规则.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					window.parent.turnToJsp("历史分析","jsp/rules/charts/showInLine.jsp?ruleId="+checkedValue);
				}
			}
			function turnToBarChart(objectKey){
				var thisObj = countMap.get(objectKey);
				var ids = thisObj["IDS"];
				ids = ids.split(",");
				var $id = ids[0];			
				window.parent.turnToJsp("核查结果","jsp/rules/charts/showInBar.jsp?demoCode="+$id);
			}
			function turnToTeamShow(){
				window.parent.turnToJsp("分组总览","jsp/rules/charts/showInGroup.jsp");
			}
			function downloadData(ruleCode){
				window.wxc.xcConfirm("是否确认下载此数据报告？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/ruleEditAction/downloadReport.ilf?ruleCode="+ruleCode);
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});	
			}
			function showOrHide(typeCode){
				if(typeCode==1){
					$("#ruleConfigDiv").hide();
					$(".buckResearch").show(400);
					$("#ruleShowDiv").show(400);
				}else if(typeCode==2){
					$("#ruleShowDiv").hide();
					$(".buckResearch").hide();
					$("#ruleConfigDiv").show(400);
				}
			}
			function flushClicked(clickedCode){
				$(".buckClass").css("background","#FFF");
				$("#"+clickedCode).css("background","#DEEFF8");
			}
			function turnUp(){
				var totalCount = parseInt($("#hiddenOfCount").val());
				if(totalCount==0){
					window.wxc.xcConfirm("当前无记录，无法到上一页.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					pageNumber--;
					if(pageNumber<1){
						pageNumber = 1;
						window.wxc.xcConfirm("已到最前面一页.",window.wxc.xcConfirm.typeEnum.info);
					}else{
						initRuleClass();
					}
				}
			}
			function turnDown(){
				var totalCount = parseInt($("#hiddenOfCount").val());
				var totalContainer = pageNumber*pageContext;
				if(totalContainer>=totalCount){
					window.wxc.xcConfirm("已到最后一页.",window.wxc.xcConfirm.typeEnum.info);
				}else{
					pageNumber++;
					initRuleClass();	
				}
			}
			function doSearchClass(){
				pageNumber = 1;
				initRuleClass();
			}
			var countMap = new HashMap();
			function initRuleClass(){
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/findClassCount.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"className="+$("#classNameKey").val()+"&pageNumber="+pageNumber+"&pageContext="+pageContext,
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#hiddenOfCount").val(textStatus["totalCount"]);
							var buckHtml = "";
							var items = textStatus["items"];
							if(items!=null && items.length>0){
								for(var i=0;i<items.length;i++){
									var $item = items[i];
									var nowDate = new Date();
									var $keys = nowDate.getTime()+i;
									countMap.put($keys,$item);
									if(i==0){
										buckHtml+="<div class='buckClass' id='"+$keys+"' onclick='javascript:flushClicked(this.id);' style='width:96%;height:95px;margin-left:5px;border-bottom:dotted 2px lightblue;margin-top:10px;'>";	
									}else{
										buckHtml+="<div class='buckClass' id='"+$keys+"' onclick='javascript:flushClicked(this.id);' style='width:96%;height:95px;margin-left:5px;border-bottom:dotted 2px lightblue;'>";
									}									
									buckHtml+="	   <div style='float:left;width:60px;height:60px;padding-top:25px;padding-left:30px;'>";
									buckHtml+="    	    <img src='${pageContext.request.contextPath}/img/icon/abiword.ico' style='height:60px;width:60px;'></img>";
									buckHtml+="	   </div>";
									buckHtml+="    <div style='float:left;width:200px;height:60px;padding-top:25px;padding-left:20px;'>";
									buckHtml+="		    <div style='width:390px;height:20px;padding-top:5px;'>";
									buckHtml+="				<font style='font-weight:bold;'>"+$item["CLASS_NAME"]+"</font>";
									buckHtml+="			</div>";
									buckHtml+="			<div style='width:390px;height:20px;padding-top:5px;'>";
									buckHtml+="				<font>规则数：</font><font color='blue'>"+$item["RULE_NUMBER"]+"</font>";
									buckHtml+="			</div>";
									buckHtml+="	   </div>";
									buckHtml+="    <div style='float:right;width:300px;height:60px;margin-top:15px;margin-right:50px;'>";
									buckHtml+="	   		<div style='float:right;margin-top:39px;width:300px;height:20px;'>";
									buckHtml+="				<div style='float:right;'>";
									buckHtml+="					<img src='${pageContext.request.contextPath}/img/icon/gnumeric.ico' style='height:20px;width:20px;'></img>";
									buckHtml+="					<a style='margin-left:3px' href='#itemsModal' role='button' onclick='javascript:ruleItems("+$keys+");' data-toggle='modal' style='cursor:pointer'>规则列表</a>";
									buckHtml+="					<img src='${pageContext.request.contextPath}/img/icon/dashcode.ico' style='height:20px;width:20px;margin-left:15px;'></img>";
									buckHtml+="					<a style='margin-left:3px;cursor:pointer;' onclick='javascript:turnToBarChart("+$keys+");' role='button' data-toggle='modal'>核查结果</a>";
									buckHtml+="					<img src='${pageContext.request.contextPath}/img/icon/codeblocks.ico' style='height:20px;width:20px;margin-left:15px;'></img>";
									buckHtml+="					<a onclick='javascript:turnToTeamShow();' style='margin-left:3px;cursor:pointer'>分组总览</a>";
									buckHtml+="				</div>";
									buckHtml+="			</div>";
									buckHtml+="    </div>";
									buckHtml+="</div>";								
								}
								buckHtml+="<div style='width:96%;height:30px;margin-left:5px;border:solid 1px #FFF;margin-top:10px;'>";
								buckHtml+="	  <center>";
								buckHtml+="    	    <img src='${pageContext.request.contextPath}/img/icon/icon-arrow-left.png' style='height:25px;width:25px;cursor:pointer;' title='上一页' onclick='javascript:turnUp();'></img>";
								buckHtml+="    	    <img src='${pageContext.request.contextPath}/img/icon/icon-arrow-right.png' style='height:25px;width:25px;cursor:pointer;margin-left:30px;' onclick='javascript:turnDown();' title='下一页'></img>";
								buckHtml+="	  		<font color='green' style='margin-left:20px;margin-top:5px;'>共发现"+textStatus["totalCount"]+"个分类结果</font>";
								buckHtml+="	  </center>";
								buckHtml+="</div>";
							}else{
								buckHtml+="<div style='width:96%;margin-left:5px;height:236px;'><center><div style='width:40%;height:30px;margin-top:160px;'><center><font color='blue' size='2'>▲ 根据查询条件未找到匹配的规则分类</font></center></div></center></div>";
								buckHtml+="<div style='width:96%;margin-left:5px;height:30px;border:solid 1px #FFF;margin-top:10px;'>";
								buckHtml+="	  <center>";
								buckHtml+="    	    <img src='${pageContext.request.contextPath}/img/icon/icon-arrow-left.png' style='height:25px;width:25px;cursor:pointer;' title='上一页' onclick='javascript:turnUp();'></img>";
								buckHtml+="    	    <img src='${pageContext.request.contextPath}/img/icon/icon-arrow-right.png' style='height:25px;width:25px;cursor:pointer;margin-left:30px;' onclick='javascript:turnDown();' title='下一页'></img>";
								buckHtml+="	  </center>";
								buckHtml+="</div>";
							}					
							document.getElementById("ruleClassBuck").innerHTML = buckHtml;
						}
					},
					error:function(){}
				});
			}
			function ruleItems(className){
				var countObject = countMap.get(className);
				$.ajax({
					url:"${pageContext.request.contextPath}/ruleEditAction/findRuleOfClass.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"className="+countObject["CLASS_NAME"],
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							var tableHtml = "";							
							tableHtml+="<table class='table table-bordered table-hover'>";
							tableHtml+="	<tbody>";
							tableHtml+="		<tr height='25'>";
							tableHtml+="			<td width='40%' style='background:#DEEFF8;'>规则名称</td>";
							tableHtml+="			<td width='60%' style='background:#DEEFF8;'>规则描述</td>";
							tableHtml+="		</tr>";							
							var items = textStatus["items"];
							if(items!=null && items.length>0){
								for(var i=0;i<items.length;i++){
									var $item = items[i];
									tableHtml+="<tr height='25'>";
										tableHtml+="<td>"+$item["RULE_NAME"]+"</td>";
										tableHtml+="<td>"+$item["RULE_DESC"]+"</td>";
									tableHtml+="</tr>";			
								}
							}else{
								tableHtml+="<tr height='25'><td colspan='2'>此类别不包含任何规则</td></tr>";
							}						
							tableHtml+="	</tbody>";
							tableHtml+="</table>";						
							document.getElementById("ruleItemBrown").innerHTML = tableHtml;
						}
					},
					error:function(){}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" value="-1" id="hiddenOfTableId"></input>
  		<input type="hidden" value="-1" id="hiddenOfCount"></input>
  		<input type="hidden" value="-1" id="HIDDEN_OF_LINKURL"></input>
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
		<div class="panel panel-default" style="height:780px;border:solid 1px #FFF;" class="models" id="mainPanel">
			<div class="panel-heading" id="panelHeading">
				<span class="panel-label"></span>规则管理 >>规则查询
			</div>
			<%--规则视图--%>
			<div class="panel-body" style="width:100%;border:0px;" id="ruleConfigDiv">
				<div style="width:99.8%;overflow:auto;border:solid 1px #FFF;" id="ruleEditsBuck">
					<div style="float:left;width:25%;height:440px;margin-top:4px;margin-left:3px;" id="leftTreePanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="leftTreeHead">
							<span class="panel-label"></span><input type="text" id="MODEL_NAME_INPUT" style="height:29px;width:200px;border:solid 1px #A3D0E3;margin-top:5px;" placeholder="请输入模型名称"></input>
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
					<div style="float:left;width:74%;margin-top:4px;margin-left:4px;" id="rightDataPanel">
						<div class="panel-heading" style="border:solid 1px #A3D0E3;border-bottom:0px;" id="ruleTablePanel">
							<span class="panel-label"></span>规则结果查询
							<form class="form-search pull-right">	
								<div style="float:right;">
									<input type="text" placeholder="请输入规则名称" style="width:160px;height:29px;font-size:12px;border:solid 1px #A3D0E3;border-radius:6px;" id="ruleNameKey"></input>
									<img src="${pageContext.request.contextPath}/img/icon/icon-Search.png" style='height:22px;width:22px;cursor:pointer;margin-left:5px;margin-right:5px;' onclick="javascript:searchData();"></img>
									<a class="btn btn-info" style="cursor:pointer" onclick="javascript:turnToLineChart();">
										<i class="icon-signal icon-white"></i>历史分析
									</a>
									<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:fireDataOperateJob();">
										<i class="icon-book icon-white"></i>发起数据处理流程
									</a>
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
													<th>&nbsp;</th>
													<th>规则名称</th>
													<th>版本</th>
													<th>规则类型</th>
													<th>最近一次执行时间</th>
													<th>问题数据数量</th>
													<th>创建时间</th>
													<th>创建人</th>
													<th>规则等级</th>
													<th>核查报告</th>
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
			<%--任务视图--%>
			<div class="panel-body" style="border:0px;display:none;" id="ruleShowDiv">
				<div style="width:99.8%;height:735px;overflow:auto;border:solid 1px #FFF;" id="ruleClassBuck">&nbsp;</div>
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
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
	String cityName = java.net.URLDecoder.decode(request.getParameter("cityName"),"UTF-8");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>场租续签明细</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<style type="text/css">
			.MAIN_PANEL{
				border-radius:0px;
				border:solid 1px #57BEDD;
				box-shadow:0px 0px 3px #57BEDD;
				position:absolute;
				background:#FFF;
			}
			.PANEL_HEADING{
				width:100%;
				height:40px;
				color:white;
				border-bottom:solid 1px #57BEDD;
				background:#57BEDD;
			}
			.PANEL_BODY{
				width:100%;
				overflow:auto;
			}
			.PANEL_FOOT{
				width:100%;
				height:45px;
				border-top:dotted 1px #F0F0F0;
				position:absolute;
				left:0;
				bottom:0;
			}
		</style>
		<script type="text/javascript">
			var cTable = null;
			var cConditions = null;
			var acheMaps = null;
			$(document).ready(function(){
				flushData();
				cConditions = [{
					name:"CONTRACT_ZH_LABEL",
					value:-1
				}];
				cTable = $("#contractTable").dataTable({
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
					"sScrollY":"255px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"CONTRACT_CODE",
						"sWidth":"70px"
					},{
						"mData":"CONTRACT_CODE",
						"sWidth":"190px"
					},{
						"mData":"CONTRACT_START_TIME",
						"sWidth":"140px"
					},{
						"mData":"SITE_CODE",
						"sWidth":"180px"
					},{
						"mData":"SITE_NAME",
						"sWidth":"240px"
					},{
						"mData":"CONTRACT_STATUS",
						"sWidth":"80px"
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":9,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(acheMaps==null){
							acheMaps = new HashMap();
						}
						acheMaps.put(aData["CONTRACT_CODE"],aData)
						$("td:eq(0)",nRow).html("<center><input type='checkbox' name='contractchecks' value='"+aData["CONTRACT_CODE"]+"' onchange='javascript:uniqueCheckAndInit(this.checked,this.name,this.value);'></input></center>");	
						$("td:eq(1)",nRow).html("<font color='red'>"+aData["CONTRACT_CODE"]+"</font>");	
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						if(acheMaps!=null){
							acheMaps = null;
						}
						$.ajax({
							url:sSource,
							type:"POST",
							data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(cConditions),
							dataType:"json",		
							success:function(data){
								fnCallback(data);
							}
						});
					},
					"sAjaxSource":"${pageContext.request.contextPath}/dealExpireAlarm/findContractsMaybe.ilf"
				});
			});
			function uniqueCheckAndInit(isChecked,checkboxName,checkedValue){
				if(isChecked){
					var checkboxes = document.getElementsByName(checkboxName);
					if(checkboxes!=null && checkboxes.length>0){
						for(var i=0;i<checkboxes.length;i++){
							if(checkboxes[i].value==checkedValue){
								checkboxes[i].checked = true;
							}else{
								checkboxes[i].checked = false;
							}
						}
					}
					$("#CONTRACT_CODE_INPUT").val(checkedValue);
				}else{
					$("#CONTRACT_CODE_INPUT").val("");
				}
			}
			function flushData(){
				window.parent.showLoading(45,47);
				$.ajax({
					url:"${pageContext.request.contextPath}/dealExpireAlarm/findDetails.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					data:"DEAL_TYPE="+$("#DEAL_TYPE_SELECT").val()+"&CITY_NAME="+$("#HIDDEN_OF_CITYNAME").val()+"&SITE_CODE="+$("#SITE_CODE_INPUT").val(),
					success:function(textStatus){
						window.parent.hideLoading();
						if(textStatus["success"]){
							var datas = textStatus["DATAS"];
							$("#panelHeadings").html(">> 场租续签明细<font color='red'>（共发现"+datas.length+"条记录）</font>");
							if(datas.length>0){
								var lineHtml = "";
								for(var j=0;j<datas.length;j++){
									var dataObj = datas[j];
									var CityName = dataObj["地市"];
									if(CityName!=null && CityName!=""){
										if(CityName=="凉山州"){
											CityName = "凉山";
										}else if(CityName=="甘孜州"){
											CityName = "甘孜";
										}else if(CityName=="阿坝州"){
											CityName = "阿坝";
										}
										lineHtml+="<tr>";
										lineHtml+="	  <td>"+CityName+"</td>";
										lineHtml+="	  <td>"+(dataObj["区县"]==null?"&nbsp;":dataObj["区县"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["站址编码"]==null?"&nbsp;":dataObj["站址编码"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["站址名称"]==null?"&nbsp;":dataObj["站址名称"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["合同编码"]==null?"&nbsp;":dataObj["合同编码"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["合同名称"]==null?"&nbsp;":dataObj["合同名称"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["合同类型"]==null?"&nbsp;":dataObj["合同类型"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["合同金额"]==null?"&nbsp;":dataObj["合同金额"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["开始日期"]==null?"&nbsp;":dataObj["开始日期"])+"</td>";
										lineHtml+="	  <td>"+(dataObj["结束日期"]==null?"&nbsp;":dataObj["结束日期"])+"</td>";
										lineHtml+="	  <td>";
										lineHtml+="		  <a class='btn btn-warning' style='cursor:pointer;margin-top:5px;' onclick='javascript:replyMessage(\""+dataObj["ZH_LABEL"]+"\");'>";
										lineHtml+="			  <i class='icon-upload icon-white'></i>反馈信息";
										lineHtml+="		  </a>";
										lineHtml+="		  <a class='btn btn-info' style='cursor:pointer;margin-top:5px;' onclick='javascript:viewHaveReply(\""+dataObj["ZH_LABEL"]+"\");'>";
										lineHtml+="			  <i class='icon-flag icon-white'></i>沟通记录";
										lineHtml+="		  </a>";
										lineHtml+="		  <a class='btn btn-success' style='cursor:pointer;margin-top:5px;' onclick='javascript:flushContractCodeWindow(\""+dataObj["ZH_LABEL"]+"\");'>";
										lineHtml+="			  <i class='icon-ok icon-white'></i>完成续签";
										lineHtml+="		  </a>";
										lineHtml+="	  </td>";
										lineHtml+="</tr>";	
									}
								}
								document.getElementById("COUNTS_TABLE").innerHTML = lineHtml;	
							}
						}
					},
					error:function(){
						window.parent.hideLoading();
					}
				});
			}
			function replyMessage(contractCode){
				$("#HIDDEN_OF_CITY").val(contractCode);
				$("#checkDetails").dialog({
					title:"信息反馈",
					show:{
						effect:"slide",
						duration:200
					},
					hide:{
				        effect:"slide",
				        duration:200
					},
					width:600,
					height:250,
					autoOpen:true,
					closeOnEscape:true,
					draggable:false,
					resizable:false,
					modal:true,
					buttons:{
						"取消":function(){
							$(this).dialog("close");
						},
		  				"确定":function(){
		  					var resultObj = new Object();
		  					var REPLY_MESSAGE = $("#REPLY_MESSAGE_INPUT").val();
		  					if(REPLY_MESSAGE==""){
		  						window.wxc.xcConfirm("反馈信息不能为空.",window.wxc.xcConfirm.typeEnum.info);
		  						return;
		  					}else{
		  						resultObj["CONTRACT_CODE"] = $("#HIDDEN_OF_CITY").val();
		  						resultObj["REPLY_MESSAGE"] = REPLY_MESSAGE;
		  					}
		  					window.wxc.xcConfirm("是否确定反馈此信息？","custom",{
		  						title:"提示",
		  						btn:parseInt("0011",2),
		  						onOk:function(){
		  							$.ajax({
		  								url:"${pageContext.request.contextPath}/dealExpireAlarm/replyMessage.ilf",
		  								async:false,
		  								type:"POST",
		  								data:"params="+JSON.stringify(resultObj),
		  								dataType:"json",
		  								timeout:10000, 
		  								success:function(textStatus){
		  									window.wxc.xcConfirm("信息反馈成功.",window.wxc.xcConfirm.typeEnum.info);
		  									document.getElementById("editForms").reset();
		  									$("#checkDetails").dialog("close");
		  								},
		  								error:function(){
		  									window.wxc.xcConfirm("信息反馈失败.",window.wxc.xcConfirm.typeEnum.error);
		  								}
		  							});
		  						}
		  					});
						}
		  			}
				});
			}
			function viewHaveReply(contractCode){
				var resultObj = new Object();
				resultObj["CONTRACT_CODE"] = contractCode;
				$.ajax({
					url:"${pageContext.request.contextPath}/dealExpireAlarm/haveReplyMessages.ilf",
					async:false,
					type:"POST",
					data:"params="+JSON.stringify(resultObj),
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							var messages = textStatus["MESSAGES"];
							var lineHtml = "";
							if(messages.length>0){
								for(var j=0;j<messages.length;j++){
									var dataObj = messages[j];
									var date = new Date();
									date.setTime(dataObj["REPLY_DATE"]["time"]);
									lineHtml+="<tr>";
									lineHtml+="	  <td style='text-align:right;background:#CCC'>"+dataObj["REPLY_USER"]+"：</td>";
									lineHtml+="	  <td>"+dataObj["REPLY_MESSAGE"]+"</td>";
									lineHtml+="	  <td>"+date.toLocaleString()+"</td>";
									lineHtml+="</tr>";
								}	
							}else{
								lineHtml+="<tr>";
								lineHtml+="	  <td colspan='3' style='text-align:center;'><font color='red'>暂无已反馈信息</font></td>";
								lineHtml+="</tr>";
							}
							document.getElementById("LOGS_TABLE").innerHTML = lineHtml;
							$("#logDetails").dialog({
								title:"已反馈信息",
								show:{
									effect:"slide",
									duration:200
								},
								hide:{
							        effect:"slide",
							        duration:200
								},
								width:600,
								height:350,
								autoOpen:true,
								closeOnEscape:true,
								draggable:false,
								resizable:false,
								modal:true,
								buttons:{
					  				"确定":function(){
					  					$(this).dialog("close");
									}
					  			}
							});
						}
					},
					error:function(){
						window.wxc.xcConfirm("信息反馈失败.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function linkToDetails(cityName){
				window.parent.addTabForDealExpire(cityName);
			}
			function downloadData(){
				window.wxc.xcConfirm("是否确认导出工单数据？.","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var CityName = $("#HIDDEN_OF_CITYNAME").val();
						var SiteCode = $("#SITE_CODE_INPUT").val();
						var exportForm = $("<form>");
					    exportForm.attr("style","display:none");
					    exportForm.attr("target","");
					    exportForm.attr("method","post");
					    exportForm.attr("action","${pageContext.request.contextPath}/dealExpireAlarm/exportDetails.ilf?siteCode="+encodeURIComponent(encodeURIComponent(SiteCode,"UTF-8"),"UTF-8")+"&cityName="+encodeURIComponent(encodeURIComponent(CityName,"UTF-8"),"UTF-8"));
					    $("body").append(exportForm);
					    exportForm.submit();
					    exportForm.remove();
					}
				});
			}
			function flushContractCodeWindow(zhLabel){
				var isAtransferZ = getCheckedValue("IS_A_TRANSFER_Z");
				if(isAtransferZ!=null && isAtransferZ!=""){
					window.wxc.xcConfirm("是否确定此合同无相关续签？","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/dealExpireAlarm/noExtendSingal.ilf",
								async:false,
								type:"POST",
								data:"ZH_LABEL="+zhLabel,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["SUCCESS"]){
										unCheckAllBoxes("IS_A_TRANSFER_Z");
										window.wxc.xcConfirm("无续签合同记录成功.",window.wxc.xcConfirm.typeEnum.info);
										flushData();
									}else{
										window.wxc.xcConfirm("无续签合同记录失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("无续签合同记录失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});	
						}
					});
				}else{
					$("#hiddenOfOldCode").val(zhLabel);
					cConditions = [{
						name:"CONTRACT_ZH_LABEL",
						value:zhLabel
					}];
					cTable.fnDraw();
					$("#MASK_DIV").show();
					$("#EDIT_PANEL").slideDown(350);	
				}
			}
			function canelContractCodeEdit(){
				$("#CONTRACT_CODE_INPUT").val("");
				$("#MASK_DIV").hide();
				$("#EDIT_PANEL").slideUp(350);
			}
			function saveMyEdit(){
				var newContractCode = $("#CONTRACT_CODE_INPUT").val();
				if(newContractCode!=null && newContractCode!=""){
					window.wxc.xcConfirm("是否确定保存此续签关系？","custom",{
						title:"警告",
						btn:parseInt("0011",2),
						onOk:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/dealExpireAlarm/saveContractConnect.ilf",
								async:false,
								type:"POST",
								data:"ZH_LABEL="+$("#hiddenOfOldCode").val()+"&NEW_CONTRACT_CODE="+newContractCode,
								dataType:"json",
								timeout:10000, 
								success:function(textStatus){
									if(textStatus["SUCCESS"]){
										canelContractCodeEdit();
										window.wxc.xcConfirm("续签成功.",window.wxc.xcConfirm.typeEnum.info);
										flushData();
									}else{
										window.wxc.xcConfirm("续签失败.",window.wxc.xcConfirm.typeEnum.error);
									}
								},
								error:function(){
									window.wxc.xcConfirm("锁定账户失败.",window.wxc.xcConfirm.typeEnum.error);
								}
							});	
						}
					});
				}else{
					window.wxc.xcConfirm("请先选择或输入一个续签合同编号.",window.wxc.xcConfirm.typeEnum.error);
				}
			}
		</script>
  	</head>
  	<body style="width:99.6%;border:solid 1px #FFF;height:460px;" id="bodyHeight">
  		<input type="hidden" id="HIDDEN_OF_CITYNAME" value="<%=cityName %>"></input>
		<div class="panel panel-default" style="margin-top:-20px;height:460px;border:solid 1px #FFF;" id="mainPanel">
			<div class="panel-heading" id="panelHeading" style="border:solid 1px #DFE8F1;"><font id="panelHeadings">>> 场租续签明细</font>
				<form class="form-search pull-right">	
					<div style="float:right;">
						<div style="float:left;width:135px;height:27px;border:solid 1px #DFE8F1;margin-top:6px;margin-right:5px;">
							<div style="float:left;margin-top:7px;margin-left:5px;"><input type="checkbox" value="Y" name="IS_A_TRANSFER_Z"/></div>
							<div style="float:left;margin-left:10px;margin-top:-6px;">转改直合同无续签</div>
						</div>
						<div style="float:left;">
							<select id="DEAL_TYPE_SELECT" style="width:200px;height:29px;border:solid 1px #DFE8F1;margin-top:6px;margin-right:3px;">
								<option value="-">非代持合同</option>
								<option value="DCHT">代持合同</option>
							</select>
						</div>
						<div style="float:left;">
							<input id="SITE_CODE_INPUT" type="text" placeholder="请输入站址编码" style="border:solid 1px #DFE8F1;width:190px;height:29px;font-size:12px;"></input>
							<a class="btn btn-success" style="cursor:pointer;" onclick="javascript:flushData();">
								<i class="icon-search icon-white"></i>执行查询
							</a>
							<a class="btn btn-primary" style="cursor:pointer;" onclick="javascript:downloadData();">
								<i class="icon-download-alt icon-white"></i>数据导出
							</a>
						</div>
					</div>
				</form>
			</div>
			<div class="panel-body" style="border:0px;height:410px;overflow:auto;border:solid 1px #DFE8F1;border-top:0px;">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto;">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<td style="background:#6BC2EB;">地市</td>
										<td style="background:#6BC2EB;">区县</td>
										<td style="background:#6BC2EB;">站址编码</td>
										<td style="background:#6BC2EB;">站址名称</td>
										<td style="background:#6BC2EB;">合同编码</td>
										<td style="background:#6BC2EB;">合同名称</td>
										<td style="background:#6BC2EB;">合同类型</td>
										<td style="background:#6BC2EB;">合同金额</td>
										<td style="background:#6BC2EB;">开始日期</td>
										<td style="background:#6BC2EB;">到期日期</td>
										<td style="background:#6BC2EB;">操作</td>
									</tr>
								</thead>
								<tbody id="COUNTS_TABLE">
									<tr>
										<td colspan="10">&nbsp;</td>
									</tr>
								</tbody>
							</table>								
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--窗口表单：开始-->
		<div id="checkDetails" style="display:none;height:125px;">
			<div style="border:solid 0px #54A8E1;margin-top:5px">
				<center>
					<form id="editForms"> 
						<table cellpadding="0" cellspacing="0" style="width:550px;font-size:12px;margin-top:5px;">
							<tr height="40">
								<td style="text-align:right;width:100%">
									<input type="hidden" id="HIDDEN_OF_CITY" value=""></input>
									<textarea id="REPLY_MESSAGE_INPUT" style="width:100%;height:140px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;resize:none;"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
		</div>
		<!--窗口表单：结束-->
		<!--窗口历史：开始-->
		<div id="logDetails" style="display:none;height:300px;">
			<div style="border:solid 0px #54A8E1;height:255px;overflow:auto;">
				<center>
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<td style="background:#6BC2EB;">反馈人</td>
								<td style="background:#6BC2EB;">详情</td>
								<td style="background:#6BC2EB;">反馈时间</td>
							</tr>
						</thead>
						<tbody id="LOGS_TABLE">
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
						</tbody>
					</table>		
				</center>
			</div>
		</div>
		<!--窗口历史：结束-->
		<%--编辑窗口--%>
		<div id="MASK_DIV" style="display:none;width:100%;height:500px;position:absolute;top:0px;left:0px;background-color:#FFF;opacity:0.7;"></div>
  		<div id="EDIT_PANEL" class="MAIN_PANEL" style="width:700px;height:410px;top:20px;right:22%;display:none;">
  			<div class="PANEL_HEADING">
  				<div style="float:left;margin-top:10px;margin-left:10px;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/rightarrow.png" style="width:15px;height:15px;margin-top:3px;"/>	
  				</div>
  				<div style="float:left;margin-top:10px;font-size:12px;font-family:'Raleway';">&nbsp;&nbsp;疑似续签合同清单</div>
  				<img onclick="javascript:canelContractCodeEdit();" src="${pageContext.request.contextPath}/extensions/form/image/close.png" style="float:right;width:17px;height:17px;margin-top:11px;margin-right:10px;cursor:pointer;"/>
  			</div>
  			<div class="PANEL_BODY" style="height:325px;background:#FEFEFE;border:solid 0px red;">
  				<input type="hidden" id="hiddenOfOldCode" value=""></input>
				<table cellpadding="0" cellspacing="0" border="0" id="contractTable">
					<thead>
						<tr height="32">
							<th>&nbsp;</th>
							<th><center><font color="black" style="margin-left:10px;">合同编号</font></center></th>
							<th><center><font color="black" style="margin-left:10px;">合同开始时间</font></center></th>
							<th><center><font color="black" style="margin-left:10px;">站址名称</font></center></th>
							<th><center><font color="black" style="margin-left:10px;">站址编码</font></center></th>
							<th><center><font color="black" style="margin-left:10px;">状态</font></center></th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
  			</div>
  			<div class="PANEL_FOOT" style="background:#FEFEFE;">
  				<input type="text" id="CONTRACT_CODE_INPUT" style="float:left;cursor:pointer;width:230px;height:27px;border:solid 1px #57BEDD;margin-top:7px;margin-left:10px;"></input>
 				<div onclick="javascript:saveMyEdit();" style="float:left;width:115px;height:27px;color:white;background:#57BEDD;font-size:12px;border-top-right-radius:5px;border-bottom-right-radius:5px;cursor:pointer;margin-top:7px;box-shadow:0 0 0px #57BEDD;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/submit.png" style="float:left;width:23px;height:13px;margin-top:7px;margin-left:7px;"/>
  					<div style="float:left;margin-left:2px;">
  						<center style="margin-top:3px;">提交续签信息</center>
  					</div>
  				</div>
  				<div onclick="javascript:canelContractCodeEdit();" style="float:left;width:75px;height:27px;color:white;background:#FF5500;font-size:12px;border-radius:5px;cursor:pointer;margin-top:7px;margin-left:8px;box-shadow:0 0 0px #FF5500;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/canel.png" style="float:left;width:14px;height:14px;margin-top:6px;margin-left:12px;"/>
  					<div style="float:left;margin-left:6px;">
  						<center style="margin-top:3px;">取消</center>
  					</div>
  				</div>
  			</div>
  		</div>
	</body>
</html>
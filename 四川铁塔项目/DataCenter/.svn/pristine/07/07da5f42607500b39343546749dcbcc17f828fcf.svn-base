<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>站址可视化</title>
		<%@ include file="/jsp/firstlink/head.jsp" %>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/firstlink/site.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
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
			var contextPath = '${pageContext.request.contextPath}';
			var oldCircle = null;
			var oldMarker = null;
			var zoomValue = 17;
			var markerArray = new Array();//停电站点
			var takebackArray = new Array();//退服站点
			var normalArray = new Array();//正常站点
            var map = null;
            var isSearch = false;
            var acheMap = null;
            var acheServiceMap = null;//初始化放入退服站站点用
			var cTable = null;
			var cConditions = null;
			var cMaps = null;
            $(document).ready(function () {
                site.init();
                cConditions = [{
					name:"DATA_TYPE",
					value:""
				},{
					name:"SITE_CODE_OR_NAME",
					value:$("#SITE_CODE_OR_NAME_INPUT").val()
				},{
					name:"CITY",
					value:""
				},{
					name:"REGION",
					value:""
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
					"sScrollY":"210px",
					"sScrollX":"100%",
					"aoColumns":[{
						"mData":"SITE_CODE",
						"sWidth":"170px"
					},{
						"mData":"SITE_NAME",
						"sWidth":"210px"
					},{
						"mData":"SITE_STATE",
						"sWidth":"80px"
					},{
						"mData":"SITE_CODE",
						"sWidth":"110px"
					},{
						"mData":"LONGITUDE",
						"bVisible":false
					},{
						"mData":"LATITUDE",
						"bVisible":false
					}],
					"aoColumnDefs":[] ,
					"iDisplayLength":5,
					"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
					"aaSorting":[[0,"desc"]],
					"fnRowCallback":function(nRow,aData,iDisplayIndex){
						if(cMaps==null){
							cMaps = new HashMap();
						}
						cMaps.put(aData["SITE_CODE"],aData);
						$("td:eq(2)",nRow).html("<font color='red'>"+aData["SITE_STATE"]+"</font>");
						var innerHtml = "";
						innerHtml+="<div onclick='javascript:locateAssignedSite(\""+aData["SITE_CODE"]+"\");' style='float:left;width:70px;height:27px;color:white;background:#57BEDD;font-size:12px;border-radius:5px;cursor:pointer;margin-top:7px;box-shadow:0 0 0px #57BEDD;'>";
						innerHtml+="	<img src='${pageContext.request.contextPath}/extensions/form/image/submit.png' style='float:left;width:23px;height:13px;margin-top:7px;margin-left:7px;'/>";
						innerHtml+="	<div style='float:left;margin-left:7px;'>";
						innerHtml+="		<center style='margin-top:5px;'>定位</center>";
						innerHtml+="	</div>";
						innerHtml+="</div>";
						$("td:eq(3)",nRow).html(innerHtml);
					},
					"fnServerData":function(sSource,aoData,fnCallback){
						if(cMaps!=null){
							cMaps = null;
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
					"sAjaxSource":"${pageContext.request.contextPath}/sitevisibleAction/findAlarmDetailsPage.ilf"
				});
            });
            function locateAssignedSite(siteCode){
            	if(cMaps!=null){
            		var siteMap = cMaps.get(siteCode);
            		if(siteMap["LONGITUDE"]!=null && siteMap["LATITUDE"]!=null){
            			map.clearOverlays();
            			var myIcon = null;
            			if($("#HIDDEN_OF_ALARM_TYPE").val()=="停电"){
            				myIcon = new BMap.Icon(
                                "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                                new BMap.Size(25,25)
                            );	
            			}else if($("#HIDDEN_OF_ALARM_TYPE").val()=="退服"){
            				myIcon = new BMap.Icon(
                                "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                                new BMap.Size(25,25)
                            );
            			}
            			var sitePoint = new BMap.Point(
							parseFloat(siteMap["LONGITUDE"]),
							parseFloat(siteMap["LATITUDE"])
                        );
            			var marker = new BMap.Marker(sitePoint,{icon:myIcon});
            			marker.id = siteMap["SITE_CODE"];
            			marker.setLabel(new BMap.Label(siteMap.SITE_NAME,{
                            offset:new BMap.Size(20,0)
                        }));
            			map.addOverlay(marker);
            			initSiteInfoForSingle(marker);
            			map.centerAndZoom(sitePoint,17);
            			$("#mapInfo").val(17);
            			hideEditWindow();
            		}
            	}
            }
            function initSiteInfoForSingle(marker){
            	marker.addEventListener("click",function(e){
                    var id = marker.id;
                    $.ajax({
                        type:"POST",
                        url:contextPath+"/sitevisibleAction/getSingleStopServiceSite.ilf",
                        dataType:"JSON",
                        data:"SITE_CODE="+id+"&ALARM_TYPE="+$("#HIDDEN_OF_ALARM_TYPE").val(),
                        async:true,
                        success:function(resultInfo){
                            if(resultInfo["SUCCESS"]){
                                var ache = resultInfo["THIS_SITE"];
                                $("#isViewed").val("Y");
                                $("#TD_REGION_NAME").html(ache["区县"]==null?"-":ache["区县"]);
                                $("#TD_SITE_CODE").html(ache["SITE_CODE"]==null?"-":ache["SITE_CODE"]);
                                $("#TD_SITE_NAME").html(ache["SITE_NAME"]==null?"-":ache["SITE_NAME"]);
                                $("#TD_LONGGITUDE_LATITUDE").html(ache["经纬度"]==null?"-":ache["经纬度"]);
                                $("#TD_SITE_TYPE").html(ache["SITE_TYPE"]==null?"-":ache["SITE_TYPE"]);
                                $("#TD_PROP_CHAR").html(ache["原产权单位"]==null?"-":ache["原产权单位"]);
                                $("#TD_BUSINESS_SCENE").html(ache["业务场景"]==null?"-":ache["业务场景"]);
                                $("#TD_SHARE_UNIT").html(ache["共享单位"]==null?"-":ache["共享单位"]);
                                $("#TD_TOWER").html(ache["铁塔数量"]==null?"-":ache["铁塔数量"]);
                                $("#TD_ROOM").html(ache["机房数量"]==null?"-":ache["机房数量"]);
                                $("#TD_SWITCH").html(ache["开关电源数量"]==null?"-":ache["开关电源数量"]);
                                $("#TD_STORAGE").html(ache["蓄电池组数量"]==null?"-":ache["蓄电池组数量"]);
                                $("#TD_AIR").html(ache["空调数量"]==null?"-":ache["空调数量"]);
                                $("#TD_ENVIR").html(ache["动环数量"]==null?"-":ache["动环数量"]);
                                if(ache["运行状态"]==null){
                                    $("#TD_STATE").html("-");
                                }else if(ache["运行状态"]=="正常"){
                                    $("#TD_STATE").html("正常");
                                }else if(ache["运行状态"]=="已停电"){
                                    $("#TD_STATE").html("<font color='yellow'>已停电</font>");
                                }else if(ache["运行状态"]=="已退服"){
                                    $("#TD_STATE").html("<font color='red'>已退服</font>");
                                }
                                $("#TD_RUN_STATE").html(ache["站址运营状态"]==null?"-":ache["站址运营状态"]);
                                $("#TD_TZ_ICB_TOTAL").html(ache["投资合计"]==null?"-":ache["投资合计"]);
                                $("#TD_TOI_CUMULATIVE_TOTAL").html(ache["经营收入"]==null?"-":ache["经营收入"]);
                                $("#TD_TOI_TOWER_RENT_Y").html(ache["塔租收入"]==null?"-":ache["塔租收入"]);
                                $("#TD_TOI_MAINTAIN_FEE_Y").html(ache["维护费收入"]==null?"-":ache["维护费收入"]);
                                $("#TD_TOI_RENTAL_COSTS_Y").html(ache["场租收入"]==null?"-":ache["场租收入"]);
                                $("#TD_TOI_POWER_INTRODUCE_Y").html(ache["电力引入收入"]==null?"-":ache["电力引入收入"]);
                                $("#TD_TOI_OIL_SERVICE_Y").html(ache["油机发电收入"]==null?"-":ache["油机发电收入"]);
                                $("#TD_TOI_NEW_BUSINESS_Y").html(ache["新业务收入"]==null?"-":ache["新业务收入"]);
                                $("#TD_TOC_CUMULATIVE_TOTAL").html(ache["运营成本"]==null?"-":ache["运营成本"]);
                                $("#TD_TOC_MAINTAIN_Y").html(ache["维护成本"]==null?"-":ache["维护成本"]);
                                $("#TD_TOC_SITE_LEASING_Y").html(ache["场租成本"]==null?"-":ache["场租成本"]);
                                $("#TD_TOC_POWER_COST_Y").html(ache["电力成本"]==null?"-":ache["电力成本"]);
                                $("#TD_TOC_OIL_COST_Y").html(ache["油机发电成本"]==null?"-":ache["油机发电成本"]);
                                $("#TD_ZJ_DAD_CUMULATIVE_TOTAL").html(ache["折旧摊销"]==null?"-":ache["折旧摊销"]);
                                $("#TD_GL_ME_CUMULATIVE_TOTAL").html(ache["管理费用"]==null?"-":ache["管理费用"]);
                                $("#TD_FINANCE_COST_Y").html(ache["财务费用"]==null?"-":ache["财务费用"]);
                                $("#TD_SS_GROSS_PROFIT").html(ache["单站毛利"]==null?"-":ache["单站毛利"]);
                                $("#EDIT_PANEL").show(300);
                            }
                        },
                        error:function(){
                            
                        }
                    });
                });
            }
            function showEditWindow(){
            	$("#SITE_CODE_OR_NAME_INPUT").val("");
				$("#MASK_DIV").show();
				$("#ALARM_DETAIL_PANEL").slideDown(350);
			}
			function hideEditWindow(){
				$("#SITE_CODE_OR_NAME_INPUT").val("");
				$("#MASK_DIV").hide();
				$("#ALARM_DETAIL_PANEL").slideUp(350);
			}
			function searchDetailDataOfWindow(){
				cConditions = [{
					name:"DATA_TYPE",
					value:$("#HIDDEN_OF_ALARM_TYPE").val()
				},{
					name:"SITE_CODE_OR_NAME",
					value:$("#SITE_CODE_OR_NAME_INPUT").val()
				},{
					name:"CITY",
					value:$("#CITYS_OPTION").val()
				},{
					name:"REGION",
					value:$("#REGIONS_OPTION").val()
				}];
				cTable.fnDraw();
			}
			function showStopElecWindowAndFlushData(){
				$("#isViewed").val("N");
				$("#EDIT_PANEL").hide();
				$("#HIDDEN_OF_ALARM_TYPE").val("停电");
				$("#ALARM_DETAIL_PANEL_TITLE").html("<div style='margin-left:5px;margin-top:2px;'>停电站点详情</div>");
				$("#SITE_CODE_OR_NAME_INPUT").val("");
				$("#MASK_DIV").show();
				$("#ALARM_DETAIL_PANEL").slideDown(350);
				cConditions = [{
					name:"DATA_TYPE",
					value:"停电"
				},{
					name:"SITE_CODE_OR_NAME",
					value:$("#SITE_CODE_OR_NAME_INPUT").val()
				},{
					name:"CITY",
					value:$("#CITYS_OPTION").val()
				},{
					name:"REGION",
					value:$("#REGIONS_OPTION").val()
				}];
				cTable.fnDraw();
			}
			function showQuitServiceWindowAndFlushData(){
				$("#isViewed").val("N");
				$("#EDIT_PANEL").hide();
				$("#HIDDEN_OF_ALARM_TYPE").val("退服");
				$("#ALARM_DETAIL_PANEL_TITLE").html("<div style='margin-left:5px;margin-top:2px;'>退服站点详情</div>");
				$("#SITE_CODE_OR_NAME_INPUT").val("");
				$("#MASK_DIV").show();
				$("#ALARM_DETAIL_PANEL").slideDown(350);
				cConditions = [{
					name:"DATA_TYPE",
					value:"退服"
				},{
					name:"SITE_CODE_OR_NAME",
					value:$("#SITE_CODE_OR_NAME_INPUT").val()
				},{
					name:"CITY",
					value:$("#CITYS_OPTION").val()
				},{
					name:"REGION",
					value:$("#REGIONS_OPTION").val()
				}];
				cTable.fnDraw();
			}
		</script>
	</head>
	<body>
		<div id="r-result" class="panel-heading" style="border:solid 1px #DFE8F1;height:35px;margin-left:3px;margin-right:3px;margin-top:2px;border-radius:3px;">
			<div style="float:left;width:30px;height:30px;margin-top:1px;margin-left:10px;">
				<img id="tokenLight" src="${pageContext.request.contextPath}/jsp/firstlink/r_light.png" style="width:30px;height:30px;margin-top:2px;"/>
			</div>
			<div style="float:right;margin-top:3px;margin-right:5px;">
				<input id="SITE_CODE_INPUT" type="text" style="cursor:pointer;float:left;text-indent:7px;font-size:12px;width:135px;height:23px;border:solid 1px #DFE8F1;" placeholder="请输入站址编码"/>
				<input id="SITE_NAME_INPUT" type="text" style="cursor:pointer;float:left;text-indent:7px;font-size:12px;width:135px;height:23px;border:solid 1px #DFE8F1;margin-left:5px;" placeholder="请输入站址名称"/>
				<div onclick="javascript:site.siteEvent.flushNodesInMap();" style="float:left;width:75px;height:27px;color:black;background:#DFE8F1;font-size:12px;border-top-right-radius:5px;border-bottom-right-radius:5px;cursor:pointer;box-shadow:0px 0px 3px #DFE8F1;">
  					<div style="float:left;margin-left:13px;">
  						<center style="margin-top:5px;">查询站址</center>
  					</div>
  				</div>
				<input id="mapInfo" type="hidden" style="font-size:12px;width:40px;height:23px;border:solid 1px #DFE8F1;padding-left:5px;" value="13"/>
				<input id="isViewed" type="hidden" value="N"/>
			</div>
			<div style="float:right;margin-top:3px;" id="SITE_TYPE_SELECT">
				<select id='SITE_TYPE_OPTION' style="text-indent:7px;cursor:pointer;width:150px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;padding-left:5px;">
					<option value="-">请选择站址类型</option>
				</select>
			</div>
			<div style="float:right;margin-top:3px;">
				<select id='RUN_STATE_OPTION' style="text-indent:7px;cursor:pointer;width:150px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;padding-left:5px;">
					<option value="-">请选择运营状态</option>
					<option value="正常">正常</option>
					<option value="亏损">亏损</option>
				</select>
			</div>
			<div style="float:right;margin-top:3px;" id="REGION_CONTAINER">
				<select id='REGIONS_OPTION' style="text-indent:7px;cursor:pointer;width:130px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;padding-left:5px;">
					<option value="-">请选择区县</option>
				</select>
			</div>
			<div style="float:right;margin-top:3px;" id="CITY_CONTAINER">
				<select id='CITYS_OPTION' style="text-indent:7px;border-top-left-radius:5px;border-bottom-left-radius:5px;cursor:pointer;width:130px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;padding-left:5px;">
					<option value="-">请选择地市</option>
				</select>
			</div>
		</div>
		<div id="allmap" style="float:left;width:99.4%;border:solid 1px #DFE8F1;border-radius:3px;margin-top:3px;margin-left:3px;margin-right:3px;"></div>
		<div id="r-buttom">
			<table width="240" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr align="center"><td height="30" colspan="3"><b>选择站点</b></td></tr>
				<tr align="center"><td height="1" colspan="3" bgcolor="#999999"></td></tr>
				<tr align="center"><td height="1" colspan="3" bgcolor="#999999"></td></tr>
				<tr>
					<td height="28" align="center"><div class="icon icon-cir icon-cir-blue" /></td>
					<td><font color="#07c5da"><b>告警站点</b></font></td>
					<td>
						<input type="checkbox" id="sc_stopElsSite" value="checkbox" checked onclick="site.siteEvent.ShowStopElecSite()"><font color="#07c5da">停电站点</font><br/>
						<input type="checkbox" id="sc_stopServiceSite" value="checkbox" checked onclick="site.siteEvent.showStopServiceSite()"><font color="#006064">退服站点</font><br/>
					</td>
				</tr>
				<tr align="center"><td height="1" colspan="3" bgcolor="#999999"></td></tr>
				<tr id = "show_normal">
					<td height="28" align="center"><div class="icon icon-cir icon-cir-green" /></td>
					<td><font color="#006064"><b>站点分布</b></font></td>
					<span><td><input type="checkbox" id="sc_normal" value="checkbox" onclick="site.siteEvent.showNormal()"><font color="#8e7cc3">正常站点</font></td></span>
				</tr>
				<tr id = "show_where_normal" style="display: none">
					<td height="28" align="center"><div class="icon icon-cir icon-cir-green" /></td>
					<td><font color="#006064"><b>站点分布</b></font></td>
					<input type="hidden" id = "hid_normal" value="N">
					<td><input type="checkbox" id="sc_where_normal" value="checkbox" onclick="site.siteEvent.showWhereNormal()"><font color="#cc4125">正常站点</font></td>
				</tr>
			</table>
		</div>
		<div style="position:absolute;width:210px;height:70px;top:50px;right:45%;border:solid 1px #CCC;background:#DDD;box-shadow:0 0 7px #CCC;filter:alpha(opacity=50);-moz-opacity:0.80;opacity:0.80;border-radius:5px;">
			<div onclick="javascript:showStopElecWindowAndFlushData();" style="cursor:pointer;float:left;width:90px;height:70px;margin-left:10px;">
				<center>
					<img src="${pageContext.request.contextPath}/jsp/firstlink/eleclost.png" style="margin-top:5px;width:40px;height:35px;"/><br/>
					<div style="font-size:12px;margin-top:5px;" id="STOP_ELEC_DIV">停电</div>
				</center>
			</div>
			<div onclick="javascript:showQuitServiceWindowAndFlushData();" style="cursor:pointer;float:left;width:90px;height:70px;margin-left:10px;">
				<center>
					<img src="${pageContext.request.contextPath}/jsp/firstlink/quitservice.png" style="margin-top:5px;width:40px;height:35px;"/><br/>
					<div style="font-size:12px;margin-top:5px;color:red;" id="QUIT_SERVICE_DIV">退服</div>
				</center>
			</div>
		</div>
  		<%--编辑窗口--%>
  		<div id="EDIT_PANEL" class="MAIN_PANEL" style="width:350px;height:350px;bottom:5px;right:5px;display:none;">
  			<div class="PANEL_HEADING">
  				<div style="float:left;margin-top:10px;margin-left:10px;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/rightarrow.png" style="width:15px;height:15px;margin-top:3px;"/>	
  				</div>
  				<div style="float:left;margin-top:14px;font-size:12px;font-family:'Raleway';">&nbsp;&nbsp;站址信息详情</div>
  				<img onclick="javascript:site.siteEvent.hideMyPanel();" src="${pageContext.request.contextPath}/extensions/form/image/close.png" style="float:right;width:19px;height:19px;margin-top:10px;margin-right:10px;cursor:pointer;"/>
  			</div>
  			<div class="PANEL_BODY" style="height:305px;">
  				<table style="width:99.9%;border:solid 1px #A3D0E3;border-bottom:0px;font-size:12px;margin-top:5px;">
  					<tbody>
  						<tr height="30">
  							<td colspan="2" style="text-align:center;border-bottom:solid 1px #A3D0E3;background:#DEEFF8;">基本信息：</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">所属区县：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_REGION_NAME">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">站址编码：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SITE_CODE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">站址名称：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SITE_NAME">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">经纬度：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_LONGGITUDE_LATITUDE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">站址类型：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SITE_TYPE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">原产权单位：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_PROP_CHAR">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">业务场景：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_BUSINESS_SCENE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">共享单位：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SHARE_UNIT">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">铁塔数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOWER">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">机房数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_ROOM">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">开关电源数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SWITCH">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">蓄电池组数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_STORAGE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">空调数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_AIR">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">动环数量：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_ENVIR">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td colspan="2" style="text-align:center;border-bottom:solid 1px #A3D0E3;background:#DEEFF8;">运行状况：</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">运行状态：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_STATE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td colspan="2" style="text-align:center;border-bottom:solid 1px #A3D0E3;background:#DEEFF8;">收支信息：</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">站址运营状态：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_RUN_STATE">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">投资合计：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TZ_ICB_TOTAL">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">经营收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_CUMULATIVE_TOTAL">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">塔租收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_TOWER_RENT_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">维护费收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_MAINTAIN_FEE_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">场租收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_RENTAL_COSTS_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">电力引入收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_POWER_INTRODUCE_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">油机发电收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_OIL_SERVICE_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">新业务收入：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOI_NEW_BUSINESS_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">运营成本：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOC_CUMULATIVE_TOTAL">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">维护成本：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOC_MAINTAIN_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">场租成本：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOC_SITE_LEASING_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">电力成本：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOC_POWER_COST_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">油机发电成本：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_TOC_OIL_COST_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">折旧摊销：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_ZJ_DAD_CUMULATIVE_TOTAL">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">管理费用：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_GL_ME_CUMULATIVE_TOTAL">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">财务费用：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_FINANCE_COST_Y">&nbsp;</td>
  						</tr>
  						<tr height="30">
  							<td style="width:30%;text-align:right;border-bottom:solid 1px #A3D0E3;border-right:solid 1px #A3D0E3;">单站毛利：</td>
  							<td style="width:70%;text-align:left;border-bottom:solid 1px #A3D0E3;" id="TD_SS_GROSS_PROFIT">&nbsp;</td>
  						</tr>
  					</tbody>
  				</table>
  			</div>
  		</div>
  		<%--编辑窗口--%>
  		<div id="shclNsInMap" style="position:fixed;bottom:45%;right:48%;width:100px;height:100px;display:none;"></div>
  		<%--提示：Success--%>
  		<div id="SUCCESS_TIP" style="position:absolute;width:270px;height:80px;bottom:110px;right:20px;border-radius:7px;border:solid 1px green;background:green;box-shadow:0 0 15px green;display:none;">
  			<div style="float:left;width:50px;height:55px;margin-top:15px;margin-left:27px;">
  				<img src="${pageContext.request.contextPath}/jsp/firstlink/allright.png" style="width:50px;height:50px;"/>
  			</div>
  			<div id="tipDiv" style="float:left;width:150px;border:solid 0px red;height:55px;color:white;font-size:13px;font-weight:bold;margin-top:15px;margin-left:10px;">
  				根据查询条件共找到14348个站点，当前只显示匹配率最高的50个.
  			</div>
  		</div>
  		<%--提示：Success--%>
  		<div id="DEVELOPINT_TIP" style="position:absolute;width:270px;height:80px;bottom:110px;right:20px;border-radius:7px;border:solid 1px green;background:green;box-shadow:0 0 15px green;display:none;">
  			<div style="float:left;width:50px;height:55px;margin-top:15px;margin-left:27px;">
  				<img src="${pageContext.request.contextPath}/jsp/firstlink/allright.png" style="width:50px;height:50px;"/>
  			</div>
  			<div style="float:left;width:150px;border:solid 0px red;height:30px;margin-top:20px;color:white;font-size:13px;font-weight:bold;margin-top:15px;margin-left:10px;">
  				<br/>告警明细展示开发中...
  			</div>
  		</div>
  		<%--编辑窗口--%>
		<div id="MASK_DIV" style="display:none;width:100%;height:630px;position:absolute;top:0px;left:0px;background-color:#FFF;opacity:0.7;"></div>
  		<div id="ALARM_DETAIL_PANEL" class="MAIN_PANEL" style="width:600px;height:410px;top:65px;right:30%;display:none;">
  			<div class="PANEL_HEADING">
  				<div style="float:left;margin-top:10px;margin-left:10px;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/rightarrow.png" style="width:15px;height:15px;margin-top:3px;"/>	
  				</div>
  				<div style="float:left;margin-top:10px;font-size:12px;font-family:'Raleway';" id="ALARM_DETAIL_PANEL_TITLE">&nbsp;&nbsp;-</div>
  				<img onclick="javascript:hideEditWindow();" src="${pageContext.request.contextPath}/extensions/form/image/close.png" style="float:right;width:17px;height:17px;margin-top:11px;margin-right:10px;cursor:pointer;"/>
  			</div>
  			<div class="PANEL_BODY" style="height:325px;background:#FEFEFE;border:solid 0px red;">
  				<div style="float:left;width:99.2%;height:40px;border:solid 1px #DFE8F1;margin-top:2px;margin-left:2px;">
					<div style="float:right;margin-right:10px;">
						<input id="SITE_CODE_OR_NAME_INPUT" type="text" placeholder="请输入站址编码或名称" style="float:left;cursor:pointer;width:170px;height:25px;margin-top:4px;margin-bottom:4px;font-size:12px;border:solid 1px #DFE8F1;"></input>
						<div onclick="javascript:searchDetailDataOfWindow();" style="float:left;width:75px;height:29px;color:white;background:#57BEDD;font-size:12px;border-top-right-radius:5px;border-bottom-right-radius:5px;cursor:pointer;margin-top:4px;box-shadow:0 0 0px #57BEDD;">
		  					<div style="float:left;margin-left:13px;">
		  						<center style="margin-top:6px;">执行查询</center>
		  					</div>
		  				</div>
					</div>
				</div>
				<div style="float:left;width:99.4%;height:275px;margin-top:2px;margin-left:2px;border:solid 0px red;">
					<input type="hidden" id="HIDDEN_OF_ALARM_TYPE" value=""></input>
					<table cellpadding="0" cellspacing="0" border="0" id="contractTable" style="border:0px;font-size:12px;">
						<thead>
							<tr height="32">
								<th><center><font color="black" style="margin-left:10px;">站址编码</font></center></th>
								<th><center><font color="black" style="margin-left:10px;">站址名称</font></center></th>
								<th><center><font color="black" style="margin-left:10px;">状态</font></center></th>
								<th><center><font color="black" style="margin-left:10px;">&nbsp;</font></center></th>
								<th><center><font color="black" style="margin-left:10px;">经度</font></center></th>
								<th><center><font color="black" style="margin-left:10px;">纬度</font></center></th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
  			</div>
  			<div class="PANEL_FOOT" style="background:#FEFEFE;">
 				<div onclick="javascript:hideEditWindow();" style="float:left;width:105px;height:27px;color:white;background:#57BEDD;font-size:12px;border-radius:5px;cursor:pointer;margin-top:7px;margin-left:42%;box-shadow:0 0 0px #57BEDD;">
  					<img src="${pageContext.request.contextPath}/extensions/form/image/submit.png" style="float:left;width:23px;height:13px;margin-top:7px;margin-left:7px;"/>
  					<div style="float:left;margin-left:7px;">
  						<center style="margin-top:5px;">关闭窗口</center>
  					</div>
  				</div>
  			</div>
  		</div>
	</body>
</html>
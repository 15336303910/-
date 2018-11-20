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
    	<title>我的门户</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			var unFixOption = null;
			var unEarnOption = null;
			var dzsyOption = null;
			var aMapOption = null;
			var zMapOption = null;
			var monthlyOption = null;
			/*
				区域标准化下级菜单<单击>事件.
			 */
			function showInjectChart(menuCode){
				recordClickLog(menuCode);
				for(var i=0;i<leftUpMenus.length;i++){
					var leftMenu = leftUpMenus[i];
					if(parseInt(leftMenu["ID"])==parseInt(menuCode)){
						if(leftMenu["MENU_URL"]==null){
							var enName = leftMenu["MENU_EN_NAME"];
							$(".ViewShow").hide();
							$("#"+enName).show(300);
						}else{
							window.open(leftMenu["MENU_URL"]+$("#hiddenOfUsername").val());	
						}
						break;
					}
				}
			}
			var leftUpMenus = null;
			var buildMenus = null;
			var mainMenuCar = null;
			var firAche = new HashMap();
			function linkToFir(menuCode){
				if(firAche!=null){
					var firObj = firAche.get(menuCode);
					if(firObj!=null){
						var url = firObj["URL"];
						if(url!=null && url!="" && url.indexOf("{cityname}")!=-1){
							url = url.replace("{cityname}",$("#hiddenOfCityName").val());
						}
						if(url!=null && url!="" && url.indexOf("{yearmonth}")!=-1){
							var newDate = new Date();
							var yearMonth = newDate.getFullYear()+"-"+(newDate.getMonth()<10?("0"+newDate.getMonth()):newDate.getMonth());
							url = url.replace("{yearmonth}",yearMonth);
						}
						window.open(url);
					}
				}
			}
			function showToBuildSite(menuCode){
				if(buildMenus!=null && buildMenus.length>0){
					recordClickLog(menuCode);
					for(var i=0;i<buildMenus.length;i++){
						var firObj = buildMenus[i];
						if(firObj["ID"]==menuCode){
							if(menuCode==14){
								/*工程进度管理*/
								window.parent.flushLeftMenusByOther(14);
							}else if(menuCode==15){
								/*外验交付确认*/
								window.parent.flushLeftMenusByOther(15);
							}else{
								var url = firObj["MENU_URL"];
								if(url!=null && url!="" && url.indexOf("{username}")!=-1){
									url = url.replace("{username}",$("#hiddenOfUsername").val());
								}
								if(url!=null && url!="" && url.indexOf("{cityname}")!=-1){
									url = url.replace("{cityname}",$("#hiddenOfCityName").val());
								}
								if(url!=null && url!="" && url.indexOf("{yearmonth}")!=-1){
									var newDate = new Date();
									var yearMonth = newDate.getFullYear()+"-"+(newDate.getMonth()<10?("0"+newDate.getMonth()):newDate.getMonth());
									url = url.replace("{yearmonth}",yearMonth);
								}
								window.open(url);
								break;	
							}
						}
					}
				}
			}
			var mainDetail = null;
			function initMainPage(){
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findIndexDatas.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						window.parent.overLoading(true);
						if(resultObj["success"]){
							mainDetail = resultObj;
							/*报表链接相关：经营收入、新业务收入等的Click事件.*/
							$("#hiddenOfCityName").val(resultObj["ACCOUNT_CITY"]);
							var FIR_LINKS = resultObj["FIR_LINKS"];
							for(var i=0;i<FIR_LINKS.length;i++){
								var FIR_LINK = FIR_LINKS[i];
								firAche.put(FIR_LINK["ID"],FIR_LINK);
							}
							/*报表*/
							if(resultObj["IS_MANAGER_ROLE"]){
								$("#hiddenOfIsManageRole").val("Y");
							}else{
								$("#hiddenOfIsManageRole").val("N");
							}
							if(resultObj["IS_INCOMEABLE"]){
								$("#INCOME_ABLE_ACHE").val("Y");	
							}else{
								$("#INCOME_ABLE_ACHE").val("N");
							}
							if(resultObj["IS_PAYOUTABLE"]){
								$("#PAYOUT_ABLE_ACHE").val("Y");	
							}else{
								$("#PAYOUT_ABLE_ACHE").val("N");
							}
							if(!resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
								$("#out_sy").show();//[显示]第三方首页
								$("#KPIS_VIEW").hide();//[隐藏]收入指标与成本指标（两行）
								$("#lineChart").hide();//[隐藏]经营收入与运营成本[线图]
								$("#maina").hide();//[隐藏]各地市KPI综合情况[地图]
								$("#earningChart").hide();//[隐藏]单站利润率排名[TOP5]
								$("#coverChart").hide();//[隐藏]拆站数量[TOP5]
								$("#paylostChart").hide();//[隐藏]亏损站数量[报表]
								$("#NEW_1").hide();//[隐藏]资本性支出占EBITDA比
								$("#NEW_2").hide();//[隐藏]新业务占收比
								$("#NEW_3").hide();//[隐藏]转资率
								$("#earningChart").css({
									"width":"99%"
								});
							}else{
								$("#KPIS_VIEW").show();//[显示]收入指标与成本指标（两行）
								if(resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
									$(".OUT_PAYS").hide();//[隐藏]成本指标数据
									$("#KPIS_VIEW").css({//[调整]指标栏高度调整为单行高度
										"height":"65px"
									});
								}else if(!resultObj["IS_INCOMEABLE"] && resultObj["IS_PAYOUTABLE"]){
									$(".IN_COMES").hide();//[隐藏]收入指标数据
									$(".OUT_PAYS").css({//[调整]取消顶部的MarginTop属性.
										"margin-top":"0px"
									});
									$("#KPIS_VIEW").css({//[调整]指标栏高度调整为单行高度
										"height":"65px"
									});
								}
							}
							var loginUser = resultObj["LOGIN_USER"];
							/*
							             用户账户.
							 */
							$("#hiddenOfUsername").val(loginUser["USER_NAME"]);
							/*
							             员工姓名.
							 */
							$("#employeeNameDiv").html(loginUser["EMPLOYEE_NAME"]);
							/*
							             工单系统：IP、Port.
							 */
							$("#hiddenOfGdLink").val(resultObj["GD_URL"]);
							/*
								----------------------- 工单数量 --------------------------
							*/
							if(resultObj["WAITING_JOB_STATE"]=="Y"){
								$("#WAITING_JOB_BUTTON").html("我的待办工单<font color='red'>（"+resultObj["WAITING_JOB_COUNT"]+"）</font>");
								/*
									--- 顶部提示栏 ---
								 */
								if(parseInt(resultObj["WAITING_JOB_COUNT"])==0){
									updateTipMessage("提示：暂无待办任务.");
								}else{
									updateTipMessage("提示：您有"+resultObj["WAITING_JOB_COUNT"]+"项待办任务，请及时处理.");
								}
							}
							if(resultObj["COPY_JOB_STATE"]=="Y"){
								$("#COPY_JOB_BUTTON").html("抄送给我的工单<font color='red'>（"+resultObj["COPY_JOB_COUNT"]+"）</font>");	
							}
							if(resultObj["COOPER_JOB_STATE"]=="Y"){
								$("#COOPER_JOB_BUTTON").html("我建立的工单<font color='red'>（"+resultObj["COOPER_JOB_COUNT"]+"）</font>");	
							}
							if(resultObj["DOING_JOB_STATE"]=="Y"){
								$("#DOING_JOB_BUTTON").html("我的在办工单<font color='red'>（"+resultObj["DOING_JOB_COUNT"]+"）</font>");	
							}
							/*
								----------------------- 门户菜单 --------------------------
							*/
							mainMenuCar = resultObj["HOME_MENUS"];
							if(mainMenuCar.length>0){
								for(var m=0;m<mainMenuCar.length;m++){
									var mainMenu = mainMenuCar[m];
									$("."+mainMenu["MENU_EN_NAME"]).show(200);
								}
							}else{
								$("#leftUpPanel").hide(200);
								$("#jobsPanel").css({
									"margin-top":"0px"
								});
							}
							/*
								----------------------- 区域标准化菜单--------------------------
							*/
							var leftMenuHtml = "";
							leftUpMenus = resultObj["LEFT_UP_MENUS"];
							if(leftUpMenus.length>0){
								for(var i=0;i<leftUpMenus.length;i++){
									var leftMenu = leftUpMenus[i];
									leftMenuHtml+="<div class='linkMenus leftUpMenus' onclick='javascript:showInjectChart(this.id);' id='"+leftMenu["ID"]+"' onmouseover='javascript:HoverMenu(this.id);' onmouseout='javascript:RemoveHover();' style='display:none;width:90%;height:30px;border:solid 0px red;cursor:pointer;'>";
									leftMenuHtml+="	  <div style='float:left;margin-left:12px;margin-top:4px;'>";
									leftMenuHtml+="		  <img src='${pageContext.request.contextPath}/"+leftMenu["ICON_NAME"]+"' style='width:18px;height:18px;margin-top:1px;'></img>";
									leftMenuHtml+="	  </div>";
									leftMenuHtml+="	  <div style='float:left;margin-left:6px;margin-top:4px;'>";
									leftMenuHtml+="		  <font size='2' color='#00988A'>"+leftMenu["MENU_NAME"]+"</font>";
									leftMenuHtml+="	  </div>";
									leftMenuHtml+="</div>";
									if(i==(leftUpMenus.length-1)){
										leftMenuHtml+="<div class='linkMenus leftUpMenus' id='12210' onmouseover='javascript:HoverMenu(12210);' onmouseout='javascript:RemoveHover();' style='display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;margin-bottom:10px;' onclick='javascript:rebackSpecialMenus();'>";
										leftMenuHtml+="	  <div style='float:left;margin-left:10px;margin-top:4px;'>";
										leftMenuHtml+="		  <img src='${pageContext.request.contextPath}/icons/reback1.png' style='width:20px;height:20px;margin-top:2px;'></img>";
										leftMenuHtml+="	  </div>";
										leftMenuHtml+="	  <div style='float:left;margin-left:6px;margin-top:4px;'>";
										leftMenuHtml+="		  <font size='2' color='#00988A'>返回</font>";
										leftMenuHtml+="	  </div>";
										leftMenuHtml+="</div>";
									}
								}	
							}else{
								/*
								             如果此账户下未有区域标准化相关功能则只展示一个返回按钮.
								 */
								leftMenuHtml+="<div class='linkMenus leftUpMenus' id='12210' onmouseover='javascript:HoverMenu(12210);' onmouseout='javascript:RemoveHover();' style='display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;margin-bottom:10px;' onclick='javascript:rebackSpecialMenus();'>";
								leftMenuHtml+="	  <div style='float:left;margin-left:10px;margin-top:4px;'>";
								leftMenuHtml+="		  <img src='${pageContext.request.contextPath}/icons/reback1.png' style='width:20px;height:20px;margin-top:2px;'></img>";
								leftMenuHtml+="	  </div>";
								leftMenuHtml+="	  <div style='float:left;margin-left:6px;margin-top:4px;'>";
								leftMenuHtml+="		  <font size='2' color='#00988A'>返回</font>";
								leftMenuHtml+="	  </div>";
								leftMenuHtml+="</div>";
							}
							document.getElementById("SYSTEM_LINKS").innerHTML = leftMenuHtml;
							/*
								----------------------- 项目建设辅助管理  --------------------------
							*/
							var buildMenuHtml = "";
							buildMenus = resultObj["BUILD_MENUS"];
							if(buildMenus!=null && buildMenus.length>0){
								for(var i=0;i<buildMenus.length;i++){
									var buildMenu = buildMenus[i];
									buildMenuHtml+="<div id='"+buildMenu["ID"]+"' onclick='javascript:showToBuildSite(this.id);' class='linkMenus leftUpMenus buildmenus' onmouseover='javascript:HoverMenu(this.id);' onmouseout='javascript:RemoveHover();' style='width:90%;height:30px;border:solid 0px red;cursor:pointer;'>";
									buildMenuHtml+="	<div style='float:left;margin-left:12px;margin-top:4px;'>";
									buildMenuHtml+="		<img src='${pageContext.request.contextPath}/"+buildMenu["ICON_NAME"]+"' style='width:18px;height:18px;margin-top:1px;'></img>";
									buildMenuHtml+="	</div>";
									buildMenuHtml+="	<div style='float:left;margin-left:6px;margin-top:4px;'>";
									buildMenuHtml+="		<font size='2' color='#00988A'>"+buildMenu["MENU_NAME"]+"</font>";
									buildMenuHtml+="	</div>";
									buildMenuHtml+="</div>";
									if(i==(buildMenus.length-1)){
										buildMenuHtml+="<div class='linkMenus leftUpMenus buildmenus' id='11110' onmouseover='javascript:HoverMenu(11110);' onmouseout='javascript:RemoveHover();' style='width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;margin-bottom:10px;' onclick='javascript:rebackSpecialMenus();'>";
										buildMenuHtml+="	<div style='float:left;margin-left:10px;margin-top:4px;'>";
										buildMenuHtml+="		<img src='${pageContext.request.contextPath}/icons/reback1.png' style='width:20px;height:20px;margin-top:2px;'></img>";
										buildMenuHtml+="	</div>";
										buildMenuHtml+="	<div style='float:left;margin-left:6px;margin-top:4px;'>";
										buildMenuHtml+="		<font size='2' color='#00988A'>返回</font>";
										buildMenuHtml+="	</div>";
										buildMenuHtml+="</div>";
									}
								}	
							}else{
								/*
								             如果此账户下未有区域标准化相关功能则只展示一个返回按钮.
								 */
								buildMenuHtml+="<div class='linkMenus leftUpMenus buildmenus' id='10010' onmouseover='javascript:HoverMenu(10010);' onmouseout='javascript:RemoveHover();' style='width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;margin-bottom:10px;' onclick='javascript:rebackSpecialMenus();'>";
								buildMenuHtml+="	<div style='float:left;margin-left:10px;margin-top:4px;'>";
								buildMenuHtml+="		<img src='${pageContext.request.contextPath}/icons/reback1.png' style='width:20px;height:20px;margin-top:2px;'></img>";
								buildMenuHtml+="	</div>";
								buildMenuHtml+="	<div style='float:left;margin-left:6px;margin-top:4px;'>";
								buildMenuHtml+="		<font size='2' color='#00988A'>返回</font>";
								buildMenuHtml+="	</div>";
								buildMenuHtml+="</div>";
							}
							document.getElementById("PROJECT_BUILD_HELP_MANAGE").innerHTML = buildMenuHtml;
							/*
								----------------------- 首页 --------------------------
							*/
							var indexCount = resultObj["INDEX_COUNT"];
							/*CRM出账收入*/
							if(indexCount["CRM出账收入"]!=null){
								$("#CRM_PAYOUT_INCOME").html(parseInt(parseInt(indexCount["CRM出账收入"])/10000)+"万");	
							}
							/*CRM出账新业务收入*/
							if(indexCount["CRM出账新业务收入"]!=null){
								$("#CRM_NEW_BUSINESS_INCOME").html(parseInt(parseInt(indexCount["CRM出账新业务收入"])/10000)+"万");	
							}
							/*经营收入*/
							if(indexCount["经营收入"]!=null){
								$("#JYSR").html(parseInt(indexCount["经营收入"])+"万");	
							}
							/*新业务收入*/
							if(indexCount["新业务收入"]!=null){
								$("#XYWSR").html(parseInt(indexCount["新业务收入"])+"万");	
							}
							/*运营成本*/
							if(indexCount["运营成本"]!=null){
								$("#YYCB").html(parseInt(indexCount["运营成本"])+"万");	
							}
							/*折旧摊销*/
							if(indexCount["折旧摊销"]!=null){
								$("#ZJTX").html(parseInt(indexCount["折旧摊销"])+"万");	
							}
							/*维护成本*/
							if(indexCount["维护成本"]!=null){
								$("#WHCB").html(parseInt(indexCount["维护成本"])+"万");	
							}
							/*场租成本*/
							if(indexCount["场租成本"]!=null){
								$("#CZCB").html(parseInt(indexCount["场租成本"])+"万");	
							}
							/*各地市收入情况 */
							var lineHtml = "";
							var mapDatas = new Array();
							var cityDatas = resultObj["COUNT_BY_CITY"];
							$("#COUNT_DATE_VIEW").html("("+resultObj["COUNT_BY_CITY_MAX_DATE"]+")");
							var IsProvince = false;
							var BelongArea = loginUser["BELONG_AREA"];
							var loginUserc = loginUser["USER_NAME"];
							if(loginUserc=="root" || BelongArea.indexOf("省")!=-1 || BelongArea.indexOf("四川")!=-1){
								IsProvince = true;
							}else{
								IsProvince = false;
							}
							for(var j=0;j<cityDatas.length;j++){
								var cityData = cityDatas[j];
								var cityName = cityData["CITY"];
								var showName = cityData["CITY"];
								if(cityName==null || cityName==""){
									cityName = "-";
									showName = "-";
								}else{
									if(cityName.indexOf("阿坝")!=-1){
										cityName = "阿坝藏族羌族自治州";
										showName = "阿坝";
									}else if(cityName.indexOf("甘孜")!=-1){
										cityName = "甘孜藏族自治州";
										showName = "甘孜州";
									}else if(cityName.indexOf("凉山")!=-1){
										cityName = "凉山彝族自治州";
										showName = "凉山州";
									}else if(cityName.indexOf("攀枝")!=-1){
										cityName = "攀枝花市";
										showName = "攀枝花";
									}else{
										cityName = cityName+"市";
									}	
								}
								if(IsProvince){
									lineHtml+="<tr>";
									lineHtml+="	  <td>"+showName+"</td>";
									lineHtml+="	  <td class='IN_COME_TD'>"+parseInt(cityData["INCOME"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["COST"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["REPAIR"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["SITE"])+"</td>";
									lineHtml+="	  <td>"+parseInt(cityData["KS_NUM"])+"</td>";
									lineHtml+="</tr>";	
								}else if(!IsProvince && cityName.indexOf(BelongArea)!=-1){
									lineHtml+="<tr>";
									lineHtml+="	  <td>"+showName+"</td>";
									lineHtml+="	  <td class='IN_COME_TD'>"+parseInt(cityData["INCOME"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["COST"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["REPAIR"])+"</td>";
									lineHtml+="	  <td class='OUT_PAY_TD'>"+parseInt(cityData["SITE"])+"</td>";
									lineHtml+="	  <td>"+parseInt(cityData["KS_NUM"])+"</td>";
									lineHtml+="</tr>";
								}
								mapDatas.push({
									name:cityName,
									value:parseInt(cityData["INCOME"])
								});
							}
							document.getElementById("COUNT_BY_CITY").innerHTML = lineHtml;
							if(!resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
								$(".IN_COME_TD").hide();
								$(".OUT_PAY_TD").hide();
							}else{
								if(resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
									$(".OUT_PAY_TD").hide();
								}else if(!resultObj["IS_INCOMEABLE"] && resultObj["IS_PAYOUTABLE"]){
									$(".IN_COME_TD").hide();
								}
							}
							/*各地市收入情况分析：地图*/
							aMapOption = {
								title:{
									text:"",
									subtext:"",
									textStyle:{
										color:"black"
									}
								},
								tooltip:{
									trigger:"item",
									formatter:function(cellObj){
							        	var res = cellObj.name+"<br/>"+cellObj.value+"万";
							         	return res;
							        },
									textStyle:{
										color:"white"
									}
								},
								legend:{
									orient:"vertical",
									x:"right",
									data:["各地市KPI综合情况"]
								},
								dataRange:{
									min:0,
									max:10000,
									color:["#F9CECE","#EE393A"],
									text:["高","低"],
									calculable:true
								},
								series:[{
									name:"各地市KPI综合情况",
									type:"map",
									mapType:"四川",
									selectedMode:"single",
									itemStyle:{
										normal:{
											label:{
												show:true,
												textStyle:{
													color:"black"
												}
											}
										},
										emphasis:{
											label:{
												show:true
											}
										}
									},
									data:mapDatas
								}]
							};
							require([
								"echarts",
								"echarts/chart/map"
							],DrawMapCharts);
							/*单站利润率排名TOP5*/
							var dzsyChart = resultObj["DZSY_CHART"];
							dzsyOption = {
							    title:{
							        text:"",
							        subtext:""
							    },
							    tooltip:{
							        trigger:"axis",
							        formatter:"{c}%"
							    },
							    legend:{
							        data:["单站利润率排名TOP5"]
							    },
							    toolbox:{
							        show:false
							    },
							    calculable:true,
							    xAxis:[{
									type:"value",
									boundaryGap:[0,0.01]
								}],
							    yAxis:[{
									type:"category",
									data:dzsyChart["CITY_LIST"]
								}],
							    series:[{
						            name:"单站利润率排名TOP5",
						            type:"bar",
						            data:dzsyChart["COUNT_LIST"]
								}]
							};
							require([
								"echarts",
								"echarts/chart/bar"
							],DrawEarningCharts);
							/*拆站数量TOP5*/
							var unFixChart = resultObj["UN_FIX_CHART"];
							unFixOption = {
							    tooltip:{
							        trigger:"axis",
							        axisPointer:{
							            type:"shadow"
							        }
							    },
							    legend:{
							        data:["拆站数量TOP5"]
							    },
							    toolbox:{
							        show:false
							    },
							    calculable:true,
							    xAxis:[{
							    	type:"value"
								}],
							    yAxis:[{
									type:"category",
							        data:unFixChart["CITY_LIST"]
								}],
							    series:[{
						            name:"拆站数量TOP5",
						            type:"bar",
						            stack:"总量",
						            itemStyle:{
						            	normal:{
						            		label:{
						            			show:true,
						            			position:"insideRight"
						            		}
										}
									},
						            data:unFixChart["COUNT_LIST"]
						        }]
							};
							require([
								"echarts",
								"echarts/chart/bar"
							],DrawCoverCharts);
							/*亏损站数量TOP5*/
							var unEarnChart = resultObj["UN_EARN_CHART"];
							unEarnOption = {
							    tooltip:{
							        trigger:"axis",
							        axisPointer:{
							            type:"shadow"
							        }
							    },
							    legend:{
							        data:["亏损站数量TOP5"]
							    },
							    toolbox:{
							        show:false
							    },
							    calculable:true,
							    xAxis:[{
							    	type:"value"
								}],
							    yAxis:[{
									type:"category",
							        data:unEarnChart["CITY_LIST"]
								}],
							    series:[{
						            name:"亏损站数量TOP5",
						            type:"bar",
						            stack:"总量",
						            itemStyle:{
						            	normal:{
						            		label:{
						            			show:true,
						            			position:"insideRight"
						            		}
										}
									},
						            data:unEarnChart["COUNT_LIST"]
						        }]
							};
							require([
								"echarts",
								"echarts/chart/bar"
							],DrawPaylostCharts);
							/*
								收入与成本趋势图
							 */
							var monthChart = resultObj["MONTHLY_CHART"];
							var legendData = [];
							var xAxisData = [];
							var seriesData = [];
							if(resultObj["IS_INCOMEABLE"] && resultObj["IS_PAYOUTABLE"]){
								/*
									如果同时具备[收入]和[成本]分析.
								 */
								legendData = ["经营收入","运营成本"];
								xAxisData = monthChart["MONTH_LIST"];
								seriesData = [{
						            name:"经营收入",
						            type:"line",
						            data:monthChart["MAX_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								},{
						            name:"运营成本",
						            type:"line",
						            data:monthChart["MIN_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								}];
							}else if(!resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
								legendData = [];
								xAxisData = [];
								seriesData = [];
							}else{
								if(resultObj["IS_INCOMEABLE"] && !resultObj["IS_PAYOUTABLE"]){
									/*
										如果具备收入但是不具备成本.
									 */
									legendData = ["经营收入"];
									xAxisData = monthChart["MONTH_LIST"];
									seriesData = [{
							            name:"经营收入",
							            type:"line",
							            data:monthChart["MAX_NUMBER"]
										/*,
							            markPoint:{
						                    data:[{
						                    	type:"max",
						                    	name:"最大值"
						                    },{
						                    	type:"min",
						                    	name:"最小值"
						                    }]
						                }*/
									}];
								}else if(!resultObj["IS_INCOMEABLE"] && resultObj["IS_PAYOUTABLE"]){
									/*
										如果具备成本但是不具备收入.
									 */
									legendData = ["运营成本"];
									xAxisData = monthChart["MONTH_LIST"];
									seriesData = [{
							            name:"运营成本",
							            type:"line",
							            data:monthChart["MIN_NUMBER"]
										/*,
							            markPoint:{
						                    data:[{
						                    	type:"max",
						                    	name:"最大值"
						                    },{
						                    	type:"min",
						                    	name:"最小值"
						                    }]
						                }*/
									}];
								}
							}
							monthlyOption = {
								title:{
							        text:"",
							        subtext:""
							    },
							    tooltip:{
							        trigger:"axis"
							    },
							    legend:{
							        data:legendData
							    },
							    toolbox:{
							        show:true,
							        feature:{ 
					                    selfButtons:{    
											show:true,    
					                        title:"同比",    
					                        icon:"${pageContext.request.contextPath}/icons/chartsred.png",    
					                        option:{},    
											onclick:function(options){    
												flushTBChart();
											}    
										}
					                }
							    },
							    calculable:true,
							    xAxis:[{
							    	type:"category",
							        boundaryGap:false,
									data:xAxisData,
									axisLabel:{
										rotate:30
									}
								}],
							    yAxis:[{
									type:"value",
									axisLabel:{
							        	formatter:"{value}万"
							        }
								}],
							    series:seriesData
							};
							require([
								"echarts",
								"echarts/chart/line"
							],DrawLineCharts);
						}
					}
				});
			}
			function flushTBChart(){
				window.parent.showLoading(45,45);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findTbChartData.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						window.parent.hideLoading();
						monthlyOption.toolbox.feature = { 
		                    selfButtons:{    
								show:true,    
		                        title:"环比",    
		                        icon:"${pageContext.request.contextPath}/icons/chartsred.png",    
		                        option:{},    
								onclick:function(options){    
									flushHBChart();
								}    
							}
		                };
						var monthChart = resultObj["MONTHLY_CHART"];
						var legendData = [];
						var xAxisData = [];
						var seriesData = [];
						var IsInComeAble = $("#INCOME_ABLE_ACHE").val();
						var IsOutComeAble = $("#PAYOUT_ABLE_ACHE").val();
						if(IsInComeAble=="Y" && IsOutComeAble=="Y"){
							legendData = ["经营收入","运营成本"];
							xAxisData = monthChart["MONTH_LIST"];
							seriesData = [{
					            name:"经营收入",
					            type:"line",
					            data:monthChart["MAX_NUMBER"]
								/*,
					            markPoint:{
				                    data:[{
				                    	type:"max",
				                    	name:"最大值"
				                    },{
				                    	type:"min",
				                    	name:"最小值"
				                    }]
				                }*/
							},{
					            name:"运营成本",
					            type:"line",
					            data:monthChart["MIN_NUMBER"]
								/*,
					            markPoint:{
				                    data:[{
				                    	type:"max",
				                    	name:"最大值"
				                    },{
				                    	type:"min",
				                    	name:"最小值"
				                    }]
				                }*/
							}];
						}else if(IsInComeAble=="N" && IsOutComeAble=="N"){
							legendData = [];
							xAxisData = [];
							seriesData = [];
						}else{
							if(IsInComeAble=="Y" && IsOutComeAble=="N"){
								legendData = ["经营收入"];
								xAxisData = monthChart["MONTH_LIST"];
								seriesData = [{
						            name:"经营收入",
						            type:"line",
						            data:monthChart["MAX_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								}];
							}else if(IsInComeAble=="N" && IsOutComeAble=="Y"){
								legendData = ["运营成本"];
								xAxisData = monthChart["MONTH_LIST"];
								seriesData = [{
						            name:"运营成本",
						            type:"line",
						            data:monthChart["MIN_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								}];
							}
						}
						monthlyOption.legend.data = legendData;
						monthlyOption.xAxis.data = xAxisData;
						monthlyOption.series = seriesData;
						require([
							"echarts",
							"echarts/chart/line"
						],DrawLineCharts);
					}
				});
			}
			function flushHBChart(){
				window.parent.showLoading(45,45);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findHbChartData.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						window.parent.hideLoading();
						monthlyOption.toolbox.feature = { 
		                    selfButtons:{    
								show:true,    
		                        title:"同比",    
		                        icon:"${pageContext.request.contextPath}/icons/chartsred.png",    
		                        option:{},    
								onclick:function(options){    
									flushTBChart();
								}    
							}
		                };
						var monthChart = resultObj["MONTHLY_CHART"];
						var legendData = [];
						var xAxisData = [];
						var seriesData = [];
						var IsInComeAble = $("#INCOME_ABLE_ACHE").val();
						var IsOutComeAble = $("#PAYOUT_ABLE_ACHE").val();
						if(IsInComeAble=="Y" && IsOutComeAble=="Y"){
							legendData = ["经营收入","运营成本"];
							xAxisData = monthChart["MONTH_LIST"];
							seriesData = [{
					            name:"经营收入",
					            type:"line",
					            data:monthChart["MAX_NUMBER"]
								/*,
					            markPoint:{
				                    data:[{
				                    	type:"max",
				                    	name:"最大值"
				                    },{
				                    	type:"min",
				                    	name:"最小值"
				                    }]
				                }*/
							},{
					            name:"运营成本",
					            type:"line",
					            data:monthChart["MIN_NUMBER"]
								/*,
					            markPoint:{
				                    data:[{
				                    	type:"max",
				                    	name:"最大值"
				                    },{
				                    	type:"min",
				                    	name:"最小值"
				                    }]
				                }*/
							}];
						}else if(IsInComeAble=="N" && IsOutComeAble=="N"){
							legendData = [];
							xAxisData = [];
							seriesData = [];
						}else{
							if(IsInComeAble=="Y" && IsOutComeAble=="N"){
								legendData = ["经营收入"];
								xAxisData = monthChart["MONTH_LIST"];
								seriesData = [{
						            name:"经营收入",
						            type:"line",
						            data:monthChart["MAX_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								}];
							}else if(IsInComeAble=="N" && IsOutComeAble=="Y"){
								legendData = ["运营成本"];
								xAxisData = monthChart["MONTH_LIST"];
								seriesData = [{
						            name:"运营成本",
						            type:"line",
						            data:monthChart["MIN_NUMBER"]
									/*,
						            markPoint:{
					                    data:[{
					                    	type:"max",
					                    	name:"最大值"
					                    },{
					                    	type:"min",
					                    	name:"最小值"
					                    }]
					                }*/
								}];
							}
						}
						monthlyOption.legend.data = legendData;
						monthlyOption.xAxis.data = xAxisData;
						monthlyOption.series = seriesData;
						require([
							"echarts",
							"echarts/chart/line"
						],DrawLineCharts);
					}
				});
			}
			function initRegionStandard(){
				require.config({
					paths: {
						echarts:"${pageContext.request.contextPath}/js"
					}
				});
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findRegionStandard.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						window.parent.overLoading(true);
						if(resultObj["success"]){
							/*场租待续签数*/
							$("#CZXQS").html(resultObj["RENT_CONTINUE"]+"个");
							/*已确认电费数*/
							$("#DFJNS").html(resultObj["ELEC_MONEY_SUBMIT"]+"个");
							/*客户问题数*/
							$("#KHWTS").html(resultObj["CLIENT_PROBLEM_NUMBER"]+"个");
							/*已巡检站数*/
							$("#XJZS").html(resultObj["XJZS_COUNT"]+"个");
							/*活动告警数*/
							$("#ACTIVITY_ALARM_NUMBER").html(resultObj["ACTIVITY_ALARM_NUMBER"]+"个");
							/*各地市站址规模*/
							var mapDatas = new Array();
							var cityDatas = resultObj["SITE_SIZE"];
							for(var j=0;j<cityDatas.length;j++){
								var cityData = cityDatas[j];
								var cityName = cityData["CITY"];
								if(cityName.indexOf("阿坝")!=-1){
									cityName = "阿坝藏族羌族自治州";
								}else if(cityName.indexOf("甘孜")!=-1){
									cityName = "甘孜藏族自治州";
								}else if(cityName.indexOf("凉山")!=-1){
									cityName = "凉山彝族自治州";
								}else if(cityName.indexOf("攀枝")!=-1){
									cityName = "攀枝花市";
								}else{
									cityName = cityName+"市";
								}
								mapDatas.push({
									name:cityName,
									value:cityData["SITE_SIZE"]
								});
							}
							zMapOption = {
								title:{
									text:"",
									subtext:""
								},
								tooltip:{
									trigger:"item",
									formatter:function(cellObj){
							        	var res = cellObj.name+"<br/>"+cellObj.value+"个";
							         	return res;
							        },
									textStyle:{
										color:"white"
									}
								},
								legend:{
									orient:"vertical",
									x:"right",
									data:["各地市站址规模"]
								},
								dataRange:{
									min:0,
									max:30000,
									color:["#F9CECE","#EE393A"],
									text:["高","低"],
									calculable:true
								},
								series:[{
									name:"各地市站址规模",
									type:"map",
									mapType:"四川",
									selectedMode:"single",
									itemStyle:{
										normal:{
											label:{
												show:true,
												textStyle:{
													color:"black"
												}
											}
										},
										emphasis:{
											label:{
												show:true
											}
										}
									},
									data:mapDatas
								}]
							};
							require([
								"echarts",
								"echarts/chart/map"
							],DrawSiteSizeMap);
						}
					}
				});
			}
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()-1
			    });
				$("#mainContainer").css({
			    	"height":parseInt($("#hiddenOfHeight").val())+16
			    });
				$("#leftpart").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*首页*/
				$("#SY_PART").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*数据中心首页*/
				$("#DATA_CENTERS").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#DATA_CENTERS_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*区域标准化*/
				$("#QYBZH_PART").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*站址可视化*/
				$("#ZZKSH_PART").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*工作任务分派*/
				$("#JOB_DISPATCHS").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#JOB_DISPATCHS_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*电费管理*/
				$("#ELEC_COST_MANAGE").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#ELEC_COST_MANAGE_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*建设项目辅助管理*/
				$("#BUILD_PROJECT_MANAGE").css({
			    	"height":parseInt($("#mainContainer").height())-5
			    });
				$("#BUILD_PROJECT_MANAGE_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-5
			    });
				/*客户问题管理*/
				$("#CLIENT_QUESTION_MANAGE").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#CLIENT_QUESTION_MANAGE_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				/*场租续签提醒*/
				$("#RENT_EXTENT_ALARM").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#RENT_EXTENT_ALARM_SUB").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#gisContainer").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#mainb").css({
			    	"width":parseInt($("#maina").width()),
			    	"height":parseInt($("#maina").height())-54
			    });
				var date = new Date();
				var fullYears = date.getFullYear();
				var fullMonth = date.getMonth();
				if(fullMonth==0){
					fullYears = fullYears-1;
					fullMonth = 12;
				}
				$(".COUNT_DATE_SHOW").html(fullYears+"年"+fullMonth+"月");
				$("#COUNT_DATE_VIEW").html("（"+fullYears+"年"+fullMonth+"月）");
				initMainPage();
				/*报表*/
				require([
					"echarts",
					"echarts/chart/bar"
				],DrawFinalA);
				require([
					"echarts",
					"echarts/chart/bar"
				],DrawFinalB);
				require([
					"echarts",
					"echarts/chart/bar"
				],DrawFinalC);
			});
			function DrawFinalA(ec){
				var finalBarChart1 = ec.init(document.getElementById("NEW_1"),"macarons");
				finalBarChart1.setOption({
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:["资本性支出占EBITDA比（开发中）"]
				    },
				    toolbox:{
				        show:false
				    },
				    calculable:true,
				    xAxis:[{
						type:"category",
				        data:["阿坝","巴中","成都","达州","德阳","甘孜","广安","广元","乐山","凉山","眉山","绵阳","南充","内江","攀枝花","雅安","宜宾","资阳","自贡","泸州"],
				        axisLabel:{
							rotate:45
						}
				    }],
				    yAxis:[{
						type:"value"
					}],
				    series:[{
			            name:"资本性支出占EBITDA比（开发中）",
			            type:"bar",
			            data:[
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random()),
							parseInt(100*Math.random())
			            ]
					}]
				});
			}
			function DrawFinalB(ec){
				var finalBarChart2 = ec.init(document.getElementById("NEW_2"),"infographic");
				finalBarChart2.setOption({
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:["新业务占收比（开发中）"]
				    },
				    toolbox:{
				        show:false
				    },
				    calculable:true,
				    xAxis:[{
						type:"category",
				        data:["阿坝","巴中","成都","达州","德阳","甘孜","广安","广元","乐山","凉山","眉山","绵阳","南充","内江","攀枝花","雅安","宜宾","资阳","自贡","泸州"],
				        axisLabel:{
							rotate:45
						}
				    }],
				    yAxis:[{
						type:"value"
					}],
				    series:[{
			            name:"新业务占收比（开发中）",
			            type:"bar",
			            data:[
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random()),
							parseInt(30*Math.random())
			            ]
					}]
				});
			}
			function DrawFinalC(ec){
				var finalBarChart2 = ec.init(document.getElementById("NEW_3"),"green");
				finalBarChart2.setOption({
				    title:{
				        text:"",
				        subtext:""
				    },
				    tooltip:{
				        trigger:"axis"
				    },
				    legend:{
				        data:["转资率（开发中）"]
				    },
				    toolbox:{
				        show:false
				    },
				    calculable:true,
				    xAxis:[{
						type:"category",
				        data:["阿坝","巴中","成都","达州","德阳","甘孜","广安","广元","乐山","凉山","眉山","绵阳","南充","内江","攀枝花","雅安","宜宾","资阳","自贡","泸州"],
				        axisLabel:{
							rotate:45
						}
				    }],
				    yAxis:[{
						type:"value"
					}],
				    series:[{
			            name:"转资率（开发中）",
			            type:"bar",
			            data:[
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random()),
							parseInt(80*Math.random())
			            ]
					}]
				});
			}
			var aMapChart = null;
			function DrawMapCharts(ec){
				aMapChart = ec.init(document.getElementById("maina"),"macarons");
				aMapChart.setOption(aMapOption);
				/*添加OnClick事件.*/
				if($("#hiddenOfIsManageRole").val()=="Y"){
					var ecConfig = require("echarts/config");
					aMapChart.on(ecConfig.EVENT.MAP_SELECTED,function (param){
					    var selected = param.selected;
					    var clickedItem = "";
					    for(var innerObj in selected){
					        if(selected[innerObj]){
					        	clickedItem = innerObj;
					        	break;
					        }
					    }
					    clickedItem = clickedItem.substring(0,2);
					    /*单站收益*/
					    window.parent.loading(true);
					    $.ajax({
							url:"${pageContext.request.contextPath}/myBenchAction/findSingleEarnSort.ilf",
							async:false,
							type:"POST",
							data:"CITY_NAME="+clickedItem,
							dataType:"json",
							timeout:10000, 
							success:function(resultObj){
								if(resultObj["SUCCESS"]){
									var dzsyChart = resultObj["DZSY_CHART"];
									dzsyOption.yAxis[0].data = dzsyChart["CITY_LIST"];
									dzsyOption.series[0].data = dzsyChart["COUNT_LIST"];
									require([
										"echarts",
										"echarts/chart/bar"
									],DrawEarningCharts);
								}
							}
						});
					    /*亏损站数量*/
					    $.ajax({
							url:"${pageContext.request.contextPath}/myBenchAction/findSinglePayloseSort.ilf",
							async:false,
							type:"POST",
							data:"CITY_NAME="+clickedItem,
							dataType:"json",
							timeout:10000, 
							success:function(resultObj){
								if(resultObj["SUCCESS"]){
									var unEarnChart = resultObj["UN_EARN_CHART"];
									unEarnOption.yAxis[0].data = unEarnChart["CITY_LIST"];
									unEarnOption.series[0].data = unEarnChart["COUNT_LIST"];
									require([
										"echarts",
										"echarts/chart/bar"
									],DrawPaylostCharts);
								}
							}
						});
					    /*拆站数量*/
					    $.ajax({
							url:"${pageContext.request.contextPath}/myBenchAction/findUnfixSort.ilf",
							async:false,
							type:"POST",
							data:"CITY_NAME="+clickedItem,
							dataType:"json",
							timeout:10000, 
							success:function(resultObj){
								if(resultObj["SUCCESS"]){
									var unFixChart = resultObj["UN_FIX_CHART"];
									unFixOption.yAxis[0].data = unFixChart["CITY_LIST"];
									unFixOption.series[0].data = unFixChart["COUNT_LIST"];
									require([
										"echarts",
										"echarts/chart/bar"
									],DrawCoverCharts);
								}
							}
						});
					    /*收入与成本*/
					    $.ajax({
							url:"${pageContext.request.contextPath}/myBenchAction/earnAndPayForCity.ilf",
							async:false,
							type:"POST",
							data:"CITY_NAME="+clickedItem,
							dataType:"json",
							timeout:10000, 
							success:function(resultObj){
								window.parent.overLoading(true);
								if(resultObj["SUCCESS"]){
									var monthlyChart = resultObj["MONTHLY_CHART"];
									monthlyOption.legend.data = ["收入","成本"];
									monthlyOption.xAxis.data = monthlyChart["MONTH_LIST"];
									monthlyOption.series = [{
							            name:"收入",
							            type:"line",
							            data:monthlyChart["MAX_NUMBER"],
							            markLine:{
							                data:[{
							                	type:"average",
							                	name:"平均值"
							                }]
							            }
									},{
							            name:"成本",
							            type:"line",
							            data:monthlyChart["MIN_NUMBER"],
							            markLine:{
							                data:[{
							                	type:"average",
							                	name:"平均值"
							                }]
							            }
									}];
									require([
										"echarts",
										"echarts/chart/line"
									],DrawLineCharts);
								}
							}
						});
					});	
				}
			}
			var bMapChart = null;
			function DrawSiteSizeMap(ec){
				bMapChart = ec.init(document.getElementById("mainb"),"macarons");
				bMapChart.setOption(zMapOption);
			}
			var myCharta = null;
			function DrawLineCharts(ec){
				myCharta = ec.init(document.getElementById("lineChart"),"macarons");
				myCharta.setOption(monthlyOption);
			}
			/*单站收益*/
			var earningChart = null;
			function DrawEarningCharts(ec){
				earningChart = ec.init(document.getElementById("earningChart"),"infographic");
				earningChart.setOption(dzsyOption);
			}
			/*拆站数量*/
			var unfixChart = null;
			function DrawCoverCharts(ec){
				unfixChart = ec.init(document.getElementById("coverChart"),"macarons");
				unfixChart.setOption(unFixOption);
			}
			/*亏损站数量*/
			var paylostChart = null;
			function DrawPaylostCharts(ec){
				paylostChart = ec.init(document.getElementById("paylostChart"),"dark");
				paylostChart.setOption(unEarnOption);
			}
			function recordClickLog(menuCode){
				$.ajax({
					url:"${pageContext.request.contextPath}/loginAction/logFunctionUse.ilf",
					async:true,
					type:"POST",
					data:"MENU_CODE="+menuCode,
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						if(!resultObj["SUCCESS"]){
							window.wxc.xcConfirm("操作日志模块异常，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
						}
					}
				});
			}
			var isRegionInit = false;
			function showView(viewCode){
				if(parseInt(viewCode)==0){
					/*首页*/
					$(".ViewShow").hide();
					if(mainDetail!=null && !mainDetail["IS_INCOMEABLE"] && !mainDetail["IS_PAYOUTABLE"]){
						$("#out_sy").show(500);
					}
					$("#SY_PART").show(500);
					$("#SYSTEM_LINKS").hide();
					$("#PROJECT_BUILD_HELP_MANAGE").hide();
					$(".OUTLINE_CONTAINER").show();
					if(mainMenuCar.length>0){
						for(var m=0;m<mainMenuCar.length;m++){
							var mainMenu = mainMenuCar[m];
							$("."+mainMenu["MENU_EN_NAME"]).show(200);
						}
					}
				}else if(parseInt(viewCode)==1){
					/*区域标准化*/
					$(".ViewShow").hide();//隐藏右侧所有报表界面
					$("#QYBZH_PART").show(300);//右侧展示区域标准化报表界面
					$("#SYSTEM_LINKS").show();
					$(".OUTLINE_CONTAINER").hide();
					$(".specialMenus").hide();//隐藏区域标准化、站址可视化、新业务、数据中心菜单
					$(".PROJECT_BUILD_HELP_MANAGE").hide();
					$(".linkMenus").show(300);//展示区域标准化下一级菜单
					if(!isRegionInit){
						setTimeout("javascript:initRegionStandard();",700);
						isRegionInit = true;
					}
					recordClickLog(135);
				}else if(parseInt(viewCode)==2){
					/*站址可视化*/
					$(".ViewShow").hide();
					$("#ZZKSH_PART").show(500);
					recordClickLog(136);
				}else if(parseInt(viewCode)==3){
					/*客户诉求管理*/
					$(".ViewShow").hide();
					$("#CLIENT_QUESTION_MANAGE").show(500);
					recordClickLog(139);
				}else if(parseInt(viewCode)==10){
					/*建设项目辅助管理*/
					$(".OUTLINE_CONTAINER").hide();
					$(".specialMenus").hide();
					$("#SYSTEM_LINKS").hide();
					$("#PROJECT_BUILD_HELP_MANAGE").show(500);
					$(".ViewShow").hide();
					$("#BUILD_PROJECT_MANAGE").show(500);
					recordClickLog(8);
				}else if(parseInt(viewCode)==11){
					/*在线风控*/
					$(".ViewShow").hide();
					$("#RISKCONTROL_ONLINE").show(500);
					//recordClickLog(11);
				}
				
			}
			function rebackSpecialMenus(){
				//1.展示区域标准化、站址可视化、新业务、数据中心菜单
				$(".OUTLINE_CONTAINER").show();
				$(".specialMenus").show(300);
				//2.隐藏区域标准化下级菜单
				$("#SYSTEM_LINKS").hide();
				$("#PROJECT_BUILD_HELP_MANAGE").hide();
				$(".linkMenus").hide();
				$(".buildmenus").show();
				$(".ViewShow").hide();
				if(mainDetail!=null && !mainDetail["IS_INCOMEABLE"] && !mainDetail["IS_PAYOUTABLE"]){
					$("#out_sy").show(500);
				}
				$("#SY_PART").show(500);
				if(mainMenuCar!=null && mainMenuCar.length>0){
					$(".MAIN_MENUS").hide();
					for(var m=0;m<mainMenuCar.length;m++){
						var mainMenu = mainMenuCar[m];
						$("."+mainMenu["MENU_EN_NAME"]).show(200);
					}
				}else{
					$("#leftUpPanel").hide(200);
				}
			}
			function doGdSystem(typeCode){
				var dataType = "";
				if(parseInt(typeCode)==1){
					dataType = "我的待办工单";
				}else if(parseInt(typeCode)==2){
					dataType = "抄送给我的工单";
				}else if(parseInt(typeCode)==3){
					dataType = "我的协办工单";
				}else if(parseInt(typeCode)==4){
					dataType = "我的在办工单";
				}else if(parseInt(typeCode)==5){
					dataType = "我的已办工单";
				}else if(parseInt(typeCode)==6){
					dataType = "我的已归档工单";
				}else if(parseInt(typeCode)==7){
					dataType = "工单综合查询";
				}
				if(dataType!=null && dataType!=""){
					$.ajax({
						url:"${pageContext.request.contextPath}/myBenchAction/findGdHttpLink.ilf",
						async:true,
						type:"POST",
						data:"GD_DATA_TYPE="+dataType,
						dataType:"json",
						timeout:10000, 
						success:function(resultObj){
							if(resultObj["SUCCESS"] && resultObj["MENU_URL"]!=null && resultObj["MENU_URL"]!=""){
								var menuUrl = resultObj["MENU_URL"];
								if(menuUrl.indexOf("USER_NAME")!=-1){
									menuUrl = menuUrl.replace("USER_NAME",$("#hiddenOfUsername").val());
								}
								window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/"+menuUrl);
							}
						}
					});	
				}
			}
			function HoverMenu(overCode){
				$(".leftUpMenus").css({
					"border":"solid 0px #A3D0E3",
					"border-radius":"0px",
					"box-shadow":"0 0 0px #A3D0E3",
					"border":"solid 0px #A3D0E3"
				});
				$("#"+overCode).css({
					"border":"solid 1px #DEEFF8",
					"border-radius":"5px",
					"box-shadow":"0 0 10px #A3D0E3",
					"border":"1px solid #A3D0E3"
				});
			}
			function RemoveHover(){
				$(".leftUpMenus").css({
					"border":"solid 0px #A3D0E3",
					"border-radius":"0px",
					"box-shadow":"0 0 0px #A3D0E3",
					"border":"solid 0px #A3D0E3"
				});
			}
			function linkToGdSystem(typeCode){
				if(typeCode==1){
					/*电费管理工单*/
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/tfmAction!init.ilf?foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(typeCode==2){
					/*客户问题处理工单*/
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/customerProblemAction!init.ilf?foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(typeCode==3){
					/*省分工作任务*/
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/taskAssignAction!init.ilf?foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(typeCode==4){
					/*地市工作任务*/
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/taskAssign2Action!init.ilf?foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}
			}
			function addTabOf(tabCode){
				if(parseInt(tabCode)==1){
					/*场租待续签数*/
					window.parent.turnToJsp("场租续签数明细","jsp/firstIndex/regions/rentExtent.jsp");	
				}else if(parseInt(tabCode)==2){
					/*已确认电费数*/
					window.parent.turnToJsp("已确认电费明细","jsp/firstIndex/regions/elecCostPayout.jsp");
				}else if(parseInt(tabCode)==3){
					/*已巡检站数*/
					window.parent.turnToJsp("巡检站数明细","jsp/firstIndex/regions/xjSites.jsp");
				}else if(parseInt(tabCode)==4){
					/*客户问题明细*/
					window.parent.turnToJsp("客户问题明细","jsp/firstIndex/regions/khwtDetails.jsp");
				}else if(parseInt(tabCode)==5){
					/*活动告警明细*/
					window.parent.turnToJsp("活动告警明细","jsp/firstIndex/regions/alarmDetail.jsp");
				}else if(parseInt(tabCode)==6){
					/*客户诉求明细*/
					window.parent.turnToJsp("客户诉求明细","jsp/firstIndex/reports/khwtDetail.jsp");
				}
			}
		 	function addTabForDealExpire(cityName,cityinfo){
				window.parent.turnToJsp("场租续签提醒明细："+cityName,"jsp/firstIndex/reports/alarmDetails.jsp?cityName="+encodeURIComponent(encodeURIComponent(cityName,"UTF-8"),"UTF-8")+"&cityinfo="+encodeURIComponent(encodeURIComponent(cityinfo,"UTF-8"),"UTF-8"));
			}
			function addTabForDoingJob(){
				window.parent.turnToJsp("场租续签提醒在途工单","jsp/firstIndex/reports/ztJobs.jsp");
			}
			function addTabForJsp(title,url){
				window.parent.turnToJsp(title,url);
			}
			function turnToNewBusiness(){
				recordClickLog(137);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findNewBusinessUrl.ilf",
					async:false,
					type:"POST",
					data:"keyName=new_business",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						if(resultObj["success"]){
							window.open(resultObj["PROPERTY_URL"]);
						}
					}
				});
			}
			function turnToRiskPage(){
				recordClickLog(9);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findNewBusinessUrl.ilf",
					async:false,
					type:"POST",
					data:"keyName=zx_app_link",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						if(resultObj["success"]){
							window.open(resultObj["PROPERTY_URL"]);
						}
					}
				});
			}
			function showCircleLoading(){
				window.parent.loading(true);
			}
			function hideCircleLoading(){
				window.parent.overLoading(true);
			}
			function updateTipMessage(message){
				window.parent.updateTip(message);
			}
			function turnToDbCenter(){
				$(".ViewShow").hide();
				$("#DATA_CENTERS").show(500);
				window.parent.rebackNormalMenu();
				recordClickLog(138);
			}
			function showDoingJob(){
				$("#DEVELOPINT_TIP").show(600);
				setTimeout("javascript:hideDoingJob();",4000);
			}
			function hideDoingJob(){
				$("#DEVELOPINT_TIP").hide(1000);
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;overflow:hidden;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfUsername" value=""></input>
  		<input type="hidden" id="hiddenOfCityName" value=""></input>
  		<input type="hidden" id="hiddenOfGdLink" value=""></input>
  		<input type="hidden" id="hiddenOfIsManageRole" value="N"></input>
  		<input type="hidden" id="INCOME_ABLE_ACHE" value="Y"></input>
  		<input type="hidden" id="PAYOUT_ABLE_ACHE" value="Y"></input>
  		<div id="mainContainer" style="width:99.8%;height:200px;border:solid 0px red;overflow:hidden;margin-top:-20px;">
  			<div style="width:18%;height:260px;float:left;border:solid 0px green;" id="leftpart">
  				<div style="width:99.8%;height:338px;border:solid 1px #DFE8F1;" id="leftUpPanel">
  					<div class="panel panel-default" style="border:0px;">
						<div class="panel-heading" style="border-bottom:solid 1px #DFE8F1;cursor:pointer;" onclick="javascript:showView(0);">
							<span class="panel-label"></span>专业功能
						</div>
						<div class="panel-body" style="border:0px;">
							<div class="panlecontent container4 clearfix" style="border:0px;">
								<div class="div_scroll" style="border:0px;">
									<div style="height:auto;width:auto;border:0px;overflow:auto;">
										<div class="OUTLINE_CONTAINER" style="border:dotted 1px #DFE8F1;padding:2px;border-radius:5px;">
											<div class="specialMenus leftUpMenus MAIN_MENUS REGION_STANDARDS" id="10001" onmouseover="javascript:HoverMenu(10001);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;cursor:pointer;" onclick="javascript:showView(1);">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/reginStandard.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">区域标准化</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS BUILD_PROJECT_MANAGEMENT" id="10010" onmouseover="javascript:HoverMenu(10010);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;" onclick="javascript:showView(10);">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/builds.png" style="width:18px;height:18px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">建设项目辅助管理</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS SITE_SEEABLE" id="10002" onmouseover="javascript:HoverMenu(10002);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;" onclick="javascript:showView(2);">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/siteOversaw.png" style="width:18px;height:18px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">站址可视化</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS NEW_BUSINESS" id="10003" onclick="javascript:turnToNewBusiness();" onmouseover="javascript:HoverMenu(10003);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/newjob.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">新业务</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS CLIENT_QUESTION_GD" id="10006" onmouseover="javascript:HoverMenu(10006);" onmouseout="javascript:RemoveHover();" style="width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;display:none;" onclick="javascript:showView(3);">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/questions.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">客户诉求管理</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS ZX_APP_MANAGE" id="10026" onmouseover="javascript:HoverMenu(10026);" onmouseout="javascript:RemoveHover();" style="width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;display:none;" onclick="javascript:turnToRiskPage();">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/ZXapp.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">中兴应用</font>
												</div>
											</div>
										</div>
										<div class="OUTLINE_CONTAINER" style="border:dotted 1px #DFE8F1;padding:2px;border-radius:5px;margin-top:6px;">
											<div onclick="javascript:turnToDbCenter();" class="specialMenus leftUpMenus MAIN_MENUS DATA_CENTERS" id="10005" onmouseover="javascript:HoverMenu(10005);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/datadb.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">数据中心</font>
												</div>
											</div>
											<div class="specialMenus leftUpMenus MAIN_MENUS RISK_CONTROLS" id="10025" onclick="javascript:showView(11);" onmouseover="javascript:HoverMenu(10025);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:30px;border:solid 0px red;margin-top:3px;cursor:pointer;">
												<div style="float:left;margin-left:10px;margin-top:4px;">
													<img src="${pageContext.request.contextPath}/icons/FKcontrol.png" style="width:18px;height:18px;margin-top:1px;"></img>
												</div>
												<div style="float:left;margin-left:7px;margin-top:4px;">
													<font size="2" color="#00988A">在线风控</font>
												</div>
											</div>
										</div>
										<%--区域标准化--%>
										<div id="SYSTEM_LINKS" style="width:99%;height:275px;border:solid 0px red;overflow:auto;display:none;">
											&nbsp;
										</div>
										<%--项目建设辅助管理--%>
										<div id="PROJECT_BUILD_HELP_MANAGE" style="width:99%;height:270px;border:solid 0px red;overflow:auto;display:none;">
											&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
  				</div>
  				<div style="width:99.8%;height:150px;border:solid 1px #DFE8F1;margin-top:5px;" id="jobsPanel">
  					<div class="panel panel-default" style="border:0px;">
						<div class="panel-heading" style="border-bottom:solid 1px #DFE8F1;">
							<span class="panel-label"></span>任务待办
						</div>
						<div class="panel-body" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:80px;width:auto;border:solid 0px red;overflow:auto;">
										<div onclick="javascript:doGdSystem(1);" class="leftUpMenus" id="10011" onmouseover="javascript:HoverMenu(10011);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;cursor:pointer;">
											<div style="float:left;margin-left:7px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/waitingjob.png" style="width:25px;height:24px;margin-top:-1px;"></img>
											</div>
											<div style="float:left;margin-left:5px;margin-top:2px;">
												<font size="2" color="#00988A" id="WAITING_JOB_BUTTON">我的待办工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(2);" class="leftUpMenus" id="10012" onmouseover="javascript:HoverMenu(10012);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;">
											<div style="float:left;margin-left:11px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/copysend.png" style="width:17px;height:17px;margin-top:1px;"></img>
											</div>
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<font size="2" color="#00988A" id="COPY_JOB_BUTTON">抄送给我的工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(3);" class="leftUpMenus" id="10013" onmouseover="javascript:HoverMenu(10013);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;">
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/cooperdo.png" style="width:22px;height:22px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:6px;margin-top:2px;">
												<font size="2" color="#00988A" id="COOPER_JOB_BUTTON">我建立的工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(4);" class="leftUpMenus" id="10014" onmouseover="javascript:HoverMenu(10014);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;">
											<div style="float:left;margin-left:10px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/runstep.png" style="width:18px;height:18px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:8px;margin-top:2px;">
												<font size="2" color="#00988A" id="DOING_JOB_BUTTON">我的在办工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(5);" class="leftUpMenus" id="10015" onmouseover="javascript:HoverMenu(10015);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;">
											<div style="float:left;margin-left:10px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/myjobs.png" style="width:18px;height:18px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:8px;margin-top:2px;">
												<font size="2" color="#00988A">我的已办工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(6);" class="leftUpMenus" id="10016" onmouseover="javascript:HoverMenu(10016);" onmouseout="javascript:RemoveHover();" style="display:none;width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;margin-bottom:10px;">
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/done.png" style="width:20px;height:20px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:6px;margin-top:2px;">
												<font size="2" color="#00988A">我的已归档工单</font>
											</div>
										</div>
										<div onclick="javascript:doGdSystem(7);" class="leftUpMenus" id="10017" onmouseover="javascript:HoverMenu(10017);" onmouseout="javascript:RemoveHover();" style="width:90%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;margin-bottom:10px;">
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/uniselect.png" style="width:20px;height:20px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:6px;margin-top:2px;">
												<font size="2" color="#00988A">工单综合查询</font>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
  				</div>
  			</div>
  			<%--首页--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;" class="ViewShow" id="SY_PART">
  				<div id="KPIS_VIEW" style="display:none;width:99%;height:135px;border:solid 0px red;margin-top:10px;margin-left:10px;">
  					<%--收入指标--%>
  					<div style="width:100%;height:60px;" class="IN_COMES">
  						<%--经营收入--%>
  						<div onclick="javascript:linkToFir(20001);" style="float:left;width:24%;height:60px;background:#FFC562;box-shadow:0 0 10px #FFC562;border-radius:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:5px;">
	  							<img src="${pageContext.request.contextPath}/icons/moneyStatu.png" style="float:right;width:42px;height:42px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">经营收入</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="JYSR">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--其中：新业务收入--%>
	  					<div onclick="javascript:linkToFir(20002);" style="float:left;width:24%;height:60px;background:#DA8DD9;box-shadow:0 0 10px #DA8DD9;border-radius:7px;margin-left:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:7px;">
	  							<img src="${pageContext.request.contextPath}/icons/works.png" style="float:right;width:38px;height:38px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">其中：新业务收入</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="XYWSR">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--CRM出账收入--%>
	  					<div style="float:left;width:24%;height:60px;background:#01D867;box-shadow:0 0 10px #01D867;border-radius:7px;cursor:pointer;margin-left:7px;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/CRM.png" style="float:right;width:42px;height:42px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">CRM出账收入</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="CRM_PAYOUT_INCOME">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--CRM新业务收入--%>
	  					<div style="float:left;width:24%;height:60px;background:#0082FC;box-shadow:0 0 10px #0082FC;border-radius:7px;cursor:pointer;margin-left:7px;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/CRM.png" style="float:right;width:42px;height:42px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">CRM新业务收入</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="CRM_NEW_BUSINESS_INCOME">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">2017年10月</font></div>
	  						</div>
	  					</div>
  					</div>
  					<%--支出指标--%>
  					<div style="width:100%;height:60px;margin-top:10px;" class="OUT_PAYS">
  						<%--运营成本--%>
  						<div id="RUN_COST" onclick="javascript:linkToFir(20003);" style="float:left;width:24%;height:60px;background:#6BC2EB;box-shadow:0 0 10px #6BC2EB;border-radius:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/basiccost.png" style="float:right;width:38px;height:38px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">运营成本</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="YYCB">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--其中：折旧摊销--%>
	  					<div onclick="javascript:linkToFir(20004);" style="float:left;width:24%;height:60px;background:#00E2CE;box-shadow:0 0 10px #00E2CE;border-radius:7px;margin-left:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/payoff.png" style="float:right;width:38px;height:38px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">其中：折旧摊销</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="ZJTX">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--其中：维护成本--%>
	  					<div onclick="javascript:linkToFir(20005);" style="float:left;width:24%;height:60px;background:#00C8C0;box-shadow:0 0 10px #00C8C0;border-radius:7px;margin-left:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/moneyCenter.png" style="float:right;width:40px;height:40px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">其中：维护成本</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="WHCB">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
	  					<%--其中：场租成本--%>
	  					<div onclick="javascript:linkToFir(20006);" style="float:left;width:24%;height:60px;background:#BCD352;box-shadow:0 0 10px #BCD352;border-radius:7px;margin-left:7px;cursor:pointer;">
	  						<div style="float:left;width:35%;height:45px;margin-top:8px;">
	  							<img src="${pageContext.request.contextPath}/icons/home.png" style="float:right;width:38px;height:38px;"></img>
	  						</div>
	  						<div style="float:left;width:55%;height:45px;margin-top:8px;margin-left:15px;">
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;">其中：场租成本</font></div>
	  							<div style="margin-top:1px;border:solid 0px red;height:17px;margin-top:3px;"><font color="white" style="font-size:12px;" id="CZCB">-</font></div>
	  							<div style="display:none;margin-top:1px;border:solid 0px red;height:17px;"><font color="white" style="font-size:12px;" class="COUNT_DATE_SHOW">2017年10月</font></div>
	  						</div>
	  					</div>
  					</div>
  				</div>
  				<div style="width:98%;height:390px;border:solid 0px red;margin-top:8px;margin-left:10px;cursor:pointer;">
  					<div style="float:left;width:34.5%;height:453px;border-radius:5px;">
  						<div style="width:99.9%;height:450px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;" id="maina">
  							&nbsp;
  						</div>
  					</div>
  					<div style="float:right;width:65.2%;height:450px;border:solid 0px #A3D0E3;border-radius:5px;margin-bottom:10px;">
  						<div style="width:100%;height:220px;">
  							<div style="width:49%;height:220px;float:left;margin-left:3px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;" id="lineChart"></div>
  							<div style="width:49%;height:220px;float:right;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;" id="earningChart"></div>
  						</div>
  						<div style="width:100%;height:220px;margin-top:10px;">
  							<div style="width:49%;height:220px;float:left;margin-left:3px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;" id="paylostChart"></div>
  							<div style="width:49%;height:220px;float:right;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;" id="coverChart"></div>
  						</div>
  					</div>
  					<div style="float:right;width:59.5%;height:350px;border:solid 1px #A3D0E3;border-radius:5px;display:none;">
  						&nbsp;
  					</div>
  				</div>
  				<div style="width:98%;height:220px;border:solid 0px red;margin-top:5px;margin-left:10px;">
  					<div id="NEW_1" style="float:left;width:34.5%;height:210px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;margin-top:7px;margin-bottom:10px;">
  						&nbsp;
  					</div>
  					<div id="NEW_2" style="float:left;width:32%;height:210px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;margin-left:4px;margin-bottom:10px;">
  						&nbsp;
  					</div>
  					<div id="NEW_3" style="float:right;width:31.9%;height:210px;border:solid 1px #A3D0E3;box-shadow:0 0 10px #A3D0E3;border-radius:5px;margin-bottom:10px;">
  						&nbsp;
  					</div>
  				</div>
  				<%--第三方首页--%>
       			<div style="display:none;width:99%;height:100%;float:right;border:solid 1px #A3D0E3;overflow:auto;margin-top:3px;" class="ViewShow" id="out_sy">
	  				<div style="width:99.8%;height:155px;border:solid 0px red;margin-top:30px;">
	  					<div style="width:27%;height:150px;border:solid 0px red;float:left;">
	  						<img src="${pageContext.request.contextPath}/icons/ChinaTower.jpg" style="width:100%;height:140px;"></img>
	  					</div>
	  					<div style="width:72%;height:150px;border:solid 0px red;float:right;">
	  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;margin-top:30px;">
	  							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国通信设施服务有限公司的成立有利于减少电信行业内铁塔以及相关基础设施的重复建设，提高行业投资效率，进
	  						</div>
	  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
	  							一步提高电信基础设施共建共享水平，缓解企业选址难的问题，增强企业集约型发展的内生动力，从机制上进一步促进节约
	  						</div>
	  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
	  							资源和环境保护。同时有利于降低中国移动的总体投资规模，有效盘活资产，节省资本开支，优化现金使用，聚焦核心业务
	  						</div>
	  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
	  							运营，提升市场竞争能力，加快转型升级。
	  						</div>
	  					</div>
	  				</div>
	  				<div style="width:98%;height:220px;border:solid 0px red;margin-left:10px;margin-top:20px;border-top:dotted 1px #A3D0E3;">
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;margin-top:30px;">
	  						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2014年 9月3日消息，继省公司总经理选聘结束后，铁塔公司又开始启动省公司副总选聘，由三大运营商根据内部报名情况推荐候选人，省公司副总与正职不能来
	  					</div>
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
	  						自同一运营商。2014年09月11日从中国铁塔股份有限公司了解到,“中国通信设施服务股份有限公司”进行了工商变更登记手续，正式更名为“中国铁塔股份有限公司”。
	  					</div>
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
	  						2014年11月5日从中国铁塔股份有限公司获悉，铁塔公司已全部完成地市分公司和31个省级分公司部门负责人的选聘工作，已聘共计842人，均来自三大电信运营商。
	  					</div>
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
							至此，铁塔公司基本完成从总部到省、市三级公司的组建.中国铁塔公司有关负责人介绍,此次聘用人选共计842人：中国移动307人，中国联通279人，中国电信256人，			
	  					</div>
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
							聘用人选平均年龄42岁。铁塔公司31个省级分公司中，安徽、贵州、河北、青海、重庆、江西、吉林、四川、湖南、陕西、湖北、黑龙江、山东、云南、海南、甘肃									
	  					</div>
	  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
							、福建17个分公司已经先后揭牌成立。									
	  					</div>
	  				</div>
	  			</div>
  			</div>
  			<%--站址可视化--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="ZZKSH_PART">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstlink/resourceGis.jsp" style="width:100%;border:0px;height:100px;" id="gisContainer"></iframe>
  			</div>
  			<%--数据中心--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="DATA_CENTERS">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/dbIndex.jsp" style="width:100%;border:0px;height:100px;" id="DATA_CENTERS_SUB"></iframe>
  			</div>
  			<%--工作任务分派--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="JOB_DISPATCHS">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/jobDispatch.jsp" style="width:100%;border:0px;height:100px;" id="JOB_DISPATCHS_SUB"></iframe>
  			</div>
  			<%--电费管理--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="ELEC_COST_MANAGE">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/dfManage.jsp" style="width:100%;border:0px;height:100px;" id="ELEC_COST_MANAGE_SUB"></iframe>
  			</div>
  			<%--建设项目辅助管理--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="BUILD_PROJECT_MANAGE">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/buildingManage.jsp" style="width:100%;border:0px;height:100px;" id="BUILD_PROJECT_MANAGE_SUB"></iframe>
  			</div>
  			<%--场租续签提醒--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="RENT_EXTENT_ALARM">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/czExpireAlarm.jsp" style="width:100%;border:0px;height:100px;" id="RENT_EXTENT_ALARM_SUB"></iframe>
  			</div>
  			<%--客户诉求管理--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="CLIENT_QUESTION_MANAGE">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/khwtManage.jsp" style="width:100%;border:0px;height:100px;" id="CLIENT_QUESTION_MANAGE_SUB"></iframe>
  			</div>
  			<%--在线风控--%>
  			<div style="width:81.5%;height:100%;float:right;border:solid 1px #DFE8F1;overflow:auto;display:none;" class="ViewShow" id="RISKCONTROL_ONLINE">
  				<iframe src="${pageContext.request.contextPath}/jsp/firstIndex/reports/riskControlOnline.jsp" style="width:100%;border:0px;height:100%;" id="RISKCONTROL_ONLINE_SUB"></iframe>
  			</div>
  			<%--区域标准化--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #DFE8F1;display:none;" class="ViewShow" id="QYBZH_PART">
  				<div style="width:99%;height:75px;border:solid 0px red;margin-top:5px;margin-left:10px;cursor:pointer;">
  					<div onclick="javascript:addTabOf(2);" style="float:left;width:16%;height:75px;background:#DA8DD9;box-shadow:0 0 10px #DA8DD9;border-radius:7px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:18px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/elec.png" style="float:right;width:42px;height:37px;"></img>
  						</div>
  						<div style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">已确认电费数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="DFJNS">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(1);" style="float:left;width:16%;height:75px;background:#FF5F33;box-shadow:0 0 10px #FF5F33;border-radius:7px;margin-left:7px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:17px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/rentdeal.png" style="float:right;width:40px;height:40px;"></img>
  						</div>
  						<div style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">场租待续签数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="CZXQS">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(3);" style="float:left;width:16%;height:75px;background:#16257C;box-shadow:0 0 10px #16257C;border-radius:7px;margin-left:6px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:17px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/oversaw.png" style="float:right;width:40px;height:40px;"></img>
  						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">已巡检站数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="XJZS">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(5);" style="float:left;width:16%;height:75px;background:#7FCD47;box-shadow:0 0 10px #7FCD47;margin-left:7px;border-radius:7px;">
  						<div style="float:left;width:40%;height:50px;margin-top:16px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/fatal.png" style="float:right;width:38px;height:38px;"></img>
  						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">活动告警数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="ACTIVITY_ALARM_NUMBER">-</font></div>
  						</div>
  					</div>
  					<div style="float:left;width:16%;height:75px;background:#3498DB;box-shadow:0 0 10px #3498DB;border-radius:7px;margin-left:6px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:16px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/room.png" style="float:right;width:40px;height:40px;"></img>
  						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">超长站数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2">-</font></div>
  						</div>
  					</div>
  					<div onclick="javascript:addTabOf(4);" style="float:left;width:16%;height:75px;background:#00C8C0;box-shadow:0 0 10px #00C8C0;border-radius:7px;margin-left:6px;cursor:pointer;">
  						<div style="float:left;width:40%;height:50px;margin-top:18px;">
  							<img src="${pageContext.request.contextPath}/icons/menu/server.png" style="float:right;width:40px;height:40px;"></img>
  						</div>
  						<div style="float:left;width:45%;height:50px;margin-top:17px;margin-left:10px;">
  							<div><font color="white" size="2">客户诉求数</font></div>
  							<div style="margin-top:1px;"><font color="white" size="2" id="KHWTS">-</font></div>
  						</div>
  					</div>
  				</div>
  				<div style="width:98%;height:415px;border:solid 0px red;margin-top:8px;overflow:auto;margin-left:10px;cursor:pointer;">
  					<div style="float:left;width:34.5%;height:300px;border:solid 1px #DFE8F1;box-shadow:0 0 10px #A3D0E3;" id="mainb">
  						&nbsp;
  					</div>
  					<div style="float:right;width:65%;height:400px;border:solid 0px #DFE8F1;box-shadow:0 0 10px #DFE8F1;overflow:hidden;">
  						<div class="panel panel-default" style="border:0px;">
							<div class="panel-heading" style="border:solid 1px #DFE8F1;border-bottom:0px;">
								<span class="panel-label"></span>各地市/区县收支情况分析（单位/万元）<font id="COUNT_DATE_VIEW">（-）</font>
							</div>
							<div class="panel-body" style="height:335px;border:solid 1px #DFE8F1;overflow:auto;">
								<div class="panlecontent container4 clearfix" style="overflow:auto;">
									<div class="div_scroll" style="overflow:auto;">
										<div style="height:auto;width:auto;overflow:auto;">
											<table class="table table-bordered table-hover">
												<thead>
													<tr>
														<td style="background:#DFE8F1;">地市/区县</td>
														<td style="background:#DFE8F1;" class="IN_COME_TD">经营收入</td>
														<td style="background:#DFE8F1;" class="OUT_PAY_TD">运营成本</td>
														<td style="background:#DFE8F1;" class="OUT_PAY_TD">其中：维护成本</td>
														<td style="background:#DFE8F1;" class="OUT_PAY_TD">其中：场租成本</td>
														<td style="background:#DFE8F1;">亏损站数</td>
													</tr>
												</thead>
												<tbody id="COUNT_BY_CITY">
													<tr>
														<td colspan="6" style="text-align:center;">暂无数据</td>
													</tr>
												</tbody>
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
  		<div id="DEVELOPINT_TIP" style="display:none;position:absolute;width:270px;height:90px;bottom:42%;right:40%;border-radius:7px;border:solid 1px green;background:green;box-shadow:0 0 15px green;">
  			<div style="float:left;width:50px;height:55px;margin-top:20px;margin-left:27px;">
  				<img src="${pageContext.request.contextPath}/jsp/firstlink/allright.png" style="width:50px;height:50px;"/>
  			</div>
  			<div style="float:left;width:150px;border:solid 0px red;height:30px;margin-top:20px;color:white;font-size:13px;font-weight:bold;margin-top:15px;margin-left:10px;">
  				<br/>相关功能开发中...
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
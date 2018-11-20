<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!DOCTYPE html>
	<head>
    	<base href="<%=basePath%>">
    	<link rel="icon" href="${pageContext.request.contextPath}/img/Tower.png" type="image/x-icon">
    	<title>四川铁塔运营支撑系统</title>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/frame.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index/indexStyle.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/leftMenus/css/style.css"/>	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/leftMenue/css/style.css" media="screen"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/topMenus/css/indexStyle.css"/>			
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/quickNavigate/css/style.css"/>    	
    	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/quickNavigate/js/js.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/quickNavigate/js/jquery.transform.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/leftMenue/js/index.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/tips/jquery.shCircleLoader.js"></script>
		<style type="text/css">
			.tabs{
				margin-top:2px;
				height:28px;	
				background:#FFF;
				border-bottom:dotted 1px #DEEFF8;
			}
			.tabs_header{
				border-right:0px;
				border-top:0px;
				background:#FFF;
				border-left:0px;
			}
			.tabs-wrap{
				background:#FFF;
				margin-top:-2px;
				border-right:0px;
			}
			#shclNs{
				width:100px;
				height:100px;
			}
			.myns > div {
				box-shadow: 0 0 3px #3498DB,inset 0 0 3px #3498DB;
			}
			.panel-header{
				height:31px;
				background:#FFF;
				border-bottom:0px;
				border-top:0px;
			}
			.panel-title{
				border:0px;
			}
			.tabs-panels{
				border:0px;
			}
			.ttBox{
				border:0px;
			}
		</style>	
		<script type="text/javascript">
			function updateTip(newMessage){
				$("#countWords").html(newMessage);
			}
			var iframeHeight = 0;
			var acheObject = null;
			$(document).ready(function(){
				var $height = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
				/*适配主编辑区域高度*/
			    $("#mainLayout").css({
			    	"height":$height-$("#topFixed").height()-$("#fixedBottom").height()+1
			    });
			    /*适配Tab*/
			    $("#divTabs").tabs({
			    	height:$("#mainLayout").height()-2
			    })
				iframeHeight = parseInt($height-$("#topFixed").height()-$("#fixedBottom").height()-56);
			    /*加载中...*/
				$("#shclNs").shCircleLoader({
					namespace:"myns",
					color:"#6BC2EB",
					dotsRadius:15
				});
				$("#shclNs").show();
				/*初始化菜单...*/
				initTopMenus();
			});
			function initTopMenus(){
				$.ajax({
					url:"${pageContext.request.contextPath}/sysmenuConfigAction/findIndexMenus.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObject){
						$("#shclNs").hide();
						if(resultObject["IS_LOGINED"]){
							if(resultObject["IS_BINDED_ROLE"]){
								if(resultObject["IS_ROLE_MENUED"]){
									if(resultObject["IS_HAVE_LEAF"]=="N"){
										window.wxc.xcConfirm("当前用户可用菜单为空，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
									}else{
										var loginUser = resultObject["LOGIN_USER"];
										$("#USER_ACCOUNT_INFO").val(loginUser["USER_NAME"]);
										$("#employeeNameShow").html(loginUser["EMPLOYEE_NAME"]);
										/*渲染通知*/
										$("#countWords").html(resultObject["TIPS"]);
										/*渲染顶部菜单*/
										var describeHtml = "";
										var textStatus = resultObject["MENUS"];						
										if(textStatus!=null && textStatus!=undefined && textStatus.length>0){
											if(acheObject==null){
												acheObject = new HashMap();
											}
											var menuString = "<ul class='nav pull-right clearfix'>";
											for(var i=0;i<textStatus.length;i++){
												var menuObject = textStatus[i];
												acheObject.put(menuObject["ID"],menuObject);
												if(i==0){
													flushLeftMenus(menuObject["ID"]);
												}
												if(menuObject["TOP_MENU_TYPE"]!=null && menuObject["TOP_MENU_TYPE"]=="DATA_CENTER"){
													/*DQM菜单*/
													menuString+="<li class='topLis nomalmenu' id='LI"+menuObject["ID"]+"' style='display:none;'>";
														menuString+="<a style='cursor:pointer;' onclick='javascript:flushLeftMenus("+menuObject["ID"]+");'>";
															menuString+="<img class='nav-img' src='"+menuObject["ICON_NAME"]+"' style='width:30px;height:30px;'></img>";
															menuString+="<div style='margin-top:5px;'><span class='nav-text'>"+menuObject["MENU_NAME"]+"</span></div>";
														menuString+="</a>";
													menuString+="</li>";
												}else if(menuObject["TOP_MENU_TYPE"]!=null && menuObject["TOP_MENU_TYPE"]=="DEPARTS"){
													var leafCharts = menuObject["CHILDREN"];
													if(leafCharts!=null && leafCharts.length>0){
														/*系统门户[部门列表]*/
														menuString+="<li class='topLis departmenu' id='LI"+menuObject["ID"]+"'>";
															menuString+="<a style='cursor:pointer;' onclick='javascript:flushLeftMenus("+menuObject["ID"]+");'>";
																menuString+="<img class='nav-img' src='"+menuObject["ICON_NAME"]+"' style='width:30px;height:30px;'></img>";
																menuString+="<div style='margin-top:5px;'><span class='nav-text'>"+menuObject["MENU_NAME"]+"</span></div>";
															menuString+="</a>";
														menuString+="</li>";	
													}else{
														menuString+="<li class='topLis departmenu' id='LI"+menuObject["ID"]+"'>";
															menuString+="<a style='cursor:pointer;' onclick='javascript:flushLeftMenus("+menuObject["ID"]+");'>";
																menuString+="<img class='nav-img' src='"+menuObject["ICON_NAME"]+"' style='width:30px;height:30px;'></img>";
																menuString+="<div style='margin-top:5px;'><span class='nav-text'>"+menuObject["MENU_NAME"]+"</span></div>";
															menuString+="</a>";
														menuString+="</li>";
													}
													/*
														渲染末梢报表
													 */
													if(leafCharts!=null && leafCharts.length>0){
														for(var h=0;h<leafCharts.length;h++){
															var leafChart = leafCharts[h];
															acheObject.put(leafChart["ID"],leafChart);
															menuString+="<li class='topLis "+menuObject["MENU_EN_NAME"]+"' id='LI"+leafChart["ID"]+"' style='display:none;'>";
																menuString+="<a style='cursor:pointer;' onclick='javascript:flushLeftMenus("+leafChart["ID"]+");'>";
																	menuString+="<img class='nav-img' src='"+leafChart["ICON_NAME"]+"' style='width:30px;height:30px;'></img>";
																	menuString+="<div style='margin-top:5px;'><span class='nav-text' style='font-size:10px;'>"+leafChart["MENU_NAME"]+"</span></div>";
																menuString+="</a>";
															menuString+="</li>";
															if(h==(leafCharts.length-1)){
																menuString+="<li class='topLis "+menuObject["MENU_EN_NAME"]+"' id='LI"+leafChart["ID"]+"' style='display:none;'>";
																	menuString+="<a style='cursor:pointer;' onclick='javascript:rebackMenus();'>";
																		menuString+="<img class='nav-img' src='icons/reback.png' style='width:30px;height:30px;'></img>";
																		menuString+="<div style='margin-top:5px;'><span class='nav-text'>返回</span></div>";
																	menuString+="</a>";
																menuString+="</li>";
															}
															/*最后往下钻两级*/
															var chartTypes = leafChart["CHILDREN"];
															if(chartTypes!=null && chartTypes.length>0){
																for(var z=0;z<chartTypes.length;z++){
																	var chartType = chartTypes[z];
																	acheObject.put(chartType["ID"],chartType);
																	var charts = chartType["CHILDREN"];
																	if(charts!=null && charts.length>0){
																		for(var s=0;s<charts.length;s++){
																			var chart = charts[s];
																			acheObject.put(chart["ID"],chart);
																		}
																	}
																}
															}
														}
													}
												}
												if(textStatus!=null && textStatus!=undefined && i==(textStatus.length-1)){
													/*系统门户[数据中心]*/
													//menuString+="<li class='topLis departmenu'>";
													//	menuString+="<a style='cursor:pointer;' onclick='javascript:rebackNormalMenu();'>";
													//		menuString+="<img class='nav-img' src='icons/DataCenter.png' style='width:30px;height:30px;'></img>";
													//		menuString+="<div style='margin-top:5px;'><span class='nav-text'>数据中心</span></div>";
													//	menuString+="</a>";
													//menuString+="</li>";
												}
												/*
													缓存剩余的其他菜单信息
												 */
												if(menuObject["HAS_CHILD"]=="Y"){
													var childMenus = menuObject["CHILDREN"];
													for(var j=0;j<childMenus.length;j++){
														var childMenu = childMenus[j];
														acheObject.put(childMenu["ID"],childMenu);
														if(childMenu["HAS_CHILD"]=="Y"){												
															var leafMenus = childMenu["CHILDREN"];												
															for(var w=0;w<leafMenus.length;w++){													
																var leafMenu = leafMenus[w];	
																acheObject.put(leafMenu["ID"],leafMenu);
															}
														}
													}	
												}
											}
											menuString+="</ul>";
											document.getElementById("topContainer").innerHTML = menuString;
										}	
									}
								}else{
									window.wxc.xcConfirm("当前登录用户在此系统中不存在功能权限.","custom",{
										title:"提示",
										btn:parseInt("0011",2),
										onOk:function(){
											window.location.href = "${pageContext.request.contextPath}/login.jsp";
										}
									});
								}
							}else{
								window.wxc.xcConfirm("当前登录用户尚未绑定角色信息，请联系系统管理员.","custom",{
									title:"提示",
									btn:parseInt("0011",2),
									onOk:function(){
										window.location.href = "${pageContext.request.contextPath}/login.jsp";
									}
								});
							}
						}else{
							window.wxc.xcConfirm("未检测到登录信息，请先登录.","custom",{
								title:"提示",
								btn:parseInt("0011",2),
								onOk:function(){
									window.location.href = "${pageContext.request.contextPath}/login.jsp";
								}
							});
						}
					},
					error:function(){
						$("#shclNs").hide();
					}
				});
			}
			function showOrHide(secondMenuCode){
				/*初始化菜单.1.隐藏所有三级菜单.2.圆润所有二级菜单*/
				$(".SUBMENUS").hide(300);
				$(".SUBMENUS").css({
					"background":"#FFF",
				});
				$(".SECOND_MENUS").css({
					"border-bottom-left-radius":"10px",
					"border-bottom-right-radius":"10px"
				});
				/*展示选定的菜单*/
				$("#SECOND_MENU_"+secondMenuCode).css({
					"border-bottom-left-radius":"0px",
					"border-bottom-right-radius":"0px"
				});
				$(".SUB_OF_"+secondMenuCode).show(500);
			}
			function HoverMenu(overCode){
				$(".SUBMENUS").css({
					"background":"#FFF",
				});
				$("#SUB_MENU_"+overCode).css({
					"background":"#DEEFF8"
				});
			}
			function RemoveHover(){
				$(".SUBMENUS").css({
					"background":"#FFF"
				});
			}
			var firstMenuCode = null;
			var isExpanded = true;
			/*
					====================== 模拟顶部菜单点击效果   ======================  	
			  */
			function flushLeftMenusByOther(topMenuCode){
				var secondCodes = null;
				if(topMenuCode==14){
					/*工程进度管理*/
					secondCodes = 23;
				}else if(topMenuCode==15){
					/*外验交付确认*/
					secondCodes = 24;
				}
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findMySecondAndLeafsOfTop.ilf",
					async:false,
					type:"POST",
					data:"secondIds="+secondCodes,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							var secondMenus = textStatus["MENUS"];
							if($(".layout-button-right")!=null){
								$(".layout-button-right").click();
								isExpanded = true;
							}
							$(".panel-header").css({
								"border-right":"0px"
							});
							$(".panel-body").css({
								"border-right":"0px"
							});
							var menuHtml = "";
							for(var i=0;i<secondMenus.length;i++){			
								var secondMenu = secondMenus[i];
								if(i==0){
									firstMenuCode = secondMenu["ID"];
								}
								menuHtml+="<div class='SECOND_MENUS' id='SECOND_MENU_"+secondMenu["ID"]+"' onclick='javascript:showOrHide("+secondMenu["ID"]+");' style='width:75%;height:80px;margin-top:10px;border:solid 1px #3498DB;box-shadow:0 0 10px #3498DB;border-top-left-radius:15px;border-top-right-radius:15px;border-bottom-left-radius:15px;border-bottom-right-radius:15px;cursor:pointer;'>";
								menuHtml+="	  <div style='width:100%;margin-top:10px;'>";
								menuHtml+="		  <img src='${pageContext.request.contextPath}/icons/menu/"+secondMenu["ICON_NAME"]+"' style='width:30%;height:40%;'></img>";
								menuHtml+="	  </div>";
								menuHtml+="	  <div style='width:100%;margin-top:10px;'>";
								menuHtml+="		  <font size='2' style='font-family:'宋体';font-weight:bold;'>"+secondMenu["MENU_NAME"]+"</font>";
								menuHtml+="	  </div>";
								menuHtml+="</div>";
								/*组装三级菜单*/						
								if(secondMenu["HAS_CHILD"]=="Y"){								
									var leafMenus = secondMenu["CHILDREN"];
									for(var j=0;j<leafMenus.length;j++){	
										var leafMenu = leafMenus[j];
										if(i==0 && j==0){ 
											setTimeout("turnJsp("+leafMenu["ID"]+");",1000);
										}
										if(leafMenus.length==1){
											menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
										}else{
											if(j==(leafMenus.length-1)){
												menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
											}else{
												menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border-left:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-right:solid 1px #3498DB;display:none;cursor:pointer;'>";
											}
										}
										menuHtml+="	  <div style='width:100%;height:35px;'>";
										menuHtml+="		  <div style='float:left;width:25%;height:34px;'>";
										menuHtml+="			  <img src='${pageContext.request.contextPath}/icons/menu/"+leafMenu["ICON_NAME"]+"' style='width:50%;height:15px;margin-top:8px;'></img>";
										menuHtml+="		  </div>";
										menuHtml+="		  <div style='float:left;width:73%;height:34px;'>";
										menuHtml+="			  <font size='2' style='float:left;margin-top:8px;margin-left:0px;font-family:'宋体';'>"+leafMenu["MENU_NAME"]+"</font>";
										menuHtml+="		  </div>";
										menuHtml+="	  </div>";
										menuHtml+="</div>";
									}
								}else{
									if(i==0){
										setTimeout("turnJsp("+secondMenu["ID"]+");",700);	
									}	
									menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+secondMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+secondMenu["ID"]+");' onclick='javascript:turnJsp("+secondMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
									menuHtml+="	  <div style='width:100%;height:40px;'>";
									menuHtml+="		  <div style='float:left;width:25%;height:34px;'>";
									menuHtml+="			  <img src='${pageContext.request.contextPath}/icons/menu/"+secondMenu["ICON_NAME"]+"' style='width:50%;height:15px;margin-top:8px;'></img>";
									menuHtml+="		  </div>";
									menuHtml+="		  <div style='float:left;width:73%;height:34px;'>";
									menuHtml+="			  <font size='2' style='float:left;margin-top:8px;margin-left:0px;font-family:'宋体';'>"+secondMenu["MENU_NAME"]+"</font>";
									menuHtml+="		  </div>";
									menuHtml+="	  </div>";
									menuHtml+="</div>";
								}
							}
							document.getElementById("menuContainer").innerHTML = menuHtml;
							if(firstMenuCode!=null && document.getElementById("SECOND_MENU_"+firstMenuCode)!=null){
								document.getElementById("SECOND_MENU_"+firstMenuCode).click();	
							}
						}
					},
					error:function(){}
				});
			}
			function flushLeftMenus(topMenuCode){
				/*所有菜单移除[被选中]样式*/
				$(".topLis").removeClass("active");
				/*指定菜单添加[被选中]样式*/
				$("#LI"+topMenuCode).addClass("active");
				var topMenu = acheObject.get(topMenuCode);
				if(topMenu!=null){
					$.ajax({
						url:"${pageContext.request.contextPath}/loginAction/logFunctionUse.ilf",
						async:true,
						type:"POST",
						data:"MENU_CODE="+topMenuCode,
						dataType:"json",
						timeout:10000, 
						success:function(resultObj){
							if(!resultObj["SUCCESS"]){
								window.wxc.xcConfirm("操作日志模块异常，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
							}
						}
					});
				}
				if(topMenu["TOP_MENU_TYPE"]=="DEPARTS" && topMenu["MENU_NAME"]!="系统管理"){
					$(".topLis").hide();
					var enClass = topMenu["MENU_EN_NAME"];
					$("."+enClass).show(500);
				}else{
					if(topMenu["IS_LEAF"]=="Y" && topMenu["IS_EXPORT"]=="Y"){
						var menuUrl = topMenu["MENU_URL"];
						if(menuUrl!=null && menuUrl!="" && menuUrl.indexOf("USER_NAME")!=-1){
							menuUrl = menuUrl.replace("USER_NAME",$("#USER_ACCOUNT_INFO").val());
						}
						window.open(menuUrl);
					}else if(topMenu["HAS_CHILD"]=="N" && topMenu["IS_LEAF"]=="Y"){
						isExpanded = false;
						$(".layout-button-left").click();
						$(".panel-header").css({
							"background":"#FFF",
							"border-right":"solid 1px #FFF"
						});
						$(".panel-body").css({
							"background":"#FFF",
							"border-right":"solid 1px #FFF"
						});
						setTimeout("turnJsp("+topMenu["ID"]+");",700);
						if(topMenu["MENU_NAME"]=="返回门户"){
							$(".topLis").hide();
							$(".departmenu").show(500);
						}
					}else{
						firstMenuCode = null;
						if(!isExpanded){
							$(".layout-button-right").click();
							isExpanded = true;
						}
						$(".panel-header").css({
							"border-right":"0px"
						});
						$(".panel-body").css({
							"border-right":"0px"
						});
						var menuHtml = "";
						var secondMenus = topMenu["CHILDREN"];
						for(var i=0;i<secondMenus.length;i++){			
							var secondMenu = secondMenus[i];
							if(i==0){
								firstMenuCode = secondMenu["ID"];
							}
							menuHtml+="<div class='SECOND_MENUS' id='SECOND_MENU_"+secondMenu["ID"]+"' onclick='javascript:showOrHide("+secondMenu["ID"]+");' style='width:75%;height:80px;margin-top:10px;border:solid 1px #3498DB;box-shadow:0 0 10px #3498DB;border-top-left-radius:15px;border-top-right-radius:15px;border-bottom-left-radius:15px;border-bottom-right-radius:15px;cursor:pointer;'>";
							menuHtml+="	  <div style='width:100%;margin-top:10px;'>";
							menuHtml+="		  <img src='${pageContext.request.contextPath}/icons/menu/"+secondMenu["ICON_NAME"]+"' style='width:30%;height:40%;'></img>";
							menuHtml+="	  </div>";
							menuHtml+="	  <div style='width:100%;margin-top:10px;'>";
							menuHtml+="		  <font size='2' style='font-family:'宋体';font-weight:bold;'>"+secondMenu["MENU_NAME"]+"</font>";
							menuHtml+="	  </div>";
							menuHtml+="</div>";
							/*组装三级菜单*/						
							if(secondMenu["HAS_CHILD"]=="Y"){								
								var leafMenus = secondMenu["CHILDREN"];
								for(var j=0;j<leafMenus.length;j++){	
									var leafMenu = leafMenus[j];
									if(i==0 && j==0){ 
										setTimeout("turnJsp("+leafMenu["ID"]+");",1000);
									}
									if(leafMenus.length==1){
										menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
									}else{
										if(j==(leafMenus.length-1)){
											menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
										}else{
											menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+leafMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+leafMenu["ID"]+");' onclick='javascript:turnJsp("+leafMenu["ID"]+");' style='width:75%;height:35px;border-left:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-right:solid 1px #3498DB;display:none;cursor:pointer;'>";
										}
									}
									menuHtml+="	  <div style='width:100%;height:35px;'>";
									menuHtml+="		  <div style='float:left;width:25%;height:34px;'>";
									menuHtml+="			  <img src='${pageContext.request.contextPath}/icons/menu/"+leafMenu["ICON_NAME"]+"' style='width:50%;height:15px;margin-top:8px;'></img>";
									menuHtml+="		  </div>";
									menuHtml+="		  <div style='float:left;width:73%;height:34px;'>";
									menuHtml+="			  <font size='2' style='float:left;margin-top:8px;margin-left:0px;font-family:'宋体';'>"+leafMenu["MENU_NAME"]+"</font>";
									menuHtml+="		  </div>";
									menuHtml+="	  </div>";
									menuHtml+="</div>";
								}
							}else{
								if(i==0){
									setTimeout("turnJsp("+secondMenu["ID"]+");",700);	
								}	
								menuHtml+="<div class='SUBMENUS SUB_OF_"+secondMenu["ID"]+"' id='SUB_MENU_"+secondMenu["ID"]+"' onmouseout='javascript:RemoveHover();' onmouseover='javascript:HoverMenu("+secondMenu["ID"]+");' onclick='javascript:turnJsp("+secondMenu["ID"]+");' style='width:75%;height:35px;border:solid 1px #3498DB;box-shadow:0 0 5px #3498DB;border-top:0px;display:none;cursor:pointer;'>";
								menuHtml+="	  <div style='width:100%;height:40px;'>";
								menuHtml+="		  <div style='float:left;width:25%;height:34px;'>";
								menuHtml+="			  <img src='${pageContext.request.contextPath}/icons/menu/"+secondMenu["ICON_NAME"]+"' style='width:50%;height:15px;margin-top:8px;'></img>";
								menuHtml+="		  </div>";
								menuHtml+="		  <div style='float:left;width:73%;height:34px;'>";
								menuHtml+="			  <font size='2' style='float:left;margin-top:8px;margin-left:0px;font-family:'宋体';'>"+secondMenu["MENU_NAME"]+"</font>";
								menuHtml+="		  </div>";
								menuHtml+="	  </div>";
								menuHtml+="</div>";
							}
						}
						document.getElementById("menuContainer").innerHTML = menuHtml;
						/*展开第一级*/
						if(firstMenuCode!=null && document.getElementById("SECOND_MENU_"+firstMenuCode)!=null){
							document.getElementById("SECOND_MENU_"+firstMenuCode).click();	
						}
					}
				}
			}
			function turnJsp(menuCode){
				if(acheObject!=null){
					var leafMenu = acheObject.get(menuCode);
					if(leafMenu!=null){
						/*
							 记录点击事件.
						  */
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
						/*
							 跳转页面.
						  */
						var leafTitle = leafMenu["MENU_NAME"];
						if(leafTitle=="返回门户"){
							leafTitle = "系统门户";
						}
						if($("#divTabs").tabs("exists",leafTitle)){
							$("#divTabs").tabs("select",leafTitle);
						}else{
							var menuUrl = leafMenu["MENU_URL"];
							if(menuUrl!=null && menuUrl!="" && menuUrl.indexOf("USER_NAME")!=-1){
								menuUrl = menuUrl.replace("USER_NAME",$("#USER_ACCOUNT_INFO").val());
							}
							if(leafMenu["IS_EXPORT"]=="Y"){
								$("#divTabs").tabs("add",{
									title:leafTitle,
									content:"<iframe frameborder='0' style='width:100%;height:100%;overflow:hidden;border:solid 1px #FFF;' scrolling='no' src='"+menuUrl+"'></iframe>",
									selected:true,
									closable:true
								});
								$(".tabs-inner").css({
							    	"border-radius":"0px",
							    	"border":"solid 1px #57BEDD",
							    	"box-shadow":"0px 0px 3px #57BEDD",
							    	"border-bottom":"0px"
							    });
							}else{
								$("#divTabs").tabs("add",{
									title:leafTitle,
									content:"<iframe frameborder='0' style='width:100%;height:100%;overflow:hidden;border:solid 1px #FFF;' scrolling='no' src='${pageContext.request.contextPath}/"+menuUrl+"?frameHeight="+iframeHeight+"'></iframe>",
									selected:true,
									closable:true
								});
								$(".tabs-inner").css({
							    	"border-radius":"0px",
							    	"border":"solid 1px #57BEDD",
							    	"box-shadow":"0px 0px 3px #57BEDD",
							    	"border-bottom":"0px"
							    });
							}
						}			
					}
				}
			}
			function turnToJsp(title,targetJsp){
				if(targetJsp!=null && targetJsp!="" && targetJsp.indexOf("USER_NAME")!=-1){
					targetJsp = targetJsp.replace("USER_NAME",$("#USER_ACCOUNT_INFO").val());
				}
				if(targetJsp.indexOf("?")==-1){
					$("#divTabs").tabs("add",{
						title:title,
						content:"<iframe frameborder='0' style='width:100%;height:100%;background:#fff;border:solid 1px #FFF;' scrolling='no' src='${pageContext.request.contextPath}/"+targetJsp+"?frameHeight="+iframeHeight+"'></iframe>",
						selected:true,
						closable:true
					});
					$(".tabs-inner").css({
				    	"border-radius":"0px",
				    	"border":"solid 1px #57BEDD",
				    	"box-shadow":"0px 0px 3px #57BEDD",
				    	"border-bottom":"0px"
				    });
				}else{
					$("#divTabs").tabs("add",{
						title:title,
						content:"<iframe frameborder='0' style='width:100%;height:100%;background:#fff;border:solid 1px #FFF;' scrolling='no' src='${pageContext.request.contextPath}/"+targetJsp+"&frameHeight="+iframeHeight+"'></iframe>",
						selected:true,
						closable:true
					});
					$(".tabs-inner").css({
				    	"border-radius":"0px",
				    	"border":"solid 1px #57BEDD",
				    	"box-shadow":"0px 0px 3px #57BEDD",
				    	"border-bottom":"0px"
				    });
				}
			}
			function closeAssignedTab(tabName){
				if($("#divTabs").tabs("exists",tabName)){
					$("#divTabs").tabs("close",tabName);
				}
			}
			function quitSystem(){
				window.wxc.xcConfirm("是否确认退出登录？","custom",{
					title:"提示",
					btn:parseInt("0011",2),
					onOk:function(){
						var date = new Date();
						var longTime = date.getTime();
						$.ajax({
							url:"${pageContext.request.contextPath}/loginAction/quit.ilf",
							async:false,
							type:"POST",
							data:"thisTime="+longTime,
							dataType:"json",
							success:function(textStatus){
								if(textStatus["success"]){
									window.location.href = "${pageContext.request.contextPath}";
								}
							},
							error:function(){}
						});
					}
				});
			}
			function modifyPassword(){
				window.location.href = "${pageContext.request.contextPath}/changePassword.jsp";
			}
			function loading(isFullScreen){
				$("#shclNs").show();
			}
			function overLoading(isFullScreen){
				$("#shclNs").hide();	
			}
			function showLoading(marginTop,marginRight){
				$("#shclNs").css({
			    	"top":marginTop+"%",
			    	"right":marginRight+"%"
			    });
				$("#shclNs").show();
			}
			function hideLoading(){
				$("#shclNs").css({
			    	"top":"45%",
			    	"right":"48%"
			    });
				$("#shclNs").hide();
			}
			/*
				=================================== 部门按钮. ===================================
			 */
			function viewChartMenu(zOrder){
				$(".topLis").hide();
				if(parseInt(zOrder)==1){
					$(".cwMenus").show(500);
				}else if(parseInt(zOrder)==2){
					$(".yfMenus").show(500);
				}else if(parseInt(zOrder)==3){
					$(".whMenus").show(500);
				}else if(parseInt(zOrder)==4){
					$(".sjMenus").show(500);
				}else if(parseInt(zOrder)==5){
					$(".zhMenus").show(500);
				}
			}
			function rebackNormalMenu(){
				if(isExpanded){
					if($("#divTabs").tabs("exists","系统门户")){
						$("#divTabs").tabs("close","系统门户");
					}
					$(".layout-button-left").click();
					isExpanded = false;
					$(".panel-header").css({
						"border-right":"0px"
					});
					$(".panel-body").css({
						"border-right":"0px"
					});
					setTimeout("turnJsp(2);",700);
				}
				$(".topLis").hide();
				$(".nomalmenu").show(500);
			}
			function rebackMenus(){
				$(".topLis").hide();
				$(".departmenu").show(500);
			}
			function flushFirChart(nodeCode){
				$(".topLis").removeClass("active");
				$("#LI"+nodeCode).addClass("active");
				var chartMenu = acheObject.get(nodeCode);
				isExpanded = false;
				$(".layout-button-left").click();
				$(".panel-header").css({
					"border-right":"0px"
				});
				$(".panel-body").css({
					"border-right":"0px"
				});
				if($("#divTabs").tabs("exists",chartMenu["MENU_NAME"])){
					$("#divTabs").tabs("select",chartMenu["MENU_NAME"]);
				}else{
					$("#divTabs").tabs("add",{
						title:chartMenu["MENU_NAME"],
						content:"<iframe frameborder='0' style='width:100%;height:100%;overflow:hidden;border:solid 1px #FFF;' scrolling='no' src='"+chartMenu["MENU_URL"]+"'></iframe>",
						selected:true,
						closable:true
					});
				}
			}
			function returnToMain(){
				window.location.href = "${pageContext.request.contextPath}/index.jsp";
			}
		</script>
    </head>
    <body>
    	<input type="hidden" id="USER_ACCOUNT_INFO" value=""></input>
    	<div class="navbar navbar-fixed-top" style="height:72px;border:solid 0px red;" id="topFixed">
    		<%--第一部分：一级菜单--%>
			<div class="navbar-inner" style="height:70px;">
				<div class="container">
					<div class="head-v3">
						<div style="height:70px;margin-right:-25px;margin-left:-20px;">
							<a class="brand">
								<img onclick="javascript:returnToMain();" src="${pageContext.request.contextPath}/img/LOGO_1.png" style="cursor:pointer;width:280px;height:50px;border:solid 0px red;margin-top:-10px;" alt=""/>
								<%--<div style="float:left;">
									<img src="${pageContext.request.contextPath}/img/TowerLogo.png" style="width:45px;height:38px;margin-top:-5px;margin-left:20px;" alt=""></img>
								</div>
								<div style="float:left;margin-top:5px;">
									<font color="white" style="font-family:'宋体';margin-left:-8px;">·四川铁塔运营支撑平台</font>
								</div>--%>
							</a>
							<div style="width:90px;height:70px;border:solid 0px red;float:right;margin-right:10px;">
								<%--<div style="float:left;height:50px;width:50px;margin-top:9px;">
									<img src="${pageContext.request.contextPath}/icons/usericon/user_1.png" style="height:50px;width:50px;"/>
								</div>--%>
								<div style="float:left;height:50px;width:70px;margin-top:9px;margin-left:7px;">
									<div style="width:65px;height:20px;border:solid 0px red;margin-top:4px;">
										<font color="white" size="2" id="employeeNameShow">&nbsp;</font>
									</div>
									<div style="width:65px;height:20px;border:solid 0px red;margin-top:4px;cursor:pointer;" onclick="javascript:quitSystem();">
										<font color="white" size="2">退出登录</font>
									</div>
								</div>
							</div>
							<div class="navigation-inner" style="float:right;border:solid 0px red;">
								<div class="container" id="topContainer"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%--第二部分：提示滚动--%>
			<div class="navbar-inner-second" id="tipFixed" style="display:none;">
				<center>
					<marquee style="width:99.7%;height:20px;margin-top:10px;" scrollamount="2" direction="Right">
						<font face="Courier New" size="2" id="countWords" color="white">暂无通知。</font>
					</marquee>
				</center>
			</div>
		</div>
		<%--第三部分：主界面--%>
		<div class="easyui-layout" data-options="border:false,split:false" style="border:solid 0px red;width:99.9%;height:1035px;margin-top:70px;" id="mainLayout">
			<div region="west" data-options="border:false,split:false" title=" " style="width:200px;">
				<div class="left">
					<center>
						<div style="width:190px;">
							<div class="menuContainer" style="width:100%;padding-bottom:10px;" id="menuContainer"></div>
						</div>
					</center>
				</div>
			</div>
			<div region="center" style="overflow:hidden;" data-options="border:false,split:false" id="mainCenter">
				<div class="easyui-tabs" style="overflow:hidden;border:solid 1px #FFF;" id="divTabs"></div>
			</div>
		</div>
		<%--第四部分：固定到底部，提示.--%>
    	<div class="navbar navbar-fixed-bottom" style="height:30px;" id="fixedBottom">
			<div class="navbar-inner">
				<div class="container">
					<div class="footer-text pull-left">浪潮天元通信信息系统有限公司</div>
					<div class="footer-text pull-right">版本信息：2017.NO.SC138</div>
				</div>
			</div>
		</div>
		<%--第五部分：加载中...--%>
		<div id="shclNs" style="position:fixed;top:45%;right:45%;display:none;"></div>
  	</body>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
</html>
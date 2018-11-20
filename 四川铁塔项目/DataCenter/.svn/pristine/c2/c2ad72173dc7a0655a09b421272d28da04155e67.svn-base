<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>首页</title>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/frame.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index/indexStyle.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/leftMenus/css/style.css"></link>	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/topMenus/css/indexStyle.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/leftMenuses/style.css">		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/quickNavigate/css/style.css">    	
    	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/quickNavigate/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/quickNavigate/js/js.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/quickNavigate/js/jquery.transform.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<style type="text/css">
			.tabs{
				background:#F3F3F3;
				height:28px;	
			}
		</style>	
		<script type="text/javascript">
			var acheObject = null;
			$(document).ready(function(){
				$("#divTabs").tabs({ 
					width:$("#divTabs").parent().width(), 
					height:$("#divTabs").parent().height()
				}); 
				initTopMenus();
				$(".PathItem").hover(function(){
					$(this).find(".metaicondetail").show();
				},function(){
					$(this).find(".metaicondetail").hide();
				});
			});
			function initTopMenus(){
				$.ajax({
					url:"${pageContext.request.contextPath}/sysmenuConfigAction/findIndexMenus.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObject){
						if(resultObject["IS_LOGINED"]){
							if(resultObject["IS_ROLE_USING"]){
								if(resultObject["IS_HAVE_LEAF"]=="N"){
									window.wxc.xcConfirm("当前用户可用菜单为空，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
								}else{
									var loginUser = resultObject["LOGIN_USER"];
									/*渲染登录用户*/
									$("#helloWords").html(loginUser["HELLO_MESSAGE"]);
									/*渲染通知*/
									$("#countWords").html(resultObject["TIPS"]);
									/*渲染顶部菜单*/
									var describeHtml = "";
									var textStatus = resultObject["MENUS"];						
									if(textStatus.length>0){
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
											if(i==0){
												menuString+="<li class='topLis active' id='LI"+menuObject["ID"]+"'>";	
											}else{
												menuString+="<li class='topLis' id='LI"+menuObject["ID"]+"'>";
											}
											menuString+="<a onclick='javascript:flushLeftMenus("+menuObject["ID"]+")' style='cursor:pointer;'>";
												menuString+="<img class='nav-img' src='"+menuObject["ICON_NAME"]+"'>";
												menuString+="<span class='nav-text'>"+menuObject["MENU_NAME"]+"</span>";
											menuString+="</a>";
											menuString+="</li>";		
											/*缓存剩余的其他菜单信息*/
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
								window.wxc.xcConfirm("当前用户的角色信息不可用，请联系系统管理员.",window.wxc.xcConfirm.typeEnum.info);
							}
						}else{
							/*如果检测到系统还未登录、则直接跳转至登录界面*/
							window.location.href = "${pageContext.request.contextPath}/login.jsp";
						}
					},
					error:function(){}
				});
			}
			function initLeftMenus(){
				$(".leftsidebar_box dt").css({
					"background-color":"#3498DB"
				});
				$(".leftsidebar_box dt img").attr("src","${pageContext.request.contextPath}/extensions/leftMenus/images/left/select_xl01.png");
				$(function(){
					$(".leftsidebar_box dd").hide();
					$(".leftsidebar_box dt").click(function(){
						$(".leftsidebar_box dt").css({"background-color":"#3498DB"})
						$(this).css({"background-color": "##3498DB"});
						$(this).parent().find('dd').removeClass("menu_chioce");
						$(".leftsidebar_box dt img").attr("src","${pageContext.request.contextPath}/extensions/leftMenus/images/left/select_xl01.png");
						$(this).parent().find('img').attr("src","${pageContext.request.contextPath}/extensions/leftMenus/images/left/select_xl.png");
						$(".menu_chioce").slideUp(); 
						$(this).parent().find('dd').slideToggle();
						$(this).parent().find('dd').addClass("menu_chioce");
					});
				});
			}
			var firstMenuCode = null;
			var isExpanded = true;
			function flushLeftMenus(topMenuCode){
				var topMenu = acheObject.get(topMenuCode);
				if(topMenu["HAS_CHILD"]=="N" && topMenu["IS_LEAF"]=="Y"){
					isExpanded = false;
					$(".layout-button-left").click();
					setTimeout("turnJsp("+topMenu["ID"]+");",700);
				}else{
					$(".layout-button-right").click();
					isExpanded = true;					
					var menuHtml = "";					
					var secondMenus = topMenu["CHILDREN"];
					for(var i=0;i<secondMenus.length;i++){
						var secondMenu = secondMenus[i];
						if(i==0){
							firstMenuCode = secondMenu["ID"];
						}
						/*组装二级菜单*/
						menuHtml+="<div class='mainMenu'>";
							menuHtml+="<div class='menu"+i+"'></div>"+secondMenu["MENU_NAME"];
						menuHtml+="</div>";	
						/*组装三级菜单*/						
						if(secondMenu["HAS_CHILD"]=="Y"){								
							var leafMenus = secondMenu["CHILDREN"];
							menuHtml+="<div class='subMenu'>";
								menuHtml+="<ul>";							
							for(var j=0;j<leafMenus.length;j++){	
								var leafMenu = leafMenus[j];
								if(i==0 && j==0){ 
									setTimeout("turnJsp("+leafMenu["ID"]+");",1000);
								}											
								menuHtml+="<li onclick='javascript:turnJsp("+leafMenu["ID"]+");'>"+leafMenu["MENU_NAME"]+"</li>";
							}
								menuHtml+="</ul>";
							menuHtml+="</div>";	
						}else{
							if(i==0){
								setTimeout("turnJsp("+secondMenu["ID"]+");",700);	
							}	
							menuHtml+="<div class='subMenu'>";
								menuHtml+="<ul>";
									menuHtml+="<li onclick='javascript:turnJsp("+secondMenu["ID"]+");'>"+secondMenu["MENU_NAME"]+"</li>";
								menuHtml+="</ul>";
							menuHtml+="</div>";			
						}											
					}
					document.getElementById("menuContainer").innerHTML = menuHtml;
					$(".mainMenu").click(function(){ 
						$(this).next("div").slideToggle("fast").siblings(".subMenu:visible").slideUp("fast");
					});
					var mainMenus = document.getElementsByClassName("mainMenu");
					if(mainMenus.length>0){
						var $object = mainMenus[0];
						$object.click();
					}
				}		
			}
			function turnJsp(menuCode){
				if(acheObject!=null){
					var leafMenu = acheObject.get(menuCode);					
					if(!isExpanded){
						$("#divTabs").tabs({ 
							width:document.body.clientWidth-31,
							height:$("#divTabs").parent().height()+1
						});	
					}else{
						$("#divTabs").tabs({ 
							width:document.body.clientWidth-204,
							height:$("#divTabs").parent().height()+1
						});
					}
					if(leafMenu!=null){
						var leafTitle = leafMenu["MENU_NAME"];
						if($("#divTabs").tabs("exists",leafTitle)){
							$("#divTabs").tabs("select",leafTitle);
						}else{
							$("#divTabs").tabs("add",{
								title:leafTitle,
								content:"<iframe frameborder='0' style='width:100%;height:484px;overflow:hidden;' scrolling='no' src='${pageContext.request.contextPath}/"+leafMenu["MENU_URL"]+"'></iframe>",
								selected:true,
								closable:true
							});
						}						
					}
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
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["success"]){
									window.location.href = "${pageContext.request.contextPath}/login.jsp";
								}
							},
							error:function(){}
						});
					}
				});
			}
			function turnToJsp(title,targetJsp){
				$("#divTabs").tabs("add",{
					title:title,
					content:"<iframe frameborder='0' style='width:99.9%;background:#fff' height='470' scrolling='no' src='${pageContext.request.contextPath}/"+targetJsp+"'></iframe>",
					selected:true,
					closable:true
				});
			}
			function modifyPassword(){
				window.location.href = "${pageContext.request.contextPath}/changePassword.jsp";
			}
		</script>
    </head>
    <body>
    	<div class="navbar navbar-fixed-top" style="height:70px">
			<div class="navbar-inner">
				<div class="container">
					<div class="head-v3">
						<div class="navigation-up" style="height:70px;margin-right:-20px;margin-left:-20px;">
						<%--左侧LOGO--%>
						<a class="brand" href="#">
							<img src="${pageContext.request.contextPath}/img/LOGO_1.png" style="margin-top:2px;margin-left:0px;height:34px;width:224px;" alt="">
						</a>
						<%--右侧菜单和登录用户问候--%>
						<div class="navigation-inner" style="float:right;">
							<div class="container" id="topContainer"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--第二部分：提示滚动--%>
		<div class="navbar-inner-second">
			<center>
				<marquee style="width:99%;height:20px;margin-top:10px;" scrollamount="2" direction="Right">
					<font face="Courier New" size="2" id="countWords" color="white">暂无通知。</font>
				</marquee>
			</center>
		</div>
		<div class="content" id="content" style="margin-top:0px">
			<div class="easyui-layout" style="width:100%;height:520px;">
				<div region="west" split="true" title=" " style="width:200px;">
					<div class="left">
						<div class="menuContainer" id="menuContainer"></div>
					</div>
				</div>
				<%--中心（右侧）主界面--%>
				<div id="content" region="center">
					<div style="width:99.8%;height:515px;" id="mainContainer">
						<div class="easyui-tabs" style="width:99%;height:517px;" id="divTabs"></div>
					</div>
				</div>
			</div>
		</div>
		<%--绑定底部面板--%>
    	<div class="navbar navbar-fixed-bottom" style="height:30px;">
			<div class="navbar-inner">
				<div class="container">
					<div class="footer-text pull-left">浪潮通信信息系统有限公司</div>
					<div class="footer-text pull-right">版本信息：2017.NO.00001</div>
				</div>
			</div>
		</div>
  	</body>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
</html>
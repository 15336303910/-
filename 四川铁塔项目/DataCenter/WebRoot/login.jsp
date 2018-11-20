<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>四川铁塔运营支撑系统</title>
    	<meta charset="UTF-8">
    	<link rel="icon" href="${pageContext.request.contextPath}/img/Tower.png" type="image/x-icon">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/content/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/content/css/fream.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>	
		<script type="text/javascript">
			$(document).ready(function(){
				var isCookieUsing = true;
				if(navigator!=null){
					if(navigator.cookieEnabled){
						var date = new Date();
						var longTime = date.getTime();
						$.ajax({
							url:"${pageContext.request.contextPath}/loginAction/loginCookie.ilf",
							async:false,
							type:"POST",
							data:"thisTime="+longTime+"&isLogin=Y",
							dataType:"json",
							timeout:10000, 
							success:function(textStatus){
								if(textStatus["autoAble"]){
									$("#myUserName").val(textStatus["userName"]);
									$("#myPassWord").val(textStatus["passWord"]);
									var checks = document.getElementsByName("isAutoLogin");
									if(checks.length>0){
										checks[0].checked = true;
									}
								}else{
									var checks = document.getElementsByName("isAutoLogin");
									if(checks.length>0){
										checks[0].checked = false;
									}
								}
							},
							error:function(){}
						});
					}else{
						$("#tipDiv").html("<font color='red'>浏览器已禁用Cookie.无法记录账号密码.</font>");
						$("#tipDiv").show(500);
						setTimeout("javascript:hideTipShow();",3000);
						var checks = document.getElementsByName("isAutoLogin");
						if(checks.length>0){
							checks[0].checked = false;
							checks[0].disabled = true;
						}
					}
				}
				/*绑定查询快捷键*/
				$("#myUserName").bind("keydown",function(event){
					if(event.keyCode==13){
						$("#myPassWord").focus();
						return false;
					}
				});
				$("#myPassWord").bind("keydown",function(event){
					if(event.keyCode==13){
						doLogin();
						return false;
					}
				});
				$("#myUserName").focus();
			});
			function doLogin(){
				/*验证数据*/
				var userName = $("#myUserName").val();
				if(userName==""){
					$("#tipDiv").html("<font color='red'>请输入用户名.</font>");
					$("#tipDiv").show(500);
					setTimeout("javascript:hideTipShow();",3000);
					return;
				}else{
					$("#tipDiv").hide(500);
				}	
				var passWord = $("#myPassWord").val();
				if(passWord==""){
					$("#tipDiv").html("<font color='red'>请输入密码.</font>");
					$("#tipDiv").show(500);
					setTimeout("javascript:hideTipShow();",3000);
					return;
				}else{
					$("#tipDiv").hide(500);
				}
				var isAutoLogin = "N";
				var checks = document.getElementsByName("isAutoLogin");
				if(checks.length>0){
					if(checks[0].checked){
						isAutoLogin = "Y";
					}
				}
				/*执行登录*/
				$.ajax({
					url:"${pageContext.request.contextPath}/loginAction/doLogin.ilf",
					async:false,
					type:"POST",
					data:"userName="+userName+"&passWord="+passWord+"&isAutoLogin="+isAutoLogin+"&isLogin=Y",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["isUserExist"]){
							if(textStatus["isLoginSuccess"]){
								window.location.href = "${pageContext.request.contextPath}/index.jsp";
							}else{
								$("#tipDiv").html(textStatus["message"]);
								$("#tipDiv").show(500);
							}						
						}else{
							$("#tipDiv").html(textStatus["message"]);
							$("#tipDiv").show(500);
						}
					},
					error:function(){
						window.wxc.xcConfirm("系统异常.",window.wxc.xcConfirm.typeEnum.error);
					}
				});
			}
			function hideTipShow(){
				$("#tipDiv").hide(500);
			}
		</script>
	</head>  
	<body class="loginbg">
	    <div class="fl w">
	        <div class="L-main">
	            <div class="loginbox">
	                <h2 class="loginboxtitle" style="margin-top:-50px;"></h2>
	                <div class="" style="margin-top:0px;">
	                	<div class="error-information" style="display:none;margin-bottom:10px;" id="tipDiv">您输入的密码不正确，请重新输入</div>
	                    <div class="pr loginformline mb20">
	                        <i class="login-id"></i>
	                        <input type="text" id="myUserName" value="" placeholder="请输入用户名"></input>
	                    </div>
	                    <div class="pr loginformline mb20">
	                        <i class="login-pw"></i>
	                        <input type="password" id="myPassWord" value="" placeholder="请输入密码"></input>
	                    </div>
	                </div>
	                <div class="loginline">
	                    <div class="fl">
	                    	<input type="checkbox" class="vm mt0" name="isAutoLogin" value="Y"></input>
							<span class="vm ml10">记住我</span>
						</div>
	                </div>
	                <div class="loginline mt30" style="cursor:pointer;">
	                    <a onclick="javascript:doLogin();" class="loginbtn"></a>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="fl L-logo">
	        <h1 class="loginlogo">
	        	<div style="height:70px;border-bottom:solid 1px white;">
	        		<font color="white" style="font-family:'宋体'">四川铁塔运营支撑平台</font>
	        	</div>
	        </h1>
	        <p class="loginfoot">浪潮天元通信信息系统有限公司</p>
	    </div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/content/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/content/js/plugins.js"></script>
</html>
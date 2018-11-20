<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>数据质量平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/loginPanel/css/less.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/loginPanel/css/basic.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				var isCookieUsing = true;
				/*浏览器对象是否为空*/
				if(navigator!=null){
					/*判断浏览器是否已禁用Cookie，如果未禁用，则进行自动登录渲染*/
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
						/*如果已禁用Cookie，则进行提示*/
						$("#tipDiv").html("浏览器已禁用Cookie.无法记录账号密码.");
						$("#tipDiv").show(500);
						var checks = document.getElementsByName("isAutoLogin");
						if(checks.length>0){
							checks[0].checked = false;
							checks[0].disabled = true;
						}
					}
				}
			});
			function doLogin(){
				/*验证数据*/
				var userName = $("#myUserName").val();
				if(userName==""){
					$("#tipDiv").html("请输入用户名.");
					$("#tipDiv").show(500);
					return;
				}else{
					$("#tipDiv").hide(500);
				}	
				var passWord = $("#myPassWord").val();
				if(passWord==""){
					$("#tipDiv").html("请输入密码.");
					$("#tipDiv").show(500);
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
		</script>
	</head>  
	<body>
		<div class="wrapper" style="background-color:white;margin-top:-20px;overflow:auto;">
			<div class="login-top">
				<div class="login-topBg">
					<div class="login-topBg1">
						<div class="login-topStyle">
							<div class="login-topStyle3" id="loginStyle" style="margin-top:100px;">
								<h3>数据质量平台</h3>
								<div class="error-information" style="display:none;" id="tipDiv">您输入的密码不正确，请重新输入</div>
								<div class="ui-form-item loginUsername">
									<input type="username" placeholder="请输入用户名" id="myUserName"></input>
								</div>
								<div class="ui-form-item loginPassword">
									<input type="password" placeholder="请输入密码" id="myPassWord"></input>
								</div>
								<div class="login_reme">
									<input type="checkbox" value="Y" name="isAutoLogin"></input>
									<a class="reme1" style="width:110px">记住账号密码</a>
									<!--<a class="reme2" href="password.html" style="margin-left:50px;">忘记密码?</a>-->
								</div>
								<a class="btnStyle btn-register" onclick="javascript:doLogin();">立即登录</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="loginCen" style="margin-top:-30px;">
				<div class="login-center">
					<div class="loginCenter-moudle">
						<div class="loginCenter-moudleLeft" style="margin-right: 60px;">&nbsp;</div>
						<div class="loginCenter-moudleRight" style="width:1067px;">  
							<a class="loginCenter-mStyle loginCenter-moudle1" id="moudleRemove" style=" display:block;width: 340px;">
								<span class="moudle-img">
									<img src="${pageContext.request.contextPath}/extensions/loginPanel/images/login_bg_01.png">
								</span>
								<span class="moudle-text"> 
									<span class="moudle-text1" style="font-family:'微软雅黑'">基础数据查询</span> 
									<span class="moudle-text2" style="font-family:'微软雅黑'">Basic Resource Data Query</span> 
								</span>
							</a> 
							<a class="loginCenter-mStyle loginCenter-moudle2" style=" display:block; width: 357px;" id="moudleRemove2" > 
								<span class="moudle-img">
									<img src="${pageContext.request.contextPath}/extensions/loginPanel/images/login_bg_02.png">
								</span> 
								<span class="moudle-text">
									<span class="moudle-text1" style="font-family:'微软雅黑'">数据轨迹运动</span>
									<span class="moudle-text2" style="font-family:'微软雅黑'">Run Of Organization</span> 
								</span>
							</a> 
							<a class="loginCenter-mStyle loginCenter-moudle3" style=" display:block;" id="moudleRemove3" > 
								<span class="moudle-img">
									<img src="${pageContext.request.contextPath}/extensions/loginPanel/images/login_bg_03.png">
								</span> 
								<span class="moudle-text"> 
									<span class="moudle-text">
										<span class="moudle-text1" style="font-family:'微软雅黑'">数据核算与统计</span>
										<span class="moudle-text2" style="font-family:'微软雅黑'">Accounting And Statistical</span>
									</span>
								</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="footer" style="margin-top:5px;">
				<span class="footerText">Copyright © 2016- 2020 浪潮通信信息系统有限公司</span>
			</div>
		</div>
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>修改密码</title>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/changePassword/assets/css/styles.css"/>
    </head>
  	<body>
  		<div id="main">
			<form method="post" action="${pageContext.request.contextPath}/loginAction/updatePassword.ilf">
				<div class="row email">
	    			<input type="text" id="email" name="oldPass" placeholder="请输入原密码" style="border-right:solid 1px #D6D6D6;"/>
        		</div>        		
        		<div class="row pass">
        			<input type="password" id="password1" name="newPass" placeholder="请输入新密码" style="border-right:solid 1px #D6D6D6;"/>
        		</div>        		
        		<div class="row pass">
        			<input type="password" id="password2" name="newPassAgain" placeholder="请再输入一次" disabled="true" style="border-right:solid 1px #D6D6D6;"/>
        		</div>
        		<div class="arrowCap"></div>
        		<div class="arrow"></div>
        		<p class="meterText">密码安全性</p>
        		<input type="submit" value="提交保存" />        		
        	</form>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/extensions/changePassword/assets/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/changePassword/assets/js/jquery.complexify.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/changePassword/assets/js/script.js"></script>
  	</body>
</html>

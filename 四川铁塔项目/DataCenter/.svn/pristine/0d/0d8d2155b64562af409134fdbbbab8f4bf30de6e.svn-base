<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
    	<base href="<%=basePath%>">
    	<title>铁塔新业务</title>
    	<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="icon" href="${pageContext.request.contextPath}/icons/frame/favicon.ico" type="image/x-icon"/>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/frame/favicon.ico" type="image/x-icon"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todc-bootstrap.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/frame.css"/>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript">
			function onMouseOn(thisBoxId){
				$("#"+thisBoxId).css({
					"border":"solid 2px #D70C19",
					"box-shadow":"0px 0px 2px #D70C19"
				});
			}
			function onMouseOut(thisBoxId){
				$("#"+thisBoxId).css({
					"border":"solid 2px #6BC2EB",
					"box-shadow":"0px 0px 0px #6BC2EB"
				});
			}
			var linkMaps = null;
			$(document).ready(function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findBusinessUrlInJsp.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							var SystemLinks = textStatus["BUSINESS_URL"];
							if(SystemLinks.length>0){
								var SystemLinksLength = SystemLinks.length;
								var totalRow = 0;
								if(SystemLinksLength%6==0){
									totalRow = SystemLinksLength/6;
								}else{
									totalRow = SystemLinksLength/6+1;
								}
								var innerHtml = "";
								for(var p=1;p<=totalRow;p++){
									var pageStart = (p-1)*6;//0,6
									var pageLimit = (p*6)-1;//5,11
									var isMarginLeft = true;
									for(var linkIndex=pageStart;linkIndex<=pageLimit;linkIndex++){
										if(linkIndex>=(SystemLinks.length)){
											break;
										}else{
											var linkObj = SystemLinks[linkIndex];
											if(linkMaps==null){
												linkMaps = new HashMap();
											}
											linkMaps.put(linkObj["ID"],linkObj);
											if(isMarginLeft){
												innerHtml+="<div id='"+linkObj["ID"]+"' class='URL_LINKS' onclick='javascript:openFire(this.id);' onmouseover='javascript:onMouseOn(this.id);' onmouseout='javascript:onMouseOut(this.id);' style='float:left;width:15.2%;height:150px;border:solid 2px #6BC2EB;border-radius:10px;cursor:pointer;box-shadow:0px 0px 5px #6BC2EB;margin-top:10px;'>";
												isMarginLeft = false;
											}else{
												innerHtml+="<div id='"+linkObj["ID"]+"' class='URL_LINKS' onclick='javascript:openFire(this.id);' onmouseover='javascript:onMouseOn(this.id);' onmouseout='javascript:onMouseOut(this.id);' style='float:left;width:15.2%;height:150px;border:solid 2px #6BC2EB;border-radius:10px;cursor:pointer;box-shadow:0px 0px 5px #6BC2EB;margin-top:10px;margin-left:15px;'>";	
											}
											innerHtml+="	<center>";
											if(linkObj["IS_BUILDING"]=="Y"){
												innerHtml+="	<img style='width:40%;height:70px;margin-top:20px;' src='${pageContext.request.contextPath}/links/image/Building.png'/>";
											}else{
												innerHtml+="	<img style='width:40%;height:70px;margin-top:20px;' src='${pageContext.request.contextPath}/links/image/"+linkObj["LINK_SYSTEM_ICON"]+"'/>";	
											}
											if(linkObj["IS_BUILDING"]=="Y"){
												innerHtml+="	<div style='margin-top:15px;color:#787878;'>"+linkObj["LINK_SYSTEM_NAME"]+"（建设中）</div>";
											}else{
												innerHtml+="	<div style='margin-top:15px;color:#787878;'>"+linkObj["LINK_SYSTEM_NAME"]+"</div>";	
											}
											
											innerHtml+="	</center>";
											innerHtml+="</div>";
										}
									}
								}
								document.getElementById("linkContainer").innerHTML = innerHtml;
							}
						}else{
							window.wxc.xcConfirm("获取新业务系统链接失败.",window.wxc.xcConfirm.typeEnum.info);
						}
					},
					error:function(){}
				});
			});
			function openFire(linkId){
				var linkMap = linkMaps.get(linkId);
				if(linkMap!=null){
					if(linkMap["IS_BUILDING"]!="Y"){
						window.open(linkMap["LINK_URL"]);
					}else{
						window.wxc.xcConfirm("系统正在建设中,暂时无法访问.",window.wxc.xcConfirm.typeEnum.info);
					}
				}
			}
		</script>
  	</head>
  	<body>
		<div style="margin-top:30px;height:80px;width:100%;">
			<center>
				<img style="width:392px;height:75px;" src="${pageContext.request.contextPath}/icons/frame/logo.png"/>
			</center>
		</div>
		<center>
			<div id="linkContainer" style="width:90%;height:500px;margin-top:20px;border:solid 0px red;overflow:auto;">
				&nbsp;
			</div>
		</center>	
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
	<!--  	<script type="text/javascript">
		function checkEvent(){
			if(parent.parent.Ext.getDom("eid")){
				var eid = parent.parent.Ext.getDom("eid").value;
				Ext.Ajax.request({
					url : 'point!getPointEvent.action',
					method : 'Post',
					params : {
						'pointEventInfoBean.eid' : eid
					},
					callback : function(options, success, response){
						if(success){
							var jsonResult = Ext.util.JSON.decode(response.responseText);
							parent.showEvent(jsonResult);
						}
					}
				});
			}
			setTimeout(function(){checkEvent()}, 3000); 
		}
		setTimeout(function(){checkEvent()}, 5000);
		//页面加载后获取盘告警
		function getPanelEvent(){
			if(parent.parent.Ext.getDom("eid")){
				var eid = parent.parent.Ext.getDom("eid").value;
				Ext.Ajax.request({
					url : 'point!getPointAlarm.action',
					method : 'Post',
					params : {
						'pointEventInfoBean.eid' : eid,
						'pointEventInfoBean.type' : '1'
					},
					callback : function(options, success, response){
						if(success){
							var jsonResult = Ext.util.JSON.decode(response.responseText);
							parent.showEvent(jsonResult);
						}
					}
				});
			}
		}
		setTimeout(function(){getPanelEvent()}, 7000);
		</script>	-->
	</head>
	<body>
	</body>
</html>

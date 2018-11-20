<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/layui/css/layui.css" media="all">
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/layui/layui.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
	var id='<%=request.getParameter("ID")%>';
		$(document).ready(function(){
			if(id!=0 && id!=null && id!=""){
				$.ajax({
					url:"${pageContext.request.contextPath}/greyListExpireAction/findDataDetail.ilf",
					async:true,
					type:"POST",
					dataType:"JSON",
					data:{"id":id},
					timeout:10000,
					success:function(data){
						if(data["success"]){
							var greyList=data["list"][0];
							/* alert(getTime(greyList["expireTime"])); */
							$("#city").val(greyList["city"]);
							$("#county").val(greyList["county"]);
							$("#saName").val(greyList["saName"]);
							$("#saCode").val(greyList["saCode"]);
							$("#glType").val(greyList["glType"]);
							$("#glDescribe").val(greyList["glDescribe"]);
							$("#glRule").val(greyList["glRule"]);
							$("#attribution").val(greyList["attribution"]);
							$("#dataSource").val(greyList["dataSource"]);
							$("#expireTime").val(getTime(greyList["expireTime"]));
							$("#city").attr("readonly","readonly");
							$("#county").attr("readonly","readonly");
							$("#saName").attr("readonly","readonly");
							$("#saCode").attr("readonly","readonly");
							$("#glType").attr("readonly","readonly");
							$("#glDescribe").attr("readonly","readonly");
							$("#glRule").attr("readonly","readonly");
							$("#attribution").attr("readonly","readonly");
							$("#dataSource").attr("readonly","readonly");
							$("#expireTime").attr("readonly","readonly");
						}
					}
				});
			}
		});
		function getTime(obj){
			var year=obj["year"];
			var month=obj["month"]+1;
			var day=obj["date"];
			var hours=obj["hours"];
			var minutes=obj["minutes"];
			var seconds=obj["seconds"];
			var time=(year+1900)+"-"+(month<10?("0"+month):month)+"-"+(day<10?("0"+day):day)+" "+(hours<10?("0"+hours):hours)+":"+(minutes<10?("0"+minutes):minutes)+":"+(seconds<10?("0"+seconds):seconds);
			return time;
		}
		function expire(){
			$.ajax({
				url:"${pageContext.request.contextPath}/greyListExpireAction/expire.ilf",
				async:true,
				type:"POST",
				dataType:"JSON",
				timeout:10000,
				data:{"validTime":$("#validTime").val(),"id":id},
				timeout:10000,
				success:function(data){
					if(data["success"]){
						$("#panel-body").html('<img src="${pageContext.request.contextPath}/img/yes.png" style="width:100px;height:100px;"/><font size="4">续期发起成功！</font>');
					}else{
						$("#panel-body").html('<img src="${pageContext.request.contextPath}/img/no.png" style="width:100px;height:100px;"/><font size="4">续期申请出现问题，请重新填写！</font>');
					}
				}
			});
		}
		function cancel(){
			$("#panel-body").html('<img src="${pageContext.request.contextPath}/img/yes.png" style="width:100px;height:100px;" /><font size="4">续期取消成功！</font>');
		}
	</script>
</head>
<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:0px;">
		<div class="panel panel-primary"  id="mainPanel">
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font id="title" color="white" size="5">灰名单续期编辑</font>
				</div>
			</div>
			<div class="panel-body" style="text-align:center;" id="panel-body">
			
				<div class="layui-form layui-form-pane">
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">地市</label>
					    <div class="layui-input-block">
					      <input type="text" name="city" id="city" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" value="" required="required">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">区县</label>
					    <div class="layui-input-block">
					      <input type="text" name="county" id="county" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">资源名称</label>
					    <div class="layui-input-block">
					      <input type="text" name="saName" id="saName" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">资源编号</label>
					    <div class="layui-input-block">
					      <input type="text" name="saCode" id="saCode" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单类型</label>
					    <div class="layui-input-block">
					      <input type="text" name="glType" id="glType" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单说明</label>
					    <div class="layui-input-block">
					      <input type="text" name="glDescribe" id="glDescribe" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单规则</label>
					    <div class="layui-input-block">
					      <input type="text" name="glRule" id="glRule" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">归口专业</label>
					    <div class="layui-input-block">
					    	<input type="text" name="attribution" id="attribution" style="color:blue;height:38px;" autocomplete="off"  class="layui-input" required="required">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">数据来源</label>
					    <div class="layui-input-block">
					      <input type="text" name="dataSource" id="dataSource" style="color:blue;height:38px;" autocomplete="off"  class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">到期时间</label>
					    <div class="layui-input-block">
					      <input type="text" name="expireTime" id="expireTime" style="color:blue;height:38px;" autocomplete="off"  class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">续期时长</label>
					    <div class="layui-input-block">
					      <select style="display:block;width:100%;height:38px;" name="validTime" id="validTime">
					      	<option value="1">1个月</option>
					      	<option value="2">2个月</option>
					      	<option value="3">3个月</option>
					      	<option value="4">4个月</option>
					      	<option value="5">5个月</option>
					      	<option value="6">6个月</option>
					      	<option value="7">7个月</option>
					      	<option value="8">8个月</option>
					      	<option value="9">9个月</option>
					      	<option value="10">10个月</option>
					      	<option value="11">11个月</option>
					      	<option value="12">12个月</option>
					      </select>
					    </div>
					  </div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12" style="width:100%;text-align:center;">
						<button onclick="expire()" class="btn btn-success" style="width:180px;height:40px;">确认续期</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button onclick="cancel()" class="btn btn-danger" style="width:180px;height:40px;">取消续期</button>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>
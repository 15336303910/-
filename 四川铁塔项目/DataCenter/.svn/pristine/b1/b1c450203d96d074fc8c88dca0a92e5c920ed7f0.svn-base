<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">
<title>解除灰名单详情页面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/content.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css">
<!-- layui -->
<link rel="stylesheet" type="text/css"
 href="${pageContext.request.contextPath}/extensions/layui/css/layui.css">	
	
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/json2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
<link
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
	<!-- layui.js -->
	<script type="text/javascript"   
	             src="${pageContext.request.contextPath}/extensions/layui/layui.js"></script>
	
	
<script type="text/javascript">
		var id='<%=request.getParameter("ID")%>';
       var isProvince='<%=request.getParameter("isProvince")%>';
    
 	$(document).ready(function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/todoListDataDetail/showDataDetail.ilf",
				async:true,
				type:"POST",
				dataType:"json",
				data:{"id":id},
				timeout:10000,
				success:function(data){
					var list=data["list"][0];
				//	$("#datadetail").css("display","");
					$("#city").val(list["CITY"]);
					$("#country").val(list["COUNTY"]);
					$("#sa_code").val(list["SA_CODE"]);
					$("#sa_name").val(list["SA_NAME"]);
					$("#attribution").val(list["ATTRIBUTION"]);
					$("#data_source").val(list["DATA_SOURCE"]);
					$("#table_source").val(list["TABLE_SOURCE"]);
					$("#gl_type").val(list["GL_TYPE"]);
					$("#gl_decribe").val(list["GL_DESCRIBE"]);
					$("#originator").val(list["ORIGINATOR"]);
					$("#gl_rule").val(list["GL_RULE"]);
					$("#launch_time").val(list["LAUNCH_TIME"]);
					$("#valid_time").val(list["VALID_TIME"]);
					$("#city_approver").val(list["CITY_APPROVER"]);
					$("#c_a_time").val(list["C_A_TIME"]);
					$("#remarks").val(list["REMARKS"]);
				}
				
			});
			if(isProvince=="TURE")
	    	   {
	    	   $("#city_approver_block").attr("style","display:block;");//显示div
	    	   $("#c_a_time_block").attr("style","display:block;");//显示div 
	    	   }
		});
		
		function ApproveDone(){		
			$.ajax({
				url:"${pageContext.request.contextPath}/todoListDataDetail/approveDone.ilf",
				async:true,
				type:"POST",
				dataType:"json",
				data:{"id":id ,"approve_decision":$("#approve_decision").val(),"rejectReason":$("#rejectReason").val},
				timeout:10000,
				success:function(data){
					if(data["success"]){
						$("#panel-body").html('<img src="${pageContext.request.contextPath}/img/yes.png" style="width:100px;height:100px;"/><font size="4">审批完成！</font>');
					}else{
						$("#panel-body").html('<img src="${pageContext.request.contextPath}/img/no.png" style="width:100px;height:100px;"/><font size="4">审批出现问题！</font>');
					}
				           }
			});
		} 
		
		<!--layui---->
	

		

		
		
		
		</script>

</head>
<body style="width: 100%; height: 100%; border: solid 0px red;"
	id="bodyHeight">
	<div class="container"
		style="width: 100%; height: 100%; margin-top: 0px;">
		
		
		<div class="panel panel-primary" id="mainPanel">



			<div id="panelHeading"
				style="width: 100%; height: 50px; background-color: #337ab7; display: table; padding-left: 10px">
				<div
					style="background-color: #337ab7; display: table-cell; vertical-align: middle; padding-left: 10px">
					<font id="title" color="white" size="5">待办事项——数据详情</font>
				</div>
			</div>

			<div id="panel-body"  class="panel-body" style="text-align: center;">
			<form class="layui-form layui-form-pane">
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">地市：</label>
					    <div class="layui-input-block">
					      <input id="city"  type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">区县：</label>
					    <div class="layui-input-block">
					      <input id="country" type="text" name=""  style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					      
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">资源名称：</label>
					    <div class="layui-input-block">
					      <input id="sa_name" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">资源编码：</label>
					    <div class="layui-input-block">
					      <input id="sa_code" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">归口专业：</label>
					    <div class="layui-input-block">
					      <input id="attribution" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">数据来源：</label>
					    <div class="layui-input-block">
					      <input  id="data_source" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">来源表：</label>
					    <div class="layui-input-block">
					      <input  id="table_source" type="text" id="city" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
				  	<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单类型：</label>
					    <div class="layui-input-block">
					      <input id="gl_type" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单说明：</label>
					    <div class="layui-input-block">
					      <input id="gl_decribe" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">发起人：</label>
					    <div class="layui-input-block">
					      <input id="originator" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">灰名单规则：</label>
					    <div class="layui-input-block">
					      <input id="gl_rule" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">发起时间：</label>
					    <div class="layui-input-block">
					      <input  id="launch_time" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">有效期：</label>
					    <div class="layui-input-block">
					      <input id="valid_time" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div  id="c_a_time_block" class="col-xs-6 col-sm-6 col-md-6" style="display:none">
					  <div class="layui-form-item">
					    <label class="layui-form-label">地市审批时间：</label>
					    <div class="layui-input-block">
					      <input id="c_a_time" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					<div id="city_approver_block"class="col-xs-6 col-sm-6 col-md-6"  style="display:none">
					  <div class="layui-form-item">
					    <label class="layui-form-label">地市审批人：</label>
					    <div class="layui-input-block">
					      <input id="city_approver" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					
						<div class="col-xs-6 col-sm-6 col-md-6">
					  <div class="layui-form-item">
					    <label class="layui-form-label">备注：</label>
					    <div class="layui-input-block">
					      <input id="remarks" type="text" name="" style="color:blue;height:38px;" autocomplete="off" disabled="disabled" class="layui-input">
					    </div>
					  </div>
					</div>
					
					
					
					<div class="col-xs-12 col-sm-12 col-md-12"  >
					  <div class="layui-form-item">
					 
					    <div class="layui-input-block">
					    
					    </div>
					  </div>
					</div>
						<div class="col-xs-12 col-sm-12 col-md-6"  >
					  <div class="layui-form-item">
					    <label class="layui-form-label">审批意见：</label>
					     <div class="layui-input-block">
					  <select  id="approve_decision" style="display:block;color:blue; width:100%;height:38px;border:solid 1px #A3D0E3;" >
                                                <option value="">同意</option>
                                                <option value="">驳回</option>
							</select> 
						
					  </div>
					    
					  </div>
					</div>
						<div class="col-xs-12 col-sm-12 col-md-6"  >
					  <div class="layui-form-item">
					    <label class="layui-form-label">原因说明：</label>
					    <div class="layui-input-block">
					      <input id="rejectReason" type="text" name="" style="color:blue;height:38px;" autocomplete="off"  class="layui-input">
					    </div>
					  </div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12"  >
		            <button type="button" class="btn btn-danger" style="width:90px;" onclick="ApproveDone()">审批完成</button>
				    </div>
				</form>
			


					</div>
                          
			
										  
						
				     
					
					
					</div>
					

					
					</div>
					

		
					
					
</body> 

</html>
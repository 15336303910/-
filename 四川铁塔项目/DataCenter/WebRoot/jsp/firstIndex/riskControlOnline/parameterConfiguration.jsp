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
    	<title>在线风控参数配置</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/my97datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		
		<script type="text/javascript">
			$(document).ready(function(){
				/*开始获取数据*/
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/dataCenterPage.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:"cityName=-1&thisMonth=-1",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["SUCCESS"]){
							/*地市列表*/
							var cityArray = textStatus["CITY_LIST"];
							var citySelect = "";
							citySelect+="<select style='width:180px;height:29px;border:solid 1px #A3D0E3;' id='citySelect'>";
							for(var i=0;i<cityArray.length;i++){
								var cityName = cityArray[i];
								citySelect+="<option value='"+cityName["CITY_NAME"]+"'>"+cityName["CITY_NAME"]+"</option>";
							}
							citySelect+="</select>";
							document.getElementById("citySelection").innerHTML = citySelect;
							/*
								=== 初始化地市信息   ===
							 */
							var cityName = "";
							if(textStatus["IS_PROVINCE"]){
								cityName = "全省";
							}else{
								cityName = textStatus["MY_CITY"];
							}
							
							if(cityName!=""){
								if(cityName.length>2){
									cityName = cityName.substring(0,2);
								}
								var citySelectObj = document.getElementById("citySelect");
								if(citySelectObj!=null && citySelectObj.options!=null && citySelectObj.options.length>0){
									for(var i=0;i<citySelectObj.options.length;i++){
			                             var optionValue = citySelectObj.options[i].value;
			                             if(optionValue.indexOf(cityName)!=-1){
											citySelectObj.options[i].selected = "selected";
		                                    break;
			                             }
									}
								}	
							}
						}
						},
		      
					error:function(){  }
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/ParameterConfigurationAction/findParameterConfigurationDatas.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:{"city":$("#citySelect").val(),"date":$("#designedDate").val()},
					timeout:10000,
					success:function(riskData){
						/*下方问题列表及数量*/
						var lineHtml="";
						var lineHtmla="";
						var lineHtmlb="";
						var lineHtmlc="";
						var fund_question=riskData["fund_list"];
						var colocation_question=riskData["colocation_list"];
						var income_question=riskData["income_list"];
						var engineering_question=riskData["engineering_list"];
						
						//资金问题
						for(var i=0;i<fund_question.length;i++){
							var data=fund_question[i];
						    var id= 0;
					 
					      //  alert("data["ID"])");
							lineHtml+="<tr>";
							lineHtml+="	  <td>"+data["NO"]+"</td>";
							lineHtml+="	  <td>"+data["RISK_NAME"]+"</td>";
							lineHtml+="	  <td>"+data["PARA_VALUE"]+"</td>";
							lineHtml+="	  <td>"+'<a href="${pageContext.request.contextPath}/jsp/firstIndex/riskControlOnline/parameterConfigurationEdit.jsp?id='+data["ID"]+'">编辑</a>'+"</td>";
							lineHtml+="</tr>";	
						}
						$("#zijin_qu").html(lineHtml);
						
						//场租费
						for(var i=0;i<colocation_question.length;i++){
							var data=colocation_question[i];
							lineHtmla+="<tr>";
							lineHtmla+="	  <td>"+data["NO"]+"</td>";
							lineHtmla+="	  <td>"+data["RISK_NAME"]+"</td>";
							lineHtmla+="	  <td>"+data["PARA_VALUE"]+"</td>";
							lineHtmla+="	  <td>"+'<a href="${pageContext.request.contextPath}/jsp/firstIndex/riskControlOnline/parameterConfigurationEdit.jsp?id='+data["ID"]+'">编辑</a>'+"</td>";
							lineHtmla+="</tr>";	
						}
						$("#colocation_qu").html(lineHtmla);
						
						//收入方面 
						for(var i=0;i<income_question.length;i++){
							var data=income_question[i];
							lineHtmlb+="<tr>";
							lineHtmlb+="	  <td>"+data["NO"]+"</td>";
							lineHtmlb+="	  <td>"+data["RISK_NAME"]+"</td>";
							lineHtmlb+="	  <td>"+data["PARA_VALUE"]+"</td>";
							lineHtmlb+="	  <td>"+'<a href="${pageContext.request.contextPath}/jsp/firstIndex/riskControlOnline/parameterConfigurationEdit.jsp?id='+data["ID"]+'">编辑</a>'+"</td>";
							lineHtmlb+="</tr>";	
						}
						$("#income_qu").html(lineHtmlb);
						
						//工程类
						for(var i=0;i<engineering_question.length;i++){
							var data=engineering_question[i];
							lineHtmlc+="<tr>";
							lineHtmlc+="	  <td>"+data["NO"]+"</td>";
							lineHtmlc+="	  <td>"+data["RISK_NAME"]+"</td>";
							lineHtmlc+="	  <td>"+data["PARA_VALUE"]+"</td>";
							lineHtmlc+="	  <td>"+'<a href="${pageContext.request.contextPath}/jsp/firstIndex/riskControlOnline/parameterConfigurationEdit.jsp?id='+data["ID"]+'">编辑</a>'+"</td>";
							lineHtmlc+="</tr>";	
						}
						$("#engineering_qu").html(lineHtmlc);
					}
				});
			});
		</script>
		<style type="text/css">
			.table th,.table td{
				text-align:center
			}
			.table tbody tr td{
				vertical-align:middle
			}
		</style>
  	</head>
  	<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:-20px;">
		<div class="panel panel-primary"  id="mainPanel">
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font color="white" size="5">参数配置</font>
				</div>
			</div>
			
			<div class="panel-body">
				<!-- 资金问题 -->
			 	<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table class="table table-bordered"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align: center;'>
							<button class="btn btn-success" type="submit">资金问题</button>
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点名称</th>
								<th>超期月份</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="zijin_qu">
						</tbody>
					</table>
				</div>
				
				<!-- 场租费 -->
				<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table class="table table-bordered"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align:right;display:inline-block;width:55%'>
							<button class="btn btn-success" type="submit">场租费</button>
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点名称</th>
								<th>超期月份</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="colocation_qu">
						</tbody>
					</table>
				</div>
				
				<!-- 收入方面 -->
				<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table class="table table-bordered"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align: center;'>
							<button class="btn btn-success" type="submit">收入方面</button>
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点名称</th>
								<th>超期月份</th>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="income_qu">
						</tbody>
					</table>
				</div>
				
				<!-- 工程类 -->
				<div class="col-xs-6 col-sm-3 col-md-6"
					style="margin-top:15px">
					<table class="table table-bordered"
						style='vertical-align: middle; text-align: center;'>
						<div class="center-block" style='text-align: center;'>
							<button class="btn btn-success" type="submit">工程类</button>
						</div>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr>
								<th>序号</th>
								<th>风险点名称</th>
								<th>参考费用</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="engineering_qu">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>	
	</body>
</html>
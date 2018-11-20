<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>数据体检</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jstree/dist/themes/default/style.min.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">    	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jstree/dist/jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<style>
			.jstrees{
				overflow:auto;
				border:1px solid silver;
				min-height:100px;
				text-align:left;
				font-size:18px;
				font-size:2.4em;
				font-size:100%;
				float:left;
			}
		</style>
		<script type="text/javascript">
			function addUnchecked(){
				window.wxc.xcConfirm("加入白名单后将不再针对此项进行核查.","custom",{
					title:"警告",
					btn:parseInt("0011",2),
					onOk:function(){
						window.wxc.xcConfirm("加入白名单成功.",window.wxc.xcConfirm.typeEnum.info);
					}
				});
			}
			$(document).ready(function(){
				/*
					渲染左侧资源结构并绑定单击事件
				*/
				$("#frmt").on("changed.jstree",function(e,data){
					if(data.selected.length){
						var nodeText = data.instance.get_node(data.selected[0]).text;						
					}
				}).jstree({
					"core":{
						"data":[{
							"text":"综合资源系统",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"传输外线",
								"state":{
									"opened":true,
									"selected":true
								},
								"children":[{
									"text":"传输外线垃圾数据",
									"icon":"jstree-file",
									"state":{
										"selected":false
									}
								},{
									"text":"管线资源合理性",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								}]
							}]
						},{
							"text":"集团综合代维",
							"state":{
								"opened":true
							},
							"children":[{
								"text":"集客",
								"state":{
									"opened":true
								},
								"children":[{
									"text":"业务电路类型配置合规律",
									"icon":"jstree-file",
									"state":{
										"selected":false
									}
								},{
									"text":"PON网络完整性",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								},{
									"text":"电路对应专线合规率",
									"icon":"jstree-file",
									"state":{
										"selected":false,
										"disabled":false
									}
								}]
							}]
						}]
					}
				});
			});
		</script>
  	</head>
  	<body>
  		<center>
  			<div style="float:left;width:21%;height:475px;margin-top:3px;margin-left:3px;border:solid 2px #DFE8F1;border-radius:6px;" id="frmt" class="jstrees"></div> 			
    		<div style="float:left;width:77%;height:475px;margin-top:3px;margin-left:2px;border:solid 2px #DFE8F1;border-radius:6px;">
    			<div class="panel-heading">
					<form class="form-search pull-right">	
						<div style="float:right;">
							<input type="text" placeholder="请输入问题关键字" style="width:120px;height:29px;font-size:12px;"></input>
							<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
								<i class="icon-search icon-white"></i>查询 
							</a>
							<a class="btn btn-success" href="#myModal" role="button" data-toggle="modal" style="cursor:pointer">
								<i class="icon-plus-sign icon-white"></i>派单
							</a>
							<a class="btn btn-warning" href="#manyModal" role="button" data-toggle="modal" style="cursor:pointer">
								<i class="icon-play-circle icon-white"></i>批量派发
							</a>
							<a class="btn btn-info" href="#manyModal" role="button" data-toggle="modal" style="cursor:pointer">
								<i class="icon-play-circle icon-white"></i>导出
							</a>						
							<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:addUnchecked();">
								<i class="icon-trash icon-white"></i>白名单
							</a>	
						</div>
					</form>
				</div>
				<div class="panel-body h340" style="border:0px;">
					<div class="panlecontent container4 clearfix">
						<div class="div_scroll">
							<div style="height:auto;width:auto">
								<table class="table table-bordered table-hover" style="border:0px;">
									<thead>
										<tr>
											<td style="border-left:0px;" class="tc"><input type="checkbox" value=""></td>
											<td>资源名称</td>
											<td>现存问题</td>
											<td>问题等级</td>
											<td style="border-right:0px;">是否已加入白名单</td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="border-left:0px;" class="tc"><input type="checkbox" value=""></td>
											<td>管道段A号</td>
											<td>管道段两个点之间的距离小于5米，或大于300米</td>
											<th><font color="red">非常严重</font></th>
											<td style="border-right:0px;"><font color="green">未加入</font></td>
										</tr>
										<tr>
											<td style="border-left:0px;" class="tc"><input type="checkbox" value=""></td>
											<td>管道段B号</td>
											<td>没有管孔的管道段</td>
											<th><font color="red">非常严重</font></th>
											<td style="border-right:0px;"><font color="green">未加入</font></td>
										</tr>
										<tr>
											<td style="border-left:0px;" class="tc"><input type="checkbox" value=""></td>
											<td>管道段C号</td>
											<td>PTN、SDH网络拓扑中缺失光路信息</td>
											<th><font color="green">严重</font></th>
											<td style="border-right:0px;"><font color="green">未加入</font></td>
										</tr>
										<tr>
											<td style="border-left:0px;" class="tc"><input type="checkbox" value=""></td>
											<td>管道段D号</td>
											<td>光缆段两端纤芯关联的端子异常占用信息</td>
											<th><font color="orange">一般</font></th>
											<td style="border-right:0px;"><font color="green">未加入</font></td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
										<tr>
											<td style="border-left:0px;">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td style="border-right:0px;">&nbsp;</td>
										</tr>
									</tbody>
								</table>						
							</div>
						</div>
					</div>
				</div>
    		</div>
    	</center>
    	<%--窗口表单：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:400px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 工单派发</h3>
			</div>
			<div class="modal-body" style="max-height:280px;">
				<form class="l-group">
					<table class="table table-bordered table-hover">
						<tr>
							<td style="text-align:center;">
								<label class="l-name">接收人.</label>
							</td>
							<td>
								<input type="text" placeholder="请点击此处选择工单接收人" readonly style="cursor:pointer;width:500px;height:30px;margin-top:5px;">
							</td>
						</tr>
						<tr>
							<td style="text-align:center;">
								<label class="l-name">资源名称.</label>
							</td>
							<td>
								<input type="text" placeholder="" value="管道段A号" style="width:500px;height:30px;margin-top:5px;"/>
							</td>
						</tr>
						<tr height="170">
							<td style="text-align:center;"><label class="l-name">现存问题.</label></td>
							<td>
								<textarea style="width:500px;height:150px;">管道段两个点之间的距离小于5米，或大于300米</textarea>
							</td>
						</tr>
						<tr height="170">
							<td style="text-align:center;"><label class="l-name">整改意见.</label></td>
							<td>
								<textarea style="width:500px;height:150px;">经纬度可能错误，修改经纬度</textarea>
							</td>
						</tr>
						<tr height="170">
							<td style="text-align:center;"><label class="l-name">派单说明.</label></td>
							<td>
								<textarea style="width:500px;height:150px;"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;">
					<i class="icon-envelope icon-white"></i>执行派发
				</button>
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>取消
				</button>
			</div>
		</div>
		<%--窗口表单：结束--%>
		<%--窗口表单：开始--%>
		<div id="manyModal" class="modal hide fade" style="width:650px;height:350px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 批量派发</h3>
			</div>
			<div class="modal-body" style="max-height:280px;">
				<form class="l-group">
					<table class="table table-bordered table-hover">
						<tr>
							<td style="text-align:center;">
								<label class="l-name">接收人.</label>
							</td>
							<td>
								<input type="text" placeholder="请点击此处选择工单接收人" readonly style="cursor:pointer;width:500px;height:30px;margin-top:5px;">
							</td>
						</tr>
						<tr height="170">
							<td style="text-align:center;"><label class="l-name">派单说明.</label></td>
							<td>
								<textarea style="width:500px;height:150px;"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;">
					<i class="icon-envelope icon-white"></i>执行派发
				</button>
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>取消
				</button>
			</div>
		</div>
		<%--窗口表单：结束--%>
  	</body>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
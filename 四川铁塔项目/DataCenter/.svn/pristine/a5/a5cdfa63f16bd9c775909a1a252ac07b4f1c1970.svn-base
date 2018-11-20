<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>指标管理</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript">
			function deleteWindow(){
				window.wxc.xcConfirm("删除指标时会同时删除其包含的规则.","custom",{
					title:"警告",
					btn:parseInt("0011",2),
					onOk:function(){
						window.wxc.xcConfirm("指标删除成功.",window.wxc.xcConfirm.typeEnum.info);
					}
				});
			}
		</script>
  	</head>
  	<body style="width:100%;">
		<div class="panel panel-default" style="margin-top:5px;height:470px;">
			<div class="panel-heading">
				<input type="hidden" id="hiddenOfEditType"></input>
				<span class="panel-label"></span>规则管理 >>指标管理
				<form class="form-search pull-right">	
					<div style="float:right;">
						<select class="w200" style="width:150px;">
							<option>-- 请选择专业 --</option>
							<option>传输外线</option>
							<option>集客</option>
							<option>家客</option>
							<option>室分与WLAN</option>
						</select>
						<input type="text" placeholder="请输入指标名称" style="width:130px;height:29px;font-size:12px;"></input>
						<a class="btn btn-primary" style="cursor:pointer" onclick="javascript:searchData();">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<a class="btn btn-success" href="#myModal" role="button" data-toggle="modal" style="cursor:pointer">
							<i class="icon-plus-sign icon-white"></i>编辑指标
						</a>						
						<a class="btn btn-danger"  style="cursor:pointer" onclick="javascript:deleteWindow();">
							<i class="icon-trash icon-white"></i>删除指标
						</a>	
					</div>
				</form>
			</div>
			<div class="panel-body h340">
				<div class="panlecontent container4 clearfix">
					<div class="div_scroll">
						<div style="height:auto;width:auto">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th width="10%" class="tc"><input type="checkbox" value=""></th>
										<th width="20%">所属专业</th>
										<th width="50%">业务指标</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="tc"><input type="checkbox" value=""></td>
										<td>传输外线</td>
										<td>传输外线垃圾数据</td>
									</tr>
									<tr>
										<td class="tc"><input type="checkbox" value=""></td>
										<td>传输外线</td>
										<td>管线资源合理性</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</tbody>
							</table>						
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="myModal" class="modal hide fade" style="width:650px;height:400px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="myModalLabel">+ 指标信息维护</h3>
			</div>
			<div class="modal-body" style="max-height:280px;">
				<form class="l-group">
					<table class="table table-bordered table-hover">
						<tr>
							<td><label class="l-name">数据源.</label></td>
							<td>
								<select class="w500">
									<option>-- 通用 --</option>
									<option>综资数据库</option>
									<option>综合代维库</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="l-name">所属专业.</label></td>
							<td>
								<select class="w500">
									<option>传输外线</option>
									<option>集客</option>
									<option>家客</option>
									<option>室分与WLAN</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="l-name">指标名称.</label></td>
							<td><input type="text" placeholder="" style="width:500px;height:30px;margin-top:5px;"></td>
						</tr>
						<tr height="80">
							<td style="text-align:center;"><label class="l-name">指标描述.</label></td>
							<td>
								<textarea style="width:500px;height:60px;"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div>
									<form class="form-search pull-right">	
										<div style="float:right;">
											<a class="btn btn-success" style="cursor:pointer;width:70px;">
												<i class="icon-plus-sign icon-white"></i>添加 
											</a>
											<a class="btn btn-warning"  style="cursor:pointer;width:70px;margin-right:15px;">
												<i class="icon-remove icon-white"></i>删除
											</a>	
										</div>
									</form>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="margin-top:5px;">
									<table class="table table-bordered table-hover" style="width:100%;">
										<tr>
											<td class="tc"><input type="checkbox" value=""></td>
											<td>规则名称</td>
											<td>权重（%）</td>
										</tr>
										<tr>
											<td class="tc"><input type="checkbox" value=""></td>
											<td>承载点资源的经纬度都要在归属本地市区域内</td>
											<td><input type="text" value="20" style="width:70px;height:25px;"></input></td>
										</tr>
										<tr>
											<td class="tc"><input type="checkbox" value=""></td>
											<td>管道段都需有管孔，分析没有管孔的管道段</td>
											<td><input type="text" value="25" style="width:70px;height:25px;"></input></td>
										</tr>
										<tr>
											<td class="tc"><input type="checkbox" value=""></td>
											<td>管道段两个点之间的距离小于5米，或大于300米</td>
											<td><input type="text" value="40" style="width:70px;height:25px;"></input></td>
										</tr>
										<tr>
											<td class="tc"><input type="checkbox" value=""></td>
											<td>光缆段敷设的管孔的状态应为占用</td>
											<td><input type="text" value="15" style="width:70px;height:25px;"></input></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-primary" style="cursor:pointer;">
					<i class="icon-envelope icon-white"></i>保存配置
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
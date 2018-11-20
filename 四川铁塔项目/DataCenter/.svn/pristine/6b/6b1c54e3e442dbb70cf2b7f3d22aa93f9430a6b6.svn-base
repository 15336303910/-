<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>归档工单</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
  	</head>
  	<body style="width:100%;">
		<div class="panel panel-default" style="margin-top:1px;height:480px;">
			<div class="panel-heading">
				<span class="panel-label"></span>归档工单
				<form class="form-search pull-right">	
					<div style="float:right;">
						<input type="text" placeholder="请输入资源关键字" style="width:150px;height:29px;font-size:12px;"></input>
						<a class="btn btn-primary" style="cursor:pointer">
							<i class="icon-search icon-white"></i>执行查询 
						</a>
						<a class="btn btn-danger"  style="cursor:pointer">
							<i class="icon-flag icon-white"></i>查看详情
						</a>	
						<a class="btn btn-inverse"  style="cursor:pointer">
							<i class="icon-film icon-white"></i>工单日志
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
										<td class="tc"><input type="checkbox" value=""></td>
										<td>工单编号</td>
										<td>当前状态</td>
										<td>资源关键字</td>
										<td>问题关键字</td>
										<td>问题等级</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="tc"><input type="checkbox" value=""></td>
										<td>GD-201702080001</td>
										<td><font color="green">已完成</font></td>
										<td>管道A号</td>
										<td>管道段长度有问题</td>
										<th><font color="red">非常严重</font></th>
									</tr>
									<tr>
										<td class="tc"><input type="checkbox" value=""></td>
										<td>GD-201702080002</td>
										<td><font color="green">已完成</font></td>
										<td>管道B号</td>
										<td>没有归属直埋段的标石</td>
										<th><font color="red">非常严重</font></th>
									</tr>
									<tr>
										<td class="tc"><input type="checkbox" value=""></td>
										<td>GD-201702080003</td>
										<td><font color="green">已完成</font></td>
										<td>管道C号</td>
										<td>杆路段无敷设的光缆信息</td>
										<th><font color="red">非常严重</font></th>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
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
						<tr>
							<td style="text-align:center;"><label class="l-name">所占权重.</label></td>
							<td>
								<input type="text" placeholder="" style="width:500px;height:30px;margin-top:5px;"> %
							</td>
						</tr>
						<tr height="120">
							<td style="text-align:center;"><label class="l-name">指标描述.</label></td>
							<td>
								<textarea style="width:500px;height:100px;"></textarea>
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
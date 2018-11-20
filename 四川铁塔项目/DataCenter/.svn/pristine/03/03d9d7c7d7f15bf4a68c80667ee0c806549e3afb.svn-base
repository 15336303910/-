<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String frameHeight = request.getParameter("frameHeight");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>第三方门户</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/todc-bootstrap.css">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/EasyUI/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/jQueryUI/css/jquery-ui.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/datatables/css/jquery.dataTables.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/zTree/css/zTreeStyle.css">	
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/font-awesome.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/topMenus/css/style.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/jQueryUI/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/datatables/js/jquery.dataTables.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/selection/jquery.fancyspinbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/checkboxes.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#bodyHeight").css({
			    	"height":$("#hiddenOfHeight").val()-1
			    });
				$("#mainContainer").css({
			    	"height":parseInt($("#hiddenOfHeight").val())+16
			    });
				$("#leftpart").css({
			    	"height":parseInt($("#mainContainer").height())-2
			    });
				$("#SY_PART").css({
			    	"height":parseInt($("#mainContainer").height())-5
			    });
				initMainPage();
			});
			function initMainPage(){
				window.parent.loading(true);
				$.ajax({
					url:"${pageContext.request.contextPath}/myBenchAction/findIndexDatas.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(resultObj){
						window.parent.overLoading(true);
						if(resultObj["success"]){
							/*
								----------------------- 登录信息  --------------------------
							*/
							var loginUser = resultObj["LOGIN_USER"];
							$("#hiddenOfGdLink").val(resultObj["GD_URL"]);
							$("#hiddenOfUsername").val(loginUser["USER_NAME"]);
							$("#employeeNameDiv").html(loginUser["EMPLOYEE_NAME"]);
							/*
								----------------------- 工单数量 --------------------------
							*/
							if(resultObj["WAITING_JOB_STATE"]=="Y"){
								$("#WAITING_JOB_BUTTON").html("我的待办任务<font color='red'>（"+resultObj["WAITING_JOB_COUNT"]+"）</font>");	
							}
							if(resultObj["COPY_JOB_STATE"]=="Y"){
								$("#COPY_JOB_BUTTON").html("抄送任务<font color='red'>（"+resultObj["COPY_JOB_COUNT"]+"）</font>");	
							}
							if(resultObj["COOPER_JOB_STATE"]=="Y"){
								$("#COOPER_JOB_BUTTON").html("协办任务<font color='red'>（"+resultObj["COOPER_JOB_COUNT"]+"）</font>");	
							}
							if(resultObj["DOING_JOB_STATE"]=="Y"){
								$("#DOING_JOB_BUTTON").html("我的在办任务<font color='red'>（"+resultObj["DOING_JOB_COUNT"]+"）</font>");	
							}
						}
					}
				});
			}
			function doGdSystem(typeCode){
				if(parseInt(typeCode)==1){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/tasklistAction!waitedTask.ilf?template_type=4&PageStyle=1&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(parseInt(typeCode)==2){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/transcribeAction!getUserTaskList.ilf?state=5&template_type=4&PageStyle=1&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(parseInt(typeCode)==3){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/dispatchAction!getUserTaskList.ilf?state=5&template_type=4&PageStyle=1&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(parseInt(typeCode)==4){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/tasklistAction!waitedTask.ilf?template_type=4&PageStyle=1&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(parseInt(typeCode)==5){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/tasklistAction!waitedTask.ilf?template_type=4&PageStyle=1&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}else if(parseInt(typeCode)==6){
					window.open("http://"+$("#hiddenOfGdLink").val()+"/irms/wfFormInfoAction.ilf?template_type=4&PageStyle=1&currentState=3&foreignlogin=true&useraccount="+$("#hiddenOfUsername").val());
				}
			}
			function HoverMenu(overCode){
				$(".leftUpMenus").css({
					"border":"solid 0px #A3D0E3",
					"border-radius":"0px",
					"box-shadow":"0 0 0px #A3D0E3",
					"border":"solid 0px #A3D0E3"
				});
				$("#"+overCode).css({
					"border":"solid 1px #DEEFF8",
					"border-radius":"5px",
					"box-shadow":"0 0 10px #A3D0E3",
					"border":"1px solid #A3D0E3"
				});
			}
			function RemoveHover(){
				$(".leftUpMenus").css({
					"border":"solid 0px #A3D0E3",
					"border-radius":"0px",
					"box-shadow":"0 0 0px #A3D0E3",
					"border":"solid 0px #A3D0E3"
				});
			}
		</script>
  	</head>
  	<body style="width:99.8%;border:solid 1px #FFF;" id="bodyHeight">
  		<input type="hidden" id="hiddenOfHeight" value="<%=frameHeight %>"></input>
  		<input type="hidden" id="hiddenOfUsername" value=""></input>
  		<input type="hidden" id="hiddenOfGdLink" value=""></input>
  		<div id="mainContainer" style="width:99.8%;height:200px;border:solid 0px red;overflow:auto;margin-top:-20px;">
  			<div style="width:18%;height:253px;float:left;border:solid 0px green;" id="leftpart">
  				<div style="width:99.8%;height:240px;border:solid 1px #A3D0E3;margin-top:3px;">
  					<div class="panel panel-default" style="border:0px;">
						<div class="panel-heading" style="border-bottom:solid 1px #A3D0E3;">
							<span class="panel-label"></span>工单任务
						</div>
						<div class="panel-body" style="border:0px;">
							<div class="panlecontent container4 clearfix">
								<div class="div_scroll">
									<div style="height:auto;width:auto">
										<div class="leftUpMenus" id="10011" onmouseover="javascript:HoverMenu(10011);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;cursor:pointer;" onclick="javascript:doGdSystem(1);">
											<div style="float:left;margin-left:7px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/waitingjob.png" style="width:25px;height:24px;margin-top:-1px;"></img>
											</div>
											<div style="float:left;margin-left:5px;margin-top:2px;">
												<font size="2" color="#00988A" id="WAITING_JOB_BUTTON">我的待办任务</font>
											</div>
										</div>
										<div class="leftUpMenus" id="10012" onmouseover="javascript:HoverMenu(10012);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;" onclick="javascript:doGdSystem(2);">
											<div style="float:left;margin-left:11px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/copysend.png" style="width:17px;height:17px;margin-top:1px;"></img>
											</div>
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<font size="2" color="#00988A" id="COPY_JOB_BUTTON">抄送任务</font>
											</div>
										</div>
										<div class="leftUpMenus" id="10013" onmouseover="javascript:HoverMenu(10013);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;" onclick="javascript:doGdSystem(3);">
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/cooperdo.png" style="width:22px;height:22px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:6px;margin-top:2px;">
												<font size="2" color="#00988A" id="COOPER_JOB_BUTTON">协办任务</font>
											</div>
										</div>
										<div class="leftUpMenus" id="10014" onmouseover="javascript:HoverMenu(10014);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;" onclick="javascript:doGdSystem(4);">
											<div style="float:left;margin-left:10px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/runstep.png" style="width:18px;height:18px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:8px;margin-top:2px;">
												<font size="2" color="#00988A" id="DOING_JOB_BUTTON">我的在办任务</font>
											</div>
										</div>
										<div class="leftUpMenus" id="10015" onmouseover="javascript:HoverMenu(10015);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;" onclick="javascript:doGdSystem(5);">
											<div style="float:left;margin-left:10px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/myjobs.png" style="width:18px;height:18px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:8px;margin-top:2px;">
												<font size="2" color="#00988A">我的工单</font>
											</div>
										</div>
										<div class="leftUpMenus" id="10016" onmouseover="javascript:HoverMenu(10016);" onmouseout="javascript:RemoveHover();" style="width:100%;height:25px;border:solid 0px red;margin-top:5px;cursor:pointer;" onclick="javascript:doGdSystem(6);">
											<div style="float:left;margin-left:9px;margin-top:2px;">
												<img src="${pageContext.request.contextPath}/icons/menu/uniselect.png" style="width:20px;height:20px;margin-top:0px;"></img>
											</div>
											<div style="float:left;margin-left:6px;margin-top:2px;">
												<font size="2" color="#00988A">工单综合查询</font>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
  				</div>
  			</div>
  			<%--首页--%>
  			<div style="width:81.5%;height:200px;float:right;border:solid 1px #A3D0E3;overflow:auto;margin-top:3px;" class="ViewShow" id="SY_PART">
  				<div style="width:99.8%;height:155px;border:solid 0px red;margin-top:30px;">
  					<div style="width:27%;height:150px;border:solid 0px red;float:left;">
  						<img src="${pageContext.request.contextPath}/icons/ChinaTower.jpg" style="width:100%;height:140px;"></img>
  					</div>
  					<div style="width:72%;height:150px;border:solid 0px red;float:right;">
  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;margin-top:30px;">
  							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国通信设施服务有限公司的成立有利于减少电信行业内铁塔以及相关基础设施的重复建设，提高行业投资效率，进一步
  						</div>
  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
  							提高电信基础设施共建共享水平，缓解企业选址难的问题，增强企业集约型发展的内生动力，从机制上进一步促进节约资源和
  						</div>
  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
  							环境保护。同时有利于降低中国移动的总体投资规模，有效盘活资产，节省资本开支，优化现金使用，聚焦核心业务运营，提
  						</div>
  						<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
  							升市场竞争能力，加快转型升级。
  						</div>
  					</div>
  				</div>
  				<div style="width:98%;height:220px;border:solid 0px red;margin-left:10px;margin-top:20px;border-top:dotted 1px #A3D0E3;">
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;margin-top:30px;">
  						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2014年 9月3日消息，继省公司总经理选聘结束后，铁塔公司又开始启动省公司副总选聘，由三大运营商根据内部报名情况推荐候选人，省公司副总与正职不能来自同
  					</div>
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
  						一运营商。[2]2014年09月11日从中国铁塔股份有限公司了解到，“中国通信设施服务股份有限公司”进行了工商变更登记手续，正式更名为“中国铁塔股份有限公司”。[3]2014
  					</div>
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
  						年11月5日从中国铁塔股份有限公司获悉，铁塔公司已全部完成地市分公司和31个省级分公司部门负责人的选聘工作，已聘共计842人，均来自三大电信运营商。至此，铁塔
  					</div>
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
						公司基本完成从总部到省、市三级公司的组建。 中国铁塔公司有关负责人介绍，此次聘用人选共计842人：中国移动307人，中国联通279人，中国电信256人，聘用人选平			
  					</div>
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
						均年龄42岁。铁塔公司31个省级分公司中，安徽、贵州、河北、青海、重庆、江西、吉林、四川、湖南、陕西、湖北、黑龙江、山东、云南、海南、甘肃、福建17个分公司									
  					</div>
  					<div style="width:99.8%;height:30px;border:solid 0px red;font-size:14px;">
						已经先后揭牌成立。									
  					</div>
  				</div>
  			</div>
  		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</html>
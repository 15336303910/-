<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<title>工作台</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/todc-bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/js/table/superTable.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/css/body-layout.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/index/content/css/test.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/xcConfirm/css/xcConfirm.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/xcConfirm/js/xcConfirm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/HashMap.js"></script>
		<script type="text/javascript">
			var ruleAche = null;
			$(document).ready(function(){
				window.parent.loading(true);
				initMostTrouble();
				initMostPerfect();
				initPerfectUser();
				initNews();
				initTools();
				window.parent.overLoading(true);
			});
			/*
			 	获取已配置的互联网工具
			 */
			function initTools(){
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findTools.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["success"] && textStatus["items"]!=null && textStatus["items"].length>0){
							var tools = textStatus["items"];
							for(var i=0;i<tools.length;i++){
								var $tool = tools[i];
								if(i==0){
									$("#IMAGE_1").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+$tool["APP_ICON"]);
									$("#NAME_1").html($tool["APP_NAME"]);
									var title = "应用详情:"+$tool["APP_NAME"];
									var url = "jsp/tools/toolDetail.jsp?detailId="+$tool["ID"];
									$("#DIV_1").attr("onclick","javascript:window.parent.turnToJsp('"+title+"','"+url+"');");
								}else if(i==1){
									$("#IMAGE_2").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+$tool["APP_ICON"]);
									$("#NAME_2").html($tool["APP_NAME"]);
									var title = "应用详情:"+$tool["APP_NAME"];
									var url = "jsp/tools/toolDetail.jsp?detailId="+$tool["ID"];
									$("#DIV_2").attr("onclick","javascript:window.parent.turnToJsp('"+title+"','"+url+"');");
								}else if(i==2){
									$("#IMAGE_3").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+$tool["APP_ICON"]);
									$("#NAME_3").html($tool["APP_NAME"]);
									var title = "应用详情:"+$tool["APP_NAME"];
									var url = "jsp/tools/toolDetail.jsp?detailId="+$tool["ID"];
									$("#DIV_3").attr("onclick","javascript:window.parent.turnToJsp('"+title+"','"+url+"');");
								}else if(i==3){
									$("#IMAGE_4").attr("src","${pageContext.request.contextPath}/jsp/tools/images/"+$tool["APP_ICON"]);
									$("#NAME_4").html($tool["APP_NAME"]);
									var title = "应用详情:"+$tool["APP_NAME"];
									var url = "jsp/tools/toolDetail.jsp?detailId="+$tool["ID"];
									$("#DIV_4").attr("onclick","javascript:window.parent.turnToJsp('"+title+"','"+url+"');");
								}
							}
							if(tools.length<4){
								var startIndex = tools.length+1;
								for(var j=startIndex;j<=4;j++){
									$("#TOOL_"+j).html("&nbsp;");
								}
							}
						}else{
							$("#TOOL_1").html("&nbsp;");
							$("#TOOL_2").html("&nbsp;");
							$("#TOOL_3").html("&nbsp;");
							$("#TOOL_4").html("&nbsp;");
						}
					},
					error:function(){}
				});
			}
			/*
				获取最新的二十条规则滚动展示.
			 */
			var acheNews = null;
			var startIndex = 0;
			function initNews(){
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findNews.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000,
					success:function(textStatus){
						if(textStatus["success"]){
							startIndex = 0;
							var items = textStatus["items"];
							if(items.length>0){
								acheNews = items;
							}
							flushNews();
						}
					},
					error:function(){}
				});
			}
			function flushNews(){
				if(acheNews!=null && acheNews.length>0){
					var limitIndex = startIndex+4;
					var itemHtml = "";
					var account = 0;
					for(var i=startIndex;i<=limitIndex;i++){
						if(account==0 && i>=acheNews.length){
							startIndex = 0;
							limitIndex = startIndex+4;
							i = 0;
						}else{
							if(i>=acheNews.length){
								i = 0;
								limitIndex = 4-account;
							}
						}					
						var ruleItem = acheNews[i];
						var $date = new Date();
						var ruleName = ruleItem["RULE_NAME"];
						if(ruleName.length>18){
							ruleName = ruleName.substr(0,18)+"...";
						}
						$date.setTime(ruleItem["CREATE_TIME"]["time"]);
						itemHtml+="<div style='width:95%;margin-left:10px;height:28px;border-bottom:dotted 1px #CCC;'>";
							itemHtml+="<div style='margin-top:5px;'><font color='green'>["+$date.toLocaleDateString()+"]</font>&nbsp;<font color='red'>["+ruleItem["RULE_VERSION"]+"]</font>&nbsp;"+ruleName+"</div>";
						itemHtml+="</div>";
						account++;
					}
					document.getElementById("newsContainer").innerHTML = itemHtml;
					startIndex++;
					setTimeout("javascript:flushNews();",2000);
				}
			}		
			/*
				获取得分较低（倒数前五名）的项指标得分.
			 */
			function initMostTrouble(){
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findMostTrouble.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#userIconImg").attr("src","${pageContext.request.contextPath}/extensions/index/heads/"+textStatus["loginUserIcon"]);
							$("#loginUserName").html(textStatus["loginUserCode"]);
							$("#loginUserCompany").html(textStatus["loginUserCompany"]);
							/*总分及数据质量评级*/
							if(textStatus["finalScore"]!=null){
								var finalScore = parseFloat(textStatus["finalScore"]);
								var finalGrade = "";
								if(finalScore>=90){
									finalGrade = "优";
									document.getElementById("auditGrade").style.color = "green";
								}else if(finalScore>=70 && finalScore<90){
									finalGrade = "良";
									document.getElementById("auditGrade").style.color = "orange";
								}else{
									finalGrade = "差";
									document.getElementById("auditGrade").style.color = "red";
								}
								document.getElementById("auditGrade").innerHTML = finalGrade;
								initScores(finalScore);
							}
							/*核查指标总数*/
							if(textStatus["checkedCount"]!=null){
								document.getElementById("checkedCount").innerHTML = textStatus["checkedCount"];
							}
							/*得分较低的项*/
							if(textStatus["items"]!=null){
								if(ruleAche==null){
									ruleAche = new HashMap();
								}
								document.getElementById("seriousCount").innerHTML = textStatus["items"].length;
								if(textStatus["items"].length>0){
									var problemHtml = "";
									for(var i=0;i<textStatus["items"].length;i++){
										var $item = textStatus["items"][i];
										var scores = parseFloat($item["FINAL_SCORE"]);
										problemHtml+="<div>";
										problemHtml+="	  <h6 class='listtitle pr danger js-title'>";
										problemHtml+="		  <a href='javascript:;' class='db'>";
										problemHtml+="			  <i class='ticon ti-tip vm'></i>";
										problemHtml+="			  <b class='vm ml10'>"+$item["INDEX_NAME"]+"&nbsp[得分:"+scores.toFixed(1)+"]</b>";
										problemHtml+="			  <i class='ticon ti-add'></i>";
										problemHtml+="		  </a>";
										problemHtml+="	  </h6>";
										problemHtml+="	  <div class='js-con'>";
										problemHtml+="		  <ul class='childinfolist Qinfo'>";
										/*严重扣分项*/
										var leastRules = $item["LEAST_RULE"];
										for(var j=0;j<leastRules.length;j++){
											var ruleObject = leastRules[j];
											ruleAche.put(ruleObject["RULE_ID"],ruleObject["RESOLVE_METHOD"]);
											problemHtml+="		  <li><div style='border-bottom:dotted 1px #FA4874;height:35px;'>[严重扣分项]"+ruleObject["RULE_NAME"]+"（得分："+ruleObject["NORMAL_RATIO"].toFixed(1)+"）<a href='#countModal' role='button' data-toggle='modal' style='margin-left:10px;cursor:pointer;' onclick='javascript:flushMethod("+ruleObject["RULE_ID"]+");'>解决建议</a></div></li>";
										}
										problemHtml+="		  </ul>";
										problemHtml+="	  </div>";
										problemHtml+="</div>";
									}
									document.getElementById("problemContainer").innerHTML = problemHtml;
								}else{
									$("#problemBuck").hide();	
								}
							}else{
								$("#problemBuck").hide();
							}
						}
					},
					error:function(){}
				});
			}
			function flushMethod(ruleCode){
				$("#resolveMethod").html(ruleAche.get(ruleCode));
			}
			/*
				获取得分较高（正数前五名）的项用户得分.
			 */
			var userMap = null;
			function initPerfectUser(){
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findPerfectUsers.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						userMap = new HashMap();
						if(textStatus["success"] && textStatus["ITEMS"]!=null && textStatus["ITEMS"].length>0){
							for(var i=1;i<=textStatus["ITEMS"].length;i++){
								var $item = textStatus["ITEMS"][i-1];
								userMap.put(i,$item["ID"]);
								$("#IMG_"+i).attr("src","${pageContext.request.contextPath}/extensions/index/heads/"+$item["USER_ICON"]);
								$("#USER_"+i).html($item["EMPLOYEE_NAME"]);
								$("#COMPANY_"+i).html($item["EMPLOYEE_COMPANY"]);
								$("#SCORE_"+i).html(parseFloat($item["FINAL_SCORE"]).toFixed(1));
								$("#FUCK_"+i).html($item["OH_MY_GOD"]);
							}				
						}
					},
					error:function(){}
				});
			}
			function doUpdate(userOrder){
				var userCode = userMap.get(userOrder);
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/doFuck.ilf",
					async:false,
					type:"POST",
					data:"userCode="+userCode,
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"]){
							$("#FUCK_"+userOrder).html(textStatus["NEW_VALUE"]);
							$("#PERFECT_"+userOrder).removeAttr("onclick");
							$("#PERFECT_"+userOrder).attr("onclick","javascript:haveUpdated();");
						}
					},
					error:function(){}
				});
			}
			function haveUpdated(){
				window.wxc.xcConfirm("您今天已经给此员工点过赞了.",window.wxc.xcConfirm.typeEnum.info);
			}
			/*
				获取得分较高（正数前五名）的项指标得分.
			 */
			function initMostPerfect(){
				$.ajax({
					url:"${pageContext.request.contextPath}/indexAction/findPerfects.ilf",
					async:false,
					type:"POST",
					dataType:"json",
					timeout:10000, 
					success:function(textStatus){
						if(textStatus["success"] && textStatus["items"]!=null && textStatus["items"].length>0){
							var okHtml = "";
							for(var i=0;i<textStatus["items"].length;i++){
								var $item = textStatus["items"][i];
								var scores = parseFloat($item["FINAL_SCORE"]);
								okHtml+="<div class='borderbottom'>";
								okHtml+="	<h6 class='listtitle ltnormal pr js-title'>";
								okHtml+="		<a href='javascript:;' class='db'>";
								okHtml+="			<i class='ticon ti-t3 vm'></i>";
								okHtml+="			<span class='protip vm ml10'>"+$item["INDEX_NAME"]+"&nbsp;[<font color='green'>得分:"+scores.toFixed(1)+"</font>]</span>";
								okHtml+="		</a>";
								okHtml+="	</h6>";
								okHtml+="</div>";
							}				
							document.getElementById("perfectContainer").innerHTML = okHtml;
						}else{
							document.getElementById("perfectContainer").innerHTML = "&nbsp;";
						}
					},
					error:function(){}
				});
			}
			function initScores(finalScore){
				var grayStep = 1;
				if(finalScore>=0 && finalScore<=15){
					grayStep = 2;
					$("#score_1").html(finalScore);
				}else if(finalScore>15 && finalScore<=30){
					grayStep = 3;
					$("#score_2").html(finalScore);
				}else if(finalScore>30 && finalScore<=45){
					grayStep = 4;
					$("#score_3").html(finalScore);
				}else if(finalScore>45 && finalScore<=60){
					grayStep = 5;
					$("#score_4").html(finalScore);
				}else if(finalScore>60 && finalScore<=75){
					grayStep = 6;
					$("#score_5").html(finalScore);
				}else if(finalScore>75 && finalScore<=90){
					grayStep = 7;
					$("#score_6").html(finalScore);
				}else{
					grayStep = 8;
					$("#score_7").html(finalScore);
				}
				if(grayStep>=1 && grayStep<=8){
					var currentIndex = grayStep-1;
					for(var j=1;j<=7;j++){
						if(j!=currentIndex){
							$("#score_"+j).html("&nbsp;");
						}
					}
				}			
				for(var i=grayStep;i<=7;i++){
					$("#score_"+i).html("&nbsp;");
					if(i<=3){
						$("#token_"+i).removeClass("s-danger");
					}else if(i>=4 && i<=5){
						$("#token_"+i).removeClass("s-warning");
					}else if(i>=6 && i<=7){
						$("#token_"+i).removeClass("s-ok");
					}
				}
			}
			function editMyIcon(){
				window.parent.turnToJsp("我的头像","jsp/firstIndex/iconEdit.jsp");
			}
		</script>
  	</head>
  	<body>
    	<div class="index-content clearfix" style="margin-top:0px;">
			<div id="indexcontent" class="ovhidden">
				<div class="leftside">
					<div class="panle panle-title panle-footer" id="js-box1-1">
						<div class="panletitle">
							<span class="panle-label"></span>个人信息
						</div>
						<div class="panlecontent">
							<div class="user-info">
								<div class="user-header">
									<img id="userIconImg" src="${pageContext.request.contextPath}/extensions/index/heads/1034.png" style="width:87px;height:87px;margin-left:5px;margin-top:5px;border-radius:50%;" alt=""></img>
								</img></div>
								<p class="user-name" style="margin-left:17px;">
									<a id="loginUserName">&nbsp;</a><br/>
									<span class="user-group" id="loginUserCompany">&nbsp;</span>
								</p>
								<p style="margin-left:17px;margin-top:-10px;">
									<a class="btn btn-exit btn-large" onclick="javascript:window.parent.quitSystem();">注销登录</a>
								</p>
							</div>
						</div>
						<div class="panlefooter">
							<ul class="user-ico clearfix">
								<li><a style="cursor:pointer" onclick="javascript:editMyIcon();"><span class="uicon u-upimg"></span>修改头像</a></li>
								<li class="last"><a onclick="javascript:window.parent.modifyPassword();" style="cursor:pointer"><span class="uicon u-lock"></span>修改密码</a></li>
							</ul>
						</div>
					</div>
					<div class="panle panle-title clearfix" id="js-box1-2">
						<div class="panletitle">
							<span class="panle-label"></span>我的待办
						</div>
						<div class="panlecontent">
							<ul class="nav-list m10">
								<li>
									<a class="pr">
										<i class="CircleMod Circle-green"></i>
										<span class="menu-text">待办工单</span>
										<i class="pa icon-plus-sign tip-icon-pa"></i>
									</a>
									<ul class="submenu none">
										<li><a><i class="icon-list-alt "></i>已超时工单</a></li>
										<li><a><i class="icon-list-alt "></i>即将超时工单</a></li>
										<li><a><i class="icon-list-alt "></i>未及时履约工单</a></li>
										<li><a><i class="icon-list-alt "></i>未超时工单</a></li>
									</ul>
								</li>
								<li>
									<a href="javascript:;" class="pr">
										<i class="CircleMod Circle-org"></i>
										<span class="menu-text">待处理核查</span>
										<i class="pa icon-plus-sign tip-icon-pa"></i>
									</a>
									<ul class="submenu none">
										<li><a href="javascript:;"><i class="icon-list-alt "></i>已超时工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>即将超时工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>未及时履约工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>未超时工单</a></li>
									</ul>
								</li>
								<li>
									<a href="javascript:;" class="pr">
										<i class="CircleMod Circle-red"></i>
										<span class="menu-text">待审批</span>
										<i class="pa icon-plus-sign tip-icon-pa"></i>
									</a>
									<ul class="submenu none">
										<li><a href="javascript:;"><i class="icon-list-alt "></i>已超时工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>即将超时工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>未及时履约工单</a></li>
										<li><a href="javascript:;"><i class="icon-list-alt "></i>未超时工单</a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="centerside">
					<div class="centerside-inner">
						<div class="panle mb0">
							<div class="panlecontent" id="js-box2-1">
								<div class="T-top T-checking" id="js-top-box">
									<div class="T-icon-bg"><i></i></div>
									<div class="T-step">
										<div class="clearfix h44">
											<h3 class="T-status pull-left">您当前数据质量环境为<font color='green' id="auditGrade">良</font></h3>
										</div>
										<ul class="T-steplist clearfix">
											<li class="s-danger" id="token_1">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_1">15</p>
											</li>
											<li class="s-danger" id="token_2">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_2">30</p>
											</li>
											<li class="s-danger" id="token_3">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_3">45</p>
											</li>
											<li class="s-warning" id="token_4">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_4">60</p>
											</li>
											<li class="s-warning" id="token_5">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_5">75</p>
											</li>
											<li class="s-ok" id="token_6">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_6">90</p>
											</li>
											<li class="s-ok" id="token_7">
												<i class="stepdot"><b></b></i>
												<p class="tc steptitle" id="score_7">100</p>
											</li>
										</ul>
									</div>
								</div>
								<div class="T-ResultBox clearfix">
									<div class="w" id="j-test-Result">
										<div class="">
											<div class="">
												<div class="T-info danger-box" id="problemBuck">
													<h1 class="infotitle pr js-title">
														<a href="javascript:;" class="db">
															共核查<font id="checkedCount">0</font>项指标，以下<font id="seriousCount">0</font>项指标有严重问题<i class="ticon ti-arrow"></i>
														</a>
													</h1>
													<div class="pl15 pr15 pt10 pb10 js-con" style="display:block" id="problemContainer">&nbsp;</div>
												</div>
												<div class="T-info">
													<h1 class="infotitle pr js-title">
														<a href="javascript:;" class="db">
															<font color="green">以下核查得分良好</font>
															<i class="ticon ti-arrow"></i>
														</a>
													</h1>
													<div class="pl15 pr15 js-con" style="display:block" id="perfectContainer">&nbsp;</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="rightside">
					<div class="panle panle-title panle-footer" id="js-box3-1" style="height:140px;">
						<div class="panletitle">
							<span class="panle-label"></span>最新规则发布公告
							<a class="panle-setting"></a>
						</div>
						<div class="panlecontent">
							<div class="pr">
								<i class="Notice-tip">NEW</i>
								<div style="width:99%" id="newsContainer">&nbsp;</div>
							</div>
						</div>
					</div>
					<div class="panle panle-title div-open" id="js-box3-2">
						<div class="panletitle">
							<span class="panle-label"></span>系统模块
						</div>
						<div class="panlecontent">
							<div class="clearfix pt5 pb5 pl10 pr10 borderbottom">
								<ul class="nav nav-tabs pull-left">
									<li class="active">
										<a href="#todo-two" data-toggle="tab">个人排名Top5</a>
									</li>
									<li>
										<a href="#todo-one" data-toggle="tab">常用工具导航</a>
									</li>
								</ul>
							</div>
							<div class="tab-content">
								<div class="tab-pane active js-box2-2-block" id="todo-two">
									<div id="userContainer">
										<ul class="RankList">
											<li class="active">
												<i class="num-bg bg-red">1</i>
												<div class="proinfo pr">
													<h6 id="USER_1">李庆振</h6>
													<p id="COMPANY_1" class="mb0 mt10 advstyle cutword">浙江省移动省公司</p>
													<div class="advstyle appImg">
														<img id="IMG_1" src="${pageContext.request.contextPath}/extensions/index/img/1.jpg" style="width:45px;height:45px;border-radius:50%;"></img>
													</div>
												</div>
												<div class="pa markbox">
													<b class="color-red f16 mr5" id="SCORE_1">89</b><span class="f12 fn font-gary">分</span>
												</div>
												<a class="pa downlink" id="PERFECT_1" style="cursor:pointer;" onclick="javascript:doUpdate(1);">
													<span><i class="icon icon-thumbs-up"></i><font id="FUCK_1">0</font></span>
												</a>
											</li>
											<li>
												<i class="num-bg bg-yellow">2</i>
												<div class="proinfo pr">
													<h6 id="USER_2">王海涛</h6>
													<p id="COMPANY_2" class="mb0 mt10 advstyle cutword">杭州分公司</p>
													<div class="advstyle appImg">
														<img id="IMG_2" src="${pageContext.request.contextPath}/extensions/index/img/2.jpg" style="width:45px;height:45px;border-radius:50%;"></img>
													</div>
												</div>
												<div class="pa markbox">
													<b class="color-red f16 mr5" id="SCORE_2">80</b><span class="f12 fn font-gary">分</span>
												</div>
												<a class="pa downlink" id="PERFECT_2" style="cursor:pointer;" onclick="javascript:doUpdate(2);">
													<span><i class="icon icon-thumbs-up"></i><font id="FUCK_2">0</font></span>
												</a>
											</li>
											<li>
												<i class="num-bg bg-green">3</i>
												<div class="proinfo pr">
													<h6 id="USER_3">刘庆朋</h6>
													<p id="COMPANY_3" class="mb0 mt10 advstyle cutword">义乌分公司</p>
													<div class="advstyle appImg">
														<img id="IMG_3" src="${pageContext.request.contextPath}/extensions/index/img/3.jpg" style="width:45px;height:45px;border-radius:50%;"></img>
													</div>
												</div>
												<div class="pa markbox">
													<b class="color-red f16 mr5" id="SCORE_3">79</b><span class="f12 fn font-gary">分</span>
												</div>
												<a class="pa downlink" id="PERFECT_3" style="cursor:pointer;" onclick="javascript:doUpdate(3);">
													<span><i class="icon icon-thumbs-up"></i><font id="FUCK_3">0</font></span>
												</a>
											</li>
											<li>
												<i class="num-bg">4</i>
												<div class="proinfo pr">
													<h6 id="USER_4">董志富</h6>
													<p id="COMPANY_4" class="mb0 mt10 advstyle cutword">余杭分公司</p>
													<div class="advstyle appImg">
														<img id="IMG_4" src="${pageContext.request.contextPath}/extensions/index/img/4.jpg" style="width:45px;height:45px;border-radius:50%;"></img>
													</div>
												</div>
												<div class="pa markbox">
													<b class="color-red f16 mr5" id="SCORE_4">75</b><span class="f12 fn font-gary">分</span>
												</div>
												<a class="pa downlink" id="PERFECT_4" style="cursor:pointer;" onclick="javascript:doUpdate(4);">
													<span><i class="icon icon-thumbs-up"></i><font id="FUCK_4">0</font></span>
												</a>
											</li>
											<li>
												<i class="num-bg">5</i>
												<div class="proinfo pr">
													<h6 id="USER_5">刘连猛</h6>
													<p id="COMPANY_5" class="mb0 mt10 advstyle cutword">义乌分公司</p>
													<div class="advstyle appImg">
														<img id="IMG_5" src="${pageContext.request.contextPath}/extensions/index/img/5.jpg" style="width:45px;height:45px;border-radius:50%;"></img>
													</div>
												</div>
												<div class="pa markbox">
													<b class="color-red f16 mr5" id="SCORE_5">73</b><span class="f12 fn font-gary">分</span>
												</div>
												<a class="pa downlink" id="PERFECT_5" style="cursor:pointer;" onclick="javascript:doUpdate(5);">
													<span><i class="icon icon-thumbs-up"></i><font id="FUCK_5">0</font></span>
												</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="new-table tab-pane js-box2-2-block" id="todo-one">
									<div class="new-task">
										<div id="new-task-main" style="margin-top:-15px;">
											<table cellpadding="0" cellspacing="0" style="width:99%;">
												<tr height="120">
													<td style="width:50%;" id="TOOL_1">
														<center>
															<div id="DIV_1" style="width:80%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;">
																<div style="width:100%;height:65px;margin-top:5px;">
																	<img id="IMAGE_1" src="${pageContext.request.contextPath}/jsp/tools/images/words.png" style='height:50px;width:50px;margin-top:10px;'></img>
																</div>
																<div style="width:100%;height:25px;margin-top:5px;">
																	<font id="NAME_1" color='#4AA3DF' size="3">协同办公</font>
																</div>
															</div>
														</center>
													</td>
													<td style="width:50%;" id="TOOL_2">
														<center>
															<div id="DIV_2" style="width:80%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;">
																<div style="width:100%;height:65px;margin-top:5px;">
																	<img id="IMAGE_2" src="${pageContext.request.contextPath}/jsp/tools/images/clocks.png" style='height:50px;width:50px;margin-top:10px;'></img>
																</div>
																<div style="width:100%;height:25px;margin-top:5px;">
																	<font id="NAME_2" color='#4FA6E0' size="3">备忘录</font>
																</div>
															</div>
														</center>
													</td>
												</tr>
												<tr height="120">
													<td style="width:50%;" id="TOOL_3">
														<center>
															<div id="DIV_3" style="width:80%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;">
																<div style="width:100%;height:65px;margin-top:5px;">
																	<img id="IMAGE_3" src="${pageContext.request.contextPath}/jsp/tools/images/navigate.png" style='height:50px;width:50px;margin-top:10px;'></img>
																</div>
																<div style="width:100%;height:25px;margin-top:5px;">
																	<font id="NAME_3" color='#4AA3DF' size="3">企业类型查询</font>
																</div>
															</div>
														</center>
													</td>
													<td style="width:50%;" id="TOOL_4">
														<center>
															<div id="DIV_4" style="width:80%;height:105px;border:dotted 1px #A3D0E3;border-radius:10px;cursor:pointer;">
																<div style="width:100%;height:65px;margin-top:5px;">
																	<img id="IMAGE_4" src="${pageContext.request.contextPath}/jsp/tools/images/clouds.png" style='height:50px;width:50px;margin-top:10px;'></img>
																</div>
																<div style="width:100%;height:25px;margin-top:5px;">
																	<font id="NAME_4" color='#4FA6E0' size="3">浪潮云</font>
																</div>
															</div>
														</center>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%--窗口表单：开始--%>
		<div id="countModal" class="modal hide fade" style="width:650px;height:300px;margin-left:-300px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h5 id="myModalLabel">+ 解决建议</h5>
			</div>
			<div class="modal-body" style="max-height:230px;">
				<div style="width:100%;height:200px;border:solid 1px #FFF;" id="resolveMethod">
					
				</div>
			</div>
			<div class="modal-footer tc">
				<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">
					<i class="icon-zoom-out icon-white"></i>关闭窗口
				</button>
			</div>
		</div>
    </body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/ConScroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/index/js/plugins.js"></script>
</html>

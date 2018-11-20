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
    	<title>数据列表详情</title>
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
		var id='<%=request.getParameter("id")%>';
		var risk_name='<%=request.getParameter("risk_name")%>';
		var tab_id='<%=request.getParameter("tab_id")%>';
		risk_name=decodeURI(risk_name);	
		$(document).ready(function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/feedbackDataListDetailsAction/findFeedbackDataListDetails.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:{"risk_name":risk_name,"id":id,"tab_id":tab_id},
					timeout:10000,
					success:function(data){
						var list=data["list"][0];
						if(risk_name=="合同信息与收款不一致"){
							$("#fund_1").css("display","block");
							$("#f1_con_owner").html(list["CON_OWNER"]);
							$("#f1_receiver").html(list["RECEIVER"]);
							$("#f1_con_number").html(list["CON_NUMBER"]);
							$("#f1_con_name").html(list["CON_NAME"]);
							$("#f1_pay_staff").html(list["PAY_STAFF"]);
							$("#f1_qu_des").html(list["QU_DES"]);
							$("#f1_reason").html(list["REASON"]);
						}else if(risk_name=="场租电费可能重复支付提示"){
							$("#fund_2").css("display","block");
							$("#f2_sta_number").html(list["STA_NUMBER"]);
							$("#f2_sta_name").html(list["STA_NAME"]);
							$("#f2_con_number").html(list["CON_NUMBER"]);
							$("#f2_con_name").html(list["CON_NAME"]);
							$("#f2_con_owner").html(list["CON_OWNER"]);
							$("#f2_receiver").html(list["RECEIVER"]);
							$("#f2_el_receiver").html(list["EL_RECEIVER"]);
							$("#f2_el_staff").html(list["EL_STAFF"]);
							$("#f2_re_receiver").html(list["RE_RECEIVER"]);
							$("#f2_re_staff").html(list["RE_STAFF"]);
							$("#f2_pay_cycle").html(list["PAY_CYCLE"]);
							$("#f2_qu_des").html(list["QU_DES"]);
							$("#f2_reason").html(list["REASON"]);
						}else if(risk_name=="销项选址费支付提示"){
							$("#fund_3").css("display","block");
							$("#f3_sta_number").html(list["STA_NUMBER"]);
							$("#f3_sta_name").html(list["STA_NAME"]);
							$("#f3_select_pay").html(list["SELECT_PAY"]);
							$("#f3_oc_time").html(time(list["OC_TIME"]));
							$("#f3_qu_des").html(list["QU_DES"]);
							$("#f3_reason").html(list["REASON"]);
						}else if(risk_name=="超两月未核销电费预付款"){
							$("#fund_4").css("display","block");
							$("#f4_sta_number").html(list["STA_NUMBER"]);
							$("#f4_sta_name").html(list["STA_NAME"]);
							$("#f4_con_number").html(list["CON_NUMBER"]);
							$("#f4_con_name").html(list["CON_NAME"]);
							$("#f4_pre_number").html(list["PRE_NUMBER"]);
							$("#f4_pre_balance").html(list["PRE_BALANCE"]);
							$("#f4_qu_des").html(list["QU_DES"]);
							$("#f4_reason").html(list["REASON"]);
						}else if(risk_name=="租金支付分析"){
							$("#fund_5").css("display","block");
							$("#f5_sta_number").html(list["STA_NUMBER"]);
							$("#f5_sta_name").html(list["STA_NAME"]);
							$("#f5_con_number").html(list["CON_NUMBER"]);
							$("#f5_con_name").html(list["CON_NAME"]);
							$("#f5_const_time").html(time(list["CONST_TIME"]));
							$("#f5_cycle").html(list["CYCLE"]);
							$("#f5_rp_time").html(time(list["RP_TIME"]));
							$("#f5_pay_amount").html(list["PAY_AMOUNT"]);
							$("#f5_pt_earliest").html(time(list["PT_EARLIEST"]));
							$("#f5_rpf_time").html(time(list["RPF_TIME"]));
							$("#f5_qu_des").html(list["QU_DES"]);
							$("#f5_reason").html(list["REASON"]);
						}else if(risk_name=="连续三月停止计收站仍在支付场租站提示"){
							$("#fund_6").css("display","block");
							$("#f6_sta_number").html(list["STA_NUMBER"]);
							$("#f6_sta_name").html(list["STA_NAME"]);
							$("#f6_pay_order").html(list["PAY_ORDER"]);
							$("#f6_pay_number").html(list["PAY_NUMBER"]);
							$("#f6_qu_des").html(list["QU_DES"]);
							$("#f6_reason").html(list["REASON"]);
						}else if(risk_name=="预付场租费超期未核销"){
							$("#fund_7").css("display","block");
							$("#f7_sta_number").html(list["STA_NUMBER"]);
							$("#f7_sta_name").html(list["STA_NAME"]);
							$("#f7_cre_time").html(time(list["CRE_TIME"]));
							$("#f7_des_time").html(time(list["DES_TIME"]));
							$("#f7_qu_des").html(list["QU_DES"]);
							$("#f7_reason").html(list["REASON"]);
						}else if(risk_name=="一站多合同"){
							$("#fund_8").css("display","block");
							$("#f8_sta_number").html(list["STA_NUMBER"]);
							$("#f8_sta_name").html(list["STA_NAME"]);
							$("#f8_con_number").html(list["CON_NUMBER"]);
							$("#f8_con_name").html(list["CON_NAME"]);
							$("#f8_owner").html(list["OWNER"]);
							$("#f8_qu_des").html(list["QU_DES"]);
							$("#f8_reason").html(list["REASON"]);
						}else if(risk_name=="计收有关数据差异"){
							$("#income_1").css("display","");
							$("#i1_sta_number").html(list["STA_NUMBER"]);
							$("#i1_sta_name").html(list["STA_NAME"]);
							$("#i1_pro_number").html(list["PRO_NUMBER"]);
							$("#i1_pro_name").html(list["PRO_NAME"]);
							$("#i1_CRM_pay_day").html(list["CRM_PAY_DAY"]);
							$("#i1_qu_des").html(risk_name);
							$("#i1_reason").html(list["REASON"]);
						}else if(risk_name=="外验三个月未起租"){
							$("#income_2").css("display","");
							$("#i2_sta_number").html(list["STA_NUMBER"]);
							$("#i2_sta_name").html(list["STA_NAME"]);
							$("#i2_qu_des").html(risk_name);
							$("#i2_reason").html(list["REASON"]);
						}else if(risk_name=="工程项目线上线下一致性"){
							$("#project_1").css("display","");
							$("#p1_pro_number").html(list["PRO_NUMBER"]);
							$("#p1_pro_name").html(list["PRO_NAME"]);
							$("#p1_pro_type").html(list["PRO_TYPE"]);
							$("#p1_pro_state").html(list["PRO_STATE"]);
							$("#p1_qu_des").html(risk_name);
							$("#p1_reason").html(list["REASON"]);
						}else if(risk_name=="新建站造价异常分析"){
							$("#project_2").css("display","");
							$("#p2_pro_number").html(list["PRO_NUMBER"]);
							$("#p2_pro_name").html(list["PRO_NAME"]);
							$("#p2_sta_number").html(list["STA_NUMBER"]);
							$("#p2_sta_name").html(list["STA_NAME"]);
							$("#p2_con_type").html(list["CON_TYPE"]);
							$("#p2_invest_total").html(list["INVEST_TOTAL"]);
							$("#p2_qu_des").html(risk_name);
							$("#p2_reason").html(list["REASON"]);
						}else if(risk_name=="转资金额较设计批复金额偏差分析"){
							$("#project_3").css("display","");
							$("#p3_pro_number").html(list["PRO_NUMBER"]);
							$("#p3_pro_name").html(list["PRO_NAME"]);
							$("#p3_sta_number").html(list["STA_NUMBER"]);
							$("#p3_sta_name").html(list["STA_NAME"]);
							$("#p3_reply_number").html(list["REPLY_NUMBER"]);
							$("#p3_ct_number").html(list["CT_NUMBER"]);
							$("#p3_iaf_time").html(time(list["IAF_TIME"]));
							$("#p3_das_time").html(time(list["DAS_TIME"]));
							$("#p3_qu_des").html(risk_name);
							$("#p3_reason").html(list["REASON"]);
						}else if(risk_name=="实际建造方式与运营商订单确定建造方式不一致"){
							$("#project_4").css("display","");
							$("#p4_pro_number").html(list["PRO_NUMBER"]);
							$("#p4_pro_name").html(list["PRO_NAME"]);
							$("#p4_sta_number").html(list["STA_NUMBER"]);
							$("#p4_sta_name").html(list["STA_NAME"]);
							$("#p4_db_number").html(list["DB_NUMBER"]);
							$("#p4_de_operator").html(list["DE_OPERATOR"]);
							$("#p4_PMS_type").html(list["PMS_TYPE"]);
							$("#p4_CRM_type").html(list["CRM_TYPE"]);
							$("#p4_qu_des").html(risk_name);
							$("#p4_reason").html(list["REASON"]);
						}else if(risk_name=="转供电电费畸高分析"){
							$("#station_1").css("display","");
							$("#s1_sta_number").html(list["STA_NUMBER"]);
							$("#s1_sta_name").html(list["STA_NAME"]);
							$("#s1_cu_elec").html(list["CU_ELEC"]);
							$("#s1_qu_des").html(risk_name);
							$("#s1_reason").html(list["REASON"]);
						}else if(risk_name=="每月抄表情况分析"){
							$("#station_2").css("display","");
							$("#s2_sta_number").html(list["STA_NUMBER"]);
							$("#s2_sta_name").html(list["STA_NAME"]);
							$("#s2_qu_des").html(risk_name);
							$("#s2_reason").html(list["REASON"]);
						}else if(risk_name=="零场租合同分析"){
							$("#colocation_1").css("display","");
							$("#c1_sta_number").html(list["STA_NUMBER"]);
							$("#c1_sta_name").html(list["STA_NAME"]);
							$("#c1_pro_number").html(list["PRO_NUMBER"]);
							$("#c1_zc_reason").html(list["ZC_REASON"]);
							$("#c1_qu_des").html(risk_name);
							$("#c1_reason").html(list["REASON"]);
						}else if(risk_name=="租金成本分析"){
							$("#colocation_2").css("display","");
							$("#c2_sta_number").html(list["STA_NUMBER"]);
							$("#c2_sta_name").html(list["STA_NAME"]);
							$("#c2_con_number").html(list["CON_NUMBER"]);
							$("#c2_con_name").html(list["CON_NAME"]);
							$("#c2_rent_year").html(list["RECENT_YEAR"]);
							$("#c2_qu_des").html(risk_name);
							$("#c2_reason").html(list["REASON"]);
						}
					}
					
				});
			});
			/*时间格式化*/
			function time(mouth){
				if(mouth){
					var Y=mouth["year"]+1900 + '-';
					var M=(mouth["month"]+1 < 10 ? '0'+(mouth["month"]+1) : mouth["month"]+1);
					return Y+M;
				}else{
					return "";
				}
				
			}
			/*关闭页面*/
			function html_close(){
				var userAgent = navigator.userAgent;
			    if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
			        window.location.href="about:blank";
			        window.close();
			    } else {
			        window.opener = null;
			        window.open("", "_self");
			        window.close();
			    }
			}
		
		</script>	
	
	
	
	
		
  	</head>
 
   	<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:-20px;">
		<div class="panel panel-primary"  id="mainPanel">
			
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font id="title" color="white" size="5">反馈数据列表详情</font>
				</div>
			</div>
			
			<div class="panel-body" style="text-align:center;">
				<!-- 1.1合同信息与支付信息不一致 -->
				<table id="fund_1" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>合同中业主信息：</span></td>
						<td style="width:25%;"><span id="f1_con_owner"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>支付收款人：</span></td>
						<td style="width:25%;"><span id="f1_receiver"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="f1_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span id="f1_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>支付内部员工名称：</span></td>
						<td colspan="3"><span id="f1_pay_staff"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f1_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="f1_reason"></span></td>
					
					</tr>
				
				</table>
				<!-- 1.2场租电费可能重复支付提示 -->
				<table id="fund_2" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f2_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f2_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="f2_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span id="f2_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同信息中业主名称：</span></td>
						<td><span id="f2_con_owner"></span></td>
						<td style="vertical-align:middle;"><span>收款人：</span></td>
						<td><span id="f2_receiver"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>电费支付收款人：</span></td>
						<td><span id="f2_el_receiver"></span></td>
						<td style="vertical-align:middle;"><span>电费支付内部员工名称：</span></td>
						<td><span id="f2_el_staff"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>租金支付收款人：</span></td>
						<td><span id="f2_re_receiver"></span></td>
						<td style="vertical-align:middle;"><span>租金支付内部员工名称：</span></td>
						<td><span id="f2_re_staff"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>支付周期：</span></td>
						<td colspan="3"><span id="f2_pay_cycle"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f2_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="f2_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 1.3销项选址费支付提示 -->
				<table id="fund_3" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f3_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f3_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>选址费：</span></td>
						<td><span id="f3_select_pay"></span></td>
						<td style="vertical-align:middle;"><span>销项确认时间：</span></td>
						<td><span id="f3_oc_time"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f3_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
					<td colspan="3"><span style="text-color:red" id="f3_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 1.4超两月未核销电费预付款 -->
				<table id="fund_4" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f4_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f4_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="f4_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span id="f4_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>预付金额：</span></td>
						<td><span id="f4_pre_number"></span></td>
						<td style="vertical-align:middle;"><span>预付电费余额：</span></td>
						<td><span id="f4_pre_balance"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f4_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
							<td colspan="3"><span style="text-color:red" id="f4_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 1.5租金支付分析 -->
				<table id="fund_5" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f5_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f5_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="f5_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span id="f5_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同开始时间：</span></td>
						<td><span id="f5_const_time"></span></td>
						<td style="vertical-align:middle;"><span>周期：</span></td>
						<td><span id="f5_cycle"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>实际支付时间：</span></td>
						<td><span id="f5_rp_time"></span></td>
						<td style="vertical-align:middle;"><span>支付金额：</span></td>
						<td><span id="f5_pay_amount"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>最早支付时间：</span></td>
						<td><span id="f5_pt_earliest"></span></td>
						<td style="vertical-align:middle;"><span>租金约定第一次支付时间：</span></td>
						<td><span id="f5_rpf_time"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f5_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
							<td colspan="3"><span style="text-color:red" id="f5_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 1.6连续三月停止计收站仍在支付场租站提示 -->
				<table id="fund_6" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f6_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f6_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>支付单号：</span></td>
						<td><span id="f6_pay_order"></span></td>
						<td style="vertical-align:middle;"><span>支付金额：</span></td>
						<td><span id="f6_pay_number"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f6_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
							<td colspan="3"><span style="text-color:red" id="f6_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 1.7预付场租费超期未核销 -->
				<table id="fund_7" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f7_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f7_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>创建时间：</span></td>
						<td><span id="f7_cre_time"></span></td>
						<td style="vertical-align:middle;"><span>核销时间：</span></td>
						<td><span id="f7_des_time"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f7_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
							<td colspan="3"><span style="text-color:red" id="f7_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 1.8一站多合同 -->
				<table id="fund_8" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="f8_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="f8_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="f8_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span if="f8_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>所属业主和名称：</span></td>
						<td colspan="3"><span id="f8_owner"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="f8_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="f8_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 2.1计收有关数据差异 -->
				<table id="income_1" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="i1_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="i1_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>项目编码：</span></td>
						<td><span id="i1_pro_number"></span></td>
						<td style="vertical-align:middle;"><span>项目名称：</span></td>
						<td><span if="i1_pro_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>CRM出账账期：</span></td>
						<td colspan="3"><span id="i1_CRM_pay_day"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="i1_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="i1_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 2.2外验三个月未起租 -->
				<table id="income_2" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="i2_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="i2_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="i2_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="i2_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 3.1工程项目线上线下一致性 -->
				<table id="project_1" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>项目编码：</span></td>
						<td style="width:25%;"><span id="p1_pro_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>项目名称：</span></td>
						<td style="width:25%;"><span if="p1_pro_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>项目类型：</span></td>
						<td><span id="p1_pro_type"></span></td>
						<td style="vertical-align:middle;"><span>项目状态：</span></td>
						<td><span id="p1_pro_state"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="p1_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
					<td colspan="3"><span style="text-color:red" id="p1_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 3.2新建站造价异常分析 -->
				<table id="project_2" style="display:none;width:80%;">
					<tr height="50px;">
						<td style="width:25%;vertical-align:middle;"><span>项目编码：</span></td>
						<td style="width:25%;"><span id="p2_pro_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>项目名称：</span></td>
						<td style="width:25%;"><span if="p2_pro_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>站点编码：</span></td>
						<td><span id="p2_sta_number"></span></td>
						<td style="vertical-align:middle;"><span>站点名称：</span></td>
						<td><span id="p2_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>建设方式：</span></td>
						<td><span id="p2_con_type"></span></td>
						<td style="vertical-align:middle;"><span>投资总计：</span></td>
						<td><span id="p2_invest_total"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="p2_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="p2_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 3.3转资金额较设计批复金额偏差分析 -->
				<table id="project_3" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>项目编码：</span></td>
						<td style="width:25%;"><span id="p3_pro_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>项目名称：</span></td>
						<td style="width:25%;"><span if="p3_pro_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>站点编码：</span></td>
						<td><span id="p3_sta_number"></span></td>
						<td style="vertical-align:middle;"><span>站点名称：</span></td>
						<td><span id="p3_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>设计批复金额：</span></td>
						<td><span id="p3_reply_number"></span></td>
						<td style="vertical-align:middle;"><span>转资金额：</span></td>
						<td><span id="p3_ct_number"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>内部验收结束时间：</span></td>
						<td><span id="p3_iaf_time"></span></td>
						<td style="vertical-align:middle;"><span>交付验收开始时间：</span></td>
						<td><span id="p3_das_time"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="p3_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="p3_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 3.4 实际建造方式与运营商订单确定建造方式不一致-->
				<table id="project_4" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>项目编码：</span></td>
						<td style="width:25%;"><span id="p4_pro_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>项目名称：</span></td>
						<td style="width:25%;"><span if="p4_pro_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>站点编码：</span></td>
						<td><span id="p4_sta_number"></span></td>
						<td style="vertical-align:middle;"><span>站点名称：</span></td>
						<td><span id="p4_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>需求单编码：</span></td>
						<td><span id="p4_db_number"></span></td>
						<td style="vertical-align:middle;"><span>需求运营商：</span></td>
						<td><span id="p4_de_operator"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>PMS建设方式：</span></td>
						<td><span id="p4_PMS_type"></span></td>
						<td style="vertical-align:middle;"><span>CRM建设方式：</span></td>
						<td><span id="p4_CRM_type"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="p4_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
						<td colspan="3"><span style="text-color:red" id="p4_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 4.1转供电电费畸高分析-->
				<table id="station_1" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="s1_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="s1_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>当前电费：</span></td>
						<td colspan="3"><span style="text-color:red" id="s1_cu_elec"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="s1_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
					<td colspan="3"><span style="text-color:red" id="s1_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 4.2每月抄表情况分析-->
				<table id="station_2" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="s2_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="s2_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="s2_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
					<td colspan="3"><span style="text-color:red" id="s2_reason"></span></td>
					</tr>
				
				</table>
				
				<!-- 5.1零场租合同分析-->
				<table id="colocation_1" style="display:none;width:80%;">
					<tr height="50px">
						<td style="width:25%;vertical-align:middle;"><span>站点编码：</span></td>
						<td style="width:25%;"><span id="c1_sta_number"></span></td>
						<td style="width:25%;vertical-align:middle;"><span>站点名称：</span></td>
						<td style="width:25%;"><span id="c1_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>物业编码：</span></td>
						<td><span id="c1_pro_number"></span></td>
						<td style="vertical-align:middle;"><span>零场租原因：</span></td>
						<td><span id="c1_zc_reason"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="c1_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
					<td colspan="3"><span style="text-color:red" id="c1_reason"></span></td>
					</tr>
					
				</table>
				
				<!-- 5.2租金成本分析-->
				<table id="colocation_2" style="display:none;width:80%;">
					<tr height="50px">
						<td style="vertical-align:middle;"><span>站点编码：</span></td>
						<td><span id="c2_sta_number"></span></td>
						<td style="vertical-align:middle;"><span>站点名称：</span></td>
						<td><span id="c2_sta_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>合同编码：</span></td>
						<td><span id="c2_con_number"></span></td>
						<td style="vertical-align:middle;"><span>合同名称：</span></td>
						<td><span id="c2_con_name"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>基本年租金：</span></td>
						<td colspan="3"><span style="text-color:red" id="c2_rent_year"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>问题描述：</span></td>
						<td colspan="3"><span style="text-color:red" id="c2_qu_des"></span></td>
					</tr>
					<tr height="50px">
						<td style="vertical-align:middle;"><span>原因说明：</span></td>
							<td colspan="3"><span style="text-color:red" id="c2_reason"></span></td>
					</tr>
					
				</table>
			</div>
		</div>
	</div>	
	</body>
</html>
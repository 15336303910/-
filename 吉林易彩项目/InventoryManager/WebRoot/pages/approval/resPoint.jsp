<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>点资源呈现</title>
     <script type="text/javascript">
		var context_path = '${pageContext.request.contextPath}';
  	</script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/css/bootstrap.min.css" /> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/css/bootstrap-datetimepicker.min.css" /> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jqthumb/jqthumb.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/js/bootstrap.min.js" /> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap/js/bootstrap-datetimepicker.min.js" /> 
	<script type="text/javascript">
		var id =  '<%=request.getParameter("id") %>';
		var type = '<%=request.getParameter("resType") %>';
		var grapType = '<%=request.getParameter("grapType") %>';
		var taskId = '<%=request.getParameter("taskId") %>';
		var resStr ='';
		var flag = false;
		var diag = new Dialog();
		$(function(){
			$.ajax({
				url:context_path+"/approvalAction!getResObj.action?",
	            type: "POST",
	            async:false,
	            data: {resId:id, resType:type},
	            success: function (data) {
					var dataObj=eval("("+data+")");
					resStr = dataObj.resStr;
					var imgStr = dataObj.imgStr;
					flag = dataObj.roll;
					var arr ;
					if(grapType == 'line'){
						if(type =='cable'){
							arr = ["资源名称","维护区县","计算长度","维护长度","A端设备",
							       "Z端设备","创建时间","修改时间","纤芯数","综资标识"];
						}else{
							arr = ["资源名称","系统名称","维护区县","计算长度","维护长度","A端设备",
							       "Z端设备","创建时间","修改时间","产权性质","产权单位","综资标识"];
						}
					}else{
						if(type == 'optical' || type == 'equtinfo'){
							arr = ["资源名称","经度","纬度","所属区县","光交箱面数","安装地址",
							       "安装容量","使用容量","设计容量","空闲容量",
							       "创建时间","修改时间","综资标识","备注"];
						}else if(type == 'OpticalTerminal'){
							arr = ["资源名称","经度","纬度","所属区县","所属设备",
							       "创建时间","修改时间","综资标识"];
						}else if(type =='station'){
							arr = ["资源名称","经度","纬度","所属区县","资源地址","站点类型","业务级别",
							       "创建时间","修改时间","综资标识"];
							
						}else if(type == 'generator'){
							arr = ["资源名称","经度","纬度","所属区县","资源地址","机房类型","业务级别","所在楼层",
							       "创建时间","修改时间","综资标识"];
						}else if(type == 'rack'){
							arr = ["资源名称","厂家","机架类型","机架行号","机架列号",
							       "创建时间","修改时间","综资标识"];
						}else if(type == 'odm'){
							arr = ["模块名称","模块编号","行数","列数",
							       "创建时间","修改时间","综资标识"];
							$("#sendRes").attr({ disabled: "disabled" });
						}else if(type == 'ne'){
							arr = ["设备名称","设备型号","设备厂家",
							       "创建时间","修改时间","综资标识"];
							$("#sendRes").attr({ disabled: "disabled" });
						}else if(type == 'neCard'){
							arr = ["板卡名称","软件版本","板卡型号","板卡类型",
							       "创建时间","修改时间","综资标识"];
							$("#sendRes").attr({ disabled: "disabled" });
						}else if(type == 'odmPoint'){
							arr = ["资源名称","列号","行号","上架纤芯","状态",
							       "创建时间","修改时间","综资标识"];
							$("#sendRes").attr({ disabled: "disabled" });
						}else if(type == 'nePoint'){
							arr = ["资源名称","对端","状态",
							       "创建时间","修改时间","综资标识"];
							$("#sendRes").attr({ disabled: "disabled" });
						}else{
							arr = ["资源名称","编号","经度","纬度","所属区县",
							       "创建时间","修改时间","产权性质","产权单位",
							       "一线维护人","备注","综资标识"];
						}
					}
					var resTable = $('#resTable'); 
					for(var i= 0,len= arr.length;i<len;i++){
						var tval = resStr[arr[i]];
						if(arr[i] =='产权性质'){
							tval = getNature(resStr[arr[i]]);
						}
						if(arr[i] =='产权单位'){
							tval = getPropComp(resStr[arr[i]]);
						}
						if(tval == null || tval == "null"){
							tval ="";
						}
						
						var row ="";
						if(arr[i] =='资源名称'){
							if(tval.length <18){
								row+="<tr><td>"+arr[i]+":<input type=\"text\" class=\"form-control\" id=\"res"+i+"\" placeholder=\"请输入\" style=\"height:15px\"  value=\""+tval+"\"></td></tr>";
							}else{
								row+="<tr><td>"+arr[i]+":<textarea class=\"form-control\" id=\"res"+i+"\" placeholder=\"请输入\" >"+tval+"</textarea>";
							}
						}else{
							row+="<tr><td>"+arr[i]+":<input type=\"text\" class=\"form-control\" id=\"res"+i+"\" placeholder=\"请输入\" style=\"height:15px\" readonly=\"readonly\" value=\""+tval+"\"></td></tr>";
						}
						
						resTable.append(row); 
					}
					
					
					var imgTable = $('#imgTable'); 
					var imgs ="<tr>";
					for(var i=0;i<imgStr.length;i++){
						imgs += "<td align=\"center\"><img id=\"resImg_"+i+"\" src=\""+imgStr[i].ImagePath+"\"/>"
							+"</td>";
					}
					imgs +="</tr>";
					imgTable.append(imgs);
					$("#imgTable img").jqthumb({
						width:80,
						height:80
					});
					$("#imgTable tr").click(function(e){
						diag.Modal = true;
						diag.Title = "图片";
						diag.Width = 500;
						diag.Height = 400;
						diag.URL = context_path+"/pages/approval/resImageInfo.jsp?id="+id+"&resType="+type;
						diag.show();
					})
	            },
	            error: function (data) {
					var mExpr = data.responseText;
					alert(mExpr);
	            }
	        });
			
			
		});
		window.onbeforeunload=onclose;
		//产权性质
		function getNature(num){
			var nature = "自建"
			if(num == '0'){
				nature ="自建";
			}else if(num =='1'){
				nature ="合建";
			}else if(num =='2'){
				nature ="共建";
			}else if(num =='3'){
				nature ="租用";
			}else if(num == '4'){
				nature ="购买";
			}else if(num == '5'){
				nature ="置换";
			}else {
				nature ="其他";
			}
			return nature;
		}
		//产权单位
		function getPropComp(num){
			var comp ="中国移动";
			if(num =='0'){
				comp ="中国移动";
			}else if(num == '1'){
				comp = "中国联通";
			}else if(num =='2'){
				comp ="中国电信";
			}else if(num =='3'){
				comp ="中国铁通";
			}else if(num =='4'){
				comp ="广电";
			}else if(num == '5'){
				comp ="电力";	
			}else if(num =='6'){
				comp = "业主";				
			}else if(num =='8'){
				comp ="北信基础";
			}else if(num =='9'){
				comp ="歌华";
			}else if(num =='10'){
				comp ="天童";
			}else if(num =='11'){
				comp ="亦庄博大";
			}else if(num =='12'){
				comp ="公联";
			}else if(num =='13'){
				comp ="路灯";
			}else if(num =='14'){
				comp ="污水";
			}else{
				comp = "其他";
			}
			return comp;
		}
		function onclose()
		{
			return "您确定退出吗？";
		}
		function iGetInnerText(testStr) {
	        var resultStr = testStr.replace(/\ +/g, "");
	        resultStr = resultStr.replace(/\s+/g, "");
	        resultStr = resultStr.replace(/[ ]/g, "");
	        resultStr = resultStr.replace(/<\/?.+?>/g,"")
	        resultStr = resultStr.replace(/[\r\n]/g, "");
	        return resultStr;
	    }
		function sendRes(){
			var tbLength = $("#resTable tr").length;
			var resName = document.getElementById("res0").value;
			resName = iGetInnerText(resName);
			if(flag == false){
				alert("无审批权限!");
				return ;
			}
			$.ajax({
				url:context_path+"/approvalAction!sendResObj.action?",
	            type: "POST",
	            data: {resId:id, type:type,resName:resName},
	            dataType: "json",
	            beforeSend: function(){  
	            	$("#sendRes").attr({ disabled: "disabled" });
	            	$("#rejectRes").attr({ disabled: "disabled" });
	            },
	            success: function (data) {
	            	var re = /^[0-9]+.?[0-9]*$/;
	            	if(re.test(data)){
	            		alert("数据同步完成!");
						$('#res'+(tbLength-1)).attr("value",data);
						parent.clickFun();
	            	}else{
	            		if(data == ""){
		            		alert("数据未更新!");
		            	}else if(data.indexOf("error") >=0){
		            		if(data.indexOf("_") >=0){
		            			alert(data.substring(6));
		            		}else{
		            			alert("数据同步失败，请联系管理员!");
		            		}
						}else{
							alert("数据同步完成!");
							$('#res'+(tbLength-1)).attr("value",data);
							parent.clickFun();
						}
	            	}
	            },
	            error: function (data) {
	            	var mExpr = data.responseText;
					if(mExpr=='success'){
						alert("数据同步成功!");
						parent.clickFun();
					}else{
						if(mExpr.indexOf("error_")>=0){
							alert(mExpr.substring(6));
						}else{
							alert("数据同步失败，请联系管理员!");
						}
					}
	            }
	        });
		}
		function rejectRes(){
			var resName = resStr['资源名称'];
			var parties = resStr['一线维护人'];
			var rejectStr = document.getElementById("rejectStr").value;
			if(flag == false){
				alert("无审批权限!");
				return ;
			}
			$.ajax({
				url:context_path+"/approvalAction!rejectRes.action?",
	            type: "POST",
	            data: {resId:id, resType:type,taskId:taskId,resName:resName,rejectStr:rejectStr,parties:parties},
	            beforeSend: function(){  
	            	$("#sendRes").attr({ disabled: "disabled" });
	            	$("#rejectRes").attr({ disabled: "disabled" });
	            },
	            success: function (data) {
	            	if(data =="success"){
	            		alert("标记成功");
	            		parent.rejectFun();
	            	}else{
	            		alert("标记失败!");
	            	}
	            },
	            error: function (data) {
	            	alert("标记成功");
	            }
	        });
		}
		//查看机房内信息
		function seeRes(){
			var resName = resStr['资源名称'];
			var url = context_path+"/pages/approval/site/stationInfo.jsp?resId="+id+"&resType=optical&resName="+resName+"&taskId="+taskId;
            var width = 900;
            var height = 500;
            var diag =new Dialog();
            diag.Width = width;
     	    diag.Height = height;
     	    diag.Modal = false;
     	    diag.Drag=true;
     	    diag.Title = "资源详情";
     	    diag.Top="0%";
     	    diag.Left="100%";
     	    diag.URL = url;
     	    diag.show();
		}
	</script>
  </head>
  
  <body>
  <div class="form-group">
	<table class="table-striped" width="100%" id="resTable">
	</table>
	<table class="table-striped" width="100%" id="endTable">
		<tr>
			<td align="left">
				处理意见:<textarea class="form-control" id="rejectStr" placeholder="处理意见" ></textarea>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" id="sendRes" class="btn btn-default" value="同步数据" onclick="sendRes()"/>
				&nbsp;&nbsp;
				<input type="button" id="rejectRes" class="btn btn-default" value="驳回" onclick="rejectRes()"/>
				&nbsp;&nbsp;
				<%
					if(request.getParameter("resType").equals("optical") || request.getParameter("resType").equals("equtinfo")){
						%>
						<input type="button" id="seeRes" class="btn btn-default" value="查看" onclick="seeRes()"/>
						<%
					}
				%>
				
			</td>
		</tr>
	</table>
	<table class="table-striped" width="100%" id="imgTable">
	</table>
  </div>
  </body>
</html>

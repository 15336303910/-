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
	<meta name="viewport" content="width=device-width, initial-scale=1">
   	<base href="<%=basePath%>">
   	<title>灰名单</title>
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts3.js"></script>
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript">
		function open_win() 
		{
			window.parent.parent.turnToJsp("灰名单数量","jsp/firstIndex/data/greyList/greyListDetail.jsp?id=ID_a")
		}
		function open_win_a() 
		{
			window.parent.parent.turnToJsp("到期灰名单数量","jsp/firstIndex/data/greyList/greyListDetail.jsp?id=ID_b")
		}
		function showTodoList(){
			window.parent.parent.turnToJsp("待办事项","jsp/firstIndex/data/greyList/showTodoList.jsp");
		}
		function showApply(){
			window.parent.parent.turnToJsp("灰名单发起申请","jsp/firstIndex/data/greyList/greyListApply.jsp");
		}
		function showRemove(){
			window.parent.parent.turnToJsp("灰名单发起解除","jsp/firstIndex/data/greyList/greyListRemove.jsp");
		}
		function showExpire(){
			window.parent.parent.turnToJsp("灰名单到期确认","jsp/firstIndex/data/greyList/greyListExpire.jsp");
		}
		$(document).ready(function(){
			var cities=[];//城市数组（实际用来盛放X轴坐标值）
			var counties=[];//区县数组（实际用来盛放X轴坐标值）
			/*显示灰名单总数、到期灰名单总数和到期占比*/
			$.ajax({
				url:"${pageContext.request.contextPath}/GreyListFirstPageAction/findGreyListNumPro.ilf", 
				async:true,
				type:"POST",
				dataType:"json",
				data:{},
				timeout:10000,
				success:function(data){
					if(data["success"]){
						var list=data["list"];
						var rolename=data["rolename"];
 						if("省级领导"==rolename){//根据返回的角色名称显示不同的页面
 							cities=['成都','雅安','凉山州','宜宾','泸州','乐山','资阳','阿坝','德阳','攀枝花','甘孜州','内江','广元','眉山','巴中','绵阳','自贡','广安','达州','南充','遂宁'];
  						}else if("地市领导"==rolename){
  							for(var i=0;i<data["list_counties"].length;i++){
  								counties.push(data["list_counties"][i]["COUNTY_NAME"]);
  							}
  							cities=counties;
  						}else if("灰名单市专业管理员（维护）"==rolename||"灰名单市专业管理员（市场）"==rolename||"灰名单市专业管理员（财务）"==rolename){
  							$("#work").css("display","");
  							for(var i=0;i<data["list_counties"].length;i++){
  								counties.push(data["list_counties"][i]["COUNTY_NAME"]);
  							}
  							cities=counties;
  						}
 						else if("灰名单省专业管理员（维护）"==rolename||"灰名单省专业管理员（市场）"==rolename||"灰名单省专业管理员（财务）"==rolename){
 							$("#examine").css("display","table-cell");
 							cities=['成都','雅安','凉山州','宜宾','泸州','乐山','资阳','阿坝','德阳','攀枝花','甘孜州','内江','广元','眉山','巴中','绵阳','自贡','广安','达州','南充','遂宁'];
  						}else if("灰名单市分管副总"==rolename){
  							$("#examine").css("display","table-cell");
  						}
						$("#greylist_num").html(list[0]["GREYLISTNUM"]);
						$("#expiry_num").html(list[0]["EXPIREGREYLISTNUM"]);
						$("#expiry_pro").html(list[0]["EXPIRYPRO"]);
					}
					/*图表*/
					
			        var nums=[];      //数量数组（实际用来盛放Y坐标值:灰名单数量）
			        var nums_expire=[];      //数量数组（实际用来盛放Y坐标值:到期灰名单数量）
			        var pro=[];      //占比
					/*为图提供数据*/
					$.ajax({
						url:"${pageContext.request.contextPath}/GreyListFirstPageAction/findGreyListNumProaaa.ilf", 
						async:true,
						type:"POST",
						dataType:"json",
						data:{},
						timeout:10000,
						success:function(data){
							if(data["success"]){
								var list=data["list"];
								var lista=data["lista"];
							    for(var i=0;i<list.length;i++){       
									 nums.push(list[i]["TOTALNUM"]);
				                     }
							    for(var j=0;j<lista.length;j++){       
									 nums_expire.push(lista[j]["EXPIRETOTALNUM"]);//挨个取出类别并填入数量数组
				                     }
							    for(var k=0;k<lista.length;k++){  
							    if(nums[k]==0){
							    	pro.push(0);
							    }else{
							    	pro.push((parseInt(nums_expire[k])/(parseInt(nums[k]))*10000)/100.00);//挨个取出类别并填入数量数组
							    }	
								
				                 }
								/*图1*/
								var myChart = echarts.init(document.getElementById('echarts_1'));
								myChart.setOption({ 
									title : {
										text : '四川铁塔灰名单数量',
										left : 'center'
									},
									color: ['#0066ff'],
									tooltip : {},
									legend : {
										data : ['灰名单数量']
									},
									xAxis : {
										data :cities
										},
									yAxis : {
										
									},
									series : [{
										name : '数量',
										type : 'bar',
										data : nums
									}]	
								});
								/*图2*/
								var myCharta = echarts.init(document.getElementById('echarts_2'));
								myCharta.setOption({ 
									title : {
										text : '四川铁塔灰名单到期数量',
										left : 'center'
									},
									color: ['#0066ff'],
									tooltip : {},
									legend : {
										data : ['灰名单到期数量']
									},
									xAxis : {
										
									},
									yAxis : {
										data :cities
									},
									series : [{
										name : '数量',
										type : 'bar',
										data : nums_expire
									}]	
								});
								/*图3*/
								var myChartb = echarts.init(document.getElementById('echarts_3'));
								myChartb.setOption({ 
									title : {
										text : '四川铁塔灰名单占比',
										left : 'center'
									},
									color: ['#0066ff'],
									tooltip : {},
									xAxis : {
										data: cities
									},
									yAxis : {
										
									},
									series : [{
										name : '数量',
										type: 'line',
										data : pro
									}]	
								});
								
							}
						}
						
					});
				}
			});
			
			
		});
	</script>
</head>
<body style="width:100%;height:100%;border:solid 0px red;" id="bodyHeight">
  	<div class="container" style="width:100%;height:100%;margin-top:0px;">
		<div class="panel panel-primary"  id="mainPanel">
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font color="white" size="5">数据中心--灰名单</font>
				</div>
				<div  id="examine" style="background-color:#337ab7;display:none;vertical-align:middle;padding-right:700px;text-align:right">
					<a style="text-decoration:none;cursor:pointer;" onclick="showTodoList()">
					<span class="glyphicon glyphicon-bullhorn" aria-hidden="true" style="color:red;font-Size:25px"></span>
					<font color="red" size="3" style="font-weight: bold;">待办任务</font>
					</a>
				</div>
			</div>
			<div class="panel-body">
				<div style="margin-top:20px;height:90px;margin-bottom:10px;">
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:85px;vertical-align:middle;">
						<div style="width:65%;height:85px;display:inline-block;background:#EEEE00;box-shadow:0 0 10px #EEEE00;border-radius:7px;cursor:pointer;">
							<div style="float:left;width:40%;height:50px;margin-top:17px;margin-left:-30px;">
							<span class="glyphicon glyphicon-search" aria-label="Left Align" aria-hidden="true" style="float:right;color:black;font-Size:35px"></span>
							</div>
							<div type=button  onclick="open_win()"  style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
								<div><font color="blue" size="3" style="font-weight: bold;">灰名单数量</font></div>
								<div style="margin-top:1px;"><font color="blue" size="2" id="greylist_num"><span id="qu_totalnum">-</span></font></div>
							</div>
						</div>
					</div>
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:85px;vertical-align:middle;">
						<div style="width:65%;height:85px;display:inline-block;background:#FF0000;box-shadow:0 0 10px #FF0000;border-radius:7px;cursor:pointer;">
							<div style="float:left;width:40%;height:50px;margin-top:17px;margin-left:-30px;">
							<span class="glyphicon glyphicon-time" aria-label="Left Align" aria-hidden="true" style="float:right;color:green;font-Size:35px"></span>
							</div>
							<div type=button value="Open_2" onclick="open_win_a()" style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
								<div><font color="white" size="3" style="font-weight: bold;">到期灰名单数量</font></div>
								<div style="margin-top:1px;"><font color="white" size="2" id=""><span id="expiry_num">-</span></font></div>
							</div>
						</div>
					</div>
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:85px;vertical-align:middle;">
						<div style="width:65%;height:85px;display:inline-block;background:#228B22;box-shadow:0 0 10px #228B22;border-radius:7px;cursor:pointer;">
							<div style="float:left;width:40%;height:50px;margin-top:17px;margin-left:-30px;">
							<span class="glyphicon glyphicon-stats" aria-label="Left Align" aria-hidden="true" style="float:right;color:red;font-Size:35px"></span>
							</div>
							<div style="float:left;width:50%;height:50px;margin-top:17px;margin-left:10px;">
								<div><font color="yellow" size="3" style="font-weight: bold;">灰名单到期占比</font></div>
								<div style="margin-top:1px;"><font color="yellow" size="2" id=""><span id="expiry_pro">-</span></font></div>
							</div>
						</div>
					</div>
				</div>
				<div id="work" style="display:none;margin-top:20px;height:45px;" >
				 	<div onclick="showApply()" class="col-xs-3 col-sm-3 col-md-3" style="display:table-cell;text-align:center;height:40px;vertical-align:middle;">
						<div style="width:50%;height:45px;display:inline-block;background:#FFC1C1;box-shadow:0 0 10px #FFC1C1;border-radius:7px;cursor:pointer;">
							<div style="margin-top:12px;">
								<font color="green" size="3" style="font-weight: bold;">灰名单发起申请</font>
							</div>
						</div>
					</div>
					<div onclick="showRemove()" class="col-xs-3 col-sm-3 col-md-3" style="display:table-cell;text-align:center;height:40px;vertical-align:middle;">
						<div style="width:50%;height:45px;display:inline-block;background:#FFC1C1;box-shadow:0 0 10px #FFC1C1;border-radius:7px;cursor:pointer;">
							<div style="margin-top:12px;">
								<font color="red" size="3" style="font-weight: bold;">解除灰名单申请</font>
							</div>
						</div>
					</div>
					<div onclick="showExpire()" class="col-xs-3 col-sm-3 col-md-3" style="display:table-cell;text-align:center;height:40px;vertical-align:middle;">
						<div style="width:50%;height:45px;display:inline-block;background:#FFC1C1;box-shadow:0 0 10px #FFC1C1;border-radius:7px;cursor:pointer;">
							<div style="margin-top:12px;">
								<font color="blue" size="3" style="font-weight: bold;">灰名单到期确认</font>
							</div>
						</div>
					</div>
					<div onclick="" class="col-xs-3 col-sm-3 col-md-3" style="display:table-cell;text-align:center;height:40px;vertical-align:middle;">
						<div style="width:50%;height:45px;display:inline-block;background:#FFC1C1;box-shadow:0 0 10px #FFC1C1;border-radius:7px;cursor:pointer;">
							<div style="margin-top:12px;">
								<font color="#8B8B7A" size="3" style="font-weight: bold;">可疑灰名单数据</font>
							</div>
						</div>
					</div>
				</div>
				<div style="width:100%;">
					<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="100%" color=#8F8F8F SIZE=10>
				</div>
				<div style="margin-top:0px;height:300px;">
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:100%;vertical-align:middle;">
						<div id="echarts_1" style="width:100%;height:100%;display:inline-block;cursor:pointer;">
						</div>
					</div>
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:100%;vertical-align:middle;">
						<div id="echarts_2" style="width:100%;height:100%;display:inline-block;cursor:pointer;">
							
						</div>
					</div>
					<div onclick="" class="col-xs-4 col-sm-4 col-md-4" style="display:table-cell;text-align:center;height:100%;vertical-align:middle;">
						<div id="echarts_3" style="width:100%;height:100%;display:inline-block;cursor:pointer;">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>
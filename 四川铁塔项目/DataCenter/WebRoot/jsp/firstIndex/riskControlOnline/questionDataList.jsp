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
    	<title>问题数据列表</title>
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
		<script type="text/javascript" src="https://cdn.datatables.net/select/1.2.7/js/dataTables.select.min.js"></script>
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.7/css/select.dataTables.min.css">
		
		<script type="text/javascript">
			var th_conditions = [];
			var oTable = null;
			var conditions = [];
			var column=[];
			var qu_id="";
			$(document).ready(function(){
				qu_id='<%=request.getParameter("quid")%>';
				var localCity='<%=request.getParameter("city")%>';//获取上一菜单的搜索条件
				localCity = decodeURI(localCity);
				var localDate='<%=request.getParameter("date")%>';//获取上一菜单的搜索条件
				
				//添加默认筛选条件				
				conditions.push({
					name:"CITY",
					value:localCity
				});
				
				conditions.push({
					name:"DATE",
					value:localDate
				});
				
				//时间选择框赋值
				$("#designedDate").val(localDate);
				/*开始获取地市下拉框数据*/
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
			                             if(optionValue==localCity){
			                            	 citySelectObj.options[i].selected = "selected";
			                            	 break;
			                             }
									}
								}	
							}
						}
					},
					error:function(){}
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/riskQuestionDataListAction/findRiskDataListFirst.ilf",
					async:true,
					type:"POST",
					dataType:"json",
					data:{"qu_id":qu_id},
					timeout:10000,
					success:function(data){
						if(data["success"]){
							$("#title").html(data["RISK_NAME"]+"数据列表");
							var th_html="";
							var list=data["th_info"];
							column.push({"mData":"ID","sWidth":Math.round(1/(list.length+3)*10000)/100.00+"%",
									"mRender": function ( data, type, full ) {
						        					return "<input type=\"checkbox\" name=\"\" value=\""+data+"\"/>"+data;}
									},
									{"mData":"CITY","sWidth":Math.round(1/(list.length+3)*10000)/100.00+"%"},
									{"mData":"MOUTH","sWidth":Math.round(1/(list.length+3)*10000)/100.00+"%"});
							for(var i=0;i<list.length;i++){
								th_conditions.push({
									name:list[i]["ID"],
									value:list[i]["QU_TYPE"]
								});
								th_html+="<th>"+list[i]["QU_TYPE"]+"</th>";
								column.push({
									"mData":"A"+list[i]["ID"],
									"sWidth":Math.round(1/(list.length+3)*10000)/100.00+"%",
									"mRender": function ( data, type, full ) {
								        return "<a href='javaScript:void(0)' onclick='showDetail(\""+full.CITY+"\",\""+full.MOUTH+"\",\""+data.split("~")[0]+"\")'>"+data.split("~")[1]+"</a>";
								  	}
								});
							}
							$("#first_th").append(th_html);
							
							//var obj=th_conditions[0];
							//alert(obj.name+":"+obj.value);
							/*获取具体表格中的数据*/
							oTable=$('#firstDataList').dataTable({
								"bSortClasses":false,
								"aLengthMenu":[10,20,30],
								"bAutoWidth":false,
								"bSort":true,
								"bProcessing":true,
								"bServerSide":true,
								"bFilter":false,
								"bLengthChange":true,
								"sPaginationType":"full_numbers",
								"bStateSave":false,
								"bScrollCollapse":true,
								"sScrollX":"100%",
								"aoColumns":column,
								/*
								[{
									"mData":"ID",
									"sWidth":"20%"
								},{
									"mData":"RISK_TYPE",
									"sWidth":"20%"
								},{
									"mData":"CITY",
									"sWidth":"20%"
								},{
									"mData":"MOUTH",
									"sWidth":"20%"
								},{
									"mData":"AMOUNT",
									"sWidth":"20%",
									 "mRender": function ( data, type, full ) {
									        return "<a href='javaScript:void(0)' onclick='showDetail(\""+full.RISK_TYPE+"\",\""+full.CITY+"\",\""+full.MOUTH+"\",\""+data+"\")'>"+data+"</a>";
									  }
								}],*/
								"aoColumnDefs":[] ,
								"iDisplayLength":10,
								"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
								"aaSorting":[[0,"desc"]],
								"fnRowCallback":function(nRow,aData,iDisplayIndex){
									//$("td:eq(0)",nRow).html("");
								},
								"fnServerData":function(sSource,aoData,fnCallback){
									$.ajax({
										url:sSource,
										type:"POST",
										data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions)+"&qu_id="+qu_id,
										dataType:"json",		
										success:function(data){
											fnCallback(data);
										}
									});
								},
								"sAjaxSource":"${pageContext.request.contextPath}/riskQuestionDataListAction/findRiskDataListTable.ilf"
							});
						}
					}
				});
				
				
			});
			/*按条件查询*/
			function searchData(){
				var date=$("#designedDate").val();
				if(date!=null && date!=""){
					conditions = [{
						name:"DATE",
						value:date
					}];	
				}else{
					conditions = [];
				}
				conditions.push({
					name:"CITY",
					value:$("#citySelect").val()
				});
				oTable.fnDraw();
			}
			function showDetail(city,mouth,id){
				city=encodeURI(city);
				url = encodeURI("jsp/firstIndex/riskControlOnline/questionDataListDetailsList.jsp?qu_type="+id+"&city="+city+"&mouth="+mouth);
				window.parent.turnToJsp("问题数据列表",url);
			}
			
			/*清除掉查询条件*/
			function searchClear(){
				//下拉框回归到第一个，地市只有一个数据，全省的第一个数据为'--请选择--'
				$("#citySelection option:first").prop("selected", 'selected');
				
				//日期条件直接清除
				$("#designedDate").val("");
			}
			
			/*导出报表*/
			function exportExcel(){
				var date=$("#designedDate").val();
				var city=$("#citySelect").val();
				window.location.href="${pageContext.request.contextPath}/riskQuestionDataListAction/exportExcel.ilf?"
						+"qu_id="+qu_id+"&date="+date+"&city="+encodeURI(encodeURI(city));//条件为qu_id以及区划和时间
			}
		</script>
		<style type="text/css">
			.badge{
				background-color:blue
			}
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
					<font id="title" color="white" size="5">数据列表</font>
				</div>
			</div>
			
			<div class="panel-body">
				<div style="width:100%;height:50px">
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>地市：</span>
						<div style="display:inline"  id="citySelection">
								<select style="width:180px;border:solid 1px #A3D0E3;" id="citySelect">
									<option value="-">-</option>
								</select>
						</div>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>月份：</span>
						<input id="designedDate" type="text" placeholder="请选择日期" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate"></input>
					</div>
				</div>
				<div style="width:100%;height:50px">
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<button class="btn btn-info" style="width:80px;" onclick="searchData()">查询</button>
						<button class="btn btn-warning" style="width:80px;" onclick="searchClear()">清除</button>
						<button class="btn btn-danger" style="width:80px;" onclick="exportExcel()">导出</button>
					</div>
				</div>
				
			 	<div id="amount" class="col-xs-12 col-sm-12 col-md-12"
					style="margin-top:15px;display:block;">
					<table id="firstDataList" class="table table-bordered table-hover"
						style="vertical-align: middle; text-align: center;">
						<thead>
							<tr id="first_th">
								<th>序号</th>
								<th>地市</th>
								<th>月份</th>
								
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	</body>
</html>
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
    	<title>灰名单发起申请</title>
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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extensions/layui/css/layui.css" media="all">
		<script type="text/javascript" src="${pageContext.request.contextPath}/extensions/layui/layui.js" charset="utf-8"></script>
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
			var table=null;
			var conditions = [];
			$(document).ready(function(){
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
					error:function(){}
				});
			});
			function selectFirst(){
				var value=$("#select01").val();
				$("#select02").html("");
				$("#select03").html("");
				if(value==1 || value==2){
					$.ajax({
						url:"${pageContext.request.contextPath}/greyListApplyAction/findSelectOptionsFirst.ilf",
						async:true,
						type:"POST",
						dataType:"JSON",
						data:{"value":value},
						timeout:10000,
						success:function(data){
							//alert(data["success"]);
							if(data["success"]){
								var list=data["list"];
								var options="";
								if(value==2){
									options+="<option value='0'>请选择风险点</option>";
									for(var i=0;i<list.length;i++){
										options+="<option value='"+list[i]['ID']+"'>"+list[i]['RISK_NAME']+"</option>";
									}
									$("#select02").html(options);
								}else if(value==1){
									options+="<option value='0'>请选择运营数据</option>";
									for(var j=0;j<list.length;j++){
										options+="<option value='"+list[j]['DATA']+"'>"+list[j]['DATA']+"</option>";
									}
									$("#select02").html(options);
								}
							}
						}
					});
				}
			}
			function selectSecond(){
				var value=$("#select02").val();
				$("#select03").html("");
				if(value!=0 && $("#select01").val()==2){
					$.ajax({
						url:"${pageContext.request.contextPath}/greyListApplyAction/findSelectOptionsSecond.ilf",
						async:true,
						type:"POST",
						dataType:"JSON",
						data:{"value":value,"type":"在线风控"},
						timeout:10000,
						success:function(data){
							if(data["success"]){
								var list=data["list"];
								var options="<option value='0'>请选择子风险点</option>";
								for(var i=0;i<list.length;i++){
									options+="<option value='"+list[i]['ID']+"'>"+list[i]['QU_TYPE']+"</option>";
								}
								$("#select03").html(options);
							}
						}
					});
				}else if(value!=0 && $("#select01").val()==1){
					var option="";
					option+="<option value='0'>- 请选择问题类型</option>";
					option+="<option value='IS_UNIFORM'>S标准表与C参考表数据不一致</option>";
					option+="<option value='IS_A_ONLY'>仅S标准表有数据</option>";
					option+="<option value='IS_Z_ONLY'>仅C参考表有数据</option>";
					option+="<option value='IS_A_FATAL'>S标准表数据异常</option>";
					option+="<option value='IS_Z_FATAL'>C参考表数据异常</option>";
					$("#select03").html(option);
				}
			}
			function showApplyList(){
				var select01=$("#select01").val();
				var select02=$("#select02").val();
				var select03=$("#select03").val();
				if(select01!=0 && select02!=0 && select03!=0){
					if(select01==2){
						if(table){
							table.fnClearTable();//清空数据
							table.fnDestroy();//销毁表格
						}
						$("#datatables_sc").css("display","none");
						$("#datatables_risk").css("display","");
						var obj01={
							"bSortClasses":false,
							"aLengthMenu":[10,20,30],
							"bAutoWidth":true,
							"bSort":true,
							"bProcessing":true,
							"bServerSide":true,
							"bFilter":false,
							"bLengthChange":true,
							"sPaginationType":"full_numbers",
							"bStateSave":false,
							"bScrollCollapse":true,
							"sScrollX":"100%",
							"aoColumns":[{
								"mData":"ID",
								"sWidth":"10%",
								"mRender": function ( data, type, full ) {
							        return "<input name='check' type='checkbox' value='"+full.ID+"'//>";
							  	}
							},{
								"mData":"CITY",
								"sWidth":"10%"
							},{
								"mData":"MOUTH",
								"sWidth":"10%"
							},{
								"mData":"NUMBER1",
								"sWidth":"35%"
							},{
								"mData":"NAME1",
								"sWidth":"35%"
							}],
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
									data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions)+"&select01="+select01+"&select02="+select02+"&select03="+select03,
									dataType:"json",		
									success:function(data){
										fnCallback(data);
									}
								});
							},
							"sAjaxSource":"${pageContext.request.contextPath}/greyListApplyAction/findTableData.ilf"
						};
						table=$("#datatables_risk").dataTable(obj01);
					}else if(select01==1){
						if(table){
							table.fnClearTable();//清空数据
							table.fnDestroy();//销毁表格
						}
						$("#datatables_risk").css("display","none");
						$("#datatables_sc").css("display","");
						var obj02={
								"bSortClasses":false,
								"aLengthMenu":[10,20,30],
								"bAutoWidth":true,
								"bSort":true,
								"bProcessing":true,
								"bServerSide":true,
								"bFilter":false,
								"bLengthChange":true,
								"sPaginationType":"full_numbers",
								"bStateSave":false,
								"bScrollCollapse":true,
								"sScrollX":"100%",
								"aoColumns":[{
									"mData":"ID",
									"sWidth":"10%",
									"mRender": function ( data, type, full ) {
								        return "<input name='check' type='checkbox' value='"+full.ID+"'//>";
								  	}
								},{
									"mData":"DATA_CITY",
									"sWidth":"10%"
								},{
									"mData":"RESOURCE_NAME",
									"sWidth":"20%"
								},{
									"mData":"PRIMARY_VALUE",
									"sWidth":"20%"
								},{
									"mData":"PROBLEM_DESC",
									"sWidth":"40%"
								}],
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
										data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions)+"&select01="+select01+"&select02="+select02+"&select03="+select03,
										dataType:"json",		
										success:function(data){
											fnCallback(data);
										}
									});
								},
								"sAjaxSource":"${pageContext.request.contextPath}/greyListApplyAction/findTableData.ilf"
							};
						table=$("#datatables_sc").dataTable(obj02);
					}
				}else{
					alert("请选择正确的选项！");
				}
			}
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
				table.fnDraw();
			}
			function checkApply(){
				var param01=$("#select01").val();
				var param02=$("#select02").val();
				var param03=$("#select03").val();
				var id=$("input[name='check']:checked").val();
				var type="check";
				var url01="";
				if(param01!=0 && param02!=0 &&param03!=0 && id!=0){
					param02=encodeURI(param02);
					url01="jsp/firstIndex/data/greyList/greyListApplyEdit.jsp?param01="+param01+"&param02="+param02+"&param03="+param03+"&ID="+id+"&type="+type;
					var url=encodeURI(url01);
				}
				window.parent.parent.turnToJsp("灰名单申请填写",url);
				//alert($("input[name='check']:checked").val());
			}
			function writeApply(){
				var param01=0;
				var param02=0;
				var param03=0;
				var id=0;
				var type="write";
				var url01="jsp/firstIndex/data/greyList/greyListApplyEdit.jsp?param01="+param01+"&param02="+param02+"&param03="+param03+"&ID="+id+"&type="+type;
				var url=encodeURI(url01);
				window.parent.parent.turnToJsp("灰名单申请填写",url);
			}
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
  	<div class="container" style="width:100%;height:100%;margin-top:0px;">
		<div class="panel panel-primary"  id="mainPanel">
			<div id="panelHeading" style="width:100%;height:50px;background-color:#337ab7;display:table;padding-left:10px">
				<div style="background-color:#337ab7;display:table-cell;vertical-align:middle;padding-left:10px">
					<font color="white" size="5">灰名单发起申请</font>
				</div>
			</div>
			
			<div class="panel-body">
				<div style="width:100%;height:50px;">
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>地市：</span>
						<div style="display:inline"  id="citySelection">
								<select style="width:180px;border:solid 1px #A3D0E3;" id="citySelect">
									<option value="-">-</option>
								</select>
						</div>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<span>日期：</span>
						<input id="designedDate" type="text" placeholder="请选择日期" style="cursor:pointer;border:solid 1px #A3D0E3;width:190px;height:29px;font-size:12px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate"></input>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4" style="text-align:center;">
						<a class="btn btn-success" style="cursor:pointer;margin-bottom:10px;" onclick="javascript:searchData();">
								<span class="icon-search icon-white"></span>查询
						</a>
					</div>
				</div>
				<div style="width:100%;">
					<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="100%" color=#8F8F8F SIZE=10>
				</div>
			 	<div class="col-xs-12 col-sm-12 col-md-12"
					style="margin-top:0px">
					<div class="cols-xs-9 col-sm-9 col-md-9" style="height:40px;">
						<select id="select01" style="height:30px;width:150px;" onchange="selectFirst()">
							<option value="0">选择数据来源</option>
							<option value="1">SC表</option>
							<option value="2">在线风控</option>
						</select>
						<select id="select02" style="height:30px;width:200px;margin-left:5px;" onchange="selectSecond()">
							
						</select>
						<select id="select03" style="height:30px;width:200px;margin-left:5px;">
							
						</select>
						<button onclick="showApplyList()" type="button" class="btn btn-info btn-sm" style="width:120px;height:35px;margin-left:20px;"><span id="">生成列表</span></button>
					</div>
					<div class="cols-xs-3 col-sm-3 col-md-3" style="height:40px;">
						<button onclick="checkApply()" type="button" class="btn btn-success btn-sm" style="width:120px;height:35px;margin-right:20px;"><span id="">灰名单发起申请</span></button>
						<button onclick="writeApply()" type="button" class="btn btn-warning btn-sm" style="width:120px;height:35px;margin-right:20px;"><span id="">灰名单手动填写</span></button>
					</div>
					<table id="datatables_risk" class="table table-bordered table-hover display"
						style='display:none;vertical-align: middle; text-align: center;margin-top:10px;'>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr id="th">
								<th>选择</th>
								<th>地市</th>
								<th>月份</th>
								<th>资源编码</th>
								<th>资源名称</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
					<table id="datatables_sc" class="table table-bordered table-hover display"
						style='display:none;vertical-align: middle; text-align: center;margin-top:10px;'>
						<div style="width:100%;height:6px"></div>
						<thead>
							<tr id="th">
								<th>选择</th>
								<th>地市</th>
								<th>资源名称</th>
								<th>资源编号</th>
								<th>问题描述</th>
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
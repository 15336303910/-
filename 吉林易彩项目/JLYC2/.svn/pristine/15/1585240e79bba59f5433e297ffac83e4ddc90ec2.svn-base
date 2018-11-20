/**
 * 初始化动环配套数据
 */
function ringInit(int_id){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaSupportingRing!getSupportingRing.interface";
	if(typeof(int_id)!="undefined"){ 
		mui.ajax(url, {
			type: 'post',
			dataType: 'json',
			timeout: 10000 * 60,
			data: {
				jsonRequest: "{'int_id':'"+int_id+"'}",
				UID: UID,
				longiner: logingUser,
				areaName: areaName
			},
			success: function(response) {
				var result = JSON.stringify(response);
				var data = JSON.parse(result);
				var result = data.result;
				if(result == '0') {
					infos = JSON.parse(data.info);
					var info = infos[0]; 
					$("#city_id").val(info.city_id);
					changeCity(document.getElementById("city_id"));
					$("#county_id").val(info.county_id);
					$("#res_id").val(info.res_id);
					$("#zh_label").val(info.zh_label);
					$("#related_equiproom").val(info.related_equiproom);
					$("#equipment_model").val(info.equipment_model);
					$("#equipment_type").val(info.equipment_type);
					$("#vendor").val(info.vendor);
					$("#longitude").val(info.longitude);
					$("#latitude").val(info.latitude);
					getMaintainCompanyData(info.maintain_company);
					getMaintainAreaData(info.maintain_area);
					$("#maintain_manager").val(info.maintain_manager);
					$("#maintain_manager_phone").val(info.maintain_manager_phone);
					$("#remark").val(info.remark);
				} else {
					mui.alert("获取数据失败！");
				}
			},
			error: function(xhr, type, errorThrown) {
				plus.nativeUI.closeWaiting();
				mCurrentWebView.endPullToRefresh();
			}
	
		});
	}
}

function updateRing(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaSupportingRing!updateSupportingRing.interface";
	var jsonStr = "{";
	var city_id = $("#city_id").val();
	if(city_id != null && city_id !=''){
		jsonStr +="'city_id':"+city_id+",";
	}
	var county_id = $("#county_id").val();
	if(county_id != null && county_id !=''){
		jsonStr +="'county_id':"+county_id+",";
	}
	jsonStr = jsonStr + "'res_id':'"+$("#res_id").val()+"',"
		+"'related_equiproom':'"+$("#related_equiproom").val()+"',"
		+"'int_id':'"+$("#int_id").val()+"',"
	        
		+"'zh_label':'"+$("#zh_label").val()+"',"
		+"'equipment_type':'"+$("#equipment_type").val()+"',"
		+"'equipment_model':'"+$("#equipment_model").val()+"',"
		+"'vendor':'"+$("#vendor").val()+"',"
		+"'longitude':'"+$("#longitude").val()+"',"
		+"'latitude':'"+$("#latitude").val()+"',"
		+"'maintain_company':'"+$("#maintain_company").val()+"',"
		+"'maintain_area':'"+$("#maintain_area").val()+"',"
		+"'maintain_manager':'"+$("#maintain_manager").val()+"',"
		+"'maintain_manager_phone':'"+$("#maintain_manager_phone").val()+"',"			
		+"'remark':'"+$("#remark").val()+"'"
		+"}"
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:jsonStr,
			UID:UID,
			longiner:logingUser,
			areaName:areaName
		},
		success: function(response) {
			plus.nativeUI.closeWaiting();
			var result = JSON.stringify(response);
			var data = JSON.parse(result);
			var result = data.result;
			if(result =='0'){
				var btnArray = ['继续', '返回'];
				mui.confirm('修改成功，是否返回动环配套列表?', '成功', btnArray, function(e) {
					if (e.index == 1) {
						mui.openWindow("supportingRingList.html");
					}
				});
				
			}else{
				mui.alert("获取数据失败！");
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
		}

	});
}
/**
 * 得到经纬度数据
 */
function getLonLat(){
	mui.openWindow({
		url: "../../main/getLatLon.html",
		id: "getLonLat",
		extras: {
			resType:"ring",
			operate:"detail"
		}
	});
}

//得到传递参数
function getQueryStr(str){
	var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp; 
	if (tmp = rs) { 
		return tmp[2]; 
	} 
	return ""; 
}

function setLonLat(detail){
	$("#longitude").val(detail.lon);
	$("#latitude").val(detail.lat);
}

function deleteRing(){
	var btnArray = ['确定', '取消'];
	mui.confirm('确定删除吗?', '提示信息', btnArray, function(e) {
		if (e.index == 0) {
			var UID = plus.storage.getItem("uid");//获取缓存记录
			var logingUser = plus.storage.getItem("logingUser");//当前登录用户
			var areaName = plus.storage.getItem("areaName");//得到权限控制地市
			var url = plus.storage.getItem("url")+"/pdaSupportingRing!deleteSupportingRing.interface";
			mui.ajax(url, {
				type: 'post',
				dataType: 'json',
				timeout: 10000 * 60,
				data: {
					jsonRequest:"{'int_id':'"+$("#int_id").val()+"'}",
					UID:UID,
					longiner:logingUser,
					areaName:areaName
				},
				success: function(response) {
					plus.nativeUI.closeWaiting();
					var result = JSON.stringify(response);
					var data = JSON.parse(result);
					var result = data.result;
					if(result =='0'){
						mui.alert("删除成功");
						mui.openWindow("supportingRingList.html");
					}else{
						mui.alert("获取数据失败！");
					}
				},
				error: function(xhr, type, errorThrown) {
					plus.nativeUI.closeWaiting();
				}
		
			});
		}
	});
	
}

function updateDeleteFlag(){
	var btnArray = ['确定', '取消'];
	mui.confirm('确定删除吗?', '提示信息', btnArray, function(e) {
		if (e.index == 0) {
			var UID = plus.storage.getItem("uid");//获取缓存记录
			var logingUser = plus.storage.getItem("logingUser");//当前登录用户
			var areaName = plus.storage.getItem("areaName");//得到权限控制地市
			var url = plus.storage.getItem("url")+"/pdaSupportingRing!updateDeleteFlag.interface";
			mui.ajax(url, {
				type: 'post',
				dataType: 'json',
				timeout: 10000 * 60,
				data: {
					jsonRequest:"{'int_id':'"+$("#int_id").val()+"'}",
					UID:UID,
					longiner:logingUser,
					areaName:areaName
				},
				success: function(response) {
					plus.nativeUI.closeWaiting();
					var result = JSON.stringify(response);
					var data = JSON.parse(result);
					var result = data.result;
					if(result =='0'){
						mui.alert("删除成功");
						mui.openWindow("supportingRingList.html");
					}else{
						mui.alert("获取数据失败！");
					}
				},
				error: function(xhr, type, errorThrown) {
					plus.nativeUI.closeWaiting();
				}
		
			});
		}
	});
	
}
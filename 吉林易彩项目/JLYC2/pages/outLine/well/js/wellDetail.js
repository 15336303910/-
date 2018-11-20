/**
 * 初始化数据
 * @param {Object} wellId
 */
function initWell(int_id){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaPipeCustom!getWellCustom.interface";
	if(typeof(int_id)!="undefined"){ 
		mui.ajax(url, {
			type: 'post',
			async: true,
			dataType: 'json',
			timeout: 10000 * 60,
			data: {
				jsonRequest: "{'int_id':'"+int_id+"'}",
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
					var infos = JSON.parse(data.info);
					var info = infos[0];
					$("#zh_label").val(info.zh_label);
					$("#city_id").val(info.city_id);
					changeCity(document.getElementById("city_id"));
					$("#county_id").val(info.county_id);
					$("#well_kind").val(info.well_kind);
					$("#well_type").val(info.well_type);
					$("#ownership").val(info.ownership);
					$("#transfer_level").val(info.transfer_level);
					$("#purpose").val(info.purpose);
					$("#longitude").val(info.longitude);
					$("#latitude").val(info.latitude);
					getMaintainCompanyData(info.maintain_company);
					getMaintainAreaData(info.maintain_area);
					$("#maintain_manager").val(info.maintain_manager);
					$("#maintain_manager_phone").val(info.maintain_manager_phone);
					$("#remark").val(info.remark);
				}
			},
			error: function(xhr, type, errorThrown) {
				plus.nativeUI.closeWaiting();
			}
	
		});
	}
}

/**
 * 提交修改
 */
function updateWell(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaPipeCustom!updateWellCustom.interface";
	var jsonStr = "{'int_id':'"+$("#int_id").val()+"',"
		+"'zh_label':'"+$("#zh_label").val()+"',"
		+"'well_kind':'"+$("#well_kind").val()+"',"
		+"'well_type':'"+$("#well_type").val()+"',"
		+"'ownership':'"+$("#ownership").val()+"',"
		+"'transfer_level':'"+$("#transfer_level").val()+"',"
		+"'purpose':'"+$("#purpose").val()+"',"
		+"'longitude':'"+$("#longitude").val()+"',"
		+"'latitude':'"+$("#latitude").val()+"',"
		+"'maintain_company':'"+$("#maintain_company").val()+"',"
		+"'maintain_area':'"+$("#maintain_area").val()+"',"
		+"'maintain_manager':"+$("#maintain_manager").val()+","
		+"'maintain_manager_phone':'"+$("#maintain_manager_phone").val()+"',"
		+"'remark':'"+$("#remark").val()+"',"
		
	var city_id = $("#city_id").val();
	if(city_id != null && city_id !='' && county_id != "null"){
		jsonStr +="'city_id':"+city_id+",";
	}
	var county_id = $("#county_id").val();
	if(county_id != null && county_id !='' && county_id != "null"){
		jsonStr +="'county_id':"+county_id+"";
	}
	if(jsonStr.charAt(jsonStr.length-1) ==','){
		jsonStr = jsonStr.substr(0,jsonStr.length-1);
	}
	jsonStr +="}";
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
				mui.confirm('管井修改成功，是否返回管井列表?', '成功', btnArray, function(e) {
					if (e.index == 1) {
						var infos = JSON.parse(data.info);
						mui.openWindow("wellList.html");
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

function geocoder_CallBack2(data){
	var address;
	address = data.regeocode.formattedAddress;
	//document.getElementById("region").value=address
}

/**
 * 得到经纬度数据
 */
function getLonLat(){
	mui.openWindow({
		url: "../../main/getLatLon.html",
		id: "getLonLat",
		extras: {
			resType:"well",
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

function deleteWell(){
	var btnArray = ['确定', '取消'];
	mui.confirm('确定删除吗?', '提示信息', btnArray, function(e) {
		if (e.index == 0) {
			var UID = plus.storage.getItem("uid");//获取缓存记录
			var logingUser = plus.storage.getItem("logingUser");//当前登录用户
			var areaName = plus.storage.getItem("areaName");//得到权限控制地市
			var url = plus.storage.getItem("url")+"/pdaPipeCustom!deleteWellCustom.interface";
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
						mui.openWindow("wellList.html");
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

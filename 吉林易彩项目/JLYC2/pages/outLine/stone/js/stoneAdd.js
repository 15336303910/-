/**
 * 得到经纬度数据
 */
function getLonLat(){
	mui.openWindow({
		url: "../../main/getLatLon.html",
		id: "getLonLat",
		extras: {
			resType:"stone",
			operate:"add"
		}
	});
}


/**
 * 提交新增标石
 */
function insertStone(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaStoneCustom!insertStoneCustom.interface";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:"{'zh_label':'"+$("#zh_label").val()+"',"
						+"'city_id':'"+$("#city_id").val()+"',"
						+"'county_id':'"+$("#county_id").val()+"',"
						+"'longitude':'"+$("#longitude").val()+"',"
						+"'latitude':'"+$("#latitude").val()+"',"
						+"'ownership':'"+$("#ownership").val()+"',"
						+"'maintain_company':'"+$("#maintain_company").val()+"',"
						+"'maintain_area':'"+$("#maintain_area").val()+"',"
						+"'maintain_manager':'"+$("#maintain_manager").val()+"',"
						+"'maintain_manager_phone':'"+$("#maintain_manager_phone").val()+"',"
						+"'remark':'"+$("#remark").val()+"'"
						+"}",
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
				mui.confirm('标石增加成功，是否返回标石列表?','提示信息', btnArray, function(e) {
					if (e.index == 1) {
						var infos = JSON.parse(data.info);
						mui.openWindow("stoneList.html");
					}
				});
				
			}else{
				mui.alert(data.info);
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
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
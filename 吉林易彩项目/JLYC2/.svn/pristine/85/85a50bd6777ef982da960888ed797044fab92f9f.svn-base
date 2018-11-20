/**
 * 初始化相关基础数据
 */
function initPipe(){
	var cv = plus.webview.currentWebview();
	pipeId = cv.pipeId;
	$("#id").val(cv.pipeId);
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLineSystem!getLineSystemObj.interface";
	mui.ajax(url, {
		type: 'post',
		async: true,
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: "{'id':"+pipeId+"}",
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
				var info = JSON.parse(data.info);
				$("#lineName").val(info.lineName);
				$("#resNum").val(info.resNum);
				$("#lineType").val(info.lineType);
				$("#direction").val(info.direction);
				$("#lineArea").val(info.lineArea);
				$("#propertyNature").val(info.propertyNature);
				$("#propertyComp").val(info.propertyComp);
				$("#countLength").val(info.countLength);
				$("#maintainLength").val(info.maintainLength);
				$("#remark").val(info.remark);
			}else{
				mui.alert("查无数据","提醒");
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
		}

	});
}

//根据当前经纬度获取地址信息
function getAddByLonLat(lon,lat) {
	var lnglatXY = new AMap.LngLat(lon,lat);
	mapObj.plugin(["AMap.Geocoder"], function() {       
		MGeocoder = new AMap.Geocoder({
			radius: 1000,
			extensions: "all"
		});
	AMap.event.addListener(MGeocoder, "complete", geocoder_CallBack2);
	MGeocoder.getAddress(lnglatXY);
	});
}
function geocoder_CallBack2(data){
	var address;
	address = data.regeocode.formattedAddress;
	//document.getElementById("region").value=address
}

/**
 * 得到所属的电杆
 */
function getWell(){
	mui.openWindow({
		url:"../well/wellList.html",
		id: "wellList",
		extras: {
			pipeId:pipeId
		}
	});
}

/**
 * 打开管道段列表
 */
function getPipeSeg(){
	mui.openWindow({
		url:"../pipeSeg/pipeSegList.html",
		id: "poleLineSegList",
		extras: {
			pipeId:pipeId
		}
	});
}

/**
 * 得到管线系统名称
 */
function getLineName(){
	
}

/**
 * 修改管道系统
 */
function submitPipe(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLineSystem!insertLineSystem.interface";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:"{'lineName':'"+$("#lineName").val()+"',"
						+"'lineType':'"+$("#lineType").val()+"',"
						+"'direction':'"+$("#direction").val()+"',"
						+"'lineArea':'"+$("#lineArea").val()+"',"
						+"'propertyNature':'"+$("#propertyNature").val()+"',"
						+"'propertyComp':'"+$("#propertyComp").val()+"',"
						+"'countLength':'"+$("#countLength").val()+"',"
						+"'maintainLength':'"+$("#maintainLength").val()+"',"
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
				
				
			}else{
				mui.alert("获取数据失败！");
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
	$("#poleLongitude").val(detail.lon);
	$("#poleLatitude").val(detail.lat);
}
/**
			 * 初始化相关基础数据
			 */
function initPoleLine(){
	var cv = plus.webview.currentWebview();
	$("#lineName").val(cv.lineName);
	poleLineId = cv.poleLineId;
	if(typeof(poleLineId)!="undefined"){ 
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
				jsonRequest: "{'id':"+poleLineId+"}",
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
					$("#id").val(info.poleLineId);
					$("#lineName").val(info.lineName);
					$("#lineType").val(info.lineType);
					$("#direction").val(info.direction);
					$("#lineArea").val(info.lineArea);
					$("#propertyNature").val(info.propertyNature);
					$("#propertyComp").val(info.propertyComp);
					$("#propertyNature").val(info.propertyNature);
					$("#countLength").val(info.countLength);
					$("#maintainLength").val(info.maintainLength);
					$("#remark").val(info.remark);
					$("#resNum").val(info.resNum);
				}else{
					mui.alert("查无数据","提醒");
				}
			},
			error: function(xhr, type, errorThrown) {
				plus.nativeUI.closeWaiting();
			}
		});
	}
	
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
function getPole(){
	mui.openWindow({
		url:"../pole/poleList.html",
		id: "poleList",
		extras: {
			poleLineId:poleLineId
		}
	});
}

/**
 * 打开杆路段列表
 */
function getPoleLineSeg(){
	mui.openWindow({
		url:"../poleLineSeg/poleLineSegList.html",
		id: "poleLineSegList",
		extras: {
			poleLineId:poleLineId
		}
	});
}

/**
 * 得到管线系统名称
 */
function getLineName(){
	
}

/**
 * 提交新建杆路
 */
function submitPoleLine(){
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
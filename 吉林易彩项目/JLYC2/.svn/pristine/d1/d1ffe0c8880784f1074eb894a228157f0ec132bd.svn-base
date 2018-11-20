/**
 * 根据直埋段id得到相应的直埋信息
 * @param {Object} segId
 */
function initBasestationSeg(segId){
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var url = plus.storage.getItem("url") + "/pdaBasestationPart!getBasestationPartObj.interface";
	if(typeof(segId)!="undefined"){ 
		mui.ajax(url, {
			type: 'post',
			async: true,
			dataType: 'json',
			timeout: 10000 * 60,
			data: {
				jsonRequest: "{'id':'" + segId + "'}",
				UID: UID,
				longiner: logingUser,
				areaName: areaName
			},
			success: function(response) {
				plus.nativeUI.closeWaiting();
				var result = JSON.stringify(response);
				var data = JSON.parse(result);
				var result = data.result;
				if(result == '0') {
					var info = JSON.parse(data.info);
					$("#basestationPartName").val(info.basestationPartName);
					$("#basestationId").val(info.basestationId);
					$("#basestationPartStart").val(info.basestationPartStart);
					$("#basestationPartStartId").val(info.basestationPartStartId);
					$("#basestationPartEnd").val(info.basestationPartEnd);
					$("#basestationPartEndId").val(info.basestationPartEndId);
					$("#basestationPartArea").val(info.basestationPartArea);
					$("#basestationPartLength").val(info.basestationPartLength);
					$("#maintainLength").val(info.maintainLength);
					$("#propertyRight").val(info.propertyRight);
					$("#propertyDept").val(info.propertyDept);
				} else {
					mui.alert("查无数据", "提醒");
				}
			},
			error: function(xhr, type, errorThrown) {
				plus.nativeUI.closeWaiting();
			}
	
		});
	}
}


function getRad(d){
	return d*PI/180.0;
}
//得到传递参数
function getQueryStr(str){
	var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp; 
	if (tmp = rs) { 
		return tmp[2]; 
	} 
	return ""; 
}
/**
* approx distance between two points on earth ellipsoid
* @param {Object} lat1
* @param {Object} lng1
* @param {Object} lat2
* @param {Object} lng2
*/
function getFlatternDistance(lat1,lng1,lat2,lng2){
	var f = getRad((lat1*1 + lat2*1)/2);
	var g = getRad((lat1*1 - lat2*1)/2);
	var l = getRad((lng1*1 - lng2*1)/2);
	var sg = Math.sin(g);
	var sl = Math.sin(l);
	var sf = Math.sin(f);

	var s,c,w,r,d,h1,h2;
	var a = EARTH_RADIUS;
	var fl = 1/298.257;

	sg = sg*sg;
	sl = sl*sl;
	sf = sf*sf;

	s = sg*(1-sl) + (1-sf)*sl;
	c = (1-sg)*(1-sl) + sf*sl;

	w = Math.atan(Math.sqrt(s/c));
	r = Math.sqrt(s*c)/w;
	d = 2*w*a;
	h1 = (3*r -1)/2/c;
	h2 = (3*r +1)/2/s;
	return d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
}
function toDecimal(x) { 
	var f = parseFloat(x); 
	if (isNaN(f)) { 
		return; 
	} 
	f = Math.round(x*100)/100; 
	return f; 
} 

/**
 * 选择标石
 * @param {Object} type
 */
function checkStone(type){
	mui.openWindow({
		url: "../stone/stoneCheck.html",
		id: "stoneCheck",
		extras: {
			resType:"segDetail",
			operate:type
		}
	});
}
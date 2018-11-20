/**
 * 初始化直埋信息
 */
function initBuried(){
	var cv = plus.webview.currentWebview();
	buriedId = cv.buriedId;
	$("#id").val(cv.buriedId);
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLineSystem!getLineSystemObj.interface";
	if(typeof(buriedId)!="undefined"){ 
		mui.ajax(url, {
			type: 'post',
			async: true,
			dataType: 'json',
			timeout: 10000 * 60,
			data: {
				jsonRequest: "{'id':"+buriedId+"}",
				UID:UID,
				longiner:logingUser,
				areaName:areaName
			},
			success: function(response) {
				var result = JSON.stringify(response);
				var data = JSON.parse(result);
				var result = data.result;
				if(result =='0'){
					console.log(data.info);
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
}

/**
 * 跳转到标石页面
 */
function getStone(){
	mui.openWindow({
		url:"../stone/stoneList.html",
		id: "stoneList",
		extras: {
			buriedId:buriedId
		}
	});
}
/**
 * 得到直埋下的直埋段信息
 */
function getBuriedSeg(){
	mui.openWindow({
		url:"../buriedSeg/buriedSegList.html",
		id: "buriedSegList",
		extras: {
			buriedId:buriedId
		}
	});
}

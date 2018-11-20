//根据
function getPoleList(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaPolelineCustom!getPoleCustom.interface";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: "{}",
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
				for(var i=0;i<infos.length;i++){
					var info = infos[i];
					var resStr = getNewline(info.zh_label);
			        
			        $("#poleUl").append("<div class=\"con borderbottom\" onclick=\"checkPole('"+info.int_id+"','"+info.zh_label+"','"+info.longitude+"','"+info.latitude+"')\";>" +
						"<h1 class=\"con-head\">" +resStr + "</h1>" +
						"<div class=\"pr10\">" +
						"<ul class=\"mui-table-view adrlist\" >" +
						"<li class=\"mui-table-view-cell mapicon\" >创建人:" +
						info.creator +
						"</li></ul></div></div>");
				}
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
 * 选中电杆数据
 * @param {Object} poleId
 * @param {Object} poleName
 */
function checkPole(int_id,zh_label,lon,lat){
	var poleLineSeg = plus.webview.currentWebview().opener();//获取打开的页面信息
	if(resType == 'segAdd'){
		mui.fire(poleLineSeg, "poleLineSegAdd", {int_id:int_id,zh_label:zh_label,operate:operate,lon:lon,lat:lat});
	}
	if(resType == 'segDetail'){
		mui.fire(poleLineSeg, "poleLineSegDetail", {int_id:int_id,zh_label:zh_label,operate:operate,lon:lon,lat:lat});
	}
	mui.back();
}

/**
 * 
 * @param {Object} val
 */
function getNewline(val) {  
	var str = new String(val);  
	var bytesCount = 0;  
	var s="";
	for (var i = 0 ,n = str.length; i < n; i++) {  
		var c = str.charCodeAt(i);  
		//统计字符串的字符长度
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			bytesCount += 1;  
		} else {  
			bytesCount += 2;  
		}
		//换行
		s += str.charAt(i);
		if(bytesCount>=26){  
			s = s + ' <br> ';
			//重置
			bytesCount=0;
		} 
	}  
	return s;  
}
function getBasestationSegListMore() {
	start++;
	getBasestationSegList(start);
}

			/**
 * 新增直埋段
 */
function addBasestationSeg() {
	mui.openWindow("basestationSegAdd.html");
}
/**
 * 
 * @param {Object} basestationSegName
 */
function getBasestationSegList(index) {
	plus.nativeUI.showWaiting("正在加载数据......");
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var url = plus.storage.getItem("url") + "/pdaBasestationPart!getBasestationPart.interface";
	var cv = plus.webview.currentWebview();
	var basestationId =cv.basestationId;
	var jsonStr = "{";
	if(basestationId != null && basestationId !=''){
		jsonStr +="'basestationId':"+basestationId+",";
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
			jsonRequest: jsonStr,
			UID: UID,
			start: index,
			limit: length,
			longiner: logingUser,
			areaName: areaName
		},
		success: function(response) {
			plus.nativeUI.closeWaiting();
			mCurrentWebView.endPullToRefresh();
			var result = JSON.stringify(response);
			var data = JSON.parse(result);
			var result = data.result;
			if(result == '0') {
				var infos = JSON.parse(data.info);
				var cells = document.body.querySelectorAll('.borderbottom');
				var count = -1;
				for(var i = cells.length, len = i + infos.length; i < len; i++) {
					count++;
					var info = infos[count];
					totalList.push(info);
					var resStr = getNewline(info.basestationPartName);
					$("#basestationSegUl").prepend("<div class=\"con borderbottom\" onclick=\"getBasestationSegDetail('" + info.id + "','" + info.basestationPartName + "')\";>" +
						"<h1 class=\"con-head\">" + resStr + "</h1>" +
						"<div class=\"pr10\">" +
						"<ul class=\"mui-table-view adrlist\" >" +
						"<li class=\"mui-table-view-cell mapicon\" >所属区域:" +
						info.basestationPartArea +
						"</li></ul></div></div>");
				}
			} else {
				mui.alert("获取数据失败！");
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
			mCurrentWebView.endPullToRefresh();
			mui.alert("获取数据失败！");

		}

	});
}

/**
 * 得到
 * @param {Object} basestationSegId
 * @param {Object} basestationSegName
 */
function getBasestationSegDetail(basestationSegId, basestationSegName) {
	mui.openWindow({
		url: "basestationSegDetail.html",
		id: "basestationSegDetail",
		extras: {
			basestationSegId: basestationSegId
		}
	});	
}

function getNewline(val) {
	var str = new String(val);
	var bytesCount = 0;
	var s = "";
	for(var i = 0, n = str.length; i < n; i++) {
		var c = str.charCodeAt(i);
		//统计字符串的字符长度
		if((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			bytesCount += 1;
		} else {
			bytesCount += 2;
		}
		//换行
		s += str.charAt(i);
		if(bytesCount >= 26) {
			s = s + ' <br> ';
			//重置
			bytesCount = 0;
		}
	}
	return s;
}
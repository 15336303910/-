function getBasestationListMore() {
	start=start+7;
	getBasestationList(start);
}

			//增加基站
function addBasestation() {
      mui.openWindow("basestationAdd.html");
}

//获取基站信息
function getBasestationList(index) {
	plus.nativeUI.showWaiting("正在加载数据......");
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaSiteBase!getSiteBase.interface";
	var cv = plus.webview.currentWebview();
	var int_id =cv.int_id;
	var jsonStr = "{";
	if(int_id != null && int_id !=''){
		jsonStr +="'int_id':"+int_id+",";
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
				if(infos.length == 0) {
					mui.alert("没有更多数据");
					return;
				}
				var cells = document.body.querySelectorAll('.borderbottom');
				var count = -1;
				for(var i = cells.length, len = i + infos.length; i < len; i++) {
					count++;
					var info = infos[count];
					totalList.push(info);
					var resStr = getNewline(info.zh_label);
					$("#basestationUl").prepend("<div class=\"con borderbottom\" onclick=\"getBasestationDetail('"+ info.int_id+"','"+info.zh_label+"');\">" +
						"<h1 class=\"con-head\">" + resStr + "</h1>" +
						"<div class=\"pr10\"><ul class=\"mui-table-view adrlist\">" +
						"<li class=\"mui-table-view-cell mapicon\" >所属区域:" +
						info.maintain_area +
						"</li></ul></div></div>");
				}
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

/**
 * 跳转到直埋修改详情页面
 * @param {Object} int_id
 * @param {Object} zh_label
 */
function getBasestationDetail(int_id, zh_label) {
	mui.openWindow({
		url:"basestationDetail.html",
		id:"basestationDetail",
		extras: {
			int_id:int_id,
			zh_label:zh_label
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

function searchBasestation() {
	var zh_label = $("#basestationName").val();
	plus.nativeUI.showWaiting("正在加载数据......");
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaSiteBase!getSiteBase.interface";
	var jsonStr = "{";
	if(zh_label != null && zh_label !=''){
		jsonStr +="'zh_label':"+zh_label+",";
	}else{
		return;
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
			longiner: logingUser
		},
		success: function(response) {
			plus.nativeUI.closeWaiting();
			mCurrentWebView.endPullToRefresh();
			var result = JSON.stringify(response);
			var data = JSON.parse(result);
			var result = data.result;
			if(result == '0') {
				$("#basestationUl").empty();
				var infos = JSON.parse(data.info);
				if(infos.length == 0) {
					mui.alert("没有更多数据");
					return;
				}
				var cells = document.body.querySelectorAll('.borderbottom');
				var count = -1;
				for(var i = cells.length, len = i + infos.length; i < len; i++) {
					count++;
					var info = infos[count];
					totalList.push(info);
					var resStr = getNewline(info.zh_label);
					$("#basestationUl").prepend("<div class=\"con borderbottom\" onclick=\"getBasestationDetail('"+ info.int_id+"','"+info.zh_label+"');\">" +
						"<h1 class=\"con-head\">" + resStr + "</h1>" +
						"<div class=\"pr10\"><ul class=\"mui-table-view adrlist\">" +
						"<li class=\"mui-table-view-cell mapicon\" >" +
						info.maintain_area +
						"</li></ul></div></div>");
				}
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
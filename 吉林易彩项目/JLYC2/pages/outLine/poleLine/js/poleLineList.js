//得到杆路段
function addPoleLine(){
	mui.openWindow("poleLineAdd.html");
}
function getPoleLineList(poleLineName){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLineSystem!getLineSystem.interface";
	
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: "{lineType:0}",
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
					var resStr = getNewline(info.lineName);
			        
			        $("#poleLineUl").append("<div class=\"con borderbottom\" onclick=\"getPolineDetail('"+info.id+"','"+ info.lineName+"')\";>" +
						"<h1 class=\"con-head\">" +resStr + "</h1>" +
						"<div class=\"pr10\">" +
						"<ul class=\"mui-table-view adrlist\" >" +
						"<li class=\"mui-table-view-cell mapicon\" >所属区域:" +
							info.lineArea +
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
 * 
 * @param {Object} poleLineId
 * @param {Object} poleLineName
 */
function getPolineDetail(id,poleLineName){
	mui.openWindow({
		url:"poleLineDetail.html",
		id:"poleLineDetail.html",
		extras: {
			poleLineId:id
		}
	});
}

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
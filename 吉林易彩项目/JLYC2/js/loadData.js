function getMaintainCompanyData(company) {
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLoadData!getMaintainCompanyData.interface";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:"{}",
			UID:UID,
			longiner:logingUser,
			areaName:areaName
		},
		success: function(response) {
			var result = JSON.stringify(response);
			var data = JSON.parse(result);
			var result = data.result;
			if(result =='0'){
				var infos = JSON.parse(data.info);
				for(var i=0;i<infos.length;i++){
					if(company != "" && typeof(company)!="undefined" && company == infos[i].value){
						$("#maintain_company").append("<option value ="+infos[i].value+" selected=\"selected\">"+infos[i].name+"</option>");
					}else{
						$("#maintain_company").append("<option value ="+infos[i].value+">"+infos[i].name+"</option>");
					}
				}
			}else{
				mui.alert(data.info);
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
		}
	});
}

function getMaintainAreaData(area) {
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var url = plus.storage.getItem("url")+"/pdaLoadData!getMaintainAreaData.interface";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:"{}",
			UID:UID,
			longiner:logingUser,
			areaName:areaName
		},
		success: function(response) {
			var result = JSON.stringify(response);
			var data = JSON.parse(result);
			var result = data.result;
			if(result =='0'){
				var infos = JSON.parse(data.info);
				for(var i=0;i<infos.length;i++){
					if(area != "" && typeof(area)!="undefined" && area == infos[i].value){
						$("#maintain_area").append("<option value ="+infos[i].value+" selected=\"selected\">"+infos[i].name+"</option>");
					}else{
						$("#maintain_area").append("<option value ="+infos[i].value+">"+infos[i].name+"</option>");
					}
				}
			}else{
				mui.alert(data.info);
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
		}
	});
}
/**
 * 提交新建直埋
 */
function submitBuried(){
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
				mui.alert("新增成功");
				
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
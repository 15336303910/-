/**
 * 提交新增基站
 */
function submitSiteBase(){
	var UID = plus.storage.getItem("uid");//获取缓存记录
	var logingUser = plus.storage.getItem("logingUser");//当前登录用户
	var areaName = plus.storage.getItem("areaName");//得到权限控制地市
	var jsonStr = "{";
	var e_nodeb_id = $("#e_nodeb_id").val();
	if(e_nodeb_id != null && e_nodeb_id !=''){
		jsonStr +="'e_nodeb_id':'"+e_nodeb_id+"',";
	}
	var city_id = $("#city_id").val();
	if(city_id != null && city_id !=''){
		jsonStr +="'city_id':'"+city_id+"',";
	}
	var county_id = $("#county_id").val();
	if(county_id != null && county_id !=''){
		jsonStr +="'county_id':'"+county_id+"',";
	}
	var cell_num = $("#cell_num").val();
	if(cell_num != null && cell_num !=''){
		jsonStr +="'cell_num':'"+cell_num+"',";
	}
	var ant_azimuth = $("#ant_azimuth").val();
	if(ant_azimuth != null && ant_azimuth !=''){
		jsonStr +="'ant_azimuth':'"+ant_azimuth+"',";
	}
	var an_advanceangle = $("#an_advanceangle").val();
	if(an_advanceangle != null && an_advanceangle !=''){
		jsonStr +="'an_advanceangle':'"+an_advanceangle+"',";
	}
	
	var related_site_addr = $("#related_site_addr").val();
	if(related_site_addr != null && related_site_addr !=''){
		jsonStr +="'related_site_addr':'"+related_site_addr+"',";
	}
	var related_site_addr_id = $("#related_site_addr_id").val();
	if(related_site_addr_id != null && related_site_addr_id !=''){
		jsonStr +="'related_site_addr_id':'"+related_site_addr_id+"',";
	}
	var tower_site_addr_code = $("#tower_site_addr_code").val();
	if(tower_site_addr_code != null && tower_site_addr_code !=''){
		jsonStr +="'tower_site_addr_code':'"+tower_site_addr_code+"',";
	}
	var tower_site_addr_id = $("#tower_site_addr_id").val();
	if(tower_site_addr_id != null && tower_site_addr_id !=''){
		jsonStr +="'tower_site_addr_id':'"+tower_site_addr_id+"',";
	}
	var clusters = $("#clusters").val();
	if(clusters != null && clusters !=''){
		jsonStr +="'clusters':'"+clusters+"',";
	}
	var province = $("#province").val();
	if(province != null && province !=''){
		jsonStr +="'province':'"+province+"',";
	}
	var countryside = $("#countryside").val();
	if(countryside != null && countryside !=''){
		jsonStr +="'countryside':'"+countryside+"',";
	}
	var unit = $("#unit").val();
	if(unit != null && unit !=''){
		jsonStr +="'unit':'"+unit+"',";
	}
	var dept_area = $("#dept_area").val();
	if(dept_area != null && dept_area !=''){
		jsonStr +="'dept_area':'"+dept_area+"',";
	}
	var district = $("#district").val();
	if(district != null && district !=''){
		jsonStr +="'district':'"+district+"',";
	}
	var reseau = $("#reseau").val();
	if(reseau != null && reseau !=''){
		jsonStr +="'reseau':'"+reseau+"',";
	}
	var zh_label = $("#zh_label").val();
	if(zh_label != null && zh_label !=''){
		jsonStr +="'zh_label':'"+zh_label+"',";
	}
	var enodeb_gap_name = $("#enodeb_gap_name").val();
	if(enodeb_gap_name != null && enodeb_gap_name !=''){
		jsonStr +="'enodeb_gap_name':'"+enodeb_gap_name+"',";
	}
	var equipment_vendor = $("#equipment_vendor").val();
	if(equipment_vendor != null && equipment_vendor !=''){
		jsonStr +="'equipment_vendor':'"+equipment_vendor+"',";
	}
	var equipment_type = $("#equipment_type").val();
	if(equipment_type != null && equipment_type !=''){
		jsonStr +="'equipment_type':'"+equipment_type+"',";
	}
	var ipv4_addr = $("#ipv4_addr").val();
	if(ipv4_addr != null && ipv4_addr !=''){
		jsonStr +="'ipv4_addr':'"+ipv4_addr+"',";
	}
	var subnet_mask = $("#subnet_mask").val();
	if(subnet_mask != null && subnet_mask !=''){
		jsonStr +="'subnet_mask':'"+subnet_mask+"',";
	}
	var gateway = $("#gateway").val();
	if(gateway != null && gateway !=''){
		jsonStr +="'gateway':'"+gateway+"',";
	}
	var bandwidth = $("#bandwidth").val();
	if(bandwidth != null && bandwidth !=''){
		jsonStr +="'bandwidth':'"+bandwidth+"',";
	}
	var mme1 = $("#mme1").val();
	if(mme1 != null && mme1 !=''){
		jsonStr +="'mme1':'"+mme1+"',";
	}
	var mme2 = $("#mme2").val();
	if(mme2 != null && mme2 !=''){
		jsonStr +="'mme2':'"+mme2+"',";
	}
	var enodeb_version = $("#enodeb_version").val();
	if(enodeb_version != null && enodeb_version !=''){
		jsonStr +="'enodeb_version':'"+enodeb_version+"',";
	}
	var duplex_mode = $("#duplex_mode").val();
	if(duplex_mode != null && duplex_mode !=''){
		jsonStr +="'duplex_mode':'"+duplex_mode+"',";
	}
	var omc_site_status = $("#omc_site_status").val();
	if(omc_site_status != null && omc_site_status !=''){
		jsonStr +="'omc_site_status':'"+omc_site_status+"',";
	}
	var site_esn = $("#site_esn").val();
	if(site_esn != null && site_esn !=''){
		jsonStr +="'site_esn':'"+site_esn+"',";
	}
	var site_type = $("#site_type").val();
	if(site_type != null && site_type !=''){
		jsonStr +="'site_type':'"+site_type+"',";
	}
	var site_level = $("#site_level").val();
	if(site_level != null && site_level !=''){
		jsonStr +="'site_level':'"+site_level+"',";
	}
	var site_longitude = $("#site_longitude").val();
	if(site_longitude != null && site_longitude !=''){
		jsonStr +="'site_longitude':'"+site_longitude+"',";
	}
	var site_latitude = $("#site_latitude").val();
	if(site_latitude != null && site_latitude !=''){
		jsonStr +="'site_latitude':'"+site_latitude+"',";
	}
	var project_no = $("#project_no").val();
	if(project_no != null && project_no !=''){
		jsonStr +="'project_no':'"+project_no+"',";
	}
	var is_site_shared = $("#is_site_shared").val();
	if(is_site_shared != null && is_site_shared !=''){
		jsonStr +="'is_site_shared':'"+is_site_shared+"',";
	}
	var omcid = $("#omcid").val();
	if(omcid != null && omcid !=''){
		jsonStr +="'omcid':'"+omcid+"',";
	}
	var net_date = $("#net_date").val();
	if(net_date != null && net_date !=''){
		jsonStr +="'net_date':'"+net_date+"',";
	}
	var remark = $("#remark").val();
	if(remark != null && remark !=''){
		jsonStr +="'remark':'"+remark+"',";
	}
	var update_time = $("#update_time").val();
	if(update_time != null && update_time !=''){
		jsonStr +="'update_time':'"+update_time+"',";
	}
	var update_person = $("#update_person").val();
	if(update_person != null && update_person !=''){
		jsonStr +="'update_person':'"+update_person+"',";
	}
	var maintain_company = $("#maintain_company").val();
	if(maintain_company != null && maintain_company !=''){
		jsonStr +="'maintain_company':'"+maintain_company+"',";
	}
	var maintain_area = $("#maintain_area").val();
	if(maintain_area != null && maintain_area !=''){
		jsonStr +="'maintain_area':'"+maintain_area+"',";
	}
	var maintain_manager = $("#maintain_manager").val();
	if(maintain_manager != null && maintain_manager !=''){
		jsonStr +="'maintain_manager':'"+maintain_manager+"',";
	}
	var maintain_manager_phone = $("#maintain_manager_phone").val();
	if(maintain_manager_phone != null && maintain_manager_phone !=''){
		jsonStr +="'maintain_manager_phone':'"+maintain_manager_phone+"',";
	}
	var maintain_area_zy = $("#maintain_area_zy").val();
	if(maintain_area_zy != null && maintain_area_zy !=''){
		jsonStr +="'maintain_area_zy':'"+maintain_area_zy+"',";
	}
	if(jsonStr.charAt(jsonStr.length-1) ==','){
		jsonStr = jsonStr.substr(0,jsonStr.length-1);
	}
	jsonStr +="}";
	var url = plus.storage.getItem("url")+"/pdaSiteBase!insertSiteBase.interface";
		mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest:jsonStr,
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
				var btnArray = ['继续', '返回'];
				mui.confirm('增加成功，是否返回基站列表?', '成功', btnArray, function(e) {
					if (e.index == 1) {
						var infos = JSON.parse(data.info);
						mui.openWindow("basestationList.html");
					}
				});
				
			}else{
				mui.alert(data.info);
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
	$("#site_longitude").val(detail.lon);
	$("#site_latitude").val(detail.lat);
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
}

/**
 * 得到经纬度数据
 */
function getLonLat(){
	mui.openWindow({
		url: "../../main/getLatLon.html",
		id: "getLonLat",
		extras: {
			resType:"siteBase",
			operate:"add"
		}
	});
}
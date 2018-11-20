/**
 * 弹出窗口信息
 * @param {Object} title
 * @param {Object} content
 */
function createInfoWindow(title, content) {
	var info = document.createElement("div");
	info.className = "info";

	var top = document.createElement("div");
	var titleD = document.createElement("div");
	var closeX = document.createElement("img");
	top.className = "info-top";
	titleD.innerHTML = title;
	closeX.src = "../../image/map/close2.gif";
	closeX.onclick = closeInfoWindow;

	top.appendChild(titleD);
	top.appendChild(closeX);
	info.appendChild(top);

	var middle = document.createElement("div");
	middle.className = "info-middle";
	middle.style.backgroundColor = 'white';
	middle.innerHTML = content;
	info.appendChild(middle);

	var bottom = document.createElement("div");
	bottom.className = "info-bottom";
	bottom.style.position = 'relative';
	bottom.style.top = '0px';
	bottom.style.margin = '0 auto';
	var sharp = document.createElement("img");
	sharp.src = "../../image/map/sharp.png";
	bottom.appendChild(sharp);
	info.appendChild(bottom);
	return info;
}

function closeInfoWindow() {
	map.clearInfoWindow();
}

/**
 * 画条线玩玩
 * @param {Object} start
 * @param {Object} end
 * @param {String} name
 * @param {String} id
 * @param {String} type
 */
function initResLine(start, end, name, id, type) {
	var lineArr = [
		[start.lon, start.lat],
		[end.lon, end.lat]
	];
	var polyline = new AMap.Polyline({
		path: lineArr,
		strokeColor: "#3366FF",
		strokeOpacity: 1,
		strokeWeight: 3,
		strokeStyle: "solid",
		extData: start.id + "_" + start.type + "*" + end.id + "_" + end.type,
		strokeDasharray: [10, 5]
	});
	lineList.push(polyline);
	polyline.setMap(map);
	polyline.on('click', function() {
		console.log("走了这里。。。点击了线。。。。。。。。。。");
		title = name;
		content = [];
		content.push(name+"<br>");
		content.push("<a href='javascript:void(0);' onclick='seelineDetail(\"" + id + "\",\"" + type + "\")'>查看详情</a>");
		content.push("<a href='javascript:void(0);' onclick='deletline(\"" + id + "\",\"" + type + "\",\"" + start + "\",\"" + end + "\",\"" + name + "\")'>删除</a>");
		content.push("<a href='javascript:void(0);' onclick='addlinePoint(\"" + id + "\",\"" + type + "\")'>插入井</a>");
		var infoWindow = new AMap.InfoWindow({
			isCustom: true, //使用自定义窗体
			content: createInfoWindow(title, content.join("<br/>")),
			offset: new AMap.Pixel(16, -45)
		});
		infoWindow.open(map, polyline.getPath()[1]);

	});
}

function geoInf(position) {
	plus.nativeUI.closeWaiting();
	var str = "";
	str += "地址：" + position.addresses + "\n"; //获取地址信息
	str += "坐标类型：" + position.coordsType + "\n";
	var timeflag = position.timestamp; //获取到地理位置信息的时间戳；一个毫秒数；
	str += "时间戳：" + timeflag + "\n";
	var codns = position.coords; //获取地理坐标信息；
	var lat = codns.latitude; //获取到当前位置的纬度；
	str += "纬度：" + lat + "\n";
	var longt = codns.longitude; //获取到当前位置的经度
	str += "经度：" + longt + "\n";
	var alt = codns.altitude; //获取到当前位置的海拔信息；
	str += "海拔：" + alt + "\n";
	var accu = codns.accuracy; //地理坐标信息精确度信息；
	str += "精确度：" + accu + "\n";
	var altAcc = codns.altitudeAccuracy; //获取海拔信息的精确度；
	str += "海拔精确度：" + altAcc + "\n";
	var head = codns.heading; //获取设备的移动方向；
	str += "移动方向：" + head + "\n";
	var sped = codns.speed; //获取设备的移动速度；
	str += "移动速度：" + sped;
	console.log(JSON.stringify(position));
	console.log("定位结果" + str);

	plus.storage.setItem("cadress", position.addresses);
	console.log("获取地址==" + position.addresses);

	//定位成功获取当前的经纬度信息
	getGisRes(longt, lat, '');
	locLon = longt;
	locLat = lat;
	console.log("locLon=====" + locLon);
	console.log("locLat=====" + locLat);
//	map.center = [locLon, locLat];
	map.setCenter([locLon, locLat]);

}

//定位成功
function onComplete(data) {
	locLon = data.position.getLng();
	locLat = data.position.getLat();
	console.log("locLon=====" + locLon);
	console.log("locLat=====" + locLat);
	//定位成功获取当前的经纬度信息
	getGisRes(locLon, locLat, '');

}
//定位失败
function onError(data) {
	console.log("data====" + data.state)
	
//	mui.alert("网络链接失败!");
    plus.geolocation.getCurrentPosition(geoInf, function(e) {
		plus.nativeUI.closeWaiting();
		mui.toast("定位失败，请点击定位按钮或切换网络重试" + e.message);
	}, {
		geocode: true
	});
}


/**
 * 定位刷新
 */
function setCenter() {
	
	plus.geolocation.getCurrentPosition(geoInf, function(e) {
		plus.nativeUI.closeWaiting();
		mui.toast("定位失败，请点击定位按钮或切换网络重试" + e.message);
	}, {
		geocode: true
	});
}

/**
 * 刷新地图数据
 */
function refreshFun() {
//	map.clearMap();
	map.remove(lineList);
	map.remove(markObjectList);
	markList = new  Array;
	getGisRes(locLon, locLat, '');
}

/**
 * 调用资源接口在GIS地图上画点线
 * @param {Object} locLon
 * @param {Object} locLat
 * @param {Object} mvalue
 */
function getGisRes(locLon, locLat, mValue) {
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var url = plus.storage.getItem("url") + "/pdaGis!query.interface";
	resType = 'all'
	var jsonStr = "{";
	if(locLon != null && locLon != '') {
		jsonStr += "'lon':" + locLon + ",'lat':" + locLat + ",";
	}
	if(resType != null && resType != '') {
		jsonStr += "'type':" + resType + ",";
	}
	if(mValue != null && mValue != '') {
		jsonStr += "'mValue':" + mValue + ",";
	}
	if(jsonStr.charAt(jsonStr.length - 1) == ',') {
		jsonStr = jsonStr.substr(0, jsonStr.length - 1);
	}
	jsonStr += "}";
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: jsonStr,
			UID: UID,
			longiner: logingUser,
			areaName: areaName
		},
		success: function(response) {
			plus.nativeUI.closeWaiting();
			var result = JSON.stringify(response);
//			result ='{"info":[{"start":{"id":"3","name":"测试浪潮站点","lat":"36.6625484734","lon":"117.1301419107","type":"site"}},{"start":{"id":"EIU_1508305773994","name":"历下区G309辅路/GJ001","lat":"36.6670233125","lon":"117.1336895461","type":"optical"}},{"start":{"id":"EIU_1483422986943","name":"历下区经十路辅路/GJ001","lat":"36.6680115765","lon":"117.1299002909","type":"optical"}}],"result":"0"';
			console.log("===地图返回=="+result);
			var data = JSON.parse(result);
			var result = data.result; 
			console.log("=====" + data.info);
			initRes(data.info, locLon, locLat);
			if(result == '0') {
				initRes(data.info, locLon, locLat);
				resData = data.info;
			} else {
				mui.alert("获取数据失败！");
			}
		},
		error: function(xhr, type, errorThrown) {
			console.log("====="+errorThrown);
			plus.nativeUI.closeWaiting();
		}

	});
}

/**
 * 弹出点详情页面
 * @param {Object} id
 * @param {Object} type
 */
function seeDetail(id, type) {
	if(type == 'well') {
		mui.openWindow({
			url: "/pages/outLine/well/wellDetail.html",
			id: "wellDetail",
			extras: {
				//wellId: id
			      int_id:id
			}
		});
	}
	if(type == 'pole') {
		mui.openWindow({
			url: "/pages/outLine/pole/poleDetail.html",
			id: "poleDetail",
			extras: {
				poleId: id
			}
		});
	}
	if(type == 'stone') {
		mui.openWindow({
			url: "/pages/outLine/stone/stoneDetail.html",
			id: "stoneDetail",
			extras: {
				id: id
			}
		});
	}
}

/**
 * 删除点
 * @param {Object} id
 * @param {Object} type
 */
function deletPoint(id, type) {
	closeInfoWindow();
	mui.confirm("确定要删除吗？", "", ["取消", "确认"], function(el) {
		if(el.index == 1) {
			var UID = plus.storage.getItem("uid"); //获取缓存记录
			var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
			var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
			if(type == 'well') {
				url = plus.storage.getItem("url") + "/pdaPipe!deleteWell.interface";
				jsonStr = "{'wellId':'" + id + "'" +
					"}";
			} else if(type == 'pole') {
				url = plus.storage.getItem("url") + "/pdaPoleline!deletePole.interface";
				jsonStr = "{'poleId':'" + id + "'" +
					"}";
			} else if(type == 'optical') {
				url = plus.storage.getItem("url") + "/pdaEqut!updateEqut.interface";
				jsonStr = "{'eid':'" + id + "'," +
					"'deleteFlag':'1'" +
					"}";
			} else if(type == 'stone') {
				url = plus.storage.getItem("url") + "/pdaLeadup!upSupportPoint.interface";
				jsonStr = "{'id':'" + id + "'," +
					"'deleteFlag':'1'" +
					"}";
			} else if(type == 'joint') {
				url = plus.storage.getItem("url") + "/ppdaRoute!deleteJoint.interface";
				jsonStr = "{'joinId':'" + id + "'," +
					"'deleteFlag':'1'" +
					"}";
			}

			mui.ajax(url, {
				type: 'post',
				dataType: 'json',
				timeout: 10000 * 600,
				data: {
					jsonRequest: jsonStr,
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
//						map.clearMap();
						refreshFun();
						mui.alert("删除成功！");					

					} else {
						mui.alert("获取数据失败！");
					}
				},
				error: function(xhr, type, errorThrown) {
					plus.nativeUI.closeWaiting();
				}

			});
		} else {
			return;
		}
	});

}

/**
 * 跳转路线详情页面
 * @param {Object} id
 * @param {Object} type
 */
function seelineDetail(id, type) {
	console.log("type========="+type);
	var url;
	if(type == 'pipe') {
		mui.openWindow({
			url: "/pages/outLine/pipe/pipeDetail.html",
			id: "pipeDetail",
			extras: {
				wellId: id
			}
		});
	}
	else if(type == 'poleLine') {
		mui.openWindow({
			url: "/pages/outLine/poleLine/poleLineDetail.html",
			id: "poleLineDetail",
			extras: {
				poleId: id
			}
		});
	}
	else if(type == 'poleLineSeg') {
		mui.openWindow({
			url: "/pages/outLine/poleLineSeg/poleLineSegDetail.html",
			id: "poleLineSegDetail",
			extras: {
				id: id
			}
		});
	}else if(type == 'hangWall') {
		mui.openWindow({
			url: "/pages/outLine/poleLineSeg/poleLineSegDetail.html",
			id: "poleLineSegDetail",
			extras: {
				id: id
			}
		});
	}else 
//	if(type == 'cable') 
	{
		mui.openWindow({
			url: "/pages/toec/equt/equtDetail.html",
			id: "poleLineSegDetail",
			extras: {
				id: id
			}
		});
	}

}

/**
 * 删除路线
 * @param {Object} id
 * @param {Object} type
 */
function deletline(id, type, start, end, name) {
	closeInfoWindow();
	console.log("type========"+type);
	mui.confirm("确定要删除吗？", "", ["取消", "确认"], function(el) {
		if(el.index == 1) {
			var UID = plus.storage.getItem("uid"); //获取缓存记录
			var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
			var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
			resType = 'all'
			if(type == 'pipe') {
				url = plus.storage.getItem("url") + "/pdaPipe!deletePipeseg.interface";
				jsonStr = "{'pipeSegmentId':'" + id + "'," +
					"'pipeSegmentName':'" + name + "'," +
					"'startDeviceId':'" + start + "'," +
					"'endDeviceId':'" + end + "'," +
					"'deleteFlag':'1'" +
					"}";
			} else if(type == 'poleLine') {
				url = plus.storage.getItem("url") + "/pdaPoleline!deletePolelineSeg.interface";
				jsonStr = "{'poleLineSegmentId':'" + id + "'," +
					"'poleLineSegmentName':'" + name + "'," +
					"'startDeviceId':'" + start + "'," +
					"'endDeviceId':'" + end + "'," +
					"'deleteFlag':'1'" +
					"}";
			} else if(type == 'hangWall') {
				url = plus.storage.getItem("url") + "/pdaLeadup!updateHangWall.interface";
				jsonStr = "{'id':'" + id + "'," +
					"'hangWallName':'" + name + "'," +
					"'startDeviceId':'" + start + "'," +
					"'endDeviceId':'" + end + "'," +
					"'deleteFlag':'1'" +
					"}";
			} else 
			if(type == 'cable') 
			{
				url = plus.storage.getItem("url") + "/pdaRoute!deleteCable.interface";
				jsonStr = "{'pipeSegmentId':'" + id + "'," +
					"'pipeSegmentName':'" + name + "'," +
					"'startDeviceId':'" + start + "'," +
					"'endDeviceId':'" + end + "'," +
					"'deleteFlag':'1'" +
					"}";
			}
			mui.ajax(url, {
				type: 'post',
				dataType: 'json',
				timeout: 10000 * 60,
				data: {
					jsonRequest: jsonStr,
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
//						map.clearMap();
						refreshFun();
						mui.alert("删除成功！");
					} else {
						mui.alert("删除失败！");
					}
				},
				error: function(xhr, type, errorThrown) {
					plus.nativeUI.closeWaiting();
				}

			});
		} else {
			return;
		}
	});

}

/**
 * 插入井
 * @param {Object} id
 * @param {Object} type
 */
function addlinePoint(id, type) {
	closeInfoWindow();
	mui.openWindow("/pages/outLine/well/wellAdd.html?lined='" + id + "'&linetype='" + type + "'");
}

/**
 * 加载周边的资源信息
 */
function initRes(info,mLon,mLat) {
	var lines = eval("(" + info + ")");
	initCurrentPoint(mLon,mLat);
	for(var i = 0; i < lines.length; i++) {
		if(i == 0){
			console.log("d定位点==");
			initPoint(lines[i].start);
		}else{
			initPoint(lines[i].start);
		}
		if(lines[i].id != null && lines[i].id != '') {
			initPoint(lines[i].end);
			initResLine(lines[i].start, lines[i].end, lines[i].name, lines[i].id, lines[i].end.type);
		}
	}
}

/**
 * 点击事件
 * @param {Object} id
 * @param {Object} type
 */
function resOnClick(id, type) {

}

/**
 * 判断MarkList是否包含相应的数据
 * @param {Object} obj
 */
function constainFun(obj) {
	for(var i in markList) {
		if(obj == markList[i]) {
			return true;
		} else {
			continue;
		}
	}
	return false;
}

/**
 * 定位点信息
 * @param {Object} mlon 经度
 * @param {Object} mlat 维度
 */
function initCurrentPoint(mlon, mlat) {
	if(mlon != null && mlat != null) {
		var mMarker = new AMap.Marker({
			icon: "../../image/map/dingwei.png",
			offset: new AMap.Pixel(-24, -48),
			draggable: false,
			extData: "当前位置",
			position: [mlon, mlat]
		});
		mMarker.setMap(map);
	}
}
/**
 * 初始化点信息
 * @param {Object} point
 */
function initPoint(point) {
	var png = "current";
	if(point.type != null && point.type != '') {
		png = point.type;
	}

	/*if(mlon != null && mlat != null) {
		var mMarker = new AMap.Marker({
			icon: "../../image/map/icon_marka.png",
			offset: new AMap.Pixel(-24, -48),
			draggable: false,
			extData: "当前位置",
			position: [mlon, mlat]
		});

		mMarker.setMap(map);

	}*/

	if(!(constainFun(point.id + "_" + point.type))) {
		var marker = new AMap.Marker({
			icon: "../../image/map/" + png + ".png",
			offset: new AMap.Pixel(-24, -48),
			draggable: true,
			extData: point.id + "_" + point.type,
			position: [point.lon, point.lat]
		});

		//点击事件
		marker.on('click', function() {
			if(reverseDeleteStatus == 1) { //正常点击
				this.setLabel({ //label默认蓝框白底左上角显示，样式className为：amap-marker-label
					content: "已选"
				});
				reverseDeleteRes.push(this);

			} else if(isRelating == true) {
				if(relatmarkinfo.length == 1) {
					for(var i = 0; i < relatmarkinfo.length; i++) {
						if(relatmarkinfo[i].id == point.id) {
							mui.alert("请选择不同的两点", "系统提示");
							return;
						}
						/*if(!relatmarkinfo.get[i].type==point.type) {
							mui.alert("请选择同种类型的两点", "系统提示");
							return;
						}*/
					}
					mui.confirm("设为终点", "", ["取消", "确认"], function(el) {
						if(el.index == 1) {
							relatmarkinfo.push(point);
							isRelating = false;
							relateConfirm();
						} else {
							return;
						}
					});

				} else if(relatmarkinfo.length == 0) {
					mui.confirm("设为起点", "", ["取消", "确认"], function(el) {
						if(el.index == 1) {
							relatmarkinfo.push(point);
						} else {
							return;
						}
					});
					//				relatmarkinfo.push(point);
				}
			} else { //反向删除时点击

				title = point.name;
				content = [];
				content.push("经度:" + point.lon + ";<br>纬度:" + point.lat + ";");
				content.push("<a href='javascript:void(0);' onclick='seeDetail(\"" + point.id + "\",\"" + point.type + "\")'>查看详情</a>");
				content.push("<a href='javascript:void(0);' onclick='deletPoint(\"" + point.id + "\",\"" + point.type + "\")'>删除</a>");
				var infoWindow = new AMap.InfoWindow({
					isCustom: true, //使用自定义窗体
					content: createInfoWindow(title, content.join("<br/>")),
					offset: new AMap.Pixel(16, -45)
				});
				infoWindow.open(map, marker.getPosition());
			}

		});
		//开始移动
		marker.on('dragstart', function(e) {
			var mdata = e.target.getExtData();
			for(var i = 0; i < lineList.length; i++) {
				var ldata = lineList[i].getExtData().split("*");
				if(ldata[0] == mdata) {
					lineStart.push(lineList[i]);
				}
				if(ldata[1] == mdata) {
					lineEnd.push(lineList[i]);
				}

			}
		});
		//移动中
		marker.on('dragging', function(e) {
			for(var i = 0; i < lineStart.length; i++) {
				var path = lineStart[i].getPath();
				path.splice(0, 1, [e.lnglat.lng, e.lnglat.lat]);
				lineStart[i].setPath(path);
			}
			for(var i = 0; i < lineEnd.length; i++) {
				var path = lineEnd[i].getPath(); //得到现在的path
				path.splice(1, 1, [e.lnglat.lng, e.lnglat.lat]);
				lineEnd[i].setPath(path);
			}
		})
		//结束移动
		marker.on('dragend', function(e) {
			var mdata = e.target.getExtData().split("_");
			for(var i = 0; i < lineStart.length; i++) {
				var path = lineStart[i].getPath();
				path.splice(0, 1, [e.lnglat.lng, e.lnglat.lat]);
				lineStart[i].setPath(path);
			}
			for(var i = 0; i < lineEnd.length; i++) {
				var path = lineEnd[i].getPath(); //得到现在的path
				path.splice(1, 1, [e.lnglat.lng, e.lnglat.lat]);
				lineEnd[i].setPath(path);
			}
			lineEnd = new Array();
			lineStart = new Array();
			//		e.target.setOffset(new AMap.Pixel(-24, -48));
			/**
			 * 作者：zhuxulus@gmail.com
			 * 时间：2018-06-23
			 * 增加提示信息
			 */
			mui.confirm("新坐标：" + e.lnglat.lng + "," + e.lnglat.lat + ",确定更新吗？", "", ["取消", "确认"], function(el) {
				if(el.index == 1) {
					e.target.setOffset(new AMap.Pixel(-24, -48));
					moveRes(mdata[0], mdata[1], e.lnglat.lng, e.lnglat.lat);
				} else {
					return;
				}
			});
		});
		//	map.add(marker);
		marker.setMap(map);
		markList.push(point.id + "_" + point.type);
		markObjectList.push(marker);
	}
}

/**
 * 移动资源点数据
 * @param {Object} resId
 * @param {Object} resType
 * @param {Object} lng
 * @param {Object} lat
 */
function moveRes(resId, resType, lng, lat) {
	var url, jsonStr;
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	if(resType == 'well') {
		url = plus.storage.getItem("url") + "/pdaPipe!updateWell.interface";
		jsonStr = "{'wellId':'" + resId + "'," +
			"'longitude':'" + lng + "'," +
			"'latitude':'" + lat + "'" +
			"}";
	} else if(resType == 'pole') {
		url = plus.storage.getItem("url") + "/pdaPoleline!updatePole.interface";
		jsonStr = "{'poleId':'" + resId + "'," +
			"'poleLongitude':'" + lng + "'," +
			"'poleLatitude':'" + lat + "'" +
			"}";
	}

	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: jsonStr,
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
				mui.alert("修改成功!");

			} else {
				mui.alert("获取数据失败！");
			}
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
		}

	});

}

/**
 * 打开工单列表
 */
function sendTask() {
	mui.alert("工单列表");
}
//打开管线的资源
function getPipe() {
	mui.openWindow("/pages/pipe/pipeList.html?resType=\"pipe\"");
}
//打开外线资源9宫格
function getOutLine() {
	mui.openWindow("/pages/outLine/outMain.html");
}

function getToec() {
	mui.openWindow("/pages/toec/toecMain.html")
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-21
      描述：选择资源类型，跳转到相对应的新增页面
 */
/**
 * 切换 选择资源类型层 的显示状态
 */
function locateAdd() {
	mui('.mui-popover').popover('toggle', document.getElementById("locateAddPopover"));
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-21
 * 选择资源类型后 确定
 */
function locateAddSubmit() {
	var resourceType = document.getElementById("resourceType");
	var index = resourceType.selectedIndex;
	var resourceTypeValue = resourceType.options[index].value;
	if(resourceTypeValue == "well") {
		mui.openWindow("/pages/outLine/well/wellAdd.html");
	} else if(resourceTypeValue == "pole") {
		mui.openWindow("/pages/outLine/pole/poleAdd.html");
	} else if(resourceTypeValue == "stone") {
		mui.openWindow("/pages/outLine/stone/storeAdd.html");
	} else if(resourceTypeValue == "optical") {
		//TODO
		mui.openWindow("/pages/outLine/optical/opticalAdd.html");
	} else {
		mui.alert("请选择资源类型")

	}

	locateAddCancel();
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-21
 * 选择资源类型后 取消
 */
function locateAddCancel() {
	mui('.mui-popover').popover('hide', document.getElementById("locateAddPopover"));
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-22
 * 切换显示gis地图上的各类资源点、线
 */
function toggleResourcesByType(type) {
	console.log(type)
	//清除地图上所有点和线
	map.remove(lineList);
	map.remove(markObjectList);
	//清空数组
	markList.length = 0;
	//按类型重新加载资源点线
	var lines = eval("(" + resData + ")");
	if(lines == null || lines == '') {
		return;
	}
	for(var i = 0; i < lines.length; i++) {

		//console.log(JSON.stringify(lines[i]))
		if(lines[i].start.type == type || type == "all") { //点-start
			if(i == 0) {
				initPoint(lines[i].start);
			} else {
				initPoint(lines[i].start);
			}
		}
		if(lines[i].id != null && lines[i].id != '' && lines[i].id != undefined) { //点-end和线
			if(type == "all") {
				initPoint(lines[i].end);
				initResLine(lines[i].start, lines[i].end, lines[i].name, lines[i].id, lines[i].end.type);
			} else if(type == 'pole') { //电杆
				if(lines[i].type == type || lines[i].type == "pole") {
					initPoint(lines[i].end);
					initResLine(lines[i].start, lines[i].end, lines[i].name, lines[i].id, lines[i].end.type);
				}
			} else if(type == 'well') { //管井
				if(lines[i].type == type || lines[i].type == "pipe") {
					initPoint(lines[i].end);
					initResLine(lines[i].start, lines[i].end, lines[i].name, lines[i].id, lines[i].end.type);
				}
			} else if(type == 'stone') { //标石
				if(lines[i].type == type || lines[i].type == "buried") {
					initPoint(lines[i].end);
					initResLine(lines[i].start, lines[i].end, lines[i].name, lines[i].id, lines[i].end.type);
				}
			}

		}
	}
}

/**
 *关联
 * 
 */
function relate() {
	isRelating = true;
	document.getElementById("relateConfirmBtn").style.display = ""; //#0D9BF2
	document.getElementById("relateCancelBtn").style.display = ""; //#0D9BF2
	document.getElementById("pantoBtn").style.display = "none";
	document.getElementById("locateAdd").style.display = "none";
	document.getElementById("deleteAllBtn").style.display = "none";
	document.getElementById("reverseDeleteBtn").style.display = "none";
	relatmarkinfo = new Array;
}

/**
 * 取消关联 
 */
function relateCancel() {
	isRelating = false;
	document.getElementById("relateConfirmBtn").style.display = "none"; //#0D9BF2
	document.getElementById("relateCancelBtn").style.display = "none"; //#0D9BF2
	document.getElementById("pantoBtn").style.display = "";
	document.getElementById("locateAdd").style.display = "";
	document.getElementById("deleteAllBtn").style.display = "";
	document.getElementById("reverseDeleteBtn").style.display = "";
	relatmarkinfo = new Array;
}

/**
 * 关联 确认
 */
function relateConfirm() {
	if(relatmarkinfo.length < 2) {
		return;
	}
	var url, jsonStr;
	var UID = plus.storage.getItem("uid"); //获取缓存记录
	var logingUser = plus.storage.getItem("logingUser"); //当前登录用户
	var areaName = plus.storage.getItem("areaName"); //得到权限控制地市
	var start;
	var end;

	for(var i = 0; i < relatmarkinfo.length; i++) {
		if(i == 0) {
			start = relatmarkinfo[0];
		} else {
			end = relatmarkinfo[1];
		}
	}
	var type = start.type;

	if("pole" == type) {
		//url = plus.storage.getItem("url") + "/pdaPoleline!insertPolelineSeg.interface";
		url = plus.storage.getItem("url") + "/pdaPoleSegCustom!insertPoleSegCustom.interface";
		jsonStr = "{'poleLineSegmentName':'" + start.name + "'-'" + end.name + "'杆路段," +
			"'endDeviceName':'" + end.name + "'," +
			"'startDeviceName':'" + start.name + "'" +
			"'startDeviceId':'" + start.id + "'," +
			"'endDeviceId':'" + end.id + "'" +
			"}";

	} else if("well" == type) {
		url = plus.storage.getItem("url") + "/pdaLeadup!insertHangWall.interface";
		jsonStr = "{'pipeSegmentName':'" + start.name + "'-'" + end.name + "'挂墙段," +
			"'endDeviceId':'" + end.id + "'," +
			"'latitude':'" + lat + "'" +
			"}";

	} else if("stone" == type) {
		url = plus.storage.getItem("url") + "/pdaBuriedPart!insertBuriedPart.interface";
		jsonStr = "{'buriedPartName':'" + start.name + "'-'" + end.name + "'直埋段," +
			"'buriedPartEnd':'" + end.name + "'," +
			"'buriedPartStart':'" + start.name + "'" +
			"'buriedPartStartId':'" + start.id + "'," +
			"'buriedPartEndId':'" + end.id + "'" +
			"}";

	}
	console.log("jsonStr=====" + jsonStr);
	mui.ajax(url, {
		type: 'post',
		dataType: 'json',
		timeout: 10000 * 60,
		data: {
			jsonRequest: jsonStr,
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
				mui.alert("关联成功!");
//				map.clearMap();
				refreshFun();

			} else {
				mui.alert("关联失败！");
			}
			relateCancel();
		},
		error: function(xhr, type, errorThrown) {
			plus.nativeUI.closeWaiting();
			relateCancel();
		}

	});

}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-23
 * 全部删除
 */
function deleteAll() {
	mui.confirm("确定要全部删除吗？", "", ["取消", "确认"], function(el) {
		if(el.index == 1) {
			/*	var url,jsonStr;
				var UID = plus.storage.getItem("uid");//获取缓存记录
				var logingUser = plus.storage.getItem("logingUser");//当前登录用户
				var areaName = plus.storage.getItem("areaName");//得到权限控制地市
				url = plus.storage.getItem("url")+"/pdaGis!deleteall.interface";
				jsonStr = "{'fw':'"+this.value+"'"
					 +"'lon':"+locLon+",'lat':"+locLat+""
				    +"}";
					//清除地图上所有点和线
					mui.ajax(url, {
						type: 'post',
						dataType: 'json',
						timeout: 10000 * 60,
						data: {
							jsonRequest: jsonStr,
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
								map.clearMap();		            
								mui.alert("删除成功！");
						        
							}else{
								mui.alert("获取数据失败！");
							}
						},
						error: function(xhr, type, errorThrown) {
							plus.nativeUI.closeWaiting();
						}
				
					});	*/

			//清除地图上所有点和线
			map.remove(lineList);
			map.remove(markObjectList);
			mui.alert("删除成功！")

			//TODO
			//后台删除
			//			mui.alert("删除成功！")
		} else {
			return;
		}
	});
}

function showRelateMarkerInfoWindow() {

}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-23
 * 反向删除
 */
function reverseDelete() {
	reverseDeleteStatus = 1;
	document.getElementById("reverseDeleteConfirmBtn").style.display = ""; //#0D9BF2
	document.getElementById("reverseDeleteCancelBtn").style.display = ""; //#0D9BF2
	document.getElementById("reverseDeleteBtn").style.display = "none";
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-23
 * 反向删除 确认
 */
function reverseDeleteConfirm() {
	mui.confirm("确定要删除其他资源吗？", "", ["取消", "确认"], function(el) {
		if(el.index == 1) {
			//清除地图上所有点和线
			for(var i = 0; i < markObjectList.length; i++) {
				if(!reverseDeleteRes.contains(markObjectList[i])) {
					map.remove(markObjectList[i]);
				}
			}
			//TODO
			//删除相关的线

			//TODO
			//后台删除

			reverseDeleteStatus = 0;
			for(var i = 0; i < reverseDeleteRes.length; i++) {
				reverseDeleteRes[i].setLabel({ //label默认蓝框白底左上角显示，样式className为：amap-marker-label
					content: ""
				});
			}
			mui.alert("删除成功！")

			/*var url,jsonStr;
			var UID = plus.storage.getItem("uid");//获取缓存记录
			var logingUser = plus.storage.getItem("logingUser");//当前登录用户
			var areaName = plus.storage.getItem("areaName");//得到权限控制地市
			url = plus.storage.getItem("url")+"/pdaGis!delectall.interface";
			var deletstr = new Gson().toJSON(reverseDeleteList);
			jsonStr = "{'reverseDeleteList':'"+reverseDeleteList+"',"
				 
			    +"}";
				//清除地图上所有点和线
				mui.ajax(url, {
					type: 'post',
					dataType: 'json',
					timeout: 10000 * 60,
					data: {
						jsonRequest: jsonStr,
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
							reverseDeleteStatus = 0;
							for(var i=0; i<reverseDeleteRes.length; i++) {
								reverseDeleteRes[i].setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
							        content: ""
							    });
							}
							mui.alert("删除成功！")
					        
						}else{
							mui.alert("获取数据失败！");
						}
					},
					error: function(xhr, type, errorThrown) {
						plus.nativeUI.closeWaiting();
					}
			
				});	*/
		} else {
			return;
		}
	});
}

/**
 * 作者：zhuxulus@gmail.com
      时间：2018-06-23
 * 反向删除 取消
 */
function reverseDeleteCancel() {
	reverseDeleteStatus = 0;
	document.getElementById("reverseDeleteConfirmBtn").style.display = "none"; //#0D9BF2
	document.getElementById("reverseDeleteCancelBtn").style.display = "none"; //#0D9BF2
	document.getElementById("reverseDeleteBtn").style.display = "";
	for(var i = 0; i < reverseDeleteRes.length; i++) {
		reverseDeleteRes[i].setLabel({ //label默认蓝框白底左上角显示，样式className为：amap-marker-label
			content: ""
		});
	}
}

/**
 * array contains方法
 * @param {Object} obj
 */
Array.prototype.contains = function(obj) {
	var i = this.length;
	while(i--) {
		if(this[i] === obj) {
			return true;
		}
	}
	return false;
}
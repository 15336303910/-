function getEtypeStr(v) {
	if (v == '1') {
		return '光配线架（ODF）';
	} else if (v == '2') {
		return '综合性光配线架（MODF）';
	} else if (v == '3') {
		return '光交接箱';
	} else if (v == '4') {
		return '光分路器';
	} else if (v == '5') {
		return '光分纤盒';
	} else if (v == '6') {
		return '光缆接头盒（终端盒）';
	} else if (v == '7') {
		return '分光器(ODB)';
	} else if (v == '9') {
		return '未知';
	}
}
function getEstatStr(v) {
	if (v == '1') {
		return '空闲';
	} else if (v == '2') {
		return '故障';
	} else if (v == '3') {
		return '可用';
	}
}
function getPointTypeStr(v) {
	if ("1" == v) {
		return "普通端子";
	} else if ("2" == v) {
		return "分光器端子";
	} else if ("3" == v) {
		return "λ端子";
	} else if ("4" == v) {
		return "ONU上连端子";
	} else if ("5" == v) {
		return "ONU网口";
	}
}
function getPstatStr(v) {
	if ("1"==v) {
		return"空闲";
	} else if ("2"==v) {
		return"故障";
	} else if ("3"==v) {
		return"可用";
	} else if ("4"==v) {
		return"占用";
	} else if ("5"==v) {
		return"待核查";
	}
}

function getPdirectionStr(v) {
	if ("1"==v) {
		return"向局端";
	} else if ("2"==v) {
		return"向用户端";
	} 
}

function getAuditflagStr(v) {
	if (v == '0') {
		return '手动审核';
	} else if (v == '1') {
		return '自动审核';
	}
}

function getOrderTypeStr(v) {
	if (v == '6') {
		return '施工工单';
	} else if (v == '7') {
		return '设备配置工单';
	} else if (v == '8') {
		return '设备巡检工单';
	} else if (v == '9') {
		return '设备升级工单';
	}else if (v == '10') {
		return '端子核查单';
	}else if (v == '11') {
		return '设备配置工单';
	}else if (v == '12') {
		return '设备配置修改工单';
	}else if (v == '13') {
		return '设备巡检';
	}else if (v == '14') {
		return '端口操作工单';
	}else if (v == '15') {
		return '分光器施工工单';
	}else if (v == '16') {
		return '设备巡检核查工单';
	}else if(v=='17'){
		return '业务工单';
	}
}
function getOrderStateStr(v) {
	// 0:创建 1:派发 2:收单 3:退单 4:回单 5:竣工 6:销单 7:重新派单 8:中心接受 9:子工单退单 10:转派
	if (v == '0') {
		return '创建';
	} else if (v == '1') {
		return '派发';
	} else if (v == '2') {
		return '收单';
	} else if (v == '3') {
		return '退单';
	} else if (v == '4') {
		return '回单';
	} else if (v == '5') {
		return '竣工';
	} else if (v == '6') {
		return '销单';
	} else if (v == '7') {
		return '重新派单';
	} else if (v == '8') {
		return '中心接受';
	} else if (v == '9') {
		return '子工单退单';
	} else if (v == '10') {
		return '转派';
	}else if (v == '11') {
		return '升级成功';
	}else if (v == '12') {
		return '升级失败';
	}
}
function getFiberStateStr(v) {
	if (v == '0') {
		return '空闲';
	} else if (v == '1') {
		return '单通';
	} else if (v == '2') {
		return '跳接';
	} else if (v == '20') {
		return '分光器输入端连接';
	} else if (v == '21') {
		return '分光器输出端连接';
	}
}

function getAlarmType(v) {
	if (v == '0') {
		return '端子告警';
	} else if (v == '1') {
		return '盘告警';
	}
}

function getEventType(v) {
	if (v == '0') {
		return '端子日志';
	} else if (v == '1') {
		return '盘日志';
	}
}

function help(s){
	var url=s;
	var iWidth=850; //弹出窗口的宽度;
	var iHeight=700; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
	var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
	window.open(url,"newwindow",param);
}

function time(v){
	v=v.replace("T"," ");
	return v;
}


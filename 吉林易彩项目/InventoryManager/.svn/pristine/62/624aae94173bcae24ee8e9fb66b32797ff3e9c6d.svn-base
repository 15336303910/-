Ext.namespace("com.increase.cen.log");


//列模型
var cm = new Ext.grid.ColumnModel([
{header : "序号",width : 50,hideable : false,
	renderer : function(value, metadata, record, rowIndex, colIndex, store) {
		var start = store.lastOptions.params.start;
		if (isNaN(start)) {
			start = 0;
		}
		return start + rowIndex + 1;
	}
}, 
{header : 'logID',dataIndex : 'generatorName',sortable : true,hidden : true}, 
{header : '操作时间',dataIndex : 'logTime',
	renderer:function(v){
		v=v.replace("T"," ");
		v=grey(v);
		return v;
	}
}, 
{header : '操作人',dataIndex : 'logOperater',renderer:grey},
{header : '操作类型',dataIndex : 'operationType',renderer:OperationType}, 
{header : '高频开关电源ID',dataIndex : 'switchingId',hidden : true}, 
{header : '设备名称',dataIndex : 'deviceName',renderer:color1}, 
{header : '设备别名',dataIndex : 'deviceAlias',renderer:color1}, 
{header : '资产标签号',dataIndex : 'fixedAssetsCode',width : 150,renderer:color1}, 
{header : '所属机房',dataIndex : 'generator',renderer:color1},
{header : '额定电压',dataIndex : 'ratedVoltage',renderer:color1},
{header : '额定电流',dataIndex : 'ratedElectricity',width:150,renderer:color1},
{header : '所属动力系统',dataIndex : 'powerSystem',renderer:color1}, 
{header : '资源生命周期',dataIndex : 'lifecycle',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "设计状态";
		}else if(v=='1'){
			return "工程建设期（入网带业务前）";
		}else if(v=='2'){
			return "工程可用期（已入网带业务）";
		}else if(v=='3'){
			return "工程初验后试运行";
		}else if(v=='4'){
			return "工程终验后在网";
		}else if(v=='5'){
			return "退网状态";
		}
	}	
},
{header : '生命周期时间',dataIndex : 'lifecycleTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '维护状态',dataIndex : 'maintainState',width : 100,sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "正常";
		}else if(v=='1'){
			return "故障";
		}else if(v=='2'){
			return "维护";
		}
	}		
},
{header : '切割标记',dataIndex : 'cutMark',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "是";
		}else if(v=='1'){
			return "否";
		}
	}		
},
{header : '入网时间',dataIndex : 'inNetTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '设备厂商',dataIndex : 'deviceMaker',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "IBM";
		}else if(v=='1'){
			return "JUNIPER";
		}else if(v=='2'){
			return "ORACLE(SUN)";
		}else if(v=='3'){
			return "阿朗";
		}else if(v=='4'){
			return "爱立信";
		}else if(v=='5'){
			return "上海贝尔";
		}else if(v=='6'){
			return "烽火";
		}else if(v=='7'){
			return "华为";
		}else if(v=='8'){
			return "惠普";
		}else if(v=='9'){
			return "诺西";
		}else if(v=='10'){
			return "思科";
		}else if(v=='11'){
			return "中兴";
		}else if(v=='12'){
			return "3COM";
		}else if(v=='13'){
			return "APC";
		}else if(v=='14'){
			return "DB2";
		}else if(v=='15'){
			return "DELL";
		}else if(v=='16'){
			return "D-LINK";
		}else if(v=='17'){
			return "EMC";
		}else if(v=='18'){
			return "GNB";
		}else if(v=='19'){
			return "Microsoft";
		}else if(v=='20'){
			return "NETAPP";
		}else if(v=='21'){
			return "Oracle";
		}else if(v=='22'){
			return "PCCW";
		}else if(v=='23'){
			return "Quantum(昆腾)";
		}else if(v=='24'){
			return "Symantec";
		}else if(v=='25'){
			return "TP-LINK";
		}else if(v=='26'){
			return "艾克赛";
		}else if(v=='27'){
			return "艾默生";
		}else if(v=='28'){
			return "东方国信";
		}else if(v=='29'){
			return "东软";
		}else if(v=='30'){
			return "华日";
		}else if(v=='31'){
			return "佳力图";
		}else if(v=='32'){
			return "力博特";
		}else if(v=='33'){
			return "联想";
		}else if(v=='34'){
			return "梅兰日兰";
		}else if(v=='35'){
			return "神州数码";
		}else if(v=='36'){
			return "双登";
		}else if(v=='37'){
			return "泰岳";
		}else if(v=='38'){
			return "拓维";
		}else if(v=='39'){
			return "西门子";
		}else if(v=='40'){
			return "亚联";
		}else if(v=='41'){
			return "Jeep";
		}else if(v=='42'){
			return "LG";
		}else if(v=='43'){
			return "TCL";
		}else if(v=='44'){
			return "爱普生";
		}else if(v=='45'){
			return "奥林巴斯";
		}else if(v=='46'){
			return "宝马";
		}else if(v=='47'){
			return "奔驰";
		}else if(v=='48'){
			return "本田";
		}else if(v=='49'){
			return "标志";
		}else if(v=='50'){
			return "博世";
		}else if(v=='51'){
			return "大众";
		}else if(v=='52'){
			return "东南";
		}else if(v=='53'){
			return "东芝";
		}else if(v=='54'){
			return "丰田";
		}else if(v=='55'){
			return "福特";
		}else if(v=='56'){
			return "海尔";
		}else if(v=='57'){
			return "海信";
		}else if(v=='58'){
			return "佳能";
		}else if(v=='59'){
			return "金龙";
		}else if(v=='60'){
			return "康佳";
		}else if(v=='61'){
			return "理光";
		}else if(v=='62'){
			return "铃木";
		}else if(v=='63'){
			return "明基";
		}else if(v=='64'){
			return "尼康";
		}else if(v=='65'){
			return "日产";
		}else if(v=='66'){
			return "日立";
		}else if(v=='67'){
			return "三星";
		}else if(v=='68'){
			return "索尼";
		}else if(v=='69'){
			return "通用";
		}else if(v=='70'){
			return "同方";
		}else if(v=='71'){
			return "沃尔沃";
		}else if(v=='72'){
			return "五菱";
		}else if(v=='73'){
			return "夏普";
		}else if(v=='74'){
			return "现代";
		}else if(v=='75'){
			return "长安";
		}else if(v=='76'){
			return "长城";
		}else if(v=='77'){
			return "长虹";
		}else if(v=='78'){
			return "紫光";
		}else if(v=='79'){
			return "小生产厂商";
		}
	}		
},
{header : '小生产厂商',dataIndex : 'littleDeviceMaker',renderer:color1},
{header : '规格型号',dataIndex : 'model',renderer:color1},
{header : '是否环境监控',dataIndex : 'isEnvironmentMonitoring',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "是";
		}else if(v=='1'){
			return "否";
		}
	}	
},
{header : '动力监控系统名称',dataIndex : 'monitoringSystemName',renderer:color1},
{header : '整流模块型号',dataIndex : 'rectifierModuleModel',renderer:color1},
{header : '监控模块型号',dataIndex : 'supervisoryModuleModel',renderer:color1},
{header : '整流模块数量',dataIndex : 'rectifierModuleNum',renderer:color1},
{header : '已配模块数量',dataIndex : 'SuppliedModuleNum',renderer:color1},
{header : '已用模块数量',dataIndex : 'alreadUsedModuleNum',renderer:color1},
{header : '实际使用功率',dataIndex : 'actualUsePower',renderer:color1},
{header : '维护方式',dataIndex : 'maintenanceMode',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "自维";
		}else if(v=='1'){
			return "代维";
		}
	}	
},
{header : '维护单位',dataIndex : 'maintenanceUnit',width : 150,renderer:color1},
{header : '包机人',dataIndex : 'charteredMan',renderer:color1},
{header : '第三方维护单位',dataIndex : 'otherMaintainUnit',renderer:color1},
{header : '续保截止日期',dataIndex : 'renewalOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '维保类型',dataIndex : 'maintenanceType',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "硬件维修";
		}else if(v=='1'){
			return "紧急备件支持";
		}else if(v=='2'){
			return "软件补丁版本支持";
		}else if(v=='3'){
			return "技术支持服务（电话咨询、电话支持、远程支持）";
		}else if(v=='4'){
			return "无";
		}
	}		
},
{header : '已购买维保累计年限',dataIndex : 'purchasedMaintenanceTime',renderer:color1},
{header : '工程项目编号',dataIndex : 'projectCode',renderer:color1},
{header : '工程项目名称',dataIndex : 'projectName',renderer:color1},
{header : '工程保修截止日期',dataIndex : 'projectGuaranteeOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "是";
		}else if(v=='1'){
			return "否";
		}
	}		
},
{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',renderer:color1},
{header : '设计用途',dataIndex : 'designPurposes',renderer:color1},
{header : '原资源系统设备编号',dataIndex : 'oldResourceSystemEquNum',renderer:color1},
{header : '原资源系统设备名称',dataIndex : 'oldResourceSystemEquName',renderer:color1},

{header : '备注',dataIndex : 'note',width : 150,renderer:color1}
]);
// 从远程读取数据
var proxy = new Ext.data.HttpProxy({
	url : "log!getHFSLog.action"
});
// Record 定义记录结果
var hfs= Ext.data.Record.create([
{name : 'switchingId',type : 'string',mapping : 'switchingId',hidden : true
},{name : 'logId',type : 'string',mapping : 'logId'
},{name : 'logTime',type : 'string',mapping : 'logTime'
},{name : 'logOperater',type : 'string',mapping : 'logOperater'
},{name : 'operationType',type : 'string',mapping : 'operationType'
},{
	name : 'deviceName',
	type : 'string',
	mapping : 'deviceName'
},{
	name : 'deviceAlias',
	type : 'string',
	mapping : 'deviceAlias'
},{
	name : 'fixedAssetsCode',
	type : 'string',
	mapping : 'fixedAssetsCode'
},{
	name : 'generator',
	type : 'string',
	mapping : 'generator'
},{
	name : 'ratedVoltage',
	type : 'string',
	mapping : 'ratedVoltage'
},{
	name : 'ratedElectricity',
	type : 'string',
	mapping : 'ratedElectricity'
},{
	name : 'powerSystem',
	type : 'string',
	mapping : 'powerSystem'
},{
	name : 'lifecycle',
	type : 'string',
	mapping : 'lifecycle'
},{
	name : 'lifecycleTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'lifecycleTime'
},{
	name : 'maintainState',
	type : 'string',
	mapping : 'maintainState'
},{
	name : 'cutMark',
	type : 'string',
	mapping : 'cutMark'
},{
	name : 'inNetTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'inNetTime'
},{
	name : 'deviceMaker',
	type : 'string',
	mapping : 'deviceMaker'
},{
	name : 'littleDeviceMaker',
	type : 'string',
	mapping : 'littleDeviceMaker'
},{
	name : 'model',
	type : 'string',
	mapping : 'model'
},{
	name : 'isEnvironmentMonitoring',
	type : 'string',
	mapping : 'isEnvironmentMonitoring'
},{
	name : 'monitoringSystemName',
	type : 'string',
	mapping : 'monitoringSystemName'
},{
	name : 'rectifierModuleModel',
	type : 'string',
	mapping : 'rectifierModuleModel'
},{
	name : 'supervisoryModuleModel',
	type : 'string',
	mapping : 'supervisoryModuleModel'
},{
	name : 'rectifierModuleNum',
	type : 'string',
	mapping : 'rectifierModuleNum'
},{
	name : 'SuppliedModuleNum',
	type : 'string',
	mapping : 'SuppliedModuleNum'
},{
	name : 'alreadUsedModuleNum',
	type : 'string',
	mapping : 'alreadUsedModuleNum'
},{
	name : 'actualUsePower',
	type : 'string',
	mapping : 'actualUsePower'
},{
	name : 'maintenanceMode',
	type : 'string',
	mapping : 'maintenanceMode'
},{
	name : 'maintenanceUnit',
	type : 'string',
	mapping : 'maintenanceUnit'
},{
	name : 'charteredMan',
	type : 'string',
	mapping : 'charteredMan'
},{
	name : 'otherMaintainUnit',
	type : 'string',
	mapping : 'otherMaintainUnit'
},{
	name : 'renewalOverTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'renewalOverTime'
},{
	name : 'maintenanceType',
	type : 'string',
	mapping : 'maintenanceType'
},{
	name : 'purchasedMaintenanceTime',
	type : 'string',
	mapping : 'purchasedMaintenanceTime'
},{
	name : 'projectCode',
	type : 'string',
	mapping : 'projectCode'
},{
	name : 'projectName',
	type : 'string',
	mapping : 'projectName'
},{
	name : 'projectGuaranteeOverTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'projectGuaranteeOverTime'
},{
	name : 'isAuthorizationManagement',
	type : 'string',
	mapping : 'isAuthorizationManagement'
},{
	name : 'authorizationManagementUnit',
	type : 'string',
	mapping : 'authorizationManagementUnit'
},{
	name : 'designPurposes',
	type : 'string',
	mapping : 'designPurposes'
},{
	name : 'oldResourceSystemEquNum',
	type : 'string',
	mapping : 'oldResourceSystemEquNum'
},{
	name : 'oldResourceSystemEquName',
	type : 'string',
	mapping : 'oldResourceSystemEquName'
},{
	name : 'note',
	type : 'string',
	mapping : 'note'
}]);
// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "hfss"
}, hfs);
var store = new Ext.data.Store({// 提供数据输入
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
	
	com.increase.cen.log.aloneHFS = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "aloneHFS",
		border : false,
		cm : cm,// 列定义的model(必需)
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},

		initComponent : function(config) {
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.log.aloneHFS.superclass.initComponent.call(this);
		}
	});
Ext.reg("aloneHFS", com.increase.cen.log.aloneHFS);
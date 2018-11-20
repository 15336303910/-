Ext.onReady(function() {
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 列模型
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
	
	{header : '高频开关电源ID',dataIndex : 'switchingId',hidden : true}, 
	{header : '设备名称',dataIndex : 'deviceName',sortable : true}, 
	{header : '设备别名',dataIndex : 'deviceAlias',sortable : true}, 
	{header : '资产标签号',dataIndex : 'fixedAssetsCode',width : 150,sortable : true}, 
	{header : '所属机房',dataIndex : 'generator',sortable : true},
	{header : '额定电压',dataIndex : 'ratedVoltage',sortable : true},
	{header : '额定电流',dataIndex : 'ratedElectricity',width:150,sortable : true},
	{header : '所属动力系统',dataIndex : 'powerSystem',sortable : true}, 
	{header : '资源生命周期',dataIndex : 'lifecycle',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "设计状态";
			}else if(v=='2'){
				return "工程建设期（入网带业务前）";
			}else if(v=='3'){
				return "工程可用期（已入网带业务）";
			}else if(v=='4'){
				return "工程初验后试运行";
			}else if(v=='5'){
				return "工程终验后在网";
			}else if(v=='6'){
				return "退网状态";
			}
		}	
	},
	{header : '生命周期时间',dataIndex : 'lifecycleTime'},
	{header : '维护状态',dataIndex : 'maintainState',width : 100,sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "正常";
			}else if(v=='2'){
				return "故障";
			}else if(v=='3'){
				return "维护";
			}
		}		
	},
	{header : '切割标记',dataIndex : 'cutMark',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "是";
			}else if(v=='2'){
				return "否";
			}
		}		
	},
	{header : '入网时间',dataIndex : 'inNetTime'},
	{header : '设备厂商',dataIndex : 'deviceMaker',
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "IBM";
			}else if(v=='2'){
				return "JUNIPER";
			}else if(v=='3'){
				return "ORACLE(SUN)";
			}else if(v=='4'){
				return "阿朗";
			}else if(v=='5'){
				return "爱立信";
			}else if(v=='6'){
				return "上海贝尔";
			}else if(v=='7'){
				return "烽火";
			}else if(v=='8'){
				return "华为";
			}else if(v=='9'){
				return "惠普";
			}else if(v=='10'){
				return "诺西";
			}else if(v=='11'){
				return "思科";
			}else if(v=='12'){
				return "中兴";
			}else if(v=='13'){
				return "3COM";
			}else if(v=='14'){
				return "APC";
			}else if(v=='15'){
				return "DB2";
			}else if(v=='16'){
				return "DELL";
			}else if(v=='17'){
				return "D-LINK";
			}else if(v=='18'){
				return "EMC";
			}else if(v=='19'){
				return "GNB";
			}else if(v=='20'){
				return "Microsoft";
			}else if(v=='21'){
				return "NETAPP";
			}else if(v=='22'){
				return "Oracle";
			}else if(v=='23'){
				return "PCCW";
			}else if(v=='24'){
				return "Quantum(昆腾)";
			}else if(v=='25'){
				return "Symantec";
			}else if(v=='26'){
				return "TP-LINK";
			}else if(v=='27'){
				return "艾克赛";
			}else if(v=='28'){
				return "艾默生";
			}else if(v=='29'){
				return "东方国信";
			}else if(v=='30'){
				return "东软";
			}else if(v=='31'){
				return "华日";
			}else if(v=='32'){
				return "佳力图";
			}else if(v=='33'){
				return "力博特";
			}else if(v=='34'){
				return "联想";
			}else if(v=='35'){
				return "梅兰日兰";
			}else if(v=='36'){
				return "神州数码";
			}else if(v=='37'){
				return "双登";
			}else if(v=='38'){
				return "泰岳";
			}else if(v=='39'){
				return "拓维";
			}else if(v=='40'){
				return "西门子";
			}else if(v=='41'){
				return "亚联";
			}else if(v=='42'){
				return "Jeep";
			}else if(v=='43'){
				return "LG";
			}else if(v=='44'){
				return "TCL";
			}else if(v=='45'){
				return "爱普生";
			}else if(v=='46'){
				return "奥林巴斯";
			}else if(v=='47'){
				return "宝马";
			}else if(v=='48'){
				return "奔驰";
			}else if(v=='49'){
				return "本田";
			}else if(v=='50'){
				return "标志";
			}else if(v=='51'){
				return "博世";
			}else if(v=='52'){
				return "大众";
			}else if(v=='53'){
				return "东南";
			}else if(v=='54'){
				return "东芝";
			}else if(v=='55'){
				return "丰田";
			}else if(v=='56'){
				return "福特";
			}else if(v=='57'){
				return "海尔";
			}else if(v=='58'){
				return "海信";
			}else if(v=='59'){
				return "佳能";
			}else if(v=='60'){
				return "金龙";
			}else if(v=='61'){
				return "康佳";
			}else if(v=='62'){
				return "理光";
			}else if(v=='63'){
				return "铃木";
			}else if(v=='64'){
				return "明基";
			}else if(v=='65'){
				return "尼康";
			}else if(v=='66'){
				return "日产";
			}else if(v=='67'){
				return "日立";
			}else if(v=='68'){
				return "三星";
			}else if(v=='69'){
				return "索尼";
			}else if(v=='70'){
				return "通用";
			}else if(v=='71'){
				return "同方";
			}else if(v=='72'){
				return "沃尔沃";
			}else if(v=='73'){
				return "五菱";
			}else if(v=='74'){
				return "夏普";
			}else if(v=='75'){
				return "现代";
			}else if(v=='76'){
				return "长安";
			}else if(v=='77'){
				return "长城";
			}else if(v=='78'){
				return "长虹";
			}else if(v=='79'){
				return "紫光";
			}else if(v=='80'){
				return "小生产厂商";
			}
		}		
	},
	{header : '小生产厂商',dataIndex : 'littleDeviceMaker',sortable : true},
	{header : '规格型号',dataIndex : 'model',sortable : true},
	{header : '是否环境监控',dataIndex : 'isEnvironmentMonitoring',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "是";
			}else if(v=='2'){
				return "否";
			}
		}	
	},
	{header : '动力监控系统名称',dataIndex : 'monitoringSystemName',sortable : true},
	{header : '整流模块型号',dataIndex : 'rectifierModuleModel',sortable : true},
	{header : '监控模块型号',dataIndex : 'supervisoryModuleModel',sortable : true},
	{header : '整流模块数量',dataIndex : 'rectifierModuleNum',sortable : true},
	{header : '已配模块数量',dataIndex : 'SuppliedModuleNum',sortable : true},
	{header : '已用模块数量',dataIndex : 'alreadUsedModuleNum',sortable : true},
	{header : '实际使用功率',dataIndex : 'actualUsePower',sortable : true},
	{header : '维护方式',dataIndex : 'maintenanceMode',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "自维";
			}else if(v=='2'){
				return "代维";
			}
		}	
	},
	{header : '维护单位',dataIndex : 'maintenanceUnit',width : 150,sortable : true},
	{header : '包机人',dataIndex : 'charteredMan',sortable : true},
	{header : '第三方维护单位',dataIndex : 'otherMaintainUnit',sortable : true},
	{header : '续保截止日期',dataIndex : 'renewalOverTime',renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '维保类型',dataIndex : 'maintenanceType',
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "硬件维修";
			}else if(v=='2'){
				return "紧急备件支持";
			}else if(v=='3'){
				return "软件补丁版本支持";
			}else if(v=='4'){
				return "技术支持服务（电话咨询、电话支持、远程支持）";
			}else if(v=='5'){
				return "无";
			}
		}		
	},
	{header : '已购买维保累计年限',dataIndex : 'purchasedMaintenanceTime',sortable : true},
	{header : '工程项目编号',dataIndex : 'projectCode',sortable : true},
	{header : '工程项目名称',dataIndex : 'projectName',sortable : true},
	{header : '工程保修截止日期',dataIndex : 'projectGuaranteeOverTime',renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "是";
			}else if(v=='2'){
				return "否";
			}
		}		
	},
	{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',sortable : true},
	{header : '设计用途',dataIndex : 'designPurposes',sortable : true},
	{header : '原资源系统设备编号',dataIndex : 'oldResourceSystemEquNum',sortable : true},
	{header : '原资源系统设备名称',dataIndex : 'oldResourceSystemEquName',sortable : true},
	
	{header : '备注',dataIndex : 'note',width : 150,sortable : true}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "generator!getHFS.action"
	});
	// Record 定义记录结果
	var hfs= Ext.data.Record.create([
	{name : 'switchingId',type : 'string',mapping : 'switchingId',hidden : true
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
		type : 'string',
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
		type : 'string',
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
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
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
		type : 'string',
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
	
	var HFSGrid = new Ext.grid.GridPanel({
		title : "当前位置：电源管理",
		region : 'center',
		id : "HFSGrid",
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
		buttonAlign : "center",// 按钮居中
		tbar : [{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler : function() {
				help("pages/help/HFS.jsp");
			}
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}],
		bbar : new Ext.PagingToolbar({// 分页工具栏
			store : store,
			pageSize : pageSize,
			beforePageText : "当前第",
			afterPageText : "页，共{0}页",
			lastText : "尾页",
			nextText : "下一页",
			prevText : "上一页",
			firstText : "首页",
			refreshText : "刷新页面",
			displayInfo : true,// 是否显示displayMsg
			displayMsg : "显示第{0}-{1}条 ，共{2}条",
			emptyMsg : "没有记录"
		})
	});
	
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : limit
		}
	});

	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [HFSGrid]
	});
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("HFSGrid").getStore().load({
			params : {
   					start : 0,
					limit : limit
   				}
		});
});
	
	HFSGrid.on('rowdblclick', function(g, r, e) {
	e.stopEvent();
	var HFSGrid=Ext.getCmp("HFSGrid");
	var selModel = HFSGrid.getSelectionModel();
	if (selModel.hasSelection()) {
		var records = selModel.getSelections();
		HFSGrid.seeHFSWindow = new com.increase.cen.generator.GeneratorWindow({
				id : "seeHFSWindow",
				width:650,
				height:300,
				title : '查看电源信息',
				iconCls : 'i-script-window',
				items : [new com.increase.cen.generator.seeHFS({
					id : "seeHFS",
					windowId: "seeHFSWindow"
				})]
			});
		HFSGrid.seeHFSWindow.show();
		Ext.getCmp("deviceName").setValue(records[0].json.deviceName);
		Ext.getCmp("fixedAssetsCode").setValue(records[0].json.fixedAssetsCode);
		Ext.getCmp("generator").setValue(records[0].json.generator);
		Ext.getCmp("ratedVoltage").setValue(records[0].json.ratedVoltage);
		Ext.getCmp("ratedElectricity").setValue(records[0].json.ratedElectricity);
		
		var a=lifecycle(records[0].json.lifecycle);
		Ext.getCmp("lifecycle").setValue(a);
		
		var b=maintainState(records[0].json.maintainState);
		Ext.getCmp("maintainState").setValue(b);
		Ext.getCmp("inNetTime").setValue(records[0].json.inNetTime);
		
		var c=deviceMaker(records[0].json.deviceMaker);
		Ext.getCmp("deviceMaker").setValue(c);
		
		var d=maintenanceMode(records[0].json.maintenanceMode);
		Ext.getCmp("maintenanceMode").setValue(d);
		Ext.getCmp("maintenanceUnit").setValue(records[0].json.maintenanceUnit);
		Ext.getCmp("note").setValue(records[0].json.note);
	}
});
	HFSGrid.NoWindow = new com.increase.cen.generator.NoWindow({
		width:180,
		html:"此功能即将上线，敬请期待！"
		
	});
	HFSGrid.NoWindow.show();
});
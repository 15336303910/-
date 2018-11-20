Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
//		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, /*{
		header : "序号",
		width : 40,
		hideable : false,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			return start + rowIndex + 1;
		}
	}, */
	{header : '分线盒ID',dataIndex : 'fiberBoxId',hidden : true}, 
	{header : '分纤盒名称',dataIndex : 'fiberBoxName',width : 220,sortable : true}, 
	{header : '分线盒序号',dataIndex : 'fiberBoxNo',width : 180,sortable : true}, 
	{header : '分线盒名称综合',dataIndex : 'fiberBoxNameSub',width : 100,sortable : true}, 
	{header : '所属维护区域',dataIndex : 'maintenanceAreaName',width : 180,sortable : true}, 
	{header : '所属机房/设备放置点',dataIndex : 'generatorName',width : 180,sortable : true},
	{header : '所属机房ID',dataIndex : 'generatorId',width : 180,sortable : true},
	{header : '位置',dataIndex : 'address',width : 180,sortable : true},
	{header : '经度',dataIndex : 'longitude',width : 180,sortable : true},
	{header : '纬度',dataIndex : 'latitude',width : 180,sortable : true},
	{header : '类型 ',dataIndex : 'type',width : 180,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "光终端盒";
			}else if(v=='2'){
				return "光分纤盒";
			}
		}
	},
	{header : '安装方式',dataIndex : 'wayToInstall',width : 180,sortable : true},
	{header : '设备厂商',dataIndex : 'deviceMaker',width : 180,sortable : true},
	{header : '小生产厂商',dataIndex : 'littleDeviceMaker',width : 180,sortable : true},
	{header : '规格型号',dataIndex : 'model',width : 180,sortable : true},
	{header : '维护方式',dataIndex : 'maintenanceMode',width : 180,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "自维";
			}else if(v=='2'){
				return "代维";
			}
		}
	},
	{header : '维护单位',dataIndex : 'maintenanceUnit',width : 180,sortable : true},
	{header : '包机人',dataIndex : 'charteredMan',width : 180,sortable : true},
	{header : '第三方维护单位',dataIndex : 'otherMaintainUnit',width : 180,sortable : true},
	{header : '资产标签号',dataIndex : 'fixedAssetsCode',width : 180,sortable : true},
	{header : '距地高度',dataIndex : 'hight',width : 180,sortable : true},
	{header : '平面直角X坐标',dataIndex : 'x',width : 180,sortable : true},
	{header : '平面直角Y坐标',dataIndex : 'y',width : 180,sortable : true},
	{header : '端子总数',dataIndex : 'pointSize',width : 180,sortable : true},
	{header : '占用端子数',dataIndex : 'zPointSize',width : 180,sortable : true},
	{header : '空闲端子数',dataIndex : 'kPointSize',width : 180,sortable : true},
	{header : '损坏端子总数',dataIndex : 'sPointSize',width : 180,sortable : true},
	{header : '入网时间',dataIndex : 'inNetTime',width : 180,sortable : true},
	{header : '维护状态',dataIndex : 'maintainState',width : 180,sortable : true},
	{header : '割接标记',dataIndex : 'cutMark',width : 180,sortable : true},
	{header : '设备序列号',dataIndex : 'isn',width : 180,sortable : true},
	{header : '续保截止日期',dataIndex : 'renewalOverTime',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '维保类型',dataIndex : 'maintenanceType',width : 180,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "硬件维修";
			}else if(v=='2'){
				return "紧急备件支持";
			}else if(v=='3'){
				return "软件补丁版本支持";
			}else if(v=='4'){
				return "技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）";
			}else if(v=='5'){
				return "无";
			}
		}
	},
	{header : '已购买维保累计年限',dataIndex : 'purchasedMaintenanceTime',width : 180,sortable : true},
	{header : '工程项目编号',dataIndex : 'projectCode',width : 180,sortable : true},
	{header : '工程项目名称',dataIndex : 'projectName',width : 180,sortable : true},
	{header : '工程保修截止日期',dataIndex : 'projectGuaranteeOverTime',width : 180,sortable : true},
	{header : '资源生命周期',dataIndex : 'lifecycle',width : 180,sortable : true,
		renderer:function(v){
			if(v=='1'){
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
	{header : '生命周期时间',dataIndex : 'lifecycleTime',width : 180,sortable : true},
	{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',width : 180,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "是";
			}else if(v=='2'){
				return "否";
			}
		}
	},
	{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',width : 180,sortable : true},
	{header : '设计用途',dataIndex : 'designPurposes',width : 180,sortable : true},
	{header : '备注',dataIndex : 'note',width : 180,sortable : true}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "route!getfiberlist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'fiberBoxId',type : 'string',mapping : 'fiberBoxId',hidden : true
	}, {
		name : 'fiberBoxName',
		type : 'string',
		mapping : 'fiberBoxName'
	}, {
		name : 'fiberBoxNo',
		type : 'string',
		mapping : 'fiberBoxNo'
	}, {
		name : 'fiberBoxNameSub',
		type : 'string',
		mapping : 'fiberBoxNameSub'
	}, {
		name : 'maintenanceAreaName',
		type : 'string',
		mapping : 'maintenanceAreaName'
	}, {
		name : 'generatorName',
		type : 'string',
		mapping : 'generatorName'
	},{
		name : 'generatorId',
		type : 'string',
		mapping : 'generatorId'
	},{
		name : 'address',
		type : 'string',
		mapping : 'address'
	},{
		name : 'longitude',
		type : 'string',
		mapping : 'longitude'
	},{
		name : 'latitude',
		type : 'string',
		mapping : 'latitude'
	},{
		name : 'type',
		type : 'string',
		mapping : 'type'
	},{
		name : 'wayToInstall',
		type : 'string',
		mapping : 'wayToInstall'
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
		name : 'fixedAssetsCode',
		type : 'string',
		mapping : 'fixedAssetsCode'
	},{
		name : 'hight',
		type : 'string',
		mapping : 'hight'
	},{
		name : 'x',
		type : 'string',
		mapping : 'x'
	},{
		name : 'y',
		type : 'string',
		mapping : 'y'
	},{
		name : 'pointSize',
		type : 'string',
		mapping : 'pointSize'
	},{
		name : 'zPointSize',
		type : 'string',
		mapping : 'zPointSize'
	},{
		name : 'kPointSize',
		type : 'string',
		mapping : 'kPointSize'
	},{
		name : 'sPointSize',
		type : 'string',
		mapping : 'sPointSize'
	},{
		name : 'inNetTime',
		type : 'string',
		mapping : 'inNetTime'
	},{
		name : 'maintainState',
		type : 'string',
		mapping : 'maintainState'
	},{
		name : 'cutMark',
		type : 'string',
		mapping : 'cutMark'
	},{
		name : 'isn',
		type : 'string',
		mapping : 'isn'
	},{
		name : 'renewalOverTime',
		mapping : 'renewalOverTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
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
		mapping : 'projectGuaranteeOverTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'lifecycle',
		type : 'string',
		mapping : 'lifecycle'
	},{
		name : 'lifecycleTime',
		type : 'string',
		mapping : 'lifecycleTime'
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
		name : 'note',
		type : 'string',
		mapping : 'note'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "fibers"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
		/*sortInfo: {  
        	field: 'jumpfiberId',  
        	direction: "DESC"  
    	}*/
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：分线盒管理",
		region : 'center',
		id : "fiberGrid",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : sm,// 作为grid的选择模型(selection model)
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
			id : 'refreshfiberBtn',
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
		items : [grid]
	});


	
	
	
	// 获得刷新按钮
	var refreshfiberBtn = Ext.getCmp("refreshfiberBtn");
	refreshfiberBtn.on('click', function() {
		grid.reconfigure(store,cm);//重新配置Grid的Store和ColumnModel（列模型）
		store.load();//数据加载
	});

	
	

	
});
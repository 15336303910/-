Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
//		singleSelect : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
	{header : "序号",width : 50,hideable : false,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			return start + rowIndex + 1;
		}
	}, 
	
	{header : '吊线ID',dataIndex : 'suspensionWireId',width :50,hidden : true},
    {header : '吊线名称',dataIndex : 'suspensionWireName',width :150}, 
    {header : '吊线编号',dataIndex : 'suspensionWireCode',width : 100}, 
    {header : '所属维护区域',dataIndex : 'region',width : 100},
    {header : '所属杆路',dataIndex : 'poleLineName',width : 100}, 
    {header : '长度',dataIndex : 'length',width : 100}, 
    {header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',width : 100,
    	renderer:function(v){
			if(v=="1"){
				return "设计状态";
			}else if(v=="2"){
				return "工程建设期";
			}else if(v=="3"){
				return "工程可用期";
			}else if(v=="4"){
				return "工程初验后试运行";
			}else if(v=="5"){
				return "工程终验后在网";
			}else if(v=="6"){
				return "退网状态";
			}
		}
    }, 
    {header : '生命周期时间',dataIndex : 'lifecycleTime',width : 150,
    	renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
    },
    {header : '是否授权管理',dataIndex : 'isAuthorizationManagement',width : 150,
    	renderer:function(F){
			if(F==1){
				return "是";
			}else if(F==2){
				return "否";
			}
		}
    },
    {header : '授权管理单位',dataIndex : 'authorizationManagementUnit',width : 150},
    {header : '设计用途',dataIndex : 'designPurposes',width : 150},
    {header : '备注',dataIndex : 'note',width : 150}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!getSuspensionlist.action"
	});
	// Record 定义记录结果
	var suspension= Ext.data.Record.create([
	{name : 'suspensionWireId',type : 'string',mapping : 'suspensionWireId'},
	{name : 'suspensionWireName',type : 'string',mapping : 'suspensionWireName'},
	{name : 'suspensionWireCode',type : 'string',mapping : 'suspensionWireCode'},
	{name : 'region',type : 'string',mapping : 'region'},
	{name : 'poleLineName',type : 'string',mapping : 'poleLineName'},
	{name : 'length',type : 'string',mapping : 'length'},
	{name : 'resourceLifePeriodEnumId',type : 'string',mapping : 'resourceLifePeriodEnumId'

	},{
	name : 'lifecycleTime',type : Ext.data.Types.DATE,dateFormat : 'Y-m-d\\TH:i:s',mapping : 'lifecycleTime'
	},{	
		
	name : 'isAuthorizationManagement',type : 'string',mapping : 'isAuthorizationManagement'},
	{name : 'authorizationManagementUnit',type : 'string',mapping : 'authorizationManagementUnit'},
	{name : 'designPurposes',type : 'string',mapping : 'designPurposes'},
	{name : 'note',type : 'string',mapping : 'note'}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "suspensions"
	}, suspension);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：吊线管理",
		region : 'center',
		id : "suspensionGrid",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : sm,
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
		bodyCls:'.ext-body',
		buttonAlign : "center",// 按钮居中
		tbar : [
		{
			xtype : 'tbseparator'
		},{
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'importBtn',
			text : '上传',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{	
			id : 'exportBtn',
			text : '下载',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{	
			xtype : 'tbseparator'
		},{
			id : 'importDXDBtn',
			text : '上传吊线段',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{		
			xtype : 'tbseparator'	
		},{
			id : 'exportDXDBtn',
			text : '下载吊线段',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'			
		},{
			id : 'seeDXDBtn',
			text : '查看吊线段',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"	
		},{
			xtype : 'tbseparator'		
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
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("stationGrid").getStore().load({
			params : {
   					start : 0,
					limit : limit
   				}
		//Ext.getCmp("proGrid").getStore（）.reload();
		});
});
	grid.on('rowdblclick', function(g, r, e) {
		e.stopEvent();
		var suspension=g.getStore().getAt(r).json;
		this.suspensionsegGridWindow=new com.increase.cen.poleline.PolelineWindow({
			id:"suspensionsegGridWindow",
			width : 750,
			items : [{
				xtype:"suspensionsegGrid",
				suspensionWireId:suspension.suspensionWireId
			}]
		});
		this.suspensionsegGridWindow.show();
	});
	
	//获得上传按钮
	var importBtn = Ext.getCmp("importBtn");
	importBtn.on('click', function(){
		grid.importSuspensionWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "importSuspensionWindow",
			height:500,
			width:600,
			title : '上传',
			iconCls : 'i-script-window',
			items : [{
				id:'importsuspension',
				fileUpload : true,
				height:300,
				xtype : 'importsuspension'// 窗口里的表单
			}]
			
		});
		grid.importSuspensionWindow.show("importBtn");
	});
	//获得上传吊线段按钮
	var importDXDBtn = Ext.getCmp("importDXDBtn");
	importDXDBtn.on('click', function(){
		grid.importSuspensionsegWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "importSuspensionsegWindow",
			height:500,
			width:600,
			title : '上传',
			iconCls : 'i-script-window',
			items : [{
				id:'importsuspensionseg',
				fileUpload : true,
				height:300,
				xtype : 'importsuspensionseg'// 窗口里的表单
			}]
			
		});
		grid.importSuspensionsegWindow.show("importDXDBtn");
	});
	//获得下载按钮
	var exportBtn=Ext.getCmp("exportBtn");
	exportBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("suspensionWireId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getsuspensionlist.action?ids="+ids;
			
		}else{
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请您至少选择一条数据进行操作!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	//获得下载吊线段按钮
	var exportDXDBtn=Ext.getCmp("exportDXDBtn");
	exportDXDBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("suspensionWireId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getsuspensionseglist.action?ids="+ids;
			
		}else{
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请您至少选择一条数据进行操作!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	//
	
    
});
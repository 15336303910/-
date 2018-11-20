Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
//		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, 
	{header : 'ID',dataIndex : 'ledupPointId',hidden : true}, 
	{header : '名称',dataIndex : 'ledupPointName',width : 250}, 
	{header : '编号',dataIndex : 'ledupPointCode',width : 150}, 
	{header : '数目',dataIndex : 'tubeQuantity',width : 100}, 
	{header : '已用数目',dataIndex : 'occupiedTubeQuantity',width : 100}, 
	{header : '预留数目',dataIndex : 'reservedTubeQuantity',width : 100}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!getLedup.action"
	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'ledupPointId',type : 'string',mapping : 'ledupPointId',hidden : true
	}, {
		name : 'ledupPointName',
		type : 'string',
		mapping : 'ledupPointName'
	},{
		name : 'ledupPointCode',
		type : 'string',
		mapping : 'ledupPointCode'
	},{
		name : 'tubeQuantity',
		type : 'string',
		mapping : 'tubeQuantity'
	}, {
		name : 'occupiedTubeQuantity',
		type : 'string',
		mapping : 'occupiedTubeQuantity'
	}, {
		name : 'reservedTubeQuantity',
		type : 'string',
		mapping : 'reservedTubeQuantity'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "ledups"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：引上点管理",
		region : 'center',
		id : "LedupGrid",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : sm,// 作为grid的选择模型(selection model)
		store : store,
		autoScroll : true,// 滚动条
		buttonAlign : "center",// 按钮居中
		tbar : [{
			xtype : 'tbseparator'// 一个分隔栏
			},{
				id : 'exportLedupBtn',
				text : '导出数据',
				icon : "imgs/fiber/link_break.png",
				cls : "x-btn-text-icon"
			},{
				xtype : 'tbseparator'
			},{
				id : 'importLedupBtn',
				text : '导入数据',
				icon : "imgs/fiber/link_break.png",
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
	//tengxy
	var exportLedupBtn=Ext.getCmp("exportLedupBtn");
	exportLedupBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("ledupPointId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getLeduplist.action?ids="+ids;
			
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
	
	//tengxy
	var importLedupBtn = Ext.getCmp("importLedupBtn");
	importLedupBtn.on('click', function(){
			grid.importLedupWindow = new com.increase.cen.pipe.pipeWindow({
				id : "importLedupWindow",
				height:500,
				width:600,
				title : '数据导入',
				iconCls : 'i-script-window',
				items : [{
					id:'importLedup',
					fileUpload : true,
					height:300,
					xtype : 'importLedup'// 窗口里的表单
				}]
			});
		
		grid.importLedupWindow.show();
	});
	
	
})
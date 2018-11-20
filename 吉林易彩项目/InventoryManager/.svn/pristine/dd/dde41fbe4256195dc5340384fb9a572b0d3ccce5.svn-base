Ext.onReady(function(){
	Ext.useShims=true;
	// 列模型
	var cm = new Ext.grid.ColumnModel([{
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
	}, 
	{header : '光缆名称',dataIndex : 'routename',width : 120, sortable : true}, 
	{header : '光缆段名称',dataIndex : 'cablename',width : 120,sortable : true}, 
	{header : '本端设备名称',dataIndex : 'ename1',width : 80,sortable : true}, 
	{header : '本端设备编码',dataIndex : 'ecode1',width : 80,sortable : true}, 
	{header : '本端端子ID',dataIndex : 'pid1',width : 80,sortable : true}, 
	{header : '本端端子编码',dataIndex : 'pcode1',width : 80,sortable : true}, 
	{header : '端子状态',dataIndex : 'pstat',width : 80,sortable : true}, 
	{header : '光路编码',dataIndex : 'ofpcode',width : 80},
	{header : '承载业务',dataIndex : 'pserv',width : 180},
	{header : '对端设备名称',dataIndex : 'ename2',width : 80,sortable : true}, 
	{header : '对端设备编码',dataIndex : 'ecode2',width : 80,sortable : true}, 
	{header : '对端端子ID',dataIndex : 'pid2',width : 80,sortable : true}, 
	{header : '对端端子编码',dataIndex : 'pcode2',width : 80,sortable : true}, 
	]);
	
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "route!searchOppoFiber.action"
		
	});
	
	// Record 定义记录结果
	var oppofiber = Ext.data.Record.create([
		{name : 'routename',type : 'string',mapping : 'routename',hidden : true}, 
		{name : 'cablename',type : 'string',mapping : 'cablename'}, 
		{name : 'ename1',type : 'string',mapping : 'ename1'}, 
		{name : 'ecode1',type : 'string',mapping : 'ecode1'}, 
		{name : 'pid1',type : 'string',mapping : 'pid1'}, 
		{name : 'pcode1',type : 'string',mapping : 'pcode1'}, 
		{name : 'ofpcode',type : 'string',mapping : 'ofpcode'},
		{name : 'pserv',type : 'string',mapping : 'pserv'},
		{name : 'ename2',type : 'string',mapping : 'ename2'},
		{name : 'ecode2',type : 'string',mapping : 'ecode2'},
		{name : 'pid2',type : 'string',mapping : 'pid2'},
		{name : 'pcode2',type : 'string',mapping : 'pcode2'}
	]);
	
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		root : "oppoFibers"
	}, oppofiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取光缆和光缆段名称
	var routename = Ext.getDom("routename").value;
	var cablename = Ext.getDom("cablename").value;
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：光缆纤芯列表",
		region : 'center',
		id : "oppoFiberGrid",
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
		addUserWindow : null,// 添加用户窗口
		modifyUserWindow : null,// 修改用户窗口
		queryUserWindow : null,// 查询用户窗口
		buttonAlign : "center",// 按钮居中
		tbar : [{
			xtype : 'tbseparator'// 一个分隔栏
		},  {
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}]
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
			'searchOppoFiber.routename' : routename,
			'searchOppoFiber.cablename' :cablename
		}
	});
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	// 获得刷新按钮，刷新全部用户信息
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		grid.getStore().load({
			params : {
				'searchOppoFiber.routename' : routename,
				'searchOppoFiber.cablename' :cablename
			}
		});
		grid.getView().refresh();
	})
});
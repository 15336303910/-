Ext.namespace("com.increase.cen.log");
var pageSize = 10;
var limit = 10;


// 列模型
	var cm = new Ext.grid.ColumnModel([ {
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
	{header : '操作人',dataIndex : 'username'}, 
	{header : '操作类型',dataIndex : 'type',width : 350}, 
	{header : '操作时间',dataIndex : 'eventDate',width : 180,renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')}, 
	{header : '操作模块',dataIndex : 'method',width : 100}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!betweenEvent.action"

	});
	// Record 定义记录结果
	var event = Ext.data.Record.create([
		{name : 'username',type : 'string',mapping : 'username'},
		{name : 'type',type : 'string',mapping : 'type'},
		{name : 'eventDate',type : Ext.data.Types.DATE,dateFormat : 'Y-m-d\\TH:i:s',mapping : 'eventDate'},
		{name : 'method',type : 'string',mapping : 'method'}
		]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "events"
	}, event);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	com.increase.cen.log.seeEvent = Ext.extend(Ext.grid.GridPanel, {
		id:'eventGrid',
		cm : cm,// 列定义的model(必需)
		store : store,
		autoDestroy:true,
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
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
		}),

		initComponent : function(config) {
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.log.seeEvent.superclass.initComponent.call(this);
		}
	});
Ext.reg("seeEvent", com.increase.cen.log.seeEvent);
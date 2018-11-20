Ext.namespace("com.increase.cen.equt");
var pageSize = 12;

// 列模型
	var cm = new Ext.grid.ColumnModel([ {
		header : "序号",
		width : 60,
		hideable : false,
		hidden : true,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			rowI=start + rowIndex + 1;
			return start + rowIndex + 1;
		}
	},{
		header : 'pid',
		dataIndex : 'pid',
		width : 70,
		hidden:true
	}, {
		header : '端子编码',
		dataIndex : 'pcode',
		width : 130
	}, {header : '端子类型',dataIndex : 'ptype',width : 70,renderer : function(v) {return getPointTypeStr(v);}}, 
	   {header : '端子状态',dataIndex : 'pstat',width : 70,renderer : function(v) {return getPstatStr(v);}}
	, {
		header : '承载业务',
		dataIndex : 'pserv',
		width : 180
	}, {
		header : '光纤名称',
		dataIndex : 'fibname',
		width : 180
	}, {
		header : '光路编码',
		dataIndex : 'ofpcode',
		width : 180
	}]);
	
	var proxy = new Ext.data.HttpProxy({
		url : "equt!showEqutPoint.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
		name : 'pid',
		type : 'string',
		mapping : 'pid'
	}, {
		name : 'pcode',
		type : 'string',
		mapping : 'pcode'
	}, {
		name : 'pstat',
		type : 'string',
		mapping : 'pstat'
	}, {
		name : 'ptype',
		type : 'string',
		mapping : 'ptype'
	}, {
		name : 'pserv',
		type : 'string',
		mapping : 'pserv'
	}, {
		name : 'fibname',
		type : 'string',
		mapping : 'fibname'
	}, {
		name : 'ofpcode',
		type : 'string',
		mapping : 'ofpcode'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "points"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	com.increase.cen.equt.showPointGrid = Ext.extend(Ext.grid.GridPanel, {
		id:'showPointGrid',
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
			limit : limit,
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
			com.increase.cen.equt.showPointGrid.superclass.initComponent.call(this);
		}
	});
	
Ext.reg("showPointGrid", com.increase.cen.equt.showPointGrid);
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
	{header : '设备名称'}, 
	{header : '设备别名'},
	{header : '资产标签号'},
	{header : '所属网络'},
	{header : '所属网络层次'},
	{header : '所属机房/设备放置点'},
	{header : '资产归属'},
	{header : '维护状态'},
	{header : '是否重保'},
	{header : '割接标记'},
	{header : '设备网管标识'},
	{header : '设备厂家'},
	{header : '小生产厂商'},
	{header : '设备型号'},
	{header : '设备类型'},
	{header : '设备序列号'},
	{header : '维护方式'},
	{header : '维护单位'},
	{header : '包机人'},
	{header : '第三方维护单位'},
	{header : '续保截止日期'},
	{header : '维保类型'},
	{header : '已购买维保累计年限'},
	{header : '工程项目编号'},
	{header : '工程项目名称'},
	{header : '工程保修截止日期'},
	{header : '是否授权管理'},
	{header : '授权管理单位'},
	{header : '设计用途'},
	{header : '原资源系统设备编号'},
	{header : '原资源系统设备名称'},
	{header : '备注'}
	]);

	var store = new Ext.data.Store({});// 提供数据输入
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：数据网络资源",
		region : 'center',
		id : "stationGrid",
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
//				help("pages/help/station.jsp");
			}
		},
		{
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
	


	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
});
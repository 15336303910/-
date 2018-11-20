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
	{header : '社区名称'}, 
	{header : '标准地址'},
	{header : '社区类型'},
	{header : '楼宇数量'},
	{header : '住宅套数'},
	{header : '入住家庭数'},
	{header : '接入方式'},
	{header : '在网用户数'},
	{header : 'FTTH端口'},
	{header : 'PON+LAN端口'},
	{header : 'PON+DSL端口'},
	{header : '传统LAN端口'},
	{header : '光纤到户数'},
	{header : '光纤已入户数'},
	{header : 'PON+LAN到户数'},
	{header : 'PON+DSL到户数'},
	{header : '传统LAN到户数'},
	{header : 'FTTH用户数'},
	{header : 'PON+LAN用户数'},
	{header : 'DSL用户数'},
	{header : 'LAN用户数'},
	{header : '最大可提供带宽'},
	{header : '是否具备固话开通能力'},
	{header : '固话局向信息'},
	{header : 'FTTH固话局向信息'},
	{header : 'PSTN固话局向信息'},
	{header : '接入类型'},
	{header : '接入端口数'},
	{header : '接入线路到户数'},
	{header : '其他运营商'},
	{header : '其他运营商接入方式'},
	{header : '二级运营商'},
	{header : '二级运营商接入方式'}
	]);

	var store = new Ext.data.Store({});// 提供数据输入
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：现网社区资源",
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
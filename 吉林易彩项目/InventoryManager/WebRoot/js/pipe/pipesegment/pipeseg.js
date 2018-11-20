Ext.onReady(function() {
var limit=10;
	var cm = new Ext.grid.ColumnModel([
		{header : '管道段ID',dataIndex : 'pipeSegmentId',hidden : true}, 
		{header : '管道段名称',dataIndex : 'pipeSegmentName',width : 300,sortable : true},
		{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
		{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!searchPipeSegmentlist.action"
	});
	//数据结构
	var route = Ext.data.Record.create([
		{name : 'pipeSegmentId',type : 'string',mapping : 'pipeSegmentId',hidden : true}, 
		{name : 'pipeSegmentName',type : 'string',mapping : 'pipeSegmentName'},
		{name : 'startDeviceName',type : 'string',mapping : 'startDeviceName'},
		{name : 'endDeviceName',type : 'string',mapping : 'endDeviceName'}
	]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "pipesegments"
	}, route);
	
	 var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	psgrid = new Ext.grid.GridPanel({
		title : "选择管道段",
		region : 'center',
		id : "pipeSegGrid",
		border : false,
		hidden:true,
		height:320,
		cm : cm,// 列定义的model(必需)
		store : store,
		autoScroll : true,// 滚动条
		buttonAlign : "center",// 按钮居中
		tbar : [{
			xtype : 'label',
			text : '管道段名称：'
		},{
			xtype : 'textfield',
			id:'pipeSegName',
			cls:'sel-textfield-width'
		},{
			xtype : 'tbseparator'
		},{
			text : '查询',
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon",
			handler:function(){
				var grid = Ext.getCmp("pipeSegGrid");
				var pipesname = Ext.getCmp("pipeSegName").getValue();
				grid.getStore().load({
					params : {
						start : 0,
						limit : 10,
						'pipeSegmentInfoBean.pipeSegName' : pipesname
					}
				});
				grid.getStore().on('beforeload',function(){
		   			grid.getStore().baseParams = {
		   				'pipeSegmentInfoBean.pipeSegmentName' : pipesname
		   			};
				});
				grid.getView().refresh();
			}
		},{
			xtype : 'tbseparator'
		},{
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon",
			handler:function(){
				var grid = Ext.getCmp("pipeSegGrid");
				Ext.getCmp("pipeSegName").setValue("");
				grid.getStore().on('beforeload',function(){
		   				grid.getStore().baseParams = {
		   					start : 0,
							limit : 10
		   				};
				});
				grid.getStore().load({
					params : {
						start : 0,
						limit : limit
					}
				});
				grid.getView().refresh();
			}
		}],
		bbar : new Ext.PagingToolbar({// 分页工具栏
			store : store,
			pageSize : 10,
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
			limit : 10
		}
	});

})
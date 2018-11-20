var rowI=0;
Ext.onReady(function() {
	Ext.useShims = false;
	
	// 列模型
	var cm = new Ext.grid.ColumnModel([ {
		header : "序号",
		width : 50,
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
		header : '光缆段名称',
		dataIndex : 'cablename',
		width : 200
	}, {
		header : '起始设备',
		dataIndex : 'startDeviceName',
		width : 150
	}, {
		header : '终止设备',
		dataIndex : 'endDeviceName',
		width : 150
	},  {
		header : '纤芯总数',
		dataIndex : 'fibercount',
		width : 100
	}, {
		header : '实占纤芯数',
		dataIndex : 'inusecount',
		width : 100
	}, {
		header : '故障纤芯数',
		dataIndex : 'faultcount',
		width : 100
	}, {
		header : '空闲纤芯数',
		dataIndex : 'freecount',
		width : 100
	}, {
		header : '利用率',
		dataIndex : 'inuesPercent',
		width : 100
		
	}, {
		header : '可用率',
		dataIndex : 'freePercent',
		width : 100
	}, {
		header : '故障率',
		dataIndex : 'faultPercent',
		width : 100
	}]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "statistics!CableStat.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
		name : 'cablename',
		type : 'string',
		mapping : 'cablename'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	}, {
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	}, {
		name : 'fibercount',
		type : 'Integer',
		mapping : 'fibercount'
	}, {
		name : 'inusecount',
		type : 'Integer',
		mapping : 'inusecount'
	}, {
		name : 'faultcount',
		type : 'Integer',
		mapping : 'faultcount'
	}, {
		name : 'freecount',
		type : 'Integer',
		mapping : 'freecount'
	}, {
		name : 'inuesPercent',
		type : 'string',
		mapping : 'inuesPercent'
	},  {
		name : 'freePercent',
		type : 'string',
		mapping : 'freePercent'
	}, {
		name : 'faultPercent',
		type : 'string',
		mapping : 'faultPercent'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "cables"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：线缆资源利用率",
		region : 'center',
		id : "grid",
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
		tbar :[	'->',{
						xtype:'label',
						text:'光缆段名称：'
					},{
						xtype:'textfield',
						cls :'order-sel-textfield-width',
						id:'cablename'
					},{
						xtype:'label',
						text:'起始设备：'
					},{
						xtype:'textfield',
						cls :'order-sel-textfield-width',
						id:'startDeviceName'
					},{
						xtype:'label',
						text:'终止设备：'
					},{
						xtype:'textfield',
						cls :'order-sel-textfield-width',
						id:'endDeviceName'
					},{
						xtype:'label',
						text:'地区：'
					},{
						xtype : "treecombo",
						id : "ename_sel",
						width:125,
						listWidth:200,
						anchor : "100%",
						hiddenName : "eid", 
						url : "statistics!loadEqutInspecTree.action",
						setHiddenValue : function(node, dispText) {	
								this.setValue(node.attributes.domainCode);
								Ext.form.ComboBox.superclass.setValue.call(this, node.text);
								this.hiddenValue = node.attributes.domainCode;
						}
					},{
						id : 'seekBtn',
						text : '搜索',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					},{
						id:'resetBtn',
						text : '重置',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					},{
						id : 'refreshBtn',
						text : '导出',
						icon : "imgs/all_refresh.png",
						cls : "x-btn-text-icon"
					}
				],
		
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
	// 解决分页点击下一页时不带参数问题
	store.on('beforeload', function() {
		store.baseParams = {
			
		};
	});
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	// var view = Ext.getCmp("view")

	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var cablename = Ext.getCmp("cablename").getValue();
		var startDeviceName = Ext.getCmp("startDeviceName").getValue();
		var endDeviceName = Ext.getCmp("endDeviceName").getValue();
		var code = Ext.getCmp("ename_sel").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'cablename':cablename,
				'startDeviceName':startDeviceName,
				'endDeviceName':endDeviceName,
				'areano':code
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'cablename':cablename,
				'startDeviceName':startDeviceName,
				'endDeviceName':endDeviceName,
				'areano':code
			}
		});
		grid.getView().refresh();		
		/*Ext.getCmp("cablename").setValue("");
		Ext.getCmp("startDeviceName").setValue("");
		Ext.getCmp("endDeviceName").setValue("");
		Ext.getCmp("ename_sel").setValue("");
		Ext.getCmp("ename_sel").hiddenValue="";*/
	});
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		var cablename = Ext.getCmp("cablename").getValue();
		var startDeviceName = Ext.getCmp("startDeviceName").getValue();
		var endDeviceName = Ext.getCmp("endDeviceName").getValue();
		var code = Ext.getCmp("ename_sel").getValue();
		cablename=encodeURI(encodeURI(cablename));
		startDeviceName=encodeURI(encodeURI(startDeviceName));
		endDeviceName=encodeURI(encodeURI(endDeviceName));
		if(rowI%limit==0){
			var start=rowI-limit;
		}else{
			var start=rowI-rowI%limit;
		}
		location.href="statistics!exportcables.action?cablename="+cablename+"&startDeviceName="+startDeviceName+"&endDeviceName="+endDeviceName+"&start="+start+"&limit="+limit+"&areano="+code;
		grid.getView().refresh();		
		/*Ext.getCmp("cablename").setValue("");
		Ext.getCmp("startDeviceName").setValue("");
		Ext.getCmp("endDeviceName").setValue("");
		Ext.getCmp("ename_sel").setValue("");*/
	});
	
	var resetBtn=Ext.getCmp("resetBtn");
	resetBtn.on('click', function() {
		Ext.getCmp("cablename").setValue("");
		Ext.getCmp("startDeviceName").setValue("");
		Ext.getCmp("endDeviceName").setValue("");
		Ext.getCmp("ename_sel").setValue("");
		Ext.getCmp("ename_sel").hiddenValue="";
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				start : 0,
				limit : limit,
			}
		});
	});
});

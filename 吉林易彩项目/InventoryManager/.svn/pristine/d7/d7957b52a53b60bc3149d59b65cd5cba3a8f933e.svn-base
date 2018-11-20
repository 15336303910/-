Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
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
	{header : 'logID',dataIndex : 'generatorName',sortable : true,hidden : true}, 
	{header : '操作时间',dataIndex : 'logTime',
		renderer:function(v){
			v=v.replace("T"," ");
			v=grey(v);
			return v;
		}
	}, 
	{header : '操作人',dataIndex : 'logOperater',renderer:grey},
	{header : '操作类型',dataIndex : 'operationType',renderer:OperationType}, 
	{header : 'id',dataIndex : 'id',hidden:true},
	{
		header : '设备ID',
		dataIndex : 'eid',
		width : 100,
		hidden:true
	},{
		header : '设备名称',
		dataIndex : 'ename',
		width : 100,
		renderer:color1
	}, {
		header : '设备编码',
		dataIndex : 'ecode',
		width : 100,
		renderer:color1
	}, {
		header : '设备地址',
		dataIndex : 'eaddr',
		width : 120,
		renderer:color1
	}, {
		header : '设备类型',
		dataIndex : 'etype',
		width : 100,
		renderer : function(v) {
			return getEtypeStr(v);
		}
	}, {
		header : '设备状态',
		dataIndex : 'estat',
		width : 60,
		renderer : function(v) {
			return getEstatStr(v);
		}
	}, {
		header : '设备型号',
		dataIndex : 'emodel',
		width : 100,
		renderer:color1
	},	{
		header : '维护人',
		dataIndex : 'mp',
		width : 100,
		renderer:color1
	},{
		header : '维护时间',
		dataIndex : 'mtime',
		width : 100,
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : '经度',
		dataIndex : 'lon',
		width : 100,
		renderer:color1
	},{
		header : '纬度',
		dataIndex : 'lat',
		width : 100,
		renderer:color1
	},{
		header : '负责人',
		dataIndex : 'prlusername',
		width : 100,
		renderer:color1
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getEqutLog.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([
    {name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',mapping : 'logTime',type : 'string'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	},{name : 'id',type : 'string',mapping : 'id'
	},{
		name : 'eid',
		type : 'string',
		mapping : 'eid'
	},{
		name : 'ename',
		type : 'string',
		mapping : 'ename'
	}, {
		name : 'ecode',
		type : 'string',
		mapping : 'ecode'
	}, {
		name : 'eaddr',
		type : 'string',
		mapping : 'eaddr'
	}, {
		name : 'etype',
		type : 'string',
		mapping : 'etype'
	}, {
		name : 'estat',
		type : 'string',
		mapping : 'estat'
	}, {
		name : 'emodel',
		type : 'string',
		mapping : 'emodel'
	}, {
		name : 'mp',
		type : 'string',
		mapping : 'mp'
	}, {
		name : 'mtime',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'mtime'
	}, {
		name : 'lon',
		type : 'string',
		mapping : 'lon'
	}, {
		name : 'lat',
		type : 'string',
		mapping : 'lat'
	}, {
		name : 'prlusername',
		type : 'string',
		mapping : 'prlusername'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "equts"
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
		region : 'center',
		id : "occEqutGrid",
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
		buttonAlign : "center",// 按钮居中
		tbar : [{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler : function() {
				help("pages/help/log.jsp");
			}
		},{
			xtype : 'tbseparator'
		},{
			id : 'seeEqut',
			text : '查看日志',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
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
			'equt.etype':'3',
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
		Ext.getCmp("occEqutGrid").getStore().load({
			params : {
				'equt.etype':'3',
   					start : 0,
					limit : limit
   				}
		});
	
});
	var seeEqut=Ext.getCmp("seeEqut");
	seeEqut.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("id"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行操作!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				var occEqutGrid=Ext.getCmp("occEqutGrid");
				if(!occEqutGrid.seeEqutWindow){
					occEqutGrid.seeEqutWindow=new com.increase.cen.log.logWindow({
					id : "seeEqutWindow",
					height : 400,
					width : 1000,
					title : '选择设备',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'aloneOccEqut'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("aloneOccEqut").getStore();
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
						'equt.id' : ids[0],
						start : 0,
						limit : limit
					}
				});
				occEqutGrid.seeEqutWindow.show();
			 }
			}else{
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '您未选择数据，请选择!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
});
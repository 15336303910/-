Ext.onReady(function() {
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
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
	{header : '局站ID',dataIndex : 'stationBaseId',hidden : true}, 
	{header : '局站名称',dataIndex : 'stationName',renderer:color1}, 
	{header : '所属维护区域',dataIndex : 'region',width : 150,renderer:color1},
	{header : '所属行政区域标识',dataIndex : 'regionalism',hidden : true},
	{header : '站点经度',dataIndex : 'lon',renderer:color1},
	{header : '站点纬度',dataIndex : 'lat',renderer:color1},
	{header : '站点地址',dataIndex : 'stationAddr',width:150,renderer:color1},
	{header : '站点类型',dataIndex : 'stationType',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "普通局站";
			}else if(v=='1'){
				return "基站站点";
			}else if(v=='2'){
				return "多机房ITMC";
			}else if(v=='3'){
				return "边境站";
			}
		}
	},
	{header : '站点网络类型',dataIndex : 'stationNetWorkType',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "GSM";
			}else if(v=='1'){
				return "WCDMA";
			}else if(v=='2'){
				return "GSM和WCDMA共用";
			}
		}	
	},
	{header : '局站等级',dataIndex : 'stationLevel',width : 100,sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "A";
			}else if(v=='1'){
				return "B";
			}else if(v=='2'){
				return "C";
			}
		}		
	},
	{header : '站点范围',dataIndex : 'stationRange',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "城市";
			}else if(v=='1'){
				return "乡村";
			}
		}	
	},
	{header : '固移共站',dataIndex : 'sameStation',renderer:color1},
	{header : '固网专用',dataIndex : 'fixedDedicated',renderer:color1},
	{header : '工程项目编号',dataIndex : 'projectCode',renderer:color1},
	{header : '工程项目名称',dataIndex : 'projectName',renderer:color1},
	{header : '工程保修截止日期',dataIndex : 'projectGuaranteeOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "是";
			}else if(v=='1'){
				return "否";
			}
		}
	},
	{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',renderer:color1},
	{header : '创建时间',dataIndex : 'creationDate',width : 150,renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 150,renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '备注',dataIndex : 'note',width : 150,renderer:color1}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getStationLog.action"
	});
	// Record 定义记录结果
	var station= Ext.data.Record.create([
	{name : 'stationBaseId',type : 'string',mapping : 'stationBaseId'
	},{name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',type : 'string',mapping : 'logTime'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	},{
		name : 'stationName',
		type : 'string',
		mapping : 'stationName'
	},{
		name : 'region',
		type : 'string',
		mapping : 'region'
	},{
		name : 'regionalism',
		type : 'string',
		mapping : 'regionalism'
	},{
		name : 'lon',
		type : 'string',
		mapping : 'lon'
	},{
		name : 'lat',
		type : 'string',
		mapping : 'lat'
	},{
		name : 'stationAddr',
		type : 'string',
		mapping : 'stationAddr'
	},{
		name : 'stationType',
		type : 'string',
		mapping : 'stationType'
	},{
		name : 'stationNetWorkType',
		type : 'string',
		mapping : 'stationNetWorkType'
	},{
		name : 'stationLevel',
		type : 'string',
		mapping : 'stationLevel'
	},{
		name : 'stationRange',
		type : 'string',
		mapping : 'stationRange'
	},{
		name : 'sameStation',
		type : 'string',
		mapping : 'sameStation'
	},{
		name : 'fixedDedicated',
		type : 'string',
		mapping : 'fixedDedicated'
	},{
		name : 'projectCode',
		type : 'string',
		mapping : 'projectCode'
	},{
		name : 'projectName',
		type : 'string',
		mapping : 'projectName'
	},{
		name : 'projectGuaranteeOverTime',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'projectGuaranteeOverTime'
	},{
		name : 'isAuthorizationManagement',
		type : 'string',
		mapping : 'isAuthorizationManagement'
	},{
		name : 'authorizationManagementUnit',
		type : 'string',
		mapping : 'authorizationManagementUnit'
	},{
		name : 'creationDate',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'creationDate'
	},{
		name : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'lastUpdateDate'
		
	},{
		name : 'note',
		type : 'string',
		mapping : 'note'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "stations"
	}, station);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		id : "stationGrid",
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
			id : 'seeStation',
			text : '查看资源日志',
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
		});
});
	var seeStation=Ext.getCmp("seeStation");
	seeStation.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("stationBaseId"));// 获得选择的编号
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
				var stationGrid=Ext.getCmp("stationGrid");
				if(!stationGrid.seeStationWindow){
					stationGrid.seeStationWindow=new com.increase.cen.log.logWindow({
					id : "seeStationWindow",
					height : 400,
					width : 1000,
					title : '查看资源日志',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'aloneStation'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("aloneStation").getStore();
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
						'station.stationBaseId' : ids[0],
						start : 0,
						limit : limit
					}
				});
				stationGrid.seeStationWindow.show();
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
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
	{header : '井ID',dataIndex : 'wellId',hidden : true}, 
	{header : '人/手井名称',dataIndex : 'wellNameSub',renderer:color1}, 
	{header : '别名',dataIndex : 'alias',renderer:color1}, 
	{header : '方位',dataIndex : 'direction',renderer:color1}, 
	{header : '序号',dataIndex : 'wellNo',renderer:color1}, 
	{header : '扩充后缀',dataIndex : 'wellSubNo',renderer:color1}, 
	{header : '井类型',dataIndex : 'manholeTypeEnumId',sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "人孔";
			}else if(v=='2'){
				return "手孔";
			}else if(v=='3'){
				return "双人孔";
			}else if(v=='4'){
				return "三人孔";
			}else if(v=='5'){
				return "局前井";
			}else if(v=='6'){
				return "地下室";
			}
		}
	}, 
	
	{header : '人/手井经度',dataIndex : 'longitude',renderer:color1}, 
	{header : '人/手井纬度',dataIndex : 'latitude',renderer:color1},
	{header : '盖井高程',dataIndex : 'coverTopElevation',renderer:color1}, 
	{header : '井坑底高程',dataIndex : 'latitude',renderer:color1},
	{header : '人/手井地址',dataIndex : 'wellAddress',width : 200,renderer:color1}, 
	{header : '井深（米）',dataIndex : 'manholeDepth',renderer:color1},
	{header : '井长（米）',dataIndex : 'manholeLength',renderer:color1},
	{header : '井宽（米）',dataIndex : 'manholeWidth',renderer:color1},
	{header : '井形状',dataIndex : 'manholeShape',renderer:color1},
	{header : '井规格',dataIndex : 'manholeSpecification',renderer:color1},
	{header : '井结构',dataIndex : 'manholeStructure',renderer:color1},
	{header : '道路类型',dataIndex : 'roadTypeEnumId',renderer:color1},
	{header : '路面类型',dataIndex : 'roadSurfaceTypeEnumId',renderer:color1},
	{header : '井盖材质',dataIndex : 'coverMaterialEnumId',renderer:color1},
	{header : '井盖形状',dataIndex : 'coverShapeEnumId',renderer:color1},
	{header : '井盖数量',dataIndex : 'coverQuantity',renderer:color1},
	{header : '井壁厚度',dataIndex : 'wallThickness',renderer:color1},
	{header : '底基厚度',dataIndex : 'subbaseThickness',renderer:color1},
	{header : '维护方式',dataIndex : 'maintenanceModeEnumId',renderer:maintenanceMode},
	{header : '维护单位',dataIndex : 'maintenanceOrgId',renderer:color1},
	{header : '第三方维护单位',dataIndex : 'thirdPartyMaintenanceOrg',renderer:color1},
	{header : '维护类型',dataIndex : 'maintenanceTypeEnumId',renderer:maintenanceType},
	{header : '购买年限',dataIndex : 'purchasedMaintenanceTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',renderer:color1},
	{header : '项目编号',dataIndex : 'projectNumber',renderer:color1},
	{header : '项目名称',dataIndex : 'projectName',renderer:color1},
	{header : '续保截止日期',dataIndex : 'renewalExpirationDate',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '工程保修截止日期',dataIndex : 'projectWarrantyExpireDate',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getWellLog.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'wellId',type : 'string',mapping : 'wellId',hidden : true
	},{name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',type : 'string',mapping : 'logTime'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	}, {
		name : 'wellNameSub',
		type : 'string',
		mapping : 'wellNameSub'
	}, {
		name : 'alias',
		type : 'string',
		mapping : 'alias'
	},{
		name : 'direction',
		type : 'string',
		mapping : 'direction'
	},{
		name : 'wellNo',
		type : 'string',
		mapping : 'wellNo'
	},{
		name : 'wellSubNo',
		type : 'string',
		mapping : 'wellSubNo'
	},{
		name : 'manholeTypeEnumId',
		type : 'string',
		mapping : 'manholeTypeEnumId'
	}, {
		name : 'longitude',
		type : 'string',
		mapping : 'longitude'
	}, {
		name : 'latitude',
		type : 'string',
		mapping : 'latitude'
	}, {
		name : 'coverTopElevation',
		type : 'string',
		mapping : 'coverTopElevation'
	}, {
		name : 'latitude',
		type : 'string',
		mapping : 'latitude'
	}, {
		name : 'wellAddress',
		type : 'string',
		mapping : 'wellAddress'
	}, {
		name : 'manholeDepth',
		type : 'string',
		mapping : 'manholeDepth'
	}, {
		name : 'manholeLength',
		type : 'string',
		mapping : 'manholeLength'
	}, {
		name : 'manholeWidth',
		type : 'string',
		mapping : 'manholeWidth'
	}, {
		name : 'manholeShape',
		type : 'string',
		mapping : 'manholeShape'
	}, {
		name : 'manholeSpecification',
		type : 'string',
		mapping : 'manholeSpecification'
	}, {
		name : 'manholeStructure',
		type : 'string',
		mapping : 'manholeStructure'
	}, {
		name : 'roadTypeEnumId',
		type : 'string',
		mapping : 'roadTypeEnumId'
	}, {
		name : 'roadSurfaceTypeEnumId',
		type : 'string',
		mapping : 'roadSurfaceTypeEnumId'
	}, {
		name : 'coverMaterialEnumId',
		type : 'string',
		mapping : 'coverMaterialEnumId'
	}, {
		name : 'coverShapeEnumId',
		type : 'string',
		mapping : 'coverShapeEnumId'
	}, {
		name : 'coverQuantity',
		type : 'string',
		mapping : 'coverQuantity'
	}, {
		name : 'wallThickness',
		type : 'string',
		mapping : 'wallThickness'
	}, {
		name : 'subbaseThickness',
		type : 'string',
		mapping : 'subbaseThickness'
	}, {
		name : 'maintenanceModeEnumId',
		type : 'string',
		mapping : 'maintenanceModeEnumId'
	}, {
		name : 'maintenanceOrgId',
		type : 'string',
		mapping : 'maintenanceOrgId'
	}, {
		name : 'thirdPartyMaintenanceOrg',
		type : 'string',
		mapping : 'thirdPartyMaintenanceOrg'
	}, {
		name : 'maintenanceTypeEnumId',
		type : 'string',
		mapping : 'maintenanceTypeEnumId'
	}, {
		name : 'purchasedMaintenanceTime',
		mapping : 'purchasedMaintenanceTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}, {
		name : 'resourceLifePeriodEnumId',
		type : 'string',
		mapping : 'resourceLifePeriodEnumId'
	}, {
		name : 'projectNumber',
		type : 'string',
		mapping : 'projectNumber'
	}, {
		name : 'projectName',
		type : 'string',
		mapping : 'projectName'
	}, {
		name : 'renewalExpirationDate',
		mapping : 'renewalExpirationDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}, {
		name : 'projectWarrantyExpireDate',
		mapping : 'projectWarrantyExpireDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "wellLogs"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	this.typeStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '人孔'], ['2', '手孔'], ['3', '双人孔'],['4', '三人孔'], ['5', '局前井'], ['6', '地下室']]
	});
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		id : "wellGrid",
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
			id : 'seeWell',
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
	
	// 获得刷新按钮
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		grid.getStore().on('beforeload',function(){
   				grid.getStore().baseParams = {
   					start : 0,
					limit : limit
   				};
		});
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});
		grid.getView().refresh();
	});
	
//	var wellGrid=Ext.getCmp("wellGrid");
//	wellGrid.on('rowdblclick',function(g, r, e){
//		e.stopEvent();
//		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
//		if (selModel.hasSelection()) {
//			var records = selModel.getSelections();
//			var ids = [];
//			for (var i = 0; i < records.length; i++) {
//				ids.push(records[i].get("logTime"));// 获得选择的编号
//			}
//			if (ids.length > 1) {
//				Ext.Msg.show({
//					title : '提示',
//					width : 300,
//					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
//							+ '请您选择一条数据进行操作!',
//					buttons : {
//						ok : "确定"
//					}
//				});
//			} else if (ids.length == 1) {
//				var wellGrid=Ext.getCmp("wellGrid");
//				if(!wellGrid.seeEventWindow){
//					wellGrid.seeEventWindow=new com.increase.cen.log.logWindow({
//					id : "seeEventWindow",
//					height : 400,
//					width : 1000,
//					title : '查看日志',
//					iconCls : 'i-script-window',
//					items : [({
//						height : 350,
//						xtype : 'seeEvent'// 窗口里的表单
//					})]
//				});
//				}
//				var store = Ext.getCmp("eventGrid").getStore();
//				var loadMarsk = new Ext.LoadMask(document.body, {
//					msg : '数据加载中，请稍候......',
//					disabled : false,
//					store : store
//				});
//				store.addListener('beforeload', function() {
//					loadMarsk.show();
//				});
//
//				store.load({
//					params : {
//						time : ids[0],
//						start : 0,
//						limit : limit
//					}
//				});
//				store.on('beforeload',function(){
//					store.baseParams = {
//				   			start : 0,
//							limit : limit,
//							time : ids[0],
//				   	};
//				});
//	
//				wellGrid.seeEventWindow.show();
//			 }
//			}else{
//			Ext.Msg.show({
//				title : '提示',
//				width : 300,
//				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
//						+ '您未选择数据，请选择!',
//				buttons : {
//					ok : "确定"
//				}
//			});
//		}
//	});
	
	
	var seeWell=Ext.getCmp("seeWell");
	seeWell.on('click',function(g, r, e){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("wellId"));// 获得选择的编号
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
				var wellGrid=Ext.getCmp("wellGrid");
				if(!wellGrid.seeWellWindow){
					wellGrid.seeWellWindow=new com.increase.cen.log.logWindow({
					id : "seeWellWindow",
					height : 400,
					width : 1000,
					title : '查看资源日志',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'aloneWell'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("aloneWell").getStore();
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
						'well.wellId' : ids[0],
						start : 0,
						limit : limit
					}
				});
				wellGrid.seeWellWindow.show();
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

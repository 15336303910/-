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
	{header : 'logID',dataIndex : 'generatorName',hidden : true}, 
	{header : '操作时间',dataIndex : 'logTime',
		renderer:function(v){
			v=v.replace("T"," ");
			v=grey(v);
			return v;
		}	
	}, 
	{header : '操作人',dataIndex : 'logOperater',renderer:grey},
	{header : '操作类型',dataIndex : 'operationType',renderer:OperationType}, 
	{header : '电杆ID',dataIndex : 'poleId',hidden : true}, 
	{header : '电杆名称',dataIndex : 'poleNameSub',renderer:color1}, 
	
	{header : '电杆序号',dataIndex : 'poleNo',renderer:color1}, 
	{header : '扩充后缀',dataIndex : 'poleSubNo',renderer:color1}, 
	{header : '电杆编码',dataIndex : 'poleCode',renderer:color1}, 
	{header : '电杆类型',dataIndex : 'poleTypeEnumId',
		renderer:function(v){
			if(v=='1'){
				return "普通杆";
			}else if(v=='2'){
				return "单接杆";
			}else if(v=='3'){
				return "双接杆";
			}else if(v=='4'){
				return "H型杆";
			}else if(v=='5'){
				return "A型杆";
			}else if(v=='6'){
				return "L型杆";
			}else if(v=='7'){
				return "三角杆";
			}else if(v=='8'){
				return "井型杆";
			}else if(v=='9'){
				return "引上杆";
			}else if(v=='10'){
				return "终端杆";
			}else if(v=='11'){
				return "角杆";
			}else if(v=='12'){
				return "分歧杆";
			}else if(v=='13'){
				return "T型杆";
			}else if(v=='14'){
				return "跨路杆";
			}
		}
	}, 
	{header : '电杆材质',dataIndex : 'poleMaterial',renderer:color1}, 
	{header : '电杆程式',dataIndex : 'poleLength',renderer:color1}, 
	{header : '电杆埋深',dataIndex : 'buriedDepth',renderer:color1}, 
	{header : '电杆半径',dataIndex : 'poleRadius',renderer:color1}, 
	{header : '电杆地址',dataIndex : 'poleAddress',renderer:color1}, 
	
	{header : '电杆经度',dataIndex : 'poleLongitude',renderer:color1}, 
	{header : '电杆纬度',dataIndex : 'poleLatitude',renderer:color1}, 
	{header : '购买年限',dataIndex : 'purchasedMaintenanceTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')}, 
	{header : '维护方式',dataIndex : 'maintenanceModeEnumId',renderer:maintenanceMode}, 
	{header : '维护单位',dataIndex : 'maintenanceOrgId',renderer:color1}, 
	{header : '第三方维护 单位',dataIndex : 'thirdPartyMaintenanceOrg',renderer:color1}, 
	{header : '维护类型',dataIndex : 'maintenanceTypeEnumId',renderer:maintenanceType}, 
	
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getPoleLog.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'poleId',type : 'string',mapping : 'poleId',hidden : true
	}, {name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',type : 'string',mapping : 'logTime'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	},{
		name : 'poleNameSub',
		type : 'string',
		mapping : 'poleNameSub'
	}, {
		name : 'poleNo',
		type : 'string',
		mapping : 'poleNo'
	}, {
		name : 'poleSubNo',
		type : 'string',
		mapping : 'poleSubNo'
	}, {
		name : 'poleCode',
		type : 'string',
		mapping : 'poleCode'
	}, {
		name : 'poleTypeEnumId',
		type : 'string',
		mapping : 'poleTypeEnumId'
	}, {
		name : 'poleMaterial',
		type : 'string',
		mapping : 'poleMaterial'
	}, {
		name : 'poleLength',
		type : 'string',
		mapping : 'poleLength'
	}, {
		name : 'buriedDepth',
		type : 'string',
		mapping : 'buriedDepth'
	}, {
		name : 'poleRadius',
		type : 'string',
		mapping : 'poleRadius'
	}, {
		name : 'poleAddress',
		type : 'string',
		mapping : 'poleAddress'
	}, {
		name : 'poleLongitude',
		type : 'string',
		mapping : 'poleLongitude'
	}, {
		name : 'poleLatitude',
		type : 'string',
		mapping : 'poleLatitude'
	}, {
		name : 'purchasedMaintenanceTime',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'purchasedMaintenanceTime'
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
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "poles"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
		/*sortInfo: {  
        	field: 'jumpfiberId',  
        	direction: "DESC"  
    	}*/
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		id : "poleGrid",
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
			id : 'seePole',
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
	
	var seePole=Ext.getCmp("seePole");
	seePole.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleId"));// 获得选择的编号
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
				var poleGrid=Ext.getCmp("poleGrid");
				if(!poleGrid.seePoleWindow){
					poleGrid.seePoleWindow=new com.increase.cen.log.logWindow({
					id : "seePoleWindow",
					height : 400,
					width : 1000,
					title : '查看资源日志',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'alonePole'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("alonePole").getStore();
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
						'pole.poleId' : ids[0],
						start : 0,
						limit : limit
					}
				});
				poleGrid.seePoleWindow.show();
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
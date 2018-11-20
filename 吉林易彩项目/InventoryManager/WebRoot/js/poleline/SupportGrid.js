Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
	{header : '撑点ID',dataIndex : 'supportId',hidden : true},
	{header : '撑点名称',dataIndex : 'supportName'}, 
	{header : '撑点别名',dataIndex : 'supportBieName',width : 220}, 
	{header : '所属维护区域',dataIndex : 'region',width : 180}, 
	{header : '所在位置',dataIndex : 'address',width : 100}, 
	{header : '撑点类型',dataIndex : 'supportType',width : 180,
		renderer:function(v){
			if(v=="1"){
				return "终端撑点";
			}else if(v=="2"){
				return "分叉撑点";
			}else if(v=="3"){
				return "拐点撑点";
			}
		}
	}, 
	{header : '纬度',dataIndex : 'lat',width : 180},
	{header : '经度',dataIndex : 'lon',width : 180},
	{header : '撑点介质',dataIndex : 'supportJZ',width : 180,
		renderer:function(v){
			if(v=="1"){
				return "三脚架";
			}else if(v=="2"){
				return "卡丁";
			}else if(v=="3"){
				return "铁件";
			}
		}
	},
	{header : '距地高度',dataIndex : 'hight',width : 180},
	{header : '资源生命周期',dataIndex : 'lifecycle',width : 180,
		renderer:function(v){
			if(v=="1"){
				return "设计状态";
			}else if(v=="2"){
				return "工程建设期(入网带业务前)";
			}else if(v=="3"){
				return "工程可用期(已入网带业务)";
			}else if(v=="4"){
				return "工程初验后试运行";
			}else if(v=="5"){
				return "工程终验后在网";
			}else if(v=="6"){
				return "退网状态";
			}
		}
		
	},
	{header : '生命周期时间',dataIndex : 'lifecycleTime',width : 180},
	{header : '备注',dataIndex : 'note',width : 180},
	{header : '平面直角X坐标',dataIndex : 'planeRightAngleX',width : 180},
	{header : '平面直角Y坐标',dataIndex : 'planeRightAngleY',width : 180}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!getSupportlist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'supportId',type : 'string',mapping : 'supportId',hidden : true
	}, {
		name : 'supportName',
		type : 'string',
		mapping : 'supportName'
	}, {
		name : 'supportBieName',
		type : 'string',
		mapping : 'supportBieName'
	}, {
		name : 'region',
		type : 'string',
		mapping : 'region'
	}, {
		name : 'address',
		type : 'string',
		mapping : 'address'
	}, {
		name : 'supportType',
		type : 'string',
		mapping : 'supportType'
	},{
		name : 'lat',
		type : 'string',
		mapping : 'lat'
	},{
		name : 'lon',
		type : 'string',
		mapping : 'lon'
	},{
		name : 'supportJZ',
		type : 'string',
		mapping : 'supportJZ'
	},{
		name : 'hight',
		type : 'string',
		mapping : 'hight'
	},{
		name : 'lifecycle',
		type : 'string',
		mapping : 'lifecycle'
	},{
		name : 'lifecycleTime',
		type : 'string',
		mapping : 'lifecycleTime'
	},{
		name : 'note',
		type : 'string',
		mapping : 'note'
	},{
		name : 'planeRightAngleX',
		type : 'string',
		mapping : 'planeRightAngleX'
	},{   
		name : 'planeRightAngleY',
		type : 'planeRightAngleY',
		mapping : 'planeRightAngleY'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "supports"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：撑点管理",
		region : 'center',
		id : "supportGrid",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : sm,// 作为grid的选择模型(selection model)
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
			id : 'importBtn',
			text : '上传',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {	
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			id : 'exportBtn',
			text : '下载',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'// 一个分隔栏
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
	
	//获得上传按钮
	var importBtn = Ext.getCmp("importBtn");
	importBtn.on('click', function(){
		grid.importWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "importWindow",
			height:500,
			title : '上传',
			iconCls : 'i-script-window',
			width:600,
			items : [{
				id:'importsupport',
				fileUpload : true,
				height:300,
				xtype : 'importsupport'// 窗口里的表单
			}]
			
		});
		grid.importWindow.show("importBtn");
	});
	//获得刷新按钮
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});
		grid.getView().refresh();
	});
	//获得下载按钮
	var exportBtn=Ext.getCmp("exportBtn");
	exportBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("supportId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getsupportlist.action?ids="+ids;
			
		}else{
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请您至少选择一条数据进行操作!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	
	
	//
	
	
	
	
});
var grid;
function reload() {
	grid.getStore().reload({params:{
		start:0,limit:10
	}});
}

Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
	{header : '挂墙段ID',dataIndex : 'id',hidden : true, align:'center', sortable : true},
	{header : '挂墙段名称',dataIndex : 'hangWallName',width : 180, align:'center', sortable : true},
	{header : '维护方式',dataIndex : 'maintMode', align:'center', sortable : true,
		/*renderer:function(v){
			if(v=="0"){
				return "其他";
			} else if(v=="1") {
				return "自维";
			} else {
				return "代维";
			}
		}*/
	}, 
	{header : '维护区县',dataIndex : 'maintArea', align:'center', width : 180,sortable : true}, 
	{header : '维护方向',dataIndex : 'direction', align:'center', width : 180,sortable : true}, 
	{header : '用途',dataIndex : 'purpose', align:'center', sortable : true}, 
	{header : '使用单位',dataIndex : 'username', align:'center', sortable : true}, 
	{header : '挂墙长度',dataIndex : 'hangLength', align:'center', sortable : true}, 
	{header : '维护长度',dataIndex : 'maintLength', align:'center', sortable : true}, 
	{header : '起始撑点id',dataIndex : 'startDeviceId',hidden : true, align:'center', sortable : true}, 
	{header : '起始撑点名称',dataIndex : 'startDeviceName', align:'center', sortable : true}, 
	{header : '终止撑点id',dataIndex : 'endDeviceId',hidden : true, align:'center', sortable : true}, 
	{header : '终止撑点名称',dataIndex : 'endDeviceName', align:'center', sortable : true}, 
	{header : '一线维护人',dataIndex : 'maintainer', align:'center', sortable : true},
	{header : '创建时间',dataIndex : 'createTime',width : 180,sortable : true,align:'center',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '最后更新时间',dataIndex : 'lastUpTime',width : 180,sortable : true,align:'center',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : context_path + "/leadupAction!getHangWallList.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'id',type : 'string',mapping : 'id',hidden : true
	}, {
		name : 'hangWallName',
		type : 'string',
		mapping : 'hangWallName'
	},  {
		name : 'maintMode',
		type : 'string',
		mapping : 'maintMode'
	},  {
		name : 'maintArea',
		type : 'string',
		mapping : 'maintArea'
	},{
		name : 'direction',
		type : 'string',
		mapping : 'direction'
	},{
		name : 'purpose',
		type : 'string',
		mapping : 'purpose'
	},{
		name : 'username',
		type : 'string',
		mapping : 'username'
	},{
		name : 'hangLength',
		type : 'string',
		mapping : 'hangLength'
	},{
		name : 'maintLength',
		type : 'string',
		mapping : 'maintLength'
	},{
		name : 'startDeviceId',
		type : 'string',
		mapping : 'startDeviceId'
	},{
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceId',
		type : 'string',
		mapping : 'endDeviceId'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	},{
		name : 'maintainer',
		type : 'string',
		mapping : 'maintainer'
	},{
		name : 'createTime',
		mapping : 'createTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d H:i:s'
	},{
		name : 'lastUpTime',
		mapping : 'lastUpTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d H:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "hangWallList"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
	});
	
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	grid = new Ext.grid.GridPanel({
		title : "当前位置：挂墙段管理",
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
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addHangWallPojo',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"
		},'-', {
			id : 'modifyHangWallPojo',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype:'tbseparator'
		}, {
			id : 'deleteHangWallPojo',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype:'tbseparator'
		}, {
			id : 'importBtn',
			text : '上传',
			icon : "imgs/fiber/link_break.png",
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
		}, {
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'// 一个分隔栏
		},],
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
		/*grid.importWindow = new com.increase.cen.poleline.PolelineWindow({
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
		grid.importWindow.show("importBtn");*/
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
		/*var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
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
		}*/
	});
	
	//获取添加按钮
	var addHangWallPojo = Ext.getCmp("addHangWallPojo");
	addHangWallPojo.on('click', function() {
		addHangWall();
	});
	
	//添加删除按钮
	var deleteSupportPointPojo = Ext.getCmp("deleteHangWallPojo");
	deleteSupportPointPojo.on('click', function() {
		var rows = grid.getSelectionModel().getSelections();
		delRows('leadupAction!delHangWall.action','id',rows,reload);
	});
	
	//添加修改按钮
	var modifyHangWallPojo = Ext.getCmp("modifyHangWallPojo");
	modifyHangWallPojo.on('click', function() {
		
		var row = grid.getSelectionModel().getSelections();
		if(row.length!=1){
			Ext.MessageBox.alert("","请选择一条数据进行修改！");
		} else {
			
			var r = row[0];
			Ext.Ajax.request({ 
				url:context_path+'/leadupAction!getDetailHangWall.action',
				method:'post',
				params:{id:r.data['id']},
				success:function(fp,o){
					var obj = eval("("+fp.responseText+")");
					modifyHangWall(obj);
					
				}
		 	});
		}
		
	});
	
});



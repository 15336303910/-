Ext.onReady(function() {
	Ext.useShims = true;
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true		
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, {
		header : '设备ID',
		dataIndex : 'eid',
		width : 100,
		hidden:true
	},{
		header : '设备名称',
		dataIndex : 'ename',
		width : 100
	}, {
		header : '设备编码',
		dataIndex : 'ecode',
		width : 100
	}, {
		header : '资产标签号',
		dataIndex : 'proper_zcbqh',
		width : 100
	}, {
		header : '设备地址',
		dataIndex : 'eaddr',
		width : 120
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
		width : 100
	},	{
		header : '维护人',
		dataIndex : 'mp',
		width : 100
	},{
		header : '维护时间',
		dataIndex : 'mtime',
		width : 100,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : '经度',
		dataIndex : 'lon',
		width : 100
	},{
		header : '纬度',
		dataIndex : 'lat',
		width : 100
	},{
		header : '负责人',
		dataIndex : 'prlusername',
		width : 100
	}]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "equt!getEqutGrid.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
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
		name : 'proper_zcbqh',
		type : 'string',
		mapping : 'proper_zcbqh'
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
		title : "当前位置：ODF管理",
		region : 'center',
		id : "grid",
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
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler : function() {
				help("pages/help/ODF.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '查看端子',
			id : 'seePoint',
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'seeReport',
			text : '查看报表',
			icon : "imgs/queryBtn.png",
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
			limit : limit,
			'equtInfoBean.etype':'1'
		}
	});
	// 解决分页点击下一页时不带参数问题
	store.on('beforeload', function() {
		store.baseParams = {
		'equtInfoBean.etype':'1'
		};
	});
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	// var view = Ext.getCmp("view")
	
	//双击查看信息
	var grid=Ext.getCmp("grid");
	grid.on('rowdblclick', function(g, r, e) {
		e.stopEvent();
		var equt=g.getStore().getAt(r).json;
		
		grid.equtWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "equtWindow",
				width:600,
				height:500,
				title : '查看设备信息',
				iconCls : 'i-script-window',
				items : [new com.increase.cen.equt.seeEqut({
					id : "seeEqut",
					equt : equt,
					windowId: "equtWindow"
				})]
			});
		grid.equtWindow.show();
	});
	var seePoint=Ext.getCmp("seePoint");
	seePoint.on('click',function(){
		var grid=Ext.getCmp("grid");
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if(selModel.hasSelection()){
			var record = selModel.getSelections();
			if(!grid.showPointWindow){
			grid.showPointWindow=new com.increase.cen.poleline.poleLSGridWindow({
				id : "showPointWindow",
				height : 400,
				width : 1000,
				title : '查看端子',
				iconCls : 'i-script-window',
				items : [({
					height : 350,
					xtype : 'showPointGrid'// 窗口里的表单
				})]
			});
			}
			var showPointGrid=Ext.getCmp("showPointGrid");
			var store = showPointGrid.getStore();
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
					"equtInfoBean.eid":record[0].get("eid"),
					start : 0,
					limit : 12
				}
			});
			store.on('beforeload',function(){
				store.baseParams = {
						"equtInfoBean.eid":record[0].get("eid")
			   	};
			});
			
			grid.showPointWindow.show();
			
			showPointGrid.on('rowdblclick',function(g,r,e){
				e.stopEvent();
				var point=g.getStore().getAt(r).json;
					showPointGrid.seePointWindow = new com.increase.cen.poleline.PolelineWindow({
							id : "seePointWindow",
							width:600,
							title : '查看端子信息',
							iconCls : 'i-script-window',
							items : [new com.increase.cen.equt.seePoint({
								id : "seePoint",
								point : point,
								windowId: "seePointWindow"
							})]
						});
				showPointGrid.seePointWindow.show();
			});
			
			
		
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
	
	var seeReport=Ext.getCmp("seeReport");
	seeReport.on('click',function(){
		var grid=Ext.getCmp("grid");
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if(selModel.hasSelection()){
			var record = selModel.getSelections();
			var url="equt!seeReport.action?equtInfoBean.eid="+record[0].get("eid");
			var iWidth=850; //弹出窗口的宽度;
			var iHeight=700; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
			var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
			window.open(url,"newwindow",param);
		};
	});
});

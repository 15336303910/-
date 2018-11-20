Ext.onReady(function() {
	Ext.useShims=true;
	var expander = new Ext.grid.RowExpander({
		tpl : new Ext.XTemplate('<div class="detailData"></div>')
	});
	expander.on("expand", function(expander, r, body, rowIndex) {
		
		if (Ext.DomQuery.select("div.x-panel-bwrap", body).length == 0) {
			var route = r.json;	
			var routeName = route.routename;
			var pageSize = parseInt(Ext.getDom("pageSize").value);
			var limit = parseInt(Ext.getDom("limit").value);
			//数据源
			var proxy = new Ext.data.HttpProxy({
				url : "route!searchCable.action"
			});
			var cable = Ext.data.Record.create([
				{name : 'cableid',type : 'string',mapping : 'cableid',hidden : true}, 
				{name : 'routename',type : 'string',mapping : 'routename',hidden : true},
				{name : 'cablename',type : 'string',mapping : 'cablename'},
				{name : 'type',type : 'string',mapping : 'type'},
				{name : 'mode',type : 'string',mapping : 'mode'},
				{name : 'length',type : 'string',mapping : 'length'},
				{name : 'paveWay',type : 'string',mapping : 'paveWay'},
				{name : 'state',type : 'string',mapping : 'state'},
				{name : 'sharetype',type : 'string',mapping : 'sharetype'},
				{name : 'shareoperator',type : 'string',mapping : 'shareoperator'},
				{name : 'starteid',type : 'string',mapping : 'starteid',hidden:true},
				{name : 'startDeviceName',type : 'string',mapping : 'startDeviceName'},
				{name : 'endeid',type : 'string',mapping : 'endeid',hidden:true},
				{name : 'endDeviceName',type : 'string',mapping : 'endDeviceName'}
			]);
			var reader = new Ext.data.JsonReader({
				totalProperty : "total",
				root : "cables"
			}, cable);
			
			var cStore = new Ext.data.Store({// 提供数据输入
				proxy : proxy,
				reader : reader,
				remoteSort : true
			});
			var smc = new Ext.grid.CheckboxSelectionModel({
				/*id:"cableCheckbox",
				name:"cableCheckbox",
				width:25*/
				singleSelect : true
			});	
			
			var ccm = new Ext.grid.ColumnModel([smc,
				{header : "cableid",hidden : true,dataIndex : 'cableid',width : 130,hideable : false,sortable : false,resizable : true},
				{header : "光缆名称",hidden : true,dataIndex :'routename',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "光缆段名称",dataIndex : 'cablename',width : 260,hideable : true,sortable : true,resizable : true},
				{header : "starteid",hidden : true,dataIndex :'starteid',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "起始设施名称",dataIndex :'startDeviceName',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "endeid",hidden : true,dataIndex :'endeid',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "终止设施名称",dataIndex :'endDeviceName',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "缆线类型",dataIndex : 'type',width : 100,hideable : true,sortable : true,resizable : true},
				{header : "光缆段型号",dataIndex : 'mode',width : 100,hideable : true,sortable : true,resizable : true},
				{header : "光缆段长度",dataIndex : 'length',width : 70,hideable : true,sortable : true,resizable : true},
				{header : "铺设方式",dataIndex :'paveWay',width : 100,hideable : true, sortable : true,resizable : true},
				{header : "维护状态",dataIndex :'state',width : 60,hideable : true, sortable : true,resizable : true,
					renderer:function(v){//列模型回调函数
						if(v == '1'){
							return '割接封锁';
						}else if(v == '2'){
							return '正常';					
						}else if(v == '3'){
							return '不可用';	
						}
					}
				},
				{header : "共建/共享类型",dataIndex :'sharetype',width : 120,hideable : true, sortable : true,resizable : true,
					renderer:function(v){//列模型回调函数
						if(v == '1'){
							return '共建';
						}else if(v == '2'){
							return '共享(我方共享他方)';					
						}else if(v == '3'){
							return '共享(他方共享我方)';	
						}else if(v == '4'){
							return '自建自用';	
						}else if(v == '5'){
							return '自建预留';	
						}
					}
				},
				{header : "共建/共享方",dataIndex :'shareoperator',width : 120,hideable : true, sortable : true,resizable : true}
			]);
			// 表格
			var cableGrid = new Ext.grid.GridPanel({
				id : "cableGrid"+rowIndex,
				store : cStore,
				cm : ccm,
				sm :smc,
				renderTo : Ext.DomQuery.select("div.detailData", body)[0],
				autoWidth : true,
				autoHeight : true,
				viewConfig : {
					sortAscText : '升序',
					sortDescText : '降序',
					columnsText : '可选列',
					forceFit : false,// Ture表示自动扩展或缩小列的宽度
					scrollOffset : -1
				},
				buttonAlign : "left",// 按钮居中
				tbar : [{
					text : '添加光缆段',
					id : 'addCableBtn',
					icon : "js/ext/resources/images/default/dd/drop-add.gif",
					cls : "x-btn-text-icon"
		
				},{
					id : 'mergeCableBtn',
					text : '合并光缆段',
					icon : "js/ext/resources/images/yourtheme/dd/drop-merge.gif",
					cls : "x-btn-text-icon"
				},{
					text : '查看纤芯',
					id : 'checkCableBtn',
					icon : "imgs/modifyBtn.png",
					cls : "x-btn-text-icon"
		
				},{
					id : 'refreshCableBtn',
					text : '全部刷新',
					icon : "imgs/all_refresh.png",
					cls : "x-btn-text-icon"
				},{
					xtype: 'tbspacer',
					width:100
					
				},{
					xtype:'label',
					x:200,
					text:'光缆段名称：'
				},{
					xtype:'textfield',
					id:'cablename_sel'+rowIndex,
					cls:'sel-textfield-width'
				},{
					xtype : 'tbseparator'
				},{
					id : 'queryCableBtn',
					text : '查询',
					icon : "imgs/queryBtn.png",
					cls : "x-btn-text-icon"
				}],
				bbar : new Ext.PagingToolbar({// 分页工具栏
					store : cStore,
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
				})/*,
				listeners: {
					mouseover: function(e){
        				e.stopEvent();
					},
					rowmousedown: function(g, r, e){
        				e.stopEvent();
        				
					},
					 rowcontextmenu: function(grid, rowIndex, e) {
           				e.stopEvent();
           				var cable = grid.getStore().getAt(rowIndex).json;					
						var cablename = cable.cablename;
									
           				//alert(rowIndex);
           				var menus=new Ext.menu.Menu({
            				items:[{
                				text:"添加光缆段",
                				icon:"js/ext/resources/images/yourtheme/dd/drop-add.gif",
                				handler:function() {
									addCable(route,rowIndex);
                				}
            				},{
                				text:"合并光缆段",
                				icon:"js/ext/resources/images/yourtheme/dd/drop-merge.gif",
                				handler:function() {
									mergeCable(route,rowIndex);
									
                				}
            				},{
                				text:"编辑光缆段",
                				icon:"imgs/modifyBtn.png",
                				handler:function() {
									modifyCable(cable,rowIndex);
                				}
            				},{
                				text:"查看纤芯",
                				icon:"imgs/queryBtn.png",
                				handler:function() {
									parent.Ext.getCmp("pnCenterframe").body.update('<iframe id=domainTreeIframe src=route!oppoFiberGrid.action?searchOppoFiber.routename='+cable.routename+'&searchOppoFiber.cablename='+cable.cablename+' width=100% height=100% frameborder=no border=0 marginwidth=0 marginheight=0 align=center></iframe>');
                				}
            				}],
           					minWidth:200
        				});
        				menus.showAt(e.getPoint());
					}
				}*/
			});
			
			//添加光缆段按钮
			var addCableBtn = Ext.getCmp("addCableBtn");
			// 增加按钮的click事件
			addCableBtn.on('click', function() {
				
				addCable(route,rowIndex);
			});
			//编辑光缆按钮
	/*var editCableBtn = Ext.getCmp("editCableBtn");
	// 编辑按钮的click事件
	editCableBtn.on('click', function() {
		var cableGrid = Ext.getCmp("cableGrid");
		var selModel = cableGrid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("cableid"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行修改!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				Ext.MessageBox.show({
					title : '确认',
					msg : '<div style="margin-top:5px;margin-left:10px;">您确定要修改选择的记录吗?</div>',
					width : 300,
					multiline : false,// 设置为true，提示用户输入多行文本
					closable : false,
					buttons : {
						yes : '确定',
						no : '取消'
					},
					icon : Ext.MessageBox.QUESTION,
					fn : function(btn) {
						if (btn == 'yes') {// 如果选择确定，则进行修改
							if (!cableGrid.modifyCableWindow) {// 如果窗口不存在，则创建新窗口
								// 新建修改窗口
								cableGrid.modifyCableWindow = new com.increase.cen.route.routeWindow({
									id : "modifyCableWindow",
									width:500,
									height:300,
									title : '修改光缆段信息',
									iconCls : 'i-script-window',
									items : [{
										id : "modifyCable",
										xtype : 'modifyCable'// 窗口里的修改表单
									}]
								});
							}
							Ext.getCmp("modifyCable").getForm().reset();
							Ext.getCmp("updateCable.cableid").setValue(records[0].get("cableid"));
							Ext.getCmp("updateCable.routename").setValue(records[0].get("routename"));
							Ext.getCmp("updateCable.cablename").setValue(records[0].get("cablename"));
							Ext.getCmp("updateCable.newCablename").setValue(records[0].get("cablename"));
							Ext.getCmp("updateCable.starteid").setValue(records[0].get("starteid"));
							Ext.getCmp("updateCable.startDeviceName").setValue(records[0].get("startDeviceName"));
							Ext.getCmp("updateCable.endeid").setValue(records[0].get("endeid"));
							Ext.getCmp("updateCable.endDeviceName").setValue(records[0].get("endDeviceName"));
							Ext.getCmp("updateCable.type").setValue(records[0].get("type"));
							Ext.getCmp("updateCable.mode").setValue(records[0].get("mode"));
							Ext.getCmp("updateCable.length").setValue(records[0].get("length"));
							Ext.getCmp("updateCable.paveWay").setValue(records[0].get("paveWay"));
							Ext.getCmp("updateCable.state1").setValue(records[0].get("state"));
							Ext.getCmp("updateCable.sharetype1").setValue(records[0].get("sharetype"));
							Ext.getCmp("updateCable.shareoperator").setValue(records[0].get("shareoperator"));
							
							cableGrid.modifyCableWindow.show();
						}
					}
				});
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想修改的数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});*/
			//合并光缆段按钮queryCableBtn
			var mergeCableBtn = Ext.getCmp("mergeCableBtn");
			// 增加按钮的click事件
			mergeCableBtn.on('click', function() {
				mergeCable(route,rowIndex);
			});
			//查看纤芯
			var checkCableBtn=Ext.getCmp("checkCableBtn");
			checkCableBtn.on('click', function() {
				var grid = Ext.getCmp("cableGrid"+rowIndex);
				var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
				if (selModel.hasSelection()) {
					var records = selModel.getSelections();
					var cable=records[0];
					parent.Ext.getCmp("pnCenterframe").body.update('<iframe id=domainTreeIframe src=route!oppoFiberGrid.action?searchOppoFiber.routename='+cable.get("routename")+'&searchOppoFiber.cablename='+cable.get("cablename")+' width=100% height=100% frameborder=no border=0 marginwidth=0 marginheight=0 align=center></iframe>');
					
				}else{
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_warn.png" align="center" hspace="15"/>'
								+ '请您选择一条光缆段进行查看!',
						buttons : {
							ok : "确定"
						}
					});
				}
			});
			//全部刷新按钮
			var refreshCableBtn = Ext.getCmp("refreshCableBtn");
			// 增加按钮的click事件
			refreshCableBtn.on('click', function() {
				// 刷新表格
				Ext.getCmp("cableGrid"+rowIndex).getStore().load({
					params : {
						start : 0,
						limit : limit,
						'searchCable.routename' : routeName
					}
				});
			});
			
			//全部刷新按钮
			var queryCableBtn = Ext.getCmp("queryCableBtn");
			// 增加按钮的click事件
				queryCableBtn.on('click', function() {
				// 刷新表格
				Ext.getCmp("cableGrid"+rowIndex).getStore().load({
					params : {
						start : 0,
						limit : limit,
						'searchCable.routename' : routeName,
						'searchCable.cablename':Ext.getCmp("cablename_sel"+rowIndex).getValue()
					}
				});
				Ext.getCmp("cablename_sel"+rowIndex).setValue("");
			});
			
			var loadMarsk = new Ext.LoadMask(document.body, {
				msg : '数据加载中，请稍候......',
				disabled : false,
				store : cStore
			});
			cStore.addListener('beforeload', function() {
				loadMarsk.show();
			});
			
			cStore.load({
				params : {
					start : 0,
					limit : limit,
					'searchCable.routename' : routeName
				}
			});
			cStore.on('beforeload',function(){
   				cStore.baseParams = {'searchCable.routename' : routeName};
			});
			var cableGrid = Ext.getCmp("cableGrid"+rowIndex);
			cableGrid.on('rowdblclick', function(g, r, e) {
				e.stopEvent();
				var cable = g.getStore().getAt(r).json;
				modifyCable(cable,rowIndex);
			});
		}
	});
	
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		/*id:"routeCheckbox",
		name:"routeCheckbox",
		width:25*/
		//singleSelect : true,
		handleMouseDown:Ext.emptyFn
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([
		sm, 
		expander, 
		{header : "序号",width : 60,hideable : false,
			renderer : function(value, metadata, record, rowIndex, colIndex, store) {
				var start = store.lastOptions.params.start;
				if (isNaN(start)) {
					start = 0;
				}
				return start + rowIndex + 1;
			}
		}, 
		{header : 'routeid',dataIndex : 'routeid',hideable : false,width : 100,hidden : true}, 
		{header : '光缆名称',dataIndex : 'routename',width : 300,sortable : true}, 
		{header : '光缆级别',dataIndex : 'routelevel',width : 150,sortable : true,
			renderer:function(v){//列模型回调函数
				if(v == '1'){
					return '一级干线';
				}else if(v == '2'){
					return '二级干线';					
				}else if(v == '3'){
					return '本地中继';	
				}else if(v == '4'){
					return '本地核心';	
				}else if(v == '5'){
					return '本地汇聚';	
				}else if(v == '6'){
					return '本地接入';	
				}
			}
		}, 
		
		{header : '维护状态',dataIndex : 'state',width : 150,sortable : true,
			renderer:function(v){//列模型回调函数
				if(v == '1'){
					return '割接封锁';
				}else if(v == '2'){
					return '正常';					
				}else if(v == '3'){
					return '不可用';	
				}
			}
		}, 
		{header : '起始局站',dataIndex : 'startsite',width : 150,sortable : true}, 
		{header : '终止局站',dataIndex : 'endsite',width : 150,sortable : true}, 
		{header : '光缆标牌',dataIndex : 'sign',width : 170,sortable : true}
	]);
	//数据源
	var proxy = new Ext.data.HttpProxy({
		url : "route!searchRoute.action"
	});
	//数据结构
	var route = Ext.data.Record.create([
		{name : 'routeid',type : 'string',mapping : 'routeid',hidden : true}, 
		{name : 'routename',type : 'string',mapping : 'routename'}, 
		{name : 'routelevel',type : 'string',mapping : 'routelevel'}, 
		{name : 'state',type : 'string',mapping : 'state'}, 
		{name : 'startsite',type : 'startsite',mapping : 'startsite'}, 
		{name : 'endsite',type : 'string',mapping : 'endsite'},
		{name : 'sign',type : 'string',mapping : 'sign'}
	]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "routes"
	}, route);
	
	// 提供数据输入
	var store = new Ext.data.Store({
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	var stateStore = new Ext.data.SimpleStore({
		fields : ['value', 'text'],
		data : [['', '全部状态'],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
	});
	var levelStore = new Ext.data.SimpleStore({
		fields : ['value', 'text'],
		data : [['', '全部级别'],['1', '一级干线'], ['2', '二级干线'], ['3', '本地中继'], ['4', '本地核心'], ['5', '本地汇聚'], ['6', '本地接入']]
	});
	// 表格
	var routeGrid = new Ext.grid.GridPanel({
		title : "当前位置：光缆管理",
		region : 'center',
		id : "routeGrid",
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
			text : '添加光缆',
			id : 'addRouteBtn',
			icon : "js/ext/resources/images/default/dd/drop-add.gif",
			cls : "x-btn-text-icon"
		},/*{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '编辑光缆',
			id : 'editRouteBtn',
			icon : "imgs/modifyBtn.png",
			cls : "x-btn-text-icon"
		},*/ {
			xtype : 'tbseparator'
		}, {
			id : 'mergeRouteBtn',
			text : '合并光缆',
			icon : "js/ext/resources/images/yourtheme/dd/drop-merge.gif",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'betchRouteBtn',
			text : '批量维护',
			icon : "js/ext/resources/images/yourtheme/dd/drop-merge.gif",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'exportRouteBtn',
			text : '导出光缆列表',
			icon : "js/ext/resources/images/yourtheme/dd/drop-merge.gif",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'refreshRouteBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			xtype: 'tbspacer',
			width:100
			
		},{
			xtype:'label',
			text:'光缆名称：'
		},{
			xtype:'textfield',
			id:'routename_sel',
			cls:'sel-textfield-width'
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'光缆级别：'
		},{
			id:'routelevel_sel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : levelStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'routelevel'
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'起始局站：'
		},{
			xtype:'textfield',
			id:'startsite_sel',
			cls:'sel-textfield-width'
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'终止局站：'
		},{
			xtype:'textfield',
			id:'endsite_sel',
			cls:'sel-textfield-width'
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '维护状态：'
		},{
			id:'state_sel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : stateStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'state'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'光缆标牌：'
		},{
			xtype:'textfield',
			id:'sign_sel',
			cls:'sel-textfield-width'
		},{
			xtype : 'tbseparator'
		},{
			id : 'queryRouteBtn',
			text : '查询',
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
		}),
		plugins : [expander]
		
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
	var routeGrid = Ext.getCmp("routeGrid");
			routeGrid.on('rowdblclick', function(g, r, e) {
				e.stopEvent();
				var route = g.getStore().getAt(r).json;
				modifyRoute(route);
			});
	
	
	new Ext.Viewport({
		layout : 'border',
		items : [routeGrid]
	});
	
	//添加光缆按钮
	var addRouteBtn = Ext.getCmp("addRouteBtn");
	// 增加按钮的click事件
	addRouteBtn.on('click', function() {
		addRoute();
	});
	/*//编辑光缆按钮
	var editRouteBtn = Ext.getCmp("editRouteBtn");
	// 编辑按钮的click事件
	editRouteBtn.on('click', function() {
		var routeGrid = Ext.getCmp("routeGrid");
		var selModel = routeGrid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("routeid"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行修改!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				Ext.MessageBox.show({
					title : '确认',
					msg : '<div style="margin-top:5px;margin-left:10px;">您确定要修改选择的记录吗?</div>',
					width : 300,
					multiline : false,// 设置为true，提示用户输入多行文本
					closable : false,
					buttons : {
						yes : '确定',
						no : '取消'
					},
					icon : Ext.MessageBox.QUESTION,
					fn : function(btn) {
						if (btn == 'yes') {// 如果选择确定，则进行修改
							if (!routeGrid.modifyRouteWindow) {// 如果窗口不存在，则创建新窗口
								// 新建修改窗口
								routeGrid.modifyRouteWindow = new com.increase.cen.route.routeWindow({
									id : "modifyRouteWindow",
									width:500,
									height:300,
									title : '修改光缆信息',
									iconCls : 'i-script-window',
									items : [{
										id : "modifyRoute",
										xtype : 'modifyRoute'// 窗口里的修改表单
									}]
								});
							}
							Ext.getCmp("routeid").setValue(records[0].get("routeid"));
							Ext.getCmp("routename").setValue(records[0].get("routename"));
							Ext.getCmp("newRoutename").setValue(records[0].get("routename"));
							Ext.getCmp("routelevel").setValue(records[0].get("routelevel"));
							Ext.getCmp("state").setValue(records[0].get("state"));
							Ext.getCmp("startsite").setValue(records[0].get("startsite"));
							Ext.getCmp("endsite").setValue(records[0].get("endsite"));
							Ext.getCmp("sign").setValue(records[0].get("sign"));
							
							var modifyRouteForm = Ext.getCmp("modifyRoute");
							routeGrid.modifyRouteWindow.show();
						}
					}
				});
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想修改的数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});*/
	
	//合并光缆按钮
	var mergeRouteBtn = Ext.getCmp("mergeRouteBtn");
	mergeRouteBtn.on('click', function() {
		mergeRoute();
	});
	var betchRouteBtn = Ext.getCmp("betchRouteBtn");
	betchRouteBtn.on('click', function() {
		var routeGrid=Ext.getCmp("routeGrid");
		if (!routeGrid.betchRouteWindow) {
			// 新建添加窗口
			routeGrid.betchRouteWindow = new com.increase.cen.route.routeWindow({
				id : "betchRouteWindow",
				title : '批量维护',
				iconCls : 'i-script-window',
				items : [{
					id : "betchRoute",
					fileUpload : true,
					height : 260,
					xtype : 'betchRoute'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("betchRoute").getForm().reset();
		routeGrid.betchRouteWindow.show("betchRouteBtn");
	});
	
	var exportRouteBtn= Ext.getCmp("exportRouteBtn");
	exportRouteBtn.on('click', function() {
		var grid=Ext.getCmp("routeGrid");
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("routeid"));// 获得选择的编号
			}
			if(ids.length<1){
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请至少选择一条光缆!',
					buttons : {
						ok : "确定"
					}
				});
			}else{
				location.href="route!exportRoute.action?ids="+ids;
			}
		}
	});
	//查询按钮
	var queryRouteBtn = Ext.getCmp("queryRouteBtn");
	// 增加按钮的click事件
	queryRouteBtn.on('click', function() {
		// 刷新表格
		Ext.getCmp("routeGrid").getStore().load({
			params : {
				start : 0,
				limit : limit,
				'searchRoute.routename':Ext.getCmp("routename_sel").getValue(),
				'searchRoute.routelevel':Ext.getCmp("routelevel_sel").getValue(),
				'searchRoute.startsite':Ext.getCmp("startsite_sel").getValue(),
				'searchRoute.endsite':Ext.getCmp("endsite_sel").getValue(),
				'searchRoute.state':Ext.getCmp("state_sel").getValue(),
				'searchRoute.sign':Ext.getCmp("sign_sel").getValue()
			}
		});// 表格加载数据
		Ext.getCmp("routeGrid").getStore().on('beforeload',function(){
			Ext.getCmp("routeGrid").getStore().baseParams ={
				'searchRoute.routename':Ext.getCmp("routename_sel").getValue(),
				'searchRoute.routelevel':Ext.getCmp("routelevel_sel").getValue(),
				'searchRoute.startsite':Ext.getCmp("startsite_sel").getValue(),
				'searchRoute.endsite':Ext.getCmp("endsite_sel").getValue(),
				'searchRoute.state':Ext.getCmp("state_sel").getValue(),
				'searchRoute.sign':Ext.getCmp("sign_sel").getValue()
			};
		});
		
		//Ext.getCmp("routename_sel").setValue('')
	});
	
	
	//全部刷新按钮
	var refreshRouteBtn = Ext.getCmp("refreshRouteBtn");
	// 增加按钮的click事件
	refreshRouteBtn.on('click', function() {
		// 刷新表格
		Ext.getCmp("routename_sel").setValue('');
		Ext.getCmp("routelevel_sel").setValue('');
		Ext.getCmp("startsite_sel").setValue('');
		Ext.getCmp("endsite_sel").setValue('');
		Ext.getCmp("state_sel").setValue('');
		Ext.getCmp("sign_sel").setValue('');
		Ext.getCmp("routeGrid").getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	});
	
	Ext.override(Ext.grid.RowSelectionModel, {
   		selectRow: function(index, keepExisting, preventViewNotify) {
   			
        	if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) || (keepExisting && this.isSelected(index)) || (Number(this.grid.store.getAt(index).get("EditFlag")) === 0)) {//根据每行的一个标识字段来判断是否选中
            	return;
        	}
        	var r = this.grid.store.getAt(index);
       		if (r && this.fireEvent('beforerowselect', this, index, keepExisting, r) !== false) {
            	if (!keepExisting || this.singleSelect) {
                	this.clearSelections();
            	}
            	this.selections.add(r);
            	this.last = this.lastActive = index;
           		if (!preventViewNotify) {
                	this.grid.getView().onRowSelect(index);
            	}
            	this.fireEvent('rowselect', this, index, r);
            	this.fireEvent('selectionchange', this);
        	}

    	}
	});
	routeGrid.on({
		'mouseover' : function(e){
			e.stopPropagation();	
		},
		'mouseout' : function(e){
			e.stopPropagation();	
		},'rowcontextmenu':function(grid,rowIndex,e){
			e.preventDefault();
        	var menus=new Ext.menu.Menu({
            items:[{
                text:"添加光缆",
                icon:"js/ext/resources/images/yourtheme/dd/drop-add.gif",
                handler:function() {
					addRoute();
                }
            },{
                text:"添加光缆段",
                icon:"imgs/form_add.png",
                handler:function() {
				var route = grid.getStore().getAt(rowIndex).json;	
					addCable(route);
                }
            },{
                text:"合并光缆",
                icon:"js/ext/resources/images/yourtheme/dd/drop-merge.gif",
                handler:function() {
					mergeRoute();
                }
            },{
                text:"编辑光缆",
                icon:"imgs/modifyBtn.png",
                handler:function() {
					var route = grid.getStore().getAt(rowIndex).json;					
					modifyRoute(route);
                }
            }],
           		minWidth:200
        	});
        	menus.showAt(e.getPoint());
		}
	});
	
	
});
function resetquery(){
	var routeGrid = Ext.getCmp("routeGrid");
	Ext.getCmp("routename_sel").setValue('');
		Ext.getCmp("routelevel_sel").setValue('');
		Ext.getCmp("startsite_sel").setValue('');
		Ext.getCmp("endsite_sel").setValue('');
		Ext.getCmp("state_sel").setValue('');
		Ext.getCmp("sign_sel").setValue('');
	routeGrid.getStore().on('beforeload',function(){
			routeGrid.getStore().baseParams ={
				start : 0,
				limit : limit
			};
		});
}
function addRoute(){
	var routeGrid = Ext.getCmp("routeGrid");
	resetquery();//调用此方法重置查询项
	if (!routeGrid.addRouteWindow) {
			// 新建添加窗口
			routeGrid.addRouteWindow = new com.increase.cen.route.routeWindow({
				id : "addRouteWindow",
				title : '添加光缆',
				iconCls : 'i-script-window',
				items : [{
					id : "addRoute",
					xtype : 'addRoute'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("addRoute").getForm().reset();
		routeGrid.addRouteWindow.show("addRouteBtn");
		// 是否批量增加复选框有效状态
	//	Ext.getCmp("batchAdd").enable();
}
function mergeRoute(){
	var routeGrid = Ext.getCmp("routeGrid");
	resetquery();//调用此方法重置查询项
	if (!routeGrid.mergeRouteWindow) {
			// 合并光缆窗口
			routeGrid.mergeRouteWindow = new com.increase.cen.route.routeWindow({
				id : "mergeRouteWindow",
				width:700,
				title : '光缆合并',
				iconCls : 'i-script-window',
				items : [{
					id : "mergeRouteGrid",
					xtype : 'mergeRouteGrid'// 窗口里的表单
				}]
			});
		}
		routeGrid.mergeRouteWindow.show("mergeRouteBtn");
}
function modifyRoute(route){
	var routeGrid = Ext.getCmp("routeGrid");
	resetquery();//调用此方法重置查询项
	if (!routeGrid.modifyRouteWindow) {
			// 合并光缆窗口
			routeGrid.modifyRouteWindow = new com.increase.cen.route.routeWindow({
				id : "modifyRouteWindow",
				width:500,
				title : '光缆修改',
				iconCls : 'i-script-window',
				items : [{
					id : "modifyRoute",
					xtype : 'modifyRoute'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("routeid").setValue(route.routeid);
		Ext.getCmp("routename").setValue(route.routename);
		Ext.getCmp("newRoutename").setValue(route.routename);
		Ext.getCmp("routelevel2").setValue(route.routelevel);
		Ext.getCmp("state2").setValue(route.state);
		Ext.getCmp("startsite").setValue(route.startsite);
		Ext.getCmp("endsite").setValue(route.endsite);
		Ext.getCmp("sign").setValue(route.sign);
		
		var modifyRouteForm = Ext.getCmp("modifyRoute");
		routeGrid.modifyRouteWindow.show();
}

function addCable(route,rowIndex){
	var routeGrid = Ext.getCmp("routeGrid");
	if(!routeGrid.addCableWindow){
		routeGrid.addCableWindow = new com.increase.cen.route.routeWindow({
			id:"addCableWindow",
			width:500,
			height:600,
			title : '添加光缆段',
			iconCls : 'i-script-window',
			items :[{
				id : "addCable",
				xtype : "addCable"
			}]
		});
	}
	Ext.getCmp("addCable").getForm().reset();
	Ext.getCmp("newCable.routename").setValue(route.routename);
	Ext.getCmp("newCable.routeid").setValue(route.routeid);
	Ext.getCmp("rowno").setValue(rowIndex);
	routeGrid.addCableWindow.show();
}

function mergeCable(route,rowIndex){
	var routeGrid = Ext.getCmp("routeGrid");
	if (!routeGrid.mergeCableWindow) {
			// 合并光缆窗口
			routeGrid.mergeCableWindow = new com.increase.cen.route.routeWindow({
				id : "mergeCableWindow",
				width:700,
				title : '光缆段合并',
				iconCls : 'i-script-window',
				items : [{
					id : "mergeCable",
					xtype : 'mergeCable'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("mergeCable.newRoutename").setValue(route.routename);
		Ext.getCmp("rowno").setValue(rowIndex);
		routeGrid.mergeCableWindow.show();
		Ext.getCmp("cableItemSelector").fromStore.load({
			params : {
				'searchCable.routename' : route.routename
			}
		});// 表格加载数据
}
function modifyCable(cable,rowIndex){
	var routeGrid = Ext.getCmp("routeGrid");
	if (!routeGrid.modifyCableWindow) {
			// 合并光缆窗口
			routeGrid.modifyCableWindow = new com.increase.cen.route.routeWindow({
				id : "modifyCableWindow",
				width:500,
				title : '光缆段修改',
				iconCls : 'i-script-window',
				items : [{
					id : "modifyCable",
					xtype : 'modifyCable'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("modifyCable").getForm().reset();
		Ext.getCmp("updateCable.cableid").setValue(cable.cableid);
		Ext.getCmp("updateCable.routename").setValue(cable.routename);
		Ext.getCmp("updateCable.routeid").setValue(cable.routeid);
		Ext.getCmp("updateCable.cablename").setValue(cable.cablename);
		Ext.getCmp("updateCable.newCablename").setValue(cable.cablename);
		Ext.getCmp("updateCable.starteid").setValue(cable.starteid);
		Ext.getCmp("updateCable.startDeviceName").setValue(cable.startDeviceName);
		Ext.getCmp("updateCable.endeid").setValue(cable.endeid);
		Ext.getCmp("updateCable.endDeviceName").setValue(cable.endDeviceName);
		Ext.getCmp("updateCable.type").setValue(cable.type);
		Ext.getCmp("updateCable.mode").setValue(cable.mode);
		Ext.getCmp("updateCable.length").setValue(cable.length);
		Ext.getCmp("updateCable.paveWay").setValue(cable.paveWay);
		Ext.getCmp("updateCable.state1").setValue(cable.state);
		Ext.getCmp("updateCable.sharetype1").setValue(cable.sharetype);
		Ext.getCmp("updateCable.shareoperator").setValue(cable.shareoperator);
		Ext.getCmp("rowno").setValue(rowIndex);
		routeGrid.modifyCableWindow.show();
};
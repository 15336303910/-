Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
//		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, 
	{header : '管道ID',dataIndex : 'pipeId',hidden : true}, 
	{header : '管道名称',dataIndex : 'pipeName',width : 250,sortable : true}, 
	{header : '管道别名',dataIndex : 'alias',width : 150,sortable : true}, 
	{header : '管道级别',dataIndex : 'pipeLevel',width : 100,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "一干";
			}else if(v=='2'){
				return "二干";
			}else if(v=='3'){
				return "本地";
			}
		}
	}, 
	{header : '管道长度',dataIndex : 'length',width : 100,sortable : true}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!list.action"
	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'pipeId',type : 'string',mapping : 'pipeId',hidden : true
	}, {
		name : 'pipeName',
		type : 'string',
		mapping : 'pipeName'
	},{
		name : 'alias',
		type : 'string',
		mapping : 'alias'
	},{
		name : 'pipeLevel',
		type : 'string',
		mapping : 'pipeLevel'
	}, {
		name : 'length',
		type : 'string',
		mapping : 'length'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "pipes"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	this.levelStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
	});
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：管道管理",
		region : 'center',
		id : "pipeGrid",
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
				help("pages/help/pipe.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addPipeBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyPipeBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deletePipeBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'dwPipeBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'seePipesegBtn',
			text : '查看管道段列表',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'exportPipeBtn',
			text : '导出数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'importPipeBtn',
			text : '导入数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbspacer',
			width : 100
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '管道名称：'
		},{
			xtype : 'textfield',
			id:'pipeName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '管道别名：'
		},{
			xtype : 'textfield',
			id:'alias',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '管道级别：'
		},{
			id:'pipeLevel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.levelStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'pipeLevel'
			
		},{
			xtype : 'tbseparator'
		},{
			id : 'seekBtn',
			text : '搜索',
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
			limit : limit
		}
	});

	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	
	//获得添加按钮
	var addPipeBtn=Ext.getCmp("addPipeBtn");
	addPipeBtn.on('click',function(){
		grid.addPipeWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPipeWindow",
			title : '添加管道',
			iconCls : 'i-script-window',
			width:650,
			items : [{
				id : "addPipe",
				height : 400,
				xtype : 'addPipe'// 窗口里的表单
			}]
		});
		grid.addPipeWindow.show("addPipeBtn");
	});
	//获得修改按钮
	var modifyPipeBtn=Ext.getCmp("modifyPipeBtn");
	modifyPipeBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeId"));// 获得选择的编号
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
				
					icon : Ext.MessageBox.QUESTION,
					
							grid.modifyPipeWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyPipeWindow",
								title : '修改管道',
								iconCls : 'i-script-window',
								width:650,
								items : [{
									id : "modifyPipe",
									height : 400,
									xtype : 'modifyPipe'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyPipeWindow.show("modifyPipeBtn");
							// 表单加载数据
							var modifyPipeForm = Ext.getCmp("modifyPipe");
							modifyPipeForm.getForm().load({
								url : 'pipe!editpipe.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPipeId").setValue(json.pipeInfoBean.pipeId);
									Ext.getCmp("modifyPipeName").setValue(json.pipeInfoBean.pipeName);
									Ext.getCmp("modifyAlias").setValue(json.pipeInfoBean.alias);
									Ext.getCmp("modifyPipeLevel").setValue(json.pipeInfoBean.pipeLevel);
									Ext.getCmp("modifyLength").setValue(json.pipeInfoBean.length);
									Ext.getCmp("modifyStartDeviceId").setValue(json.pipeInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.pipeInfoBean.startDeviceName);
									Ext.getCmp("modifyEndDeviceId").setValue(json.pipeInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.pipeInfoBean.endDeviceName);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.pipeInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.pipeInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.pipeInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.pipeInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.pipeInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.pipeInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyProjectCode").setValue(json.pipeInfoBean.projectCode);
									Ext.getCmp("modifyProjectName").setValue(json.pipeInfoBean.projectName);
									Ext.getCmp("comboxWithTree").setValue(json.pipeInfoBean.areaname);
									if(json.pipeInfoBean.renewalExpirationDate!=null||json.pipeInfoBean.renewalExpirationDate!=""){
										var date =new Date(json.pipeInfoBean.renewalExpirationDate);
										Ext.getCmp("modifyRenewalExpirationDate").setValue(date);
									}
									if(json.pipeInfoBean.projectWarrantyExpireDate!=null||json.pipeInfoBean.projectWarrantyExpireDate!=""){
										var date =new Date(json.pipeInfoBean.projectWarrantyExpireDate);
										Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(date);
									}
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									//用于判断名称
									Ext.getCmp("oldPipeName").setValue(json.pipeInfoBean.pipeName);
									Ext.getCmp("modifyPipeId").setValue(json.pipeInfoBean.pipeId);
									Ext.getCmp("modifyPipeName").setValue(json.pipeInfoBean.pipeName);
									Ext.getCmp("modifyAlias").setValue(json.pipeInfoBean.alias);
									Ext.getCmp("modifyPipeLevel").setValue(json.pipeInfoBean.pipeLevel);
									Ext.getCmp("modifyLength").setValue(json.pipeInfoBean.length);
									Ext.getCmp("modifyStartDeviceId").setValue(json.pipeInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.pipeInfoBean.startDeviceName);
									Ext.getCmp("modifyStartDeviceType").setValue(json.pipeInfoBean.startDeviceType);
									Ext.getCmp("modifyEndDeviceId").setValue(json.pipeInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.pipeInfoBean.endDeviceName);
									Ext.getCmp("modifyEndDeviceType").setValue(json.pipeInfoBean.endDeviceType);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.pipeInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.pipeInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.pipeInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.pipeInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.pipeInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.pipeInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyProjectCode").setValue(json.pipeInfoBean.projectCode);
									Ext.getCmp("modifyProjectName").setValue(json.pipeInfoBean.projectName);
									Ext.getCmp("comboxWithTree").setValue(json.pipeInfoBean.areaname);
									if(json.pipeInfoBean.renewalExpirationDate!=null||json.pipeInfoBean.renewalExpirationDate!=""){
										var date =new Date(json.pipeInfoBean.renewalExpirationDate);
										Ext.getCmp("modifyRenewalExpirationDate").setValue(date);
									}
									if(json.pipeInfoBean.projectWarrantyExpireDate!=null||json.pipeInfoBean.projectWarrantyExpireDate!=""){
										var date =new Date(json.pipeInfoBean.projectWarrantyExpireDate);
										Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(date);
									}
								}
							});
					
			}
		}else{
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
	});
	//获取删除按钮
	var deletePipeBtn=Ext.getCmp("deletePipeBtn");
	deletePipeBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeId"));
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行删除!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
			Ext.MessageBox.show({// 提示框
				title : '确认',
				msg : '您确定要删除选择的记录吗?',
				width : 300,
				multiline : false,// 设置为true，提示用户输入多行文本
				closable : false,
				buttons : {
					yes : '确定',
					no : '取消'
				},
				icon : Ext.MessageBox.QUESTION,
				fn : function(btn) {
					if (btn == "yes") {
						Ext.Ajax.request({
							url : 'pipe!dodelete.action',
							method : 'post',
							params : {
								ids : ids
							},
							success : function(response) {
								var json = Ext.util.JSON.decode(response.responseText);
								if (json.success == true) {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
												+ json.deleteMsg,
										buttons : {
											ok : "确定"
										}
									});
								} else {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
												+ '删除管道信息失败!',
										buttons : {
											ok : "确定"
										}
									});
								}
								grid.getStore().remove(records);
								grid.getStore().load({
									params : {
										start : 0,
										limit : limit
									}
								});
								grid.getView().refresh();
							},
							failure : function() {
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '删除管道信息失败!',
									buttons : {
										ok : "确定"
									}
								});
							}
						});
					}
				}
			});
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想删除的数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	var dwPipeBtn=Ext.getCmp("dwPipeBtn");
	dwPipeBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeId"));
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行定位!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
			var url=context_path+"/pipe!pipeDW.action?ids="+ids;
		    var iWidth=699; //弹出窗口的宽度;
			var iHeight=552; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
			var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
			window.open(url,"newwindow",param);
			}
		}else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想定位的数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	//查看管道段列表
	var seePipesegBtn=Ext.getCmp("seePipesegBtn");
	seePipesegBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeId"));// 获得选择的编号
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
				var pipeGrid=Ext.getCmp("pipeGrid");
				if(!pipeGrid.seePipesegWindow){
				pipeGrid.seePipesegWindow=new com.increase.cen.pipe.pipeWindow({
					id : "seePipesegWindow",
					height : 400,
					width : 1000,
					title : '查看管道段',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'pipeSegmentGrid'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("pipeSegmentGrid").getStore();
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
						"pipeSegmentInfoBean.pipeId":ids[0],
						start : 0,
						limit : limit
					}
				});
				store.on('beforeload',function(){
					store.baseParams = {
				   			start : 0,
							limit : limit,
							"pipeSegmentInfoBean.pipeId":ids[0]
				   	};
				});
	
				pipeGrid.seePipesegWindow.show();
				
				pipeGrid.seePipesegWindow.on('beforeclose',function(){
//					this.Ext.getCmp("pipeSegmentGrid1").destory();
					Ext.getCmp("pipeSegmentGrid").getStore().removeAll();
					
				});
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
	
	var exportPipeBtn=Ext.getCmp("exportPipeBtn");
	exportPipeBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getPipelist.action?ids="+ids;
			
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
	
	
	
	// 获得刷新按钮
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("pipeName").setValue("");
		Ext.getCmp("alias").setValue("");
		Ext.getCmp("pipeLevel").setValue("");
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
	//获得搜索按钮
	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var pipename = Ext.getCmp("pipeName").getValue();
		var pipelevel = Ext.getCmp("pipeLevel").getValue();
		var alias = Ext.getCmp("alias").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'pipeInfoBean.pipeName' : pipename,
				'pipeInfoBean.alias' : alias,
				'pipeInfoBean.pipeLevel' : pipelevel
			}
		});
		grid.getStore().on('beforeload',function(){
   			grid.getStore().baseParams = {
   				'pipeInfoBean.pipeName' : pipename,
   				'pipeInfoBean.alias' : alias,
				'pipeInfoBean.pipeLevel' : pipelevel
   			};
		});
		grid.getView().refresh();
	});
	
	
	var importPipeBtn = Ext.getCmp("importPipeBtn");
	importPipeBtn.on('click', function(){
			grid.importPipeWindow = new com.increase.cen.pipe.pipeWindow({
				id : "importPipeWindow",
				height:500,
				width:600,
				title : '数据导入',
				iconCls : 'i-script-window',
				items : [{
					id:'importPipe',
					fileUpload : true,
					height:300,
					xtype : 'importPipe'// 窗口里的表单
				}]
			});
		
		grid.importPipeWindow.show();
	});
})
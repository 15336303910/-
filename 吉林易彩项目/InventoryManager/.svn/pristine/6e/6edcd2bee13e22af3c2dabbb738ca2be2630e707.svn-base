Ext.onReady(function() {
	Ext.useShims = true;
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, {
		header : 'did',
		dataIndex : 'did',
		hidden : true
	}, {
		header : '档案名称',
		dataIndex : 'filename',
		width : 100
	}, {
		header : '设备名称',
		dataIndex : 'ename',
		width : 100
	}, {
		header : '设备编码',
		dataIndex : 'ecode',
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
	}, {
		header : '上传人员',
		dataIndex : 'mpname',
		width : 80
	}, {
		header : '上传时间',
		dataIndex : 'mtime',
		width : 130,
		sortable : true,
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i')
	}, {
		header : '所属地区',
		dataIndex : 'areaname',
		width : 70
	}, {
		header : '端子总数',
		dataIndex : 'pcount',
		width : 60
	}]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "document!loadDocEqutGrid.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
		name : 'did',
		type : 'string',
		mapping : 'did'
	}, {
		name : 'filename',
		type : 'string',
		mapping : 'filename'
	}, {
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
		name : 'mpname',
		type : 'string',
		mapping : 'mpname'
	}, {
		name : 'mtime',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'mtime'
	}, {
		name : 'areaname',
		type : 'string',
		mapping : 'areaname'
	}, {
		name : 'pcount',
		type : 'string',
		mapping : 'pcount'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "docEquts"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	var domainName = Ext.getDom("domainName").value;
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：" + domainName + "档案管理",
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
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addBtn',
			icon : "js/ext/resources/images/default/dd/drop-add.gif",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'deleteBtn',
			text : '删除',
			icon : "imgs/deleteBtn.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'queryBtn',
			text : '查询',
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'checkpointGridBtn',
			text : '查看端子',
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '资管数据导入',
			id : 'dataImportBtn',
			icon : "js/ext/resources/images/default/dd/drop-add.gif",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '生成配置工单',
			id : 'addBtns',
			icon : "js/ext/resources/images/default/dd/drop-add.gif",
			cls : "x-btn-text-icon"

		}/*, {
				// xtype:'label',
				// x:200,
				// text:'请输入关键词：'
				}, {
				// xtype:'textfield',id:'KeyWord'
				}*/],
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
			"searchdequt.areano" : Ext.getDom("domainCode").value
		}
	});
	// 解决分页点击下一页时不带参数问题
	store.on('beforeload', function() {
		store.baseParams = {
			"searchdequt.areano" : Ext.getDom("domainCode").value
		};
	});
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	// var view = Ext.getCmp("view")

	// 获得增加按钮,导入档案
	var addBtn = Ext.getCmp("addBtn");
	// 增加按钮的click事件
	addBtn.on('click', function() {
	
			// 新建添加窗口
			if (!grid.addDocumentWindow) {
			grid.addDocumentWindow = new com.increase.cen.document.DocumentWindow({
				id : "addDocumentWindow",
				height:500,
				title : '导入新档案',
				iconCls : 'i-script-window',
				items : [{
					id : "addDocument",
					fileUpload : true,
					autoScroll:true,
					height:500,
					xtype : 'addDocument'// 窗口里的表单
				}]
			});
		}
		//Ext.getCmp("uploadAreano").setValue(Ext.getDom("domainCode").value);
		grid.addDocumentWindow.show();
	});

	// 获得删除按钮，删除员工信息
	var deleteBtn = Ext.getCmp("deleteBtn");
	// 按钮加click监听事件
	deleteBtn.on('click', function() {
		// 返回grid的SelectionModel
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			Ext.MessageBox.show({// 提示框
				title : '确认',
				msg : '您确定要删除选择的记录吗?',
				width : 300,
				multiline : false,// 设置为true，提示档案输入多行文本
				closable : false,
				buttons : {
					yes : '确定',
					no : '取消'
				},
				icon : Ext.MessageBox.QUESTION,
				fn : function(btn) {
					if (btn == "yes") {
						var records = selModel.getSelections();// 返回所有选中的记录
						var ids = [];
						for (var i = 0; i < records.length; i++) {
							ids.push(records[i].get("did"));
						}
						Ext.Ajax.request({
							url : 'document!deleteDocument.action',
							method : 'post',
							params : {
								ids : ids
							},
							success : function(response) {
								var json = Ext.util.JSON
										.decode(response.responseText);
								if (json.success == true) {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
												+ json.verifyMsg,
										buttons : {
											ok : "确定"
										}
									});
								} else {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
												+ '删除档案信息失败!',
										buttons : {
											ok : "确定"
										}
									});
								}
								grid.getStore().remove(records);
								grid.getStore().load({
									params : {
										start : 0,
										limit : limit,
										"searchdequt.areano" : Ext
												.getDom("domainCode").value
									}
								});
								grid.getView().refresh();
							},
							failure : function() {
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '删除档案信息失败!',
									buttons : {
										ok : "确定"
									}
								});
							}
						});
					}
				}
			});
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
	
	//生成配置工单按钮
	var addBtns = Ext.getCmp("addBtns");
	addBtns.on('click', function() {
		if (!grid.addDocumentWindow2) {
				// 新建添加窗口
				grid.addDocumentWindow2 = new com.increase.cen.document.DocumentWindow({
					id : "addDocumentWindow2",
					height:500,
					title : '生成配置工单',
					iconCls : 'i-script-window',
					items : [{
						id : "addDocmentWorkerorder",
						fileUpload : true,
						autoScroll:true,
						height:500,
						xtype : 'addDocmentWorkerorder'// 窗口里的表单
					}]
				});
			}
		//Ext.getCmp("uploadAreano").setValue(Ext.getDom("domainCode").value);
		grid.addDocumentWindow2.show();
		
	});
	
	
	
	// 获得查询按钮，查询档案信息
	var queryBtn = Ext.getCmp("queryBtn");
	queryBtn.on('click', function() {
		// 创建查询窗口
		if (!grid.queryDocWindow) {
			grid.queryDocWindow = new com.increase.cen.document.DocumentWindow({
				id : "queryDocWindow",
				title : '查询档案信息',
				iconCls : 'i-script-window',
				items : [{
					id : "queryDoc",
					xtype : 'queryDoc'
				}]
			});
		}

		Ext.getCmp("queryDoc").getForm().reset();
		Ext.getCmp("queryAreano").setValue(Ext.getDom("domainCode").value);
		// 显示查询窗口
		grid.queryDocWindow.show("queryBtn");

	});

	var checkpointGridBtn = Ext.getCmp("checkpointGridBtn");
	checkpointGridBtn.on('click', function() {
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("did"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行查看!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				if (!grid.docPointWindow) {
					grid.docPointWindow = new com.increase.cen.document.DocumentWindow({
						id : "docPointWindow",
						title : '查看端子信息',
						iconCls : 'i-script-window',
						width : 1100,
						items : [{
							height : 320,
							xtype : 'docPointGrid'// 窗口里的修改表单

						}]
					});
				}
				var store = Ext.getCmp("docPoint").getStore();
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
						limit : 12,
						"searchdpoint.did" : ids[0]
					}
				});
				// 解决分页点击下一页时不带参数问题
				store.on('beforeload', function() {
					store.baseParams = {
						"searchdpoint.did" : ids[0]
					};
				});
				grid.docPointWindow.show();
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择一条您要查看的档案!',
				buttons : {
					ok : "确定"
				}

			});
		}
	})

	// 获得刷新按钮，刷新全部档案信息
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		store.on('beforeload', function() {
			store.baseParams = {
				start : 0,
				limit : limit,
				"searchdequt.areano" : Ext.getDom("domainCode").value
			};
		});
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				"searchdequt.areano" : Ext.getDom("domainCode").value
			}
		});
		grid.getView().refresh();
	});
	
	var dataImportBtn = Ext.getCmp("dataImportBtn");
	dataImportBtn.on('click', function(){
		if (!grid.dataImportWindow) {
			grid.dataImportWindow = new com.increase.cen.document.DocumentWindow({
				id : "dataImportWindow",
				height:500,
				title : '资管数据导入',
				iconCls : 'i-script-window',
				items : [{
					id : "dataImport",
					fileUpload : true,
					autoScroll:true,
					height:500,
					xtype : 'dataImport'// 窗口里的表单
				}]
			});
		}
		grid.dataImportWindow.show();
	});
});

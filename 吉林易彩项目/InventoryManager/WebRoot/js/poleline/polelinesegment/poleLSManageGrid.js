Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
	{header : '杆路段ID',dataIndex : 'poleLineSegmentId',hidden : true}, 
	{header : '杆路段名称',dataIndex : 'poleLineSegmentName',width : 350,sortable : true}, 
	{header : '杆路段编码',dataIndex : 'poleLineSegmentCode',width : 180,sortable : true}, 
	{header : '所属维护区域',dataIndex : 'maintenanceAreaName',width : 180,sortable : true}, 
	{header : '杆路段别名',dataIndex : 'poleLineSegmentBieName',width : 180,sortable : true},
	{header : '资产标签号',dataIndex : 'assetTag',width : 180,sortable : true},
	{header : '状态',dataIndex : 'status',width : 100,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "空闲";
			}else if(v=='2'){
				return "在用";
			}else if(v=='3'){
				return "出租";
			}
		}
	}, 
	{header : '杆路段长度（米）',dataIndex : 'poleLineSegmentLength',width : 180,sortable : true}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true},
	{header : '生命周期时间',dataIndex : 'lifecycleTime',width : 200,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!polelslist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'poleLineSegmentId',type : 'string',mapping : 'poleLineSegmentId',hidden : true
	}, {
		name : 'poleLineSegmentName',
		type : 'string',
		mapping : 'poleLineSegmentName'
	}, {
		name : 'poleLineSegmentCode',
		type : 'string',
		mapping : 'poleLineSegmentCode'
	}, {		
		name : 'maintenanceAreaName',
		type : 'string',
		mapping : 'maintenanceAreaName'
	}, {
		name : 'poleLineSegmentBieName',
		type : 'string',
		mapping : 'poleLineSegmentBieName'
	}, {
		name : 'assetTag',
		type : 'string',
		mapping : 'assetTag'
	}, {
		name : 'status',
		type : 'string',
		mapping : 'status'
	}, {
		name : 'poleLineSegmentLength',
		type : 'string',
		mapping : 'poleLineSegmentLength'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	},{
		name : 'lifecycleTime',
		mapping : 'lifecycleTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "polelinesegments"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	this.statusStore=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '空闲'], ['2', '在用'], ['3', '占用']]
    	})
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：杆路段管理",
		region : 'center',
		id : "poleLSGrid",
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
				help("pages/help/polelinesegment.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addPoleLSBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyPoleLSBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deletePoleLSBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {	
			id : 'importBtn',
			text : '上传',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {		
			xtype : 'tbseparator'
		}, {		
			id : 'exportBtn',
			text : '下载',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'dwPoleLSBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshPoleLSBtn',
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
			text : '杆路段名称：'
		},{
			xtype : 'textfield',
			id:'poleLineSegmentName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '杆路段编码：'
		},{
			xtype : 'textfield',
			id:'poleLineSegmentCode',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '状态：'
		},{
			id:'status',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.statusStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'status'
			
		},/*{
			xtype : 'tbseparator'
		},
		{
			xtype:'label',
			text:'装机起始时间：'
		},{
			id:'createtimebegin',
			xtype : "datefield",
			// format : 'yyyy-MM-dd hh:mm:ss',//日期格式
			format : 'Y-m-d',// 日期格式
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			cls :'order-sel-textfield-width',
			listeners:{
				'select':function(v){
					var end = Ext.getCmp("createtimeend").getValue();
					if(end != null && end != ""){
						if(new Date(end) < new Date(v.getValue())){
							v.setValue("");
						}
					}
				}
			}
		}, {
			xtype : 'tbseparator'
		}, {
			xtype:'label',
			text:'装机结束时间：'
		},{
			id:'createtimeend',
			xtype : "datefield",
			// format : 'yyyy-MM-dd hh:mm:ss',//日期格式
			format : 'Y-m-d',// 日期格式
			cls :'order-sel-textfield-width',
			invalidText : '结束时间必须大于开始时间！',
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			listeners:{
				'select' : function(v) {
					var begin = Ext.getCmp("createtimebegin").getValue();
					if(begin!=null && begin!=""){
						if(new Date(begin) > new Date(v.getValue())){
							v.setValue("");
						}
					}
				}
			
			}
		}, */{
			xtype : 'tbseparator'
		},{
			id : 'seekPoleLSBtn',
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
	
	//获得添加电杆按钮
	var addPoleLSBtn=Ext.getCmp("addPoleLSBtn");
	addPoleLSBtn.on('click',function(){
		grid.addPoleLSWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPoleLSWindow",
			title : '添加杆路段',
			iconCls : 'i-script-window',
			width:600,
			items : [{
				id : "addPoleLS",
				height : 440,
				xtype : 'addPoleLS'// 窗口里的表单
			}]
		});
		grid.addPoleLSWindow.show("addPoleLSBtn");
	});
	//获得修改按钮
	var modifyPoleLSBtn=Ext.getCmp("modifyPoleLSBtn");
	modifyPoleLSBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineSegmentId"));// 获得选择的编号
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
							grid.modifyPoleLSWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyPoleLSWindow",
								title : '修改杆路段',
								iconCls : 'i-script-window',
								width:600,
								items : [{
									id : "modifyPoleLS",
									height : 420,
									xtype : 'modifyPoleLS'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyPoleLSWindow.show("modifyPoleLSBtn");
							// 表单加载数据
							var modifyPoleLSForm = Ext.getCmp("modifyPoleLS");
							modifyPoleLSForm.getForm().load({
								url : 'poleline!editpolelinesegment.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPoleLineSegmentId").setValue(json.polelineSegmentInfoBean.poleLineSegmentId);
									Ext.getCmp("modifyPoleLineSegmentName").setValue(json.polelineSegmentInfoBean.poleLineSegmentName);
									Ext.getCmp("modifyPLSName").setValue(json.polelineSegmentInfoBean.poleLineSegmentName);
									Ext.getCmp("modifyPoleLineSegmentLength").setValue(json.polelineSegmentInfoBean.poleLineSegmentLength);
									Ext.getCmp("modifyPoleLineId").setValue(json.polelineSegmentInfoBean.poleLineId);
									Ext.getCmp("modifyPoleLineName").setValue(json.polelineSegmentInfoBean.poleLineName);
									Ext.getCmp("modifyPoleLineSegmentCode").setValue(json.polelineSegmentInfoBean.poleLineSegmentCode);
									Ext.getCmp("modifyStatus").setValue(json.polelineSegmentInfoBean.status);
									Ext.getCmp("modifyStartDeviceId").setValue(json.polelineSegmentInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.polelineSegmentInfoBean.startDeviceName);
									Ext.getCmp("modifyEndDeviceId").setValue(json.polelineSegmentInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.polelineSegmentInfoBean.endDeviceName);
									Ext.getCmp("modifyConstructionSharingEnumId").setValue(json.polelineSegmentInfoBean.constructionSharingEnumId);
									Ext.getCmp("modifyConstructionSharingOrg").setValue(json.polelineSegmentInfoBean.constructionSharingOrg);
									Ext.getCmp("modifySharingTypeEnumId").setValue(json.polelineSegmentInfoBean.sharingTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.polelineSegmentInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("comboxWithTree").setValue(json.polelineSegmentInfoBean.areaname);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPoleLineSegmentId").setValue(json.polelineSegmentInfoBean.poleLineSegmentId);
									Ext.getCmp("modifyPoleLineSegmentName").setValue(json.polelineSegmentInfoBean.poleLineSegmentName);
									Ext.getCmp("modifyPLSName").setValue(json.polelineSegmentInfoBean.poleLineSegmentName);
									Ext.getCmp("modifyPoleLineSegmentLength").setValue(json.polelineSegmentInfoBean.poleLineSegmentLength);
									Ext.getCmp("modifyPoleLineId").setValue(json.polelineSegmentInfoBean.poleLineId);
									Ext.getCmp("modifyPoleLineName").setValue(json.polelineSegmentInfoBean.poleLineName);
									Ext.getCmp("modifyPoleLineSegmentCode").setValue(json.polelineSegmentInfoBean.poleLineSegmentCode);
									Ext.getCmp("modifyStatus").setValue(json.polelineSegmentInfoBean.status);
									Ext.getCmp("modifyStartDeviceId").setValue(json.polelineSegmentInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.polelineSegmentInfoBean.startDeviceName);
									Ext.getCmp("modifyEndDeviceId").setValue(json.polelineSegmentInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.polelineSegmentInfoBean.endDeviceName);
									Ext.getCmp("modifyConstructionSharingEnumId").setValue(json.polelineSegmentInfoBean.constructionSharingEnumId);
									Ext.getCmp("modifyConstructionSharingOrg").setValue(json.polelineSegmentInfoBean.constructionSharingOrg);
									Ext.getCmp("modifySharingTypeEnumId").setValue(json.polelineSegmentInfoBean.sharingTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.polelineSegmentInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyAreano").setValue(json.polelineSegmentInfoBean.areano);
									Ext.getCmp("modifyAreaname").setValue(json.polelineSegmentInfoBean.areaname);
								}
							});
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
	var deletePoleLSBtn=Ext.getCmp("deletePoleLSBtn");
	deletePoleLSBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineSegmentId"));
			}
//			Ext.Ajax.request({
//				url : 'poleline!searchPlsToCable.action',
//				method : 'post',
//				params : {
//					ids : ids
//				},
//				success : function(response) {
//					var json = Ext.util.JSON.decode(response.responseText);
//					if (json.success == true) {
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
										url : 'poleline!dodeletepls.action',
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
															+ '删除杆路段信息失败!',
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
														+ '删除杆路段信息失败!',
												buttons : {
													ok : "确定"
												}
											});
										}
									});
								}
							}
						});
//					}else{
//						Ext.Msg.show({
//							title : '提示',
//							width : 300,
//							msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ '请先解除杆路段与光缆的关系！',
//							buttons : {
//								ok : "确定"
//							}
//						});
//					}
//				}
//			});
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
	var dwPoleLSBtn=Ext.getCmp("dwPoleLSBtn");
	dwPoleLSBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineSegmentId"));
			}
			var url=context_path+"/poleline!polelinesegmentlistDW.action?ids="+ids;
			    var iWidth=699; //弹出窗口的宽度;
				var iHeight=552; //弹出窗口的高度;
				var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
				var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
				window.open(url,"newwindow",param);
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想查看定位的数据!',
				buttons : {
					ok : "确定"
				}

			});
		}
	});
	// 获得刷新按钮
	var refreshPoleLSBtn = Ext.getCmp("refreshPoleLSBtn");
	refreshPoleLSBtn.on('click', function() {
		Ext.getCmp("poleLineSegmentName").setValue("");
		Ext.getCmp("poleLineSegmentCode").setValue("");
		Ext.getCmp("status").setValue("");
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
	//获得上传按钮
	var importBtn = Ext.getCmp("importBtn");
	importBtn.on('click', function(){
		grid.importWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "importWindow",
			height:500,
			width:600,
			title : '上传',
			iconCls : 'i-script-window',
			items : [{
				id:'importpoleLSManage',
				fileUpload : true,
				height:300,
				xtype : 'importpoleLSManage'// 窗口里的表单
			}]
			
		});
		grid.importWindow.show("importBtn");
	});
	//获得下载按钮
	var exportBtn=Ext.getCmp("exportBtn");
	exportBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineSegmentId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getpoleLineSegmentlist.action?ids="+ids;
			
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
	//获得搜索按钮
	var seekPoleLSBtn = Ext.getCmp("seekPoleLSBtn");
	seekPoleLSBtn.on('click', function() {
		var polelsname = Ext.getCmp("poleLineSegmentName").getValue();
		var polelscode = Ext.getCmp("poleLineSegmentCode").getValue();
		var status = Ext.getCmp("status").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'polelineSegmentInfoBean.poleLineSegmentName' : polelsname,
				'polelineSegmentInfoBean.poleLineSegmentCode' : polelscode,
				'polelineSegmentInfoBean.status' : status
			}
		});
		grid.getStore().on('beforeload',function(){
   				grid.getStore().baseParams = {
   					'polelineSegmentInfoBean.poleLineSegmentName' : polelsname,
					'polelineSegmentInfoBean.poleLineSegmentCode' : polelscode,
					'polelineSegmentInfoBean.status' : status
   				};
		});
		grid.getView().refresh();
	});

})
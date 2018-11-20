Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, 
	{header : '井ID',dataIndex : 'wellId',hidden : true}, 
	{header : '人/手井名称',dataIndex : 'wellName',width : 250,sortable : true}, 
	{header : '井编号',dataIndex : 'wellNo',width : 100,sortable : true}, 
	{header : '井地址',dataIndex : 'wellAddress',width : 250,sortable : true}, 
	{header : '井经度',dataIndex : 'longitude',width : 250,sortable : true}, 
	{header : '井纬度',dataIndex : 'latitude',width : 250,sortable : true}, 
	{header : '接头盒名称',dataIndex : 'jointName',width : 250,sortable : true}, 
	{header : '创建时间',dataIndex : 'creationDate',width : 250,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')}, 
	
	{header : '所属维护区域',dataIndex : 'region',width : 200,sortable : true}, 
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!welllist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'wellId',type : 'string',mapping : 'wellId',hidden : true
	}, {
		name : 'wellName',
		type : 'string',
		mapping : 'wellName'
	},{
		name : 'wellNo',
		type : 'string',
		mapping : 'wellNo'
	}, {
		name : 'wellAddress',
		type : 'string',
		mapping : 'wellAddress'
	}, {
		name : 'longitude',
		type : 'string',
		mapping : 'longitude'
	},{
		name : 'latitude',
		type : 'string',
		mapping : 'latitude'
	},{
		name : 'jointName',
		type : 'string',
		mapping : 'jointName'
	},{
		name : 'creationDate',
		mapping : 'creationDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'region',
		type : 'string',
		mapping : 'region'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "wells"
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
	})
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：井管理",
		region : 'center',
		id : "wellGrid",
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
				help("pages/help/well.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addWellBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyWellBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deleteWellBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'wellFacadeBtn',
			text : '井立面管理',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'dwWellBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'exportWellBtn',
			text : '导出井',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'importWellBtn',
			text : '导入井',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'exportTubeBtn',
			text : '导出管孔',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon",
			handler : function() {
				var selModel = Ext.getCmp("wellGrid").getSelectionModel();// 返回grid的SelectionModel
				if (selModel.hasSelection()) {
					var records = selModel.getSelections();
					var ids = [];
					for (var i = 0; i < records.length; i++) {
						ids.push(records[i].get("wellId"));// 获得选择的编号
					}
					location.href="dataupdate!getTubelist.action?ids="+ids;
					
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
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		},{
			id : 'importTubeBtn',
			text : '导入管孔',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon",
			handler : function() {
				grid.importTubeWindow = new com.increase.cen.poleline.PolelineWindow({
					id : "importTubeWindow",
					height:500,
					title : '数据导入',
					iconCls : 'i-script-window',
					items : [{
						id:'importTube',
						fileUpload : true,
						width:600,
						height:300,
						xtype : 'importTube'// 窗口里的表单
					}]
				});
			
			grid.importTubeWindow.show();
			}
		},{
			xtype : 'tbspacer',
			width : 100
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '人/手井名称：'
		},{
			xtype : 'textfield',
			id:'wellNameSub',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '人/手井地址：'
		},{
			xtype : 'textfield',
			id:'wellAddress',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '井类型：'
		},{
			id:'manholeTypeEnumId',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.typeStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'manholeTypeEnumId'
			
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
	
	//获得添加电杆按钮
	var addWellBtn=Ext.getCmp("addWellBtn");
	addWellBtn.on('click',function(){
		grid.addWellWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addWellWindow",
			title : '添加井',
			iconCls : 'i-script-window',
			width:600,
			items : [{
				id : "addWell",
				height : 500,
				xtype : 'addWell'// 窗口里的表单
			}]
		});
		grid.addWellWindow.show("addWellBtn");
	});
	//获得修改按钮
	var modifyWellBtn=Ext.getCmp("modifyWellBtn");
	modifyWellBtn.on('click',function(){
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
							grid.modifyWellWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyWellWindow",
								title : '修改井',
								iconCls : 'i-script-window',
								width:600,
								items : [{
									id : "modifyWell",
									height : 500,
									xtype : 'modifyWell'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyWellWindow.show("modifyWellBtn");
							// 表单加载数据
							var modifyWellForm = Ext.getCmp("modifyWell");
							modifyWellForm.getForm().load({
								url : 'pipe!editwell.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyWellId").setValue(json.wellInfoBean.wellId);
									Ext.getCmp("modifyWellName").setValue(json.wellInfoBean.wellName);
									Ext.getCmp("modifyDirection").setValue(json.wellInfoBean.direction);
									Ext.getCmp("modifyWellNo").setValue(json.wellInfoBean.wellNo);
									Ext.getCmp("modifyWellSubNo").setValue(json.wellInfoBean.wellSubNo);
									Ext.getCmp("modifyAlias").setValue(json.wellInfoBean.alias);
									Ext.getCmp("modifyManholeTypeEnumId").setValue(json.wellInfoBean.manholeTypeEnumId);
									Ext.getCmp("modifyLongitude").setValue(json.wellInfoBean.longitude);
									Ext.getCmp("modifyLatitude").setValue(json.wellInfoBean.latitude);
									Ext.getCmp("modifyCoverTopElevation").setValue(json.wellInfoBean.coverTopElevation);
									Ext.getCmp("modifyBottomAltitude").setValue(json.wellInfoBean.bottomAltitude);
									Ext.getCmp("modifyWellAddress").setValue(json.wellInfoBean.wellAddress);
									Ext.getCmp("modifyManholeDepth").setValue(json.wellInfoBean.manholeDepth);
									Ext.getCmp("modifyManholeLength").setValue(json.wellInfoBean.manholeLength);
									Ext.getCmp("modifyManholeWidth").setValue(json.wellInfoBean.manholeWidth);
									Ext.getCmp("modifyManholeShape").setValue(json.wellInfoBean.manholeShape);
									Ext.getCmp("modifyManholeSpecification").setValue(json.wellInfoBean.manholeSpecification);
									Ext.getCmp("modifyManholeStructure").setValue(json.wellInfoBean.manholeStructure);
									Ext.getCmp("modifyRoadTypeEnumId").setValue(json.wellInfoBean.roadTypeEnumId);
									Ext.getCmp("modifyRoadSurfaceTypeEnumId").setValue(json.wellInfoBean.roadSurfaceTypeEnumId);
									Ext.getCmp("modifyCoverMaterialEnumId").setValue(json.wellInfoBean.coverMaterialEnumId);
									Ext.getCmp("modifyCoverShapeEnumId").setValue(json.wellInfoBean.coverShapeEnumId);
									Ext.getCmp("modifyCoverQuantity").setValue(json.wellInfoBean.coverQuantity);
									Ext.getCmp("modifyWallThickness").setValue(json.wellInfoBean.wallThickness);
									Ext.getCmp("modifySubbaseThickness").setValue(json.wellInfoBean.subbaseThickness);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.wellInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.wellInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.wellInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.wellInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.wellInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.wellInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("comboxWithTree").setValue(json.wellInfoBean.areaname);
									if(json.wellInfoBean.renewalExpirationDate!=null||json.wellInfoBean.renewalExpirationDate!=""){
										var date =new Date(json.wellInfoBean.renewalExpirationDate);
										Ext.getCmp("modifyRenewalExpirationDate").setValue(date);
									}
									if(json.wellInfoBean.projectWarrantyExpireDate!=null||json.wellInfoBean.projectWarrantyExpireDate!=""){
										var date =new Date(json.wellInfoBean.projectWarrantyExpireDate);
										Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(date);
									}
									Ext.getCmp("modifyResponsiblePersonId").setValue(json.wellInfoBean.responsiblePersonId);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									//用于判断名称
									Ext.getCmp("oldWellName").setValue(json.wellInfoBean.wellName);
									Ext.getCmp("oldWellNo").setValue(json.wellInfoBean.wellNo);
									Ext.getCmp("oldWellSubNo").setValue(json.wellInfoBean.wellSubNo);
									Ext.getCmp("oldDirection").setValue(json.wellInfoBean.direction);
									Ext.getCmp("modifyWellId").setValue(json.wellInfoBean.wellId);
									Ext.getCmp("modifyWellName").setValue(json.wellInfoBean.wellName);
									Ext.getCmp("modifyDirection").setValue(json.wellInfoBean.direction);
									Ext.getCmp("modifyWellNo").setValue(json.wellInfoBean.wellNo);
									Ext.getCmp("modifyWellSubNo").setValue(json.wellInfoBean.wellSubNo);
									Ext.getCmp("modifyAlias").setValue(json.wellInfoBean.alias);
									Ext.getCmp("modifyManholeTypeEnumId").setValue(json.wellInfoBean.manholeTypeEnumId);
									Ext.getCmp("modifyLongitude").setValue(json.wellInfoBean.longitude);
									Ext.getCmp("modifyLatitude").setValue(json.wellInfoBean.latitude);
									Ext.getCmp("modifyCoverTopElevation").setValue(json.wellInfoBean.coverTopElevation);
									Ext.getCmp("modifyBottomAltitude").setValue(json.wellInfoBean.bottomAltitude);
									Ext.getCmp("modifyWellAddress").setValue(json.wellInfoBean.wellAddress);
									Ext.getCmp("modifyManholeDepth").setValue(json.wellInfoBean.manholeDepth);
									Ext.getCmp("modifyManholeLength").setValue(json.wellInfoBean.manholeLength);
									Ext.getCmp("modifyManholeWidth").setValue(json.wellInfoBean.manholeWidth);
									Ext.getCmp("modifyManholeShape").setValue(json.wellInfoBean.manholeShape);
									Ext.getCmp("modifyManholeSpecification").setValue(json.wellInfoBean.manholeSpecification);
									Ext.getCmp("modifyManholeStructure").setValue(json.wellInfoBean.manholeStructure);
									Ext.getCmp("modifyRoadTypeEnumId").setValue(json.wellInfoBean.roadTypeEnumId);
									Ext.getCmp("modifyRoadSurfaceTypeEnumId").setValue(json.wellInfoBean.roadSurfaceTypeEnumId);
									Ext.getCmp("modifyCoverMaterialEnumId").setValue(json.wellInfoBean.coverMaterialEnumId);
									Ext.getCmp("modifyCoverShapeEnumId").setValue(json.wellInfoBean.coverShapeEnumId);
									Ext.getCmp("modifyCoverQuantity").setValue(json.wellInfoBean.coverQuantity);
									Ext.getCmp("modifyWallThickness").setValue(json.wellInfoBean.wallThickness);
									Ext.getCmp("modifySubbaseThickness").setValue(json.wellInfoBean.subbaseThickness);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.wellInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.wellInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.wellInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.wellInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.wellInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.wellInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyProjectNumber").setValue(json.wellInfoBean.projectNumber);
									Ext.getCmp("modifyProjectName").setValue(json.wellInfoBean.projectName);
									Ext.getCmp("comboxWithTree").setValue(json.wellInfoBean.areaname);
									if(json.wellInfoBean.renewalExpirationDate!=null||json.wellInfoBean.renewalExpirationDate!=""){
										var date =new Date(json.wellInfoBean.renewalExpirationDate);
										Ext.getCmp("modifyRenewalExpirationDate").setValue(date);
									}
									if(json.wellInfoBean.projectWarrantyExpireDate!=null||json.wellInfoBean.projectWarrantyExpireDate!=""){
										var date =new Date(json.wellInfoBean.projectWarrantyExpireDate);
										Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(date);
									}
									Ext.getCmp("modifyResponsiblePersonId").setValue(json.wellInfoBean.responsiblePersonId);
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
	var deleteWellBtn=Ext.getCmp("deleteWellBtn");
	deleteWellBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("wellId"));
			}
			Ext.Ajax.request({
				url : 'pipe!searchWellToCable.action',
				method : 'post',
				params : {
					ids : ids
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (json.success == true) {
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
										url : 'pipe!dodeletewell.action',
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
															+ '删除井信息失败!',
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
														+ '删除井信息失败!',
												buttons : {
													ok : "确定"
												}
											});
										}
									});
								}
							}
						});
					}else{
						Ext.Msg.show({
							title : '提示',
							width : 300,
							msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ '请先解除井和光缆的关系!',
							buttons : {
								ok : "确定"
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
	//井立面的按钮
	var wellFacadeBtn=Ext.getCmp("wellFacadeBtn");
	wellFacadeBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = "";
			ids=(records[0].get("wellId"));
			if(!grid.wellFaceWindow){
				grid.wellFaceWindow= new com.increase.cen.pipe.pipeWindow({
					id:"wellFaceWindow",
					iconCls : 'i-script-window',
					title:"井立面",
					width:600,
					items:[({
						height:400,
						xtype:'seewellface'
					})]
				
				});
			}
			var store = Ext.getCmp("seewellface").getStore();
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
					'faceInfo.wellId' : ids
					
				}
			});
			grid.wellFaceWindow.show();
			
			
		}else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择一条数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	//定位
	var dwWellBtn=Ext.getCmp("dwWellBtn");
	dwWellBtn.on('click', function() {
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("wellId"));
			}
			Ext.Ajax.request({
				url : 'pipe!editwell.action',
				method : 'post',
				params : {
					ids : ids
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					var addr=encodeURI(json.wellInfoBean.wellAddress,"utf-8");
					var name=json.wellInfoBean.wellNameSub;
					name=name.replace("#","P");
					var wellname=encodeURI(name,"utf-8");
	    			initialize(json.wellInfoBean.longitude, json.wellInfoBean.latitude, addr,wellname);
				}
			});
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
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("wellNameSub").setValue("");
		Ext.getCmp("manholeTypeEnumId").setValue("");
		Ext.getCmp("wellAddress").setValue("");
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
	})
	//获得搜索按钮
	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var wellname = Ext.getCmp("wellNameSub").getValue();
		var type = Ext.getCmp("manholeTypeEnumId").getValue();
		var welladdress = Ext.getCmp("wellAddress").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'wellInfoBean.wellNameSub' : wellname,
				'wellInfoBean.manholeTypeEnumId' : type,
				'wellInfoBean.wellAddress' : welladdress
			}
		});
		grid.getStore().on('beforeload',function(){
   				grid.getStore().baseParams = {
   					'wellInfoBean.wellNameSub' : wellname,
					'wellInfoBean.manholeTypeEnumId' : type,
					'wellInfoBean.wellAddress' : welladdress
   				};
		});
		grid.getView().refresh();
	});
	//tengxy
	var exportWellBtn=Ext.getCmp("exportWellBtn");
	exportWellBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("wellId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getWelllist.action?ids="+ids;
			
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
	//tengxy
	var importWellBtn = Ext.getCmp("importWellBtn");
	importWellBtn.on('click', function(){
			grid.importWellWindow = new com.increase.cen.pipe.pipeWindow({
				id : "importWellWindow",
				height:500,
				width:600,
				title : '数据导入',
				iconCls : 'i-script-window',
				items : [{
					id:'importWell',
					fileUpload : true,
					height:300,
					xtype : 'importWell'// 窗口里的表单
				}]
			});
		
		grid.importWellWindow.show();
	});
	
	
	
	
	
	
	
	
})
function initialize(lon,lat,addr,wellname) {
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： cis/website/meun.htm
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName); // 获取主机地址，如： http://localhost:8080
	var localhostPaht = curWwwPath.substring(pos); // 获取带"/"的项目名，如：/cis
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') +1);
	if(lat==0&&lon==0){
		alert("井无坐标记录，请补全信息后再查看。");
	}else{
		var url = projectName+"/pages/pipe/dwWell.jsp?lat="+lat+"&lon="+lon+"&addr="+addr+"&wellname="+wellname;
		//window.showModalDialog(url,"newwindow","dialogHeight=552px;dialogWidth=699px;center=yes; toolbar=no; menubar=no; scrollbars=no;  status=no");//resizable=yes;
		//window.open(url,0,"status=no,toolbar=no,menubar=no,location=no,titlebar=no,resizable=no,width=699px,height=552, top=100,left=300");
		var iWidth=699; //弹出窗口的宽度;
		var iHeight=552; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
		var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
		window.open(url,"newwindow",param);
	}
}
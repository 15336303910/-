Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, 
	{header : '管道段ID',dataIndex : 'pipeSegmentId',hidden : true}, 
	{header : '管道段名称',dataIndex : 'pipeSegmentName',width : 400,sortable : true}, 
	{header : '管道段编号',dataIndex : 'pipeSegmentCode',width : 180,sortable : true}, 
	{header : '管道类型',dataIndex : 'pipeSegmentType',width : 70,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "人井管段";
			}else if(v=='2'){
				return "引上类型";
			}else if(v=='3'){
				return "引入类型";
			}
		}
	}, 
	{header : '起始设施类型',dataIndex : 'startDeviceType',width : 90,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "井";
			}else if(v=='2'){
				return "杆";
			}
		}
	}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
	{header : '终止设施类型',dataIndex : 'endDeviceType',width : 90,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "井";
			}else if(v=='2'){
				return "杆";
			}
		}
	},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 150,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!searchPipeSegmentlist.action"
	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'pipeSegmentId',type : 'string',mapping : 'pipeSegmentId',hidden : true
	}, {
		name : 'pipeSegmentName',
		type : 'string',
		mapping : 'pipeSegmentName'
	},{
		name : 'pipeSegmentCode',
		type : 'string',
		mapping : 'pipeSegmentCode'
	},{
		name : 'pipeSegmentType',
		type : 'string',
		mapping : 'pipeSegmentType'
	}, {
		name : 'startDeviceType',
		type : 'string',
		mapping : 'startDeviceType'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceType',
		type : 'string',
		mapping : 'endDeviceType'
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
		root : "pipesegments"
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
    	data : [['', ''],['1', '人井管段'], ['2', '引上类型'], ['3', '引入类型']]
	})
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：管道段管理",
		region : 'center',
		id : "pipeSegmentGrid",
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
				help("pages/help/pipesegment.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addPipeSegmentBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyPipeSegmentBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deletePipeSegmentBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '生成管道',
			id : 'addPipeBySegBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'dwPipeSegmentBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'exportPipesegBtn',
			text : '导出数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'importPipesegBtn',
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
			text : '管道段名称：'
		},{
			xtype : 'textfield',
			id:'pipeSegmentName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '管道段编号：'
		},{
			xtype : 'textfield',
			id:'pipeSegmentCode',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '管道类型：'
		},{
			id:'pipeSegmentType',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.typeStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'pipeSegmentType'
			
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
	var addPipeSegmentBtn=Ext.getCmp("addPipeSegmentBtn");
	addPipeSegmentBtn.on('click',function(){
		grid.addPipeSegmentWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPipeSegmentWindow",
			title : '添加管道段',
			iconCls : 'i-script-window',
			width:600,
			items : [{
				id : "addPipeSegment",
				height : 400,
				xtype : 'addPipeSegment'// 窗口里的表单
			}]
		});
		grid.addPipeSegmentWindow.show("addPipeSegmentBtn");
	});
	//获得修改按钮
	var modifyPipeSegmentBtn=Ext.getCmp("modifyPipeSegmentBtn");
	modifyPipeSegmentBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeSegmentId"));// 获得选择的编号
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
							grid.modifyPipeSegmentWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyPipeSegmentWindow",
								title : '修改管道段',
								iconCls : 'i-script-window',
								width:600,
								items : [{
									id : "modifyPipeSegment",
									height : 400,
									xtype : 'modifyPipeSegment'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyPipeSegmentWindow.show("modifyPipeSegmentBtn");
							// 表单加载数据
							var modifyPipeSegmentForm = Ext.getCmp("modifyPipeSegment");
							modifyPipeSegmentForm.getForm().load({
								url : 'pipe!editpipeSegment.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPipeSegmentId").setValue(json.pipeSegmentInfoBean.pipeSegmentId);
									Ext.getCmp("modifyPipeSegmentName").setValue(json.pipeSegmentInfoBean.pipeSegmentName);
									Ext.getCmp("modifyPipeSegmentCode").setValue(json.pipeSegmentInfoBean.pipeSegmentCode);
									Ext.getCmp("modifyPipeSegmentType").setValue(json.pipeSegmentInfoBean.pipeSegmentType);
									Ext.getCmp("modifyPipeId").setValue(json.pipeSegmentInfoBean.pipeId);
									Ext.getCmp("modifyPipeName").setValue(json.pipeSegmentInfoBean.pipeName);
									Ext.getCmp("modifyRoadName").setValue(json.pipeSegmentInfoBean.roadName);
									Ext.getCmp("modifyPipeSegmentLength").setValue(json.pipeSegmentInfoBean.pipeSegmentLength);
									Ext.getCmp("modifyStartNestTopElevation").setValue(json.pipeSegmentInfoBean.startNestTopElevation);
									Ext.getCmp("modifyEndNestTopElevation").setValue(json.pipeSegmentInfoBean.endNestTopElevation);
									Ext.getCmp("modifyStartNestBottomElevation").setValue(json.pipeSegmentInfoBean.startNestBottomElevation);
									Ext.getCmp("modifyEndNestBottomElevation").setValue(json.pipeSegmentInfoBean.endNestBottomElevation);
									Ext.getCmp("modifyStartDeviceId").setValue(json.pipeSegmentInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.pipeSegmentInfoBean.startDeviceName);
									Ext.getCmp("modifyStartDeviceType").setValue(json.pipeSegmentInfoBean.startDeviceType);
									Ext.getCmp("modifyEndDeviceId").setValue(json.pipeSegmentInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.pipeSegmentInfoBean.endDeviceName);
									Ext.getCmp("modifyEndDeviceType").setValue(json.pipeSegmentInfoBean.endDeviceType);
									Ext.getCmp("modifyStartFaceNo").setValue(json.pipeSegmentInfoBean.startFaceNo);
									Ext.getCmp("modifyEndFaceNo").setValue(json.pipeSegmentInfoBean.endFaceNo);
									Ext.getCmp("modifyBuriedDepth").setValue(json.pipeSegmentInfoBean.buriedDepth);
									Ext.getCmp("modifyPipeLineLayingEnumId").setValue(json.pipeSegmentInfoBean.pipeLineLayingEnumId);
									Ext.getCmp("modifyConstructionSharingEnumId").setValue(json.pipeSegmentInfoBean.constructionSharingEnumId);
									Ext.getCmp("modifyConstructionSharingOrg").setValue(json.pipeSegmentInfoBean.constructionSharingOrg);
									Ext.getCmp("modifySharingTypeEnumId").setValue(json.pipeSegmentInfoBean.sharingTypeEnumId);
									Ext.getCmp("modifyHoleQuantity").setValue(json.pipeSegmentInfoBean.holeQuantity);
									Ext.getCmp("modifyBuildAndShareNum").setValue(json.pipeSegmentInfoBean.buildAndShareNum);
									Ext.getCmp("modifyOccupiedSharingHoleQuantity").setValue(json.pipeSegmentInfoBean.occupiedSharingHoleQuantity);
									Ext.getCmp("modifyRentFlag").setValue(json.pipeSegmentInfoBean.rentFlag);
									Ext.getCmp("modifyRentOrg").setValue(json.pipeSegmentInfoBean.rentOrg);
									Ext.getCmp("modifyRentChargingCode").setValue(json.pipeSegmentInfoBean.rentChargingCode);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.pipeSegmentInfoBean.resourceLifePeriodEnumId);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyLedup").hide();
									Ext.getCmp("modifywellmap").hide();
									//用于判断名称
									Ext.getCmp("oldPipeSegmentName").setValue(json.pipeSegmentInfoBean.pipeSegmentName);
									Ext.getCmp("modifyPipeSegmentId").setValue(json.pipeSegmentInfoBean.pipeSegmentId);
									Ext.getCmp("modifyPipeSegmentName").setValue(json.pipeSegmentInfoBean.pipeSegmentName);
									Ext.getCmp("modifyPipeSegmentCode").setValue(json.pipeSegmentInfoBean.pipeSegmentCode);
									Ext.getCmp("modifyPipeSegmentType").setValue(json.pipeSegmentInfoBean.pipeSegmentType);
									if(json.pipeSegmentInfoBean.pipeSegmentType=="1"){
										Ext.getCmp("modifywellmap").show();
									}
									Ext.getCmp("modifyPipeId").setValue(json.pipeSegmentInfoBean.pipeId);
									Ext.getCmp("modifyPipeName").setValue(json.pipeSegmentInfoBean.pipeName);
									Ext.getCmp("modifyRoadName").setValue(json.pipeSegmentInfoBean.roadName);
									Ext.getCmp("modifyPipeSegmentLength").setValue(json.pipeSegmentInfoBean.pipeSegmentLength);
									Ext.getCmp("modifyStartNestTopElevation").setValue(json.pipeSegmentInfoBean.startNestTopElevation);
									Ext.getCmp("modifyEndNestTopElevation").setValue(json.pipeSegmentInfoBean.endNestTopElevation);
									Ext.getCmp("modifyStartNestBottomElevation").setValue(json.pipeSegmentInfoBean.startNestBottomElevation);
									Ext.getCmp("modifyEndNestBottomElevation").setValue(json.pipeSegmentInfoBean.endNestBottomElevation);
									Ext.getCmp("modifyStartDeviceId").setValue(json.pipeSegmentInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.pipeSegmentInfoBean.startDeviceName);
									Ext.getCmp("modifyStartDeviceType").setValue(json.pipeSegmentInfoBean.startDeviceType);
									if(json.pipeSegmentInfoBean.startDeviceType=="2"){
										Ext.getCmp("modifystartdevicename").setValue("电杆");
									}else{
										Ext.getCmp("modifystartdevicename").setValue("井");
									}
									Ext.getCmp("modifyEndDeviceId").setValue(json.pipeSegmentInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.pipeSegmentInfoBean.endDeviceName);
									Ext.getCmp("modifyEndDeviceType").setValue(json.pipeSegmentInfoBean.endDeviceType);
									if(json.pipeSegmentInfoBean.endDeviceType=="2"){
										Ext.getCmp("modifyenddevicename").setValue("电杆");
									}else{
										Ext.getCmp("modifyenddevicename").setValue("井");
									}
									Ext.getCmp("modifyStartFaceNo").setValue(json.pipeSegmentInfoBean.startFaceNo);
									Ext.getCmp("modifyEndFaceNo").setValue(json.pipeSegmentInfoBean.endFaceNo);
									Ext.getCmp("modifyBuriedDepth").setValue(json.pipeSegmentInfoBean.buriedDepth);
									Ext.getCmp("modifyPipeLineLayingEnumId").setValue(json.pipeSegmentInfoBean.pipeLineLayingEnumId);
									Ext.getCmp("modifyConstructionSharingEnumId").setValue(json.pipeSegmentInfoBean.constructionSharingEnumId);
									Ext.getCmp("modifyConstructionSharingOrg").setValue(json.pipeSegmentInfoBean.constructionSharingOrg);
									Ext.getCmp("modifySharingTypeEnumId").setValue(json.pipeSegmentInfoBean.sharingTypeEnumId);
									Ext.getCmp("modifyHoleQuantity").setValue(json.pipeSegmentInfoBean.holeQuantity);
									Ext.getCmp("modifyBuildAndShareNum").setValue(json.pipeSegmentInfoBean.buildAndShareNum);
									Ext.getCmp("modifyOccupiedSharingHoleQuantity").setValue(json.pipeSegmentInfoBean.occupiedSharingHoleQuantity);
									Ext.getCmp("modifyRentFlag").setValue(json.pipeSegmentInfoBean.rentFlag);
									Ext.getCmp("modifyRentOrg").setValue(json.pipeSegmentInfoBean.rentOrg);
									Ext.getCmp("modifyRentChargingCode").setValue(json.pipeSegmentInfoBean.rentChargingCode);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.pipeSegmentInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyAreano").setValue(json.pipeSegmentInfoBean.areano);
									Ext.getCmp("modifyAreaname").setValue(json.pipeSegmentInfoBean.areaname);
									if(json.pipeSegmentInfoBean.rentStartDate!=null||json.pipeSegmentInfoBean.rentStartDate!=""){
										var date =new Date(json.pipeSegmentInfoBean.rentStartDate);
										Ext.getCmp("modifyRentStartDate").setValue(date);
									}
									if(json.pipeSegmentInfoBean.rentEndDate!=null||json.pipeSegmentInfoBean.rentEndDate!=""){
										var date =new Date(json.pipeSegmentInfoBean.rentEndDate);
										Ext.getCmp("modifyRentEndDate").setValue(date);
									}
									if(json.ledupInfoBean!=null){
										Ext.getCmp("modifyLedup").show();
										Ext.getCmp("modifyLedupPointId").setValue(json.ledupInfoBean.ledupPointId);
										Ext.getCmp("modifyLedupPointName").setValue(json.ledupInfoBean.ledupPointName);
										Ext.getCmp("modifyLedupPointCode").setValue(json.ledupInfoBean.ledupPointCode);
										Ext.getCmp("modifyWellName").setValue(json.ledupInfoBean.wellName);
										Ext.getCmp("modifyWellId").setValue(json.ledupInfoBean.wellId);
										Ext.getCmp("modifyPoleName").setValue(json.ledupInfoBean.poleName);
										Ext.getCmp("modifyPoleId").setValue(json.ledupInfoBean.poleId);
										Ext.getCmp("modifyTubeQuantity").setValue(json.ledupInfoBean.tubeQuantity);
										Ext.getCmp("modifyOccupiedTubeQuantity").setValue(json.ledupInfoBean.occupiedTubeQuantity);
										Ext.getCmp("modifyReservedTubeQuantity").setValue(json.ledupInfoBean.reservedTubeQuantity);
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
	var deletePipeSegmentBtn=Ext.getCmp("deletePipeSegmentBtn");
	deletePipeSegmentBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeSegmentId"));
			}
			Ext.Ajax.request({
				url : 'pipe!searchPipeSegmentToCable.action',
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
										url : 'pipe!dodeletepipeSegment.action',
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
															+ '删除管道段信息失败!',
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
														+ '删除管道段信息失败!',
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
							msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ '请先解除该管道段与光缆关系!',
							buttons : {
								ok : "确定"
							}
						});
					}
				}
			})
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
	var addPipeBySegBtn=Ext.getCmp("addPipeBySegBtn");
	addPipeBySegBtn.on('click',function(){
		grid.addPipeBySegWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPipeBySegWindow",
			title : '生成管道',
			width:700,
			iconCls : 'i-script-window',
			items : [{
				id : "addPipeBySeg",
				height : 500,
				xtype : 'addPipeBySeg'// 窗口里的表单
			}]
		});
		// 显示窗口
		grid.addPipeBySegWindow.show("addPipeBySegBtn");
	});
	var dwPipeSegmentBtn=Ext.getCmp("dwPipeSegmentBtn");
	dwPipeSegmentBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeSegmentId"));
			}
			var url=context_path+"/pipe!pipeSegmentDW.action?ids="+ids;
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
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("pipeSegmentName").setValue("");
		Ext.getCmp("pipeSegmentCode").setValue("");
		Ext.getCmp("pipeSegmentType").setValue("");
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
		var pipesname = Ext.getCmp("pipeSegmentName").getValue();
		var pipestype = Ext.getCmp("pipeSegmentType").getValue();
		var pipescode = Ext.getCmp("pipeSegmentCode").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'pipeSegmentInfoBean.pipeSegmentName' : pipesname,
				'pipeSegmentInfoBean.pipeSegmentCode' : pipescode,
				'pipeSegmentInfoBean.pipeSegmentType' : pipestype
			}
		});
		grid.getStore().on('beforeload',function(){
   			grid.getStore().baseParams = {
   				'pipeSegmentInfoBean.pipeSegmentName' : pipesname,
				'pipeSegmentInfoBean.pipeSegmentCode' : pipescode,
				'pipeSegmentInfoBean.pipeSegmentType' : pipestype
   			};
		});
		grid.getView().refresh();
	});
	
	var exportPipesegBtn=Ext.getCmp("exportPipesegBtn");
	exportPipesegBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("pipeSegmentId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getPipeseglist.action?ids="+ids;
			
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
	
	var importPipesegBtn = Ext.getCmp("importPipesegBtn");
	importPipesegBtn.on('click', function(){
			grid.importPipesegWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "importPipesegWindow",
				height:500,
				width:600,
				title : '数据导入',
				iconCls : 'i-script-window',
				items : [{
					id:'importPipeseg',
					fileUpload : true,
					height:300,
					xtype : 'importPipeseg'// 窗口里的表单
				}]
			});
		
		grid.importPipesegWindow.show();
	});
	
})
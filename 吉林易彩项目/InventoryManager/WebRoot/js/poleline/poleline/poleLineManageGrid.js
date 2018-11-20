Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
	{header : '杆路ID',dataIndex : 'poleLineId',hidden : true}, 
	{header : '杆路名称',dataIndex : 'poleLineName',width : 220,sortable : true}, 
	{header : '杆路编码',dataIndex : 'poleLineCode',width : 180,sortable : true}, 
	{header : '杆路级别',dataIndex : 'poleLineLevel',width : 100,sortable : true,
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
	{header : '杆路长度（米）',dataIndex : 'poleLineLength',width : 180,sortable : true}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 180,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 180,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!list.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'poleLineId',type : 'string',mapping : 'poleLineId',hidden : true
	}, {
		name : 'poleLineName',
		type : 'string',
		mapping : 'poleLineName'
	}, {
		name : 'poleLineCode',
		type : 'string',
		mapping : 'poleLineCode'
	}, {
		name : 'poleLineLevel',
		type : 'string',
		mapping : 'poleLineLevel'
	}, {
		name : 'poleLineLength',
		type : 'string',
		mapping : 'poleLineLength'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	},{
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "polelines"
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
	this.levelStore=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
    	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：杆路管理",
		region : 'center',
		id : "poleLineGrid",
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
				help("pages/help/poleline.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addPoleLineBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyPoleLineBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deletePoleLineBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'dwPoleLineBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'seePolelineBtn',
			text : '查看杆路段列表',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'addPoleLSBtn',
			text : '生成杆路段',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'exportPoleBtn',
			text : '导出数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'importPoleBtn',
			text : '导入数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshPoleLineBtn',
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
			text : '杆路名称：'
		},{
			xtype : 'textfield',
			id:'poleLineName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '杆路编码：'
		},{
			xtype : 'textfield',
			id:'poleLineCode',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '杆路级别：'
		},{
			id:'poleLineLevel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.levelStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'poleLinelevel'
			
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
			id : 'seekPoleLineBtn',
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
	var addPoleLineBtn=Ext.getCmp("addPoleLineBtn");
	addPoleLineBtn.on('click',function(){
		grid.addPoleLineWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPoleLineWindow",
			title : '添加杆路',
			iconCls : 'i-script-window',
			width:600,
			items : [{
				id : "addPoleLine",
				height : 380,
				width:600,
				xtype : 'addPoleLine'// 窗口里的表单
			}]
		});
		grid.addPoleLineWindow.show("addPoleLineBtn");
	});
	//获得修改按钮
	var modifyPoleLineBtn=Ext.getCmp("modifyPoleLineBtn");
	modifyPoleLineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));// 获得选择的编号
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
							grid.modifyPoleLineWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyPoleLineWindow",
								title : '修改杆路',
								iconCls : 'i-script-window',
								width:600,
								items : [{
									id : "modifyPoleLine",
									height : 360,
									xtype : 'modifyPoleLine'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyPoleLineWindow.show("modifyPoleLineBtn");
							// 表单加载数据
							var modifyPoleLineForm = Ext.getCmp("modifyPoleLine");
							modifyPoleLineForm.getForm().load({
								url : 'poleline!editpoleline.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPoleLineId").setValue(json.polelineInfoBean.poleLineId);
									Ext.getCmp("modifyPoleLineName").setValue(json.polelineInfoBean.poleLineName);
									Ext.getCmp("modifyPLName").setValue(json.polelineInfoBean.poleLineName);
									Ext.getCmp("modifyPoleLineCode").setValue(json.polelineInfoBean.poleLineCode);
									Ext.getCmp("modifyPoleLineLevel").setValue(json.polelineInfoBean.poleLineLevel);
									Ext.getCmp("modifyPoleLineLength").setValue(json.polelineInfoBean.poleLineLength);
									Ext.getCmp("modifyStartDeviceId").setValue(json.polelineInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.polelineInfoBean.startDeviceName);
									Ext.getCmp("modifyEndDeviceId").setValue(json.polelineInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.polelineInfoBean.endDeviceName);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.polelineInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.polelineInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.polelineInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.polelineInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.polelineInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyProjectName").setValue(json.polelineInfoBean.projectName);
									Ext.getCmp("modifyProjectCode").setValue(json.polelineInfoBean.projectCode);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.polelineInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("modifyRenewalExpirationDate").setValue(json.polelineInfoBean.renewalExpirationDate);
									Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(json.polelineInfoBean.projectWarrantyExpireDate);
									Ext.getCmp("comboxWithTree").setValue(json.polelineInfoBean.areaname);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPLName").setValue(json.polelineInfoBean.poleLineName);
									Ext.getCmp("modifyPoleLineId").setValue(json.polelineInfoBean.poleLineId);
									Ext.getCmp("modifyPoleLineName").setValue(json.polelineInfoBean.poleLineName);
									Ext.getCmp("modifyPLName").setValue(json.polelineInfoBean.poleLineName);
									Ext.getCmp("modifyPoleLineCode").setValue(json.polelineInfoBean.poleLineCode);
									Ext.getCmp("modifyPoleLineLevel").setValue(json.polelineInfoBean.poleLineLevel);
									Ext.getCmp("modifyPoleLineLength").setValue(json.polelineInfoBean.poleLineLength);
									Ext.getCmp("modifyStartDeviceId").setValue(json.polelineInfoBean.startDeviceId);
									Ext.getCmp("modifyStartDeviceName").setValue(json.polelineInfoBean.startDeviceName);
									Ext.getCmp("modifyEndDeviceId").setValue(json.polelineInfoBean.endDeviceId);
									Ext.getCmp("modifyEndDeviceName").setValue(json.polelineInfoBean.endDeviceName);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.polelineInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.polelineInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.polelineInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.polelineInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.polelineInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("modifyProjectName").setValue(json.polelineInfoBean.projectName);
									Ext.getCmp("modifyProjectCode").setValue(json.polelineInfoBean.projectCode);
									Ext.getCmp("modifyResourceLifePeriodEnumId").setValue(json.polelineInfoBean.resourceLifePeriodEnumId);
									Ext.getCmp("comboxWithTree").setValue(json.polelineInfoBean.areaname);
									if(json.polelineInfoBean.renewalExpirationDate!=null||json.polelineInfoBean.renewalExpirationDate!=""){
										var date =new Date(json.polelineInfoBean.renewalExpirationDate);
										Ext.getCmp("modifyRenewalExpirationDate").setValue(date);
									}
									if(json.polelineInfoBean.projectWarrantyExpireDate!=null||json.polelineInfoBean.projectWarrantyExpireDate!=""){
										var date =new Date(json.polelineInfoBean.projectWarrantyExpireDate);
										Ext.getCmp("modifyProjectWarrantyExpireDate").setValue(date);
									}
									
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
	var deletePoleLineBtn=Ext.getCmp("deletePoleLineBtn");
	deletePoleLineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		var records = selModel.getSelections();// 返回所有选中的记录
		var ids = [];
		if (selModel.hasSelection()) {
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));
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
							url : 'poleline!dodelete.action',
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
												+ '删除杆路信息失败!',
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
											+ '删除杆路信息失败!',
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
	var dwPoleLineBtn=Ext.getCmp("dwPoleLineBtn");
	dwPoleLineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));
			}
			if(ids.length > 1){
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行删除!',
					buttons : {
						ok : "确定"
					}
				});
			}else if(ids.length == 1){
			var url=context_path+"/poleline!polelineDW.action?ids="+ids;
		    var iWidth=699; //弹出窗口的宽度;
			var iHeight=552; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;	
			var param = "width="+iWidth+",height="+iHeight+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
			window.open(url,"newwindow",param);
			}
		} else {
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
	
	var exportPoleBtn=Ext.getCmp("exportPoleBtn");
	exportPoleBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getPolelinelist.action?ids="+ids;
			
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
	var refreshPoleLineBtn = Ext.getCmp("refreshPoleLineBtn");
	refreshPoleLineBtn.on('click', function() {
		Ext.getCmp("poleLineName").setValue("");
		Ext.getCmp("poleLineCode").setValue("");
		Ext.getCmp("poleLineLevel").setValue("");
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
	var seekPoleLineBtn = Ext.getCmp("seekPoleLineBtn");
	seekPoleLineBtn.on('click', function() {
		var polelinename = Ext.getCmp("poleLineName").getValue();
		var polelinecode = Ext.getCmp("poleLineCode").getValue();
		var polelinelevel = Ext.getCmp("poleLineLevel").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'polelineInfoBean.poleLineName' : polelinename,
				'polelineInfoBean.poleLineCode' : polelinecode,
				'polelineInfoBean.poleLineLevel' : polelinelevel
			}
		});
		grid.getStore().on('beforeload',function(){
   				grid.getStore().baseParams = {
   					'polelineInfoBean.poleLineName' : polelinename,
					'polelineInfoBean.poleLineCode' : polelinecode,
					'polelineInfoBean.poleLineLevel' : polelinelevel
   				};
		});
		grid.getView().refresh();
	});

	//查询杆路段列表
	var seePolelineBtn=Ext.getCmp("seePolelineBtn");
	seePolelineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));// 获得选择的编号
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
				var poleLineGrid=Ext.getCmp("poleLineGrid");
				if(!poleLineGrid.seePolelineWindow){
				poleLineGrid.seePolelineWindow=new com.increase.cen.poleline.poleLSGridWindow({
					id : "seePolelineWindow",
					height : 400,
					width : 1000,
					title : '查看杆路段',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'poleLSGrid'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("poleLSGrid").getStore();
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
						"polelineSegmentInfoBean.poleLineId":ids[0],
						start : 0,
						limit : limit
					}
				});
				store.on('beforeload',function(){
					store.baseParams = {
				   			start : 0,
							limit : limit,
							"polelineSegmentInfoBean.poleLineId":ids[0]
				   	};
				});
	
				poleLineGrid.seePolelineWindow.show();
				
//				poleLineGrid.seePolelineWindow.on('beforeclose',function(){
////					this.Ext.getCmp("pipeSegmentGrid1").destory();
//					Ext.getCmp("poleLSGrid").getStore().removeAll();
//					
//				});
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
	
	
	//生成杆路段
	var addPoleLSBtn=Ext.getCmp("addPoleLSBtn");
	addPoleLSBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleLineId"));// 获得选择的编号
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
				var poleLineGrid=Ext.getCmp("poleLineGrid");
				if(!poleLineGrid.addPoleLSWindow){
				poleLineGrid.addPoleLSWindow=new com.increase.cen.poleline.poleLSGridWindow({
					id : "addPoleLSWindow",
					height : 400,
					width : 1000,
					title : '选择设备',
					iconCls : 'i-script-window',
					items : [({
						autoHeight :true,
						xtype : 'poleGrid',// 窗口里的表单
						polelineId:records[0].get("poleLineId")
					})]
				});
				}
				var store = Ext.getCmp("poleGridPanel").getStore();
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
						"poleInfoBean.poleLineIdStr":'(\''+ids[0]+'\','+"\'0\')",
						start : 0,
						limit : limit
					}
				});
				store.on('beforeload',function(){
					store.baseParams = {
				   			start : 0,
							limit : limit,
							"poleInfoBean.poleLineIdStr":'\''+ids[0]+'\''+"\'0\'"
				   	};
				});
	
				poleLineGrid.addPoleLSWindow.show();
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
	
	grid.on('rowdblclick', function(g, r, e) {
		e.stopEvent();
		var poleline=g.getStore().getAt(r).json;
		
		grid.seePoleline=new com.increase.cen.poleline.PolelineWindow({
			id : "seePolelineWindow",
			width : 750,
			height : 250,
			items : [new com.increase.cen.poleline.seePoleline({
				poleline : poleline,
				windowId : "seePolelineWindow"
			})]
		});
		
		grid.seePoleline.show();
	});
	
	var importPoleBtn=Ext.getCmp("importPoleBtn");
	importPoleBtn.on('click', function(){
		grid.importPolelineWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "importPolelineWindow",
			height:500,
			width:600,
			title : '数据导入',
			iconCls : 'i-script-window',
			items : [{
				id:'importPoleline',
				fileUpload : true,
				height:300,
				xtype : 'importPoleline'// 窗口里的表单
			}]
		});
	
	grid.importPolelineWindow.show();
});
	
});
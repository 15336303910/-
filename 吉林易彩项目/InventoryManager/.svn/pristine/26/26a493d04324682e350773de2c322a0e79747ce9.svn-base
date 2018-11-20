Ext.onReady(function() {
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, 
	{header : '电杆ID',dataIndex : 'poleId',hidden : true},
	{header : '电杆名称', dataIndex : 'poleName', width : 150,sortable : true},
	{header : '电杆编号', dataIndex : 'poleNo', width : 150,sortable : true},
	{header : '电杆类型',dataIndex : 'poleTypeEnumId',width : 100,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "普通杆";
			}else if(v=='2'){
				return "单接杆";
			}else if(v=='3'){
				return "双接杆";
			}else if(v=='4'){
				return "H型杆";
			}else if(v=='5'){
				return "A型杆";
			}else if(v=='6'){
				return "L型杆";
			}else if(v=='7'){
				return "三角杆";
			}else if(v=='8'){
				return "井型杆";
			}else if(v=='9'){
				return "引上杆";
			}else if(v=='10'){
				return "终端杆";
			}else if(v=='11'){
				return "角杆";
			}else if(v=='12'){
				return "分歧杆";
			}else if(v=='13'){
				return "T型杆";
			}else if(v=='14'){
				return "跨路杆";
			}
		}
	}, 
	{header : '电杆经度',dataIndex : 'poleLongitude',width : 180,sortable : true},
	{header : '电杆纬度', dataIndex : 'poleLatitude', width : 180, sortable : true},
	{header : '接头盒名称', dataIndex : 'jointName', width : 180, sortable : true},
	{header : '创建日期', dataIndex : 'creationDate', width : 180, sortable : true,
			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '维护区域', dataIndex : 'region', width : 180, sortable : true}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!polelist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'poleId',type : 'string',mapping : 'poleId',hidden : true
	}, {
		name : 'poleName',
		type : 'string',
		mapping : 'poleName'
	}, {
		name : 'poleNo',
		type : 'string',
		mapping : 'poleNo'
	}, {
		name : 'poleTypeEnumId',
		type : 'string',
		mapping : 'poleTypeEnumId'
	},  {
		name : 'poleLongitude',
		type : 'string',
		mapping : 'poleLongitude'
	},{
		name : 'poleLatitude',
		type : 'string',
		mapping : 'poleLatitude'
	},{
		name : 'jointName',
		type : 'string',
		mapping : 'jointName'
	},{
		name : 'creationDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'creationDate'
	},{
		name : 'region',
		type : 'string',
		mapping : 'region'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "poles"
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
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：电杆管理",
		region : 'center',
		id : "poleGrid",
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
				help("pages/help/pole.jsp");
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addPoleBtn',
			icon : "imgs/fiber/link_add.png",
			cls : "x-btn-text-icon"

		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyPoleBtn',
			text : '修改',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'deletePoleBtn',
			text : '删除',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'exportPoleBtn',
			text : '导出数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'importPoleBtn',
			text : '导入数据',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		}, {
			id : 'dwPoleBtn',
			text : '定位',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		}, {
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
			text : '电杆名称：'
		},{
			xtype : 'textfield',
			id:'poleNameSub',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '电杆编码：'
		},{
			xtype : 'textfield',
			id:'poleCode',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '电杆地址：'
		},{
			xtype : 'textfield',
			id:'poleAddress',
			cls:'sel-textfield-width'
			
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
	var addPoleBtn=Ext.getCmp("addPoleBtn");
	addPoleBtn.on('click',function(){
		grid.addPoleWindow=new com.increase.cen.poleline.PolelineWindow({
			id : "addPoleWindow",
			title : '添加电杆',
			iconCls : 'i-script-window',
			width:650,
			items : [{
				id : "addPole",
				height : 400,
				width:650,
				xtype : 'addPole'// 窗口里的表单
			}]
		});
		grid.addPoleWindow.show("addPoleBtn");
	});
	//获得修改按钮
	var modifyPoleBtn=Ext.getCmp("modifyPoleBtn");
	modifyPoleBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleId"));// 获得选择的编号
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
							grid.modifyPoleWindow=new com.increase.cen.poleline.PolelineWindow({
								id : "modifyPoleWindow",
								title : '修改电杆',
								iconCls : 'i-script-window',
								width:650,
								items : [{
									id : "modifyPole",
									height : 370,
									xtype : 'modifyPole'// 窗口里的表单
								}]
							});
							// 显示窗口
							grid.modifyPoleWindow.show("modifyPoleBtn");
							// 表单加载数据
							var modifyPoleForm = Ext.getCmp("modifyPole");
							modifyPoleForm.getForm().load({
								url : 'poleline!editpole.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPoleId").setValue(json.poleInfoBean.poleId);
									Ext.getCmp("modifyPoleName").setValue(json.poleInfoBean.poleName);
									Ext.getCmp("modifyPoleNo").setValue(json.poleInfoBean.poleNo);
									Ext.getCmp("modifyPoleSubNo").setValue(json.poleInfoBean.poleSubNo);
									Ext.getCmp("modifyPoleCode").setValue(json.poleInfoBean.poleCode);
									Ext.getCmp("modifyPoleTypeEnumId").setValue(json.poleInfoBean.poleTypeEnumId);
									Ext.getCmp("modifyPoleMaterial").setValue(json.poleInfoBean.poleMaterial);
									Ext.getCmp("modifyPoleLength").setValue(json.poleInfoBean.poleLength);
									Ext.getCmp("modifyBuriedDepth").setValue(json.poleInfoBean.buriedDepth);
									Ext.getCmp("modifyPoleRadius").setValue(json.poleInfoBean.poleRadius);
									Ext.getCmp("modifyPoleAddress").setValue(json.poleInfoBean.poleAddress);
									Ext.getCmp("modifyPoleLongitude").setValue(json.poleInfoBean.poleLongitude);
									Ext.getCmp("modifyPoleLatitude").setValue(json.poleInfoBean.poleLatitude);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.poleInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.poleInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.poleInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.poleInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("comboxWithTree").setValue(json.poleInfoBean.areaname);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									Ext.getCmp("modifyPoleId").setValue(json.poleInfoBean.poleId);
									Ext.getCmp("modifyPoleName").setValue(json.poleInfoBean.poleName);
									Ext.getCmp("modifyPoleNo").setValue(json.poleInfoBean.poleNo);
									//用于判断名称
									Ext.getCmp("oldPoleName").setValue(json.poleInfoBean.poleName);
									Ext.getCmp("oldPoleNo").setValue(json.poleInfoBean.poleNo);
									Ext.getCmp("oldPoleSubNo").setValue(json.poleInfoBean.poleSubNo);
									Ext.getCmp("modifyPoleSubNo").setValue(json.poleInfoBean.poleSubNo);
									Ext.getCmp("modifyPoleCode").setValue(json.poleInfoBean.poleCode);
									Ext.getCmp("modifyPoleTypeEnumId").setValue(json.poleInfoBean.poleTypeEnumId);
									Ext.getCmp("modifyPoleMaterial").setValue(json.poleInfoBean.poleMaterial);
									Ext.getCmp("modifyPoleLength").setValue(json.poleInfoBean.poleLength);
									Ext.getCmp("modifyBuriedDepth").setValue(json.poleInfoBean.buriedDepth);
									Ext.getCmp("modifyPoleRadius").setValue(json.poleInfoBean.poleRadius);
									Ext.getCmp("modifyPoleAddress").setValue(json.poleInfoBean.poleAddress);
									Ext.getCmp("modifyPoleLongitude").setValue(json.poleInfoBean.poleLongitude);
									Ext.getCmp("modifyPoleLatitude").setValue(json.poleInfoBean.poleLatitude);
									Ext.getCmp("modifyPurchasedMaintenanceTime").setValue(json.poleInfoBean.purchasedMaintenanceTime);
									Ext.getCmp("modifyMaintenanceModeEnumId").setValue(json.poleInfoBean.maintenanceModeEnumId);
									Ext.getCmp("modifyMaintenanceOrgId").setValue(json.poleInfoBean.maintenanceOrgId);
									Ext.getCmp("modifyThirdPartyMaintenanceOrg").setValue(json.poleInfoBean.thirdPartyMaintenanceOrg);
									Ext.getCmp("modifyMaintenanceTypeEnumId").setValue(json.poleInfoBean.maintenanceTypeEnumId);
									Ext.getCmp("comboxWithTree").setValue(json.poleInfoBean.areaname);
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
	var deletePoleBtn=Ext.getCmp("deletePoleBtn");
	deletePoleBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleId"));
			}
			if(ids.length > 1){
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请选择一条数据进行删除!',
					buttons : {
						ok : "确定"
					}
				});
			}else if(ids.length == 1){
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
							url : 'poleline!dodeletepole.action',
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
												+ '删除电杆信息失败!',
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
											+ '删除电杆信息失败!',
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
	//tengxy
	var exportPoleBtn=Ext.getCmp("exportPoleBtn");
	exportPoleBtn.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleId"));// 获得选择的编号
			}
			location.href=context_path+"/dataupdate!getPolelist.action?ids="+ids;
			
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
	var importPoleBtn = Ext.getCmp("importPoleBtn");
	importPoleBtn.on('click', function(){
			grid.importPoleWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "importPoleWindow",
				height:500,
				width:600,
				title : '数据导入',
				iconCls : 'i-script-window',
				items : [{
					id : "importPole",
					fileUpload : true,
					autoScroll:true,
					height:300,
					xtype : 'importPole'// 窗口里的表单
				}]
			});
		
		grid.importPoleWindow.show();
	});
	
	//定位
	var dwPoleBtn=Ext.getCmp("dwPoleBtn");
	dwPoleBtn.on('click', function() {
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();// 返回所有选中的记录
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("poleId"));
			}
			if(ids.length > 1){
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行操作!',
					buttons : {
						ok : "确定"
					}

				});
			}else if(ids.length == 1){
			Ext.Ajax.request({
				url : 'poleline!editpole.action',
				method : 'post',
				params : {
					ids : ids
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					var addr=encodeURI(json.poleInfoBean.poleAddress,"utf-8");
					var polename=encodeURI(json.poleInfoBean.poleNameSub,"utf-8");
	    			initialize(json.poleInfoBean.poleLongitude, json.poleInfoBean.poleLatitude, addr,polename);
				}
			});
			}
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
	})
	//获得搜索按钮
	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var polename = Ext.getCmp("poleNameSub").getValue();
		var polecode = Ext.getCmp("poleCode").getValue();
		var poleadress = Ext.getCmp("poleAddress").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'poleInfoBean.poleNameSub' : polename,
				'poleInfoBean.poleCode' : polecode,
				'poleInfoBean.poleAddress' : poleadress
			}
		});
		grid.getStore().on('beforeload',function(){
   				grid.getStore().baseParams = {
   					'poleInfoBean.poleNameSub' : polename,
					'poleInfoBean.poleCode' : polecode,
					'poleInfoBean.poleAddress' : poleadress
   				};
		});
		grid.getView().refresh();
	});

})
function initialize(lon,lat,addr,polename) {
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： cis/website/meun.htm
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName); // 获取主机地址，如： http://localhost:8080
	var localhostPaht = curWwwPath.substring(pos); // 获取带"/"的项目名，如：/cis
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') +1);
	if(lat==0&&lon==0){
		alert("电杆无坐标记录，请补全信息后再查看。");
	}else{
		var url = projectName+"/pages/poleline/dwPole.jsp?lat="+lat+"&lon="+lon+"&addr="+addr+"&polename="+polename;
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